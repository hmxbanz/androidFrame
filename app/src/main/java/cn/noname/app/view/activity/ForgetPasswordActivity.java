package cn.noname.app.view.activity;

import android.os.Bundle;

import cn.noname.app.R;
import cn.noname.app.presenter.ForgetPasswordPresenter;


public class ForgetPasswordActivity extends BaseActivity {

    private ForgetPasswordPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        setTitle("找回密码");
        presenter = new ForgetPasswordPresenter(this);
    }

}
