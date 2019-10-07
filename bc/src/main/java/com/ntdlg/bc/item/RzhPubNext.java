//
//  RzhPubNext
//
//  Created by Administrator on 2019-10-07 09:33:18
//  Copyright (c) Administrator All rights reserved.


/**

 */

package com.ntdlg.bc.item;

import com.ntdlg.bc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.EditText;

import com.mdx.framework.widget.MImageView;
import com.ntdlg.bc.model.ModelPub;

import android.widget.TextView;


public class RzhPubNext extends BaseItem {
    public LinearLayout mLinearLayout_1;
    public EditText mEditText_code1;
    public LinearLayout mLinearLayout_2;
    public EditText mEditText_code2;
    public MImageView MImageView;
    public LinearLayout mLinearLayout_3;
    public MImageView MImageView_ewm;
    public TextView mTextView;
    public ModelPub.NextProcessBean item;

    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_rzh_pub_next, null);
        convertView.setTag(new RzhPubNext(convertView));
        return convertView;
    }

    public RzhPubNext(View view) {
        this.contentview = view;
        this.context = contentview.getContext();
        initView();
    }

    private void initView() {
        this.contentview.setTag(this);
        findVMethod();
    }

    private void findVMethod() {
        mLinearLayout_1 = (LinearLayout) contentview.findViewById(R.id.mLinearLayout_1);
        mEditText_code1 = (EditText) contentview.findViewById(R.id.mEditText_code1);
        mLinearLayout_2 = (LinearLayout) contentview.findViewById(R.id.mLinearLayout_2);
        mEditText_code2 = (EditText) contentview.findViewById(R.id.mEditText_code2);
        MImageView = (MImageView) contentview.findViewById(R.id.MImageView);
        mLinearLayout_3 = (LinearLayout) contentview.findViewById(R.id.mLinearLayout_3);
        MImageView_ewm = (MImageView) contentview.findViewById(R.id.MImageView_ewm);
        mTextView = (TextView) contentview.findViewById(R.id.mTextView);

        mEditText_code1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                item.code = mEditText_code1.getText().toString().trim();
            }
        });
        mEditText_code2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                item.code = mEditText_code1.getText().toString().trim();
            }
        });
    }

    public void set(ModelPub.NextProcessBean item) {
        this.item = item;
        if (item.type.equals("01")) {
            mLinearLayout_1.setVisibility(View.VISIBLE);
            mLinearLayout_2.setVisibility(View.GONE);
            mLinearLayout_3.setVisibility(View.GONE);
        } else if (item.type.equals("02")) {
            mLinearLayout_1.setVisibility(View.GONE);
            mLinearLayout_2.setVisibility(View.VISIBLE);
            mLinearLayout_3.setVisibility(View.GONE);
            MImageView.setObj(item.imageUrl);
        } else if (item.type.equals("03")) {
            mLinearLayout_1.setVisibility(View.GONE);
            mLinearLayout_2.setVisibility(View.GONE);
            mLinearLayout_3.setVisibility(View.VISIBLE);
            MImageView_ewm.setObj(item.imageUrl);
            mTextView.setText("提示说明:" + item.prompt);
        }

    }


}