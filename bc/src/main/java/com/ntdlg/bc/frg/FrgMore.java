//
//  FrgMore
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framewidget.frg.FrgPtDetail;
import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.NoTitleAct;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.config.BaseConfig;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;

import static com.ntdlg.bc.F.logout;
import static com.ntdlg.bc.F.readClassAttr;


public class FrgMore extends BaseFrg {

    public LinearLayout mLinearLayout_kf;
    public LinearLayout mLinearLayout_help;
    public LinearLayout mLinearLayout_xsh;
    public LinearLayout mLinearLayout_fk;
    public TextView mTextView_more;
    public TextView clk_mTextView_next;


    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_more);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mLinearLayout_kf = (LinearLayout) findViewById(R.id.mLinearLayout_kf);
        mLinearLayout_help = (LinearLayout) findViewById(R.id.mLinearLayout_help);
        mLinearLayout_xsh = (LinearLayout) findViewById(R.id.mLinearLayout_xsh);
        mLinearLayout_fk = (LinearLayout) findViewById(R.id.mLinearLayout_fk);
        mTextView_more = (TextView) findViewById(R.id.mTextView_more);
        clk_mTextView_next = (TextView) findViewById(R.id.clk_mTextView_next);

        clk_mTextView_next.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        mLinearLayout_kf.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        mLinearLayout_help.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        mLinearLayout_xsh.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        mLinearLayout_fk.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));

    }

    public void loaddata() {

    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(logout)) {
            F.Login("", "", "");
            Frame.HANDLES.sentAll("FrgWode,FrgRzh", 0, null);
            Frame.HANDLES.sentAll("FrgShouye", 1, null);
            this.finish();
        }
    }

    @Override
    public void onClick(android.view.View v) {
        if (R.id.clk_mTextView_next == v.getId()) {
            BeanBase mBeanBase = new BeanBase();
            mBeanBase.sign = readClassAttr(mBeanBase);
            loadJsonUrl(logout, new Gson().toJson(mBeanBase));
        } else if (R.id.mLinearLayout_kf == v.getId()) {
            Helper.startActivity(getContext(), FrgPtDetail.class, NoTitleAct.class, "url", BaseConfig.getUri()+"/other/show.html?type=" + 4, "title", "客服");
        } else if (R.id.mLinearLayout_help == v.getId()) {
            Helper.startActivity(getContext(), FrgPtDetail.class, NoTitleAct.class, "url", BaseConfig.getUri()+"/other/show.html?type=" + 2, "title", "帮助");
        } else if (R.id.mLinearLayout_xsh == v.getId()) {
            Helper.startActivity(getContext(), FrgPtDetail.class, NoTitleAct.class, "url", BaseConfig.getUri()+"/other/show.html?type=" + 1, "title", "新手指引");
        } else if (R.id.mLinearLayout_fk == v.getId()) {
            Helper.startActivity(getContext(), FrgYijianFk.class, TitleAct.class);
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("更多");
    }
}