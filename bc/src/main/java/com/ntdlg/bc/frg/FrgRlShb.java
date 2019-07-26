//
//  FrgRlShb
//
//  Created by DELL on 2017-06-12 15:57:53
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.frg;
import android.os.Bundle;

import com.ntdlg.bc.R;

import android.widget.TextView;



public class FrgRlShb extends BaseFrg{

    public TextView mTextView_shenqing;


 	@Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_rl_shb);
        initView();
        loaddata();
    }

    private void initView(){
        findVMethod();
    }
    
    private void findVMethod() {
        mTextView_shenqing=(TextView)findViewById(R.id.mTextView_shenqing);


    }
    
    public void loaddata(){

    }
    
   
 
}