package com.qianyuan.casedetail.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Che on 2016/8/18.
 */
public class ImageUtil {

    /**
     * Bitmap to Byte[]
     *
     * @param bm
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 85, bas);
        return bas.toByteArray();
    }

    /**
     * Get bitmap by file path.
     *
     * @param path
     * @return
     */
    public static Bitmap getBitmapFromFile(String path) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(fis);
    }
}
