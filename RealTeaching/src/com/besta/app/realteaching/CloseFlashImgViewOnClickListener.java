package com.besta.app.realteaching;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class CloseFlashImgViewOnClickListener implements OnClickListener {
	Context context;
	OneSubViewData mOneData = null;
	ImageView closePlayFlashBt = null;

	public CloseFlashImgViewOnClickListener(Context context,
			ImageView closePlayFlashBt, OneSubViewData mOneData) {
		this.context = context;
		this.closePlayFlashBt = closePlayFlashBt;
		// this.playFlashName = playFlashName;
		this.mOneData = mOneData;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
