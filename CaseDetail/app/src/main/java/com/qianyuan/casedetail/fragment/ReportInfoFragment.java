package com.qianyuan.casedetail.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qianyuan.casedetail.R;
import com.qianyuan.casedetail.bean.CaseDetailBean;
import com.qianyuan.casedetail.bean.Constant;
import com.qianyuan.casedetail.bean.MessageEvent;
import com.qianyuan.casedetail.bean.Url;
import com.qianyuan.casedetail.utils.AudioHandlerCallBack;
import com.qianyuan.casedetail.utils.AutoWidgetListener;
import com.qianyuan.casedetail.utils.DownLoadImgUtil;
import com.qianyuan.casedetail.utils.EventBusUtil;
import com.qianyuan.casedetail.utils.GsonUtil;
import com.qianyuan.casedetail.utils.LogUtil;
import com.qianyuan.casedetail.utils.PictureUtil;
import com.qianyuan.casedetail.utils.ScreenUtil;
import com.qianyuan.casedetail.utils.SharedPreferencesUtil;
import com.qianyuan.casedetail.utils.ThreadUtils;
import com.qianyuan.casedetail.utils.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sun on 2017/4/24.
 */

public class ReportInfoFragment extends BaseFragment {
    private TextView tvReportTime;
    private TextView tvReportDescribe;
    private TextView tvReportCategory;
    private LinearLayout llPic;
    private TextView tvLocation;
    private TextView tvDegree;
    private TextView tvDuration;
    private TextView tvState;
    private ImageView ivFirst;
    private RelativeLayout ivPlay;
    private ImageView ivPlayGif;
    private ImageView ivPlayNoGif;
    private boolean isNoGif = true;
    private View viewLine1;
    private View viewLine2;

    @Override
    protected void initData() {
        EventBusUtil.register(this);
        requestNet();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(MessageEvent event) {
        if (event.getWhat() == MessageEvent.CaseModifyActivity) {
            showImages("", "");
            requestNet();
        }
    }

    private void requestNet() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
                final String userId = SharedPreferencesUtil.getString("userId", "userId", "");
                final String caseId = SharedPreferencesUtil.getString("caseId", "caseId", "");
                final String url = projectUrl + Url.GetCaseInfo + "USERID=" + userId + "&CASEID=" + caseId;
                OkGo.post(url).tag(this).execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int code = (int) jsonObject.get("CODE");
                            if (code == Constant.SUCCEED_USER_NO) {
                                ToastUtil.showLongToast("用户未登录");
                            }
                            if (code == Constant.SUCCEED_CODE) {
                                CaseDetailBean caseDetailBean = GsonUtil.parseJsonToBean(result, CaseDetailBean.class);
                                CaseDetailBean.DATABean data = caseDetailBean.getDATA();
                                writeView(data);
                                getData(data);
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

    private void writeView(CaseDetailBean.DATABean data) {
        tvLocation.setText(data.getUPLOADPOSITION());
        if (data.getEMERGENCYSTATE() != null) {
            tvDegree.setText("【" + data.getEMERGENCYSTATE() + "】");
        } else {
            tvDegree.setText("【" + "  " + "】");
        }
        tvDuration.setText(data.getCASETIME());
        tvState.setText(data.getSTATE());
        tvReportTime.setText(data.getUPLOADTIME());
        tvReportDescribe.setText(data.getWORDDETAIL());
        tvReportCategory.setText(data.getCASETYPE());
        viewLine1.setVisibility(View.VISIBLE);
        viewLine2.setVisibility(View.VISIBLE);
        List<CaseDetailBean.DATABean.PICTUREIDBean> pictureidList = data.getPICTUREID();
        String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
        String proUrlButton1 = projectUrl.substring(0, projectUrl.lastIndexOf("/"));
        String proUrlButton = projectUrl.substring(0, proUrlButton1.lastIndexOf("/"));
        String getPic = proUrlButton;
        if (pictureidList != null && pictureidList.size() > 0) {
            for (int i = 0; i < pictureidList.size(); i++) {
                String url = getPic + pictureidList.get(i).getURL();
                String id = pictureidList.get(i).getID();
                showImages(url, id);
            }
            Glide.with(getActivity()).load(getPic + pictureidList.get(0).getURL()).into(ivFirst);//上边小图标设置为第一个图片
        }
        if (pictureidList != null && pictureidList.size() > 0) {
            //下载图片
            File fileFolder = new File(Url.SaveCaseImagePath);
            if (!fileFolder.exists()) {
                fileFolder.mkdirs();// 创建文件夹路径
            }
            for (int i = 0; i < pictureidList.size(); i++) {
                String path = proUrlButton + pictureidList.get(i).getURL();
                String imgId = pictureidList.get(i).getID();
                File file = new File(Url.SaveCaseImagePath + imgId + ".png");
                //保存图片名称
                if (file.exists()) {
                    continue;
                } else {
                    DownLoadImgUtil.downLoadImg(path, imgId + ".png", "/gridmember/uploadImg/");
                }
            }
        }
    }

    @Override
    protected void initView(View view) {
        tvLocation = (TextView) getActivity().findViewById(R.id.tv_location);
        tvDegree = (TextView) getActivity().findViewById(R.id.tv_degree);
        tvDuration = (TextView) getActivity().findViewById(R.id.tv_duration);
        tvState = (TextView) getActivity().findViewById(R.id.tv_state);
        tvReportTime = (TextView) view.findViewById(R.id.tv_report_time);
        tvReportDescribe = (TextView) view.findViewById(R.id.tv_report_describe);
        tvReportCategory = (TextView) view.findViewById(R.id.tv_report_category);
        viewLine1 = view.findViewById(R.id.view_line1);
        viewLine2 = view.findViewById(R.id.view_line2);
        llPic = (LinearLayout) view.findViewById(R.id.ll_picture);
        ivFirst = (ImageView) getActivity().findViewById(R.id.iv_first);
        ivPlay = (RelativeLayout) view.findViewById(R.id.iv_play);
        ivPlayGif = (ImageView) view.findViewById(R.id.iv_play_gif);
        ivPlayNoGif = (ImageView) view.findViewById(R.id.iv_play_no_gif);
    }

    private void showImages(final String picUrl, String imgId) {
        if (picUrl.equals("") && imgId.equals("")) {
            llPic.removeAllViews();
        } else {
            final String path = "file:///" + Url.SaveCaseImagePath + imgId + ".png";
            final String path2 = Url.SaveCaseImagePath + imgId + ".png";
            final ImageView picIv = new ImageView(mContext);
            int picWidth = ScreenUtil.dip2px(mContext, 120);
            int picHeight = ScreenUtil.dip2px(mContext, 120);
            int margin = ScreenUtil.dip2px(mContext, 10);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(picWidth, picHeight);
            lp.setMargins(margin, 0, 0, 0);
            picIv.setLayoutParams(lp);
            picIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(activity).load(picUrl).into(picIv);
            final File file = new File(path2);
            picIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (file.exists()) {
                        PictureUtil.zoomInPicture(activity, picIv, path);
                    }
                }
            });
            llPic.addView(picIv);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_info;
    }

    public void getData(CaseDetailBean.DATABean data) {
        String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
        String proUrlButton1 = projectUrl.substring(0, projectUrl.lastIndexOf("/"));
        String proUrlButton = projectUrl.substring(0, proUrlButton1.lastIndexOf("/"));
        final String getPic = proUrlButton;
        final List<CaseDetailBean.DATABean.AUDIOIDBean> audioidList = data.getAUDIOID();
        if (audioidList != null && audioidList.size() > 0) {
            String url = getPic + audioidList.get(0).getURL();
            ivPlayNoGif.setVisibility(View.VISIBLE);
            Handler handler = new Handler(new AudioHandlerCallBack(ivPlayGif, ivPlayNoGif));
            ivPlay.setOnClickListener(new AutoWidgetListener().new AutoPlayClickListener(url, ivPlayGif, ivPlayNoGif, getActivity(), handler));
        } else {
            ivPlay.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }
}
