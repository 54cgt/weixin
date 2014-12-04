package net.cgt.weixin.activity;

import net.cgt.weixin.R;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 聊天
 * 
 * @author lijian
 * @date 2014-12-04
 */
public class ChatActivity extends BaseActivity {

	private LinearLayout mLl_chat_showBox;
	private EditText mEt_chat_input;
	private Button mBtn_chat_send;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cgt_activity_chat);
		init();
	}

	private void init() {
		initView();
		initData();
	}

	private void initView() {
		mLl_chat_showBox = (LinearLayout) findViewById(R.id.cgt_ll_chat_showBox);
		mEt_chat_input = (EditText) findViewById(R.id.cgt_et_chat_input);
		mBtn_chat_send = (Button) findViewById(R.id.cgt_btn_chat_send);
	}

	private void initData() {
		// TODO Auto-generated method stub

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
