package cn.noname.app.presenter;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leeiidesu.permission.PermissionHelper;
import com.leeiidesu.permission.callback.OnPermissionResultListener;

import java.io.File;
import java.util.ArrayList;

import cn.noname.app.NnyConst;
import cn.noname.app.R;
import cn.noname.app.common.NLog;
import cn.noname.app.common.PhotoUtils;
import cn.noname.app.listener.AlertDialogCallBack;
import cn.noname.app.server.HttpException;
import cn.noname.app.server.async.OnDataListener;
import cn.noname.app.server.broadcast.BroadcastManager;
import cn.noname.app.server.response.CommonResponse;
import cn.noname.app.server.response.GetUserMessagesCountResponse;
import cn.noname.app.server.response.GetUserResponse;
import cn.noname.app.view.activity.MainActivity;
import cn.noname.app.view.fragment.MineFragment;
import cn.noname.app.widget.BottomMenuDialog;
import cn.noname.app.widget.DialogAlertWithImg;
import cn.noname.app.widget.DragPointView;
import cn.noname.app.widget.DialogLoading;
import cn.noname.app.widget.SelectableRoundedImageView;
import cn.noname.app.widget.DialogYesOrNo;
import cn.noname.app.widget.dialog.DialogWithImg;


public class MinePresenter extends BasePresenter implements OnPermissionResultListener, OnDataListener {
    private static final String TAG = MinePresenter.class.getSimpleName();
    private final Fragment mineFragment;
    private MainActivity mainActivity;
    private BottomMenuDialog dialog;
    private PhotoUtils photoUtils;
    private Uri selectUri;

    public static final int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private SelectableRoundedImageView imageAvator;
    private File selectedFile;
    private TextView txtCheckName,txt_nick_name;
    private DragPointView unreadNumView;
    private DragPointView redDot;


    public MinePresenter(Context context, Fragment fragment) {
        super(context);
        this.mineFragment = fragment;
        this.mainActivity = (MainActivity) context;
        init();
    }

    public void init() {
        imageAvator = mineFragment.getView().findViewById(R.id.img_avatar);
        txtCheckName = mineFragment.getView().findViewById(R.id.txt_check_name);
        txt_nick_name=  mineFragment.getView().findViewById(R.id.txt_nick_name);
        mineFragment.getView().findViewById(R.id.btn_sing_up).setOnClickListener(this);
        if(txt_nick_name.getText().toString().contains("登录"))  txt_nick_name.setOnClickListener(this);


        imageAvator.setOnClickListener(this);
        reloadAvatar();
//        RongIM.getInstance().addUnReadMessageCountChangedObserver(new IUnReadMessageObserver() {
//            @Override
//            public void onCountChanged(int count) {
//                NLog.e("数量", count);
//                if(count>0)
//                MinePresenter.this.redDot.setVisibility(View.VISIBLE);
//            }
//        }, conversationsTypesSystemCount);
        BroadcastManager.getInstance(context).addAction(MainPresenter.UPDATEUNREAD, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String command = intent.getAction();
                String s = intent.getStringExtra("String");
                if (!TextUtils.isEmpty(command)) {
                    switch (s) {
                        case "newFriend":
                            redDot.setVisibility(View.VISIBLE);

                            break;
                        case "newFriendGone":
                            redDot.setVisibility(View.GONE);
                            break;
                        case "login":
                            reloadAvatar();
                            break;
                        default:

                    }

                }
            }
        });

    }

    public void reloadAvatar() {
        initData();
        if(isLogin) {
            atm.request(NnyConst.GETUSER, this);
        }
    }

    private void setPortraitChangeListener() {
        photoUtils = new PhotoUtils(new PhotoUtils.OnPhotoResultListener() {
            @Override
            public void onPhotoResult(Uri uri, File file) {
                if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                    selectUri = uri;
                    selectedFile = file;
                    NLog.i(TAG, selectedFile.getAbsolutePath());
                    //待3秒上传，确保刚才裁剪的照片保存完毕
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    DialogLoading.show(context);
                    atm.request(NnyConst.UPLOADAVATOR, MinePresenter.this);
                }
            }

            @Override
            public void onPhotoCancel() {

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PhotoUtils.INTENT_CROP:
            case PhotoUtils.INTENT_TAKE:
            case PhotoUtils.INTENT_SELECT:
                photoUtils.onActivityResult(this.mainActivity, requestCode, resultCode, data);
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

        dialog = new BottomMenuDialog(context);

        dialog.setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                photoUtils.takePicture(MinePresenter.this.mainActivity);
            }
        });
        dialog.setMiddleListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                photoUtils.selectPicture(MinePresenter.this.mainActivity);
            }
        });


        //权限申请
        String[] Permissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionHelper.with(mainActivity)
                .permissions(Permissions)
                .showOnRationale("需打开相机、存储权限", "取消", "授权")    //用户拒绝过但没有勾选不再提示会显示对话框
                .showOnDenied("需打开相机，存储权限", "取消", "去设置") //用户勾选不再提示会显示对话框
                .listener(this)
                .request();


        //请求权限
//        if (Build.VERSION.SDK_INT >= 23) {
//            int checkPermission = context.checkSelfPermission(Manifest.permission.CAMERA);
//            int checkReadPermission = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
//
//            if (checkPermission != PackageManager.PERMISSION_GRANTED
//                    ||
//                    checkReadPermission != PackageManager.PERMISSION_GRANTED ) {
//                if (mineFragment.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
//                        ||
//                        mineFragment.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                    mineFragment.requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
//                } else {
//                    DialogYesOrNo.getInstance().showDialog(context, "您需要打开相机权限",new AlertDialogCallBack(){
//                        @Override
//                        public void executeEvent() {
//                            mineFragment.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
//
//                        }
//                    });
//
//                }
//                return;
//            }
//            else {
//                dialog.show();
//            }
//        }
//        else
//        {
//            dialog.show();
//        }
    }


    @Override
    public void onGranted() {
        dialog.show();
        setPortraitChangeListener();
    }

    @Override
    public void onFailed(ArrayList<String> deniedPermissions) {
        Toast.makeText(context, "获取权限失败，请点击后允许获取", Toast.LENGTH_SHORT).show();
    }

    public void startHome() {
        //GetUserActivity.StartActivity(context, basePresenter.userInfoId);
    }

    public void onDestroy() {
        //Glide.with(context).pauseRequests();
        try {
            BroadcastManager.getInstance(context).destroy(MainPresenter.UPDATEUNREAD);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case NnyConst.UPLOADAVATOR:
                return userAction.uploadAvatar(selectedFile);
            case NnyConst.GETUSER:
                return userAction.getUser(userInfoId);
            case NnyConst.GETUSERMESSAGESCOUNT:
                return userAction.getUserMessagesCount("2");
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
        if (result == null) return;
        switch (requestCode) {
            case NnyConst.UPLOADAVATOR:
                if (result != null) {
                    CommonResponse commonResponse = (CommonResponse) result;
                    if (commonResponse.getState() == NnyConst.SUCCESS) {
                        atm.request(NnyConst.GETUSER, this);
                    }
                }
                break;
            case NnyConst.GETUSER:
                GetUserResponse getUserResponse = (GetUserResponse) result;
                if (getUserResponse.getState() == NnyConst.SUCCESS) {
                    GetUserResponse.ResultEntity.UserInfoEntity entity = getUserResponse.getUserInfo().getUserInfo();

                    txtCheckName.setText("审核状态：" + (entity.getCheckName()==null?"待审核":entity.getCheckName()) + " 类型：" + entity.getRoleName());
                    if(getUserResponse.getUserInfo().getUserInfo().getIconSmall() !=null)
                    Glide.with(context).load(NnyConst.SERVERURI + entity.getIconSmall()).into(imageAvator);
                }
                break;
            case NnyConst.GETUSERMESSAGESCOUNT:
                GetUserMessagesCountResponse messagesCountResponse = (GetUserMessagesCountResponse) result;
                if (messagesCountResponse.getState() == NnyConst.SUCCESS) {
                    if (messagesCountResponse.getMessagesCount() > 0) {
                        this.redDot.setVisibility(View.VISIBLE);
                        DialogYesOrNo dialog = DialogYesOrNo.getInstance();
                        dialog.showDialog(context, "您有新的好友请求", new AlertDialogCallBack() {
                            @Override
                            public void executeEvent() {
                            }

                        });
                        dialog.setConfirmText("查看");
                    } else
                        this.redDot.setVisibility(View.GONE);
                }
                break;


        }

    }


    public void getUserMessagesCount() {
        if(isLogin) {
            atm.request(NnyConst.GETUSERMESSAGESCOUNT, this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_avatar:
                showPhotoDialog();
                break;
            case R.id.layout_home:
                startHome();
                break;
            case R.id.txt_nick_name:
                LoginPresenter.startActivity(activity);
                break;
            case R.id.btn_sing_up:
                showSignUpDialog();
        }
    }

    private void showSignUpDialog() {
        DialogWithImg dialogWithImg = new DialogWithImg(context);
        dialogWithImg.setCancelable(true);
        dialogWithImg.show();
        Drawable title_img = context.getDrawable(R.drawable.qd_img);
        StringBuilder strBuilder = new StringBuilder("<font color='" + context.getString(R.string.main_color) + "' face='" + context.getString(R.string.font_type) + "'>");
        strBuilder.append("<big>10</big><small>积分</small>").append("</font>");
        dialogWithImg.setContent(strBuilder.toString());
        dialogWithImg.setTitle("签到成功！");
        dialogWithImg.setTitle_img(title_img);
        dialogWithImg.setBtnSubmit("明天再来");

    }

}
