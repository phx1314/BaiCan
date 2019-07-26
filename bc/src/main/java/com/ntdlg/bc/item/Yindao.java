//
//  Yindao
//
//  Created by DELL on 2017-09-22 08:37:19
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mdx.framework.widget.MImageView;
import com.ntdlg.bc.R;


public class Yindao extends BaseItem {
    public LinearLayout activity_main;
    public MImageView mImageView;


    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_yindao, null);
        convertView.setTag(new Yindao(convertView));
        return convertView;
    }

    public Yindao(View view) {
        this.contentview = view;
        this.context = contentview.getContext();
        initView();
    }

    private void initView() {
        this.contentview.setTag(this);
        findVMethod();
    }

    private void findVMethod() {
        activity_main = (LinearLayout) contentview.findViewById(R.id.activity_main);
        mImageView = (MImageView) contentview.findViewById(R.id.mImageView);


    }

    public void set(Integer item) {
        mImageView.setBackgroundResource(item);
    }


}