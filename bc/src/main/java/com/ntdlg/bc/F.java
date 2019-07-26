package com.ntdlg.bc;

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
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.ab.util.AbDateUtil;
import com.ab.util.AbMd5;
import com.authreal.api.AuthBuilder;
import com.authreal.api.OnResultListener;
import com.face.bsdk.crypt.Md5;
import com.google.gson.Gson;
import com.mdx.framework.Frame;
import com.mdx.framework.utility.Helper;
import com.moxie.client.model.MxParam;
import com.ntdlg.bc.model.ModelHT;
import com.ntdlg.bc.model.ModelSF;
import com.ntdlg.bc.view.SortModel;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
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
    public static String mApiKey = "ab81c5bb5d95462fb02d0b267e0a8ff2";      //获取任务状态时使用
    public static String mBannerColor = "#000000"; //标题栏背景色
    public static String mTextColor = "#ffffff";  //标题栏字体颜色
    public static String mThemeColor = "#ff9500"; //页面主色调
    public static String mAgreementUrl = "https://api.51datakey.com/h5/agreement.html"; //协议URL
    public static final String MUSERID = "MUSERID";
    public static final String TOKEN = "TOKEN";
    public static final String REFTOKEN = "REFTOKEN";
    public static final String APPLYID = "APPLYID";

    public static String UserId = "", token, reftoken, applyId;
    public static String login = "/login";
    public static String check = "/check";
    public static String rapidLoan = "/api/customer/rapidLoan";
    public static String basicInfo = "/api/customer/basicInfo";
    public static String professionInfo = "/api/customer/professionInfo";
    public static String linkManInfo = "/api/customer/linkManInfo";
    public static String phoneAuth = "/api/customer/phoneAuth";
    public static String getPlatform = "/api/customer/getPlatform";
    public static String getInfo = "/api/customer/getInfo";
    public static String queryCards = "/api/customer/queryCards";
    public static String queryBill = "/api/customer/queryBill";
    public static String myBill = "/api/customer/myBill";
    public static String updateversion = "/updateversion";
    public static String addopinion = "/api/customer/addopinion";
    public static String applyDeadline = "/applyDeadline";
    public static String sendSms = "/sendSms";
    public static String profrssion = "/api/customer/profrssion";
    public static String education = "/api/customer/education";
    public static String relation = "/api/customer/relation";
    public static String jingdongAuth = "/api/customer/jingdongAuth";
    public static String taobaoAuth = "/api/customer/taobaoAuth";
    public static String zhifubaoAuth = "/api/customer/zhifubaoAuth";
    public static String gongjijinAuth = "/api/customer/gongjijinAuth";
    public static String shebaoAuth = "/api/customer/shebaoAuth";
    public static String xuexinwangAuth = "/api/customer/xuexinwangAuth";
    public static String yunyingshangAuth = "/api/customer/yunyingshangAuth";
    public static String orderAuth = "/api/customer/orderAuth";
    public static String setNicheng = "/api/customer/setNicheng";
    public static String setPhoto = "/api/customer/setPhoto";
    public static String chekNumber = "/api/customer/chekNumber";
    public static String applyPromote = "/api/customer/applyPromote";
    public static String affirmBorrow = "/api/customer/affirmBorrow";
    public static String myBills = "/api/customer/myBills";
    public static String scanIdentity = "/api/customer/scanIdentity";
    public static String logout = "/api/customer/logout";
    public static String beginApply = "/api/customer/beginApply";
    public static String bioAssay = "/api/customer/bioAssay";
    public static String scanBackCard = "/api/customer/scanBackCard";
    public static String setDefaultBank = "/api/customer/setDefaultBank";
    public static String bankBind = "/api/customer/bankBind";
    public static String bank = "/api/customer/bank";
    public static String loadProduct = "/loadProduct";
    public static String nextStep = "/api/customer/nextStep";
    public static String submitCkeck = "/api/customer/submitCkeck";
    public static String affirmBorrowData = "/api/customer/affirmBorrowData";
    public static String getUserCard = "/api/customer/getUserCard";
    public static String repayment = "/api/customer/repayment";
    public static String pay = "/api/customer/pay";
    public static String upRepayPic = "/api/customer/upRepayPic";
    public static String share = "/api/customer/share";
    public static String savePhone = "/api/customer/savePhone";
    public static String showAgreement = "/api/customer/showAgreement";
    public static String getOfflineData = "/api/customer/getOfflineData";
    public static String document = "/api/customer/document";

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

    public static String getTime(String time) {
        if (!TextUtils.isEmpty(time)) {
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

    // T-B(活体检测+人像比对)
    public static void mTBlivessCompare(Activity activity, String packageSessionId, String from) {
        AuthBuilder authBuilder = getAuthBuilder(from);
        authBuilder.setPackageCode("TC009");
        authBuilder.livenessCompare(activity, packageSessionId);
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

    // T-A(OCR+身份认证)
    public static void mTAocrVerify(Activity context, String from) {
        AuthBuilder authBuilder = getAuthBuilder(from);
        authBuilder.setPackageCode("TC009");
//        authBuilder.isHandOcr(true);
        authBuilder.isShowConfirm(false);// TODO: 2016/12/28 OCR之后需要确认身份证识别信息
        authBuilder.ocrVerify(context, "");
    }

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

    public static AuthBuilder getAuthBuilder(final String from) {
        /** 测试环境 */
//        String partner_order_id =  "201706140554";
        String partner_order_id = System.currentTimeMillis() + "";
        String pubKey = "64cbd1d8-1fc3-43b7-aad3-408be531d86f";
        String security_key = "c6c0bdf2-a9ad-4ddc-aab4-5acd2483eb38";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String sign_time = simpleDateFormat.format(new Date());
        String singStr = "pub_key=" + pubKey + "|partner_order_id=" + partner_order_id + "|sign_time=" + sign_time + "|security_key=" + security_key;
        String sign = Md5.encrypt(singStr);
        AuthBuilder authBuilder = new AuthBuilder(partner_order_id, pubKey, sign_time, sign, new OnResultListener() {
            @Override
            public void onResult(int op_type, String result) {
                switch (op_type) {
                    case AuthBuilder.OPTION_EOORO://流程终端异常,例如 用户返回

                        break;
                    case AuthBuilder.OPTION_COMPARE://活体检测结果回调
                        ModelHT response1 = new Gson().fromJson(result, ModelHT.class);
                        if (response1 != null && !TextUtils.isEmpty(response1.auth_result)) {
                            if (response1.auth_result.equals("T")) {
                                Frame.HANDLES.sentAll(from, 130, null);
                            } else {
                                Helper.toast("认证未通过", Frame.CONTEXT);
                            }
                        }
                        break;
                    case AuthBuilder.OPTION_OCR://人像比对结果回调
                        ModelSF response = new Gson().fromJson(result, ModelSF.class);
                        if (response != null && !TextUtils.isEmpty(response.idcard_front_photo)) {
                            try {
                                response.idcard_front_photo = URLDecoder.decode(response.idcard_front_photo, "UTF-8");
                                response.idcard_back_photo = URLDecoder.decode(response.idcard_back_photo, "UTF-8");
                                Frame.HANDLES.sentAll(from, 120, response);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }

        });
        return authBuilder;
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
                if (!field.getName().equals("sign") && !TextUtils.isEmpty(field.get(tb).toString())) {
                    map.put(field.getName(), TextUtils.isEmpty(field.get(tb).toString()) ? "" : field.get(tb).toString());
                    list.add(field.getName());
                }
            }
            if (tb.getClass().getSuperclass() != null && (tb.getClass().getSuperclass().getSimpleName().equals("BeanBase") || tb.getClass().getSuperclass().getSimpleName().equals("BeanListBase"))) {
                Field[] fields_father = tb.getClass().getSuperclass().getDeclaredFields();
                for (Field field : fields_father) {
                    field.setAccessible(true);
                    if (!field.getName().equals("sign") && !TextUtils.isEmpty(field.get(tb).toString())) {
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

    public static void rZhengWb(Activity activity, String type) {
        try {
            Bundle bundle = new Bundle();
            MxParam mxParam = new MxParam();
            mxParam.setUserId(getSharedPreferValue("userId"));
            mxParam.setApiKey(getSharedPreferValue("apiKey"));
            mxParam.setBannerBgColor(getSharedPreferValue("bannerBgColor"));
            mxParam.setBannerTxtColor(getSharedPreferValue("bannerTxtColor"));
            mxParam.setThemeColor(getSharedPreferValue("themeColor"));
            mxParam.setAgreementUrl(getSharedPreferValue("agreementUrl"));
            mxParam.setFunction(type);
            bundle.putParcelable("param", mxParam);
            Intent intent = new Intent(activity, com.moxie.client.MainActivity.class);
            intent.putExtras(bundle);
            activity.startActivityForResult(intent, 0);
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
