package cn.noname.app.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jaeger.library.StatusBarUtil;

import cn.noname.app.R;

/**
 * Created by hmx on 2016/5/21.
 */
public class BaseActivity extends AppCompatActivity {
    protected final static String TAG = BaseActivity.class.getSimpleName();
    protected TextView txtTitle;
    protected TextView txtRight;
    protected RelativeLayout layoutBack, layoutRight;
    private ViewFlipper contentView;
    private LinearLayout headLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        super.setContentView(R.layout.layout_base);
        //Window window = getWindow();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.RED);
//
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//           window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//           window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }

        //x.view().inject(this);
        // 初始化公共头部
        contentView = (ViewFlipper) super.findViewById(R.id.layout_container);
        headLayout = (LinearLayout) super.findViewById(R.id.layout_title_top);
        layoutBack = (RelativeLayout) super.findViewById(R.id.layout_back);
        layoutRight = (RelativeLayout) super.findViewById(R.id.layout_right);
        txtTitle = (TextView) super.findViewById(R.id.txt_title);
        txtRight = (TextView) super.findViewById(R.id.txt_right);
    }

    @Override
    public void setContentView(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        contentView.addView(view);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }

    /**
     * 设置头部是否可见
     *
     * @param visibility
     */
    public void setHeadVisibility(int visibility) {
        headLayout.setVisibility(visibility);
    }

    /**
     * 设置左边是否可见
     *
     * @param visibility
     */
    public void setHeadLeftButtonVisibility(int visibility) {
        layoutBack.setVisibility(visibility);
    }

    /**
     * 设置右边是否可见
     *
     * @param visibility
     */
    public void setHeadRightButtonVisibility(int visibility) {
        layoutRight.setVisibility(visibility);
    }

    /**
     * 设置标题
     */
    public void setTitle(int titleId) {
        setTitle(getString(titleId), false);
    }
    public void setTitle(int titleId, boolean flag) {
        setTitle(getString(titleId), flag);
    }
    public void setTitle(String title) {
        setTitle(title, false);
    }
    public void setTitle(String title, boolean flag) {
        txtTitle.setText(title);
        if (flag) {
            //mBtnLeft.setCompoundDrawables(null, null, null, null);
        } else {
            //mBtnLeft.setCompoundDrawables(mBtnBackDrawable, null, null, null);
        }
    }

    /**
     * 点击左按钮
     */
    public void onHeadLeftClick(View v) {
        finish();
    }

    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
        //StatusBarUtil.setColor(this, getResources().getColor(R.color.mainColorBlueDark), 0);
    }

}
