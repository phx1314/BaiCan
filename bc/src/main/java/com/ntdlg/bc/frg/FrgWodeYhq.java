//
//  FrgWodeYhq
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.frg;
import android.os.Bundle;

import com.ntdlg.bc.R;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import com.ntdlg.bc.pullview.AbPullListView;



public class FrgWodeYhq extends BaseFrg{

    public RelativeLayout clk_mRelativeLayout_1;
    public TextView mTextView_1;
    public ImageView mImageView_1;
    public RelativeLayout clk_mRelativeLayout_2;
    public TextView mTextView_2;
    public ImageView mImageView_2;
    public AbPullListView mAbPullListView;


 	@Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_wode_yhq);
        initView();
        loaddata();
    }

    private void initView(){
        findVMethod();
    }
    
    private void findVMethod() {
        clk_mRelativeLayout_1=(RelativeLayout)findViewById(R.id.clk_mRelativeLayout_1);
        mTextView_1=(TextView)findViewById(R.id.mTextView_1);
        mImageView_1=(ImageView)findViewById(R.id.mImageView_1);
        clk_mRelativeLayout_2=(RelativeLayout)findViewById(R.id.clk_mRelativeLayout_2);
        mTextView_2=(TextView)findViewById(R.id.mTextView_2);
        mImageView_2=(ImageView)findViewById(R.id.mImageView_2);
        mAbPullListView=(AbPullListView)findViewById(R.id.mAbPullListView);

        clk_mRelativeLayout_1.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mRelativeLayout_2.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));

    }
    
    public void loaddata(){

    }
    
	@Override
	public void onClick(android.view.View v) {

        if(R.id.clk_mRelativeLayout_1==v.getId()){

        }else if(R.id.clk_mRelativeLayout_2==v.getId()){

        }
	}   
 
}