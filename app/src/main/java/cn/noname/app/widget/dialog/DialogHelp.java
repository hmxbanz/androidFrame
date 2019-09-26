package cn.noname.app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import cn.noname.app.R;

public class DialogHelp extends Dialog implements View.OnClickListener {

    private Context context;
    private static DialogHelp instance;
    private DialogPopListener listener;
    private boolean mCancelable;
    private TextView txt_dialog_title,txt_dialog_content;

    public void setTxt_dialog_title(String txt_dialog_title) {
        this.txt_dialog_title.setText(txt_dialog_title);
    }

    public void setTxt_dialog_content(String txt_dialog_content) {
        this.txt_dialog_content.setText(txt_dialog_content);
    }

    public DialogPopListener getListener() {
        return listener;
    }
    public void setListener(DialogPopListener listener) {
        this.listener = listener;
    }

    public DialogHelp(Context context) {
        super(context, R.style.dialogHelp);
    this.context=context;
    }
    public DialogHelp(Context context, int theme) { super(context, theme);   }

    public DialogHelp(Context context, String s) {
        super(context, R.style.dialogHelp);
    }
    public static DialogHelp getInstance(Context context) {
        if (instance == null) {
            instance = new DialogHelp(context);
        }
        return instance;
    }
    public interface DialogPopListener {
        void onSubmit(int i, int quantity, int productAttributeId);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_help);
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

        findViewById(R.id.txt_skip).setOnClickListener(this);
        findViewById(R.id.img_close).setOnClickListener(this);
        txt_dialog_title=findViewById(R.id.txt_dialog_title);
        txt_dialog_content=findViewById(R.id.txt_dialog_content);

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
            case R.id.txt_skip:
                    dismiss();
                break;
        }
            return;
        }
    }

