package com.qianyuan.casedetail.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.qianyuan.casedetail.R;
import com.qianyuan.casedetail.application.MyApplication;
import com.qianyuan.casedetail.bean.CaseType;
import com.qianyuan.casedetail.bean.Constant;
import com.qianyuan.casedetail.bean.Url;
import com.qianyuan.casedetail.utils.GsonUtil;
import com.qianyuan.casedetail.utils.LocalFileLayer;
import com.qianyuan.casedetail.utils.LogUtil;
import com.qianyuan.casedetail.utils.ScreenUtil;
import com.qianyuan.casedetail.utils.SharedPreferencesUtil;
import com.qianyuan.casedetail.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sun on 2017/5/19.
 */

public class CategoryDialog extends BasePopupDialog implements AdapterView.OnItemSelectedListener {

    public Context context;
    public OnChangeClick onChangeClick;
    public Spinner mSpinner;
    public Spinner mSpinner2;
    public Spinner mSpinner3;
    public Spinner mSpinner4;
    private Button mCancelBtn;
    private Button mConfirmBtn;

    List<String> firstList = new ArrayList<>();
    List<String> secondList = new ArrayList<>();
    List<String> thirdList = new ArrayList<>();
    List<String> forthList = new ArrayList<>();
    public String firstChoosed;
    public String secondChoosed;
    public String thirdChoosed;
    public String forthChoosed;
    public String classifyList;


    public CategoryDialog(Context context, OnChangeClick onChangeClick) {
        super(context);
        this.mContext = context;
        this.onChangeClick = onChangeClick;
    }



    @Override
    public int getViewId() {
        return R.layout.dialog_category;
    }

    @Override
    public void findViews() {
        mCancelBtn = (Button) findViewById(R.id.btn_dialog_cancel);
        mConfirmBtn = (Button) findViewById(R.id.btn_dialog_confirm);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner2 = (Spinner) findViewById(R.id.spinner2);
        mSpinner3 = (Spinner) findViewById(R.id.spinner3);
        mSpinner4 = (Spinner) findViewById(R.id.spinner4);


    }

    @Override
    public void setDialogStyle() {
        setDialogSize();
    }

    @Override
    public void setListener() {
        mCancelBtn.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        mSpinner.setOnItemSelectedListener(this);
        mSpinner2.setOnItemSelectedListener(this);
        mSpinner3.setOnItemSelectedListener(this);
        mSpinner4.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        TextView tv=(TextView)view;
        tv.setTextColor(Color.WHITE);
        if (parent.getId() == R.id.spinner) {
            firstChoosed = mSpinner.getSelectedItem().toString();
            secondList.clear();
            thirdList.clear();
            forthList.clear();
            getCaseType(2, firstChoosed, "", "");
        } else if (parent.getId() == R.id.spinner2) {
            secondChoosed = mSpinner2.getSelectedItem().toString();
            thirdList.clear();
            forthList.clear();
            getCaseType(3, firstChoosed, secondChoosed, "");
        } else if (parent.getId() == R.id.spinner3) {
            thirdChoosed = mSpinner3.getSelectedItem().toString();
            forthList.clear();
            getCaseType(4, firstChoosed, secondChoosed, thirdChoosed);
        } else if (parent.getId() == R.id.spinner4) {
            forthChoosed = mSpinner4.getSelectedItem().toString();

        }
    }

    @Override
    public void initData() {
        getCaseType(1, "", "", "");
    }

    public void getCaseType(final int level, final String firstChoosed, final String secondChoosed, final String thirdChoosed) {
        //最开始设置为false
        LocalFileLayer localFileLayer = new LocalFileLayer(Url.GetSDPath);
        String file = localFileLayer.getFile("案件类别.txt");
        CaseType caseType5 = GsonUtil.parseJsonToBean(file, CaseType.class);
        List<CaseType.DATABean> dataList = caseType5.getDATA();
        switch (level) {
            case 1:
                for (int i = 0; i < dataList.size(); i++) {
                    String type = dataList.get(i).getTYPE();
                    if (firstList.contains(type)) {
                        continue;
                    } else {
                        firstList.add(type);
                    }
                }
                initSpinnerData(mSpinner, firstList);
                break;
            case 2:
                for (int i = 0; i < dataList.size(); i++) {
                    String type = dataList.get(i).getBNAME();
                    if (dataList.get(i).getTYPE().equals(firstChoosed)) {
                        if (secondList.contains(type)) {
                            continue;
                        } else {
                            secondList.add(type);
                        }
                    }
                }
                initSpinnerData(mSpinner2, secondList);
                break;
            case 3:
                for (int i = 0; i < dataList.size(); i++) {
                    String type = dataList.get(i).getSNAME();
                    if (dataList.get(i).getBNAME().equals(secondChoosed) && dataList.get(i).getTYPE().equals(firstChoosed)) {
                        if (thirdList.contains(type)) {
                            continue;
                        } else {
                            thirdList.add(type);
                        }
                    }
                }
                initSpinnerData(mSpinner3, thirdList);
                break;
            case 4:
                for (int i = 0; i < dataList.size(); i++) {
                    String type = dataList.get(i).getSCONDITION();
                    if (dataList.get(i).getSNAME().equals(thirdChoosed) && dataList.get(i).getBNAME().equals(secondChoosed) && dataList.get(i).getTYPE().equals(firstChoosed)) {
                        if (forthList.contains(type)) {
                            continue;
                        } else {
                            forthList.add(type);
                        }
                    }
                }
                initSpinnerData(mSpinner4, forthList);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_cancel:
                dismiss();

                break;
            case R.id.btn_dialog_confirm:
                StringBuffer sb = new StringBuffer();
                String separator = "-";
                sb.append(firstChoosed).append(separator).append(secondChoosed).append(separator).append(thirdChoosed).append(separator).append(forthChoosed);
                classifyList=sb.toString();

                dismiss();
                onChangeClick.onChange();
                break;
        }
    }

    public interface OnChangeClick {
        void onChange();
    }

    /**
     * 修改 框体大小
     */
    public void setDialogSize() {
        setCanceledOnTouchOutside(false);
        Window win = this.getWindow();
        win.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        //win.setWindowAnimations(R.style.BottomDialogAnim); // 添加动画
        int width = ScreenUtil.screenWidth(MyApplication.getInstance());
        WindowManager.LayoutParams params = win.getAttributes();
        params.width = width;
        this.getWindow().setAttributes(params);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
