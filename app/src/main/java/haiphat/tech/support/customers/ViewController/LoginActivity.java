package haiphat.tech.support.customers.ViewController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import haiphat.tech.support.customers.R;
import haiphat.tech.support.customers.Services.Api.OnApiResponseListener;
import haiphat.tech.support.customers.Utills.dialog.DgmAlert;
import haiphat.tech.support.customers.Utills.dialog.DgmLoading;
import haiphat.tech.support.customers.ViewModel.LoginViewModel;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private View view;
    private Button btnLogin;
    private EditText edName;
    private EditText edPassword;
    private LoginViewModel vm = new LoginViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setVisibleHeader(View.GONE);
    }

    @Override
    protected View getContentView() {
        view = View.inflate(this, R.layout.activity_login, null);
        btnLogin = view.findViewById(R.id.btnLogin);
        edName = view.findViewById(R.id.txtUsername);
        edPassword = view.findViewById(R.id.txtPassword);
        btnLogin.setOnClickListener(this);
        return view;
    }

    private void LoginEmail(){
        vm.username = edName.getText().toString();
        vm.password = edPassword.getText().toString();
//        DgmLoading.getInstance().show(this);
        vm.login(new OnApiResponseListener() {
            @Override
            public void onSuccess(Object data) {
//                DgmLoading.getInstance().dismiss();
                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailed(String errorMessage) {
//                DgmLoading.getInstance().dismiss();
                DgmAlert.error(LoginActivity.this, errorMessage, null);

            }
        });

    }

    @Override
    public void onClick(View view) {
        LoginEmail();
    }
}
