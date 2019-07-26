//
//  FrgDshrzh
//
//  Created by DELL on 2017-06-23 09:55:39
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.moxie.client.model.MxParam;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanSQ;
import com.ntdlg.bc.model.ModelGRXYRZXX;

import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;
import static com.ntdlg.bc.F.getPlatform;
import static com.ntdlg.bc.F.jingdongAuth;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.rZhengWb;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.taobaoAuth;


public class FrgDshrzh extends BaseFrg {

    public TextView mTextView_1;
    public TextView mTextView_2;
    public TextView mTextView_shenqing;
    public ModelGRXYRZXX mModelGRXYRZXX;
    public LinearLayout mLinearLayout_1;
    public LinearLayout mLinearLayout_2;
    public String type = "1";
    public String remark;
    public TextView mTextView_remark;

    @Override
    protected void create(Bundle savedInstanceState) {
        remark = getActivity().getIntent().getStringExtra("remark");
        setContentView(R.layout.frg_dshrzh);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mTextView_1 = (TextView) findViewById(R.id.mTextView_1);
        mTextView_2 = (TextView) findViewById(R.id.mTextView_2);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);
        mLinearLayout_1 = (LinearLayout) findViewById(R.id.mLinearLayout_1);
        mLinearLayout_2 = (LinearLayout) findViewById(R.id.mLinearLayout_2);
        mTextView_remark = (TextView) findViewById(R.id.mTextView_remark);
        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrgDshrzh.this.finish();
            }
        });
        mLinearLayout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mModelGRXYRZXX.isTaobaoAuth.equals("1")) {
                    Helper.toast("您已认证", getContext());
                    return;
                }
                type = "1";
                rZhengWb(getActivity(), MxParam.PARAM_FUNCTION_TAOBAO);
            }
        });
        mLinearLayout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mModelGRXYRZXX.isJingdongAuth.equals("1")) {
                    Helper.toast("您已认证", getContext());
                    return;
                }
                type = "2";
                rZhengWb(getActivity(), MxParam.PARAM_FUNCTION_JINGDONG);
            }
        });
    }

    public void loaddata() {
        if (!TextUtils.isEmpty(remark)) {
            mTextView_remark.setText(remark);
        }
        BeanBase mBeanBase = new BeanBase();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(getPlatform, new Gson().toJson(mBeanBase));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(taobaoAuth) || methodName.equals(jingdongAuth)) {
            Frame.HANDLES.sentAll("FrgRenzhengxinxi,FrgRzh", 0, null);
            loaddata();
        } else if (methodName.equals(getPlatform)) {
            mModelGRXYRZXX = (ModelGRXYRZXX) json2Model(content, ModelGRXYRZXX.class);
            if (mModelGRXYRZXX.isTaobaoAuth.equals("1")) {
                mTextView_1.setText("已认证");
                mTextView_1.setTextColor(getResources().getColor(R.color.A));
            } else {
                mTextView_1.setText("未认证");
                mTextView_1.setTextColor(getResources().getColor(R.color.shouye_red));
            }
            if (mModelGRXYRZXX.isJingdongAuth.equals("1")) {
                mTextView_2.setText("已认证");
                mTextView_2.setTextColor(getResources().getColor(R.color.A));
            } else {
                mTextView_2.setText("未认证");
                mTextView_2.setTextColor(getResources().getColor(R.color.shouye_red));
            }
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
                                BeanSQ mBeanSQ = new BeanSQ();
                                mBeanSQ.taskId = jsonObject.getString("taskId");
                                mBeanSQ.sign = readClassAttr(mBeanSQ);
                                if (type.equals("1")) {
                                    loadJsonUrl(taobaoAuth, new Gson().toJson(mBeanSQ));
                                } else {
                                    loadJsonUrl(jingdongAuth, new Gson().toJson(mBeanSQ));
                                }
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

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("电商认证");
    }
}