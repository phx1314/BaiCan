//
//  FrgWodeJk2
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.os.Bundle;

import com.ab.view.listener.AbOnListViewListener;
import com.mdx.framework.adapter.MAdapter;
import com.ntdlg.bc.R;
import com.ntdlg.bc.ada.AdaWodeJk2;
import com.ntdlg.bc.bean.BeanWDJK;
import com.ntdlg.bc.model.ModelWDJK;
import com.ntdlg.bc.pullview.AbPullListView;

import java.util.ArrayList;

import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.queryBill;


public class FrgWodeJk2 extends BaseFrg {

    public AbPullListView mAbPullListView;


    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_wode_jk_2);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mAbPullListView = (AbPullListView) findViewById(R.id.mAbPullListView);


    }

    public void loaddata() {
        BeanWDJK mBeanWDJK = new BeanWDJK();
        mBeanWDJK.type = "2";
        mAbPullListView.setJsonApiLoadParams(queryBill, mBeanWDJK);
        mAbPullListView.setAdapter(new AdaWodeJk2(getContext(), new ArrayList<ModelWDJK.BillsBean>()));
        mAbPullListView.setAbOnListViewListener(new AbOnListViewListener() {
            @Override
            public MAdapter onSuccess(String methodName, String content) {
                ModelWDJK mModelWDJK = (ModelWDJK) json2Model(content, ModelWDJK.class);
                return new AdaWodeJk2(getContext(), mModelWDJK.bills);
            }
        });


    }
}