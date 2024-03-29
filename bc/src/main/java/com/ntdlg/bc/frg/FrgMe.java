//
//  FrgMe
//
//  Created by DELL on 2019-08-02 09:22:28
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.frg;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.model.ModelGRXYRZXX;

import static com.ntdlg.bc.F.elemeAuth;
import static com.ntdlg.bc.F.getPlatform;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.meituanAuth;
import static com.ntdlg.bc.F.rZhengWb;
import static com.ntdlg.bc.F.readClassAttr;


public class FrgMe extends BaseFrg{

    public LinearLayout mLinearLayout_1;
    public TextView mTextView_1;
    public LinearLayout mLinearLayout_2;
    public TextView mTextView_2;
    public TextView mTextView_remark;
    public TextView mTextView_shenqing;
    public ModelGRXYRZXX mModelGRXYRZXX;

 	@Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_me);
        initView();
        loaddata();
    }
    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type){
            case 0:
                loaddata();
                break;
        }
    }
    private void initView(){
        findVMethod();
    }
    
    private void findVMethod() {
        mLinearLayout_1=(LinearLayout)findViewById(R.id.mLinearLayout_1);
        mTextView_1=(TextView)findViewById(R.id.mTextView_1);
        mLinearLayout_2=(LinearLayout)findViewById(R.id.mLinearLayout_2);
        mTextView_2=(TextView)findViewById(R.id.mTextView_2);
        mTextView_remark=(TextView)findViewById(R.id.mTextView_remark);
        mTextView_shenqing=(TextView)findViewById(R.id.mTextView_shenqing);

        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrgMe.this.finish();
            }
        });
        mLinearLayout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mModelGRXYRZXX.isMeituanAuth.equals("1")) {
                    Helper.toast("您已认证", getContext());
                    return;
                }
                rZhengWb(getActivity(), F.MT,FrgMe.this);
            }
        });
        mLinearLayout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mModelGRXYRZXX.isElemeAuth.equals("1")) {
                    Helper.toast("您已认证", getContext());
                    return;
                }
                rZhengWb(getActivity(), F.ELM,FrgMe.this);
            }
        });
    }

    public void loaddata() {
        BeanBase mBeanBase = new BeanBase();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(getPlatform, new Gson().toJson(mBeanBase));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(meituanAuth) || methodName.equals(elemeAuth)) {
            Frame.HANDLES.sentAll("FrgRenzhengxinxi,FrgRzh", 0, null);
            loaddata();
        } else if (methodName.equals(getPlatform)) {
            mModelGRXYRZXX = (ModelGRXYRZXX) json2Model(content, ModelGRXYRZXX.class);
            if (mModelGRXYRZXX.isMeituanAuth.equals("1")) {
                mTextView_1.setText("已认证");
                mTextView_1.setTextColor(getResources().getColor(R.color.A));
            } else {
                mTextView_1.setText("未认证");
                mTextView_1.setTextColor(getResources().getColor(R.color.shouye_red));
            }
            if (mModelGRXYRZXX.isElemeAuth.equals("1")) {
                mTextView_2.setText("已认证");
                mTextView_2.setTextColor(getResources().getColor(R.color.A));
            } else {
                mTextView_2.setText("未认证");
                mTextView_2.setTextColor(getResources().getColor(R.color.shouye_red));
            }
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("美团/饿了么认证");
    }
    
   
 
}