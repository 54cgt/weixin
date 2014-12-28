package net.cgt.weixin.activity;

import net.cgt.weixin.Constants;
import net.cgt.weixin.GlobalParams;
import net.cgt.weixin.R;
import net.cgt.weixin.utils.AppToast;
import net.cgt.weixin.utils.HandlerTypeUtils;
import net.cgt.weixin.utils.L;
import net.cgt.weixin.utils.LogUtil;
import net.cgt.weixin.utils.SpUtil;
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
	private Button mBtn_regist;
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
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
				break;
			case HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_FAIL:
				AppToast.getToast().show("登陆失败");
				break;
			}
		}
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
		mBtn_regist = (Button) findViewById(R.id.cgt_btn_regist);
		mBtn_login.setOnClickListener(this);
		mBtn_regist.setOnClickListener(this);
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
		case R.id.cgt_btn_regist:
			startActivity(new Intent(this, RegistActivity.class));
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
				//				ClientConService ccs = new ClientConService(LoginActivity.this);
				//				boolean b = ccs.login(userName, userPwd);

				boolean b = XmppManager.getInstance().login(userName, userPwd);

				if (b) {
					handler.sendEmptyMessage(HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_SUCCESS);
				} else {
					handler.sendEmptyMessage(HandlerTypeUtils.WX_HANDLER_TYPE_LOAD_DATA_FAIL);
				}
			}
		}).start();
	}
}
