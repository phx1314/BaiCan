//
//  FrgRzhPub
//
//  Created by DELL on 2019-09-30 09:02:53
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanapplyAuth;
import com.ntdlg.bc.bean.BeanperformAuth;

import static com.ntdlg.bc.F.applyAuth;
import static com.ntdlg.bc.F.performAuth;
import static com.ntdlg.bc.F.readClassAttr;


public class FrgRzhPub extends BaseFrg {

    public EditText mEditText_code;
    public LinearLayout mLinearLayout_yh;
    public EditText mEditText_code_yh;
    public EditText mEditText_khh;
    public EditText mEditText_yzm;
    public TextView clk_mTextView_get;
    public EditText mEditText_yzhm;
    public TextView mTextView_get;
    public TextView mTextView_shenqing;
    private int times = 60;
    private Handler handler = new Handler();
    private Runnable runnable;
    private String type;

    @Override
    protected void create(Bundle savedInstanceState) {
        type = getActivity().getIntent().getStringExtra("type");
        setContentView(R.layout.frg_rzh_pub);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mEditText_code = (EditText) findViewById(R.id.mEditText_code);
        mLinearLayout_yh = (LinearLayout) findViewById(R.id.mLinearLayout_yh);
        mEditText_code_yh = (EditText) findViewById(R.id.mEditText_code_yh);
        mEditText_khh = (EditText) findViewById(R.id.mEditText_khh);
        mEditText_yzm = (EditText) findViewById(R.id.mEditText_yzm);
        clk_mTextView_get = (TextView) findViewById(R.id.clk_mTextView_get);
        mEditText_yzhm = (EditText) findViewById(R.id.mEditText_yzhm);
        mTextView_get = (TextView) findViewById(R.id.mTextView_get);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);

        clk_mTextView_get.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        mTextView_shenqing.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));

    }

    public void loaddata() {

    }

    private void doTimer() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (times > 0) {
                    times--;
                    clk_mTextView_get.setText("(" + times + ")重新获取验证码");
                    clk_mTextView_get.setTextColor(getContent().getResources().getColor(R.color.gray));
                    clk_mTextView_get.setClickable(false);
                    handler.postDelayed(runnable, 1000);
                } else if (times == 0) {
                    clk_mTextView_get.setClickable(true);
                    clk_mTextView_get.setText("获取验证码");
                    clk_mTextView_get.setTextColor(getContent().getResources().getColor(R.color.Ea));
                }
            }
        };
        handler.post(runnable);
    }

    @Override
    public void onClick(android.view.View v) {

        if (R.id.clk_mTextView_get == v.getId()) {
            if (TextUtils.isEmpty(mEditText_code.getText().toString())) {
                Helper.toast("请输入账号", getContext());
                return;
            }
            if (TextUtils.isEmpty(mEditText_code_yh.getText().toString()) ) {
                Helper.toast("请输入密码", getContext());
                return;
            }
            BeanapplyAuth mBeanapplyAuth = new BeanapplyAuth();
            mBeanapplyAuth.acountName = mEditText_code.getText().toString();
            mBeanapplyAuth.password = mEditText_code_yh.getText().toString();
            mBeanapplyAuth.type = type;
            mBeanapplyAuth.sign = readClassAttr(mBeanapplyAuth);
            loadJsonUrl(applyAuth, new Gson().toJson(mBeanapplyAuth));
        } else if (R.id.mTextView_shenqing == v.getId()) {
            if (mEditText_yzhm.getText().toString().trim().length() != 11) {
                Helper.toast("请输入验证码", getContext());
                return;
            }
            BeanperformAuth mBeanperformAuth = new BeanperformAuth();
            mBeanperformAuth.smsCode = mEditText_yzhm.getText().toString();
            mBeanperformAuth.type = type;
            mBeanperformAuth.sign = readClassAttr(mBeanperformAuth);
            loadJsonUrl(performAuth, new Gson().toJson(mBeanperformAuth));
        }
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(performAuth)) {
            Helper.toast("提交成功", getContext());
            Frame.HANDLES.sentAll("FrgRenzhengxinxi,FrgDshrzh,FrgMe", 0, null);
            finish();
        } else if (methodName.equals(applyAuth)) {
            times = 60;
            doTimer();
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("认证");
    }
}