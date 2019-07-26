//
//  FrgHome
//
//  Created by DELL on 2017-05-27 13:45:12
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ab.http.HttpUtil;
import com.framewidget.newMenu.OnCheckChange;
import com.framewidget.newMenu.OnPageSelset;
import com.framewidget.newMenu.SlidingFragment;
import com.framewidget.view.CallBackOnly;
import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.Beanupdateversion;
import com.ntdlg.bc.model.ModelVersion;

import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;
import static com.ntdlg.bc.F.getVersionCode;
import static com.ntdlg.bc.F.getVersionName;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.updateversion;


public class FrgHome extends BaseFrg implements OnCheckChange, OnPageSelset {

    public LinearLayout mLinearLayout_content;
    public SlidingFragment mSlidingFragment;
    public android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_home);
        setWindowStatusBarColor(getActivity(), R.color.A);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, final Object obj) {
        switch (type) {
            case 1105:
                F.Login("", "", "");
                Frame.HANDLES.sentAll("FrgWode,FrgRzh", 0, null);
                Frame.HANDLES.sentAll("FrgShouye", 1, null);
                Helper.toast("请登录", getContext());
                Frame.HANDLES.close("FrgLogin");
                Helper.startActivity(getContext(), Intent.FLAG_ACTIVITY_CLEAR_TOP, FrgLogin.class, TitleAct.class);
                break;
            case 0:
//                mSlidingFragment.setMoveback(new MoveCallback() {
//                    @Override
//                    public boolean getBol() {
//                        return Boolean.valueOf(obj.toString());
//                    }
//                });
                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mLinearLayout_content = (LinearLayout) findViewById(R.id.mLinearLayout_content);
        mSlidingFragment = new SlidingFragment(this);
        fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.mLinearLayout_content, mSlidingFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        mSlidingFragment.addContentView(new FrgShouye(), "首页",
                R.drawable.btn_checked_1);
        mSlidingFragment.addContentView(new FrgRzh(), "认证",
                R.drawable.btn_checked_2);
        mSlidingFragment.addContentView(new FrgWode(), "我的",
                R.drawable.btn_checked_3);
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        mSlidingFragment.setFadeDegree(0.5f);
        F.loadContacts(getContext());
    }

    public void loaddata() {
        com.framewidget.F.mCallBackOnly = new CallBackOnly() {
            @Override
            public void goReturn(String token, String reftoken) {
                F.Login(token, reftoken);
            }

            @Override
            public void goReturnDo(Dialog mDialog) {

            }
        };

        Beanupdateversion mBeanupdateversion = new Beanupdateversion();
        mBeanupdateversion.version = getVersionCode();
        mBeanupdateversion.sign = readClassAttr(mBeanupdateversion);
        loadJsonUrlNoshow(updateversion, new Gson().toJson(mBeanupdateversion));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(updateversion)) {
            ModelVersion mModelVersion = (ModelVersion) json2Model(content, ModelVersion.class);
            if (!TextUtils.isEmpty(mModelVersion.url) && !mModelVersion.version.equals(getVersionName())) {
                HttpUtil.updateApk(getActivity(), mModelVersion.url);
            }
        }
    }

    @Override
    public void onCheckedChanged(int id, int position) {
        switch (position) {
            case 0:
                Frame.HANDLES.sentAll("FrgShouye", 0, null);
                break;
            case 1:
                Frame.HANDLES.sentAll("FrgRzh", 0, null);
                break;
            case 2:
                Frame.HANDLES.sentAll("FrgWode", 0, null);
                break;
        }
    }

    @Override
    public void OnPageSelseTed(int position) {
        switch (position) {
            case 0:
                Frame.HANDLES.sentAll("FrgShouye", 0, null);
                break;
            case 1:
                Frame.HANDLES.sentAll("FrgRzh", 0, null);
                break;
            case 2:
                Frame.HANDLES.sentAll("FrgWode", 0, null);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                Bundle b = data.getExtras();              //data为B中回传的Intent
                String result = b.getString("result");    //result即为回传的值(JSON格式)
                if (TextUtils.isEmpty(result)) {
                    Toast.makeText(getActivity(), "用户没有进行导入操作!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int code = 0;
                        JSONObject jsonObject = new JSONObject(result);

                        code = jsonObject.getInt("code");
                        switch (code) {
                            case -1:
                                Toast.makeText(getActivity(), "用户没有进行导入操作", Toast.LENGTH_SHORT).show();
                                break;
                            case -2:
                                Toast.makeText(getActivity(), "导入失败(平台方服务问题)", Toast.LENGTH_SHORT).show();
                                break;
                            case -3:
                                Toast.makeText(getActivity(), "导入失败(魔蝎数据服务异常)", Toast.LENGTH_SHORT).show();
                                break;
                            case -4:
                                Toast.makeText(getActivity(), "导入失败(" + jsonObject.getString("message") + ")", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(getActivity(), "导入失败", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(getActivity(), "导入成功", Toast.LENGTH_SHORT).show();
                                Frame.HANDLES.sentAll("FrgRzh", 110, jsonObject);
                                break;
                            case 2:
                                /**
                                 * 如果用户中途导入魔蝎SDK会出现这个情况，如需获取最终状态请轮询贵方后台接口
                                 * 魔蝎后台会向贵方后台推送Task通知和Bill通知
                                 * Task通知：登录成功/登录失败
                                 * Bill通知：账单通知
                                 */
                                Toast.makeText(getActivity(), "导入中", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}