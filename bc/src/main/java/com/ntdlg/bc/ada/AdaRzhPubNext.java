//
//  AdaRzhPubNext
//
//  Created by Administrator on 2019-10-07 09:33:18
//  Copyright (c) Administrator All rights reserved.


/**

 */

package com.ntdlg.bc.ada;

import java.util.List;

import com.mdx.framework.adapter.MAdapter;

import android.content.Context;
import android.view.ViewGroup;
import android.view.View;

import com.ntdlg.bc.item.RzhPubNext;
import com.ntdlg.bc.model.ModelPub;

public class AdaRzhPubNext extends MAdapter<ModelPub.NextProcessBean> {

    public AdaRzhPubNext(Context context, List<ModelPub.NextProcessBean> list) {
        super(context, list);
    }


    @Override
    public View getview(int position, View convertView, ViewGroup parent) {
        ModelPub.NextProcessBean item = get(position);
        if (convertView == null) {
            convertView = RzhPubNext.getView(getContext(), parent);
            ;
        }
        RzhPubNext mRzhPubNext = (RzhPubNext) convertView.getTag();
        mRzhPubNext.set(item);
        return convertView;
    }
}
