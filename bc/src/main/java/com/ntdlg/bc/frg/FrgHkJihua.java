//
//  FrgHkJihua
//
//  Created by DELL on 2017-06-09 16:10:17
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.frg;
import android.os.Bundle;

import com.ntdlg.bc.R;

import android.widget.TextView;
import com.ntdlg.bc.pullview.AbPullListView;



public class FrgHkJihua extends BaseFrg{

    public TextView mTextView_hk_qc;
    public TextView mTextView_hk_je;
    public TextView mTextView_hk_time;
    public AbPullListView mAbPullListView;


 	@Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_hk_jihua);
        initView();
        loaddata();
    }

    private void initView(){
        findVMethod();
    }
    
    private void findVMethod() {
        mTextView_hk_qc=(TextView)findViewById(R.id.mTextView_hk_qc);
        mTextView_hk_je=(TextView)findViewById(R.id.mTextView_hk_je);
        mTextView_hk_time=(TextView)findViewById(R.id.mTextView_hk_time);
        mAbPullListView=(AbPullListView)findViewById(R.id.mAbPullListView);


    }
    
    public void loaddata(){

    }
    
   
 
}