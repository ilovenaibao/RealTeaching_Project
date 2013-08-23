package com.besta.app.realteaching;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyRealTeachingViewPager extends ViewPager {
	private boolean touch_able = true;

	public MyRealTeachingViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyRealTeachingViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			return super.onTouchEvent(arg0);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		if (touch_able) {
			try {
				return super.onInterceptTouchEvent(arg0);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public void setTouchAble(boolean flag) {
		touch_able = flag;
	}
}
