package cn.qqtheme.framework.picker;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Calendar;

import cn.qqtheme.framework.util.DateUtils;
import cn.qqtheme.framework.widget.WheelView;

/**
 * 年龄，身高，体重范围选择器
 *
 * @author hmx[QQ:41335373]
 * @since 2017/05/29
 */
public class RangePicker extends WheelPicker {
    /**
     * 年龄
     */
    public static final int AGE = 0;
    /**
     * 身高
     */
    public static final int BODY_HEIGHT = 1;
    /**
     * 体重
     */
    public static final int BODY_WEIGHT = 2;
    private OnRangePickListener onRangePickListener;
    private int mode;
    private String minLabel = "起", maxLabel = "止";
    private String selectedMin = "", selectedMax = "";
    private int minStart,minEnd,maxStart,maxEnd;

    /**
     * 安卓开发应避免使用枚举类（enum），因为相比于静态常量enum会花费两倍以上的内存。
     * http://developer.android.com/training/articles/memory.html#Overhead
     */
    @IntDef(flag = false, value = {AGE, BODY_HEIGHT,BODY_WEIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public RangePicker(Activity activity) {
        this(activity, AGE);
    }

    /**
     * @see #BODY_HEIGHT
     * @see #BODY_WEIGHT
     */
    public RangePicker(Activity activity, @Mode int mode) {
        super(activity);
        this.mode = mode;
    }

    /**
     * 设置时间显示的单位
     */
    public void setLabel(String minLabel, String maxLabel) {
        this.minLabel = minLabel;
        this.maxLabel = maxLabel;
    }

    /**
     * 设置默认选中的时间
     */
    public void setSelectedItem(int min, int max) {
        selectedMin = String.valueOf(min);
        selectedMax = String.valueOf(max);
    }
    /**
     * 设置选择范围
     */
    public void setRange(int minStart,int minEnd, int maxStart,int maxEnd) {
        this.minStart = minStart;
        this.minEnd=minEnd;
        this.maxStart = maxStart;
        this.maxEnd=maxEnd;
    }
    public void setOnRangePickListener(OnRangePickListener listener) {
        this.onRangePickListener = listener;
    }

    @Override
    @NonNull
    protected View makeCenterView() {
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        WheelView minView = new WheelView(activity);
        minView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        minView.setTextSize(textSize);
        minView.setTextColor(textColorNormal, textColorFocus);
        minView.setLineVisible(lineVisible);
        minView.setLineColor(lineColor);
        layout.addView(minView);
        TextView minTextView = new TextView(activity);
        minTextView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        minTextView.setTextSize(textSize);
        minTextView.setTextColor(textColorFocus);
        if (!TextUtils.isEmpty(minLabel)) {
            minTextView.setText(minLabel);
        }
        layout.addView(minTextView);
        ////////////////
        WheelView maxView = new WheelView(activity);
        maxView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        maxView.setTextSize(textSize);
        maxView.setTextColor(textColorNormal, textColorFocus);
        maxView.setLineVisible(lineVisible);
        maxView.setLineColor(lineColor);
        maxView.setOffset(offset);
        layout.addView(maxView);
        TextView maxTextView = new TextView(activity);
        maxTextView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        maxTextView.setTextSize(textSize);
        maxTextView.setTextColor(textColorFocus);
        if (!TextUtils.isEmpty(maxLabel)) {
            maxTextView.setText(maxLabel);
        }
        layout.addView(maxTextView);

        ArrayList<String> mins = new ArrayList<String>();
        if (mode == AGE) {
            for (int i = minStart; i <= minEnd; i++) {
                mins.add(String.valueOf(i));
            }
        }
        minView.setItems(mins, selectedMin);

        ArrayList<String> maxs = new ArrayList<String>();
        for (int i = maxStart; i < maxEnd; i++) {
            maxs.add(String.valueOf(i));
        }
        maxView.setItems(maxs, selectedMax);
        minView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                selectedMin = item;
            }
        });
        maxView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                selectedMax = item;
            }
        });
        return layout;
    }

    @Override
    public void onSubmit() {
        if (onRangePickListener != null) {
            onRangePickListener.onRangePicked(selectedMin, selectedMax);
        }
    }

    /**
     * Gets selected min.
     *
     * @return the selected min Num
     */
    public String getSelectedMin() {
        return selectedMin;
    }

    /**
     * Gets selected max.
     *
     * @return the selected max Num
     */
    public String getSelectedMax() {
        return selectedMax;
    }

    /**
     * The interface On Range pick listener.
     */
    public interface OnRangePickListener {

        /**
         * On Range picked.
         *
         * @param min   the start num
         * @param max the end num
         */
        void onRangePicked(String min, String max);

    }

}
