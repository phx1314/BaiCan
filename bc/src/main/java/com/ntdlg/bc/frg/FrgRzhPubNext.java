//
//  FrgRzhPubNext
//
//  Created by Administrator on 2019-10-07 08:52:14
//  Copyright (c) Administrator All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.mdx.framework.widget.MPageListView;
import com.ntdlg.bc.R;
import com.ntdlg.bc.ada.AdaRzhPubNext;
import com.ntdlg.bc.model.ModelPub;

import static android.R.attr.type;


public class FrgRzhPubNext extends BaseFrg {

    public MPageListView mMPageListView;
    public TextView mTextView_shenqing;
    private ModelPub mModelPub;
    private String type;
    private String smsCode = "";
    private String imgCode = "";

    @Override
    protected void create(Bundle savedInstanceState) {
        type = getActivity().getIntent().getStringExtra("type");
        mModelPub = (ModelPub) getActivity().getIntent().getSerializableExtra("mModelPub");
        setContentView(R.layout.frg_rzh_pub_next);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mMPageListView = (MPageListView) findViewById(R.id.mMPageListView);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);

        mMPageListView.setAdapter(new AdaRzhPubNext(getContext(), mModelPub.nextProcess));
    }

    public void loaddata() {
        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean idOk = true;
                for (ModelPub.NextProcessBean mNextProcessBean : mModelPub.nextProcess) {
                    if (mNextProcessBean.type.equals("01")) {
                        if (TextUtils.isEmpty(mNextProcessBean.code)) {
                            Helper.toast("请输入短信验证码", getContext());
                            idOk = false;
                            break;
                        } else {
                            smsCode = mNextProcessBean.code;
                        }

                    }
                    if (mNextProcessBean.type.equals("02")) {
                        if (TextUtils.isEmpty(mNextProcessBean.code)) {
                            Helper.toast("请输入图片验证码", getContext());
                            idOk = false;
                            break;
                        } else {
                            imgCode = mNextProcessBean.code;
                        }
                    }
                }
                if (idOk) {
                    Helper.startActivity(getActivity(), FrgRzhPubLaqu.class, TitleAct.class, "type", type, "smsCode", smsCode, "imgCode", imgCode);
                }
            }
        });
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("认证");
    }
}