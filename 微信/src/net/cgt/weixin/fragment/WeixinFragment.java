package net.cgt.weixin.fragment;

import java.util.ArrayList;
import java.util.List;

import net.cgt.weixin.GlobalParams;
import net.cgt.weixin.R;
import net.cgt.weixin.domain.User;
import net.cgt.weixin.view.adapter.RosterAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.cgt_fragment_weixin, null);
		init(v);
		return v;
	}

	private void init(View v) {
		initView(v);
		initData();
	}

	private void initView(View v) {
		mLv_weixin = (ListView) v.findViewById(R.id.cgt_lv_weixin);
	}

	private void initData() {
		// 假数据
		List<User> mList_user = new ArrayList<User>();
		for (int i = 0; i < 100; i++) {
			User user = new User();
//			user.setUserPhote("R.drawable.icon");
			user.setUserAccount("test" + i);
			user.setPersonalizedSignature("人潮人海,有你有我");
			user.setDate("11月26日");
			mList_user.add(user);
		}

		if (mRosterAdapter == null) {
			mRosterAdapter = new RosterAdapter(GlobalParams.activity, mList_user);
			mLv_weixin.setAdapter(mRosterAdapter);
		} else {
			mRosterAdapter.notifyDataSetChanged();
		}
	}
}
