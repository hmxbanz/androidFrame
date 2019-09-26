package cn.noname.app.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jaeger.library.StatusBarUtil;

import cn.noname.app.R;
import cn.noname.app.presenter.StartPresenter;

/**
 * Created by Administrator on 2015/10/3.
 */
public class StartActivity extends AppCompatActivity {
    private StartPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_start);
        presenter = new StartPresenter(this);
        presenter.init();
        //setHeadVisibility(View.GONE);
    }

}
