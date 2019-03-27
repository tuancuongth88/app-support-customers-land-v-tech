package haiphat.tech.support.customers.Services.Api.Auth;

import java.util.HashMap;
import java.util.Map;

import haiphat.tech.support.customers.Services.Api.BaseApi;
import haiphat.tech.support.customers.Services.OkHttpService;
import haiphat.tech.support.customers.Services.UrlConfig;

public class AuthApi extends BaseApi {

    @Override
    protected String getEndPoint() {
        return UrlConfig.API_URL;
    }

    @Override
    protected String getApiUrl() {
        return "oauth/";
    }

    public void login(String userName, String password, OkHttpService.OnHttpServiceListener onResponseListener){
        Map<String,String> data = new HashMap<>();
        data.put("username",userName);
        data.put("password",password);
        data.put("grant_type","password");
        data.put("client_id", UrlConfig.CLIENT_ID);
        data.put("client_secret", UrlConfig.CLIENT_SECRET);
        this.post("token", data, onResponseListener);
    }

}
