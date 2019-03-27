package haiphat.tech.support.customers.Utills.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import haiphat.tech.support.customers.R;

public class DgmLoading {
    private MaterialDialog materialDialog;
    private static DgmLoading instance;
    private DgmLoading(){

    }
    public static synchronized DgmLoading getInstance(){
        if (instance == null){
            instance = new DgmLoading();
        }
        return instance;
    }
    public  void show(Context pContext){
        this.dismiss();
        materialDialog =  new MaterialDialog.Builder(pContext)
                .content(R.string.text_processing)
                .progress(true, 0)
                .show();
    }

    public void dismiss(){
        if (materialDialog != null && materialDialog.isShowing()){
            materialDialog.dismiss();
        }
    }
}