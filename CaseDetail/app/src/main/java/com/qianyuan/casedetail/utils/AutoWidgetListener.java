package com.qianyuan.casedetail.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by book on 10/14/16.
 */

public class AutoWidgetListener {
    public class AutoPlayClickListener implements View.OnClickListener {
        private String audioUrl;
        private ImageView mPlayIvGif;
        private ImageView mPlayIvNoGif;
        private Context context;
        private Handler handler;

        public AutoPlayClickListener(String audioUrl, ImageView mPlayIvGif, ImageView mPlayIvNoGif, Context context, Handler handler) {
            this.audioUrl = audioUrl;
            this.mPlayIvGif = mPlayIvGif;
            this.mPlayIvNoGif = mPlayIvNoGif;
            this.context = context;
            this.handler = handler;
        }

        @Override
        public void onClick(View view) {
            Log.i("AutoPlayClickListener", "onClick");
            Log.i("getVisibility",  mPlayIvNoGif.getVisibility()+"");
            if (audioUrl != null && mPlayIvNoGif.getVisibility() == View.VISIBLE) {
                mPlayIvNoGif.setVisibility(View.GONE);
                Log.i("mPlayIvNoGif",mPlayIvNoGif.toString());
                mPlayIvGif.setVisibility(View.VISIBLE);
                try {
                    Thread audioPlayThread = new Thread(new AudioPlayTask(context, audioUrl, handler));
                    audioPlayThread.start();
                } catch (Exception e) {
                }
            }
        }
    }
}
