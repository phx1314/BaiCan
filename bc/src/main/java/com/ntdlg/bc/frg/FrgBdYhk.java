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
import android.os.Handler;
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
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanFSDXYZM;
import com.ntdlg.bc.bean.BeanbankBindOne;
import com.ntdlg.bc.bean.BeanbankBindTwo;
import com.ntdlg.bc.model.BaseData;
import com.ntdlg.bc.model.ModelYHList;
import com.ntdlg.bc.model.ModelbankBindOne;

import java.util.ArrayList;
import java.util.List;

import static com.ntdlg.bc.F.bank;
import static com.ntdlg.bc.F.bankBindOne;
import static com.ntdlg.bc.F.bankBindTwo;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.sendSms;


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
    public BeanbankBindTwo mBeanbankBindTwo = new BeanbankBindTwo();
    public String from;
    public EditText mEditText_yzm;
    public TextView clk_mTextView_get;
    private int times = 60;
    private Handler handler;
    private Runnable runnable;
    private ModelbankBindOne mModelbankBindOne;

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
                mBeanbankBindTwo.bankId = item.code;
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
        mEditText_yzm = (EditText) findViewById(R.id.mEditText_yzm);
        clk_mTextView_get = (TextView) findViewById(R.id.clk_mTextView_get);
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
                if (TextUtils.isEmpty(mEditText_yzm.getText().toString().trim())) {
                    Helper.toast("请输入验证码", getContext());
                    return;
                }
                mBeanbankBindTwo.bankNo = mEditText_code.getText().toString().trim();
                mBeanbankBindTwo.phone = mEditText_phone.getText().toString().trim();
                mBeanbankBindTwo.txId = mModelbankBindOne.txId;
                mBeanbankBindTwo.code = mEditText_yzm.getText().toString().trim();
                mBeanbankBindTwo.sign = readClassAttr(mBeanbankBindTwo);
                loadJsonUrl(bankBindTwo, new Gson().toJson(mBeanbankBindTwo));
            }
        });
        clk_mTextView_get.setOnClickListener(new View.OnClickListener() {
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
                if (TextUtils.isEmpty(mEditText_phone.getText().toString())) {
                    Helper.toast("请输入手机号", getContext());
                    return;
                }
                if (mEditText_phone.getText().toString().trim().length() != 11) {
                    Helper.toast("请输入正确的手机号", getContext());
                    return;
                }
                BeanbankBindOne mBeanbankBindOne = new BeanbankBindOne();
                mBeanbankBindOne.bankNo = mEditText_code.getText().toString().trim();
                mBeanbankBindOne.phone = mEditText_phone.getText().toString().trim();
                mBeanbankBindOne.sign = readClassAttr(mBeanbankBindOne);
                loadJsonUrl(bankBindOne, new Gson().toJson(mBeanbankBindOne));

//                BeanFSDXYZM mBeanFSDXYZM = new BeanFSDXYZM();
////                mBeanFSDXYZM.type = "20";
//                mBeanFSDXYZM.phone = mEditText_phone.getText().toString();
//                mBeanFSDXYZM.sign = readClassAttr(mBeanFSDXYZM);
//                loadJsonUrl(sendSms, new Gson().toJson(mBeanFSDXYZM));
            }
        });
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
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(bank)) {
            ModelYHList mModelYHList = (ModelYHList) json2Model(content, ModelYHList.class);
            for (ModelYHList.RecordsBean mRecordsBean : mModelYHList.records) {
                BaseData mBaseData = new BaseData();
                mBaseData.code = mRecordsBean.bankId;
                mBaseData.value = mRecordsBean.bankName;
                list.add(mBaseData);
            }
        } else if (methodName.equals(sendSms)) {
            times = 60;
            doTimer();
        } else if (methodName.equals(bankBindOne)) {
            mModelbankBindOne = (ModelbankBindOne) json2Model(content, ModelbankBindOne.class);
            times = 60;
            doTimer();
        } else if (methodName.equals(bankBindTwo)) {
            Helper.toast("绑定成功", getContext());
            Frame.HANDLES.sentAll("FrgWodeYhk,FrgRenzhengxinxi", 0, null);
            if (!TextUtils.isEmpty(from))
                Frame.HANDLES.sentAll(from, 1, null);
            this.finish();
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("绑定银行卡");
    }
}