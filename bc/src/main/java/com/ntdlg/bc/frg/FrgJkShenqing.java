//
//  FrgJkShenqing
//
//  Created by DELL on 2017-06-09 16:03:32
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.framewidget.F;
import com.framewidget.frg.FrgPtDetail;
import com.framewidget.view.CallBackOnly;
import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.NoTitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanQYQR;
import com.ntdlg.bc.bean.BeanQYQRSJ;
import com.ntdlg.bc.bean.BeanSQTE;
import com.ntdlg.bc.bean.BeanVip;
import com.ntdlg.bc.bean.BeannoVip;
import com.ntdlg.bc.item.DialogCao;
import com.ntdlg.bc.model.ModelKSJK2;
import com.ntdlg.bc.model.ModelLoginUrl;
import com.ntdlg.bc.model.ModelQUQRSJ;

import static com.ntdlg.bc.F.affirmBorrow;
import static com.ntdlg.bc.F.affirmBorrowData;
import static com.ntdlg.bc.F.beginApply;
import static com.ntdlg.bc.F.getVip88LoginUrl;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.noVip;
import static com.ntdlg.bc.F.readClassAttr;


public class FrgJkShenqing extends BaseFrg {

    public TextView mTextView_1;
    public TextView mTextView_2;
    public TextView mTextView_3;
    public TextView mTextView_4;
    public TextView mTextView_5;
    public TextView mTextView_6;
    public CheckBox mCheckBox;
    public TextView tv_read;
    public TextView mTextView_shenqing;


    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_jk_shenqing);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 0:
//                BeanQYQR mBeanQYQR = new BeanQYQR();
//                mBeanQYQR.sign = readClassAttr(mBeanQYQR);
//                loadJsonUrl(affirmBorrow, new Gson().toJson(mBeanQYQR));
                BeannoVip mBeannoVip = new BeannoVip();
                mBeannoVip.sign = readClassAttr(mBeannoVip);
                loadJsonUrl(noVip, new Gson().toJson(mBeannoVip));
                break;
            case 1:
                loadJsonUrl(getVip88LoginUrl, new Gson().toJson(new BeanVip()));
                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mTextView_1 = (TextView) findViewById(R.id.mTextView_1);
        mTextView_2 = (TextView) findViewById(R.id.mTextView_2);
        mTextView_3 = (TextView) findViewById(R.id.mTextView_3);
        mTextView_4 = (TextView) findViewById(R.id.mTextView_4);
        mTextView_5 = (TextView) findViewById(R.id.mTextView_5);
        mTextView_6 = (TextView) findViewById(R.id.mTextView_6);
        mCheckBox = (CheckBox) findViewById(R.id.mCheckBox);
        tv_read = (TextView) findViewById(R.id.tv_read);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);

        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox.isChecked()) {
                    BeanSQTE mBeanSQTE = new BeanSQTE();
                    mBeanSQTE.sign = readClassAttr(mBeanSQTE);
                    loadJsonUrl(beginApply, new Gson().toJson(mBeanSQTE));


                } else {
                    Helper.toast("请同意相关协议", getContext());
                }
            }
        });
    }

    public void loaddata() {
        BeanQYQRSJ mBeanQYQRSJ = new BeanQYQRSJ();
        mBeanQYQRSJ.sign = readClassAttr(mBeanQYQRSJ);
        loadJsonUrl(affirmBorrowData, new Gson().toJson(mBeanQYQRSJ));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(affirmBorrowData)) {
            final ModelQUQRSJ mModelQUQRSJ = (ModelQUQRSJ) json2Model(content, ModelQUQRSJ.class);
            mTextView_1.setText(mModelQUQRSJ.applyAmount + "元");
            mTextView_2.setText(mModelQUQRSJ.applyTerm + mModelQUQRSJ.applyTermType);
            mTextView_3.setText(mModelQUQRSJ.applyRaio + "/天");
            mTextView_4.setText(mModelQUQRSJ.repayAmount + "元/" + mModelQUQRSJ.repayAmounyType);
            mTextView_5.setText(mModelQUQRSJ.repayModes);
            mTextView_6.setText(mModelQUQRSJ.bankNo);
            tv_read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Helper.startActivity(getContext(), FrgPtDetail.class, NoTitleAct.class, "url", mModelQUQRSJ.url, "title", "签约协议", "hasTextSize", true);
                }
            });
        } else if (methodName.equals(noVip)) {
            Helper.toast("操作成功", getContext());
            Frame.HANDLES.sentAll("FrgSxed,FrgWode,FrgWodeJk1", 0, null);
            this.finish();
        } else if (methodName.equals(beginApply)) {
            final ModelKSJK2 mModelKSJK2 = (ModelKSJK2) json2Model(content, ModelKSJK2.class);
            final View view = DialogCao.getView(getContext(), null);
            F.showCenterDialog(getContext(), view, new CallBackOnly() {
                @Override
                public void goReturn(String token, String reftoken) {

                }

                @Override
                public void goReturnDo(Dialog mDialog) {
                    ((DialogCao) view.getTag()).set(mDialog, mModelKSJK2);
                }
            });
        } else if (methodName.equals(getVip88LoginUrl)) {
            ModelLoginUrl mModelLoginUrl = (ModelLoginUrl) json2Model(content, ModelLoginUrl.class);
            Helper.startActivity(getContext(), FrgVip.class, NoTitleAct.class, "url", mModelLoginUrl.dataObject.data);
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("借款申请");
    }
}