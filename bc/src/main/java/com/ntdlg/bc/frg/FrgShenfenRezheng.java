//
//  FrgShenfenRezheng
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.frg;
import android.os.Bundle;

import com.ntdlg.bc.R;

import android.widget.TextView;
import com.mdx.framework.widget.MImageView;
import android.widget.EditText;



public class FrgShenfenRezheng extends BaseFrg{

    public TextView mImageView_1;
    public TextView mImageView_2;
    public TextView mImageView_3;
    public TextView mImageView_4;
    public MImageView mMImageView_1;
    public MImageView mMImageView_2;
    public EditText mEditText_name;
    public EditText mEditText_code;
    public EditText mEditText_time;
    public TextView mTextView_shenqing;


 	@Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_shenfen_rezheng);
        initView();
        loaddata();
    }

    private void initView(){
        findVMethod();
    }
    
    private void findVMethod() {
        mImageView_1=(TextView)findViewById(R.id.mImageView_1);
        mImageView_2=(TextView)findViewById(R.id.mImageView_2);
        mImageView_3=(TextView)findViewById(R.id.mImageView_3);
        mImageView_4=(TextView)findViewById(R.id.mImageView_4);
        mMImageView_1=(MImageView)findViewById(R.id.mMImageView_1);
        mMImageView_2=(MImageView)findViewById(R.id.mMImageView_2);
        mEditText_name=(EditText)findViewById(R.id.mEditText_name);
        mEditText_code=(EditText)findViewById(R.id.mEditText_code);
        mEditText_time=(EditText)findViewById(R.id.mEditText_time);
        mTextView_shenqing=(TextView)findViewById(R.id.mTextView_shenqing);


    }
    
    public void loaddata(){

    }
    
   
 
}