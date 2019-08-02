//
//  AdaDialogCao
//
//  Created by DELL on 2019-08-02 13:19:52
//  Copyright (c) DELL All rights reserved.


/**
   
*/

package com.ntdlg.bc.ada;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.framework.adapter.MAdapter;
import com.ntdlg.bc.item.DialogCao;

import java.util.List;

public class AdaDialogCao extends MAdapter<String>{

   public AdaDialogCao(Context context, List<String> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        String item = get(position);
        if (convertView == null) {
            convertView = DialogCao.getView(getContext(), parent);;
        }
        DialogCao mDialogCao=(DialogCao) convertView.getTag();
//        mDialogCao.set(item);
        return convertView;
    }
}
