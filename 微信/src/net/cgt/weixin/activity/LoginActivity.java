package net.cgt.weixin.activity;

import net.cgt.weixin.Constants;
import net.cgt.weixin.GlobalParams;
import net.cgt.weixin.R;
import net.cgt.weixin.service.ClientConService;
import net.cgt.weixin.utils.AppToast;
import net.cgt.weixin.utils.HandlerTypeUtils;
import net.cgt.weixin.utils.L;
import net.cgt.weixin.utils.LogUtil;
import net.cgt.weixin.utils.SpUtil;
import net.cgt.weixin.view.listener.TaxiChatManagerListener;
import net.cgt.weixin.view.manager.XmppManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

/**
 * 登陆
 * 
 * @author lijian
 * 
 * @data 2014-11-21
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
	
	private static final String LOGTAG = LogUtil.makeLogTag(LoginActivity.class);

	private EditText mEt_userName;
	private EditText mEt_password;
	private Button mBtn_login;
	private SpUtil sp;
	private Vibrator vibrator;

	public static String mCurrentUserName;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_SUCCESS:
				L.i(LOGTAG, "login success");
				sp.saveString(Constants.XMPP_USERNAME, userName);
				sp.saveString(Constants.XMPP_PASSWORD, userPwd);
				AppToast.getToast().show("登陆成功");
				GlobalParams.ISLOGIN = true;
				addChatListener();// 登陆成功,添加聊天监听器
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
				break;
			case HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_FAIL:
				AppToast.getToast().show("登陆失败");
				break;
			}
		}

		/**
		 * 添加聊天监听器
		 */
		private void addChatListener() {
			// 添加监听，最好是放在登录方法中，在关闭连接方法中，移除监听，原因是为了避免重复添加监听，接受重复消息
			// 退出程序应该关闭连接，移除监听，该监听可以接受所有好友的消息，很方便吧~
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					TaxiChatManagerListener chatManagerListener = new TaxiChatManagerListener();
					XmppManager.getInstance().getConnection().getChatManager().addChatListener(chatManagerListener);
				}
			});
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cgt_activity_login);
		init();
	}

	private void init() {
		initView();
		initDate();
	}

	private void initView() {
		mEt_userName = (EditText) findViewById(R.id.cgt_et_userName);
		mEt_password = (EditText) findViewById(R.id.cgt_et_password);
		mBtn_login = (Button) findViewById(R.id.cgt_btn_login);
		mBtn_login.setOnClickListener(this);
	}

	private void initDate() {
		sp = new SpUtil(this);
		mEt_userName.setText(sp.getString(Constants.XMPP_USERNAME, ""));
		mEt_password.setText(sp.getString(Constants.XMPP_PASSWORD, ""));
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cgt_btn_login:
			if (checkValidity()) {
				submitLogin();
			}
			break;
		}
	}

	private String userName = "";
	private String userPwd = "";

	private void getUserInfo() {
		userName = mEt_userName.getText().toString().trim();
		userPwd = mEt_password.getText().toString().trim();
	}

	private boolean checkValidity() {
		getUserInfo();
		if (TextUtils.isEmpty(userName)) {
			mEt_userName.requestFocus();
			AppToast.getToast().show("用户名不能为空");
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			mEt_userName.startAnimation(shake);
			vibrator.vibrate(300);
			return false;
		}
		if (TextUtils.isEmpty(userPwd)) {
			mEt_password.requestFocus();
			AppToast.getToast().show("密码不能为空");
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			mEt_password.startAnimation(shake);
			vibrator.vibrate(300);
			return false;
		}
		if (userPwd.length() < 6) {
			mEt_password.requestFocus();
			AppToast.getToast().show("密码格式不正确");
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			mEt_password.startAnimation(shake);
			vibrator.vibrate(300);
			return false;
		}
		return true;
	}

	private void submitLogin() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				ClientConService ccs = new ClientConService(LoginActivity.this);
				boolean b = ccs.login(userName, userPwd);
				if (b) {
					handler.sendEmptyMessage(HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_SUCCESS);
				} else {
					handler.sendEmptyMessage(HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_FAIL);
				}
			}
		}).start();
	}
}
