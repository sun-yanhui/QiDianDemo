package com.qianyuan.casedetail.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by sun on 2017/3/29.
 */

public class LocalFileLayer {
    private String saveDir;

    public LocalFileLayer(String folderName) {
        // 使用sd作保存路径+"/gridMember/img/"
        saveDir = Environment.getExternalStorageDirectory().getAbsolutePath()  + folderName;
        File file = new File(saveDir);
        if (!file.exists()) {
            file.mkdirs();// 创建路径
        }
    }

    /***
     * 方法
     * @param item:图片名称
     * @return
     */
    public Bitmap getBitmap(String item) {
        return BitmapFactory.decodeFile(saveDir + item);
    }

    /***
     * 方法
     *
     * @param item
     * @param bitmap
     */
    public void putBitmap(String item, Bitmap bitmap) {
        try {
            // Android底层的文件存储是不支持: //等字符，所以可以将这些字符编码。
            File file = new File(saveDir, item);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream out = new FileOutputStream(file);
            // compress将bitmap的像素存到文件输出流
            // 1.格式
            // 2.质量100
            // 3.文件输出流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void putFile(String info,String fileName) {
        //写入文件
        try {
            File file=new File(saveDir,fileName);
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(info.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getFile(String fileName) {
        try {
            //读取文件
            File file=new File(saveDir,fileName);
            FileInputStream fis=new FileInputStream(file);
            byte[] b=new byte[fis.available()];
            fis.read(b);
            String result=new String(b);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
