//
//  FrgWode
//
//  Created by DELL on 2017-06-01 11:15:58
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.MImageView;
import com.mdx.framework.widget.getphoto.PopUpdataPhoto;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanSZTX;
import com.ntdlg.bc.bean.BeanShare;
import com.ntdlg.bc.model.ModelShare;
import com.ntdlg.bc.model.ModelZHZX;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.ntdlg.bc.F.Bitmap2StrByBase64;
import static com.ntdlg.bc.F.getInfo;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.setPhoto;
import static com.ntdlg.bc.F.share;


public class FrgWode extends BaseFrg {

    public ImageView clk_mTextView_hd;
    public MImageView mImageView;
    public TextView mTextView_name;
    public LinearLayout mLinearLayout_1;
    public TextView mTextView_jk;
    public LinearLayout mLinearLayout_2;
    public TextView mTextView_zhangdan;
    public LinearLayout mLinearLayout_3;
    public LinearLayout mLinearLayout_4;
    public ModelZHZX mModelZHZX;
    public ModelShare mModelShare;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_wode);
        setWindowStatusBarColor(getActivity(), R.color.A);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 0:
                loaddata();
                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        clk_mTextView_hd = (ImageView) findViewById(R.id.clk_mTextView_hd);
        mImageView = (MImageView) findViewById(R.id.mImageView);
        mTextView_name = (TextView) findViewById(R.id.mTextView_name);
        mLinearLayout_1 = (LinearLayout) findViewById(R.id.mLinearLayout_1);
        mTextView_jk = (TextView) findViewById(R.id.mTextView_jk);
        mLinearLayout_2 = (LinearLayout) findViewById(R.id.mLinearLayout_2);
        mTextView_zhangdan = (TextView) findViewById(R.id.mTextView_zhangdan);
        mLinearLayout_3 = (LinearLayout) findViewById(R.id.mLinearLayout_3);
        mLinearLayout_4 = (LinearLayout) findViewById(R.id.mLinearLayout_4);

        clk_mTextView_hd.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        mLinearLayout_1.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        mLinearLayout_2.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        mLinearLayout_3.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        mLinearLayout_4.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        mImageView.setOnClickListener(new View.OnClickListener() {
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
//                            mImageView.setObj("file:" + photoPath);
//                            mImageView.setCircle(true);
                            BeanSZTX mBeanSZTX = new BeanSZTX();
                            mBeanSZTX.photoPic = Bitmap2StrByBase64(photoPath);
                            mBeanSZTX.sign = readClassAttr(mBeanSZTX);
                            loadJsonUrl(setPhoto, new Gson().toJson(mBeanSZTX));
                        }
                    }
                }, 10, 10, 640, 640);
            }
        });

    }

    public void loaddata() {
        if (!TextUtils.isEmpty(F.UserId)) {
            BeanBase mBeanBase = new BeanBase();
            mBeanBase.sign = readClassAttr(mBeanBase);
            loadJsonUrlNoshow(getInfo, new Gson().toJson(mBeanBase));
            BeanShare mBeanShare = new BeanShare();
            mBeanShare.sign = readClassAttr(mBeanShare);
            loadJsonUrlNoshow(share, new Gson().toJson(mBeanShare));

        } else {
            mImageView.setObj("");
            mTextView_name.setText("");
        }

    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(getInfo)) {
            mModelZHZX = (ModelZHZX) json2Model(content, ModelZHZX.class);
            mTextView_name.setText(mModelZHZX.alias);
            mImageView.setObj(mModelZHZX.photo);
            mImageView.setCircle(true);
        } else if (methodName.equals(setPhoto)) {
            loaddata();
        } else if (methodName.equals(share)) {
            mModelShare = (ModelShare) json2Model(content, ModelShare.class);
        }
    }

    @Override
    public void onClick(android.view.View v) {
        if (TextUtils.isEmpty(com.ntdlg.bc.F.UserId)) {
            Helper.toast("请先登录", getContext());
            Helper.startActivity(getContext(), FrgLogin.class, TitleAct.class);
            return;
        }
        if (R.id.clk_mTextView_hd == v.getId()) {
            if (mModelShare != null) {
//                com.framewidget.F.getShare(getContext(), "", mModelShare.url
//                        , mModelShare.content, mModelShare.title);
//                share(getContext(), mModelShare.title, mModelShare.content + mModelShare.url);
                if (!TextUtils.isEmpty(mModelZHZX.qrCodeUrl)) {
                    //耗时操作都要放在子线程操作
                    //开启子线程获取输入流
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpURLConnection conn = null;
                            InputStream is = null;
                            try {
                                URL url = new URL(mModelZHZX.qrCodeUrl);
                                //开启连接
                                conn = (HttpURLConnection) url.openConnection();
                                //设置连接超时
                                conn.setConnectTimeout(5000);
                                //设置请求方式
                                conn.setRequestMethod("GET");
                                //conn.connect();
                                if (conn.getResponseCode() == 200) {
                                    is = conn.getInputStream();
                                    Bitmap b = BitmapFactory.decodeStream(is);
                                    shareImage(getContext(), b);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    //用完记得关闭
                                    is.close();
                                    conn.disconnect();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }


            }

        } else if (R.id.mLinearLayout_1 == v.getId()) {
            Helper.startActivity(getContext(), FrgWodeJk.class, TitleAct.class, "mModelZHZX", mModelZHZX);
        } else if (R.id.mLinearLayout_2 == v.getId()) {
            if (mModelZHZX.ischeck.equals("3") || mModelZHZX.ischeck.equals("1")) {
                Helper.toast("账单未生成", getContext());
                return;
            }
            Helper.startActivity(getContext(), FrgZhangdan.class, TitleAct.class, "title", "我的账单");
        } else if (R.id.mLinearLayout_3 == v.getId()) {
            Helper.startActivity(getContext(), FrgWodeYhk.class, TitleAct.class);
        } else if (R.id.mLinearLayout_4 == v.getId()) {
            Helper.startActivity(getContext(), FrgMore.class, TitleAct.class);
        }
    }

    public static void share(Context context, String title, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(
                Intent.createChooser(intent, title));
    }

    /**
     * 分享前必须执行本代码，主要用于兼容SDK18以上的系统
     */
    private static void checkFileUriExposure() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }


    /**
     * @param context 上下文
     */
    private static void shareImage(Context context, Bitmap mBitmap) {
        checkFileUriExposure();

        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), mBitmap, null, null)));
            intent.setType("image/*");   //分享文件
            context.startActivity(Intent.createChooser(intent, "分享"));
        } catch (Exception e) {
            Helper.toast("分享失败，未知错误", context);
        }
    }

}