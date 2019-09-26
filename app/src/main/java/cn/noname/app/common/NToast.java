/*
    ShengDao Android Client, NToast
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package cn.noname.app.common;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.noname.app.R;


public class NToast {

    public static void shortToast(Context context, int resId) {
        showToast(context, resId, Toast.LENGTH_SHORT);
    }

    public static void shortToast(Context context, String text) {
        if (!TextUtils.isEmpty(text) && !"".equals(text.trim())) {
            showToast(context, text, Toast.LENGTH_SHORT);
        }
    }

    public static void longToast(Context context, int resId) {
        showToast(context, resId, Toast.LENGTH_LONG);
    }

    public static void longToast(Context context, String text) {
        if (!TextUtils.isEmpty(text) && !"".equals(text.trim())) {
            showToast(context, text, Toast.LENGTH_LONG);
        }
    }

    public static void showToast(Context context, int resId, int duration) {
        if (context == null) {
            return;
        }
        if (context != null && context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        String text = context.getString(resId);
        showToast(context, text, duration);
    }

    public static void showToast(Context context, String text, int duration) {
        if (context == null) {
            return;
        }
        if (context != null && context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (!TextUtils.isEmpty(text) && !"".equals(text.trim())) {
            Toast toast=Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastView = (LinearLayout) toast.getView();

            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            View layout = inflater.inflate(R.layout.layout_custom_toast,(ViewGroup) ((Activity)context).findViewById(R.id.llToast));
            ImageView image = layout.findViewById(R.id.tvImageToast);
            image.setImageResource(R.drawable.icon_tang);
            TextView text2 = layout.findViewById(R.id.tvTextToast);
            text2.setText(text);
            toast.setView(layout);
            toast.show();
        }
    }
}
