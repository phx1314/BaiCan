//
//  FrgHelp
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ntdlg.bc.R;


public class FrgHelp extends BaseFrg {

    public RelativeLayout clk_mRelativeLayout_1;
    public TextView mTextView_1;
    public ImageView mImageView_1;
    public RelativeLayout clk_mRelativeLayout_2;
    public TextView mTextView_2;
    public ImageView mImageView_2;
    public RelativeLayout clk_mRelativeLayout_3;
    public TextView mTextView_3;
    public ImageView mImageView_3;
    public WebView mWebView;


    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_help);
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
        clk_mRelativeLayout_3 = (RelativeLayout) findViewById(R.id.clk_mRelativeLayout_3);
        mTextView_3 = (TextView) findViewById(R.id.mTextView_3);
        mImageView_3 = (ImageView) findViewById(R.id.mImageView_3);
        mWebView = (WebView) findViewById(R.id.mWebView);

        clk_mRelativeLayout_1.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mRelativeLayout_2.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mRelativeLayout_3.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));

    }

    public void loaddata() {

    }

    @Override
    public void onClick(android.view.View v) {
        if (R.id.clk_mRelativeLayout_1 == v.getId()) {

        } else if (R.id.clk_mRelativeLayout_2 == v.getId()) {

        } else if (R.id.clk_mRelativeLayout_3 == v.getId()) {

        }
    }

}