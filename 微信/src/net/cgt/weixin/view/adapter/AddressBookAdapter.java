package net.cgt.weixin.view.adapter;

import java.util.List;

import net.cgt.weixin.R;
import net.cgt.weixin.domain.User;
import net.cgt.weixin.utils.StringMatcherUtils;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

/**
 * 通讯录适配器
 * 
 * @author lijian-pc
 * @date 2014-11-26
 */
public class AddressBookAdapter extends BaseAdapter implements SectionIndexer {

	private String mSections = "↑☆ABCDEFGHIJKLMNOPQRSTUVWXYZ#";
	
	private Context mContext;
	private List<User> mList;

	public AddressBookAdapter(Context context, List<User> list) {
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		} else {
			return mList.size();
		}
	}

	@Override
	public Object getItem(int position) {
		if (mList == null) {
			return null;
		} else {
			return mList.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		if (mList == null) {
			return 0;
		} else {
			return position;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = View.inflate(mContext, R.layout.cgt_layout_addressbook_item, null);
			holder = new ViewHolder();
			holder.userPhoto = (ImageView) convertView.findViewById(R.id.cgt_iv_userPhoto);
			holder.userName = (TextView) convertView.findViewById(R.id.cgt_tv_userName);
			convertView.setTag(holder);
		}
		
//		holder.userPhoto.setImageResource(Integer.parseInt(mList.get(position).getUserPhote()));
		holder.userPhoto.setImageResource(R.drawable.icon);
		holder.userName.setText(mList.get(position).getUserAccount());
		return convertView;
	}

	class ViewHolder {
		/** 用户头像 **/
		ImageView userPhoto;
		/** 用户名 **/
		TextView userName;
	}

	@Override
	public Object[] getSections() {
		String[] sections = new String[mSections.length()];
		for (int i = 0; i < mSections.length(); i++){
			sections[i] = String.valueOf(mSections.charAt(i));
		}
		return sections;
	}

	@Override
	public int getPositionForSection(int section) {
		// 如果当前部分没有item，则之前的部分将被选择
		for (int i = section; i >= 0; i--) {
			for (int j = 0; j < getCount(); j++) {
				if (i == 0) {
					// 数字部分
					for (int k = 0; k <= 9; k++) {// 1...9
						// 字符串第一个字符与1~9之间的数字进行匹配
						if (StringMatcherUtils.match(String.valueOf(((User)getItem(j)).getUserAccount().charAt(0)), String.valueOf(k)))
							return j;
					}
				} else {// A~Z
					if (StringMatcherUtils.match(String.valueOf(((User)getItem(j)).getUserAccount().charAt(0)), String.valueOf(mSections.charAt(i))))
						return j;
				}
			}
		}
		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}
}
