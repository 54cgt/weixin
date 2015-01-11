package net.cgt.weixin.utils.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 获取app线程
 * 
 * @author lijian
 * @date 2015-01-11 17:06:13
 */
public class ThreadPoolUtil {

	private static ExecutorService instance = null;

	private ThreadPoolUtil() {
	}

	public static ExecutorService getInstance() {
		if (instance == null)
			instance = Executors.newCachedThreadPool();
		return instance;
	}
}
