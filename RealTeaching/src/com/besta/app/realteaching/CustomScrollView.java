package com.besta.app.realteaching;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {
	private boolean touch_able = true;

	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFadingEdgeLength(0);
		// requestFocus();
	}

	@Override
	protected int computeScrollDeltaToGetChildRectOnScreen(Rect arg0) {
		// TODO Auto-generated method stub
		// return super.computeScrollDeltaToGetChildRectOnScreen(arg0);
		return 0;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (touch_able) {
			return super.onInterceptTouchEvent(ev);
		} else {
			return false;
		}
		// return false;
	}

	public void setTouchAble(boolean flag) {
		touch_able = flag;
	}

	public static void scrollToBottom(final View scroll, final View inner) {
		Handler mHandler = new Handler();

		mHandler.post(new Runnable() {
			public void run() {
				if (scroll == null || inner == null) {
					return;
				}

				int offset = inner.getMeasuredHeight() - scroll.getHeight();
				if (offset < 0) {
					offset = 0;
				}

				scroll.scrollTo(0, offset);
			}
		});
	}
}