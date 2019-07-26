//
//  FrgGerenziliao
//
//  Created by DELL on 2017-06-23 09:32:59
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.model.ModelGRXYRZXX;

import static com.ntdlg.bc.F.getPlatform;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.readClassAttr;


public class FrgGerenziliao extends BaseFrg {

    public TextView mImageView_1;
    public TextView mImageView_2;
    public TextView mImageView_3;
    public TextView mImageView_4;
    public TextView mTextView_1;
    public TextView mTextView_2;
    public TextView mTextView_3;
    public LinearLayout mLinearLayout_1;
    public LinearLayout mLinearLayout_2;
    public LinearLayout mLinearLayout_3;


    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_gerenziliao);
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
        mImageView_1 = (TextView) findViewById(R.id.mImageView_1);
        mImageView_2 = (TextView) findViewById(R.id.mImageView_2);
        mImageView_3 = (TextView) findViewById(R.id.mImageView_3);
        mImageView_4 = (TextView) findViewById(R.id.mImageView_4);
        mTextView_1 = (TextView) findViewById(R.id.mTextView_1);
        mTextView_2 = (TextView) findViewById(R.id.mTextView_2);
        mTextView_3 = (TextView) findViewById(R.id.mTextView_3);
        mLinearLayout_1 = (LinearLayout) findViewById(R.id.mLinearLayout_1);
        mLinearLayout_2 = (LinearLayout) findViewById(R.id.mLinearLayout_2);
        mLinearLayout_3 = (LinearLayout) findViewById(R.id.mLinearLayout_3);
        mLinearLayout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.startActivity(getContext(), FrgJbxx.class, TitleAct.class);
            }
        });
        mLinearLayout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.startActivity(getContext(), FrgGzxx.class, TitleAct.class);
            }
        });
        mLinearLayout_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.startActivity(getContext(), FrgLxr.class, TitleAct.class);
            }
        });

    }

    public void loaddata() {
        BeanBase mBeanBase = new BeanBase();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(getPlatform, new Gson().toJson(mBeanBase));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(getPlatform)) {
            ModelGRXYRZXX mModelGRXYRZXX = (ModelGRXYRZXX) json2Model(content, ModelGRXYRZXX.class);
            if (mModelGRXYRZXX.isBasicAuth.equals("1")) {
                mTextView_1.setText("已认证");
                mTextView_1.setTextColor(getResources().getColor(R.color.A));
            } else {
                mTextView_1.setText("未认证");
                mTextView_1.setTextColor(getResources().getColor(R.color.gray));
            }
            if (mModelGRXYRZXX.isProfessionAuth.equals("1")) {
                mTextView_2.setText("已认证");
                mTextView_2.setTextColor(getResources().getColor(R.color.A));
            } else {
                mTextView_2.setText("未认证");
                mTextView_2.setTextColor(getResources().getColor(R.color.gray));
            }
            if (mModelGRXYRZXX.isLinkMainAuth.equals("1")) {
                mTextView_3.setText("已认证");
                mTextView_3.setTextColor(getResources().getColor(R.color.A));
            } else {
                mTextView_3.setText("未认证");
                mTextView_3.setTextColor(getResources().getColor(R.color.gray));
            }
            if (mModelGRXYRZXX.isBasicAuth.equals("1") && mModelGRXYRZXX.isProfessionAuth.equals("1") && mModelGRXYRZXX.isLinkMainAuth.equals("1")) {
                mImageView_1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.personal1hover, 0, 0);
            } else {
                mImageView_1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.personal1, 0, 0);
            }
            if (mModelGRXYRZXX.isOperatorAuth.equals("1")) {
                mImageView_2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.phone2hover, 0, 0);
            } else {
                mImageView_2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.phone2, 0, 0);
            }
            if (mModelGRXYRZXX.isNameAuth.equals("1")) {
                mImageView_3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.identity3hover, 0, 0);
            } else {
                mImageView_3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.identity3, 0, 0);
            }
            if (mModelGRXYRZXX.isTaobaoAuth.equals("1") || mModelGRXYRZXX.isJingdongAuth.equals("1")) {
                mImageView_4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.bankcard4hover, 0, 0);
            } else {
                mImageView_4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.bankcard4, 0, 0);
            }

        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("个人资料");
    }
}