package cn.noname.app.presenter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
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
import cn.noname.app.view.activity.RegisterActivity;
import cn.noname.app.widget.dialog.DialogHelp;
import cn.noname.app.widget.dialog.DialogWeb;
import cn.noname.app.widget.DialogLoading;
import cn.noname.app.widget.DialogYesOrNo;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static cn.smssdk.SMSSDK.getVerificationCode;
import static cn.smssdk.SMSSDK.submitVerificationCode;

public class RegisterPresenter extends BasePresenter implements DialogWeb.DialogWebListner {
    private RegisterActivity activity;
    private String userName;
    private String password;
    private String captcha;
    private TextView btnCaptcha;
    private String captchaFromServer;
    private DialogHelp dialog;

    private EditText edUserName, edPassword, edCaptcha;
    private CheckBox checkBox;
    private TextView txt_country,txt_zone;







    public EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    captchaCheck=true;
                    register(checkBox, edUserName, edPassword, edCaptcha);
                    //提交验证码成功
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功

                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }
            else if(result==0)
            {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                            NToast.shortToast(activity, "验证码不正确");
                    }
                });
                }

            }
            else{
                ((Throwable)data).printStackTrace();
            }
        }
    };
    private DialogWeb dialogProtocol;
    private String code="86";
    private boolean captchaCheck;

    public RegisterPresenter(Context context){
        super(context);
        activity = (RegisterActivity) context;
        SMSSDK.registerEventHandler(eh); //注册短信回调
        init();
    }

    public static void startActivity(Context context) {
        Intent i = new Intent(context, RegisterActivity.class);
        context.startActivity(i);
    }

    public void init() {
        checkBox = (CheckBox) activity.findViewById(R.id.checkbox);
        Drawable drawable = activity.getResources().getDrawable(R.drawable.selector_checkbox);
        drawable.setBounds(0,0,60,60);
        if(Build.VERSION.SDK_INT>=21)
            drawable.setTint(activity.getResources().getColor(R.color.mainColorBlue));
        checkBox.setCompoundDrawables(drawable,null,null,null);

        edUserName =  activity.findViewById(R.id.username);
        edPassword =  activity.findViewById(R.id.password);
        edCaptcha =  activity.findViewById(R.id.captcha);

        activity.findViewById(R.id.btn_register).setOnClickListener(this);
        activity.findViewById(R.id.txt_protocol).setOnClickListener(this);
        activity.findViewById(R.id.txt_need_help).setOnClickListener(this);

        btnCaptcha =activity.findViewById(R.id.btn_captcha);
        btnCaptcha.setOnClickListener(this);
        txt_country=activity.findViewById(R.id.txt_country);
        txt_country.setOnClickListener(this);
        txt_zone=activity.findViewById(R.id.txt_zone);
        txt_zone.setOnClickListener(this);
    }
    public void register(CheckBox checkBox, EditText cellphone, EditText password, EditText captcha) {
        Boolean check=checkBox.isChecked();
        this.userName = cellphone.getText().toString();
        this.password = password.getText().toString();
        this.captcha = captcha.getText().toString();

        if(TextUtils.isEmpty(this.userName))
        {
            NToast.shortToast(context, R.string.phone_number_be_null);
            return;
        }
        if (TextUtils.isEmpty(this.password)) {
            NToast.shortToast(context, R.string.password_be_null);
            return;
        }
        if (this.password.contains(" ")) {
            NToast.shortToast(context, R.string.password_cannot_contain_spaces);
            return;
        }
        if (this.password.length()>11) {
            NToast.shortToast(context, R.string.password_length_over_float);
            return;
        }
        if (TextUtils.isEmpty(this.captcha)) {
            NToast.shortToast(context, R.string.captcha_cannot_be_null);
            return;
        }
        if(this.captchaCheck==false){
            submitVerificationCode(code, userName, this.captcha);
            return;
        }

        if(!check)
        {
            showProtocol();
            return;
        }

            DialogLoading.show(activity);
            //atm.request(NnyConst.REGISTER,this);

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
        getVerificationCode(code, this.userName);
//        DialogLoading.show(activity);
//        atm.request(NnyConst.GETCAPTCHA,this);
    }
    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case NnyConst.REGISTER:
                return userAction.register(this.userName,this.password,this.captcha);
            case NnyConst.GETCAPTCHA:
                return userAction.getCaptcha(this.userName);
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
            btnCaptcha.setEnabled(false);
            btnCaptcha.setText((millisUntilFinished / 1000) + ""+context.getText(R.string.captcha_change_text));
        }
        @Override
        public void onFinish() {
            btnCaptcha.setEnabled(true);
            btnCaptcha.setText(context.getText(R.string.captcha_text));
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
        dialog.setTxt_dialog_content(context.getResources().getString(R.string.dialog_reg_help));

    }
    @TargetApi(23)
    public void showProtocol() {
        if (dialogProtocol != null && dialogProtocol.isShowing()) {
            dialogProtocol.dismiss();
        }
        dialogProtocol = new DialogWeb(context);
        dialogProtocol.setListner(this);
        //dialog.setCancelable(true);
        dialogProtocol.show();
        dialogProtocol.setTxt_dialog_title(context.getResources().getString(R.string.txt_protocle));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register(checkBox, edUserName, edPassword, edCaptcha);
                break;
            case R.id.btn_captcha:
                getCaptcha(btnCaptcha, edUserName);
                break;
            case R.id.txt_protocol:
                showProtocol();
                //activity.startActivity(new Intent(activity,ProtocolActivity.class));
                break;
            case R.id.txt_country:
                activity.startActivityForResult(new Intent(activity,ContactsActivity.class),NnyConst.CHOOSE_COUNTRY);
                break;
            case R.id.txt_need_help:
                showHelp();
                break;

        }

    }

    public void onActivityResultt(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            txt_country.setText(data.getStringExtra("countryName"));
            code=data.getStringExtra("countryCode");
            txt_zone.setText("+"+code);
        }
    }

    @Override
    public void onSubmit() {
        checkBox.setChecked(true);
    }

    @Override
    public void onCancle() {

    }
}
