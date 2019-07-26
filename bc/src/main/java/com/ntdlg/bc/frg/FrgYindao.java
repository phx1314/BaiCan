//
//  FrgYindao
//
//  Created by DELL on 2017-09-22 08:37:16
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.framewidget.view.DfCirleCurr;
import com.ntdlg.bc.R;
import com.ntdlg.bc.ada.AdaYindao;

import java.util.ArrayList;
import java.util.List;


public class FrgYindao extends BaseFrg {

    public LinearLayout activity_main;
    public DfCirleCurr mDfCirleCurr;
    public List<Integer> data = new ArrayList<>();

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_yindao);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        data.add(R.drawable.a);
        data.add(R.drawable.b);
        data.add(R.drawable.c);
        activity_main = (LinearLayout) findViewById(R.id.activity_main);
        mDfCirleCurr = (DfCirleCurr) findViewById(R.id.mDfCirleCurr);
        mDfCirleCurr.setAutoScroll(false);
        mDfCirleCurr.setPageColor(Color.parseColor("#00000000"));
        mDfCirleCurr.setFillColor(Color.parseColor("#00000000"));
        mDfCirleCurr.setAdapter(new AdaYindao(getContext(), data));
    }

    public void loaddata() {

    }


}