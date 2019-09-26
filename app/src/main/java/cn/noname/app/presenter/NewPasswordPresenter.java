package cn.noname.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import cn.noname.app.NnyConst;
import cn.noname.app.R;
import cn.noname.app.common.NToast;
import cn.noname.app.listener.AlertDialogCallBack;
import cn.noname.app.server.HttpException;
import cn.noname.app.server.async.AsyncTaskManager;
import cn.noname.app.server.response.CommonResponse;
import cn.noname.app.view.activity.LoginActivity;
import cn.noname.app.view.activity.NewPasswordActivity;
import cn.noname.app.widget.DialogLoading;
import cn.noname.app.widget.DialogYesOrNo;

public class NewPasswordPresenter extends BasePresenter{
    private NewPasswordActivity mActivity;
    private String userName;
    private String password;
    private String captcha;
    private String passwordAgain;

    public NewPasswordPresenter(Context context) {
        super(context);
        this.mActivity= (NewPasswordActivity) context;
    }
    public void init(){
    };
    public void newPassword(EditText password, EditText passwordAgain){
        Intent intent=mActivity.getIntent();
        this.userName = intent.getStringExtra("userName");
        this.password=password.getText().toString();
        this.passwordAgain=passwordAgain.getText().toString();
        this.captcha = intent.getStringExtra("captcha");

        if(TextUtils.isEmpty(this.password))
        {
            NToast.shortToast(context, R.string.password_be_null);
            return;
        }
        if(!this.password.equals(this.passwordAgain))
        {
            NToast.shortToast(context, R.string.password_not_equal);
            return;
        }
        DialogLoading.show(mActivity);
        atm.request(NnyConst.NEWPASSWORD,this);

    }
    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case NnyConst.NEWPASSWORD:
                return userAction.newPassword(this.userName,this.password,this.captcha);
        }
        return null;
    }
    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
        if (result == null) return;
            switch (requestCode) {
                case NnyConst.NEWPASSWORD:
                    CommonResponse commonResponse = (CommonResponse) result;
                    if (commonResponse.getState() == NnyConst.SUCCESS) {
                        editor.putString(NnyConst.LOGING_PASSWORD, this.password);
                        editor.apply();
                        DialogYesOrNo dialog= DialogYesOrNo.getInstance();
                        dialog.showDialog(context, "修改成功", new AlertDialogCallBack() {
                            @Override
                            public void executeEvent() {
                                context.startActivity(new Intent(context, LoginActivity.class));
                            }

                        });
                        dialog.setConfirmText("前往登录");
                    }
                    break;
            }
    }
    @Override
    public void onFailure(int requestCode, int state, Object result) {
        super.onFailure(requestCode, state, result);
        switch (requestCode) {
            case NnyConst.NEWPASSWORD:
                DialogLoading.dismiss(mActivity);
                NToast.shortToast(mActivity, mActivity.getString(R.string.request_fail));
                break;
        }
    }

}
