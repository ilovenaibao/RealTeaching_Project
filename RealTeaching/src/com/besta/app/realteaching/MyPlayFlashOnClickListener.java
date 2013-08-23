package com.besta.app.realteaching;

import com.besta.flash.FlashView;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MyPlayFlashOnClickListener implements OnClickListener {
	FlashView flashview = null;
	ImageView play_flash_v_img = null;
	String flashPath = null;
	String flashName = null;

	MyOneData oneData = null;

	public MyPlayFlashOnClickListener(FlashView flashview,
			ImageView play_flash_v_img, String path, String name,
			MyOneData oneData) {
		this.flashview = flashview;
		this.play_flash_v_img = play_flash_v_img;
		flashPath = path;
		flashName = name;
		this.oneData = oneData;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
