//
//  FrgRzh
//
//  Created by DELL on 2017-06-05 13:15:51
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.moxie.client.model.MxParam;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanSFSM;
import com.ntdlg.bc.bean.BeanSQ;
import com.ntdlg.bc.model.ModelGRXYRZXX;
import com.ntdlg.bc.model.ModelSF;

import org.json.JSONObject;

import static com.ntdlg.bc.F.getPlatform;
import static com.ntdlg.bc.F.gongjijinAuth;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.mTAocrVerify;
import static com.ntdlg.bc.F.rZhengWb;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.scanIdentity;
import static com.ntdlg.bc.F.taobaoAuth;
import static com.ntdlg.bc.F.xuexinwangAuth;
import static com.ntdlg.bc.F.yunyingshangAuth;


public class FrgRzh extends BaseFrg {

    public TextView mImageView_1;
    public TextView mImageView_2;
    public TextView mImageView_3;
    public TextView mImageView_4;
    public TextView mImageView_te_1;
    public TextView mImageView_te_2;
    public TextView mImageView_te_3;
    public TextView mImageView_te_4;
    public TextView mTextView_shenqing;
    public ModelGRXYRZXX mModelGRXYRZXX;
    public String type = "1";

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_rzh);
        setWindowStatusBarColor(getActivity(), R.color.A);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 0:
                loaddata();
                break;
            case 120:
                ModelSF mModelSF = (ModelSF) obj;
                BeanSFSM mBeanSFSM = new BeanSFSM();
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
                break;
            case 110:
                try {
                    JSONObject jsonObject = (JSONObject) obj;
                    BeanSQ mBeanSQ = new BeanSQ();
                    mBeanSQ.taskId = jsonObject.getString("taskId");
                    mBeanSQ.sign = readClassAttr(mBeanSQ);
                    if (FrgRzh.this.type.equals("1")) {
                        loadJsonUrl(taobaoAuth, new Gson().toJson(mBeanSQ));
                    } else if (FrgRzh.this.type.equals("2")) {
                        loadJsonUrl(gongjijinAuth, new Gson().toJson(mBeanSQ));
                    } else if (FrgRzh.this.type.equals("3")) {
                        loadJsonUrl(xuexinwangAuth, new Gson().toJson(mBeanSQ));
                    } else if (FrgRzh.this.type.equals("4")) {
                        loadJsonUrl(yunyingshangAuth, new Gson().toJson(mBeanSQ));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mImageView_1 = (TextView) findViewById(R.id.mImageView_1);
        mImageView_2 = (TextView) findViewById(R.id.mImageView_2);
        mImageView_3 = (TextView) findViewById(R.id.mImageView_3);
        mImageView_4 = (TextView) findViewById(R.id.mImageView_4);
        mImageView_te_1 = (TextView) findViewById(R.id.mImageView_te_1);
        mImageView_te_2 = (TextView) findViewById(R.id.mImageView_te_2);
        mImageView_te_3 = (TextView) findViewById(R.id.mImageView_te_3);
        mImageView_te_4 = (TextView) findViewById(R.id.mImageView_te_4);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);

        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(com.ntdlg.bc.F.UserId)) {
                    Helper.toast("请先登录", getContext());
                    Helper.startActivity(getContext(), FrgLogin.class, TitleAct.class);
                    return;
                }
                Helper.startActivity(getContext(), FrgGerenziliao.class, TitleAct.class);
            }
        });
        mImageView_te_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(com.ntdlg.bc.F.UserId)) {
                    Helper.toast("请先登录", getContext());
                    Helper.startActivity(getContext(), FrgLogin.class, TitleAct.class);
                    return;
                }
                if (mModelGRXYRZXX.isBillAuth.equals("1")) {
                    Helper.toast("您已认证", getContext());
                    return;
                }
                Helper.startActivity(getContext(), FrgZhangdanRzh.class, TitleAct.class);
            }
        });
        mImageView_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(com.ntdlg.bc.F.UserId)) {
                    Helper.toast("请先登录", getContext());
                    Helper.startActivity(getContext(), FrgLogin.class, TitleAct.class);
                    return;
                }
                if (mModelGRXYRZXX.isNameAuth.equals("1")) {
                    Helper.startActivity(getContext(), FrgShenfenDetail.class, TitleAct.class);
                } else {
                    mTAocrVerify(getActivity(), "FrgRzh");
                }
            }
        });
        mImageView_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(com.ntdlg.bc.F.UserId)) {
                    Helper.toast("请先登录", getContext());
                    Helper.startActivity(getContext(), FrgLogin.class, TitleAct.class);
                    return;
                }
                Helper.startActivity(getContext(), FrgDshrzh.class, TitleAct.class, "mModelGRXYRZXX", mModelGRXYRZXX);
            }
        });

        mImageView_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(com.ntdlg.bc.F.UserId)) {
                    Helper.toast("请先登录", getContext());
                    Helper.startActivity(getContext(), FrgLogin.class, TitleAct.class);
                    return;
                }
                Helper.startActivity(getContext(), FrgGerenziliao.class, TitleAct.class);
            }
        });
        mImageView_te_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(com.ntdlg.bc.F.UserId)) {
                    Helper.toast("请先登录", getContext());
                    Helper.startActivity(getContext(), FrgLogin.class, TitleAct.class);
                    return;
                }
                Helper.startActivity(getContext(), FrgDshrzh.class, TitleAct.class, "mModelGRXYRZXX", mModelGRXYRZXX, "remark", "二者皆认证方可提高额度");
            }
        });
        mImageView_te_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(com.ntdlg.bc.F.UserId)) {
                    Helper.toast("请先登录", getContext());
                    Helper.startActivity(getContext(), FrgLogin.class, TitleAct.class);
                    return;
                }
                if (mModelGRXYRZXX.isGongjijinAuth.equals("1")) {
                    Helper.toast("您已认证", getContext());
                    return;
                }
                type = "2";
                rZhengWb(getActivity(), MxParam.PARAM_FUNCTION_FUND);//公积金
            }
        });
        mImageView_te_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(com.ntdlg.bc.F.UserId)) {
                    Helper.toast("请先登录", getContext());
                    Helper.startActivity(getContext(), FrgLogin.class, TitleAct.class);
                    return;
                }
                if (mModelGRXYRZXX.isFundAuth.equals("1")) {
                    Helper.toast("您已认证", getContext());
                    return;
                }
//                type = "3";
//                rZhengWb(getActivity(), MxParam.PARAM_FUNCTION_CHSI);//学历
                Helper.startActivity(getContext(),FrgChzhRzh.class,TitleAct.class);
            }
        });
        mImageView_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(com.ntdlg.bc.F.UserId)) {
                    Helper.toast("请先登录", getContext());
                    Helper.startActivity(getContext(), FrgLogin.class, TitleAct.class);
                    return;
                }
                if (mModelGRXYRZXX.isOperatorAuth.equals("1")) {
                    Helper.toast("您已认证", getContext());
                    return;
                }
                type = "4";
                rZhengWb(getActivity(), MxParam.PARAM_FUNCTION_CARRIER);//手机认证
            }
        });
    }


    public void loaddata() {
        if (!TextUtils.isEmpty(F.UserId)) {
            BeanBase mBeanBase = new BeanBase();
            mBeanBase.sign = readClassAttr(mBeanBase);
            loadJsonUrl(getPlatform, new Gson().toJson(mBeanBase));
        } else {
            mImageView_1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.personal1, 0, 0);
            mImageView_2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.phone2, 0, 0);
            mImageView_3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.identity3, 0, 0);
            mImageView_4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dianshang, 0, 0);
            mImageView_te_1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dianshang, 0, 0);
            mImageView_te_2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.authentication2, 0, 0);
            mImageView_te_3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.authentication3, 0, 0);
            mImageView_te_4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.authentication4, 0, 0);
        }
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(getPlatform)) {
            mModelGRXYRZXX = (ModelGRXYRZXX) json2Model(content, ModelGRXYRZXX.class);
            if (mModelGRXYRZXX.isBasicAuth.equals("1") && mModelGRXYRZXX.isProfessionAuth.equals("1") && mModelGRXYRZXX.isLinkMainAuth.equals("1")) {
                mImageView_1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.personal1hover, 0, 0);
            } else {
                mImageView_1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.personal1, 0, 0);
            }
            if (mModelGRXYRZXX.isOperatorAuth.equals("1")) {
                mImageView_2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.phone2hover, 0, 0);
            } else {
                mImageView_2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.phone2, 0, 0);
            }
            if (mModelGRXYRZXX.isNameAuth.equals("1")) {
                mImageView_3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.identity3hover, 0, 0);
            } else {
                mImageView_3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.identity3, 0, 0);
            }
            if (mModelGRXYRZXX.isTaobaoAuth.equals("1") || mModelGRXYRZXX.isJingdongAuth.equals("1")) {
                mImageView_4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dianshang1, 0, 0);
                mImageView_te_1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dianshang1, 0, 0);
            } else {
                mImageView_4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dianshang, 0, 0);
                mImageView_te_1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dianshang, 0, 0);
            }
//            if (mModelGRXYRZXX.isTaobaoAuth.equals("1")) {
//                mImageView_te_1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tao, 0, 0);
//            } else {
//                mImageView_te_1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.authentication1, 0, 0);
//            }
            if (mModelGRXYRZXX.isGongjijinAuth.equals("1")) {
                mImageView_te_2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.gong, 0, 0);
            } else {
                mImageView_te_2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.authentication2, 0, 0);
            }
            if (mModelGRXYRZXX.isFundAuth.equals("1")) {
                mImageView_te_3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.xue, 0, 0);
            } else {
                mImageView_te_3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.authentication3, 0, 0);
            }
            if (mModelGRXYRZXX.isBillAuth.equals("1")) {
                mImageView_te_4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.zhang, 0, 0);
            } else {
                mImageView_te_4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.authentication4, 0, 0);
            }
        } else if (methodName.equals(yunyingshangAuth) || methodName.equals(taobaoAuth) || methodName.equals(xuexinwangAuth) || methodName.equals(gongjijinAuth) || methodName.equals(scanIdentity)) {
            loaddata();
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
//            case RESULT_OK:
//                Bundle b = data.getExtras();              //data为B中回传的Intent
//                String result = b.getString("result");    //result即为回传的值(JSON格式)
//                if (TextUtils.isEmpty(result)) {
//                    Toast.makeText(getActivity(), "用户没有进行导入操作!", Toast.LENGTH_SHORT).show();
//                } else {
//                    try {
//                        int code = 0;
//                        JSONObject jsonObject = new JSONObject(result);
//
//                        code = jsonObject.getInt("code");
//                        switch (code) {
//                            case -1:
//                                Toast.makeText(getActivity(), "用户没有进行导入操作", Toast.LENGTH_SHORT).show();
//                                break;
//                            case -2:
//                                Toast.makeText(getActivity(), "导入失败(平台方服务问题)", Toast.LENGTH_SHORT).show();
//                                break;
//                            case -3:
//                                Toast.makeText(getActivity(), "导入失败(魔蝎数据服务异常)", Toast.LENGTH_SHORT).show();
//                                break;
//                            case -4:
//                                Toast.makeText(getActivity(), "导入失败(" + jsonObject.getString("message") + ")", Toast.LENGTH_SHORT).show();
//                                break;
//                            case 0:
//                                Toast.makeText(getActivity(), "导入失败", Toast.LENGTH_SHORT).show();
//                                break;
//                            case 1:
//                                Toast.makeText(getActivity(), "导入成功", Toast.LENGTH_SHORT).show();
//                                break;
//                            case 2:
//                                /**
//                                 * 如果用户中途导入魔蝎SDK会出现这个情况，如需获取最终状态请轮询贵方后台接口
//                                 * 魔蝎后台会向贵方后台推送Task通知和Bill通知
//                                 * Task通知：登录成功/登录失败
//                                 * Bill通知：账单通知
//                                 */
//                                Toast.makeText(getActivity(), "导入中", Toast.LENGTH_SHORT).show();
//                                break;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                break;
//            default:
//                break;
//        }
//    }
}