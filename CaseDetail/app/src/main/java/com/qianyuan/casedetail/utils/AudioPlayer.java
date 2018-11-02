package com.qianyuan.casedetail.utils;

/**
 * Created by sun on 2017/5/8.
 */

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import java.io.IOException;

public class AudioPlayer {
    public static final int HANDLER_MESSAGE_ARG1 = 1;
    public static final String TAG = "AudioPlayer";
    public static MediaPlayer mediaPlayer;
    public static MediaRecorder mediaRecorder;

    public static void play(Context context, String url) {
        mediaPlayer = MediaPlayer.create(context, Uri.parse(url));
        if (mediaPlayer != null) {
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        }
    }

    public static void play1(final Context context, String url, final Handler handler) {
        mediaPlayer = MediaPlayer.create(context, Uri.parse(url));
        if (mediaPlayer != null) {
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    mp.start();
                    Log.i("AudioPlayer", "onPrepared");
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Message message = Message.obtain();
                    message.arg1 = HANDLER_MESSAGE_ARG1;
                    if (context != null)
                        handler.sendMessage(message);
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    ToastUtil.showLongToast("获取音频失败");
                    return false;
                }
            });
        }
    }

    public static void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public static void stopPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public static void record(String FileName) {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(FileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
        mediaRecorder.start();
    }

    public static void stopRecord() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }
}
