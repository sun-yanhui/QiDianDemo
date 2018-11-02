package com.qianyuan.casedetail.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.lzy.okgo.OkGo;
import com.qianyuan.casedetail.application.MyApplication;
import com.qianyuan.casedetail.bean.Url;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Response;

import static android.R.attr.id;

/**
 * Created by sun on 2017/4/26.
 */

public class DownLoadImgUtil {
    //获取网络图片并保存到sd
    //参一：url，参二：保存文件名称，参三：保存文件路径
    public static void downLoadImg(final String path, final String imageName, final String folderName) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // 使用httpUrlconnection
                try {
                    URL url = new URL(path);
                    // 根据 地址获取连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    InputStream is = conn.getInputStream();
                    // BitmapFactory是一个工具类decodeStream 将流转换成Bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    LocalFileLayer mLocalFileLayer = new LocalFileLayer(folderName);
                    // 缓存到二级
                    mLocalFileLayer.putBitmap(imageName, bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        ThreadUtils.runOnSubThread(runnable);
    }

    //参一：url，参二：保存文件路径名称
    public static void downLoadFile(final String url, final String path, final String fileName) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                OkGo.get(url).execute(new com.lzy.okgo.callback.FileCallback(path, fileName) {
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                    }
                });
            }
        };
        ThreadUtils.runOnSubThread(runnable);
    }
}
