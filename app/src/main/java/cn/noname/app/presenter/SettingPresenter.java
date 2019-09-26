package cn.noname.app.presenter;

import android.content.Context;
import android.os.Environment;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;

import java.io.File;

import cn.noname.app.NnyConst;
import cn.noname.app.common.NToast;
import cn.noname.app.listener.AlertDialogCallBack;
import cn.noname.app.server.HttpException;
import cn.noname.app.server.broadcast.BroadcastManager;
import cn.noname.app.server.response.CommonResponse;
import cn.noname.app.server.response.UserConfigResponse;
import cn.noname.app.view.activity.SettingActivity;
import cn.noname.app.widget.DialogLoading;
import cn.noname.app.widget.DialogYesOrNo;


import static cn.noname.app.common.CommonTools.getVersionInfo;


public class SettingPresenter extends BasePresenter {
    private SettingActivity activity;
    private SwitchButton sbBtnInfo, sbBtnAlbum;
    private boolean userInfoIsChecked;

    public SettingPresenter(Context context) {
        super(context);
        this.activity =(SettingActivity)context;
    }
    public void init(TextView txtVersion, SwitchButton mSbBtnInfo, SwitchButton mSbBtnAlbum){
        String[] versionInfo = getVersionInfo(context);
        String versionCode=versionInfo[0];
        String versionName=versionInfo[1];
        txtVersion.setText(txtVersion.getText().toString()+versionName+" "+versionCode);
        this.sbBtnInfo = mSbBtnInfo;
        this.sbBtnAlbum = mSbBtnAlbum;
        mSbBtnInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userInfoIsChecked=isChecked;
                atm.request(NnyConst.CLOSEINFO,SettingPresenter.this);
            }
        });

        mSbBtnAlbum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NToast.showToast(context,"此功能仅对VIP会员开放！", Toast.LENGTH_LONG);
            }
        });

        DialogLoading.show(context);
        atm.request(NnyConst.GETCONFIG,this);
    };

    public void logOff() {
        DialogYesOrNo.getInstance().showDialog(context, "是否退出帐号?", new AlertDialogCallBack() {
            @Override
            public void executeEvent() {
                File file = new File(Environment.getExternalStorageDirectory().getPath() + context.getPackageName());
                //deleteFile(file);
                BroadcastManager.getInstance(context).sendBroadcast(NnyConst.EXIT);
                remove();

                //RongIM.getInstance().logout();
                atm.request(NnyConst.LOGOFF,SettingPresenter.this);
            }

        });
    }


    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case NnyConst.GETCONFIG:
                return userAction.getUserConfig(userInfoId);
            case NnyConst.CLOSEINFO:
                return userAction.modifyConfig("UserInfoStatus",String.valueOf(userInfoIsChecked));
            case NnyConst.LOGOFF:
                return userAction.logoff();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
        if (result == null) return;

        switch (requestCode) {
            case NnyConst.GETCONFIG:
                UserConfigResponse userConfigResponse = (UserConfigResponse) result;
                if (userConfigResponse.getState() == NnyConst.SUCCESS) {
                    UserConfigResponse.ResultEntity entity = userConfigResponse.getUserConfig();
                    sbBtnInfo.setChecked(entity.getStatus());
                }
                break;
            case NnyConst.CLOSEINFO:
                CommonResponse commonResponse = (CommonResponse) result;
                if (commonResponse.getState() == NnyConst.SUCCESS)
                {}
                else if(commonResponse.getState() == NnyConst.LOGINTIMEOUT){}
            break;
            case NnyConst.LOGOFF:
                CommonResponse commonResponse2 = (CommonResponse) result;
                if (commonResponse2.getState() == NnyConst.SUCCESS)
                {
                    NToast.shortToast(context, "退出成功");
                    activity.finish();
                }

        }
    }
}
