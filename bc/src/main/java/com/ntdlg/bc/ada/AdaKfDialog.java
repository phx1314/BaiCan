//
//  AdaKfDialog
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

import com.ntdlg.bc.item.KfDialog;

public class AdaKfDialog extends MAdapter<String>{

   public AdaKfDialog(Context context, List<String> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        String item = get(position);
        if (convertView == null) {
            convertView = KfDialog.getView(getContext(), parent);;
        }
        KfDialog mKfDialog=(KfDialog) convertView.getTag();
        mKfDialog.set(item);
        return convertView;
    }
}
