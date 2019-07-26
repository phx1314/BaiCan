//
//  AdaHkJihua
//
//  Created by DELL on 2017-06-09 16:11:50
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.ada;

import java.util.List;
import com.mdx.framework.adapter.MAdapter;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View;

import com.ntdlg.bc.item.HkJihua;

public class AdaHkJihua extends MAdapter<String>{

   public AdaHkJihua(Context context, List<String> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        String item = get(position);
        if (convertView == null) {
            convertView = HkJihua.getView(getContext(), parent);;
        }
        HkJihua mHkJihua=(HkJihua) convertView.getTag();
        mHkJihua.set(item);
        return convertView;
    }
}
