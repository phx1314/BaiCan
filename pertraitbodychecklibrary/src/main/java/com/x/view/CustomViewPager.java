package com.x.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pertraitbodycheck.activity.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * pageView 轮播显示图片
 *
 *
 */
@SuppressLint("HandlerLeak")
public class CustomViewPager extends LinearLayout {
	private Context context;
	private ImageView[] allPage;
	private ViewPager viewPager;
	private LinearLayout dotLayout;
	private LinearLayout pageLayout;

	private TextView pageName;

	// 自定义轮播图的资源
	private List<Bitmap> images;
	private String[] pageNames;
	// 指示器位置
	private int pageIndicatorGravity = Gravity.RIGHT;
	// 当前指示器样式
	private int indicatorDrawableChecked;
	private int indicatorDrawableUnchecked;
	// 自动轮播启用开关
	private boolean isAutoPlay = true;
	// 轮播指示器启用开关
	private boolean isDisplayIndicator = true;
	// 轮播图片显示名称
	private boolean isDisplayPageName;
	private int currentIndex = 0;
	// 定时任务
	private ScheduledExecutorService scheduledExecutorService;

	private Handler viewPagerHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			viewPager.setCurrentItem((currentIndex + 1) % allPage.length);
		}
	};

	/**
	 * 执行轮播图切换任务
	 */
	private class SlideShowTask implements Runnable {

		@Override
		public void run() {
			synchronized (viewPager) {
				viewPagerHandler.obtainMessage().sendToTarget();
			}
		}
	}

	public CustomViewPager(Context context) {
		super(context, null);
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public CustomViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		images = new ArrayList<Bitmap>();
	}

	/**
	 * 初始化ADViewPager
	 */
	@SuppressWarnings("deprecation")
	private void initADViewPager() {
		if (images == null || images.size() == 0) {
			return;
		}
		LayoutInflater.from(context)
				.inflate(R.layout.custom_view_pager, this, true);
		dotLayout = (LinearLayout) findViewById(R.id.ll_dot);
		pageLayout = (LinearLayout) findViewById(R.id.viewPager_container);

		viewPager = new ViewPager(context);

		// 获取屏幕像素相关信息
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);

		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();

		// 根据屏幕信息设置ViewPager的宽高 高度占屏幕的2/5
		LayoutParams params = new LayoutParams(new ViewGroup.LayoutParams(width, height * 2 / 5));

		pageLayout.setLayoutParams(params);

		// 将ViewPager容器设置到布局文件父容器中
		pageLayout.addView(viewPager);

		pageName = (TextView) findViewById(R.id.page_name);

		dotLayout.removeAllViews();
		allPage = new ImageView[images.size()];
		// 热点个数与图片特殊相等
		for (int i = 0; i < images.size(); i++) {
			ImageView pageView = new ImageView(context);
			pageView.setImageBitmap(images.get(i));
			allPage[i] = pageView;
		}

		if (isDisplayPageName) {
			if (pageNames.length > 0) {
				pageName.setText(pageNames[0]);
			}
		}

		if (isDisplayIndicator) {
			drawPageIndicator();
		}

		PagerAdapter adapter = new ADViewPagerAdapter();
		viewPager.setAdapter(adapter);
		viewPager.setFocusable(true);
		viewPager.setOnPageChangeListener(new ADViewPagerChangeListener());
	}

	/**
	 * 绘制指示器
	 */
	private void drawPageIndicator() {
		if (images.size() <= 1) {
			return;
		}
		for (int i = 0; i < images.size(); i++) {
			ImageView dotView = new ImageView(context);
			LayoutParams params = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 4;
			params.rightMargin = 4;
			params.width = 6;//设置滑动点的宽高
			params.height = 6;//设置滑动点的宽高
			dotView.setBackgroundResource(indicatorDrawableUnchecked);
			dotLayout.addView(dotView, params);
		}
		dotLayout.setGravity(pageIndicatorGravity);
		dotLayout.getChildAt(0).setBackgroundResource(indicatorDrawableChecked);
	}

	/**
	 * 填充ViewPager的页面适配器
	 */
	private class ADViewPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(allPage[position]);
		}

		@Override
		public Object instantiateItem(View container, int position) {
			ImageView imageView = allPage[position];
			((ViewPager) container).addView(imageView);
			return imageView;
		}

		@Override
		public int getCount() {
			return allPage == null ? 0 : allPage.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return (arg0 == arg1);
		}
	}

	/**
	 * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
	 */
	private class ADViewPagerChangeListener implements
			ViewPager.OnPageChangeListener {
		//private boolean isScrolled;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			switch (arg0) {
				case 1:// 手势滑动
					//isScrolled = false;
					break;
				case 2:// 界面切换
					//isScrolled = true;
					break;
				case 0:// 滑动结束
				/*if (viewPager.getCurrentItem() == viewPager.getAdapter()
						.getCount() - 1 && !isScrolled) {
					// 当前为最后一张，此时从右向左滑，则切换到第一张
					viewPager.setCurrentItem(0);
				} else if (viewPager.getCurrentItem() == 0 && !isScrolled) {
					// 当前为第一张，此时从左向右滑，则切换到最后一张
					viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
				}*/

					break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			setCurrentDot(arg0);
			setCurPageName(arg0);
		}
	}

	/**
	 * 设置当前选中的指示器
	 *
	 * @param position
	 */
	private void setCurrentDot(int position) {
		currentIndex = position;
		if (!isDisplayIndicator) {
			return;
		}
		for (int i = 0; i < dotLayout.getChildCount(); i++) {
			if (i == position) {
				dotLayout.getChildAt(position).setBackgroundResource(
						indicatorDrawableChecked);
			} else {
				dotLayout.getChildAt(i).setBackgroundResource(
						indicatorDrawableUnchecked);
			}
		}
	}

	private void setCurPageName(int position) {
		currentIndex = position;
		if (!isDisplayPageName) {
			return;
		}
		pageName.setText(pageNames[position]);
	}

	/**
	 * 初始化page 并且开始执行轮播
	 *
	 * @param delay
	 *            延迟轮播，单位second
	 * @param period
	 *            轮播的周期，单位second
	 */
	public void startPlay(long delay, long period) {
		initADViewPager();
		if (isAutoPlay && images.size() > 1) {
			scheduledExecutorService = Executors
					.newSingleThreadScheduledExecutor();
			scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(),
					delay, period, TimeUnit.SECONDS);
		}
	}

	/**
	 * 初始化page,并且开始执行轮播，默认延迟1秒开始执行，且周期是6秒
	 */
	public void startPlay() {
		startPlay(1, 6);
	}

	/**
	 * 停止轮播图切换
	 */
	private void stopPlay() {
		if (scheduledExecutorService != null) {
			scheduledExecutorService.shutdown();
		}
		if (viewPagerHandler != null) {
			viewPagerHandler.removeCallbacksAndMessages(null);
		}
	}

	/**
	 * 设置轮播图
	 *
	 * @param images
	 */
	public CustomViewPager setImages(List<Bitmap> images) {
		this.images = images;
		return this;
	}

	/**
	 * 设置图片名称
	 *
	 * @param pageNames
	 */
	public CustomViewPager setPageNames(String[] pageNames) {
		this.pageNames = pageNames;
		return this;
	}

	/**
	 * 设置是否自动播放
	 *
	 * @param isAutoPlay
	 * @return
	 */
	public CustomViewPager setAutoPlay(boolean isAutoPlay) {
		this.isAutoPlay = isAutoPlay;
		return this;
	}

	/**
	 * 设置是否显示指示器
	 *
	 * @param isDisplayIndicator
	 * @return
	 */
	public CustomViewPager setDisplayIndicator(boolean isDisplayIndicator) {
		this.isDisplayIndicator = isDisplayIndicator;
		return this;
	}

	/**
	 * 是否显示图片名称
	 *
	 * @param isDisplayPageName
	 * @return
	 */
	public CustomViewPager setDisplayPageName(boolean isDisplayPageName) {
		this.isDisplayPageName = isDisplayPageName;
		return this;
	}

	/**
	 * 设置指示器的位置
	 *
	 * @param gravity
	 * @return
	 */
	public CustomViewPager setIndicatorGravity(int gravity) {
		this.pageIndicatorGravity = gravity;
		return this;
	}

	/**
	 * 设置指示器选中时的drawable
	 *
	 * @param resId
	 * @return
	 */
	public CustomViewPager setIndicatorDrawableChecked(int resId) {
		this.indicatorDrawableChecked = resId;
		return this;
	}

	/**
	 * 设置指示器未选中时的drawable
	 *
	 * @param resId
	 * @return
	 */
	public CustomViewPager setIndicatorDrawableUnchecked(int resId) {
		this.indicatorDrawableUnchecked = resId;
		return this;
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stopPlay();
	}

}
