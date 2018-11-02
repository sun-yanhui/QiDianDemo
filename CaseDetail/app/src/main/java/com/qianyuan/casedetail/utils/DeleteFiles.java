package com.qianyuan.casedetail.utils;

import java.io.File;

/**
 * Created by sun on 2017/5/8.
 */

public class DeleteFiles {

    public static void deleteFile(String path,int num){
        File file=new File(path);
        File[] files = file.listFiles();
        if(file.exists()){
            if(files.length >num ){
                for (File  f: files) {
                    f.delete();
                }
            }
        }
    }

}
