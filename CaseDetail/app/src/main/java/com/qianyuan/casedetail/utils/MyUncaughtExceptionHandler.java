package com.qianyuan.casedetail.utils;

/**
 * Created by sun on 2017/5/22.
 */

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        // 结束当前进程
        android.os.Process.killProcess(android.os.Process.myPid());

    }
}
