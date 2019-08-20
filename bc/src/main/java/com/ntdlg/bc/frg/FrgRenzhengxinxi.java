//
//  FrgRenzhengxinxi
//
//  Created by DELL on 2017-06-23 09:39:47
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.moxie.client.model.MxParam;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanSFSM;
import com.ntdlg.bc.bean.BeanSubXSSH;
import com.ntdlg.bc.bean.BeansavePhone;
import com.ntdlg.bc.model.ModelGRXYRZXX;
import com.ntdlg.bc.model.ModelSF;
import com.ntdlg.bc.view.SortModel;

import java.util.ArrayList;
import java.util.List;

import static com.ntdlg.bc.F.elemeAuth;
import static com.ntdlg.bc.F.getPlatform;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.mTAocrVerify;
import static com.ntdlg.bc.F.meituanAuth;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.savePhone;
import static com.ntdlg.bc.F.scanIdentity;
import static com.ntdlg.bc.F.submitCkeck;
import static com.ntdlg.bc.F.yunyingshangAuth;
import static com.umeng.socialize.utils.DeviceConfig.context;


public class FrgRenzhengxinxi extends BaseFrg {

    public TextView clk_mTextView_1;
    public TextView clk_mTextView_2;
    public TextView clk_mTextView_3;
    public TextView clk_mTextView_4;
    public TextView clk_mTextView_5;
    public TextView clk_mTextView_6;
    public TextView mTextView_lg;
    public ModelGRXYRZXX mModelGRXYRZXX;
    public BeanSFSM mBeanSFSM = new BeanSFSM();
    public ModelSF mModelSF;
    public TextView clk_mTextView_7;
    public TextView clk_mTextView_8;
    public RelativeLayout mRelativeLayout_title;
    public int type;
    public List<SortModel> mAllContactsList = new ArrayList<SortModel>();

    @Override
    protected void create(Bundle savedInstanceState) {
        type = getActivity().getIntent().getIntExtra("type", 0);
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
                com.ntdlg.bc.F.getUrl(mModelSF.idcard_back_photo, "FrgRenzhengxinxi", 2);
                break;
            case 2:
                mBeanSFSM.sign = readClassAttr(mBeanSFSM);
                loadJsonUrl(scanIdentity, new Gson().toJson(mBeanSFSM));
                break;
            case 3:
                mAllContactsList = (ArrayList) obj;
                if (mTextView_lg.getVisibility() == View.VISIBLE) {
                    if (mAllContactsList.size() > 0) {
                        BeansavePhone mBeansavePhone = new BeansavePhone();
                        for (SortModel mSortModel : mAllContactsList) {
                            mBeansavePhone.linkMan.add(new BeansavePhone.linkManBean(mSortModel.name, mSortModel.number));
                        }
                        mBeansavePhone.sign = readClassAttr(mBeansavePhone);
                        loadJsonUrlNoshow(savePhone, new Gson().toJson(mBeansavePhone));

                    } else {
                        Toast.makeText(getContext(), "未获得读取联系人权限 或 未联系人数据不存在", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case 120:
                mModelSF = (ModelSF) obj;
                mBeanSFSM = new BeanSFSM();
                mBeanSFSM.idcard_back_photo = mModelSF.idcard_back_photo;
                mBeanSFSM.idcard_front_photo = mModelSF.idcard_front_photo;
                mBeanSFSM.pic_photo = mModelSF.pic_photo;
                mBeanSFSM.sign = readClassAttr(mBeanSFSM);
                loadJsonUrl(scanIdentity, new Gson().toJson(mBeanSFSM));
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
        clk_mTextView_7 = (TextView) findViewById(R.id.clk_mTextView_7);
        clk_mTextView_8 = (TextView) findViewById(R.id.clk_mTextView_8);
        mRelativeLayout_title = (RelativeLayout) findViewById(R.id.mRelativeLayout_title);

        clk_mTextView_1.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mTextView_2.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mTextView_3.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mTextView_4.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mTextView_5.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mTextView_6.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mTextView_7.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        clk_mTextView_8.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        mTextView_lg.setOnClickListener(com.mdx.framework.utility.Helper.delayClickLitener(this));
        if (type == 1) {
            mRelativeLayout_title.setVisibility(View.GONE);
        } else {
            mTextView_lg.setVisibility(View.GONE);
        }
    }

    public void loaddata() {
//        Frame.HANDLES.sentAll("FrgRzh", 0, null);
        if (!TextUtils.isEmpty(F.UserId)) {
            BeanBase mBeanBase = new BeanBase();
            mBeanBase.sign = readClassAttr(mBeanBase);
            loadJsonUrl(getPlatform, new Gson().toJson(mBeanBase));
        }

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
            if (mModelGRXYRZXX.isTaobaoAuth.equals("1") || mModelGRXYRZXX.isZhifubaoAuth.equals("1") || mModelGRXYRZXX.isJingdongAuth.equals("1")) {
                clk_mTextView_6.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dshhover, 0, 0);
                clk_mTextView_6.setBackgroundResource(R.drawable.bg1_hover);
            } else {
                clk_mTextView_6.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dsh, 0, 0);
                clk_mTextView_6.setBackgroundResource(R.drawable.bg1);
            }
            if (mModelGRXYRZXX.isGongjijinAuth.equals("1") || mModelGRXYRZXX.isShebaoAuth.equals("1")) {
                clk_mTextView_7.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dshhover, 0, 0);
                clk_mTextView_7.setBackgroundResource(R.drawable.bg1_hover);
            } else {
                clk_mTextView_7.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dsh, 0, 0);
                clk_mTextView_7.setBackgroundResource(R.drawable.bg1);
            }
            if (mModelGRXYRZXX.isMeituanAuth.equals("1") || mModelGRXYRZXX.isElemeAuth.equals("1")) {
                clk_mTextView_8.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dshhover, 0, 0);
                clk_mTextView_8.setBackgroundResource(R.drawable.bg1_hover);
            } else {
                clk_mTextView_8.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dsh, 0, 0);
                clk_mTextView_8.setBackgroundResource(R.drawable.bg1);
            }
        } else if (methodName.equals(yunyingshangAuth) || methodName.equals(scanIdentity) || methodName.equals(elemeAuth) || methodName.equals(meituanAuth)) {
            loaddata();
        } else if (methodName.equals(submitCkeck)) {
            Helper.toast("提交成功", getContext());
            Frame.HANDLES.sentAll("FrgWode", 0, null);
            Frame.HANDLES.sentAll("FrgShouye", 0, null);
            this.finish();

        } else if (methodName.equals(savePhone)) {
            com.framewidget.F.yShoure(getContext(), "", "请仔细核对所填信息，确保真实有效完整，一经提交将无法修改", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    BeanSubXSSH mBeanKSJK = new BeanSubXSSH();
                    mBeanKSJK.location = F.address;
                    mBeanKSJK.sign = readClassAttr(mBeanKSJK);
                    loadJsonUrl(submitCkeck, new Gson().toJson(mBeanKSJK));
                }
            });
        }
    }

    @Override
    public void onClick(android.view.View v) {
        if (TextUtils.isEmpty(com.ntdlg.bc.F.UserId)) {
            Helper.toast("请先登录", getContext());
            Helper.startActivity(getContext(), FrgLogin.class, TitleAct.class);
            return;
        }
        if (R.id.clk_mTextView_1 == v.getId()) {
//            if (mModelGRXYRZXX.isBasicAuth.equals("1")) {
//                Helper.toast("您已认证", getContext());
//                return;
//            }
            Helper.startActivity(getContext(), FrgJbxx.class, TitleAct.class);
        } else if (R.id.clk_mTextView_2 == v.getId()) {
//            if (mModelGRXYRZXX.isProfessionAuth.equals("1")) {
//                Helper.toast("您已认证", getContext());
//                return;
//            }
            Helper.startActivity(getContext(), FrgGzxx.class, TitleAct.class);
        } else if (R.id.clk_mTextView_3 == v.getId()) {
//            if (mModelGRXYRZXX.isLinkMainAuth.equals("1")) {
//                Helper.toast("您已认证", getContext());
//                return;
//            }
            Helper.startActivity(getContext(), FrgLxr.class, TitleAct.class);
        } else if (R.id.clk_mTextView_4 == v.getId()) {//手机
            if (mModelGRXYRZXX.isOperatorAuth.equals("1")) {
                Helper.toast("您已认证", getContext());
                return;
            }
            F.rZhengWb(getActivity(), MxParam.PARAM_TASK_CARRIER, FrgRenzhengxinxi.this);//手机认证
        } else if (R.id.clk_mTextView_5 == v.getId()) {//身份
            if (mModelGRXYRZXX.isNameAuth.equals("1")) {
                Helper.startActivity(getContext(), FrgShenfenDetail.class, TitleAct.class);
            } else {
                mTAocrVerify(getActivity(), "FrgRenzhengxinxi");
            }
        } else if (R.id.clk_mTextView_6 == v.getId()) {
            Helper.startActivity(getContext(), FrgDshrzh.class, TitleAct.class, "mModelGRXYRZXX", mModelGRXYRZXX);
        } else if (R.id.clk_mTextView_7 == v.getId()) {
            Helper.startActivity(getContext(), FrgGt.class, TitleAct.class);
        } else if (R.id.clk_mTextView_8 == v.getId()) {
            Helper.startActivity(getContext(), FrgMe.class, TitleAct.class);
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
            if (!mModelGRXYRZXX.isZhifubaoAuth.equals("1")) {
                Helper.toast("支付宝未认证", getContext());
                return;
            }
            if (!mModelGRXYRZXX.isGongjijinAuth.equals("1") && !mModelGRXYRZXX.isShebaoAuth.equals("1")) {
                Helper.toast("公积金/社保未认证", getContext());
                return;
            }
            if (!mModelGRXYRZXX.isMeituanAuth.equals("1") && !mModelGRXYRZXX.isElemeAuth.equals("1")) {
                Helper.toast("美团/饿了么未认证", getContext());
                return;
            }
            F.loadContacts(getContext(), "FrgRenzhengxinxi");

        }
    }


    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("认证信息");
    }
}