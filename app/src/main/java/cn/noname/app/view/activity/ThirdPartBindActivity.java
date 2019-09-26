package cn.noname.app.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import cn.noname.app.R;
import cn.noname.app.common.NToast;
import cn.noname.app.presenter.RegisterPresenter;
import cn.noname.app.presenter.ThirdPartBindPresenter;
import cn.smssdk.SMSSDK;

import static cn.smssdk.SMSSDK.getVerificationCode;


public class ThirdPartBindActivity extends BaseActivity{
    private static final int CHOOSE_COUNTRY = 1;
    private ThirdPartBindPresenter presenter;
    private TextView txtProtocol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirdpart_bind);
        presenter = new ThirdPartBindPresenter(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NToast.shortToast(this,data.getStringExtra("countryName"));
    }
}
