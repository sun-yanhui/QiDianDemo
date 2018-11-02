package com.qianyuan.casedetail.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Che on 16/8/1.
 */
public class DialogUtil {

    public static void simpleDialog(Context context, String title, String message,
                                    String positiveText, String negativeText,
                                    DialogInterface.OnClickListener posListener,
                                    DialogInterface.OnClickListener negListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton(positiveText, posListener)
                .setNegativeButton(negativeText, negListener).create().show();

    }

    public static void simpleDialog(Context context, int title, int message,
                                    int positiveText, int negativeText,
                                    DialogInterface.OnClickListener posListener,
                                    DialogInterface.OnClickListener negListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton(positiveText, posListener)
                .setNegativeButton(negativeText, negListener).create().show();

    }
}
