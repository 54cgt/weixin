/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.cgt.weixin.service;

import java.util.Properties;

import net.cgt.weixin.Constants;
import net.cgt.weixin.utils.L;
import net.cgt.weixin.utils.LogUtil;
import net.cgt.weixin.utils.SpUtil;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.content.Context;

/**
 * 连接服务器登陆服务
 * 
 * @author lijian
 * @date 2014-11-22
 */
public class ClientConService {

	private static final String LOGTAG = LogUtil.makeLogTag(ClientConService.class);

	private Context context;

	/** 加载配置文件 **/
	private Properties props;

	private String version = "0.0.1";

	private String apiKey;

	private String xmppHost;

	private String xmppPort;

	/** 回调Activity的包名 **/
	private String callbackActivityPackageName;

	/** 回调Activity的全类名 **/
	private String callbackActivityClassName;

	private XMPPConnection connection;

	public ClientConService(Context context) {
		this.context = context;
		init(context);
	}

	private void init(Context context) {
		if (context instanceof Activity) {
			L.i(LOGTAG, "Callback Activity...");
			Activity callbackActivity = (Activity) context;
			callbackActivityPackageName = callbackActivity.getPackageName();
			callbackActivityClassName = callbackActivity.getClass().getName();
		}

		props = loadProperties();
		apiKey = props.getProperty("apiKey", "");
		xmppHost = props.getProperty("xmppHost", "127.0.0.1");
		xmppPort = props.getProperty("xmppPort", "5222");
		L.i(LOGTAG, "apiKey=" + apiKey);
		L.i(LOGTAG, "xmppHost=" + xmppHost);
		L.i(LOGTAG, "xmppPort=" + xmppPort);

		SpUtil sp = new SpUtil(context);
		sp.saveString(Constants.API_KEY, apiKey);
		sp.saveString(Constants.VERSION, version);
		sp.saveString(Constants.XMPP_HOST, xmppHost);
		sp.saveInt(Constants.XMPP_PORT, Integer.parseInt(xmppPort));
		sp.saveString(Constants.CALLBACK_ACTIVITY_PACKAGE_NAME, callbackActivityPackageName);
		sp.saveString(Constants.CALLBACK_ACTIVITY_CLASS_NAME, callbackActivityClassName);
	}

	public boolean login(String account, String password) {
		ConnectionConfiguration connConfig = new ConnectionConfiguration(xmppHost, Integer.parseInt(xmppPort));
		//设置安全模式
		connConfig.setSecurityMode(SecurityMode.required);
		//设置SASL认证是否启用
		connConfig.setSASLAuthenticationEnabled(false);
		//设置数据压缩是否启用
		connConfig.setCompressionEnabled(false);
		//是否启用调试模式
		connConfig.setDebuggerEnabled(true);

		/** 创建connection连接 */
		XMPPConnection connection = new XMPPConnection(connConfig);
		setConnection(connection);

		try {
			// 连接到服务器
			connection.connect();
			L.i(LOGTAG, "XMPP connected successfully");

			//登陆
			connection.login(account, password);

			/** 开启读写线程，并加入到管理类中 */
			//ClientSendThread cst = new ClientSendThread(connection);
			//cst.start();
			//ManageClientThread.addClientSendThread(account,cst);

			return true;
		} catch (XMPPException e) {
			L.e(LOGTAG, "XMPP connection failed", e);
		}
		return false;
	}

	/**
	 * 得到xmpp连接
	 * 
	 * @return
	 */
	public XMPPConnection getConnection() {
		return connection;
	}

	/**
	 * 设置连接
	 * 
	 * @param connection 一个xmpp连接
	 */
	public void setConnection(XMPPConnection connection) {
		this.connection = connection;
	}

	/**
	 * 加载Properties配置文件
	 * 
	 * @return
	 */
	private Properties loadProperties() {
		Properties props = new Properties();
		try {
			int id = context.getResources().getIdentifier("weixin", "raw", context.getPackageName());
			props.load(context.getResources().openRawResource(id));
		} catch (Exception e) {
			L.e(LOGTAG, "Could not find the properties file.", e);
		}
		return props;
	}
}
