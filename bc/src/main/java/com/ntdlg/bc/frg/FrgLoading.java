//
//  FrgLoading
//
//  Created by DELL on 2017-05-27 13:46:33
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

import com.mdx.framework.activity.IndexAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.MImageView;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;


public class FrgLoading extends BaseFrg {

    public RelativeLayout mLinearLayout;
    public MImageView iv_loading;


    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_loading);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mLinearLayout = (RelativeLayout) findViewById(R.id.mLinearLayout);
        iv_loading = (MImageView) findViewById(R.id.iv_loading);
        if (F.isFirstInstall()) {
            Helper.startActivity(getContext(), FrgYindao.class, IndexAct.class);
            FrgLoading.this.finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Helper.startActivity(getContext(), FrgHome.class, IndexAct.class);
                    FrgLoading.this.finish();
                }
            }, 2000);
        }

    }

    public void loaddata() {

    }


}