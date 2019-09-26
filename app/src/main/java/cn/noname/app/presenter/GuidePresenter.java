package cn.noname.app.presenter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

import cn.noname.app.R;
import cn.noname.app.adapter.GuideAdapter;
import cn.noname.app.listener.OnPageScrolledListener;
import cn.noname.app.server.async.OnDataListener;
import cn.noname.app.view.activity.GuideActivity;
import cn.noname.app.widget.DialogLoading;
import cn.noname.app.widget.scaleIndicator.ScaleIndicatorLayout;

public class GuidePresenter extends BasePresenter implements OnDataListener{
    private ViewPager viewPager;
    private ScaleIndicatorLayout indicator;
    GuideActivity guideActivity;
    public GuidePresenter(Context context){
        super(context);
        guideActivity=(GuideActivity)activity;
        init();
    }

    public void init()
    {
        indicator = activity.findViewById(R.id.indicator);
        this.viewPager = activity.findViewById(R.id.GuiderViewPager);
        GuideAdapter GuideAdapter = new GuideAdapter(guideActivity.getSupportFragmentManager(), context);
        this.viewPager.setAdapter(GuideAdapter);
        OnPageScrolledListener listener = new OnPageScrolledListener() {
            @Override
            public void onPageScrolled(int curItem, int nextItem, float radio) {
                indicator.setRadio(curItem, 1f - radio);
                indicator.setRadio(nextItem, radio);
            }
            @Override
            public void onPageSelected(int position) {
                indicator.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
            }
        };
        listener.setViewPager(this.viewPager);
        int curIndex = 0;
        this.viewPager.setCurrentItem(curIndex);
        indicator.setRadio(curIndex, 1f);
    }


}
