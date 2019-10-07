//
//  BaseFrg
//
//  Created by DELL on 2017-05-27 13:45:12
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ab.http.HttpUtil;
import com.ab.util.HttpResponseListener;
import com.ab.util.HttpResponseListenerSon;
import com.framewidget.F;
import com.framewidget.view.Headlayout;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.LoadingAct;
import com.mdx.framework.activity.MFragment;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.moxie.client.manager.MoxieSDK;
import com.ntdlg.bc.R;

import static com.ntdlg.bc.F.isOpen;

public abstract class BaseFrg extends MFragment implements View.OnClickListener, HttpResponseListenerSon {
    public Headlayout mHeadlayout;

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onFailure(int statusCode, String content, Throwable error) {

    }

    @Override
    public void onFail(String methodName, String content) {

    }

    @Override
    public void onSuccess(String methodName, String content) {

    }


    public void loadJson(String methodName, String methodNameBiaoShi, String json) {
        HttpUtil.loadJsonUrl(getContext(), methodName, json, new HttpResponseListener(getContext(), this, methodNameBiaoShi));
    }

    public void loadJsonNoshow(String methodName, String methodNameBiaoShi, String json) {
        HttpUtil.loadJsonUrl(getContext(), methodName, json, new HttpResponseListener(getContext(), this, methodNameBiaoShi, false));
    }

    public void loadJsonUrl(String methodName, String json) {
        HttpUtil.loadJsonUrl(getContext(), methodName, json, new HttpResponseListener(getContext(), this, methodName));
    }

    public void loadJsonUrlNoshow(String methodName, String json) {
        HttpUtil.loadJsonUrl(getContext(), methodName, json, new HttpResponseListener(getContext(), this, methodName, false));
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        mHeadlayout = new Headlayout(context);
        mHeadlayout.setBgColor(context.getResources().getColor(R.color.A2));
        mHeadlayout.setLeftBackground(R.drawable.arrows_left);
        mHeadlayout.setGoBack(getActivity());
        actionBar.addView(mHeadlayout);
    }

    @Override
    protected void initcreate(Bundle savedInstanceState) {
        super.initcreate(savedInstanceState);
        setWindowStatusBarColor(getActivity(), R.color.A2);
        if (!isOpen) {
            Frame.HANDLES.closeAll();
            Helper.startActivity(getContext(), FrgLoading.class, LoadingAct.class);
            isOpen = true;
        }
    }

    @Override
    public void finish() {
        F.closeSoftKey(getActivity());
        super.finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MoxieSDK.getInstance().isDoing()) {
            MoxieSDK.getInstance().show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //用来清理数据或解除引用
        MoxieSDK.getInstance().clear();
    }

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
