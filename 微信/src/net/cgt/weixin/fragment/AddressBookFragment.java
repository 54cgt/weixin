package net.cgt.weixin.fragment;

import http.common.SchemaDef;

import java.util.ArrayList;
import java.util.List;

import net.cgt.weixin.R;
import net.cgt.weixin.activity.UserDetailedInfoActivity;
import net.cgt.weixin.domain.User;
import net.cgt.weixin.utils.AppToast;
import net.cgt.weixin.utils.DensityUtil;
import net.cgt.weixin.utils.HandlerTypeUtils;
import net.cgt.weixin.utils.LogUtil;
import net.cgt.weixin.view.adapter.PinyinAdapter;
import net.cgt.weixin.view.manager.XmppManager;
import net.cgt.weixin.view.pinyin.AssortView;
import net.cgt.weixin.view.pinyin.AssortView.OnTouchAssortListener;

import org.jivesoftware.smack.Roster;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 通讯录
 * 
 * @author lijian-pc
 * @date 2014-11-27
 */
public class AddressBookFragment extends BaseFragment implements OnItemLongClickListener, OnChildClickListener {

	private static final String LOGTAG = LogUtil.makeLogTag(AddressBookFragment.class);

	/**
	 * 可扩展的ListView
	 */
	private ExpandableListView mElv_addressbook;
	/**
	 * 右侧拼音导航
	 */
	private AssortView mV_addressbook_right;
	/**
	 * 可扩展ListView的适配器
	 */
	private PinyinAdapter mAdpt_pingyin;
	/**
	 * 泡泡
	 */
	private PopupWindow popupWindow;
	/**
	 * 用户信息集合
	 */
	private List<User> mList_user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.cgt_fragment_addressbook, null);
		init(v);
		return v;
	}

	@Override
	protected void requset(int schemaDef) {
		switch (schemaDef) {
		case SchemaDef.PULL_ALL_FRIENDS:
			pullAllFriends();
			break;
		}
	}

	/**
	 * 拉取所有好友信息
	 */
	private void pullAllFriends() {
		Roster roster = XmppManager.getInstance().getConnection().getRoster();
		List<User> list = XmppManager.getInstance().getAllUser(roster);
		if (list.size() == 0) {
			mHandler.sendEmptyMessage(HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_EMPTY);
		} else {
			//			if (list.get(0).getUserAccount() == mList_user.get(0).getUserAccount()) {
			//				mHandler.sendEmptyMessage(HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_SAME);
			//			} else {
			mList_user.addAll(list);
			mHandler.sendEmptyMessage(HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_SUCCESS);
			//			}
		}
	}

	@Override
	protected void parseMessage(Message msg) {
		switch (msg.what) {
		case HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_SUCCESS:
			setData();
			break;
		case HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_EMPTY:
			break;

		default:
			break;
		}
	}

	private void setData() {
		if (mAdpt_pingyin == null) {
			mAdpt_pingyin = new PinyinAdapter(getActivity(), mList_user);
		} else {
			mAdpt_pingyin.notifyDataSetChanged();
		}
	}

	private void init(View v) {
		initView(v);
		initData();
	}

	private void initView(View v) {
		mElv_addressbook = (ExpandableListView) v.findViewById(R.id.cgt_elv_addressbook);
		mV_addressbook_right = (AssortView) v.findViewById(R.id.cgt_v_addressbook_right);

		mElv_addressbook.setOnItemLongClickListener(this);
		mElv_addressbook.setOnChildClickListener(this);
	}

	private void initData() {
		if (mList_user == null) {
			mList_user = new ArrayList<User>();
		}
		requestData(SchemaDef.PULL_ALL_FRIENDS);

		//		ArrayList<String> mItems = new ArrayList<String>();
		//		mItems.add("admin");
		//		mItems.add("lijian");
		//		mItems.add("lijian1");
		//		mItems.add("lijian2");
		//		mItems.add("lijian3");
		//		mItems.add("lijian4");
		//		mItems.add("lijian5");
		//		mItems.add("李建");
		//		mItems.add("李建1");
		//		mItems.add("李建2");
		//		mItems.add("李建3");
		//		mItems.add("李建4");
		//		mItems.add("李建5");
		//		mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
		//		mItems.add("Steve Jobs");
		//		mItems.add("Inheritance (The Inheritance Cycle)");
		//		mItems.add("11/22/63: A Novel");
		//		mItems.add("张三");
		//		mItems.add("The Hunger Games");
		//		mItems.add("李四");
		//		mItems.add("王五");
		//		mItems.add("赵六");
		//		mItems.add("The LEGO Ideas Book");
		//		mItems.add("田七");
		//		mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
		//		mItems.add("Catching Fire (The Second Book of the Hunger Games)");
		//		mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
		//		mItems.add("Death Comes to Pemberley");
		//		mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
		//		mItems.add("Steve Jobs");
		//		mItems.add("Inheritance (The Inheritance Cycle)");
		//		mItems.add("11/22/63: A Novel");
		//		mItems.add("The Hunger Games");
		//		mItems.add("The LEGO Ideas Book");
		//		mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
		//		mItems.add("Catching Fire (The Second Book of the Hunger Games)");
		//		mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
		//		mItems.add("Death Comes to Pemberley");
		//
		//		mItems.add("张三");
		//		mItems.add("李四");
		//		mItems.add("王五");
		//		mItems.add("赵六");
		//		mItems.add("田七");
		//		mItems.add("韩千叶");
		//		mItems.add("柳辰飞");
		//		mItems.add("夏舒征");
		//		mItems.add("慕容冲");
		//		mItems.add("萧合凰");
		//		mItems.add("阮停");
		//		mItems.add("西粼宿");
		//		mItems.add("孙祈钒");
		//		mItems.add("狄云");
		//		mItems.add("丁典");
		//		mItems.add("花错");
		//		mItems.add("顾西风");
		//		mItems.add("统月");
		//		mItems.add("苏普");
		//		mItems.add("江城子");
		//		mItems.add("柳长街");
		//		mItems.add("韦好客");
		//		mItems.add("袁冠南");
		//		mItems.add("燕七");
		//		mItems.add("金不换 ");
		//
		//		mItems.add("장삼");
		//		mItems.add("영희");
		//		mItems.add("王五");
		//		mItems.add("赵六");
		//		mItems.add("삼칠초");
		//		mItems.add("韩千叶");
		//		mItems.add("柳辰飞");
		//		mItems.add("夏舒征");
		//		mItems.add("慕容冲");
		//		mItems.add("萧合凰");
		//		mItems.add("阮停");
		//		mItems.add("서 노인 맑고 깨끗하다");
		//		mItems.add("孙祈钒");
		//		mItems.add("성 구름");
		//		mItems.add("丁典");
		//		mItems.add("잘못 썼다");
		//		mItems.add("顾西风");
		//		mItems.add("전부 달");
		//		mItems.add("소 보편적");
		//		mItems.add("장청 아들");
		//		mItems.add("버드나무 장거리");
		//		mItems.add("위 손님 접대를 좋아한다");
		//		mItems.add("袁冠南");
		//		mItems.add("연 일곱");
		//		mItems.add("먹");

		//		Collections.sort(mItems);
		//
		//		mList = new ArrayList<User>();
		//		for (int i = 0; i < mItems.size(); i++) {
		//			User user = new User();
		//			user.setUserAccount(mItems.get(i));
		//			if (i % 5 == 0) {
		//				user.setUserPhote(String.valueOf(R.drawable.icon));
		//			} else {
		//				user.setUserPhote(String.valueOf(R.drawable.user_picture));
		//			}
		//
		//			mList.add(user);
		//		}

		User user1 = new User();
		user1.setUserAccount(" 1新的朋友");
		user1.setUserPhote(String.valueOf(R.drawable.cgt_addressbook_newfriend));
		User user2 = new User();
		user2.setUserAccount(" 2群聊");
		user2.setUserPhote(String.valueOf(R.drawable.cgt_addressbook_groupchat));
		User user3 = new User();
		user3.setUserAccount(" 3标签");
		user3.setUserPhote(String.valueOf(R.drawable.cgt_addressbook_mark));
		User user4 = new User();
		user4.setUserAccount(" 4公众号");
		user4.setUserPhote(String.valueOf(R.drawable.cgt_addressbook_publicnumber));
		//		mList.add(user1);
		//		mList.add(user2);
		//		mList.add(user3);
		//		mList.add(user4);
		mList_user.add(user1);
		mList_user.add(user2);
		mList_user.add(user3);
		mList_user.add(user4);

		if (mAdpt_pingyin == null) {
			mAdpt_pingyin = new PinyinAdapter(getActivity(), mList_user);
		} else {
			mAdpt_pingyin.notifyDataSetChanged();
		}
		mElv_addressbook.setAdapter(mAdpt_pingyin);

		// 展开所有
		for (int i = 0, length = mAdpt_pingyin.getGroupCount(); i < length; i++) {
			mElv_addressbook.expandGroup(i);
		}

		// 字母按键回调
		mV_addressbook_right.setOnTouchAssortListener(new OnTouchAssortListener() {

			View layoutView = LayoutInflater.from(getActivity()).inflate(R.layout.cgt_layout_addressbook_middle_alert_dialog, null);
			TextView text = (TextView) layoutView.findViewById(R.id.cgt_tv_addressbook_content);

			@Override
			public void onTouchAssortListener(String str) {
				int index = mAdpt_pingyin.getAssort().getHashList().indexOfKey(str);
				if (index != -1) {
					mElv_addressbook.setSelectedGroup(index);
				}
				if (popupWindow != null) {
					text.setText(str);
				} else {
					popupWindow = new PopupWindow(layoutView, DensityUtil.dip2px(getActivity(), 80), DensityUtil.dip2px(getActivity(), 80), false);
					// 显示在Activity的根视图中心
					popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
				}
				text.setText(str);
			}

			@Override
			public void onTouchAssortUP() {
				if (popupWindow != null) {
					popupWindow.dismiss();
					popupWindow = null;
				}
			}
		});
	}

	@Override
	public void onStop() {
		if (popupWindow != null) {
			popupWindow.dismiss();
			popupWindow = null;
		}
		super.onStop();
	}

	@Override
	public void onDestroy() {
		if (popupWindow != null) {
			popupWindow.dismiss();
			popupWindow = null;
		}
		super.onDestroy();
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		if (groupPosition == 0) {
			switch (childPosition) {
			case 0:
				AppToast.getToast().show(R.string.text_addressbook_newFriend);
				break;
			case 1:
				AppToast.getToast().show(R.string.text_addressbook_groupChat);
				break;
			case 2:
				AppToast.getToast().show(R.string.text_addressbook_mark);
				break;
			case 3:
				AppToast.getToast().show(R.string.text_addressbook_publicNumber);
				break;
			default:
				break;
			}
		} else {
			Intent intent = new Intent();
			intent.setClass(getActivity(), UserDetailedInfoActivity.class);
			Bundle bundle = new Bundle();
			bundle.putParcelable("user", (User) mAdpt_pingyin.getChild(groupPosition, childPosition));
			intent.putExtras(bundle);
			startActivity(intent);
			AppToast.getToast().show(((User) mAdpt_pingyin.getChild(groupPosition, childPosition)).getUserAccount());
		}
		return false;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

		int groupPosition = (Integer) view.getTag(R.id.cgt_tv_addressbook_content);
		int childPosition = (Integer) view.getTag(R.id.cgt_tv_addressbook_group_item);

		if (childPosition != -1) {
			String userName = ((User) mAdpt_pingyin.getChild(groupPosition, childPosition)).getUserAccount();
			AppToast.getToast().show(userName);

			final Dialog dialog = new Dialog(getActivity(), R.style.cgt_dialog);
			View v = View.inflate(getActivity(), R.layout.cgt_layout_addressbook_item_alert_dialog, null);
			TextView mTv_item_alert_dialog_userName = (TextView) v.findViewById(R.id.cgt_tv_item_alert_dialog_userName);
			TextView mTv_item_alert_dialog_click = (TextView) v.findViewById(R.id.cgt_tv_item_alert_dialog_click);
			mTv_item_alert_dialog_userName.setText(userName);
			mTv_item_alert_dialog_click.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AppToast.getToast().show("设置标签及备注");

					dialog.cancel();
				}
			});

			dialog.setCancelable(true);
			dialog.setContentView(v);
			dialog.show();

			Window dialogWindow = dialog.getWindow();
			WindowManager m = getActivity().getWindowManager();
			Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
			WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
			// p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
			p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
			dialogWindow.setAttributes(p);
		}
		return true;//设置为true,长点击完后,消耗该事件
	}
}
