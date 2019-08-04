package com.ntdlg.bc;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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


public class F {
    //    public static String mUserId = "5f0ccf69a9da4a5da28097a460d425e2";  //老
//    public static String mApiKey = "924fe49d1902490b9db135d4851b0dbf";
//    public static String mUserId = "2e3526ec62ee48e8b804b7c308a5db2f";                                  //合作方系统中的客户ID
    public static String mUserId = com.ntdlg.bc.F.UserId;                                  //合作方系统中的客户ID
    //    public static String mApiKey = "54642b1be96a4117a8bc0f01f46a621f";      //获取任务状态时使用
    public static String mApiKey = "228aa9f04f6c4e8ca9288dc049000a98";      //获取任务状态时使用
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
    public static String affirmBorrow = "/api/customer/affirmBorrow";
    public static String myBills = "/api/customer/myBills";
    public static String scanIdentity = "/api/customer/scanIdentity";
    public static String logout = "/api/customer/logout";
    public static String beginApply = "/api/customer/beginApply";
    public static String bioAssay = "/api/customer/bioAssay";
    public static String setDefaultBank = "/api/customer/setDefaultBank";
    public static String bankBind = "/api/customer/bankBind";
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
        return Base64.encodeToString(bitmap2Byte(picpathcrop), Base64.DEFAULT);
    }


    public static byte[] bitmap2Byte(String picpathcrop) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        com.mdx.framework.utility.BitmapRead.decodeSampledBitmapFromFile(
                picpathcrop, 480, 0).compress(Bitmap.CompressFormat.JPEG, 80,
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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80,
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
    public static void loadContacts(final Context context) {
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
                            SortModel sortModel = new SortModel(contactName, phoneNumber, sortKey);
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
                        Frame.HANDLES.sentAll("FrgLxrList", 0, mAllContactsList);
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
}
