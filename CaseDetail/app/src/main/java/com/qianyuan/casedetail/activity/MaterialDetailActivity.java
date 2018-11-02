package com.qianyuan.casedetail.activity;

import android.os.Bundle;

import com.qianyuan.casedetail.R;

/**
 * Created by sun on 2017/4/21.
 */

public class MaterialDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_material_detail);
        initView();
        initData();
    }

    private void initView() {
        setTitleText("已补充材料详情");
    }

    private void initData() {
    }
}
