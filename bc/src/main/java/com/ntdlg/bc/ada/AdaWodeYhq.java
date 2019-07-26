//
//  AdaWodeYhq
//
//  Created by DELL on 2017-06-06 14:25:03
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.ada;

import java.util.List;
import com.mdx.framework.adapter.MAdapter;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View;

import com.ntdlg.bc.item.WodeYhq;

public class AdaWodeYhq extends MAdapter<String>{

   public AdaWodeYhq(Context context, List<String> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        String item = get(position);
        if (convertView == null) {
            convertView = WodeYhq.getView(getContext(), parent);;
        }
        WodeYhq mWodeYhq=(WodeYhq) convertView.getTag();
        mWodeYhq.set(item);
        return convertView;
    }
}
