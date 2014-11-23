package net.cgt.weixin.activity;

import net.cgt.weixin.Constants;
import net.cgt.weixin.GlobalParams;
import net.cgt.weixin.R;
import net.cgt.weixin.factory.FragmentFactory;
import net.cgt.weixin.utils.L;
import net.cgt.weixin.utils.SpUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 主控制界面
 * 
 * @author lijian
 * 
 */
@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnCheckedChangeListener {

	private static final String TAG = "MainActivity";
	private FragmentManager mFragmentManager;
	private RadioGroup mRg_tab;
	private SpUtil sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (!GlobalParams.ISLOGIN) {
			L.d(TAG, "MainActivity--->isLogin");
			Intent intent = new Intent(this, LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			//			finish();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cgt_activity_main);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!GlobalParams.ISLOGIN) {
			finish();
		}
	}

	private void init() {
		initView();
		initData();
	}

	private void initView() {
		mFragmentManager = getFragmentManager();
		mRg_tab = (RadioGroup) findViewById(R.id.cgt_rg_tab);
		mRg_tab.setOnCheckedChangeListener(this);
	}

	private void initData() {
		sp = new SpUtil(this);
		int id = sp.getInt(Constants.PAGENUMBER, 1);

		showPreviousPage(id);
	}

	/**
	 * 显示上次退出时的页面
	 * 
	 * @param id
	 */
	private void showPreviousPage(int id) {
		RadioButton mRb_show = null;
		switch (id) {
		case 1:
			mRb_show = (RadioButton) findViewById(R.id.cgt_rb_weixin);
			break;
		case 2:
			mRb_show = (RadioButton) findViewById(R.id.cgt_rb_addressbook);
			break;
		case 3:
			mRb_show = (RadioButton) findViewById(R.id.cgt_rb_find);
			break;
		case 4:
			mRb_show = (RadioButton) findViewById(R.id.cgt_rb_me);
			break;
		default:
			mRb_show = (RadioButton) findViewById(R.id.cgt_rb_weixin);
			break;
		}
		mRb_show.setChecked(true);
		ShowFragmen(id);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int id = 1;
		switch (checkedId) {
		case R.id.cgt_rb_weixin:
			id = 1;
			break;
		case R.id.cgt_rb_addressbook:
			id = 2;
			break;
		case R.id.cgt_rb_find:
			id = 3;
			break;
		case R.id.cgt_rb_me:
			id = 4;
			break;
		default:
			id = 1;
			break;
		}
		sp.saveInt(Constants.PAGENUMBER, id);
		ShowFragmen(id);
	}

	/**
	 * 显示Fragment页面
	 * 
	 * @param id
	 */
	private void ShowFragmen(int id) {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		Fragment fragment = FragmentFactory.getInstanceByIndex(id);
		transaction.replace(R.id.cgt_fl_content, fragment);
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
