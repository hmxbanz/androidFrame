package cn.noname.app.view.activity;

import android.os.Bundle;
import android.view.View;

import cn.noname.app.R;
import cn.noname.app.presenter.GuidePresenter;

public class GuideActivity extends BaseActivity {

    private GuidePresenter presenter;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        setHeadVisibility(View.GONE);
        presenter = new GuidePresenter(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

}
