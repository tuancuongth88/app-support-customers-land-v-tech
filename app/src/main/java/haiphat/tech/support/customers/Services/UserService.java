package haiphat.tech.support.customers.Services;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import haiphat.tech.support.customers.BaseApplication;
import haiphat.tech.support.customers.Model.LoginResponse;
import haiphat.tech.support.customers.Utills.ConstantsKey.SharePreferencesKeys;
import haiphat.tech.support.customers.Utills.SharePreferencesLoader;
import haiphat.tech.support.customers.ViewController.BaseActivity;

public class UserService {
    private static UserService instance;
    public String refreshToken, accessToken;
//    public UserInfo userInfo;


    private UserService() {
        this.accessToken = SharePreferencesLoader.getInstance().getValue(SharePreferencesKeys.ACCESS_TOKEN, null);
        this.refreshToken = SharePreferencesLoader.getInstance().getValue(SharePreferencesKeys.REFRESH_TOKEN, null);
//        this.userInfo = getUserInfo();
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void saveAccessToken(LoginResponse response){
        this.refreshToken = response.refresh_token;
        this.accessToken = response.access_token;
        SharePreferencesLoader.getInstance().saveValue(SharePreferencesKeys.ACCESS_TOKEN, this.accessToken);
        SharePreferencesLoader.getInstance().saveValue(SharePreferencesKeys.REFRESH_TOKEN, this.refreshToken);

    }


    public void logout() {
        this.refreshToken = null;
        this.accessToken = null;
//        this.userInfo = null;
        SharePreferencesLoader.getInstance().clear();
        // force logout and open login page
        Intent pIntent = new Intent();
        pIntent.setAction(BaseActivity.FORCE_LOGOUT_ACTION);
        LocalBroadcastManager.getInstance(BaseApplication.applicationContext).sendBroadcast(pIntent);
    }




    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(this.accessToken);
    }

}
