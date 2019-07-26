package com.framewidget.newMenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framewidget.R;
import com.framewidget.newMenu.DfRadioGroup.DfCallback;
import com.framewidget.newMenu.SlidingMenu.OnCloseListener;
import com.framewidget.newMenu.SlidingMenu.OnOpenListener;
import com.mdx.framework.activity.MFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("ValidFragment")
public class SlidingFragment extends MFragment implements OnPageChangeListener,
        OnCheckedChangeListener {
    protected DfMViewPager mContentView;
    protected ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    protected Map<Integer, String> mtexts = new HashMap<Integer, String>();
    protected Map<Integer, Boolean> mStatus = new HashMap<Integer, Boolean>(); // 是否已经加载
    protected Map<Integer, Boolean> mIsShow = new HashMap<Integer, Boolean>();
    protected Map<Integer, String> mDatas = new HashMap<Integer, String>(); // 红点中的数字
    protected Map<Integer, Integer> mres = new HashMap<Integer, Integer>();
    protected Map<Integer, OnClickListener> mOnClickListeners = new HashMap<Integer, OnClickListener>();
    protected SlidingMenu menu;
    protected DfRadioGroup mRadioGroup;
    protected int whitch = 0;
    protected int width_left = 120;
    protected int width_right = 120;
    protected int position_new;
    protected int position_old;
    protected int position_check = 0;
    protected int bcRes;
    protected boolean isShow = true;
    protected Fragment mFragment_left;
    protected Fragment mFragment_right;
    protected LinearLayout mLinearLayout;
    protected DfCallback mICallback;
    protected MoveCallback mMoveCallback;
    protected RelativeLayout mRelativeLayout_bottom;
    protected Object mMActivity;
    protected boolean isTrue = true;
    protected boolean isCanPage = false;
    protected int resource = -1;
    protected float fadeDegree = 0;
    protected int OffscreenPageLimit;
    protected OnOpenListener mOnOpenListener;
    protected OnCloseListener mOnCloseListener;
    protected int direction = 0;// 0下1上

    public int getOffscreenPageLimit() {
        return OffscreenPageLimit;
    }

    public void setOffscreenPageLimit(int offscreenPageLimit) {
        OffscreenPageLimit = offscreenPageLimit;//只会加载一次onCreate
    }

    @SuppressLint("ValidFragment")
    public SlidingFragment(Object mMActivity) {
        this.mMActivity = mMActivity;
    }

    public SlidingFragment() {
    }

    @Override
    protected void create(Bundle arg0) {
        setContentView(R.layout.fra_sliding);
        init();
        setData();
    }

    @SuppressLint("NewApi")
    private void setData() {
        if (fragments.size() > 0 && isTrue) {
            WindowManager wm = (WindowManager) getContext().getSystemService(
                    Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            for (int i = 0; i < fragments.size(); i++) {
                LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
                        width / fragments.size(), LayoutParams.WRAP_CONTENT, 1);
                RadioButton mRadioButton;
                if (resource != -1) {
                    mRadioButton = (RadioButton) LayoutInflater.from(
                            getActivity()).inflate(resource, null);
                } else {
                    mRadioButton = (RadioButton) LayoutInflater.from(
                            getActivity()).inflate(R.layout.item_radio, null);
                }
                mRadioButton.setId(i);
                if (mtexts.containsKey(i)) {
                    mRadioButton.setText(mtexts.get(i));
                }
                if (mres.containsKey(i)) {
                    mRadioButton.setCompoundDrawablesWithIntrinsicBounds(0,
                            mres.get(i), 0, 0);
                }
                if (mOnClickListeners.containsKey(i)) {
                    mRadioButton.setOnClickListener(mOnClickListeners.get(i));
                }
                mRadioGroup.addView(mRadioButton, mLayoutParams);
                if (bcRes != 0)
                    mRadioGroup.setBackgroundResource(bcRes);
                LayoutParams mLayoutParams_1 = new LinearLayout.LayoutParams(
                        width / fragments.size(), LayoutParams.WRAP_CONTENT, 1);
                LinearLayout mLinearLayout_son = (LinearLayout) LayoutInflater
                        .from(getActivity()).inflate(R.layout.item_dian, null);
                mLinearLayout_son.setLayoutParams(mLayoutParams_1);
                mLinearLayout_son.setBackgroundColor(Color.TRANSPARENT);
                if (mDatas.containsKey(i)) {
                    ((TextView) mLinearLayout_son
                            .findViewById(R.id.mTextView_dian))
                            .setVisibility(View.VISIBLE);
                    ((TextView) mLinearLayout_son
                            .findViewById(R.id.mTextView_dian)).setText(mDatas
                            .get(i));
                    if (mIsShow.containsKey(i)) {
                        if (mIsShow.get(i)) {
                            mLinearLayout_son.findViewById(R.id.mTextView_dian)
                                    .setVisibility(View.VISIBLE);
                        } else {
                            mLinearLayout_son.findViewById(R.id.mTextView_dian)
                                    .setVisibility(View.INVISIBLE);
                        }
                    }
                }
                mLinearLayout.addView(mLinearLayout_son);
            }
            if (isCanPage) {
                mContentView.setScrollAble(false);
            }
            if (mICallback != null) {
                mRadioGroup.setCallback(mICallback);
            }
            mContentView.setAdapter(new MFragmentAdapter(
                    getChildFragmentManager()));
            if (mRadioGroup != null
                    && mRadioGroup.getChildAt(position_check) != null) {
                mRadioGroup.check(mRadioGroup.getChildAt(position_check)
                        .getId());
            }
            if (fragments.size() > 0 && fragments.get(0) instanceof ICallback) {
                ((ICallback) fragments.get(0)).dataLoad_ICallback();
                mStatus.put(0, false);
            }
            if (mOnOpenListener != null) {
                this.menu.setOnOpenListener(mOnOpenListener);
            }
            if (mOnCloseListener != null) {
                this.menu.setOnCloseListener(mOnCloseListener);
            }
            isTrue = false;
            if (OffscreenPageLimit != 0)
                mContentView.setOffscreenPageLimit(OffscreenPageLimit);
        }
    }

    /**
     * 设置回调
     */
    public void setmICallback(DfCallback mICallback) {
        this.mICallback = mICallback;
    }

    public void setMoveback(MoveCallback mMoveCallback) {
        mContentView.setmMoveCallback(mMoveCallback);
    }

    /**
     * 设置第一个显示的frgment
     */
    public void setFistShow(int position_check) {
        this.position_check = position_check;
    }

    /**
     * 设置指定位置的radiobutton的onclick事件
     */
    public void setOnPositionClick(int position, OnClickListener l) {
        mOnClickListeners.put(position, l);
    }

    /**
     * 设置指定位置的获取Mfragment
     */
    public Fragment getMfragmentByPosition(int position) {
        return fragments.get(position);
    }

    /**
     * 替换指定位置的图片和文字
     */
    public void replaceRes(int position, String text, int res) {
        ((RadioButton) mRadioGroup.getChildAt(position))
                .setCompoundDrawablesWithIntrinsicBounds(0, res, 0, 0);
        ((RadioButton) mRadioGroup.getChildAt(position)).setText(text);
    }

    /**
     * 替换指定位置的图片
     */
    public void replaceRes(int position, int res) {
        ((RadioButton) mRadioGroup.getChildAt(position))
                .setCompoundDrawablesWithIntrinsicBounds(0, res, 0, 0);
    }

    /**
     * 替换指定位置的文字
     */
    public void replaceRes(int position, String text) {
        ((RadioButton) mRadioGroup.getChildAt(position)).setText(text);
    }

    /**
     * 替换指定位置的右上角红点中的数值
     */
    public void replaceResDianCounts(int position, String text) {
        ((TextView) ((LinearLayout) mLinearLayout.getChildAt(position))
                .getChildAt(0)).setText(text);
    }

    /**
     * 设置是不能滑动
     */
    public void setNoPage() {
        this.isCanPage = true;
    }

    /**
     * 设置指定位置红点显示或隐藏
     */
    public void setIsShow(boolean isShow, int position) {
        if (mLinearLayout != null) {
            if (isShow) {
                ((TextView) ((LinearLayout) mLinearLayout.getChildAt(position))
                        .getChildAt(0)).setVisibility(View.VISIBLE);
            } else {
                ((TextView) ((LinearLayout) mLinearLayout.getChildAt(position))
                        .getChildAt(0)).setVisibility(View.INVISIBLE);
            }
        } else {
            mIsShow.put(position, isShow);
        }
    }

    // /**
    // * 设置指定位置初始状态红点显示或隐藏
    // *
    // */
    // public void setFirstIsShow(boolean isShow, int position) {
    // mIsShow.put(position, isShow);
    // }

    /**
     * 加载fragement
     *
     * @param mFragment
     */
    public void addContentView(Fragment mFragment) {
        fragments.add(mFragment);
        mStatus.put(fragments.size() - 1, true);
    }

    /**
     * 加载fragement
     *
     * @param mFragment
     */
    public void addContentView(Fragment mFragment, String text, int res,
                               String data) {
        fragments.add(mFragment);
        mtexts.put(fragments.size() - 1, text);
        mres.put(fragments.size() - 1, res);
        mStatus.put(fragments.size() - 1, true);
        mDatas.put(fragments.size() - 1, data);
    }

    /**
     * 加载fragement
     *
     * @param mFragment
     */
    public void addContentView(Fragment mFragment, String text, int res) {
        fragments.add(mFragment);
        mtexts.put(fragments.size() - 1, text);
        mres.put(fragments.size() - 1, res);
        mStatus.put(fragments.size() - 1, true);
    }

    /**
     * 加载fragement
     *
     * @param mFragment
     */
    public void addContentView(Fragment mFragment, String data) {
        fragments.add(mFragment);
        mStatus.put(fragments.size() - 1, true);
        mDatas.put(fragments.size() - 1, data);
    }

    /**
     * 设置底部导航栏背景
     */
    public void setBottomBackground(int bcRes) {
        this.bcRes = bcRes;
    }

    private void init() {
        mContentView = (DfMViewPager) findViewById(R.id.frame_content);
        mRadioGroup = (DfRadioGroup) findViewById(R.id.mRadioGroup);
        mLinearLayout = (LinearLayout) findViewById(R.id.mLinearLayout);
        mRelativeLayout_bottom = (RelativeLayout) findViewById(R.id.mRelativeLayout_bottom);
        mContentView.setOnPageChangeListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        if (direction == 0) {
            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            mRelativeLayout_bottom.setLayoutParams(layoutParams1);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ABOVE,
                    R.id.mRelativeLayout_bottom);
            mContentView.setLayoutParams(layoutParams);
        } else {
            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            mRelativeLayout_bottom.setLayoutParams(layoutParams1);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.BELOW,
                    R.id.mRelativeLayout_bottom);
            mContentView.setLayoutParams(layoutParams);
        }
        if (whitch != 0) {
            menu = new SlidingMenu(getActivity());
            switch (whitch) {
                case 1:
                    menu.setMode(SlidingMenu.LEFT);
                    break;
                case 2:
                    menu.setMode(SlidingMenu.RIGHT);
                    break;
                case 3:
                default:
                    menu.setMode(SlidingMenu.LEFT_RIGHT);
                    break;
            }
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
            // menu.setShadowWidthRes(R.dimen.j120dp);
            menu.setShadowDrawable(R.drawable.shadow);
            menu.setSecondaryShadowDrawable(R.drawable.shadow_right);
            menu.setBehindWidth(width_left);
            menu.setRightBehindWidthRes(width_right);
            menu.setFadeDegree(fadeDegree);
            menu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
            menu.setMenu(R.layout.menu_left);
            menu.setSecondaryMenu(R.layout.menu_right);
            if (mFragment_left != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mFrameLayout_left, mFragment_left)
                        .commit();
            }
            if (mFragment_right != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mFrameLayout_right, mFragment_right)
                        .commit();
            }
            // menu.attachToActivity(getActivity(),
            // SlidingMenu.SLIDING_CONTENT);
            menu.attachToActivity(getActivity(), SlidingMenu.SLIDING_CONTENT);
        }
        if (isShow) {
            mRelativeLayout_bottom.setVisibility(View.VISIBLE);
        } else {
            mRelativeLayout_bottom.setVisibility(View.GONE);
        }
    }

    /**
     * 设置左侧fragment
     */
    public void setLeftFragMent(MFragment mMFragment) {
        this.mFragment_left = mMFragment;
    }

    /**
     * 设置右侧fragment
     */
    public void setRightFragMent(MFragment mMFragment) {
        this.mFragment_right = mMFragment;
    }

    /**
     *
     */
    public void setLeftWidth(int width) {
        this.width_left = width;
    }

    /**
     */
    public void setFadeDegree(float fadeDegree) {
        this.fadeDegree = fadeDegree;
    }

    /**
     *
     */
    public void setRightWidth(int width) {
        this.width_right = width;
    }

    /**
     * 设置打开监听
     */
    public void setOnOpenListener(OnOpenListener l) {
        this.mOnOpenListener = l;
    }

    /**
     * 设置关闭监听
     */
    public void setOnCloseListener(OnCloseListener l) {
        this.mOnCloseListener = l;
    }

    /**
     * 设置上下bar
     */
    public void setTopOrBottom(int direction) {
        this.direction = direction;
    }

    /**
     * 设置模式 1:左侧显示 2：右侧显示 3：双显(默认是不显示)
     *
     * @param whitch
     */
    public void setMode(int whitch) {
        this.whitch = whitch;
    }

    public class MFragmentAdapter extends FragmentPagerAdapter {
        public MFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // MFragment mf = (MFragment) object;
            // mf.clearView();
            super.destroyItem(container, position, object);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (mMActivity instanceof OnPageScroll) {
            ((OnPageScroll) mMActivity).OnPageScroll(arg0, arg1, arg2);
        }
    }

    @Override
    public void onPageSelected(int arg0) {
        position_old = position_new;
        position_new = arg0;
        mRadioGroup.setOnCheckedChangeListener(null);
        mRadioGroup.check(mRadioGroup.getChildAt(arg0).getId());
        mRadioGroup.setOnCheckedChangeListener(this);
        if (fragments.get(arg0) instanceof ICallback && mStatus.get(arg0)) {
            ((ICallback) fragments.get(arg0)).dataLoad_ICallback();
            mStatus.put(arg0, false);
        }
        if (mMActivity instanceof OnPageSelset) {
            ((OnPageSelset) mMActivity).OnPageSelseTed(arg0);
        }
    }

    /**
     * 左滑
     */
    public void setLeftToggle() {
        menu.toggle();
    }

    /**
     * 右滑
     */
    public void setRightToggle() {
        menu.showSecondaryMenu();
    }

    /**
     * 返回上一页
     */
    public void goBack() {
        mContentView.setCurrentItem(position_old, false);
    }

    /**
     * 跳转到指定页
     */
    public void goWhere(int position) {
        mContentView.setCurrentItem(position, false);
    }

    /**
     * 设置底部显示或隐藏
     */
    public void toogleBottomBar(boolean isShow) {
        this.isShow = isShow;
    }

    /**
     * 设置button资源
     */
    public void setResource(int resource) {
        this.resource = resource;
    }

    /**
     * 首页不能使用dataLoad_ICallback方法，其他fagment可以使用
     */
    @Override
    public void onCheckedChanged(RadioGroup arg0, int arg1) {
        position_old = position_new;
        position_new = arg1;
        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            if (arg1 == mRadioGroup.getChildAt(i).getId()) {
                mContentView.setOnPageChangeListener(null);
                mContentView.setCurrentItem(i);
                if (fragments.size() > 0 && fragments.get(i) != null
                        && fragments.get(i) instanceof ICallback
                        && mStatus.get(i)) {
                    ((ICallback) fragments.get(i)).dataLoad_ICallback();
                    mStatus.put(i, false);
                }
                mContentView.setOnPageChangeListener(this);
                mRadioGroup.check(mRadioGroup.getChildAt(i).getId());
                if (mMActivity instanceof OnCheckChange) {
                    ((OnCheckChange) mMActivity).onCheckedChanged(arg1, i);
                }
            }
        }
    }

}
