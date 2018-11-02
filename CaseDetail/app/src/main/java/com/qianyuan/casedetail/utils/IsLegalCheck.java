package com.qianyuan.casedetail.utils;

import android.text.TextUtils;

import com.qianyuan.casedetail.R;

/**
 * Created by sun on 2017/5/8.
 */

public class IsLegalCheck {
    public static boolean isLegalCheck(String des){
        if (TextUtils.isEmpty(des)) {
            ToastUtil.showLongToast(R.string.toast_describe_cannot_be_empty);
            return false;
        }
        return true;
    }
}
