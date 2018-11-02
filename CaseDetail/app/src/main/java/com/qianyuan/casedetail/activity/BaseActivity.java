package com.qianyuan.casedetail.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qianyuan.casedetail.R;
import com.qianyuan.casedetail.utils.ActivityCollector;

import java.util.Map;

/**
 * Created by sun on 2017/4/21.
 */

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    public void OnTitleBackClick(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    protected void setRightBtn(String text) {
        Button rightBtn = (Button) findViewById(R.id.btn_title_right);
        if (rightBtn != null) {
            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setText(text);
        }
    }

    protected void gotoActivity(Class cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    protected void gotoActivity(Class cls, Map<String, String> map) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        for (String key : map.keySet()) {
            intent.putExtra(key, map.get(key));
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    protected void setTitleText(String text) {
        TextView titleTv = (TextView) findViewById(R.id.tv_title);
        if (titleTv != null) {
            titleTv.setText(text);
        }
    }

    protected void setTitleText(int text) {
        TextView titleTv = (TextView) findViewById(R.id.tv_title);
        if (titleTv != null) {
            titleTv.setText(text);
        }
    }

    protected void setRightBtn(int text) {
        Button rightBtn = (Button) findViewById(R.id.btn_title_right);
        if (rightBtn != null) {
            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setText(text);
        }
    }

    protected void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    protected void showProgressDialog(int text) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(text));
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    protected void showProgressDialog(String text) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(text);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    protected void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


}
