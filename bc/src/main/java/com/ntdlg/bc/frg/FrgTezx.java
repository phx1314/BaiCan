//
//  FrgTezx
//
//  Created by DELL on 2017-07-05 13:42:43
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.moxie.client.model.MxParam;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanSQTE;
import com.ntdlg.bc.model.ModelGRXYRZXX;

import static com.ntdlg.bc.F.applyPromote;
import static com.ntdlg.bc.F.getPlatform;
import static com.ntdlg.bc.F.gongjijinAuth;
import static com.ntdlg.bc.F.jingdongAuth;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.rZhengWb;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.taobaoAuth;
import static com.ntdlg.bc.F.xuexinwangAuth;


public class FrgTezx extends BaseFrg {

    public ImageView mImageView_1;
    public TextView mTextView_1;
    public ImageView mImageView_2;
    public TextView mTextView_2;
    public ImageView mImageView_3;
    public TextView mTextView_3;
    public ImageView mImageView_4;
    public TextView mTextView_4;
    public ImageView mImageView_5;
    public TextView mTextView_5;
    public LinearLayout mLinearLayout_1;
    public LinearLayout mLinearLayout_2;
    public LinearLayout mLinearLayout_3;
    public LinearLayout mLinearLayout_4;
    public LinearLayout mLinearLayout_5;
    public ModelGRXYRZXX mModelGRXYRZXX;
    public String type = "1";
    public TextView mTextView_lg;
    public TextView mTextView_sb;
    public LinearLayout mLinearLayout_sb;
    public ImageView mImageView_sb;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_tezx);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 0:
                loaddata();
                break;

        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mImageView_1 = (ImageView) findViewById(R.id.mImageView_1);
        mTextView_1 = (TextView) findViewById(R.id.mTextView_1);
        mImageView_2 = (ImageView) findViewById(R.id.mImageView_2);
        mTextView_2 = (TextView) findViewById(R.id.mTextView_2);
        mImageView_3 = (ImageView) findViewById(R.id.mImageView_3);
        mTextView_3 = (TextView) findViewById(R.id.mTextView_3);
        mImageView_4 = (ImageView) findViewById(R.id.mImageView_4);
        mTextView_4 = (TextView) findViewById(R.id.mTextView_4);
        mImageView_5 = (ImageView) findViewById(R.id.mImageView_5);
        mTextView_5 = (TextView) findViewById(R.id.mTextView_5);
        mLinearLayout_1 = (LinearLayout) findViewById(R.id.mLinearLayout_1);
        mLinearLayout_2 = (LinearLayout) findViewById(R.id.mLinearLayout_2);
        mLinearLayout_3 = (LinearLayout) findViewById(R.id.mLinearLayout_3);
        mLinearLayout_4 = (LinearLayout) findViewById(R.id.mLinearLayout_4);
        mLinearLayout_5 = (LinearLayout) findViewById(R.id.mLinearLayout_5);
        mTextView_lg = (TextView) findViewById(R.id.mTextView_lg);
        mTextView_sb = (TextView) findViewById(R.id.mTextView_sb);
        mLinearLayout_sb = (LinearLayout) findViewById(R.id.mLinearLayout_sb);
        mImageView_sb = (ImageView) findViewById(R.id.mImageView_sb);

        mLinearLayout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mModelGRXYRZXX.isTaobaoAuth.equals("1")) {
                    rZhengWb(getActivity(), MxParam.PARAM_TASK_TAOBAO, FrgTezx.this);//淘宝
                }
            }
        });
        mLinearLayout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mModelGRXYRZXX.isGongjijinAuth.equals("1")) {
                    type = "2";
                    rZhengWb(getActivity(), MxParam.PARAM_TASK_FUND, FrgTezx.this);//公积金
                }
            }
        });
        mLinearLayout_sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mModelGRXYRZXX.IsShebaoAuth.equals("1")) {
                    type = MxParam.PARAM_TASK_FUND;
                    rZhengWb(getActivity(), MxParam.PARAM_TASK_SECURITY, FrgTezx.this);//社保
                }
            }
        });
        mLinearLayout_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mModelGRXYRZXX.isFundAuth.equals("1")) {
                    type = F.MT;
                    rZhengWb(getActivity(), F.MT, FrgTezx.this);//美团
                }
            }
        });
        mLinearLayout_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mModelGRXYRZXX.isBillAuth.equals("1")) {
                    type = F.ELM;
                    rZhengWb(getActivity(), F.ELM, FrgTezx.this);//饿了么
                }
            }
        });
        mLinearLayout_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mModelGRXYRZXX.isJingdongAuth.equals("1")) {
                    type = "4";
                    rZhengWb(getActivity(), MxParam.PARAM_TASK_JINGDONG, FrgTezx.this);
                }
            }
        });
        mTextView_lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BeanSQTE mBeanSQTE = new BeanSQTE();
                mBeanSQTE.sign = readClassAttr(mBeanSQTE);
                loadJsonUrl(applyPromote, new Gson().toJson(mBeanSQTE));
            }
        });
    }

    public void loaddata() {
        BeanBase mBeanBase = new BeanBase();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(getPlatform, new Gson().toJson(mBeanBase));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(getPlatform)) {
            mModelGRXYRZXX = (ModelGRXYRZXX) json2Model(content, ModelGRXYRZXX.class);
            if (mModelGRXYRZXX.isTaobaoAuth.equals("1")) {
                mImageView_1.setImageResource(R.drawable.authentication1);
                mTextView_1.setText("已认证");
                mTextView_1.setTextColor(getResources().getColor(R.color.A));
            } else {
                mImageView_1.setImageResource(R.drawable.tao);
                mTextView_1.setText("未认证");
                mTextView_1.setTextColor(getResources().getColor(R.color.shouye_red));
            }
            if (mModelGRXYRZXX.isGongjijinAuth.equals("1")) {
                mImageView_2.setImageResource(R.drawable.authentication2);
                mTextView_2.setText("已认证");
                mTextView_2.setTextColor(getResources().getColor(R.color.A));
            } else {
                mImageView_2.setImageResource(R.drawable.gong);
                mTextView_2.setText("未认证");
                mTextView_2.setTextColor(getResources().getColor(R.color.shouye_red));
            }
            if (mModelGRXYRZXX.IsShebaoAuth.equals("1")) {
                mImageView_sb.setImageResource(R.drawable.authentication2);
                mTextView_sb.setText("已认证");
                mTextView_sb.setTextColor(getResources().getColor(R.color.A));
            } else {
                mImageView_sb.setImageResource(R.drawable.gong);
                mTextView_sb.setText("未认证");
                mTextView_sb.setTextColor(getResources().getColor(R.color.shouye_red));
            }
//            if (mModelGRXYRZXX.isFundAuth.equals("1")) {
//                mImageView_3.setImageResource(R.drawable.authentication3);
//                mTextView_3.setText("已认证");
//                mTextView_3.setTextColor(getResources().getColor(R.color.A));
//            } else {
//                mImageView_3.setImageResource(R.drawable.xue);
//                mTextView_3.setText("未认证");
//                mTextView_3.setTextColor(getResources().getColor(R.color.shouye_red));
//            }
//            if (mModelGRXYRZXX.isBillAuth.equals("1")) {
//                mImageView_4.setImageResource(R.drawable.authentication4);
//                mTextView_4.setText("已认证");
//                mTextView_4.setTextColor(getResources().getColor(R.color.A));
//            } else {
//                mImageView_4.setImageResource(R.drawable.zhang);
//                mTextView_4.setText("未认证");
//                mTextView_4.setTextColor(getResources().getColor(R.color.shouye_red));
//            }
            if (mModelGRXYRZXX.isJingdongAuth.equals("1")) {
                mImageView_5.setImageResource(R.drawable.jd);
                mTextView_5.setText("已认证");
                mTextView_5.setTextColor(getResources().getColor(R.color.A));
            } else {
                mImageView_5.setImageResource(R.drawable.jd1);
                mTextView_5.setText("未认证");
                mTextView_5.setTextColor(getResources().getColor(R.color.shouye_red));
            }
        } else if (methodName.equals(taobaoAuth) || methodName.equals(gongjijinAuth) || methodName.equals(xuexinwangAuth) || methodName.equals(jingdongAuth)) {
            loaddata();
            Frame.HANDLES.sentAll("FrgRzh", 0, null);
        } else if (methodName.equals(applyPromote)) {
            Helper.toast("提交成功", getContext());
            Frame.HANDLES.sentAll("FrgSxed", 0, null);
            this.finish();
        }
    }


    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("提额增信");
    }
}