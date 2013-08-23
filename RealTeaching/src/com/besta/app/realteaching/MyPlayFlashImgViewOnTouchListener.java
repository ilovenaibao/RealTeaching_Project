package com.besta.app.realteaching;

import com.besta.flash.FlashView;
import com.besta.flash.FlashView2;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class MyPlayFlashImgViewOnTouchListener implements OnTouchListener {

	Context context;
	ImageView playFlashImgView = null;
	boolean isDisplayImgView = true;
	// String playFlashName = null;
	MyOneData mOneData = null;
	FlashView2 flashView = null;
	FlashView flashView23 = null;
	int nowFlashCount = 0;
	String playFlashPath = null;
	public int selectLayoutVer = 0;

	public MyPlayFlashImgViewOnTouchListener(Context context,
			ImageView playFlashImgView, MyOneData mOneData, int nowFlashCount,
			FlashView2 flashView, FlashView flashView23, String playFlashPath) {
		this.context = context;
		this.playFlashImgView = playFlashImgView;
		// this.playFlashName = playFlashName;
		this.mOneData = mOneData;
		this.nowFlashCount = nowFlashCount;
		this.flashView = flashView;
		this.flashView23 = flashView23;
		if (null != this.flashView && null == this.flashView23) {
			selectLayoutVer = 1;
		} else if (null == this.flashView && null != this.flashView23) {
			selectLayoutVer = 2;
		}
		this.playFlashPath = playFlashPath;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
