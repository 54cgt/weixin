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
import android.widget.TextView;
import android.widget.Toast;

public class PinyinAdapter extends BaseExpandableListAdapter {

	// 字符串
	private List<User> mList;

	private AssortPinyinList assort = new AssortPinyinList();

	private Context context;

	private LayoutInflater inflater;
	// 中文排序
	private LanguageComparator_CN cnSort = new LanguageComparator_CN();

	public PinyinAdapter(Context context, List<User> userList) {
		super();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mList = userList;
		if (userList == null) {
			userList = new ArrayList<User>();
		}

		long time = System.currentTimeMillis();
		// 排序
		sort();
		Toast.makeText(context, String.valueOf(System.currentTimeMillis() - time), 1).show();
	}

	private void sort() {
		// 分类
		for (int i = 0; i < mList.size(); i++) {
			assort.getHashList().add(mList.get(i).getUserAccount());
		}
		assort.getHashList().sortKeyComparator(cnSort);
		for (int i = 0, length = assort.getHashList().size(); i < length; i++) {
			Collections.sort((assort.getHashList().getValueListIndex(i)), cnSort);
		}

	}

	public Object getChild(int group, int child) {
		return assort.getHashList().getValueIndex(group, child);
	}

	public long getChildId(int group, int child) {
		return child;
	}

	public View getChildView(int group, int child, boolean arg2, View contentView, ViewGroup arg4) {
		if (contentView == null) {
			contentView = inflater.inflate(R.layout.cgt_layout_addressbook_item, null);
		}
		TextView textView = (TextView) contentView.findViewById(R.id.cgt_tv_userName);
		textView.setText(assort.getHashList().getValueIndex(group, child));
		return contentView;
	}

	public int getChildrenCount(int group) {
		return assort.getHashList().getValueListIndex(group).size();
	}

	public Object getGroup(int group) {
		return assort.getHashList().getValueListIndex(group);
	}

	public int getGroupCount() {
		return assort.getHashList().size();
	}

	public long getGroupId(int group) {
		return group;
	}

	public View getGroupView(int group, boolean arg1, View contentView, ViewGroup arg3) {
		if (contentView == null) {
			contentView = inflater.inflate(R.layout.cgt_layout_addressbook_group_item, null);
			contentView.setClickable(true);
		}
		TextView textView = (TextView) contentView.findViewById(R.id.name);
		textView.setText(assort.getFirstChar(assort.getHashList().getValueIndex(group, 0)));
		// 禁止伸展

		return contentView;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

	public AssortPinyinList getAssort() {
		return assort;
	}

}
