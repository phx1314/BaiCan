//
//  FrgYijianFk
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;


public class FrgYijianFk extends BaseFrg {

    public EditText mEditText;
    public TextView clk_mTextView_next;


    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_yijian_fk);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mEditText = (EditText) findViewById(R.id.mEditText);
        clk_mTextView_next = (TextView) findViewById(R.id.clk_mTextView_next);

        clk_mTextView_next.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));

    }

    public void loaddata() {

    }

    @Override
    public void onClick(android.view.View v) {
        if (R.id.clk_mTextView_next == v.getId()) {
            FrgYijianFk.this.finish();
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("意见反馈");
    }
}