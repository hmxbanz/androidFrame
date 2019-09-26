package cn.noname.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import cn.noname.app.R;
import cn.noname.app.presenter.ModifyPassPresenter;


public class ModifyPassActivity extends BaseActivity implements View.OnClickListener {
    private EditText edOldPass, edPass, edPassAgain;
    private ModifyPassPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pass);
        initViews();
        presenter = new ModifyPassPresenter(this);
        presenter.init();
    }
    public void initViews(){
        setTitle("修改密码");
        edOldPass = findViewById(R.id.old_pass);
        edPass =  findViewById(R.id.password);
        edPassAgain =  findViewById(R.id.password_again);
        findViewById(R.id.btn_modify_pass).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_modify_pass:
                presenter.modifyPassword(edOldPass, edPass, edPassAgain);
                break;
        }
    }
}
