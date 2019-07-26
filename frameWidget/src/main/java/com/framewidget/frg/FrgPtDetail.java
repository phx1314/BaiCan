//
//  FrgPtDetail
//
//  Created by Administrator on 2015-01-28 13:49:35
//  Copyright (c) Administrator All rights reserved.

/**

 */

package com.framewidget.frg;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framewidget.R;


public class FrgPtDetail extends BaseFrg {

    public WebView mWebView;
    public TextView tv_close;
    public TextView tv_title;
    public ImageButton btn_left;
    public LinearLayout mLinearLayout_back;
    public String url = "";
    public String title = "";
    public boolean hasTextSize = false;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_pt_detail);
        url = getActivity().getIntent().getStringExtra("url");
        title = getActivity().getIntent().getStringExtra("title");
        hasTextSize = getActivity().getIntent().getBooleanExtra("hasTextSize", false);
        initView();
        loaddata();
    }

    private void initView() {
        mLinearLayout_back = (LinearLayout) findViewById(R.id.mLinearLayout_back);
        tv_close = (TextView) findViewById(R.id.tv_close);
        tv_title = (TextView) findViewById(R.id.tv_title);
        btn_left = (ImageButton) findViewById(R.id.btn_left);
        mWebView = (WebView) findViewById(R.id.mWebView);

    }

    public void loaddata() {
        btn_left.setImageResource(R.drawable.yslt_bt_fanhui_n);
        btn_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) { // 表示按返回键
                    mWebView.goBack(); // 后退
                } else {
                    FrgPtDetail.this.finish();
                }
            }
        });
        tv_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FrgPtDetail.this.finish();
            }
        });
        tv_title.setText(title);
        mWebView.loadUrl(url);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        if (hasTextSize) {
            mWebView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setOnKeyListener(new OnKeyListener() {
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
    public void onClick(View arg0) {

    }


}