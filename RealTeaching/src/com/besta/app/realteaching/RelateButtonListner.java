package com.besta.app.realteaching;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class RelateButtonListner {

	public class RelateButtonOntouchListener implements OnTouchListener {
		Context mContext;

		public RelateButtonOntouchListener(Context context) {
			mContext = context;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	public class RelateButtonOnClickListener implements OnClickListener {
		Context mContext;

		public RelateButtonOnClickListener(Context context) {
			mContext = context;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		}

	}

	public class RelateButtonOnClickListener1 implements OnClickListener {
		Context mContext;
		OneSubViewData oneData = null;

		public RelateButtonOnClickListener1(Context context, OneSubViewData data) {
			mContext = context;
			oneData = data;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		}

	}

	public class RelateButtonOnClickListener_viewpager implements
			OnClickListener {
		Context mContext;
		OneSubViewData oneData = new OneSubViewData();

		public RelateButtonOnClickListener_viewpager(Context context,
				OneSubViewData data) {
			mContext = context;
			oneData = data;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		}

	}
}