package net.cgt.weixin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

/**
 * 系统信息的工具类
 * @author Administrator
 *
 */
public class SystemInfoUtils {

	/**
	 * 获取手机里面正在运行的进程的个数
	 * @param context 上下文
	 * @return
	 */
	public static int getRunningProcessCount(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		//获取手机里面所有的正在运行的进程信息 
		List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();
		return infos.size();
	}
	
	/**
	 * 获取可用的手机内存
	 * @param context 上下文
	 * @return
	 */
	public static long getAvailRAM(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		am.getMemoryInfo(outInfo);
		return outInfo.availMem;
	}
	/**
	 * 获取全部的手机内存
	 * @param context 上下文
	 * @return
	 */
	public static long getTotalRAM(Context context){
//		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//		MemoryInfo outInfo = new MemoryInfo();
//		am.getMemoryInfo(outInfo);
//		return outInfo.totalMem;
		try {
			File file = new File("/proc/meminfo");
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			//MemTotal:         513000 kB  字符 串
			String line = br.readLine();
			char[] chars = line.toCharArray();
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<chars.length;i++){
				if(chars[i]>='0'&&chars[i]<='9'){
					sb.append(chars[i]);
				}
			}
			long memsize = Integer.parseInt(sb.toString());
			return memsize*1024;//byte 单位 
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
