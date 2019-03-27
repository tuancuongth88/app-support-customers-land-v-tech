package haiphat.tech.support.customers.Services;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpService {
    private static OkHttpService instance;
    private OkHttpClient okHttpClient;

    private OkHttpService() {
        this.buildOkHttpClient();
    }

    public synchronized static OkHttpService getInstance() {
        if (instance == null) {
            instance = new OkHttpService();
        }
        return instance;
    }

    private void buildOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new OAuthInterceptor());
        builder.connectTimeout(1, TimeUnit.MINUTES);
        this.okHttpClient = builder.build();
    }

    public void request(String url, Method method, RequestBody pRequestBody, Headers.Builder headerBuilder, OnHttpServiceListener onHttpServiceListener) {
        Request.Builder builder = new Request.Builder();
        switch (method) {
            case GET:
                break;
            case POST:
                builder.post(pRequestBody);
                break;
            case DELETE:
                builder.delete(pRequestBody);
                break;
            case PUT:
                builder.put(pRequestBody);
                break;
        }
        builder.url(url);
        if (headerBuilder != null) {
            builder.headers(headerBuilder.build());
        }
        this.request(builder.build(), onHttpServiceListener);
    }

    public DgmResponse request(String url, Method method, RequestBody pRequestBody, Headers.Builder headerBuilder) {
        Request.Builder builder = new Request.Builder();
        switch (method) {
            case GET:
                break;
            case POST:
                builder.post(pRequestBody);
                break;
            case DELETE:
                builder.delete(pRequestBody);
                break;
            case PUT:
                builder.put(pRequestBody);
                break;
        }
        builder.url(url);
        builder.headers(headerBuilder.build());
        try {
            Response response = this.request(builder.build());
            return new DgmResponse(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new DgmResponse(ex);
        }
    }

    private void request(final Request request, final OnHttpServiceListener onHttpServiceListener) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                final DgmResponse dgmResponse = new DgmResponse(e);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (onHttpServiceListener != null) {
                            onHttpServiceListener.onResponse(dgmResponse);
                        }
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final DgmResponse dgmResponse = new DgmResponse(response);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (onHttpServiceListener != null) {
                            onHttpServiceListener.onResponse(dgmResponse);
                        }
                    }
                });

            }
        });
    }

    private Response request(final Request request) throws Exception {
        return this.okHttpClient.newCall(request).execute();
    }

    public void release() {
        okHttpClient = null;
    }

    public enum Method {
        POST, GET, PUT, DELETE
    }

    public interface OnHttpServiceListener {
        void onResponse(DgmResponse dgmResponse);
    }

    private class OAuthInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            //Build new request
            Request.Builder builder = request.newBuilder();
//            builder.header("Accept", "application/json"); //if necessary, say to consume JSON
//
            String token = UserService.getInstance().accessToken; //save token of this request for future
            setAuthHeader(builder, token);
            //write current token to request
            request = builder.build(); //overwrite old request
            Response response = chain.proceed(request); //perform request, here original request will be executed
            if (response.code() == 401) { //if unauthorized

                UserService.getInstance().logout();
                return response;
//                synchronized (okHttpClient) { //perform all 401 in sync blocks, to avoid multiply token updates
//                    String currentToken = UserService.getInstance().accessToken; //get currently stored token
//                    if (currentToken != null && currentToken.equals(token)) { //compare current token with token that was stored before, if it was not updated - do update
//                        DgmResponse dgmResponse = Api.authApi.refreshTokenSynchronous();
//                        if (dgmResponse.hasNetwordError()) {
//                            return response;
//                        }
//                        if (!dgmResponse.isSuccess()) {
//                            UserService.getInstance().logout();
//                            return response;
//                        }
//                    }
//
//                    if (UserService.getInstance().accessToken != null) { //retry requires new auth token,
//                        setAuthHeader(builder, UserService.getInstance().accessToken); //set auth token to updated
//                        request = builder.build();
//                        return chain.proceed(request); //repeat request with new token
//                    }
//                }
            }
            return response;
        }

        private void setAuthHeader(Request.Builder builder, String token) {
            if (!TextUtils.isEmpty(token)) {//Add Auth token to each request if authorized
                Log.d("OkHttpService", builder.build().url().toString());
                builder.header("Authorization", String.format("Bearer %s", token));
//                switch (LanguageService.getLanguage()) {
//                    case English:
//                        builder.header("Accept-Language", "en-US");
//                        break;
//                    case Vietnamese:
//                        builder.header("Accept-Language", "vi-VN");
//                        break;
//                    case Unknown:
//                        break;
//                }
            }
        }
    }
}