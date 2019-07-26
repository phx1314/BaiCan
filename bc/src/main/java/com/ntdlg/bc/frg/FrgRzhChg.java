//
//  FrgRzhChg
//
//  Created by DELL on 2017-06-13 13:57:57
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.frg;
import android.os.Bundle;

import com.ntdlg.bc.R;

import android.widget.TextView;



public class FrgRzhChg extends BaseFrg{

    public TextView mTextView_shenqing;
    public TextView mImageView_te_1;
    public TextView mImageView_te_2;
    public TextView mImageView_te_3;
    public TextView mImageView_te_4;


 	@Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_rzh_chg);
        initView();
        loaddata();
    }

    private void initView(){
        findVMethod();
    }
    
    private void findVMethod() {
        mTextView_shenqing=(TextView)findViewById(R.id.mTextView_shenqing);
        mImageView_te_1=(TextView)findViewById(R.id.mImageView_te_1);
        mImageView_te_2=(TextView)findViewById(R.id.mImageView_te_2);
        mImageView_te_3=(TextView)findViewById(R.id.mImageView_te_3);
        mImageView_te_4=(TextView)findViewById(R.id.mImageView_te_4);


    }
    
    public void loaddata(){

    }
    
   
 
}