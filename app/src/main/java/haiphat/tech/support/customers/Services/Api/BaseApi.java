package haiphat.tech.support.customers.Services.Api;

import android.text.TextUtils;
import android.util.Log;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import haiphat.tech.support.customers.Services.DgmResponse;
import haiphat.tech.support.customers.Services.OkHttpService;
import haiphat.tech.support.customers.Utills.GsonUtils;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public abstract class BaseApi {

    protected abstract String getEndPoint();
    protected abstract String getApiUrl();

    protected void get(String queryString, OkHttpService.OnHttpServiceListener onHttpServiceListener) {
        this.request(this.getEndPoint() + "/" + this.getApiUrl() + queryString,
                OkHttpService.Method.GET, null, null, onHttpServiceListener);
    }

    protected void put(String method, Map<String, String> body, OkHttpService.OnHttpServiceListener onHttpServiceListener) {
        Headers.Builder headerBuilder = new Headers.Builder();
        headerBuilder.add("Content-Type", "application/json");
        this.request(this.getEndPoint() + "/" + this.getApiUrl() +  method,
                OkHttpService.Method.PUT, makeBody(body), headerBuilder, onHttpServiceListener);
    }

    protected void putString(String method, String body, OkHttpService.OnHttpServiceListener onHttpServiceListener) {
        Headers.Builder headerBuilder = new Headers.Builder();
        headerBuilder.add("Content-Type", "application/json");
        this.request(this.getEndPoint() + "/" + this.getApiUrl() +  method,
                OkHttpService.Method.PUT, RequestBody.create(MediaType.parse("application/json"), body), headerBuilder, onHttpServiceListener);
    }

    protected void delete(String method, Map<String, String> body, OkHttpService.OnHttpServiceListener onHttpServiceListener) {
        Headers.Builder headerBuilder = new Headers.Builder();
        headerBuilder.add("Content-Type", "application/json");
        this.request(this.getEndPoint() + "/" + this.getApiUrl() +  method,
                OkHttpService.Method.DELETE, makeBody(body), headerBuilder, onHttpServiceListener);
    }

    protected void post(String method, Map<String, String> body, OkHttpService.OnHttpServiceListener onHttpServiceListener) {
        Headers.Builder headerBuilder = new Headers.Builder();
        headerBuilder.add("Content-Type", "application/json");
        this.request(this.getEndPoint() + "/" + this.getApiUrl() +  method,
                OkHttpService.Method.POST, makeBody(body), headerBuilder, onHttpServiceListener);
    }

    protected void postString(String method, String body, OkHttpService.OnHttpServiceListener onHttpServiceListener) {
        Headers.Builder headerBuilder = new Headers.Builder();
        headerBuilder.add("Content-Type", "application/json");
        this.request(this.getEndPoint() + "/" + this.getApiUrl() + method,
                OkHttpService.Method.POST, RequestBody.create(MediaType.parse("application/json"), body), headerBuilder, onHttpServiceListener);
    }

    protected void postUrlEncoded(String method, Map<String, String> body, OkHttpService.OnHttpServiceListener onHttpServiceListener) {
        Headers.Builder headerBuilder = new Headers.Builder();
        headerBuilder.add("Content-Type", "application/x-www-form-urlencoded");
        this.request(this.getEndPoint() + "/" + this.getApiUrl() + method,
                OkHttpService.Method.POST, makeUrlEncodeBody(body), headerBuilder, onHttpServiceListener);
    }

    protected DgmResponse postUrlEncoded(String method, Map<String, String> body){
        Headers.Builder headerBuilder = new Headers.Builder();
        headerBuilder.add("Content-Type", "application/x-www-form-urlencoded");
        return this.request(this.getEndPoint() + "/" + this.getApiUrl() + method,
                OkHttpService.Method.POST, makeUrlEncodeBody(body), headerBuilder);
    }

    protected void upload(String method, MultipartBody.Builder builder, OkHttpService.OnHttpServiceListener onHttpServiceListener) {
        this.request(this.getEndPoint() + "/" + this.getApiUrl() + method,
                OkHttpService.Method.POST, builder.build(), null, onHttpServiceListener);
    }

    public void request(String url, OkHttpService.Method method, RequestBody pRequestBody,
                        Headers.Builder headerBuilder, OkHttpService.OnHttpServiceListener onHttpServiceListener) {
        Log.d("dmg: ", "url: " + url);
        OkHttpService.getInstance().request(url, method, pRequestBody, headerBuilder, onHttpServiceListener);
    }

    public DgmResponse request(String url, OkHttpService.Method method, RequestBody pRequestBody, Headers.Builder headerBuilder) {
        Log.d("dmg: ", "url: " + url);
        return OkHttpService.getInstance().request(url, method, pRequestBody, headerBuilder);
    }

    private RequestBody makeBody(Map<String, String> body) {
        return RequestBody.create(MediaType.parse("application/json"), GsonUtils.toJson(body));
    }

    private RequestBody makeUrlEncodeBody(Map<String, String> body) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : body.entrySet()) {
            formBuilder.add(entry.getKey(), entry.getValue());
        }
        return formBuilder.build();
    }

    protected String parseUrlWithParams(String url, Map<String,String> params){
        List<String> fragments = new ArrayList<>();
        if (params != null && !params.isEmpty()){
            String[] urlParams = url.split("/");
            for (String item : urlParams){
                if (item.startsWith("{") && item.endsWith("}")){
                    String paramName = item.replace("{","").replace("}","");
                    if (params.containsKey(paramName)){
                        String value = params.get(paramName);
                        value = value == null ? "" : value;
                        String appendValue = value;
                        try {
                            appendValue  = URLEncoder.encode(value,"utf-8");
                        } catch (Exception ex){
                        }
                        fragments.add(appendValue);
                    }
                } else {
                    fragments.add(item);
                }
            }
        }
        return TextUtils.join("/",fragments);
    }

    protected String parseUrlQueryStringWithParams(String url, Map<String,String> params){
        String ret = url;
        if (params != null && !params.isEmpty()){
            String[] urlParams = url.split("[&?/]");
            for (String item : urlParams) {
                if (item.startsWith("{") && item.endsWith("}")){
                    String paramName = item.replace("{","").replace("}","");
                    String value = params.get(paramName);
                    if (value !=  null){
                        String appendValue = value;
                        try {
                            appendValue = URLEncoder.encode(value,"utf-8");
                        } catch (Exception ex){

                        }
                        ret = ret.replace(item,paramName + "=" + appendValue);
                    } else  {
                        ret = ret.replace(item,"");
                    }
                }
            }
        }
        return ret;
    }
}