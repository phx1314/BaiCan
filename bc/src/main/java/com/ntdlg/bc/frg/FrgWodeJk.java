//
//  FrgWodeJk
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.model.ModelZHZX;


public class FrgWodeJk extends BaseFrg {

    public RelativeLayout clk_mRelativeLayout_1;
    public TextView mTextView_1;
    public ImageView mImageView_1;
    public RelativeLayout clk_mRelativeLayout_2;
    public TextView mTextView_2;
    public ImageView mImageView_2;
    public LinearLayout mLinearLayout_content;
    public Fragment fragment1;
    public Fragment fragment2;
    public static ModelZHZX mModelZHZX;

    @Override
    protected void create(Bundle savedInstanceState) {
        mModelZHZX = (ModelZHZX) getActivity().getIntent().getSerializableExtra("mModelZHZX");
        setContentView(R.layout.frg_wode_jk);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 0:
//                mHeadlayout.setRText("查看合同");
//                mHeadlayout.setRightOnclicker(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Helper.startActivity(getContext(), FrgHt.class, TitleAct.class);
//                    }
//                });
                break;
        }
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
        mLinearLayout_content = (LinearLayout) findViewById(R.id.mLinearLayout_content);

        clk_mRelativeLayout_1.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mRelativeLayout_2.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        fragment1 = new FrgWodeJk1();
        fragment2 = new FrgWodeJk2();
    }

    private void chageFrgment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.mLinearLayout_content, fragment);
        transaction.commit();
    }

    public void loaddata() {
        chageFrgment(fragment1);
    }

    @Override
    public void onClick(android.view.View v) {
        if (R.id.clk_mRelativeLayout_1 == v.getId()) {
            chageFrgment(fragment1);
            mTextView_1.setTextColor(getResources().getColor(R.color.A));
            mImageView_1.setVisibility(View.VISIBLE);
            mImageView_2.setVisibility(View.GONE);
            mTextView_2.setTextColor(getResources().getColor(R.color.white));
//            mHeadlayout.setRightShow();
        } else if (R.id.clk_mRelativeLayout_2 == v.getId()) {
            chageFrgment(fragment2);
            mTextView_1.setTextColor(getResources().getColor(R.color.white));
            mTextView_2.setTextColor(getResources().getColor(R.color.A));
            mImageView_1.setVisibility(View.GONE);
            mImageView_2.setVisibility(View.VISIBLE);
//            mHeadlayout.setRightGone();
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("我的借款");
        mHeadlayout.setRText("查看合同");
        mHeadlayout.setRightOnclicker(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.startActivity(getContext(), FrgHt.class, TitleAct.class);
            }
        });
    }
}