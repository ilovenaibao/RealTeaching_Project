package com.besta.app.realteaching;

import java.lang.reflect.Method;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.besta.app.realteaching.dict.engine.listeners.SelectionListener;
import com.besta.view.crosssearchwin.tools.CrossJSInterfaceObj;
import com.besta.view.crosssearchwin.tools.CrossWebView;

public class MyCrossWebView extends CrossWebView {
	private int mTouchEventX = -1;
	private int mTouchEventY = -1;
	private int mScrollX;
	private int mScrollY;
	private String mSelectedText = "";
	private SelectionListener mSelectionControl;

	public MyCrossWebView(Context context) {
		super(context);
		initMySettings();
	}

	public MyCrossWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyCrossWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private boolean startSearching(String text, int type) {
		if (mSelectionControl != null) {
			return mSelectionControl.onContentSelected(text, type, this);
		}
		return false;
	}

	public int getTouchEventX() {
		return mTouchEventX;
	}

	public int getTouchEventY() {
		return mTouchEventY;
	}

	private void initMySettings() {
		setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				startMarking();
				// ConfigModel.getInstance().textSelectionMode = true;
				return false;
			}
		});

		setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		// return super.onInterceptTouchEvent(ev);
		// super.onInterceptHoverEvent(ev);
		return true;
	}

	public void setSelectionListener(SelectionListener selectionListener) {
		mSelectionControl = selectionListener;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void startMarking() {
		requestFocus();
		Class webViewClass = getClass().getSuperclass();
		Class[] parameterTypes = new Class[2];
		Method startSelection;
		Method wordSelection;
		Method viewToContentX;
		Method viewToContentY;
		parameterTypes[0] = int.class;
		parameterTypes[1] = int.class;
		try {
			startSelection = webViewClass.getDeclaredMethod("setUpSelect",
					new Class[0]);
			wordSelection = webViewClass.getDeclaredMethod(
					"nativeWordSelection", parameterTypes);
			parameterTypes = new Class[1];
			parameterTypes[0] = int.class;
			viewToContentX = webViewClass.getDeclaredMethod("viewToContentX",
					parameterTypes);
			viewToContentY = webViewClass.getDeclaredMethod("viewToContentY",
					parameterTypes);
			startSelection.setAccessible(true);
			wordSelection.setAccessible(true);
			viewToContentX.setAccessible(true);
			viewToContentY.setAccessible(true);
			Object[] args = new Object[2];
			args[0] = viewToContentX.invoke(this, mTouchEventX + mScrollX);
			args[1] = viewToContentY.invoke(this, mTouchEventY + mScrollY);
			startSelection.invoke(this, new Object[0]);
			wordSelection.invoke(this, args);
			return;
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		String jellyBeanSelection = null; // used this to store the selection
											// text in JellyBean or higher
											// switch (ev.getAction()) {
		// case MotionEvent.ACTION_UP:
		// // requestFocus();
		// Class<?> webViewClass = getClass().getSuperclass();
		// Method getWebViewProvider = null;
		// Object webViewProvider = null;
		// Field filedTouchHighlightRegion = null;
		// Field selectionField = null;
		// try {
		// selectionField = webViewClass
		// .getDeclaredField("mSelectingText");
		// } catch (SecurityException e1) {
		// e1.printStackTrace();
		// } catch (NoSuchFieldException e1) {
		// e1.printStackTrace();
		// // in 4.1 and higher, the WebView has no such filed, we should
		// // reflect the WebViewProvider and get the selection
		// try {
		// getWebViewProvider = webViewClass
		// .getDeclaredMethod("getWebViewProvider");
		// webViewProvider = getWebViewProvider.invoke(this);
		// if (webViewProvider != null) {
		// Method getSelection;
		// getSelection = webViewProvider.getClass()
		// .getDeclaredMethod("getSelection");
		// getSelection.setAccessible(true);
		// jellyBeanSelection = (String) getSelection
		// .invoke(webViewProvider);
		// }
		// } catch (NoSuchMethodException e) {
		// e.printStackTrace();
		// } catch (IllegalArgumentException e) {
		// e.printStackTrace();
		// } catch (IllegalAccessException e) {
		// e.printStackTrace();
		// } catch (InvocationTargetException e) {
		// e.printStackTrace();
		// }
		// }
		// // 2.3
		// if (selectionField == null) {
		// try {
		// selectionField = webViewClass
		// .getDeclaredField("mTouchSelection");
		// } catch (SecurityException e1) {
		// e1.printStackTrace();
		// } catch (NoSuchFieldException e1) {
		// e1.printStackTrace();
		// }
		// }
		// if (selectionField != null) {
		// try {
		// selectionField.setAccessible(true);
		// boolean touchSelection = selectionField.getBoolean(this);
		// if (touchSelection) {
		// // selectionField.setBoolean(this, false);
		// Method selectionMethod = webViewClass
		// .getDeclaredMethod("nativeGetSelection");
		// selectionMethod.setAccessible(true);
		// mSelectedText = (String) selectionMethod.invoke(this);
		// ClipboardManager clipboard = (ClipboardManager) getContext()
		// .getSystemService(Context.CLIPBOARD_SERVICE);
		// CharSequence backupStr = clipboard.getText();
		// clipboard.setText("");
		// mTouchEventY = (int) ev.getY();
		// boolean ret = super.onTouchEvent(ev);
		// touchSelection = selectionField.getBoolean(this);
		// if (!touchSelection) {
		// System.out.println("selection end!");
		// // mSelectedText = clipboard.getText().toString();
		//
		// if (mSelectedText.length() == 0) {
		// clipboard.setText(backupStr);
		// // EngPropertyBean propertyBean =
		// // EngPropertyBean.getInstance();
		// // propertyBean.unlockSelectState();
		// } else {
		// startSearching(mSelectedText,
		// SelectionListener.SELECT_TEXT);
		// // EngPropertyBean propertyBean =
		// // EngPropertyBean.getInstance();
		// // propertyBean.unlockSelectState();
		// }
		// } else {
		// System.out.println("selection continued!");
		// }
		//
		// return ret;
		// }
		// } catch (SecurityException e) {
		// e.printStackTrace();
		// } catch (IllegalArgumentException e) {
		// e.printStackTrace();
		// } catch (IllegalAccessException e) {
		// e.printStackTrace();
		// } catch (NoSuchMethodException e) {
		// e.printStackTrace();
		// } catch (InvocationTargetException e) {
		// e.printStackTrace();
		// }
		// }
		//
		// // 4.0
		// if (selectionField == null) {
		// try {
		// if (jellyBeanSelection != null) {
		// if (webViewProvider != null) {
		// selectionField = webViewProvider.getClass()
		// .getDeclaredField("mSelectingText");
		// filedTouchHighlightRegion = webViewProvider
		// .getClass().getDeclaredField(
		// "mTouchHighlightRegion");
		// }
		// } else {
		// selectionField = webViewClass
		// .getDeclaredField("mTouchSelection");
		// }
		// } catch (SecurityException e1) {
		// e1.printStackTrace();
		// } catch (NoSuchFieldException e1) {
		// e1.printStackTrace();
		// }
		// }
		//
		// if (selectionField != null) {
		// try {
		// boolean touchSelection = false;
		// if (selectionField != null) {
		// selectionField.setAccessible(true);
		// if (jellyBeanSelection != null) {
		// touchSelection = selectionField
		// .getBoolean(webViewProvider);
		// } else {
		// touchSelection = selectionField.getBoolean(this);
		// }
		// }
		// if (touchSelection) {
		// if (jellyBeanSelection != null) {
		// mSelectedText = jellyBeanSelection;
		// } else {
		// // selectionField.setBoolean(this, false);
		// Method selectionMethod = webViewClass
		// .getDeclaredMethod("nativeGetSelection");
		// selectionMethod.setAccessible(true);
		// mSelectedText = (String) selectionMethod
		// .invoke(this);
		// }
		// if (mSelectedText == null)
		// return false;
		// boolean ret = super.onTouchEvent(ev);
		// if (jellyBeanSelection != null) {
		// touchSelection = selectionField
		// .getBoolean(webViewProvider);
		// } else {
		// touchSelection = selectionField.getBoolean(this);
		// }
		// boolean mLockGesture;
		// if (!touchSelection) {
		// Point pt;
		// PointerCoords outPointerCoords = new PointerCoords();
		// ev.getPointerCoords(0, outPointerCoords);
		// pt = new Point((int) outPointerCoords.x,
		// (int) outPointerCoords.y);
		// if (jellyBeanSelection != null) {
		// // judge whether touch the selection
		// Region reg;
		// if (filedTouchHighlightRegion != null) {
		// filedTouchHighlightRegion
		// .setAccessible(true);
		// reg = (Region) filedTouchHighlightRegion
		// .get(webViewProvider);
		// if (reg != null) {
		// reg.contains(pt.x + getScrollX(), pt.y
		// + getScrollY());
		// }
		// }
		// Method getSelectionHandles;
		// int[] selectionPos = new int[4];
		// Class<int[]> params = int[].class;
		// getSelectionHandles = webViewProvider
		// .getClass().getDeclaredMethod(
		// "getSelectionHandles", params);
		// getSelectionHandles.setAccessible(true);
		// getSelectionHandles.invoke(webViewProvider,
		// selectionPos);
		// final Rect rect = new Rect(getLeft(),
		// selectionPos[1] - 100, getRight(),
		// selectionPos[3]);
		// if (!rect.contains(pt.x + getScrollX(), pt.y
		// + getScrollY()))
		// return true; // exit if selection is not
		// // touched
		// selectionPos = null;
		// }
		// mLockGesture = false;
		// System.out.println("selection end!");
		// // mSelectedText = clipboard.getText().toString();
		//
		// if (mSelectedText.length() == 0) {
		// EngPropertyBean propertyBean = EngPropertyBean
		// .getInstance();
		// propertyBean.unlockSelectState();
		// } else {
		// startSearching(mSelectedText,
		// SelectionListener.SELECT_TEXT);
		// EngPropertyBean propertyBean = EngPropertyBean
		// .getInstance();
		// if (null != propertyBean) {
		// propertyBean.unlockSelectState();
		// }
		// }
		// } else {
		// mLockGesture = true;
		// System.out.println("selection continued!");
		// }
		// return true;
		// }
		// } catch (SecurityException e) {
		// e.printStackTrace();
		// } catch (IllegalArgumentException e) {
		// e.printStackTrace();
		// } catch (IllegalAccessException e) {
		// e.printStackTrace();
		// } catch (NoSuchMethodException e) {
		// e.printStackTrace();
		// } catch (InvocationTargetException e) {
		// e.printStackTrace();
		// }
		// }
		//
		// break;
		// case MotionEvent.ACTION_DOWN:
		// mTouchEventX = (int) ev.getX();
		// mTouchEventY = (int) ev.getY();
		// break;
		// }

		boolean ret = super.onTouchEvent(ev);

		return ret;
	}

	@Override
	public CrossJSInterfaceObj getJavascriptInterface() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSearchType() {
		// TODO Auto-generated method stub
		return 0;
	}
}
