package com.qianyuan.casedetail.application;

import android.app.Application;
import android.os.Environment;

import com.lzy.okgo.OkGo;
import com.qianyuan.casedetail.utils.DeleteFiles;
import com.qianyuan.casedetail.utils.MyUncaughtExceptionHandler;

import org.xutils.x;

/**
 * Created by sun on 2017/4/21.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        instance = this;
        init();
    }

    private void init() {
        //设置捕捉全局异常
//        MyUncaughtExceptionHandler uncaughtExceptionHandler=new MyUncaughtExceptionHandler();
//        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
        OkGo.init(this);
        //图片的数量到达一定程度，删除
        String photoDir = Environment.getExternalStorageDirectory().getPath() + "/gridmember/uploadImg/";
        String voiceDir = Environment.getExternalStorageDirectory().getPath() + "/gridmember/voice/";
        String imgDir = Environment.getExternalStorageDirectory().getPath() + "/Pictures";
        DeleteFiles.deleteFile(photoDir, 100);
        DeleteFiles.deleteFile(imgDir, 50);
        DeleteFiles.deleteFile(voiceDir, 100);

    }
}
