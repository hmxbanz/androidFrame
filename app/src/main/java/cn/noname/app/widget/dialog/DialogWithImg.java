package cn.noname.app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.noname.app.R;

public class DialogWithImg extends Dialog implements View.OnClickListener {

    private Context context;
    private Button btnSubmit,btnCancle;
    private static DialogWithImg instance;
    private DialogListener listener;
    private boolean mCancelable;
    private TextView title,content;
    private ImageView title_img;
    public DialogListener getListener() {
        return listener;
    }

    public Button getBtnSubmit() {
        return btnSubmit;
    }
    public TextView getTitle() {
        return title;
    }
    public TextView getContent() {
        return content;
    }
    public ImageView getTitle_img() {
        return title_img;
    }

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }
    public void setBtnSubmit(String btnText) {
        this.btnSubmit.setText(btnText);
    }
    public void setTitle(String title) {
        this.title.setText(Html.fromHtml(title));
    }
    public void setContent(String content) {
        this.content.setText(Html.fromHtml(content));
    }
    public void setTitle_img(Drawable title_img) {
        this.title_img.setImageDrawable(title_img);
    }

    public DialogWithImg(Context context) {
        super(context, R.style.dialogFullscreenTranslucent);
    this.context=context;
    }
    public DialogWithImg(Context context, int theme) { super(context, theme);   }
    public DialogWithImg(Context context, String s) {
        super(context, R.style.dialogFullscreenTranslucent);
    }
    public static DialogWithImg getInstance(Context context) {
        if (instance == null) {
            instance = new DialogWithImg(context);
        }
        return instance;
    }

    public interface DialogListener {
        void onSubmit();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_with_img);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(layoutParams);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //window.setBackgroundDrawableResource(R.drawable.transparent_drawable);
        findViewById(R.id.root_view).setOnClickListener(this);

        setCanceledOnTouchOutside(true);

        title_img=findViewById(R.id.title_img);
        content=findViewById(R.id.content);
        content.setOnClickListener(this);
        title=findViewById(R.id.txt_dialog_title);
        btnSubmit =  findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        btnCancle =  findViewById(R.id.btn_cancel);
        btnCancle.setOnClickListener(this);

    }

    public boolean onTouchEvent(MotionEvent event) {
        Window window = getWindow();

        if (window == null) return false;
        final View decorView = window.getDecorView();
        if (mCancelable && isShowing() && shouldCloseOnTouch(event, decorView)) {
            cancel();
            return true;
        }
        return false;
    }

    public boolean shouldCloseOnTouch(MotionEvent event, View decorView) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        return (x <= 0) || (y <= 0)
                || (x > (decorView.getWidth()))
                || (y > (decorView.getHeight()));
    }

    public void setCancelable(boolean flag)   {
        super.setCancelable(flag);
        mCancelable = flag;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                    dismiss();
                break;
            case R.id.btn_submit:
                    dismiss();
                break;
        }
            return;
        }
    }

