package cn.noname.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import cn.noname.app.R;
import cn.noname.app.presenter.NewPasswordPresenter;


public class NewPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText edPass, edPassAgain;
    private NewPasswordPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        initViews();
        presenter = new NewPasswordPresenter(this);
        presenter.init();
    }
    public void initViews(){
        setTitle("更改密码");
        edPass =  findViewById(R.id.password);
        edPassAgain =  findViewById(R.id.password_again);
        findViewById(R.id.btn_new_pass).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_new_pass:
                presenter.newPassword(edPass, edPassAgain);
                break;
        }
    }
}
