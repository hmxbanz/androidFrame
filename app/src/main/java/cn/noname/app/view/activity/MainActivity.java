package cn.noname.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.noname.app.R;
import cn.noname.app.presenter.MainPresenter;

public class MainActivity extends BaseActivity {

    private MainPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.base_layout).setFitsSystemWindows(false);
        presenter = new MainPresenter(this);
        presenter.init();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String toWhere = intent.getStringExtra("toWhere");
        presenter.toWhere(toWhere);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResultt(requestCode, resultCode, data);
    }


    public static void StartActivity(Context context, String toWhere){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("toWhere",toWhere);
        context.startActivity(intent);
    }

}
