package net.cgt.weixin;

public interface ConstantValue {
	// public static final String AGENTERID="";
	/**
	 * 代理的id（子代理）
	 */
	String AGENTERID = "889931";
	/**
	 * 来源
	 */
	String SOURCE = "ivr";

	/**
	 * 加密算法
	 */
	String COMPRESS = "DES";
	/**
	 * 编码
	 */
	String ENCODING = "UTF-8";

	/**
	 * 子代理商的密钥(.so) JNI
	 */
	String AGENTER_PASSWORD = "9ab62a694d8bf6ced1fab6acd48d02f8";

	/**
	 * des加密用密钥
	 */
	String DES_PASSWORD = "9b2648fcdfbad80f";

	/**
	 * 服务器地址
	 */
	String LOTTERY_URI = "http://10.0.2.2:80/ZCWService/Entrance";// 10.0.2.2模拟器如果需要跟PC机通信127.0.0.1
	// String LOTTERY_URI = "http://192.168.1.100:8080/ZCWService/Entrance";// 10.0.2.2模拟器如果需要跟PC机通信127.0.0.1

	int VIEW_FIRST = 1;
	int VIEW_SECOND = 2;
	/**
	 * 购彩大厅
	 */
	int VIEW_HALL = 10;
	/**
	 * 购物车
	 */
	int VIEW_SHOPPING = 15;
	/**
	 * 登录
	 */
	int VIEW_USERLOGIN = 20;
	/**
	 * 双色球选号界面
	 */
	int VIEW_PLAYSSQ = 25;
	/**
	 * 追期和倍投的设置界面
	 */
	int VIEW_PREBET = 30;

	int SSQ = 118;
	/**
	 * 服务器处理结果描述：0成功
	 */
	String SUCCESS="0";

	/**
	 * XmlPullParser parser=Xml.newPullParser(); try { parser.setInput(is,ConstantValue.ENCODING);
	 * 
	 * int eventType = parser.getEventType(); String name=""; while(eventType!=XmlPullParser.END_DOCUMENT) { switch(eventType) { case XmlPullParser.START_TAG:
	 * name=parser.getName(); if("".equals(name)) {
	 * 
	 * } break; } eventType=parser.next(); }
	 * 
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 */

}
