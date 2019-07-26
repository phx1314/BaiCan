//
//  AdaWodeJk2
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
import com.ntdlg.bc.item.WodeJk2;
import com.ntdlg.bc.model.ModelWDJK;

import java.util.List;

public class AdaWodeJk2 extends MAdapter<ModelWDJK.BillsBean>{

   public AdaWodeJk2(Context context, List<ModelWDJK.BillsBean> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        ModelWDJK.BillsBean item = get(position);
        if (convertView == null) {
            convertView = WodeJk2.getView(getContext(), parent);;
        }
        WodeJk2 mWodeJk2=(WodeJk2) convertView.getTag();
        mWodeJk2.set(item);
        return convertView;
    }
}
