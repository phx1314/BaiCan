//
//  FrgLxr
//
//  Created by DELL on 2017-06-23 09:28:07
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
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanLXRXX;
import com.ntdlg.bc.model.BaseData;
import com.ntdlg.bc.model.ModelQSGX;
import com.ntdlg.bc.view.SortModel;

import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.linkManInfo;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.relation;


public class FrgLxr extends BaseFrg {

    public EditText mEditText_jj_name;
    public LinearLayout mLinearLayout_phone;
    public EditText mTextView_phone;
    public LinearLayout mLinearLayout_qshgx;
    public TextView mTextView_qshgx;
    public EditText mEditText_jj_name2;
    public LinearLayout mLinearLayout_phone2;
    public EditText mTextView_phone2;
    public LinearLayout mLinearLayout_qshgx2;
    public TextView mTextView_qshgx2;
    public TextView mTextView_shenqing;
    public ModelQSGX mModelQSGX;
    public BeanLXRXX mBeanLXRXX = new BeanLXRXX();
    public ImageView mImageView_phone1;
    public ImageView mImageView_phone2;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_lxr);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 101:
                SortModel item = (SortModel) obj;
                mTextView_phone.setText(item.number.replace(" ", ""));
                mEditText_jj_name.setText(item.name);
                break;
            case 102:
                SortModel item2 = (SortModel) obj;
                mTextView_phone2.setText(item2.number.replace(" ", ""));
                mEditText_jj_name2.setText(item2.name);
                break;
            case 103:
                BaseData mBaseData = (BaseData) obj;
                mTextView_qshgx.setText(mBaseData.value);
                mBeanLXRXX.relationOne = mBaseData.code;
                break;
            case 104:
                BaseData mBaseData2 = (BaseData) obj;
                mTextView_qshgx2.setText(mBaseData2.value);
                mBeanLXRXX.relationTwo = mBaseData2.code;
                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {

        mEditText_jj_name = (EditText) findViewById(R.id.mEditText_jj_name);
        mLinearLayout_phone = (LinearLayout) findViewById(R.id.mLinearLayout_phone);
        mTextView_phone = (EditText) findViewById(R.id.mTextView_phone);
        mLinearLayout_qshgx = (LinearLayout) findViewById(R.id.mLinearLayout_qshgx);
        mTextView_qshgx = (TextView) findViewById(R.id.mTextView_qshgx);
        mEditText_jj_name2 = (EditText) findViewById(R.id.mEditText_jj_name2);
        mLinearLayout_phone2 = (LinearLayout) findViewById(R.id.mLinearLayout_phone2);
        mTextView_phone2 = (EditText) findViewById(R.id.mTextView_phone2);
        mLinearLayout_qshgx2 = (LinearLayout) findViewById(R.id.mLinearLayout_qshgx2);
        mTextView_qshgx2 = (TextView) findViewById(R.id.mTextView_qshgx2);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);
        mImageView_phone1 = (ImageView) findViewById(R.id.mImageView_phone1);
        mImageView_phone2 = (ImageView) findViewById(R.id.mImageView_phone2);
        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEditText_jj_name.getText().toString().trim())) {
                    Helper.toast("请输入亲属姓名", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mTextView_phone.getText().toString().trim())) {
                    Helper.toast("请输入亲属手机号", getContext());
                    return;
                }
                if (mTextView_phone.getText().toString().trim().length() != 11) {
                    Helper.toast("请输入正确的亲属手机号", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mTextView_qshgx.getText().toString().trim())) {
                    Helper.toast("请选择亲属关系", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mEditText_jj_name2.getText().toString().trim())) {
                    Helper.toast("请输入亲属联系人2姓名", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mTextView_phone2.getText().toString().trim())) {
                    Helper.toast("请输入亲属联系人2手机号", getContext());
                    return;
                }
                if (mTextView_phone2.getText().toString().trim().length() != 11) {
                    Helper.toast("请输入正确的亲属联系人2手机号", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mTextView_qshgx2.getText().toString().trim())) {
                    Helper.toast("请选择亲属联系人2关系", getContext());
                    return;
                }
                mBeanLXRXX.nameOne = mEditText_jj_name.getText().toString().trim();
                mBeanLXRXX.phoneOne = mTextView_phone.getText().toString().trim();
                mBeanLXRXX.nameTwo = mEditText_jj_name2.getText().toString().trim();
                mBeanLXRXX.phoneTwo = mTextView_phone2.getText().toString().trim();
                mBeanLXRXX.sign = readClassAttr(mBeanLXRXX);
                loadJsonUrl(linkManInfo, new Gson().toJson(mBeanLXRXX));

            }
        });
        mImageView_phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.startActivity(getContext(), FrgLxrList.class, TitleAct.class, "from", "FrgLxr", "type", 101);
            }
        });
        mImageView_phone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.startActivity(getContext(), FrgLxrList.class, TitleAct.class, "from", "FrgLxr", "type", 102);
            }
        });
        mLinearLayout_qshgx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.startActivity(getContext(), FrgPubList.class, TitleAct.class, "from", "FrgLxr", "type", 103, "data", mModelQSGX.qsgxRecords);
            }
        });
        mLinearLayout_qshgx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.startActivity(getContext(), FrgPubList.class, TitleAct.class, "from", "FrgLxr", "type", 104, "data", mModelQSGX.qsgxRecords);
            }
        });
    }

    public void loaddata() {
        BeanBase mBeanBase = new BeanBase();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(relation, new Gson().toJson(mBeanBase));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(relation)) {
            mModelQSGX = (ModelQSGX) json2Model(content, ModelQSGX.class);
            if (mModelQSGX.isExist.equals("1") && mModelQSGX.lxrRecords != null) {
                for (int i = 0; i < mModelQSGX.lxrRecords.size(); i++) {
                    if (i == 0) {
                        mEditText_jj_name.setText(mModelQSGX.lxrRecords.get(i).name);
                        mTextView_phone.setText(mModelQSGX.lxrRecords.get(i).phone);
                        mTextView_qshgx.setText(mModelQSGX.lxrRecords.get(i).relation);
                        mBeanLXRXX.idOne = mModelQSGX.lxrRecords.get(i).id;
                    } else {
                        mEditText_jj_name2.setText(mModelQSGX.lxrRecords.get(i).name);
                        mTextView_phone2.setText(mModelQSGX.lxrRecords.get(i).phone);
                        mTextView_qshgx2.setText(mModelQSGX.lxrRecords.get(i).relation);
                        mBeanLXRXX.idTwo = mModelQSGX.lxrRecords.get(i).id;
                    }
                }
            }
        } else if (methodName.equals(linkManInfo)) {
            Frame.HANDLES.sentAll("FrgRenzhengxinxi,FrgGerenziliao,FrgRzh", 0, null);
            this.finish();
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("联系人");
    }
}