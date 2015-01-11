package net.cgt.weixin.fragment;

import net.cgt.weixin.R;
import net.cgt.weixin.control.ViewCallBack.onRefreshCallBack;
import net.cgt.weixin.utils.HandlerTypeUtils;
import net.cgt.weixin.utils.L;
import net.cgt.weixin.utils.LogUtil;
import net.cgt.weixin.utils.thread.ThreadPoolUtil;
import net.cgt.weixin.view.dialog.WXProgressDialog;
import net.cgt.weixin.view.load.LoadingView;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment基类
 * 
 * @author lijian
 * 
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class BaseFragment extends Fragment implements onRefreshCallBack {

	private static final String LOGTAG = LogUtil.makeLogTag(BaseFragment.class);

	private int layoutResID;
	private View view;
	private WXProgressDialog mWXProgressDialog;
	private LoadingView mV_loading;

	protected Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			parseMessage(msg);
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = null;
		if (layoutResID == 0) {
			view = inflater.inflate(R.layout.cgt_fragment_base, null);
			return view;
		} else {
			view = inflater.inflate(layoutResID, null);
		}
		return view;
	}

	protected void init() {
		mV_loading = new LoadingView(getActivity(), this);
	}

	/**
	 * 处理Handler Message
	 * 
	 * @param msg 消息包
	 */
	protected void parseMessage(Message msg) {
		cancelNetProgressDialog();
		if (msg == null) {
			return;
		}
		switch (msg.what) {
		case HandlerTypeUtils.WX_HANDLER_HTTP_EXCEPTION:
			L.i(LOGTAG, "WX_HANDLER_FAIL_HTTP_EXCEPTION");
			if (mV_loading != null) {
				mV_loading.showError("");
			}
			break;
		case HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_SUCCESS:
			L.i(LOGTAG, "WX_HANDLER_TYPE_LOAD_DATA_SUCCESS");
			if (mV_loading != null) {
				mV_loading.loadOver();
			}
			break;
		case HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_FAIL:
			L.i(LOGTAG, "WX_HANDLER_TYPE_LOAD_DATA_FAIL");
			if (mV_loading != null) {
				mV_loading.showError(String.valueOf(msg.obj));
			}
			break;
		case HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_EMPTY:
			L.i(LOGTAG, "WX_HANDLER_TYPE_LOAD_DATA_EMPTY");
			if (mV_loading != null) {
				mV_loading.showEmpty(R.string.text_NetRequest_dataEmpty);
			}
			break;
		case HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_SAME:
			L.i(LOGTAG, "WX_HANDLER_TYPE_LOAD_DATA_SAME");
			if (mV_loading != null) {
				mV_loading.loadOver();
			}
			break;
		case HandlerTypeUtils.WX_HANDLER_TYPE_REFRESH:
			if (mV_loading != null) {
				mV_loading.showEmpty("");
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 显示网络请求进度对话框
	 */
	public void showNetProgressDialog() {
		if (mWXProgressDialog == null) {
			mWXProgressDialog = new WXProgressDialog(getActivity());
		}
		if (mWXProgressDialog.isShowing()) {
			mWXProgressDialog.cancel();
		}
		mWXProgressDialog.show();
	}

	/**
	 * 取消网络请求进度对话框
	 */
	public void cancelNetProgressDialog() {
		if (null != mWXProgressDialog) {
			mWXProgressDialog.cancel();
		}
	}

	public void setContentView(int layoutResID) {
		this.layoutResID = layoutResID;
	}

	/**
	 * 请求网络数据(子类请求网络数据要调用的方法)
	 * 
	 * @param schemaDef 协议编号
	 */
	protected void requestData(final int schemaDef) {
		ThreadPoolUtil.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				requset(schemaDef);
			}
		});
	}

	/**
	 * 请求方法(子类具体要发送的请求在这里实现)
	 * 
	 * @param schemaDef 协议编号
	 */
	protected void requset(int schemaDef) {
		// TODO 这里是发送请求的具体参数
	}

	@Override
	public void onDestroy() {
		System.gc();
		super.onDestroy();
	}

	@Override
	public void onRefreshCallBack() {
		mHandler.sendEmptyMessage(HandlerTypeUtils.WX_HANDLER_TYPE_REFRESH);
	}
}
