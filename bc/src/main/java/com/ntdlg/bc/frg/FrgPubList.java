//
//  FrgPubList
//
//  Created by DELL on 2017-06-23 13:24:02
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;

import com.mdx.framework.Frame;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.ada.AdaPubList;
import com.ntdlg.bc.model.BaseData;
import com.ntdlg.bc.pullview.AbPullListView;

import java.util.List;


public class FrgPubList extends BaseFrg {

    public AbPullListView mAbPullListView;
    public String from;
    public int type;
    public List<BaseData> data;

    @Override
    protected void create(Bundle savedInstanceState) {
        from = getActivity().getIntent().getStringExtra("from");
        type = getActivity().getIntent().getIntExtra("type", 0);
        data = (List<BaseData>) getActivity().getIntent().getSerializableExtra("data");
        setContentView(R.layout.frg_pub_list);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 0:
                Frame.HANDLES.sentAll(from, FrgPubList.this.type, obj);
                this.finish();
                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mAbPullListView = (AbPullListView) findViewById(R.id.mAbPullListView);
        mAbPullListView.setAdapter(new AdaPubList(getContext(), data));
    }

    public void loaddata() {
        mAbPullListView.setPullLoadEnable(false);
        mAbPullListView.setPullRefreshEnable(false);
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("请选择");
    }
}