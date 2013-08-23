package com.besta.app.realteachingtestcallapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.besta.app.realteaching.MyDataPath;
import com.besta.app.realteaching.R;
import com.besta.app.realteaching.RealTeachingActivity;

public class TestaCallApp extends Activity {

	Context mContext;
	String edStr = "";
	String knowledgeDataPath = "/mnt/sdcard/besta/lms/video_teaching/J_math_video.dat";

	// String knowledgeDataPath = "/mnt/" + MyDataPath.ext_sdcard[2]
	// + "J_eng_video.dat";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_call_main);
		mContext = this;

		Button bt_1 = (Button) findViewById(R.id.bt_1);
		bt_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText ed_1 = (EditText) findViewById(R.id.ed_1);
				edStr = ed_1.getText().toString();
				if (!edStr.isEmpty()) {
					myStartRealTeachingActivity(mContext, edStr,
							knowledgeDataPath);
				}
			}
		});
	}

	public void myStartRealTeachingActivity(Context context,
			String knowledgeID, String knowledgeDataPath) {
		Intent intent = new Intent(RealTeachingActivity.ACTIVITY_ACTION);
		Bundle bundle = new Bundle();
		bundle.putString(RealTeachingActivity.KNOWLEDGE_ID_KEY, knowledgeID);
		bundle.putString(RealTeachingActivity.KNOWLEDGE_DATA_PATH_KEY,
				knowledgeDataPath);
		bundle.putString(RealTeachingActivity.KNOWLEDGE_ID_NAME_KEY,
				"對應的List 目錄內容");
		ArrayList<String> all_KnowledgeID = getArrayListID();
		bundle.putStringArrayList(RealTeachingActivity.ALL_KNOWLEDGE_ID,
				all_KnowledgeID);
		bundle.putInt(RealTeachingActivity.CLICK_KNOWLEDGE_ID, 1);
		intent.putExtras(bundle);

		try {
			startActivity(intent);
		} catch (Exception e) {

		}
	}

	private ArrayList<String> getArrayListID() {
		ArrayList<String> all_KnowledgeID = new ArrayList<String>();
		String[] all_id = { "m101010|單元 1 因數與倍數", "m101013|單元 2 因數與倍數的應用",
				"m101020|單元 3 質數 (1)", "m101023|單元 4 質數 (2)",
				"m101030|單元 5 質因數分解", "m101033|單元 6 質因數分解的應用",
				"m101040|單元 7 標準分解式", "m101043|單元 8 標準分解式的應用",
				"m101050|單元 9 最大公因數", "m101053|單元 10 最大公因數的應用",
				"m101060|單元 11 最小公倍數", "m101063|單元 12 最小公倍數的應用",
				"m101070|單元 13 分數", "m101080|單元 14 最簡分數",
				"m101083|單元 15 最簡分數的應用", "m101090|單元 16 分數的大小",
				"m101500|單元 17 本章總複習(因數與倍數)",
				"m101503|單元 18 本章總複習-練習 (1)(因數與倍數)",
				"m101504|單元 19 本章總複習-練習 (2)(因數與倍數)", "m7A0120|單元 20 輾轉相除法",
				"m102010|單元 1 分數的加減運算", "m102013|單元 2 分數的加減運算應用",
				"m102020|單元 3 分數與整數的乘除運算", "m102023|單元 4 分數與整數的乘除運算應用",
				"m102030|單元 5 分數與分數的乘除運算", "m102033|單元 6 分數與分數的乘除應用",
				"m102040|單元 7 數的四則運算", "m102043|單元 8 數的四則運算應用",
				"m102050|單元 9 以符號代表數 (1)", "m102053|單元 10 以符號代表數 (2)",
				"m102060|單元 11 數的四則運算規則 (1)", "m102070|單元 12 數的四則運算規則 (2)",
				"m102073|單元 13 四則運算規則的應用", "m102080|單元 14 繁分數的化簡",
				"m102500|單元 15 本章總複習(數的運算)",
				"m102503|單元 16 本章總複習-練習 (1)(數的運算)",
				"m102504|單元 17 本章總複習-練習 (2)(數的運算)",
				"m102505|單元 18 本章總複習-練習 (3)(數的運算)",
				"m102506|單元 19 本章總複習-練習 (4)(數的運算)", "m103010|單元 1 負數的概念 (1)",
				"m103020|單元 2 負數的概念 (2)", "m103030|單元 3 負數的應用",
				"m103040|單元 4 數線 (1)", "m103050|單元 5 數線 (2)",
				"m103060|單元 6 常用的數學符號", "m103070|單元 7 負數的加法",
				"m103080|單元 8 負數的加法練習", "m103090|單元 9 負數的減法",
				"m103100|單元 10 負數的減法練習", "m103105|單元 11 負數的減法的應用",
				"m103110|單元 12 負數的乘法 (1)", "m103120|單元 13 負數的乘法 (2)",
				"m103130|單元 14 負數的乘法練習", "m103140|單元 15 負數的除法",
				"m103150|單元 16 負數的除法練習", "m103160|單元 17 數與數線 (1)",
				"m103170|單元 18 數與數線 (2)", "m103180|單元 19 數與數線練習",
				"m103500|單元 20 本章總複習 (1)(負數)", "m103510|單元 21 本章總複習 (2)(負數)",
				"m103520|單元 22 本章總複習-練習 (1)(負數)",
				"m103530|單元 23 本章總複習-練習 (2)(負數)",
				"m103540|單元 24 本章總複習-練習 (3)(負數)", "m104010|單元 1 指數的基本概念",
				"m104020|單元 2 指數的基本概念-練習", "m104030|單元 3 指數律 (1)",
				"m104040|單元 4 指數律 (2)", "m104050|單元 5 指數律-練習 (1)" };

		for (int i = 0; i < all_id.length; i++) {
			all_KnowledgeID.add(all_id[i]);
		}

		return all_KnowledgeID;
	}
}
