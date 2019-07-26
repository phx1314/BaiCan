//
//  Src
//
//  Created by df on 2016-10-10 16:01:26
//  Copyright (c) df All rights reserved.


/**

 */

package com.framewidget.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framewidget.F;
import com.framewidget.R;
import com.framewidget.ada.AdaSrc;
import com.framewidget.view.CallBackGridPhoto;
import com.framewidget.view.goReturn;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.MImageView;
import com.mdx.framework.widget.getphoto.PopUpdataPhoto;

import java.util.ArrayList;


public class Src extends BaseItem {
    public MImageView mMImageView;
    public MImageView mMImageView_add;
    public String item;
    public int max;
    public int position;
    private CallBackGridPhoto mCallBackGridPhoto;
    private AdaSrc mAdaSrc;

    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_df_src, null);
        convertView.setTag(new Src(convertView));
        return convertView;
    }

    public Src(View view) {
        this.contentview = view;
        this.context = contentview.getContext();
        initView();
    }

    private void initView() {
        this.contentview.setTag(this);
        findVMethod();
    }

    private void findVMethod() {
        mMImageView = (MImageView) contentview.findViewById(R.id.mMImageView);
        mMImageView_add = (MImageView) contentview.findViewById(R.id.mMImageView_add);
        mMImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View view1 = ImgshowDialog.getView(context, null);
                F.showImgDialog(context, view1, new goReturn() {
                    @Override
                    public void go2Object(Object obj) {
                        ((ImgshowDialog) view1.getTag()).set(obj, mAdaSrc.getList(),
                                position, mCallBackGridPhoto);
                    }
                });
            }
        });
        mMImageView_add.setOnClickListener(Helper.delayClickLitener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.getPhotos(context, new PopUpdataPhoto.OnReceiverPhotos() {
                    @Override
                    public void onReceiverPhoto(ArrayList<String> arrayList) {
                        mCallBackGridPhoto.goReturnDo(arrayList);
                    }
                }, max);
            }
        }));

    }

    public void set(AdaSrc mAdaSrc, String item, int max, int add_src, int position, CallBackGridPhoto mCallBackGridPhoto) {
        this.mAdaSrc = mAdaSrc;
        this.item = item;
        this.max = max;
        this.position = position;
        this.mCallBackGridPhoto = mCallBackGridPhoto;
        if (TextUtils.isEmpty(item)) {
            mMImageView_add.setVisibility(View.VISIBLE);
            mMImageView.setVisibility(View.GONE);
        } else {
            mMImageView_add.setVisibility(View.GONE);
            mMImageView.setVisibility(View.VISIBLE);
            mMImageView.setObj("file:" + item);
        }
        if (add_src != 0) {
            mMImageView_add.setImageResource(add_src);
        }
    }


}