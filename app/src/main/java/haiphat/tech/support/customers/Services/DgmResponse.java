package haiphat.tech.support.customers.Services;

import android.text.TextUtils;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import haiphat.tech.support.customers.BaseApplication;
import haiphat.tech.support.customers.Model.ErrorModel;
import haiphat.tech.support.customers.R;
import haiphat.tech.support.customers.Utills.GsonUtils;
import okhttp3.Response;

public class DgmResponse {
    public int statusCode;
    public String responseContent;
    public String message;
    public Exception exception;

    public DgmResponse(Exception exception) {
        this.exception = exception;
    }

    public DgmResponse(Response response) {
        this.statusCode = response.code();
        try {
            this.responseContent = response.body().string().toString();
            this.message = GsonUtils.fromJson(responseContent, ErrorModel.class).messages;
        } catch (Exception ex) {
            this.exception = ex;
        }
    }

    public boolean isSuccess() {
        return statusCode >= 200 && statusCode < 300;
    }

    public String getErrorMessage() {
        if (statusCode == 401) {
            return null;
        }
        if (TextUtils.isEmpty(responseContent)) {
            if (statusCode == 0) {
                if (exception instanceof TimeoutException || exception instanceof SocketTimeoutException) {
                    return BaseApplication.applicationContext.getString(R.string.text_cannot_connect_to_server);
                } else {
                    return BaseApplication.applicationContext.getString(R.string.text_no_internet_connection);
                }
            } else {
                return null;
            }
        }
        return message ;
    }

    public boolean hasNetwordError() {
        return this.exception != null;
    }
}