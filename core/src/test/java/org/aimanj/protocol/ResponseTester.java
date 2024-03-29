package org.aimanj.protocol;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.ResponseBody;
import org.junit.Before;

import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.Response;
import org.aimanj.protocol.http.HttpService;

import static org.junit.Assert.fail;
import static org.aimanj.protocol.http.HttpService.JSON_MEDIA_TYPE;

/**
 * Protocol Response tests.
 */
public abstract class ResponseTester {

    private HttpService aiManjService;
    private OkHttpClient okHttpClient;
    private ResponseInterceptor responseInterceptor;

    @Before
    public void setUp() {
        responseInterceptor = new ResponseInterceptor();
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(responseInterceptor)
                .build();
        configureAiManjService(false);
    }

    protected void buildResponse(String data) {
        responseInterceptor.setJsonResponse(data);
    }

    protected void configureAiManjService(boolean includeRawResponses) {
        aiManjService = new HttpService(okHttpClient, includeRawResponses);
    }

    protected <T extends Response> T deserialiseResponse(Class<T> type) {
        T response = null;
        try {
            response = aiManjService.send(new Request(), type);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return response;
    }

    private class ResponseInterceptor implements Interceptor {

        private String jsonResponse;

        public void setJsonResponse(String jsonResponse) {
            this.jsonResponse = jsonResponse;
        }

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            if (jsonResponse == null) {
                throw new UnsupportedOperationException("Response has not been configured");
            }

            okhttp3.Response response = new okhttp3.Response.Builder()
                    .body(ResponseBody.create(JSON_MEDIA_TYPE, jsonResponse))
                    .request(chain.request())
                    .protocol(Protocol.HTTP_2)
                    .code(200)
                    .message("")
                    .build();

            return response;
        }
    }
}
