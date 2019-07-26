//
//  FrgRenzhengxinxi
//
//  Created by DELL on 2017-06-23 09:39:47
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.moxie.client.model.MxParam;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanSFSM;
import com.ntdlg.bc.bean.BeanSQ;
import com.ntdlg.bc.bean.BeanSubXSSH;
import com.ntdlg.bc.model.ModelGRXYRZXX;
import com.ntdlg.bc.model.ModelSF;

import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;
import static com.ntdlg.bc.F.getPlatform;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.mTAocrVerify;
import static com.ntdlg.bc.F.rZhengWb;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.scanIdentity;
import static com.ntdlg.bc.F.submitCkeck;
import static com.ntdlg.bc.F.yunyingshangAuth;


public class FrgRenzhengxinxi extends BaseFrg {

    public TextView clk_mTextView_1;
    public TextView clk_mTextView_2;
    public TextView clk_mTextView_3;
    public TextView clk_mTextView_4;
    public TextView clk_mTextView_5;
    public TextView clk_mTextView_6;
    public TextView mTextView_lg;
    public ModelGRXYRZXX mModelGRXYRZXX;
    public BeanSQ mBeanSQ = new BeanSQ();
    public BeanSFSM mBeanSFSM = new BeanSFSM();
    public ModelSF mModelSF;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_renzhengxinxi);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 0:
                loaddata();
                break;
            case 1:
//                mBeanSFSM.frontPic = obj.toString();
                com.ntdlg.bc.F.getUrl(mModelSF.idcard_back_photo, "FrgRenzhengxinxi", 2);
                break;
            case 2:
//                mBeanSFSM.backPic = obj.toString();
                mBeanSFSM.sign = readClassAttr(mBeanSFSM);
                loadJsonUrl(scanIdentity, new Gson().toJson(mBeanSFSM));
                break;
            case 120:
                mModelSF = (ModelSF) obj;
                mBeanSFSM.idcard_back_photo = mModelSF.idcard_back_photo;
                mBeanSFSM.idcard_front_photo = mModelSF.idcard_front_photo;
                mBeanSFSM.sessid = mModelSF.package_session_id;
                mBeanSFSM.id_name = mModelSF.id_name;
                mBeanSFSM.id_number = mModelSF.id_number;
                mBeanSFSM.address = mModelSF.address;
                mBeanSFSM.issuing_authority = mModelSF.issuing_authority;
                mBeanSFSM.gender = mModelSF.gender;
                mBeanSFSM.validity_period = mModelSF.validity_period;
                mBeanSFSM.sign = readClassAttr(mBeanSFSM);
                loadJsonUrl(scanIdentity, new Gson().toJson(mBeanSFSM));
//                com.ntdlg.qd.F.getUrl(mModelSF.idcard_front_photo, "FrgRenzhengxinxi", 1);
                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        clk_mTextView_1 = (TextView) findViewById(R.id.clk_mTextView_1);
        clk_mTextView_2 = (TextView) findViewById(R.id.clk_mTextView_2);
        clk_mTextView_3 = (TextView) findViewById(R.id.clk_mTextView_3);
        clk_mTextView_4 = (TextView) findViewById(R.id.clk_mTextView_4);
        clk_mTextView_5 = (TextView) findViewById(R.id.clk_mTextView_5);
        clk_mTextView_6 = (TextView) findViewById(R.id.clk_mTextView_6);
        mTextView_lg = (TextView) findViewById(R.id.mTextView_lg);

        clk_mTextView_1.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mTextView_2.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mTextView_3.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mTextView_4.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mTextView_5.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mTextView_6.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        mTextView_lg.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));

    }

    public void loaddata() {
        Frame.HANDLES.sentAll("FrgRzh", 0, null);
        BeanBase mBeanBase = new BeanBase();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(getPlatform, new Gson().toJson(mBeanBase));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(getPlatform)) {
            mModelGRXYRZXX = (ModelGRXYRZXX) json2Model(content, ModelGRXYRZXX.class);
            if (mModelGRXYRZXX.isBasicAuth.equals("1")) {
                clk_mTextView_1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.grhover, 0, 0);
                clk_mTextView_1.setBackgroundResource(R.drawable.bg1_hover);
            } else {
                clk_mTextView_1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.gr, 0, 0);
                clk_mTextView_1.setBackgroundResource(R.drawable.bg1);
            }
            if (mModelGRXYRZXX.isProfessionAuth.equals("1")) {
                clk_mTextView_2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.gzhover, 0, 0);
                clk_mTextView_2.setBackgroundResource(R.drawable.bg1_hover);
            } else {
                clk_mTextView_2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.gz, 0, 0);
                clk_mTextView_2.setBackgroundResource(R.drawable.bg1);
            }
            if (mModelGRXYRZXX.isLinkMainAuth.equals("1")) {
                clk_mTextView_3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.lxrhover, 0, 0);
                clk_mTextView_3.setBackgroundResource(R.drawable.bg1_hover);
            } else {
                clk_mTextView_3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.lxr2, 0, 0);
                clk_mTextView_3.setBackgroundResource(R.drawable.bg1);
            }
            if (mModelGRXYRZXX.isOperatorAuth.equals("1")) {
                clk_mTextView_4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.phonerzhover, 0, 0);
                clk_mTextView_4.setBackgroundResource(R.drawable.bg1_hover);
            } else {
                clk_mTextView_4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.phonerz, 0, 0);
                clk_mTextView_4.setBackgroundResource(R.drawable.bg1);
            }
            if (mModelGRXYRZXX.isNameAuth.equals("1")) {
                clk_mTextView_5.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sfzhover, 0, 0);
                clk_mTextView_5.setBackgroundResource(R.drawable.bg1_hover);
            } else {
                clk_mTextView_5.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sfz, 0, 0);
                clk_mTextView_5.setBackgroundResource(R.drawable.bg1);
            }
            if (mModelGRXYRZXX.isTaobaoAuth.equals("1") || mModelGRXYRZXX.isJingdongAuth.equals("1")) {
                clk_mTextView_6.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dshhover, 0, 0);
                clk_mTextView_6.setBackgroundResource(R.drawable.bg1_hover);
            } else {
                clk_mTextView_6.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dsh, 0, 0);
                clk_mTextView_6.setBackgroundResource(R.drawable.bg1);
            }
        } else if (methodName.equals(yunyingshangAuth) || methodName.equals(scanIdentity)) {
            loaddata();
        } else if (methodName.equals(submitCkeck)) {
            Helper.toast("提交成功", getContext());
            Frame.HANDLES.sentAll("FrgWode", 0, null);
            Frame.HANDLES.sentAll("FrgShouye", 0, null);
            this.finish();

        }
    }

    @Override
    public void onClick(android.view.View v) {
        if (R.id.clk_mTextView_1 == v.getId()) {
            Helper.startActivity(getContext(), FrgJbxx.class, TitleAct.class);
        } else if (R.id.clk_mTextView_2 == v.getId()) {
            Helper.startActivity(getContext(), FrgGzxx.class, TitleAct.class);
        } else if (R.id.clk_mTextView_3 == v.getId()) {
            Helper.startActivity(getContext(), FrgLxr.class, TitleAct.class);
        } else if (R.id.clk_mTextView_4 == v.getId()) {//手机
            if (mModelGRXYRZXX.isOperatorAuth.equals("1")) {
                Helper.toast("您已认证", getContext());
                return;
            }
            rZhengWb(getActivity(), MxParam.PARAM_FUNCTION_CARRIER);//手机认证
        } else if (R.id.clk_mTextView_5 == v.getId()) {//身份
            if (mModelGRXYRZXX.isNameAuth.equals("1")) {
                Helper.startActivity(getContext(), FrgShenfenDetail.class, TitleAct.class);
            } else {
                mTAocrVerify(getActivity(), "FrgRenzhengxinxi");
            }
        } else if (R.id.clk_mTextView_6 == v.getId()) {
            Helper.startActivity(getContext(), FrgDshrzh.class, TitleAct.class, "mModelGRXYRZXX", mModelGRXYRZXX);
        } else if (R.id.mTextView_lg == v.getId()) {
            if (!mModelGRXYRZXX.isBasicAuth.equals("1")) {
                Helper.toast("基本信息尚未认证", getContext());
                return;
            }
            if (!mModelGRXYRZXX.isProfessionAuth.equals("1")) {
                Helper.toast("工作信息尚未认证", getContext());
                return;
            }
            if (!mModelGRXYRZXX.isLinkMainAuth.equals("1")) {
                Helper.toast("联系人信息尚未认证", getContext());
                return;
            }
            if (!mModelGRXYRZXX.isOperatorAuth.equals("1")) {
                Helper.toast("运营商尚未认证", getContext());
                return;
            }
            if (!mModelGRXYRZXX.isNameAuth.equals("1")) {
                Helper.toast("身份证信息尚未认证", getContext());
                return;
            }
            if (!mModelGRXYRZXX.isTaobaoAuth.equals("1") && !mModelGRXYRZXX.isJingdongAuth.equals("1")) {
                Helper.toast("电商未认证", getContext());
                return;
            }
            BeanSubXSSH mBeanKSJK = new BeanSubXSSH();
            mBeanKSJK.sign = readClassAttr(mBeanKSJK);
            loadJsonUrl(submitCkeck, new Gson().toJson(mBeanKSJK));

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                Bundle b = data.getExtras();              //data为B中回传的Intent
                String result = b.getString("result");    //result即为回传的值(JSON格式)
                /**
                 *  result的格式如下：
                 *  1.1.没有进行账单导入(后台没有通知)
                 *      {"code" : -1, "function" : "mail", "searchId" : "", "taskId" : "", "message" : "", "account" : ""}
                 *  1.2.平台方服务问题(后台没有通知)
                 *      {"code" : -2, "function" : "mail", "searchId" : "", "taskId" : "", "message" : "", "account" : "xxx"}
                 *  1.3.魔蝎数据服务异常(后台没有通知)
                 *      {"code" : -3, "function" : "mail", "searchId" : "", "taskId" : "", "message" : "", "account" : "xxx"}
                 *  1.4.用户输入出错（密码、验证码等输错且未继续输入）
                 *      {"code" : -4, "function" : "mail", "searchId" : "", "taskId" : "", "message" : "密码错误", "account" : "xxx"}
                 *  2.账单导入失败(后台有通知)
                 *      {"code" : 0, "function" : "mail", "searchId" : "3550622685459407187", "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx"}
                 *  3.账单导入成功(后台有通知)
                 *      {"code" : 1, "function" : "mail", "searchId" : "3550622685459407187", "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx"}
                 *  4.账单导入中(后台有通知)
                 *      {"code" : 2, "function" : "mail", "searchId" : "3550622685459407187", "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx"}
                 */
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
                                mBeanSQ.taskId = jsonObject.getString("taskId");
                                mBeanSQ.sign = readClassAttr(mBeanSQ);
                                loadJsonUrl(yunyingshangAuth, new Gson().toJson(mBeanSQ));
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
        mHeadlayout.setTitle("认证信息");
    }
}