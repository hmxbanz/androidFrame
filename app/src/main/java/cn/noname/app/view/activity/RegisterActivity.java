package cn.noname.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.noname.app.R;
import cn.noname.app.presenter.RegisterPresenter;


public class RegisterActivity extends BaseActivity {
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenter(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResultt(requestCode, resultCode, data);

    }
}
