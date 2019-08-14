package com.facefr.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facefr.activity_ocr.R;
import com.facefr.bean.CollectInfoInstance;
import com.facefr.bean.ModelSF;
import com.facefr.util.BmpUtil;
import com.facefr.util.MiniBitmap;
import com.google.gson.Gson;
import com.intsig.scanner.ScannerEngine;
import com.intsig.scanner.ScannerSDK;
import com.mdx.framework.Frame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class PictureUploadActivity extends Activity implements Camera.PreviewCallback {

    public static final String EXTRA_KEY_APP_KEY = "EXTRA_KEY_APP_KEY";
    public static final String EXTRA_KEY_IMG_CAMERA_PATH = "EXTRA_KEY_IMG_CAMERA_PATH";
    private static final String APPKEY = "yh2NDK9692y5LaLTh2PLNRBS";
    private Preview mPreview = null;
    private Camera mCamera = null;
    private int numberOfCameras;
    public static byte[] camerabyte = null;
    // The first rear facing camera
    private int defaultCameraId;
    private Bitmap bm = null;//拿到的图片数据
    private float mDensity = 2.0f;

    private String mTakePicPath = null;// 拍照图片路径
    private static final String DIR_STORAGE = Environment
            .getExternalStorageDirectory().getAbsolutePath();
    private static final String DIR_ICR = DIR_STORAGE + "/IDCardSDKCaller/";
    String copyfilename = null;
    // 0701
    /**
     * 用于引擎指针，用于辅助检测切边区域是否合法
     */

    private int mEngineContext;
    private DetectThread mDetectThread = null;
    private int type;
    ScannerSDK mScannerSDK;
    RelativeLayout rootView;

    String cameraPathString = "";

    @SuppressLint("NewApi")
    public static boolean checkDeviceHasNavigationBar(Context activity) {

        // 通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey || !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mDensity = getResources().getDisplayMetrics().density;

        /*************************** set a SurfaceView as the content of our activity.******START ***********************/
        CollectInfoInstance.getInstance(this);
        // 初始化目录
        File dir = new File(DIR_ICR);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
        }
        if (mTakePicPath == null) {
            mTakePicPath = DIR_ICR + "card.jpg";
        }
        copyfilename = DIR_ICR + "uploadcard.jpg";
        mPreview = new Preview(this);
        float dentisy = getResources().getDisplayMetrics().density;
        RelativeLayout root = new RelativeLayout(this);
        root.setBackgroundColor(Color.TRANSPARENT);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.bottomMargin = (int) (100 * dentisy);
        lp.topMargin = (int) (30 * dentisy);
        root.addView(mPreview, lp);
        lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

        setContentView(root);
        rootView = root;
        // 初始化预览界面左边按钮组
//		initButtonGroup();
        /*************************** Find the ID of the default camera******END ***********************/

        /*************************** Find the ID of the default camera******START ***********************/
        // Find the total number of cameras available
        numberOfCameras = Camera.getNumberOfCameras();
        // Find the ID of the default camera
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
                defaultCameraId = i;
            }
        }
        /*************************** Find the ID of the default camera******END ***********************/

        /*************************** Add mPreview Touch Listener******START ***********************/

        mPreview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mCamera != null) {
                    mCamera.autoFocus(null);
                }
                return false;
            }
        });
        /*************************** Add mPreview Touch Listener******END ***********************/
        cameraPathString = getIntent().getStringExtra(EXTRA_KEY_IMG_CAMERA_PATH);
        /*************************** init recog appkey ******START ***********************/
        mScannerSDK = new ScannerSDK();
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {

                int ret = mScannerSDK.initSDK(PictureUploadActivity.this, APPKEY);
                mEngineContext = mScannerSDK.initThreadContext();
                int id = mEngineContext;
                Log.e("zw", "ret-----:" + ret);
                //Toast.makeText(getApplicationContext(), "ret---"+ret, 2000);
                return ret;
            }

            @Override
            protected void onPostExecute(Integer result) {
                //Toast.makeText(getApplicationContext(), "result---"+result, 2000);
                Log.e("zw", "result-----:" + result);
                if (result != 0) {
                    /**
                     * 101 包名错误, 授权APP_KEY与绑定的APP包名不匹配； 102
                     * appKey错误，传递的APP_KEY填写错误； 103 超过时间限制，授权的APP_KEY超出使用时间限制；
                     * 104 达到设备上限，授权的APP_KEY使用设备数量达到限制； 201
                     * 签名错误，授权的APP_KEY与绑定的APP签名不匹配； 202 其他错误，其他未知错误，比如初始化有问题；
                     * 203 服务器错误，第一次联网验证时，因服务器问题，没有验证通过； 204
                     * 网络错误，第一次联网验证时，没有网络连接，导致没有验证通过； 205
                     * 包名/签名错误，授权的APP_KEY与绑定的APP包名和签名都不匹配；
                     */
                    new AlertDialog.Builder(PictureUploadActivity.this)
                            .setMessage("Error：" + result)
                            .setNegativeButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            finish();
                                        }
                                    }).setCancelable(false).create().show();
                }
            }
        }.execute();

    }

    boolean mNeedInitCameraInResume = false;

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mCamera = Camera.open(defaultCameraId);// open the default camera
        } catch (Exception e) {
            e.printStackTrace();
            showFailedDialogAndFinish();
            return;
        }
        /********************************* preview是自定义的viewgroup 继承了surfaceview,将相机和surfaceview 通过holder关联 ***********************/
        mPreview.setCamera(mCamera);
        /********************************* 设置显示的图片和预览角度一致 ***********************/
        setDisplayOrientation();
        try {
            /********************************* 对surfaceview的PreviewCallback的 callback监听，回调onPreviewFrame ***********************/
            mCamera.setOneShotPreviewCallback(this);
        } catch (Exception e) {
            e.printStackTrace();

        }
        /*************************** 当按power键后,再回到程序,surface 不会调用created/changed,所以需要主动初始化相机参数******START ***********************/
        if (mNeedInitCameraInResume) {
            mPreview.surfaceCreated(mPreview.mHolder);
            mPreview.surfaceChanged(mPreview.mHolder, 0,
                    mPreview.mSurfaceView.getWidth(),
                    mPreview.mSurfaceView.getHeight());
            mHandler.sendEmptyMessageDelayed(100, 100);
        }
        mNeedInitCameraInResume = true;
        /********************************* END ***********************/

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Because the Camera object is a shared resource, it's very
        // important to release it when the activity is paused.
        if (mCamera != null) {
            Camera camera = mCamera;
            mCamera = null;
            camera.setOneShotPreviewCallback(null);
            mPreview.setCamera(null);
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        mHandler.removeMessages(MSG_AUTO_FOCUS);

        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }


    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Size size = camera.getParameters().getPreviewSize();
        starttime = System.currentTimeMillis();
        if (mDetectThread == null) {
            mDetectThread = new DetectThread();
            mDetectThread.start();
            /********************************* 自动对焦的核心 启动handler 来进行循环对焦 ***********************/
            mHandler.sendEmptyMessageDelayed(MSG_AUTO_FOCUS, 100);
        }
        /********************************* 向预览线程队列中 加入预览的 data 分析是否ismatch ***********************/
        mDetectThread.addDetect(data, size.width, size.height);
    }

    private void showFailedDialogAndFinish() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.fail_to_contect_camcard)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                finish();
                            }
                        }).create().show();
    }

    private void resumePreviewCallback() {
        if (mCamera != null) {
            mCamera.setOneShotPreviewCallback(this);
        }
    }

    /**
     * 功能：将显示的照片和预览的方向一致
     */
    private void setDisplayOrientation() {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(defaultCameraId, info);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result = (info.orientation - degrees + 360) % 360;
        mCamera.setDisplayOrientation(result);
        /**
         * 注释原因：因为FOCUS_MODE_CONTINUOUS_PICTURE 不一定兼容所有手机
         * 小米4华为mate8对焦有问题，现在考虑用定时器来实现自动对焦
         */

    }

    public boolean isSupported(String value, List<String> supported) {
        return supported == null ? false : supported.indexOf(value) >= 0;
    }

    private static final int MSG_AUTO_FOCUS = 100;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == MSG_AUTO_FOCUS) {
                autoFocus();
            }
            if (msg.what == RESULT_OK) {
                doRecogWork(null);
                onNext();
            }
        }

        ;
    };

    @SuppressLint("HandlerLeak")
    private void doRecogWork(final String imgPath) {
        if (camerabyte == null) {
            bm = loadBitmap(imgPath);
        } else {
            bm = loadBitmap(camerabyte);
            camerabyte = null;
        }
        Log.e("zw", "拿到的数据照片------" + bm);
    }

    public static Bitmap loadBitmap(byte[] pathName) {
        return loadBitmap(pathName, 720, 1280);
    }

    public static Bitmap loadBitmap(String pathName) {
        return loadBitmap(pathName, 720, 1280);
    }

    public static Bitmap loadBitmap(byte[] data, float ww, float hh) {
        Bitmap b = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            b = BitmapFactory.decodeByteArray(data, 0, data.length, opts);

            int originalWidth = opts.outWidth;
            int originalHeight = opts.outHeight;

            // float hh = 1280f;// 这里设置高度为800f
            // float ww = 720f;// 这里设置宽度为480f
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;// be=1表示不缩放
            if (originalWidth > originalHeight && originalWidth > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (originalWidth / ww);
            } else if (originalWidth < originalHeight && originalHeight > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (originalHeight / hh);
            }
            if (be <= 0)
                be = 1;
            Log.d("decodeFile", "originalWidth:" + originalWidth
                    + ",originalHeight:" + originalHeight + ",be:" + be);

            BitmapFactory.Options optso = new BitmapFactory.Options();
            optso.inJustDecodeBounds = false;
            optso.inPreferredConfig = Bitmap.Config.RGB_565;
            optso.inSampleSize = be;
            b = BitmapFactory.decodeByteArray(data, 0, data.length, optso);
        } catch (Exception e) {
            e.printStackTrace();
            b = null;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            b = null;
        }
        return b;
    }

    public static Bitmap loadBitmap(String pathName, float ww, float hh) {
        Bitmap b = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            b = BitmapFactory.decodeFile(pathName, opts);

            int originalWidth = opts.outWidth;
            int originalHeight = opts.outHeight;

            // float hh = 1280f;// 这里设置高度为800f
            // float ww = 720f;// 这里设置宽度为480f
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;// be=1表示不缩放
            if (originalWidth > originalHeight && originalWidth > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (originalWidth / ww);
            } else if (originalWidth < originalHeight && originalHeight > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (originalHeight / hh);
            }
            if (be <= 0)
                be = 1;
            Log.d("decodeFile", "originalWidth:" + originalWidth
                    + ",originalHeight:" + originalHeight + ",be:" + be);

            BitmapFactory.Options optso = new BitmapFactory.Options();
            optso.inJustDecodeBounds = false;
            optso.inPreferredConfig = Bitmap.Config.RGB_565;
            optso.inSampleSize = be;
            b = BitmapFactory.decodeFile(pathName, optso);
        } catch (Exception e) {
            e.printStackTrace();
            b = null;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            b = null;
        }
        return b;
    }

    private void onNext() {
        //经过质量检测的身份证翻拍照
        CollectInfoInstance.getInstance().setOcrfrontId(
                BmpUtil.Bitmap2Bytes(bm));
        bm = MiniBitmap.resizeBitmap(bm, 640);
        //压缩后的身份证翻拍照
        Toast.makeText(getApplicationContext(), "拿到照片", 2000).show();
        CollectInfoInstance.getInstance().setFrontId(BmpUtil.Bitmap2Bytes(bm));
        Frame.HANDLES.sentAll("FrgShenfenRezhengNew", type, bm);
        finish();

    }

    private void autoFocus() {
        if (mCamera != null) {
            try {
                mCamera.autoFocus(focusCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    boolean isFocus = false;

    /**
     * 功能：对焦后的回调，每次返回bool值，如果对焦成功延时2秒对焦，如果失败继续对焦
     */
    @SuppressWarnings("deprecation")
    AutoFocusCallback focusCallback = new AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            Log.d("lz", "success==" + success);

            if (success) {
                if (camera != null) {
                    isFocus = true;

                }
            } else {
                if (camera != null) {
                    isFocus = false;
                    // mHandler.sendEmptyMessage(MSG_AUTO_FOCUS);
                    // Log.d("lz", "isFocus==" + isFocus);

                }

            }
            if (starttime - endtime > 1000)
                mHandler.sendEmptyMessageDelayed(MSG_AUTO_FOCUS, 1500L);

        }
    };

    boolean isVertical = false;

    /**
     * @param newWidth
     * @param newHeight
     * @return
     * @CN: 功能：根据当前屏幕的方向还有 宽高，还有证件的比例比如身份证高宽比 0.618来算出
     * 预览框的位置和大小，可以更改此处来更改预览框的方向位置还有大小
     * @EN :Function: according to the current screen orientation and high
     * width, and the proportion of documents such as identity card with
     * high aspect ratio the size and location of the 0.618 to calculate the
     * preview box, you can change here to change the direction of the
     * location and size of the preview box
     */
    public Map<String, Float> getPositionWithArea(int newWidth, int newHeight,
                                                  float scaleW, float scaleH) {
        float left, top, right, bottom;
        Map<String, Float> map = new HashMap<String, Float>();
        if (isVertical) {// vertical
            float dis = 1 / 20f;
            left = newWidth * dis;
            right = newWidth - left;
            top = 200f * scaleH;
            bottom = top + (newWidth - left - left) * 0.618f;
            /**
             * 注解部分是：竖直模式居中，如果是竖直模式相对位置 则需要注意 不同分辨率手机的缩放比例
             */
            // top = (newHeight - (newWidth - left - left) * 0.618f) / 2;
            // bottom = newHeight - top;
        } else {// horizental
            float dis = 1 / 6f;// 10

            left = newWidth * dis;
            right = newWidth - left;
            /**
             * 注解部分是：横向模式居中，如果是竖相对位置 则需要注意 不同分辨率手机的缩放比例
             */
            top = (newHeight - (newWidth - left - left) / 0.618f) / 2;
            bottom = newHeight - top;

        }
        map.put("left", left);
        map.put("right", right);
        map.put("top", top);
        map.put("bottom", bottom);
        return map;

    }

    // thread to detect and recognize.
    /**
     * 功能：将每一次预览的data 存入ArrayBlockingQueue 队列中，然后依次进行ismatch的验证，如果匹配就会就会进行进一步的识别
     * 注意点： 1.其中 控制预览框的位置大小，需要
     */
    int ivalue = 0;

    /****************************
     * 预览框 远近判断 晃动判断 声音间隔时间参数 START
     *********************************************/
    long voiceStart = 0;
    long voiceEnd = 0;
    int continue_match_time = 0;

    int continue_match_time_smaller = 0;
    int continue_match_time_bigger = 0;

    float mScale;

    /**************************** 预览框 远近判断 晃动判断 声音间隔时间参数 END *********************************************/
    /**
     * 临时储存
     *
     * @param bitmap
     * @throws IOException
     */
    public String saveTemp(Bitmap bitmap) {

        File file = new File(cameraPathString);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            if (!bitmap.isRecycled()) {
                bitmap.recycle();   //回收图片所占的内存
                System.gc();  //提醒系统及时回收
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return cameraPathString;
    }

    PictureCallback pictureCallback = new PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            if (mScannerSDK != null) {
                mScannerSDK.destroyContext(mEngineContext);

            }
            camerabyte = data;
            mHandler.sendEmptyMessage(RESULT_OK);
        }
    };
    ToneGenerator tone;

    public void takepictrueCameraTake() {
        mCamera.autoFocus(new AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean arg0, Camera arg1) {
                // TODO Auto-generated method stub
//				if(arg0){
                Log.d("match", "arg0:" + arg0);

                mCamera.takePicture(new ShutterCallback() {
                    @Override
                    public void onShutter() {
                        if (tone == null) {
//							 发出提示用户的声音
                            tone = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
                        }
                        tone.startTone(ToneGenerator.TONE_PROP_BEEP);
                    }
                }, null, pictureCallback);

//				}

            }
        });
//		resumePreviewCallback();
    }

    int datacount = 0;

    /**
     * A simple wrapper around a Camera and a SurfaceView that renders a
     * centered preview of the Camera to the surface. We need to center the
     * SurfaceView because not all devices have cameras that support preview
     * sizes at the same aspect ratio as the device's display.
     */
    private class Preview extends ViewGroup implements SurfaceHolder.Callback {
        private final String TAG = "Preview";
        private SurfaceView mSurfaceView = null;//视频播放view
        private SurfaceHolder mHolder = null;
        private Size mPreviewSize = null;
        private List<Size> mSupportedPreviewSizes = null;
        private Camera mCamera = null;
        private DetectView mDetectView = null;//拍照边框View
        private TextView mInfoView = null;
        private TextView mCopyRight = null;
        ;

        // private ImageView imageView;
        public Preview(Context context) {
            super(context);
            /*********************************
             * 自定义viewgrop上添加SurfaceView 然后对应的其他ui DetectView是自定义的预览框
             *
             * ***********************/

            mSurfaceView = new SurfaceView(context);
            addView(mSurfaceView);

            mInfoView = new TextView(context);
            addView(mInfoView);

            mDetectView = new DetectView(context);
            addView(mDetectView);


            mHolder = mSurfaceView.getHolder();
            mHolder.addCallback(this);
        }

        public void setCamera(Camera camera) {
            mCamera = camera;
            if (mCamera != null) {
                mSupportedPreviewSizes = mCamera.getParameters()
                        .getSupportedPreviewSizes();
                requestLayout();
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            // We purposely disregard child measurements because act as a
            // wrapper to a SurfaceView that centers the camera preview instead
            // of stretching it.
            final int width = resolveSize(getSuggestedMinimumWidth(),
                    widthMeasureSpec);
            final int height = resolveSize(getSuggestedMinimumHeight(),
                    heightMeasureSpec);
            setMeasuredDimension(width, height);
            Log.e(TAG, "xxxx onMesaure " + width + " " + height);
            if (mSupportedPreviewSizes != null) {
                int targetHeight = 720;
                if (width > targetHeight && width <= 1080)
                    targetHeight = width;
                mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes,
                        height, width, targetHeight);// 竖屏模式，寬高颠倒

                Log.e(TAG, "xxxx mPreviewSize " + mPreviewSize.width + " "
                        + mPreviewSize.height);
            }
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            if (changed && getChildCount() > 0) {
                final View child = getChildAt(0);

                final int width = r - l;
                final int height = b - t;

                int previewWidth = width;
                int previewHeight = height;
                if (mPreviewSize != null) {
                    previewWidth = mPreviewSize.height;
                    previewHeight = mPreviewSize.width;
                }

                // Center the child SurfaceView within the parent.
                if (width * previewHeight > height * previewWidth) {
                    final int scaledChildWidth = previewWidth * height
                            / previewHeight;
                    child.layout((width - scaledChildWidth) / 2, 0,
                            (width + scaledChildWidth) / 2, height);
                    mDetectView.layout((width - scaledChildWidth) / 2, 0,
                            (width + scaledChildWidth) / 2, height);
                } else {
                    final int scaledChildHeight = previewHeight * width
                            / previewWidth;
                    child.layout(0, (height - scaledChildHeight) / 2, width,
                            (height + scaledChildHeight) / 2);
                    mDetectView.layout(0, (height - scaledChildHeight) / 2,
                            width, (height + scaledChildHeight) / 2);
                }
                getChildAt(1).layout(l, t, r, b);

            }
        }

        public void surfaceCreated(SurfaceHolder holder) {
            // The Surface has been created, acquire the camera and tell it
            // where to draw.
            try {
                if (mCamera != null) {
                    mCamera.setPreviewDisplay(holder);
                }
            } catch (IOException exception) {
                Log.e(TAG, "IOException caused by setPreviewDisplay()",
                        exception);
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // Surface will be destroyed when we return, so stop the preview.
            if (mCamera != null) {
                mCamera.stopPreview();
            }
        }

        private Size getOptimalPreviewSize(List<Size> sizes, int w, int h,
                                           int targetHeight) {
            final double ASPECT_TOLERANCE = 0.2;
            double targetRatio = (double) w / h;
            if (sizes == null)
                return null;
            Size optimalSize = null;
            double minDiff = Double.MAX_VALUE;

            // Try to find an size match aspect ratio and size
            for (Size size : sizes) {
                double ratio = (double) size.width / size.height;
                if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                    continue;
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }

            // Cannot find the one match the aspect ratio, ignore the
            // requirement
            if (optimalSize == null) {
                minDiff = Double.MAX_VALUE;
                for (Size size : sizes) {
                    if (Math.abs(size.height - targetHeight) < minDiff) {
                        optimalSize = size;
                        minDiff = Math.abs(size.height - targetHeight);
                    }
                }
            }
            return optimalSize;
        }

        private Size getOptimalPictureSize(List<Size> sizes) {

            Size optimalSize = null;
            double minDiff = Double.MAX_VALUE;

            // Try to find an size match aspect ratio and size
            for (Size size : sizes) {
                if (optimalSize == null) {
                    optimalSize = size;
                }
                Log.e(TAG, "getOptimalPictureSize:" + size.width + ",height:" + size.height
                );

                if (optimalSize.width <= size.width && optimalSize.height <= size.height)
                    optimalSize = size;
            }
            return optimalSize;
        }


        public void surfaceChanged(SurfaceHolder holder, int format, int w,
                                   int h) {
            if (mCamera != null) {
                // Now that the size is known, set up the camera parameters and
                // begin the preview.
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setPreviewSize(mPreviewSize.width,
                        mPreviewSize.height);
                parameters.setPreviewFormat(ImageFormat.NV21);

                parameters.setPictureFormat(PixelFormat.JPEG); // 设置图片格式
                requestLayout();
                mDetectView.setPreviewSize(mPreviewSize.width,
                        mPreviewSize.height);

                List<Size> mSupportedPictureSizes = mCamera.getParameters()
                        .getSupportedPictureSizes();

                Size picSize = getOptimalPictureSize(mSupportedPictureSizes);
                Log.e(TAG, "picSize:width:" + picSize.width + ",height:" + picSize.height
                );
                parameters.setPictureSize(picSize.width,
                        picSize.height); // 设置保存的图片尺寸

                parameters.setJpegQuality(100); // 设置照片质量

                mCamera.setParameters(parameters);
                Size preSize = parameters.getPreviewSize();

                mScale = picSize.width / (float) preSize.width;
                mCamera.startPreview();
            }
        }

        public void showBorder(int[] border, boolean match) {
            mDetectView.showBorder(border, match);
        }
    }

    /**
     * 边框View
     */

    private class DetectView extends View {
        private Paint paint = null;
        private int[] border = null;
        private String result = null;
        private boolean match = false;
        public int previewWidth;
        public int previewHeight;
        private int mColorNormal = 0xff01d2ff;
        private int mColorMatch = 0xff005bd7;

        // 蒙层位置路径
        Path mClipPath = new Path();
        public RectF mClipRect = new RectF();//边框的具体位置
        float mRadius = 12;
        int mRectWidth = 9;
        float cornerSize = 80;// 4个角的大小
        float cornerStrokeWidth = 8;

        public void showBorder(int[] border, boolean match) {
            this.border = border;
            this.match = match;
            postInvalidate();
        }

        public DetectView(Context context) {
            super(context);
            paint = new Paint();
            paint.setColor(0xffff0000);
        }

        public void setPreviewSize(int width, int height) {
            this.previewWidth = width;
            this.previewHeight = height;
        }

        // 计算蒙层位置
        @SuppressLint("NewApi")
        public void upateClipRegion(float scale, float scaleH) {
            float left, top, right, bottom;
            float density = getResources().getDisplayMetrics().density;
            mRadius = 0;
            mRectWidth = (int) (9 * density);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }

            cornerStrokeWidth = 4 * density;

            Map<String, Float> map = getPositionWithArea(getWidth(),
                    getHeight(), scale, scaleH);
            left = map.get("left");
            right = map.get("right");
            top = map.get("top");
            bottom = map.get("bottom");
            Log.e("upateClipRegion ", getWidth() + ",getHeight():"
                    + getHeight());

            Log.e("upateClipRegion ", left + ",right:" + right + ",top:" + top
                    + ",bottom:" + bottom);
            topMarginValue = (int) top;
            mClipPath.reset();
            mClipRect.set(left, top, right, bottom);
            mClipPath.addRoundRect(mClipRect, mRadius, mRadius, Direction.CW);


        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            float scaleW;
            float scaleH;

            scaleW = getWidth() / (float) previewHeight;
            scaleH = getHeight() / (float) previewWidth;
            upateClipRegion(scaleW, scaleH);
            addTipsView("请将证件放入预览框");

        }

        boolean boolUpdateClip = true;// 保证没有正反面业务时只会更新一次
        boolean boolServiceUpdateClip = false;

        @Override
        public void onDraw(Canvas c) {

            /**
             * 绘制 灰色蒙层
             */
            // upateClipRegion(scaleW, scaleH);
//			c.save();
//			c.clipPath(mClipPath, Region.Op.DIFFERENCE);
//			c.drawColor(0xAA666666);
//			c.drawRoundRect(mClipRect, mRadius, mRadius, paint);
//			c.restore();
            /********************************
             * START*************************************
             * 绘制预览框的四个角，根据预览是否匹配改变角的颜色
             */
            if (match) {// 设置颜色
                paint.setColor(mColorMatch);
            } else {
                paint.setColor(mColorNormal);
            }
            float len = cornerSize;
            float strokeWidth = cornerStrokeWidth;
//			paint.setStrokeWidth(strokeWidth);
//			// 左上
//			c.drawLine(mClipRect.left - strokeWidth / 2, mClipRect.top,
//					mClipRect.left + len, mClipRect.top, paint);
//			c.drawLine(mClipRect.left, mClipRect.top, mClipRect.left,
//					mClipRect.top + len, paint);
//			// 右上
//			c.drawLine(mClipRect.right - len, mClipRect.top, mClipRect.right
//					+ strokeWidth / 2, mClipRect.top, paint);
//			c.drawLine(mClipRect.right, mClipRect.top, mClipRect.right,
//					mClipRect.top + len, paint);
//			// 右下
//			c.drawLine(mClipRect.right - len, mClipRect.bottom, mClipRect.right
//					+ strokeWidth / 2, mClipRect.bottom, paint);
//			c.drawLine(mClipRect.right, mClipRect.bottom - len,
//					mClipRect.right, mClipRect.bottom, paint);
//			// 左下
//			c.drawLine(mClipRect.left - strokeWidth / 2, mClipRect.bottom,
//					mClipRect.left + len, mClipRect.bottom, paint);
//			c.drawLine(mClipRect.left, mClipRect.bottom - len, mClipRect.left,
//					mClipRect.bottom, paint);


            /********************************
             * END************************************* 绘制预览框的四个角，根据预览是否匹配改变角的颜色
             */

            /********************************
             * START************************************* 绘制预览证件的提示边框
             */


            /********************************
             * END************************************* 绘制预览证件的提示边框
             */
        }
    }

    /*********************************
     * 预览框的UI自定义 START
     *********************************************/

    void setImgFrame() {
        //添加拍照框
        ImageView frameImgView = new ImageView(this);
        InputStream is = getResources().openRawResource(type == 1 ? R.drawable.camera_idcard_front : R.drawable.camera_idcard_back);
        Bitmap bmp = BitmapFactory.decodeStream(is);
        frameImgView.setImageBitmap(bmp);

        RelativeLayout.LayoutParams lp = null;
        lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        lp.width = (int) mPreview.mDetectView.mClipRect.width();
        lp.height = (int) mPreview.mDetectView.mClipRect.height();

        frameImgView.setAdjustViewBounds(true);
        frameImgView.setScaleType(ImageView.ScaleType.FIT_XY);
        frameImgView.setX(mPreview.mDetectView.mClipRect.left);
        frameImgView.setY(topMarginValue + mPreview.mDetectView.mClipRect.top);
        rootView.addView(frameImgView, lp);


        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();//屏幕的宽
        int height = wm.getDefaultDisplay().getHeight();//屏幕的高
        int margin = width / 20;//阴影和框之间的间隔
        //添加阴影遮挡
        View topView = new View(this);
        topView.setBackgroundColor(Color.BLACK);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        topView.setX(0);
        topView.setY(0);
        lp2.width = (int) width;
        lp2.height = (int) topMarginValue + (int) mPreview.mDetectView.mClipRect.top - margin;
        rootView.addView(topView, lp2);

        View bottomView = new View(this);
        bottomView.setBackgroundColor(Color.BLACK);
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        bottomView.setX(0);
        bottomView.setY(topMarginValue + mPreview.mDetectView.mClipRect.top +
                mPreview.mDetectView.mClipRect.height() + margin);
        lp3.width = (int) width;
        lp3.height = (int) height - (topMarginValue + (int) mPreview.mDetectView.mClipRect.top) -
                (int) mPreview.mDetectView.mClipRect.height() - margin;
        rootView.addView(bottomView, lp3);

        View leftView = new View(this);
        leftView.setBackgroundColor(Color.BLACK);
        RelativeLayout.LayoutParams lp4 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        leftView.setX(0);
        leftView.setY(topView.getHeight());
        lp4.width = (int) (width - mPreview.mDetectView.mClipRect.width()) / 2 - margin;
        lp4.height = (int) height - topView.getHeight() - bottomView.getHeight();
        rootView.addView(leftView, lp4);

        View rightView = new View(this);
        rightView.setBackgroundColor(Color.BLACK);
        RelativeLayout.LayoutParams lp5 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rightView.setX(mPreview.mDetectView.mClipRect.left + mPreview.mDetectView.mClipRect.width() + margin);
        rightView.setY(topView.getHeight());
        lp5.width = (int) (width - mPreview.mDetectView.mClipRect.width()) / 2 - margin;
        lp5.height = (int) height - topView.getHeight() - bottomView.getHeight();
        rootView.addView(rightView, lp5);

        initButtonGroup(bottomView.getHeight());
    }

    int topMarginValue = 0;
    TextView tips = null;

    boolean mHasAddTips = false;

    void addTipsView(String mTips) {

//		changeTipsView(mTips);//****可删

        setImgFrame();

    }

    String commentTipsString = "请将A4纸放入预览框自动拍摄";

    @SuppressLint("NewApi")
    void changeTipsView(String mTips) {

        int topMargin = topMarginValue;
        if (mHasAddTips) {
            tips.setText(mTips);
            return;
        }
        tips = new TextView(this);
        tips.setTextSize(16);
        tips.setTextColor(Color.WHITE);
        tips.setText(mTips);

        RelativeLayout.LayoutParams lp = null;
        float density = 0;
        if (!isVertical
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            /********************************* 横向模式UI预览框的注释UI布局 都是动态布局 ***********************/

            int width = getResources().getDisplayMetrics().widthPixels;
            density = getResources().getDisplayMetrics().density;
            /********************************* 预览框title ***********************/

            lp = new RelativeLayout.LayoutParams(width,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.rightMargin = (int) (20 * density) - width / 2;
            tips.setRotation(90);
            tips.setGravity(Gravity.CENTER);
            lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

        } else {
            lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            tips.setGravity(Gravity.CENTER_HORIZONTAL);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            lp.topMargin = (int) (topMargin - 32 * getResources()
                    .getDisplayMetrics().density);
        }
        rootView.addView(tips, lp);

        mHasAddTips = true;
    }

    public int getDpValue(int spvalue) {
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, spvalue, getResources().getDisplayMetrics()));
    }

    /**
     * 按钮
     * initButtonGroup中的添加方式
     */
    private void initButtonGroup(int bottomH) {


        // **********************************添加动态的布局
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.camera, null);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);


        ImageView take_photo_id = (ImageView) view.findViewById(R.id.take_photo_id);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        lp.bottomMargin = (bottomH - take_photo_id.getHeight()) / 2;//设置距离下方距离
        take_photo_id.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;

                    case MotionEvent.ACTION_UP:

                        takepictrueCameraTake();

                        break;

                    default:
                        break;
                }
                return true;
            }
        });

        rootView.addView(view, lp);
    }

    long starttime = 0;
    long endtime = 0;

    private class DetectThread extends Thread {
        private ArrayBlockingQueue<byte[]> mPreviewQueue = new ArrayBlockingQueue<byte[]>(
                1);
        private int width;
        private int height;


        public void stopRun() {
            addDetect(new byte[]{0}, -1, -1);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    byte[] data = mPreviewQueue.take();// block here, if no data
                    // in the queue.
                    if (data.length == 1) {// quit the thread, if we got special
                        // byte array put by stopRun().
                        return;
                    }
                    float left, top, right, bottom;
                    int newWidth = height;
                    int newHeight = width;

                    Map<String, Float> map = getPositionWithArea(newWidth,
                            newHeight, 1, 1);
                    left = map.get("left");
                    right = map.get("right");
                    top = map.get("top");
                    bottom = map.get("bottom");

                    // the (left, top, right, bottom) is base on preview image's
                    // coordinate. that's different with ui coordinate.
                    /********************************* 通过底册api 将预览的数据 还有证件的坐标位置 获取当前一帧证件的4个点坐标的数组 ***********************/
                    // int[] out = mIDCardScanSDK.detectBorder(data, width,
                    // height, (int) top, (int) (height - right),
                    // (int) bottom, (int) (height - left));

                    int[] out = mScannerSDK.detectBorderYuv(mEngineContext,
                            data, ScannerEngine.COLOR_FORMAT_YUV_Y, width, height);

                    // if activity is port mode then (x,y)->(preview.height-y,x)
                    if (out != null) {// find border
                        Log.d("DetectCard", "DetectCard >>>>>>>>>>>>> "
                                + Arrays.toString(out));
                        for (int i = 0; i < 4; i++) {
                            int tmp = out[0 + i * 2];
                            out[0 + i * 2] = height - out[1 + i * 2];
                            out[1 + i * 2] = tmp;
                        }

                        boolean match = false;

                        match = isMatch((int) left, (int) top, (int) right,
                                (int) bottom, out);
                        /********************************* 实时画出预览 证件的虚拟边框，用来辅助 将证件 与预览框重合 更好识别 ***********************/


                        mPreview.showBorder(out, match);

                        /********************************* 当前预览帧的 证件四个点的坐标 和 预览框的证件4个点的坐标 校验，在一定范围内认定校验成功 ***********************/

                        starttime = System.currentTimeMillis();
                        Log.d("match", "match:" + match + ",isFocus" + isFocus);
                        if (match && starttime - endtime > 1000) {
                            // 自动拍摄

                            takepictrueCameraTake();

                            endtime = System.currentTimeMillis();

                        }


                        // if (match && isFocus) { // get matched border
                        // ResultData result = mIDCardScanSDK.recognize(data,
                        // width, height, mImageFolder);
                        //
                        // if(result != null){
                        //
                        //
                        // showResult(result);
                        // return;
                        // }
                        // }
                    } else {// no find border, continue to preview;
                        mPreview.showBorder(null, false);
                    }
                    // continue to preview;
                    resumePreviewCallback();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 当前预览帧的 证件四个点的坐标 和 预览框的证件4个点的坐标 校验，在一定范围内认定校验成功
         * 注意点：其中120是多次验证的值，没有其他理由校验比较稳定，这个值可以自己尝试改变 注意：判断远近 稍有误差 可以作为适当辅助预览
         *
         * @param left
         * @param top
         * @param right
         * @param bottom
         * @param qua
         * @return
         */

        int biggerNumSum = 0;
        int smallerNumSum = 0;
        int countSum = 0;

        public boolean isMatch(int left, int top, int right, int bottom,
                               int[] qua) {
            int dif = 75;
            int num = 0;

            if (Math.abs(left - qua[6]) < dif && Math.abs(top - qua[7]) < dif) {
                num++;
            }
            if (Math.abs(right - qua[0]) < dif && Math.abs(top - qua[1]) < dif) {
                num++;
            }
            if (Math.abs(right - qua[2]) < dif
                    && Math.abs(bottom - qua[3]) < dif) {
                num++;
            }
            if (Math.abs(left - qua[4]) < dif
                    && Math.abs(bottom - qua[5]) < dif) {
                num++;
            }
//			Log.e("aaaaaaaaaa", "inside " + Arrays.toString(qua));
            Log.e("aaaaaaaaaa", left + ", " + top + ", " + right + ", "
                    + bottom);
            if (num > 2) {
                continue_match_time++;
                if (continue_match_time >= 1)
                    return true;
            } else {
                continue_match_time = 0;
            }
            return false;
        }

        int continue_match_timeShow = 0;

        public boolean isMatchShow(int left, int top, int right, int bottom,
                                   int[] qua) {
            int dif = 320;
            int num = 0;

            if (Math.abs(left - qua[6]) < dif && Math.abs(top - qua[7]) < dif) {
                num++;
            }
            if (Math.abs(right - qua[0]) < dif && Math.abs(top - qua[1]) < dif) {
                num++;
            }
            if (Math.abs(right - qua[2]) < dif
                    && Math.abs(bottom - qua[3]) < dif) {
                num++;
            }
            if (Math.abs(left - qua[4]) < dif
                    && Math.abs(bottom - qua[5]) < dif) {
                num++;
            }
            Log.e("aaaaaaaaaa", "inside " + Arrays.toString(qua));
            Log.e("aaaaaaaaaa", left + ", " + top + ", " + right + ", "
                    + bottom);

            if (Math.abs(qua[5] - qua[7]) - Math.abs(qua[0] - qua[6]) > 100) {

                if (num > 2) {
                    continue_match_timeShow++;
                    if (continue_match_timeShow >= 1)
                        return true;
                } else {
                    continue_match_timeShow = 0;
                }
            }
            return false;
        }

        public void addDetect(byte[] data, int width, int height) {
            if (mPreviewQueue.size() == 1) {
                mPreviewQueue.clear();
            }
            mPreviewQueue.add(data);
            this.width = width;
            this.height = height;
        }

    }
}
