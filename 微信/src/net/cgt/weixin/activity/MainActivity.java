package net.cgt.weixin.activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.cgt.weixin.Constants;
import net.cgt.weixin.GlobalParams;
import net.cgt.weixin.R;
import net.cgt.weixin.factory.FragmentFactory;
import net.cgt.weixin.utils.AppToast;
import net.cgt.weixin.utils.L;
import net.cgt.weixin.utils.LogUtil;
import net.cgt.weixin.utils.SpUtil;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SearchView;

/**
 * 主控制界面
 * 
 * @author lijian
 * 
 */
@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnCheckedChangeListener {

	private static final String LOGTAG = LogUtil.makeLogTag(MainActivity.class);

	private FragmentManager mFragmentManager;
	private RadioGroup mRg_tab;
	private SpUtil sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (!GlobalParams.ISLOGIN) {
			L.d(LOGTAG, "MainActivity--->isLogin");
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
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayUseLogoEnabled(false);
		setOverflowShowingAlways();

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
	 * 通过反射得到Android的有无物理Menu键
	 */
	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		getMenuInflater().inflate(R.menu.cgt_menu_weixin, menu);
		MenuItem searchItem = menu.findItem(R.id.menu_weixin_search);
		SearchView searchView = (SearchView) searchItem.getActionView();
//		// 给SearchView添加展开/合并的监听
//		searchItem.setOnActionExpandListener(new OnActionExpandListener() {
//
//			@Override
//			public boolean onMenuItemActionExpand(MenuItem item) {
//				L.i(LOGTAG, "on expand");
//				AppToast.getToast().show("搜索展开");
//				return false;
//			}
//
//			@Override
//			public boolean onMenuItemActionCollapse(MenuItem item) {
//				L.i(LOGTAG, "on collapse");
//				AppToast.getToast().show("搜索合并");
//				return false;
//			}
//		});

		return super.onCreateOptionsMenu(menu);//true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			AppToast.getToast().show("返回上一页");
			// Intent upIntent = NavUtils.getParentActivityIntent(this);
			// if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
			//
			// TaskStackBuilder.create(this)
			// .addNextIntentWithParentStack(upIntent).startActivities();
			// } else {
			// upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// NavUtils.navigateUpTo(this, upIntent);
			// }

			break;
		case R.id.menu_weixin_groupChat:
			AppToast.getToast().show(R.string.text_menu_weixin_groupChat);
			break;
		case R.id.menu_weixin_addFriend:
			AppToast.getToast().show(R.string.text_menu_weixin_addFriend);
			break;
		case R.id.menu_weixin_sweep:
			AppToast.getToast().show(R.string.text_menu_weixin_sweep);
			break;
		case R.id.menu_weixin_feedback:
			AppToast.getToast().show(R.string.text_menu_weixin_feedback);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * overflow被展开的时候调用
	 * 
	 * @param featureId
	 * @param menu
	 * @return
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		//通过返回反射的方法将MenuBuilder的setOptionalIconsVisible变量设置为true
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}
}
