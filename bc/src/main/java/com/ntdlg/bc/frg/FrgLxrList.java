//
//  FrgLxrList
//
//  Created by DELL on 2017-06-06 14:42:54
//  Copyright (c) DELL All rights reserved.


/**

 */

package com.ntdlg.bc.frg;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framewidget.F;
import com.framewidget.view.SideBar;
import com.mdx.framework.Frame;
import com.mdx.framework.widget.ActionBar;
import com.mdx.framework.widget.MPageListView;
import com.ntdlg.bc.R;
import com.ntdlg.bc.ada.AdaLxr;
import com.ntdlg.bc.ada.AdaLxr2;
import com.ntdlg.bc.view.PinyinComparator;
import com.ntdlg.bc.view.SortModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FrgLxrList extends BaseFrg {

    public LinearLayout mLinearLayout_serach;
    public EditText mEditText;
    public RelativeLayout mRelativeLayout_1;
    public MPageListView mMPageListView;
    public SideBar sidrbar;
    public TextView dialog;
    public MPageListView mMPageListView_2;
    public List<SortModel> mAllContactsList = new ArrayList<SortModel>();
    public String from;
    public int type;

    @Override
    protected void create(Bundle savedInstanceState) {
        from = getActivity().getIntent().getStringExtra("from");
        type = getActivity().getIntent().getIntExtra("type", 0);

        setContentView(R.layout.frg_lxr_list);
        initView();
        loaddata();
    }

    @Override
    public void disposeMsg(int type, Object obj) {
        switch (type) {
            case 0:
                mAllContactsList = (ArrayList) obj;
                Collections.sort(mAllContactsList, new PinyinComparator());
                mMPageListView.setAdapter(new AdaLxr(getContext(),
                        mAllContactsList));
                break;
            case 1:
                Frame.HANDLES.sentAll(from, FrgLxrList.this.type, obj);
                this.finish();
                break;
        }
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mLinearLayout_serach = (LinearLayout) findViewById(R.id.mLinearLayout_serach);
        mEditText = (EditText) findViewById(R.id.mEditText);
        mRelativeLayout_1 = (RelativeLayout) findViewById(R.id.mRelativeLayout_1);
        mMPageListView = (MPageListView) findViewById(R.id.mMPageListView);
        sidrbar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        mMPageListView_2 = (MPageListView) findViewById(R.id.mMPageListView_2);

        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                dialog.setText(s);
                dialog.setVisibility(View.VISIBLE);
                // 该字母首次出现的位置
                int position = getPositionForSection(s.charAt(0), ((AdaLxr) mMPageListView.getListAdapter()).getList());
                if (position != -1) {
                    mMPageListView.setSelection(position + 1);
                }
            }

            @Override
            public void onUp() {
                dialog.setVisibility(View.INVISIBLE);
            }
        });
        sidrbar.setTextView(dialog);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                if (arg0.toString().equals("")) {
                    mRelativeLayout_1.setVisibility(View.VISIBLE);
                    mMPageListView_2.setVisibility(View.GONE);
                } else {
                    mRelativeLayout_1.setVisibility(View.GONE);
                    mMPageListView_2.setVisibility(View.VISIBLE);
                    filterData(arg0.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(char section, List<SortModel> mSortModels) {
        for (int i = 0; i < mSortModels.size(); i++) {
            String sortStr = F.toPinYin(mSortModels.get(i).name.charAt(0));
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    public void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = mAllContactsList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : mAllContactsList) {
                String name = sortModel.name;
                if (name.indexOf(filterStr.toString()) != -1
                        || F
                        .toPinYin(sortModel.name.charAt(0))
                        .startsWith(filterStr.toString()) || F
                        .toPinYin(sortModel.name.charAt(0)).toUpperCase()
                        .startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }
        // // 根据a-z进行排序
//        Collections.sort(filterDateList, pinyinComparator);
        mMPageListView_2.setAdapter(new AdaLxr2(getContext(),
                filterDateList));
    }

    public void loaddata() {
        com.ntdlg.bc.F.loadContacts(getContext(),"FrgLxrList");
    }

    @Override
    public void setActionBar(ActionBar actionBar, Context context) {
        super.setActionBar(actionBar, context);
        mHeadlayout.setTitle("选择联系人");
    }
}