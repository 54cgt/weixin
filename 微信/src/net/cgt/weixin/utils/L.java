package net.cgt.weixin.utils;

import android.util.Log;

/**
 * Log control class
 * 
 * @author lijian
 * 
 * @date 2014-11-12
 * 
 */
public class L {
	private static final boolean flag = true;

	public static void i(String tag, String msg) {
		if (flag)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (flag)
			Log.d(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (flag)
			Log.e(tag, msg);
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (flag)
			Log.e(tag, msg, tr);
	}

	public static void v(String tag, String msg) {
		if (flag)
			Log.v(tag, msg);
	}

	public static void m(String tag, String msg) {
		if (flag)
			Log.e(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (flag)
			Log.w(tag, msg);
	}
}