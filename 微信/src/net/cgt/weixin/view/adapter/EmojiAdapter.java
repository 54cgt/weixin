package net.cgt.weixin.view.adapter;

import java.util.List;

import net.cgt.weixin.R;
import net.cgt.weixin.domain.ChatEmoji;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * 表情填充器
 * 
 * @author lijian
 * @date 2014-12-13 17:59:13
 */
public class EmojiAdapter extends BaseAdapter {

	/**
	 * 上下文
	 */
	private Context mContext;
	/**
	 * 表情集合
	 */
	private List<ChatEmoji> mList_chatEmoji;

	public EmojiAdapter(Context context, List<ChatEmoji> list) {
		this.mContext = context;
		this.mList_chatEmoji = list;
	}

	@Override
	public int getCount() {
		if (mList_chatEmoji == null) {
			return 0;
		}
		return mList_chatEmoji.size();
	}

	@Override
	public Object getItem(int position) {
		return mList_chatEmoji.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatEmoji emoji = mList_chatEmoji.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.cgt_layout_chat_emoji_item, null);
			viewHolder.mIv_emoji = (ImageView) convertView.findViewById(R.id.cgt_iv_chat_item_emoji);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (emoji.getId() == R.drawable.cgt_selector_chat_emoji_del_bg) {
//			convertView.setBackgroundDrawable(null);
			viewHolder.mIv_emoji.setImageResource(emoji.getId());
		} else if (TextUtils.isEmpty(emoji.getDescription())) {
			convertView.setBackgroundDrawable(null);
			viewHolder.mIv_emoji.setImageDrawable(null);
		} else {
			viewHolder.mIv_emoji.setTag(emoji);
			viewHolder.mIv_emoji.setImageResource(emoji.getId());
		}

		return convertView;
	}

	class ViewHolder {
		ImageView mIv_emoji;
	}
}