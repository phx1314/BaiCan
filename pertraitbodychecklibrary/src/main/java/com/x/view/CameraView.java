package com.x.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.x.util.CameraUtil;
import com.x.util.CameraUtil.Degree;
import com.x.util.DisplayUtil;

import java.util.List;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback,
		Camera.ErrorCallback {

	public static final String TAG = "CameraPreview";
	// 压缩尺寸
	public static final int IMG_SCALE_WIDTH = 480;// 以240宽度为基准
	public static final int IMG_SCALE_HEIGHT = 640;// 20160518改为宽高中大的为基准
	public static final float IMG_SCALE = 480.0f;

	// 预览最大值
	public static final float MAX_SO_PHOTO_SUPPORT_SIZE = 1000;
	// 拍照尺寸
	public static final int IMG_MAX_HEIGHT = 640;
	public static final int IMG_MAX_WIDTH = 480;

	public CameraView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);// ->
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 已过期的设置，但版本低于3.0的Android还需要
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasWindowFocus);
		Log.i(TAG, "CameraView onWindowFocusChanged===" + hasWindowFocus);
		if (hasWindowFocus) {
			if (mCamera == null)
				surfaceCreated(mSurfaceHolder);
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// surface已被创建，现在把预览画面的位置通知摄像头
		Log.i(TAG, "surfaceCreated");
		if (!CameraUtil.checkCameraHardware(this.getContext())) {
			Toast.makeText(this.getContext(), "摄像头不存在，请先安装！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		// 创建Camera实例
		if (mCamera == null) {
			mCamera = getCameraInstance(firstCameraId);
		}
		if (mCamera == null) {
			Toast.makeText(this.getContext(), "摄像头不可用！", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		try {
			setCameraParams();
			mCamera.setErrorCallback(this);
			mCamera.setPreviewDisplay(holder);
			mCamera.setPreviewCallback(mPreviewCallback);
			mCamera.startPreview();
			setPreviewing(true);

			setVisibility(View.VISIBLE);//###***恢复显示，防止闪烁
		} catch (Exception e) {
			Log.d(TAG, "Error setting camera preview: " + e.getMessage());
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {
		// TODO Auto-generated method stub
		// 如果预览无法更改或旋转，注意此处的事件
		// 确保在缩放或重排时停止预览
		// 更改时停止预览
		Log.i(TAG, "surfaceChanged width:" + width + " height:" + height);
		if (holder.getSurface() == null) {
			// 预览surface不存在
			return;
		}
		try {
			mCamera.setPreviewCallback(null);
			setPreviewing(false);
			mCamera.stopPreview();
		} catch (Exception e) {
			// 忽略：试图停止不存在的预览
		}

		// 以新的设置启动预览
		try {
			mCamera.setErrorCallback(this);
			mCamera.setPreviewDisplay(holder);
			mCamera.setPreviewCallback(mPreviewCallback);
			mCamera.startPreview();
			setPreviewing(true);

			setVisibility(View.VISIBLE);//###***恢复显示，防止闪烁

		} catch (Exception e) {
			Log.d(TAG, "Error starting camera preview: " + e.getMessage());
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// 回收不一定及时;若相邻的两个Activity都用到摄像头,在activity中早释放
		releaseCamera();
	}

	@Override
	public void onError(int error, Camera arg1) {
		// TODO Auto-generated method stub
		if (error == Camera.CAMERA_ERROR_SERVER_DIED) {
			Log.e(TAG, "Camera出错" + error);
			releaseCamera();
		}
	}

	public PreviewCallback getPreviewCallback() {
		return mPreviewCallback;
	}

	// 设置预览回调
	public void setPreviewCallback(PreviewCallback mPreviewCallback) {
		this.mPreviewCallback = mPreviewCallback;
	}

	// 重新开始预览
	public void startPreview() {
		if (mCamera != null && !isPreviewing()) {
			mCamera.startPreview();
			// 在开始预览的时候，添加预览回调
			mCamera.setPreviewCallback(mPreviewCallback);
			setPreviewing(true);
		}
	}

	// 屏幕触摸事件
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 按下时自动对焦
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (this.getVisibility() == View.VISIBLE) {
				autofocus();
			}
		}

		return true;
	}

	// 停止预览
	public void stopPreview() {
		if (mCamera != null && isPreviewing()) {
			mCamera.stopPreview();
			setPreviewing(false);
		}
	}

	//聚焦
	public void autofocus(){
		if (mCamera != null && isPreviewing) {
			mCamera.autoFocus(new AutoFocusCallback() {
				@Override
				public void onAutoFocus(boolean success, Camera camera) {
					if (!success) {
						autofocus();
					}
				}
			});
		}
	}

	// 默认有快门
	public void takePicture(PictureCallback pic) {
		takePicture(mShutterCallback, pic);
	}

	/* 拍照撷取影像 */
	public void takePicture(ShutterCallback shutter, PictureCallback pic) {
		if (mCamera != null && isPreviewing()) {
			setPreviewing(false);
			mCamera.takePicture(shutter, null, pic);
		}
	}

	// 为其它应用释放摄像头
	public void releaseCamera() {
		setPreviewing(false);
		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	/**
	 * 切换摄像头
	 */
	public void changeCamera() {
		if (mNumberOfCameras <= 1) {
			return;
		}
		// 当前是后置摄像头,则切换至前置
		if (mDefaultCameraId == CameraInfo.CAMERA_FACING_BACK) {
			firstCameraId = CameraInfo.CAMERA_FACING_FRONT;
		} else {
			firstCameraId = CameraInfo.CAMERA_FACING_BACK;
		}
		try {
			releaseCamera();
			mCamera = getCameraInstance(firstCameraId);
			if (mCamera == null) {
				Toast.makeText(this.getContext(), "摄像头不可用！", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			setCameraParams();
			mCamera.setPreviewDisplay(mSurfaceHolder);
			mCamera.setPreviewCallback(mPreviewCallback);
			mCamera.startPreview();
			setPreviewing(true);
		} catch (Exception e) {
			Log.d(TAG, "Error starting camera preview: " + e.getMessage());
		}
	}

	/**
	 * 切换闪光灯
	 * */
	public void openLight(String method) {

	}

	// ///////////////////////////////////////////
	public boolean isPreviewing() {
		return isPreviewing;
	}

	public void setFirstCameraId(int firstCameraId) {
		this.firstCameraId = firstCameraId;
	}

	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
	}

	public int getiDisplayOrientation() {
		return iDisplayOrientation;
	}

	public int getJpegRotation() {
		return iJpegRotation;
	}

	// 获取当前打开的摄像头
	public int getCurOpenCameraId() {
		return mDefaultCameraId;
	}

	// 获取预览size
	public Size getPreviewSize() {
		return mPreviewSize;
	}

	// 设置优先的预览高宽
	public void setPreviewWH(Integer width, Integer height) {
		iHeight = height;
		iWidth = width;
	}

	// 初始化时是否打开闪光灯
	public void setFlashMode(String flashMode) {
		this.flashMode = flashMode;
	}
	public void dealMatrix(Bitmap bitMap,Matrix matrix){
		dealBmpMatrix(bitMap,matrix,
				getiDisplayOrientation(),getJpegRotation(),getCurOpenCameraId());
	}

	/**
	 * 通用[预览BMP+拍照BMP照片]处理前矩阵运算(旋转方向处理)
	 * @param bitMap
	 * @param matrix
	 * @param iDisplayOrientation
	 * @param iJpgOrientation
	 * @param iCurCameraId
	 */
	public static void dealBmpMatrix(Bitmap bitMap,Matrix matrix,
									 int iDisplayOrientation,int iJpgOrientation,int iCurCameraId){
		Log.i(CameraView.TAG,"处理w:"+bitMap.getWidth() +" h:" +bitMap.getHeight());
		if(iJpgOrientation==Degree.ROTATION_0){
			if(iCurCameraId==CameraInfo.CAMERA_FACING_FRONT){
				Log.i(TAG, "拍照照上下翻转");
				matrix.postScale(1, -1);
			}else{
				Log.i(TAG, "拍照照旋转180");
				matrix.postRotate(Degree.ROTATION_180);
			}
		}else{
			if(iJpgOrientation==Degree.ROTATION_180){
				Log.i(TAG, "拍照照不转");
			}else{
				Log.i(TAG, "拍照照旋转"+iJpgOrientation);
				matrix.postRotate(iJpgOrientation);
			}
			if(iCurCameraId==CameraInfo.CAMERA_FACING_FRONT){
				//前置镜像左右翻转
				//matrix.postScale(-1, 1);
			}
		}
	}

	// ///////////////////////////////////////////
	private Camera mCamera;
	private Size mPreviewSize = null;
	private Size mPictureSize = null;
	private int mDefaultCameraId;// 当前真正打开的摄像头
	private int mNumberOfCameras = 0;
	// 当前摄像头旋转角度
	private int iDisplayOrientation = 0;
	//拍照JPG照片旋转角度
	private int iJpegRotation=0;
	private boolean isPreviewing;// 是否在预览标记,startPreview=true,stopPreview=false
	private SurfaceHolder mSurfaceHolder;

	// 优先选择打开的摄像头，默认前置
	private int firstCameraId = CameraInfo.CAMERA_FACING_FRONT;
	// 优先的预览宽高,预览是否全屏
	private boolean fullScreen = true;
	private Integer iHeight, iWidth;
	// 是否开启闪光灯,默认关闭
	private String flashMode;

	private PreviewCallback mPreviewCallback;

	// ///////////////////////////////////////////
	private void setPreviewing(boolean isPreviewing) {
		this.isPreviewing = isPreviewing;
	}

	/**
	 * 安全获取Camera对象实例的方法
	 *
	 * @param cameraId
	 *            优先打开的摄像头
	 * @return
	 */
	private Camera getCameraInstance(int cameraId) {
		Camera c = null;
		try {
			// Find the total number of cameras available
			mNumberOfCameras = Camera.getNumberOfCameras();

			// Find the ID of the rear-facing ("default") camera
			boolean bFind = false;
			CameraInfo cameraInfo = new CameraInfo();
			for (int i = 0; i < mNumberOfCameras; i++) {
				Camera.getCameraInfo(i, cameraInfo);
				if (cameraInfo.facing == cameraId) {
					mDefaultCameraId = i;
					bFind = true;
					break;
				}
			}
			if (bFind)
				c = Camera.open(mDefaultCameraId); // 试图获取Camera实例
			else {
				mDefaultCameraId = CameraInfo.CAMERA_FACING_BACK;
				c = Camera.open(mDefaultCameraId);
			}
			// 在运行Android 2.3 (API Level 9)
			// 以上版本的设备上，可以用Camera.open(int)访问指定的摄像头。在拥有多于一个摄像头的设备上，以上示例代码将会访问第一个也即朝后的那个摄像头。
			// 可用Camera.getCameraInfo()来确定摄像头朝前还是朝后以及图像的方向
			// parameters.set("camera-id",2);//sumsung 2.3以前的手机　　前置
			// parameters.set("camera-id",1);//sumsung 2.3以前的手机　　后置
			// parameters.set("video_input","secondary");//htc2.3以前的手机　　前置
			// parameters.set("video_input","main");//htc2.3以前的手机 后置
		} catch (Exception e) {
			// 摄像头不可用（正被占用或不存在）
			Log.i(TAG, " 摄像头不可用（正被占用或不存在): " + e.getMessage());
			return null;
		}

		return c; // 不可用则返回null
	}

	// 初始化并设置摄像头参数
	@SuppressLint("InlinedApi")
	private void setCameraParams() {
		// 默认为全屏
		if (!fullScreen) {
			if (iHeight == null || iWidth == null) {
				iHeight = 640;// 4
				iWidth = 480;// 3
			}
		} else {
			iHeight = DisplayUtil.getScreenMetrics(getContext()).y;// 16
			iWidth = DisplayUtil.getScreenMetrics(getContext()).x;// 9
		}

		int max = iHeight >= iWidth ? iHeight : iWidth;
		// 尽量控制起始值不超过,仍无法真实取到的分辨率
		if (max > MAX_SO_PHOTO_SUPPORT_SIZE) {
			float fix = MAX_SO_PHOTO_SUPPORT_SIZE / (float) max;
			iHeight = (int) (iHeight * fix);
			iWidth = (int) (iWidth * fix);
		}

		Parameters sParameters = mCamera.getParameters();
		List<Size> pictureSizes = sParameters.getSupportedPictureSizes();
		List<Size> previewSizes = sParameters.getSupportedPreviewSizes();
		// 尽量保证拍照、预览、画布大小比例或尺寸保持一致
		// 根据比例拿最佳照片分辨率(size.height<size.width相反)
		mPictureSize = CameraUtil.getOptimalPreviewSize(pictureSizes,
				IMG_MAX_HEIGHT, IMG_MAX_WIDTH);
		sParameters.setPictureSize(mPictureSize.width, mPictureSize.height);// 要在摄像头支持的拍照范围内选择
		Log.i(TAG, " h2:" + mPictureSize.height + " w2:" + mPictureSize.width);

		// 根据照片分辨率拿最佳预览分辨率
		mPreviewSize = CameraUtil.getOptimalPreviewSize(previewSizes, iHeight,
				iWidth);
		sParameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);// 要在摄像头支持的预览范围内选择
		Log.i(TAG, " h3:" + mPreviewSize.height + " w3:" + mPreviewSize.width);

		final Activity activity = (Activity) this.getContext();
		iDisplayOrientation = CameraUtil.getDisplayOrientation(
				CameraUtil.getDisplayRotation(activity), mDefaultCameraId);
		Log.i(TAG, "iDisplayOrientation:" + iDisplayOrientation);
		iJpegRotation=CameraUtil.getJpegRotation(mDefaultCameraId,
				CameraUtil.getDisplayRotation(activity));

		Log.i(TAG, "iJpegRotation:" + iJpegRotation);
		// 画布旋转角度
		mCamera.setDisplayOrientation(iDisplayOrientation);

		// 闪光灯设置
		if (flashMode != null) {
			List<String> flashModes = sParameters.getSupportedFlashModes();
			if (flashModes != null) {
				if (flashModes.contains(flashMode)) {
					sParameters.setFlashMode(flashMode);
				}
			}
		}
		// 设定拍照格式为JPEG(默认值)
		sParameters.setPictureFormat(PixelFormat.JPEG);

		if (isSupported(Parameters.FOCUS_MODE_AUTO,sParameters.getSupportedFocusModes())) {
			sParameters.setFocusMode(Parameters.FOCUS_MODE_AUTO);
		}

		// 每秒显示帧率
		Log.i(TAG, "帧率：" + mCamera.getParameters().getPreviewFrameRate());
		mCamera.setParameters(sParameters);

		this.setZOrderOnTop(false);// ->置于最底端
		this.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						CameraView.this.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						// 对surfaceview画幅的大小按比例设置
						FrameLayout.LayoutParams mLP = (FrameLayout.LayoutParams) CameraView.this
								.getLayoutParams();
						mLP.height = CameraView.this.getHeight();
						mLP.width = CameraView.this.getWidth();
						Log.i(TAG, " h4:"
								+ mLP.height
								+ " w4:"
								+ mLP.width
								+ "ch4:"
								+ DisplayUtil.getContentView(activity)
								.getHeight());
						// 针对竖屏布局下有标题栏、状态栏但surfaceview fill_parent的情形
						// 如果祖先节点高度与surfaceview高度一致,则当全屏处理
						if (mLP.height == DisplayUtil.getContentView(activity)
								.getHeight()) {
							mLP.height = DisplayUtil
									.getScreenMetrics(CameraView.this
											.getContext()).y;
							Log.i(TAG, "Content-Full");
						}
						if (iDisplayOrientation == Degree.ROTATION_90)
							CameraUtil.getOptimalSurfaceSize(mLP,
									mPreviewSize.height, mPreviewSize.width);
						else
							CameraUtil.getOptimalSurfaceSize(mLP,
									mPreviewSize.width, mPreviewSize.height);
						Log.i(TAG, " h5:" + mLP.height + " w5:" + mLP.width);
					}
				});
	}

	public boolean isSupported(String value, List<String> supported) {
		return supported == null ? false : supported.indexOf(value) >= 0;
	}

	private ShutterCallback mShutterCallback = new ShutterCallback() {
		public void onShutter() {
			// Shutter has closed 快门已关闭
		}
	};

}
