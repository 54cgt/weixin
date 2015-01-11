package net.cgt.weixin.view.load;

import net.cgt.weixin.R;
import net.cgt.weixin.control.ViewCallBack.onRefreshCallBack;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 加载中...
 * 
 * @author lijian
 * @date 2015-01-11 19:44:26
 */
public class LoadingView implements OnClickListener {

	private Activity mActivity;
	/** 回调 **/
	private onRefreshCallBack mCallBack;
	/** 是否显示中 **/
	private boolean isFlag;
	/** 控件根节点 **/
	private LinearLayout mLl_root;
	/** 进度圈 **/
	private ProgressBar mPb_progress;
	/** 网络错误 **/
	private ImageButton mIb_httpError;
	/** 加载数据空 **/
	private ImageButton mIb_dataEmpty;
	/** 描述信息 **/
	private TextView mTv_msg;

	public LoadingView(Activity activity, onRefreshCallBack callBack) {
		this.mActivity = activity;
		this.mCallBack = callBack;
		init();
	}

	private Context getContext() {
		return this.mActivity;
	}

	private void init() {
		mLl_root = (LinearLayout) mActivity.findViewById(R.id.cgt_ll_loading_root);
		if (mLl_root == null) {
			isFlag = false;
			return;
		}
		mPb_progress = (ProgressBar) mActivity.findViewById(R.id.cgt_pb_loading_progress);
		mIb_httpError = (ImageButton) mActivity.findViewById(R.id.cgt_ib_loading_httpError);
		mIb_dataEmpty = (ImageButton) mActivity.findViewById(R.id.cgt_ib_loading_dataEmpty);
		mTv_msg = (TextView) mActivity.findViewById(R.id.cgt_tv_loading_msg);

		mIb_httpError.setOnClickListener(this);
		mIb_dataEmpty.setOnClickListener(this);
	}

	/**
	 * 加载中...
	 * 
	 * @param str 要显示的文本(为空时默认显示：加载中……)
	 */
	public void showWaiting(CharSequence str) {
		if (!isFlag) {
			return;
		}
		if (!TextUtils.isEmpty(str)) {
			mTv_msg.setText(str);
		} else {
			mTv_msg.setText(mActivity.getResources().getString(R.string.text_NetProgressDialogMsg));
		}
		mTv_msg.setTextColor(getContext().getResources().getColor(R.color.color_45c01a));
		mPb_progress.setVisibility(View.VISIBLE);
		mIb_httpError.setVisibility(View.GONE);
		mIb_dataEmpty.setVisibility(View.GONE);

		mLl_root.setVisibility(View.VISIBLE);
	}

	/**
	 * 加载中...
	 * 
	 * @param resid 要显示的文本资源id
	 */
	public void showWaiting(int resid) {
		String msg = getContext().getResources().getString(resid);
		showWaiting(msg);
	}

	/**
	 * 加载数据错误
	 * 
	 * @param str 要显示的文本(为空时默认显示：网络异常!)
	 */
	public void showError(String str) {
		if (!isFlag) {
			return;
		}
		if (!TextUtils.isEmpty(str)) {
			mTv_msg.setText(str);
		} else {
			mTv_msg.setText(mActivity.getResources().getString(R.string.text_NetRequest_dataError));
		}
		mTv_msg.setTextColor(getContext().getResources().getColor(R.color.color_45c01a));
		mPb_progress.setVisibility(View.GONE);
		mIb_httpError.setVisibility(View.VISIBLE);
		mIb_dataEmpty.setVisibility(View.GONE);

		mLl_root.setVisibility(View.VISIBLE);
	}

	/**
	 * 加载数据错误
	 * 
	 * @param resid 要显示的文本资源id
	 */
	public void showError(int resid) {
		String msg = getContext().getResources().getString(resid);
		showError(msg);
	}

	/**
	 * 加载数据为空
	 * 
	 * @param str 要显示的文本(为空时默认显示：数据为空!)
	 */
	public void showEmpty(String str) {
		if (!isFlag) {
			return;
		}
		if (!TextUtils.isEmpty(str)) {
			mTv_msg.setText(str);
		} else {
			mTv_msg.setText(mActivity.getResources().getString(R.string.text_NetRequest_dataEmpty));
		}
		mTv_msg.setTextColor(getContext().getResources().getColor(R.color.color_45c01a));
		mPb_progress.setVisibility(View.GONE);
		mIb_httpError.setVisibility(View.GONE);
		mIb_dataEmpty.setVisibility(View.VISIBLE);

		mLl_root.setVisibility(View.VISIBLE);
	}

	/**
	 * 加载数据为空
	 * 
	 * @param resid 要显示的文本资源id
	 */
	public void showEmpty(int resid) {
		String msg = getContext().getResources().getString(resid);
		showError(msg);
	}

	/**
	 * 读取结束
	 */
	public void loadOver() {
		if (!isFlag) {
			return;
		}
		mLl_root.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		if (mCallBack != null) {
			mCallBack.onRefreshCallBack();
		}
	}
}
