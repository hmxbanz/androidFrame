package cn.noname.app.presenter;

import android.content.Context;

import cn.noname.app.server.async.OnDataListener;
import cn.noname.app.widget.DialogLoading;

public class CommonPresenter extends BasePresenter implements OnDataListener{
    //private ContactsActivity activity;
    public CommonPresenter(Context context){
        super(context);
        //activity = (ContactsActivity) context;
    }

    public void init() {
        DialogLoading.show(context);
    }


}
