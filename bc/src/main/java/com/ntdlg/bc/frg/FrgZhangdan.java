//
//  FrgZhangdan
//
//  Created by DELL on 2017-06-02 11:34:11
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framewidget.util.AbDateUtil;
import com.google.gson.Gson;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanHKZF;
import com.ntdlg.bc.bean.BeanTQHK;
import com.ntdlg.bc.model.ModelTQHK;
import com.ntdlg.bc.model.ModelWDZD;
import com.ntdlg.bc.view.MViewOne;

import static com.ntdlg.bc.F.changeTime;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.myBill;
import static com.ntdlg.bc.F.pay;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.repayment;


public class FrgZhangdan extends BaseFrg {

    public TextView mTextView_time;
    public MViewOne mMViewOne;
    public TextView mTextView_mx;
    public TextView mTextView_price;
    public TextView mTextView_price1;
    public TextView mTextView_price2;
    public TextView mTextView_price3;
    public TextView mTextView_time_last;
    public TextView mTextView_xianxia;
    public TextView mTextView_shenqing;
    public TextView mTextView_remark;
    public TextView mTextView_all_price;
    public TextView mTextView_wh_price;
    public String title;
    public LinearLayout mLinearLayout_bottom;
    public TextView mTextView_ws_1;
    public TextView mTextView_ws_2;
    public TextView mTextView_ws_3;
    public TextView mTextView_ws_4;
    public BeanHKZF mBeanHKZF = new BeanHKZF();
    public TextView mTextView_qsh;

    @Override
    protected void create(Bundle savedInstanceState) {
        title = getActivity().getIntent().getStringExtra("title");
        setContentView(R.layout.frg_zhangdan);
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
        mTextView_time = (TextView) findViewById(R.id.mTextView_time);
        mMViewOne = (MViewOne) findViewById(R.id.mMViewOne);
        mTextView_mx = (TextView) findViewById(R.id.mTextView_mx);
        mTextView_price = (TextView) findViewById(R.id.mTextView_price);
        mTextView_price1 = (TextView) findViewById(R.id.mTextView_price1);
        mTextView_price2 = (TextView) findViewById(R.id.mTextView_price2);
        mTextView_price3 = (TextView) findViewById(R.id.mTextView_price3);
        mTextView_time_last = (TextView) findViewById(R.id.mTextView_time_last);
        mTextView_xianxia = (TextView) findViewById(R.id.mTextView_xianxia);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);
        mTextView_remark = (TextView) findViewById(R.id.mTextView_remark);
        mTextView_all_price = (TextView) findViewById(R.id.mTextView_all_price);
        mTextView_wh_price = (TextView) findViewById(R.id.mTextView_wh_price);
        mLinearLayout_bottom = (LinearLayout) findViewById(R.id.mLinearLayout_bottom);
        mTextView_ws_1 = (TextView) findViewById(R.id.mTextView_ws_1);
        mTextView_ws_2 = (TextView) findViewById(R.id.mTextView_ws_2);
        mTextView_ws_3 = (TextView) findViewById(R.id.mTextView_ws_3);
        mTextView_ws_4 = (TextView) findViewById(R.id.mTextView_ws_4);
        mTextView_qsh = (TextView) findViewById(R.id.mTextView_qsh);
        mTextView_xianxia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.startActivity(getContext(), FrgXxhk.class, TitleAct.class);
            }
        });
        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBeanHKZF.sign = readClassAttr(mBeanHKZF);
                loadJsonUrl(pay, new Gson().toJson(mBeanHKZF));
            }
        });
    }

    public void loaddata() {
        if (title.equals("还款")) {
            mTextView_time.setVisibility(View.INVISIBLE);
            mTextView_time_last.setVisibility(View.INVISIBLE);
            mTextView_xianxia.setVisibility(View.INVISIBLE);
            mLinearLayout_bottom.setVisibility(View.INVISIBLE);
            mTextView_remark.setVisibility(View.INVISIBLE);
            BeanTQHK mBeanTQHK = new BeanTQHK();
            mBeanTQHK.sign = readClassAttr(mBeanTQHK);
            loadJsonUrl(repayment, new Gson().toJson(mBeanTQHK));
            mTextView_ws_1.setText("还款总额(元)");
            mTextView_ws_2.setText("还款本金(元)");
            mTextView_ws_3.setText("利息(元)");
            mTextView_ws_4.setText("手续费(元)");
            mTextView_shenqing.setText("确定");
            mBeanHKZF.type = "2";
        } else {
            BeanBase mBeanBase = new BeanBase();
            mBeanBase.sign = readClassAttr(mBeanBase);
            loadJsonUrl(myBill, new Gson().toJson(mBeanBase));
            mBeanHKZF.type = "1";
        }
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(myBill)) {
            ModelWDZD mModelWDZD = (ModelWDZD) json2Model(content, ModelWDZD.class);
            mTextView_time.setText(AbDateUtil.getCurrentDate("MM-dd"));
            mTextView_price.setText(mModelWDZD.bqyhje);
            mTextView_price1.setText(mModelWDZD.yqwhje);
            mTextView_price2.setText(mModelWDZD.fxje);
            mTextView_price3.setText(mModelWDZD.bqyhbx);
            mTextView_time_last.setText("最后还款日  " + changeTime(mModelWDZD.endDate));
            mTextView_qsh.setText("期数:" + mModelWDZD.currentPeriords + "/" + mModelWDZD.totalPeriords);
            mTextView_all_price.setText(mModelWDZD.jkze);
            mTextView_wh_price.setText(mModelWDZD.whbj);
            mTextView_xianxia.setText(Html.fromHtml(mModelWDZD.bankNo + mModelWDZD.bankName + "卡余额不足？尝试<font color='#FDA935'>线下还款</font>元"));
            mMViewOne.setProgress(Float.valueOf(mModelWDZD.yqwhje), Float.valueOf(mModelWDZD.bqyhbx) - Float.valueOf(mModelWDZD.bqyhje), Float.valueOf(mModelWDZD.bqyhbx));
            mBeanHKZF.amount = mModelWDZD.bqyhje;
            mBeanHKZF.repayId = mModelWDZD.repayId;
            if (mModelWDZD.status.equals("4")) {
                mTextView_shenqing.setEnabled(false);
                mTextView_shenqing.setText("审核中");
            } else if (mModelWDZD.status.equals("8")) {
                mTextView_shenqing.setBackgroundResource(R.drawable.shape_gray_shen);
                mTextView_shenqing.setText("还款中");
                mTextView_xianxia.setVisibility(View.INVISIBLE);
                mTextView_remark.setText("您已使用线下还款，系统正在审核中");
                mTextView_shenqing.setEnabled(false);
            }
        } else if (methodName.equals(repayment)) {
            ModelTQHK mModelTQHK = (ModelTQHK) json2Model(content, ModelTQHK.class);
            mTextView_price.setText(mModelTQHK.hkze);
            mTextView_price1.setText(mModelTQHK.hkbj);
            mTextView_price2.setText(mModelTQHK.lx);
            mTextView_price3.setText(mModelTQHK.sxf);
            mMViewOne.setProgress(Float.valueOf(mModelTQHK.hkbj), Float.valueOf(mModelTQHK.lx), Float.valueOf(mModelTQHK.sxf));
            mBeanHKZF.amount = mModelTQHK.hkze;
            mBeanHKZF.applyId = mModelTQHK.applyId;
        } else if (methodName.equals(pay)) {
            Helper.toast("提交成功", getContext());
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle(title);

        if (!title.equals("还款")) {
            mHeadlayout.setRText("历史账单");
            mHeadlayout.setRightOnclicker(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Helper.startActivity(getContext(), FrgLishiZhangdanList.class, TitleAct.class);
                }
            });
        }
    }
}