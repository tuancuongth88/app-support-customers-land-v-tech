package haiphat.tech.support.customers.ViewModel;

import haiphat.tech.support.customers.Model.LoginResponse;
import haiphat.tech.support.customers.Services.Api.Api;
import haiphat.tech.support.customers.Services.Api.OnApiResponseListener;
import haiphat.tech.support.customers.Services.DgmResponse;
import haiphat.tech.support.customers.Services.OkHttpService;
import haiphat.tech.support.customers.Services.UserService;
import haiphat.tech.support.customers.Utills.GsonUtils;

public class LoginViewModel {

    public String username;
    public String password;


    public void login(final OnApiResponseListener onApiResponseListener) {
        Api.authApi.login(username, password, new OkHttpService.OnHttpServiceListener() {
            @Override
            public void onResponse(DgmResponse dgmResponse) {
                LoginResponse response = GsonUtils.fromJson(dgmResponse.responseContent, LoginResponse.class);
                if (dgmResponse.isSuccess()) {
                    if (response != null){
//                        UserService.getInstance().saveAccessToken(response);
                        onApiResponseListener.onSuccess(null);
                    }
                }else {
                    onApiResponseListener.onFailed(dgmResponse.getErrorMessage());
                }
            }
        });
    }
}
