package com.qianyuan.casedetail.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qianyuan.casedetail.R;
import com.qianyuan.casedetail.bean.CaseDetailBean;
import com.qianyuan.casedetail.bean.Constant;
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
import com.qianyuan.casedetail.view.CategoryDialog;
import com.qianyuan.casedetail.view.TakePicDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by sun on 2017/5/8.
 */

public class CaseModifyActivity extends BaseActivity {
    @BindView(R.id.iv_take_pic)
    ImageView ivTakePic;
    @BindView(R.id.git_listener)
    GifImageView gifListener;
    @BindView(R.id.iv_record)
    ImageView ivRecord;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.ll_pic)
    LinearLayout llPic;
    @BindView(R.id.tv_record_finish)
    TextView tvRecordFinish;//上传
    @BindView(R.id.rl_not_urgent)
    RelativeLayout rlNotUrgent;
    @BindView(R.id.rl_urgent)
    RelativeLayout rlUrgent;
    @BindView(R.id.iv_not_urgent)
    ImageView ivNotUrgent;
    @BindView(R.id.tv_not_urgent)
    TextView tvNotUrgent;
    @BindView(R.id.iv_urgent)
    ImageView ivUrgent;
    @BindView(R.id.tv_urgent)
    TextView tvUrgent;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.et_location)
    TextView etLocation;
    @BindView(R.id.et_describe)
    EditText etDescribe;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.scrollView2)
    ScrollView scrollView2;
    @BindView(R.id.rlCommenTitleBG)
    ImageView rlCommenTitleBG;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_image_right)
    ImageView rlImageRight;
    @BindView(R.id.rl_title_right)
    RelativeLayout rlTitleRight;
    @BindView(R.id.btn_title_left)
    Button btnTitleLeft;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.ll_title_left)
    LinearLayout llTitleLeft;
    @BindView(R.id.btn_title_right)
    Button btnTitleRight;
    @BindView(R.id.btn_category_modify)
    Button btnCategoryModify;
    @BindView(R.id.btn_confirm)
    TextView btnConfirm;
    private File mCurrentPhotoFile;
    private String picPath;
    private boolean isRecord = true;
    private String voicePath;
    private static final int PIC_URL = 11;
    private List<String> picUrlList = new ArrayList<>();
    private static final int PIC_ID = 01;
    private static final int PIC_POSITION = 0;
    public CategoryDialog dialog = null;
    public String state = "非紧急";//默认值是从上报获取到的状态
    public List<String> picList = new ArrayList<>();
    public List<String> audioList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_modify);
        ButterKnife.bind(this);
        initVeiw();
        initData();
    }

    private void initData() {
        setTitleText("案件修改");
        getCaseDetail();
    }

    private void initVeiw() {
    }

    @OnClick(R.id.iv_take_pic)
    public void onTakepicClick() {
        TakePicDialog takePicDialog = new TakePicDialog(this) {
            @Override
            public void takePic() {
                mCurrentPhotoFile = PictureUtil.takeCamera(CaseModifyActivity.this, mCurrentPhotoFile);
                dismiss();
            }

            @Override
            public void choosePic() {
                PictureUtil.choosePicture(CaseModifyActivity.this);
                dismiss();
            }
        };
        takePicDialog.show();
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
                    addPic(false, null, null, 0);
                } else {
                    Toast.makeText(this, "照相失败，请重试！", Toast.LENGTH_SHORT).show();
                }
                break;
            case PictureUtil.CHOOSE_PIC:
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        picPath = PictureUtil.getPhotoPath(this, selectedImage);

                        addPic(false, null, null, 0);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addPic(boolean isOld, String picUrl, String picId, int position) {
        final ImageView picIv = new ImageView(this);
        int picWidth = ScreenUtil.dip2px(this, 120);
        int picHeight = ScreenUtil.dip2px(this, 120);
        int margin = ScreenUtil.dip2px(this, 10);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(picWidth, picHeight);
        lp.setMargins(margin, 0, 0, 0);
        picIv.setLayoutParams(lp);
        picIv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if (isOld) {
            Glide.with(this).load(picUrl).into(picIv);
            picUrlList.add(picId);
            Map<Integer, String> map = new HashMap<>();
            Map<Integer, Integer> map0 = new HashMap<>();
            map.put(PIC_ID, picId);
            map.put(PIC_URL, picUrl);
            map0.put(PIC_POSITION, position);
            picIv.setTag(map);
            picIv.setTag(map0);
        } else {
            String path = Constant.IMG_SD + picPath;//文件图片
            Glide.with(this).load(path).into(picIv);
            uploadPic(picIv);
        }
        picIv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                DialogUtil.simpleDialog(CaseModifyActivity.this, "提示", "是否删除图片？", "是", "否",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //是否删除图片
                                llPic.removeView(picIv);
                                Map<Integer, String> map = (Map<Integer, String>) v.getTag();
                                picUrlList.remove(map.get(PIC_ID));
                                Map<Integer, Integer> map11 = (Map<Integer, Integer>) picIv.getTag();
                                int position = map11.get(PIC_POSITION);
                                if (position >= 0) {
                                    picList.remove(position);
                                    for (int i = 0; i < picList.size(); i++) {
                                    }
                                }
                            }
                        }, null);
                return true;
            }
        });
        llPic.addView(picIv, 0);//替换第1个image
    }

    private void uploadPic(ImageView picIv) {
        //压缩图片
        final String filePath = PictureUtil.compressImage(picPath, picPath, 100);
        showProgressDialog();
        String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
        String userId = SharedPreferencesUtil.getString("userId", "userId", "");
        final String url = projectUrl + Url.UploadFile + "USERID=" + userId;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                OkGo.post(url)
                        .tag(this)
                        .params("NAME", new File(filePath))
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

    @OnClick(R.id.iv_record)
    public void onRecordClick() {
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

    @OnClick(R.id.tv_record_finish)
    public void onUpLoadVoiceClick() {
        //上传录音
        String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
        String userId = SharedPreferencesUtil.getString("userId", "userId", "");
        final String url = projectUrl + Url.UploadFile + "USERID=" + userId;
        showProgressDialog();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                OkGo.post(url)
                        .params("NAME", new File(voicePath))
                        .params("TYPECODE", 1)
                        .params("MARK", "voice")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String result, Call call, Response response) {
                                closeProgressDialog();
                                EventBusUtil.post(new MessageEvent(MessageEvent.CASEVERIFYDIALOG));
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    int code = (int) jsonObject.get("CODE");
                                    if (code == Constant.SUCCEED_CODE) {
                                        UpLoadFile upLoadFile = GsonUtil.parseJsonToBean(result, UpLoadFile.class);
                                        SharedPreferencesUtil.putString("isUpLoadMp4", "isUpLoadMp4", "true");
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
    }

    @OnClick({R.id.rl_not_urgent, R.id.rl_urgent})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_not_urgent:
                setDegreeType(false);
                state = "非紧急";
                break;
            case R.id.rl_urgent:
                setDegreeType(true);
                state = "紧急";
                break;
        }
    }

    private void setDegreeType(boolean isUrgent) {
        ivUrgent.setImageResource(isUrgent ?
                R.drawable.urgent_report_checked : R.drawable.urgent_report);
        ivNotUrgent.setImageResource(isUrgent ?
                R.drawable.not_urgent_report : R.drawable.not_urgent_report_checked);
        tvUrgent.setTextColor(getResources().getColor(isUrgent ?
                R.color.blue_text : R.color.grey_text));
        tvNotUrgent.setTextColor(getResources().getColor(isUrgent ?
                R.color.grey_text : R.color.blue_text));
    }

    @OnClick(R.id.btn_category_modify)
    public void onCategoryClick() {
        dialog = new CategoryDialog(this, new CategoryDialog.OnChangeClick() {
            @Override
            public void onChange() {
                if (!(dialog.classifyList == null)) {
                    tvType.setText(dialog.classifyList);
                }

            }
        });
        dialog.show();
    }

    @OnClick(R.id.btn_confirm)
    public void onConfirmClick() {
        String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
        final String userId = SharedPreferencesUtil.getString("userId", "userId", "");
        final String caseId = SharedPreferencesUtil.getString("caseId", "caseId", "");
        final String url = projectUrl + Url.ModifyCase + "USERID=" + userId + "&CASEID=" + caseId;

        final String postiondes = etLocation.getText().toString();//默认值是从上报获取的位置
        final String worddetail = etDescribe.getText().toString();
        String casetype2 = tvType.getText().toString();
        final String casetype = casetype2.replaceAll("-", ",");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss  ");
        Date date = new Date(System.currentTimeMillis());
        final String dateTime = simpleDateFormat.format(date);
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

        if (postiondes.equals("")) {
            ToastUtil.showShortToast("请点击获得地址或者输入地址");
        } else if (worddetail.equals("")) {
            ToastUtil.showShortToast("描述不能为空");
        } else if (!postiondes.equals("") && !worddetail.equals("")) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    OkGo.post(url)
                            .params("USERID", userId)
                            .params("CASEID", caseId)
                            .params("POSTIONDES", postiondes)
                            .params("EMERGENCYSTATE", state)
                            .params("WORDDETAIL", worddetail)
                            .params("PICTUREID", sb.toString())
                            .params("AUDIOID", sb2.toString())
                            .params("CLASSIFYLIST", casetype)
                            .params("DATETIME", dateTime)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String result, Call call, Response response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        int code = (int) jsonObject.get("CODE");
                                        if (code == Constant.SUCCEED_CODE) {
                                            ToastUtil.showShortToast("修改成功");
                                            String isUpLoadMp4 = SharedPreferencesUtil.getString("isUpLoadMp4", "isUpLoadMp4", "");
                                            EventBusUtil.post(new MessageEvent(MessageEvent.CaseModifyActivity, isUpLoadMp4));
                                            finish();
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
    }

    public void getCaseDetail() {
        final String projectUrl = SharedPreferencesUtil.getString("projectUrl", "projectUrl", "");
        final String userId = SharedPreferencesUtil.getString("userId", "userId", "");
        final String caseId = SharedPreferencesUtil.getString("caseId", "caseId", "");
        final String url = projectUrl + Url.ModifyCase + "USERID=" + userId + "&CASEID=" + caseId;
        String proUrlButton1 = projectUrl.substring(0, projectUrl.lastIndexOf("/"));
        String proUrlButton = projectUrl.substring(0, proUrlButton1.lastIndexOf("/"));

        final String getPic = proUrlButton;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String url = projectUrl + Url.GetCaseInfo + "USERID=" + userId + "&CASEID=" + caseId;
                OkGo.post(url).tag(this).execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int code = (int) jsonObject.get("CODE");
                            if (code == Constant.SUCCEED_CODE) {
                                CaseDetailBean caseDetailBean = GsonUtil.parseJsonToBean(result, CaseDetailBean.class);
                                CaseDetailBean.DATABean data = caseDetailBean.getDATA();
                                final String uploadposition = data.getUPLOADPOSITION();
                                final String emergencystate = data.getEMERGENCYSTATE();
                                final String worddetail = data.getWORDDETAIL();
                                final String casetype = data.getCASETYPE();
                                List<CaseDetailBean.DATABean.PICTUREIDBean> pictureidList = data.getPICTUREID();

                                List<CaseDetailBean.DATABean.AUDIOIDBean> audioidList = data.getAUDIOID();
                                etLocation.setText(uploadposition);
                                if (emergencystate != null && emergencystate.equals("紧急")) {
                                    state = "紧急";
                                    setDegreeType(true);
                                } else if (emergencystate != null && emergencystate.equals("非紧急")) {
                                    setDegreeType(false);
                                    state = "非紧急";
                                }

                                etDescribe.setText(worddetail);
                                tvType.setText(casetype);
                                if (pictureidList != null) {
                                    for (int i = 0; i < pictureidList.size(); i++) {
                                        String url2 = getPic + pictureidList.get(i).getURL();
                                        String id = pictureidList.get(i).getID();
                                        picList.add(id);
                                        addPic(true, url2, id, i);
                                    }
                                }

                                if (audioidList != null) {
                                    for (int i = 0; i < audioidList.size(); i++) {
                                        String audioId = audioidList.get(i).getID();
                                        audioList.add(audioId);
                                    }
                                }
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
}
