//
//  AdaZfDialog
//
//  Created by DELL on 2019-09-06 08:39:50
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.ada;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.framework.adapter.MAdapter;
import com.ntdlg.bc.item.ZfDialog;

import java.util.List;

public class AdaZfDialog extends MAdapter<String>{

   public AdaZfDialog(Context context, List<String> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        String item = get(position);
        if (convertView == null) {
            convertView = ZfDialog.getView(getContext(), parent);;
        }
        ZfDialog mZfDialog=(ZfDialog) convertView.getTag();
//        mZfDialog.set(item);
        return convertView;
    }
}
