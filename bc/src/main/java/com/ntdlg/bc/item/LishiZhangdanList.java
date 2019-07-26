//
//  LishiZhangdanList
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.model.ModelLSZD;


public class LishiZhangdanList extends BaseItem {
    public ImageView mImageView_dot;
    public TextView mTextView_type;
    public TextView mTextView_time;
    public TextView mTextView_time2;
    public TextView mTextView_time3;
    public TextView mTextView_time4;


    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_lishi_zhangdan_list, null);
        convertView.setTag(new LishiZhangdanList(convertView));
        return convertView;
    }

    public LishiZhangdanList(View view) {
        this.contentview = view;
        this.context = contentview.getContext();
        initView();
    }

    private void initView() {
        this.contentview.setTag(this);
        findVMethod();
    }

    private void findVMethod() {
        mImageView_dot = (ImageView) contentview.findViewById(R.id.mImageView_dot);
        mTextView_type = (TextView) contentview.findViewById(R.id.mTextView_type);
        mTextView_time = (TextView) contentview.findViewById(R.id.mTextView_time);
        mTextView_time2 = (TextView) contentview.findViewById(R.id.mTextView_time2);
        mTextView_time3 = (TextView) contentview.findViewById(R.id.mTextView_time3);
        mTextView_time4 = (TextView) contentview.findViewById(R.id.mTextView_time4);


    }

    public void set(ModelLSZD.RecordsBean item) {
        if (item.status.equals("1")) {
            mImageView_dot.setImageResource(R.drawable.lx);
            mTextView_type.setText("已还");
            mTextView_type.setTextColor(Color.parseColor("#46D4C7"));
        } else {
            mImageView_dot.setImageResource(R.drawable.sxf);
            mTextView_type.setText("逾期");
            mTextView_type.setTextColor(Color.parseColor("#FF3A31"));
        }
        mTextView_time.setText(item.currentPeriords + "/" + item.totalPeriords);
        mTextView_time2.setText(item.bqyhje);
        mTextView_time3.setText(item.yhje);
        mTextView_time4.setText(F.changeTime(item.endDate));
    }


}