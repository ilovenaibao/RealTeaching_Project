package com.besta.app.realteaching;

import com.besta.flash.FlashView;

public class MyOneData {
	public final static int DEFUALT_VALUE = 0xFFFF;
	public final static int FLASH_FILE_EXIST = 0x0001;
	public final static int FLASH_FILE_NOT_EXIST = 0x0002;
	public final static int FLASH_FILE_DOWNLOADING = 0x0003;
	public final static int FLASH_FILE_NEED_UPDATE = 0x0004;
	public final static int FLASH_FILE_NEED_DOWNLOAD = 0x0005;

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

	public MyOneData() {
		flashFileName = new String("");
		summaryName = new String("");
		summaryContent = new String("");
		myFlashView = null;
		playFlashFlag = isDownloading = isUpdating = false;
		localFileLastModifedTime = 0;
		flashFileStatus = DEFUALT_VALUE;
		fileLastModify = DEFUALT_VALUE;
	}
}
