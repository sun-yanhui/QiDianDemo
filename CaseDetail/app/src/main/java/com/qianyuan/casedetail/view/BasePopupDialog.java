package com.qianyuan.casedetail.view;

/**
 * Created by sun on 2017/5/8.
 */
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;
public abstract class BasePopupDialog extends AlertDialog implements View.OnClickListener{
    protected Context mContext;
    private ProgressDialog progressDialog;

    protected BasePopupDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        setDialogStyle();
        findViews();
        setListener();
        initData();

    }

    public abstract int getViewId();

    public abstract void findViews();

    public abstract void setListener();

    public abstract void setDialogStyle();

    public abstract void initData();

    public Spinner initSpinner(int id, String[] data) {
        List<String> list = null;
        Spinner spinner = (Spinner) findViewById(id);
        list = Arrays.asList(data);
        ArrayAdapter adapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
        return spinner;
    }

    public Spinner initSpinner(int id, List<String> data) {
        Spinner spinner = (Spinner) findViewById(id);
        ArrayAdapter adapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return spinner;
    }

    public void initSpinnerData(Spinner spinner, List<String> data){
        ArrayAdapter adapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
