//
//  FrgBdZhanghao
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanFSDXYZM;
import com.ntdlg.bc.bean.BeanLogin;
import com.ntdlg.bc.model.ModelFSDXYZM;
import com.ntdlg.bc.model.ModelLogin;

import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.login;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.sendSms;


public class FrgBdZhanghao extends BaseFrg {
    //    http://blog.csdn.net/ys408973279/article/details/50350313/
    public EditText mEditText_phone;
    public EditText mEditText_code;
    public String otherNo;
    public String type;
    public com.mdx.framework.widget.MImageView mMImageView;
    public TextView clk_mTextView_get;
    public TextView mTextView_lg;

    private int times = 60;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void create(Bundle savedInstanceState) {
        otherNo = getActivity().getIntent().getStringExtra("otherNo");
        type = getActivity().getIntent().getStringExtra("type");
        setContentView(R.layout.frg_bd_zhanghao);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mEditText_phone = (EditText) findViewById(R.id.mEditText_phone);
        mEditText_code = (EditText) findViewById(R.id.mEditText_code);
        mMImageView = (com.mdx.framework.widget.MImageView) findViewById(R.id.mMImageView);
        clk_mTextView_get = (TextView) findViewById(R.id.clk_mTextView_get);
        mTextView_lg = (TextView) findViewById(R.id.mTextView_lg);
        clk_mTextView_get.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        mTextView_lg.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
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
            if (TextUtils.isEmpty(mEditText_phone.getText().toString())) {
                Helper.toast("请输入手机号", getContext());
                return;
            }
            if (mEditText_phone.getText().toString().trim().length() != 11) {
                Helper.toast("请输入正确的手机号", getContext());
                return;
            }
            BeanFSDXYZM mBeanFSDXYZM = new BeanFSDXYZM();
//            mBeanFSDXYZM.type = "20";
            mBeanFSDXYZM.phone = mEditText_phone.getText().toString();
            mBeanFSDXYZM.sign = readClassAttr(mBeanFSDXYZM);
            loadJsonUrl(sendSms, new Gson().toJson(mBeanFSDXYZM));
        } else if (R.id.mTextView_lg == v.getId()) {
            if (TextUtils.isEmpty(mEditText_phone.getText().toString())) {
                Helper.toast("请输入手机号", getContext());
                return;
            }
            if (mEditText_phone.getText().toString().trim().length() != 11) {
                Helper.toast("请输入正确的手机号", getContext());
                return;
            }
            if (TextUtils.isEmpty(mEditText_code.getText().toString())) {
                Helper.toast("请输入验证码", getContext());
                return;
            }
            BeanLogin mBeanLogin = new BeanLogin();
            mBeanLogin.phone = mEditText_phone.getText().toString();
            mBeanLogin.code = mEditText_code.getText().toString();
            mBeanLogin.type = type;
            mBeanLogin.otherNo = otherNo;
            mBeanLogin.sign = readClassAttr(mBeanLogin);
            loadJsonUrl(login, new Gson().toJson(mBeanLogin));
        }
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(login)) {
            ModelLogin mModelLogin = (ModelLogin) json2Model(content, ModelLogin.class);
            F.Login(mModelLogin.accountId, mModelLogin.token, mModelLogin.reftoken);
            Frame.HANDLES.sentAll("FrgWode,FrgLogin,FrgRenzhengxinxi", 0, null);
            Frame.HANDLES.sentAll("FrgShouye", 1, null);
            Helper.toast("登录成功", getContext());
            this.finish();
        } else if (methodName.equals(sendSms)) {
            ModelFSDXYZM mModelLogin = (ModelFSDXYZM) json2Model(content, ModelFSDXYZM.class);
            times = 60;
            doTimer();
        }
    }

    public void loaddata() {

    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("绑定账号");
    }
}