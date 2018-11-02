package com.qianyuan.casedetail.fragment;

import android.view.View;
import android.widget.ListView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qianyuan.casedetail.R;
import com.qianyuan.casedetail.adapter.HandleDetailListAdapter;
import com.qianyuan.casedetail.bean.Constant;
import com.qianyuan.casedetail.bean.HandleDetailBean;
import com.qianyuan.casedetail.bean.MessageEvent;
import com.qianyuan.casedetail.bean.Url;
import com.qianyuan.casedetail.utils.EventBusUtil;
import com.qianyuan.casedetail.utils.GsonUtil;
import com.qianyuan.casedetail.utils.LogUtil;
import com.qianyuan.casedetail.utils.SharedPreferencesUtil;
import com.qianyuan.casedetail.utils.ThreadUtils;
import com.qianyuan.casedetail.utils.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sun on 2017/4/24.
 */

public class HandleDetailFragment extends BaseFragment {
    private ListView lvDetail;
    private List<HandleDetailBean.DATABean> data;

    @Override
    protected void initData() {
        EventBusUtil.register(this);
        requestInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(MessageEvent event) {
        if (event.what == MessageEvent.CASEVERIFYDIALOG) {
            //刷新
            requestInfo();
        }
    }

    private void requestInfo() {
        String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
        String userId = SharedPreferencesUtil.getString("userId", "userId", "");
        String caseId = SharedPreferencesUtil.getString("caseId", "caseId", "");
        final String url = projectUrl + Url.GetCaseList + "USERID=" + userId + "&CASEID=" + caseId;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                OkGo.post(url).tag(this).execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        HandleDetailBean handleDetailBean = GsonUtil.parseJsonToBean(result, HandleDetailBean.class);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int code = (int) jsonObject.get("CODE");
                            if (code == Constant.SUCCEED_USER_NO) {
                                ToastUtil.showLongToast("用户未登录");
                            }
                            if (code == Constant.SUCCEED_CODE) {
                                data = handleDetailBean.getDATA();
                                HandleDetailListAdapter adapter = new HandleDetailListAdapter(activity, data);
                                lvDetail.setAdapter(adapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        ThreadUtils.runOnSubThread(runnable);
    }

    @Override
    protected void initView(View view) {
        lvDetail = (ListView) view.findViewById(R.id.lv_detail);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_handle_detail;
    }

    @Override
    public void onDestroy() {
        EventBusUtil.unregister(this);
        super.onDestroy();

    }
}
