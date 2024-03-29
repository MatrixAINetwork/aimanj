package org.aimanj.protocol.http;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.response.ManBlockNumber;
import org.aimanj.protocol.core.methods.response.ManSubscribe;
import org.aimanj.protocol.exceptions.ClientConnectionException;
import org.aimanj.protocol.websocket.events.NewHeadsNotification;

import static org.junit.Assert.assertTrue;

public class HttpServiceTest {
    
    private HttpService httpService = new HttpService();
    
    @Test
    public void testAddHeader() {
        String headerName = "customized_header0";
        String headerValue = "customized_value0";
        httpService.addHeader(headerName, headerValue);
        assertTrue(httpService.getHeaders().get(headerName).equals(headerValue));
    }
    
    @Test
    public void testAddHeaders() {
        String headerName1 = "customized_header1";
        String headerValue1 = "customized_value1";
        
        String headerName2 = "customized_header2";
        String headerValue2 = "customized_value2";
        
        HashMap<String, String> headersToAdd = new HashMap<>();
        headersToAdd.put(headerName1, headerValue1);
        headersToAdd.put(headerName2, headerValue2);
        
        httpService.addHeaders(headersToAdd);
        
        assertTrue(httpService.getHeaders().get(headerName1).equals(headerValue1));
        assertTrue(httpService.getHeaders().get(headerName2).equals(headerValue2));
    }

    @Test
    public void httpWebException() throws IOException {
        String content = "400 error";
        Response response = new Response.Builder()
                .code(400)
                .message("")
                .body(ResponseBody.create(null, content))
                .request(new okhttp3.Request.Builder()
                        .url(HttpService.DEFAULT_URL)
                        .build())
                .protocol(Protocol.HTTP_1_1)
                .build();

        OkHttpClient httpClient = Mockito.mock(OkHttpClient.class);
        Mockito.when(httpClient.newCall(Mockito.any()))
                .thenAnswer(invocation -> {
                    Call call = Mockito.mock(Call.class);
                    Mockito.when(call.execute()).thenReturn(response);

                    return call;
                });
        HttpService mockedHttpService = new HttpService(httpClient);

        Request<String, ManBlockNumber> request = new Request<>(
                "man_blockNumber1",
                Collections.emptyList(),
                mockedHttpService,
                ManBlockNumber.class);
        try {
            mockedHttpService.send(request, ManBlockNumber.class);
        } catch (ClientConnectionException e) {
            Assert.assertEquals(
                    e.getMessage(),
                    "Invalid response received: "
                            + response.code() + "; " + content);
            return;
        }

        Assert.fail("No exception");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void subscriptionNotSupported() {
        Request<Object, ManSubscribe> subscribeRequest = new Request<>(
                "man_subscribe",
                Arrays.asList("newHeads", Collections.emptyMap()),
                httpService,
                ManSubscribe.class);

        httpService.subscribe(
                subscribeRequest,
                "man_unsubscribe",
                NewHeadsNotification.class
        );
    }
    
}
