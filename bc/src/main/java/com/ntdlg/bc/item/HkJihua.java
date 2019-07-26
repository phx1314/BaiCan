//
//  HkJihua
//
//  Created by DELL on 2017-06-09 16:11:50
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.item;

import com.ntdlg.bc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.view.View;
import android.widget.TextView;



public class HkJihua extends BaseItem{
    public TextView mTextView_hk_qc;
    public TextView mTextView_hk_je;
    public TextView mTextView_hk_time;


	@SuppressLint("InflateParams")
    public static View getView(Context context,ViewGroup parent){
	     LayoutInflater flater = LayoutInflater.from(context);
	     View convertView = flater.inflate(R.layout.item_hk_jihua,null);
	     convertView.setTag( new HkJihua(convertView));
	     return convertView;
	}

	public HkJihua(View view){
		this.contentview=view;
		this.context=contentview.getContext();
		initView();
	}
    
    private void initView() {
    	this.contentview.setTag(this);
    	findVMethod();
    }

    private void findVMethod(){
        mTextView_hk_qc=(TextView)contentview.findViewById(R.id.mTextView_hk_qc);
        mTextView_hk_je=(TextView)contentview.findViewById(R.id.mTextView_hk_je);
        mTextView_hk_time=(TextView)contentview.findViewById(R.id.mTextView_hk_time);


    }

    public void set(String item){

    }
    
    

}