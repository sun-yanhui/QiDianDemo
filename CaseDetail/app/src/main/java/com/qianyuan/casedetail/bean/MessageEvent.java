package com.qianyuan.casedetail.bean;

/**
 * Created by sun on 2017/3/7.
 */

public class MessageEvent {
    public static final int CASEDETAIL=0;
    public static final int CaseModifyActivity=1;
    public static final int CASEEVALUATEACTIVITY=2;
    public static final int CASEVERIFYDIALOG=3;
    public static final int CASEVERIFYDIALOGSECOND=4;

    public int what;
    public Object message;
    public MessageEvent(int what) {
        super();
        this.what = what;
    }

    public MessageEvent(int what, Object message) {
        super();
        this.what = what;
        this.message = message;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

}
