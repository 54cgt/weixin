package net.cgt.weixin.utils;

import net.cgt.weixin.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class AppDialog extends Dialog {

	private Context mContext;

	protected AppDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.mContext = context;
	}

	public AppDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}

	public AppDialog(Context context) {
		super(context);
		this.mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);
		init();
	}

	private void init() {
		initView();
		initData();
	}

	private void initView() {
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

}
