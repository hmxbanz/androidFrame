package cn.noname.app.presenter;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import cn.noname.app.NnyConst;
import cn.noname.app.R;
import cn.noname.app.server.HttpException;
import cn.noname.app.server.async.OnDataListener;
import cn.noname.app.view.activity.ProtocolActivity;
import cn.noname.app.widget.DialogLoading;


public class ProtocolPresenter extends BasePresenter implements OnDataListener{
    private ProtocolActivity activity;
    private TextView txtProtocol;
    public ProtocolPresenter(Context context){
        super(context);
        activity = (ProtocolActivity) context;
    }

    public void init() {
        txtProtocol = (TextView) activity.findViewById(R.id.txt_protocol);
        DialogLoading.show(context);
        atm.request(NnyConst.GETPROTOCOCOL,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return userAction.getProtocol();
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
        if (result == null) return;
        txtProtocol.setText(Html.fromHtml(result.toString()));
    }
}
