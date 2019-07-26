//
//  PubList
//
//  Created by DELL on 2017-06-23 13:38:08
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ntdlg.bc.R;
import com.ntdlg.bc.model.BaseData;


public class PubList extends BaseItem {
    public TextView mTextView;


    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_pub_list, null);
        convertView.setTag(new PubList(convertView));
        return convertView;
    }

    public PubList(View view) {
        this.contentview = view;
        this.context = contentview.getContext();
        initView();
    }

    private void initView() {
        this.contentview.setTag(this);
        findVMethod();
    }

    private void findVMethod() {
        mTextView = (TextView) contentview.findViewById(R.id.mTextView);


    }

    public void set(BaseData item) {
        mTextView.setText(item.value);
    }


}