//
//  FrgRzhPubLaqu
//
//  Created by Administrator on 2019-10-07 08:49:00
//  Copyright (c) Administrator All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanperformAuth;
import com.ntdlg.bc.model.ModelPub;

import org.json.JSONObject;

import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.performAuth;
import static com.ntdlg.bc.F.readClassAttr;


public class FrgRzhPubLaqu extends BaseFrg {

    private ProgressBar mProgressBar;
    private int times = 60;
    private Handler handler;
    private Runnable runnable;
    private int count = 0;
    public String type = "";
    public String smsCode = "";
    public String imgCode = "";

    @Override
    protected void create(Bundle savedInstanceState) {
        type = getActivity().getIntent().getStringExtra("type");
        smsCode = getActivity().getIntent().getStringExtra("smsCode");
        imgCode = getActivity().getIntent().getStringExtra("imgCode");
        setContentView(R.layout.frg_rzh_pub_laqu);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);

        handler = new Handler();
        runnable = new Runnable() {

            @Override
            public void run() {
                if (times > 0) {
                    times--;
                    count++;
                    if (count >= 5) {
                        count = 0;
                        loaddata();
                    } else {
                        handler.postDelayed(runnable, 1000);
                    }
                } else if (times == 0) {
                    finish();
                }
            }
        };
        handler.post(runnable);
    }

    public void loaddata() {
        BeanperformAuth mBeanperformAuth = new BeanperformAuth();
        mBeanperformAuth.type = type;
        mBeanperformAuth.smsCode = smsCode;
        mBeanperformAuth.imgCode = imgCode;
        mBeanperformAuth.sign = readClassAttr(mBeanperformAuth);
        loadJsonUrl(performAuth, new Gson().toJson(mBeanperformAuth));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        Helper.toast("认证成功", getContext());
        finish();
        Frame.HANDLES.sentAll("FrgRenzhengxinxi,FrgDshrzh,FrgMe", 0, null);
        Frame.HANDLES.closeIds("FrgRzhPubLaqu,FrgRzhPubNext,FrgRzhPub");
    }

    @Override
    public void onFail(String methodName, String content) {
        try {
            if (!TextUtils.isEmpty(content)) {
                JSONObject mJSONObject = new JSONObject(content);
                if (mJSONObject.getString("errorcode").equals("5100")) {
                    ModelPub mModelPub = (ModelPub) json2Model(content, ModelPub.class);
                    if (mModelPub.nextProcess != null) {
                        Helper.startActivity(getActivity(), FrgRzhPubNext.class, TitleAct.class, "mModelPub", mModelPub, "type", type);
                        finish();
                    } else {
                        Helper.toast("nextProcess数据返回空", getContext());
                    }
                } else if (mJSONObject.getString("errorcode").equals("5000")) {
                    handler.postDelayed(runnable, 1000);
                } else {
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (handler != null)
                handler.removeCallbacks(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("认证中");
    }
}