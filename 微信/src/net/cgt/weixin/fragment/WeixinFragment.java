package net.cgt.weixin.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.cgt.weixin.GlobalParams;
import net.cgt.weixin.R;
import net.cgt.weixin.domain.User;
import net.cgt.weixin.utils.AppToast;
import net.cgt.weixin.view.adapter.RosterAdapter;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

/**
 * 微信
 * 
 * @author lijian
 * @date 2014-11-22
 * 
 */
public class WeixinFragment extends BaseFragment {

	private ListView mLv_weixin;
	private RosterAdapter mRosterAdapter;
	private View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.cgt_fragment_weixin, null);
		init();
		return v;
	}

	private void init() {
		initView();
		initData();
	}

	private void initView() {
//		ActionBar actionBar = GlobalParams.activity.getActionBar();
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		setOverflowShowingAlways();

		mLv_weixin = (ListView) v.findViewById(R.id.cgt_lv_weixin);
	}

	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration
					.get(GlobalParams.activity);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initData() {
		// 假数据
		List<User> mList_user = new ArrayList<User>();
		for (int i = 0; i < 100; i++) {
			User user = new User();
			user.setUserAccount("test" + i);
			user.setUserPhote("R.drawable.icon");
			mList_user.add(user);
		}

		if (mRosterAdapter == null) {
			mRosterAdapter = new RosterAdapter(GlobalParams.activity,
					mList_user);
			mLv_weixin.setAdapter(mRosterAdapter);
		} else {
			mRosterAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.cgt_menu_weixin, menu);
		MenuItem searchItem = menu.findItem(R.id.menu_weixin_search);
		SearchView searchView = (SearchView) searchItem.getActionView();
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

}
