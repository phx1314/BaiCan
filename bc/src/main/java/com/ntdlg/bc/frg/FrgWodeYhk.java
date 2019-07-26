//
//  FrgWodeYhk
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanSZMRYHK;
import com.ntdlg.bc.item.WodeYhk;
import com.ntdlg.bc.model.ModelYHKList;

import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.queryCards;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.setDefaultBank;


public class FrgWodeYhk extends BaseFrg {

    public LinearLayout mLinearLayout_kong;
    public TextView mTextView_yhk;
    public LinearLayout mLinearLayout_fk;
    public LinearLayout mLinearLayout_content;
    public BeanSZMRYHK mBeanSZMRYHK = new BeanSZMRYHK();
    public int position_xz;
    public String from;

    @Override
    protected void create(Bundle savedInstanceState) {
        from = getActivity().getIntent().getStringExtra("from");
        setContentView(R.layout.frg_wode_yhk);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 0:
                loaddata();
                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mLinearLayout_kong = (LinearLayout) findViewById(R.id.mLinearLayout_kong);
        mTextView_yhk = (TextView) findViewById(R.id.mTextView_yhk);
        mLinearLayout_fk = (LinearLayout) findViewById(R.id.mLinearLayout_fk);
        mLinearLayout_content = (LinearLayout) findViewById(R.id.mLinearLayout_content);


    }

    public void loaddata() {
        BeanBase mBeanBase = new BeanBase();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(queryCards, new Gson().toJson(mBeanBase));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(queryCards)) {
            mLinearLayout_content.removeAllViews();
            final ModelYHKList mModelYHKList = (ModelYHKList) json2Model(content, ModelYHKList.class);
            if (mModelYHKList.bankcards != null)
                for (int i = 0; i < mModelYHKList.bankcards.size(); i++) {
                    View view = WodeYhk.getView(getContext(), null);
                    ((WodeYhk) view.getTag()).set(mModelYHKList.bankcards.get(i));
                    mLinearLayout_content.addView(view);
                    final int position = i;
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            position_xz = position;
                            mBeanSZMRYHK.bankId = mModelYHKList.bankcards.get(position).bankId;
                            mBeanSZMRYHK.sign = readClassAttr(mBeanSZMRYHK);
                            loadJsonUrl(setDefaultBank, new Gson().toJson(mBeanSZMRYHK));
                        }
                    });
                }
        } else if (methodName.equals(setDefaultBank)) {
            for (int i = 0; i < mLinearLayout_content.getChildCount(); i++) {
                WodeYhk mWodeYhk = (WodeYhk) mLinearLayout_content.getChildAt(i).getTag();
                mWodeYhk.set(position_xz == i);
            }
        }
    }


    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("我的银行卡");
        mHeadlayout.setRText("添加");
        mHeadlayout.setRightOnclicker(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLinearLayout_content.getChildCount() < 3) {
                    Helper.startActivity(getContext(), FrgBdYhk.class, TitleAct.class,"from", TextUtils.isEmpty(from)?"":from);
                } else {
                    Helper.toast("最多绑定三张银行卡", getContext());
                }
            }
        });
    }
}