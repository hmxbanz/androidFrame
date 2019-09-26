package cn.noname.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import cn.noname.app.R;

public class DialogTip extends Dialog implements View.OnClickListener {

    private Context context;
    private Button btnSubmit;
    private static DialogTip instance;
    private DialogPopListener listener;
    private boolean mCancelable;

    public DialogPopListener getListener() {
        return listener;
    }
    public void setListener(DialogPopListener listener) {
        this.listener = listener;
    }

    public DialogTip(Context context) {
        super(context, R.style.dialogFullscreenTranslucent);
    this.context=context;
    }
    public DialogTip(Context context, int theme) { super(context, theme);   }

    public DialogTip(Context context, String s) {
        super(context, R.style.dialogFullscreenTranslucent);
    }
    public static DialogTip getInstance(Context context) {
        if (instance == null) {
            instance = new DialogTip(context);
        }
        return instance;
    }
    public interface DialogPopListener {
        void onSubmit(int i, int quantity, int productAttributeId);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_tip);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(layoutParams);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //window.setBackgroundDrawableResource(R.drawable.transparent_drawable);
        findViewById(R.id.root_view).setOnClickListener(this);
        setCanceledOnTouchOutside(true);

        btnSubmit =  findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

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
            case R.id.root_view:

                break;
        }
            return;
        }
    }

