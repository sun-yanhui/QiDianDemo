package com.qianyuan.casedetail.bean;

import android.os.Environment;

/**
 * Created by sun on 2017/5/19.
 */

public class Url {

    public static String GetSDPath = "/gridMember/file/";
    public static String UrlPath = "url.txt";
    public static String UserIdPath = "UserId.txt";
    public static String AddressPath = "地址信息.txt";
    public static String CaseIdPath = "caseId.txt";
    public static String ModifyCase = "/Higo-View/App.UpdateCaseInfo?";
    public static String EndCase = "/Higo-View/App.EndCase?";
    public static String GetEndCaseinfo = "/Higo-View/App.GetEndCaseInfo?";
    public static String GetCaseList = "/Higo-View/App.GetCaseDealList?";
    public static String GetCaseInfo = "Higo-View/App.GetCaseInfo?";
    public static String CaseVerify = "Higo-View/App.ConfirmCheck?";//核实
    public static String CheckFalse = "&EVENTCHECK=false";//核实-虚假案件
    public static String CheckTrue = "&EVENTCHECK=true";//核实-真实案件
    public static String SaveVoicePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gridmember/voice/";
    public static String UploadFile = "/Higo-View/App.FileUploadSB?";
    public static String UpLoadvVrifyFile = "/Higo-View/App.FileUploadJA?";
    public static String DialogClassfy = "/Higo-View/App.GetCaseType?";
    public static String SaveCaseImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gridmember/uploadImg/";
    public static String SaveImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/";
}
