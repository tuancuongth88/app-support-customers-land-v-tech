package haiphat.tech.support.customers.ViewController;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import haiphat.tech.support.customers.R;
import haiphat.tech.support.customers.databinding.BaseActivityBinding;

public abstract class BaseActivity extends AppCompatActivity {



    protected BaseActivityBinding baseActivityBinding;

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

    protected abstract View getContentView();
}
