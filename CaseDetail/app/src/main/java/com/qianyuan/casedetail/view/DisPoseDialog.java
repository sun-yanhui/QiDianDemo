package com.qianyuan.casedetail.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.qianyuan.casedetail.R;
import com.qianyuan.casedetail.application.MyApplication;
import com.qianyuan.casedetail.utils.ScreenUtil;

/**
 * Created by sun on 2017/11/20.
 */

public abstract class DisPoseDialog extends BasePopupDialog {

    private Button btnTrueDispose;
    private Button btnFalseDispose;

    protected DisPoseDialog(Context context) {
        super(context);
    }

    @Override
    public int getViewId() {
        return R.layout.dialog_dispose;
    }

    @Override
    public void findViews() {
        btnTrueDispose = (Button) findViewById(R.id.btn_true_dispose);
        btnFalseDispose = (Button) findViewById(R.id.btn_false_dispose);
    }

    @Override
    public void setListener() {
        btnFalseDispose.setOnClickListener(this);
        btnTrueDispose.setOnClickListener(this);
    }

    @Override
    public void setDialogStyle() {
        setCanceledOnTouchOutside(true);
        Window win = this.getWindow();
        win.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        //win.setWindowAnimations(R.style.BottomDialogAnim); // 添加动画
        int width = ScreenUtil.screenWidth(MyApplication.getInstance());
        WindowManager.LayoutParams params = win.getAttributes();
        params.width = width;
        this.getWindow().setAttributes(params);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v == btnTrueDispose){
            onTrueClick();
        }else if (v == btnFalseDispose){
            onFalseClick();
        }
    }
    public abstract void onTrueClick();
    public abstract void onFalseClick();
}
