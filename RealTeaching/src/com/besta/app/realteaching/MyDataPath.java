package com.besta.app.realteaching;

public class MyDataPath {
	public final static String[] ext_sdcard = { "sdcard", "extsd",
			"sdcard/besta/lms/video_teaching/" };

	public final static int KIND_OF_COURSE[] = {
			// 真人教學部份：
			0, // math
			1, // phy
			2, // chemistry
			3, // bio
			4, // earth
			5, // eng
				// 自然科實驗課部份：
			6, // (物理)
			7, // (化學)
			8, // (生物)
				// 相關公式：
			9, // 不分科目
				// 單子王等學習類ap
			10, // 單子王
			11, // 片語王
			12, // 句型王
			13, // 發音王
			14, // 文法
			15, // 句型
			16, // 對話
			17, // 閱讀
			18, // 寫作
			19, // 發音
			20, // 英檢聽力
			21, // 英檢閱讀
			22, // 英檢口說
			23, // 英檢寫作
	};

	public final static String PKG_NAME[] = {
			// 真人教學部份：
			"com.besta.app.knowledge.twmath", // math
			"com.besta.app.knowledge.twphysics", // phy
			"com.besta.app.knowledge.twchemistry", // chem
			"com.besta.app.knowledge.twbiology", // bio
			"com.besta.app.knowledge.twearthscience", // earth
			"com.besta.app.knowledge.", // eng
			// 自然科實驗課部份：
			"com.besta.app.phybiodiycourse", // (物理)
			"com.besta.app.phybiodiycourse", // (化學)
			"com.besta.app.phybiodiycourse", // (生物)
	};

	public final static String FLASH_DATA_PATH[] = {
			// 真人教學部份：
			"besta/video_teaching/jur_math/", // math
			"besta/video_teaching/jur_phy/", // phy
			"besta/video_teaching/jur_che/", // chem
			"besta/video_teaching/jur_biology/", // bio
			"besta/video_teaching/jur_earth_science/", // earth
			"besta/video_teaching/jur_eng/", // eng
			// 自然科實驗課部份：
			"besta/video_teaching/jur_lab/", // (物理)
			"besta/video_teaching/jur_lab/", // (化學)
			"besta/video_teaching/jur_lab/", // (生物)
			// 相關公式：
			"besta/video_teaching/jur_formular/", // 不分科目
			// 單子王等學習類ap
			"besta/video_teaching/Word/", // 單子王
			"besta/video_teaching/Phas/", // 片語王
			"besta/video_teaching/Patn/", // 句型王
			"besta/video_teaching/Pron/", // 發音王
			"besta/video_teaching/jur_eng/", // 文法
			"besta/video_teaching/jur_eng/", // 句型
			"besta/video_teaching/jur_eng/", // 對話
			"besta/video_teaching/jur_eng/", // 閱讀
			"besta/video_teaching/jur_eng/", // 寫作
			"besta/video_teaching/jur_eng/", // 發音
			"besta/video_teaching/GEPT_Listen/", // 英檢聽力
			"besta/video_teaching/GEPT_Read/", // 英檢閱讀
			"besta/video_teaching/GEPT_Speak/", // 英檢口說
			"besta/video_teaching/GEPT_Write/", // 英檢寫作

	};

	public final static String RES_DATA_TITLE_NAME[] = {
			// 真人教學部份：
			"國中數學", // math
			"國中物理", // phy
			"國中化學", // chemistry
			"國中生物", // bio
			"地球科學", // earth
			"國中英語", // eng
			// 自然科實驗課部份：
			"物理實驗課", // (物理)
			"化學實驗課", // (化學)
			"生物實驗課", // (生物)
			// 相關公式：
			"相關公式", // 不分科目
			// 單子王等學習類ap
			"單子王", // 單子王
			"片語王", // 片語王
			"句型王", // 句型王
			"發音王", // 發音王
			"文法", // 文法
			"句型", // 句型
			"對話", // 對話
			"閱讀", // 閱讀
			"寫作", // 寫作
			"發音", // 發音
			"英檢聽力", // 英檢聽力
			"英檢閱讀", // 英檢閱讀
			"英檢口說", // 英檢口說
			"英檢寫作", // 英檢寫作
	};

	public final static int RES_PRE_SHOW_PNG[] = {
			// 真人教學部份：
			R.drawable.pre_show_gz_math, // math
			R.drawable.pre_show_gz_phy, // phy
			R.drawable.pre_show_gz_chem, // chem
			R.drawable.pre_show_gz_bio, // bio
			R.drawable.pre_show_gz_earth, // earth
			R.drawable.pre_show_gz_eng, // eng
			// 自然科實驗課部份：
			R.drawable.pre_show_gz_diy_phy, // (物理)
			R.drawable.pre_show_gz_diy_chem, // (化學)
			R.drawable.pre_show_gz_diy_bio, // (生物)
			// 相關公式：
			R.drawable.play_flash, // 不分科目
			// 單子王等學習類ap
			R.drawable.play_flash, // 單子王
			R.drawable.play_flash, // 片語王
			R.drawable.play_flash, // 句型王
			R.drawable.play_flash, // 發音王
			R.drawable.play_flash, // 文法
			R.drawable.play_flash, // 句型
			R.drawable.play_flash, // 對話
			R.drawable.play_flash, // 17 閱讀
			R.drawable.play_flash, // 18 寫作
			R.drawable.play_flash, // 19 發音
			R.drawable.play_flash, // 20 英檢聽力
			R.drawable.play_flash, // 21 英檢閱讀
			R.drawable.play_flash, // 22 英檢口說
			R.drawable.play_flash, // 23 英檢寫作
	};

	public final static String RES_DATA_PATH[] = {
			// 真人教學部份：
			"/data/data/com.besta.app.realteaching/jur_math/", // math
			"/data/data/com.besta.app.realteaching/jur_phy_che/", // phy
			"/data/data/com.besta.app.realteaching/jur_phy_che/", // chem
			"/data/data/com.besta.app.realteaching/jur_biology/", // bio
			"/data/data/com.besta.app.realteaching/jur_earth_science/", // earth
			"/data/data/com.besta.app.realteaching/jur_eng/", // eng
			// 自然科實驗課部份：
			"/data/data/com.besta.app.realteaching/jur_lab/", // (物理)
			"/data/data/com.besta.app.realteaching/jur_lab/", // (化學)
			"/data/data/com.besta.app.realteaching/jur_lab/", // (生物)
			// 相關公式：
			"/data/data/com.besta.app.realteaching/jur_formular/", // 不分科目
			// 單子王等學習類ap
			"/data/data/com.besta.app.realteaching/Word/", // 單子王
			"/data/data/com.besta.app.realteaching/Phas/", // 片語王
			"/data/data/com.besta.app.realteaching/Patn/", // 句型王
			"/data/data/com.besta.app.realteaching/Pron/", // 發音王
			"/data/data/com.besta.app.realteaching/JuHS_Grammar/", // 文法
			"/data/data/com.besta.app.realteaching/JuHS_Patn/", // 句型
			"/data/data/com.besta.app.realteaching/JuHS_Dlg/", // 對話
			"/data/data/com.besta.app.realteaching/JuHS_Read/", // 17 閱讀
			"/data/data/com.besta.app.realteaching/JuHS_Write/", // 18 寫作
			"/data/data/com.besta.app.realteaching/JuHS_Pron/", // 19 發音
			"/data/data/com.besta.app.realteaching/GEPT_Listen/", // 20
																	// 英檢聽力
			"/data/data/com.besta.app.realteaching/GEPT_Read/", // 21 英檢閱讀
			"/data/data/com.besta.app.realteaching/GEPT_Speak/", // 22
																	// 英檢口說
			"/data/data/com.besta.app.realteaching/GEPT_Write/", // 23
																	// 英檢寫作
	};

}
