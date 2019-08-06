//
//  DialogCao
//
//  Created by DELL on 2019-08-02 13:19:52
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.item;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdx.framework.Frame;
import com.ntdlg.bc.R;
import com.ntdlg.bc.model.ModelKSJK2;


public class DialogCao extends BaseItem {
    public TextView mTextView_title;
    public TextView mTextView_1;
    public TextView mTextView_2;
    public Dialog item;
    public ModelKSJK2 mModelKSJK2;

    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_dialog_cao, null);
        convertView.setTag(new DialogCao(convertView));
        return convertView;
    }

    public DialogCao(View view) {
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
        mTextView_1 = (TextView) contentview.findViewById(R.id.mTextView_1);
        mTextView_2 = (TextView) contentview.findViewById(R.id.mTextView_2);


    }

    public void set(final Dialog item, ModelKSJK2 mModelKSJK2) {
        this.item = item;
        this.mModelKSJK2 = mModelKSJK2;
        if (!TextUtils.isEmpty(mModelKSJK2.type) && mModelKSJK2.type.equals("1")) {
            mTextView_title.setText("尊敬的会员您现在可以享受vip立即放款功能！");
            mTextView_1.setText("取消");
            mTextView_2.setText("确认");
            mTextView_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.dismiss();
                }
            });
            mTextView_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.dismiss();
                    Frame.HANDLES.sentAll("FrgSxed,FrgWode,FrgWodeJk1", 0, null);
                    Frame.HANDLES.closeIds("FrgJkShenqing,FrgSign");
                }
            });
        } else {
            mTextView_title.setText("尊敬的客户，您现在可以选择购买vip会员即享立即放款功能或者选择24小时放款功能！(工作时间内才能立即放款)");
            mTextView_1.setText("去购买");
            mTextView_2.setText("24小时放款");
            mTextView_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.dismiss();
                    Frame.HANDLES.sentAll("FrgJkShenqing", 1, null);
                }
            });
            mTextView_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.dismiss();
                    Frame.HANDLES.sentAll("FrgJkShenqing", 0, null);
                }
            });
        }
    }


}