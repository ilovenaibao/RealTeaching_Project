package com.besta.app.realteaching.dict.engine.listeners;

import android.view.View;
import android.webkit.WebView;

public interface SelectionListener {
	int SELECT_TEXT = 0, SELECT_LINK_ID = 1, SELECT_LINK_STRING = 2,
			SELECT_LINK_SYBSPH = 3, SELECT_LINK_SHELL_RUN = 4;

	boolean onContentSelected(String content, int type, View srcView);

	boolean onLoadFinish(WebView wv);
}
