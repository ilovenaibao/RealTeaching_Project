package com.besta.app.realteaching;

import com.android.mylib.loadingview.MyLoadingView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MyViewPagerAdapterHandlerStruct {
	private Context mContext = null;
	private LayoutInflater mInflater;
	public View MainLayoutView = null;
	RelativeLayout mainRelativeViewLayout = null;
	public MyLoadingView mLoadingView = null;
	public boolean isStarting = false;
	public int handlerCount = 0;
	public int loadingDataPosition = 0;

	public MyViewPagerAdapterHandlerStruct(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		MainLayoutView = mInflater.inflate(R.layout.real_teaching_main, null);
		isStarting = false;
		handlerCount = loadingDataPosition = 0;
		mLoadingView = new MyLoadingView(context, 0);
		mLoadingView.setOneLoadingView(context, 0);
		if (null != mLoadingView.selectLoadingView) {
			// main_relative_layout layout
			mainRelativeViewLayout = (RelativeLayout) MainLayoutView
					.findViewById(R.id.main_relative_layout);
			ScrollView ly = (ScrollView) MainLayoutView
					.findViewById(R.id.my_main_scroll);
			if (null != ly) {
				ly.setBackgroundColor(Color.WHITE);
			}
		}
	}
}
