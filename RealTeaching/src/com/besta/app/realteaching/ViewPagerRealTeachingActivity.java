package com.besta.app.realteaching;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManager.DisplayListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.besta.view.crosssearchwin.RetrievalPopUpWindow;

public class ViewPagerRealTeachingActivity extends Activity {
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
	public static final int HANDLER_MSG_DOWNLOADFLASH_FAIL_ONE = 0x0030;

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

	/** Called when the activity is first created. */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mAdapter.onDestroy();
		unregisterReceiver(mReceiver);
		int myProcessPid = android.os.Process.myPid();
		// android.os.Process.killProcess(myProcessPid);
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mAdapter.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		isNotPlayFlashFlag = onResumeWifiDirect();
		if (null != mAdapter) {
			mAdapter.onResume();
		}
		super.onResume();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		mAdapter.onConfigurationChanged();
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub

		super.onNewIntent(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

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
	MyRealTeachingViewPager mViewPager = null;
	String knowledgeDataPath = "";
	String oneKnowledgeID = "";
	String oneKnowledgeName = "";
	ArrayList<String> all_knowledgeID = new ArrayList<String>();
	String divide_str = "|";
	public int my_course_id = 0;
	ViewPagerAdapter mAdapter = null;
	public static LinearLayout waiting_layout = null;
	public static String personalQuestions = "";
	int sdk_ver = getAndroidSDKVersion();
	int WIFI_DIRECT_SDK_VER = 17;

	@Override
	public void onCreate(Bundle savedInstanceState) {
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
		setContentView(R.layout.realteatching_view_pager);
		mContext = this;
		pkg_name = this.getPackageName();
		Bundle bundle = getIntent().getExtras();
		if (null != bundle) {
			oneKnowledgeID = bundle.getString(KNOWLEDGE_ID_KEY);
			knowledgeDataPath = bundle.getString(KNOWLEDGE_DATA_PATH_KEY);
			oneKnowledgeName = bundle.getString(KNOWLEDGE_ID_NAME_KEY);
			all_knowledgeID = bundle.getStringArrayList(ALL_KNOWLEDGE_ID);
			click_knowledgeID_count = bundle.getInt(CLICK_KNOWLEDGE_ID);
			personalQuestions = bundle.getString("personalQuestions");

			mViewPager = (MyRealTeachingViewPager) findViewById(R.id.realteaching_viewpager);
			mAdapter = new ViewPagerAdapter(this, all_knowledgeID,
					knowledgeDataPath, mViewPager);
			mViewPager.setAdapter(mAdapter);
			// mViewPager.setOnPageChangeListener(page_listener);
			mViewPager.setCurrentItem(click_knowledgeID_count);
			waiting_layout = (LinearLayout) findViewById(R.id.loading_data_view_1);
			waiting_layout.setBackgroundColor(0xAA000000);
			waiting_layout.setVisibility(View.VISIBLE);
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction(KnowledgeEgnRecevier.ACTION_HDMI_PLUGGED);
		mReceiver = new KnowledgeEgnRecevier();
		registerReceiver(mReceiver, filter);
		initializeWifiDirect();
		// initializeData(knowledgeDataPath, oneKnowledgeID);
	}

	public KnowledgeEgnRecevier mReceiver = null;

	public class KnowledgeEgnRecevier extends BroadcastReceiver {
		private Context mContext;
		public static final String ACTION_HDMI_PLUGGED = "android.intent.action.HDMI_PLUGGED";

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(ACTION_HDMI_PLUGGED)) {
				// true not play flash
				isHDMIFlag = intent.getBooleanExtra("state", false);
				if (null != mAdapter) {
					mAdapter.onResume();
				}
				return;
			}
		}
	}

	public DisplayManager dm = null;
	public static boolean isNotPlayFlashFlag = false;
	public static boolean isHDMIFlag = false;

	public boolean onResumeWifiDirect() {
		if (sdk_ver < WIFI_DIRECT_SDK_VER) {
			isNotPlayFlashFlag = false;
			return false;
		}
		boolean ret = false;
		dm = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
		if (null != dm) {
			Display[] ds = dm.getDisplays();
			if (null != ds && 1 < ds.length) {
				ret = true;
			}
		}
		return ret;
	}

	public void initializeWifiDirect() {
		if (sdk_ver < WIFI_DIRECT_SDK_VER) {
			isNotPlayFlashFlag = false;
			return;
		}
		isNotPlayFlashFlag = onResumeWifiDirect();
		if (null != dm) {
			DisplayManager.DisplayListener dmListener = new DisplayListener() {

				@Override
				public void onDisplayRemoved(int displayId) {
					// TODO Auto-generated method stub
					// Toast.makeText(mContext, "onDisplayRemoved = " +
					// displayId,
					// Toast.LENGTH_SHORT).show();
					isNotPlayFlashFlag = onResumeWifiDirect();
				}

				@Override
				public void onDisplayChanged(int displayId) {
					// TODO Auto-generated method stub
					// Toast.makeText(mContext, "onDisplayChanged = " +
					// displayId,
					// Toast.LENGTH_SHORT).show();
					isNotPlayFlashFlag = onResumeWifiDirect();
				}

				@Override
				public void onDisplayAdded(int displayId) {
					// TODO Auto-generated method stub
					// Toast.makeText(mContext, "onDisplayAdded = " + displayId,
					// Toast.LENGTH_SHORT).show();
					isNotPlayFlashFlag = onResumeWifiDirect();
				}
			};
			dm.registerDisplayListener(dmListener, mHandler);
		}
	}

	private OnPageChangeListener page_listener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};

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
				waiting_layout.setVisibility(View.GONE);
				int curItem = mViewPager.getCurrentItem();
				Message updateFlashMsg = new Message();
				updateFlashMsg.what = ViewPagerAdapter.VIEWPAGER_MSG_CHECK_FLASH_UPDATE;
				mAdapter.mHandler.sendMessage(updateFlashMsg);
				// if (null != mAdapter) {
				// mAdapter.onResume();
				// }
				// this.sendMessage(updateFlashMsg);
				// PrepareLoadData(curItem);
				break;
			case HANDLER_MSG_LOADING_DIY_DATA_SUCCESS:
				break;
			case HANDLER_MSG_CHECK_FLASH_UPDATE:
				break;
			case HANDLER_MSG_SHOW_UPDATE_FLASH_BT:
				// check flash need update and refresh UI
				break;
			case HANDLER_MSG_DOWNLOADFLASH_SUCCEED:
				// 下載一個flash文件成功，需要刷新一下list的顯示。
				// getFlashDownloadState(true);
				Message tmpMsg = new Message();
				tmpMsg.what = ViewPagerAdapter.VIEWPAGER_MSG_DOWNLOAD_SUCCESS;
				mAdapter.setHandlerMSG(tmpMsg);
				break;
			case HANDLER_MSG_DOWNLOADFLASH_FAIL_OTHER:
				// download flash failed
			case HANDLER_MSG_DOWNLOADFLASH_FAIL_NOWIFI:
				// WIFI is not started
			case HANDLER_MSG_DOWNLOADFLASH_FAIL_SDCARD:
				// sdcard is not enough to download flash
			case HANDLER_MSG_DOWNLOADFLASH_CANCELALL:
				// cancel download flashs
				// reFreshDownloadIconStatus(mContext, selectOneItemLayoutVer);
				Message tmpMsg1 = new Message();
				tmpMsg1.what = ViewPagerAdapter.VIEWPAGER_MSG_DOWNLOADFLASH_CANCELALL;
				mAdapter.setHandlerMSG(tmpMsg1);
				break;
			case HANDLER_MSG_DOWNLOADFLASH_FAIL_ONE:
				Message tmpMsg11 = new Message();
				tmpMsg11.what = ViewPagerAdapter.VIEWPAGER_MSG_SHOW_UPDATE_FLASH_BT;
				tmpMsg11.setData(msg.getData());
				mAdapter.setHandlerMSG(tmpMsg11);
				break;
			case HANDLER_MSG_WIFI_ISCONNECTED:
				// 收到wifi連接成功的廣播消息
				break;
			case HANDLER_MSG_WIFI_ISDISCONNECTED:
				// 收到wifi連接斷開的廣播消息
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (PlayFlash.flashRequestCode == requestCode) {
			if (null != mAdapter && mAdapter.getPlayingDIYFlashFlag()) {
				mAdapter.setPlayingDIYFlashFlag(false);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
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
		ret = false;
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
}