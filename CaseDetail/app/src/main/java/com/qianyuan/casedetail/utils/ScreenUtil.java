package com.qianyuan.casedetail.utils;

import android.content.Context;

/**
 * Created by Che on 2016/8/3.
 */
public class ScreenUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕的密度
     */
    public static float density(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕的宽
     */
    public static int screenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕的高
     */
    public static int screenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int statusBarHeight(Context context) {
        int statusHeight = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        statusHeight = context.getResources().getDimensionPixelSize(statusHeight);
        return statusHeight;
    }
}
