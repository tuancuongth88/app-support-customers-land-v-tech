package haiphat.tech.support.customers.Utills;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static Toast toastInstance;
    public static void showToast(Context context, String message, boolean isLengthLong) {
        if (toastInstance != null) toastInstance.cancel();
        toastInstance = Toast.makeText(context, message, isLengthLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toastInstance.show();
    }
}
