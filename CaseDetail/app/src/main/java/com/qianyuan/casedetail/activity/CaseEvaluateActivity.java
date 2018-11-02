package com.qianyuan.casedetail.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qianyuan.casedetail.R;
import com.qianyuan.casedetail.application.MyApplication;
import com.qianyuan.casedetail.bean.Constant;
import com.qianyuan.casedetail.bean.EndCase;
import com.qianyuan.casedetail.bean.MessageEvent;
import com.qianyuan.casedetail.bean.UpLoadFile;
import com.qianyuan.casedetail.bean.Url;
import com.qianyuan.casedetail.utils.AudioPlayer;
import com.qianyuan.casedetail.utils.DialogUtil;
import com.qianyuan.casedetail.utils.EventBusUtil;
import com.qianyuan.casedetail.utils.GsonUtil;
import com.qianyuan.casedetail.utils.LogUtil;
import com.qianyuan.casedetail.utils.PictureUtil;
import com.qianyuan.casedetail.utils.ScreenUtil;
import com.qianyuan.casedetail.utils.SharedPreferencesUtil;
import com.qianyuan.casedetail.utils.ThreadUtils;
import com.qianyuan.casedetail.utils.ToastUtil;
import com.qianyuan.casedetail.view.TakePicDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.shaohui.advancedluban.Luban;
import okhttp3.Call;
import okhttp3.Response;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by sun on 2017/5/25.
 */

public class CaseEvaluateActivity extends BaseActivity {
    @BindView(R.id.rl_good)
    RelativeLayout rlGood;
    @BindView(R.id.rl_not_good)
    RelativeLayout rlNotGood;
    @BindView(R.id.iv_good)
    ImageView ivGood;
    @BindView(R.id.tv_good)
    TextView tvGood;
    @BindView(R.id.iv_not_good)
    ImageView ivNotGood;
    @BindView(R.id.tv_not_good)
    TextView tvNotGood;
    @BindView(R.id.et_describe)
    EditText etDescribe;
    @BindView(R.id.btn_confirm)
    TextView btnConfirm;
    @BindView(R.id.iv_take_pic)
    ImageView ivTakePic;
    @BindView(R.id.ll_pic)
    LinearLayout llPic;
    @BindView(R.id.iv_record)
    ImageView ivRecord;
    @BindView(R.id.git_listener)
    GifImageView gifListener;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.tv_record_finish)
    TextView tvRecordFinish;
    private boolean goodType;
    private boolean isComplete = true;
    private File mCurrentPhotoFile;
    private String picPath;
    private List<String> picUrlList = new ArrayList<>();
    public List<String> picList = new ArrayList<>();
    private boolean isRecord = true;
    private String voicePath;
    public List<String> audioList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_evaluate);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setTitleText("核查案件");
    }

    private void initData() {
        OkGo.init(MyApplication.getInstance());

    }

    @OnClick({R.id.rl_good, R.id.rl_not_good, R.id.btn_confirm, R.id.iv_take_pic, R.id.iv_record, R.id.tv_record_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_good:
                setGoodType(true);
                isComplete = true;

                break;
            case R.id.rl_not_good:
                setGoodType(false);
                isComplete = false;

                break;
            case R.id.iv_record:
                if (isRecord) {
                    try {
                        voicePath = getVoicePath();
                        AudioPlayer.record(voicePath);
                        ToastUtil.showLongToast("开始录音");
                        isRecord = false;
                        gifListener.setVisibility(View.VISIBLE);
                        ivRecord.setImageResource(R.drawable.stop);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                        ToastUtil.showLongToast("请允许使用录音权限");
                    }
                } else {
                    try {
                        AudioPlayer.record(voicePath);
                        ToastUtil.showLongToast("开始录音");
                        isRecord = true;
                        gifListener.setVisibility(View.INVISIBLE);
                        tvRecordFinish.setVisibility(View.VISIBLE);
                        ivPlay.setVisibility(View.VISIBLE);
                        ivRecord.setImageResource(R.drawable.microphone_report);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                        ToastUtil.showLongToast("请允许使用录音权限");
                    }
                }
                break;
            case R.id.tv_record_finish:
                //上传录音
                showProgressDialog();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
                        String userId = SharedPreferencesUtil.getString("userId", "userId", "");
                        final String url = projectUrl + Url.UpLoadvVrifyFile + "USERID=" + userId;
                        OkGo.post(url)
                                .params("NAME", new File(voicePath))
                                .params("TYPECODE", 1)
                                .params("MARK", "voice")
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String result, Call call, Response response) {
                                        closeProgressDialog();
                                        try {
                                            JSONObject jsonObject = new JSONObject(result);
                                            int code = (int) jsonObject.get("CODE");
                                            if (code == Constant.SUCCEED_CODE) {
                                                UpLoadFile upLoadFile = GsonUtil.parseJsonToBean(result, UpLoadFile.class);
                                                List<UpLoadFile.DATABean> data = upLoadFile.getDATA();
                                                String uuid = data.get(0).getUUID();
                                                audioList.add(uuid);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                };
                ThreadUtils.runOnSubThread(runnable);

                break;
            case R.id.iv_take_pic:
                TakePicDialog takePicDialog = new TakePicDialog(this) {
                    @Override
                    public void takePic() {
                        mCurrentPhotoFile = PictureUtil.takeCamera(CaseEvaluateActivity.this, mCurrentPhotoFile);
                        dismiss();
                    }

                    @Override
                    public void choosePic() {
                        PictureUtil.choosePicture(CaseEvaluateActivity.this);
                        dismiss();
                    }
                };
                takePicDialog.show();
                break;

            case R.id.btn_confirm:
                final String wordDetail = etDescribe.getText().toString();
                String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
                final String userId = SharedPreferencesUtil.getString("userId", "userId", "");
                final String caseId = SharedPreferencesUtil.getString("caseId", "caseId", "");
                final String url = projectUrl + Url.EndCase + "USERID=" + userId + "&CASEID=" + caseId;
                //图片id
                final StringBuffer sb = new StringBuffer();
                String separator = ",";
                for (int i = 0; i < picList.size(); i++) {
                    if (i == (picList.size() - 1)) {
                        sb.append(picList.get(i));
                    } else {
                        sb.append(picList.get(i)).append(separator);
                    }
                }
                //音频id
                final StringBuffer sb2 = new StringBuffer();
                for (int i = 0; i < audioList.size(); i++) {
                    if (i == (audioList.size() - 1)) {
                        sb2.append(audioList.get(i));
                    } else {
                        sb2.append(audioList.get(i)).append(separator);
                    }
                }

                if (wordDetail.equals("")) {
                    ToastUtil.showShortToast("描述不能为空");
                } else {
                    Runnable runnable1 = new Runnable() {
                        @Override
                        public void run() {
                            OkGo.post(url)
                                    .params("USERID", userId)
                                    .params("CASEID", caseId)
                                    .params("WORDDETAIL", wordDetail)
                                    .params("COMPLETE", isComplete)
                                    .params("PICTUREID", sb.toString())
                                    .params("AUDIOID", sb2.toString())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String result, Call call, Response response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(result);
                                                int code = (int) jsonObject.get("CODE");
                                                if (code == Constant.SUCCEED_CODE) {
                                                    ToastUtil.showLongToast("核查成功");
                                                    EventBusUtil.post(new MessageEvent(MessageEvent.CASEEVALUATEACTIVITY));
                                                    EventBusUtil.post(new MessageEvent(MessageEvent.CASEVERIFYDIALOG));
                                                    finish();
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        }
                    };
                    ThreadUtils.runOnSubThread(runnable1);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PictureUtil.TAKE_PIC:
                if (null != mCurrentPhotoFile) {
                    picPath = mCurrentPhotoFile.getAbsolutePath();
                    addPic();
                } else {
                    Toast.makeText(this, "照相失败，请重试！", Toast.LENGTH_SHORT).show();
                }
                break;
            case PictureUtil.CHOOSE_PIC:
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        picPath = PictureUtil.getPhotoPath(this, selectedImage);
                        addPic();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getVoicePath() {
        String path;
        String dir = Url.SaveVoicePath;

        File photo_dir = new File(dir);
        if (!photo_dir.exists()) {
            photo_dir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".mp4";
        path = dir + fileName;
        return path;
    }


    private void addPic() {
        //压缩图片
        String filePath = PictureUtil.compressImage(picPath, picPath, 100);
        final ImageView picIv = new ImageView(this);
        int picWidth = ScreenUtil.dip2px(this, 120);
        int picHeight = ScreenUtil.dip2px(this, 120);
        int margin = ScreenUtil.dip2px(this, 10);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(picWidth, picHeight);
        lp.setMargins(margin, 0, 0, 0);
        picIv.setLayoutParams(lp);
        picIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        final String path = Constant.IMG_SD + picPath;//文件图片
        Glide.with(this).load(path).into(picIv);
        uploadPic(picIv);
        picIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureUtil.zoomInPicture(CaseEvaluateActivity.this, picIv, path);
            }
        });
        picIv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                DialogUtil.simpleDialog(CaseEvaluateActivity.this, "提示", "是否删除图片？", "是", "否",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //是否删除图片
                                llPic.removeView(picIv);
                            }
                        }, null);
                return true;
            }
        });
        llPic.addView(picIv, 0);//替换第1个image
    }

    private void uploadPic(ImageView picIv) {
        showProgressDialog();
        String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
        String userId = SharedPreferencesUtil.getString("userId", "userId", "");
        final String url = projectUrl + Url.UpLoadvVrifyFile + "USERID=" + userId;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                OkGo.post(url)
                        .tag(this)
                        .params("NAME", new File(picPath))
                        .params("TYPECODE", 0)
                        .params("MARK", "pic")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String result, Call call, Response response) {
                                closeProgressDialog();
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    int code = (int) jsonObject.get("CODE");
                                    if (code == Constant.SUCCEED_CODE) {
                                        UpLoadFile upLoadFile = GsonUtil.parseJsonToBean(result, UpLoadFile.class);
                                        List<UpLoadFile.DATABean> data = upLoadFile.getDATA();
                                        String uuid = data.get(0).getUUID();
                                        picList.add(uuid);
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

    public void setGoodType(boolean isGood) {
        ivGood.setImageResource(isGood ?
                R.drawable.good_pressed : R.drawable.good);
        ivNotGood.setImageResource(isGood ?
                R.drawable.bad : R.drawable.bad_pressed);
        tvGood.setTextColor(getResources().getColor(isGood ?
                R.color.blue_text : R.color.grey_text));
        tvNotGood.setTextColor(getResources().getColor(isGood ?
                R.color.grey_text : R.color.blue_text));

    }
}
