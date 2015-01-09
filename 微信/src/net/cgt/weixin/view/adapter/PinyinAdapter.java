package net.cgt.weixin.view.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.cgt.weixin.R;
import net.cgt.weixin.domain.User;
import net.cgt.weixin.domain.pinyin.LanguageComparator_CN;
import net.cgt.weixin.view.pinyin.AssortPinyinList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PinyinAdapter extends BaseExpandableListAdapter {
	/**
	 * 要被排序的集合
	 */
	private List<User> mList;

	private AssortPinyinList mAssortPinyinList = new AssortPinyinList();

	private Context context;

	private LayoutInflater inflater;
	// 中文排序
	private LanguageComparator_CN<String> cnSort_str = new LanguageComparator_CN<String>();
	private LanguageComparator_CN<User> cnSort_user = new LanguageComparator_CN<User>();

	public PinyinAdapter(Context context, List<User> userList) {
		super();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mList = userList;
		if (userList == null) {
			userList = new ArrayList<User>();
		}

		//		long time = System.currentTimeMillis();
		// 排序
		sort();
		//		Toast.makeText(context, String.valueOf(System.currentTimeMillis() - time), 1).show();
	}

	private void sort() {
		// 分类
		for (int i = 0; i < mList.size(); i++) {
			mAssortPinyinList.getHashList().add(mList.get(i));
		}
		mAssortPinyinList.getHashList().sortKeyComparator(cnSort_str);
		for (int i = 0, length = mAssortPinyinList.getHashList().size(); i < length; i++) {
			Collections.sort((mAssortPinyinList.getHashList().getValueListThroughIndex(i)), cnSort_user);
		}
	}

	@Override
	public int getGroupCount() {
		return mAssortPinyinList.getHashList().size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		//		if (groupPosition == 0) {
		//			return 4;
		//		}
		return mAssortPinyinList.getHashList().getValueListThroughIndex(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		//		if (groupPosition == 0) {
		//			List<User> mList_top = new ArrayList<User>();
		//			User user1 = new User();
		//			user1.setUserAccount(context.getResources().getString(R.string.text_addressbook_newFriend));
		//			user1.setUserPhote(String.valueOf(R.drawable.cgt_addressbook_newfriend));
		//			User user2 = new User();
		//			user2.setUserAccount(context.getResources().getString(R.string.text_addressbook_newFriend));
		//			user2.setUserPhote(String.valueOf(R.drawable.cgt_addressbook_newfriend));
		//			User user3 = new User();
		//			user3.setUserAccount(context.getResources().getString(R.string.text_addressbook_newFriend));
		//			user3.setUserPhote(String.valueOf(R.drawable.cgt_addressbook_newfriend));
		//			User user4 = new User();
		//			user4.setUserAccount(context.getResources().getString(R.string.text_addressbook_newFriend));
		//			user4.setUserPhote(String.valueOf(R.drawable.cgt_addressbook_newfriend));
		//			mList_top.add(user1);
		//			mList_top.add(user2);
		//			mList_top.add(user3);
		//			mList_top.add(user4);
		//			return mList_top;
		//		}
		return mAssortPinyinList.getHashList().getValueListThroughIndex(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		//		if (groupPosition == 0) {
		//			return ((List<User>) getGroup(0)).get(childPosition);
		//		}
		return mAssortPinyinList.getHashList().getValueThroughIndexAndKey(groupPosition, childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		//无论Child是否有相同的ID都指向同一个对象
		//		这样说把. 通知ListView, 你id是唯一的. 不会重复,
		//		默认这个方法是返回false的, 需要返回true.  这样, 才能使用ListView的
		//		CHOICE_MODE_MULTIPLE, CHOICE_MODE_SINGLE, 通过getCheckedItemIds方法才能正常获取用户选中的选项的id, LZ可以试试看

		//		组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
		//	      　　返回值
		//		返回一个Boolean类型的值，如果为TRUE，意味着相同的ID永远引用相同的对象。
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.cgt_layout_addressbook_group_item, null);
			// 禁止伸展
			convertView.setClickable(true);//设置是否可以点击伸展
		}
		TextView mTv_addressbook_group_item = (TextView) convertView.findViewById(R.id.cgt_tv_addressbook_group_item);
		if (groupPosition == 0) {
			mTv_addressbook_group_item.setText("↑");
			mTv_addressbook_group_item.setVisibility(View.GONE);
		} else {
			mTv_addressbook_group_item.setText(mAssortPinyinList.getFirstChar(mAssortPinyinList.getHashList().getValueThroughIndexAndKey(groupPosition, 0)));
			mTv_addressbook_group_item.setVisibility(View.VISIBLE);
		}

		convertView.setTag(R.id.cgt_tv_addressbook_content, groupPosition);
		convertView.setTag(R.id.cgt_tv_addressbook_group_item, -1);

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.cgt_layout_addressbook_item, null);
		}
		TextView mTv_userName = (TextView) convertView.findViewById(R.id.cgt_tv_userName);
		ImageView mIv_userPhoto = (ImageView) convertView.findViewById(R.id.cgt_iv_userPhoto);
		View mV_line = convertView.findViewById(R.id.cgt_v_line);
		if (groupPosition == 0) {
			switch (childPosition) {
			case 0:
				mTv_userName.setText(context.getResources().getString(R.string.text_addressbook_newFriend));
				mIv_userPhoto.setImageResource(R.drawable.cgt_addressbook_newfriend);
				break;
			case 1:
				mTv_userName.setText(context.getResources().getString(R.string.text_addressbook_groupChat));
				mIv_userPhoto.setImageResource(R.drawable.cgt_addressbook_groupchat);
				break;
			case 2:
				mTv_userName.setText(context.getResources().getString(R.string.text_addressbook_mark));
				mIv_userPhoto.setImageResource(R.drawable.cgt_addressbook_mark);
				break;
			case 3:
				mTv_userName.setText(context.getResources().getString(R.string.text_addressbook_publicNumber));
				mIv_userPhoto.setImageResource(R.drawable.cgt_addressbook_publicnumber);
				break;

			default:
				break;
			}
		} else {
			mTv_userName.setText(mAssortPinyinList.getHashList().getValueThroughIndexAndKey(groupPosition, childPosition).getUserAccount());
			String userPhote = mAssortPinyinList.getHashList().getValueThroughIndexAndKey(groupPosition, childPosition).getUserPhote();
			if (userPhote == null) {
				mIv_userPhoto.setImageResource(R.drawable.user_picture);
			} else {
				mIv_userPhoto.setImageResource(Integer.parseInt(mAssortPinyinList.getHashList().getValueThroughIndexAndKey(groupPosition, childPosition).getUserPhote()));
			}
		}
		if (isLastChild) {
			mV_line.setVisibility(View.GONE);
		} else {
			mV_line.setVisibility(View.VISIBLE);
		}

		convertView.setTag(R.id.cgt_tv_addressbook_content, groupPosition);
		convertView.setTag(R.id.cgt_tv_addressbook_group_item, childPosition);

		return convertView;
	}

	/**
	 * 是否选中指定位置上的子元素。
	 * 
	 * 参数
	 * 
	 * groupPosition 组位置（该组内部含有这个子元素）
	 * 
	 * childPosition 子元素位置
	 * 
	 * 返回值
	 * 
	 * 是否选中子元素
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public AssortPinyinList getAssort() {
		return mAssortPinyinList;
	}
}
