package net.cgt.weixin.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceStatusUtils {
	
	/**
	 * �ж�һ�������Ƿ��ڿ���״̬
	 * @param context ������
	 * @param serviceClassname ���������������
	 * @return
	 */
	public static boolean isServiceRunning(Context context, String serviceClassname){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo>  serviceInfos = am.getRunningServices(100);
		for(RunningServiceInfo serviceInfo : serviceInfos){
			String servicename = serviceInfo.service.getClassName();
			if(serviceClassname.equals(servicename)){
				return true;
			}
		}
		return false;
	}
}
