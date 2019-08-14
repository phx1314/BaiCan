//
//  AdaDialogBottom
//
//  Created by DELL on 2019-08-14 11:55:54
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.ada;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.framework.adapter.MAdapter;
import com.ntdlg.bc.item.DialogBottom;

import java.util.List;

public class AdaDialogBottom extends MAdapter<String>{

   public AdaDialogBottom(Context context, List<String> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        String item = get(position);
        if (convertView == null) {
            convertView = DialogBottom.getView(getContext(), parent);;
        }
        DialogBottom mDialogBottom=(DialogBottom) convertView.getTag();
//        mDialogBottom.set(item);
        return convertView;
    }
}
