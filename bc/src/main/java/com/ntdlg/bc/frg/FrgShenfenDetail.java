//
//  FrgShenfenDetail
//
//  Created by DELL on 2017-06-30 16:24:52
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanGetSFZXX;
import com.ntdlg.bc.model.ModelGetSFZXX;

import static com.ntdlg.bc.F.getUserCard;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.readClassAttr;


public class FrgShenfenDetail extends BaseFrg {


    public TextView mEditText_name;
    public TextView mEditText_code;
    public TextView mEditText_name2;
    public TextView mEditText_code2;
    public TextView mTextView_shenqing;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_shenfen_detail);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {


        mEditText_name = (TextView) findViewById(R.id.mEditText_name);
        mEditText_code = (TextView) findViewById(R.id.mEditText_code);
        mEditText_name2 = (TextView) findViewById(R.id.mEditText_name2);
        mEditText_code2 = (TextView) findViewById(R.id.mEditText_code2);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);
        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrgShenfenDetail.this.finish();
            }
        });
    }

    public void loaddata() {
        BeanGetSFZXX mBeanGetSFZXX = new BeanGetSFZXX();
        mBeanGetSFZXX.sign = readClassAttr(mBeanGetSFZXX);
        loadJsonUrl(getUserCard, new Gson().toJson(mBeanGetSFZXX));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(getUserCard)) {
            ModelGetSFZXX mModelGetSFZXX = (ModelGetSFZXX) json2Model(content, ModelGetSFZXX.class);
            mEditText_name.setText(mModelGetSFZXX.userName);
            mEditText_code.setText(mModelGetSFZXX.userCard);
            mEditText_name2.setText(mModelGetSFZXX.issueAuthority);
            mEditText_code2.setText(mModelGetSFZXX.vaildPriod);
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("身份信息");
    }
}