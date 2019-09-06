package com.ntdlg.bc;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.ab.http.HttpUtil;
import com.ab.util.AbDateUtil;
import com.ab.util.AbMd5;
import com.ab.util.HttpResponseListener;
import com.facefr.controller.Controller;
import com.facefr.controller.SampleControllerCallBack;
import com.facefr.controller.StyleModel;
import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.TitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.utility.permissions.PermissionRequest;
import com.moxie.client.exception.ExceptionType;
import com.moxie.client.exception.MoxieException;
import com.moxie.client.manager.MoxieCallBack;
import com.moxie.client.manager.MoxieCallBackData;
import com.moxie.client.manager.MoxieContext;
import com.moxie.client.manager.MoxieSDK;
import com.moxie.client.manager.MoxieSDKRunMode;
import com.moxie.client.model.MxParam;
import com.ntdlg.bc.bean.BeanSQ;
import com.ntdlg.bc.frg.BaseFrg;
import com.ntdlg.bc.frg.FrgShenfenRezhengNew;
import com.ntdlg.bc.view.SortModel;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class F {
    public static String mUserId = com.ntdlg.bc.F.UserId;                                  //合作方系统中的客户ID
    //    public static String mApiKey = "228aa9f04f6c4e8ca9288dc049000a98";      //获取任务状态时使用
    public static String mApiKey = "8fd884a085f44159af23c38af7c79473";      //获取任务状态时使用
    public static String mBannerColor = "#000000"; //标题栏背景色
    public static String mTextColor = "#ffffff";  //标题栏字体颜色
    public static String mThemeColor = "#ff9500"; //页面主色调
    public static String mAgreementUrl = "https://api.51datakey.com/h5/agreement.html"; //协议URL
    public static final String MUSERID = "MUSERID";
    public static final String TOKEN = "TOKEN";
    public static final String REFTOKEN = "REFTOKEN";
    public static final String APPLYID = "APPLYID";
    public static final String MT = "meituanwaimai";
    public static final String ELM = "eleme";
    public static double latitude = 0;
    public static double longitude = 0;
    public static String address = "";
    public static String UserId = "", token, reftoken, applyId;
    public static String login = "/login";
    public static String check = "/check";
    public static String rapidLoan = "/api/customer/rapidLoan";
    public static String basicInfo = "/api/customer/basicInfo";
    public static String professionInfo = "/api/customer/professionInfo";
    public static String linkManInfo = "/api/customer/linkManInfo";
    public static String getPlatform = "/api/customer/getPlatform";
    public static String getVip88LoginUrl = "/api/customer/getVip88LoginUrl";
    public static String getInfo = "/api/customer/getInfo";
    public static String queryCards = "/api/customer/queryCards";
    public static String queryBill = "/api/customer/queryBill";
    public static String myBill = "/api/customer/myBill";
    public static String updateversion = "/updateversion";
    public static String applyDeadline = "/applyDeadline";
    public static String sendSms = "/sendSms";
    public static String profrssion = "/api/customer/profrssion";
    public static String education = "/api/customer/education";
    public static String relation = "/api/customer/relation";
    public static String jingdongAuth = "/api/customer/jingdongAuth";
    public static String taobaoAuth = "/api/customer/taobaoAndAlipayAuth";
    public static String noVip = "/api/customer/noVip";
    public static String gongjijinAuth = "/api/customer/gongjijinAuth";
    public static String shebaoAuth = "/api/customer/shebaoAuth";
    public static String xuexinwangAuth = "/api/customer/xuexinwangAuth";
    public static String yunyingshangAuth = "/api/customer/yunyingshangAuth";
    public static String orderAuth = "/api/customer/orderAuth";
    public static String setPhoto = "/api/customer/setPhoto";
    public static String chekNumber = "/api/customer/chekNumber";
    public static String applyPromote = "/api/customer/applyPromote";
    public static String myBills = "/api/customer/myBills";
    public static String scanIdentity = "/api/customer/scanIdentity";
    public static String logout = "/api/customer/logout";
    public static String beginApply = "/api/customer/beginApply";
    public static String bioAssay = "/api/customer/bioAssay";
    public static String setDefaultBank = "/api/customer/setDefaultBank";
    public static String bankBindOne = "/api/customer/bankBindOne";
    public static String bankBindTwo = "/api/customer/bankBindTwo";
    public static String bank = "/api/customer/bank";
    public static String loadProduct = "/loadProduct";
    public static String submitCkeck = "/api/customer/submitCkeck";
    public static String affirmBorrowData = "/api/customer/affirmBorrowData";
    public static String getUserCard = "/api/customer/getUserCard";
    public static String repayment = "/api/customer/repayment";
    public static String pay = "/api/customer/pay";
    public static String upRepayPic = "/api/customer/upRepayPic";
    public static String share = "/api/customer/share";
    public static String showAgreement = "/api/customer/showAgreement";
    public static String getOfflineData = "/api/customer/getOfflineData";
    public static String document = "/api/customer/document";
    public static String esgin = "/api/customer/esign";
    public static String meituanAuth = "/api/customer/meituanAuth";
    public static String elemeAuth = "/api/customer/elemeAuth";
    public static String savePhone = "/api/customer/savePhone";
    public static String getXDAlpayContent = "/api/customer/getXDAlpayContent";
    public static String getXDWeiXinPath = "/api/customer/getXDWeiXinPath";
    public static boolean isOpen = false;

    public static void Login(String mUserId, String token, String reftoken) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(Frame.CONTEXT);
        sp.edit().putString(MUSERID, mUserId).putString(TOKEN, token).putString(REFTOKEN, reftoken).commit();
        F.UserId = mUserId;
        F.token = token;
        F.reftoken = reftoken;
    }

    public static void Login(String token, String reftoken) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(Frame.CONTEXT);
        sp.edit().putString(TOKEN, token).putString(REFTOKEN, reftoken).commit();
        F.token = token;
        F.reftoken = reftoken;
    }

    public static void saveApplyId(String applyId) {
        if (!TextUtils.isEmpty(applyId)) {
            SharedPreferences sp = PreferenceManager
                    .getDefaultSharedPreferences(Frame.CONTEXT);
            sp.edit().putString(APPLYID, applyId).commit();
            F.applyId = applyId;
        }
    }

    public static String changeTime(String date) {
        try {
            return AbDateUtil.getStringByFormat(AbDateUtil.getDateByFormat(date, "yyyyMMddHHmmss"), "yyyy-MM-dd").equals("1900-01-01") ? "" : AbDateUtil.getStringByFormat(AbDateUtil.getDateByFormat(date, "yyyyMMddHHmmss"), "yyyy-MM-dd");
        } catch (Exception e) {
            return date;
        }
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    public static String getTime(String time) {
        if (!TextUtils.isEmpty(time) && com.framewidget.F.isDateBefore(AbDateUtil.getCurrentDate("yyyyMMddHHmmss"), time, "yyyyMMddHHmmss")) {
            String data = "";
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
                Date d2 = df.parse(time);
                long diff = d2.getTime() - new Date().getTime();// 这样得到的差值是微秒级别
                long days = diff / (1000 * 60 * 60 * 24);

                long hours = (diff - days * (1000 * 60 * 60 * 24))
                        / (1000 * 60 * 60);
                long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                        * (1000 * 60 * 60))
                        / (1000 * 60);
                long miao = (diff - days * 1000 * 60 * 60 * 24 - hours * 1000
                        * 60 * 60 - minutes * 1000 * 60) / 1000;
                data = "" + days + "天" + hours + "小时" + minutes + "分" + miao
                        + "秒";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        } else {
            return "";
        }
    }

    public static boolean isFirstInstall() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(Frame.CONTEXT);
        return sp.getBoolean("isFirstInstall", true);
    }

    public static void saveFirstInstall() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(Frame.CONTEXT);
        sp.edit().putBoolean("isFirstInstall", false).commit();
    }

    public static void init() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(Frame.CONTEXT);
        if (sp.contains(MUSERID)) {
            UserId = sp.getString(MUSERID, "");
            token = sp.getString(TOKEN, "");
            reftoken = sp.getString(REFTOKEN, "");
            applyId = sp.getString(APPLYID, "");
        }
    }

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     *
     * @return
     */
    public static String Bitmap2StrByBase64(String picpathcrop) {
        return Base64.encodeToString(bitmap2Byte(picpathcrop), Base64.NO_WRAP);
    }


    public static byte[] bitmap2Byte(String picpathcrop) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        com.mdx.framework.utility.BitmapRead.decodeSampledBitmapFromFile(
                picpathcrop, 360, 0).compress(Bitmap.CompressFormat.PNG, 50,
                out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    public static byte[] bitmap2Byte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50,
                out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    // T-B(活体检测)
    public static void mTBlivessCompare(final Activity activity, String packageSessionId, final String from) {
//        AuthBuilder authBuilder = getAuthBuilder(from);
//        authBuilder.setPackageCode("TC009");
//        authBuilder.livenessCompare(activity, packageSessionId);

        Helper.requestPermissions(new String[]{Manifest.permission.CAMERA}, new PermissionRequest() {
            @Override
            public void onGrant(String[] strings, int[] ints) {
                StyleModel model = new StyleModel();
//		可以设置各种属性（也可以不设置，按照默认值来），如：
//		model.resContentBgColor = Color.parseColor("#181818");
//		model.resActionBackImg = R.drawable.base_map;
//		model.actCount = 1;
//		model.actType = EnumInstance.EActType.act_shake;
                SampleControllerCallBack sampleControllerCallBack = new SampleControllerCallBack() {
                    //重写两个回调函数
                    @Override
                    public void onBack() {
                        System.out.println("回调：点击了返回");
                        //如果有需要，可以在这里做点击返回后要做的事情
                    }

                    @Override
                    public void onAllStepCompleteCallback(boolean isSuccess, String dataPage) {
                        if (isSuccess) {
                            System.out.println("回调：活体检测成功");
                            Frame.HANDLES.sentAll(from, 130, dataPage);
                        } else {
                            Helper.toast("活体检测失败", Frame.CONTEXT);
                        }
                    }

                };
                //开始
                Controller.getInstance(activity).setCallBack(sampleControllerCallBack).show(model);
            }
        });
    }


    // T-A(身份认证)
    public static void mTAocrVerify(Activity context, String from) {
//        AuthBuilder authBuilder = getAuthBuilder(from);
//        authBuilder.setPackageCode("TC009");
////        authBuilder.isHandOcr(true);
//        authBuilder.isShowConfirm(false);// TODO: 2016/12/28 OCR之后需要确认身份证识别信息
//        authBuilder.ocrVerify(context, "");
        Helper.startActivity(context, FrgShenfenRezhengNew.class, TitleAct.class, "from", from);

    }

//    public static AuthBuilder getAuthBuilder(final String from) {
//        /** 测试环境 */
////        String partner_order_id =  "201706140554";
//        String partner_order_id = System.currentTimeMillis() + "";
//        String pubKey = "64cbd1d8-1fc3-43b7-aad3-408be531d86f";
//        String security_key = "c6c0bdf2-a9ad-4ddc-aab4-5acd2483eb38";
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        String sign_time = simpleDateFormat.format(new Date());
//        String singStr = "pub_key=" + pubKey + "|partner_order_id=" + partner_order_id + "|sign_time=" + sign_time + "|security_key=" + security_key;
//        String sign = Md5.encrypt(singStr);
//        AuthBuilder authBuilder = new AuthBuilder(partner_order_id, pubKey, sign_time, sign, new OnResultListener() {
//            @Override
//            public void onResult(int op_type, String result) {
//                switch (op_type) {
//                    case AuthBuilder.OPTION_EOORO://流程终端异常,例如 用户返回
//
//                        break;
//                    case AuthBuilder.OPTION_COMPARE://活体检测结果回调
//                        ModelHT response1 = new Gson().fromJson(result, ModelHT.class);
//                        if (response1 != null && !TextUtils.isEmpty(response1.auth_result)) {
//                            if (response1.auth_result.equals("T")) {
//                                Frame.HANDLES.sentAll(from, 130, null);
//                            } else {
//                                Helper.toast("认证未通过", Frame.CONTEXT);
//                            }
//                        }
//                        break;
//                    case AuthBuilder.OPTION_OCR://人像比对结果回调
//                        ModelSF response = new Gson().fromJson(result, ModelSF.class);
//                        if (response != null && !TextUtils.isEmpty(response.idcard_front_photo)) {
//                            try {
//                                response.idcard_front_photo = URLDecoder.decode(response.idcard_front_photo, "UTF-8");
//                                response.idcard_back_photo = URLDecoder.decode(response.idcard_back_photo, "UTF-8");
//                                Frame.HANDLES.sentAll(from, 120, response);
//                            } catch (UnsupportedEncodingException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        break;
//                }
//            }
//
//        });
//        return authBuilder;
//    }

    public static void getUrl(final String url, final String from, final int type) {
        try {
            new Thread() {
                @Override
                public void run() {
                    Bitmap bitmap = null;
                    try {
                        URL pictureUrl = new URL(url);
                        HttpURLConnection con = (HttpURLConnection) pictureUrl
                                .openConnection();
                        InputStream in = con.getInputStream();
                        bitmap = BitmapFactory.decodeStream(in);
                        in.close();
                        Frame.HANDLES.sentAll(from, type, Base64.encodeToString(bitmap2Byte(bitmap), Base64.DEFAULT));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载联系人数据
     */
    public static void loadContacts(final Context context, final String from) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 插叙
                    String queryTye[] = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, "sort_key", "phonebook_label",
                            ContactsContract.CommonDataKinds.Phone.PHOTO_ID};
                    ContentResolver resolver = context.getContentResolver();
                    Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, queryTye, null, null,
                            "sort_key COLLATE LOCALIZED ASC");
                    if (phoneCursor == null || phoneCursor.getCount() == 0) {
                        Toast.makeText(context, "未获得读取联系人权限 或 未获得联系人数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int PHONES_NUMBER_INDEX = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int PHONES_DISPLAY_NAME_INDEX = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int SORT_KEY_INDEX = phoneCursor.getColumnIndex("sort_key");
                    int PHONEBOOK_LABEL = phoneCursor.getColumnIndex("phonebook_label");
                    int PHOTO_ID = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID);
                    if (phoneCursor.getCount() > 0) {
                        List<SortModel> mAllContactsList = new ArrayList<SortModel>();
                        while (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                            if (TextUtils.isEmpty(phoneNumber))
                                continue;
                            // 头像id
                            long photoId = phoneCursor.getLong(PHOTO_ID);
                            String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                            String sortKey = phoneCursor.getString(SORT_KEY_INDEX);
                            String book = phoneCursor.getString(PHONEBOOK_LABEL);
                            SortModel sortModel = new SortModel(filterEmoji(contactName), phoneNumber.replace(" ", ""), sortKey);
                            if (book == null) {
                                book = "#";
                            } else if (book.equals("#")) {
                                book = "#";
                            } else if (book.equals("")) {
                                book = "#";
                            }
                            mAllContactsList.add(sortModel);
                            Log.i("电话", phoneNumber + "");
                        }
                        if (from.equals("FrgLxrList")) {
                            Frame.HANDLES.sentAll("FrgLxrList", 0, mAllContactsList);
                        } else if (from.equals("FrgRenzhengxinxi")) {
                            Frame.HANDLES.sentAll("FrgRenzhengxinxi", 3, mAllContactsList);
                        }
                    }
                    phoneCursor.close();
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public static String getSharedPreferValue(String key) {
        String defValue = "";
        switch (key) {
            case "userId":
                defValue = com.ntdlg.bc.F.UserId;
                break;
            case "apiKey":
                defValue = mApiKey;
                break;
            case "bannerBgColor":
                defValue = mBannerColor;
                break;
            case "bannerTxtColor":
                defValue = mTextColor;
                break;
            case "themeColor":
                defValue = mThemeColor;
                break;
            case "agreementUrl":
                defValue = mAgreementUrl;
                break;
        }
        return getSharedPreferValue(key, defValue);
    }

    public static String getSharedPreferValue(String key, String defValue) {
        SharedPreferences sp = Frame.CONTEXT.getSharedPreferences("apikey_and_token", Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static Object json2Model(String json, Class<?> mclass) {
        return new Gson().fromJson(json, mclass);
    }

    public static String getVersionName() {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = Frame.CONTEXT.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    Frame.CONTEXT.getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (Exception e) {

        }
        return "1.0";
    }

    public static String getVersionCode() {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = Frame.CONTEXT.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    Frame.CONTEXT.getPackageName(), 0);
            String version = packInfo.versionCode + "";
            return version;
        } catch (Exception e) {

        }
        return "1.0";
    }

    /**
     * 用来遍历对象属性和属性值
     */
    public static String readClassAttr(Object tb) {
        HashMap map = new HashMap();
        List<String> list = new ArrayList<>();
        String str = "";
        try {
            Field[] fields = tb.getClass().getDeclaredFields();
            System.out.println(fields.length);
            for (Field field : fields) {
                field.setAccessible(true);
                if (!field.getName().equals("sign") && field.get(tb) != null && !TextUtils.isEmpty(field.get(tb).toString())) {
                    map.put(field.getName(), TextUtils.isEmpty(field.get(tb).toString()) ? "" : field.get(tb).toString());
                    list.add(field.getName());
                }
            }
            if (tb.getClass().getSuperclass() != null && (tb.getClass().getSuperclass().getSimpleName().equals("BeanBase") || tb.getClass().getSuperclass().getSimpleName().equals("BeanListBase"))) {
                Field[] fields_father = tb.getClass().getSuperclass().getDeclaredFields();
                for (Field field : fields_father) {
                    field.setAccessible(true);
                    if (!field.getName().equals("sign") && field.get(tb) != null && !TextUtils.isEmpty(field.get(tb).toString())) {
                        map.put(field.getName(), TextUtils.isEmpty(field.get(tb).toString()) ? "" : field.get(tb).toString());
                        list.add(field.getName());
                    }
                }
            }
            Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
            for (String key : list) {
                str += key + "=" + map.get(key) + "&";
            }
            str += "key=XiaoFeiJinRong6a6a877f144a05934";
            Log.i("sign=", str);
            return AbMd5.md5(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String readClassByJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String str = "";
            List<String> list = new ArrayList<>();
            HashMap map = new HashMap();
            Iterator iterator = jsonObject.keys();                       // joData是JSONObject对象
            while (iterator.hasNext()) {
                String key = iterator.next() + "";
                if (!key.equals("sign") && !TextUtils.isEmpty(jsonObject.optString(key))) {
                    map.put(key, jsonObject.optString(key));
                    list.add(key);
                }
            }
            Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
            for (String key : list) {
                str += key + "=" + map.get(key) + "&";
            }
            str += "key=XiaoFeiJinRong6a6a877f144a05934";
            Log.i("sign=", str);
            return AbMd5.md5(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern
                    .compile(
                            "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("[表情]");
                return source;
            }
            return source;
        }
        return source;
    }

    public static void rZhengWb(final Activity activity, String type, final BaseFrg mBaseFrg) {
        try {
            MxParam mxParam = new MxParam();
            mxParam.setUserId(getSharedPreferValue("userId"));
            mxParam.setApiKey(getSharedPreferValue("apiKey"));
//            mxParam.setBannerBgColor(getSharedPreferValue("bannerBgColor"));
//            mxParam.setBannerTxtColor(getSharedPreferValue("bannerTxtColor"));
//            mxParam.setThemeColor(getSharedPreferValue("themeColor"));
//            mxParam.setAgreementUrl(getSharedPreferValue("agreementUrl"));
            mxParam.setTaskType(type);
            mxParam.setPhone("");
            mxParam.setName("");
            mxParam.setIdcard("");

            MoxieSDK.getInstance().startInMode(activity, MoxieSDKRunMode.MoxieSDKRunModeForeground, mxParam, new MoxieCallBack() {

                @Override
                public void onStatusChange(MoxieContext moxieContext, MoxieCallBackData moxieCallBackData) {
                    super.onStatusChange(moxieContext, moxieCallBackData);
                    Log.d("onStatusChange", moxieCallBackData.toString());
                    if (MoxieSDK.getInstance().getRunMode() == MoxieSDKRunMode.MoxieSDKRunModeBackground
                            && !MoxieSDK.getInstance().isForeground()
                            && moxieCallBackData.getWaitCode() != null) {
                        Toast.makeText(activity, "需要显示SDK", Toast.LENGTH_SHORT).show();
                        MoxieSDK.getInstance().show();
                    } else if (MoxieSDK.getInstance().getRunMode() == MoxieSDKRunMode.MoxieSDKRunModeBackground
                            && moxieCallBackData.getWaitCode() == null) {
                    }
                }

                /**
                 *
                 *  物理返回键和左上角返回按钮的back事件以及sdk退出后任务的状态都通过这个函数来回调
                 *
                 * @param moxieContext       可以用这个来实现在魔蝎的页面弹框或者关闭魔蝎的界面
                 * @param moxieCallBackData  我们可以根据 MoxieCallBackData 的code来判断目前处于哪个状态，以此来实现自定义的行为
                 * @return 返回true表示这个事件由自己全权处理，返回false会接着执行魔蝎的默认行为(比如退出sdk)
                 *
                 *   # 注意，假如设置了MxParam.setQuitOnLoginDone(MxParam.PARAM_COMMON_YES);
                 *   登录成功后，返回的code是MxParam.ResultCode.IMPORTING，不是MxParam.ResultCode.IMPORT_SUCCESS
                 */
                @Override
                public boolean callback(MoxieContext moxieContext, MoxieCallBackData moxieCallBackData) {
                    if (MoxieSDK.getInstance().getRunMode() == MoxieSDKRunMode.MoxieSDKRunModeBackground) {
                    }

                    /**
                     *  # MoxieCallBackData的格式如下：
                     *  1.1.没有进行账单导入，未开始！(后台没有通知)
                     *      "code" : MxParam.ResultCode.IMPORT_UNSTART, "taskType" : "mail", "taskId" : "", "message" : "", "account" : "", "loginDone": false, "businessUserId": ""
                     *  1.2.平台方服务问题(后台没有通知)
                     *      "code" : MxParam.ResultCode.THIRD_PARTY_SERVER_ERROR, "taskType" : "mail", "taskId" : "", "message" : "", "account" : "xxx", "loginDone": false, "businessUserId": ""
                     *  1.3.魔蝎数据服务异常(后台没有通知)
                     *      "code" : MxParam.ResultCode.MOXIE_SERVER_ERROR, "taskType" : "mail", "taskId" : "", "message" : "", "account" : "xxx", "loginDone": false, "businessUserId": ""
                     *  1.4.用户输入出错（密码、验证码等输错且未继续输入）
                     *      "code" : MxParam.ResultCode.USER_INPUT_ERROR, "taskType" : "mail", "taskId" : "", "message" : "密码错误", "account" : "xxx", "loginDone": false, "businessUserId": ""
                     *  2.账单导入失败(后台有通知)
                     *      "code" : MxParam.ResultCode.IMPORT_FAIL, "taskType" : "mail",  "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx", "loginDone": false, "businessUserId": ""
                     *  3.账单导入成功(后台有通知)
                     *      "code" : MxParam.ResultCode.IMPORT_SUCCESS, "taskType" : "mail",  "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx", "loginDone": true, "businessUserId": "xxxx"
                     *  4.账单导入中(后台有通知)
                     *      "code" : MxParam.ResultCode.IMPORTING, "taskType" : "mail",  "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx", "loginDone": true, "businessUserId": "xxxx"
                     *
                     *  code           :  表示当前导入的状态
                     *  taskType       :  导入的业务类型，与MxParam.setTaskType()传入的一致
                     *  taskId         :  每个导入任务的唯一标识，在登录成功后才会创建
                     *  message        :  提示信息
                     *  account        :  用户输入的账号
                     *  loginDone      :  表示登录是否完成，假如是true，表示已经登录成功，接入方可以根据此标识判断是否可以提前退出
                     *  businessUserId :  第三方被爬取平台本身的userId，非商户传入，例如支付宝的UserId
                     */
                    if (moxieCallBackData != null) {
                        Log.d("BigdataFragment", "MoxieSDK Callback Data : " + moxieCallBackData.toString());
                        switch (moxieCallBackData.getCode()) {
                            /**
                             * 账单导入中
                             *
                             * 如果用户正在导入魔蝎SDK会出现这个情况，如需获取最终状态请轮询贵方后台接口
                             * 魔蝎后台会向贵方后台推送Task通知和Bill通知
                             * Task通知：登录成功/登录失败
                             * Bill通知：账单通知
                             */
                            case MxParam.ResultCode.IMPORTING:
                                if (moxieCallBackData.isLoginDone()) {
                                    //状态为IMPORTING, 且loginDone为true，说明这个时候已经在采集中，已经登录成功
                                    Log.d("Tag", "任务已经登录成功，正在采集中，SDK退出后不会再回调任务状态，任务最终状态会从服务端回调，建议轮询APP服务端接口查询任务/业务最新状态");

                                } else {
                                    //状态为IMPORTING, 且loginDone为false，说明这个时候正在登录中
                                    Log.d("Tag", "任务正在登录中，SDK退出后不会再回调任务状态，任务最终状态会从服务端回调，建议轮询APP服务端接口查询任务/业务最新状态");
                                }
                                break;
                            /**
                             * 任务还未开始
                             *
                             * 假如有弹框需求，可以参考 {@link BigdataFragment#showDialog(MoxieContext)}
                             *
                             * example:
                             *  case MxParam.ResultCode.IMPORT_UNSTART:
                             *      showDialog(moxieContext);
                             *      return true;
                             * */
                            case MxParam.ResultCode.IMPORT_UNSTART:
                                Log.d("Tag", "任务未开始");
                                break;
                            case MxParam.ResultCode.THIRD_PARTY_SERVER_ERROR:
                                Toast.makeText(activity, "导入失败(平台方服务问题)", Toast.LENGTH_SHORT).show();
                                break;
                            case MxParam.ResultCode.MOXIE_SERVER_ERROR:
                                Toast.makeText(activity, "导入失败(魔蝎数据服务异常)", Toast.LENGTH_SHORT).show();
                                break;
                            case MxParam.ResultCode.USER_INPUT_ERROR:
                                Toast.makeText(activity, "导入失败(" + moxieCallBackData.getMessage() + ")", Toast.LENGTH_SHORT).show();
                                break;
                            case MxParam.ResultCode.IMPORT_FAIL:
                                Toast.makeText(activity, "导入失败", Toast.LENGTH_SHORT).show();
                                break;
                            case MxParam.ResultCode.IMPORT_SUCCESS:
                                Log.d("tag", "任务采集成功，任务最终状态会从服务端回调，建议轮询APP服务端接口查询任务/业务最新状态");
                                BeanSQ mBeanSQ = new BeanSQ();
                                mBeanSQ.taskId = moxieCallBackData.getTaskId();
                                mBeanSQ.sign = readClassAttr(mBeanSQ);
                                //根据taskType进行对应的处理
                                switch (moxieCallBackData.getTaskType()) {
                                    case MxParam.PARAM_TASK_TAOBAO:
                                    case MxParam.PARAM_TASK_ALIPAY:
                                        HttpUtil.loadJsonUrl(activity, taobaoAuth, new Gson().toJson(mBeanSQ), new HttpResponseListener(activity, mBaseFrg, taobaoAuth));
                                        break;
                                    case MxParam.PARAM_TASK_FUND:
                                        HttpUtil.loadJsonUrl(activity, gongjijinAuth, new Gson().toJson(mBeanSQ), new HttpResponseListener(activity, mBaseFrg, gongjijinAuth));
                                        break;
                                    case MxParam.PARAM_TASK_JINGDONG:
                                        HttpUtil.loadJsonUrl(activity, jingdongAuth, new Gson().toJson(mBeanSQ), new HttpResponseListener(activity, mBaseFrg, jingdongAuth));
                                        break;
                                    case F.MT:
                                        HttpUtil.loadJsonUrl(activity, meituanAuth, new Gson().toJson(mBeanSQ), new HttpResponseListener(activity, mBaseFrg, meituanAuth));
                                        break;
                                    case F.ELM:
                                        HttpUtil.loadJsonUrl(activity, elemeAuth, new Gson().toJson(mBeanSQ), new HttpResponseListener(activity, mBaseFrg, elemeAuth));
                                        break;
                                    case MxParam.PARAM_TASK_CARRIER:
                                        HttpUtil.loadJsonUrl(activity, yunyingshangAuth, new Gson().toJson(mBeanSQ), new HttpResponseListener(activity, mBaseFrg, yunyingshangAuth));
                                        break;
                                    case MxParam.PARAM_TASK_SECURITY:
                                        HttpUtil.loadJsonUrl(activity, shebaoAuth, new Gson().toJson(mBeanSQ), new HttpResponseListener(activity, mBaseFrg, shebaoAuth));
                                        break;
                                    default:
                                }
                                moxieContext.finish();
                                return true;
                        }
                    }
                    return false;
                }

                /**
                 * @param moxieContext    可能为null，说明还没有打开魔蝎页面，使用前要判断一下
                 * @param moxieException  通过exception.getExceptionType();来获取ExceptionType来判断目前是哪个错误
                 */
                @Override
                public void onError(MoxieContext moxieContext, MoxieException moxieException) {
                    super.onError(moxieContext, moxieException);
                    if (moxieException.getExceptionType() == ExceptionType.SDK_HAS_STARTED) {
                        Toast.makeText(activity, moxieException.getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (moxieException.getExceptionType() == ExceptionType.SDK_LACK_PARAMETERS) {
                        Toast.makeText(activity, moxieException.getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (moxieException.getExceptionType() == ExceptionType.WRONG_PARAMETERS) {
                        Toast.makeText(activity, moxieException.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    Log.d("BigdataFragment", "MoxieSDK onError : " + (moxieException != null ? moxieException.toString() : ""));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否是银行卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 打开文件
     *
     * @param file
     */
    public static void openFile(File file, Context context) {
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 设置intent的Action属性
            intent.setAction(Intent.ACTION_VIEW);
            // 获取文件file的MIME类型
            String type = getMIMEType(file);
            // 设置intent的data和Type属性。
            intent.setDataAndType(/* uri */Uri.fromFile(file), type);
            // 跳转
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Helper.toast("暂不支持此文件查看", context);
        }
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    public static String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "")
            return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    public static final String[][] MIME_MapTable = {
            // {后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"}, {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"}, {".rtf", "application/rtf"},
            {".sh", "text/plain"}, {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"}, {".txt", "text/plain"},
            {".wav", "audio/x-wav"}, {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"}, {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"}, {"", "*/*"},


            {".323", "text/h323"},
            {".3gp", "video/3gpp"},
            {".aab", "application/x-authoware-bin"},
            {".aam", "application/x-authoware-map"},
            {".aas", "application/x-authoware-seg"},
            {".acx", "application/internet-property-stream"},
            {".ai", "application/postscript"},
            {".aif", "audio/x-aiff"},
            {".aifc", "audio/x-aiff"},
            {".aiff", "audio/x-aiff"},
            {".als", "audio/X-Alpha5"},
            {".amc", "application/x-mpeg"},
            {".ani", "application/octet-stream"},
            {".apk", "application/vnd.android.package-archive"},
            {".asc", "text/plain"},
            {".asd", "application/astound"},
            {".asf", "video/x-ms-asf"},
            {".asn", "application/astound"},
            {".asp", "application/x-asap"},
            {".asr", "video/x-ms-asf"},
            {".asx", "video/x-ms-asf"},
            {".au", "audio/basic"},
            {".avb", "application/octet-stream"},
            {".avi", "video/x-msvideo"},
            {".awb", "audio/amr-wb"},
            {".axs", "application/olescript"},
            {".bas", "text/plain"},
            {".bcpio", "application/x-bcpio"},
            {".bin ", "application/octet-stream"},
            {".bld", "application/bld"},
            {".bld2", "application/bld2"},
            {".bmp", "image/bmp"},
            {".bpk", "application/octet-stream"},
            {".bz2", "application/x-bzip2"},
            {".c", "text/plain"},
            {".cal", "image/x-cals"},
            {".cat", "application/vnd.ms-pkiseccat"},
            {".ccn", "application/x-cnc"},
            {".cco", "application/x-cocoa"},
            {".cdf", "application/x-cdf"},
            {".cer", "application/x-x509-ca-cert"},
            {".cgi", "magnus-internal/cgi"},
            {".chat", "application/x-chat"},
            {".class", "application/octet-stream"},
            {".clp", "application/x-msclip"},
            {".cmx", "image/x-cmx"},
            {".co", "application/x-cult3d-object"},
            {".cod", "image/cis-cod"},
            {".conf", "text/plain"},
            {".cpio", "application/x-cpio"},
            {".cpp", "text/plain"},
            {".cpt", "application/mac-compactpro"},
            {".crd", "application/x-mscardfile"},
            {".crl", "application/pkix-crl"},
            {".crt", "application/x-x509-ca-cert"},
            {".csh", "application/x-csh"},
            {".csm", "chemical/x-csml"},
            {".csml", "chemical/x-csml"},
            {".css", "text/css"},
            {".cur", "application/octet-stream"},
            {".dcm", "x-lml/x-evm"},
            {".dcr", "application/x-director"},
            {".dcx", "image/x-dcx"},
            {".der", "application/x-x509-ca-cert"},
            {".dhtml", "text/html"},
            {".dir", "application/x-director"},
            {".dll", "application/x-msdownload"},
            {".dmg", "application/octet-stream"},
            {".dms", "application/octet-stream"},
            {".doc", "application/msword"},
            {".docx",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".dot", "application/msword"},
            {".dvi", "application/x-dvi"},
            {".dwf", "drawing/x-dwf"},
            {".dwg", "application/x-autocad"},
            {".dxf", "application/x-autocad"},
            {".dxr", "application/x-director"},
            {".ebk", "application/x-expandedbook"},
            {".emb", "chemical/x-embl-dl-nucleotide"},
            {".embl", "chemical/x-embl-dl-nucleotide"},
            {".eps", "application/postscript"},
            {".epub", "application/epub+zip"},
            {".eri", "image/x-eri"},
            {".es", "audio/echospeech"},
            {".esl", "audio/echospeech"},
            {".etc", "application/x-earthtime"},
            {".etx", "text/x-setext"},
            {".evm", "x-lml/x-evm"},
            {".evy", "application/envoy"},
            {".exe", "application/octet-stream"},
            {".fh4", "image/x-freehand"},
            {".fh5", "image/x-freehand"},
            {".fhc", "image/x-freehand"},
            {".fif", "application/fractals"},
            {".flr", "x-world/x-vrml"},
            {".flv", "flv-application/octet-stream"},
            {".fm", "application/x-maker"},
            {".fpx", "image/x-fpx"},
            {".fvi", "video/isivideo"},
            {".gau", "chemical/x-gaussian-input"},
            {".gca", "application/x-gca-compressed"},
            {".gdb", "x-lml/x-gdb"},
            {".gif", "image/gif"},
            {".gps", "application/x-gps"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".hdf", "application/x-hdf"},
            {".hdm", "text/x-hdml"},
            {".hdml", "text/x-hdml"},
            {".hlp", "application/winhlp"},
            {".hqx", "application/mac-binhex40"},
            {".hta", "application/hta"},
            {".htc", "text/x-component"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".hts", "text/html"},
            {".htt", "text/webviewhtml"},
            {".ice", "x-conference/x-cooltalk"},
            {".ico", "image/x-icon"},
            {".ief", "image/ief"},
            {".ifm", "image/gif"},
            {".ifs", "image/ifs"},
            {".iii", "application/x-iphone"},
            {".imy", "audio/melody"},
            {".ins", "application/x-internet-signup"},
            {".ips", "application/x-ipscript"},
            {".ipx", "application/x-ipix"},
            {".isp", "application/x-internet-signup"},
            {".it", "audio/x-mod"},
            {".itz", "audio/x-mod"},
            {".ivr", "i-world/i-vrml"},
            {".j2k", "image/j2k"},
            {".jad", "text/vnd.sun.j2me.app-descriptor"},
            {".jam", "application/x-jam"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jfif", "image/pipeg"},
            {".jnlp", "application/x-java-jnlp-file"},
            {".jpe", "image/jpeg"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".jpz", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".jwc", "application/jwc"},
            {".kjx", "application/x-kjx"},
            {".lak", "x-lml/x-lak"},
            {".latex", "application/x-latex"},
            {".lcc", "application/fastman"},
            {".lcl", "application/x-digitalloca"},
            {".lcr", "application/x-digitalloca"},
            {".lgh", "application/lgh"},
            {".lha", "application/octet-stream"},
            {".lml", "x-lml/x-lml"},
            {".lmlpack", "x-lml/x-lmlpack"},
            {".log", "text/plain"},
            {".lsf", "video/x-la-asf"},
            {".lsx", "video/x-la-asf"},
            {".lzh", "application/octet-stream"},
            {".m13", "application/x-msmediaview"},
            {".m14", "application/x-msmediaview"},
            {".m15", "audio/x-mod"},
            {".m3u", "audio/x-mpegurl"},
            {".m3url", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".ma1", "audio/ma1"},
            {".ma2", "audio/ma2"},
            {".ma3", "audio/ma3"},
            {".ma5", "audio/ma5"},
            {".man", "application/x-troff-man"},
            {".map", "magnus-internal/imagemap"},
            {".mbd", "application/mbedlet"},
            {".mct", "application/x-mascot"},
            {".mdb", "application/x-msaccess"},
            {".mdz", "audio/x-mod"},
            {".me", "application/x-troff-me"},
            {".mel", "text/x-vmel"},
            {".mht", "message/rfc822"},
            {".mhtml", "message/rfc822"},
            {".mi", "application/x-mif"},
            {".mid", "audio/mid"},
            {".midi", "audio/midi"},
            {".mif", "application/x-mif"},
            {".mil", "image/x-cals"},
            {".mio", "audio/x-mio"},
            {".mmf", "application/x-skt-lbs"},
            {".mng", "video/x-mng"},
            {".mny", "application/x-msmoney"},
            {".moc", "application/x-mocha"},
            {".mocha", "application/x-mocha"},
            {".mod", "audio/x-mod"},
            {".mof", "application/x-yumekara"},
            {".mol", "chemical/x-mdl-molfile"},
            {".mop", "chemical/x-mopac-input"},
            {".mov", "video/quicktime"},
            {".movie", "video/x-sgi-movie"},
            {".mp2", "video/mpeg"},
            {".mp3", "audio/mpeg"},
            {".mp4", "video/mp4"},
            {".mpa", "video/mpeg"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".mpn", "application/vnd.mophun.application"},
            {".mpp", "application/vnd.ms-project"},
            {".mps", "application/x-mapserver"},
            {".mpv2", "video/mpeg"},
            {".mrl", "text/x-mrml"},
            {".mrm", "application/x-mrm"},
            {".ms", "application/x-troff-ms"},
            {".msg", "application/vnd.ms-outlook"},
            {".mts", "application/metastream"},
            {".mtx", "application/metastream"},
            {".mtz", "application/metastream"},
            {".mvb", "application/x-msmediaview"},
            {".mzv", "application/metastream"},
            {".nar", "application/zip"},
            {".nbmp", "image/nbmp"},
            {".nc", "application/x-netcdf"},
            {".ndb", "x-lml/x-ndb"},
            {".ndwn", "application/ndwn"},
            {".nif", "application/x-nif"},
            {".nmz", "application/x-scream"},
            {".nokia-op-logo", "image/vnd.nok-oplogo-color"},
            {".npx", "application/x-netfpx"},
            {".nsnd", "audio/nsnd"},
            {".nva", "application/x-neva1"},
            {".nws", "message/rfc822"},
            {".oda", "application/oda"},
            {".ogg", "audio/ogg"},
            {".oom", "application/x-AtlasMate-Plugin"},
            {".p10", "application/pkcs10"},
            {".p12", "application/x-pkcs12"},
            {".p7b", "application/x-pkcs7-certificates"},
            {".p7c", "application/x-pkcs7-mime"},
            {".p7m", "application/x-pkcs7-mime"},
            {".p7r", "application/x-pkcs7-certreqresp"},
            {".p7s", "application/x-pkcs7-signature"},
            {".pac", "audio/x-pac"},
            {".pae", "audio/x-epac"},
            {".pan", "application/x-pan"},
            {".pbm", "image/x-portable-bitmap"},
            {".pcx", "image/x-pcx"},
            {".pda", "image/x-pda"},
            {".pdb", "chemical/x-pdb"},
            {".pdf", "application/pdf"},
            {".pfr", "application/font-tdpfr"},
            {".pfx", "application/x-pkcs12"},
            {".pgm", "image/x-portable-graymap"},
            {".pict", "image/x-pict"},
            {".pko", "application/ynd.ms-pkipko"},
            {".pm", "application/x-perl"},
            {".pma", "application/x-perfmon"},
            {".pmc", "application/x-perfmon"},
            {".pmd", "application/x-pmd"},
            {".pml", "application/x-perfmon"},
            {".pmr", "application/x-perfmon"},
            {".pmw", "application/x-perfmon"},
            {".png", "image/png"},
            {".pnm", "image/x-portable-anymap"},
            {".pnz", "image/png"},
            {".pot,", "application/vnd.ms-powerpoint"},
            {".ppm", "image/x-portable-pixmap"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".pqf", "application/x-cprplayer"},
            {".pqi", "application/cprplayer"},
            {".prc", "application/x-prc"},
            {".prf", "application/pics-rules"},
            {".prop", "text/plain"},
            {".proxy", "application/x-ns-proxy-autoconfig"},
            {".ps", "application/postscript"},
            {".ptlk", "application/listenup"},
            {".pub", "application/x-mspublisher"},
            {".pvx", "video/x-pv-pvx"},
            {".qcp", "audio/vnd.qcelp"},
            {".qt", "video/quicktime"},
            {".qti", "image/x-quicktime"},
            {".qtif", "image/x-quicktime"},
            {".r3t", "text/vnd.rn-realtext3d"},
            {".ra", "audio/x-pn-realaudio"},
            {".ram", "audio/x-pn-realaudio"},
            {".rar", "application/rar"},
            {".ras", "image/x-cmu-raster"},
            {".rc", "text/plain"},
            {".rdf", "application/rdf+xml"},
            {".rf", "image/vnd.rn-realflash"},
            {".rgb", "image/x-rgb"},
            {".rlf", "application/x-richlink"},
            {".rm", "audio/x-pn-realaudio"},
            {".rmf", "audio/x-rmf"},
            {".rmi", "audio/mid"},
            {".rmm", "audio/x-pn-realaudio"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rnx", "application/vnd.rn-realplayer"},
            {".roff", "application/x-troff"},
            {".rp", "image/vnd.rn-realpix"},
            {".rpm", "audio/x-pn-realaudio-plugin"},
            {".rt", "text/vnd.rn-realtext"},
            {".rte", "x-lml/x-gps"},
            {".rtf", "application/rtf"},
            {".rtg", "application/metastream"},
            {".rtx", "text/richtext"},
            {".rv", "video/vnd.rn-realvideo"},
            {".rwc", "application/x-rogerwilco"},
            {".s3m", "audio/x-mod"},
            {".s3z", "audio/x-mod"},
            {".sca", "application/x-supercard"},
            {".scd", "application/x-msschedule"},
            {".sct", "text/scriptlet"},
            {".sdf", "application/e-score"},
            {".sea", "application/x-stuffit"},
            {".setpay", "application/set-payment-initiation"},
            {".setreg", "application/set-registration-initiation"},
            {".sgm", "text/x-sgml"},
            {".sgml", "text/x-sgml"},
            {".sh", "application/x-sh"},
            {".shar", "application/x-shar"},
            {".shtml", "magnus-internal/parsed-html"},
            {".shw", "application/presentations"},
            {".si6", "image/si6"},
            {".si7", "image/vnd.stiwap.sis"},
            {".si9", "image/vnd.lgtwap.sis"},
            {".sis", "application/vnd.symbian.install"},
            {".sit", "application/x-stuffit"},
            {".skd", "application/x-Koan"},
            {".skm", "application/x-Koan"},
            {".skp", "application/x-Koan"},
            {".skt", "application/x-Koan"},
            {".slc", "application/x-salsa"},
            {".smd", "audio/x-smd"},
            {".smi", "application/smil"},
            {".smil", "application/smil"},
            {".smp", "application/studiom"},
            {".smz", "audio/x-smd"},
            {".snd", "audio/basic"},
            {".spc", "application/x-pkcs7-certificates"},
            {".spl", "application/futuresplash"},
            {".spr", "application/x-sprite"},
            {".sprite", "application/x-sprite"},
            {".sdp", "application/sdp"},
            {".spt", "application/x-spt"},
            {".src", "application/x-wais-source"},
            {".sst", "application/vnd.ms-pkicertstore"},
            {".stk", "application/hyperstudio"},
            {".stl", "application/vnd.ms-pkistl"},
            {".stm", "text/html"},
            {".svg", "image/svg+xml"},
            {".sv4cpio", "application/x-sv4cpio"},
            {".sv4crc", "application/x-sv4crc"},
            {".svf", "image/vnd"},
            {".svg", "image/svg+xml"},
            {".svh", "image/svh"},
            {".svr", "x-world/x-svr"},
            {".swf", "application/x-shockwave-flash"},
            {".swfl", "application/x-shockwave-flash"},
            {".t", "application/x-troff"},
            {".tad", "application/octet-stream"},
            {".talk", "text/x-speech"},
            {".tar", "application/x-tar"},
            {".taz", "application/x-tar"},
            {".tbp", "application/x-timbuktu"},
            {".tbt", "application/x-timbuktu"},
            {".tcl", "application/x-tcl"},
            {".tex", "application/x-tex"},
            {".texi", "application/x-texinfo"},
            {".texinfo", "application/x-texinfo"},
            {".tgz", "application/x-compressed"},
            {".thm", "application/vnd.eri.thm"},
            {".tif", "image/tiff"},
            {".tiff", "image/tiff"},
            {".tki", "application/x-tkined"},
            {".tkined", "application/x-tkined"},
            {".toc", "application/toc"},
            {".toy", "image/toy"},
            {".tr", "application/x-troff"},
            {".trk", "x-lml/x-gps"},
            {".trm", "application/x-msterminal"},
            {".tsi", "audio/tsplayer"},
            {".tsp", "application/dsptype"},
            {".tsv", "text/tab-separated-values"},
            {".ttf", "application/octet-stream"},
            {".ttz", "application/t-time"},
            {".txt", "text/plain"},
            {".uls", "text/iuls"},
            {".ult", "audio/x-mod"},
            {".ustar", "application/x-ustar"},
            {".uu", "application/x-uuencode"},
            {".uue", "application/x-uuencode"},
            {".vcd", "application/x-cdlink"},
            {".vcf", "text/x-vcard"},
            {".vdo", "video/vdo"},
            {".vib", "audio/vib"},
            {".viv", "video/vivo"},
            {".vivo", "video/vivo"},
            {".vmd", "application/vocaltec-media-desc"},
            {".vmf", "application/vocaltec-media-file"},
            {".vmi", "application/x-dreamcast-vms-info"},
            {".vms", "application/x-dreamcast-vms"},
            {".vox", "audio/voxware"},
            {".vqe", "audio/x-twinvq-plugin"},
            {".vqf", "audio/x-twinvq"},
            {".vql", "audio/x-twinvq"},
            {".vre", "x-world/x-vream"},
            {".vrml", "x-world/x-vrml"},
            {".vrt", "x-world/x-vrt"},
            {".vrw", "x-world/x-vream"},
            {".vts", "workbook/formulaone"},
            {".wav", "audio/x-wav"},
            {".wax", "audio/x-ms-wax"},
            {".wbmp", "image/vnd.wap.wbmp"},
            {".wcm", "application/vnd.ms-works"},
            {".wdb", "application/vnd.ms-works"},
            {".web", "application/vnd.xara"},
            {".wi", "image/wavelet"},
            {".wis", "application/x-InstallShield"},
            {".wks", "application/vnd.ms-works"},
            {".wm", "video/x-ms-wm"},
            {".wma", "audio/x-ms-wma"},
            {".wmd", "application/x-ms-wmd"},
            {".wmf", "application/x-msmetafile"},
            {".wml", "text/vnd.wap.wml"},
            {".wmlc", "application/vnd.wap.wmlc"},
            {".wmls", "text/vnd.wap.wmlscript"},
            {".wmlsc", "application/vnd.wap.wmlscriptc"},
            {".wmlscript", "text/vnd.wap.wmlscript"},
            {".wmv", "audio/x-ms-wmv"},
            {".wmx", "video/x-ms-wmx"},
            {".wmz", "application/x-ms-wmz"},
            {".wpng", "image/x-up-wpng"},
            {".wps", "application/vnd.ms-works"},
            {".wpt", "x-lml/x-gps"},
            {".wri", "application/x-mswrite"},
            {".wrl", "x-world/x-vrml"},
            {".wrz", "x-world/x-vrml"},
            {".ws", "text/vnd.wap.wmlscript"},
            {".wsc", "application/vnd.wap.wmlscriptc"},
            {".wv", "video/wavelet"},
            {".wvx", "video/x-ms-wvx"},
            {".wxl", "application/x-wxl"},
            {".x-gzip", "application/x-gzip"},
            {".xaf", "x-world/x-vrml"},
            {".xar", "application/vnd.xara"},
            {".xbm", "image/x-xbitmap"},
            {".xdm", "application/x-xdma"},
            {".xdma", "application/x-xdma"},
            {".xdw", "application/vnd.fujixerox.docuworks"},
            {".xht", "application/xhtml+xml"},
            {".xhtm", "application/xhtml+xml"},
            {".xhtml", "application/xhtml+xml"},
            {".xla", "application/vnd.ms-excel"},
            {".xlc", "application/vnd.ms-excel"},
            {".xll", "application/x-excel"},
            {".xlm", "application/vnd.ms-excel"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".xlt", "application/vnd.ms-excel"},
            {".xlw", "application/vnd.ms-excel"}, {".xm", "audio/x-mod"},
            {".xml", "text/plain"}, {".xml", "application/xml"},
            {".xmz", "audio/x-mod"}, {".xof", "x-world/x-vrml"},
            {".xpi", "application/x-xpinstall"},
            {".xpm", "image/x-xpixmap"}, {".xsit", "text/xml"},
            {".xsl", "text/xml"}, {".xul", "text/xul"},
            {".xwd", "image/x-xwindowdump"}, {".xyz", "chemical/x-pdb"},
            {".yz1", "application/x-yz1"},
            {".z", "application/x-compress"},
            {".zac", "application/x-zaurus-zac"},
            {".zip", "application/zip"}, {".json", "application/json"}
    };
}
