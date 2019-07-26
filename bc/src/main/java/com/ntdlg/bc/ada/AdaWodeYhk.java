//
//  AdaWodeYhk
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
import com.ntdlg.bc.item.WodeYhk;

import java.util.List;

public class AdaWodeYhk extends MAdapter<String>{

   public AdaWodeYhk(Context context, List<String> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        String item = get(position);
        if (convertView == null) {
            convertView = WodeYhk.getView(getContext(), parent);;
        }
        WodeYhk mWodeYhk=(WodeYhk) convertView.getTag();
//        mWodeYhk.set(item);
        return convertView;
    }
}
