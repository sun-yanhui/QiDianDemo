package com.qianyuan.casedetail.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;


import com.qianyuan.casedetail.application.MyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Created by sun on 2017/2/28.
 */

public class SharedPreferencesUtil {

    /**
     * 向haredPreferences里边存储内容
     * 参数1：sharedPreferences的名字
     * 参数2：sharedPreferences的参数的名字
     * 参数2：sharedPreferences的参数的值
     **/
    public static void putLong(String name, String key, Long value) {
        Context context = MyApplication.getInstance();
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(key, value).commit();
    }

    public static void putString(String name, String key, String value) {
        Context sContext = MyApplication.getInstance().getApplicationContext();
        SharedPreferences sPreferences = sContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        sPreferences.edit().putString(key, value).commit();
    }

    public static void putBoolean(String name, String key, boolean value) {
        Context sContext = MyApplication.getInstance().getApplicationContext();
        SharedPreferences sPreferences = sContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        sPreferences.edit().putBoolean(key, value).commit();
    }

    public static void putInt(String name, String key, int value) {
        Context sContext = MyApplication.getInstance().getApplicationContext();
        SharedPreferences sPreferences = sContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        sPreferences.edit().putInt(key, value).commit();
    }

    /**
     * 获取sharedPreferences里边的内容
     * 参数1：sharedPreferences的名字
     * 参数2：sharedPreferences的参数的名字
     * 参数2：sharedPreferences的参数的值
     **/
    public static Long getLong(String name, String key, Long defValue) {
        Context sContext = MyApplication.getInstance().getApplicationContext();
        SharedPreferences sPreferences = sContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sPreferences.getLong(key, defValue);
    }

    public static String getString(String name, String key, String defValue) {
        Context sContext = MyApplication.getInstance().getApplicationContext();
        SharedPreferences sPreferences = sContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sPreferences.getString(key, defValue);
    }

    public static int getInt(String name, String key, int defValue) {
        Context sContext = MyApplication.getInstance().getApplicationContext();
        SharedPreferences sPreferences = sContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sPreferences.getInt(key, defValue);
    }

    public static boolean getBoolean(String name, String key, boolean defValue) {
        Context sContext = MyApplication.getInstance().getApplicationContext();
        SharedPreferences sPreferences = sContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sPreferences.getBoolean(key, defValue);
    }

    public static void saveString(String name,String key,String value){
        Context sContext = MyApplication.getInstance().getApplicationContext();
        SharedPreferences sPreferences = sContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        sPreferences.edit().putString(key,value).commit();
    }
    /**
     * 保存序列化过的对象
     * @param key
     * @param obj
     */
    public static void saveObj(String name,String key,Object obj){
        if(obj==null)return;
        if(!(obj instanceof Serializable)){
            throw new IllegalArgumentException("The object should implements Serializable!");
        }

        //1.write obj to bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);

            //2.convert obj to string via Base64
            byte[] bytes = Base64.encode(baos.toByteArray(), Base64.DEFAULT);

            //3.save string
            saveString(name,key,new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取序列化过的对象
     * @param key
     * @return
     */
    public static Object getObj(String name,String key){
        Context sContext = MyApplication.getInstance().getApplicationContext();
        SharedPreferences sPreferences = sContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        //1.get string
        String string = sPreferences.getString(key, null);

        if(TextUtils.isEmpty(string))return null;

        //2.decode string
        byte[] bytes = Base64.decode(string, Base64.DEFAULT);

        //3.read bytes to Object
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



}
