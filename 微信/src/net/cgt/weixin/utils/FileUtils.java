package net.cgt.weixin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import android.content.Context;

/**
 * 文件工具类
 * 
 * @author lijian
 * @date 2014-12-11 23:00:53
 */
public class FileUtils {

	private static final String LOGTAG = LogUtil.makeLogTag(FileUtils.class);

	/**
	 * 读取assets资产目录下的表情配置文件
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getEmojiFile(Context context) {
		InputStream in = null;
		BufferedReader buff = null;
		try {
			List<String> list = new ArrayList<String>();
			in = context.getResources().getAssets().open("emoji");// 获得一个字节流
			buff = new BufferedReader(new InputStreamReader(in, "UTF-8"));// 将字节流读取到内存，并加入缓冲区
			String line = null;
			while ((line = buff.readLine()) != null) {// 每次读取一行
				list.add(line);
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
			L.e(LOGTAG, "getEmojiFile--->Could not find the properties file.", e);
		} finally {
			try {
				if (buff != null) {
					//其实关闭缓冲区，就是在关闭缓冲区中的流对象
					buff.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				L.e(LOGTAG, "getEmojiFile--->Could not find the properties file.", e);
			}
		}
		return null;
	}

	/**
	 * 读取Android资源目录下的文件(res-->raw-->emoji.properties)
	 * 
	 * @param context
	 * @return
	 */
	public static Properties loadProperties(Context context) {
		Properties props = new Properties();
		try {
			int id = context.getResources().getIdentifier("emoji", "raw", context.getPackageName());
			props.load(context.getResources().openRawResource(id));
		} catch (Exception e) {
			e.printStackTrace();
			L.e(LOGTAG, "loadProperties--->Could not find the properties file.", e);
		}
		return props;
	}
}
