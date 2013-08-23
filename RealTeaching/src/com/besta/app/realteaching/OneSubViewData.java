package com.besta.app.realteaching;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besta.flash.FlashView;
import com.besta.flash.FlashView2;

public class OneSubViewData {
	// read strcut from offline data andr create Main View struct
	public class MyDataFlags {
		int total_flags = 12;
		int is_has_diy; // is has diy button?
		int is_has_test; // is has test button?
		int is_has_summary; // is has summary info?

		// construct
		public MyDataFlags() {
			is_has_diy = 0;
			is_has_test = 0;
			is_has_summary = 0;
		}

		/**
		 * @describe create Main View's frame
		 * 
		 * @return int : 1 -> test
		 */
		public int getLayoutKind() {
			int ret = 0;
			if (0 == is_has_test && 0 == is_has_diy) {

			} else if (1 == is_has_test && 0 == is_has_diy) {
				ret = 1;
			} else if (1 == is_has_test && 1 == is_has_diy) {
				ret = 2;
			} else if (0 == is_has_test && 1 == is_has_diy) {
				ret = 3;
			}

			return ret;
		}
	}

	public final static int DEFUALT_VALUE = 0xFFFF;
	public final static int FLASH_FILE_EXIST = 0x0001;
	public final static int FLASH_FILE_NOT_EXIST = 0x0002;
	public final static int FLASH_FILE_DOWNLOADING = 0x0003;
	public final static int FLASH_FILE_NEED_UPDATE = 0x0004;
	public final static int FLASH_FILE_NEED_DOWNLOAD = 0x0005;

	int viewPagerPosition;
	String knowledgeID;
	String knowledgeName;
	String flashFileName;
	boolean isDownloading;
	long localFileLastModifedTime;
	String unit_id;
	String summaryName;
	String summaryContent;
	boolean isUpdating;

	int flashFileStatus;
	long fileLastModify;

	FlashView myFlashView;
	boolean playFlashFlag;
	boolean isCallTestActivity = false;

	LinearLayout mainViewLayout = null;
	CustomScrollView mScrollView = null;
	View[] view = null;
	boolean[] isFlashPlaying = null;
	int playingCount = 0;
	int downloadFlashSuccess = 0;

	public MyDataFlags buttonFlags = null;
	boolean[] buttonDownFlag = { false, // test_bt
			false, // diy_bt
			false, // update flash button
	};
	Button[] relateButton = new Button[buttonDownFlag.length];

	public OneSubViewData() {
		viewPagerPosition = 0;
		knowledgeID = new String("");
		knowledgeName = new String("");
		flashFileName = new String("");
		unit_id = new String("");
		summaryName = new String("");
		summaryContent = new String("");
		myFlashView = null;
		playFlashFlag = isCallTestActivity = isDownloading = isUpdating = false;
		localFileLastModifedTime = 0;
		flashFileStatus = DEFUALT_VALUE;
		fileLastModify = DEFUALT_VALUE;
		buttonFlags = new MyDataFlags();
	}

	public void setOneSubViewData(int count) {
		view = new View[count];
		isFlashPlaying = new boolean[count];
		downloadFlashSuccess = 0;
	}

	public void doPause(int selectOneItemLayoutVer) {
		for (int count = 0; null != view && count < view.length; count++) {
			if (null != view[count]) {
				switch (selectOneItemLayoutVer) {
				case 1: // 4.0
					FlashView2 flashView = (FlashView2) view[count]
							.findViewById(R.id.play_flash_webview);
					if (null != flashView) {
						if (isFlashPlaying[count]) {
							// flashView.doPause();
							flashView.onPause();
						}
					}
					break;
				case 2:
					FlashView flashView23 = (FlashView) view[count]
							.findViewById(R.id.play_flash_webview);
					if (null != flashView23) {
						if (isFlashPlaying[count]) {
							// flashView23.doPause();
							flashView23.onPause();
						}
					}
					break;
				}
			}
		}
	}

	public void doDestroy(int selectOneItemLayoutVer) {
		for (int count = 0; null != view && count < view.length; count++) {
			if (null != view[count]) {
				switch (selectOneItemLayoutVer) {
				case 1: // 4.0
					FlashView2 flashView = (FlashView2) view[count]
							.findViewById(R.id.play_flash_webview);
					if (null != flashView) {
						// flashView.doDestroy();
						flashView.destroy();
					}
					break;
				case 2:
					FlashView flashView23 = (FlashView) view[count]
							.findViewById(R.id.play_flash_webview);
					if (null != flashView23) {
						flashView23.doDestroy();
						// flashView23.destroy();
					}
					break;
				}
			}

		}
	}

}
