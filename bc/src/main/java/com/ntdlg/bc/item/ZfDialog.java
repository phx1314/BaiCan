//
//  ZfDialog
//
//  Created by DELL on 2019-09-06 08:39:50
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.item;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mdx.framework.Frame;
import com.ntdlg.bc.R;


public class ZfDialog extends BaseItem {
    public LinearLayout mLinearLayout_zfb;
    public LinearLayout mLinearLayout_wx;


    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_zf_dialog, null);
        convertView.setTag(new ZfDialog(convertView));
        return convertView;
    }

    public ZfDialog(View view) {
        this.contentview = view;
        this.context = contentview.getContext();
        initView();
    }

    private void initView() {
        this.contentview.setTag(this);
        findVMethod();
    }

    private void findVMethod() {
        mLinearLayout_zfb = (LinearLayout) contentview.findViewById(R.id.mLinearLayout_zfb);
        mLinearLayout_wx = (LinearLayout) contentview.findViewById(R.id.mLinearLayout_wx);

        mLinearLayout_zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Frame.HANDLES.sentAll("FrgZhangdan", 1, 0);
            }
        });
        mLinearLayout_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Frame.HANDLES.sentAll("FrgZhangdan", 2, 0);
            }
        });
    }

    public void set(Dialog item) {

    }


}