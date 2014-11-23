package net.cgt.weixin.view.listener;

import java.util.Timer;
import java.util.TimerTask;

import net.cgt.weixin.Constants;
import net.cgt.weixin.GlobalParams;
import net.cgt.weixin.utils.L;
import net.cgt.weixin.utils.SpUtil;
import net.cgt.weixin.view.manager.XmppManager;

import org.jivesoftware.smack.ConnectionListener;

public class TaxiConnectionListener implements ConnectionListener {

	private Timer tExit;
	private String username;
	private String password;
	private int logintime = 2000;

	@Override
	public void connectionClosed() {
		L.i("TaxiConnectionListener", "连接关闭");
		// 关闭连接  
		//		XmppManager.getInstance().closeConnection();
		// 重连服务器  
		tExit = new Timer();
		tExit.schedule(new timetask(), logintime);
	}

	@Override
	public void connectionClosedOnError(Exception e) {
		L.i("TaxiConnectionListener", "连接关闭异常");
		//判断为帐号已被登录
		boolean error = e.getMessage().equals("stream:error (conflict)");
		if (!error) {
			//关闭连接
			//			XmppManager.getInstance().closeConnection();
			// 重连服务器  
			tExit = new Timer();
			tExit.schedule(new timetask(), logintime);
		}
	}

	class timetask extends TimerTask {
		@Override
		public void run() {
			SpUtil sp = new SpUtil(GlobalParams.activity);
			sp.getString(Constants.XMPP_USERNAME, "");
			sp.getString(Constants.XMPP_PASSWORD, "");
			//			username = Utils.getInstance().getSharedPreferences("taxicall", "account", GlobalParams.activity);
			//			password = Utils.getInstance().getSharedPreferences("taxicall", "password", GlobalParams.activity);
			if (username != null && password != null) {
				L.i("TaxiConnectionListener", "尝试登录");
				// 连接服务器  
				if (XmppManager.getInstance().login(username, password)) {
					L.i("TaxiConnectionListener", "登录成功");
				} else {
					L.i("TaxiConnectionListener", "重新登录");
					tExit.schedule(new timetask(), logintime);
				}
			}
		}
	}

	@Override
	public void reconnectingIn(int arg0) {
	}

	@Override
	public void reconnectionFailed(Exception arg0) {
	}

	@Override
	public void reconnectionSuccessful() {
	}

}
