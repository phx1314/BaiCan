//
//  KfDialog
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



public class KfDialog extends BaseItem{
    public TextView mTextView_cancel;
    public TextView mTextView_zhidian;


	@SuppressLint("InflateParams")
    public static View getView(Context context,ViewGroup parent){
	     LayoutInflater flater = LayoutInflater.from(context);
	     View convertView = flater.inflate(R.layout.item_kf_dialog,null);
	     convertView.setTag( new KfDialog(convertView));
	     return convertView;
	}

	public KfDialog(View view){
		this.contentview=view;
		this.context=contentview.getContext();
		initView();
	}
    
    private void initView() {
    	this.contentview.setTag(this);
    	findVMethod();
    }

    private void findVMethod(){
        mTextView_cancel=(TextView)contentview.findViewById(R.id.mTextView_cancel);
        mTextView_zhidian=(TextView)contentview.findViewById(R.id.mTextView_zhidian);


    }

    public void set(String item){

    }
    
    

}