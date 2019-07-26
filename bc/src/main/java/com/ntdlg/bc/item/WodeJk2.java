//
//  WodeJk2
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ntdlg.bc.R;
import com.ntdlg.bc.model.ModelWDJK;

import static com.ntdlg.bc.F.changeTime;


public class WodeJk2 extends BaseItem {
    public TextView mTextView_zhou;
    public ImageView mImageView_top;
    public ImageView mImageView_bottom;
    public TextView mTextView_price;
    public TextView mTextView_time;


    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_wode_jk_2, null);
        convertView.setTag(new WodeJk2(convertView));
        return convertView;
    }

    public WodeJk2(View view) {
        this.contentview = view;
        this.context = contentview.getContext();
        initView();
    }

    private void initView() {
        this.contentview.setTag(this);
        findVMethod();
    }

    private void findVMethod() {
        mTextView_zhou = (TextView) contentview.findViewById(R.id.mTextView_zhou);
        mImageView_top = (ImageView) contentview.findViewById(R.id.mImageView_top);
        mImageView_bottom = (ImageView) contentview.findViewById(R.id.mImageView_bottom);
        mTextView_price = (TextView) contentview.findViewById(R.id.mTextView_price);
        mTextView_time = (TextView) contentview.findViewById(R.id.mTextView_time);


    }

    public void set(ModelWDJK.BillsBean item) {
        mTextView_zhou.setText(item.periods + item.term);
        mTextView_price.setText(item.rapayAmount);
        mTextView_time.setText(changeTime(item.beginData) + "    " + changeTime(item.endData));
    }


}