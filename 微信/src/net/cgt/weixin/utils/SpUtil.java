package net.cgt.weixin.utils;

import java.util.Map;
import java.util.Set;

import net.cgt.weixin.Constants;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences
 * 
 * @author lijian
 * 
 */
public class SpUtil {

	private SharedPreferences sp;

	public SpUtil(Context context) {
		sp = context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
	}

	/**
	 * 判断某个键值对是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean contains(String key) {
		return sp.contains(key);
	}

	/**
	 * 保存配置信息
	 * 
	 * @param key
	 * @param value
	 */
	public void saveBoolean(String key, boolean value) {
		sp.edit().putBoolean(key, value).commit();
	}

	/**
	 * 保存配置信息
	 * 
	 * @param key
	 * @param value
	 */
	public void saveFloat(String key, float value) {
		sp.edit().putFloat(key, value).commit();
	}

	/**
	 * 保存配置信息
	 * 
	 * @param key
	 * @param value
	 */
	public void saveLong(String key, long value) {
		sp.edit().putLong(key, value).commit();
	}

	/**
	 * 保存配置信息
	 * 
	 * @param key
	 * @param value
	 */
	public void saveInt(String key, int value) {
		sp.edit().putInt(key, value).commit();
	}

	/**
	 * 保存配置信息
	 * 
	 * @param key
	 * @param value
	 */
	public void saveString(String key, String value) {
		sp.edit().putString(key, value).commit();
	}

	/**
	 * 保存配置信息
	 * 
	 * @param key
	 * @param value
	 */
	@SuppressLint("NewApi")
	public void saveStringSet(String key, Set<String> values) {
		sp.edit().putStringSet(key, values).commit();
	}

	/**
	 * 获得配置信息
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public String getString(String key, String... defValue) {
		if (defValue.length > 0) {
			return sp.getString(key, defValue[0]);
		} else {
			return sp.getString(key, "");
		}
	}

	/**
	 * 获得配置信息
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public int getInt(String key, int... defValue) {
		if (defValue.length > 0) {
			return sp.getInt(key, defValue[0]);
		} else {
			return sp.getInt(key, 0);
		}
	}

	/**
	 * 获得配置信息
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public boolean getBoolean(String key, boolean... defValue) {
		if (defValue.length > 0) {
			return sp.getBoolean(key, defValue[0]);
		} else {
			return sp.getBoolean(key, false);
		}
	}

	/**
	 * 获得配置信息
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public float getFloat(String key, float... defValue) {
		if (defValue.length > 0) {
			return sp.getFloat(key, defValue[0]);
		} else {
			return sp.getFloat(key, 0);
		}
	}

	/**
	 * 获得配置信息
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public long getLong(String key, long... defValue) {
		if (defValue.length > 0) {
			return sp.getLong(key, defValue[0]);
		} else {
			return sp.getLong(key, 0);
		}
	}

	/**
	 * 获得配置信息
	 * 
	 * @return
	 */
	public Map<String, ?> getAll() {
		return sp.getAll();
	}

	/**
	 * 获得配置信息
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	@SuppressLint("NewApi")
	public Set<String> getStringSet(String key, Set<String>... defValue) {
		if (defValue.length > 0) {
			return sp.getStringSet(key, defValue[0]);
		} else {
			return sp.getStringSet(key, null);
		}
	}
}
