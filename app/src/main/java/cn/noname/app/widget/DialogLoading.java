package cn.noname.app.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import cn.noname.app.R;
import cn.noname.app.common.NToast;

public class DialogLoading extends Dialog {

    /**
     * DialogLoading
     */
    private static DialogLoading dialogLoading;
    /**
     * canNotCancel, the mDialogTextView dimiss or undimiss flag
     */
    private boolean canNotCancel;
    /**
     * if the mDialogTextView don't dimiss, what is the tips.
     */
    private String tipMsg;

    private TextView mShowMessage;

    /**
     * the DialogLoading constructor
     *
     * @param context          Context
     * @param canNotCancel boolean
     * @param tipMsg       String
     */
    public DialogLoading(final Context context, boolean canNotCancel, String tipMsg, boolean isTranslucent) {
        super(context);

        this.canNotCancel = canNotCancel;
        this.tipMsg = tipMsg;
        this.getContext().setTheme(android.R.style.Theme_InputMethod);
        setContentView(R.layout.layout_dialog_loading);

        if (!TextUtils.isEmpty(this.tipMsg)) {
            mShowMessage = (TextView) findViewById(R.id.show_message);
            mShowMessage.setText(this.tipMsg);
        }

        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        //设置窗体背景透明度
        if(isTranslucent) {
            attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            attributesParams.dimAmount = 0.5f;
        }
        attributesParams.alpha=0.7f;//设置内容透明度
        window.setAttributes(attributesParams);

        window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canNotCancel) {
                NToast.shortToast(getContext(), tipMsg);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * show the mDialogTextView
     *
     * @param context
     */
    public static void show(Context context) {
        show(context, null, false,false);
    }
    //可让背景有半透明黑色遮罩
    public static void show(Context context,boolean isTranslucent) {
        show(context, null, false,true);
    }

    /**
     * show the mDialogTextView
     *
     * @param context Context
     * @param message String
     */
    public static void show(Context context, String message) {
        show(context, message, false,false);
    }

    /**
     * show the mDialogTextView
     *
     * @param context  Context
     * @param message  String, show the message to user when isCancel is true.
     * @param isCancel boolean, true is can't dimiss，false is can dimiss
     */
    private static void show(Context context, String message, boolean isCancel,boolean isTranslucent) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (dialogLoading != null && dialogLoading.isShowing()) {
            return;
        }
        dialogLoading = new DialogLoading(context, isCancel, message,isTranslucent);
        dialogLoading.show();
    }

    /**
     * dismiss the mDialogTextView
     */
    public static void dismiss(Context context) {
        try {
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    dialogLoading = null;
                    return;
                }
            }

            if (dialogLoading != null && dialogLoading.isShowing()) {
                Context loadContext = dialogLoading.getContext();
                if (loadContext != null && loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        dialogLoading = null;
                        return;
                    }
                }
                dialogLoading.dismiss();
                dialogLoading = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            dialogLoading = null;
        }
    }
}
