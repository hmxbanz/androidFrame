package cn.noname.app.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import cn.noname.app.R;
import cn.noname.app.presenter.HomeFragmentPresenter;
import cn.noname.app.widget.NoScrollViewPager;


public class SportFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener, OnBannerListener {
    private  int CURRENTPAGEINDEX =0;
    private final int MAXCACHEVIEWPAGES =4;
    private NoScrollViewPager viewPager;
    private List<Fragment> fragments;
    FragmentPagerAdapter fragmentPagerAdapter; //将 tab  页面持久在内存中
    public static List<?> images=new ArrayList<>();
    public static SportFragment instance = null;
    private View view;
    private Banner banner;
    private SwipeRefreshLayout swiper;
    public static SportFragment getInstance() {
        if (instance == null) {
            instance = new SportFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);
        initViews();
        HomeFragmentPresenter homeFragmentPresenter = new HomeFragmentPresenter(getContext());
        homeFragmentPresenter.init(banner);
        return view;
    }

    private void initViews() {
        swiper = view.findViewById(R.id.swipe);

        initMianViewPager();
    }

    private void initMianViewPager() {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public void loadData(int fragmentIndex){
        switch (fragmentIndex){
            case 0:
                HomeNewFragment e= (HomeNewFragment) fragmentPagerAdapter.getItem(fragmentIndex);
                e.Refresh();
                break;
            case 1:
                HomeHotFragment d= (HomeHotFragment) fragmentPagerAdapter.getItem(fragmentIndex);
                d.Refresh();
                break;
        }

    }

    @Override
    public void onRefresh() {
        loadData(CURRENTPAGEINDEX);
        swiper.setRefreshing(false);
    }

    @Override
    public void OnBannerClick(int position) {

    }
}
