package net.cgt.weixin.activity;

import java.lang.reflect.Method;

import net.cgt.weixin.R;
import net.cgt.weixin.domain.User;
import net.cgt.weixin.utils.AppToast;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cgt_menu_userdetailedinfo, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home://返回上一菜单页
			AppToast.getToast().show("返回上一页");
			Intent upIntent = NavUtils.getParentActivityIntent(this);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
			} else {
				upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				NavUtils.navigateUpTo(this, upIntent);
			}
			break;
		case R.id.menu_userDetailedInfo_pencil:
			AppToast.getToast().show(R.string.text_menu_userDetailedInfo_pencil);
			break;
		case R.id.menu_userDetailedInfo_stars:
			AppToast.getToast().show(R.string.text_menu_userDetailedInfo_stars);
			break;
		case R.id.menu_userDetailedInfo_jurisdiction:
			AppToast.getToast().show(R.string.text_menu_userDetailedInfo_jurisdiction);
			break;
		case R.id.menu_userDetailedInfo_forward:
			AppToast.getToast().show(R.string.text_menu_userDetailedInfo_forward);
			break;
		case R.id.menu_userDetailedInfo_blacklist:
			AppToast.getToast().show(R.string.text_menu_userDetailedInfo_blacklist);
			break;
		case R.id.menu_userDetailedInfo_delete:
			AppToast.getToast().show(R.string.text_menu_userDetailedInfo_delete);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * overflow被展开的时候调用
	 * 
	 * @param featureId
	 * @param menu
	 * @return
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		//通过返回反射的方法将MenuBuilder的setOptionalIconsVisible变量设置为true
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}
}
