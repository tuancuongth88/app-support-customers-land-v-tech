package haiphat.tech.support.customers.ViewController;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import haiphat.tech.support.customers.R;
import haiphat.tech.support.customers.databinding.BaseActivityBinding;

public abstract class BaseActivity extends AppCompatActivity {



    protected BaseActivityBinding baseActivityBinding;

    public static final String FORCE_LOGOUT_ACTION = "FORCE_LOGOUT_ACTION";

    private BroadcastReceiver logoutBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!BaseActivity.this.isFinishing()){
                Intent pIntent = new Intent(BaseActivity.this, LoginActivity.class);
                ComponentName cn = pIntent.getComponent();
                Intent mainIntent = intent.makeRestartActivityTask(cn);
                startActivity(mainIntent);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpContentView();

    }
    public void backPressed(View view){
        this.onBackPressed();
    }

    public void onClickUser(View view){
        startActivity(new Intent(this, ProfileActivity.class));
    }

    public void setVisibleHeader(int visible){baseActivityBinding.header.setVisibility(visible); }

    public void setHeaderTitle(int title){ baseActivityBinding.tvTitle.setText(title);}


    protected void setUpContentView(){
        baseActivityBinding = DataBindingUtil.setContentView(this, R.layout.base_activity);
        baseActivityBinding.contentContainer.addView(getContentView(), LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
    }
    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(logoutBroadcastReceiver, new IntentFilter(FORCE_LOGOUT_ACTION));
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(logoutBroadcastReceiver);
        super.onStop();
    }
    protected abstract View getContentView();
}
