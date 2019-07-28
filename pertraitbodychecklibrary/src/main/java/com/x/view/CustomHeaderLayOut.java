package com.x.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.facefr.controller.Controller;
import com.pertraitbodycheck.activity.R;


/**
 * 自定义Title
 *
 *
 */
public class CustomHeaderLayOut extends RelativeLayout {

	public CustomHeaderLayOut(Context context) {
		super(context);
	}

	public CustomHeaderLayOut(Context context, AttributeSet attrs,
							  int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		_setCustomAttributes(attrs, defStyleAttr);
	}

	public CustomHeaderLayOut(Context context, AttributeSet attrs) {
		super(context, attrs);
		_setCustomAttributes(attrs, 0);
	}


	//设置返回按钮的方法
	public void setOnleftListener(OnClickListener listener){
		if (mLeftImgBtn!=null) {
//			mLeftImgLayOut.setOnClickListener(listener);
			mLeftImgBtn.setOnClickListener(listener);//增加，防止图片按钮不可点击
		}
	}

	public void setTitle(String title){
		System.out.println("setTitle//////////////////");
//		if (mTitle!=null) {
//			mTitle.setText(title);
//		}
	}

	/*public void setOnLeftImageClickListener(OnLeftImageClickListener listener) {
		mLeftImgClickListener = listener;
	}

	// 设置左侧按钮监听自定义接口
	public interface OnLeftImageClickListener {
		void onClick(View arg0);
	}*/

	// /////////////////////////////////////////////////////////////
	// 文本文字
	private String mTitleText;
//	private TextView mTitle;// 标题
//	private LinearLayout mLeftImgLayOut;// 左侧按钮
	@SuppressWarnings("unused")
	private Button mLeftImgBtn;
	//private OnLeftImageClickListener mLeftImgClickListener;
	private View mHeader;

	// //////////////////////////////////////////////////////////////
	private void _setCustomAttributes(AttributeSet attrs, int defStyleAttr) {
		// 获得我们自定义的自定义样式属性
//		TypedArray a = this.getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CustomHeaderLayOut,defStyleAttr, 0);
//		mTitleText = a.getString(R.styleable.CustomHeaderLayOut_titleText);
//		a.recycle();
		_initView();
	}

	// 实现初始化，加载布局文件
	@SuppressLint("InflateParams") private void _initView() {
		LayoutInflater mInflater = LayoutInflater.from(this.getContext());
		mHeader = mInflater.inflate(R.layout.custom_header_bar_view, null);
		addView(mHeader);

		/*// 默认返回事件为关闭当前Activity
		if (mLeftImgClickListener == null) {
			final Activity activity = (Activity) this.getContext();
			mLeftImgClickListener = new OnLeftImageClickListener() {
				@Override
				public void onClick(View arg0) {
					activity.finish();
				}
			};
		}*/

//		mLeftImgLayOut = (LinearLayout) mHeader.findViewById(R.id.linear_back);
		mLeftImgBtn=(Button) mHeader.findViewById(R.id.btn_back);

		mLeftImgBtn.setBackground(getResources().getDrawable(Controller.getInstance().mStyle.resBackImg) );
		/*mLeftImgLayOut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mLeftImgClickListener != null) {
					// 回调方法，调用onLeftImageButtonClickListener接口实现类的方法
					mLeftImgClickListener.onClick(arg0);
				}
			}
		});

		mLeftImgBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mLeftImgClickListener != null) {
					mLeftImgClickListener.onClick(arg0);
				}
			}
		});*/

//		mTitle = (TextView) mHeader.findViewById(R.id.titleTV);
//		if (mTitleText != null)
//			mTitle.setText(mTitleText);
	}

}
