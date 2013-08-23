package com.besta.app.realteaching;

import com.besta.flash.FlashView;
import com.besta.flash.FlashView2;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MyPlayFlashImgViewOnClickListener implements OnClickListener {
	Context context;
	ImageView playFlashImgView = null;
	boolean isDisplayImgView = true;
	// String playFlashName = null;
	MyOneData mOneData = null;
	FlashView2 flashView = null;
	FlashView flashView23 = null;
	int nowFlashCount = 0;
	String playFlashPath = null;
	ImageView closePlayFlashBt = null;

	public MyPlayFlashImgViewOnClickListener(Context context,
			ImageView playFlashImgView, ImageView closePlayFlashBt,
			MyOneData mOneData, int nowFlashCount, FlashView2 flashView,
			FlashView flashView23, String playFlashPath) {
		this.context = context;
		this.playFlashImgView = playFlashImgView;
		this.closePlayFlashBt = closePlayFlashBt;
		// this.playFlashName = playFlashName;
		this.mOneData = mOneData;
		this.flashView = flashView;
		this.flashView23 = flashView23;
		this.nowFlashCount = nowFlashCount;
		this.playFlashPath = playFlashPath;
	}

	public void setIsDisplayImgViewFlag(boolean flag) {
		this.isDisplayImgView = flag;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
