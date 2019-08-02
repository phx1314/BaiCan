package com.ntdlg.bc;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.mdx.framework.Frame;
import com.moxie.client.manager.MoxieSDK;
import com.umeng.socialize.PlatformConfig;


/**
 * Created by bob on 2015/1/30.
 */
public class Mapplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Frame.init(getApplicationContext());
        F.init();
        MoxieSDK.init(this);
    }

    {
        com.framewidget.F.ICON_SHARE = R.drawable.logo;
        com.framewidget.F.WEIXINKEY = com.framewidget.F.WEIXINID = "wx3f06a2ea3b98c175";
        com.framewidget.F.WEIXINSEC = "84ebd12b60cdd7c224871736e5de7a78";
        com.framewidget.F.APPNAME = "小时代";
        com.framewidget.F.QQID = "1106422418";
        com.framewidget.F.QQSEC = "tlCpuAlnK6kd9OIh";
        PlatformConfig.setWeixin(com.framewidget.F.WEIXINID,
                com.framewidget.F.WEIXINSEC);
        // 新浪微博
        PlatformConfig.setSinaWeibo(com.framewidget.F.SINAID,
                com.framewidget.F.SiNASEC);
        PlatformConfig.setQQZone(com.framewidget.F.QQID,
                com.framewidget.F.QQSEC);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
