package cn.noname.app.presenter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.orhanobut.logger.Logger;

import java.util.HashMap;

import cn.noname.app.NnyConst;
import cn.noname.app.R;
import cn.noname.app.common.NToast;
import cn.noname.app.listener.AlertDialogCallBack;
import cn.noname.app.server.HttpException;
import cn.noname.app.server.response.LoginResponse;
import cn.noname.app.view.activity.ForgetPasswordActivity;
import cn.noname.app.view.activity.LoginActivity;
import cn.noname.app.view.activity.MainActivity;
import cn.noname.app.widget.dialog.DialogAlert;
import cn.noname.app.widget.DialogLoading;
import cn.noname.app.widget.DialogTip;
import cn.noname.app.widget.DialogYesOrNo;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;


public class LoginPresenter extends BasePresenter implements View.OnClickListener, DialogTip.DialogPopListener {
    private static final String TAG=LoginPresenter.class.getSimpleName();
    private LoginActivity activity;
    private String userName;
    private String password;
    private DialogAlert dialog;
    private DialogTip dialogTip;
    private View txt_register_tip;
    private String openId,thirdPartName;
    private EditText usernameEdit, passwordEdit;

    public LoginPresenter(Context context){
        super(context);
        activity = (LoginActivity) context;
        init();
    }

    public void init() {
        txt_register_tip = activity.findViewById(R.id.txt_register_tip);
        txt_register_tip.setOnClickListener(this);
        activity.findViewById(R.id.txt_forget_password).setOnClickListener(this);
        activity.findViewById(R.id.layout_wx).setOnClickListener(this);
        activity.findViewById(R.id.layout_wx).setOnClickListener(this);
        usernameEdit = activity.findViewById(R.id.username);
        passwordEdit = activity.findViewById(R.id.password);
        activity.findViewById(R.id.btn_login).setOnClickListener(this);
    }

    public void login(EditText userName, EditText password) {
        this.userName =userName.getText().toString();
        this.password =password.getText().toString();
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
        DialogLoading.show(context);
        atm.request(NnyConst.LOGIN,this);
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case NnyConst.LOGIN:
                return userAction.login("86", userName, password);
            case NnyConst.GET_TOKEN:
                //return action.getToken();
            case NnyConst.SYNC_USER_INFO:
                //return action.getUserInfoById(connectResultId);
        }
        return null;
    }
    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
        if (result == null) return;
            switch (requestCode) {
                case NnyConst.LOGIN:
                    LoginResponse loginResponse = (LoginResponse) result;
                    if (loginResponse.getState() == NnyConst.SUCCESS) {
                        LoginResponse.ResultEntity entity=loginResponse.getUserInfo();
                        save(entity);
                        initData();
                        //BroadcastManager.getInstance(context).sendBroadcast(UPDATEUNREAD,"login");
                        if (!TextUtils.isEmpty(entity.getRongCloudToken())) {

                        }

                        MainActivity.StartActivity(context,"toMine");
                    } else if (loginResponse.getState() == NnyConst.FAILURE) {
                        DialogYesOrNo.getInstance().showDialog(context, "用户名或密码出错",new AlertDialogCallBack());
                    }
                    NToast.shortToast(context, loginResponse.getMsg());
                    break;

            }

    }

    /**
     * 弹出底部框
     */
    @TargetApi(23)
    public void showPhotoDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new DialogAlert(context);
        dialog.setCancelable(true);
        dialog.show();
    }
    /**
     * 弹出底部框
     */
    @TargetApi(23)
    public void showTipDialog() {
        if (dialogTip != null && dialogTip.isShowing()) {
            dialogTip.dismiss();
        }
        dialogTip = new DialogTip(context);
        dialogTip.setCancelable(true);
        dialogTip.show();
    }

    @Override
    public void onSubmit(int i, int quantity, int productAttributeId) {

    }
    public void wxLogin() {
        DialogLoading.show(context);
        final Platform weixin = ShareSDK.getPlatform(Wechat.NAME);

        //设置监听回调
        weixin.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, final HashMap<String, Object> hashMap) {
                DialogLoading.dismiss(context);
                Log.d(TAG, " _Weixin: -->> onComplete: Platform:" + platform.toString());
                Log.d(TAG, " _Weixin: -->> onComplete: hashMap:" + hashMap);
                //weixin.removeAccount(true); //移除授权状态和本地缓存，下次授权会重新授权
                //当前线程不能执行UI操作，需要放到主线程中去
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showUser_WeiXin(hashMap);
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                DialogLoading.dismiss(context);
                if(i==8){
//                    activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            NToast.shortToast(context,"手机没有安装微信");
//                        }
//                    });

                    NToast.shortToast(context,"手机没有安装微信");
                }

                Log.d(TAG, " _Weixin: -->> onError:  " + throwable.toString());
                throwable.printStackTrace();
                weixin.removeAccount(true);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                NToast.shortToast(context,"取消了");
            }
        });

        //授权并获取用户信息
        weixin.showUser(null);

    }
    private void showUser_WeiXin(HashMap<String, Object> hashMap) {
        openId =(String)hashMap.get("openid");
        String nickname = (String) hashMap.get("nickname");
        String headimgurl = (String) hashMap.get("headimgurl");
        Logger.d(hashMap.toString());
        Logger.d("nickname:"+nickname);
        Logger.d("headimgurl:"+headimgurl);
        thirdPartName="WeiXin";
        //atm.request(NnyConst.THIRDPART_CHECK,this);
        //判断在系统是否有用户，有则使用用户登录。
        //没有则让用户注册,注册成功后自动登录
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_register_tip:
                RegisterPresenter.startActivity(activity);
                break;
            case R.id.layout_wx:
                wxLogin();
                break;
            case R.id.txt_forget_password:
                context.startActivity(new Intent(context, ForgetPasswordActivity.class));
                break;
            case R.id.btn_login:
                //login(usernameEdit, passwordEdit);
                //showPhotoDialog();
                //showTipDialog();
                //DialogLoading.show(this,false);
                //DialogLoading.show(this);
        }
    }

    public static void startActivity(Context context) {
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
    }
}
