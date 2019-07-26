//
//  FrgJbxx
//
//  Created by DELL on 2017-06-23 09:27:51
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
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.dialog.DataSelectDialog;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.utility.commons.AddressChoose;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanJBXX;
import com.ntdlg.bc.model.BaseData;
import com.ntdlg.bc.model.ModelXL;

import static com.ntdlg.bc.F.basicInfo;
import static com.ntdlg.bc.F.education;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.readClassAttr;


public class FrgJbxx extends BaseFrg implements DataSelectDialog.OnSelected {

    public EditText mEditText_name;
    public LinearLayout mLinearLayout_address;
    public TextView mTextView_address;
    public EditText mEditText_detail;
    public TextView mTextView_fw;
    public TextView mTextView_xl;
    public TextView mTextView_hy;
    public TextView mTextView_shenqing;
    public LinearLayout mLinearLayout_fw;
    public LinearLayout mLinearLayout_xl;
    public LinearLayout mLinearLayout_hy;
    public EditText mEditText_userCid;
    private DataSelectDialog addressdialog;
    private ModelXL mModelXL;
    private BeanJBXX mBeanJBXX = new BeanJBXX();

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_jbxx);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 101:
                BaseData mBaseData = (BaseData) obj;
                mTextView_fw.setText(mBaseData.value);
                mBeanJBXX.userInhabiting = mBaseData.code;
                break;
            case 102:
                BaseData mBaseData2 = (BaseData) obj;
                mTextView_xl.setText(mBaseData2.value);
                mBeanJBXX.userEducation = mBaseData2.code;
                break;
            case 103:
                BaseData mBaseData3 = (BaseData) obj;
                mTextView_hy.setText(mBaseData3.value);
                mBeanJBXX.userMarriage = mBaseData3.code;
                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        AddressChoose data = new AddressChoose();
        addressdialog = new DataSelectDialog(getActivity(), data);
        addressdialog.setOnSelected(this);
        mEditText_name = (EditText) findViewById(R.id.mEditText_name);
        mLinearLayout_address = (LinearLayout) findViewById(R.id.mLinearLayout_address);
        mTextView_address = (TextView) findViewById(R.id.mTextView_address);
        mEditText_detail = (EditText) findViewById(R.id.mEditText_detail);
        mTextView_fw = (TextView) findViewById(R.id.mTextView_fw);
        mTextView_xl = (TextView) findViewById(R.id.mTextView_xl);
        mTextView_hy = (TextView) findViewById(R.id.mTextView_hy);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);
        mLinearLayout_fw = (LinearLayout) findViewById(R.id.mLinearLayout_fw);
        mLinearLayout_xl = (LinearLayout) findViewById(R.id.mLinearLayout_xl);
        mLinearLayout_hy = (LinearLayout) findViewById(R.id.mLinearLayout_hy);
        mEditText_userCid = (EditText) findViewById(R.id.mEditText_userCid);

        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEditText_name.getText().toString().trim())) {
                    Helper.toast("请输入姓名", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mEditText_userCid.getText().toString().trim())) {
                    Helper.toast("请输入身份证号码", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mTextView_address.getText().toString().trim())) {
                    Helper.toast("请选择城市", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mEditText_detail.getText().toString().trim())) {
                    Helper.toast("请输入详细地址", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mTextView_fw.getText().toString().trim())) {
                    Helper.toast("请选择房屋类型", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mTextView_xl.getText().toString().trim())) {
                    Helper.toast("请选择学历", getContext());
                    return;
                }
                if (TextUtils.isEmpty(mTextView_hy.getText().toString().trim())) {
                    Helper.toast("请选择婚姻状况", getContext());
                    return;
                }

                mBeanJBXX.userName = mEditText_name.getText().toString().trim();
                mBeanJBXX.userSSQ = mTextView_address.getText().toString().trim();
                mBeanJBXX.userCid = mEditText_userCid.getText().toString().trim();
                mBeanJBXX.userResidence = mEditText_detail.getText().toString().trim();
                mBeanJBXX.sign = readClassAttr(mBeanJBXX);
                loadJsonUrl(basicInfo, new Gson().toJson(mBeanJBXX));
            }
        });
        mLinearLayout_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressdialog.show();
            }
        });
        mLinearLayout_fw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.startActivity(getContext(), FrgPubList.class, TitleAct.class, "from", "FrgJbxx", "type", 101, "data", mModelXL.jzqkRecords);
            }
        });
        mLinearLayout_xl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.startActivity(getContext(), FrgPubList.class, TitleAct.class, "from", "FrgJbxx", "type", 102, "data", mModelXL.xlRecords);
            }
        });
        mLinearLayout_hy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.startActivity(getContext(), FrgPubList.class, TitleAct.class, "from", "FrgJbxx", "type", 103, "data", mModelXL.hyRecords);
            }
        });
    }

    public void loaddata() {
        BeanBase mBeanBase = new BeanBase();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(education, new Gson().toJson(mBeanBase));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(education)) {
            mModelXL = (ModelXL) json2Model(content, ModelXL.class);
            if (mModelXL.isExist.equals("1")) {
                mEditText_name.setText(mModelXL.username);
                mTextView_address.setText(mModelXL.userSSQ);
                mEditText_userCid.setText(mModelXL.usercid);
                mEditText_detail.setText(mModelXL.userResidence);
                mTextView_fw.setText(mModelXL.userInhabiting);
                mTextView_xl.setText(mModelXL.userEducation);
                mTextView_hy.setText(mModelXL.userMarriage);
            }
        } else if (methodName.equals(basicInfo)) {
            Frame.HANDLES.sentAll("FrgRenzhengxinxi,FrgGerenziliao,FrgRzh", 0, null);
            this.finish();
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("基本信息");
    }

    @Override
    public void onSelected(Dialog dialog, String first, String second, String thread) {
        mTextView_address.setText(first + second + thread);
    }
}