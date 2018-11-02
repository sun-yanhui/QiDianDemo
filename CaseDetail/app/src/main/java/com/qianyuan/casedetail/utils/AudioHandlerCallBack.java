package com.qianyuan.casedetail.utils;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by book on 10/14/16.
 */

public class AudioHandlerCallBack implements Handler.Callback {

    private ImageView mPlayIvGif;
    private ImageView mPlayIvNoGif;

    public AudioHandlerCallBack(ImageView mPlayIvGif, ImageView mPlayIvNoGif) {
        this.mPlayIvGif = mPlayIvGif;
        this.mPlayIvNoGif = mPlayIvNoGif;
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.arg1) {
            case AudioPlayer.HANDLER_MESSAGE_ARG1:
                mPlayIvNoGif.setVisibility(View.VISIBLE);
                mPlayIvGif.setVisibility(View.GONE);
                break;
        }
        return false;
    }
}