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
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framewidget.F;
import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanapplyAuth;
import com.ntdlg.bc.bean.BeanperformAuth;
import com.ntdlg.bc.model.ModelPub;

import org.json.JSONObject;

import static com.ntdlg.bc.F.applyAuth;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.performAuth;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.R.id.clk_mTextView_get;
import static com.ntdlg.bc.R.id.mEditText_khh;
import static com.ntdlg.bc.R.id.mEditText_yzhm;
import static com.ntdlg.bc.R.id.mEditText_yzm;
import static com.umeng.socialize.utils.DeviceConfig.context;
import static java.security.AccessController.getContext;


public class FrgRzhPub extends BaseFrg {

    public EditText mEditText_code;
    public LinearLayout mLinearLayout_yh;
    public EditText mEditText_code_yh;
    public TextView mTextView_shenqing;
    public TextView mTextView_name;
    public TextView mTextView_pass;
    public TextView mTextView_ts;
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
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);
        mTextView_name = (TextView) findViewById(R.id.mTextView_name);
        mTextView_pass = (TextView) findViewById(R.id.mTextView_pass);
        mTextView_ts = (TextView) findViewById(R.id.mTextView_ts);

        mTextView_shenqing.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));

    }

    public void loaddata() {
        if (type.equals("03")) {
            mTextView_name.setText("手机号码");
            mTextView_pass.setText("服务密码");
            mEditText_code.setHint("请输入手机号码");
            mEditText_code_yh.setHint("请输入服务密码");
        } else {
            mTextView_ts.setText("");
        }
    }


    @Override
    public void onClick(android.view.View v) {
        if (R.id.mTextView_shenqing == v.getId()) {
            if (TextUtils.isEmpty(mEditText_code.getText().toString())) {
                Helper.toast(mEditText_code.getHint().toString(), getContext());
                return;
            }
            if (TextUtils.isEmpty(mEditText_code_yh.getText().toString())) {
                Helper.toast(mEditText_code_yh.getHint().toString(), getContext());
                return;
            }

            BeanapplyAuth mBeanapplyAuth = new BeanapplyAuth();
            mBeanapplyAuth.accountName = mEditText_code.getText().toString();
            mBeanapplyAuth.password = mEditText_code_yh.getText().toString();
            mBeanapplyAuth.type = type;
            mBeanapplyAuth.sign = readClassAttr(mBeanapplyAuth);
            loadJsonUrl(applyAuth, new Gson().toJson(mBeanapplyAuth));
        }
    }

    @Override
    public void onFail(String methodName, String content) {
        try {
            if (!TextUtils.isEmpty(content)) {
                JSONObject mJSONObject = new JSONObject(content);
                if (mJSONObject.getString("errorcode").equals("5100")) {
                    ModelPub mModelPub = (ModelPub) json2Model(content, ModelPub.class);
                    if (mModelPub.nextProcess != null) {
                        Helper.startActivity(getActivity(), FrgRzhPubNext.class, TitleAct.class, "mModelPub", mModelPub, "type", type);
                    } else {
                        Helper.toast("nextProcess数据返回空", getContext());
                    }
                } else if (mJSONObject.getString("errorcode").equals("5000")) {
                    Helper.startActivity(getActivity(), FrgRzhPubLaqu.class, TitleAct.class, "type", type, "smsCode", "", "imgCode", "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(applyAuth)) {
            Helper.toast("认证成功", getContext());
            Frame.HANDLES.sentAll("FrgRenzhengxinxi,FrgDshrzh,FrgMe", 0, null);
            finish();
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        if (type.equals("03")) {
            mHeadlayout.setTitle("运营商认证");
        } else if (type.equals("11")) {
            mHeadlayout.setTitle("淘宝认证");
        }
    }
}