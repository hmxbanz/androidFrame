package cn.noname.app.view.activity;

import android.os.Bundle;
import android.view.View;

import cn.noname.app.R;
import cn.noname.app.presenter.ProtocolPresenter;


public class ProtocolActivity extends BaseActivity implements View.OnClickListener {
private ProtocolPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        initView();
        presenter = new ProtocolPresenter(this);
        presenter.init();
    }

    private void initView() {
        setTitle("用户协议");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
