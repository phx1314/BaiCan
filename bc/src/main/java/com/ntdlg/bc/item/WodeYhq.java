//
//  WodeYhq
//
//  Created by DELL on 2017-06-06 14:25:03
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



public class WodeYhq extends BaseItem{
    public TextView mTextView_type;
    public TextView mTextView_time;
    public TextView mTextView_name;
    public TextView mTextView_remark;
    public TextView mTextView_remark1;


	@SuppressLint("InflateParams")
    public static View getView(Context context,ViewGroup parent){
	     LayoutInflater flater = LayoutInflater.from(context);
	     View convertView = flater.inflate(R.layout.item_wode_yhq,null);
	     convertView.setTag( new WodeYhq(convertView));
	     return convertView;
	}

	public WodeYhq(View view){
		this.contentview=view;
		this.context=contentview.getContext();
		initView();
	}
    
    private void initView() {
    	this.contentview.setTag(this);
    	findVMethod();
    }

    private void findVMethod(){
        mTextView_type=(TextView)contentview.findViewById(R.id.mTextView_type);
        mTextView_time=(TextView)contentview.findViewById(R.id.mTextView_time);
        mTextView_name=(TextView)contentview.findViewById(R.id.mTextView_name);
        mTextView_remark=(TextView)contentview.findViewById(R.id.mTextView_remark);
        mTextView_remark1=(TextView)contentview.findViewById(R.id.mTextView_remark1);


    }

    public void set(String item){

    }
    
    

}