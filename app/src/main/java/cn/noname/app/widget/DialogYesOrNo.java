package cn.noname.app.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import cn.noname.app.R;



/**
 * Created by AMing on 15/11/26.
 * Company RongCloud
 */
public class DialogYesOrNo {

    private static DialogYesOrNo instance = null;
    private Context context;
    private TextView txtTitle, txtBtnCancle, txtBtnConfirm,content;
    private View line;

    public static DialogYesOrNo getInstance() {
        if (instance == null) {
            instance = new DialogYesOrNo();
        }
        return instance;
    }

    private DialogYesOrNo() {

    }

    public void showDialog(Context context, String titleText, final DialogCallBack callBack) {
        this.context= context;
        final AlertDialog alterDialog = new AlertDialog.Builder(this.context,R.style.mydialog).create();
        alterDialog.show();
        Window window=alterDialog.getWindow();
        window.setContentView(R.layout.layout_alert_dialog);
        txtTitle =  window.findViewById(R.id.txt_title);
        txtTitle.setText(titleText);
        txtBtnCancle =  window.findViewById(R.id.btn_cancle);
        txtBtnCancle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alterDialog.dismiss();
                callBack.onCancle();
            }
        });
        txtBtnConfirm =  window.findViewById(R.id.btn_confirm);
        txtBtnConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callBack.executeEvent();
                alterDialog.dismiss();
            }
        });

        line=alterDialog.findViewById(R.id.line);
        content = (TextView) alterDialog.findViewById(R.id.txt_content);

        //另一种自定义alertDialog样式的方法(只定义中间部分布局，按键“确定”，“取消”还是系统的)
//AlertDialog.Builder alterDialog = new AlertDialog.Builder(context);
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_setting, null);
//        alterDialog.setView(layout);
//        alterDialog.setMessage(titleInfo);
//        alterDialog.setCancelable(true);//
//        alterDialog.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                callBack.executeEvent();
//            }
//        });
//        alterDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
    }

    public void setTitle(String title){
        if(!TextUtils.isEmpty(title))
            txtTitle.setText(title);
    }
    public void setConfirmText(String confirmText){
        if(!TextUtils.isEmpty(confirmText))
            txtBtnConfirm.setText(confirmText);
    }

    public void setCancleVisibility(int flag){
        if(flag==View.GONE) {
            this.txtBtnCancle.setVisibility(View.GONE);
            this.line.setVisibility(View.GONE);
        }
    }

    public void setCancleText(String cancleText){
        if(!TextUtils.isEmpty(cancleText))
            txtBtnCancle.setText(cancleText);
    }

    public void setContent(String content){
        this.content.setText(content);
        this.content.setVisibility(View.VISIBLE);
    }

    public interface DialogCallBack {
        void executeEvent();
        void onCancle();
    }


}
