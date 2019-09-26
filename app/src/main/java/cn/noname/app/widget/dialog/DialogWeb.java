package cn.noname.app.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import cn.noname.app.R;
import cn.noname.app.webview.MyWebChromeClient;
import cn.noname.app.webview.MyWebViewClient;
import cn.noname.app.widget.FlikerProgressBar;

public class DialogWeb extends Dialog implements View.OnClickListener {

    private Context context;
    private static DialogWeb instance;
    private boolean mCancelable;
    private TextView txt_dialog_title,txt_dialog_content;

    public void setListner(DialogWebListner listner) {
        this.listner = listner;
    }

    private DialogWebListner listner;

    public interface DialogWebListner{
        void onSubmit();
        void onCancle();
    }
    public void setTxt_dialog_title(String txt_dialog_title) {
        this.txt_dialog_title.setText(txt_dialog_title);
    }

    public void setTxt_dialog_content(String txt_dialog_content) {
        this.txt_dialog_content.setText(txt_dialog_content);
    }


    public DialogWeb(Context context) {
        super(context, R.style.dialogHelp);
        this.context=context;
    }
    public DialogWeb(Context context, int theme) { super(context, theme);   }

    public DialogWeb(Context context, String s) {
        super(context, R.style.dialogHelp);
    }
    public static DialogWeb getInstance(Context context) {
        if (instance == null) {
            instance = new DialogWeb(context);
        }
        return instance;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_web);
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

        txt_dialog_title=findViewById(R.id.txt_dialog_title);
        findViewById(R.id.img_close).setOnClickListener(this);
        findViewById(R.id.btn_agree).setOnClickListener(this);
        FlikerProgressBar progressBar = findViewById(R.id.flikerbar);

        WebView webView = findViewById(R.id.webView);
        MyWebChromeClient webChromeClient = new MyWebChromeClient((Activity)context);
        webChromeClient.setProgressBar(progressBar);
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(new MyWebViewClient((Activity) context));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        //webView.addJavascriptInterface(new WebViewActivity.JpushForJs(), "Jpush");
        //webView.getSettings().setUserAgentString("Android Chrome/37.0.0.0 Mobile Safari/537.36");
        webView.getSettings().setAppCacheEnabled(true);
        //设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        String storePath = Environment.getExternalStorageDirectory().getPath().toString() + "/webcache/";
        webView.getSettings().setAppCachePath(storePath);
        webView.loadUrl("https://reg.163.com/agreement_wap.shtml?v=20171127");

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
            case R.id.btn_agree:
                if(listner!=null){ dismiss();listner.onSubmit();}

                break;
        }
            return;
        }
    }

