//
//  FrgWodeJk1
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.NoTitleAct;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanHt;
import com.ntdlg.bc.bean.BeanSQTE;
import com.ntdlg.bc.bean.BeanVip;
import com.ntdlg.bc.bean.BeanWDJK;
import com.ntdlg.bc.model.ModelGRXYRZXX;
import com.ntdlg.bc.model.ModelKSJK2;
import com.ntdlg.bc.model.ModelLoginUrl;
import com.ntdlg.bc.model.ModelWDJK;

import static com.ntdlg.bc.F.beginApply;
import static com.ntdlg.bc.F.bioAssay;
import static com.ntdlg.bc.F.changeTime;
import static com.ntdlg.bc.F.getPlatform;
import static com.ntdlg.bc.F.getTime;
import static com.ntdlg.bc.F.getVip88LoginUrl;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.queryBill;
import static com.ntdlg.bc.F.readClassAttr;


public class FrgWodeJk1 extends BaseFrg {

    public TextView mTextView_time;
    public TextView mTextView_price;
    public TextView mTextView_type;
    public TextView mTextView_je;
    public TextView mTextView_qx;
    public TextView mTextView_jv;
    public TextView mTextView_hkje;
    public TextView mTextView_remark;
    public TextView mTextView_tj;
    public LinearLayout mLinearLayout_content;
    public boolean isOk = true;
    public ModelGRXYRZXX mModelGRXYRZXX;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_wode_jk_1);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 0:
                loaddata();
                break;
            case 1:
                F.mTBlivessCompare(getActivity(), mModelGRXYRZXX.sessid, "FrgWodeJk1");
                break;
            case 130:
                BeanHt mBeanHt = new BeanHt();
                mBeanHt.assayPic = obj.toString();
                mBeanHt.sign = readClassAttr(mBeanHt);
                loadJsonUrl(bioAssay, new Gson().toJson(mBeanHt));
                break;
        }
    }

    private void findVMethod() {
        mTextView_time = (TextView) findViewById(R.id.mTextView_time);
        mTextView_price = (TextView) findViewById(R.id.mTextView_price);
        mTextView_type = (TextView) findViewById(R.id.mTextView_type);
        mTextView_je = (TextView) findViewById(R.id.mTextView_je);
        mTextView_qx = (TextView) findViewById(R.id.mTextView_qx);
        mTextView_jv = (TextView) findViewById(R.id.mTextView_jv);
        mTextView_hkje = (TextView) findViewById(R.id.mTextView_hkje);
        mTextView_remark = (TextView) findViewById(R.id.mTextView_remark);
        mTextView_tj = (TextView) findViewById(R.id.mTextView_tj);
        mLinearLayout_content = (LinearLayout) findViewById(R.id.mLinearLayout_content);


    }

    public void loaddata() {
        BeanWDJK mBeanWDJK = new BeanWDJK();
        mBeanWDJK.type = "1";
        mBeanWDJK.sign = readClassAttr(mBeanWDJK);
        loadJsonUrl(queryBill, new Gson().toJson(mBeanWDJK));

        BeanBase mBeanBase = new BeanBase();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(getPlatform, new Gson().toJson(mBeanBase));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isOk = false;
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(queryBill)) {
            final ModelWDJK mModelWDJK = (ModelWDJK) json2Model(content, ModelWDJK.class);
            if (mModelWDJK.bills != null && mModelWDJK.bills.size() > 0) {
                F.saveApplyId(mModelWDJK.bills.get(0).billId);
                mLinearLayout_content.setVisibility(View.VISIBLE);
                mTextView_time.setText(changeTime(mModelWDJK.bills.get(0).beginData));
                mTextView_price.setText(mModelWDJK.bills.get(0).billAmount);
                mTextView_je.setText(mModelWDJK.bills.get(0).billAmount + "元");
                mTextView_qx.setText(mModelWDJK.bills.get(0).periods + mModelWDJK.bills.get(0).term);
                mTextView_jv.setText(mModelWDJK.bills.get(0).radio + "/天");
                mTextView_hkje.setText(mModelWDJK.bills.get(0).rapayAmount + "元/周");
                if (mModelWDJK.bills.get(0).billStatus.equals("2")) {//审核中
                    mTextView_remark.setVisibility(View.GONE);
                    mTextView_tj.setVisibility(View.INVISIBLE);
                    mTextView_type.setText("审核中");
                } else if (mModelWDJK.bills.get(0).billStatus.equals("9")) {//签约中
                    mTextView_remark.setVisibility(View.VISIBLE);
                    mTextView_tj.setVisibility(View.INVISIBLE);
                    mTextView_type.setText("签约中");
                    mTextView_tj.setText("开始借款");
                    mTextView_remark.setText(Html.fromHtml("签约倒计时  <font color='#FDA935'>" + getTime(mModelWDJK.bills.get(0).date) + "</font>"));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (isOk) {
                                try {
                                    Thread.sleep(1000);
                                    if (getActivity() != null)
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mTextView_remark.setText(Html.fromHtml("签约倒计时  <font color='#FDA935'>" + getTime(mModelWDJK.bills.get(0).date) + "</font>"));
                                            }
                                        });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                    mTextView_tj.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            if (FrgWodeJk.mModelZHZX.ischeck.equals("1")) {
//                                Helper.toast("申请中", getContext());
//                                return;
//                            }
//                            Helper.startActivity(getContext(), FrgZhangdan.class, TitleAct.class, "title", "我的账单");
                            BeanSQTE mBeanSQTE = new BeanSQTE();
                            mBeanSQTE.sign = readClassAttr(mBeanSQTE);
                            loadJsonUrl(beginApply, new Gson().toJson(mBeanSQTE));
                        }
                    });
                } else if (mModelWDJK.bills.get(0).billStatus.equals("10")) {//放款中
                    mTextView_remark.setVisibility(View.GONE);
                    mTextView_tj.setVisibility(View.VISIBLE);
                    mTextView_type.setText("放款中");
                    mTextView_tj.setText("查看VIP权益");
                    mTextView_tj.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadJsonUrl(getVip88LoginUrl, new Gson().toJson(new BeanVip()));
                        }
                    });
                } else if (mModelWDJK.bills.get(0).billStatus.equals("11")) {//放款中
                    mTextView_remark.setVisibility(View.GONE);
                    mTextView_tj.setVisibility(View.VISIBLE);
                    mTextView_type.setText("放款中");
                    mTextView_remark.setText(Html.fromHtml("放款倒计时  <font color='#FDA935'>" + getTime(mModelWDJK.bills.get(0).date) + "</font>"));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (isOk) {
                                try {
                                    Thread.sleep(1000);
                                    if (getActivity() != null)
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mTextView_remark.setText(Html.fromHtml("放款倒计时  <font color='#FDA935'>" + getTime(mModelWDJK.bills.get(0).date) + "</font>"));
                                            }
                                        });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                    mTextView_tj.setText("购买VIP");
                    mTextView_tj.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            com.framewidget.F.yShoure(getContext(), "购买会员成功后，须工作时间内才能立即放款", "", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    loadJsonUrl(getVip88LoginUrl, new Gson().toJson(new BeanVip()));
                                }
                            });
                        }
                    });
                } else if (mModelWDJK.bills.get(0).billStatus.equals("6")) {//还款中
                    mTextView_remark.setVisibility(View.VISIBLE);
                    mTextView_tj.setVisibility(View.VISIBLE);
                    Frame.HANDLES.sentAll("FrgWodeJk", 0, null);
                    mTextView_type.setText("还款中");
                    mTextView_tj.setText("提前结清");
                    mTextView_remark.setText(Html.fromHtml("未还本金  <font color='#FDA935'>" + mModelWDJK.bills.get(0).whbj + "</font>元"));
                    mTextView_tj.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Helper.startActivity(getContext(), FrgZhangdan.class, TitleAct.class, "title", "还款");
                        }
                    });
                }
            } else {
                mLinearLayout_content.setVisibility(View.GONE);
            }
        } else if (methodName.equals(getPlatform)) {
            mModelGRXYRZXX = (ModelGRXYRZXX) json2Model(content, ModelGRXYRZXX.class);
        } else if (methodName.equals(beginApply)) {
            ModelKSJK2 mModelKSJK2 = (ModelKSJK2) json2Model(content, ModelKSJK2.class);
            if (mModelKSJK2.result.equals("1")) {
                Helper.toast("请先绑定银行卡", getContext());
                Helper.startActivity(getContext(), FrgWodeYhk.class, TitleAct.class, "from", "FrgWodeJk1");
            } else if (mModelKSJK2.result.equals("2")) {
                F.mTBlivessCompare(getActivity(), mModelGRXYRZXX.sessid, "FrgWodeJk1");
            } else if (mModelKSJK2.result.equals("3")) {
                Helper.startActivity(getContext(), FrgJkShenqing.class, TitleAct.class);
            } else if (mModelKSJK2.result.equals("4")) {
                Helper.startActivity(getContext(), FrgSign.class, TitleAct.class);
            }
        } else if (methodName.equals(bioAssay)) {
            Helper.toast("认证成功", getContext());
            Helper.startActivity(getContext(), FrgSign.class, TitleAct.class);
        } else if (methodName.equals(getVip88LoginUrl)) {
            ModelLoginUrl mModelLoginUrl = (ModelLoginUrl) json2Model(content, ModelLoginUrl.class);
            Helper.startActivity(getContext(), FrgVip.class, NoTitleAct.class, "url", mModelLoginUrl.dataObject.data);
        }
    }
}