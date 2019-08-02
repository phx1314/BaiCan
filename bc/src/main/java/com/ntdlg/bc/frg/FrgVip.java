//
//  FrgVip
//
//  Created by DELL on 2019-08-01 15:02:45
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ntdlg.bc.R;


public class FrgVip extends BaseFrg {

    public ImageButton btn_left;
    public LinearLayout mLinearLayout_back;
    public TextView tv_close;
    public TextView tv_title;
    public ImageView mImageView;
    public ImageButton btn_right_2;
    public ImageButton btn_right;
    public TextView mTextView_right;
    public RelativeLayout mRelativeLayout;
    public WebView mWebView;
    public String url = "";

    @Override
    protected void create(Bundle savedInstanceState) {
        url = getActivity().getIntent().getStringExtra("url");
        setContentView(R.layout.frg_vip);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        btn_left = (ImageButton) findViewById(R.id.btn_left);
        mLinearLayout_back = (LinearLayout) findViewById(R.id.mLinearLayout_back);
        tv_close = (TextView) findViewById(R.id.tv_close);
        tv_title = (TextView) findViewById(R.id.tv_title);
        mImageView = (ImageView) findViewById(R.id.mImageView);
        btn_right_2 = (ImageButton) findViewById(R.id.btn_right_2);
        btn_right = (ImageButton) findViewById(R.id.btn_right);
        mTextView_right = (TextView) findViewById(R.id.mTextView_right);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.mRelativeLayout);
        mWebView = (WebView) findViewById(R.id.mWebView);
        btn_left.setImageResource(com.framewidget.R.drawable.yslt_bt_fanhui_n);
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) { // 表示按返回键
                    mWebView.goBack(); // 后退
                } else {
                    FrgVip.this.finish();
                }
            }
        });
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrgVip.this.finish();
            }
        });
        mWebView.loadUrl(url);
        // 将对象注入windows对象,建立JS与原生的桥接
        mWebView.addJavascriptInterface(new JsBridge(getContext()), "Android");

        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
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

    public class JsBridge {
        private Context context;

        public JsBridge(Context context) {
            this.context = context;
        }

        /**
         * 日志标记
         */
        private final static String TAG = "VIP-JsBridge";

        /**
         * 跳转原生界面
         */
        @JavascriptInterface
        public void gotoPage(String pageName) {
            Log.i(TAG, "gotoPage:" + pageName);
        }

        /**
         * 打开新窗体加载网址
         */
        @JavascriptInterface
        public void gotoPage(String pageName, String url) {
            Log.i(TAG, "gotoPage:" + pageName + " url" + url);
            Intent intent = new Intent("com.vip.demo.PageActivity");
            intent.putExtra("url", url);
            context.startActivity(intent);
        }

        /**
         * 通知事件
         *
         * @param eventName
         */
        @JavascriptInterface
        public void action(String eventName) {
            Log.i(TAG, "action:" + eventName);
            switch (eventName) {
                // 通知会员首页刷新
                case "refresh":
                    break;
            }
        }
    }

    public void loaddata() {

    }


}