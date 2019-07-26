//
//  FrgJksq
//
//  Created by DELL on 2017-06-13 14:32:06
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.frg;
import android.os.Bundle;

import com.ntdlg.bc.R;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageButton;



public class FrgJksq extends BaseFrg{

    public LinearLayout mLinearLayout_top;
    public TextView mTextView_price;
    public TextView mTextView_qx;
    public TextView mTextView_fl;
    public TextView mTextView_hk;
    public ImageButton btn_left;
    public TextView tv_title;


 	@Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_jksq);
        initView();
        loaddata();
    }

    private void initView(){
        findVMethod();
    }
    
    private void findVMethod() {
        mLinearLayout_top=(LinearLayout)findViewById(R.id.mLinearLayout_top);
        mTextView_price=(TextView)findViewById(R.id.mTextView_price);
        mTextView_qx=(TextView)findViewById(R.id.mTextView_qx);
        mTextView_fl=(TextView)findViewById(R.id.mTextView_fl);
        mTextView_hk=(TextView)findViewById(R.id.mTextView_hk);
        btn_left=(ImageButton)findViewById(R.id.btn_left);
        tv_title=(TextView)findViewById(R.id.tv_title);


    }
    
    public void loaddata(){

    }
    
   
 
}