//
//  FrgBdYhk
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBK;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.model.BaseData;
import com.ntdlg.bc.model.ModelYHList;

import java.util.ArrayList;
import java.util.List;

import static com.ntdlg.bc.F.bank;
import static com.ntdlg.bc.F.bankBind;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.readClassAttr;


public class FrgBdYhk extends BaseFrg {

    public EditText mEditText_code;
    public ImageView mImageView_pzh;
    public TextView mEditText_code_yh;
    public EditText mEditText_khh;
    public EditText mEditText_phone;
    public EditText mEditText_yzhm;
    public TextView mTextView_get;
    public TextView mTextView_shenqing;
    public LinearLayout mLinearLayout_yh;
    public List<BaseData> list = new ArrayList<>();
    public BeanBK mBeanBK = new BeanBK();
    public String from;

    @Override
    protected void create(Bundle savedInstanceState) {
        from = getActivity().getIntent().getStringExtra("from");
        setContentView(R.layout.frg_bd_yhk);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 101:
                BaseData item = (BaseData) obj;
                mEditText_code_yh.setText(item.value);
                mBeanBK.bankId = item.code;
                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mEditText_code = (EditText) findViewById(R.id.mEditText_code);
        mImageView_pzh = (ImageView) findViewById(R.id.mImageView_pzh);
        mEditText_code_yh = (TextView) findViewById(R.id.mEditText_code_yh);
        mEditText_khh = (EditText) findViewById(R.id.mEditText_khh);
        mEditText_phone = (EditText) findViewById(R.id.mEditText_phone);
        mEditText_yzhm = (EditText) findViewById(R.id.mEditText_yzhm);
        mTextView_get = (TextView) findViewById(R.id.mTextView_get);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);
        mLinearLayout_yh = (LinearLayout) findViewById(R.id.mLinearLayout_yh);
        mLinearLayout_yh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.startActivity(getContext(), FrgPubList.class, TitleAct.class, "from", "FrgBdYhk", "type", 101, "data", list);
            }
        });

    }

    public void loaddata() {
        BeanBase mBeanBase = new BeanBase();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(bank, new Gson().toJson(mBeanBase));
        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEditText_code.getText().toString().trim())) {
                    Helper.toast("请输入卡号", getContext());
                    return;
                }
                if (!F.checkBankCard(mEditText_code.getText().toString().trim())) {
                    Helper.toast("请输入有效卡号", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mEditText_code_yh.getText().toString().trim())) {
                    Helper.toast("请选择银行", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mEditText_phone.getText().toString().trim())) {
                    Helper.toast("请输入预留手机号", getContext());
                    return;
                }
                if (mEditText_phone.getText().toString().trim().length() != 11) {
                    Helper.toast("请输入正确的手机号", getContext());
                    return;
                }
                mBeanBK.bankNo = mEditText_code.getText().toString().trim();
                mBeanBK.phone = mEditText_phone.getText().toString().trim();
//                mBeanBK.securityCode = mBeanBK.bankNo.substring(mBeanBK.bankNo.length() - 3, mBeanBK.bankNo.length());
                mBeanBK.sign = readClassAttr(mBeanBK);
                loadJsonUrl(bankBind, new Gson().toJson(mBeanBK));
            }
        });
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(bankBind)) {
            Helper.toast("绑定成功", getContext());
            Frame.HANDLES.sentAll("FrgWodeYhk,FrgRenzhengxinxi", 0, null);
            if (!TextUtils.isEmpty(from))
                Frame.HANDLES.sentAll(from, 1, null);
            this.finish();
        } else if (methodName.equals(bank)) {
            ModelYHList mModelYHList = (ModelYHList) json2Model(content, ModelYHList.class);
            for (ModelYHList.RecordsBean mRecordsBean : mModelYHList.records) {
                BaseData mBaseData = new BaseData();
                mBaseData.code = mRecordsBean.bankId;
                mBaseData.value = mRecordsBean.bankName;
                list.add(mBaseData);
            }
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("绑定银行卡");
    }
}