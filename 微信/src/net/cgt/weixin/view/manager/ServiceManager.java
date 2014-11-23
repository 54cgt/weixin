package net.cgt.weixin.view.manager;

import java.util.Properties;

import net.cgt.weixin.Constants;
import net.cgt.weixin.utils.L;
import net.cgt.weixin.utils.SpUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public final class ServiceManager {

	private static final String TAG = "ServiceManager";

	private Context context;

	/** 加载配置文件 **/
	private Properties props;

	private String version = "0.5.0";

	private String apiKey;

	private String xmppHost;

	private String xmppPort;

	/** 回调Activity的包名 **/
	private String callbackActivityPackageName;

	/** 回调Activity的全类名 **/
	private String callbackActivityClassName;

	private SpUtil sp;

	public ServiceManager(Context context) {
		this.context = context;
		if (context instanceof Activity) {
			L.i(TAG, "Callback Activity...");
			Activity callbackActivity = (Activity) context;
			callbackActivityPackageName = callbackActivity.getPackageName();
			callbackActivityClassName = callbackActivity.getClass().getName();
		}
		props = loadProperties();
		apiKey = props.getProperty("apiKey", "");
		xmppHost = props.getProperty("xmppHost", "127.0.0.1");
		xmppPort = props.getProperty("xmppPort", "5222");
		L.i(TAG, "apiKey=" + apiKey);
		L.i(TAG, "xmppHost=" + xmppHost);
		L.i(TAG, "xmppPort=" + xmppPort);

		sp = new SpUtil(context);

		sp.saveString(Constants.API_KEY, apiKey);
		sp.saveString(Constants.VERSION, version);
		sp.saveString(Constants.XMPP_HOST, xmppHost);
		sp.saveInt(Constants.XMPP_PORT, Integer.parseInt(xmppPort));
		sp.saveString(Constants.CALLBACK_ACTIVITY_PACKAGE_NAME, callbackActivityPackageName);
		sp.saveString(Constants.CALLBACK_ACTIVITY_CLASS_NAME, callbackActivityClassName);
	}

	/**
	 * 开新线程启动服务
	 */
	public void startService() {
		Thread serviceThread = new Thread(new Runnable() {
			@Override
			public void run() {
//				Intent intent = NotificationService.getIntent();
//				context.startService(intent);
			}
		});
		serviceThread.start();
	}

	/**
	 * 停止服务
	 */
	public void stopService() {
//		Intent intent = NotificationService.getIntent();
//		context.stopService(intent);
	}

	/**
	 * 加载Properties配置文件
	 * 
	 * @return
	 */
	private Properties loadProperties() {
		Properties props = new Properties();
		try {
			int id = context.getResources().getIdentifier("androidpn", "raw", context.getPackageName());
			props.load(context.getResources().openRawResource(id));
		} catch (Exception e) {
			L.e(TAG, "Could not find the properties file.", e);
		}
		return props;
	}

	/**
	 * 设置通知logo
	 * 
	 * @param iconId
	 */
	public void setNotificationIcon(int iconId) {
		sp.saveInt(Constants.NOTIFICATION_ICON, iconId);
	}

	/**
	 * 进入到通知设置界面
	 * 
	 * @param context
	 */
	public static void viewNotificationSettings(Context context) {
//		Intent intent = new Intent().setClass(context, NotificationSettingsActivity.class);
//		context.startActivity(intent);
	}
}
