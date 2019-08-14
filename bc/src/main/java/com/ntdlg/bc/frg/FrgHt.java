//
//  FrgHt
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanCKHT;
import com.ntdlg.bc.model.ModelCKHT;

import java.io.File;

import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.showAgreement;


public class FrgHt extends BaseFrg {

    public RelativeLayout clk_mRelativeLayout_1;
    public TextView mTextView_1;
    public ImageView mImageView_1;
    public RelativeLayout clk_mRelativeLayout_2;
    public TextView mTextView_2;
    public ImageView mImageView_2;
    public WebView mWebView;
    public String ht_url;
    public String xy_url;
    public com.github.barteksc.pdfviewer.PDFView pdfView;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_ht);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        clk_mRelativeLayout_1 = (RelativeLayout) findViewById(R.id.clk_mRelativeLayout_1);
        mTextView_1 = (TextView) findViewById(R.id.mTextView_1);
        mImageView_1 = (ImageView) findViewById(R.id.mImageView_1);
        clk_mRelativeLayout_2 = (RelativeLayout) findViewById(R.id.clk_mRelativeLayout_2);
        mTextView_2 = (TextView) findViewById(R.id.mTextView_2);
        mImageView_2 = (ImageView) findViewById(R.id.mImageView_2);
        mWebView = (WebView) findViewById(R.id.mWebView);
        pdfView = (com.github.barteksc.pdfviewer.PDFView) findViewById(R.id.pdfView);

        clk_mRelativeLayout_1.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mRelativeLayout_2.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));

    }

    public void loaddata() {
        BeanCKHT mBeanCKHT = new BeanCKHT();
        mBeanCKHT.sign = readClassAttr(mBeanCKHT);
        loadJsonUrl(showAgreement, new Gson().toJson(mBeanCKHT));


        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDisplayZoomControls(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK
                            && mWebView.canGoBack()) { // 表示按返回键
                        mWebView.goBack(); // 后退
                        return true; // 已处理
                    }
                }
                return false;
            }
        });
    }


    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(showAgreement)) {
            ModelCKHT mModelCKHT = (ModelCKHT) json2Model(content, ModelCKHT.class);
            for (ModelCKHT.ArrayBean mArrayBean : mModelCKHT.array) {
                if (mArrayBean.type.equals("1")) {
                    xy_url = mArrayBean.url;
                }
                if (mArrayBean.type.equals("2")) {
                    ht_url = mArrayBean.url;
                }
            }
            clk_mRelativeLayout_1.performClick();

        }
    }

    @Override
    public void onClick(android.view.View v) {
        if (R.id.clk_mRelativeLayout_1 == v.getId()) {
            mTextView_1.setTextColor(getResources().getColor(R.color.A));
            mImageView_1.setVisibility(View.VISIBLE);
            mImageView_2.setVisibility(View.GONE);
            mTextView_2.setTextColor(getResources().getColor(R.color.white));
//            mWebView.loadUrl(ht_url);
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
                                mWebView.loadUrl("file:///android_asset/show_pdf.html?" + path);
//                                pdfView.fromFile(new File(path)).swipeVertical(true)
////                .pages(0, 0, 0, 0, 0, 0) // 默认全部显示，pages属性可以过滤性显示
//                                        .defaultPage(1)//默认展示第一页
//                                        .load();
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
        } else if (R.id.clk_mRelativeLayout_2 == v.getId()) {
            mTextView_1.setTextColor(getResources().getColor(R.color.white));
            mTextView_2.setTextColor(getResources().getColor(R.color.A));
            mImageView_1.setVisibility(View.GONE);
            mImageView_2.setVisibility(View.VISIBLE);
            final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + System.currentTimeMillis() + ".pdf";
            FileDownloader.getImpl().create(xy_url)
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
                                mWebView.loadUrl("file:///android_asset/show_pdf.html?" + path);
//                                pdfView.fromFile(new File(path)).swipeVertical(true)
////                .pages(0, 0, 0, 0, 0, 0) // 默认全部显示，pages属性可以过滤性显示
//                                        .defaultPage(1)//默认展示第一页
//                                        .load();
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
        mHeadlayout.setTitle("查看合同");
    }
}