package net.cgt.weixin.view.adapter;

import java.util.List;

import net.cgt.weixin.R;
import net.cgt.weixin.domain.User;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 用户列表适配器
 * 
 * @author lijian
 * 
 */
public class RosterAdapter extends BaseAdapter {

	private Context mContext;
	private List<User> mList;

	public RosterAdapter(Context context, List<User> list) {
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
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.cgt_layout_roster, null);
			holder.userPic = (ImageView) convertView.findViewById(R.id.cgt_iv_userPic);
			holder.userName = (TextView) convertView.findViewById(R.id.cgt_tv_userName);
			holder.personalizedSignature = (TextView) convertView.findViewById(R.id.cgt_tv_personalizedSignature);
			holder.status = (TextView) convertView.findViewById(R.id.cgt_tv_status);
			convertView.setTag(holder);
		}

		holder.userName.setText(mList.get(position).getUserAccount());
		holder.status.setText(mList.get(position).getUserPhote());

		return convertView;
	}

	class ViewHolder {
		/** 用户头像 **/
		ImageView userPic;
		/** 用户名 **/
		TextView userName;
		/** 个性签名 **/
		TextView personalizedSignature;
		/** 在线状态 **/
		TextView status;
	}

}
