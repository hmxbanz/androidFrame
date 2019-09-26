package cn.noname.app.receiver;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

public class NLService extends NotificationListenerService {

    @Override
    public void onCreate() {
        super.onCreate();
        toggleNotificationListenerService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        toggleNotificationListenerService();
        return START_STICKY;
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);


        //当收到一条消息时回调，sbn里面带有这条消息的具体信息
        System.out.println("----------------onNotificationPosted--------->");
        System.out.println("----------------onNotificationPosted----1----->" + sbn.getPackageName());
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString(Notification.EXTRA_TITLE); //通知title
        String content = extras.getString(Notification.EXTRA_TEXT); //通知内容
        int smallIconId = extras.getInt(Notification.EXTRA_SMALL_ICON); //通知小图标id
        Bitmap largeIcon =  extras.getParcelable(Notification.EXTRA_LARGE_ICON); //通知的大图标，注意和获取小图标的区别
        PendingIntent pendingIntent = sbn.getNotification().contentIntent; //获取通知的PendingIntent
        System.out.println("----------------onNotificationPosted----1--1--->" + extras);
        System.out.println("----------------onNotificationPosted----2----->" + title);
        System.out.println("----------------onNotificationPosted----3----->" + content);
        System.out.println("----------------onNotificationPosted----4----->" + smallIconId);
        System.out.println("----------------onNotificationPosted----5----->" + largeIcon);
        System.out.println("----------------onNotificationPosted----6----->" + pendingIntent);

        Toast.makeText(getApplicationContext(), "收到状态栏消息:"+content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    /**
     * 被杀后再次启动时，监听不生效的问题
     */
    private void toggleNotificationListenerService() {

        Log.e("--------------", "toggleNotificationListenerService" + "被杀后再次启动时，监听不生效的问题");
        PackageManager pm = getPackageManager();
        Log.e("------1--------", pm.toString());
        pm.setComponentEnabledSetting(new ComponentName(this, NLService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(new ComponentName(this, NLService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        Log.e("------1--------", pm.toString());
        Log.e("-------222-------", "toggleNotificationListenerService" + "被杀后再次启动时，监听不生效的问题");
    }
}