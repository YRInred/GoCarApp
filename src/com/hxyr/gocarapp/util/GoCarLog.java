package com.hxyr.gocarapp.util;

import android.text.TextUtils;
import android.util.Log;

public class GoCarLog {
	
	public static void printInfo(String msg) {
		if(TextUtils.isEmpty(msg)) {
			Log.i("inred", msg);
		}
	}
	
	public static void printInfo(String tag,String msg) {
		if(TextUtils.isEmpty(tag) && TextUtils.isEmpty(msg)) 
			Log.i(tag, msg);
	}
	
	public static void printError(String msg) {
		if(TextUtils.isEmpty(msg)) {
			Log.e("inred", msg);
		}
	}
	
	public static void printError(String tag,String msg) {
		if(TextUtils.isEmpty(tag) && TextUtils.isEmpty(msg)) {
			Log.e(tag, msg);
		}
	}
	
}
