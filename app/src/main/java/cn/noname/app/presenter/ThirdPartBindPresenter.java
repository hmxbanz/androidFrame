package cn.noname.app.presenter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.noname.app.NnyConst;
import cn.noname.app.R;
import cn.noname.app.common.NToast;
import cn.noname.app.listener.AlertDialogCallBack;
import cn.noname.app.server.HttpException;
import cn.noname.app.server.response.CaptchaResponse;
import cn.noname.app.server.response.RegisterResponse;
import cn.noname.app.view.activity.ContactsActivity;
import cn.noname.app.view.activity.LoginActivity;
import cn.noname.app.view.activity.ProtocolActivity;
import cn.noname.app.view.activity.RegisterActivity;
import cn.noname.app.widget.ClearWriteEditText;
import cn.noname.app.widget.dialog.DialogHelp;
import cn.noname.app.widget.DialogYesOrNo;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static cn.smssdk.SMSSDK.getVerificationCode;

public class ThirdPartBindPresenter extends BasePresenter{
    private RegisterActivity activity;
    private EditText userNameEdit;
    private ClearWriteEditText captchaEdit;
    private String captchaFromServer;
    private DialogHelp dialog;
    private String strCellphone,strCaptcha;
    private TextView txtCaptcha;
    public EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功

                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                ((Throwable)data).printStackTrace();
            }
        }
    };


    public ThirdPartBindPresenter(Context context){
        super(context);
        activity = (RegisterActivity) context;
        SMSSDK.registerEventHandler(eh); //注册短信回调
        init();
    }


    public void init() {
        userNameEdit =  activity.findViewById(R.id.username);
        captchaEdit =  activity.findViewById(R.id.captcha);
        txtCaptcha =  activity.findViewById(R.id.txt_captcha);
    }
    public void register() {
        strCellphone = this.userNameEdit.getText().toString();
        strCaptcha = this.captchaEdit.getText().toString();
        if(TextUtils.isEmpty(strCellphone))
        {
            NToast.shortToast(context, R.string.phone_number_be_null);
            return;
        }
        if (strCellphone.contains(" ")) {
            NToast.shortToast(context, R.string.password_cannot_contain_spaces);
            return;
        }
        if (TextUtils.isEmpty(strCaptcha)) {
            NToast.shortToast(context, R.string.captcha_cannot_be_null);
            return;
        }
        if(!captchaEdit.equals(this.captchaFromServer))
        {
            NToast.shortToast(activity, "验证码不正确");
            return;
        }
//            DialogLoading.show(activity);
//            atm.request(NnyConst.REGISTER,this);

    }
    public void getCaptcha() {
        if(TextUtils.isEmpty( userNameEdit.getText().toString()))
        {
            NToast.shortToast(context, R.string.phone_number_be_null);
            return;
        }
        timer.start();//开始倒计时60秒
        //getVerificationCode("86", this.userNameEdit);
//        DialogLoading.show(activity);
//        atm.request(NnyConst.GETCAPTCHA,this);
    }
    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case NnyConst.REGISTER:
                return userAction.register(strCellphone,strCellphone,strCaptcha);
            case NnyConst.GETCAPTCHA:
                return userAction.getCaptcha(strCellphone);
                   }
        return null;
    }
    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
        if (result == null) return;
        if (result != null) {
            switch (requestCode) {
                case NnyConst.REGISTER:
                    RegisterResponse registerResponse = (RegisterResponse) result;
                    if (registerResponse.getState() == NnyConst.SUCCESS) {
                        RegisterResponse.ResultEntity entity=registerResponse.getResult();
                        DialogYesOrNo dialog= DialogYesOrNo.getInstance();
                        dialog.showDialog(context, "注册成功", new AlertDialogCallBack() {
                            @Override
                            public void executeEvent() {
                                activity.startActivity(new Intent(activity, LoginActivity.class));
                            }

                        });
                        dialog.setConfirmText("前往登录");

                    }
                    break;
                case NnyConst.GETCAPTCHA:
                    CaptchaResponse captchaResponse = (CaptchaResponse) result;
                    if (captchaResponse.getState() == NnyConst.SUCCESS) {
                        this.captchaFromServer=captchaResponse.getCaptcha();
                    }
                    break;

            }
        }
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtCaptcha.setClickable(false);
            txtCaptcha.setText((millisUntilFinished / 1000) + "秒后可重发");
        }
        @Override
        public void onFinish() {
            txtCaptcha.setEnabled(true);
            txtCaptcha.setText("获取验证码");
        }
    };
    @TargetApi(23)
    public void showHelp() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = new DialogHelp(context);
        //dialog.setCancelable(true);
        dialog.show();
        dialog.setTxt_dialog_title(context.getResources().getString(R.string.help));
        dialog.setTxt_dialog_content(context.getResources().getString(R.string.dialog_bind_help));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                //presenter.register(checkBox, edUserName, edPassword, captchaEdit);
                activity.startActivityForResult(new Intent(activity, ContactsActivity.class),NnyConst.CHOOSE_COUNTRY);
                break;
            case R.id.btn_captcha:
                //presenter.getCaptcha(btnCaptcha, edUserName);
                SMSSDK.registerEventHandler(eh); //注册短信回调
                getVerificationCode("86", "13729213015");
                break;
            case R.id.txt_protocol:
                activity.startActivity(new Intent(activity, ProtocolActivity.class));
                break;
            case R.id.txt_need_help:
                showHelp();
                break;
        }

    }
}
