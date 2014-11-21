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
 * ϵͳ��Ϣ�Ĺ�����
 * @author Administrator
 *
 */
public class SystemInfoUtils {

	/**
	 * ��ȡ�ֻ������������еĽ�̵ĸ���
	 * @param context ������
	 * @return
	 */
	public static int getRunningProcessCount(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		//��ȡ�ֻ��������е��������еĽ����Ϣ 
		List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();
		return infos.size();
	}
	
	/**
	 * ��ȡ���õ��ֻ��ڴ�
	 * @param context ������
	 * @return
	 */
	public static long getAvailRAM(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		am.getMemoryInfo(outInfo);
		return outInfo.availMem;
	}
	/**
	 * ��ȡȫ�����ֻ��ڴ�
	 * @param context ������
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
			//MemTotal:         513000 kB  �ַ� ��
			String line = br.readLine();
			char[] chars = line.toCharArray();
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<chars.length;i++){
				if(chars[i]>='0'&&chars[i]<='9'){
					sb.append(chars[i]);
				}
			}
			long memsize = Integer.parseInt(sb.toString());
			return memsize*1024;//byte ��λ 
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
