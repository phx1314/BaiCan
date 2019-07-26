//
//  FrgLishiZhangdanList
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.ada.AdaLishiZhangdanList;
import com.ntdlg.bc.bean.BeanLSZD;
import com.ntdlg.bc.model.ModelLSZD;
import com.ntdlg.bc.pullview.AbPullListView;

import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.myBills;
import static com.ntdlg.bc.F.readClassAttr;


public class FrgLishiZhangdanList extends BaseFrg {

    public AbPullListView mAbPullListView;


    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_lishi_zhangdan_list);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mAbPullListView = (AbPullListView) findViewById(R.id.mAbPullListView);
        mAbPullListView.setPullRefreshEnable(false);
        mAbPullListView.setPullLoadEnable(false);
    }

    public void loaddata() {

        BeanLSZD mBeanBase = new BeanLSZD();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(myBills, new Gson().toJson(mBeanBase));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(myBills)) {
            ModelLSZD mModelLSZD = (ModelLSZD) json2Model(content, ModelLSZD.class);
            mAbPullListView.setAdapter(new AdaLishiZhangdanList(getContext(),mModelLSZD.records));
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("历史账单");
    }
}