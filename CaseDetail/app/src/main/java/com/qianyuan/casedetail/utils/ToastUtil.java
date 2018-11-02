package com.qianyuan.casedetail.utils;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.qianyuan.casedetail.application.MyApplication;


/**
 * Created by sun on 2017/2/28.
 */

public class ToastUtil {


    private static Toast mToast;
    private static final int TOAST_LENGTH_SHORT = 0;
    private static final int TOAST_LENGTH_LONG = 1;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    private static void showToast(String msg, int length_type) {
        Context context = MyApplication.getInstance();
        if (null == context || TextUtils.isEmpty(msg)) {
            return;
        }
        mHandler.removeCallbacks(r);
        if (mToast != null)
            mToast.setText(msg);
        else
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        mHandler.postDelayed(r, 3000);
        mToast.show();
    }

    private static void showToast(int msg, int length_type) {
        Context context = MyApplication.getInstance();
        if (null == context) {
            return;
        }
        mHandler.removeCallbacks(r);
        if (mToast != null)
            mToast.setText(msg);
        else
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        mHandler.postDelayed(r, 3000);
        mToast.show();
    }

    public static void showShortToast(String msg) {
        showToast(msg, TOAST_LENGTH_SHORT);
    }

    public static void showShortToast(int msg) {
        showToast(msg, TOAST_LENGTH_SHORT);
    }

    public static void showLongToast(String msg) {
        showToast(msg, TOAST_LENGTH_LONG);
    }

    public static void showLongToast(int msg) {
        showToast(msg, TOAST_LENGTH_LONG);
    }


}
