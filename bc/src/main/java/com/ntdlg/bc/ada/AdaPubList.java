//
//  AdaPubList
//
//  Created by DELL on 2017-06-23 13:38:08
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.ada;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.framework.Frame;
import com.mdx.framework.adapter.MAdapter;
import com.ntdlg.bc.item.PubList;
import com.ntdlg.bc.model.BaseData;

import java.util.List;

public class AdaPubList extends MAdapter<BaseData> {

    public AdaPubList(Context context, List<BaseData> list) {
        super(context, list);
    }


    @Override
    public View getview(int position, View convertView, ViewGroup parent) {
        final BaseData item = get(position);
        if (convertView == null) {
            convertView = PubList.getView(getContext(), parent);
        }
        PubList mPubList = (PubList) convertView.getTag();
        mPubList.set(item);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Frame.HANDLES.sentAll("FrgPubList", 0, item);
            }
        });
        return convertView;
    }
}
