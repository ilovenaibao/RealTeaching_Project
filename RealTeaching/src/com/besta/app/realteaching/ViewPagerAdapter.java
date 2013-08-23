package com.besta.app.realteaching;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;
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
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
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

public class ViewPagerAdapter extends PagerAdapter {
	// for using debug app
	public boolean DebugFlag = true;
	public static String debugStr = "ViewPager--->";

	// refresh msg
	public final static int VIEWPAGER_REFRESH_MSG_NONE = 0x0001;
	public final static int VIEWPAGER_REFRESH_MSG_REFRESH = 0x0002;
	// handler msg
	public final int VIEWPAGER_MSG_LOADING_ONE_DATA_SUCCESS = 0x0001;
	public final int VIEWPAGER_MSG_START_LOADING = 0x0002;
	public final int VIEWPAGER_MSG_COMPELETE = 0x0003;
	public final int VIEWPAGER_MSG_WIFI_CONNECT_ERROR = 0x0004;
	public final static int VIEWPAGER_MSG_SHOW_UPDATE_FLASH_BT = 0x0005;
	public final static int VIEWPAGER_MSG_DOWNLOAD_SUCCESS = 0x0006;
	public final static int VIEWPAGER_MSG_CHECK_FLASH_UPDATE = 0x0007;
	public final static int VIEWPAGER_MSG_DOWNLOADFLASH_CANCELALL = 0x0008;
	public final int VIEWPAGER_MSG_LOADING_DIY_DATA_SUCCESS = 0x0009;
	// Message for activity handler
	public static final int DOWNLOADWAIT = 0x0011;
	public static final int DOWNLOADBEGIN = 0x0012;
	// varible value for create one Main View of widges
	public static final int BT_TEST = 0; // test button
	public static final int BT_DIY = 1; // diy button
	public static final int BT_UPDATE_FLASH = 2; // flash update button

	private static final String total_pkg_name = "PublicDatabase";

	private LayoutInflater mInflater;
	private Context mContext;
	private ArrayList<String> all_knowledgeID = null;
	private String knowledgePath = null;
	// play flash path and extern name
	public static String flashPath = "";
	public static String flashExtendName = ".bfe";
	public ArrayList<OneSubViewData> totalData = new ArrayList<OneSubViewData>();
	public ArrayList<OneSubViewData> totalData_now = new ArrayList<OneSubViewData>();
	public OneSubViewData nowLoadingData = new OneSubViewData();
	public boolean isOneThreadRunning = false;
	public ArrayList<MyViewPagerAdapterHandlerStruct> viewpagerHandlerList = new ArrayList<MyViewPagerAdapterHandlerStruct>();
	public ArrayList<MyViewPagerAdapterHandlerStruct> viewpagerHandlerList_copy = new ArrayList<MyViewPagerAdapterHandlerStruct>();
	private boolean save_info_flag = false;

	private int isPlayingPosition = -1;
	private boolean isUsingViewFlag = false;
	private MyRealTeachingViewPager viewpager = null;

	public ViewPagerAdapter(Context context, ArrayList<String> knowledgeID,
			String dataPath, MyRealTeachingViewPager mViewPager) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		all_knowledgeID = knowledgeID;
		knowledgePath = dataPath;
		save_info_flag = false;
		isPlayingPosition = -1;
		isUsingViewFlag = false;
		viewpager = mViewPager;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		// super.destroyItem(container, position, object);
		// container.removeViewAt(position);
	}

	private class AddPositionData {
		int position;

		public AddPositionData() {
			position = 0;
		}
	}

	private ArrayList<AddPositionData> add_position = new ArrayList<ViewPagerAdapter.AddPositionData>();

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		AddPositionData tmpAddPositionData = new AddPositionData();
		tmpAddPositionData.position = position;
		add_position.add(tmpAddPositionData);
		View view = initializeLoadingData(container, position);
		// View view = initializeLoadingData_2(container, position);
		return view;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return all_knowledgeID.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		if (isPlayingPosition != position) {
			for (int i = 0; i < totalData_now.size(); i++) {
				OneSubViewData tmpData = totalData_now.get(i);
				if (tmpData.viewPagerPosition == isPlayingPosition) {
					onCloseOldPlayingFlashView(mContext, null, tmpData);
					addNewPlayFlashView(mContext, null, tmpData);
				}
			}
			isPlayingPosition = -1;
			reFreshDownloadIconStatus_2(mContext, selectOneItemLayoutVer);
		}
		super.setPrimaryItem(container, position, object);
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		if (isUsingViewFlag) {
			return;
		}
		switch (reFreshKind) {
		case VIEWPAGER_REFRESH_MSG_NONE:
			break;
		case VIEWPAGER_REFRESH_MSG_REFRESH:
			getFlashDownloadState(true);
			reFreshDownloadIconStatus(mContext, selectOneItemLayoutVer);
			checkFlashIsNeedUpdate();
			break;

		}
		reFreshKind = VIEWPAGER_REFRESH_MSG_NONE;
		super.notifyDataSetChanged();
	}

	private int reFreshKind = VIEWPAGER_REFRESH_MSG_NONE;

	public void setNotifyDataSetChangedKind(int kind) {
		reFreshKind = kind;
	}

	public void onResume() {
		// isUsingViewFlag = false;
		onWifiDirectory();
		this.setNotifyDataSetChangedKind(ViewPagerAdapter.VIEWPAGER_REFRESH_MSG_REFRESH);
		this.notifyDataSetChanged();
	}

	public void onWifiDirectory() {
		if (null != mContext) {
			boolean breakFlag = false;
			int toastCount = 0;
			if (ViewPagerRealTeachingActivity.isNotPlayFlashFlag
					|| ViewPagerRealTeachingActivity.isHDMIFlag) {
				for (int i = 0; i < totalData_now.size(); i++) {
					OneSubViewData oneData = totalData_now.get(i);
					for (int count = 0; null != oneData.view
							&& count < oneData.view.length; count++) {
						if (null != oneData.view[count]
								&& oneData.isFlashPlaying[count]) {
							breakFlag = true;
							break;
						}
					}
					if (breakFlag) {
						onCloseOldPlayingFlashView_2(mContext, oneData);
						addNewPlayFlashView_2(mContext, oneData);
						breakFlag = false;
						if (toastCount == 0) {
							Toast.makeText(mContext, R.string.s030640,
									Toast.LENGTH_SHORT).show();
							toastCount++;
						}
					}
				}
				return;
			}
		}
	}

	public void onConfigurationChanged() {
		// if (null != onConfigChangeData) {
		// onCloseOldPlayingFlashView_2(mContext, onConfigChangeData);
		// addNewPlayFlashView_2(mContext, onConfigChangeData);
		// }
		for (int i = 0; i < totalData_now.size(); i++) {
			OneSubViewData oneData = totalData_now.get(i);
			for (int count = 0; null != oneData.view
					&& count < oneData.view.length; count++) {
				if (null != oneData.view[count]) {
					oneData.mScrollView.scrollTo(0, 0);
				}
			}
		}
	}

	public void onPause() {
		isUsingViewFlag = false;
		for (int i = 0; i < totalData_now.size(); i++) {
			OneSubViewData oneData = totalData_now.get(i);
			for (int count = 0; null != oneData.view
					&& count < oneData.view.length; count++) {
				if (null != oneData.view[count]) {
					switch (selectOneItemLayoutVer) {
					case 1: // 4.0
						FlashView2 flashView = (FlashView2) oneData.view[count]
								.findViewById(R.id.play_flash_webview);
						if (null != flashView) {
							if (oneData.isFlashPlaying[count]) {
								// flashView.doPause();
								flashView.onPause();
							}
						}
						break;
					case 2:
						FlashView flashView23 = (FlashView) oneData.view[count]
								.findViewById(R.id.play_flash_webview);
						if (null != flashView23) {
							if (oneData.isFlashPlaying[count]) {
								// flashView23.doPause();
								flashView23.onPause();
							}
						}
						break;
					}
				}
				isFirstShowFlashView = false;
			}
		}
	}

	public void onDestroy() {
		isUsingViewFlag = false;
		for (int i = 0; i < totalData_now.size(); i++) {
			OneSubViewData oneData = totalData_now.get(i);
			// oneData.mainViewLayout.removeAllViews();
			for (int count = 0; null != oneData.view
					&& count < oneData.view.length; count++) {
				if (null != oneData.view[count]) {
					switch (selectOneItemLayoutVer) {
					case 1: // 4.0
						FlashView2 flashView = (FlashView2) oneData.view[count]
								.findViewById(R.id.play_flash_webview);
						if (null != flashView) {
							// flashView.doDestroy();
							flashView.destroy();
						}
						break;
					case 2:
						FlashView flashView23 = (FlashView) oneData.view[count]
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
					// e.printStackTrace();
				}
				String basePath = "/sdcard/besta";
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
					// Internet using
					JSONArray inputJson = null;
					inputJson = new JSONArray();
					ArrayList<String> pathArray = getPathArrayList();
					getFlashDownloadState(mContext.getPackageName(), pathArray,
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
						OneSubViewData temp;
						int flashCount = 0;

						for (int i = 0; i < totalData_now.size(); i++) {
							temp = totalData_now.get(i);
							if (temp.flashFileName.isEmpty()) {

							} else {
								JSONObject jsonObject = (JSONObject) jsonNArray
										.get(flashCount);
								String flashname = jsonObject
										.getString("fileName");
								if (jsonObject.getBoolean("isNeedUpdate")) {
									File localFlashFile = null;
									String tmpOneFlashPath = GetfileMethod
											.getfilepath(
													MyDataPath.FLASH_DATA_PATH[my_course_id]
															+ flashname,
													mContext);
									if (null == tmpOneFlashPath) {
										tmpOneFlashPath = "/"
												+ MyDataPath.FLASH_DATA_PATH[my_course_id]
												+ flashname;
										File tmpFile = new File(tmpOneFlashPath);
										if (!tmpFile.exists()) {
											tmpOneFlashPath = "";
										}
									}
									if (null != tmpOneFlashPath
											&& !tmpOneFlashPath.equals("")) {
										localFlashFile = new File(
												tmpOneFlashPath);
									}
									if (MyOneData.FLASH_FILE_DOWNLOADING != temp.flashFileStatus
											&& !temp.isUpdating) {
										Date[] date = new Date[2];
										if (null != localFlashFile
												&& localFlashFile
														.lastModified() != 0) {
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
										temp.flashFileStatus = MyOneData.FLASH_FILE_DOWNLOADING;
										temp.isDownloading = true;
									}
									findfileCount++;
								} else {
									// 下面這句是個bug,在網站沒有找到這個文件并不等於這個文件本地就存在。
									// temp.updateType = ListDef.FLASH_EXIST;
									File localFlashFile = null;
									String tmpOneFlashPath = GetfileMethod
											.getfilepath(
													MyDataPath.FLASH_DATA_PATH[my_course_id]
															+ flashname,
													mContext);
									if (null == tmpOneFlashPath) {
										tmpOneFlashPath = "/"
												+ MyDataPath.FLASH_DATA_PATH[my_course_id]
												+ flashname;
										File tmpFile = new File(tmpOneFlashPath);
										if (!tmpFile.exists()) {
											tmpOneFlashPath = "";
										}
									}
									if (null != tmpOneFlashPath
											&& !tmpOneFlashPath.equals("")) {
										localFlashFile = new File(
												tmpOneFlashPath);
									}
									if (MyOneData.FLASH_FILE_DOWNLOADING != temp.flashFileStatus
											&& !temp.isUpdating) {
										Date[] date = new Date[2];
										if (null != localFlashFile
												&& localFlashFile
														.lastModified() != 0) {
											date[0] = new Date(localFlashFile
													.lastModified());
											date[1] = new Date(
													temp.fileLastModify);
											Log.e(debugStr,
													"not update flash---->"
															+ flashname
															+ ", "
															+ date[0]
																	.toString()
															+ " | "
															+ date[1]
																	.toString());
											temp.flashFileStatus = MyOneData.FLASH_FILE_EXIST;
										} else {
											temp.flashFileStatus = MyOneData.FLASH_FILE_NEED_DOWNLOAD;
										}
									} else { // 該flash文件正在下載中
										temp.flashFileStatus = MyOneData.FLASH_FILE_DOWNLOADING;
										temp.isDownloading = true;
									}
									// if (MyOneData.FLASH_FILE_EXIST !=
									// temp.flashFileStatus) {
									//
									// }
								}
								flashCount++;
							}
						}
					}

				} catch (InterruptedIOException e) {
					Message msg = new Message();
					msg.what = VIEWPAGER_MSG_WIFI_CONNECT_ERROR;
					mHandler.sendMessage(msg);
					// e.printStackTrace();
				} catch (IOException e) {
					Message msg = new Message();
					msg.what = VIEWPAGER_MSG_WIFI_CONNECT_ERROR;
					mHandler.sendMessage(msg);
					// e.printStackTrace();
				} catch (JSONException e) {
					// e.printStackTrace();
				} catch (Exception e) {

				} finally {
					Message tmpMsg = new Message();
					tmpMsg.what = VIEWPAGER_MSG_SHOW_UPDATE_FLASH_BT;
					mHandler.sendMessage(tmpMsg);
				}
			}
		});
		th.start();

		// Message tmpMsg = new Message();
		// tmpMsg.what = VIEWPAGER_MSG_SHOW_UPDATE_FLASH_BT;
		// mHandler.sendMessage(tmpMsg);
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
			// e.printStackTrace();
			folderString = null;
		}
		Log.e("debug==", "GetFolderService: " + folderString);
		return folderString;
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
		for (int j = 0; null != totalData_now && j < totalData_now.size(); j++) {
			OneSubViewData oneData = totalData_now.get(j);
			for (int i = 0; null != oneData.view && i < oneData.view.length; i++) {
				if (null != oneData.view[i]) {
					// setting 數據
					// settingOneItemLayoutVer(context, view[i], oneData, i,
					// selectOneItemLayoutVer);
					switch (selectLayoutVer) {
					case 1: // 4.0
						// 設置PlayFlash View
						FlashView2 flashView = (FlashView2) oneData.view[i]
								.findViewById(R.id.play_flash_webview);
						if (null != flashView) {
							LayoutParams lp = flashView.getLayoutParams();
							// lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
							// lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
							// // lp.height = 430;
							// flashView.setLayoutParams(lp);
							if (isFirstShowFlashView) {
								flashView.setVisibility(View.GONE);
							} else if (oneData.isFlashPlaying[i]) {
								// flashView.setVisibility(View.GONE);
								boolean isClassDef = false;
								String clssName = "com.besta.hardware.BestaHDMI";
								isClassDef = MyExceptionInfo
										.checkTargetClassExist(context,
												"com.besta.app.realteaching",
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
									Toast.makeText(context,
											R.string.appShutdownStr,
											Toast.LENGTH_SHORT).show();
									((Activity) context).finish();
								}
							}
						}
						// 設置PlayFlash imgView
						ImageView playFlashImgView = (ImageView) oneData.view[i]
								.findViewById(R.id.play_flash_view_img);
						ImageView playFlashBackgroundImgView = (ImageView) oneData.view[i]
								.findViewById(R.id.play_flash_view_bg_img);
						// 更新Flash imgView
						ImageView updateFlashImgView = (ImageView) oneData.view[i]
								.findViewById(R.id.play_flash_view_img_update);

						if (null != playFlashImgView
								&& null != playFlashBackgroundImgView
								&& null != updateFlashImgView) {
							playFlashImgView.setVisibility(View.VISIBLE);
							playFlashBackgroundImgView
									.setVisibility(View.VISIBLE);
							settingPlayFlashImageView(playFlashImgView,
									playFlashBackgroundImgView,
									updateFlashImgView, oneData);
						}
						break;
					case 2: // 2.3
						// 設置PlayFlash View
						FlashView flashView23 = (FlashView) oneData.view[i]
								.findViewById(R.id.play_flash_webview);
						if (null != flashView23) {
							LayoutParams lp = flashView23.getLayoutParams();
							if (isFirstShowFlashView) {
								flashView23.setVisibility(View.GONE);
							} else if (oneData.isFlashPlaying[i]) {
								// flashView.setVisibility(View.GONE);
								boolean isClassDef = false;
								String clssName = "com.besta.hardware.BestaHDMI";
								isClassDef = MyExceptionInfo
										.checkTargetClassExist(context,
												"com.besta.app.realteaching",
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
									Toast.makeText(context,
											R.string.appShutdownStr,
											Toast.LENGTH_SHORT).show();
									((Activity) context).finish();
								}
							}
						}
						// 設置PlayFlash imgView
						ImageView playFlashImgView23 = (ImageView) oneData.view[i]
								.findViewById(R.id.play_flash_view_img);
						ImageView playFlashBackgroundImgView23 = (ImageView) oneData.view[i]
								.findViewById(R.id.play_flash_view_bg_img);
						// 更新Flash imgView
						ImageView updateFlashImgView23 = (ImageView) oneData.view[i]
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
	}

	/**
	 * @describe Refresh download Icon status for example: downloading, update
	 * 
	 * @param context
	 *            : this Activity
	 * @param selectLayoutVer
	 *            : chos 2.3 or 4.0 FlashView widge
	 */
	public void reFreshDownloadIconStatus_2(Context context, int selectLayoutVer) {
		for (int j = 0; null != totalData_now && j < totalData_now.size(); j++) {
			OneSubViewData oneData = totalData_now.get(j);
			for (int i = 0; null != oneData.view && i < oneData.view.length; i++) {
				if (null != oneData.view[i]) {
					switch (selectLayoutVer) {
					case 1: // 4.0
						// 設置PlayFlash View
						FlashView2 flashView = (FlashView2) oneData.view[i]
								.findViewById(R.id.play_flash_webview);
						if (null != flashView) {
							LayoutParams lp = flashView.getLayoutParams();
							if (isFirstShowFlashView) {
								flashView.setVisibility(View.GONE);
							} else if (oneData.isFlashPlaying[i]) {
							}
						}
						// 設置PlayFlash imgView
						ImageView playFlashImgView = (ImageView) oneData.view[i]
								.findViewById(R.id.play_flash_view_img);
						ImageView playFlashBackgroundImgView = (ImageView) oneData.view[i]
								.findViewById(R.id.play_flash_view_bg_img);
						// 更新Flash imgView
						ImageView updateFlashImgView = (ImageView) oneData.view[i]
								.findViewById(R.id.play_flash_view_img_update);

						if (null != playFlashImgView
								&& null != playFlashBackgroundImgView
								&& null != updateFlashImgView) {
							playFlashImgView.setVisibility(View.VISIBLE);
							playFlashBackgroundImgView
									.setVisibility(View.VISIBLE);
							settingPlayFlashImageView(playFlashImgView,
									playFlashBackgroundImgView,
									updateFlashImgView, oneData);
						}
						break;
					case 2: // 2.3
						// 設置PlayFlash View
						FlashView flashView23 = (FlashView) oneData.view[i]
								.findViewById(R.id.play_flash_webview);
						if (null != flashView23) {
							LayoutParams lp = flashView23.getLayoutParams();
							if (isFirstShowFlashView) {
								flashView23.setVisibility(View.GONE);
							} else if (oneData.isFlashPlaying[i]) {

							}
						}
						// 設置PlayFlash imgView
						ImageView playFlashImgView23 = (ImageView) oneData.view[i]
								.findViewById(R.id.play_flash_view_img);
						ImageView playFlashBackgroundImgView23 = (ImageView) oneData.view[i]
								.findViewById(R.id.play_flash_view_bg_img);
						// 更新Flash imgView
						ImageView updateFlashImgView23 = (ImageView) oneData.view[i]
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

		for (int i = 0; i < totalData_now.size(); i++) {
			OneSubViewData m_array = totalData_now.get(i);
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
				// else if (state == 19) {
				// m_array.flashFileStatus = MyOneData.FLASH_FILE_NEED_DOWNLOAD;
				// m_array.isDownloading = false;
				// }
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
				String localFlashPath = MyDataPath.FLASH_DATA_PATH[my_course_id]
						+ m_array.flashFileName + ".bfe";
				if (null != localFlashPath && null != mContext) {
					localFlashPath = GetfileMethod.getfilepath(localFlashPath,
							mContext);
				}
				if (null == localFlashPath) {
					localFlashPath = "/"
							+ MyDataPath.FLASH_DATA_PATH[my_course_id]
							+ m_array.flashFileName + ".bfe";
					File tmpFile = new File(localFlashPath);
					if (!tmpFile.exists()) {
						localFlashPath = "";
					}
				}
				File localFlashFile = new File(localFlashPath);// updateFlashId[i]//packagename
				m_array.localFileLastModifedTime = localFlashFile
						.lastModified();
			}
		}
	}

	private void setLoadingWaitingLayoutShowOrClose(int kind) {
		// kind = 1;
		switch (kind) {
		case 0: // close
			ViewPagerRealTeachingActivity.waiting_layout
					.setVisibility(View.GONE);
			break;
		case 1: // open
			ViewPagerRealTeachingActivity.waiting_layout
					.setBackgroundColor(0xAA000000);
			ViewPagerRealTeachingActivity.waiting_layout
					.setVisibility(View.VISIBLE);
			break;
		}
	}

	private View initializeLoadingData(ViewGroup container, int position) {
		if (0 < viewpagerHandlerList.size()) {
			setLoadingWaitingLayoutShowOrClose(1);
		}
		MyViewPagerAdapterHandlerStruct tmpViewPagerHandler = new MyViewPagerAdapterHandlerStruct(
				mContext);
		tmpViewPagerHandler.handlerCount = viewpagerHandlerList.size() + 1;
		tmpViewPagerHandler.loadingDataPosition = position;
		View view = tmpViewPagerHandler.MainLayoutView;
		viewpagerHandlerList.add(tmpViewPagerHandler);
		container.addView(view);
		Message msg = new Message();
		msg.what = VIEWPAGER_MSG_START_LOADING;
		mHandler.sendMessage(msg);
		Log.i(debugStr, "loading +++! ----> " + viewpagerHandlerList.size());
		return view;
	}

	private View initializeLoadingData_2(ViewGroup container, int position) {
		View view = getView_2(position);
		container.addView(view);
		totalData.clear();
		return view;
	}

	private View getView(int position) {
		String oneData = all_knowledgeID.get(position);
		KnowledgeInfo oneDataInfo = new KnowledgeInfo(oneData);
		View tmpView = initializeData(knowledgePath, oneDataInfo.knowledgeId,
				oneDataInfo.knowledgeName);
		return tmpView;
	}

	private View getView_2(int position) {
		String oneData = all_knowledgeID.get(position);
		KnowledgeInfo oneDataInfo = new KnowledgeInfo(oneData);
		View tmpView = initializeData_2(knowledgePath, oneDataInfo.knowledgeId,
				oneDataInfo.knowledgeName);
		return tmpView;
	}

	public void setHandlerMSG(Message msg) {
		mHandler.sendMessage(msg);
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			int i = viewpagerHandlerList.size();
			Message tmpMsg = new Message();
			switch (msg.what) {
			case VIEWPAGER_MSG_START_LOADING:
				if (0 < i) {
					MyViewPagerAdapterHandlerStruct tmpViewPagerHander = viewpagerHandlerList
							.get(0);
					if (!tmpViewPagerHander.isStarting) {
						tmpViewPagerHander.isStarting = true;
						getView(tmpViewPagerHander.loadingDataPosition);
					}
				} else {
					tmpMsg.what = VIEWPAGER_MSG_COMPELETE;
				}
				this.sendMessage(tmpMsg);
				break;
			case VIEWPAGER_MSG_LOADING_ONE_DATA_SUCCESS:
				if (0 < i) {
					MyViewPagerAdapterHandlerStruct tmpViewPagerHander = viewpagerHandlerList
							.get(0);
					tmpViewPagerHander.isStarting = false;
					tmpViewPagerHander.mLoadingView.dissmisView();
					Log.i(debugStr, "loading success!");
					viewpagerHandlerList.remove(0);
					i--;
					if (3 < viewpagerHandlerList.size()) {
						for (int j = 0; j < viewpagerHandlerList.size() - 2; j++) {
							viewpagerHandlerList.remove(j);
						}
					}
					if (0 < i) {
						tmpMsg.what = VIEWPAGER_MSG_START_LOADING;
					} else {
						tmpMsg.what = VIEWPAGER_MSG_COMPELETE;
					}
				} else {
					tmpMsg.what = VIEWPAGER_MSG_COMPELETE;
				}
				this.sendMessage(tmpMsg);
				break;
			case VIEWPAGER_MSG_COMPELETE:
				save_info_flag = true;
				Log.i(debugStr, "loading compelete!");
				// ViewPagerAdapter.this.notifyDataSetChanged();
				// setLoadingWaitingLayoutShowOrClose(0);
				Message mainActivityMsg = new Message();
				mainActivityMsg.what = ViewPagerRealTeachingActivity.HANDLER_MSG_LOADING_DATA_SUCCESS;
				ViewPagerRealTeachingActivity window = (ViewPagerRealTeachingActivity) ViewPagerRealTeachingActivity.mContext;
				window.mHandler.sendMessage(mainActivityMsg);
				// onResume();
				break;
			case VIEWPAGER_MSG_DOWNLOAD_SUCCESS:
				OneSubViewData oneData = null;
				for (int count = 0; count < totalData_now.size(); count++) {
					oneData = totalData_now.get(count);
					Log.e("oneData.isCallTestActivity", ""
							+ oneData.isCallTestActivity);
					for (int k = 0; k < oneData.view.length; k++) {
						if (null != oneData && oneData.isFlashPlaying[k]) {
							someOneFlashPlaying = true;
							break;
						} else {
							someOneFlashPlaying = false;
						}
					}
					if (null != oneData && !someOneFlashPlaying) {
						oneData.downloadFlashSuccess = 1;
						reFreshDownloadIconStatus_2(mContext,
								selectOneItemLayoutVer);
						// notifyDataSetChanged();
					} else if (null != oneData && someOneFlashPlaying) {
						break;
					}
				}
				break;
			case VIEWPAGER_MSG_WIFI_CONNECT_ERROR:
				break;
			case VIEWPAGER_MSG_CHECK_FLASH_UPDATE:
				checkFlashIsNeedUpdate();
				break;
			case VIEWPAGER_MSG_SHOW_UPDATE_FLASH_BT:
				boolean isPlayFlashNow = false;
				for (int j = 0; j < totalData_now.size(); j++) {
					OneSubViewData m_array = totalData_now.get(j);
					for (int k = 0; k < m_array.isFlashPlaying.length; k++) {
						if (m_array.isFlashPlaying[k]) {
							isPlayFlashNow = true;
							break;
						}
					}
					if (isPlayFlashNow) {
						break;
					}
				}
				if (isPlayFlashNow || isPlayingDiyFlash) {
					break;
				}
				// getFlashDownloadState(true);
				String cancel_flash = msg.getData().getString("cancelone");
				for (int i1 = 0; null != cancel_flash
						&& i1 < totalData_now.size(); i1++) {
					OneSubViewData m_array = totalData_now.get(i1);
					if (m_array.flashFileName.isEmpty()) {
						continue;
					}
					String filename = m_array.flashFileName + ".bfe";
					if (0 <= cancel_flash.indexOf(filename)) {
						// m_array.flashFileStatus =
						// MyOneData.FLASH_FILE_NEED_DOWNLOAD;
						m_array.isDownloading = false;
					}
				}
				reFreshDownloadIconStatus_2(mContext, selectOneItemLayoutVer);
				notifyDataSetChanged();
				break;
			case VIEWPAGER_MSG_DOWNLOADFLASH_CANCELALL:
				for (int i1 = 0; i1 < totalData_now.size(); i1++) {
					OneSubViewData oneData1 = totalData_now.get(i1);
					if (oneData1.isDownloading) {
						oneData1.isDownloading = false;
					}
				}
				reFreshDownloadIconStatus_2(mContext, selectOneItemLayoutVer);
				break;
			}
			super.handleMessage(msg);
		}

	};

	public void compeleteOneHandler() {
		int i = viewpagerHandlerList.size();
		Message msg = new Message();
		if (0 < i) {
			MyViewPagerAdapterHandlerStruct tmpViewPagerHander = viewpagerHandlerList
					.get(0);
			tmpViewPagerHander.isStarting = false;
			msg.what = VIEWPAGER_MSG_START_LOADING;
		} else {
			msg.what = VIEWPAGER_MSG_COMPELETE;
		}
	}

	// Loading DIY flash of progress dialog
	public static String diyDataName = ".dat";
	public int my_course_id = 0;
	boolean isWifiConnect = false;
	int selectOneItemLayoutVer = 0;
	boolean isFirstShowFlashView = true;
	boolean someOneFlashPlaying = false;

	ProgressDialog pgDlg = null;
	LoadingDataThread loadingData = null;

	/**
	 * @describe initialize Main View Datas
	 * 
	 * @param dataPath
	 *            : data absolute path
	 * @param oneKnowledgeID
	 *            : loading one data id
	 */

	public View initializeData(String dataPath, String oneKnowledgeID,
			String oneKnowledgeName) {
		/**
		 * @describe this Activity Handler for excute
		 * 
		 */

		class myHandler extends Handler {
			public MyLoadingView myLoadingView = null;

			public myHandler(MyLoadingView mlv) {
				myLoadingView = mlv;
			}

		}
		// main_relative_layout layout
		MyLoadingView mLoadingView = null;
		mLoadingView = new MyLoadingView(mContext, 0);
		mLoadingView.setOneLoadingView(mContext, 0);
		View Mainlayout = mInflater.inflate(R.layout.real_teaching_main, null);
		if (null != mLoadingView.selectLoadingView) {
			// main_relative_layout layout
			RelativeLayout mainRelativeViewLayout = (RelativeLayout) Mainlayout
					.findViewById(R.id.main_relative_layout);
			mainRelativeViewLayout.addView(mLoadingView.selectLoadingView);
			LayoutParams lp = mLoadingView.selectLoadingView.getLayoutParams();
			lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
			lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
			mLoadingView.setSelectViewParams(lp);
			mLoadingView.setSelectViewBackgroundColor(0xAA000000);
			mLoadingView.showView();
			Handler mOneHandler = new myHandler(mLoadingView) {

				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case ViewPagerRealTeachingActivity.HANDLER_MSG_READ_DATA_ERROR:
						if (null != mContext) {
							Toast.makeText(
									mContext,
									mContext.getString(R.string.not_found_data),
									Toast.LENGTH_SHORT).show();
							((Activity) mContext).finish();
						}
						break;
					case ViewPagerRealTeachingActivity.HANDLER_MSG_LOADING_DATA_SUCCESS:
						// data loading success
						MyViewPagerAdapterHandlerStruct tmpViewPagerHandler = viewpagerHandlerList
								.get(0);
						PrepareLoadData(tmpViewPagerHandler.MainLayoutView);
						OneSubViewData tmpData = totalData.get(0);
						boolean add_flag = true;
						for (int i = 0; i < totalData_now.size(); i++) {
							OneSubViewData tmpNowData = totalData_now.get(i);
							if (tmpNowData.knowledgeID
									.equals(tmpData.knowledgeID)
									&& tmpNowData.knowledgeName
											.equals(tmpData.knowledgeName)) {
								totalData_now.remove(i);
								// add_flag = false;
								break;
							}
						}
						if (add_flag) {
							tmpData.viewPagerPosition = add_position.get(0).position;
							if (2 < totalData_now.size()) {
								totalData_now.remove(0);
							}
							totalData_now.add(tmpData);
							viewpagerHandlerList_copy.add(viewpagerHandlerList
									.get(0));
						}
						add_position.remove(0);
						totalData.clear();
						Message tmpMsg = new Message();
						tmpMsg.what = VIEWPAGER_MSG_LOADING_ONE_DATA_SUCCESS;
						mHandler.sendMessage(tmpMsg);
						break;
					}
					super.handleMessage(msg);
				}

			};

			loadingData = new LoadingDataThread(dataPath, oneKnowledgeID,
					oneKnowledgeName, mOneHandler);
			loadingData.start();
		}

		return Mainlayout;
	}

	/**
	 * @describe initialize Main View Datas
	 * 
	 * @param dataPath
	 *            : data absolute path
	 * @param oneKnowledgeID
	 *            : loading one data id
	 */

	public View initializeData_2(String dataPath, String oneKnowledgeID,
			String oneKnowledgeName) {
		// main_relative_layout layout
		MyLoadingView mLoadingView = null;
		mLoadingView = new MyLoadingView(mContext, 0);
		mLoadingView.setOneLoadingView(mContext, 0);
		View Mainlayout = mInflater.inflate(R.layout.real_teaching_main, null);
		if (null != mLoadingView.selectLoadingView) {
			// main_relative_layout layout
			RelativeLayout mainRelativeViewLayout = (RelativeLayout) Mainlayout
					.findViewById(R.id.main_relative_layout);
			ReadMyData(dataPath, oneKnowledgeID, oneKnowledgeName);
			PrepareLoadData(Mainlayout);
		}

		return Mainlayout;
	}

	/**
	 * @describe Class -> Loading data from offline data
	 */

	public class LoadingDataThread extends Thread {
		String dataPath = "";
		String oneKnowledgeID = "";
		String oneKnowledgeName = "";
		boolean loadDataFlag = false;
		Handler mOneHandler = null;

		/**
		 * @describe LoadingDataThread construct
		 * 
		 * @param dataPath
		 *            : loading data path
		 * @param oneKnowledgeID
		 *            : loading one data of id
		 */

		public LoadingDataThread(String dataPath, String oneKnowledgeID,
				String oneKnowledgeName, Handler mHandler) {
			this.dataPath = dataPath;
			this.oneKnowledgeID = oneKnowledgeID;
			this.oneKnowledgeName = oneKnowledgeName;
			this.mOneHandler = mHandler;
		}

		@Override
		public void run() {
			loadDataFlag = ReadMyData(dataPath, oneKnowledgeID,
					oneKnowledgeName);
			mOneHandler
					.sendEmptyMessage(ViewPagerRealTeachingActivity.HANDLER_MSG_LOADING_DATA_SUCCESS);
			super.run();
		}
	}

	/**
	 * @describe create Main View and set Views Listener
	 * 
	 */

	private void PrepareLoadData(View Mainlayout) {
		createAllView(mContext, Mainlayout);
		initializeRelateButton(Mainlayout);
		// getFlashDownloadState(true);
		// reFreshDownloadIconStatus(mContext, selectOneItemLayoutVer);
		// Message tmpMsg = new Message();
		// tmpMsg.what = HANDLER_MSG_CHECK_FLASH_UPDATE;
		// mHandler.sendMessage(tmpMsg);
	}

	/**
	 * @describe initialize relate button has appear on Main View or not
	 * 
	 */

	public void initializeRelateButton(View Mainlayout) {
		for (int count = 0; count < totalData.size(); count++) {
			OneSubViewData oneData = totalData.get(count);
			if (!oneData.unit_id.equals("")) {
				oneData.buttonFlags.is_has_test = 1;
				break;
			}
		}

		// buttonFlags.is_has_test = 1;
		// buttonFlags.is_has_diy = 1;
		RelativeLayout rl = (RelativeLayout) Mainlayout
				.findViewById(R.id.relate_bt_layout);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		rl.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		OneSubViewData oneData = totalData.get(0);
		switch (oneData.buttonFlags.getLayoutKind()) {
		case 0:
			oneData.relateButton[BT_TEST] = null;
			oneData.relateButton[BT_DIY] = null;
			break;
		case 1:
			oneData.relateButton[BT_TEST] = new Button(mContext);
			if (null != oneData.relateButton[BT_TEST]) {
				RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				rp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
						RelativeLayout.TRUE);
				rp.rightMargin = 10;
				oneData.relateButton[BT_TEST].setLayoutParams(rp);
				rl.addView(oneData.relateButton[BT_TEST]);
				oneData.relateButton[BT_TEST].setText(R.string.pingliang_bt);
				oneData.relateButton[BT_TEST].setTextSize(20);
				oneData.relateButton[BT_TEST].setTextColor(Color.BLACK);
				LayoutParams bt_lp = oneData.relateButton[BT_TEST]
						.getLayoutParams();
				bt_lp.width = LayoutParams.WRAP_CONTENT;
				bt_lp.height = LayoutParams.WRAP_CONTENT;
				oneData.relateButton[BT_TEST].setLayoutParams(bt_lp);
				oneData.relateButton[BT_TEST]
						.setBackgroundResource(R.drawable.btn_fxtj_up);
				// .setImageResource(R.drawable.btn_fxtj_up);
				// relateButton[BT_TEST].setId(0x8f010001);
			}
			oneData.relateButton[BT_DIY] = null;
			break;
		case 2:
			oneData.relateButton[BT_TEST] = new Button(mContext);
			if (null != oneData.relateButton[BT_TEST]) {
				RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				rp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
						RelativeLayout.TRUE);
				rp.rightMargin = 10;
				oneData.relateButton[BT_TEST].setLayoutParams(rp);
				rl.addView(oneData.relateButton[BT_TEST]);
				oneData.relateButton[BT_TEST].setText(R.string.pingliang_bt);
				oneData.relateButton[BT_TEST].setTextSize(20);
				oneData.relateButton[BT_TEST].setTextColor(Color.BLACK);
				LayoutParams bt_lp = oneData.relateButton[BT_TEST]
						.getLayoutParams();
				bt_lp.width = LayoutParams.WRAP_CONTENT;
				bt_lp.height = LayoutParams.WRAP_CONTENT;
				oneData.relateButton[BT_TEST].setLayoutParams(bt_lp);
				oneData.relateButton[BT_TEST]
						.setBackgroundResource(R.drawable.btn_fxtj_up);
				oneData.relateButton[BT_TEST].setId(0x8f010001);
			}

			oneData.relateButton[BT_DIY] = new Button(mContext);
			if (null != oneData.relateButton[BT_DIY]) {
				RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				// rp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
				// RelativeLayout.TRUE);
				rp.addRule(RelativeLayout.LEFT_OF,
						oneData.relateButton[BT_TEST].getId());
				rp.rightMargin = 10;
				oneData.relateButton[BT_DIY].setLayoutParams(rp);
				rl.addView(oneData.relateButton[BT_DIY]);
				oneData.relateButton[BT_DIY].setText(R.string.diy_bt);
				oneData.relateButton[BT_DIY].setTextSize(20);
				oneData.relateButton[BT_DIY].setTextColor(Color.BLACK);
				LayoutParams bt_lp = oneData.relateButton[BT_DIY]
						.getLayoutParams();
				bt_lp.width = LayoutParams.WRAP_CONTENT;
				bt_lp.height = LayoutParams.WRAP_CONTENT;
				oneData.relateButton[BT_DIY].setLayoutParams(bt_lp);
				oneData.relateButton[BT_DIY]
						.setBackgroundResource(R.drawable.btn_starttest_up);
			}
			break;
		case 3:
			oneData.relateButton[BT_DIY] = new Button(mContext);
			if (null != oneData.relateButton[BT_DIY]) {
				RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				// rp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
				// RelativeLayout.TRUE);
				rp.addRule(RelativeLayout.LEFT_OF,
						oneData.relateButton[BT_TEST].getId());
				rp.rightMargin = 10;
				oneData.relateButton[BT_DIY].setLayoutParams(rp);
				rl.addView(oneData.relateButton[BT_DIY]);
				oneData.relateButton[BT_DIY].setText(R.string.diy_bt);
				oneData.relateButton[BT_DIY].setTextSize(20);
				oneData.relateButton[BT_DIY].setTextColor(Color.BLACK);
				LayoutParams bt_lp = oneData.relateButton[BT_DIY]
						.getLayoutParams();
				bt_lp.width = LayoutParams.WRAP_CONTENT;
				bt_lp.height = LayoutParams.WRAP_CONTENT;
				oneData.relateButton[BT_DIY].setLayoutParams(bt_lp);
				oneData.relateButton[BT_DIY]
						.setBackgroundResource(R.drawable.btn_starttest_up);
			}
			oneData.relateButton[BT_TEST] = null;
			break;
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
		OneSubViewData oneData = totalData.get(0);
		if (null != oneData.relateButton[BT_TEST]) {
			oneData.relateButton[BT_TEST]
					.setOnTouchListener(new RelateButtonListner().new RelateButtonOntouchListener(
							mContext) {
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

		if (null != oneData.relateButton[BT_DIY]) {
			oneData.relateButton[BT_DIY]
					.setOnTouchListener(new RelateButtonListner().new RelateButtonOntouchListener(
							mContext) {
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
		OneSubViewData oneData = totalData.get(0);
		if (null != oneData.relateButton[BT_TEST]) {
			oneData.relateButton[BT_TEST]
					.setOnClickListener(new RelateButtonListner().new RelateButtonOnClickListener_viewpager(
							mContext, oneData) {
						@Override
						public void onClick(View v) {
							callTestActivity(oneData);
							super.onClick(v);
						}

					});
		}

		if (null != oneData.relateButton[BT_DIY]) {
			oneData.relateButton[BT_DIY]
					.setOnClickListener(new RelateButtonListner().new RelateButtonOnClickListener_viewpager(
							mContext, oneData) {
						@Override
						public void onClick(View v) {
							if (!isPlayingDiyFlash) {
								OneSubViewData tmpOneData = oneData;
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
								new LoadingDIYThread(mContext,
										tmpOneData.knowledgeName,
										tmpOneData.flashFileName).start();
								super.onClick(v);
							}
						}
					});
		}
	}

	/**
	 * @describe Class for Loading DIY flash
	 */

	private class LoadingDIYThread extends Thread {
		private Context LoadingDIYThreadContext = null;
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

		public LoadingDIYThread(Context context, String listName,
				String DiyFileName) {
			LoadingDIYThreadContext = context;
			this.listName = listName;
			playFileName = DiyFileName;
		}

		public void run() {
			Looper.prepare();
			PlayDIYFlash(LoadingDIYThreadContext, this.listName, playFileName);
			Message msg = new Message();
			msg.what = VIEWPAGER_MSG_LOADING_DIY_DATA_SUCCESS;
			mHandler.sendMessage(msg);
			Looper.loop();
		}

	}

	private boolean isPlayingDiyFlash = false;

	/**
	 * @describe Play DIY flash function
	 * 
	 * @param listName
	 *            : show list string
	 * @param playFlashName
	 *            : diy flash name
	 */

	public void PlayDIYFlash(Context context, String listName,
			String playFlashName) {
		String choseUnzipDir = playFlashName;
		String flashSolutePath = getDIYFlashPath(context, playFlashName);
		pgDlg.dismiss();
		if (flashSolutePath.equals("")) {
			String downLoadOneFile = playFlashName + diyDataName;
			String mStoragepath = MyDataPath.FLASH_DATA_PATH[my_course_id];// "Besta/com.besta.app.yjyd.main";
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
			myStartActivity(context, null, intent, pkg);
		} else {
			try {
				if (ViewPagerRealTeachingActivity.isNotPlayFlashFlag
						|| ViewPagerRealTeachingActivity.isHDMIFlag) {
					if (null != mContext) {
						Toast.makeText(mContext, R.string.s030640,
								Toast.LENGTH_SHORT).show();
					}
					return;
				}
				int playFlashRet = PlayFlash.ShowFlash(context,
						flashSolutePath, 0, 0, false);
				if (PlayFlash.quitErrorPlug == playFlashRet) {
					isPlayingDiyFlash = false;
				} else {
					isPlayingDiyFlash = true;
				}
			} catch (Exception e) {
				isPlayingDiyFlash = false;
				Toast.makeText(context, R.string.NoBestaFlash,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public boolean getPlayingDIYFlashFlag() {
		return isPlayingDiyFlash;
	}

	public void setPlayingDIYFlashFlag(boolean flag) {
		isPlayingDiyFlash = flag;
	}

	/**
	 * @describe Get DIY flash aboslute path
	 * 
	 * @param flashName
	 *            : flash name
	 * @return String : DIY flash absolute path
	 */

	public String getDIYFlashPath(Context context, String flashName) {
		String retStr = MyDataPath.FLASH_DATA_PATH[my_course_id] + flashName
				+ diyDataName;
		retStr = GetfileMethod.getfilepath(retStr, context);
		if (null == retStr) {
			retStr = "/" + MyDataPath.FLASH_DATA_PATH[my_course_id] + flashName
					+ diyDataName;
			File tmpFile = new File(retStr);
			if (!tmpFile.exists()) {
				retStr = "";
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
			// e1.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
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
			String nowFileName = oneDiyDataPath;

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
				// e.printStackTrace();
			}

			try {
				ret = thisFile.getFilePointer();
			} catch (IOException e) {
				// e.printStackTrace();
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
	 * @describe if this knowledge has test to call test activity
	 * 
	 * @param test_id
	 *            : test id for example: SGA-01;SGA-02...
	 */

	public void callTestActivity(OneSubViewData oneData) {
		String key = "quesarray";
		String whiteBoardKey = "whiteBoardKey";
		String isWhiteBoardEnable = "isWhiteBoardEnable";
		String action = "com.besta.app.exerciseengine.ACTION_TEST";
		Intent intent = new Intent(action);
		if (null != intent) {
			intent.putExtra(key, oneData.unit_id);
			intent.putExtra(whiteBoardKey, oneData.knowledgeID);
			if (null != oneData.unit_id && !oneData.unit_id.isEmpty()) {
				intent.putExtra(isWhiteBoardEnable, true);
			}
			intent.putExtra("personalQuestions",
					ViewPagerRealTeachingActivity.personalQuestions);
			intent.putExtra("titlename",
					mContext.getString(R.string.pingliang_bt));
			try {
				// intent.putExtras(bundle);
				mContext.startActivity(intent);
				oneData.isCallTestActivity = true;
			} catch (Exception e) {
				oneData.isCallTestActivity = false;
				if (DebugFlag) {
					// MyErrorPrintfActivity.startMyErrorPrintfActivity(mContext,
					// e);
				} else {
					// e.printStackTrace();
					Toast.makeText(mContext, R.string.appShutdownStr,
							Toast.LENGTH_SHORT).show();
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

		for (int i = 0; i < totalData_now.size(); i++) {
			OneSubViewData mOneData = totalData_now.get(i);
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
				String localFlashPath = GetfileMethod.getfilepath(
						MyDataPath.FLASH_DATA_PATH[my_course_id]
								+ mOneData.flashFileName + flashExtendName,
						mContext);
				if (null == localFlashPath) {
					localFlashPath = "/"
							+ MyDataPath.FLASH_DATA_PATH[my_course_id]
							+ mOneData.flashFileName + flashExtendName;
					File tmpFile = new File(localFlashPath);
					if (!tmpFile.exists()) {
						localFlashPath = "";
					}
				}
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
					if (null != inputJSON) {
						inputJSON.put(jsonObject);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @describe create main View
	 * 
	 * @param context
	 *            : this activity context
	 */
	public void createAllView(Context context, View Mainlayout) {
		ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		// main layout
		LinearLayout mainViewLayout = (LinearLayout) Mainlayout
				.findViewById(R.id.main_layout);
		LayoutInflater inflater = LayoutInflater.from(context);

		// setting title name
		TextView tv_titleBar = (TextView) Mainlayout
				.findViewById(R.id.my_course_name_tv);
		if (null != tv_titleBar) {
			tv_titleBar.setText(MyDataPath.RES_DATA_TITLE_NAME[my_course_id]);
		}

		int view_res_id = R.layout.one_item_view;
		int totalCount = totalData.size();
		int count = 0;
		OneSubViewData oneData = totalData.get(0);
		oneData.mainViewLayout = mainViewLayout;
		oneData.mScrollView = (CustomScrollView) Mainlayout
				.findViewById(R.id.my_main_scroll);
		if (null != oneData.mScrollView) {
			oneData.mScrollView.setBackgroundColor(Color.WHITE);
		}
		oneData.setOneSubViewData(totalCount);
		for (count = 0; count < totalCount; count++) {
			try {
				oneData.view[count] = inflater.inflate(view_res_id, null);
				selectOneItemLayoutVer = 1; // 4.0 FlashView2
			} catch (Exception e) {
				view_res_id = R.layout.one_item_view_23;
				try {
					oneData.view[count] = inflater.inflate(view_res_id, null);
					selectOneItemLayoutVer = 2; // 2.3 FlashView
				} catch (Exception e1) {
					if (DebugFlag) {
						// MyErrorPrintfActivity.startMyErrorPrintfActivity(
						// mContext, e1);
					} else {
						// e1.printStackTrace();
						Toast.makeText(mContext,
								mContext.getString(R.string.not_found_data),
								Toast.LENGTH_SHORT).show();
					}
					((Activity) mContext).finish();
				}
			}
			if (null != oneData.view[count]) {
				// setting 概述title
				TextView summary_title_tv = (TextView) oneData.view[count]
						.findViewById(R.id.summary_title);
				if (null != summary_title_tv) {
					String titleName = DeleteNumberOfTitle(oneData.knowledgeName);
					summary_title_tv.setText(titleName);
					summary_title_tv.setTextColor(0xAA0000FF);
					summary_title_tv.setTextSize(30);
				}
				TextView diy_title_tv = (TextView) oneData.view[count]
						.findViewById(R.id.diy_title_name);
				if (null != diy_title_tv) {
					if (5 < my_course_id && 9 > my_course_id) {
						diy_title_tv.setVisibility(View.VISIBLE);
						String titleName = DeleteNumberOfTitle(oneData.knowledgeName);
						diy_title_tv.setText(titleName);
						diy_title_tv.setTextColor(0xAA0000FF);
						diy_title_tv.setTextSize(30);
					} else {
						diy_title_tv.setVisibility(View.GONE);
					}
				}
				// setting 數據
				oneData.isFlashPlaying[count] = false;
				// MyOneData oneData = totalData.get(count);
				ImageView closeFlashBt = (ImageView) oneData.view[count]
						.findViewById(R.id.close_play_flash_bt);
				if (null != closeFlashBt) {
					setClosePlayFlashButtonListener(context, closeFlashBt,
							oneData);
				}
				settingOneItemLayoutVer(context, oneData.view[count], oneData,
						count, selectOneItemLayoutVer);
				// 設置概述
				MyWebView summaryWebView = (MyWebView) oneData.view[count]
						.findViewById(R.id.summary_webview);
				// summaryWebView.loadData("1234567890", "text/html", "utf-8");
				if (1 == oneData.buttonFlags.is_has_summary) {
					LinearLayout summary_wv_layout = (LinearLayout) oneData.view[count]
							.findViewById(R.id.summary_wv_layout);
					summary_wv_layout.setVisibility(View.VISIBLE);
					// WebSettings webSet = summaryWebView.getSettings();
					// webSet.setJavaScriptEnabled(true);
					// summaryWebView.getSettings().setSupportZoom(true);
					// WebViewConfig.useSelectionMenu(summaryWebView, false);
					// summaryWebView.addJavascriptInterface(
					// new JavaScriptInterface(summaryWebView), "html");

					StringBuffer contentBuf = new StringBuffer(
							"<?xml version=\"1.0\" encoding=\"utf-16le\"?>");
					oneData.summaryContent = voiceOrderBuffer_Head(oneData.summaryContent);
					contentBuf.append(oneData.summaryContent);
					summaryWebView.loadDataWithBaseURL("file://",
							contentBuf.toString(), "text/html", "utf-8", null);
					// summaryWebView.loadUrl("file://mnt/sdcard/1.html");
					// summaryWebView.requestFocus(); //
					// 如果設置此項會造成CustomScrollView的滾動
					// SelectionListener selectionListener = new
					// DictViewSelectionListener(
					// summaryWebView);
					// summaryWebView.setSelectionListener(selectionListener);
					summaryWebView.setWebViewClient(new WebViewClient() {
						@Override
						public boolean shouldOverrideUrlLoading(WebView view,
								String url) {
							// TODO Auto-generated method stub
							return super.shouldOverrideUrlLoading(view, url);
						}
					});
					summaryWebView
							.setOnLongClickListener(new OnLongClickListener() {

								@Override
								public boolean onLongClick(View arg0) {
									// TODO Auto-generated method stub
									return true;
								}
							});
				} else {
					LinearLayout summary_wv_layout = (LinearLayout) oneData.view[count]
							.findViewById(R.id.summary_wv_layout);
					summary_wv_layout.setVisibility(View.GONE);
				}

				mainViewLayout.addView(oneData.view[count]);
			}
		}
	}

	public static String DeleteNumberOfTitle(String buffer) {
		String ret = "";
		char[] pBuffer = buffer.toCharArray();
		int start_flag = 0;

		for (int i = 0; i < pBuffer.length; i++) {
			if (('單' == pBuffer[i] || '单' == pBuffer[i])
					&& ('元' == pBuffer[i + 1] || '元' == pBuffer[i + 1])) {
				if (' ' == pBuffer[i + 2] || ' ' == pBuffer[i + 2]) {
					start_flag = 1;
					i += 2;
				}
			} else if (1 == start_flag) {
				if ('0' <= pBuffer[i] && '9' >= pBuffer[i]) {
					continue;
				} else {
					start_flag = 0;
				}
			}
			if (0 == start_flag) {
				ret += pBuffer[i];
			}

		}

		return ret;
	}

	/**
	 * @describe this function is using by Select Dict View
	 */

	String searchBuf = null;
	RetrievalPopUpWindow popUpWindow = null;

	private class DictViewSelectionListener implements SelectionListener {
		MyWebView mWebView = null;

		/**
		 * @describe this is Dict Select Listener construct
		 * 
		 * @param MyWebView
		 *            mWebView : MyWebView(override WebView)
		 */

		public DictViewSelectionListener(MyWebView mWebView) {
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
		private MyWebView mWebView = null;

		public JavaScriptInterface(MyWebView mWebView) {
			this.mWebView = mWebView;
		}

		public void showHtml(String html) {
			Log.i("HTML", html);
			// mWebView.clearFocus();
			CrossSearchInfo crossSearchInfo = new CrossSearchInfo();
			crossSearchInfo.setContext(mContext);// activity��context
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
			final OneSubViewData oneData, final int count, int selectLayoutVer) {
		ImageView closePlayingFlash = (ImageView) v
				.findViewById(R.id.close_play_flash_bt);
		if (null != closePlayingFlash) {
			if (oneData.isFlashPlaying[count]) {
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
				flashView.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						// TODO Auto-generated method stub
						switch (arg1.getAction()) {
						case MotionEvent.ACTION_DOWN:
							if (null != oneData.mScrollView) {
								oneData.mScrollView.setTouchAble(false);
							}
							if (null != viewpager) {
								viewpager.setTouchAble(false);
							}
							break;
						case MotionEvent.ACTION_UP:
							if (null != oneData.mScrollView) {
								oneData.mScrollView.setTouchAble(true);
							}
							if (null != viewpager) {
								viewpager.setTouchAble(true);
							}
							break;
						}
						return false;
					}
				});
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

				ViewPagerMyPlayFlashImgViewOnTouchListener playFlashOnTouchListener = new ViewPagerMyPlayFlashImgViewOnTouchListener(
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
								((ImageView) v).setImageResource(imgViewResId);
							} else if (MyOneData.FLASH_FILE_DOWNLOADING == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_downloading_dn;
								((ImageView) v).setImageResource(imgViewResId);
								setDownloadingAnimation(v);
							} else if (MyOneData.FLASH_FILE_EXIST == mOneData.flashFileStatus
									|| MyOneData.FLASH_FILE_NEED_UPDATE == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_play_dn;
								((ImageView) v).setImageResource(imgViewResId);
							}
							break;
						case MotionEvent.ACTION_UP:
						case MotionEvent.ACTION_CANCEL:
							if (MyOneData.FLASH_FILE_NEED_DOWNLOAD == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_download_up;
								((ImageView) v).setImageResource(imgViewResId);
							} else if (MyOneData.FLASH_FILE_DOWNLOADING == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_downloading_up;
								((ImageView) v).setImageResource(imgViewResId);
								setDownloadingAnimation(v);
							} else if (MyOneData.FLASH_FILE_EXIST == mOneData.flashFileStatus
									|| MyOneData.FLASH_FILE_NEED_UPDATE == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_play_up;
								((ImageView) v).setImageResource(imgViewResId);
							}
							break;
						}
						return false;
						// return super.onTouch(v, event);
					}

				};

				ViewPagerMyPlayFlashImgViewOnClickListener playFlashOnClickListener = new ViewPagerMyPlayFlashImgViewOnClickListener(
						context, playFlashImgView, closePlayingFlash, oneData,
						count, flashView, null, flashPath) {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// onStopOneFlashPlaying();
						isPlayingPosition = oneData.viewPagerPosition;
						if (DebugFlag) {
							Log.i(debugStr, mOneData.flashFileName
									+ flashExtendName);
						}
						if (MyOneData.FLASH_FILE_DOWNLOADING == mOneData.flashFileStatus) {
							downloadFlashFile(context, mOneData);
							return;
						}
						// String realPath = getRealFlashFilePath(mOneData, 0);
						String realPath = GetfileMethod.getfilepath(
								MyDataPath.FLASH_DATA_PATH[my_course_id]
										+ mOneData.flashFileName
										+ flashExtendName, context);
						if (null == realPath) {
							realPath = "/"
									+ MyDataPath.FLASH_DATA_PATH[my_course_id]
									+ mOneData.flashFileName + flashExtendName;
							File tmpFile = new File(realPath);
							if (!tmpFile.exists()) {
								realPath = "";
							}
						}
						// realPath =
						// "/mnt/sdcard/besta/video_teaching/jur_math/m101010.bfe";
						onConfigChangeData = oneData;
						if (null != realPath && !realPath.isEmpty()) {
							if (ViewPagerRealTeachingActivity.isNotPlayFlashFlag
									|| ViewPagerRealTeachingActivity.isHDMIFlag) {
								if (null != mContext) {
									Toast.makeText(mContext, R.string.s030640,
											Toast.LENGTH_SHORT).show();
								}
								return;
							}
							this.playFlashImgView.setVisibility(View.GONE);
							if (null != flashView) {
								LayoutParams lp = flashView.getLayoutParams();
								lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = 430;
								flashView.setLayoutParams(lp);
								flashView.setVisibility(View.VISIBLE);
								flashView.ShowFlash(realPath, 0, 0, false);
								isUsingViewFlag = true;
								oneData.isFlashPlaying[count] = true;
							} else if (null != flashView23) {
								LayoutParams lp = flashView23.getLayoutParams();
								lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = 430;
								flashView23.setLayoutParams(lp);
								flashView23.setVisibility(View.VISIBLE);
								flashView23.ShowFlash(realPath, 0, 0, false);
								oneData.isFlashPlaying[count] = true;
								isUsingViewFlag = true;
							}
							if (null != this.closePlayFlashBt) {
								if (oneData.isFlashPlaying[count]) {
									closePlayFlashBt
											.setVisibility(View.VISIBLE);
								} else {
									closePlayFlashBt.setVisibility(View.GONE);
								}
							}
							oneData.playingCount = this.nowFlashCount;
							this.isDisplayImgView = false;
						} else {
							// downloading... set imgview
							downloadFlashFile(context, mOneData);
							isWifiConnect = MyWifiInfo
									.checkWifiConnect(mContext);
							if (isWifiConnect) {
								mOneData.flashFileStatus = MyOneData.FLASH_FILE_DOWNLOADING;
								((ImageView) v)
										.setImageResource(R.drawable.flashstatus_downloading_up);
								setDownloadingAnimation(v);
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
				flashView23.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						// TODO Auto-generated method stub
						switch (arg1.getAction()) {
						case MotionEvent.ACTION_DOWN:
							if (null != oneData.mScrollView) {
								oneData.mScrollView.setTouchAble(false);
							}
							if (null != viewpager) {
								viewpager.setTouchAble(false);
							}
							break;
						case MotionEvent.ACTION_UP:
							if (null != oneData.mScrollView) {
								oneData.mScrollView.setTouchAble(true);
							}
							if (null != viewpager) {
								viewpager.setTouchAble(true);
							}
							break;
						}
						return false;
					}
				});
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

				ViewPagerMyPlayFlashImgViewOnTouchListener playFlashOnTouchListener = new ViewPagerMyPlayFlashImgViewOnTouchListener(
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
								((ImageView) v).setImageResource(imgViewResId);
							} else if (MyOneData.FLASH_FILE_DOWNLOADING == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_downloading_dn;
								((ImageView) v).setImageResource(imgViewResId);
								setDownloadingAnimation(v);
							} else if (MyOneData.FLASH_FILE_EXIST == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_play_dn;
								((ImageView) v).setImageResource(imgViewResId);
							}
							break;
						case MotionEvent.ACTION_UP:
						case MotionEvent.ACTION_CANCEL:
							if (MyOneData.FLASH_FILE_NEED_DOWNLOAD == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_download_up;
								((ImageView) v).setImageResource(imgViewResId);
							} else if (MyOneData.FLASH_FILE_DOWNLOADING == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_downloading_up;
								((ImageView) v).setImageResource(imgViewResId);
								setDownloadingAnimation(v);
							} else if (MyOneData.FLASH_FILE_EXIST == mOneData.flashFileStatus) {
								imgViewResId = R.drawable.flashstatus_play_up;
								((ImageView) v).setImageResource(imgViewResId);
							}
							break;
						}
						return false;
						// return super.onTouch(v, event);
					}

				};

				ViewPagerMyPlayFlashImgViewOnClickListener playFlashOnClickListener = new ViewPagerMyPlayFlashImgViewOnClickListener(
						context, playFlashImgView23, closePlayingFlash,
						oneData, count, null, flashView23, flashPath) {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// onStopOneFlashPlaying();
						isPlayingPosition = oneData.viewPagerPosition;
						if (DebugFlag) {
							Log.i(debugStr, mOneData.flashFileName
									+ flashExtendName);
						}
						if (MyOneData.FLASH_FILE_DOWNLOADING == mOneData.flashFileStatus) {
							downloadFlashFile(context, mOneData);
							return;
						}
						// String realPath = getRealFlashFilePath(mOneData, 0);
						String realPath = GetfileMethod.getfilepath(
								MyDataPath.FLASH_DATA_PATH[my_course_id]
										+ mOneData.flashFileName
										+ flashExtendName, context);
						if (null == realPath) {
							realPath = "/"
									+ MyDataPath.FLASH_DATA_PATH[my_course_id]
									+ mOneData.flashFileName + flashExtendName;
							File tmpFile = new File(realPath);
							if (!tmpFile.exists()) {
								realPath = "";
							}
						}
						onConfigChangeData = oneData;
						if (!realPath.isEmpty()) {
							if (ViewPagerRealTeachingActivity.isNotPlayFlashFlag
									|| ViewPagerRealTeachingActivity.isHDMIFlag) {
								if (null != mContext) {
									Toast.makeText(mContext, R.string.s030640,
											Toast.LENGTH_SHORT).show();
								}
								return;
							}
							this.playFlashImgView.setVisibility(View.GONE);
							if (null != flashView) {
								LayoutParams lp = flashView.getLayoutParams();
								lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = 430;
								flashView.setLayoutParams(lp);
								flashView.setVisibility(View.VISIBLE);
								flashView.ShowFlash(realPath, 0, 0, false);
								oneData.isFlashPlaying[count] = true;
								isUsingViewFlag = true;
							} else if (null != flashView23) {
								LayoutParams lp = flashView23.getLayoutParams();
								lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
								lp.height = 430;
								flashView23.setLayoutParams(lp);
								flashView23.setVisibility(View.VISIBLE);
								flashView23.ShowFlash(realPath, 0, 0, false);
								oneData.isFlashPlaying[count] = true;
								isUsingViewFlag = true;
							}
							if (null != this.closePlayFlashBt) {
								if (oneData.isFlashPlaying[count]) {
									closePlayFlashBt
											.setVisibility(View.VISIBLE);
								} else {
									closePlayFlashBt.setVisibility(View.GONE);
								}
							}
							oneData.playingCount = this.nowFlashCount;
							this.isDisplayImgView = false;
							// mOneData.flashFileStatus =
							// MyOneData.FLASH_FILE_EXIST;
						} else {
							// downloading... set imgview
							downloadFlashFile(context, mOneData);
							isWifiConnect = MyWifiInfo
									.checkWifiConnect(mContext);
							if (isWifiConnect) {
								mOneData.flashFileStatus = MyOneData.FLASH_FILE_DOWNLOADING;
								((ImageView) v)
										.setImageResource(R.drawable.flashstatus_downloading_up);
								setDownloadingAnimation(v);
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

	public void setDownloadingAnimation(View v) {
		((ImageView) v).setImageResource(R.anim.animation_list);
		AnimationDrawable draw = (AnimationDrawable) ((ImageView) v)
				.getDrawable();
		draw.start();
	}

	public void setClosePlayFlashButtonListener(final Context context,
			ImageView view, OneSubViewData mOneData) {
		view.setOnTouchListener(new OnTouchListener() {

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

		view.setOnClickListener(new CloseFlashImgViewOnClickListener(context,
				view, mOneData) {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onCloseOldPlayingFlashView(context, v, mOneData);
				addNewPlayFlashView(context, v, mOneData);
			}
		});
	}

	public void onCloseOldPlayingFlashView(Context context, View v,
			OneSubViewData oneData) {
		isPlayingPosition = -1;
		isUsingViewFlag = false;
		if (null != oneData.mainViewLayout) {
			for (int count = 0; null != oneData.view
					&& count < oneData.view.length; count++) {
				if (null != oneData.view[count]) {
					oneData.isFlashPlaying[count] = false;
					switch (selectOneItemLayoutVer) {
					case 1: // 4.0
						FlashView2 flashView = (FlashView2) oneData.view[count]
								.findViewById(R.id.play_flash_webview);
						if (null != flashView) {
							// flashView.doPause();
							// flashView.doDestroy();
							flashView.onPause();
							flashView.destroy();
							flashView = null;
						}
						break;
					case 2:
						FlashView flashView23 = (FlashView) oneData.view[count]
								.findViewById(R.id.play_flash_webview);
						if (null != flashView23) {
							flashView23.doPause();
							flashView23.doDestroy();
							flashView23 = null;
						}
						break;
					}
					oneData.mainViewLayout.removeView(oneData.view[count]);
					oneData.view[count] = null;
					oneData.isFlashPlaying[count] = false;
				}
			}
		}
	}

	OneSubViewData onConfigChangeData = null;

	public void onCloseOldPlayingFlashView_2(Context context,
			OneSubViewData oneData) {
		isPlayingPosition = -1;
		isUsingViewFlag = false;
		if (null != oneData.mainViewLayout) {
			for (int count = 0; null != oneData.view
					&& count < oneData.view.length; count++) {
				if (null != oneData.view[count]) {
					switch (selectOneItemLayoutVer) {
					case 1: // 4.0
						FlashView2 flashView = (FlashView2) oneData.view[count]
								.findViewById(R.id.play_flash_webview);
						if (null != flashView) {
							// flashView.doPause();
							// flashView.doDestroy();
							flashView.onPause();
							flashView.destroy();
							flashView = null;
						}
						break;
					case 2:
						FlashView flashView23 = (FlashView) oneData.view[count]
								.findViewById(R.id.play_flash_webview);
						if (null != flashView23) {
							flashView23.doPause();
							flashView23.doDestroy();
							flashView23 = null;
						}
						break;
					}
					oneData.mainViewLayout.removeView(oneData.view[count]);
					oneData.view[count] = null;
					oneData.isFlashPlaying[count] = false;
				}
			}
		}
	}

	public void addNewPlayFlashView(Context context, View v,
			OneSubViewData oneData) {
		// main layout
		// LinearLayout mainViewLayout = (LinearLayout) ((Activity) context)
		// .findViewById(R.id.main_layout);
		if (null != oneData.mainViewLayout) {
			int childViewCount = oneData.mainViewLayout.getChildCount();
			childViewCount = 0;
			ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			LayoutInflater inflater = LayoutInflater.from(context);
			int view_res_id = R.layout.one_item_view;
			int count = 0;
			for (count = 0; count < oneData.view.length; count++) {
				try {
					oneData.view[count] = inflater.inflate(view_res_id, null);
					selectOneItemLayoutVer = 1; // 4.0 FlashView2
				} catch (Exception e) {
					view_res_id = R.layout.one_item_view_23;
					try {
						oneData.view[count] = inflater.inflate(view_res_id,
								null);
						selectOneItemLayoutVer = 2; // 2.3 FlashView
					} catch (Exception e1) {
						if (DebugFlag) {
							// MyErrorPrintfActivity.startMyErrorPrintfActivity(
							// context, e1);
						} else {
							// e1.printStackTrace();
							Toast.makeText(context,
									context.getString(R.string.not_found_data),
									Toast.LENGTH_SHORT).show();
						}
						((Activity) context).finish();
					}
				}
				if (null != oneData.view[count]) {
					// setting 概述title
					TextView summary_title_tv = (TextView) oneData.view[count]
							.findViewById(R.id.summary_title);
					if (null != summary_title_tv) {
						String titleName = DeleteNumberOfTitle(oneData.knowledgeName);
						summary_title_tv.setText(titleName);
						summary_title_tv.setTextColor(0xAA0000FF);
						summary_title_tv.setTextSize(30);
					}
					TextView diy_title_tv = (TextView) oneData.view[count]
							.findViewById(R.id.diy_title_name);
					if (null != diy_title_tv) {
						if (5 < my_course_id && 9 > my_course_id) {
							diy_title_tv.setVisibility(View.VISIBLE);
							String titleName = DeleteNumberOfTitle(oneData.knowledgeName);
							diy_title_tv.setText(titleName);
							diy_title_tv.setTextColor(0xAA0000FF);
							diy_title_tv.setTextSize(30);
						} else {
							diy_title_tv.setVisibility(View.GONE);
						}
					}
					// setting 數據
					ImageView closeFlashBt = (ImageView) oneData.view[count]
							.findViewById(R.id.close_play_flash_bt);
					if (null != closeFlashBt) {
						setClosePlayFlashButtonListener(context, closeFlashBt,
								oneData);
					}
					settingOneItemLayoutVer(context, oneData.view[count],
							oneData, count, selectOneItemLayoutVer);
					// 設置概述
					MyWebView summaryWebView = (MyWebView) oneData.view[count]
							.findViewById(R.id.summary_webview);
					if (1 == oneData.buttonFlags.is_has_summary) {
						LinearLayout summary_wv_layout = (LinearLayout) oneData.view[count]
								.findViewById(R.id.summary_wv_layout);
						summary_wv_layout.setVisibility(View.VISIBLE);
						WebSettings webSet = summaryWebView.getSettings();
						webSet.setJavaScriptEnabled(true);
						summaryWebView.getSettings().setSupportZoom(true);
						// WebViewConfig.useSelectionMenu(summaryWebView,
						// false);
						// summaryWebView
						// .addJavascriptInterface(
						// new JavaScriptInterface(summaryWebView),
						// "html");

						StringBuffer contentBuf = new StringBuffer(
								"<?xml version=\"1.0\" encoding=\"utf-16le\"?>");
						contentBuf.append(oneData.summaryContent);
						summaryWebView.loadDataWithBaseURL("file://",
								contentBuf.toString(), "text/html", "utf-8",
								null);
						// summaryWebView.loadUrl("file://mnt/sdcard/1.html");
						// summaryWebView.requestFocus(); //
						// 如果設置此項會造成CustomScrollView的滾動
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
						LinearLayout summary_wv_layout = (LinearLayout) oneData.view[count]
								.findViewById(R.id.summary_wv_layout);
						summary_wv_layout.setVisibility(View.GONE);
					}

					oneData.mainViewLayout.addView(oneData.view[count]);
				}
			}
		}
	}

	public void addNewPlayFlashView_2(Context context, OneSubViewData oneData) {
		// main layout
		// LinearLayout mainViewLayout = (LinearLayout) ((Activity) context)
		// .findViewById(R.id.main_layout);
		if (null != oneData.mainViewLayout) {
			int childViewCount = oneData.mainViewLayout.getChildCount();
			childViewCount = 0;
			ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			LayoutInflater inflater = LayoutInflater.from(context);
			int view_res_id = R.layout.one_item_view;
			int count = 0;
			for (count = 0; count < oneData.view.length; count++) {
				try {
					oneData.view[count] = inflater.inflate(view_res_id, null);
					selectOneItemLayoutVer = 1; // 4.0 FlashView2
				} catch (Exception e) {
					view_res_id = R.layout.one_item_view_23;
					try {
						oneData.view[count] = inflater.inflate(view_res_id,
								null);
						selectOneItemLayoutVer = 2; // 2.3 FlashView
					} catch (Exception e1) {
						if (DebugFlag) {
							// MyErrorPrintfActivity.startMyErrorPrintfActivity(
							// context, e1);
						} else {
							// e1.printStackTrace();
							Toast.makeText(context,
									context.getString(R.string.not_found_data),
									Toast.LENGTH_SHORT).show();
						}
						((Activity) context).finish();
					}
				}
				if (null != oneData.view[count]) {
					// setting 概述title
					TextView summary_title_tv = (TextView) oneData.view[count]
							.findViewById(R.id.summary_title);
					if (null != summary_title_tv) {
						String titleName = DeleteNumberOfTitle(oneData.knowledgeName);
						summary_title_tv.setText(titleName);
						summary_title_tv.setTextColor(0xAA0000FF);
						summary_title_tv.setTextSize(30);
					}
					TextView diy_title_tv = (TextView) oneData.view[count]
							.findViewById(R.id.diy_title_name);
					if (null != diy_title_tv) {
						if (5 < my_course_id && 9 > my_course_id) {
							diy_title_tv.setVisibility(View.VISIBLE);
							String titleName = DeleteNumberOfTitle(oneData.knowledgeName);
							diy_title_tv.setText(titleName);
							diy_title_tv.setTextColor(0xAA0000FF);
							diy_title_tv.setTextSize(30);
						} else {
							diy_title_tv.setVisibility(View.GONE);
						}
					}
					// setting 數據
					ImageView closeFlashBt = (ImageView) oneData.view[count]
							.findViewById(R.id.close_play_flash_bt);
					if (null != closeFlashBt) {
						setClosePlayFlashButtonListener(context, closeFlashBt,
								oneData);
					}
					settingOneItemLayoutVer(context, oneData.view[count],
							oneData, count, selectOneItemLayoutVer);
					// 設置概述
					MyWebView summaryWebView = (MyWebView) oneData.view[count]
							.findViewById(R.id.summary_webview);
					if (1 == oneData.buttonFlags.is_has_summary) {
						LinearLayout summary_wv_layout = (LinearLayout) oneData.view[count]
								.findViewById(R.id.summary_wv_layout);
						summary_wv_layout.setVisibility(View.VISIBLE);
						// WebSettings webSet = summaryWebView.getSettings();
						// webSet.setJavaScriptEnabled(true);
						// summaryWebView.getSettings().setSupportZoom(true);
						// WebViewConfig.useSelectionMenu(summaryWebView,
						// false);
						// summaryWebView
						// .addJavascriptInterface(
						// new JavaScriptInterface(summaryWebView),
						// "html");

						StringBuffer contentBuf = new StringBuffer(
								"<?xml version=\"1.0\" encoding=\"utf-16le\"?>");
						contentBuf.append(oneData.summaryContent);
						summaryWebView.loadDataWithBaseURL("file://",
								contentBuf.toString(), "text/html", "utf-8",
								null);
						// summaryWebView.loadUrl("file://mnt/sdcard/1.html");
						// summaryWebView.requestFocus(); //
						// 如果設置此項會造成CustomScrollView的滾動
						// SelectionListener selectionListener = new
						// DictViewSelectionListener(
						// summaryWebView);
						// summaryWebView.setSelectionListener(selectionListener);
						summaryWebView.setWebViewClient(new WebViewClient() {
							@Override
							public boolean shouldOverrideUrlLoading(
									WebView view, String url) {
								// TODO Auto-generated method stub
								return super
										.shouldOverrideUrlLoading(view, url);
							}
						});
						summaryWebView
								.setOnLongClickListener(new OnLongClickListener() {

									@Override
									public boolean onLongClick(View arg0) {
										// TODO Auto-generated method stub
										return true;
									}
								});
					} else {
						LinearLayout summary_wv_layout = (LinearLayout) oneData.view[count]
								.findViewById(R.id.summary_wv_layout);
						summary_wv_layout.setVisibility(View.GONE);
					}

					oneData.mainViewLayout.addView(oneData.view[count]);
				}
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
	public void downloadFlashFile(Context context, OneSubViewData mOneData) {
		// 下載flash
		// download flash
		String downLoadOneFile = mOneData.flashFileName + flashExtendName;
		boolean isDownloading = false;
		isDownloading = getFlashDownloadState(downLoadOneFile);
		if (isDownloading) {
			Toast.makeText(context, R.string.downloading, Toast.LENGTH_SHORT)
					.show();
			return;
		}
		String mStoragepath = MyDataPath.FLASH_DATA_PATH[my_course_id];// "Besta/com.besta.app.yjyd.main";
		String pkg_name = context.getPackageName();
		String mPackagename = MyDataPath.PKG_NAME[my_course_id];
		mPackagename = total_pkg_name;
		Intent intent = new Intent();
		intent.putExtra("packagename", mPackagename);
		intent.putExtra("storagepath", mStoragepath);
		intent.putExtra("downloadonefile", downLoadOneFile);
		String listName = getFilterDownloadTitleName(mOneData.knowledgeName);
		if (null == listName || listName.equals("")) {
			listName = mOneData.knowledgeName;
		}
		listName = MyDataPath.RES_DATA_TITLE_NAME[my_course_id] + "："
				+ listName;
		intent.putExtra("titlename", listName);
		intent.setComponent(new ComponentName(
				"com.besta.util.contentsdownload",
				"com.besta.util.contentsdownload.ContentsDownload"));
		String pkg = "com.besta.util.contentsdownload";
		myStartActivity(context, mOneData, intent, pkg);
		// Toast.makeText(mContext, "downloading...",
		// Toast.LENGTH_SHORT).show();
	}

	private String getFilterDownloadTitleName(String buffer) {
		String ret = "";
		char[] bufferChar = buffer.toCharArray();
		int count = 0;
		int totalCount = buffer.length();
		int start_flag = 0;
		int mark_count = 0;
		if (5 < my_course_id && 9 > my_course_id) {
			for (count = 0; count < totalCount; count++) {
				if (0 == count && 0 == start_flag && '單' == bufferChar[count]
						&& '元' == bufferChar[count + 1]) {
					start_flag = 1;
				} else if (1 == start_flag) {
					if (0 == mark_count && '0' <= bufferChar[count]
							&& '9' >= bufferChar[count]) {
						mark_count = 1;
					} else if (1 == mark_count
							&& (('0' <= bufferChar[count] && '9' >= bufferChar[count]) || '-' == bufferChar[count])) {
						continue;
					} else if (1 == mark_count && ' ' == bufferChar[count]) {
						start_flag = 2;
					}
				} else if (2 == start_flag) {
					ret += bufferChar[count];
				}
			}
		} else {
			ret = buffer;
		}
		return ret;
	}

	// downlaod ver :
	final static int ContentDownload_Ver_2_3 = 179;
	final static int ContentDownload_Ver_4_0 = 189;
	// call bestamarket download new content download app
	public static final String BESTA_MARKET_DOWNLOAD = "bestamarket://downloads?";
	public static final String BESTA_MARKET_DOWNLOAD_LIST_KEY = "pkg_list=";

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

	public boolean myStartActivity(Context context, OneSubViewData data,
			Intent intent, String pkg) {
		boolean ret = false;
		if (null != pkg) {
			int sdk_ver = ((ViewPagerRealTeachingActivity) context)
					.getAndroidSDKVersion();
			int targetVersion = 0;
			// content download
			if (sdk_ver >= 15) { // 4.0
				targetVersion = ContentDownload_Ver_4_0;
			} else if (sdk_ver <= 10) { // 2.3
				targetVersion = 0;
			}

			if (((ViewPagerRealTeachingActivity) context).getLinkApkInfo(pkg,
					targetVersion)) {
				Intent intent1 = null;
				if (null != data) {
					data.flashFileStatus = MyOneData.FLASH_FILE_NEED_DOWNLOAD;
				}
				try {
					intent1 = new Intent(Intent.ACTION_VIEW);
					intent1.setData(Uri.parse(BESTA_MARKET_DOWNLOAD
							+ BESTA_MARKET_DOWNLOAD_LIST_KEY + pkg));
					((ViewPagerRealTeachingActivity) context)
							.startActivity(intent1);
				} catch (Exception e) {
					CheckUpdateVer(context, pkg);
					Toast.makeText(context, R.string.contentDownloadStr,
							Toast.LENGTH_SHORT).show();

				}
			} else {

				if (((ViewPagerRealTeachingActivity) context)
						.getPackageManager().queryIntentActivities(intent, 0)
						.size() > 0) {
					if (null != data) {
						data.flashFileStatus = MyOneData.FLASH_FILE_DOWNLOADING;
					}
					ret = true;
					((ViewPagerRealTeachingActivity) context)
							.startActivity(intent);

				} else {
					if (null != data) {
						data.flashFileStatus = MyOneData.FLASH_FILE_NEED_DOWNLOAD;
					}
				}
			}
		} else {
			if (((ViewPagerRealTeachingActivity) context).getPackageManager()
					.queryIntentActivities(intent, 0).size() > 0) {
				if (null != data) {
					data.flashFileStatus = MyOneData.FLASH_FILE_DOWNLOADING;
				}
				ret = true;
				try {
					((ViewPagerRealTeachingActivity) context)
							.startActivity(intent);
				} catch (Exception e) {
					// e.printStackTrace();
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
	 * @describe check besta market need to update or not
	 * 
	 * @param pkg_name
	 *            : check app's packge name
	 */

	public void CheckUpdateVer(Context context, String pkg_name) {
		Intent intent = new Intent();
		intent.setAction("com.besta.app.bestamarket.CHECK_FOR_UPDATE");
		if (null != pkg_name) {
			intent.putExtra("package", pkg_name);
		} else {
			intent.putExtra("package", context.getPackageName());// your
			// package
			// name
			// e.g.
			// "com.besta.app.KPS"
		}
		intent.putExtra("versionCode", 1);// your package version code, e.g. 1
		context.sendBroadcast(intent);

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
			OneSubViewData mOneData) {
		if (null == playFlashImgView || null == playFlashBackgroundImgView
				|| null == updateFlashImgView) {
			Toast.makeText(mContext, "playFlashImgView == null",
					Toast.LENGTH_SHORT).show();
		} else {
			// setting image view
			// first get flash exist?
			// String playFlashFilePath = getRealFlashFilePath(mOneData, 1);
			String playFlashFilePath = GetfileMethod.getfilepath(
					MyDataPath.FLASH_DATA_PATH[my_course_id]
							+ mOneData.flashFileName + flashExtendName,
					mContext);
			if (null == playFlashFilePath) {
				playFlashFilePath = "/"
						+ MyDataPath.FLASH_DATA_PATH[my_course_id]
						+ mOneData.flashFileName + flashExtendName;
				File tmpFile = new File(playFlashFilePath);
				if (!tmpFile.exists()) {
					playFlashFilePath = "";
				}
			}
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
					if (0 == mOneData.downloadFlashSuccess) {
						flashFileStatus = 0;
					} else if (1 == mOneData.downloadFlashSuccess) {
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
					imgViewResId = R.drawable.flashstatus_downloading_up;
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
				if (R.drawable.flashstatus_downloading_up == imgViewResId) {
					setDownloadingAnimation(playFlashImgView);
				} else {
					playFlashImgView.setImageResource(imgViewResId);
				}
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
								mContext) {
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
						.setOnClickListener(new RelateButtonListner().new RelateButtonOnClickListener1(
								mContext, mOneData) {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								downloadFlashFile(mContext, oneData);
								super.onClick(v);
							}

						});
			}
		}
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

	private boolean ReadMyData(String dataPath, String knowledgeID,
			String knowledgeName) {
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
			nowLoadingData.knowledgeID = knowledgeID;
			nowLoadingData.knowledgeName = knowledgeName;

			// Debug
			// index_seek_len = myData_Len = 0;

			// read my data flags
			rf.seek(index_seek_len + 4);
			rf.read(oneCharByte);
			index_len = Str4Bytes2Int(oneCharByte);
			nowLoadingData.buttonFlags.is_has_diy = index_len;
			rf.read(oneCharByte);
			index_len = Str4Bytes2Int(oneCharByte);
			nowLoadingData.buttonFlags.is_has_test = index_len;
			rf.read(oneCharByte);
			index_len = Str4Bytes2Int(oneCharByte);
			nowLoadingData.buttonFlags.is_has_summary = index_len;

			// read kind of course
			rf.seek(index_seek_len + 4 + nowLoadingData.buttonFlags.total_flags);
			rf.read(oneCharByte);
			index_len = Str4Bytes2Int(oneCharByte);
			index_len = index_len < MyDataPath.KIND_OF_COURSE.length ? index_len
					: 0;
			my_course_id = MyDataPath.KIND_OF_COURSE[index_len];
			flashPath = MyDataPath.FLASH_DATA_PATH[my_course_id];

			// read relate course index
			// read index len
			rf.seek(index_seek_len + 8 + nowLoadingData.buttonFlags.total_flags);
			indexBuffer = null;
			rf.read(oneCharByte);
			index_len = Str4Bytes2Int(oneCharByte);

			// relateCourseIndexDataOffset = rf.getFilePointer();
			// summaryDataOffset = relateCourseIndexDataOffset + index_len * 2;
			// read relate course index buffer
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
						if (0 == nowLoadingData.buttonFlags.is_has_summary) {
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
			rf.close();
			ret = true;
		} catch (FileNotFoundException e) {
			if (DebugFlag) {
				// MyErrorPrintfActivity.startMyErrorPrintfActivity(mContext,
				// e);
			} else {
				// e.printStackTrace();
				Toast.makeText(mContext, R.string.appShutdownStr_readData,
						Toast.LENGTH_SHORT).show();
			}
			((Activity) mContext).finish();
		} catch (IOException e) {
			if (DebugFlag) {
				// MyErrorPrintfActivity.startMyErrorPrintfActivity(mContext,
				// e);
			} else {
				// e.printStackTrace();
				Toast.makeText(mContext, R.string.appShutdownStr_readData,
						Toast.LENGTH_SHORT).show();
			}
			((Activity) mContext).finish();
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

		OneSubViewData tmpOneData = new OneSubViewData();
		tmpOneData.mScrollView = nowLoadingData.mScrollView;
		tmpOneData.knowledgeID = nowLoadingData.knowledgeID;
		tmpOneData.knowledgeName = nowLoadingData.knowledgeName;
		tmpOneData.buttonFlags.is_has_diy = nowLoadingData.buttonFlags.is_has_diy;
		tmpOneData.buttonFlags.is_has_summary = nowLoadingData.buttonFlags.is_has_summary;
		tmpOneData.buttonFlags.is_has_test = nowLoadingData.buttonFlags.is_has_test;
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
		Log.e("ViewPagerAdapter----->", "error!");
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

	private class KnowledgeInfo {
		public String knowledgeId;
		public String knowledgeName;

		public KnowledgeInfo() {
			knowledgeId = knowledgeName = "";
		}

		public KnowledgeInfo(String buffer) {
			knowledgeId = knowledgeName = "";
			char[] bufferChar = new char[buffer.length()];
			buffer.getChars(0, buffer.length(), bufferChar, 0);
			int cut_str = 0;
			for (int i = 0; i < bufferChar.length; i++) {
				if ('|' == bufferChar[i]) {
					cut_str = 1;
				} else {
					if (0 == cut_str) {
						knowledgeId += bufferChar[i];
					} else if (1 == cut_str) {
						knowledgeName += bufferChar[i];
					}
				}
			}
		}
	}

}
