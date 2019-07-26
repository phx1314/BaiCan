//
//  AdaYindao
//
//  Created by DELL on 2017-09-22 08:37:19
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.ada;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.framework.Frame;
import com.mdx.framework.activity.IndexAct;
import com.mdx.framework.adapter.MAdapter;
import com.mdx.framework.utility.Helper;
import com.ntdlg.bc.F;
import com.ntdlg.bc.frg.FrgHome;
import com.ntdlg.bc.item.Yindao;

import java.util.List;

public class AdaYindao extends MAdapter<Integer> {

    public AdaYindao(Context context, List<Integer> list) {
        super(context, list);
    }


    @Override
    public View getview(final int position, View convertView, ViewGroup parent) {
        Integer item = get(position);
        if (convertView == null) {
            convertView = Yindao.getView(getContext(), parent);
        }
        Yindao mYindao = (Yindao) convertView.getTag();
        mYindao.set(item);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == getList().size() - 1) {
                    F.saveFirstInstall();
                    Frame.HANDLES.close("FrgYindao");
                    Helper.startActivity(getContext(), FrgHome.class, IndexAct.class);
                }
            }
        });
        return convertView;
    }
}
