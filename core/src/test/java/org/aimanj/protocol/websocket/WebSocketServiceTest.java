package org.aimanj.protocol.websocket;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import org.aimanj.protocol.AiManj;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.Response;
import org.aimanj.protocol.core.methods.response.ManSubscribe;
import org.aimanj.protocol.core.methods.response.AiManjClientVersion;
import org.aimanj.protocol.websocket.events.NewHeadsNotification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WebSocketServiceTest {

    private static final int REQUEST_ID = 1;

    private WebSocketClient webSocketClient = mock(WebSocketClient.class);
    private ScheduledExecutorService executorService = mock(ScheduledExecutorService.class);

    private WebSocketService service = new WebSocketService(webSocketClient, executorService, true);

    private Request<?, AiManjClientVersion> request = new Request<>(
            "aiManj_clientVersion",
            Collections.<String>emptyList(),
            service,
            AiManjClientVersion.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Request<Object, ManSubscribe> subscribeRequest;

    @Before
    public void before() throws InterruptedException {
        when(webSocketClient.connectBlocking()).thenReturn(true);
        request.setId(1);
    }

    @Test
    public void testThrowExceptionIfServerUrlIsInvalid() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Failed to parse URL: 'invalid\\url'");
        new WebSocketService("invalid\\url", true);
    }

    @Test
    public void testConnectViaWebSocketClient() throws Exception {
        service.connect();

        verify(webSocketClient).connectBlocking();
    }

    @Test
    public void testInterruptCurrentThreadIfConnectionIsInterrupted() throws Exception {
        when(webSocketClient.connectBlocking()).thenThrow(new InterruptedException());
        service.connect();

        assertTrue("Interrupted flag was not set properly",
                Thread.currentThread().isInterrupted());
    }

    @Test
    public void testThrowExceptionIfConnectionFailed() throws Exception {
        thrown.expect(ConnectException.class);
        thrown.expectMessage("Failed to connect to WebSocket");
        when(webSocketClient.connectBlocking()).thenReturn(false);
        service.connect();
    }

    @Test
    public void testNotWaitingForReplyWithUnknownId() {
        assertFalse(service.isWaitingForReply(123));
    }

    @Test
    public void testWaitingForReplyToSentRequest() throws Exception {
        service.sendAsync(request, AiManjClientVersion.class);

        assertTrue(service.isWaitingForReply(request.getId()));
    }

    @Test
    public void testNoLongerWaitingForResponseAfterReply() throws Exception {
        service.sendAsync(request, AiManjClientVersion.class);
        sendGmanVersionReply();

        assertFalse(service.isWaitingForReply(1));
    }

    @Test
    public void testSendWebSocketRequest() throws Exception {
        service.sendAsync(request, AiManjClientVersion.class);

        verify(webSocketClient).send(
                "{\"jsonrpc\":\"2.0\",\"method\":\"aiManj_clientVersion\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testIgnoreInvalidReplies() throws Exception {
        thrown.expect(IOException.class);
        thrown.expectMessage("Failed to parse incoming WebSocket message");
        service.sendAsync(request, AiManjClientVersion.class);
        service.onWebSocketMessage("{");
    }

    @Test
    public void testThrowExceptionIfIdHasInvalidType() throws Exception {
        thrown.expect(IOException.class);
        thrown.expectMessage("'id' expected to be long, but it is: 'true'");
        service.sendAsync(request, AiManjClientVersion.class);
        service.onWebSocketMessage("{\"id\":true}");
    }

    @Test
    public void testThrowExceptionIfIdIsMissing() throws Exception {
        thrown.expect(IOException.class);
        thrown.expectMessage("Unknown message type");
        service.sendAsync(request, AiManjClientVersion.class);
        service.onWebSocketMessage("{}");
    }

    @Test
    public void testThrowExceptionIfUnexpectedIdIsReceived() throws Exception {
        thrown.expect(IOException.class);
        thrown.expectMessage("Received reply for unexpected request id: 12345");
        service.sendAsync(request, AiManjClientVersion.class);
        service.onWebSocketMessage(
                "{\"jsonrpc\":\"2.0\",\"id\":12345,\"result\":\"Gman-version\"}");
    }

    @Test
    public void testReceiveReply() throws Exception {
        CompletableFuture<AiManjClientVersion> reply = service.sendAsync(
                request,
                AiManjClientVersion.class);
        sendGmanVersionReply();

        assertTrue(reply.isDone());
        assertEquals("gman-version", reply.get().getAiManjClientVersion());
    }

    @Test
    public void testReceiveError() throws Exception {
        CompletableFuture<AiManjClientVersion> reply = service.sendAsync(
                request,
                AiManjClientVersion.class);
        sendErrorReply();

        assertTrue(reply.isDone());
        AiManjClientVersion version = reply.get();
        assertTrue(version.hasError());
        assertEquals(
                new Response.Error(-1, "Error message"),
                version.getError());
    }

    @Test
    public void testCloseRequestWhenConnectionIsClosed() throws Exception {
        thrown.expect(ExecutionException.class);
        CompletableFuture<AiManjClientVersion> reply = service.sendAsync(
                request,
                AiManjClientVersion.class);
        service.onWebSocketClose();

        assertTrue(reply.isDone());
        reply.get();
    }

    @Test(expected = ExecutionException.class)
    public void testCancelRequestAfterTimeout() throws Exception {
        when(executorService.schedule(
                any(Runnable.class),
                eq(WebSocketService.REQUEST_TIMEOUT),
                eq(TimeUnit.SECONDS)))
                .then(invocation -> {
                    Runnable runnable = invocation.getArgumentAt(0, Runnable.class);
                    runnable.run();
                    return null;
                });

        CompletableFuture<AiManjClientVersion> reply = service.sendAsync(
                request,
                AiManjClientVersion.class);

        assertTrue(reply.isDone());
        reply.get();
    }

    @Test
    public void testSyncRequest() throws Exception {
        CountDownLatch requestSent = new CountDownLatch(1);

        // Wait for a request to be sent
        doAnswer(invocation -> {
            requestSent.countDown();
            return null;
        }).when(webSocketClient).send(anyString());

        // Send reply asynchronously
        runAsync(() -> {
            try {
                requestSent.await(2, TimeUnit.SECONDS);
                sendGmanVersionReply();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        AiManjClientVersion reply = service.send(request, AiManjClientVersion.class);

        assertEquals(reply.getAiManjClientVersion(), "GMAN-version");
    }

    @Test
    public void testCloseWebSocketOnClose() throws Exception {
        service.close();

        verify(webSocketClient).close();
        verify(executorService).shutdown();
    }

    @Test
    public void testSendSubscriptionReply() throws Exception {
        subscribeToEvents();

        verifyStartedSubscriptionHadnshake();
    }

    @Test
    public void testPropagateSubscriptionEvent() throws Exception {
        CountDownLatch eventReceived = new CountDownLatch(1);
        CountDownLatch disposed = new CountDownLatch(1);
        AtomicReference<NewHeadsNotification> actualNotificationRef = new AtomicReference<>();

        runAsync(() -> {
            Disposable disposable = subscribeToEvents()
                    .subscribe(newHeadsNotification -> {
                        actualNotificationRef.set(newHeadsNotification);
                        eventReceived.countDown();
                    });
            try {
                eventReceived.await(2, TimeUnit.SECONDS);
                disposable.dispose();
                disposed.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        sendSubscriptionConfirmation();
        sendWebSocketEvent();

        assertTrue(disposed.await(6, TimeUnit.SECONDS));
        assertEquals(
                "0xd9263f42a87",
                actualNotificationRef.get().getParams().getResult().getDifficulty());
    }

    @Test
    public void testSendUnsubscribeRequest() throws Exception {
        CountDownLatch unsubscribed = new CountDownLatch(1);

        runAsync(() -> {
            Flowable<NewHeadsNotification> flowable = subscribeToEvents();
            flowable.subscribe().dispose();
            unsubscribed.countDown();

        });
        sendSubscriptionConfirmation();
        sendWebSocketEvent();

        assertTrue(unsubscribed.await(2, TimeUnit.SECONDS));
        verifyUnsubscribed();
    }

    @Test
    public void testStopWaitingForSubscriptionReplyAfterTimeout() throws Exception {
        CountDownLatch errorReceived = new CountDownLatch(1);
        AtomicReference<Throwable> actualThrowable = new AtomicReference<>();

        runAsync(() -> subscribeToEvents().subscribe(new Subscriber<NewHeadsNotification>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
                actualThrowable.set(e);
                errorReceived.countDown();
            }

            @Override
            public void onSubscribe(Subscription s) {
            }

            @Override
            public void onNext(NewHeadsNotification newHeadsNotification) {

            }
        }));

        waitForRequestSent();
        Exception e = new IOException("timeout");
        service.closeRequest(1, e);

        assertTrue(errorReceived.await(2, TimeUnit.SECONDS));
        assertEquals(e, actualThrowable.get());
    }

    @Test
    public void testOnErrorCalledIfConnectionClosed() throws Exception {
        CountDownLatch errorReceived = new CountDownLatch(1);
        AtomicReference<Throwable> actualThrowable = new AtomicReference<>();

        runAsync(() -> subscribeToEvents().subscribe(new Subscriber<NewHeadsNotification>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
                actualThrowable.set(e);
                errorReceived.countDown();
            }

            @Override
            public void onSubscribe(Subscription s) {
            }

            @Override
            public void onNext(NewHeadsNotification newHeadsNotification) {

            }
        }));

        waitForRequestSent();
        sendSubscriptionConfirmation();
        service.onWebSocketClose();

        assertTrue(errorReceived.await(2, TimeUnit.SECONDS));
        assertEquals(IOException.class, actualThrowable.get().getClass());
        assertEquals("Connection was closed", actualThrowable.get().getMessage());
    }

    @Test
    public void testIfCloseObserverIfSubscriptionRequestFailed() throws Exception {
        CountDownLatch errorReceived = new CountDownLatch(1);
        AtomicReference<Throwable> actualThrowable = new AtomicReference<>();

        runAsync(() -> subscribeToEvents().subscribe(new Subscriber<NewHeadsNotification>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
                actualThrowable.set(e);
                errorReceived.countDown();
            }

            @Override
            public void onSubscribe(Subscription s) {
            }

            @Override
            public void onNext(NewHeadsNotification newHeadsNotification) {

            }
        }));

        waitForRequestSent();
        sendErrorReply();

        assertTrue(errorReceived.await(2, TimeUnit.SECONDS));

        Throwable throwable = actualThrowable.get();
        assertEquals(
                IOException.class,
                throwable.getClass()
        );
        assertEquals(
                "Subscription request failed with error: Error message",
                throwable.getMessage());
    }

    private void runAsync(Runnable runnable) {
        Executors.newSingleThreadExecutor().execute(runnable);
    }

    private Flowable<NewHeadsNotification> subscribeToEvents() {
        subscribeRequest = new Request<>(
                "man_subscribe",
                Arrays.asList("newHeads", Collections.emptyMap()),
                service,
                ManSubscribe.class);
        subscribeRequest.setId(1);

        return service.subscribe(
                subscribeRequest,
                "man_unsubscribe",
                NewHeadsNotification.class
        );
    }

    private void sendErrorReply() throws IOException {
        service.onWebSocketMessage(
                "{"
                        + "  \"jsonrpc\":\"2.0\","
                        + "  \"id\":1,"
                        + "  \"error\":{"
                        + "    \"code\":-1,"
                        + "    \"message\":\"Error message\","
                        + "    \"data\":null"
                        + "  }"
                        + "}");
    }

    private void sendGmanVersionReply() throws IOException {
        service.onWebSocketMessage(
                "{"
                        + "  \"jsonrpc\":\"2.0\","
                        + "  \"id\":1,"
                        + "  \"result\":\"gman-version\""
                        + "}");
    }

    private void verifyStartedSubscriptionHadnshake() {
        verify(webSocketClient).send(
                "{\"jsonrpc\":\"2.0\",\"method\":\"gman_subscribe\","
                        + "\"params\":[\"newHeads\",{}],\"id\":1}");
    }

    private void verifyUnsubscribed() {
        verify(webSocketClient).send(startsWith(
                "{\"jsonrpc\":\"2.0\",\"method\":\"gman_unsubscribe\","
                        + "\"params\":[\"0xcd0c3e8af590364c09d0fa6a1210faf5\"]"));
    }

    private void sendSubscriptionConfirmation() throws Exception {
        waitForRequestSent();

        service.onWebSocketMessage(
                "{"
                        + "\"jsonrpc\":\"2.0\","
                        + "\"id\":1,"
                        + "\"result\":\"0xcd0c3e8af590364c09d0fa6a1210faf5\""
                        + "}");
    }

    private void waitForRequestSent() throws InterruptedException {
        while (!service.isWaitingForReply(REQUEST_ID)) {
            Thread.sleep(50);
        }
    }

    private void sendWebSocketEvent() throws IOException {
        service.onWebSocketMessage(
                "{"
                        + "  \"jsonrpc\":\"2.0\","
                        + "  \"method\":man_subscription\","
                        + "  \"params\":{"
                        + "    \"subscription\":\"0xcd0c3e8af590364c09d0fa6a1210faf5\","
                        + "    \"result\":{"
                        + "      \"difficulty\":\"0xd9263f42a87\","
                        + "      \"uncles\":[]"
                        + "    }"
                        + "  }"
                        + "}");
    }
}
