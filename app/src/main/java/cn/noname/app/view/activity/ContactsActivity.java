package cn.noname.app.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import cn.noname.app.R;
import cn.noname.app.presenter.ContactsPresenter;
import cn.noname.app.widget.DragPointView;
import cn.noname.app.widget.pinyin.SideBar;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static cn.smssdk.SMSSDK.getSupportedCountries;



public class ContactsActivity extends BaseActivity {

    private ContactsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setTitle("国家列表");
        presenter = new ContactsPresenter(this);
    }

}
