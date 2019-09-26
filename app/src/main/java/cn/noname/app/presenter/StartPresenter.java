package cn.noname.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import cn.noname.app.NnyConst;
import cn.noname.app.common.CommonTools;
import cn.noname.app.common.json.JsonMananger;
import cn.noname.app.server.HttpException;
import cn.noname.app.server.response.CityResponse;
import cn.noname.app.server.response.SystemObjResponse;
import cn.noname.app.view.activity.GuideActivity;
import cn.noname.app.view.activity.MainActivity;
import cn.noname.app.view.activity.StartActivity;
import cn.noname.app.widget.DialogLoading;
import cn.noname.app.widget.ACache;

public class StartPresenter extends BasePresenter {
    private StartActivity activity;
    private Handler hand = new Handler() {
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    };

    public StartPresenter(Context context) {
        super(context);
        activity = (StartActivity) context;
    }

    public void init() {
        if (CommonTools.isFristRun(activity)) {
            Intent intent = new Intent(activity, GuideActivity.class);
            activity.startActivity(intent);
            activity.finish();
            return;
        }
        String systemObjCache = aCache.getAsString("SystemObjCache");
        String citiesCache = aCache.getAsString("CitiesCache");
        Logger.d("systemObjCache %s:", systemObjCache);
        Logger.d("citiesCache %s:", citiesCache);
        if (systemObjCache==null || ("null").equals(systemObjCache)) {
            getSystemObj();
        }
        if (citiesCache==null || ("null").equals(citiesCache)){
            getCities();
        }


        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Message msg = hand.obtainMessage();
                hand.sendMessage(msg);
            }
        }.start();
    }

    public void getSystemObj() {
        DialogLoading.show(context);
        atm.request(NnyConst.GETSYSTEMOBJ, this);
    }

    public void getCities() {
        DialogLoading.show(context);
        atm.request(NnyConst.GETCITIES, this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {

        switch (requestCode) {
            case NnyConst.GETSYSTEMOBJ:
                return userAction.getSystemObj();
            case NnyConst.GETCITIES:
                return userAction.getCities();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
        if (result == null) return;
        switch (requestCode) {
            case NnyConst.GETSYSTEMOBJ:
                SystemObjResponse systemObjResponse = (SystemObjResponse) result;
                if (systemObjResponse.getState() == NnyConst.SUCCESS) {
                    String cache = new Gson().toJson(systemObjResponse.getSysObj());
                    aCache.put("SystemObjCache", cache, 7 * ACache.TIME_DAY);
                }
                break;
            case NnyConst.GETCITIES:
                CityResponse cityResponse = (CityResponse) result;
                if (cityResponse.getState() == NnyConst.SUCCESS) {
                    try {
                        String cache = null;
                        cache = JsonMananger.beanToJson(cityResponse.getCities());
                        aCache.put("CitiesCache", cache, 10 * ACache.TIME_DAY);
                    } catch (HttpException e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
    }
}
