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
 * Created by Che on 2016/8/17.
 */
public abstract class VerifyDialog extends BasePopupDialog {
    private Button trueBtn;
    private Button falseBtn;

    public VerifyDialog(Context context) {
        super(context);
    }

    @Override
    public int getViewId() {
        return R.layout.dialog_verify;
    }

    @Override
    public void findViews() {
        trueBtn = (Button) findViewById(R.id.btn_true);
        falseBtn = (Button) findViewById(R.id.btn_false);
    }

    @Override
    public void setListener() {
        trueBtn.setOnClickListener(this);
        falseBtn.setOnClickListener(this);
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
        if (v == trueBtn){
            onTrueClick();
        }else if (v == falseBtn){
            onFalseClick();
        }
    }

    public abstract void onTrueClick();
    public abstract void onFalseClick();

}
