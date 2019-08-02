//
//  FrgDshrzh
//
//  Created by DELL on 2017-06-23 09:55:39
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.moxie.client.model.MxParam;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.model.ModelGRXYRZXX;

import static com.ntdlg.bc.F.getPlatform;
import static com.ntdlg.bc.F.jingdongAuth;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.rZhengWb;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.taobaoAuth;


public class FrgDshrzh extends BaseFrg {

    public TextView mTextView_1;
    public TextView mTextView_2;
    public TextView mTextView_shenqing;
    public ModelGRXYRZXX mModelGRXYRZXX;
    public LinearLayout mLinearLayout_1;
    public LinearLayout mLinearLayout_2;
    public String type = "1";
    public String remark;
    public TextView mTextView_remark;

    @Override
    protected void create(Bundle savedInstanceState) {
        remark = getActivity().getIntent().getStringExtra("remark");
        setContentView(R.layout.frg_dshrzh);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mTextView_1 = (TextView) findViewById(R.id.mTextView_1);
        mTextView_2 = (TextView) findViewById(R.id.mTextView_2);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);
        mLinearLayout_1 = (LinearLayout) findViewById(R.id.mLinearLayout_1);
        mLinearLayout_2 = (LinearLayout) findViewById(R.id.mLinearLayout_2);
        mTextView_remark = (TextView) findViewById(R.id.mTextView_remark);
        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrgDshrzh.this.finish();
            }
        });
        mLinearLayout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mModelGRXYRZXX.isTaobaoAuth.equals("1")) {
                    Helper.toast("您已认证", getContext());
                    return;
                }
                type = "1";
                rZhengWb(getActivity(), MxParam.PARAM_TASK_TAOBAO,FrgDshrzh.this);
            }
        });
        mLinearLayout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mModelGRXYRZXX.isJingdongAuth.equals("1")) {
                    Helper.toast("您已认证", getContext());
                    return;
                }
                type = "2";
                rZhengWb(getActivity(), MxParam.PARAM_TASK_JINGDONG,FrgDshrzh.this);
            }
        });
    }

    public void loaddata() {
        if (!TextUtils.isEmpty(remark)) {
            mTextView_remark.setText(remark);
        }
        BeanBase mBeanBase = new BeanBase();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(getPlatform, new Gson().toJson(mBeanBase));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(taobaoAuth) || methodName.equals(jingdongAuth)) {
            Frame.HANDLES.sentAll("FrgRenzhengxinxi,FrgRzh", 0, null);
            loaddata();
        } else if (methodName.equals(getPlatform)) {
            mModelGRXYRZXX = (ModelGRXYRZXX) json2Model(content, ModelGRXYRZXX.class);
            if (mModelGRXYRZXX.isTaobaoAuth.equals("1")) {
                mTextView_1.setText("已认证");
                mTextView_1.setTextColor(getResources().getColor(R.color.A));
            } else {
                mTextView_1.setText("未认证");
                mTextView_1.setTextColor(getResources().getColor(R.color.shouye_red));
            }
            if (mModelGRXYRZXX.isJingdongAuth.equals("1")) {
                mTextView_2.setText("已认证");
                mTextView_2.setTextColor(getResources().getColor(R.color.A));
            } else {
                mTextView_2.setText("未认证");
                mTextView_2.setTextColor(getResources().getColor(R.color.shouye_red));
            }
        }
    }


    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("电商认证");
    }
}