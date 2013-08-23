package com.besta.app.realteaching;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besta.flash.FlashView;

public class MyListAdapter extends BaseAdapter {

	Context parentContext;
	ArrayList<MyOneData> nowListData;
	ArrayList<MyOneData> totalData;
	int prePosition = -1;

	public MyListAdapter(Context view, ArrayList<MyOneData> nowListData,
			ArrayList<MyOneData> totalData) {
		parentContext = view;
		this.nowListData = nowListData;
		this.totalData = totalData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return totalData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return totalData.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void PlayFlashOnPause() {
		int totalDataCount = totalData.size();
		int count = 0;

		for (; count < totalDataCount; count++) {
			MyOneData oneData = totalData.get(count);
			if (null != oneData.myFlashView && oneData.playFlashFlag) {
				// oneData.myFlashView.doPause();
			}
		}
	}

	public void PlayFlashonResume() {
		int totalDataCount = totalData.size();
		int count = 0;

		for (; count < totalDataCount; count++) {
			MyOneData oneData = totalData.get(count);
			if (null != oneData.myFlashView) {
				oneData.myFlashView.doResume();
			}
		}
	}

	public void PlayFlashonDestroy() {
		int totalDataCount = totalData.size();
		int count = 0;

		for (; count < totalDataCount; count++) {
			MyOneData oneData = totalData.get(count);
			if (null != oneData.myFlashView && oneData.playFlashFlag) {
				oneData.myFlashView.doDestroy();
			}
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(this.parentContext);
		View view = inflater.inflate(R.layout.real_teaching_list, null);
		// 設置titlebar
		LinearLayout title_bar_layout = (LinearLayout) view
				.findViewById(R.id.title_bar_layout);
		int visibleValut = View.VISIBLE;
		if (0 == position) {
			visibleValut = View.VISIBLE;
		} else {
			visibleValut = View.GONE;
		}
		if (null != title_bar_layout) {
			title_bar_layout.setVisibility(visibleValut);
		}

		int totalDataCount = nowListData.size();
		MyOneData oneData = new MyOneData();
		oneData = totalData.get(position);

		TextView summary_tv = (TextView) view.findViewById(R.id.summary_tv);
		String summary_content = "备考策略：\r\n1. 注意日常生活题材\r\n2. 阅读英语书信、故事、心得、写作……\r\n3. 背诵历年中考书面表达范文\r\n4. 注意各种写作体裁的段落组织（起、承、转、合）、句型结构、语法、常用词组、词汇（包括转折词）、标点符号\r\n5. 平时练习写英语日记或写英语E-mail\r\n6. 多听多看英语新闻";
		if (null != summary_tv) {
			summary_tv.setText(summary_content);
			summary_tv.setTextColor(Color.BLACK);
		}

		FlashView flashview = (FlashView) view
				.findViewById(R.id.play_flash_webview);
		oneData.myFlashView = flashview;
		ImageView play_flash_v_img = (ImageView) view
				.findViewById(R.id.play_flash_view_img);
		if (null != play_flash_v_img) {
			play_flash_v_img.setVisibility(View.VISIBLE);
			play_flash_v_img.setImageResource(R.drawable.play_flash);
			flashview.setVisibility(View.VISIBLE);
			// play_flash_v_img.setImageResource(R.drawable.play_flash);
			String flashPath = "/mnt/sdcard/Besta/1com.besta.app.zuowen.flash/";
			String flashName = "QJ010010.bfe"; // m103010 c010010 QJ010010
			play_flash_v_img
					.setOnClickListener(new MyPlayFlashOnClickListener(
							flashview, play_flash_v_img, flashPath, flashName,
							oneData) {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (oneData.playFlashFlag) {
								oneData.playFlashFlag = false;
							} else {
								oneData.playFlashFlag = true;

								play_flash_v_img.setVisibility(View.GONE);
								android.view.ViewGroup.LayoutParams lp = flashview
										.getLayoutParams();
								lp.width = 800;
								lp.height = 400;

								this.flashview.setLayoutParams(lp);
								this.flashview.ShowFlash(this.flashPath
										+ this.flashName, 0, 0, false);
							}
						}
					});
		}

		return view;
	}
}
