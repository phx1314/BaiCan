//
//  FrgZhangdanRzh
//
//  Created by DELL on 2017-06-09 16:03:26
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.framewidget.view.MGridPhotoChoose;
import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanZDRZ;

import java.util.ArrayList;
import java.util.List;

import static com.ntdlg.bc.F.Bitmap2StrByBase64;
import static com.ntdlg.bc.F.orderAuth;
import static com.ntdlg.bc.F.readClassByJson;


public class FrgZhangdanRzh extends BaseFrg {

    public MGridPhotoChoose mMGridPhotoChoose;
    public TextView mTextView_shenqing;


    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_zhangdan_rzh);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mMGridPhotoChoose = (MGridPhotoChoose) findViewById(R.id.mMGridPhotoChoose);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);

        mMGridPhotoChoose.setMax(3);
    }

    public void loaddata() {
        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMGridPhotoChoose.getData().size() <= 0) {
                    Helper.toast("请选择照片", getContext());
                } else {
                    BeanZDRZ mBeanZDRZ = new BeanZDRZ();
                    List<String> bills = new ArrayList<String>();
                    for (int i = 0; i < mMGridPhotoChoose.getData().size(); i++) {
                        bills.add(Bitmap2StrByBase64(mMGridPhotoChoose.getData().get(i)));
                    }
                    mBeanZDRZ.bills = new Gson().toJson(bills);
                    mBeanZDRZ.sign = readClassByJson(new Gson().toJson(mBeanZDRZ));
                    loadJsonUrl(orderAuth, new Gson().toJson(mBeanZDRZ));
                }
            }
        });
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(orderAuth)) {
            Helper.toast("认证成功", getContext());
            this.finish();
            Frame.HANDLES.sentAll("FrgRzh,FrgTezx", 0, null);
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("账单认证");
    }
}