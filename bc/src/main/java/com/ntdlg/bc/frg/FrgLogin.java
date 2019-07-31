//
//  FrgLogin
//
//  Created by DELL on 2017-05-27 13:57:10
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanFSDXYZM;
import com.ntdlg.bc.bean.BeanLogin;
import com.ntdlg.bc.bean.BeanOtherLogin;
import com.ntdlg.bc.model.ModelFSDXYZM;
import com.ntdlg.bc.model.ModelLogin;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import static com.ntdlg.bc.F.check;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.login;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.sendSms;


public class FrgLogin extends BaseFrg {

    public EditText mEditText_phone;
    public EditText mEditText_code;
    public ImageView clk_ImageView_qq;
    public ImageView clk_ImageView_wx;
    public TextView mTextView_lg;
    public String otherNo;
    public String type;
    public TextView clk_mTextView_get;
    private int times = 60;
    private Handler handler;
    private Runnable runnable;
//    18217666592   18217666591

    //13918402027 peter

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_login);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 0:
                this.finish();
                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mEditText_phone = (EditText) findViewById(R.id.mEditText_phone);
        mEditText_code = (EditText) findViewById(R.id.mEditText_code);
        clk_ImageView_qq = (ImageView) findViewById(R.id.clk_ImageView_qq);
        clk_ImageView_wx = (ImageView) findViewById(R.id.clk_ImageView_wx);
        mTextView_lg = (TextView) findViewById(R.id.mTextView_lg);
        clk_mTextView_get = (TextView) findViewById(R.id.clk_mTextView_get);

        clk_ImageView_qq.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_ImageView_wx.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        mTextView_lg.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mTextView_get.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));

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

    public void loaddata() {
    }

    /**
     * 第三方登�?--qq
     */
    public void loginWithQQ() {
        UMShareAPI.get(getActivity()).doOauthVerify(getActivity(),
                SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onError(SHARE_MEDIA arg0, int arg1,
                                        Throwable arg2) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA arg0, int arg1,
                                           Map<String, String> data) {
                        String opendid = data.get("openid");
                        String token = data.get("access_token");// outInfoe
                        BeanOtherLogin mBeanLogin = new BeanOtherLogin();
                        type = mBeanLogin.type = "3";
                        otherNo = mBeanLogin.accountNo = token;
                        mBeanLogin.sign = readClassAttr(mBeanLogin);
                        loadJsonUrl(check, new Gson().toJson(mBeanLogin));
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA arg0, int arg1) {

                    }
                });

    }

    @Override
    public void onClick(android.view.View v) {
        if (R.id.clk_ImageView_qq == v.getId()) {
            loginWithQQ();
        } else if (R.id.clk_mTextView_get == v.getId()) {
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
        } else if (R.id.clk_ImageView_wx == v.getId()) {
            UMShareAPI.get(getActivity()).doOauthVerify(getActivity(),
                    SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                        @Override
                        public void onError(SHARE_MEDIA arg0, int arg1,
                                            Throwable arg2) {

                        }

                        @Override
                        public void onComplete(SHARE_MEDIA arg0, int arg1,
                                               Map<String, String> data) {
                            String opendid = data.get("openid");
                            String token = data.get("access_token");// outInfoe
                            BeanOtherLogin mBeanLogin = new BeanOtherLogin();
                            type = mBeanLogin.type = "2";
                            otherNo = mBeanLogin.accountNo = token;
                            mBeanLogin.sign = readClassAttr(mBeanLogin);
                            loadJsonUrl(check, new Gson().toJson(mBeanLogin));
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA arg0, int arg1) {

                        }
                    });
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
            mBeanLogin.type = "1";
            mBeanLogin.sign = readClassAttr(mBeanLogin);
            loadJsonUrl(login, new Gson().toJson(mBeanLogin));
        }
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(login)) {
            ModelLogin mModelLogin = (ModelLogin) json2Model(content, ModelLogin.class);
            F.Login(mModelLogin.accountId, mModelLogin.token, mModelLogin.reftoken);
            Helper.toast("登录成功", getContext());
            Frame.HANDLES.sentAll("FrgWode,FrgRzh", 0, null);
            Frame.HANDLES.sentAll("FrgShouye", 1, null);
            this.finish();
        } else if (methodName.equals(check)) {
            ModelLogin mModelLogin = (ModelLogin) json2Model(content, ModelLogin.class);
            if (mModelLogin.result.equals("1")) {//不需要
                F.Login(mModelLogin.accountId, mModelLogin.token, mModelLogin.reftoken);
                Frame.HANDLES.sentAll("FrgWode,FrgRzh", 0, null);
                Frame.HANDLES.sentAll("FrgShouye", 1, null);
                Helper.toast("登录成功", getContext());
                this.finish();
            } else {
                Helper.startActivity(getContext(), FrgBdZhanghao.class, TitleAct.class, "otherNo", otherNo, "type", type);
            }
        } else if (methodName.equals(sendSms)) {
            ModelFSDXYZM mModelLogin = (ModelFSDXYZM) json2Model(content, ModelFSDXYZM.class);
            times = 60;
            doTimer();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("登录");
    }


}