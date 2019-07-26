//
//  ImgshowDialog
//
//  Created by Administrator on 2015-10-04 14:37:02
//  Copyright (c) Administrator All rights reserved.

/**

 */

package com.framewidget.item;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framewidget.MyViewPagerAdapter;
import com.framewidget.R;
import com.framewidget.view.CallBackGridPhoto;
import com.mdx.framework.widget.MViewPager;

import java.util.ArrayList;
import java.util.List;

public class ImgshowDialog extends BaseItem implements OnPageChangeListener {
    public MViewPager mViewPager;
    public ImageView mImageView_back;
    public TextView mTextView_num;
    public TextView mTextView_del;
    public TextView mTextView_cancel;
    public Dialog mDialog;
    public List<String> list = new ArrayList<>();
    public int position;
    public int position_father;
    public MyViewPagerAdapter mMyViewPagerAdapter;
    public CallBackGridPhoto mCallBackGridPhoto;

    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_imgshow_dialog, null);
        convertView.setTag(new ImgshowDialog(convertView));
        return convertView;
    }

    public ImgshowDialog(View view) {
        this.contentview = view;
        this.context = contentview.getContext();
        initView();
    }

    private void initView() {
        this.contentview.setTag(this);
        mImageView_back = (ImageView) contentview.findViewById(R.id.mImageView_back);
        mViewPager = (MViewPager) contentview.findViewById(R.id.mViewPager);
        mTextView_num = (TextView) contentview.findViewById(R.id.mTextView_num);
        mTextView_del = (TextView) contentview.findViewById(R.id.mTextView_del);

        mTextView_del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new AlertDialog.Builder(context)
                        .setTitle("确认删除")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mCallBackGridPhoto.goReturnDel(list.get(mViewPager.getCurrentItem()));
                                list.remove(mViewPager.getCurrentItem());
                                int ri = mViewPager.getCurrentItem() - 1;
                                updata();
                                if (ri < 0) {
                                    ri = 0;
                                    mViewPager.setCurrentItem(ri);
                                } else {
                                    mViewPager.setCurrentItem(ri);
                                }
                                mTextView_num.setText((ri + 1) + "/" + list.size());
                                if (list.size() == 0) {
                                    mDialog.dismiss();
                                }
                            }
                        }).setNegativeButton("否", null)
                        .show();
            }
        });
        mImageView_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mDialog.dismiss();
            }
        });
        mViewPager.setOnPageChangeListener(this);
    }

    public void set(Object item, List<String> list, int position, CallBackGridPhoto mCallBackGridPhoto) {
        this.mDialog = (Dialog) item;
        this.mCallBackGridPhoto = mCallBackGridPhoto;
        this.list.clear();
        for (int i = 0; i < list.size(); i++) {
            if (!TextUtils.isEmpty(list.get(i))) {
                this.list.add(list.get(i));
            }
        }
        this.position = position;
        mTextView_num.setText((position + 1) + "/" + list.size());
        updata();
        mViewPager.setCurrentItem(position);
    }

    public void updata() {
        List<View> mViews = new ArrayList<View>();
        for (int i = 0; i < list.size(); i++) {
            View view = ImgShow.getView(context, null);
            mViews.add(view);
            ((ImgShow) view.getTag()).set(list.get(i));
        }
        mMyViewPagerAdapter = new MyViewPagerAdapter(mViews);
        mViewPager.setAdapter(mMyViewPagerAdapter);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        mTextView_num.setText((arg0 + 1) + "/" + list.size());
    }
}