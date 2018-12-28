package haiphat.tech.support.customers.ViewController;

import android.os.Bundle;
import android.view.View;

import haiphat.tech.support.customers.R;


public class LoginActivity extends BaseActivity{

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setVisibleHeader(View.VISIBLE);
    }

    @Override
    protected View getContentView() {
        view = View.inflate(this, R.layout.activity_login, null);
        return view;
    }
}
