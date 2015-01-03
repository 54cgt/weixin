package net.cgt.weixin.app;

import net.cgt.weixin.utils.LogUtil;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 全局变量
 * 
 * @author lijian
 * @date 2015-01-03 16:32:27
 */
public class App extends Application {

	private static final String LOGTAG = LogUtil.makeLogTag(App.class);

	private static App instance;

	@Override
	public void onCreate() {
		instance = this;
		super.onCreate();

		// 异常处理，不需要处理时注释掉这两句即可！
		CrashHandler crashHandler = CrashHandler.getInstance();
		// 注册crashHandler
		crashHandler.init(getApplicationContext());
	}

	@Override
	public void onTerminate() {
	}

	/**
	 * Global context
	 * 
	 * @return
	 */
	public static App getContext() {
		return instance;
	}

	/**
	 * 获取版本name
	 * 
	 * @return
	 */
	public static String getVersionName() {
		PackageManager packageManager = instance.getPackageManager();
		PackageInfo packageInfo = null;
		try {
			packageInfo = packageManager.getPackageInfo(instance.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageInfo.versionName;
	}

	/**
	 * 获取版本code
	 * 
	 * @return
	 */
	public static int getVersionCode() {
		PackageManager packageManager = instance.getPackageManager();
		PackageInfo packageInfo = null;
		try {
			packageInfo = packageManager.getPackageInfo(instance.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageInfo.versionCode;
	}
}
