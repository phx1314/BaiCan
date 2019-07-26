//
//  AdaLxr
//
//  Created by DELL on 2017-06-06 14:55:15
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.ada;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.framework.Frame;
import com.mdx.framework.adapter.MAdapter;
import com.ntdlg.bc.item.Lxr;
import com.ntdlg.bc.view.SortModel;

import java.util.List;

public class AdaLxr extends MAdapter<SortModel> {

    public AdaLxr(Context context, List<SortModel> list) {
        super(context, list);
    }


    @Override
    public View getview(int position, View convertView, ViewGroup parent) {
        final SortModel item = get(position);
        if (convertView == null) {
            convertView = Lxr.getView(getContext(), parent);
        }
        Lxr mLxr = (Lxr) convertView.getTag();
        mLxr.set(item, getList(), position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Frame.HANDLES.sentAll("FrgLxrList", 1, item );
            }
        });
        return convertView;
    }
}
