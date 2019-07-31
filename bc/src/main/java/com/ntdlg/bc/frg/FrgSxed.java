//
//  FrgSxed
//
//  Created by DELL on 2017-06-13 15:25:25
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanSQTE;
import com.ntdlg.bc.model.ModelGRXYRZXX;
import com.ntdlg.bc.model.ModelKSJK;
import com.ntdlg.bc.model.ModelKSJK2;

import static com.ntdlg.bc.F.beginApply;
import static com.ntdlg.bc.F.bioAssay;
import static com.ntdlg.bc.F.getPlatform;
import static com.ntdlg.bc.F.getTime;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.readClassAttr;


public class FrgSxed extends BaseFrg {

    public LinearLayout mLinearLayout_top;
    public ImageView mMViewOne;
    public TextView mTextView_ed;
    public TextView mTextView_time;
    public TextView mTextView_tg;
    public LinearLayout mLinearLayout_shz;
    public TextView mTextView_price;
    public TextView mTextView_qx;
    public TextView mTextView_fl;
    public TextView mTextView_hk;
    public TextView mTextView_left;
    public TextView mTextView_right;
    public ImageButton btn_left;
    public TextView tv_title;
    public ModelKSJK mModelKSJK;
    public RelativeLayout mRelativeLayout_top;
    public ImageView mImageView_jihao;
    public TextView mTextView_remark;
    public TextView mTextView_shz;
    public TextView mTextView_remark1;
    public LinearLayout mLinearLayout_bottom;
    public ModelGRXYRZXX mModelGRXYRZXX;
    public boolean isOk = true;

    @Override
    protected void create(Bundle savedInstanceState) {
        mModelKSJK = (ModelKSJK) getActivity().getIntent().getSerializableExtra("mModelKSJK");
        setContentView(R.layout.frg_sxed);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 0:
                this.finish();
                break;
            case 1:
                F.mTBlivessCompare(getActivity(), mModelGRXYRZXX.sessid, "FrgSxed");
                break;
            case 130:
                BeanBase mBeanBase = new BeanBase();
                mBeanBase.sign = readClassAttr(mBeanBase);
                loadJsonUrl(bioAssay, new Gson().toJson(mBeanBase));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isOk = false;
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mLinearLayout_top = (LinearLayout) findViewById(R.id.mLinearLayout_top);
        mMViewOne = (ImageView) findViewById(R.id.mMViewOne);
        mTextView_ed = (TextView) findViewById(R.id.mTextView_ed);
        mTextView_time = (TextView) findViewById(R.id.mTextView_time);
        mTextView_tg = (TextView) findViewById(R.id.mTextView_tg);
        mLinearLayout_shz = (LinearLayout) findViewById(R.id.mLinearLayout_shz);
        mTextView_price = (TextView) findViewById(R.id.mTextView_price);
        mTextView_qx = (TextView) findViewById(R.id.mTextView_qx);
        mTextView_fl = (TextView) findViewById(R.id.mTextView_fl);
        mTextView_hk = (TextView) findViewById(R.id.mTextView_hk);
        mTextView_left = (TextView) findViewById(R.id.mTextView_left);
        mTextView_right = (TextView) findViewById(R.id.mTextView_right);
        btn_left = (ImageButton) findViewById(R.id.btn_left);
        tv_title = (TextView) findViewById(R.id.tv_title);
        mRelativeLayout_top = (RelativeLayout) findViewById(R.id.mRelativeLayout_top);
        mImageView_jihao = (ImageView) findViewById(R.id.mImageView_jihao);
        mTextView_remark = (TextView) findViewById(R.id.mTextView_remark);
        mTextView_shz = (TextView) findViewById(R.id.mTextView_shz);
        mTextView_remark1 = (TextView) findViewById(R.id.mTextView_remark1);
        mLinearLayout_bottom = (LinearLayout) findViewById(R.id.mLinearLayout_bottom);
        mTextView_price.setText(mModelKSJK.applyAmount + "元");
        mTextView_qx.setText(mModelKSJK.applyTerm + "周");
        mTextView_fl.setText(mModelKSJK.applyRaio + "%/天");
        mTextView_hk.setText(mModelKSJK.repayAmount + "元/周");
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(beginApply)) {
            ModelKSJK2 mModelKSJK2 = (ModelKSJK2) json2Model(content, ModelKSJK2.class);
            if (mModelKSJK2.result.equals("1")) {
                Helper.toast("请先绑定银行卡", getContext());
                Helper.startActivity(getContext(), FrgWodeYhk.class, TitleAct.class, "from", "FrgSxed");
            } else if (mModelKSJK2.result.equals("2")) {
                F.mTBlivessCompare(getActivity(), mModelGRXYRZXX.sessid, "FrgSxed");
            } else if (mModelKSJK2.result.equals("3")) {
                Helper.startActivity(getContext(), FrgJkShenqing.class, TitleAct.class);
            }
        } else if (methodName.equals(getPlatform)) {
            mModelGRXYRZXX = (ModelGRXYRZXX) json2Model(content, ModelGRXYRZXX.class);
        } else if (methodName.equals(bioAssay)) {
            Helper.toast("认证成功", getContext());
        }
    }

    public void loaddata() {
        BeanBase mBeanBase = new BeanBase();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(getPlatform, new Gson().toJson(mBeanBase));

        if (mModelKSJK.result.equals("3")) {//审核中
            mRelativeLayout_top.setVisibility(View.GONE);
            mLinearLayout_bottom.setVisibility(View.GONE);
        } else if (mModelKSJK.result.equals("4")) {//审核成功
            mTextView_ed.setText(mModelKSJK.assignAmount);
            mTextView_time.setText("签约倒计时 " + getTime(mModelKSJK.endDate));
            mTextView_tg.setText("审核通过");
            mImageView_jihao.setImageResource(R.drawable.complete);
            mTextView_remark.setText("恭喜您，您的借款申请已审核通过");
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
                                        mTextView_time.setText("签约倒计时 " + getTime(mModelKSJK.endDate));
                                    }
                                });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            mTextView_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Helper.startActivity(getContext(), FrgTezx.class, TitleAct.class);

                }
            });
            mTextView_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Helper.startActivity(getContext(), FrgSign.class, TitleAct.class,"from","FrgSxed");
                    BeanSQTE mBeanSQTE = new BeanSQTE();
                    mBeanSQTE.sign = readClassAttr(mBeanSQTE);
                    loadJsonUrl(beginApply, new Gson().toJson(mBeanSQTE));
                }
            });
        } else if (mModelKSJK.result.equals("5")) {//签约失效
            mTextView_ed.setText(mModelKSJK.assignAmount);
            mTextView_time.setText("签约倒计时 00:00:00");
            mTextView_tg.setText("签约失效");
            mImageView_jihao.setImageResource(R.drawable.fail);
            mTextView_remark.setText("您的授信额度已过期，请重新申请");
            mTextView_left.setVisibility(View.GONE);
            mTextView_right.setText("再次申请");
            mTextView_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BeanSQTE mBeanSQTE = new BeanSQTE();
                    mBeanSQTE.sign = readClassAttr(mBeanSQTE);
                    loadJsonUrl(beginApply, new Gson().toJson(mBeanSQTE));
                }
            });
        } else if (mModelKSJK.result.equals("6")) {//未通过
            mRelativeLayout_top.setVisibility(View.GONE);
            mTextView_shz.setText("未通过...");
            mTextView_shz.setTextColor(Color.parseColor("#FA3C3C"));
            mTextView_shz.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.fail, 0, 0);
            mTextView_remark1.setText(Html.fromHtml("很遗憾，您的借款申请由于评分不足未能通过<br><font color='##FA3C3C'>30天</font>内将无法再次发起借款申请"));
            mTextView_left.setVisibility(View.GONE);
            mTextView_right.setText("关闭");
            mTextView_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FrgSxed.this.finish();
                }
            });
        } else if (mModelKSJK.result.equals("7")) {//失败提额
            mRelativeLayout_top.setVisibility(View.GONE);
            mTextView_shz.setText("失败提额...");
            mTextView_shz.setTextColor(Color.parseColor("#FA3C3C"));
            mTextView_shz.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.fail, 0, 0);
            mTextView_remark1.setText(Html.fromHtml("很遗憾，您的借款申提额失败"));
            mTextView_left.setVisibility(View.GONE);
            mTextView_right.setText("关闭");
            mTextView_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FrgSxed.this.finish();
                }
            });
        } else if (mModelKSJK.result.equals("8")) {//放款中
            mRelativeLayout_top.setVisibility(View.GONE);
            mTextView_shz.setText("放款中...");
            mTextView_remark1.setText(Html.fromHtml("您的借款正在放款中..."));
            mTextView_left.setVisibility(View.GONE);
            mTextView_right.setText("关闭");
            mTextView_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FrgSxed.this.finish();
                }
            });
        } else if (mModelKSJK.result.equals("9")) {//未结清
            mRelativeLayout_top.setVisibility(View.GONE);
            mTextView_shz.setText("未结清...");
            mTextView_shz.setTextColor(Color.parseColor("#FA3C3C"));
            mTextView_shz.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.fail, 0, 0);
            mTextView_remark1.setText(Html.fromHtml("很遗憾，您的借款尚未结清"));
            mTextView_left.setVisibility(View.GONE);
            mTextView_right.setText("去结清");
            mTextView_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Helper.startActivity(getContext(), FrgZhangdan.class, TitleAct.class, "title", "我的账单");
                }
            });
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        if (mModelKSJK.equals("4") || mModelKSJK.equals("5")) {
            mHeadlayout.setTitle("授信额度");
        } else {
            mHeadlayout.setTitle("借款申请");
        }
    }
}