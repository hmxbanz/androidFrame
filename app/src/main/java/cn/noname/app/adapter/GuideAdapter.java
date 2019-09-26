package cn.noname.app.adapter;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.noname.app.view.fragment.GuideFragment1;
import cn.noname.app.view.fragment.GuideFragment2;
import cn.noname.app.view.fragment.GuideFragment3;

/**
 * Created by Administrator on 2015/10/3.
 */

public class GuideAdapter extends FragmentPagerAdapter
{
    private Context context;
    //FragmentManager fragment管理器 ,上下文
    public GuideAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }
    //返回一个fragment
    //position 滑动到第几页
    @Override
    public Fragment getItem(int position) {
        Fragment mFragment = null;
        if(position == 0){
            mFragment = new GuideFragment1(context);
        }else if(position == 1){
            mFragment = new GuideFragment2(context);
        }else if(position == 2){
            mFragment = new GuideFragment3(context);
        }
        return mFragment;
    }

    //返回适配数量
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }
}

