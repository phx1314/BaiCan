package com.framewidget.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.framewidget.F;
import com.framewidget.R;
import com.mdx.framework.activity.MActivityActionbar;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 分享
 *
 * @author wchj
 */
public class ShareDialog extends Dialog implements
        android.view.View.OnClickListener {
    public Context context;
    public String url;
    public String content;
    public String title;
    public Object obj;
    private String imageUrl;
    private LinearLayout lin_qq, lin_xinlang, lin_qqkongjian, lin_weixin,
            lin_pengyouquan, lin_youjian, lin_duanxin, lin_copy;
    private TextView mTextView_cancel;

    public ShareDialog(Context context) {
        super(context);
        this.context = context;
    }

    public ShareDialog(Context context, int theme, String imageUrl, String url, String content,
                       String title) {
        super(context, theme);
        this.context = context;
        this.imageUrl = imageUrl;
        this.url = url;
        this.content = TextUtils.isEmpty(content) ? " " : content;
        this.title = TextUtils.isEmpty(title) ? " " : title;
    }

    public ShareDialog(Context context, int theme, String imageUrl, String url, String content,
                       String title, Object obj) {
        super(context, theme);
        this.context = context;
        this.url = url;
        this.imageUrl = imageUrl;
        this.content = TextUtils.isEmpty(content) ? " " : content;
        this.title = TextUtils.isEmpty(title) ? " " : title;
        this.obj = obj;
    }

    public ShareDialog(Context context, int theme, String imageUrl, String url) {
        super(context, theme);
        this.context = context;
        this.url = url;
        this.imageUrl = imageUrl;
        this.content = " ";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);
        initview();
        setShare(url, content, title);
    }

    private void initview() {
        mTextView_cancel = (TextView) findViewById(R.id.mTextView_cancel);
        lin_qq = (LinearLayout) findViewById(R.id.lin_qq);
        lin_xinlang = (LinearLayout) findViewById(R.id.lin_xinlang);
        lin_qqkongjian = (LinearLayout) findViewById(R.id.lin_qqkongjian);
        lin_weixin = (LinearLayout) findViewById(R.id.lin_weixin);
        lin_pengyouquan = (LinearLayout) findViewById(R.id.lin_pengyouquan);
        lin_youjian = (LinearLayout) findViewById(R.id.lin_youjian);
        lin_duanxin = (LinearLayout) findViewById(R.id.lin_duanxin);
        lin_copy = (LinearLayout) findViewById(R.id.lin_copy);
        lin_qq.setOnClickListener(this);
        lin_xinlang.setOnClickListener(this);
        lin_qqkongjian.setOnClickListener(this);
        lin_weixin.setOnClickListener(this);
        lin_pengyouquan.setOnClickListener(this);
        lin_youjian.setOnClickListener(this);
        lin_duanxin.setOnClickListener(this);
        lin_copy.setOnClickListener(this);
        mTextView_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareDialog.this.dismiss();
            }
        });
    }

    private void setShare(String url, String content, String title) {
        // Frontia.init(context.getApplicationContext(),
        // "2GYSP4jEmn4TigW466ZS5EyO");
        // socialShare = Frontia.getSocialShare();
        // socialShare.setContext(getContext());
        // socialShare.setClientId(MediaType.WEIXIN.toString(), F.WEIXINKEY);
        // socialShare.setClientId(MediaType.QQFRIEND.toString(), "1104610443");
        // socialShare.setClientName(MediaType.QQFRIEND.toString(), F.APPNAME);
        // mImageContent.setQQRequestType(FrontiaIQQReqestType.TYPE_DEFAULT);
        // mImageContent.setTitle(title);
        // mImageContent.setContent(content);
        // mImageContent.setLinkUrl(url);
        // mImageContent.setImageData(BitmapFactory.decodeResource(this
        // .getContext().getResources(), F.ICON_SHARE));
        // mImageContent.setImageUri(Uri.parse("http://apps.bdimg.com/developer/static/04171450/developer/images/icon/terminal_adapter.png"));
        // mImageContent.setContent(content);
    }

    @Override
    public void onClick(View arg0) {
        com.framewidget.F.isShare = 2;
        ShareAction mShareAction = new ShareAction((MActivityActionbar) context);
        mShareAction.withText(content);
        mShareAction.withTitle(title);
        mShareAction.withTargetUrl(url);
        mShareAction.setCallback(new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA share_media) {
                getShareSuccess();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Toast.makeText(context, "分享失败", Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        });
        if (TextUtils.isEmpty(imageUrl)) {
            mShareAction.withMedia(new UMImage(context, F.ICON_SHARE));
        } else {
            mShareAction.withMedia(new UMImage(context, imageUrl));
//            F.ICON_SHARE_URL = "";
        }
        if (arg0.getId() == R.id.lin_qq) {
            mShareAction.setPlatform(SHARE_MEDIA.QQ);
        } else if (arg0.getId() == R.id.lin_weixin) {
            mShareAction.setPlatform(SHARE_MEDIA.WEIXIN);
        } else if (arg0.getId() == R.id.lin_pengyouquan) {
//            mShareAction.withTitle(title + "\r\n<br>" +content);
            mShareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
        } else if (arg0.getId() == R.id.lin_xinlang) {
            mShareAction.setPlatform(SHARE_MEDIA.SINA);
        } else if (arg0.getId() == R.id.lin_youjian) {
            mShareAction.setPlatform(SHARE_MEDIA.EMAIL);
        } else if (arg0.getId() == R.id.lin_duanxin) {
            mShareAction.setPlatform(SHARE_MEDIA.SMS);
        } else if (arg0.getId() == R.id.lin_qqkongjian) {
            mShareAction.setPlatform(SHARE_MEDIA.QZONE);
        } else if (arg0.getId() == R.id.lin_copy) {
            ClipboardManager clip = (ClipboardManager) ((MActivityActionbar) context)
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            // clip.getText(); // 粘贴
            clip.setText(url); // 复制
            Toast.makeText(context, "复制成功", Toast.LENGTH_LONG).show();
            return;
        }
        this.dismiss();
        mShareAction.share();
    }

    /**
     * 分享成功
     */
    private void getShareSuccess() {
//		Toast.makeText(context, "分享成功", Toast.LENGTH_LONG)
//				.show();
        try {
            F.mCallBackShareJieKou.goReturnDo(obj);
        } catch (Exception e) {
        }
        // ApisFactory.getApiMShareSuccess().load(context, ShareDialog.this,
        // "ShareSuccess");
    }

}
