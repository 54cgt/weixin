package net.cgt.weixin.control;

import net.cgt.weixin.utils.LogUtil;

/**
 * 回调接口类
 * 
 * @author lijian
 * @date 2015-01-11 19:26:32
 */
public class ViewCallBack {

	private static final String LOGTAG = LogUtil.makeLogTag(ViewCallBack.class);

	//单例
	private static ViewCallBack instance = null;

	private ViewCallBack() {
	}

	public static ViewCallBack getInstance() {
		if (instance == null) {
			instance = new ViewCallBack();
		}
		return instance;
	}

	/********************************** 刷新回调 **********************************/

	public interface onRefreshCallBack {
		/**
		 * 刷新数据
		 */
		void onRefreshCallBack();
	}
}
