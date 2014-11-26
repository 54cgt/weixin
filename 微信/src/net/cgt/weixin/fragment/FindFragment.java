package net.cgt.weixin.fragment;

import net.cgt.weixin.R;
import net.cgt.weixin.utils.AppToast;
import net.cgt.weixin.utils.L;
import net.cgt.weixin.utils.LogUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 发现
 * 
 * @author lijian
 * @date 2014-11-26
 */
public class FindFragment extends BaseFragment implements OnClickListener {

	private static final String LOGTAG = LogUtil.makeLogTag(FindFragment.class);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.cgt_fragment_find, null);
		init(v);
		return v;
	}

	private void init(View v) {
		initView(v);
		initData();
	}

	private void initView(View v) {
		LinearLayout mLl_friends = (LinearLayout) v.findViewById(R.id.cgt_ll_find_friends);
		LinearLayout mLl_sweep = (LinearLayout) v.findViewById(R.id.cgt_ll_find_sweep);
		LinearLayout mLl_shake = (LinearLayout) v.findViewById(R.id.cgt_ll_find_shake);
		LinearLayout mLl_nearbyPeople = (LinearLayout) v.findViewById(R.id.cgt_ll_find_nearbyPeople);
		LinearLayout mLl_driftBottle = (LinearLayout) v.findViewById(R.id.cgt_ll_find_driftBottle);
		LinearLayout mLl_shopping = (LinearLayout) v.findViewById(R.id.cgt_ll_find_shopping);
		LinearLayout mLl_game = (LinearLayout) v.findViewById(R.id.cgt_ll_find_game);
		mLl_friends.setOnClickListener(this);
		mLl_sweep.setOnClickListener(this);
		mLl_shake.setOnClickListener(this);
		mLl_nearbyPeople.setOnClickListener(this);
		mLl_driftBottle.setOnClickListener(this);
		mLl_shopping.setOnClickListener(this);
		mLl_game.setOnClickListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cgt_ll_find_friends:
			AppToast.getToast().show(R.string.text_find_friends);
			L.i(LOGTAG, "朋友圈");
			break;
		case R.id.cgt_ll_find_sweep:
			AppToast.getToast().show(R.string.text_find_sweep);
			L.i(LOGTAG, "扫一扫");
			break;
		case R.id.cgt_ll_find_shake:
			AppToast.getToast().show(R.string.text_find_shake);
			L.i(LOGTAG, "摇一摇");
			break;
		case R.id.cgt_ll_find_nearbyPeople:
			AppToast.getToast().show(R.string.text_find_nearbyPeople);
			L.i(LOGTAG, "附近的人");
			break;
		case R.id.cgt_ll_find_driftBottle:
			AppToast.getToast().show(R.string.text_find_driftBottle);
			L.i(LOGTAG, "漂流瓶");
			break;
		case R.id.cgt_ll_find_shopping:
			AppToast.getToast().show(R.string.text_find_shopping);
			L.i(LOGTAG, "购物");
			break;
		case R.id.cgt_ll_find_game:
			AppToast.getToast().show(R.string.text_find_game);
			L.i(LOGTAG, "游戏");
			break;

		default:
			break;
		}
	}
}
