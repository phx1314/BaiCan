package com.facefr.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.facefr.server.in.CollectInfoInstance;
import com.pertraitbodycheck.activity.R;
import com.x.util.BmpUtil;
import com.x.view.CustomHeaderLayOut;
import com.x.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends BaseActivity {

	private TextView mTxtResult;
	private Button mBtnRestart;
	private CustomHeaderLayOut mTitle;

	// 图片展示
	private CustomViewPager mViewPager;
	private String[] pageNames = { "环境检测","活体检测1", "活体检测2", "活体检测3" };
	private List<Bitmap> mListBmps;
	private byte[] mSelBuf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		initView();

		if (CollectInfoInstance.getInstance()!=null) {
			mListBmps=CollectInfoInstance.getInstance().getBodyBmps();
			mSelBuf=CollectInfoInstance.getInstance().getSelBuffer();

			if (CollectInfoInstance.getInstance().isBodySuccess()) {
				mTxtResult.setText("data包获取成功");
			}else {
				mTxtResult.setText("活体检测失败");
			}
		}

		if (mListBmps==null&&mSelBuf!=null) {
			mListBmps=new ArrayList<Bitmap>();
			mListBmps.add(BmpUtil.Bytes2Bitmap(mSelBuf));
		}else {
			if (mSelBuf!=null) {
				mListBmps.add(0, BmpUtil.Bytes2Bitmap(mSelBuf));
			}
		}
		showImgs();
	}


	private void initView() {
		mTxtResult = (TextView) findViewById(R.id.txt_result);
		mBtnRestart = (Button) findViewById(R.id.reaction);

		mTitle = (CustomHeaderLayOut) findViewById(R.id.actionbar);
		mViewPager=(CustomViewPager) findViewById(R.id.viewpager);
		mTitle.setOnleftListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});

		mBtnRestart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(ResultActivity.this,
						MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}

	private void showImgs() {
		mViewPager.setIndicatorDrawableChecked(R.drawable.page_now) // 设置选中状态的滑动点
				.setIndicatorDrawableUnchecked(R.drawable.page) // 设置非选中状态的滑动点
				.setAutoPlay(false) // 是否开启自动轮播
				.setDisplayIndicator(true) // 是否显示滑动点
				.setIndicatorGravity(Gravity.CENTER) // 滑动点的所在位置
				.setImages(mListBmps)//设置照片list
				.setDisplayPageName(true)//是否显示照片名字
				.setPageNames(pageNames)//设置照片名字数组
				.startPlay();
	}

}
