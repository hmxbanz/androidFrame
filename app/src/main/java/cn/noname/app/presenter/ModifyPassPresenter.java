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
import cn.noname.app.server.response.CommonResponse;
import cn.noname.app.view.activity.LoginActivity;
import cn.noname.app.view.activity.ModifyPassActivity;
import cn.noname.app.widget.DialogLoading;
import cn.noname.app.widget.DialogYesOrNo;

public class ModifyPassPresenter extends BasePresenter {
    private ModifyPassActivity activity;
    private String oldPassword;
    private String newPassword;

    public ModifyPassPresenter(Context context) {
        super(context);
        this.activity = (ModifyPassActivity) context;
    }
    public void init(){
    };

    public void modifyPassword(EditText oldPassword, EditText newPassword, EditText passwordAgain) {
        this.oldPassword=oldPassword.getText().toString();
        this.newPassword=newPassword.getText().toString();
        if(TextUtils.isEmpty(this.oldPassword))
        {
            NToast.shortToast(context, R.string.password_be_null);
            return;
        }
        if(TextUtils.isEmpty(this.newPassword))
        {
            NToast.shortToast(context, R.string.password_be_null);
            return;
        }
        if(!this.newPassword.equals(passwordAgain.getText().toString()))
        {
            NToast.shortToast(context, R.string.password_not_equal);
            return;
        }
        DialogLoading.show(activity);
        atm.request(NnyConst.MODITYPASSWORD,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return userAction.modifyPassword(this.oldPassword,this.newPassword);
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
        if (result == null) return;
        CommonResponse commonResponse = (CommonResponse) result;
        if (commonResponse.getState() == NnyConst.SUCCESS) {
            editor.putString(NnyConst.LOGING_PASSWORD, this.newPassword);
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
    }
}
