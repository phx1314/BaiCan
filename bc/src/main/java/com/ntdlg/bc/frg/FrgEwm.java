//
//  FrgEwm
//
//  Created by DELL on 2019-09-06 13:14:58
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;

import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;


public class FrgEwm extends BaseFrg {

    public String url;
    public com.mdx.framework.widget.MImageView mMImageView;

    @Override
    protected void create(Bundle savedInstanceState) {
        url = getActivity().getIntent().getStringExtra("url");
        setContentView(R.layout.frg_ewm);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {


        mMImageView = (com.mdx.framework.widget.MImageView) findViewById(R.id.mMImageView);
    }

    public void loaddata() {
        mMImageView.setObj(url);
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("二维码");
    }
}