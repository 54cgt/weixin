package net.cgt.weixin.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import net.cgt.weixin.R;
import net.cgt.weixin.utils.L;
import net.cgt.weixin.utils.LogUtil;

/**
 * 自定义进度对话框
 * 
 * @author lijian
 * @date 2015-01-11 18:31:59
 */
public class WXProgressDialog {

	private static final String LOGTAG = LogUtil.makeLogTag(WXProgressDialog.class);

	private Context mContext;
	private ProgressDialog dialog;

	public WXProgressDialog(Context context) {
		this.mContext = context;
		initDialog();
	}

	/**
	 * 得到进度对话框
	 * 
	 * @return
	 */
	public ProgressDialog getDialog() {
		return dialog;
	}

	/**
	 * 初始化进度对话框
	 */
	private void initDialog() {
		dialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//转圈圈的进度条
		dialog.setCancelable(false);//不可被取消
		dialog.setMessage(mContext.getString(R.string.text_NetProgressDialogMsg));
	}

	/**
	 * 显示进度对话框
	 */
	public void show() {
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	/**
	 * 设置进度对话框的消息
	 * 
	 * @param message
	 */
	public void setContent(CharSequence message) {
		dialog.setMessage(message);
	}

	/**
	 * 取消进度对话框
	 */
	public void cancel() {
		if (dialog == null) {
			L.i(LOGTAG, "dialog==null");
			return;
		}
		if (dialog.isShowing()) {
			dialog.cancel();
		}
	}

	/**
	 * 返回进度对话框当前显示状态
	 * 
	 * @return
	 */
	public boolean isShowing() {
		if (dialog == null) {
			L.i(LOGTAG, "dialog==null");
			return false;
		}
		return dialog.isShowing();
	}
}
