//
//  FrgXxhk
//
//  Created by DELL on 2017-06-05 16:11:07
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.ActionBar;
import com.mdx.framework.widget.MImageView;
import com.mdx.framework.widget.getphoto.PopUpdataPhoto;
import com.ntdlg.bc.R;
import com.ntdlg.bc.bean.BeanBase;
import com.ntdlg.bc.bean.BeanSCHKPZ;
import com.ntdlg.bc.model.ModelGetHKJE;

import static com.ntdlg.bc.F.Bitmap2StrByBase64;
import static com.ntdlg.bc.F.changeTime;
import static com.ntdlg.bc.F.getOfflineData;
import static com.ntdlg.bc.F.json2Model;
import static com.ntdlg.bc.F.readClassAttr;
import static com.ntdlg.bc.F.upRepayPic;


public class FrgXxhk extends BaseFrg {

    public LinearLayout mLinearLayout_top;
    public TextView mTextView_price;
    public TextView mTextView_time_last;
    public MImageView mImageView_add;
    public ImageView mImageView_wx;
    public ImageView mImageView_zfb;
    public ImageView mImageView_yl;
    public TextView mTextView_shenqing;
    public TextView mTextView_remark;
    public TextView mTextView_remark1;
    public ModelGetHKJE mModelGetHKJE;
    public String photoPath;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_xxhk);
        initView();
        loaddata();
    }


    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mLinearLayout_top = (LinearLayout) findViewById(R.id.mLinearLayout_top);
        mTextView_price = (TextView) findViewById(R.id.mTextView_price);
        mTextView_time_last = (TextView) findViewById(R.id.mTextView_time_last);
        mImageView_add = (MImageView) findViewById(R.id.mImageView_add);
        mImageView_wx = (ImageView) findViewById(R.id.mImageView_wx);
        mImageView_zfb = (ImageView) findViewById(R.id.mImageView_zfb);
        mImageView_yl = (ImageView) findViewById(R.id.mImageView_yl);
        mTextView_shenqing = (TextView) findViewById(R.id.mTextView_shenqing);
        mTextView_remark = (TextView) findViewById(R.id.mTextView_remark);
        mTextView_remark1 = (TextView) findViewById(R.id.mTextView_remark1);

        mImageView_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.getPhoto(getActivity(), new PopUpdataPhoto.OnReceiverPhoto() {
                    @Override
                    public void onReceiverPhoto(String photoPath, int width,
                                                int height) {
                        if (photoPath != null) {
                            mImageView_add.setObj("file:" + photoPath);
//                            mImageView_add.setCircle(true);
                            FrgXxhk.this.photoPath = photoPath;
                        }
                    }
                }, -1, -1, 640, 640);
            }
        });
        mTextView_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(photoPath)) {
                    Helper.toast("请上传凭证", getContext());
                    return;
                }
                BeanSCHKPZ mBeanSCHKPZ = new BeanSCHKPZ();
                mBeanSCHKPZ.repayPic = Bitmap2StrByBase64(photoPath);
                mBeanSCHKPZ.repayId = mModelGetHKJE.repayId;
                mBeanSCHKPZ.amount = mModelGetHKJE.amount;
                mBeanSCHKPZ.sign = readClassAttr(mBeanSCHKPZ);
                loadJsonUrl(upRepayPic, new Gson().toJson(mBeanSCHKPZ));
            }
        });
    }

    public void loaddata() {
        BeanBase mBeanBase = new BeanBase();
        mBeanBase.sign = readClassAttr(mBeanBase);
        loadJsonUrl(getOfflineData, new Gson().toJson(mBeanBase));
    }

    @Override
    public void onSuccess(String methodName, String content) {
        if (methodName.equals(getOfflineData)) {
            mModelGetHKJE = (ModelGetHKJE) json2Model(content, ModelGetHKJE.class);
            mTextView_price.setText(mModelGetHKJE.amount + "元");
            mTextView_time_last.setText("最后还款日 " + changeTime(mModelGetHKJE.endDate));
        } else if (methodName.equals(upRepayPic)) {
            Helper.toast("提交审核成功", getContext());
            Frame.HANDLES.sentAll("FrgZhangdan", 0, null);
            this.finish();
        }
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("线下还款");
    }
}