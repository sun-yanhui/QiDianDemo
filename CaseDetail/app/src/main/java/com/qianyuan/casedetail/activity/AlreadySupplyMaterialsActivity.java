package com.qianyuan.casedetail.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qianyuan.casedetail.R;


/**
 * Created by sun on 2017/4/21.
 */

public class AlreadySupplyMaterialsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_material_detail);
        initView();
    }

    private void initView() {
        setTitleText("已补充材料");
    }
}
