package net.cgt.weixin;

/**
 * Static constants for this package.<br>
 * 静态常量
 * 
 * @author lijian
 * @date 2014-11-22
 */
public interface Constants {

	/** SharedPreferences 文件名 **/
	String SHARED_PREFERENCE_NAME = "client_preferences";

	/********************************** 用户登陆管理 ***************************************************************************************************/

	/** 用户最后登陆时间 **/
	String LAST_TIME = "LAST_TIME";

	/** 一个月内自动登陆 : 60 * 60 * 24 * 30 = 2592000 **/
	long AUTO_LOGIN = 2592000;

	/** 记录上次退出时页面 **/
	String PAGENUMBER = "PAGENUMBER";

	/********************************** 偏好设置 ***************************************************************************************************/

	/** 回调Activity的包名 **/
	String CALLBACK_ACTIVITY_PACKAGE_NAME = "CALLBACK_ACTIVITY_PACKAGE_NAME";

	/** 回调Activity的全类名 **/
	String CALLBACK_ACTIVITY_CLASS_NAME = "CALLBACK_ACTIVITY_CLASS_NAME";

	/** XMPP密钥 **/
	String API_KEY = "API_KEY";

	/** 版本号 **/
	String VERSION = "VERSION";

	/** XMPP的IP **/
	String XMPP_HOST = "XMPP_HOST";

	/** XMPP的端口 **/
	String XMPP_PORT = "XMPP_PORT";

	/** XMPP的用户名 **/
	String XMPP_USERNAME = "XMPP_USERNAME";

	/** XMPP的密码 **/
	String XMPP_PASSWORD = "XMPP_PASSWORD";

	// String USER_KEY = "USER_KEY";

	/** 设备ID(手机*#06#) **/
	String DEVICE_ID = "DEVICE_ID";

	/** 模拟器ID **/
	String EMULATOR_DEVICE_ID = "EMULATOR_DEVICE_ID";

	/** 通知的logo图片 **/
	String NOTIFICATION_ICON = "NOTIFICATION_ICON";

	/** 是否显示推送的通知 **/
	String SETTINGS_NOTIFICATION_ENABLED = "SETTINGS_NOTIFICATION_ENABLED";

	/** 当接到推送通知-->是否播放通知声音 **/
	String SETTINGS_SOUND_ENABLED = "SETTINGS_SOUND_ENABLED";

	/** 当接到推送通知-->是否震动手机 **/
	String SETTINGS_VIBRATE_ENABLED = "SETTINGS_VIBRATE_ENABLED";

	/** 当接到推送通知-->是否显示吐司 **/
	String SETTINGS_TOAST_ENABLED = "SETTINGS_TOAST_ENABLED";

	/********************************** 通知 ***************************************************************************************************/

	/** 通知的ID **/
	String NOTIFICATION_ID = "NOTIFICATION_ID";

	/** 通知的密钥 **/
	String NOTIFICATION_API_KEY = "NOTIFICATION_API_KEY";

	/** 通知的标题 **/
	String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";

	/** 通知的正文 **/
	String NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE";

	/** 通知的Url **/
	String NOTIFICATION_URI = "NOTIFICATION_URI";

	/********************************** intent动作 ***************************************************************************************************/

	/** 显示通知 **/
	String ACTION_SHOW_NOTIFICATION = "org.androidpn.client.SHOW_NOTIFICATION";

	/** 通知被点击 **/
	String ACTION_NOTIFICATION_CLICKED = "org.androidpn.client.NOTIFICATION_CLICKED";

	/** 清除通知 **/
	String ACTION_NOTIFICATION_CLEARED = "org.androidpn.client.NOTIFICATION_CLEARED";

}
