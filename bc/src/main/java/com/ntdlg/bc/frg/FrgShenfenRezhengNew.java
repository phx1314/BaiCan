//
//  FrgShenfenRezhengNew
//
//  Created by DELL on 2019-07-31 16:29:26
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.facefr.activity.PictureUploadActivity;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.mdx.framework.widget.MImageView;
import com.mdx.framework.widget.getphoto.PopUpdataPhoto;
import com.ntdlg.bc.R;
import com.ntdlg.bc.model.ModelSF;

import static com.ntdlg.bc.F.Bitmap2StrByBase64;
import static com.ntdlg.bc.F.bitmap2Byte;


public class FrgShenfenRezhengNew extends BaseFrg {

    public MImageView mMImageView_1;
    public MImageView mMImageView_2;
    public TextView mTextView_shenqing;
    public Bitmap mBitmap1;
    public Bitmap mBitmap2;
    public String from;
    public String pic_photo;
    public MImageView mMImageView_add;

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
                Helper.getPhoto(getActivity(), new PopUpdataPhoto.OnReceiverPhoto() {
                    @Override
                    public void onReceiverPhoto(String photoPath, int width,
                                                int height) {
                        if (photoPath != null) {
                            mMImageView_add.setObj("file:" + photoPath);
                            pic_photo = Bitmap2StrByBase64(photoPath);
                        }
                    }
                }, 10, 10, 640, 640);
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
                if (TextUtils.isEmpty(pic_photo)) {
                    Helper.toast("请上传手持身份证照片", getContext());
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ModelSF response = new ModelSF();
                        response.idcard_front_photo = Base64.encodeToString(bitmap2Byte(mBitmap2), Base64.DEFAULT);
                        response.idcard_back_photo = Base64.encodeToString(bitmap2Byte(mBitmap1), Base64.DEFAULT);
                        response.pic_photo = pic_photo;
                        FrgShenfenRezhengNew.this.finish();
                        Frame.HANDLES.sentAll(from, 120, response);
                    }
                }).start();

            }
        });
    }

    public void loaddata() {

    }


    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("身份证认证");
    }
}