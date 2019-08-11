//
//  FrgSign
//
//  Created by DELL on 2019-07-31 14:20:22
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.f1reking.signatureview.SignatureView;
import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.Beanesgin;

import java.io.IOException;

import static com.ntdlg.bc.F.esgin;
import static com.ntdlg.bc.F.readClassAttr;


public class FrgSign extends BaseFrg {

    public SignatureView mSignatureView;
    public String from;

    @Override
    protected void create(Bundle savedInstanceState) {
        from = getActivity().getIntent().getStringExtra("from");
        setContentView(R.layout.frg_sign);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mSignatureView = (SignatureView) findViewById(R.id.mSignatureView);


    }

    public void loaddata() {
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(esgin)) {
            Helper.toast("签名成功", getContext());
            FrgSign.this.finish();
            Helper.startActivity(getContext(), FrgJkShenqing.class, TitleAct.class);
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("签名");
        mHeadlayout.setRText("完成");
        mHeadlayout.setR2Text("清除");

        mHeadlayout.setRightOnclicker(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mSignatureView.save(Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".png", true, 0);
//                    Frame.HANDLES.sentAll("FrgWode",1,mSignatureView.getSavePath());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final Beanesgin mBeanesgin = new Beanesgin();
                            mBeanesgin.esign = F.Bitmap2StrByBase64(mSignatureView.getSavePath());
                            mBeanesgin.sign = readClassAttr(mBeanesgin);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadJsonUrl(esgin, new Gson().toJson(mBeanesgin));
                                }
                            });
                        }
                    }).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        mHeadlayout.setRight2Onclicker(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.clear();
            }
        });
    }
}