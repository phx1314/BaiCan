//
//  FrgShenfenRezhengNew
//
//  Created by DELL on 2019-07-31 16:29:26
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.facefr.activity.PictureUploadActivity;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.utility.permissions.PermissionRequest;
import com.mdx.framework.widget.ActionBar;
import com.mdx.framework.widget.MImageView;
import com.ntdlg.bc.R;
import com.ntdlg.bc.model.ModelSF;

import java.io.File;

import static com.framewidget.F.ExistSDCard;
import static com.ntdlg.bc.F.bitmap2Byte;


public class FrgShenfenRezhengNew extends BaseFrg {
    public static final int CAMERA_CODE = 1;
    public MImageView mMImageView_1;
    public MImageView mMImageView_2;
    public TextView mTextView_shenqing;
    public Bitmap mBitmap1;
    public Bitmap mBitmap2;
    public Bitmap mBitmap3;
    public String from;
    public MImageView mMImageView_add;
    public String path = Environment
            .getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".jpeg";

    @Override
    protected void create(Bundle savedInstanceState) {
        from = getActivity().getIntent().getStringExtra("from");
        setContentView(R.layout.frg_shenfen_rezheng_new);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 1:
                mBitmap1 = (Bitmap) obj;
                mMImageView_1.setImageBitmap(mBitmap1);
                break;
            case 2:
                mBitmap2 = (Bitmap) obj;
                mMImageView_2.setImageBitmap(mBitmap2);
                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mMImageView_1 = (MImageView) findViewById(R.id.mMImageView_1);
        mMImageView_2 = (MImageView) findViewById(R.id.mMImageView_2);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);
        mMImageView_add = (MImageView) findViewById(R.id.mMImageView_add);

        mMImageView_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PictureUploadActivity.class).putExtra("type", 1));
            }
        });
        mMImageView_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PictureUploadActivity.class).putExtra("type", 2));
            }
        });
        mMImageView_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(com.ntdlg.bc.F.UserId)) {
                    Helper.toast("请先登录", getContext());
                    Helper.startActivity(getContext(), FrgLogin.class, TitleAct.class);
                    return;
                }
                Helper.requestPermissions(new String[]{Manifest.permission.CAMERA}, new PermissionRequest() {
                    public void onGrant(String[] permissions, int[] grantResults) {
                        path = Environment
                                .getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".jpeg";
                        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                        startActivityForResult(intent1, CAMERA_CODE);
                    }
                });

            }
        });
        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBitmap2 == null) {
                    Helper.toast("请拍摄身份证正面", getContext());
                    return;
                }
                if (mBitmap1 == null) {
                    Helper.toast("请拍摄身份证反面", getContext());
                    return;
                }
                if (mBitmap3 == null) {
                    Helper.toast("请上传手持身份证照片", getContext());
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ModelSF response = new ModelSF();
                        response.idcard_front_photo = Base64.encodeToString(bitmap2Byte(mBitmap2), Base64.DEFAULT);
                        response.idcard_back_photo = Base64.encodeToString(bitmap2Byte(mBitmap1), Base64.DEFAULT);
                        response.pic_photo = Base64.encodeToString(bitmap2Byte(mBitmap3), Base64.DEFAULT);
                        FrgShenfenRezhengNew.this.finish();
                        Frame.HANDLES.sentAll(from, 120, response);
                    }
                }).start();

            }
        });
    }

    public void loaddata() {

    }

    /**
     * @param bitmap
     * @param orientationDegree 0 - 360 范围
     * @return
     */
    Bitmap adjustPhotoRotation(Bitmap bitmap, int orientationDegree) {

        Matrix matrix = new Matrix();
        matrix.setRotate(orientationDegree, (float) bitmap.getWidth() / 2,
                (float) bitmap.getHeight() / 2);
        float targetX, targetY;
        if (orientationDegree == 90) {
            targetX = bitmap.getHeight();
            targetY = 0;
        } else {
            targetX = bitmap.getHeight();
            targetY = bitmap.getWidth();
        }


        final float[] values = new float[9];
        matrix.getValues(values);


        float x1 = values[Matrix.MTRANS_X];
        float y1 = values[Matrix.MTRANS_Y];


        matrix.postTranslate(targetX - x1, targetY - y1);


        Bitmap canvasBitmap = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getWidth(),
                Bitmap.Config.ARGB_8888);


        Paint paint = new Paint();
        Canvas canvas = new Canvas(canvasBitmap);
        canvas.drawBitmap(bitmap, matrix, paint);


        return canvasBitmap;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getActivity().RESULT_OK) {
            return;
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            return;//取消
        }
        switch (requestCode) {
            case CAMERA_CODE:
                if (ExistSDCard()) {
                    mBitmap3 = com.mdx.framework.utility.BitmapRead.decodeSampledBitmapFromFile(
                            path, 480, 0);
                    mMImageView_add.setImageBitmap(mBitmap3);
                    mMImageView_add.setBackgroundResource(0);
                } else {
                    Helper.toast("sd卡不存在", getContext());
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("身份证认证");
    }
}