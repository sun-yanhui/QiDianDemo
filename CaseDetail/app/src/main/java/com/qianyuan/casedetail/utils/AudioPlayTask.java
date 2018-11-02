package com.qianyuan.casedetail.utils;

import android.content.Context;
import android.os.Handler;

/**
 * Created by book on 10/14/16.
 */

public class AudioPlayTask implements Runnable {
    private Context context;
    private String url;
    private Handler handler;

    public AudioPlayTask(Context context, String url, Handler handler) {
        this.context = context;
        this.url = url;
        this.handler = handler;
    }

    @Override
    public void run() {
        AudioPlayer.play1(context, url,handler);
    }
}
