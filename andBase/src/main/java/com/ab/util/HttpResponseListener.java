package com.ab.util;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;

import com.ab.http.AbStringHttpResponseListener;
import com.framewidget.F;
import com.mdx.framework.Frame;
import com.mdx.framework.log.MLog;
import com.mdx.framework.utility.Helper;

import org.json.JSONObject;

import static com.framewidget.F.createLoadingDialog;

/**
 * Created by DELL on 2017/3/29.
 */

public class HttpResponseListener extends AbStringHttpResponseListener {
    public String methodName;
    public String methodClassName;
    public boolean isShow = true;
    public Context context;
    public HttpResponseListenerSon mHttpResponseListenerSon;
    public Dialog dialog;

    public HttpResponseListener(Context context, HttpResponseListenerSon mHttpResponseListenerSon, String methodName) {
        this.methodName = methodName;
        this.mHttpResponseListenerSon = mHttpResponseListenerSon;
        this.context = context;
        dialog = createLoadingDialog(context, "数据加载中...");
    }


    public HttpResponseListener(Context context, HttpResponseListenerSon mHttpResponseListenerSon, String methodName, boolean isShow) {
        this.context = context;
        this.mHttpResponseListenerSon = mHttpResponseListenerSon;
        this.methodName = methodName;
        this.isShow = isShow;
        dialog = createLoadingDialog(context, "数据加载中...");
    }

    @Override
    public void onStart() {
        if (isShow) {
            dialog.show();
        }
        try {
            if (mHttpResponseListenerSon != null)
                mHttpResponseListenerSon.onStart();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFinish() {
        dialog.dismiss();
        try {
            if (mHttpResponseListenerSon != null)
                mHttpResponseListenerSon.onFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, String content, Throwable error) {
        Helper.toast("请求服务器失败", context);
        MLog.D("请求服务器失败方法名：" + methodName);
        dialog.dismiss();
        error.printStackTrace();
        try {
            if (mHttpResponseListenerSon != null)
                mHttpResponseListenerSon.onFailure(statusCode, content, error);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccess(int statusCode, String content) {
        dialog.dismiss();
        MLog.I(content);
        try {
            if (!TextUtils.isEmpty(content)) {
                JSONObject mJSONObject = new JSONObject(content);
                if (mJSONObject.has("errorcode") && !mJSONObject.getString("errorcode").equals("0000")) {
                    Helper.toast(mJSONObject.getString("errormsg"), context);
                    if (mJSONObject.getString("errorcode").equals("1105")) {
                        Frame.HANDLES.sentAll("FrgHome", 1105, null);
                    }
                    return;
                }
                if (F.mCallBackOnly != null && mJSONObject.has("token") && !TextUtils.isEmpty(mJSONObject.getString("token")) && mJSONObject.has("reftoken") && !TextUtils.isEmpty(mJSONObject.getString("reftoken"))) {
                    F.mCallBackOnly.goReturn(mJSONObject.getString("token"), mJSONObject.getString("reftoken"));
                }
            }
            if (mHttpResponseListenerSon != null) {
                mHttpResponseListenerSon.onSuccess(methodName, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
