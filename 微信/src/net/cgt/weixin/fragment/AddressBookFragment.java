package net.cgt.weixin.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.cgt.weixin.GlobalParams;
import net.cgt.weixin.R;
import net.cgt.weixin.domain.User;
import net.cgt.weixin.view.adapter.AddressBookAdapter;
import net.cgt.weixin.view.widget.IndexableListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 通讯录
 * 
 * @author lijian-pc
 * 
 */
public class AddressBookFragment extends BaseFragment {

	private IndexableListView mIlv_addressbook;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.cgt_fragment_addressbook, null);
		init(v);
		return v;
	}

	private void init(View v) {
		initView(v);
		initData();
	}

	private void initView(View v) {
		mIlv_addressbook = (IndexableListView) v
				.findViewById(R.id.cgt_ilv_addressbook);
	}

	private void initData() {
		ArrayList<String> mItems = new ArrayList<String>();
		mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
		mItems.add("Steve Jobs");
		mItems.add("Inheritance (The Inheritance Cycle)");
		mItems.add("11/22/63: A Novel");
		mItems.add("张三");
		mItems.add("The Hunger Games");
		mItems.add("李四");
		mItems.add("王五");
		mItems.add("赵六");
		mItems.add("The LEGO Ideas Book");
		mItems.add("田七");
		mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
		mItems.add("Catching Fire (The Second Book of the Hunger Games)");
		mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
		mItems.add("Death Comes to Pemberley");
		mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
		mItems.add("Steve Jobs");
		mItems.add("Inheritance (The Inheritance Cycle)");
		mItems.add("11/22/63: A Novel");
		mItems.add("The Hunger Games");
		mItems.add("The LEGO Ideas Book");
		mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
		mItems.add("Catching Fire (The Second Book of the Hunger Games)");
		mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
		mItems.add("Death Comes to Pemberley");

		mItems.add("张三");
		mItems.add("李四");
		mItems.add("王五");
		mItems.add("赵六");
		mItems.add("田七");
		mItems.add("韩千叶");
		mItems.add("柳辰飞");
		mItems.add("夏舒征");
		mItems.add("慕容冲");
		mItems.add("萧合凰");
		mItems.add("阮停");
		mItems.add("西粼宿");
		mItems.add("孙祈钒");
		mItems.add("狄云");
		mItems.add("丁典");
		mItems.add("花错");
		mItems.add("顾西风");
		mItems.add("统月");
		mItems.add("苏普");
		mItems.add("江城子");
		mItems.add("柳长街");
		mItems.add("韦好客");
		mItems.add("袁冠南");
		mItems.add("燕七");
		mItems.add("金不换 ");

		mItems.add("장삼");
		mItems.add("영희");
		mItems.add("王五");
		mItems.add("赵六");
		mItems.add("삼칠초");
		mItems.add("韩千叶");
		mItems.add("柳辰飞");
		mItems.add("夏舒征");
		mItems.add("慕容冲");
		mItems.add("萧合凰");
		mItems.add("阮停");
		mItems.add("서 노인 맑고 깨끗하다");
		mItems.add("孙祈钒");
		mItems.add("성 구름");
		mItems.add("丁典");
		mItems.add("잘못 썼다");
		mItems.add("顾西风");
		mItems.add("전부 달");
		mItems.add("소 보편적");
		mItems.add("장청 아들");
		mItems.add("버드나무 장거리");
		mItems.add("위 손님 접대를 좋아한다");
		mItems.add("袁冠南");
		mItems.add("연 일곱");
		mItems.add("먹");

		Collections.sort(mItems);

		List<User> mList = new ArrayList<User>();
		for (int i = 0; i < mItems.size(); i++) {
			User user = new User();
			user.setUserAccount(mItems.get(i));
			mList.add(user);
		}

		AddressBookAdapter addressBookAdapter = null;
		if (addressBookAdapter == null) {
			addressBookAdapter = new AddressBookAdapter(GlobalParams.activity,
					mList);
		} else {
			addressBookAdapter.notifyDataSetChanged();
		}
		mIlv_addressbook.setAdapter(addressBookAdapter);
		mIlv_addressbook.setFastScrollEnabled(true);// 设置快速滑动
	}
}
