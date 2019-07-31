package com.framewidget.newMenu;

import android.content.Context;
import android.util.AttributeSet;

/**
 *
 */
public class DfMViewPager extends UnsildeViewPager {
    public ICallback callback;
    public MoveCallback mMoveCallback;

    public DfMViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DfMViewPager(Context context) {
        super(context);
    }

    public void setCallback(ICallback callback) {
        this.callback = callback;
    }

    public void setmMoveCallback(MoveCallback mMoveCallback) {
        this.mMoveCallback = mMoveCallback;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (mMoveCallback != null) {
//            return mMoveCallback.getBol();
//        } else {
//            return super.onInterceptTouchEvent(ev);
//        }
//    }
}
