package com.besta.app.realteaching;

import java.net.URI;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class DownloadReceiver extends BroadcastReceiver {

	public URI mNewNameUri;
	public URI mDownloadUri;
	public String flashFilePath;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String actionBuffer = intent.getAction();
		if (actionBuffer
				.equals("com.besta.util.contentsdownload.downloadonefile.ok")) { // besta下載器文件下載完成廣播
			String uri = intent.getStringExtra("fileuri");
			if (RealTeachingActivity.mContext != null && uri != null) {
				Message msg = new Message();
				msg.what = RealTeachingActivity.HANDLER_MSG_DOWNLOADFLASH_SUCCEED;
				msg.obj = (Object) uri;
				RealTeachingActivity window = (RealTeachingActivity) RealTeachingActivity.mContext;
				window.mHandler.sendMessage(msg);
			}
		} else if (actionBuffer
				.equals("com.besta.util.contentsdownload.intent.complete")) {
			String download_status = intent.getStringExtra("status");
			if (null != download_status
					&& RealTeachingActivity.mContext != null) {
				if (download_status.compareTo("success") == 0) { // 下載成功
					String uri = intent.getStringExtra("fileuri");
					if (RealTeachingActivity.mContext != null && uri != null) {
						Message msg = new Message();
						msg.what = RealTeachingActivity.HANDLER_MSG_DOWNLOADFLASH_SUCCEED;
						msg.obj = (Object) uri;
						RealTeachingActivity window = (RealTeachingActivity) RealTeachingActivity.mContext;
						window.mHandler.sendMessage(msg);
					}
				} else if (download_status.compareTo("failed") == 0) { // 下載失敗
					String fail_reason = intent.getStringExtra("reason");
					if (RealTeachingActivity.mContext != null
							&& null == fail_reason) {
						Message msg = new Message();
						msg.what = RealTeachingActivity.HANDLER_MSG_DOWNLOADFLASH_FAIL_OTHER;
						RealTeachingActivity window = (RealTeachingActivity) RealTeachingActivity.mContext;
						window.mHandler.sendMessage(msg);
					} else {
						if (RealTeachingActivity.mContext != null
								&& (fail_reason.compareTo("insufficientspace") == 0 || fail_reason
										.compareTo("nosdcard") == 0)) { // sd卡已滿或者sdcard不存在
							Message msg = new Message();
							msg.what = RealTeachingActivity.HANDLER_MSG_DOWNLOADFLASH_FAIL_SDCARD;
							Log.d("Context before", "AAA==="
									+ RealTeachingActivity.mContext);
							RealTeachingActivity window = (RealTeachingActivity) RealTeachingActivity.mContext;
							window.mHandler.sendMessage(msg);
							Log.d("Context before", "BBB==="
									+ RealTeachingActivity.mContext);
						} else if (RealTeachingActivity.mContext != null
								&& fail_reason.compareTo("nowifi") == 0) {
							Message msg = new Message();
							msg.what = RealTeachingActivity.HANDLER_MSG_DOWNLOADFLASH_FAIL_NOWIFI;
							RealTeachingActivity window = (RealTeachingActivity) RealTeachingActivity.mContext;
							window.mHandler.sendMessage(msg);
						} else if (RealTeachingActivity.mContext != null) { // 其他原因
							String uri = intent.getStringExtra("fileuri");
							Message msg = new Message();
							msg.what = RealTeachingActivity.HANDLER_MSG_DOWNLOADFLASH_FAIL_OTHER;
							msg.obj = (Object) uri;
							RealTeachingActivity window = (RealTeachingActivity) RealTeachingActivity.mContext;
							window.mHandler.sendMessage(msg);
						}
					}
				}
			} else if (actionBuffer
					.equals("com.besta.util.contentsdownload.intent.cancelall")) {
				if (RealTeachingActivity.mContext != null) {
					Message msg = new Message();
					msg.what = RealTeachingActivity.HANDLER_MSG_DOWNLOADFLASH_CANCELALL;
					RealTeachingActivity window = (RealTeachingActivity) RealTeachingActivity.mContext;
					window.mHandler.sendMessage(msg);
				}
			} else if (intent.getAction().equals(
					DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
				DownloadManager m_DownloadManager = (DownloadManager) context
						.getSystemService(Context.DOWNLOAD_SERVICE);
				Cursor cur = m_DownloadManager
						.query(new DownloadManager.Query()
								.setFilterByStatus(DownloadManager.STATUS_RUNNING));
				if (cur.moveToFirst()) {
					do {
						m_DownloadManager.remove(cur.getLong(cur
								.getColumnIndex(DownloadManager.COLUMN_ID)));
					} while (cur.moveToNext());
				}
				Toast.makeText(context, "Cancel All downloads",
						Toast.LENGTH_SHORT).show();
			} else if (actionBuffer
					.equals(ConnectivityManager.CONNECTIVITY_ACTION)) { // wifi連接成功，斷開連接的廣播
				ConnectivityManager connectivity = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				State wifiState = connectivity.getNetworkInfo(
						ConnectivityManager.TYPE_WIFI).getState();
				if (wifiState == State.CONNECTED
						|| wifiState == State.CONNECTING) {
					if (RealTeachingActivity.mContext != null) {
						Message msg = new Message();
						msg.what = RealTeachingActivity.HANDLER_MSG_WIFI_ISCONNECTED;
						RealTeachingActivity window = (RealTeachingActivity) RealTeachingActivity.mContext;
						window.mHandler.sendMessage(msg);
					}
				} else {
					if (RealTeachingActivity.mContext != null) {
						Message msg = new Message();
						msg.what = RealTeachingActivity.HANDLER_MSG_WIFI_ISDISCONNECTED;
						RealTeachingActivity window = (RealTeachingActivity) RealTeachingActivity.mContext;
						window.mHandler.sendMessage(msg);
					}
				}
			}
		}
	}
}
