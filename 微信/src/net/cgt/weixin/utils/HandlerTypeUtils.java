package net.cgt.weixin.utils;

/**
 * Handler message 常量
 * 
 * @author lijian
 * @date 2014-11-23
 */
public interface HandlerTypeUtils {
	/**
	 * 加载数据成功
	 */
	public static final int WX_HANDLER_TYPE_LOAD_DATA_SUCCESS = 10;
	/**
	 * 加载数据失败
	 */
	public static final int WX_HANDLER_TYPE_LOAD_DATA_FAIL = 11;
	/**
	 * 加载数据相同
	 */
	public static final int WX_HANDLER_TYPE_LOAD_DATA_SAME = 12;
	/**
	 * 网络异常
	 */
	public static final int WX_HANDLER_FAIL_HTTP_EXCEPTION = 13;
}
