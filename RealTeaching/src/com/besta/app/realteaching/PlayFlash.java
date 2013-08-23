package com.besta.app.realteaching;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * <p>
 * Copyright (c) 2010 by Inventec Besta Co., Ltd. All rights reserved.
 * <P>
 * PlayFlash for call flash player play file.
 * 
 * @since Android SDK 2.3, run on Android 2.3.1 and above
 * @version Revision 1.0.1, 2011.11.03
 * @author Jack Dan
 * @see FlashPlayer
 * @Notes Only for call besta flash player
 */
public class PlayFlash {
	/** Standard activity result: operation canceled. */
	static public final String TAG = "PlayFlash";
	static public final int version = 4;

	/** Standard activity result: operation canceled. */
	static public final int quitPlayEnd = 1;
	static public final int quitKeyHome = 2;
	static public final int quitKeyEsc = 3;
	static public final int quitErrorParam = 4;
	static public final int quitErrorPlug = 5;
	static public final int quitCommand = 6;

	static public boolean bExKey = false;

	/** Standard activity result: operation canceled. */
	static public final int flashRequestCode = 999;

	private static boolean isBestaFlashInstalled(Context content) {
		String ADOBEFLASHPACKEGE = "com.besta.flash";
		PackageManager pm = content.getPackageManager();
		try {
			pm.getPackageInfo(ADOBEFLASHPACKEGE, 0);
		} catch (PackageManager.NameNotFoundException nameNotFoundException) {
			return false;
		}
		return true;
	}

	private static int CallFlash(Context context, Bundle bundle) {
		if (!isBestaFlashInstalled(context)) {
			Toast toask;
			toask = Toast.makeText(context, R.string.NoBestaFlash,
					Toast.LENGTH_SHORT);
			toask.show();
			return quitErrorPlug;
		}

		// bundle.putBoolean("bExKey", bExKey);
		// bundle.putString("privateImeOptions", "zh;editable=true;");

		Intent intent = new Intent();
		intent.setComponent(new ComponentName("com.besta.flash",
				"com.besta.flash.FlashCourseActivity"));
		intent.setAction("android.intent.action.VIEW");

		intent.putExtras(bundle);
		if (context != null) {
			if (context instanceof Activity) {
				Log.i(TAG, "Activity call besta flash player");
				Activity activity = (Activity) context;
				// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				activity.startActivityForResult(intent, flashRequestCode);
			} else {
				Log.i(TAG, "Service call besta flash player");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		} else {
			Log.e(TAG, "Invalid param context.");
		}
		// activity.startActivity(intent);
		return 0;
	}

	/**
	 * as {@link #ShowFlash(Context, String, int, int, boolean)}
	 */
	static public int ShowFlash(Context context, String file) {
		return ShowFlash(context, file, 0, 0, true);
	}

	/**
	 * as {@link #ShowFlash(Context, String, int, int, boolean)}
	 */
	static public int ShowFlash(Context context, String file, int nBegin,
			int nEnd) {
		return ShowFlash(context, file, nBegin, nEnd, true);
	}

	/**
	 * Call when show flash file. flash file support type is swf bfh bfe. swf is
	 * Adobe flash build file format. bfh is BXC encode swf file format. bfe is
	 * BestaDataDecode encode bfh file format.
	 * {@link #ShowFlash(Context, String)} to retrieve
	 * 
	 * <p>
	 * <p>
	 * <em>note.</em>
	 * </p>
	 * 
	 * @param context
	 *            your Activity or Service, if it's Activity will call
	 *            startActivityForResult, if it's Service will call
	 *            startActivity.
	 * @param file
	 *            your BFE path file name.
	 * @param nBegin
	 *            <b><i>course</i></b> file begin frame, it will begin flash
	 *            from this frame.
	 * @param nEnd
	 *            <b><i>course</i></b> file end frame, it will exit flash player
	 *            on play frame.
	 * @param bCourse
	 *            If the flash is <b><i>course</i></b> flash file, it will exit
	 *            on play end and flash frame is 480*272.
	 * @return quitPlayEnd = 1, quitKeyHome = 2, quitKeyEsc = 3, quitErrorParam
	 *         = 4, quitErrorPlug = 5, quitCommand = 6;
	 * 
	 */
	static public int ShowFlash(Context context, String file, int nBegin,
			int nEnd, boolean bCourse) {
		Bundle bundle = new Bundle();
		bundle.putString("file", file);
		if (nEnd > nBegin) {
			int arr[] = new int[2];
			arr[0] = nBegin;
			arr[1] = nEnd;
			bundle.putIntArray("playSegment", arr);
		}
		bundle.putBoolean("CoursePlayer", bCourse);

		return CallFlash(context, bundle);
	}

	/**
	 * as {@link #ShowPackFlash(Context, String, String, int, int, boolean)}
	 */
	static public int ShowPackFlash(Context context, String packFile,
			String file) {
		return ShowPackFlash(context, packFile, file, 0, 0);
	}

	/**
	 * as {@link #ShowPackFlash(Context, String, String, int, int, boolean)}
	 */
	static public int ShowPackFlash(Context context, String packFile,
			String file, int nBegin, int nEnd) {
		return ShowPackFlash(context, packFile, file, 0, 0, true);
	}

	/**
	 * as {@link #ShowFlash(Context, String, int, int, boolean)}
	 * 
	 * @param context
	 *            as {@link #ShowFlash(Context, String, int, int, boolean)}
	 * @param packFile
	 *            your bfes file name.
	 * @param file
	 *            your bfes file name.
	 * @param nBegin
	 *            as {@link #ShowFlash(Context, String, int, int, boolean)}
	 * @param nEnd
	 *            as {@link #ShowFlash(Context, String, int, int, boolean)}
	 * @param bCourse
	 *            as {@link #ShowFlash(Context, String, int, int, boolean)}
	 */
	static public int ShowPackFlash(Context context, String packFile,
			String file, int nBegin, int nEnd, boolean bCourse) {
		Bundle bundle = new Bundle();
		bundle.putString("file", file);
		bundle.putString("packName", packFile);
		if (nEnd > nBegin) {
			int arr[] = new int[2];
			arr[0] = nBegin;
			arr[1] = nEnd;
			bundle.putIntArray("playSegment", arr);
		}
		bundle.putBoolean("CoursePlayer", bCourse);
		return CallFlash(context, bundle);
	}

	private static int FOURCC(byte[] b) {
		return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}

	private static int readIntLittleEndian(RandomAccessFile file) {
		int i = 0;
		byte b[] = new byte[4];
		try {
			file.read(b, 0, 4);
			i = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	private static int getOffset(RandomAccessFile file, int foucc, int offset,
			int nIndex, int data[]) {
		int ret = 0;
		try {
			if (offset == 0) {
				int pos = 0;
				file.seek(file.length() - 4);
				pos = readIntLittleEndian(file);
				file.seek(file.length() - pos);

			} else {
				file.seek(offset);
			}
			int buffer[] = new int[2];
			buffer[0] = readIntLittleEndian(file);
			buffer[1] = readIntLittleEndian(file);
			if ((foucc == 0 || foucc == buffer[0])
					&& (nIndex > 0 && nIndex < buffer[1])) {
				file.skipBytes((nIndex - 1) * 4);
				buffer[0] = readIntLittleEndian(file);
				buffer[1] = readIntLittleEndian(file);
				ret = buffer[0];
				data[0] = buffer[0];
				data[1] = buffer[1];
				// if(pLength) *pLength = buffer[1]-buffer[0];
			} else if (foucc == buffer[0] && nIndex == 0) {
				return buffer[1];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	// private static byte[] fourccFile = {'F', 'I', 'L', 0};
	// private static byte[] fourccStringUtf8 = {'S', 'T', 'R', 0};
	// private static byte[] fourccStringUtf16 = {'S', 'T', 'R', 1};
	private static byte[] fourccOffset32 = { 'O', 'F', 'S', 0 };
	// private static byte[] fourccOffset64 = {'O', 'F', 'S', 1};
	private static byte[] fourccBfs = { 'B', 'F', 'S', 0 };
	private static byte[] fourccDWORD = { 'D', 'W', 'D', 0 };

	// list all filename from BFES file.
	public static String[] getBfsFilenames(String fileName) {

		String[] names = null;
		try {
			int data[] = new int[2];
			RandomAccessFile file = new RandomAccessFile(fileName, "r");
			int m_lNameTableOffset = getOffset(file, FOURCC(fourccBfs), 0, 2,
					data);
			int i;
			int nCount = getOffset(file, FOURCC(fourccOffset32),
					m_lNameTableOffset, 0, data);
			names = new String[nCount];
			for (i = 1; i < nCount; i++) {
				int t = getOffset(file, FOURCC(fourccOffset32),
						m_lNameTableOffset, i, data);
				int len = data[1] - data[0];
				byte p[] = new byte[len];
				file.seek(t);
				file.read(p, 0, len);
				names[i] = new String(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return names;
	}

	// get bfe file version.
	public static int getBfsVersion(String fileName) {
		int ver = 0;
		try {
			int data[] = new int[2];
			RandomAccessFile file = new RandomAccessFile(fileName, "r");
			int m_lNameTableOffset = getOffset(file, FOURCC(fourccBfs), 0, 4,
					data);
			ver = getOffset(file, FOURCC(fourccDWORD), m_lNameTableOffset, 1,
					data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ver;
	}

	// get bfe file type.
	// get bfh file type.
	// get bfes file type.
	public static int[] getBfeType(String fileName) {
		boolean bGetType = false;
		int[] ver = new int[4];
		ver[0] = 0;
		ver[1] = 0;
		ver[2] = 0;
		ver[3] = 0;

		/*
		 * if (!bGetType) { try { byte data[] = new byte[4]; BestaDataDecoder
		 * bdd = new BestaDataDecoder(); bdd.init(null); if (bdd.open(fileName))
		 * { bdd.get(data); if (data[0] == 'B' && data[1] == 'F' && data[2] ==
		 * 'H') { bdd.setPointer(4 * 7, 0); bdd.get(data); ver[0] =
		 * ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getInt();
		 * bdd.get(data); ver[1] =
		 * ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getInt();
		 * bdd.get(data); ver[2] =
		 * ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getInt();
		 * bdd.get(data); ver[3] =
		 * ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getInt();
		 * bGetType = true; } } } catch (Exception e) { e.printStackTrace(); } }
		 */

		if (!bGetType) {
			try {
				byte data[] = new byte[4];
				File file = new File(fileName);
				InputStream in = new FileInputStream(file);
				in.read(data);
				if (data[0] == 'B' && data[1] == 'F' && data[2] == 'H') {
					in.skip(4 * 6);
					in.read(data);
					ver[0] = ByteBuffer.wrap(data)
							.order(ByteOrder.LITTLE_ENDIAN).getInt();
					in.read(data);
					ver[1] = ByteBuffer.wrap(data)
							.order(ByteOrder.LITTLE_ENDIAN).getInt();
					in.read(data);
					ver[2] = ByteBuffer.wrap(data)
							.order(ByteOrder.LITTLE_ENDIAN).getInt();
					in.read(data);
					ver[3] = ByteBuffer.wrap(data)
							.order(ByteOrder.LITTLE_ENDIAN).getInt();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!bGetType) {
			try {
				int data[] = new int[2];
				RandomAccessFile file = new RandomAccessFile(fileName, "r");
				int a = getOffset(file, FOURCC(fourccBfs), 0, 4, data);
				if (a > 0) {
					ver[0] = getOffset(file, FOURCC(fourccDWORD), a, 2, data);
					ver[1] = getOffset(file, FOURCC(fourccDWORD), a, 3, data);
					ver[2] = getOffset(file, FOURCC(fourccDWORD), a, 4, data);
					ver[3] = getOffset(file, FOURCC(fourccDWORD), a, 5, data);
					bGetType = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ver;
	}
}
