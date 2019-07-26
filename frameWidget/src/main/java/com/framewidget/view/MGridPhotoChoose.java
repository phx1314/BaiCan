    package com.framewidget.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.framewidget.R;
import com.framewidget.ada.AdaSrc;

import java.util.ArrayList;
import java.util.List;

public class MGridPhotoChoose extends LinearLayout implements OnClickListener {
    private MGridView mMGridView;
    private List<String> data = new ArrayList<>();
    private int max = 9;
    private AdaSrc mAdaSrc;
    private CallBackGridPhoto mCallBackGridPhoto;

    public MGridPhotoChoose(Context context) {
        super(context);
        init();
        setonclick();
    }

    public void setMax(int max) {
        this.max = max;
        mAdaSrc.reFreash(max);
    }
    public void setAddSrc(int src) {
        mAdaSrc.reFreash_src(src);
    }
    public MGridPhotoChoose(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setonclick();
    }

    public List<String> getData() {
        List<String> sss = new ArrayList<>();
        for (int i = 0; i < mAdaSrc.getCount(); i++) {
            if (!TextUtils.isEmpty(mAdaSrc.get(i))) {
                sss.add(mAdaSrc.get(i));
            }
        }
        return sss;
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_gridphotochoose, this);
        mMGridView = (MGridView) findViewById(R.id.mMGridView);
        data.add("");
        mCallBackGridPhoto = new CallBackGridPhoto() {
            @Override
            public void goReturnDo(Object obj) {
                ArrayList<String> arrayList = (ArrayList<String>) obj;
                max -= arrayList.size();
                mAdaSrc.remove(mAdaSrc.getCount() - 1);
                mAdaSrc.AddAll(arrayList);
                if (max > 0) {
                    mAdaSrc.add("");
                }
                mAdaSrc.reFreash(max);
            }

            @Override
            public void goReturnDel(Object obj) {
                max++;
                mAdaSrc.remove(obj.toString());
                if (!TextUtils.isEmpty(mAdaSrc.get(mAdaSrc.getCount() - 1))) {
                    mAdaSrc.add("");
                }
                mAdaSrc.reFreash(max);
            }
        };
        mAdaSrc = new AdaSrc(getContext(), data, max, mCallBackGridPhoto);
        mMGridView.setAdapter(mAdaSrc);
    }

    private void setonclick() {
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case 001:
                break;
        }
    }

}
