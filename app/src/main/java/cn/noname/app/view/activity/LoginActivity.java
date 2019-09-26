package cn.noname.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import cn.noname.app.R;
import cn.noname.app.common.NToast;
import cn.noname.app.presenter.LoginPresenter;


public class LoginActivity extends BaseActivity {
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        presenter = new LoginPresenter(this);

    }

    private void initViews()  {
        setTitle("用户登录");

    }


}
