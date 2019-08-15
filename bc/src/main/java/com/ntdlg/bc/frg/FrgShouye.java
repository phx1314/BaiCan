//
//  FrgShouye
//
//  Created by DELL on 2017-05-27 14:33:34
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.framewidget.frg.FrgPtDetail;
import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.NoTitleAct;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.config.BaseConfig;
import com.mdx.framework.utility.Helper;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanCPList;
import com.ntdlg.bc.bean.BeanKSJK;
import com.ntdlg.bc.bean.BeanSQJE;
import com.ntdlg.bc.model.ModelCPList;
import com.ntdlg.bc.model.ModelKSJK;
import com.ntdlg.bc.model.ModelSQJE;
import com.ntdlg.bc.model.ModelYHZXDD;

import static com.ntdlg.bc.F.applyDeadline;
import static com.ntdlg.bc.F.changeTime;
import static com.ntdlg.bc.F.chekNumber;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.loadProduct;
import static com.ntdlg.bc.F.rapidLoan;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.readClassByJson;


public class FrgShouye extends BaseFrg {


    public TextView clk_mTextView_hd;
    public TextView mTextView_num;
    public TextView mTextView_left;
    public SeekBar mSeekBar;
    public TextView mTextView_right;
    public TextView mTextView_zhou;
    public TextView mTextView_left_zhou;
    public SeekBar mSeekBar_zhou;
    public TextView mTextView_zhong;
    public TextView mTextView_right_zhou;
    public TextView mTextView_shenqing;
    public TextView mTextView_xinshou;
    public RelativeLayout mLinearLayout_yuan;
    public ImageView mImageView_left;
    public ImageView mImageView_right;
    public ImageView mImageView_center;
    public TextView mTextView_b1;
    public ModelCPList mModelCPList;
    public TextView mTextView_b2;
    public ModelYHZXDD mModelYHZXDD;
    public ModelKSJK mModelKSJK;
    public ModelSQJE mModelSQJE;
    public com.ntdlg.bc.view.MViewOne mMViewOne;
    public ImageView mImageView_del_top;
    public ImageView mImageView_add_top;
    public ImageView mImageView_del;
    public ImageView mImageView_add;
    public int dex_top;
    public int dex;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_shouye);
        setWindowStatusBarColor(getActivity(), R.color.A);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 0:
                chEck();
                break;
            case 1:
                loaddata();
                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        clk_mTextView_hd = (TextView) findViewById(R.id.clk_mTextView_hd);
        mTextView_num = (TextView) findViewById(R.id.mTextView_num);
        mTextView_left = (TextView) findViewById(R.id.mTextView_left);
        mSeekBar = (SeekBar) findViewById(R.id.mSeekBar);
        mTextView_right = (TextView) findViewById(R.id.mTextView_right);
        mTextView_zhou = (TextView) findViewById(R.id.mTextView_zhou);
        mTextView_left_zhou = (TextView) findViewById(R.id.mTextView_left_zhou);
        mSeekBar_zhou = (SeekBar) findViewById(R.id.mSeekBar_zhou);
        mTextView_zhong = (TextView) findViewById(R.id.mTextView_zhong);
        mTextView_right_zhou = (TextView) findViewById(R.id.mTextView_right_zhou);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);
        mTextView_xinshou = (TextView) findViewById(R.id.mTextView_xinshou);
        mLinearLayout_yuan = (RelativeLayout) findViewById(R.id.mLinearLayout_yuan);
        mImageView_left = (ImageView) findViewById(R.id.mImageView_left);
        mImageView_right = (ImageView) findViewById(R.id.mImageView_right);
        mImageView_center = (ImageView) findViewById(R.id.mImageView_center);
        mTextView_b1 = (TextView) findViewById(R.id.mTextView_b1);
        mTextView_b2 = (TextView) findViewById(R.id.mTextView_b2);
        mMViewOne = (com.ntdlg.bc.view.MViewOne) findViewById(R.id.mMViewOne);
        mImageView_del_top = (ImageView) findViewById(R.id.mImageView_del_top);
        mImageView_add_top = (ImageView) findViewById(R.id.mImageView_add_top);
        mImageView_del = (ImageView) findViewById(R.id.mImageView_del);
        mImageView_add = (ImageView) findViewById(R.id.mImageView_add);
        mMViewOne.setColor1("#ffffff");
        mMViewOne.setColor2("#88ffffff");
        mImageView_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mModelSQJE != null) {
                    mSeekBar_zhou.setProgress(0);
                    mTextView_zhou.setText(mModelSQJE.deadlineRecords.get(0).value);
                    setLv(mTextView_num.getText().toString(), mTextView_zhou.getText().toString());
                }
            }
        });
        mImageView_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mModelSQJE != null && mModelSQJE.deadlineRecords.size() % 2 == 1) {
                    mTextView_zhou.setText(mModelSQJE.deadlineRecords.get((mModelSQJE.deadlineRecords.size() - 1) / 2).value);
                    mSeekBar_zhou.setProgress(Integer.valueOf(mModelSQJE.deadlineRecords.get((mModelSQJE.deadlineRecords.size() - 1) / 2).value) - Integer.valueOf(mModelSQJE.deadlineRecords.get(0).value));
                    setLv(mTextView_num.getText().toString(), mTextView_zhou.getText().toString());
                }
            }
        });
        mImageView_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mModelSQJE != null) {
                    mSeekBar_zhou.setProgress(Integer.valueOf(mModelSQJE.deadlineRecords.get(mModelSQJE.deadlineRecords.size() - 1).value) - Integer.valueOf(mModelSQJE.deadlineRecords.get(0).value));
                    mTextView_zhou.setText(mModelSQJE.deadlineRecords.get(mModelSQJE.deadlineRecords.size() - 1).value);
                    setLv(mTextView_num.getText().toString(), mTextView_zhou.getText().toString());
                }
            }
        });
        mTextView_xinshou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mModelYHZXDD != null && mModelYHZXDD.result.equals("9")) {
                    Helper.startActivity(getContext(), FrgZhangdan.class, TitleAct.class, "title", "我的账单");
                } else {
                    Helper.startActivity(getContext(), FrgPtDetail.class, NoTitleAct.class, "url", BaseConfig.getUri() + "/other/show.html?type=" + 1, "title", "新手指引");
                }
            }
        });
        clk_mTextView_hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.startActivity(getContext(), FrgPtDetail.class, NoTitleAct.class, "url", BaseConfig.getUri() + "/other/show.html?type=" + 3, "title", "活动");
            }
        });
        mTextView_shenqing.setOnClickListener(Helper.delayClickLitener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(F.UserId)) {
                    Helper.startActivity(getContext(), FrgLogin.class, TitleAct.class);
                    return;
                }
                chEck2();
            }
        }));
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Frame.HANDLES.sentAll("FrgHome", 0, false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Frame.HANDLES.sentAll("FrgHome", 0, true);
                if (mModelSQJE != null)
                    changeTop(seekBar);
            }
        });
        mSeekBar_zhou.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Frame.HANDLES.sentAll("FrgHome", 0, false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Frame.HANDLES.sentAll("FrgHome", 0, true);
                if (mModelSQJE != null)
                    changeBottom(seekBar);
            }
        });

        mImageView_del_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBar.setProgress(mSeekBar.getProgress() > dex_top ? mSeekBar.getProgress() - dex_top : 0);
                changeTop(mSeekBar);
            }
        });
        mImageView_add_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBar.setProgress(mSeekBar.getProgress() < mSeekBar.getMax() ? mSeekBar.getProgress() + dex_top : mSeekBar.getMax());
                changeTop(mSeekBar);
            }
        });
        mImageView_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBar_zhou.setProgress(mSeekBar_zhou.getProgress() > dex ? mSeekBar_zhou.getProgress() - dex : 0);
                changeBottom(mSeekBar_zhou);
            }
        });
        mImageView_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBar_zhou.setProgress(mSeekBar_zhou.getProgress() < mSeekBar_zhou.getMax() ? mSeekBar_zhou.getProgress() + dex : mSeekBar_zhou.getMax());
                changeBottom(mSeekBar_zhou);
            }
        });
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(chekNumber)) {
            mModelYHZXDD = (ModelYHZXDD) json2Model(content, ModelYHZXDD.class);
//            F.saveApplyId(mModelYHZXDD.applyId);
            if (mModelYHZXDD.result.equals("9")) {//未结清
                mTextView_xinshou.setText(mModelYHZXDD.currentPeriords + "/" + mModelYHZXDD.totalPeriords + " 最后还款日 " + changeTime(mModelYHZXDD.endDate));
            }
        } else if (methodName.equals("chekNumber2")) {
            mModelYHZXDD = (ModelYHZXDD) json2Model(content, ModelYHZXDD.class);
            if (mModelYHZXDD.result.equals("11")) {
                F.saveApplyId(mModelYHZXDD.applyId);
                Helper.startActivity(getContext(), FrgSign.class, TitleAct.class, "from", "FrgShouye");
            } else {
                BeanKSJK mBeanKSJK = new BeanKSJK();
                mBeanKSJK.result = mModelYHZXDD.result;
                mBeanKSJK.applyId = mModelYHZXDD.applyId;
                mBeanKSJK.amount = mTextView_num.getText().toString();
                mBeanKSJK.term = mTextView_zhou.getText().toString();
                mBeanKSJK.sign = readClassAttr(mBeanKSJK);
                loadJsonUrl(rapidLoan, new Gson().toJson(mBeanKSJK));
            }

        } else if (methodName.equals(applyDeadline)) {
            mModelSQJE = (ModelSQJE) json2Model(content, ModelSQJE.class);
            if (mModelSQJE.amountRecords.size() > 0) {
                int max = Integer.valueOf(mModelSQJE.amountRecords.get(mModelSQJE.amountRecords.size() - 1).value) - Integer.valueOf(mModelSQJE.amountRecords.get(0).value);
                mTextView_left.setText(mModelSQJE.amountRecords.get(0).value);
                mTextView_num.setText(mModelSQJE.amountRecords.get(0).value);
                mTextView_right.setText(mModelSQJE.amountRecords.get(mModelSQJE.amountRecords.size() - 1).value);
                mSeekBar.setMax(max);
                mMViewOne.setProgress(Integer.valueOf(mModelSQJE.amountRecords.get(0).value), max, 0);
                dex_top = max / (mModelSQJE.amountRecords.size() - 1);
            }
            if (mModelSQJE.deadlineRecords.size() > 0) {
                int max = Integer.valueOf(mModelSQJE.deadlineRecords.get(mModelSQJE.deadlineRecords.size() - 1).value) - Integer.valueOf(mModelSQJE.deadlineRecords.get(0).value);
                mTextView_left_zhou.setText(mModelSQJE.deadlineRecords.get(0).value);
                mTextView_zhou.setText(mModelSQJE.deadlineRecords.get(0).value);
                mTextView_right_zhou.setText(mModelSQJE.deadlineRecords.get(mModelSQJE.deadlineRecords.size() - 1).value);
                mSeekBar_zhou.setMax(max);
                dex = max / (mModelSQJE.deadlineRecords.size() - 1);
            }
        } else if (methodName.equals(loadProduct)) {
            mModelCPList = (ModelCPList) json2Model(content, ModelCPList.class);
            setLv(mTextView_num.getText().toString(), mTextView_zhou.getText().toString());
        } else if (methodName.equals(rapidLoan)) {
            mModelKSJK = (ModelKSJK) json2Model(content, ModelKSJK.class);
            F.saveApplyId(mModelKSJK.applyId);
            if (mModelKSJK.result.equals("0") || mModelKSJK.result.equals("2")) {
                Helper.startActivity(getContext(), FrgRenzhengxinxi.class, TitleAct.class, "type", 1);
            } else if (mModelKSJK.result.equals("1")) {
                Helper.toast("黑名单用户", getContext());
            } else {
                Helper.startActivity(getContext(), FrgSxed.class, TitleAct.class, "mModelKSJK", mModelKSJK);
            }

        }
    }

    public void changeBottom(SeekBar seekBar) {
        try {
            int i = seekBar.getProgress() + Integer.valueOf(mModelSQJE.deadlineRecords.get(0).value);
            int min = Integer.MAX_VALUE;
            ModelSQJE.DeadlineRecordsBean mAmountRecordsBean_xuanzhong = null;
            for (ModelSQJE.DeadlineRecordsBean mAmountRecordsBean : mModelSQJE.deadlineRecords) {
                if (min > Math.abs(Integer.valueOf(mAmountRecordsBean.value) - i)) {
                    min = Math.abs(Integer.valueOf(mAmountRecordsBean.value) - i);
                    mAmountRecordsBean_xuanzhong = mAmountRecordsBean;
                }
            }
            if (mAmountRecordsBean_xuanzhong != null) {
                mSeekBar_zhou.setProgress(Integer.valueOf(mAmountRecordsBean_xuanzhong.value) - Integer.valueOf(mModelSQJE.deadlineRecords.get(0).value));
                mTextView_zhou.setText(mAmountRecordsBean_xuanzhong.value);
            }
            setLv(mTextView_num.getText().toString(), mTextView_zhou.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLv(String prcAmount, String prcTerm) {
        if (mModelCPList != null) {
            for (ModelCPList.RecordsBean mRecordsBean : mModelCPList.records) {
                if (Double.valueOf(prcAmount).equals(Double.valueOf(mRecordsBean.prcAmount)) && prcTerm.equals(mRecordsBean.prcTerm) && "2".equals(mRecordsBean.prcTermUnit)) {
                    mTextView_b1.setText(mRecordsBean.prcRadio + "%");
                    mTextView_b2.setText(mRecordsBean.repayAmount);
                    return;
                }
            }
        }
    }

    public void changeTop(SeekBar seekBar) {
        try {
            int i = seekBar.getProgress() + Integer.valueOf(mModelSQJE.amountRecords.get(0).value);
            int min = Integer.MAX_VALUE;
            ModelSQJE.AmountRecordsBean mAmountRecordsBean_xuanzhong = null;
            for (ModelSQJE.AmountRecordsBean mAmountRecordsBean : mModelSQJE.amountRecords) {
                if (min > Math.abs(Integer.valueOf(mAmountRecordsBean.value) - i)) {
                    min = Math.abs(Integer.valueOf(mAmountRecordsBean.value) - i);
                    mAmountRecordsBean_xuanzhong = mAmountRecordsBean;
                }
            }
            if (mAmountRecordsBean_xuanzhong != null) {
                mSeekBar.setProgress(Integer.valueOf(mAmountRecordsBean_xuanzhong.value) - Integer.valueOf(mModelSQJE.amountRecords.get(0).value));
                mTextView_num.setText(mAmountRecordsBean_xuanzhong.value);
                mMViewOne.setProgress(Integer.valueOf(mAmountRecordsBean_xuanzhong.value), Integer.valueOf(mModelSQJE.amountRecords.get(mModelSQJE.amountRecords.size() - 1).value) - Integer.valueOf(mAmountRecordsBean_xuanzhong.value), 0);
            }
            setLv(mTextView_num.getText().toString(), mTextView_zhou.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void loaddata() {
        BeanCPList mBeanCPList = new BeanCPList();
        mBeanCPList.sign = readClassAttr(mBeanCPList);
        loadJsonUrlNoshow(loadProduct, new Gson().toJson(mBeanCPList));

        BeanSQJE mBeanSQJE = new BeanSQJE();
        mBeanSQJE.sign = readClassAttr(mBeanSQJE);
        loadJsonUrlNoshow(applyDeadline, new Gson().toJson(mBeanSQJE));
        mTextView_xinshou.setText("新手指引");
        chEck();
    }

    public void chEck() {
        if (!TextUtils.isEmpty(F.UserId)) {
            BeanBase mBeanBase = new BeanBase();
            mBeanBase.sign = readClassByJson(new Gson().toJson(mBeanBase));
            loadJsonUrlNoshow(chekNumber, new Gson().toJson(mBeanBase));
        }
    }

    public void chEck2() {
        if (!TextUtils.isEmpty(F.UserId)) {
            BeanBase mBeanBase = new BeanBase();
            mBeanBase.sign = readClassAttr(mBeanBase);
            loadJsonNoshow(chekNumber, "chekNumber2", new Gson().toJson(mBeanBase));
        }
    }

}