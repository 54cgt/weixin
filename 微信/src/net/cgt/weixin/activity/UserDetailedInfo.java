package net.cgt.weixin.activity;

import net.cgt.weixin.R;
import net.cgt.weixin.domain.User;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 用户详细资料
 * 
 * @author lijian
 * @date 2014-11-30
 */
public class UserDetailedInfo extends BaseActivity implements OnClickListener {

	private User user;
	private ImageView mIv_userPhoto;
	private TextView mTv_userName;
	private ImageView mIv_userSex;
	private TextView mTv_userWeixin;
	private TextView mTv_nickName;
	private TextView mTv_area;
	private TextView mTv_personalizedSignature;
	private LinearLayout mLl_personalPhoto_box;
	private LinearLayout mLl_personalPhoto;
	private LinearLayout mLl_socialInfo_box;
	private LinearLayout mLl_socialInfo;
	private Button mBtn_sendMsg;
	private Button mBtn_videoChat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cgt_activity_userdetailedinfo);
		Intent intent = this.getIntent();
		user = intent.getParcelableExtra("user");
		init();
	}

	private void init() {
		initView();
		initData();
	}

	private void initView() {
		mIv_userPhoto = (ImageView) findViewById(R.id.cgt_iv_userDetailedInfo_userPhoto);
		mTv_userName = (TextView) findViewById(R.id.cgt_tv_userDetailedInfo_userName);
		mIv_userSex = (ImageView) findViewById(R.id.cgt_iv_userDetailedInfo_userSex);
		mTv_userWeixin = (TextView) findViewById(R.id.cgt_tv_userDetailedInfo_userWeixin);
		mTv_nickName = (TextView) findViewById(R.id.cgt_tv_userDetailedInfo_nickname);
		mTv_area = (TextView) findViewById(R.id.cgt_tv_userDetailedInfo_area);
		mTv_personalizedSignature = (TextView) findViewById(R.id.cgt_tv_userDetailedInfo_personalizedSignature);
		mLl_personalPhoto_box = (LinearLayout) findViewById(R.id.cgt_ll_userDetailedInfo_personalPhoto_box);
		mLl_personalPhoto = (LinearLayout) findViewById(R.id.cgt_ll_userDetailedInfo_personalPhoto);
		mLl_socialInfo_box = (LinearLayout) findViewById(R.id.cgt_ll_userDetailedInfo_socialInfo_box);
		mLl_socialInfo = (LinearLayout) findViewById(R.id.cgt_ll_userDetailedInfo_socialInfo);
		mBtn_sendMsg = (Button) findViewById(R.id.cgt_btn_userDetailedInfo_sendMsg);
		mBtn_videoChat = (Button) findViewById(R.id.cgt_btn_userDetailedInfo_videoChat);

		mIv_userPhoto.setOnClickListener(this);
		mLl_personalPhoto_box.setOnClickListener(this);
		mLl_socialInfo_box.setOnClickListener(this);
		mBtn_sendMsg.setOnClickListener(this);
		mBtn_videoChat.setOnClickListener(this);
	}

	private void initData() {
		mIv_userPhoto.setImageResource(Integer.parseInt(user.getUserPhote()));
		mTv_userName.setText(user.getUserAccount());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cgt_iv_userDetailedInfo_userPhoto://用户头像
			Intent intent = new Intent(this, GalleryImageActivity.class);
			intent.putExtra("user", user);
			startActivity(intent);
			break;
		case R.id.cgt_ll_userDetailedInfo_personalPhoto_box://个人相册

			break;
		case R.id.cgt_ll_userDetailedInfo_socialInfo_box://社交资料

			break;
		case R.id.cgt_btn_userDetailedInfo_sendMsg://发消息

			break;
		case R.id.cgt_btn_userDetailedInfo_videoChat://视频聊天

			break;

		default:
			break;
		}
	}
}
