package com.qianyuan.casedetail.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianyuan.casedetail.R;

/**
 * Created by sun on 2017/2/28.
 */
public abstract class BaseFragment extends Fragment {
    protected Activity activity;
    protected Context mContext;
    protected ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        this.activity =getActivity();
        mContext=getActivity();
        initView(view);
        initData();
        return view;
    }

    protected abstract void initData();


    protected abstract void initView(View view);

    protected abstract int getLayoutId();

    protected void goToActivityFragment(Class cls) {
        Intent intent = new Intent(activity, cls);
        startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    protected void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    protected void showProgressDialog(int text) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(text));
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
