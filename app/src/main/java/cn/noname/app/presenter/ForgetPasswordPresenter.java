package cn.noname.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.noname.app.NnyConst;
import cn.noname.app.R;
import cn.noname.app.common.NToast;
import cn.noname.app.server.HttpException;
import cn.noname.app.server.response.CaptchaResponse;
import cn.noname.app.view.activity.ForgetPasswordActivity;
import cn.noname.app.view.activity.NewPasswordActivity;
import cn.noname.app.widget.DialogLoading;

public class ForgetPasswordPresenter extends BasePresenter {
    private String userName;
    private String captcha;
    private String captchaFromServer;
    private TextView btnCaptcha;

    private EditText edUserName, edCaptcha;
    public ForgetPasswordPresenter(Context context){
        super(context);
        init();
    }

    public void init() {
        edUserName =  activity.findViewById(R.id.username);
        edCaptcha =  activity.findViewById(R.id.captcha);
        btnCaptcha =activity.findViewById(R.id.btn_captcha);
        btnCaptcha.setOnClickListener(this);
        activity.findViewById(R.id.btn_forget_password)   .setOnClickListener(this);
    }
    public void getCaptcha(TextView btnCaptcha, EditText userName) {
        this.btnCaptcha =btnCaptcha;
        this.userName = userName.getText().toString();
        if(TextUtils.isEmpty(this.userName))
        {
            NToast.shortToast(context, R.string.phone_number_be_null);
            return;
        }
        timer.start();//开始倒计时60秒
        DialogLoading.show(context);
        atm.request(NnyConst.GETCAPTCHA,this);
    }
    public void forgotPassword(EditText userName, EditText captcha) {
        this.userName = userName.getText().toString();
        this.captcha = captcha.getText().toString();

        if(TextUtils.isEmpty(this.userName))
        {
            NToast.shortToast(context, R.string.phone_number_be_null);
            return;
        }
        if (TextUtils.isEmpty(this.captcha)) {
            NToast.shortToast(context, R.string.captcha_cannot_be_null);
            return;
        }
        if(!this.captcha.equals(this.captchaFromServer))
        {
            NToast.shortToast(context, "验证码不正确");
            return;
        }

        Intent intent = new Intent(context, NewPasswordActivity.class);
        intent.putExtra("userName", this.userName);
        intent.putExtra("captcha", this.captcha);
        context.startActivity(intent);
    }
    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case NnyConst.GETCAPTCHA:
                return userAction.getCaptcha(this.userName);
        }
        return null;
    }
    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
        if (result == null) return;
            switch (requestCode) {
                case NnyConst.GETCAPTCHA:
                    CaptchaResponse captchaResponse = (CaptchaResponse) result;
                    if (captchaResponse.getState() == NnyConst.SUCCESS) {
                        this.captchaFromServer=captchaResponse.getCaptcha();
                    }
                    break;
            }
 }
    @Override
    public void onFailure(int requestCode, int state, Object result) {
        super.onFailure(requestCode, state, result);
        switch (requestCode) {
            case NnyConst.FORGETPASSWORD:
                DialogLoading.dismiss(context);
                NToast.shortToast(context, activity.getString(R.string.request_fail));
                break;
        }
    }
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            btnCaptcha.setEnabled(false);
            btnCaptcha.setText((millisUntilFinished / 1000) + "秒后可重发");
        }
        @Override
        public void onFinish() {
            btnCaptcha.setEnabled(true);
            btnCaptcha.setText("获取验证码");
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_captcha:
                getCaptcha(btnCaptcha, edUserName);
                break;
            case R.id.btn_forget_password:
                forgotPassword(edUserName, edCaptcha);
                break;
        }

    }

}
