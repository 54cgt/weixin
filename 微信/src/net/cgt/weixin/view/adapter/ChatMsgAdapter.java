package net.cgt.weixin.view.adapter;

import java.util.List;

import net.cgt.weixin.R;
import net.cgt.weixin.domain.ChatMsgEntity;
import net.cgt.weixin.utils.AppUtil;
import net.cgt.weixin.utils.FaceConversionUtil;
import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 聊天消息适配器
 * 
 * @author lijian
 * @date 2014-12-29 22:31:34
 */
public class ChatMsgAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private List<ChatMsgEntity> mList;

	public ChatMsgAdapter(Context context, List<ChatMsgEntity> list) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		this.mList = list;
	}

	@Override
	public int getCount() {
		if (mList != null) {
			return mList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (mList != null) {
			return mList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (mList != null) {
			return position;
		}
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		ChatMsgEntity entity = mList.get(position);
		if (entity.isMeMsg()) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatMsgEntity entity = mList.get(position);

		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (entity.isMeMsg()) {
				convertView = mInflater.inflate(R.layout.cgt_layout_chat_msg_text_right, null);
			} else {
				convertView = mInflater.inflate(R.layout.cgt_layout_chat_msg_text_left, null);
			}
			viewHolder = getViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		setData(viewHolder, entity);
		return convertView;
	}

	private void setData(ViewHolder viewHolder, ChatMsgEntity entity) {
		switch (entity.getMsgType()) {
		case ChatMsgEntity.MSGTYPE_TEXT:
			viewHolder.mTv_time.setText(AppUtil.formatTime(entity.getTime()));
			viewHolder.mIv_userImg.setImageResource(entity.getUserImg());
			SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, entity.getTextMsg());
			viewHolder.mTv_msg_text.setText(spannableString);

			viewHolder.mTv_msg_text.setVisibility(View.VISIBLE);
			if (entity.isShowTime()) {
				viewHolder.mTv_time.setVisibility(View.VISIBLE);
			} else {
				viewHolder.mTv_time.setVisibility(View.GONE);
			}
			break;
		case ChatMsgEntity.MSGTYPE_IMG:

			break;
		case ChatMsgEntity.MSGTYPE_VOICE:

			break;
		case ChatMsgEntity.MSGTYPE_SHARE:

			break;
		case ChatMsgEntity.MSGTYPE_VIDEO:

			break;

		default:
			break;
		}
	}

	private ViewHolder getViewHolder(View v) {
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.mTv_time = (TextView) v.findViewById(R.id.cgt_tv_chat_msg_time);
		viewHolder.mIv_userImg = (ImageView) v.findViewById(R.id.cgt_iv_chat_userImg);
		viewHolder.mTv_msg_text = (TextView) v.findViewById(R.id.cgt_tv_chat_msg_text);
		return viewHolder;
	}

	class ViewHolder {
		/** 消息发送时间 **/
		TextView mTv_time;
		/** 头像 **/
		ImageView mIv_userImg;
		/** 文本消息 **/
		TextView mTv_msg_text;
	}

}
