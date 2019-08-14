//
//  DialogBottom
//
//  Created by DELL on 2019-08-14 11:55:54
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.item;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.ntdlg.bc.F;
import com.ntdlg.bc.R;

import java.io.File;


public class DialogBottom extends BaseItem {
    public TextView mTextView_ht;
    public TextView mTextView_xy;
    public TextView mTextView_cancel;
    public String ht_url;
    public String xy_url;
    public Dialog mDialog;

    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_dialog_bottom, null);
        convertView.setTag(new DialogBottom(convertView));
        return convertView;
    }

    public DialogBottom(View view) {
        this.contentview = view;
        this.context = contentview.getContext();
        initView();
    }

    private void initView() {
        this.contentview.setTag(this);
        findVMethod();
    }

    private void findVMethod() {
        mTextView_ht = (TextView) contentview.findViewById(R.id.mTextView_ht);
        mTextView_xy = (TextView) contentview.findViewById(R.id.mTextView_xy);
        mTextView_cancel = (TextView) contentview.findViewById(R.id.mTextView_cancel);
        mTextView_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mTextView_ht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                openFF(ht_url);
            }
        });
        mTextView_xy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                openFF(xy_url);
            }
        });

    }

    public void openFF(String url) {
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + System.currentTimeMillis() + ".pdf";
        FileDownloader.getImpl().create(url)
                .setPath(path)
                .setForceReDownload(true)
                .setListener(new FileDownloadListener() {
                    //等待
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    //下载进度回调
                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    //完成下载
                    @Override
                    protected void completed(BaseDownloadTask task) {
                        try {
                            F.openFile(new File(path), context);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    //暂停
                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    //下载出错
                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                    }

                    //已存在相同下载
                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }

    public void set(String ht_url, String xy_url, Dialog mDialog) {
        this.ht_url = ht_url;
        this.xy_url = xy_url;
        this.mDialog = mDialog;
    }


}