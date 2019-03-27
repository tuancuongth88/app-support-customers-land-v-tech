package haiphat.tech.support.customers.Utills.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import haiphat.tech.support.customers.R;
import haiphat.tech.support.customers.Utills.ToastUtils;

public class DgmAlert {
    public static void error(Context pContext, String message, MaterialDialog.SingleButtonCallback singleButtonCallback) {
        if (message == null) return;
        if (message != null && message.equals(pContext.getString(R.string.text_no_internet_connection))){
            errorWithToast(pContext, pContext.getString(R.string.text_no_internet_connection));
        }else if (message != null && message.equals(pContext.getString(R.string.text_cannot_connect_to_server))){
            errorWithToast(pContext, pContext.getString(R.string.text_cannot_connect_to_server));
        }else {
            errorWithDialog(pContext, message, singleButtonCallback);
        }
    }

    public static void errorWithDialog(Context pContext, String message, MaterialDialog.SingleButtonCallback singleButtonCallback){
        if (message != null) {
            new MaterialDialog.Builder(pContext)
                    .title(R.string.alert_title_error)
                    .positiveText(R.string.text_dialog_ok)
                    .onPositive(singleButtonCallback)
                    .cancelable(false)
                    .content(message).show();
        }
    }

    public static void errorWithToast(Context pContext, String message){
        if (message == null) return;
        ToastUtils.showToast(pContext, message, false);
    }

    public static void success(Context pContext, String message, MaterialDialog.SingleButtonCallback singleButtonCallback) {
        if (message != null) {
            new MaterialDialog.Builder(pContext)
                    .title(R.string.alert_title_success)
                    .positiveText(R.string.text_dialog_ok)
                    .onPositive(singleButtonCallback)
                    .content(message).show();
        }
    }

    public static void confirm(Context pContext, String message, MaterialDialog.SingleButtonCallback singleButtonCallback) {
        if (message != null) {
            new MaterialDialog.Builder(pContext)
                    .title(R.string.alert_title_confirm)
                    .positiveText(R.string.text_dialog_ok)
                    .negativeText(R.string.text_dialog_cancel)
                    .onPositive(singleButtonCallback)
                    .content(message).show();
        }
    }

}