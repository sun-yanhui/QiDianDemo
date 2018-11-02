package com.qianyuan.casedetail.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qianyuan.casedetail.R;
import com.qianyuan.casedetail.adapter.MyPagerAdapter;
import com.qianyuan.casedetail.bean.CaseDetailBean;
import com.qianyuan.casedetail.bean.Constant;
import com.qianyuan.casedetail.bean.MessageEvent;
import com.qianyuan.casedetail.bean.Url;
import com.qianyuan.casedetail.fragment.EndCaseDetailFragment;
import com.qianyuan.casedetail.fragment.HandleDetailFragment;
import com.qianyuan.casedetail.fragment.ReportInfoFragment;
import com.qianyuan.casedetail.utils.ActivityCollector;
import com.qianyuan.casedetail.utils.EventBusUtil;
import com.qianyuan.casedetail.utils.GsonUtil;
import com.qianyuan.casedetail.utils.LocalFileLayer;
import com.qianyuan.casedetail.utils.LogUtil;
import com.qianyuan.casedetail.utils.SharedPreferencesUtil;
import com.qianyuan.casedetail.utils.ThreadUtils;
import com.qianyuan.casedetail.utils.ToastUtil;
import com.qianyuan.casedetail.view.DisPoseDialog;
import com.qianyuan.casedetail.view.VerifyDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tb_case_detail)
    TabLayout tbCaseDetail;
    @BindView(R.id.vp)
    ViewPager viewPager;
    @BindView(R.id.iv_first)
    ImageView ivFirst;
    @BindView(R.id.btn_case_verify)
    RelativeLayout btnCaseVerify;
    @BindView(R.id.btn_case_supply)
    RelativeLayout btnCaseSupply;
    @BindView(R.id.textView42)
    TextView textView42;
    @BindView(R.id.textView43)
    TextView textView43;
    @BindView(R.id.btn_case_modify)
    RelativeLayout btnCaseModify;
    @BindView(R.id.btn_case_close)
    RelativeLayout btnCaseClose;
    @BindView(R.id.btn_case_dispose)
    RelativeLayout btnCaseDispose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSDInfo();
        ActivityCollector.addActivity(this);
        ButterKnife.bind(this);
        EventBusUtil.register(this);
        initView();
        initData();

        String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
        String userId = SharedPreferencesUtil.getString("userId", "userId", "");
        String caseId = SharedPreferencesUtil.getString("caseId", "caseId", "");
        final String url = projectUrl + Url.CaseVerify + "USERID=" + userId + "&" + "CASEID=" + caseId + Url.CheckTrue;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(MessageEvent event) {
        if (event.what == MessageEvent.CASEEVALUATEACTIVITY) {
            btnCaseClose.setVisibility(View.GONE);
            getViewPager();
        }
    }

    private void initView() {
        btnCaseClose.setVisibility(View.INVISIBLE);
        btnCaseVerify.setVisibility(View.INVISIBLE);
        btnCaseModify.setVisibility(View.INVISIBLE);
        setTitleText("案件详情");
    }

    private void initData() {
        getViewPager();
    }

    public void requestInfo() {
        final String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
        final String userId = SharedPreferencesUtil.getString("userId", "userId", "");
        final String caseId = SharedPreferencesUtil.getString("caseId", "caseId", "");
        final String url = projectUrl + Url.GetCaseInfo + "USERID=" + userId + "&CASEID=" + caseId;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!userId.equals("") && !caseId.equals("") && !projectUrl.equals("")) {
                    OkGo.post(url).tag(this).execute(new StringCallback() {
                        @Override
                        public void onSuccess(String result, Call call, Response response) {
                            if (result != null) {
                                try {
                                    JSONObject json = new JSONObject(result);
                                    if (json != null) {
                                        int code = (int) json.get("CODE");
                                        if (code == Constant.SUCCEED_USER_NO) {
                                            ToastUtil.showLongToast("用户未登录");
                                        } else if (code == Constant.SUCCEED_CODE) {
                                            CaseDetailBean caseDetailBean = GsonUtil.parseJsonToBean(result, CaseDetailBean.class);
                                            CaseDetailBean.DATABean data = caseDetailBean.getDATA();
                                            String state = data.getSTATE();
                                            int hsstate = data.getHSSTATE();
                                            int hcstate = data.getHCSTATE();
                                            int czstate = data.getCZSTATE();
                                            if (state.equals("上报") || state.equals("受理")) {
                                                btnCaseModify.setVisibility(View.VISIBLE);
                                                btnCaseVerify.setVisibility(View.GONE);
                                                btnCaseClose.setVisibility(View.GONE);
                                                btnCaseDispose.setVisibility(View.GONE);
                                            }
                                            if (state.equals("核实") && hsstate == 0) {
                                                btnCaseVerify.setVisibility(View.GONE);
                                                btnCaseModify.setVisibility(View.GONE);
                                                btnCaseClose.setVisibility(View.GONE);
                                                btnCaseDispose.setVisibility(View.GONE);
                                            }
                                            if (state.equals("结案")) {
                                                btnCaseVerify.setVisibility(View.GONE);
                                                btnCaseModify.setVisibility(View.GONE);
                                                btnCaseClose.setVisibility(View.GONE);
                                                btnCaseDispose.setVisibility(View.GONE);
                                            }

                                            if (state.equals("处置") || czstate==0) {
                                                btnCaseDispose.setVisibility(View.GONE);
                                                btnCaseVerify.setVisibility(View.GONE);
                                                btnCaseModify.setVisibility(View.GONE);
                                                btnCaseClose.setVisibility(View.GONE);
                                            }
                                            if (state.equals("核查") && hcstate == 0) {
                                                btnCaseVerify.setVisibility(View.GONE);
                                                btnCaseModify.setVisibility(View.GONE);
                                                btnCaseClose.setVisibility(View.GONE);
                                                btnCaseDispose.setVisibility(View.GONE);
                                            }
                                            if (state.equals("核查") && hcstate == 1) {
                                                btnCaseDispose.setVisibility(View.GONE);
                                                btnCaseVerify.setVisibility(View.GONE);
                                                btnCaseModify.setVisibility(View.GONE);
                                                btnCaseClose.setVisibility(View.VISIBLE);
                                                btnCaseClose.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        gotoActivity(CaseEvaluateActivity.class);
                                                    }
                                                });
                                            }

                                            if (state.equals("核实") && hsstate == 1) {
                                                btnCaseVerify.setVisibility(View.VISIBLE);
                                                btnCaseClose.setVisibility(View.GONE);
                                                btnCaseModify.setVisibility(View.GONE);
                                                btnCaseDispose.setVisibility(View.GONE);
                                                //可以核实
                                                btnCaseVerify.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        //核实
                                                        VerifyDialog dialog = new VerifyDialog(MainActivity.this) {
                                                            @Override
                                                            public void onTrueClick() {
                                                                String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
                                                                String userId = SharedPreferencesUtil.getString("userId", "userId", "");
                                                                String caseId = SharedPreferencesUtil.getString("caseId", "caseId", "");
                                                                final String url = projectUrl + Url.CaseVerify + "USERID=" + userId + "&" + "CASEID=" + caseId + Url.CheckTrue;
                                                                OkGo.post(url).execute(new StringCallback() {
                                                                    @Override
                                                                    public void onSuccess(String s, Call call, Response response) {
                                                                        ToastUtil.showShortToast("核实成功");
                                                                        EventBusUtil.post(new MessageEvent(MessageEvent.CASEVERIFYDIALOG));
                                                                        btnCaseModify.setVisibility(View.GONE);
                                                                        btnCaseVerify.setVisibility(View.GONE);
                                                                        dismiss();
                                                                    }
                                                                });
                                                            }

                                                            @Override
                                                            public void onFalseClick() {
                                                                String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
                                                                String userId = SharedPreferencesUtil.getString("userId", "userId", "");
                                                                String caseId = SharedPreferencesUtil.getString("caseId", "caseId", "");
                                                                final String url2 = projectUrl + Url.CaseVerify + "USERID=" + userId + "&" + "CASEID=" + caseId + Url.CheckFalse;
                                                                OkGo.post(url2).execute(new StringCallback() {
                                                                    @Override
                                                                    public void onSuccess(String s, Call call, Response response) {
                                                                        ToastUtil.showShortToast("核实成功");
                                                                        EventBusUtil.post(new MessageEvent(MessageEvent.CASEVERIFYDIALOG));
                                                                        btnCaseModify.setVisibility(View.GONE);
                                                                        btnCaseVerify.setVisibility(View.GONE);
                                                                        dismiss();
                                                                    }
                                                                });
                                                            }
                                                        };
                                                        dialog.show();
                                                    }
                                                });
                                            }
                                            if (state.equals("处置") || czstate==1) {
                                                btnCaseDispose.setVisibility(View.VISIBLE);
                                                btnCaseVerify.setVisibility(View.GONE);
                                                btnCaseModify.setVisibility(View.GONE);
                                                btnCaseClose.setVisibility(View.GONE);
                                                btnCaseDispose.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        DisPoseDialog dialog=new DisPoseDialog(MainActivity.this) {
                                                            @Override
                                                            public void onTrueClick() {


                                                            }

                                                            @Override
                                                            public void onFalseClick() {


                                                            }
                                                        };
                                                    }
                                                });
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    });

                }
            }
        };
        ThreadUtils.runOnSubThread(runnable);
    }


    private void getViewPager() {
        List<Fragment> list = new ArrayList<>();
        list.add(new ReportInfoFragment());
        list.add(new HandleDetailFragment());
        list.add(new EndCaseDetailFragment());
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);//默认选中第一个fragment
        tbCaseDetail.setupWithViewPager(viewPager);
    }


    @OnClick(R.id.btn_title_right)
    public void onMaterialDetailActivityClick() {
        gotoActivity(MaterialDetailActivity.class);
    }

    public void OnTitleBackClick(View v) {
        SharedPreferencesUtil.putBoolean("choose", "choose", false);
        finish();
        System.exit(0);
//        overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            SharedPreferencesUtil.putBoolean("choose", "choose", false);
            finish();
            System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.btn_case_close, R.id.btn_case_supply, R.id.btn_case_modify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_case_supply:
//                gotoActivity(SupplyMaterialActivity.class);
                break;
            case R.id.btn_case_modify:
                gotoActivity(CaseModifyActivity.class);
                break;
        }
    }

    public void getSDInfo() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                LocalFileLayer localFileLayer = new LocalFileLayer(Url.GetSDPath);
                final String projectUrl = localFileLayer.getFile(Url.UrlPath);
                final String userId = localFileLayer.getFile(Url.UserIdPath);
                final String address = localFileLayer.getFile(Url.AddressPath);
                final String caseId = localFileLayer.getFile(Url.CaseIdPath);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferencesUtil.putString("projectUrl", "projectUrl", projectUrl);
                        SharedPreferencesUtil.putString("userId", "userId", userId);
                        SharedPreferencesUtil.putString("address", "address", address);
                        SharedPreferencesUtil.putString("caseId", "caseId", caseId);
                        requestInfo();
                    }
                });
            }
        };
        ThreadUtils.runOnSubThread(runnable);
    }

    @Override
    protected void onDestroy() {
        EventBusUtil.unregister(this);
        super.onDestroy();
    }
}

