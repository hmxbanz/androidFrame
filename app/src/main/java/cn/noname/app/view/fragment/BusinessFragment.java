package cn.noname.app.view.fragment;


import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

import cn.noname.app.R;
import cn.noname.app.presenter.BusinessPresenter;
import cn.noname.app.webview.MyWebChromeClient;
import cn.noname.app.webview.MyWebViewClient;
import cn.noname.app.widget.FlikerProgressBar;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.OptionPicker;


/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class BusinessFragment extends Fragment implements View.OnClickListener,AddressPicker.OnAddressPickListener {
    private static final String TAG = BusinessFragment.class.getSimpleName() ;
    public static BusinessFragment instance = null;
    private LinearLayout layoutTitle;
    private TextView txtSearchTitle;
    private ImageView imageArrow;
    private View view;
    public Button btnAge, btnArea, btnSearch;
    private SwitchButton sbMarray, sbGender;
    private BusinessPresenter businessPresenter;

    //搜索参数
    private boolean gender=false;
    private boolean marry=false;
    private String ageRange="18-50岁";
    private String area="";
    private TextView emptyView;
    private WebView webView;
    private MyWebChromeClient webChromeClient;

    public static BusinessFragment getInstance() {
        if (instance == null) {
            instance = new BusinessFragment();
        }
        return instance;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_business, null);
        initViews();
        //initData();
        businessPresenter = new BusinessPresenter(getActivity());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    public void initViews(){
        FlikerProgressBar progressBar = view.findViewById(R.id.flikerbar);
        webView = view.findViewById(R.id.webView);//new PullToRefreshWebView(this);
        webChromeClient =new MyWebChromeClient(getActivity());
        webChromeClient.setProgressBar(progressBar);
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(new MyWebViewClient(getActivity()));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        //webView.addJavascriptInterface(new WebViewActivity.JpushForJs(), "Jpush");
        //webView.getSettings().setUserAgentString("Android Chrome/37.0.0.0 Mobile Safari/537.36");
        webView.getSettings().setAppCacheEnabled(true);
        //设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        String storePath = Environment.getExternalStorageDirectory().getPath().toString() + "/webcache/";
        webView.getSettings().setAppCachePath(storePath);
        webView.loadUrl("https://market.m.taobao.com/apps/market/tbsmall/hots.html?wh_weex=true&modueType=15&spm=a23456.11225166.modules.moduleOne&cpBookId=tshhgdfw");
          }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_age:
                String[] ageRange2=new String[]{"18-25岁", "26-30岁", "31-35岁","36-40岁","40岁以上"};
                OptionPicker picker = new OptionPicker(getActivity(),ageRange2 );
                picker.setOffset(1);
                picker.setSelectedIndex(1);
                picker.setTextSize(16);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int position, String option) {
                        btnAge.setText(option);
                        ageRange=option;
                    }
                });
                picker.show();
                break;
            case R.id.btn_area:

        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void initData() {
        sbGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                gender=isChecked;
            }
        });
        sbMarray.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                marry=isChecked;
            }
        });
    }

    @Override
    public void onAddressPicked(AddressPicker.Province province, AddressPicker.City city, AddressPicker.County county) {
        if (county == null) {
            //NLog.w(TAG,"province : " + province + ", city: " + city);
        } else {
            //NLog.w(TAG,"province : " + province + ", city: " + city + ", county: " + county);
        }
        btnArea.setText(province.getAreaName() + "-" + city.getAreaName());
        area=province.getAreaName() + " " + city.getAreaName();
    }
}