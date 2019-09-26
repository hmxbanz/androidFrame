package cn.noname.app.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.noname.app.NnyConst;
import cn.noname.app.R;
import cn.noname.app.common.NToast;
import cn.noname.app.server.HttpException;
import cn.noname.app.server.UserAction;
import cn.noname.app.server.async.AsyncTaskManager;
import cn.noname.app.server.async.OnDataListener;
import cn.noname.app.server.response.CommonResponse;
import cn.noname.app.server.response.LoginResponse;
import cn.noname.app.widget.ACache;
import cn.noname.app.widget.DialogLoading;

import static android.content.Context.MODE_PRIVATE;
import static cn.noname.app.server.async.AsyncTaskManager.HTTP_ERROR_CODE;
import static cn.noname.app.server.async.AsyncTaskManager.HTTP_NULL_CODE;
import static cn.noname.app.server.async.AsyncTaskManager.HTTP_TIME_OUT;
import static cn.noname.app.server.async.AsyncTaskManager.REQUEST_ERROR_CODE;

/**
 * Created by hmxbanz on 2017/5/11.
 */

public class BasePresenter implements OnDataListener, View.OnClickListener {
    //protected static BasePresenter instance;
    protected Activity activity;
    protected SharedPreferences sp;
    protected SharedPreferences.Editor editor;
    public boolean isLogin;
    protected Context context;
    public UserAction userAction;
    public AsyncTaskManager atm ;
    protected String userInfoId;
    public ACache aCache;
    protected String userName;
    protected String password;
    protected String JImUserName;
    protected String JImPass;
    protected static String TAG=null;

    public BasePresenter(Context context)
    {
        this.context = context;
        TAG=this.getClass().getSimpleName();
        activity=(AppCompatActivity) context;
        atm = AsyncTaskManager.getInstance(context);
        userAction = UserAction.getInstance(context);
        if(context != null){
        aCache = ACache.get(context);
        sp = this.context.getSharedPreferences("UserConfig", MODE_PRIVATE);
        editor = sp.edit();
        initData();
        }
    }
//    public static BasePresenter getInstance(Context context) {
//        if (instance == null) {
//            synchronized (BasePresenter.class) {
//                if (instance == null) {
//                    instance = new BasePresenter(context);
//                }
//            }
//        }
//        return instance;
//    }
    public void initData()
    {
        isLogin = sp.getBoolean(NnyConst.ISLOGIN, false);
        userInfoId = sp.getString(NnyConst.USERINFOID, "0");
        userAction.token = sp.getString(NnyConst.ACCESS_TOKEN,"");
        JImUserName=sp.getString(NnyConst.JImUserName,"");
        JImPass=sp.getString(NnyConst.JImPass,"");
    }
    public void save(LoginResponse.ResultEntity userInfo){
        editor.putString(NnyConst.ACCESS_TOKEN, userInfo.getRongCloudToken());
        editor.putString(NnyConst.USERID, userInfo.getUserID());
        editor.putString(NnyConst.USERINFOID, userInfo.getUserInfoID());
        editor.putBoolean(NnyConst.ISLOGIN, true);
        editor.putString(NnyConst.JImUserName, "hmx"+userInfo.getUserInfoID());
        editor.putString(NnyConst.JImPass, "123456");
        editor.apply();
    }
    public void remove()
    {
        editor.remove(NnyConst.ISLOGIN);
        editor.remove(NnyConst.USERID);
        editor.remove(NnyConst.USERINFOID);
        editor.remove(NnyConst.ACCESS_TOKEN);
        editor.remove(NnyConst.LOGIN_USERNAME);
        editor.remove(NnyConst.LOGING_PASSWORD);
        editor.remove(NnyConst.JImUserName);
        editor.remove(NnyConst.JImPass);
        editor.apply();
    }


    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        DialogLoading.dismiss(context);
        if (result == null || result instanceof String) return;
        CommonResponse commonResponse = (CommonResponse) result;
        if (commonResponse.getState() != NnyConst.SUCCESS)
            NToast.shortToast(context, commonResponse.getMsg());
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        DialogLoading.dismiss(context);
        String errorMsg=String.valueOf(requestCode)+" ";
        switch (state) {
            case REQUEST_ERROR_CODE:
                errorMsg += context.getString(R.string.request_fail);
                break;
            case HTTP_ERROR_CODE:
                errorMsg += context.getString(R.string.network_error);
                break;
            case HTTP_NULL_CODE:
                errorMsg += context.getString(R.string.network_not_available);
                break;
            case HTTP_TIME_OUT:
                errorMsg += context.getString(R.string.request_time_out);
                break;
            default:
                errorMsg += context.getString(R.string.unknow_error);
        }
        NToast.shortToast(context, errorMsg);
    }


    @Override
    public void onClick(View view) {

    }
}
