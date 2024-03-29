//
//  FrgHome
//
//  Created by DELL on 2017-05-27 13:45:12
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.ab.http.HttpUtil;
import com.deviceinsight.android.DeviceInsightCollector;
import com.deviceinsight.android.DeviceInsightException;
import com.framewidget.newMenu.OnCheckChange;
import com.framewidget.newMenu.OnPageSelset;
import com.framewidget.newMenu.SlidingFragment;
import com.framewidget.view.CallBackOnly;
import com.google.gson.Gson;
import com.liulishuo.filedownloader.FileDownloader;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.utility.permissions.PermissionRequest;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeangetDeviceFingerprint;
import com.ntdlg.bc.bean.Beanupdateversion;
import com.ntdlg.bc.model.ModelVersion;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.ntdlg.bc.F.getDeviceFingerprint;
import static com.ntdlg.bc.F.getVersionCode;
import static com.ntdlg.bc.F.getVersionName;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.updateversion;


public class FrgHome extends BaseFrg implements OnCheckChange, OnPageSelset {

    public LinearLayout mLinearLayout_content;
    public SlidingFragment mSlidingFragment;
    public android.support.v4.app.FragmentManager fragmentManager;
    public WebView mWebView;
    public DeviceInsightCollector collector;
    public static String payload;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_home);
        FileDownloader.setup(getActivity());
        setWindowStatusBarColor(getActivity(), R.color.A);
        initView();
        loaddata();
        PgyUpdateManager.register(getActivity(),
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        try { // 将新版本信息封装到AppBean中
                            final AppBean appBean = getAppBeanFromString(result);
                            new AlertDialog.Builder(getContext())
                                    .setTitle("版本更新")
                                    .setMessage("检查到新版本，是否更新")
                                    .setNegativeButton(
                                            "确定",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {
                                                    startDownloadTask(
                                                            getActivity(),
                                                            appBean.getDownloadURL());
                                                }
                                            }).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                    }
                });


        collector = new DeviceInsightCollector(getActivity());
        Helper.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, new PermissionRequest() {
            public void onGrant(String[] permissions, int[] grantResults) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            payload = collector.collect();
                            final String version = collector.getVersion();
                            Log.i("DeviceInsight", "Collected payload: " + payload);
                            Log.i("DeviceInsight", "Collector version: " + version);
//                            submitPayload(payload, "");
                        } catch (final DeviceInsightException ex) {
                        }
                    }
                }, "Collector").start();

            }
        });
    }


    void submitPayload(String payload, String uri) {
        if (uri.length() > 0 && payload.length() > 0) {
            try {
                mWebView.postUrl(uri, ("user_prefs=" + URLEncoder.encode(payload, "UTF-8")).getBytes());
            } catch (UnsupportedEncodingException ex) {
            }
        }
    }

    @Override
    public void disposeMsg(int type, final Object obj) {
        switch (type) {
            case 1105:
                F.Login("", "", "");
                Frame.HANDLES.sentAll("FrgWode,FrgRenzhengxinxi", 0, null);
                Frame.HANDLES.sentAll("FrgShouye", 1, null);
                Helper.toast("请登录", getContext());
                Frame.HANDLES.close("FrgLogin");
                Helper.startActivity(getContext(), Intent.FLAG_ACTIVITY_CLEAR_TOP, FrgLogin.class, TitleAct.class);
                break;
            case 0:

                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mLinearLayout_content = (LinearLayout) findViewById(R.id.mLinearLayout_content);
        mWebView = (WebView) findViewById(R.id.mWebView);
        mSlidingFragment = new SlidingFragment(this);
        fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.mLinearLayout_content, mSlidingFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        mSlidingFragment.addContentView(new FrgShouye(), "首页",
                R.drawable.btn_checked_1);
        mSlidingFragment.addContentView(new FrgRenzhengxinxi(), "认证",
                R.drawable.btn_checked_2);
        mSlidingFragment.addContentView(new FrgWode(), "我的",
                R.drawable.btn_checked_3);
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        mSlidingFragment.setFadeDegree(0.5f);

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
                Frame.HANDLES.sentAll("FrgRenzhengxinxi", 0, null);
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
                Frame.HANDLES.sentAll("FrgRenzhengxinxi", 0, null);
                break;
            case 2:
                Frame.HANDLES.sentAll("FrgWode", 0, null);
                break;
        }
    }


}