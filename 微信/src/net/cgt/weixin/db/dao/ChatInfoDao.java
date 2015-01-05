package net.cgt.weixin.db.dao;

import java.util.ArrayList;
import java.util.List;

import net.cgt.weixin.db.ChatInfoDBOpenHelper;
import net.cgt.weixin.domain.ChatMsgEntity;
import net.cgt.weixin.utils.LogUtil;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 聊天信息的增删改查业务类
 */
public class ChatInfoDao {

	private static final String LOGTAG = LogUtil.makeLogTag(ChatInfoDao.class);

	private ChatInfoDBOpenHelper helper;

	public ChatInfoDao(Context context) {
		helper = new ChatInfoDBOpenHelper(context);
	}

	/**
	 * 添加一条聊天数据
	 * 
	 * @param entity 聊天数据bean
	 */
	public void add(ChatMsgEntity entity) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("time", String.valueOf(entity.getTime()));
		String is_show_time;
		if (entity.isShowTime()) {
			is_show_time = "1";
		} else {
			is_show_time = "0";
		}
		values.put("is_show_time", is_show_time);
		values.put("user_name", entity.getUserName());
		values.put("user_img", entity.getUserImg());
		values.put("text_msg", entity.getTextMsg());
		values.put("img_msg", entity.getImgMsg());
		values.put("voice_msg", entity.getVoiceMsg());
		values.put("msg_type", String.valueOf(entity.getMsgType()));
		String is_me_msg;
		if (entity.isMeMsg()) {
			is_me_msg = "1";
		} else {
			is_me_msg = "0";
		}
		values.put("is_me_msg", is_me_msg);
		db.insert(ChatInfoDBOpenHelper.TABLE_NAME, null, values);
		db.close();
	}

	/**
	 * 删除一条聊天数据
	 * 
	 * @param id
	 */
	public void delete(String id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(ChatInfoDBOpenHelper.TABLE_NAME, "_id=?", new String[] { id });
		db.close();
	}

	/**
	 * 查询部分的聊天数据
	 * 
	 * @param maxNumber 最多返回多少条数据
	 * @param startIndex 从哪个位置开始获取数据
	 * @return
	 */
	public List<ChatMsgEntity> findPart(int maxNumber, int startIndex) {
		SQLiteDatabase db = helper.getWritableDatabase();
		//select _id,location,areacode from mob_location limit 20,10
		Cursor cursor = db.rawQuery("select * from info order by _id desc limit ? offset ?", new String[] { String.valueOf(maxNumber), String.valueOf(startIndex) });
		List<ChatMsgEntity> list = new ArrayList<ChatMsgEntity>();
		String id;
		long time;
		String isShowTime;
		String userName;
		String userImg;
		String textMsg;
		String imgMsg;
		String voiceMsg;
		int msgType;
		String isMeMsg;
		while (cursor.moveToNext()) {
			id = String.valueOf(cursor.getInt(0));
			time = Long.parseLong(cursor.getString(1));
			isShowTime = cursor.getString(2);
			userName = cursor.getString(3);
			userImg = cursor.getString(4);
			textMsg = cursor.getString(5);
			imgMsg = cursor.getString(6);
			voiceMsg = cursor.getString(7);
			msgType = Integer.parseInt(cursor.getString(8));
			isMeMsg = cursor.getString(9);

			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setId(id);
			entity.setTime(time);
			if ("0".equals(isShowTime)) {
				entity.setShowTime(false);
			} else {
				entity.setShowTime(true);
			}
			entity.setUserName(userName);
			entity.setUserImg(Integer.parseInt(userImg));
			entity.setTextMsg(textMsg);
			entity.setImgMsg(imgMsg);
			entity.setVoiceMsg(voiceMsg);
			entity.setMsgType(msgType);
			if ("0".equals(isMeMsg)) {
				entity.setMeMsg(false);
			} else {
				entity.setMeMsg(true);
			}
			list.add(0, entity);
		}
		cursor.close();
		db.close();
		return list;
	}
}
