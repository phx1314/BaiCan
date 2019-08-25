//
//  FrgSign
//
//  Created by DELL on 2019-07-31 14:20:22
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.f1reking.signatureview.SignatureView;
import com.google.gson.Gson;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanCKHT;
import com.ntdlg.bc.bean.Beanesgin;
import com.ntdlg.bc.model.ModelCKHT;

import java.io.File;
import java.io.IOException;

import static com.ntdlg.bc.F.esgin;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.showAgreement;
import static com.ntdlg.bc.R.id.clk_mRelativeLayout_1;


public class FrgSign extends BaseFrg {

    public SignatureView mSignatureView;
    public String from;
    public com.github.barteksc.pdfviewer.PDFView pdfView;
    public CheckBox mCheckBox;
    public TextView tv_read;
    public TextView mTextView_shenqing;
    public LinearLayout mLinearLayout_ht;
    public LinearLayout mLinearLayout_sign;

    @Override
    protected void create(Bundle savedInstanceState) {
        from = getActivity().getIntent().getStringExtra("from");
        setContentView(R.layout.frg_sign);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mSignatureView = (SignatureView) findViewById(R.id.mSignatureView);
        pdfView = (com.github.barteksc.pdfviewer.PDFView) findViewById(R.id.pdfView);
        mCheckBox = (CheckBox) findViewById(R.id.mCheckBox);
        tv_read = (TextView) findViewById(R.id.tv_read);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);
        mLinearLayout_ht = (LinearLayout) findViewById(R.id.mLinearLayout_ht);
        mLinearLayout_sign = (LinearLayout) findViewById(R.id.mLinearLayout_sign);

        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCheckBox.isChecked()) {
                    Helper.toast("请阅读并知晓上述全部合同内容，且认可第三方对本次借款所提供的咨询服务", getContext());
                    return;
                }
                mLinearLayout_ht.setVisibility(View.GONE);
                mLinearLayout_sign.setVisibility(View.VISIBLE);
                mHeadlayout.setRText("完成");
                mHeadlayout.setR2Text("清除");

                mHeadlayout.setRightOnclicker(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            mSignatureView.save(Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".png", true, 0);
//                    Frame.HANDLES.sentAll("FrgWode",1,mSignatureView.getSavePath());
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    final Beanesgin mBeanesgin = new Beanesgin();
                                    mBeanesgin.esign = F.Bitmap2StrByBase64(mSignatureView.getSavePath());
                                    mBeanesgin.sign = readClassAttr(mBeanesgin);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadJsonUrl(esgin, new Gson().toJson(mBeanesgin));
                                        }
                                    });
                                }
                            }).start();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });
                mHeadlayout.setRight2Onclicker(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSignatureView.clear();
                    }
                });
            }
        });
    }

    public void loaddata() {
        BeanCKHT mBeanCKHT = new BeanCKHT();
        mBeanCKHT.sign = readClassAttr(mBeanCKHT);
        loadJsonUrl(showAgreement, new Gson().toJson(mBeanCKHT));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(esgin)) {
            Helper.toast("签名成功", getContext());
            FrgSign.this.finish();
            if (TextUtils.isEmpty(from)) {
                Helper.startActivity(getContext(), FrgJkShenqing.class, TitleAct.class);
            }
        } else if (methodName.equals(showAgreement)) {
            ModelCKHT mModelCKHT = (ModelCKHT) json2Model(content, ModelCKHT.class);
            String ht_url = "";
            for (ModelCKHT.ArrayBean mArrayBean : mModelCKHT.array) {
                if (mArrayBean.type.equals("2")) {
                    ht_url = mArrayBean.url;
                }
            }
            final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + System.currentTimeMillis() + ".pdf";
            FileDownloader.getImpl().create(ht_url)
                    .setPath(path)
                    .setForceReDownload(true)
                    .setListener(new FileDownloadListener() {
                        //等待
                        @Override
                        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        }

                        //下载进度回调
                        @Override
                        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        }

                        //完成下载
                        @Override
                        protected void completed(BaseDownloadTask task) {
                            try {
                                pdfView.fromFile(new File(path)).swipeVertical(true)
//                .pages(0, 0, 0, 0, 0, 0) // 默认全部显示，pages属性可以过滤性显示
                                        .defaultPage(1)//默认展示第一页
                                        .load();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        //暂停
                        @Override
                        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                        }

                        //下载出错
                        @Override
                        protected void error(BaseDownloadTask task, Throwable e) {
                        }

                        //已存在相同下载
                        @Override
                        protected void warn(BaseDownloadTask task) {
                        }
                    }).start();
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("签名");

    }
}