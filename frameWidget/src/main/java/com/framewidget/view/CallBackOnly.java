package com.framewidget.view;

import android.app.Dialog;

public abstract class CallBackOnly {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public abstract void goReturn(String token, String reftoken);

    public abstract void goReturnDo(Dialog mDialog);
}
