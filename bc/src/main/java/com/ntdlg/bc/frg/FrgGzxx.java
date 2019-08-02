//
//  FrgGzxx
//
//  Created by DELL on 2017-06-23 09:57:37
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.dialog.DataSelectDialog;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.utility.commons.AddressChoose;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanZYXX;
import com.ntdlg.bc.model.ModelZYZWXX;

import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.professionInfo;
import static com.ntdlg.bc.F.profrssion;
import static com.ntdlg.bc.F.readClassAttr;


public class FrgGzxx extends BaseFrg implements DataSelectDialog.OnSelected {

    public EditText mEditText_dw;
    public LinearLayout mLinearLayout_dw_address;
    public TextView mTextView_dw_address;
    public EditText mEditText_dw_detail;
    public EditText mTextView_zjh;
    public TextView mTextView_shenqing;
    public ModelZYZWXX mModelZYZWXX;
    private DataSelectDialog addressdialog;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_gzxx);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        AddressChoose data = new AddressChoose();
        addressdialog = new DataSelectDialog(getActivity(), data);
        addressdialog.setOnSelected(this);
        mEditText_dw = (EditText) findViewById(R.id.mEditText_dw);
        mLinearLayout_dw_address = (LinearLayout) findViewById(R.id.mLinearLayout_dw_address);
        mTextView_dw_address = (TextView) findViewById(R.id.mTextView_dw_address);
        mEditText_dw_detail = (EditText) findViewById(R.id.mEditText_dw_detail);
        mTextView_zjh = (EditText) findViewById(R.id.mTextView_zjh);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);

        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEditText_dw.getText().toString().trim())) {
                    Helper.toast("请输入工作单位", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mTextView_dw_address.getText().toString().trim())) {
                    Helper.toast("请选择单位城市", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mEditText_dw_detail.getText().toString().trim())) {
                    Helper.toast("请输入详细地址", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mTextView_zjh.getText().toString().trim())) {
                    Helper.toast("请输入单位电话", getContext());
                    return;
                }

                BeanZYXX mBeanZYXX = new BeanZYXX();
                mBeanZYXX.company = mEditText_dw.getText().toString().trim();
                mBeanZYXX.companyCity = mTextView_dw_address.getText().toString().trim();
                mBeanZYXX.companyAddress = mEditText_dw_detail.getText().toString().trim();
                mBeanZYXX.companyPhone = mTextView_zjh.getText().toString().trim();
                mBeanZYXX.sign = readClassAttr(mBeanZYXX);
                loadJsonUrl(professionInfo, new Gson().toJson(mBeanZYXX));
            }
        });
        mTextView_dw_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressdialog.show();
            }
        });
    }

    @Override
    public void onSelected(Dialog dialog, String first, String second, String thread) {
        mTextView_dw_address.setText(first + second + thread);
    }

    public void loaddata() {
        BeanBase mBeanBase = new BeanBase();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(profrssion, new Gson().toJson(mBeanBase));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(profrssion)) {
            mModelZYZWXX = (ModelZYZWXX) json2Model(content, ModelZYZWXX.class);
            if (mModelZYZWXX.isExist.equals("1")) {
                mEditText_dw.setText(mModelZYZWXX.company);
                mTextView_dw_address.setText(mModelZYZWXX.companyCity);
                mEditText_dw_detail.setText(mModelZYZWXX.companyAddress);
                mTextView_zjh.setText(mModelZYZWXX.companyPhone);
            }
        } else if (methodName.equals(professionInfo)) {
            Frame.HANDLES.sentAll("FrgRenzhengxinxi,FrgGerenziliao", 0, null);
            this.finish();
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("工作收入");
    }
}