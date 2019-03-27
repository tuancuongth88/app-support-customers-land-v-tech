package haiphat.tech.support.customers.Services.Api;

public interface OnApiResponseListener<T> {
    void onSuccess(T data);
    void onFailed(String errorMessage);
}