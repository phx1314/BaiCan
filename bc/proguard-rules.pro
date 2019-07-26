# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keep class * extends android.app.Activity
-keep class * extends android.app.Application
-keep class * extends android.app.Service
-keep class * extends com.squareup.wire.Message
-keep public class * extends android.support.v4.app.Fragment
-keep public class * implements com.mdx.framework.prompt.Fragment { *; }
-keep public class * implements com.mdx.framework.prompt.Prompt { *; }
-keep public class * implements com.mdx.framework.prompt.ErrorPrompt { *; }
-keep public class com.udows.psocial.simle.SmileUtils { *; }
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keep public class android.os.IHardwareService$Stub
-keep public class android.os.SystemProperties
-keep class **$Builder extends com.squareup.wire.Message$Builder { *; }
-keep class com.squareup.** { *; }
-keep class org.apache.** { *; }
-keep class android.net.TrafficStats
-keep class **.R$xml


-keepnames class * extends com.squareup.wire.Message {
    public <fields>;
    public <methods>;
}
-keepclassmembers class *{
  public void *(com.mdx.framework.server.api.Son);
  public void *(...,com.mdx.framework.server.api.Son);
  public ** disposeMsg(...);
  public ** setActionBar(...);
  public Boolean onOptionsItemSelected(...);
  public ** init(...);
  public ** setFlashlightEnabled(...);
  public ** setDisplayOrientation(...);
  public ** getUidRxBytes(...);
}
-keep class com.taobao.wireless.**{*;}
-keep class com.alibaba.** {*;}

-dontwarn org.apache.**
-dontwarn com.squareup.**
-dontwarn okio.Okio

-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-keep class com.taobao.openimui.** {*;}
-dontwarn com.baidu.**

-dontwarn com.alibaba.**
-dontwarn com.taobao.**
-dontwarn com.unionpay.**
-dontwarn demo.Pinyin4jAppletDemo**
-dontwarn com.mdx.framework.**
-dontwarn com.udows.udowsmap.**
-dontwarn com.amap.api.**
-dontwarn com.udows.ouyu.**
-dontwarn org.androidannotations.api.**

-keepattributes *Annotation*

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keep   class com.amap.api.mapcore.**{*;}
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.amap.mapcore.*{*;}
-keep   class com.amap.api.location.**{*;}
-keep   class com.aps.**{*;}
-keep   class com.amap.api.services.**{*;}

-dontwarn com.igexin.**
-dontwarn android.support.**
-keep class com.igexin.**{*;}
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }


-dontwarn com.alipay.android.**
-keep class com.alipay.android.** { *;}
-dontwarn com.aretha.slidemenu.**
-keep class com.aretha.slidemenu.** { *;}
-dontwarn com.framewidget.**
-keep class com.framewidget.** { *;}
-dontwarn com.llllz.letscdf.**
-keep class com.llllz.letscdf.** { *;}
-dontwarn com.udows.lcwh.**
-keep class com.udows.lcwh.** { *;}
-dontwarn com.udows.psocial.**
-keep class com.udows.psocial.** { *;}
-dontwarn com.udows.shoppingcar.**
-keep class com.udows.shoppingcar.** { *;}
-dontwarn com.udows.social.**
-keep class com.udows.social.** { *;}
-dontwarn legency.graphic.widgets.**
-keep class legency.graphic.widgets.** { *;}

 -dontshrink
 -dontoptimize
 -dontwarn com.google.android.maps.**
 -dontwarn android.webkit.WebView
 -dontwarn com.umeng.**
 -dontwarn com.tencent.weibo.sdk.**
 -dontwarn com.facebook.**
 -keep public class javax.**
 -keep public class android.webkit.**
 -dontwarn android.support.v4.**
 -keep enum com.facebook.**
 -keepattributes Exceptions,InnerClasses,Signature
 -keepattributes *Annotation*
 -keepattributes SourceFile,LineNumberTable

 -keep public interface com.facebook.**
 -keep public interface com.tencent.**
 -keep public interface com.umeng.socialize.**
 -keep public interface com.umeng.socialize.sensor.**
 -keep public interface com.umeng.scrshot.**

 -keep public class com.umeng.socialize.* {*;}


 -keep class com.facebook.**
 -keep class com.facebook.** { *; }
 -keep class com.umeng.scrshot.**
 -keep public class com.tencent.** {*;}
 -keep class com.umeng.socialize.sensor.**
 -keep class com.umeng.socialize.handler.**
 -keep class com.umeng.socialize.handler.*
 -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
 -keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

 -keep class im.yixin.sdk.api.YXMessage {*;}
 -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

 -dontwarn twitter4j.**
 -keep class twitter4j.** { *; }

 -keep class com.tencent.** {*;}
 -dontwarn com.tencent.**
 -keep public class com.umeng.soexample.R$*{
     public static final int *;
 }
 -keep public class com.umeng.soexample.R$*{
     public static final int *;
 }
 -keep class com.tencent.open.TDialog$*
 -keep class com.tencent.open.TDialog$* {*;}
 -keep class com.tencent.open.PKDialog
 -keep class com.tencent.open.PKDialog {*;}
 -keep class com.tencent.open.PKDialog$*
 -keep class com.tencent.open.PKDialog$* {*;}

 -keep class com.sina.** {*;}
 -dontwarn com.sina.**
 -keep class  com.alipay.share.sdk.** {
    *;
 }
 -keepnames class * implements android.os.Parcelable {
     public static final ** CREATOR;
 }

 -keep class com.linkedin.** { *; }
 -keepattributes Signature

-keepclassmembers class fqcn.of.javascript.interface.for.webview {
 public *;
}

-keepattributes Exceptions,InnerClasses

-keep class io.rong.** {*;}

-keep class * implements io.rong.imlib.model.MessageContent{*;}

-keepattributes Signature

-keepattributes *Annotation*

-keep class sun.misc.Unsafe { *; }

-keep class com.google.gson.examples.android.model.** { *; }

-keepclassmembers class * extends com.sea_monster.dao.AbstractDao {
 public static java.lang.String TABLENAME;
}
-keep class **$Properties
-dontwarn org.eclipse.jdt.annotation.**

-keep class com.ultrapower.** {*;}
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
#==================gson && protobuf==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}

#-dontwarn org.kobjects.**
#-dontwarn org.ksoap2.**
#-dontwarn org.kxml2.**
#-dontwarn org.xmlpull.v1.**
#
#-keep class org.kobjects.** { *; }
#-keep class org.ksoap2.** { *; }
#-keep class org.kxml2.** { *; }
#-keep class org.xmlpull.** { *; }
#
#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-dontoptimize
#-dontpreverify


