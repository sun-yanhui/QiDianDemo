package com.qianyuan.casedetail.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lxj.okhttpengine.OkHttpEngine;
import com.lxj.okhttpengine.callback.OkHttpCallback;
import com.qianyuan.casedetail.R;
import com.qianyuan.casedetail.bean.CaseDetailBean;
import com.qianyuan.casedetail.bean.Constant;
import com.qianyuan.casedetail.bean.EndCaseBean;
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
import java.io.IOException;
import java.util.List;

/**
 * Created by sun on 2017/4/24.
 */

public class EndCaseDetailFragment extends BaseFragment {
    private TextView tvTime;
    private TextView tvQualified;
    private TextView tvCheck;
    private TextView tvNone;
    private View divider;
    private View divider2;
    private View divider3;
    private RelativeLayout ivPlay;
    private ImageView ivPlayGif;
    private ImageView ivPlayNoGif;
    private LinearLayout llPic;

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
        if (event.what == MessageEvent.CASEVERIFYDIALOGSECOND) {
            //刷新
            requestInfo();
        }
    }

    private void requestInfo() {
        String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
        String userId = SharedPreferencesUtil.getString("userId", "userId", "");
        String caseId = SharedPreferencesUtil.getString("caseId", "caseId", "");
        final String url = projectUrl + Url.GetEndCaseinfo + "USERID=" + userId + "&CASEID=" + caseId;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                OkHttpEngine.create().post(url, new OkHttpCallback<String>() {
                            @Override
                            public void onSuccess(String response) {
                                EndCaseBean endCaseBean = GsonUtil.parseJsonToBean(response, EndCaseBean.class);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int code = (int) jsonObject.get("CODE");//code=4,用户未登陆
                                    if (code == Constant.SUCCEED_DATA_NO) {//code=3,无数据
                                        tvTime.setVisibility(View.GONE);
                                        tvCheck.setVisibility(View.GONE);
                                        tvQualified.setVisibility(View.GONE);
                                        divider.setVisibility(View.GONE);
                                        divider2.setVisibility(View.GONE);
                                        tvNone.setVisibility(View.VISIBLE);
                                        return;
                                    }
                                    if (code == Constant.SUCCEED_USER_NO) {
                                        ToastUtil.showLongToast("用户未登录");
                                    }
                                    if (code == Constant.SUCCEED_CODE) {
                                        EndCaseBean.DATABean data = endCaseBean.getDATA();
                                        String datetime = data.getUPLOADTIME();
                                        String result = data.getRESULT();
                                        String memo = data.getMEMO();
                                        String state = data.getSTATE();
                                        List<EndCaseBean.DATABean.AUDIOIDBean> audioidList = data.getAUDIOID();
                                        List<EndCaseBean.DATABean.PICTUREIDBean> pictureList = data.getPICTUREID();
                                        String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
                                        String proUrlButton1 = projectUrl.substring(0, projectUrl.lastIndexOf("/"));
                                        String proUrlButton = projectUrl.substring(0, proUrlButton1.lastIndexOf("/"));

                                        if (state.equals("结案")) {
                                            tvTime.setText(datetime);
                                            tvCheck.setText(memo);
                                            tvQualified.setText(result);
                                            //音频
                                            if (audioidList != null && audioidList.size() > 0) {
                                                String url = proUrlButton + audioidList.get(0).getURL();
                                                ivPlayNoGif.setVisibility(View.VISIBLE);
                                                Handler handler = new Handler(new AudioHandlerCallBack(ivPlayGif, ivPlayNoGif));
                                                ivPlay.setOnClickListener(new AutoWidgetListener().new AutoPlayClickListener(url, ivPlayGif, ivPlayNoGif, getActivity(), handler));
                                            } else {
                                                ivPlay.setVisibility(View.GONE);
                                                divider3.setVisibility(View.GONE);
                                                divider2.setVisibility(View.GONE);
                                            }
                                        }
                                        //图片先下载
                                        if (pictureList != null && pictureList.size() > 0) {
                                            File fileFolder = new File(Url.SaveCaseImagePath);
                                            if (!fileFolder.exists()) {
                                                fileFolder.mkdirs();// 创建文件夹路径
                                            }

                                            if (pictureList != null && pictureList.size() > 0) {
                                                for (int i = 0; i < pictureList.size(); i++) {
                                                    String url = proUrlButton + pictureList.get(i).getURL();
                                                    String id = pictureList.get(i).getID();
                                                    showImages(url, id);
                                                }
                                            }
                                            for (int i = 0; i < pictureList.size(); i++) {
                                                String path = proUrlButton + pictureList.get(i).getURL();
                                                String imgId = pictureList.get(i).getID();
                                                File file = new File(Url.SaveCaseImagePath + imgId + ".png");
                                                //保存图片名称
                                                if (file.exists()) {
                                                    continue;
                                                } else {
                                                    DownLoadImgUtil.downLoadImg(path, imgId + ".png", "/gridmember/uploadImg/");
                                                }
                                            }
                                        }
                                    } else {
                                        tvTime.setVisibility(View.GONE);
                                        tvCheck.setVisibility(View.GONE);
                                        tvQualified.setVisibility(View.GONE);
                                        divider.setVisibility(View.GONE);
                                        divider2.setVisibility(View.GONE);
                                        divider3.setVisibility(View.GONE);
                                        tvNone.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(IOException e) {
                            }
                        }
                );
            }
        };
        ThreadUtils.runOnSubThread(runnable);
    }

    @Override
    protected void initView(View view) {
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvQualified = (TextView) view.findViewById(R.id.tv_qualified);
        tvCheck = (TextView) view.findViewById(R.id.tv_check);
        tvNone = (TextView) view.findViewById(R.id.tv_none);
        ivPlay = (RelativeLayout) view.findViewById(R.id.iv_play);
        ivPlayGif = (ImageView) view.findViewById(R.id.iv_play_gif);
        ivPlayNoGif = (ImageView) view.findViewById(R.id.iv_play_no_gif);
        llPic = (LinearLayout) view.findViewById(R.id.ll_pic);
        divider = view.findViewById(R.id.divider);
        divider2 = view.findViewById(R.id.divider2);
        divider3 = view.findViewById(R.id.divider3);
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
        return R.layout.fragment_case_close_detail;
    }

    @Override
    public void onDestroy() {
        EventBusUtil.unregister(this);
        super.onDestroy();
    }
}
