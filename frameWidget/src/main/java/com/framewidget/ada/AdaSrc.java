//
//  AdaSrc
//
//  Created by df on 2016-10-10 16:01:26
//  Copyright (c) df All rights reserved.


/**

 */

package com.framewidget.ada;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.framewidget.item.Src;
import com.framewidget.view.CallBackGridPhoto;
import com.mdx.framework.adapter.MAdapter;

import java.util.List;

public class AdaSrc extends MAdapter<String> {
    private int max;
    private int add_src;
    private CallBackGridPhoto mCallBackGridPhoto;

    public AdaSrc(Context context, List<String> list, int max, CallBackGridPhoto mCallBackGridPhoto) {
        super(context, list);
        this.max = max;
        this.mCallBackGridPhoto = mCallBackGridPhoto;
    }

    public void reFreash(int max) {
        this.max = max;
        this.notifyDataSetChanged();
    }

    public void reFreash_src(int add_src) {
        this.add_src = add_src;
        this.notifyDataSetChanged();
    }

    @Override
    public View getview(int position, View convertView, ViewGroup parent) {
        String item = get(position);
        if (convertView == null) {
            convertView = Src.getView(getContext(), parent);
        }
        Src mSrc = (Src) convertView.getTag();
        mSrc.set(this, item, max,add_src, position, mCallBackGridPhoto);
        return convertView;
    }
}
