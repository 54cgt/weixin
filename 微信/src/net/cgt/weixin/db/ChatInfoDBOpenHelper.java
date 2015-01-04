package net.cgt.weixin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 聊天信息数据库
 * 
 * @author lijian
 * @date 2015-01-04 21:04:21
 */
public class ChatInfoDBOpenHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "info";

	public ChatInfoDBOpenHelper(Context context) {
		super(context, "chartinfo.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table info (_id integer primary key autoincrement,time varchar(20),is_show_time varchar(2),user_name varchar(20),user_img varchar(50),text_msg varchar(1024),img_msg varchar(50),voice_msg varchar(50),msg_type varchar(2),is_me_msg varchar(2))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
