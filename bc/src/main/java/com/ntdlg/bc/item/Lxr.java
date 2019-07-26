//
//  Lxr
//
//  Created by DELL on 2017-06-06 14:55:15
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
import com.ntdlg.bc.view.SortModel;

import java.util.ArrayList;
import java.util.List;

import static com.framewidget.F.isHzorPy;
import static com.framewidget.F.toPinYin;


public class Lxr extends BaseItem {
    public TextView mTextView_title;
    public TextView mTextView;
    public List<SortModel> list = new ArrayList<SortModel>();

    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_lxr, null);
        convertView.setTag(new Lxr(convertView));
        return convertView;
    }

    public Lxr(View view) {
        this.contentview = view;
        this.context = contentview.getContext();
        initView();
    }

    private void initView() {
        this.contentview.setTag(this);
        findVMethod();
    }

    private void findVMethod() {
        mTextView_title = (TextView) contentview.findViewById(R.id.mTextView_title);
        mTextView = (TextView) contentview.findViewById(R.id.mTextView);


    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(char section) {
        for (int i = 0; i < list.size(); i++) {
            String sortStr = toPinYin(list.get(i).name.charAt(0));
            char firstChar = sortStr.charAt(0);
            if (String.valueOf(firstChar).equalsIgnoreCase(String.valueOf(section))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection2(char section) {
        for (int i = 0; i < list.size(); i++) {
            if (!isHzorPy(list.get(i).name.charAt(0))) {
                return i;
            }
        }
        return -1;
    }

    public void set(SortModel item, List<SortModel> list, int position) {
        this.list = list;
        mTextView.setText(item.name);
        if (isHzorPy(item.name.charAt(0))) {
            mTextView_title.setText(toPinYin(item.name.charAt(0)).toUpperCase().charAt(0) + "");
            if (position == getPositionForSection(toPinYin(item.name.charAt(0)).charAt(0))) {
                mTextView_title.setVisibility(View.VISIBLE);
            } else {
                mTextView_title.setVisibility(View.GONE);
            }
        } else {
            mTextView_title.setText("#");
            if (position == getPositionForSection2(item.name.charAt(0))) {
                mTextView_title.setVisibility(View.VISIBLE);
            } else {
                mTextView_title.setVisibility(View.GONE);
            }
        }

    }

    public void set(SortModel item) {
        mTextView.setText(item.name);
        mTextView_title.setVisibility(View.GONE);
    }


}