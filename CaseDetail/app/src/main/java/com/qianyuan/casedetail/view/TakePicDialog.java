package com.qianyuan.casedetail.view;

/**
 * Created by sun on 2017/5/8.
 */
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.qianyuan.casedetail.R;
import com.qianyuan.casedetail.application.MyApplication;
import com.qianyuan.casedetail.utils.ScreenUtil;


public abstract class TakePicDialog extends BasePopupDialog {
    public static final String TAG = "TakePicDialog";
    private TextView mTakePicTv;
    private TextView mChoosePicTv;

    protected TakePicDialog(Context context) {
        super(context);
    }

    @Override
    public int getViewId() {
        return R.layout.dialog_take_pic;
    }

    @Override
    public void findViews() {
        mTakePicTv = (TextView) findViewById(R.id.tv_take_pic);
        mChoosePicTv = (TextView) findViewById(R.id.tv_choose_pic);
    }

    @Override
    public void setListener() {
        mTakePicTv.setOnClickListener(this);
        mChoosePicTv.setOnClickListener(this);
    }

    @Override
    public void setDialogStyle() {
        setCanceledOnTouchOutside(true);
        Window win = this.getWindow();
        win.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        win.setWindowAnimations(R.style.BottomDialogAnim); // 添加动画
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
        if (v == mTakePicTv) {
            takePic();
        } else if (v == mChoosePicTv) {
            choosePic();
        }
    }

    public abstract void takePic();

    public abstract void choosePic();
}
