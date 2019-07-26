//
//  FrgYhkXinxi
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.frg;
import android.os.Bundle;

import com.ntdlg.bc.R;

import android.widget.TextView;
import android.widget.EditText;



public class FrgYhkXinxi extends BaseFrg{

    public TextView mImageView_1;
    public TextView mImageView_2;
    public TextView mImageView_3;
    public TextView mImageView_4;
    public EditText mEditText_code;
    public EditText mEditText_code_yh;
    public EditText mEditText_khh;
    public EditText mEditText_phone;
    public EditText mEditText_yzhm;
    public TextView mTextView_get;
    public TextView mTextView_shenqing;


 	@Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_yhk_xinxi);
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
        mEditText_code=(EditText)findViewById(R.id.mEditText_code);
        mEditText_code_yh=(EditText)findViewById(R.id.mEditText_code_yh);
        mEditText_khh=(EditText)findViewById(R.id.mEditText_khh);
        mEditText_phone=(EditText)findViewById(R.id.mEditText_phone);
        mEditText_yzhm=(EditText)findViewById(R.id.mEditText_yzhm);
        mTextView_get=(TextView)findViewById(R.id.mTextView_get);
        mTextView_shenqing=(TextView)findViewById(R.id.mTextView_shenqing);


    }
    
    public void loaddata(){

    }
    
   
 
}