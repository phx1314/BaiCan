//
//  WodeYhk
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ntdlg.bc.R;
import com.ntdlg.bc.model.ModelYHKList;


public class WodeYhk extends BaseItem {
    public TextView mTextView_name;
    public TextView mTextView_code;
    public ImageView mImageView_dui;
    public com.mdx.framework.widget.MImageView mMImageView;


    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_wode_yhk, null);
        convertView.setTag(new WodeYhk(convertView));
        return convertView;
    }

    public WodeYhk(View view) {
        this.contentview = view;
        this.context = contentview.getContext();
        initView();
    }

    private void initView() {
        this.contentview.setTag(this);
        findVMethod();
    }

    private void findVMethod() {
        mTextView_name = (TextView) contentview.findViewById(R.id.mTextView_name);
        mTextView_code = (TextView) contentview.findViewById(R.id.mTextView_code);
        mImageView_dui = (ImageView) contentview.findViewById(R.id.mImageView_dui);
        mMImageView = (com.mdx.framework.widget.MImageView) findViewById(R.id.mMImageView);


    }

    public void set(ModelYHKList.BankcardsBean item) {
        mTextView_name.setText(item.bankName);
        mTextView_code.setText(item.cardNo);
        if (!TextUtils.isEmpty(item.patientia) && item.patientia.equals("1")) {
            mImageView_dui.setVisibility(View.VISIBLE);
        } else {
            mImageView_dui.setVisibility(View.GONE);
        }
        mMImageView.setObj(item.bankLogo);
    }

    public void set(boolean isMr) {
        if (isMr) {
            mImageView_dui.setVisibility(View.VISIBLE);
        } else {
            mImageView_dui.setVisibility(View.GONE);
        }
    }


}