package com.besta.app.realteaching;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mylib.exceptioninfo.MyErrorPrintfActivity;
import com.android.mylib.exceptioninfo.MyExceptionInfo;
import com.android.mylib.loadingview.MyLoadingView;
import com.android.mylib.network.MyWifiInfo;
import com.besta.app.realteaching.dict.engine.listeners.SelectionListener;
import com.besta.app.syncourses.main.GetfileMethod;
import com.besta.flash.FlashView;
import com.besta.flash.FlashView2;
import com.besta.misc.BestaTad;
import com.besta.os.BestaEnvironment;
import com.besta.util.config.WebViewConfig;
import com.besta.view.crosssearchwin.CrossSearchInfo;
import com.besta.view.crosssearchwin.RetrievalPopUpWindow;
import com.besta.view.crosssearchwin.sign.SignHelper;

public class CrossWebViewRealTeachingActivity extends Activity {
	// for using debug app
	public boolean DebugFlag = true;
	public static String debugStr = "RealTeachingActivity--->";

	// play flash path and extern name
	public static String flashPath = "";
	public static String flashExtendName = ".bfe";

	// call bestamarket download new content download app
	public static final String BESTA_MARKET_DOWNLOAD = "bestamarket://downloads?";
	public static final String BESTA_MARKET_DOWNLOAD_LIST_KEY = "pkg_list=";

	// Message for activity handler
	public static final int DOWNLOADWAIT = 0x0011;
	public static final int DOWNLOADBEGIN = 0x0012;
	public static final int HANDLER_MSG_LOADING_DATA_SUCCESS = 0x0020;
	public static final int HANDLER_MSG_CHECK_FLASH_UPDATE = 0x0021;
	public static final int HANDLER_MSG_SHOW_UPDATE_FLASH_BT = 0x0022;
	public static final int HANDLER_MSG_WIFI_CONNECT_ERROR = 0x0023;
	public static final int HANDLER_MSG_LOADING_DIY_DATA_SUCCESS = 0x0024;
	public static final int HANDLER_MSG_HAVE_FLASH_UPDATE = 0x0025; // 有需要下載或更新的flash文件
	public static final int HANDLER_MSG_NOT_HAVE_FLASH_UPDATE = 0x0026; // 沒有需要下載或更新的flash文件
	public static final int HANDLER_MSG_DOWNLOADFLASH_SUCCEED = 0x0027; // 下載文件成功
	public static final int HANDLER_MSG_WIFI_ISCONNECTED = 0x0028; // wifi連接成功
	public static final int HANDLER_MSG_WIFI_ISDISCONNECTED = 0x0029; // wifi連接斷開
	public static final int HANDLER_MSG_DOWNLOADFLASH_FAIL_OTHER = 0x002A; // 因不明原因導致下載失敗
	public static final int HANDLER_MSG_DOWNLOADFLASH_FAIL_SDCARD = 0x002B; // 因SDCARD滿導致下載失敗
	public static final int HANDLER_MSG_DOWNLOADFLASH_CANCELALL = 0x002C; // 取消所有的下載
	public static final int HANDLER_MSG_DOWNLOADFLASH_FAIL_NOWIFI = 0x002D;
	public static final int HANDLER_MSG_DOWNLOADFLASH_STATE_CHANGED = 0x002E;
	public static final int HANDLER_MSG_READ_DATA_ERROR = 0x002F;

	// my using
	public static final String ACTIVITY_ACTION = "android.besta.app.realteaching.main";
	public static final String KNOWLEDGE_ID_KEY = "KNOWLEDGE_ID_KEY";
	public static final String KNOWLEDGE_DATA_PATH_KEY = "KNOWLEDGE_DATA_PATH_KEY";
	public static final String KNOWLEDGE_ID_NAME_KEY = "KNOWLEDGE_ID_NAME_KEY";
	public static final String ALL_KNOWLEDGE_ID = "ALL_KnowledgeIDs";
	public static final String CLICK_KNOWLEDGE_ID = "Click_KnowledgeID";

	// varible value for create one Main View of widges
	public static final int BT_TEST = 0; // test button
	public static final int BT_DIY = 1; // diy button
	public static final int BT_UPDATE_FLASH = 2; // flash update button

	private static final String total_pkg_name = "PublicDatabase";

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

	/** Called when the activity is first created. */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		isActivityDestroy = true;
		// android.os.Process.killProcess(android.os.Process.myPid());
		for (int count = 0; null != view && count < totalData.size(); count++) {
			if (null != view[count]) {
				switch (selectOneItemLayoutVer) {
				case 1: // 4.0
					FlashView2 flashView = (FlashView2) view[count]
							.findViewById(R.id.play_flash_webview);
					if (null != flashView) {
						flashView.doDestroy();
						// flashView.destroy();
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
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		for (int count = 0; null != view && count < totalData.size(); count++) {
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
			isFirstShowFlashView = false;
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		getFlashDownloadState(true);
		reFreshDownloadIconStatus(mContext, selectOneItemLayoutVer);
		checkFlashIsNeedUpdate();
		super.onResume();
	}

	/**
	 * @describe Get Flash Download state
	 * 
	 * @param checkUpdateFlag
	 */

	public void getFlashDownloadState(boolean checkUpdateFlag) {
		if (null == mContext) {
			return;
		}
		ContentResolver contentResolver = mContext.getContentResolver();
		Uri contentsUri = Uri
				.parse("content://com.besta.util.contentsdownload/downloaditem");
		String[] column = new String[] { "PackageName", "FileName",
				"DownloadState" };
		Cursor cursor = null;

		for (int i = 0; i < totalData.size(); i++) {
			MyOneData m_array = totalData.get(i);
			if (m_array.flashFileName.isEmpty()) {
				continue;
			}
			String filename = m_array.flashFileName + ".bfe";
			cursor = contentResolver.query(contentsUri, column, "PackageName=?"
					+ " and" + " FileName=?", new String[] { total_pkg_name,
					filename }, null);
			if (cursor == null) {
				m_array.isDownloading = false;
				continue;
			}
			if (cursor.moveToFirst()) {
				int index = cursor.getColumnIndex("DownloadState");
				int state = cursor.getInt(index);
				if (state == DOWNLOADWAIT || state == DOWNLOADBEGIN) {
					m_array.flashFileStatus = MyOneData.FLASH_FILE_DOWNLOADING;
					m_array.isDownloading = true;
				}
			} else {
				if (MyOneData.FLASH_FILE_DOWNLOADING == m_array.flashFileStatus) {
					m_array.flashFileStatus = MyOneData.FLASH_FILE_NEED_DOWNLOAD;
				}
				m_array.isDownloading = false;
				m_array.isUpdating = false;
			}

			if (cursor != null) {
				cursor.close();
			}
			if (checkUpdateFlag && !m_array.isDownloading) {
				String localFlashPath = getFlashPath(m_array.flashFileName
						+ ".bfe");
				File localFlashFile = new File(localFlashPath);// updateFlashId[i]//packagename
				m_array.localFileLastModifedTime = localFlashFile
						.lastModified();
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("lastModifiedTime",
							m_array.localFileLastModifedTime);
					jsonObject.put("fileName", m_array.flashFileName + ".bfe");// updateFlashId[i]
					if (null != inputJson) {
						inputJson.put(jsonObject);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @describe get flash download state
	 * 
	 * @param oneDownloadFile
	 * @return boolean
	 */

	public boolean getFlashDownloadState(String oneDownloadFile) {
		boolean ret = false;
		if (null == mContext || oneDownloadFile.isEmpty()) {
			return ret;
		}
		ContentResolver contentResolver = mContext.getContentResolver();
		Uri contentsUri = Uri
				.parse("content://com.besta.util.contentsdownload/downloaditem");
		String[] column = new String[] { "PackageName", "FileName",
				"DownloadState" };
		Cursor cursor = null;
		cursor = contentResolver.query(contentsUri, column, "PackageName=?"
				+ " and" + " FileName=?", new String[] { total_pkg_name,
				oneDownloadFile }, null);
		if (cursor == null) {
			ret = false;
		} else if (cursor.moveToFirst()) {
			int index = cursor.getColumnIndex("DownloadState");
			int state = cursor.getInt(index);
			if (state == DOWNLOADWAIT || state == DOWNLOADBEGIN) {
				ret = true;
			}
		} else {
			ret = false;
		}

		if (cursor != null) {
			cursor.close();
		}
		return ret;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		// mContext = this;
		// pkg_name = this.getPackageName();
		// Bundle bundle = getIntent().getExtras();
		// if (null != bundle) {
		// oneKnowledgeID = bundle.getString(TestaCallApp.KNOWLEDGE_ID_KEY);
		// knowledgeDataPath = bundle
		// .getString(TestaCallApp.KNOWLEDGE_DATA_PATH_KEY);
		// oneKnowledgeName = bundle
		// .getString(TestaCallApp.KNOWLEDGE_ID_NAME_KEY);
		// }

		// initializeData(knowledgeDataPath, oneKnowledgeID);
		super.onNewIntent(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			/*
			 * if (null != view) { for (int count = 0; null != view && count <
			 * totalData.size(); count++) { if (null != view[count]) { FlashView
			 * flashView = (FlashView) view[count]
			 * .findViewById(R.id.play_flash_webview); if (null != flashView) {
			 * // 設置PlayFlash imgView ImageView playFlashImgView = (ImageView)
			 * view[count] .findViewById(R.id.play_flash_view_img); ImageView
			 * playFlashBackgroundImgView = (ImageView) view[count]
			 * .findViewById(R.id.play_flash_view_bg_img); // 更新Flash imgView
			 * ImageView updateFlashImgView = (ImageView) view[count]
			 * .findViewById(R.id.play_flash_view_img_update);
			 * 
			 * playFlashImgView.setVisibility(View.VISIBLE);
			 * playFlashBackgroundImgView .setVisibility(View.VISIBLE);
			 * 
			 * settingPlayFlashImageView(playFlashImgView,
			 * playFlashBackgroundImgView, updateFlashImgView,
			 * totalData.get(0));
			 * 
			 * // flashView.doPause(); flashView.doPause();
			 * flashView.doDestroy(); flashView.setVisibility(View.GONE); //
			 * flashView.doResume(); } } } } return true;
			 */
		}
		return super.onKeyDown(keyCode, event);
	}

	String pkg_name = "";
	boolean playingFlag = false;
	static Context mContext = null;
	long summaryDataOffset = 0;
	ArrayList<MyKnowledgIndexData> myKnowledgeIndexData = new ArrayList<MyKnowledgIndexData>();
	public ArrayList<MyOneData> nowListData = new ArrayList<MyOneData>();
	public ArrayList<MyOneData> totalData = new ArrayList<MyOneData>();
	String searchBuf = null;
	RetrievalPopUpWindow popUpWindow = null;
	int click_knowledgeID_count = 0;
	String knowledgeDataPath = "";
	String oneKnowledgeID = "";
	String oneKnowledgeName = "";
	ArrayList<String> all_knowledgeID = new ArrayList<String>();
	String divide_str = "|";
	public int my_course_id = 0;
	public MyDataFlags buttonFlags = new MyDataFlags();
	boolean[] buttonDownFlag = { false, // test_bt
			false, // diy_bt
			false, // update flash button
	};
	Button[] relateButton = new Button[buttonDownFlag.length];
	LoadingDataThread loadingData = null;
	boolean isWifiConnect = false;
	int selectOneItemLayoutVer = 0;
	boolean isFirstShowFlashView = true;
	boolean[] isFlashPlaying = null;
	boolean someOneFlashPlaying = false;
	boolean isActivityDestroy = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Toast.makeText(this, "start Activity", Toast.LENGTH_SHORT).show();
		if (!isAdobeFlashInstalled(this)) {
			super.onCreate(savedInstanceState);
			Toast toask;
			toask = Toast.makeText(this, R.string.NoAdobeFlash,
					Toast.LENGTH_SHORT);
			toask.show();
			new Handler().postDelayed(new Runnable() {
				public void run() {
					finish();
				}
			}, 1000);
			return;
		}
		super.onCreate(null);
		setContentView(R.layout.real_teaching_main);
		mContext = this;
		pkg_name = this.getPackageName();
		Bundle bundle = getIntent().getExtras();
		if (null != bundle) {
			oneKnowledgeID = bundle.getString(KNOWLEDGE_ID_KEY);
			knowledgeDataPath = bundle.getString(KNOWLEDGE_DATA_PATH_KEY);
			oneKnowledgeName = bundle.getString(KNOWLEDGE_ID_NAME_KEY);
			all_knowledgeID = bundle.getStringArrayList(ALL_KNOWLEDGE_ID);
			click_knowledgeID_count = bundle.getInt(CLICK_KNOWLEDGE_ID);
		}

		initializeData(knowledgeDataPath, oneKnowledgeID);
	}

	// View[] create All view using
	View[] view = null;
	int playingCount = -1;

	/**
	 * @describe create main View
	 * 
	 * @param context
	 *            : this activity context
	 */

	public void createAllView(Context context) {
		ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		// main layout
		LinearLayout mainViewLayout = (LinearLayout) ((Activity) context)
				.findViewById(R.id.main_layout);
		LayoutInflater inflater = LayoutInflater.from(context);

		// setting title name
		TextView tv_titleBar = (TextView) findViewById(R.id.my_course_name_tv);
		if (null != tv_titleBar) {
			tv_titleBar.setText(MyDataPath.RES_DATA_TITLE_NAME[my_course_id]);
			// tv_titleBar.setTextColor(Color.BLACK);
		}

		int view_res_id = R.layout.one_item_view;
		int totalCount = totalData.size();
		int count = 0;

		view = new View[totalCount];
		isFlashPlaying = new boolean[totalCount];

		for (count = 0; count < totalCount; count++) {
			try {
				view[count] = inflater.inflate(view_res_id, null);
				selectOneItemLayoutVer = 1; // 4.0 FlashView2
			} catch (Exception e) {
				view_res_id = R.layout.one_item_view_23;
				try {
					view[count] = inflater.inflate(view_res_id, null);
					selectOneItemLayoutVer = 2; // 2.3 FlashView
				} catch (Exception e1) {
					if (DebugFlag) {
						MyErrorPrintfActivity.startMyErrorPrintfActivity(this,
								e1);
					} else {
						e1.printStackTrace();
						Toast.makeText(this,
								getString(R.string.not_found_data),
								Toast.LENGTH_SHORT).show();
					}
					finish();
				}
			}
			if (null != view[count]) {
				// setting 數據
				isFlashPlaying[count] = false;
				MyOneData oneData = totalData.get(count);
				ImageView closeFlashBt = (ImageView) view[count]
						.findViewById(R.id.close_play_flash_bt);
				if (null != closeFlashBt) {
					setClosePlayFlashButtonListener(context, closeFlashBt);
				}
				settingOneItemLayoutVer(context, view[count], oneData, count,
						selectOneItemLayoutVer);
				// 設置概述
				MyCrossWebView summaryWebView = (MyCrossWebView) view[count]
						.findViewById(R.id.summary_webview);
				// LayoutParams lp = summaryWebView.getLayoutParams();
				// DisplayMetrics dm = new DisplayMetrics();
				// getWindowManager().getDefaultDisplay().getMetrics(dm);
				// int height = dm.heightPixels;
				// int width = dm.widthPixels;
				// lp.height = height - 100;
				// summaryWebView.setLayoutParams(lp);
				if (1 == buttonFlags.is_has_summary) {
					LinearLayout summary_wv_layout = (LinearLayout) view[count]
							.findViewById(R.id.summary_wv_layout);
					summary_wv_layout.setVisibility(View.VISIBLE);
					WebSettings webSet = summaryWebView.getSettings();
					webSet.setJavaScriptEnabled(true);
					summaryWebView.getSettings().setSupportZoom(true);
					WebViewConfig.useSelectionMenu(summaryWebView, false);
					summaryWebView.addJavascriptInterface(
							new JavaScriptInterface(summaryWebView), "html");

					StringBuffer contentBuf = new StringBuffer(
							"<?xml version=\"1.0\" encoding=\"utf-16le\"?>");
					oneData.summaryContent = voiceOrderBuffer_Head(oneData.summaryContent);
					contentBuf.append(oneData.summaryContent);
					summaryWebView.loadDataWithBaseURL("file://",
							contentBuf.toString(), "text/html", "utf-8", null);
					// summaryWebView.loadUrl("file://mnt/sdcard/1.html");
					summaryWebView.requestFocus(); // 如果設置此項會造成ScrollView的滾動
					SelectionListener selectionListener = new DictViewSelectionListener(
							summaryWebView);
					// summaryWebView.setSelectionListener(selectionListener);
					summaryWebView.setWebViewClient(new WebViewClient() {
						@Override
						public boolean shouldOverrideUrlLoading(WebView view,
								String url) {
							// TODO Auto-generated method stub
							return super.shouldOverrideUrlLoading(view, url);
						}
					});
				} else {
					LinearLayout summary_wv_layout = (LinearLayout) view[count]
							.findViewById(R.id.summary_wv_layout);
					summary_wv_layout.setVisibility(View.GONE);
				}

				mainViewLayout.addView(view[count]);
			}
		}
	}

	public static String voiceOrderBuffer_Head(String buffer) {
		// buffer =
		// "解析:題目@@2K7 1的<B>be 動詞</B>為 are，主詞補語為複數的 Tim's friends；因此主詞@@2K4 1也應為複數的 These。";
		int index = 0;
		while ((index = buffer.indexOf("@@", index)) >= 0) {
			String word = buffer.substring(index + 5 + 2, index + 5 + 2 + 1);
			String code = buffer.substring(index, index + 5 + 2);

			String des = "<PRONOUCE_LIST value=\"" + code + "\">" + word
					+ "</PRONOUCE_LIST>";
			buffer = buffer.substring(0, index) + des
					+ buffer.substring(index + 5 + 2 + 1);
			// buffer = buffer.replace(code + word, des);
			index = index + des.length();
		}

		// buffer.contains()//<PRONOUCE_LIST value=”@@×××××”>字</PRONOUCE_LIST>

		return buffer;
	}

	public void setClosePlayFlashButtonListener(final Context context,
			ImageView v) {
		v.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundResource(R.drawable.popup_menu_close_dn);
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundResource(R.drawable.popup_menu_close_up);
					break;
				}
				return false;
			}
		});

		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onCloseOldPlayingFlashView(context, v);
				addNewPlayFlashView(context, v);
			}
		});
	}

	public void onCloseOldPlayingFlashView(Context context, View v) {
		// main layout
		LinearLayout mainViewLayout = (LinearLayout) ((Activity) context)
				.findViewById(R.id.main_layout);
		if (null != mainViewLayout) {
			for (int count = 0; null != view && count < totalData.size(); count++) {
				if (null != view[count]) {
					switch (selectOneItemLayoutVer) {
					case 1: // 4.0
						FlashView2 flashView = (FlashView2) view[count]
								.findViewById(R.id.play_flash_webview);
						if (null != flashView) {
							// flashView.doPause();
							flashView.onPause();
							flashView.doDestroy();
							// flashView.destroy();
							flashView = null;
						}
						break;
					case 2:
						FlashView flashView23 = (FlashView) view[count]
								.findViewById(R.id.play_flash_webview);
						if (null != flashView23) {
							flashView23.doPause();
							flashView23.doDestroy();
							flashView23 = null;
						}
						break;
					}
					mainViewLayout.removeView(view[count]);
					view[count] = null;
					isFlashPlaying[count] = false;
				}
			}
		}
	}

	public void addNewPlayFlashView(Context context, View v) {
		// main layout
		LinearLayout mainViewLayout = (LinearLayout) ((Activity) context)
				.findViewById(R.id.main_layout);
		if (null != mainViewLayout) {
			int childViewCount = mainViewLayout.getChildCount();
			childViewCount = 0;
			ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			LayoutInflater inflater = LayoutInflater.from(context);
			int view_res_id = R.layout.one_item_view;
			int count = 0;
			for (count = 0; count < view.length; count++) {
				try {
					view[count] = inflater.inflate(view_res_id, null);
					selectOneItemLayoutVer = 1; // 4.0 FlashView2
				} catch (Exception e) {
					view_res_id = R.layout.one_item_view_23;
					try {
						view[count] = inflater.inflate(view_res_id, null);
						selectOneItemLayoutVer = 2; // 2.3 FlashView
					} catch (Exception e1) {
						if (DebugFlag) {
							MyErrorPrintfActivity.startMyErrorPrintfActivity(
									this, e1);
						} else {
							e1.printStackTrace();
							Toast.makeText(this,
									getString(R.string.not_found_data),
									Toast.LENGTH_SHORT).show();
						}
						finish();
					}
				}
				if (null != view[count]) {
					// setting 數據
					MyOneData oneData = totalData.get(count);
					ImageView closeFlashBt = (ImageView) view[count]
							.findViewById(R.id.close_play_flash_bt);
					if (null != closeFlashBt) {
						setClosePlayFlashButtonListener(context, closeFlashBt);
					}
					settingOneItemLayoutVer(context, view[count], oneData,
							count, selectOneItemLayoutVer);
					// 設置概述
					MyCrossWebView summaryWebView = (MyCrossWebView) view[count]
							.findViewById(R.id.summary_webview);
					if (1 == buttonFlags.is_has_summary) {
						LinearLayout summary_wv_layout = (LinearLayout) view[count]
								.findViewById(R.id.summary_wv_layout);
						summary_wv_layout.setVisibility(View.VISIBLE);
						WebSettings webSet = summaryWebView.getSettings();
						webSet.setJavaScriptEnabled(true);
						summaryWebView.getSettings().setSupportZoom(true);
						WebViewConfig.useSelectionMenu(summaryWebView, false);
						summaryWebView
								.addJavascriptInterface(
										new JavaScriptInterface(summaryWebView),
										"html");

						StringBuffer contentBuf = new StringBuffer(
								"<?xml version=\"1.0\" encoding=\"utf-16le\"?>");
						contentBuf.append(oneData.summaryContent);
						summaryWebView.loadDataWithBaseURL("file://",
								contentBuf.toString(), "text/html", "utf-8",
								null);
						// summaryWebView.loadUrl("file://mnt/sdcard/1.html");
						summaryWebView.requestFocus(); // 如果設置此項會造成ScrollView的滾動
						SelectionListener selectionListener = new DictViewSelectionListener(
								summaryWebView);
						summaryWebView.setSelectionListener(selectionListener);
						summaryWebView.setWebViewClient(new WebViewClient() {
							@Override
							public boolean shouldOverrideUrlLoading(
									WebView view, String url) {
								// TODO Auto-generated method stub
								return super
										.shouldOverrideUrlLoading(view, url);
							}
						});
					} else {
						LinearLayout summary_wv_layout = (LinearLayout) view[count]
								.findViewById(R.id.summary_wv_layout);
						summary_wv_layout.setVisibility(View.GONE);
					}

					mainViewLayout.addView(view[count]);
				}
			}
		}
	}

	/**
	 * @describe Refresh download Icon status for example: downloading, update
	 * 
	 * @param context
	 *            : this Activity
	 * @param selectLayoutVer
	 *            : chos 2.3 or 4.0 FlashView widge
	 */
	public void reFreshDownloadIconStatus(Context context, int selectLayoutVer) {
		for (int i = 0; null != view && i < view.length; i++) {

			if (null != view[i]) {
				// setting 數據
				MyOneData oneData = totalData.get(i);
				// settingOneItemLayoutVer(context, view[i], oneData, i,
				// selectOneItemLayoutVer);
				switch (selectLayoutVer) {
				case 1: // 4.0
					// 設置PlayFlash View
					FlashView2 flashView = (FlashView2) view[i]
							.findViewById(R.id.play_flash_webview);
					if (null != flashView) {
						LayoutParams lp = flashView.getLayoutParams();
						// lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
						// lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
						// // lp.height = 430;
						// flashView.setLayoutParams(lp);
						if (isFirstShowFlashView) {
							flashView.setVisibility(View.GONE);
						} else if (isFlashPlaying[i]) {
							// flashView.setVisibility(View.GONE);
							boolean isClassDef = false;
							String clssName = "com.besta.hardware.BestaHDMI";
							isClassDef = MyExceptionInfo.checkTargetClassExist(
									context, "com.besta.app.realteaching",
									clssName);
							isClassDef = true;
							if (isClassDef) {
								try {
									flashView.onResume();
								} catch (Exception e) {
									MyErrorPrintfActivity
											.startMyErrorPrintfActivity(
													context, e);
									((Activity) context).finish();
								}
								return;
							} else {
								if (DebugFlag) {
									String[] error = getNoBestaHDMIerrorInfo();
									MyErrorPrintfActivity
											.startMyErrorStringActivity(
													context, error);
								} else {
									Toast.makeText(context,
											R.string.appShutdownStr,
											Toast.LENGTH_SHORT).show();
								}
								((Activity) context).finish();
							}
						}
					}
					// 設置PlayFlash imgView
					ImageView playFlashImgView = (ImageView) view[i]
							.findViewById(R.id.play_flash_view_img);
					ImageView playFlashBackgroundImgView = (ImageView) view[i]
							.findViewById(R.id.play_flash_view_bg_img);
					// 更新Flash imgView
					ImageView updateFlashImgView = (ImageView) view[i]
							.findViewById(R.id.play_flash_view_img_update);

					if (null != playFlashImgView
							&& null != playFlashBackgroundImgView
							&& null != updateFlashImgView) {
						playFlashImgView.setVisibility(View.VISIBLE);
						playFlashBackgroundImgView.setVisibility(View.VISIBLE);
						settingPlayFlashImageView(playFlashImgView,
								playFlashBackgroundImgView, updateFlashImgView,
								oneData);
					}
					break;
				case 2: // 2.3
					// 設置PlayFlash View
					FlashView flashView23 = (FlashView) view[i]
							.findViewById(R.id.play_flash_webview);
					if (null != flashView23) {
						LayoutParams lp = flashView23.getLayoutParams();
						if (isFirstShowFlashView) {
							flashView23.setVisibility(View.GONE);
						} else if (isFlashPlaying[i]) {
							// flashView.setVisibility(View.GONE);
							boolean isClassDef = false;
							String clssName = "com.besta.hardware.BestaHDMI";
							isClassDef = MyExceptionInfo.checkTargetClassExist(
									context, "com.besta.app.realteaching",
									clssName);
							isClassDef = true;
							if (isClassDef) {
								try {
									flashView23.onResume();
									// flashView23.doResume();
								} catch (Exception e) {
									MyErrorPrintfActivity
											.startMyErrorPrintfActivity(
													context, e);
									((Activity) context).finish();
								}
								return;
							} else {
								if (DebugFlag) {
									String[] error = getNoBestaHDMIerrorInfo();
									MyErrorPrintfActivity
											.startMyErrorStringActivity(
													context, error);
								} else {
									Toast.makeText(context,
											R.string.appShutdownStr,
											Toast.LENGTH_SHORT).show();
								}
								((Activity) context).finish();
							}
						}
					}
					// 設置PlayFlash imgView
					ImageView playFlashImgView23 = (ImageView) view[i]
							.findViewById(R.id.play_flash_view_img);
					ImageView playFlashBackgroundImgView23 = (ImageView) view[i]
							.findViewById(R.id.play_flash_view_bg_img);
					// 更新Flash imgView
					ImageView updateFlashImgView23 = (ImageView) view[i]
							.findViewById(R.id.play_flash_view_img_update);

					if (null != playFlashImgView23
							&& null != playFlashBackgroundImgView23
							&& null != updateFlashImgView23) {
						settingPlayFlashImageView(playFlashImgView23,
								playFlashBackgroundImgView23,
								updateFlashImgView23, oneData);
					}
					break;
				}
			}
		}
	}

	/**
	 * @describe bug for FlashView2 widge. It cannot found BestaHDMI.class
	 * 
	 * @return String[] : return info for using the Debug app
	 */
	public String[] getNoBestaHDMIerrorInfo() {
		String[] error = new String[25];
		error[0] = "error:";
		error[1] = "Shutting down VM";
		error[2] = "threadid=1: thread exiting with uncaught exception (group=0x40d69300)";
		error[3] = "FATAL EXCEPTION: main";
		error[4] = "java.lang.NoClassDefFoundError: com.besta.hardware.BestaHDMI";
		error[5] = "        at com.besta.flash.HDMIReceiver.doResume(HDMIReceiver.java:56)";
		error[6] = "        at com.besta.flash.FlashView2.doResume(FlashView2.java:387)";
		error[7] = "        at com.besta.app.realteaching.RealTeachingActivity.reFreshDownloadIconStatus(RealTeachingActivity.java:551)";
		error[8] = "        at com.besta.app.realteaching.RealTeachingActivity.onResume(RealTeachingActivity.java:207)";
		error[9] = "        at android.app.Instrumentation.callActivityOnResume(Instrumentation.java:1184)";
		error[10] = "        at android.app.Activity.performResume(Activity.java:5082)";
		error[11] = "        at android.app.ActivityThread.performResumeActivity(ActivityThread.java:2565)";
		error[12] = "        at android.app.ActivityThread.handleResumeActivity(ActivityThread.java:2603)";
		error[13] = "        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1237)";
		error[14] = "        at android.os.Handler.dispatchMessage(Handler.java:99)";
		error[15] = "        at android.os.Looper.loop(Looper.java:137)";
		error[16] = "        at android.app.ActivityThread.main(ActivityThread.java:4745)";
		error[17] = "        at java.lang.reflect.Method.invokeNative(NativeMethod)";
		error[18] = "        at java.lang.reflect.Method.invoke(Method.java:511)";
		error[19] = "        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:786)";
		error[20] = "        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:553)";
		error[21] = "        at dalvik.system.NativeStart.main(Native Method)";
		error[22] = "\r\n";
		error[23] = "   Force finishing activity com.besta.app.realteaching/.RealTeachingActivity";
		error[24] = "Activity pause timeout for ActivityRecord{416b23e8 com.besta.app.realteaching/.RealTeachingActivity}";
		return error;
	}

	/**
	 * @describe setting one FlashView(WebView). Include one item of FlashView
	 *           widge and Listeners
	 * 
	 * @param context
	 *            : this Activity
	 * @param v
	 *            : this item view
	 * @param oneData
	 *            : MyOneData class struct
	 * @param count
	 *            : this item count
	 * @param selectLayoutVer
	 *            : chos 2.3 of 4.0 FlashView widge
	 */
	public void settingOneItemLayoutVer(Context context, View v,
			MyOneData oneData, final int count, int selectLayoutVer) {
		ImageView closePlayingFlash = (ImageView) v
				.findViewById(R.id.close_play_flash_bt);
		if (null != closePlayingFlash) {
			if (isFlashPlaying[count]) {
				closePlayingFlash.setVisibility(View.VISIBLE);
			} else {
				closePlayingFlash.setVisibility(View.GONE);
			}
		}
		switch (selectLayoutVer) {
		case 1: // 4.0
			// setting PlayFlash View
			FlashView2 flashView = (FlashView2) v
					.findViewById(R.id.play_flash_webview);
			if (null != flashView) {
				LayoutParams lp = flashView.getLayoutParams();
				lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
				lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
				// lp.height = 430;
				flashView.setLayoutParams(lp);
				flashView.setVisibility(View.GONE);
			}
			// setting PlayFlash imgView
			ImageView playFlashImgView = (ImageView) v
					.findViewById(R.id.play_flash_view_img);
			ImageView playFlashBackgroundImgView = (ImageView) v
					.findViewById(R.id.play_flash_view_bg_img);
			// setting Flash imgView
			ImageView updateFlashImgView = (ImageView) v
					.findViewById(R.id.play_flash_view_img_update);

			if (null != playFlashImgView && null != playFlashBackgroundImgView
					&& null != updateFlashImgView) {
				settingPlayFlashImageView(playFlashImgView,
						playFlashBackgroundImgView, updateFlashImgView, oneData);

				MyPlayFlashImgViewOnTouchListener playFlashOnTouchListener = new MyPlayFlashImgViewOnTouchListener(
						context, playFlashImgView, oneData, count, flashView,
						null, flashPath) {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						int imgViewResId = 0;
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							if (MyOneData.FLASH_FILE_NEED_DOWNLOAD == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_download_dn;
							} else if (MyOneData.FLASH_FILE_DOWNLOADING == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_downloading_dn;
							} else if (MyOneData.FLASH_FILE_EXIST == mOneData.flashFileStatus
									|| MyOneData.FLASH_FILE_NEED_UPDATE == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_play_dn;
							}
							((ImageView) v).setImageResource(imgViewResId);
							break;
						case MotionEvent.ACTION_UP:
						case MotionEvent.ACTION_CANCEL:
							if (MyOneData.FLASH_FILE_NEED_DOWNLOAD == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_download_up;
							} else if (MyOneData.FLASH_FILE_DOWNLOADING == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_downloading_up;
							} else if (MyOneData.FLASH_FILE_EXIST == mOneData.flashFileStatus
									|| MyOneData.FLASH_FILE_NEED_UPDATE == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_play_up;
							}

							((ImageView) v).setImageResource(imgViewResId);
							break;
						}
						return false;
						// return super.onTouch(v, event);
					}

				};

				MyPlayFlashImgViewOnClickListener playFlashOnClickListener = new MyPlayFlashImgViewOnClickListener(
						context, playFlashImgView, closePlayingFlash, oneData,
						count, flashView, null, flashPath) {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// onStopOneFlashPlaying();
						if (DebugFlag) {
							Log.i(debugStr, mOneData.flashFileName
									+ flashExtendName);
						}
						if (MyOneData.FLASH_FILE_DOWNLOADING == mOneData.flashFileStatus) {
							downloadFlashFile(mOneData);
							return;
						}
						// String realPath = getRealFlashFilePath(mOneData, 0);
						String realPath = GetfileMethod.getfilepath(
								MyDataPath.FLASH_DATA_PATH[my_course_id]
										+ mOneData.flashFileName
										+ flashExtendName, context);
						// realPath =
						// "/mnt/sdcard/besta/video_teaching/jur_math/m101010.bfe";
						if (null != realPath && !realPath.isEmpty()) {
							this.playFlashImgView.setVisibility(View.GONE);
							if (null != flashView) {
								LayoutParams lp = flashView.getLayoutParams();
								lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = 430;
								flashView.setLayoutParams(lp);
								flashView.setVisibility(View.VISIBLE);
								flashView.ShowFlash(realPath, 0, 0, false);
							} else if (null != flashView23) {
								LayoutParams lp = flashView23.getLayoutParams();
								lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = 430;
								flashView23.setLayoutParams(lp);
								flashView23.setVisibility(View.VISIBLE);
								flashView23.ShowFlash(realPath, 0, 0, false);
							}
							isFlashPlaying[count] = true;
							if (null != this.closePlayFlashBt) {
								if (isFlashPlaying[count]) {
									closePlayFlashBt
											.setVisibility(View.VISIBLE);
								} else {
									closePlayFlashBt.setVisibility(View.GONE);
								}
							}
							playingCount = this.nowFlashCount;
							this.isDisplayImgView = false;
						} else {
							// downloading... set imgview
							downloadFlashFile(mOneData);
							isWifiConnect = MyWifiInfo
									.checkWifiConnect(mContext);
							if (isWifiConnect) {
								mOneData.flashFileStatus = MyOneData.FLASH_FILE_DOWNLOADING;
								((ImageView) v)
										.setImageResource(R.drawable.flashstatus_downloading_up);
							} else {
								mOneData.flashFileStatus = MyOneData.FLASH_FILE_NEED_DOWNLOAD;
								((ImageView) v)
										.setImageResource(R.drawable.flashstatus_download_up);
							}
						}
					}
				};
				playFlashImgView.setOnClickListener(playFlashOnClickListener);
				playFlashImgView.setOnTouchListener(playFlashOnTouchListener);
			}
			break;
		case 2: // 2.3
			// 設置PlayFlash View
			FlashView flashView23 = (FlashView) v
					.findViewById(R.id.play_flash_webview);
			if (null != flashView23) {
				LayoutParams lp = flashView23.getLayoutParams();
				lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
				lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
				flashView23.setLayoutParams(lp);
				flashView23.setVisibility(View.GONE);
			}
			// 設置PlayFlash imgView
			ImageView playFlashImgView23 = (ImageView) v
					.findViewById(R.id.play_flash_view_img);
			ImageView playFlashBackgroundImgView23 = (ImageView) v
					.findViewById(R.id.play_flash_view_bg_img);
			// 更新Flash imgView
			ImageView updateFlashImgView23 = (ImageView) v
					.findViewById(R.id.play_flash_view_img_update);

			if (null != playFlashImgView23
					&& null != playFlashBackgroundImgView23
					&& null != updateFlashImgView23) {
				settingPlayFlashImageView(playFlashImgView23,
						playFlashBackgroundImgView23, updateFlashImgView23,
						oneData);

				MyPlayFlashImgViewOnTouchListener playFlashOnTouchListener = new MyPlayFlashImgViewOnTouchListener(
						context, playFlashImgView23, oneData, count, null,
						flashView23, flashPath) {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						int imgViewResId = 0;
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							if (MyOneData.FLASH_FILE_NEED_DOWNLOAD == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_download_dn;
							} else if (MyOneData.FLASH_FILE_DOWNLOADING == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_downloading_dn;
							} else if (MyOneData.FLASH_FILE_EXIST == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_play_dn;
							}
							((ImageView) v).setImageResource(imgViewResId);
							break;
						case MotionEvent.ACTION_UP:
						case MotionEvent.ACTION_CANCEL:
							if (MyOneData.FLASH_FILE_NEED_DOWNLOAD == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_download_up;
							} else if (MyOneData.FLASH_FILE_DOWNLOADING == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_downloading_up;
							} else if (MyOneData.FLASH_FILE_EXIST == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_play_up;
							}

							((ImageView) v).setImageResource(imgViewResId);
							break;
						}
						return false;
						// return super.onTouch(v, event);
					}

				};

				MyPlayFlashImgViewOnClickListener playFlashOnClickListener = new MyPlayFlashImgViewOnClickListener(
						context, playFlashImgView23, closePlayingFlash,
						oneData, count, null, flashView23, flashPath) {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// onStopOneFlashPlaying();
						if (DebugFlag) {
							Log.i(debugStr, mOneData.flashFileName
									+ flashExtendName);
						}
						if (MyOneData.FLASH_FILE_DOWNLOADING == mOneData.flashFileStatus) {
							downloadFlashFile(mOneData);
							return;
						}
						// String realPath = getRealFlashFilePath(mOneData, 0);
						String realPath = GetfileMethod.getfilepath(
								MyDataPath.FLASH_DATA_PATH[my_course_id]
										+ mOneData.flashFileName
										+ flashExtendName, context);
						if (!realPath.isEmpty()) {
							this.playFlashImgView.setVisibility(View.GONE);
							if (null != flashView) {
								LayoutParams lp = flashView.getLayoutParams();
								lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = 430;
								flashView.setLayoutParams(lp);
								flashView.setVisibility(View.VISIBLE);
								flashView.ShowFlash(realPath, 0, 0, false);
							} else if (null != flashView23) {
								LayoutParams lp = flashView23.getLayoutParams();
								lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = 430;
								flashView23.setLayoutParams(lp);
								flashView23.setVisibility(View.VISIBLE);
								flashView23.ShowFlash(realPath, 0, 0, false);
							}
							isFlashPlaying[count] = true;
							if (null != this.closePlayFlashBt) {
								if (isFlashPlaying[count]) {
									closePlayFlashBt
											.setVisibility(View.VISIBLE);
								} else {
									closePlayFlashBt.setVisibility(View.GONE);
								}
							}
							playingCount = this.nowFlashCount;
							this.isDisplayImgView = false;
							// mOneData.flashFileStatus =
							// MyOneData.FLASH_FILE_EXIST;
						} else {
							// downloading... set imgview
							downloadFlashFile(mOneData);
							isWifiConnect = MyWifiInfo
									.checkWifiConnect(mContext);
							if (isWifiConnect) {
								mOneData.flashFileStatus = MyOneData.FLASH_FILE_DOWNLOADING;
								((ImageView) v)
										.setImageResource(R.drawable.flashstatus_downloading_up);
							} else {
								mOneData.flashFileStatus = MyOneData.FLASH_FILE_NEED_DOWNLOAD;
								((ImageView) v)
										.setImageResource(R.drawable.flashstatus_download_up);
							}
						}
					}
				};
				playFlashImgView23.setOnClickListener(playFlashOnClickListener);
				playFlashImgView23.setOnTouchListener(playFlashOnTouchListener);
			}
			break;
		}
	}

	/**
	 * @describe setting images of one flashview widge
	 * 
	 * @param playFlashImgView
	 *            : play flash ImageView widge
	 * @param playFlashBackgroundImgView
	 *            : Background of flashview(WebView)
	 * @param updateFlashImgView
	 *            : if this flash need to update and show this ImageView
	 * @param mOneData
	 *            : MyOneData class, that one FlashView(WebView) widge data
	 *            struct
	 * 
	 */
	public void settingPlayFlashImageView(ImageView playFlashImgView,
			ImageView playFlashBackgroundImgView, ImageView updateFlashImgView,
			MyOneData mOneData) {
		if (null == playFlashImgView || null == playFlashBackgroundImgView
				|| null == updateFlashImgView) {
			Toast.makeText(this, "playFlashImgView == null", Toast.LENGTH_SHORT)
					.show();
		} else {
			// setting image view
			// first get flash exist?
			// String playFlashFilePath = getRealFlashFilePath(mOneData, 1);
			String playFlashFilePath = GetfileMethod.getfilepath(
					MyDataPath.FLASH_DATA_PATH[my_course_id]
							+ mOneData.flashFileName + flashExtendName, this);
			int flashFileStatus = 0;
			int imgViewResId, imgBackgroundViewResId, imgUpdateViewResId;
			imgViewResId = imgBackgroundViewResId = imgUpdateViewResId = -1;

			if (null == playFlashFilePath || playFlashFilePath.isEmpty()) {
				// not exist
				flashFileStatus = 0;
				if (MyOneData.DEFUALT_VALUE == mOneData.flashFileStatus) {
					mOneData.flashFileStatus = MyOneData.FLASH_FILE_NEED_DOWNLOAD;
				} else if (MyOneData.FLASH_FILE_DOWNLOADING != mOneData.flashFileStatus) {
					mOneData.flashFileStatus = MyOneData.FLASH_FILE_NEED_DOWNLOAD;
				}
			} else {
				// then check is need update? (use a new thread for check)
				// now can use the exist icon for instead of this status
				flashFileStatus = 1;
				if (MyOneData.DEFUALT_VALUE == mOneData.flashFileStatus) {
					mOneData.flashFileStatus = MyOneData.FLASH_FILE_EXIST;
				} else if (MyOneData.FLASH_FILE_DOWNLOADING == mOneData.flashFileStatus) {
					if (0 == downloadFlashSuccess) {
						flashFileStatus = 0;
					} else if (1 == downloadFlashSuccess) {
						flashFileStatus = 1;
						mOneData.flashFileStatus = MyOneData.FLASH_FILE_EXIST;
					}
				} else if (MyOneData.FLASH_FILE_NEED_UPDATE == mOneData.flashFileStatus) {
					flashFileStatus = 2;
				} else if (MyOneData.FLASH_FILE_NEED_DOWNLOAD == mOneData.flashFileStatus) {
					mOneData.flashFileStatus = MyOneData.FLASH_FILE_EXIST;
				}
			}

			switch (flashFileStatus) {
			case 0:
				if (mOneData.isDownloading) {
					imgViewResId = R.drawable.flashstatus_downloading;
				} else {
					imgViewResId = R.drawable.flashstatus_download_up;
				}
				break;
			case 1:
				imgViewResId = R.drawable.flashstatus_play_up;
				break;
			case 2:
				imgViewResId = R.drawable.flashstatus_play_up;

				imgUpdateViewResId = R.drawable.flashstatus_update_up;
				break;
			}
			imgBackgroundViewResId = MyDataPath.RES_PRE_SHOW_PNG[my_course_id];

			if (-1 != imgViewResId) {
				playFlashImgView.setImageResource(imgViewResId);
			}
			if (-1 != imgBackgroundViewResId) {
				playFlashBackgroundImgView
						.setBackgroundResource(imgBackgroundViewResId);
				playFlashBackgroundImgView
						.setImageResource(R.drawable.play_flash_bg_color);
				ViewGroup.LayoutParams tmpLp = playFlashBackgroundImgView
						.getLayoutParams();
				// DisplayMetrics dm = new DisplayMetrics();
				// getWindowManager().getDefaultDisplay().getMetrics(dm);
				// int height = dm.heightPixels;
				// int width = dm.widthPixels;
				tmpLp.width = 580;
				tmpLp.height = 330;
				playFlashBackgroundImgView.setLayoutParams(tmpLp);
			}
			if (2 == flashFileStatus) {
				updateFlashImgView.setVisibility(View.VISIBLE);
				if (-1 != imgUpdateViewResId) {
					updateFlashImgView.setImageResource(imgUpdateViewResId);
				}
			} else {
				updateFlashImgView.setVisibility(View.GONE);
				playFlashImgView.setVisibility(View.VISIBLE);
				playFlashBackgroundImgView.setVisibility(View.VISIBLE);
			}

			if (null != updateFlashImgView) {
				updateFlashImgView
						.setOnTouchListener(new RelateButtonListner().new RelateButtonOntouchListener(
								this) {
							@Override
							public boolean onTouch(View v, MotionEvent event) {
								// TODO Auto-generated method stub
								switch (event.getAction()) {
								case MotionEvent.ACTION_DOWN:
									((ImageView) v)
											.setImageResource(R.drawable.flashstatus_update_dn);
									break;
								case MotionEvent.ACTION_UP:
								case MotionEvent.ACTION_CANCEL:
									((ImageView) v)
											.setImageResource(R.drawable.flashstatus_update_up);
									break;
								}
								return false;
								// return super.onTouch(v, event);
							}

						});

				updateFlashImgView
						.setOnClickListener(new RelateButtonListner().new RelateButtonOnClickListener(
								this) {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								int index = totalData.size();
								if (0 < index) {
									MyOneData tmpOneData = totalData
											.get(index - 1);
									tmpOneData.flashFileStatus = MyOneData.FLASH_FILE_DOWNLOADING;
									tmpOneData.isUpdating = true;
									downloadFlashFile(tmpOneData);
									// Toast.makeText(
									// mContext,
									// "update:"
									// + totalData.get(index - 1).flashFileName,
									// Toast.LENGTH_SHORT).show();
								}
								super.onClick(v);
							}

						});
			}
		}
	}

	/**
	 * 
	 * @describe download one flash file from besta server
	 * 
	 * @param mOneData
	 *            : MyOneData class
	 * 
	 */
	public void downloadFlashFile(MyOneData mOneData) {
		// 下載flash
		// download flash
		String downLoadOneFile = mOneData.flashFileName + flashExtendName;
		boolean isDownloading = false;
		isDownloading = getFlashDownloadState(downLoadOneFile);
		if (isDownloading) {
			Toast.makeText(this, R.string.downloading,
					Toast.LENGTH_SHORT).show();
			return;
		}
		String mStoragepath = MyDataPath.FLASH_DATA_PATH[my_course_id];// "Besta/com.besta.app.yjyd.main";
		if (pkg_name.isEmpty()) {
			pkg_name = mContext.getPackageName();
		}
		String mPackagename = MyDataPath.PKG_NAME[my_course_id];
		mPackagename = total_pkg_name;
		Intent intent = new Intent();
		intent.putExtra("packagename", mPackagename);
		intent.putExtra("storagepath", mStoragepath);
		intent.putExtra("downloadonefile", downLoadOneFile);
		String listName = oneKnowledgeName;
		listName = MyDataPath.RES_DATA_TITLE_NAME[my_course_id] + "："
				+ listName;
		intent.putExtra("titlename", listName);
		intent.setComponent(new ComponentName(
				"com.besta.util.contentsdownload",
				"com.besta.util.contentsdownload.ContentsDownload"));
		String pkg = "com.besta.util.contentsdownload";
		myStartActivity(mOneData, intent, pkg);
		Toast.makeText(mContext, "downloading...", Toast.LENGTH_SHORT).show();
	}

	/**
	 * @describe Get one flash absolute path
	 * 
	 * @param mOneData
	 *            : MyOneData class struct
	 * @param kind
	 *            : 0 -> need to download flash; 1 -> only check flash is exist
	 * @return String : flash absolute path
	 */
	public String getRealFlashFilePath(MyOneData mOneData, int kind) {
		String flashDataPath = "";

		flashDataPath = getFlashExternalPathIsExist(
				MyDataPath.FLASH_DATA_PATH[my_course_id],
				mOneData.flashFileName + flashExtendName, my_course_id);

		if (flashDataPath.isEmpty()) {
			// check internal path
			flashDataPath = getFlashInternalPathIsExist(
					MyDataPath.FLASH_DATA_PATH[my_course_id],
					mOneData.flashFileName + flashExtendName, my_course_id);
			if (flashDataPath.isEmpty()) {
				flashDataPath = "";
			}
		}

		switch (kind) {
		case 0:
			// 下載flash
			if (!flashDataPath.isEmpty()) {

			} else {
				// download flash
				String downLoadOneFile = mOneData.flashFileName
						+ flashExtendName;
				boolean isDownloading = false;
				isDownloading = getFlashDownloadState(downLoadOneFile);
				if (isDownloading) {
					Toast.makeText(this, R.string.downloading,
							Toast.LENGTH_SHORT).show();
					return flashDataPath;
				}
				String mStoragepath = MyDataPath.FLASH_DATA_PATH[my_course_id];// "Besta/com.besta.app.yjyd.main";
				if (pkg_name.isEmpty()) {
					pkg_name = mContext.getPackageName();
				}
				String mPackagename = MyDataPath.PKG_NAME[my_course_id];
				mPackagename = total_pkg_name;
				Intent intent = new Intent();
				intent.putExtra("packagename", mPackagename);
				intent.putExtra("storagepath", mStoragepath);
				intent.putExtra("downloadonefile", downLoadOneFile);
				String listName = oneKnowledgeName;
				listName = MyDataPath.RES_DATA_TITLE_NAME[my_course_id] + "："
						+ listName;
				intent.putExtra("titlename", listName);
				intent.setComponent(new ComponentName(
						"com.besta.util.contentsdownload",
						"com.besta.util.contentsdownload.ContentsDownload"));
				String pkg = "com.besta.util.contentsdownload";
				myStartActivity(mOneData, intent, pkg);
				Toast.makeText(mContext, "downloading...", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case 1:
			// 只check 本地是否存在
			break;
		}

		return flashDataPath;
	}

	/**
	 * @describe Get Android SDK version
	 * 
	 * @return int : version value
	 */

	@SuppressWarnings("deprecation")
	public static int getAndroidSDKVersion() {
		int version = 0;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			// CSLog.e(Tool.class, e.toString());
			Log.e("sdk_version", "cannot get");
		}
		return version;
	}

	/**
	 * @describe 判斷Version大小，如果小於目標version則為true需要更新。
	 * 
	 * @param packageName
	 * @param targetVersion
	 * @return true 需更新; false 不需
	 */
	public boolean getLinkApkInfo(String packageName, int targetVersion) {
		boolean ret = false;
		int versionCode = 0;
		List<PackageInfo> packages = getPackageManager()
				.getInstalledPackages(0);
		for (PackageInfo p : packages) {
			String packName = p.packageName;
			if (packName.equals(packageName)) {
				versionCode = p.versionCode;
			}
		}

		if (versionCode < targetVersion) {
			ret = true;
		}

		return ret;
	}

	/**
	 * @describe check besta market need to update or not
	 * 
	 * @param pkg_name
	 *            : check app's packge name
	 */

	public void CheckUpdateVer(String pkg_name) {
		Intent intent = new Intent();
		intent.setAction("com.besta.app.bestamarket.CHECK_FOR_UPDATE");
		if (null != pkg_name) {
			intent.putExtra("package", pkg_name);
		} else {
			intent.putExtra("package", getPackageName());// your
															// package
															// name
			// e.g.
			// "com.besta.app.KPS"
		}
		intent.putExtra("versionCode", 1);// your package version code, e.g. 1
		sendBroadcast(intent);

	}

	// downlaod ver :
	final static int ContentDownload_Ver_2_3 = 179;
	final static int ContentDownload_Ver_4_0 = 189;

	/**
	 * @describe start download content activity
	 * 
	 * @param data
	 *            : MyOneData class struct
	 * @param intent
	 *            : activity intent
	 * @param pkg
	 *            : this activity package name
	 * @return boolean : start download content activity is successful
	 */

	public boolean myStartActivity(MyOneData data, Intent intent, String pkg) {
		boolean ret = false;
		if (null != pkg) {
			int sdk_ver = getAndroidSDKVersion();
			int targetVersion = 0;
			// content download
			if (sdk_ver >= 15) { // 4.0
				targetVersion = ContentDownload_Ver_4_0;
			} else if (sdk_ver <= 10) { // 2.3
				targetVersion = 0;
			}

			if (getLinkApkInfo(pkg, targetVersion)) {
				Intent intent1 = null;
				if (null != data) {
					data.flashFileStatus = MyOneData.FLASH_FILE_NEED_DOWNLOAD;
				}
				try {
					intent1 = new Intent(Intent.ACTION_VIEW);
					intent1.setData(Uri.parse(BESTA_MARKET_DOWNLOAD
							+ BESTA_MARKET_DOWNLOAD_LIST_KEY + pkg));
					startActivity(intent1);
				} catch (Exception e) {
					CheckUpdateVer(pkg);
					Toast.makeText(this, R.string.contentDownloadStr,
							Toast.LENGTH_SHORT).show();

				}
			} else {

				if (this.getPackageManager().queryIntentActivities(intent, 0)
						.size() > 0) {
					if (null != data) {
						data.flashFileStatus = MyOneData.FLASH_FILE_DOWNLOADING;
					}
					ret = true;
					this.startActivity(intent);

				} else {
					if (null != data) {
						data.flashFileStatus = MyOneData.FLASH_FILE_NEED_DOWNLOAD;
					}
				}
			}
		} else {
			if (this.getPackageManager().queryIntentActivities(intent, 0)
					.size() > 0) {
				if (null != data) {
					data.flashFileStatus = MyOneData.FLASH_FILE_DOWNLOADING;
				}
				ret = true;
				try {
					this.startActivity(intent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (null != data) {
					data.flashFileStatus = MyOneData.FLASH_FILE_NEED_DOWNLOAD;
				}
			}
		}
		return ret;
	}

	/**
	 * @describe Get one flash is exist besta internal path
	 * 
	 * @param path
	 *            : this value is not using
	 * @param fileName
	 *            : flash name
	 * @param course_id
	 *            : course kind for exaple: math, phy, bio ...
	 * @return String : Besta internal absolute path
	 */

	public String getFlashInternalPathIsExist(String path, String fileName,
			int course_id) {
		String ret = "";

		File[] internalPath = BestaEnvironment.getExternalStorageDirectoryEx();
		if (null != internalPath) {
			for (int count = 0; count < internalPath.length; count++) {
				ret = internalPath[count] + File.separator
						+ MyDataPath.FLASH_DATA_PATH[course_id] + fileName;
				if (!new File(ret).exists()) {
					ret = "";
				} else {
					break;
				}
			}
		}

		if (ret.isEmpty()) {
			ret = "/mnt/" + MyDataPath.ext_sdcard[1] + File.separator
					+ MyDataPath.FLASH_DATA_PATH[course_id] + fileName;
			File tmpFile = new File(ret);
			if (!tmpFile.exists()) {
				ret = "";
			}
		}

		return ret;
	}

	/**
	 * @describe Get flash abolute path from external sdcard
	 * 
	 * @param path
	 *            : this value is not using
	 * @param fileName
	 *            : flash name
	 * @param course_id
	 *            : course kind
	 * @return String : external absolute path
	 */

	public String getFlashExternalPathIsExist(String path, String fileName,
			int course_id) {
		String ret = "";
		File externalPath = Environment.getExternalStorageDirectory();
		ret = externalPath.getPath() + File.separator
				+ MyDataPath.FLASH_DATA_PATH[course_id] + fileName;

		if (!new File(ret).exists()) {
			ret = "";
		}

		return ret;
	}

	/**
	 * @describe stop playing flashs(delete functions)
	 * 
	 * @return boolean
	 */

	public boolean onStopOneFlashPlaying() {
		boolean ret = false;
		if (0 > playingCount) {
			return true;
		}
		// MyOneData tmpOneData = totalData.get(playingCount);
		if (null != view[playingCount]) {
			FlashView2 flashView = (FlashView2) view[playingCount]
					.findViewById(R.id.play_flash_webview);
			if (null != flashView) {
				// flashView.doPause();
				flashView.onPause();
			}
			ImageView playFlashImgView = (ImageView) view[playingCount]
					.findViewById(R.id.play_flash_view_img);
			if (null != playFlashImgView) {
				playFlashImgView.setVisibility(View.VISIBLE);
				playFlashImgView.setImageResource(R.drawable.play_flash);
			}
		}
		playingCount = -1;

		return ret;
	}

	/**
	 * @describe Class -> Loading data from offline data
	 */

	public class LoadingDataThread extends Thread {
		String dataPath = "";
		String oneKnowledgeID = "";
		boolean loadDataFlag = false;

		/**
		 * @describe LoadingDataThread construct
		 * 
		 * @param dataPath
		 *            : loading data path
		 * @param oneKnowledgeID
		 *            : loading one data of id
		 */

		public LoadingDataThread(String dataPath, String oneKnowledgeID) {
			this.dataPath = dataPath;
			this.oneKnowledgeID = oneKnowledgeID;
		}

		@Override
		public void run() {
			loadDataFlag = ReadMyData(dataPath, oneKnowledgeID);
			mHandler.sendEmptyMessage(HANDLER_MSG_LOADING_DATA_SUCCESS);
			super.run();
		}
	}

	private int downloadFlashSuccess = 0;

	/**
	 * @describe this Activity Handler for excute
	 * 
	 */

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLER_MSG_READ_DATA_ERROR:
				if (null != mContext) {
					Toast.makeText(mContext,
							getString(R.string.not_found_data),
							Toast.LENGTH_SHORT).show();
					finish();
				}
				break;
			case HANDLER_MSG_LOADING_DATA_SUCCESS:
				// data loading success
				if (mLoadingView.getShowingFlag()) {
					mLoadingView.dissmisView();
				}
				if (loadingData.loadDataFlag) {
					PrepareLoadData();
				} else {
					Toast.makeText(CrossWebViewRealTeachingActivity.this,
							"cannot found : " + knowledgeDataPath,
							Toast.LENGTH_SHORT).show();
				}
				// Toast.makeText(RealTeachingActivity.this, "success Activity",
				// Toast.LENGTH_SHORT).show();
				break;
			case HANDLER_MSG_LOADING_DIY_DATA_SUCCESS:
				if (null != pgDlg && pgDlg.isShowing()) {
					pgDlg.dismiss();
				}
				break;
			case HANDLER_MSG_CHECK_FLASH_UPDATE:
				// check flash is need update
				checkFlashIsNeedUpdate();
				break;
			case HANDLER_MSG_SHOW_UPDATE_FLASH_BT:
				// check flash need update and refresh UI
				reSetUpdateFlashView();
				break;
			case HANDLER_MSG_DOWNLOADFLASH_SUCCEED:
				// 下載一個flash文件成功，需要刷新一下list的顯示。
				// getFlashDownloadState(true);
				for (int count = 0; count < totalData.size(); count++) {
					if (isFlashPlaying[count]) {
						someOneFlashPlaying = true;
						break;
					} else {
						someOneFlashPlaying = false;
					}
				}
				if (!someOneFlashPlaying) {
					downloadFlashSuccess = 1;
					reFreshDownloadIconStatus(mContext, selectOneItemLayoutVer);
				}
				break;
			case HANDLER_MSG_DOWNLOADFLASH_FAIL_OTHER:
				// download flash failed
			case HANDLER_MSG_DOWNLOADFLASH_FAIL_NOWIFI:
				// WIFI is not started
			case HANDLER_MSG_DOWNLOADFLASH_FAIL_SDCARD:
				// sdcard is not enough to download flash
			case HANDLER_MSG_DOWNLOADFLASH_CANCELALL:
				// cancel download flashs
				reFreshDownloadIconStatus(mContext, selectOneItemLayoutVer);
				break;
			case HANDLER_MSG_WIFI_ISCONNECTED:
				// 收到wifi連接成功的廣播消息
				checkFlashIsNeedUpdate();
				break;
			case HANDLER_MSG_WIFI_ISDISCONNECTED:
				// 收到wifi連接斷開的廣播消息
				isWifiConnect = false;
				break;
			}
			super.handleMessage(msg);
		}

	};

	/**
	 * @describe check flash is need to update
	 * 
	 */

	public void checkFlashIsNeedUpdate() {
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String basePath = "/sdcard/besta";
				if (!new File("/sdcard/besta/").exists()) {
					if (new File("/mnt/besta-internal/Besta/").exists()) {
						basePath = "/mnt/besta-internal/Besta";
					}
				}

				String JSON = "";
				int findfileCount = 0;
				boolean ishavenewFlash = false;
				try {
					String path = getFolderService("http://www.5dkg.com/BestaMarket");
					HttpClient client = new DefaultHttpClient();
					HttpPost request = new HttpPost(path
							+ "/checkSubFileInServer.jsp");
					HttpParams httpParams = client.getParams();
					HttpConnectionParams
							.setConnectionTimeout(httpParams, 10000);
					HttpConnectionParams.setSoTimeout(httpParams, 10000);

					// HttpPost request = new
					// HttpPost("http://203.69.69.244/BestaMarket/checkSubFileInServer.jsp");
					request.addHeader("charset", HTTP.UTF_8);
					List<NameValuePair> postParams = new ArrayList<NameValuePair>();
					String localFlashPath;
					inputJson = new JSONArray();
					ArrayList<String> pathArray = getPathArrayList();
					getFlashDownloadState(getPackageName(), pathArray,
							inputJson, true);

					if (DebugFlag) {
						Log.i(debugStr, "[In JSON]" + inputJson.toString());
					}

					if (null != inputJson) {
						postParams.add(new BasicNameValuePair("subfileList",
								inputJson.toString()));
						postParams.add(new BasicNameValuePair("packageName",
								total_pkg_name));
						postParams.add(new BasicNameValuePair("machineType",
								android.os.Build.DEVICE));
						UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
								postParams);
						request.setEntity(formEntity);
						HttpResponse response = client.execute(request);

						BufferedReader in = new BufferedReader(
								new InputStreamReader(response.getEntity()
										.getContent()));
						StringBuffer sb = new StringBuffer("");
						String line = "";
						String NL = System.getProperty("line.separator");
						while ((line = in.readLine()) != null) {
							sb.append(line + NL);
						}
						in.close();
						JSON = sb.toString().substring(
								new String("bestamarket").length());
						// System.out.println("[Out JSON]" + JSON);
						if (DebugFlag) {
							Log.i(debugStr, "[Out JSON]" + JSON);
						}
						JSONTokener jsonParser = new JSONTokener(JSON);
						JSONArray jsonNArray = (JSONArray) jsonParser
								.nextValue();
						ishavenewFlash = true;
						MyOneData temp;
						int flashCount = 0;

						for (int i = 0; i < totalData.size(); i++) {
							temp = totalData.get(i);
							if (temp.flashFileName.isEmpty()) {

							} else {
								JSONObject jsonObject = (JSONObject) jsonNArray
										.get(flashCount);
								String flashname = jsonObject
										.getString("fileName");
								if (!isActivityDestroy
										&& jsonObject
												.getBoolean("isNeedUpdate")) {
									File localFlashFile = null;
									String tmpOneFlashPath = GetfileMethod
											.getfilepath(
													MyDataPath.FLASH_DATA_PATH[my_course_id]
															+ flashname,
													mContext);
									if (null != tmpOneFlashPath) {
										localFlashFile = new File(
												tmpOneFlashPath);
									}
									if (MyOneData.FLASH_FILE_DOWNLOADING != temp.flashFileStatus
											&& !temp.isUpdating) {
										Date[] date = new Date[2];
										if (localFlashFile.lastModified() != 0) {
											date[0] = new Date(localFlashFile
													.lastModified());
											date[1] = new Date(
													temp.fileLastModify);
											Log.e(debugStr,
													flashname
															+ ", "
															+ date[0]
																	.toString()
															+ " | "
															+ date[1]
																	.toString());
											temp.flashFileStatus = MyOneData.FLASH_FILE_NEED_UPDATE;
										} else {
											temp.flashFileStatus = MyOneData.FLASH_FILE_NEED_DOWNLOAD;
										}
									} else { // 該flash文件正在下載中
									}
									findfileCount++;
								} else {
									// 下面這句是個bug,在網站沒有找到這個文件并不等於這個文件本地就存在。
									// temp.updateType = ListDef.FLASH_EXIST;
									if (MyOneData.FLASH_FILE_EXIST != temp.flashFileStatus) {

									}
								}
								flashCount++;
							}
						}
					}

				} catch (InterruptedIOException e) {
					Message msg = new Message();
					msg.what = HANDLER_MSG_WIFI_CONNECT_ERROR;
					mHandler.sendMessage(msg);
					e.printStackTrace();
				} catch (IOException e) {
					Message msg = new Message();
					msg.what = HANDLER_MSG_WIFI_CONNECT_ERROR;
					mHandler.sendMessage(msg);
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {

				} finally {
					// Message msg = new Message();
					// msg.what = HANDLER_MSG_CHECK_FLASH_UPDATE;
					// mHandler.sendMessage(msg);
					Message tmpMsg = new Message();
					tmpMsg.what = HANDLER_MSG_SHOW_UPDATE_FLASH_BT;
					mHandler.sendMessage(tmpMsg);
				}
			}
		});
		th.start();

		Message tmpMsg = new Message();
		tmpMsg.what = HANDLER_MSG_SHOW_UPDATE_FLASH_BT;
		mHandler.sendMessage(tmpMsg);
	}

	// Internet using
	JSONArray inputJson = null;

	/**
	 * @describe Get flash download status
	 * 
	 * @param mPackagename
	 *            : this activity pkg name
	 * @param pathArray
	 *            : string[] of path
	 * @param inputJSON
	 *            : JSONArray
	 * @param checkUpdateFlag
	 *            : boolean value can excute continue update check
	 */

	public void getFlashDownloadState(String mPackagename,
			ArrayList<String> pathArray, JSONArray inputJSON,
			boolean checkUpdateFlag) {
		if (null == mContext || null == mPackagename || mPackagename.equals("")) {
			return;
		}
		ContentResolver contentResolver = mContext.getContentResolver();
		Uri contentsUri = Uri
				.parse("content://com.besta.util.contentsdownload/downloaditem");
		String[] column = new String[] { "PackageName", "FileName",
				"DownloadState" };
		Cursor cursor = null;

		for (int i = 0; i < totalData.size(); i++) {
			MyOneData mOneData = totalData.get(i);
			if (mOneData.flashFileName.isEmpty()) {
				continue;
			}
			String filename = mOneData.flashFileName + ".bfe";
			cursor = contentResolver.query(contentsUri, column, "PackageName=?"
					+ " and" + " FileName=?", new String[] { total_pkg_name,
					filename }, null);
			if (cursor == null) {
				// mOneData.flashFileStatus = false;
				continue;
			}
			if (cursor.moveToFirst()) {
				int index = cursor.getColumnIndex("DownloadState");
				int state = cursor.getInt(index);
				if (state == DOWNLOADWAIT || state == DOWNLOADBEGIN) {
					mOneData.flashFileStatus = MyOneData.FLASH_FILE_DOWNLOADING;
				}
			}

			if (cursor != null) {
				cursor.close();
			}
			if (checkUpdateFlag
					&& MyOneData.FLASH_FILE_DOWNLOADING != mOneData.flashFileStatus) {
				// String localFlashPath = getFlashPath(mOneData.flashFileName);
				String localFlashPath = GetfileMethod.getfilepath(
						MyDataPath.FLASH_DATA_PATH[my_course_id]
								+ mOneData.flashFileName + flashExtendName,
						mContext);
				if (localFlashPath.equals("")) {
					mOneData.fileLastModify = 0;
				} else {
					File localFlashFile = new File(localFlashPath);// updateFlashId[i]//packagename
					mOneData.fileLastModify = localFlashFile.lastModified();
				}
				if (DebugFlag) {
					Date date = new Date(mOneData.fileLastModify);
					Log.i(debugStr,
							mOneData.flashFileName + ".bfe, " + date.toString());
				}

				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("lastModifiedTime", mOneData.fileLastModify);
					jsonObject.put("fileName", mOneData.flashFileName + ".bfe");// updateFlashId[i]
					if (null != inputJson) {
						inputJson.put(jsonObject);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @describe check flash is need update of get Server Folder
	 * 
	 * @param rootUrl
	 *            : ***
	 * @return String : ***
	 */

	public static String getFolderService(String rootUrl) {
		String folderString = null;
		String serialNumber = BestaTad.getSN();
		String machineType = android.os.Build.DEVICE;
		try {
			HttpClient client = new DefaultHttpClient();

			HttpPost request = new HttpPost(rootUrl + "/getApDirectory.jsp");
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			postParams.add(new BasicNameValuePair("machineType", machineType));
			postParams.add(new BasicNameValuePair("dataType", "database"));
			postParams.add(new BasicNameValuePair("password", "bestabxc"));
			postParams
					.add(new BasicNameValuePair("serialNumber", serialNumber));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParams, "utf-8");
			request.addHeader("charset", HTTP.UTF_8);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity());
				Pattern p = Pattern.compile("\\s*|\t|\r|\n");
				Matcher m = p.matcher(result);
				folderString = m.replaceAll("");
				Log.d("debug==", "Post result is : " + result);

				if (folderString != null) {
					int begin = folderString.indexOf("http");
					if (begin > 0) {
						folderString = folderString.substring(begin,
								folderString.length());
						String urlRoot = folderString.substring(0,
								folderString.indexOf(";"));
						folderString = urlRoot;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			folderString = null;
		}
		Log.e("debug==", "GetFolderService: " + folderString);
		return folderString;
	}

	/**
	 * @describe get path ArrayList
	 */

	public ArrayList<String> getPathArrayList() {
		ArrayList<String> retStr = new ArrayList<String>();
		String stateInternalStorage = "";
		File[] internalStorages = BestaEnvironment
				.getExternalStorageDirectoryEx();
		for (File file : internalStorages) {
			stateInternalStorage = BestaEnvironment
					.getExternalStorageStateEx(file.getPath());
			if (stateInternalStorage.equals(Environment.MEDIA_MOUNTED)) {
				File path = new File(file.getPath(), "");
				try {
					// Make sure the Besta directory exists.
					// path.mkdirs();
					String filePath = path.getAbsolutePath();
					File flashfile = new File(filePath
							+ MyDataPath.FLASH_DATA_PATH[my_course_id]);
					if (flashfile.isDirectory()) {
						retStr.add(filePath
								+ MyDataPath.FLASH_DATA_PATH[my_course_id]);
					}
				} catch (SecurityException e) {
					Log.w("path=", "Error mkdir " + path, e);
				}
			}
		}

		return retStr;
	}

	/**
	 * @describe Get flash absolute path
	 * 
	 * @param flashName
	 *            : flash name
	 * @return String : return one path of flash absolue path
	 */

	public String getFlashPath_2(String flashName) {
		String ret = "";
		StorageManager mStorageManager = null;
		Method mMethodGetPaths = null;
		String[] mStoragePaths = null;

		mStorageManager = (StorageManager) this
				.getSystemService(Activity.STORAGE_SERVICE);
		try {
			mMethodGetPaths = mStorageManager.getClass().getMethod(
					"getVolumePaths");
		} catch (NoSuchMethodException e) {
			if (DebugFlag) {
				MyErrorPrintfActivity.startMyErrorPrintfActivity(this, e);
			} else {
				e.printStackTrace();
				Toast.makeText(this, R.string.appShutdownStr,
						Toast.LENGTH_SHORT).show();
			}
		}
		mStoragePaths = getVolumePaths(mStorageManager);

		return ret;
	}

	/**
	 * @describe new get flash path function
	 * 
	 * @param storageManager
	 *            : ****
	 * @return String[] : ****
	 */

	public static String[] getVolumePaths(StorageManager storageManager) {
		String[] ret = null;
		if (storageManager == null) {
			return ret;
		}
		Method getVolumePathsMethod = null;
		try {
			getVolumePathsMethod = storageManager.getClass().getDeclaredMethod(
					"getVolumePaths");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if (getVolumePathsMethod != null) {
			getVolumePathsMethod.setAccessible(true);
			try {
				ret = (String[]) getVolumePathsMethod.invoke(storageManager);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	public static String getVolumeState(StorageManager storageManager,
			String path) {
		String ret = Environment.MEDIA_REMOVED;
		if (storageManager == null) {
			return ret;
		}
		Method getVolumeStateMethod = null;
		try {
			getVolumeStateMethod = storageManager.getClass().getDeclaredMethod(
					"getVolumeState", String.class);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (getVolumeStateMethod != null) {
			getVolumeStateMethod.setAccessible(true);
			try {
				ret = (String) getVolumeStateMethod
						.invoke(storageManager, path);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * @describe old get flash absolute path function
	 * 
	 * @param flashName
	 *            : flash name
	 * @return String : return one flash absolute path
	 */

	public String getFlashPath(String flashName) {
		String retStr = "";
		ArrayList<String> pathArray = new ArrayList<String>();
		String stateInternalStorage = "";
		File[] internalStorages = BestaEnvironment
				.getExternalStorageDirectoryEx();
		for (File file : internalStorages) {
			stateInternalStorage = BestaEnvironment
					.getExternalStorageStateEx(file.getPath());
			if (stateInternalStorage.equals(Environment.MEDIA_MOUNTED)) {
				File path = new File(file.getPath(), "");
				try {
					// Make sure the Besta directory exists.
					// path.mkdirs();
					String filePath = path.getAbsolutePath();
					File flashfile = new File(filePath
							+ MyDataPath.FLASH_DATA_PATH[my_course_id]
							+ flashName + flashExtendName);
					if (flashfile.exists()) {
						pathArray.add(filePath
								+ MyDataPath.FLASH_DATA_PATH[my_course_id]
								+ flashName + flashExtendName);
						// break;
					}
				} catch (SecurityException e) {
					Log.w("path=", "Error mkdir " + path, e);
				}
			}
		}

		long fileLastModifyTime = 0;
		for (int i = 0; i < pathArray.size(); i++) {
			File tmpFile = new File(pathArray.get(i));
			if (fileLastModifyTime < tmpFile.lastModified()) {
				retStr = pathArray.get(i);
				fileLastModifyTime = tmpFile.lastModified();
			}
		}

		return retStr;
	}

	/**
	 * @describe reset FlashView(WebView)
	 * 
	 */

	public void reSetUpdateFlashView() {
		// showKind : 0 is not show update icon and 1 is show
		if (null != view) {
			int totalCount = view.length;
			int count = 0;
			int showViewKind = 0;
			for (count = 0; count < totalCount && null != view[count]; count++) {
				ImageView updateFlashImgView = (ImageView) view[count]
						.findViewById(R.id.play_flash_view_img_update);
				if (null != updateFlashImgView) {
					if (MyOneData.FLASH_FILE_NEED_UPDATE == totalData
							.get(count).flashFileStatus) {
						showViewKind = View.VISIBLE;
					} else {
						showViewKind = View.GONE;
					}

					updateFlashImgView.setVisibility(showViewKind);
				}
			}
		}
	}

	// Loading DIY flash of progress dialog
	ProgressDialog pgdlg = null;
	MyLoadingView mLoadingView = null;

	/**
	 * @describe initialize Main View Datas
	 * 
	 * @param dataPath
	 *            : data absolute path
	 * @param oneKnowledgeID
	 *            : loading one data id
	 */

	public void initializeData(String dataPath, String oneKnowledgeID) {
		mLoadingView = new MyLoadingView(this, 0);
		mLoadingView.setOneLoadingView(this, 0);
		if (null != mLoadingView.selectLoadingView) {
			// main_relative_layout layout
			RelativeLayout mainRelativeViewLayout = (RelativeLayout) ((Activity) mContext)
					.findViewById(R.id.main_relative_layout);
			mainRelativeViewLayout.addView(mLoadingView.selectLoadingView);
			LayoutParams lp = mLoadingView.selectLoadingView.getLayoutParams();
			lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
			lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
			mLoadingView.setSelectViewParams(lp);
			mLoadingView.setSelectViewBackgroundColor(0xAA000000);
			ScrollView ly = (ScrollView) findViewById(R.id.my_main_scroll);
			if (null != ly) {
				ly.setBackgroundColor(Color.WHITE);
			}
		}

		mLoadingView.showView();

		loadingData = new LoadingDataThread(dataPath, oneKnowledgeID);
		loadingData.start();
	}

	/**
	 * @describe create Main View and set Views Listener
	 * 
	 */

	private void PrepareLoadData() {
		createAllView(CrossWebViewRealTeachingActivity.this);
		initializeRelateButton();
		getFlashDownloadState(true);
		reFreshDownloadIconStatus(mContext, selectOneItemLayoutVer);
		Message tmpMsg = new Message();
		tmpMsg.what = HANDLER_MSG_CHECK_FLASH_UPDATE;
		mHandler.sendMessage(tmpMsg);

	}

	/**
	 * @describe initialize relate button has appear on Main View or not
	 * 
	 */

	public void initializeRelateButton() {
		if (null != buttonFlags) {
			if (0 == buttonFlags.is_has_test) {
				for (int count = 0; count < totalData.size(); count++) {
					if (!totalData.get(count).unit_id.equals("")) {
						buttonFlags.is_has_test = 1;
						break;
					}
				}
			}

			// buttonFlags.is_has_test = 1;
			// buttonFlags.is_has_diy = 1;
			RelativeLayout rl = (RelativeLayout) findViewById(R.id.relate_bt_layout);
			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);

			rl.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
			switch (buttonFlags.getLayoutKind()) {
			case 0:
				relateButton[BT_TEST] = null;
				relateButton[BT_DIY] = null;
				break;
			case 1:
				relateButton[BT_TEST] = new Button(this);
				if (null != relateButton[BT_TEST]) {
					RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
							ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
					rp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
							RelativeLayout.TRUE);
					rp.rightMargin = 10;
					relateButton[BT_TEST].setLayoutParams(rp);
					rl.addView(relateButton[BT_TEST]);
					relateButton[BT_TEST].setText(R.string.pingliang_bt);
					relateButton[BT_TEST].setTextSize(20);
					relateButton[BT_TEST].setTextColor(Color.BLACK);
					LayoutParams bt_lp = relateButton[BT_TEST]
							.getLayoutParams();
					bt_lp.width = LayoutParams.WRAP_CONTENT;
					bt_lp.height = LayoutParams.WRAP_CONTENT;
					relateButton[BT_TEST].setLayoutParams(bt_lp);
					relateButton[BT_TEST]
							.setBackgroundResource(R.drawable.btn_fxtj_up);
					// .setImageResource(R.drawable.btn_fxtj_up);
					// relateButton[BT_TEST].setId(0x8f010001);
				}
				relateButton[BT_DIY] = null;
				break;
			case 2:
				relateButton[BT_TEST] = new Button(this);
				if (null != relateButton[BT_TEST]) {
					RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
							ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
					rp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
							RelativeLayout.TRUE);
					rp.rightMargin = 10;
					relateButton[BT_TEST].setLayoutParams(rp);
					rl.addView(relateButton[BT_TEST]);
					relateButton[BT_TEST].setText(R.string.pingliang_bt);
					relateButton[BT_TEST].setTextSize(20);
					relateButton[BT_TEST].setTextColor(Color.BLACK);
					LayoutParams bt_lp = relateButton[BT_TEST]
							.getLayoutParams();
					bt_lp.width = LayoutParams.WRAP_CONTENT;
					bt_lp.height = LayoutParams.WRAP_CONTENT;
					relateButton[BT_TEST].setLayoutParams(bt_lp);
					relateButton[BT_TEST]
							.setBackgroundResource(R.drawable.btn_fxtj_up);
					relateButton[BT_TEST].setId(0x8f010001);
				}

				relateButton[BT_DIY] = new Button(this);
				if (null != relateButton[BT_DIY]) {
					RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
							ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
					// rp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
					// RelativeLayout.TRUE);
					rp.addRule(RelativeLayout.LEFT_OF,
							relateButton[BT_TEST].getId());
					rp.rightMargin = 10;
					relateButton[BT_DIY].setLayoutParams(rp);
					rl.addView(relateButton[BT_DIY]);
					relateButton[BT_DIY].setText(R.string.diy_bt);
					relateButton[BT_DIY].setTextSize(20);
					relateButton[BT_DIY].setTextColor(Color.BLACK);
					LayoutParams bt_lp = relateButton[BT_DIY].getLayoutParams();
					bt_lp.width = LayoutParams.WRAP_CONTENT;
					bt_lp.height = LayoutParams.WRAP_CONTENT;
					relateButton[BT_DIY].setLayoutParams(bt_lp);
					relateButton[BT_DIY]
							.setBackgroundResource(R.drawable.btn_starttest_up);
				}
				break;
			case 3:
				relateButton[BT_DIY] = new Button(this);
				if (null != relateButton[BT_DIY]) {
					RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
							ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
					// rp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
					// RelativeLayout.TRUE);
					rp.addRule(RelativeLayout.LEFT_OF,
							relateButton[BT_TEST].getId());
					rp.rightMargin = 10;
					relateButton[BT_DIY].setLayoutParams(rp);
					rl.addView(relateButton[BT_DIY]);
					relateButton[BT_DIY].setText(R.string.diy_bt);
					relateButton[BT_DIY].setTextSize(20);
					relateButton[BT_DIY].setTextColor(Color.BLACK);
					LayoutParams bt_lp = relateButton[BT_DIY].getLayoutParams();
					bt_lp.width = LayoutParams.WRAP_CONTENT;
					bt_lp.height = LayoutParams.WRAP_CONTENT;
					relateButton[BT_DIY].setLayoutParams(bt_lp);
					relateButton[BT_DIY]
							.setBackgroundResource(R.drawable.btn_starttest_up);
				}
				relateButton[BT_TEST] = null;
				break;
			}
		}

		setRelateButtonListener();
	}

	/**
	 * @describe set Relate Button Touch and Click listener
	 * 
	 */

	public void setRelateButtonListener() {
		setButtonTouchListener();
		setButtonClickListener();
	}

	/**
	 * @describe set relate button touch listener
	 * 
	 */

	public void setButtonTouchListener() {
		if (null != relateButton[BT_TEST]) {
			relateButton[BT_TEST]
					.setOnTouchListener(new RelateButtonListner().new RelateButtonOntouchListener(
							this) {
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// TODO Auto-generated method stub
							switch (event.getAction()) {
							case MotionEvent.ACTION_DOWN:
								((Button) v)
										.setBackgroundResource(R.drawable.btn_fxtj_dn);
								// ((ImageView) v)
								// .setImageResource(R.drawable.btn_pl_dn);
								break;
							case MotionEvent.ACTION_UP:
							case MotionEvent.ACTION_CANCEL:
								((Button) v)
										.setBackgroundResource(R.drawable.btn_fxtj_up);
								// ((ImageView) v)
								// .setImageResource(R.drawable.btn_pl_up);
								break;
							}
							return false;
							// return super.onTouch(v, event);
						}
					});
		}

		if (null != relateButton[BT_DIY]) {
			relateButton[BT_DIY]
					.setOnTouchListener(new RelateButtonListner().new RelateButtonOntouchListener(
							this) {
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							switch (event.getAction()) {
							case MotionEvent.ACTION_DOWN:
								((Button) v)
										.setBackgroundResource(R.drawable.btn_starttest_dn);
								// ((ImageView) v)
								// .setImageResource(R.drawable.btn_syk_dn);
								break;
							case MotionEvent.ACTION_UP:
							case MotionEvent.ACTION_CANCEL:
								((Button) v)
										.setBackgroundResource(R.drawable.btn_starttest_up);
								// ((ImageView) v)
								// .setImageResource(R.drawable.btn_syk_up);
								break;
							}
							return false;
						}
					});
		}
	}

	/**
	 * @describe set relate button click listener
	 * 
	 */

	public void setButtonClickListener() {
		if (null != relateButton[BT_TEST]) {
			relateButton[BT_TEST]
					.setOnClickListener(new RelateButtonListner().new RelateButtonOnClickListener(
							this) {
						@Override
						public void onClick(View v) {
							int index = totalData.size();
							if (0 < index) {
								callTestActivity(totalData.get(index - 1).unit_id);
							}
							super.onClick(v);
						}

					});
		}

		if (null != relateButton[BT_DIY]) {
			relateButton[BT_DIY]
					.setOnClickListener(new RelateButtonListner().new RelateButtonOnClickListener(
							this) {
						@Override
						public void onClick(View v) {
							int index = totalData.size();
							if (0 < index) {
								MyOneData tmpOneData = totalData.get(index - 1);
								boolean isDownloading = false;
								isDownloading = getFlashDownloadState(tmpOneData.flashFileName
										+ diyDataName);
								if (isDownloading && null != mContext) {
									Toast.makeText(mContext,
											R.string.downloading,
											Toast.LENGTH_SHORT).show();
									return;
								}
								int res_str = 0;
								String subStr = tmpOneData.flashFileName
										.substring(0, 1);
								if (subStr.equals("B")) {
									res_str = R.string.str_progressDlg_message_bio;
								} else if (subStr.equals("P")) {
									res_str = R.string.str_progressDlg_message_phy;
								}
								pgDlg = new ProgressDialog(mContext);
								pgDlg.setMessage(mContext.getString(res_str));
								pgDlg.show();
								new LoadingDIYThread(oneKnowledgeName,
										tmpOneData.flashFileName).start();
							}
							super.onClick(v);
						}
					});
		}
	}

	/**
	 * @describe if this knowledge has test to call test activity
	 * 
	 * @param test_id
	 *            : test id for example: SGA-01;SGA-02...
	 */

	public void callTestActivity(String test_id) {
		String key = "quesarray";
		String whiteBoardKey = "whiteBoardKey";
		String isWhiteBoardEnable = "isWhiteBoardEnable";
		String action = "com.besta.app.exerciseengine.ACTION_TEST";
		Intent intent = new Intent(action);
		if (null != intent) {
			intent.putExtra(key, test_id);
			intent.putExtra(whiteBoardKey, oneKnowledgeID);
			if (null != test_id && !test_id.isEmpty()) {
				intent.putExtra(isWhiteBoardEnable, true);
			}
			Bundle bundle = new Bundle();
			bundle.putString(key, test_id);
			bundle.putString(whiteBoardKey, oneKnowledgeID);
			try {
				intent.putExtras(bundle);
				startActivity(intent);
			} catch (Exception e) {
				if (DebugFlag) {
					MyErrorPrintfActivity.startMyErrorPrintfActivity(this, e);
				} else {
					e.printStackTrace();
					Toast.makeText(this, R.string.appShutdownStr,
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	// play diy flash values
	public static String diyDataName = ".dat";
	// public static String prePath = "/sdcard/";
	ProgressDialog pgDlg = null;
	String targetDir = "";
	String choseUnzipDir = "";
	String downLoadOneFile = "";
	String oldUnzipFilesStr = "";
	String nowFileName = "";

	/**
	 * @describe Class for Loading DIY flash
	 */

	private class LoadingDIYThread extends Thread {
		String listName;
		String playFileName;

		/**
		 * @describe Loading DIY flash class construct
		 * 
		 * @param listName
		 *            : show loading view of string
		 * @param DiyFileName
		 *            : diy flash file name
		 */

		public LoadingDIYThread(String listName, String DiyFileName) {
			this.listName = listName;
			playFileName = DiyFileName;
		}

		public void run() {
			Looper.prepare();
			PlayDIYFlash(this.listName, playFileName);
			Message msg = new Message();
			msg.what = HANDLER_MSG_LOADING_DIY_DATA_SUCCESS;
			mHandler.sendMessage(msg);
			Looper.loop();
		}

	}

	/**
	 * @describe Play DIY flash function
	 * 
	 * @param listName
	 *            : show list string
	 * @param playFlashName
	 *            : diy flash name
	 */

	public void PlayDIYFlash(String listName, String playFlashName) {
		String choseUnzipDir = playFlashName;
		String flashSolutePath = getDIYFlashPath(playFlashName);
		pgDlg.dismiss();
		if (flashSolutePath.equals("")) {
			downLoadOneFile = playFlashName + diyDataName;
			String mStoragepath = MyDataPath.FLASH_DATA_PATH[my_course_id];// "Besta/com.besta.app.yjyd.main";
			if (pkg_name.isEmpty()) {
				pkg_name = mContext.getPackageName();
			}
			String mPackagename = MyDataPath.PKG_NAME[my_course_id];
			mPackagename = total_pkg_name;
			Intent intent = new Intent();
			intent.putExtra("packagename", mPackagename);
			intent.putExtra("storagepath", mStoragepath);
			intent.putExtra("downloadonefile", downLoadOneFile);
			listName = MyDataPath.RES_DATA_TITLE_NAME[my_course_id] + "："
					+ listName;
			intent.putExtra("titlename", listName);
			intent.setComponent(new ComponentName(
					"com.besta.util.contentsdownload",
					"com.besta.util.contentsdownload.ContentsDownload"));
			String pkg = "com.besta.util.contentsdownload";
			myStartActivity(null, intent, pkg);
		} else {
			PlayFlash.ShowFlash(mContext, flashSolutePath, 0, 0, false);
		}
	}

	/**
	 * @describe Get DIY flash aboslute path
	 * 
	 * @param flashName
	 *            : flash name
	 * @return String : DIY flash absolute path
	 */

	public String getDIYFlashPath(String flashName) {
		String retStr = "";
		ArrayList<String> pathArray = new ArrayList<String>();
		String stateInternalStorage = "";
		File[] internalStorages = BestaEnvironment
				.getExternalStorageDirectoryEx();
		String filePath = null;
		for (File file : internalStorages) {
			stateInternalStorage = BestaEnvironment
					.getExternalStorageStateEx(file.getPath());
			if (stateInternalStorage.equals(Environment.MEDIA_MOUNTED)) {
				File path = new File(file.getPath(), "");
				try {
					// Make sure the Besta directory exists.
					// path.mkdirs();
					filePath = path.getAbsolutePath() + "/";
					String diyFile = filePath
							+ MyDataPath.FLASH_DATA_PATH[my_course_id]
							+ flashName + diyDataName;
					File diyDataFile = new File(diyFile);
					if (diyDataFile.exists()) {
						pathArray.add(diyFile);
					} else {
						continue;
					}
				} catch (SecurityException e) {
					Log.w("path=", "Error mkdir " + path, e);
				}
			}
		}

		long fileLastModifyTime = 0;
		for (int i = 0; i < pathArray.size(); i++) {
			File tmpFile = new File(pathArray.get(i));
			if (fileLastModifyTime < tmpFile.lastModified()) {
				retStr = pathArray.get(i);
				fileLastModifyTime = tmpFile.lastModified();
			}
		}

		if (null != retStr && !retStr.equals("")) {
			boolean unZipDiyDataFlag = false;
			File tmpFile = new File(retStr);
			String tmpPath = tmpFile.getParent() + File.separator;
			unZipDiyDataFlag = UnzipOneDiyData(flashName, diyDataName, tmpPath,
					retStr);
			retStr = "";
			if (unZipDiyDataFlag) {
				File flashfile = null;
				String unziFlashFilePath = tmpPath + flashName + File.separator
						+ flashName + ".bfe";
				flashfile = new File(unziFlashFilePath);
				if (flashfile.exists()) {
					retStr = unziFlashFilePath;
				}
			}
		}

		return retStr;
	}

	/**
	 * @describe unzip my struct of zip diy data
	 * 
	 * @param targetFileData
	 *            : file data path
	 * @param dataName
	 *            : file name
	 * @return boolean : return Unzip is compelete
	 */

	public boolean UnzipOneDiyData(String targetFileData, String dataName,
			String path, String diyFile) {

		boolean bRet = true;
		long end_address = 0;
		long nowFilePointer = 0;

		RandomAccessFile realDiyDataFile = null;
		try {
			realDiyDataFile = new RandomAccessFile(diyFile, "r");
			realDiyDataFile.seek(0);
			end_address = realDiyDataFile.length();

			while (nowFilePointer < end_address) {
				byte[] oneSubFileBuffer = new byte[1008];
				realDiyDataFile.read(oneSubFileBuffer);
				nowFilePointer = writeDiyDataInNewFile(oneSubFileBuffer,
						targetFileData, path, realDiyDataFile);
				if (nowFilePointer == -1) {
					bRet = false;
					break;
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bRet;
	}

	/**
	 * @describe write Zip DIY flash to absolute path
	 * 
	 * @param byte[] oragin : read of DIY data buffer
	 * @param String
	 *            dataName : file name
	 * @param String
	 *            rootPath : Zip DIY data path
	 * @param RandomAccessFile
	 *            thisFile : write of file
	 * @return long : return read Zip DIY data file pointer positon set
	 */

	public long writeDiyDataInNewFile(byte[] oragin, String dataName,
			String rootPath, RandomAccessFile thisFile) {
		long ret = 0;
		byte[] dataNameByte = dataName.getBytes();
		boolean isFilePathFlag = false;
		String oneDiyDataPath = null;

		if (oragin[9] == dataNameByte[0] && oragin[10] == dataNameByte[1]
				&& oragin[11] == dataNameByte[2]
				&& oragin[12] == dataNameByte[3]
				&& oragin[13] == dataNameByte[4]
				&& oragin[14] == dataNameByte[5]
				&& oragin[15] == dataNameByte[6]) {
			isFilePathFlag = true;
			int nowCount = 0;
			int totalCount = 1000;
			int dirCount = 0;
			byte[] oneFileLength = new byte[8];
			long oneFileRealLength = 0;
			for (nowCount = 0; nowCount < totalCount; nowCount++) {
				if (nowCount >= 8) {
					if (oragin[nowCount] == 0x00) {
						break;
					} else {
						if (oragin[nowCount] == '\\') {
							oragin[nowCount] = '/';
							dirCount += 1;
						}
					}
				} else {
					oneFileLength[nowCount] = oragin[nowCount];
				}
			}
			oneFileRealLength = typeBytesConvertLong(oneFileLength);
			oneDiyDataPath = new String(oragin, 9, nowCount - 9);
			// oneDiyDataPath = labTestPath + oneDiyDataPath;
			oneDiyDataPath = rootPath + oneDiyDataPath;
			checkIsExistDir(oneDiyDataPath, dirCount);
			nowFileName = oneDiyDataPath;

			try {
				@SuppressWarnings("resource")
				RandomAccessFile nowWriteFile = new RandomAccessFile(
						nowFileName, "rw");
				int tmpLen = 2000;
				int totalReadCount = 0;
				int endRealLen = 0;
				byte[] thisFileLen = null;
				for (;;) {
					endRealLen = (int) (oneFileRealLength - totalReadCount);
					if (endRealLen >= tmpLen) {
						thisFileLen = new byte[tmpLen];
						thisFile.read(thisFileLen);
						nowWriteFile.write(thisFileLen);
						totalReadCount += tmpLen;
					} else if (endRealLen > 0) {
						thisFileLen = new byte[endRealLen];
						thisFile.read(thisFileLen);
						nowWriteFile.write(thisFileLen);
						totalReadCount += endRealLen;
						break;
					}
				}
				// ret = thisFile.getFilePointer();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				ret = thisFile.getFilePointer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			ret = -1;
		}

		return ret;
	}

	/**
	 * @describe check Unzip DIY flash path is exist
	 * 
	 * @param oraginDir
	 *            : parent absolute path
	 * @param dirCount
	 *            : this valute is not using
	 */

	public void checkIsExistDir(String oraginDir, int dirCount) {
		File tmpOneFile = new File(oraginDir);
		File realOneFile = new File(tmpOneFile.getParent() + "/");
		if (!realOneFile.exists()) {
			realOneFile.mkdirs();
		}
	}

	/**
	 * @describe type byte[8] conver long
	 * 
	 * @return long : return type long
	 */

	public long typeBytesConvertLong(byte[] oragin) {
		long ret = 0;
		int nowCount = 0;
		char tmp = '\0';
		for (nowCount = 0; nowCount < 8; nowCount++) {
			if (oragin[nowCount] < 0) {
				tmp = (char) (((char) oragin[nowCount]) & 0x00FF);
			} else {
				tmp = (char) oragin[nowCount];
			}
			ret = ret | tmp;
			if (nowCount < 7) {
				ret <<= 8;
			}
		}

		return ret;
	}

	/**
	 * @describe Read Main View of info data
	 * 
	 * @param dataPath
	 *            : absolute path of data
	 * @param knowledgeID
	 *            : read one knowledge id
	 * @return boolean : read is compelete
	 */

	private boolean ReadMyData(String dataPath, String knowledgeID) {
		boolean ret = false;
		try {
			RandomAccessFile rf = new RandomAccessFile(dataPath, "r");
			long fileLen = rf.length();
			int index_len = 0;
			byte[] oneCharByte = new byte[4];
			byte[] indexBuffer = null;
			int count = 0;
			char[] oneChar = new char[1];
			String indexStr = "";
			int oneBufferLen = 0;
			int oneContent = 0;
			int index_seek_len = 0;
			int myData_Len = 0;

			rf.seek(24);
			rf.read(oneCharByte);
			index_seek_len = Str4Bytes2Int(oneCharByte);
			rf.read(oneCharByte);
			myData_Len = Str4Bytes2Int(oneCharByte);

			// Debug
			// index_seek_len = myData_Len = 0;

			// read my data flags
			rf.seek(index_seek_len + 4);
			rf.read(oneCharByte);
			index_len = Str4Bytes2Int(oneCharByte);
			buttonFlags.is_has_diy = index_len;
			rf.read(oneCharByte);
			index_len = Str4Bytes2Int(oneCharByte);
			buttonFlags.is_has_test = index_len;
			rf.read(oneCharByte);
			index_len = Str4Bytes2Int(oneCharByte);
			buttonFlags.is_has_summary = index_len;

			// read kind of course
			rf.seek(index_seek_len + 4 + buttonFlags.total_flags);
			rf.read(oneCharByte);
			index_len = Str4Bytes2Int(oneCharByte);
			index_len = index_len < MyDataPath.KIND_OF_COURSE.length ? index_len
					: 0;
			my_course_id = MyDataPath.KIND_OF_COURSE[index_len];
			flashPath = MyDataPath.FLASH_DATA_PATH[my_course_id];

			// read relate course index
			// read index len
			rf.seek(index_seek_len + 8 + buttonFlags.total_flags);
			indexBuffer = null;
			rf.read(oneCharByte);
			index_len = Str4Bytes2Int(oneCharByte);

			// relateCourseIndexDataOffset = rf.getFilePointer();
			// summaryDataOffset = relateCourseIndexDataOffset + index_len * 2;
			// read relate course index buffer
			if (0 >= index_len) {
				Message msg = new Message();
				msg.what = HANDLER_MSG_READ_DATA_ERROR;
				mHandler.sendMessage(msg);
			}
			indexBuffer = new byte[index_len];
			rf.read(indexBuffer);
			count = 0;
			indexStr = "";
			oneBufferLen = 0;
			boolean getRealTeachingDataFlag = false;
			int compareCount = 0;
			int maxCompareCount = 0;
			// MyKnowledgIndexData preCompareData = new MyKnowledgIndexData();
			// preCompareData = myKnowledgeIndexData.get(callCount);
			maxCompareCount = getMaxRelateCourseCount(knowledgeID);
			int contentData_len = 0;
			int address_pos = 0;
			long x = rf.getFilePointer();
			rf.read(oneCharByte);
			contentData_len = Str4Bytes2Int(oneCharByte);

			// read index buffer
			byte[] contentBuffer = new byte[contentData_len * 2];
			rf.read(contentBuffer);
			String tmp_str = "";
			// long readFileNowPos = 0;
			for (count = 0; count < indexBuffer.length && count < index_len * 2; count += 2) {
				if (compareCount >= maxCompareCount && 0 == address_pos) {
					break;
				} else if (-1 == address_pos) {
					count += 2;
					address_pos++;
					continue;
				}
				if (0 == oneBufferLen) {
					oneBufferLen = (((indexBuffer[count] > 0 ? indexBuffer[count]
							: (indexBuffer[count] & 0x00FF)))
							| (((indexBuffer[count + 1] > 0 ? indexBuffer[count + 1]
									: (indexBuffer[count + 1] & 0x00FF))) << 8)
							| (((indexBuffer[count + 2] > 0 ? indexBuffer[count + 2]
									: (indexBuffer[count + 2] & 0x00FF))) << 16) | (((indexBuffer[count + 3] > 0 ? indexBuffer[count + 3]
							: (indexBuffer[count + 3] & 0x00FF))) << 24));
					if (1 == address_pos) {
						address_pos = oneBufferLen;
						readDataFromAddress(rf, index_seek_len + address_pos,
								dataPath, index_seek_len);
						address_pos = 0;
					} else if (2 == address_pos) {

					}
					count += 2;
				} else {
					oneChar[0] = (char) ((indexBuffer[count] > 0 ? indexBuffer[count]
							: (indexBuffer[count] & 0x00FF)) | (((indexBuffer[count + 1] > 0 ? indexBuffer[count + 1]
							: (indexBuffer[count + 1] & 0x00FF))) << 8));
					indexStr += oneChar[0];
					if (oneBufferLen == indexStr.length()) {
						// tmp_str += indexStr + "|";
						if (0 == buttonFlags.is_has_summary) {
							address_pos = 2;
						} else {

						}
						if (isHaveThisRelateCourse(knowledgeID, indexStr)) {
							address_pos = 1;
							// getMyOneDataInfo(contentBuffer, contentData_len,
							// indexStr);
							compareCount++;
						} else {
							address_pos = -1;
						}
						indexStr = "";
						oneBufferLen = 0;
					}
				}
			}

			// if (0 < totalData.size()) {
			// // read myData len
			// rf.seek(index_seek_len);
			// rf.read(oneCharByte);
			// index_len = Str4Bytes2Int(oneCharByte);
			// readResDataFromAddress(new File(dataPath), rf, index_seek_len
			// + index_len, index_seek_len + myData_Len,
			// MyDataPath.RES_DATA_PATH[my_course_id]);
			// }

			rf.close();
			ret = true;
		} catch (FileNotFoundException e) {
			if (DebugFlag) {
				MyErrorPrintfActivity.startMyErrorPrintfActivity(this, e);
			} else {
				e.printStackTrace();
				Toast.makeText(this, R.string.appShutdownStr_readData,
						Toast.LENGTH_SHORT).show();
			}
			finish();
		} catch (IOException e) {
			if (DebugFlag) {
				MyErrorPrintfActivity.startMyErrorPrintfActivity(this, e);
			} else {
				e.printStackTrace();
				Toast.makeText(this, R.string.appShutdownStr_readData,
						Toast.LENGTH_SHORT).show();
			}
			finish();
		}

		return ret;
	}

	/**
	 * @describe Read One Res Data From one Knowledge images
	 * 
	 * @param File
	 *            srcDataFile : opened Res data file
	 * @param RandomAccessFile
	 *            rf : opened write target res data file
	 * @param long offset : rf's read offset
	 * @param long fileLen : rf's read buffer len
	 * @param String
	 *            thisDataPath : read data absolute path
	 */

	public void readOneResDataFromAddress(File srcDataFile,
			RandomAccessFile rf, long offset, long fileLen, String thisDataPath)
			throws IOException {
		if (rf.length() < offset) {
			if (DebugFlag) {
				Log.i("readResDataFromAddress->", "cannot found img");
			}
			return;
		}
		rf.seek(offset);
		byte[] oneCharByte = new byte[4];
		int index_len = 0;
		byte[] indexBuffer = null;
		long count = 0;
		String indexStr = "";
		// read bmp file name len
		rf.read(oneCharByte);
		index_len = Str4Bytes2Int(oneCharByte) * 2;
		indexBuffer = new byte[index_len];
		rf.read(indexBuffer);
		indexStr = bytesUnicode2Str(indexBuffer);
		// if (DebugFlag) {
		// Log.i(debugStr, "bmp----> " + indexStr);
		// }
		// create this res file
		rf.read(oneCharByte);
		index_len = Str4Bytes2Int(oneCharByte);
		if (null == indexStr || indexStr.isEmpty()) {
			return;
		}
		File tmpFile = new File(thisDataPath + indexStr);
		if (!tmpFile.exists()) {
			File tmpFile_2 = new File(thisDataPath);
			tmpFile_2.mkdirs();
			RandomAccessFile tmpRf = new RandomAccessFile(tmpFile, "rw");
			indexBuffer = null;
			indexBuffer = new byte[index_len];
			rf.read(indexBuffer);
			tmpRf.write(indexBuffer);
			tmpRf.close();
		} else {
			// 先判斷是否有更新文件
			if (tmpFile.lastModified() < srcDataFile.lastModified()) {
				tmpFile.delete();
				File tmpFile_2 = new File(thisDataPath);
				tmpFile_2.mkdirs();
				RandomAccessFile tmpRf = new RandomAccessFile(tmpFile, "rw");
				indexBuffer = null;
				indexBuffer = new byte[index_len];
				rf.read(indexBuffer);
				tmpRf.write(indexBuffer);
				tmpRf.close();
			} else {
				long tmpFilePos = rf.getFilePointer();
				tmpFilePos += index_len;
				rf.seek(tmpFilePos);
			}
		}

	}

	/**
	 * @describe Read All course res data (this function is not using, and
	 *           instead of * by readOneResDataFromAddress())
	 * 
	 * @param File
	 *            srcDataFile : opened res data file
	 * @param RandomAccessFile
	 *            rf : opened write target res data file
	 * @param long offset : read rf's offset
	 * @param long fileLen : read rf's buffer len
	 * @param String
	 *            thisDataPath : res data absolute path
	 */

	public void readResDataFromAddress(File srcDataFile, RandomAccessFile rf,
			long offset, long fileLen, String thisDataPath) throws IOException {
		if (rf.getFilePointer() >= offset) {
			if (DebugFlag) {
				Log.i("readResDataFromAddress->", "cannot found img");
			}
			return;
		}
		rf.seek(offset);
		byte[] oneCharByte = new byte[4];
		int index_len = 0;
		byte[] indexBuffer = null;
		long count = 0;
		String indexStr = "";

		for (count = index_len; count < fileLen; count = rf.getFilePointer()) {
			// read bmp file name len
			rf.read(oneCharByte);
			index_len = Str4Bytes2Int(oneCharByte) * 2;
			indexBuffer = new byte[index_len];
			rf.read(indexBuffer);
			indexStr = bytesUnicode2Str(indexBuffer);
			// if (DebugFlag) {
			// Log.i(debugStr, "bmp----> " + indexStr);
			// }
			// create this res file
			rf.read(oneCharByte);
			index_len = Str4Bytes2Int(oneCharByte);
			if (null == indexStr || indexStr.isEmpty()) {
				break;
			}
			File tmpFile = new File(thisDataPath + indexStr);
			if (!tmpFile.exists()) {
				File tmpFile_2 = new File(thisDataPath);
				tmpFile_2.mkdirs();
				RandomAccessFile tmpRf = new RandomAccessFile(tmpFile, "rw");
				indexBuffer = null;
				indexBuffer = new byte[index_len];
				rf.read(indexBuffer);
				tmpRf.write(indexBuffer);
				tmpRf.close();
			} else {
				// 先判斷是否有更新文件
				if (tmpFile.lastModified() < srcDataFile.lastModified()) {
					tmpFile.delete();
					File tmpFile_2 = new File(thisDataPath);
					tmpFile_2.mkdirs();
					RandomAccessFile tmpRf = new RandomAccessFile(tmpFile, "rw");
					indexBuffer = null;
					indexBuffer = new byte[index_len];
					rf.read(indexBuffer);
					tmpRf.write(indexBuffer);
					tmpRf.close();
				} else {
					long tmpFilePos = rf.getFilePointer();
					tmpFilePos += index_len;
					rf.seek(tmpFilePos);
					break;
				}
			}
			//
		}

	}

	/**
	 * @describe Unicode byte[] convert Char[] (byte[2] convert char[1])
	 * 
	 * @param byte[] buffer : convert byte[]
	 * @return String : return string of Unicode of encode
	 */

	public String bytesUnicode2Str(byte[] buffer) {
		int count = 0;
		int totalCount = buffer.length;
		char[] oneChar = new char[1];
		String indexStr = "";

		for (count = 0; count < totalCount; count += 2) {
			oneChar[0] = (char) ((buffer[count] > 0 ? buffer[count]
					: (buffer[count] & 0x00FF)) | (((buffer[count + 1] > 0 ? buffer[count + 1]
					: (buffer[count + 1] & 0x00FF))) << 8));
			indexStr += oneChar[0];
		}

		return indexStr;
	}

	/**
	 * @describe string of byte[4] convert int
	 * 
	 * @param byte[] bytes4 : string of byte[4]
	 * @return int : return int of type
	 */

	public static int Str4Bytes2Int(byte[] bytes4) {
		return (((bytes4[0] > 0 ? bytes4[0] : (bytes4[0] & 0x00FF)))
				| (((bytes4[1] > 0 ? bytes4[1] : (bytes4[1] & 0x00FF))) << 8)
				| (((bytes4[2] > 0 ? bytes4[2] : (bytes4[2] & 0x00FF))) << 16) | (((bytes4[3] > 0 ? bytes4[3]
				: (bytes4[3] & 0x00FF))) << 24));
	}

	/**
	 * @describe Read Main View data from path
	 * 
	 * @param RandomAccessFile
	 *            rf : opened read data file
	 * @param int address_pos : rf's read offset
	 * @param String
	 *            dataPath : data file absolute path
	 * @param long offset : this valute is not using
	 */

	public void readDataFromAddress(RandomAccessFile rf, int address_pos,
			String dataPath, long offset) throws IOException {
		rf.seek(address_pos);
		int oneBufferLen = 0;
		byte[] oneCharByte = new byte[4];
		int index_len = 0;
		byte[] indexBuffer = null;
		int count = 0;
		char[] oneChar = new char[1];
		String indexStr = "";
		int readBufferCount = 0;
		int total_read_content_kind = 4;
		int img_count = 1;
		int img_count_len = 4;
		int[] img_start_pos = null;

		MyOneData tmpOneData = new MyOneData();
		for (readBufferCount = 0; readBufferCount < total_read_content_kind; readBufferCount++) {
			rf.read(oneCharByte);
			index_len = Str4Bytes2Int(oneCharByte);
			int malloc_len = 0;
			if (img_count == readBufferCount) {
				malloc_len = index_len * 4;
				img_start_pos = new int[index_len];
			} else {
				malloc_len = index_len * 2;
			}
			if (0 >= malloc_len) {
				continue;
			}
			indexBuffer = new byte[malloc_len];
			rf.read(indexBuffer);
			if (img_count == readBufferCount) {
				int start_pos = Str4Bytes2Int(indexBuffer);
				if (0xFEFEFEFE != start_pos) {
					img_start_pos = getOneItemImgStartAdress(indexBuffer);
				}
			} else {
				for (count = 0; count < malloc_len; count += 2) {
					oneChar[0] = (char) ((indexBuffer[count] > 0 ? indexBuffer[count]
							: (indexBuffer[count] & 0x00FF)) | (((indexBuffer[count + 1] > 0 ? indexBuffer[count + 1]
							: (indexBuffer[count + 1] & 0x00FF))) << 8));
					indexStr += oneChar[0];
				}
			}
			if (0 == readBufferCount) {
				tmpOneData.flashFileName = tmpOneData.summaryName = indexStr;
			} else if (1 == readBufferCount && null != img_start_pos) {
				// img count
				// read myData len
				int myDatalen = 0;
				long preReadDataPos = rf.getFilePointer();
				rf.seek(offset);
				rf.read(oneCharByte);
				myDatalen = Str4Bytes2Int(oneCharByte);
				for (int i = 0; i < img_start_pos.length; i++) {
					readOneResDataFromAddress(new File(dataPath), rf, offset
							+ myDatalen + img_start_pos[i], 0,
							MyDataPath.RES_DATA_PATH[my_course_id]);
				}
				rf.seek(preReadDataPos);
			} else if (2 == readBufferCount) {
				tmpOneData.unit_id = indexStr;
			} else if (3 == readBufferCount) {
				// StringBuffer contentBuf = new StringBuffer("");
				// indexStr = getOneSummaryHtmlData(indexStr);
				tmpOneData.summaryContent = indexStr;
				totalData.add(tmpOneData);
			}
			indexBuffer = null;
			indexStr = "";
			img_start_pos = null;
		}
	}

	/**
	 * @describe Get one item from Main View data file start adress
	 * 
	 * @param byte[] buffer : from data file read of buffer
	 * @return int[] : return byte[4] of int of start adress
	 */

	private int[] getOneItemImgStartAdress(byte[] buffer) {
		int[] ret = null;
		int bufferLen = buffer.length;
		byte[] oneData = new byte[4];
		int j = 0;

		ret = new int[bufferLen / 4];
		for (int count = 0; count < bufferLen;) {
			for (int i = 0; i < 4; i++, count++) {
				oneData[i] = buffer[count];
			}
			ret[j] = Str4Bytes2Int(oneData);
			j++;
		}

		return ret;
	}

	// summary html head buffer
	private String[] summaryHtmlBuffer = {
			"<html>\r\n<head>\r\n<style>\r\np.new_line {\r\ntext-indent:0.5em;\r\n}\r\np.table_1 {\r\ntext-indent:2em;\r\n}\r\np.table_2 {\r\ntext-indent:4em;\r\n}\r\n</style>\r\n</head><body>\r\n", // 0
			"\r\n</body>\r\n</html>", // 1
			"<p class=\"new_line\">", // 2
			"<p class=\"table_1\">", // 3
			"<p class=\"table_2\">" // 4

	};

	/**
	 * @describe Get one summary html data from Main View data file
	 * 
	 * @param String
	 *            buffer : read string buffer from Main View data file
	 * @return String : one item summary string buffer
	 */

	public String getOneSummaryHtmlData(String buffer) {
		String ret = "";

		byte[] pBuffer = buffer.getBytes();
		int totalCount = buffer.length();
		int count = 0;
		char[] oneChar = new char[1];

		for (count = 0; count < totalCount; count += 2) {
			oneChar[0] = (char) ((pBuffer[count] > 0 ? pBuffer[count]
					: (pBuffer[count] & 0x00FF)) | (((pBuffer[count + 1] > 0 ? pBuffer[count + 1]
					: (pBuffer[count + 1] & 0x00FF))) << 8));
			if ('\r' == oneChar[0]) {
				int a = 0;
				a = 1;
			}
		}

		return ret;
	}

	/**
	 * @describe Get max relate course count
	 * 
	 * @param String
	 *            data : read from Main View data file
	 * @return int : get max relate couse count
	 */

	public int getMaxRelateCourseCount(String data) {
		int ret = 1;

		int count = 0;
		int totalCount = data.length();
		byte[] tmpBytes = data.getBytes();
		for (count = 0; count < totalCount; count++) {
			if ('|' == tmpBytes[count]) {
				ret++;
			}
		}

		return ret;
	}

	/**
	 * @describe Get MyOneData class struct info
	 * 
	 * @param byte[] indexBuffer : read from Main View data file
	 * @param long index_len : read buffer len
	 * @param String
	 *            summaryID : one item of knowledge's summary id
	 */

	public void getMyOneDataInfo(byte[] indexBuffer, long index_len,
			String summaryID) throws IOException {
		int count = 0;
		char[] oneChar = new char[1];
		String indexStr = "";
		int oneBufferLen = 0;
		int oneContent = 0;
		MyOneData tmpOneData = null;
		for (count = 0; count < index_len * 2; count += 2) {
			if (0 == oneBufferLen) {
				oneBufferLen = (((indexBuffer[count] > 0 ? indexBuffer[count]
						: (indexBuffer[count] & 0x00FF)))
						| (((indexBuffer[count + 1] > 0 ? indexBuffer[count + 1]
								: (indexBuffer[count + 1] & 0x00FF))) << 8)
						| (((indexBuffer[count + 2] > 0 ? indexBuffer[count + 2]
								: (indexBuffer[count + 2] & 0x00FF))) << 16) | (((indexBuffer[count + 3] > 0 ? indexBuffer[count + 3]
						: (indexBuffer[count + 3] & 0x00FF))) << 24));
				count += 2;
			} else {
				if (0 > oneContent) {
					count += oneBufferLen * 2 - 2;
					oneContent++;
					oneBufferLen = 0;
					continue;
				}
				oneChar[0] = (char) ((indexBuffer[count] > 0 ? indexBuffer[count]
						: (indexBuffer[count] & 0x00FF)) | (((indexBuffer[count + 1] > 0 ? indexBuffer[count + 1]
						: (indexBuffer[count + 1] & 0x00FF))) << 8));
				indexStr += oneChar[0];
				if (oneBufferLen == indexStr.length()) {
					if (0 == oneContent) {
						if (indexStr.equals(summaryID)) {
							oneContent = 1;
							tmpOneData = new MyOneData();
							tmpOneData.flashFileName = summaryID;
							tmpOneData.summaryName = summaryID;
						} else {
							oneContent = -2;
						}
					} else if (1 == oneContent) {
						tmpOneData.unit_id = indexStr;
						oneContent = 2;
						// break;
					} else if (2 == oneContent) {
						tmpOneData.summaryContent = indexStr;
						totalData.add(tmpOneData);
						oneContent = 0;
						break;
					}

					indexStr = "";
					oneBufferLen = 0;
				}
			}
		}
	}

	/**
	 * @describe check this relate course is right
	 * 
	 * @param String
	 *            knowledgeID : check of knowledge id
	 * @param String
	 *            relateCourseName : wating for check id
	 * @return boolean : return this id is right
	 */

	public boolean isHaveThisRelateCourse(String knowledgeID,
			String relateCourseName) {
		boolean ret = false;

		if (0 <= knowledgeID.indexOf(relateCourseName)) {
			ret = true;
		}

		return ret;
	}

	/**
	 * @describe check Adobe flash palyer plugin is already installed
	 * 
	 * @param Context
	 *            context : this activity context
	 * @return boolean : return adobe flash is installed or not
	 */

	public static boolean isAdobeFlashInstalled(Context context) {
		String ADOBEFLASHPACKEGE = "com.adobe.flashplayer";
		PackageManager pm = context.getPackageManager();
		try {
			pm.getPackageInfo(ADOBEFLASHPACKEGE, 0);
		} catch (PackageManager.NameNotFoundException nameNotFoundException) {
			return false;
		}
		return true;
	}

	/**
	 * @describe this function is using by Select Dict View
	 */

	private class DictViewSelectionListener implements SelectionListener {
		MyCrossWebView mWebView = null;

		/**
		 * @describe this is Dict Select Listener construct
		 * 
		 * @param MyCrossWebView
		 *            mWebView : MyCrossWebView(override WebView)
		 */

		public DictViewSelectionListener(MyCrossWebView mWebView) {
			this.mWebView = mWebView;
		}

		/**
		 * @describe Select string buffer from html
		 * 
		 * @param String
		 *            content : select html buffer
		 * @param int type : select type
		 * @param View
		 *            srcView : Main View
		 * @return boolean : return select is compelete
		 */

		public boolean onContentSelected(String content, int type, View srcView) {
			boolean ret = false;
			switch (type) {
			case SELECT_TEXT:
				mWebView.clearFocus();
				searchBuf = content;
				mWebView.loadUrl("javascript:window.html.showHtml(document.getElementsByTagName('body')[0].innerHTML);");
				ret = true;
				break;
			default:
				break;
			}
			return ret;
		}

		public boolean onLoadFinish(WebView wv) {
			wv.loadUrl("javascript:SetState('djkk', 'KK')");
			return false;
		}
	}

	class JavaScriptInterface {
		private MyCrossWebView mWebView = null;

		public JavaScriptInterface(MyCrossWebView mWebView) {
			this.mWebView = mWebView;
		}

		public void showHtml(String html) {
			Log.i("HTML", html);
			// mWebView.clearFocus();
			CrossSearchInfo crossSearchInfo = new CrossSearchInfo();
			crossSearchInfo.setContext(CrossWebViewRealTeachingActivity.this);// activity��context
			crossSearchInfo.setSearchContent(searchBuf);// searchStr���Ϭd���e
			crossSearchInfo.setSearchLayer(0);// �Ϭd�h��
			crossSearchInfo.setHtml(html);// html���������X�r��
			crossSearchInfo.setMarkRange(SignHelper.getMarkRange(mWebView, 1));
			crossSearchInfo.setDefaultParaphraseLan("");

			try {
				popUpWindow = new RetrievalPopUpWindow(crossSearchInfo);
			} catch (Exception e) {
				popUpWindow = new RetrievalPopUpWindow(crossSearchInfo);
			}
			if (popUpWindow != null) {
				PopupWindow temp = popUpWindow.getPopupWindow();
				if (temp != null) {
					temp.showAtLocation(mWebView, Gravity.NO_GRAVITY, 70, 200);
				}
			}
		}
	}
}