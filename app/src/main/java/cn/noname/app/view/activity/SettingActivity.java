package cn.noname.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

import cn.noname.app.NnyConst;
import cn.noname.app.R;
import cn.noname.app.common.NToast;
import cn.noname.app.presenter.SettingPresenter;


public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private SettingPresenter presenter;
    private SwitchButton sbBtnInfo, sbBtnAlbum;
    private TextView txtVersion;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
        presenter =new SettingPresenter(this);
        presenter.init(txtVersion,sbBtnInfo, sbBtnAlbum);
    }
    public void initViews(){
        setTitle("功能设置");
        sbBtnInfo = (SwitchButton) findViewById(R.id.sb_info);
        sbBtnAlbum = (SwitchButton) findViewById(R.id.sb_ablum);
        findViewById(R.id.layout_modify_pass).setOnClickListener(this);
        findViewById(R.id.btn_logoff).setOnClickListener(this);
        txtVersion = (TextView) findViewById(R.id.txt_version);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_modify_pass:
                startActivity(new Intent(this,ModifyPassActivity.class));
                break;
            case R.id.btn_logoff:
                presenter.logOff();
                break;

        }
    }

    public void toTestVersion(View view) {
        if(index==0){NnyConst.SERVERURI="http://test.nannvyou.cn";index++;NToast.shortToast(this,"test");}
        else if(index==1){NnyConst.SERVERURI="http://192.168.0.128";index++;NToast.shortToast(this,"192");}
        else {NnyConst.SERVERURI="http://m.nannvyou.cn";index=0;NToast.shortToast(this,"m");}

        //startActivity(new Intent(this,DbActivity.class));
    }
}
