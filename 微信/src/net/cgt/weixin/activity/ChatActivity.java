package net.cgt.weixin.activity;

import net.cgt.weixin.R;
import net.cgt.weixin.domain.User;
import net.cgt.weixin.utils.AppToast;
import net.cgt.weixin.utils.LogUtil;
import net.cgt.weixin.view.manager.XmppManager;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 聊天
 * 
 * @author lijian
 * @date 2014-12-04
 */
public class ChatActivity extends BaseActivity implements OnClickListener {
	
	private static final String LOGTAG = LogUtil.makeLogTag(ChatActivity.class);

	private User user;
	private LinearLayout mLl_chat_showBox;
	private EditText mEt_chat_msg;
	private Button mBtn_chat_send;
	private Vibrator vibrator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cgt_activity_chat);
		Intent intent = this.getIntent();
		user = intent.getParcelableExtra("user");
		init();
	}

	private void init() {
		initView();
		initData();
	}

	private void initView() {
		mLl_chat_showBox = (LinearLayout) findViewById(R.id.cgt_ll_chat_showBox);
		mEt_chat_msg = (EditText) findViewById(R.id.cgt_et_chat_msg);
		mBtn_chat_send = (Button) findViewById(R.id.cgt_btn_chat_send);
		mBtn_chat_send.setOnClickListener(this);
	}

	private void initData() {
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cgt_btn_chat_send://发送按钮
			if (checkValidity()) {
				sendMsg();
			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * 检查合法性
	 * @return
	 */
	private boolean checkValidity() {
		String msg = mEt_chat_msg.getText().toString().trim();
		if (TextUtils.isEmpty(msg)) {
			mEt_chat_msg.requestFocus();
			AppToast.getToast().show("发送消息不能为空");
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			mEt_chat_msg.startAnimation(shake);
			vibrator.vibrate(300);
			return false;
		}
		return true;
	}
	
	/**
	 * 发送消息
	 */
	private void sendMsg() {
		new Thread(new Runnable() {
			@Override
			public void run() {
//				Chat chat = XmppManager.getInstance().getFriendChat(user.getUserAccount(), null);
				Chat chat = XmppManager.getInstance().getFriendChat("admin", null);

				String messageType = null;
				String chanId = null;
				String chanName = null;
				
				
				//	friend为好友名，不是JID；listener 监听器可以传null，利用聊天窗口对象调用sendMessage发送消息
				//	这里sendMessage我传的是一个JSON字符串，以便更灵活的控制，发送消息完成~
					try {
					 	String msgjson = "{\"messageType\":\""+messageType+"\",\"chanId\":\""+chanId+"\",\"chanName\":\""+chanName+"\"}";
						chat.sendMessage(msgjson);
					} catch (XMPPException e) {
						e.printStackTrace();
					}

				//	添加监听，最好是放在登录方法中，在关闭连接方法中，移除监听，原因是为了避免重复添加监听，接受重复消息
				//	退出程序应该关闭连接，移除监听，该监听可以接受所有好友的消息，很方便吧~
//					TaxiChatManagerListener chatManagerListener = new TaxiChatManagerListener();  
//					xmppManager.getConnection().getChatManager().addChatListener(chatManagerListener); 

				//	connection.getChatManager().removeChatListener(chatManagerListener);
			}
		}).start();
	}

	/**
	 * 查杀病毒
	 */
	/*private void scanVirus() {
		tv_scan_status.setText("正在初始化8核杀毒引擎。");
		virusPacknames = new ArrayList<String>();
		new Thread(){
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				final PackageManager pm = getPackageManager();
				 List<PackageInfo> infos = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
				 pb_progress.setMax(infos.size());
				 int total = 0;
				 for(final PackageInfo info:infos){
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							tv_scan_status.setText("正在扫描："+info.applicationInfo.loadLabel(pm));
						}
					});
					String md5 = Md5Utils.encode(info.signatures[0].toCharsString());
					//查询md5信息是否在病毒数据库里面。
					String result = AntivirusDao.find(md5);
					final String showinfo ;
					if(result!=null){
						showinfo = "发现病毒→☆"+info.applicationInfo.loadLabel(pm)+"☆\n病毒类型："+result;
						virusPacknames.add(info.packageName);			
					}else{
						showinfo =  "扫描安全→"+info.applicationInfo.loadLabel(pm);
					}
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TextView tv = new TextView(getApplicationContext());
							tv.setTextColor(Color.BLACK);
							tv.setTextSize(16);
							tv.setText(showinfo);
							ll_container.addView(tv, 0);
						}
					});
					total++;
					pb_progress.setProgress(total);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				 }
				 runOnUiThread(new Runnable() {
						@Override
						public void run() {
							tv_scan_status.setText("扫描完毕");
							iv_scan.clearAnimation();
							iv_scan.setVisibility(View.INVISIBLE);
							AlertDialog.Builder builder = new Builder(AntiVirusActivity.this);
							if(virusPacknames.size()>0){
								builder.setTitle("警告!");
								builder.setMessage("在您的手机里面发现了"+virusPacknames.size()+"个病毒！！,不查杀手机就会爆炸！");
								builder.setPositiveButton("立刻处理", new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										for(String packname : virusPacknames){
											Intent intent = new Intent();
											intent.setAction(Intent.ACTION_DELETE);
											intent.setData(Uri.parse("package:"+packname));
											startActivity(intent);
										}
									}
								});
								builder.setNegativeButton("我不怕", null);
								builder.show();
							}else{
								builder.setTitle("提示!");
								builder.setMessage("你的手机非常安全,请放心使用...");
								builder.setNegativeButton("确定", null);
								builder.show();
								Toast.makeText(getApplicationContext(), "你的手机非常安全,请放心使用...", Toast.LENGTH_SHORT).show();
							}
						}
					});
				 
			};
		}.start();
	}*/
}
