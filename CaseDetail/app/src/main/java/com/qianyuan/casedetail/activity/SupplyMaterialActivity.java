package com.qianyuan.casedetail.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qianyuan.casedetail.R;
import com.qianyuan.casedetail.bean.Constant;
import com.qianyuan.casedetail.utils.AudioPlayer;
import com.qianyuan.casedetail.utils.DialogUtil;
import com.qianyuan.casedetail.utils.ImageUtil;
import com.qianyuan.casedetail.utils.IsLegalCheck;
import com.qianyuan.casedetail.utils.LogUtil;
import com.qianyuan.casedetail.utils.PictureUtil;
import com.qianyuan.casedetail.utils.ScreenUtil;
import com.qianyuan.casedetail.utils.ToastUtil;
import com.qianyuan.casedetail.view.TakePicDialog;

import java.io.File;
import java.util.ArrayList;
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

public class SupplyMaterialActivity extends BaseActivity {
    @BindView(R.id.iv_take_pic)
    ImageView ivTakePic;

    @BindView(R.id.git_listener)
    GifImageView gifListener;
    @BindView(R.id.iv_record)
    ImageView ivRecord;
    @BindView(R.id.tv_record_finish)
    TextView tvRecordFinish;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.btn_supply_material_confirm)
    TextView btnSupplyMaterialConfirm;
    @BindView(R.id.et_describe)
    EditText etDescribe;
    @BindView(R.id.ll_pic)
    LinearLayout llPic;
    private File mCurrentPhotoFile;
    private String picPath;
    private boolean isRecord = true;
    private String voicePath;
    private static final int PIC_URL = 11;
    private static final int PIC_ID = 01;
    private List<String> picUrlList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_material);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        setTitleText("补充材料");
    }

    private void initView() {

    }

    @OnClick(R.id.iv_take_pic)
    public void onTakePicClick() {
        TakePicDialog takePicDialog = new TakePicDialog(this) {
            @Override
            public void takePic() {
                mCurrentPhotoFile = PictureUtil.takeCamera(SupplyMaterialActivity.this, mCurrentPhotoFile);
                dismiss();
            }

            @Override
            public void choosePic() {
                PictureUtil.choosePicture(SupplyMaterialActivity.this);
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

    private void addPic() {
        final ImageView picIv = new ImageView(this);
        int picWidth = ScreenUtil.dip2px(this, 120);
        int picHeight = ScreenUtil.dip2px(this, 120);
        int margin = ScreenUtil.dip2px(this, 10);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(picWidth, picHeight);
        lp.setMargins(margin, 0, 0, 0);
        picIv.setLayoutParams(lp);
        picIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String path = Constant.IMG_SD + picPath;//文件图片
        Glide.with(this).load(path).into(picIv);
        uploadPic(picIv);
        picIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Integer, String> map = (Map<Integer, String>) v.getTag();
                PictureUtil.zoomInPicture(SupplyMaterialActivity.this, picIv, map.get(PIC_URL));
            }
        });
        picIv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                DialogUtil.simpleDialog(SupplyMaterialActivity.this, "提示", "是否删除图片？", "是", "否",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                llPic.removeView(picIv);
                                Map<Integer, String> map = (Map<Integer, String>) v.getTag();
                                picUrlList.remove(map.get(PIC_ID));
                            }
                        }, null);
                return true;
            }
        });
        llPic.addView(picIv, 0);//替换第1个image
    }

    private void uploadPic(final ImageView picIv) {
        showProgressDialog();
        String url = "http://101.200.218.197:8081/fangshan/Higo-View/App.FileUploadSB?user=9AF72BB3AB0811F582B698F02E83A35E4D436668";
        Bitmap bm = ImageUtil.getBitmapFromFile(picPath);
        byte[] picByte = ImageUtil.bitmap2Bytes(bm);
        long name = System.currentTimeMillis();
        OkGo.post(url)
                .tag(this)
                .isMultipart(true)
                .params("NAME", name + ".jpg")
                .params("TYPECODE", "0")
                .params("MARK", 0)
                .params("file", new File(picPath))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                    }
                });
    }

    @OnClick(R.id.iv_record)
    public void onRecordClick() {
        if (isRecord) {
            isRecord = false;
            gifListener.setVisibility(View.VISIBLE);
            ivRecord.setImageResource(R.drawable.stop);
            voicePath = getVoicePath();
            AudioPlayer.record(voicePath);
            ToastUtil.showLongToast("开始录音");
        } else {
            isRecord = true;
            gifListener.setVisibility(View.INVISIBLE);
            tvRecordFinish.setVisibility(View.VISIBLE);
            ivPlay.setVisibility(View.VISIBLE);
            ivRecord.setImageResource(R.drawable.microphone_report);
            AudioPlayer.stopRecord();
            ToastUtil.showLongToast("停止录音");
        }
    }

    private String getVoicePath() {
        String path;
        String dir = Environment.getExternalStorageDirectory().getPath() + "/gridmember/voice/";
        File photo_dir = new File(dir);
        if (!photo_dir.exists()) {
            photo_dir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".3gp";
        path = dir + fileName;
        return path;
    }

    @OnClick(R.id.btn_supply_material_confirm)
    public void onConfirmClick() {
        String des = etDescribe.getText().toString().trim();
        if (IsLegalCheck.isLegalCheck(des)) {
            ToastUtil.showShortToast("成功");
            gotoActivity(AlreadySupplyMaterialsActivity.class);
            finish();
        }

    }
}
