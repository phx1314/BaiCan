//
//  AdaLishiZhangdanList
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.ada;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.framework.adapter.MAdapter;
import com.ntdlg.bc.item.LishiZhangdanList;
import com.ntdlg.bc.model.ModelLSZD;

import java.util.List;

public class AdaLishiZhangdanList extends MAdapter<ModelLSZD.RecordsBean>{

   public AdaLishiZhangdanList(Context context, List<ModelLSZD.RecordsBean> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        ModelLSZD.RecordsBean item = get(position);
        if (convertView == null) {
            convertView = LishiZhangdanList.getView(getContext(), parent);;
        }
        LishiZhangdanList mLishiZhangdanList=(LishiZhangdanList) convertView.getTag();
        mLishiZhangdanList.set(item);
        return convertView;
    }
}
