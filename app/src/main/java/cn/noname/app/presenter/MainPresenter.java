package cn.noname.app.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leeiidesu.permission.PermissionHelper;
import com.leeiidesu.permission.callback.OnPermissionResultListener;

import java.util.ArrayList;
import java.util.List;


import cn.noname.app.MainApplication;
import cn.noname.app.NnyConst;
import cn.noname.app.R;
import cn.noname.app.listener.AlertDialogCallBack;
import cn.noname.app.receiver.NLService;
import cn.noname.app.server.HttpException;
import cn.noname.app.server.broadcast.BroadcastManager;
import cn.noname.app.server.response.VersionResponse;
import cn.noname.app.view.activity.LoginActivity;
import cn.noname.app.view.activity.MainActivity;
import cn.noname.app.view.fragment.BusinessFragment;
import cn.noname.app.view.fragment.DiscoveryFragment;
import cn.noname.app.view.fragment.SportFragment;
import cn.noname.app.view.fragment.MineFragment;
import cn.noname.app.widget.DragPointView;
import cn.noname.app.widget.DialogLoading;
import cn.noname.app.widget.DialogYesOrNo;
import cn.noname.app.widget.downloadService.DownloadService;

import static cn.noname.app.common.CommonTools.getVersionInfo;

public class MainPresenter extends BasePresenter implements OnPermissionResultListener, DragPointView.OnDragListencer {
    public static final String UPDATEUNREAD = "updateUnread";
    private MainActivity activity;
    private final int CURRENTVIEWPAGEINDEX =0;
    private final int MAXCACHEVIEWPAGES =3;
    private int msgCount;
    private String apkUrl;
    private ViewPager viewPager;
    private ImageView imageBusiness,imageSport,imageDiscovery,imageMe;
    private TextView txtBusiness,txtSport,txtDiscovery,txtMe;

    private List<Fragment> fragments;
    public MainPresenter(Context context) {
        super(context);
        activity = (MainActivity) context;
    }

    public void init() {
        activity.setHeadVisibility(View.GONE);
        RelativeLayout searchLayout, sportLayout, discoveryLayout,meLayout;
        searchLayout =  activity.findViewById(R.id.tab_layout_business);
        sportLayout =  activity.findViewById(R.id.tab_layout_sport);
        discoveryLayout =  activity.findViewById(R.id.tab_layout_discovery);
        meLayout =  activity.findViewById(R.id.tab_layout_me);
        imageBusiness =  activity.findViewById(R.id.tab_img_business);
        imageSport = activity.findViewById(R.id.tab_img_sport);
        imageDiscovery = activity.findViewById(R.id.tab_img_discovery);
        imageMe =  activity.findViewById(R.id.tab_img_me);
        txtBusiness =  activity.findViewById(R.id.tab_text_business);
        txtSport =  activity.findViewById(R.id.tab_text_sport);
        txtDiscovery =  activity.findViewById(R.id.tab_text_discovery);
        txtMe =  activity.findViewById(R.id.tab_text_me);
        searchLayout.setOnClickListener(this);
        sportLayout.setOnClickListener(this);
        discoveryLayout.setOnClickListener(this);
        meLayout.setOnClickListener(this);

        this.viewPager = activity.findViewById(R.id.main_viewpager);
        initMianViewPager();
        changeTextViewColor();
        changeSelectedTabState(CURRENTVIEWPAGEINDEX);

//        DialogLoading.show(activity);
//        atm.request(NnyConst.CHECKVERSION, this);
        requestPermissions();

        if (!isNotificationServiceEnable()) {
            activity.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
        else
        {
            activity.startService(new Intent(activity, NLService.class));
        }
    }
    /**
     * 是否已授权
     *
     * @return
     */
    private boolean isNotificationServiceEnable() {
        //Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(context);
        return NotificationManagerCompat.getEnabledListenerPackages(context).contains(context.getPackageName());
    }

    private void changeTextViewColor() {
        imageBusiness.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.sw));
        imageSport.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.yd));
        imageDiscovery.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.fx));
        imageMe.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.wd));
        txtBusiness.setTextColor(Color.parseColor("#abadbb"));
        txtSport.setTextColor(Color.parseColor("#abadbb"));
        txtDiscovery.setTextColor(Color.parseColor("#abadbb"));
        txtMe.setTextColor(Color.parseColor("#abadbb"));
    }

    private void changeSelectedTabState(int position) {
        switch (position) {
            case 0:
                txtBusiness.setTextColor(activity.getResources().getColor(R.color.mainColor));
                imageBusiness.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.sw_visited));
                break;
            case 1:
                txtSport.setTextColor(activity.getResources().getColor(R.color.mainColor));
                imageSport.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.yd_visited));
                break;
            case 2:
                txtDiscovery.setTextColor(activity.getResources().getColor(R.color.mainColor));
                imageDiscovery.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.fx_visited));
                break;
            case 3:
                txtMe.setTextColor(activity.getResources().getColor(R.color.mainColor));
                imageMe.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.wd_visited));
                break;
        }
    }

    public void onMeClick(final ViewPager viewPager, final int id) {
        if (isLogin) {
//            if (id == R.id.btn_msg)
//
//            else {
//                viewPager.setCurrentItem(2, true);
//                viewMainTop.setVisibility(View.GONE);
//            }
        } else {
            DialogYesOrNo dialog = DialogYesOrNo.getInstance();
            dialog.showDialog(context, "请先登录", new AlertDialogCallBack() {
                @Override
                public void executeEvent() {
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                }

            });
            dialog.setConfirmText("前往登录");

        }

    }

    public void onInit() {
        initData();
        DialogLoading.show(activity);
        atm.request(NnyConst.CHECKVERSION, this);

        if (isLogin) {
            //BroadcastManager.getInstance(context).sendBroadcast(UPDATEUNREAD, "login");

            String toWhere = activity.getIntent().getStringExtra("toWhere");
            toWhere(toWhere);
        } else {
            viewPager.setCurrentItem(1, true);
        }

    }

    private void initMianViewPager() {
        FragmentPagerAdapter fragmentPagerAdapter; //将 tab  页面持久在内存中
        fragments = new ArrayList<>();
        fragments.add(BusinessFragment.getInstance());
        fragments.add(SportFragment.getInstance());
        fragments.add(DiscoveryFragment.getInstance());
        fragments.add(MineFragment.getInstance());
        fragmentPagerAdapter = new FragmentPagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setCurrentItem(CURRENTVIEWPAGEINDEX);
        viewPager.setOffscreenPageLimit(MAXCACHEVIEWPAGES);
        viewPager.setOnPageChangeListener(new PageChangerListener());
        //initData();
    }

    /**
     * 重写onactivityresult方法，使二个或多个fragment嵌套使用时能收到onactivityresut回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResultt(int requestCode, int resultCode, Intent data) {
        FragmentManager fragmentManager=activity.getSupportFragmentManager();
        for(int indext=0;indext<fragmentManager.getFragments().size();indext++)
        {
            Fragment fragment=fragmentManager.getFragments().get(indext); //找到第一层Fragment
            if(fragment==null)
                Log.w(TAG, "Activity result no fragment exists for index: 0x"
                        + Integer.toHexString(requestCode));
            else
                handleResult(fragment,requestCode,resultCode,data);
        }
    }

    /**
     * 递归调用，对所有的子Fragment生效
     * @param fragment
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment fragment,int requestCode,int resultCode,Intent data)
    {
        fragment.onActivityResult(requestCode, resultCode, data);//调用每个Fragment的onActivityResult
        Log.e(TAG, "MyBaseFragmentActivity");
        List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments(); //找到第二层Fragment
        if(childFragment!=null){
            for(Fragment f:childFragment)
                if(f!=null)
                    handleResult(f, requestCode, resultCode, data);
        }
        else
            Log.e(TAG, "MyBaseFragmentActivity1111");
    }


    private class PageChangerListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            int index= viewPager.getCurrentItem();
//            if(index==3){
//                onMeClick(viewPager, R.id.tab_layout_me);
//            }
            changeTextViewColor();
            changeSelectedTabState(position);
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    public void toWhere(String toWhere) {
        if ("toMine".equals(toWhere)) {
            viewPager.setCurrentItem(2, true);
        }
        else if ("toSearch".equals(toWhere)) {
            viewPager.setCurrentItem(0, true);
        }
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
               case NnyConst.CHECKVERSION:
                return userAction.checkVersion();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
        if (result == null) return;

        switch (requestCode) {
            case NnyConst.CHECKVERSION:
                VersionResponse versionResponse = (VersionResponse) result;
                if (versionResponse.getState() == NnyConst.SUCCESS) {
                    final VersionResponse.ResultEntity entity = versionResponse.getAndroid();
                    String[] versionInfo = getVersionInfo(activity);
                    int versionCode = Integer.parseInt(versionInfo[0]);
                    if (entity.getVersionCode() > versionCode) {
                        DialogYesOrNo dialog = DialogYesOrNo.getInstance();
                        dialog.showDialog(context, "发现新版本:" + entity.getVersionName(), new AlertDialogCallBack() {
                            @Override
                            public void executeEvent() {
                                MainPresenter.this.apkUrl=entity.getDownloadUrl();
                                requestPermissions();
                            }

                        });
                        dialog.setConfirmText("立即更新");
                        dialog.setContent(entity.getVersionInfo());
                    }
                }
                break;
        }
    }

    public void onDestroy() {

    }

    private void requestPermissions() {
        //权限申请
        String[] Permissions = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.PROCESS_OUTGOING_CALLS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS};
        PermissionHelper.with(activity)
                .permissions(Permissions)
                .showOnRationale("需打开相机、存储、定位权限", "取消", "授权")    //用户拒绝过但没有勾选不再提示会显示对话框
                .showOnDenied("需打开相机、存储、定位权限", "取消", "去设置") //用户勾选不再提示会显示对话框
                .listener(this)
                .request();
    }

    @Override
    public void onGranted() {
        if (apkUrl == null) return;
        Intent intent = new Intent(activity, DownloadService.class);
        intent.putExtra("url", apkUrl);
        activity.startService(intent);
    }

    @Override
    public void onFailed(ArrayList<String> deniedPermissions) {

    }

    @Override
    public void onDragOut() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_layout_business:
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.tab_layout_sport:
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.tab_layout_discovery:
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.tab_layout_me:
                viewPager.setCurrentItem(3, false);
                //onMeClick(viewPager,R.id.tab_layout_me);
                break;
        }
    }
}
