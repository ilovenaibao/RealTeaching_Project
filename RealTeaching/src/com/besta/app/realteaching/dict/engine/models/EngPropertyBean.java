package com.besta.app.realteaching.dict.engine.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;

public class EngPropertyBean {
	public static final int DIVIDER_WIDTH_DP = 8 ;
	public static final int EDIT_TEXT_SIZE = 20;
	
	private static final int LIST_HEIGHT_GROW		= 2;
	
	private static final int LIST_HEIGHT_MIN		= 9;
	private static final int LIST_HEIGHT_INIT		= 10;
	
	private static final int TEXT_SIZE_GROW		= 4;
	
	public static final int TEXT_SIZE_MAX 		= 32;
	public static final int TEXT_SIZE_MIN		= 16;
	public static final int TEXT_SIZE_INIT		= 20;
	
	private static EngPropertyBean uniqueConfig;
	private static int incrementalID;
	
	/*private boolean textSelectionMode;
	public static final String PROPERTY_TEXT_SELECTION_MODE = "textSelectionMode";*/
	
	private int textSize;
	private int listHeight;
	public static final String PROPERTY_TEXT_SIZE = "textSize";
	
	private boolean funcFolded;
	public static final String PROPERTY_FUNC_FOLDED = "funcFolded";
	
	private PropertyChangeSupport changeSupport;
	private boolean selecting;
	private String cacheDir;
	private String[] curDJKKState;    // not just used for DJKK,or pinyin zhuyin and others
	private static SharedPreferences prefs;
	
	private EngPropertyBean() {
		
	}
	
	public static void prepare(Context context) {
		if (uniqueConfig == null) {
			/*NotificationManager notificationManager = 
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);*/
			uniqueConfig = new EngPropertyBean();
			EngPropertyBean config = uniqueConfig;
    		prefs = context.getSharedPreferences("PublicData",
                    Context.MODE_PRIVATE);
    		SharedPreferences.Editor editor = prefs.edit();
			config.textSize = -1;
			config.textSize = prefs.getInt("textSize", config.textSize);
			if(config.textSize == -1){
				config.textSize = TEXT_SIZE_INIT;
				editor.putInt("textSize", config.textSize);
			}
			config.listHeight = LIST_HEIGHT_INIT;
			config.funcFolded = false;
			config.changeSupport = new PropertyChangeSupport(config);
			config.cacheDir = context.getCacheDir().getAbsolutePath();
			
			config.curDJKKState = new String[6];
		}
		else {
			//uniqueConfig.textSelectionMode = false;
			uniqueConfig.selecting = false;
			
		}
	}
	
	public void setDJKKState(String type, String state){
		this.curDJKKState[0] = type;
		this.curDJKKState[1] = state;
	}
	
	public String[] getDJKKState(){
		return curDJKKState;
	}
	
	public static void quit() {
		
	}
	
	public static EngPropertyBean getInstance() {
		return uniqueConfig;
	}
	
	public static int createEngineModelID() {
		return incrementalID++;
	}

	
	public boolean textSizeGrow() {
		boolean ret = false;
		if (!selecting) {
			int tmpSize = textSize;
			int tmpHeight = listHeight;
			if (tmpSize < TEXT_SIZE_MAX) {
				tmpSize += TEXT_SIZE_GROW;
				tmpHeight += LIST_HEIGHT_GROW;
			}
			else {
				tmpSize = TEXT_SIZE_MIN;
				tmpHeight = LIST_HEIGHT_MIN;
			}
			setTextSize(tmpSize);
			listHeight = tmpHeight;
			ret = true;
		}	
		return ret;
	}
	
	public boolean textSizeSet(int size){
		boolean ret = false;
		if(!selecting){
			setTextSize(size);
			listHeight = (size - TEXT_SIZE_MIN) / 2 + LIST_HEIGHT_MIN;
			ret = true;
		}
		return ret;
	}
	
	public void resizeForTextFont(StringBuffer contentBuf, 
									ArrayList<Integer> fontStorageList, 
									ArrayList<Integer> imgStorageList) {
		StringBuffer skdBuf = null;
		int end = contentBuf.indexOf("</STYLE>");
		if (end != -1) {
			skdBuf = new StringBuffer(contentBuf.substring(0, end));
		}
		else {
			skdBuf = contentBuf;
		}
		
		int correctFontSize = textSize + 4;	
		
		replaceForTextFont(skdBuf, correctFontSize, 
				"font-size:", "", "px", fontStorageList, 0);
		
		
		/*int offset = replaceForTextFont(skdBuf, textSize, 
						"ImgCtrlSizeByCSS", "width:", "px", imgStorageList, 0);*/
		replaceForTextFont(skdBuf, correctFontSize, 
				"ImgCtrlSizeByCSS", "height:", "px", imgStorageList, /*offset*/0);
		
		if (end != -1) {
			contentBuf.replace(0, end, skdBuf.toString());
		}
	}
	
	private int replaceForTextFont(StringBuffer contentBuf, int replaceSize,
			String head, String sizePrefix, String sizePostfix, 
			ArrayList<Integer> storageList, int storeOffset) {
		boolean store = false;
		if (storageList == null || storageList.size() <= storeOffset) {
			store = true;
		}
		int headPos = 0;
		int sizePrefixPos = 0;
		int sizePos = 0;
		int sizePostfixPos = 0;
		int size = 0;
		String sizeText = null;
		while ((headPos = contentBuf.indexOf(head, headPos)) != -1) {
			sizePrefixPos = contentBuf.indexOf(sizePrefix, 
					headPos + head.length());
			sizePos = sizePrefixPos + sizePrefix.length();
			sizePostfixPos = contentBuf.indexOf(sizePostfix, sizePos);
			size = Integer.parseInt(
					contentBuf.substring(sizePos, sizePostfixPos).trim());
			if (store) {
				if (storageList != null)
					storageList.add(size);
				size += replaceSize - TEXT_SIZE_INIT/*(TEXT_SIZE_MAX + TEXT_SIZE_MIN) / 2*/;
			}
			else {
				size = storageList.get(storeOffset++) + replaceSize - TEXT_SIZE_INIT
							/*(TEXT_SIZE_MAX + TEXT_SIZE_MIN) / 2*/;
			}
			sizeText = "" + size;
			contentBuf.replace(sizePos, sizePostfixPos, sizeText);
			headPos = sizePos + sizeText.length() + sizePostfix.length() ;
		}
		return storeOffset;
	}
	
	public void addPropertyChangeListener(String propertyName, 
			PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}
	
	public void removePropertyChangeListener(String propertyName, 
			PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}	
	
	/***************Getters and setters***************************/
	/*public boolean getTextSelectionMode() {
		return textSelectionMode;
	}

	public void setTextSelectionMode(boolean textSelectionMode) {
		if (this.textSelectionMode != textSelectionMode) {
			boolean oldValue = this.textSelectionMode;
			this.textSelectionMode = textSelectionMode;
			changeSupport.firePropertyChange(PROPERTY_TEXT_SELECTION_MODE, 
					oldValue, textSelectionMode);
		}
	}*/

	public String getCacheDir() {
		return cacheDir;
	}
	
	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		if (this.textSize != textSize) {
			int oldValue = this.textSize;
			this.textSize = textSize;
			changeSupport.firePropertyChange(PROPERTY_TEXT_SIZE, 
					oldValue, textSize);
    		SharedPreferences.Editor editor = prefs.edit();
    		editor.putInt("textSize", this.textSize);
    		editor.commit();
		}
	}
	
	public int getListHeight() {
		return listHeight;
	}
	
	public boolean isFuncFolded() {
		return funcFolded;
	}
	
	public void setFuncFolded(boolean funcFolded) {
		if (this.funcFolded != funcFolded) {
			boolean oldValue = this.funcFolded;
			this.funcFolded = funcFolded;
			changeSupport.firePropertyChange(PROPERTY_FUNC_FOLDED, 
					oldValue, funcFolded);
		}
	}
	
	public void lockSelectState() {
		selecting = true;
	}
	
	public void unlockSelectState() {
		selecting = false;
	}
	
	public boolean getSelectState() {
		return selecting;
	}
	/*************************************************************/
	
	/*private static final int scaleListHeight(int textSize) {
		return textSize * (TEXT_SIZE_MIN + LIST_HEIGHT_PADDING) / TEXT_SIZE_MIN;
	}*/
	
	public static int dip2px(Context context, float dipValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(pxValue / scale + 0.5f);
	}
	
	/*************************************************************/
	
	public static interface PropertyRegisterable {
		void registerProperty();
		void unregisterProperty();
	}
}
