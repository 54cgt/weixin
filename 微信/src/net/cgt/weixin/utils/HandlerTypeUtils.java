package net.cgt.weixin.utils;

/**
 * Handler message 常量
 * 
 * @author lijian
 * @date 2014-11-23
 */
public interface HandlerTypeUtils {
	/**
	 * 网络异常
	 */
	public static final int WX_HANDLER_HTTP_EXCEPTION = 10;
	/**
	 * 加载数据成功
	 */
	public static final int WX_HANDLER_TYPE_LOAD_DATA_SUCCESS = 11;
	/**
	 * 加载数据失败
	 */
	public static final int WX_HANDLER_TYPE_LOAD_DATA_FAIL = 12;
	/**
	 * 加载数据空
	 */
	public static final int WX_HANDLER_TYPE_LOAD_DATA_EMPTY = 13;
	/**
	 * 加载数据相同
	 */
	public static final int WX_HANDLER_TYPE_LOAD_DATA_SAME = 14;
	/**
	 * 刷新界面
	 */
	public static final int WX_HANDLER_TYPE_REFRESH = 15;
}
