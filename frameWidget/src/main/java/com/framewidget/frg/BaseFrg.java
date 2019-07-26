//
//  BaseFrg
//
//  Created by wchj on 2015-08-17 14:28:55
//  Copyright (c) wchj All rights reserved.

/**

 */

package com.framewidget.frg;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.framewidget.F;
import com.framewidget.R;
import com.framewidget.view.Headlayout;
import com.mdx.framework.activity.MFragment;
import com.mdx.framework.widget.ActionBar;

public abstract class BaseFrg extends MFragment implements View.OnClickListener {

    public Headlayout mHeadlayout;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initcreate(Bundle savedInstanceState) {
        super.initcreate(savedInstanceState);
        LoadingShow = false;
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        mHeadlayout = new Headlayout(context);
        mHeadlayout.setLeftBackground(R.drawable.arrows_left);
        mHeadlayout.setGoBack(getActivity());
        mHeadlayout.setRTColor(getResources().getColor(R.color.YLEa));
        actionBar.addView(mHeadlayout);
    }

    @Override
    public void finish() {
        super.finish();
        F.closeSoftKey(getActivity());
    }
}
