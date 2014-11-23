package net.cgt.weixin.view.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

import net.cgt.weixin.Constants;
import net.cgt.weixin.utils.L;
import net.cgt.weixin.utils.LogUtil;
import net.cgt.weixin.utils.SpUtil;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smack.provider.ProviderManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.util.Log;

/**
 * 登陆XMPP管理器
 * 
 * @author lijian
 * @date 2014-11-22
 */
public class XmppLoginManager {
//
//	private static final String LOGTAG = LogUtil.makeLogTag(XmppLoginManager.class);
//
//	/** XMPP资源名称 **/
//	private static final String XMPP_RESOURCE_NAME = "WeixinClient";
//
//	/** 上下文 **/
//	private Context context;
//
//	/** 获得提交一个新的运行任务 **/
//	private NotificationService.TaskSubmitter taskSubmitter;
//
//	/** 监测(控制)运行中的任务数 **/
//	private NotificationService.TaskTracker taskTracker;
//
//	/** XMPP的IP **/
//	private String xmppHost;
//
//	/** XMPP的端口 **/
//	private int xmppPort;
//
//	/** 一个xmpp连接 **/
//	private XMPPConnection connection;
//
//	/** XMPP的用户名 **/
//	private String username;
//
//	/** XMPP的密码 **/
//	private String password;
//
//	/** 持久连接监听器 **/
//	private ConnectionListener connectionListener;
//
//	/** 通知数据包监听器 **/
//	private PacketListener notificationPacketListener;
//
//	private Handler handler;
//
//	/** 任务数组 **/
//	private List<Runnable> taskList;
//
//	/** **/
//	private boolean running = false;
//
//	/****/
//	private Future<?> futureTask;
//
//	/**
//	 * 重连线程(管理：当网络异常被中断后，线程内管理多少时间再次发起连接请求)
//	 **/
//	private Thread reconnection;
//
//	private SpUtil sp;
//
//	public XmppLoginManager(Context context) {
//		this.context = context;
//		taskSubmitter = notificationService.getTaskSubmitter();
//		taskTracker = notificationService.getTaskTracker();
//
//		sp = new SpUtil(context);
//
//		xmppHost = sp.getString(Constants.XMPP_HOST, "localhost");
//		xmppPort = sp.getInt(Constants.XMPP_PORT, 5222);
//		username = sp.getString(Constants.XMPP_USERNAME, "");
//		password = sp.getString(Constants.XMPP_PASSWORD, "");
//
//		connectionListener = new PersistentConnectionListener(this);
//		notificationPacketListener = new NotificationPacketListener(this);
//
//		handler = new Handler();
//		taskList = new ArrayList<Runnable>();
//		reconnection = new ReconnectionThread(this);
//	}
//
//	public Context getContext() {
//		return context;
//	}
//
//	/**
//	 * 建立连接
//	 */
//	public void connect() {
//		L.d(LOGTAG, "connect()...");
//		submitLoginTask();
//	}
//
//	/**
//	 * 断开连接
//	 */
//	public void disconnect() {
//		L.d(LOGTAG, "disconnect()...");
//		terminatePersistentConnection();
//	}
//
//	/**
//	 * 终止持久连接
//	 */
//	public void terminatePersistentConnection() {
//		L.d(LOGTAG, "terminatePersistentConnection()...");
//		Runnable runnable = new Runnable() {
//
//			final XmppLoginManager xmppLoginManager = XmppLoginManager.this;
//
//			public void run() {
//				if (xmppLoginManager.isConnected()) {
//					L.d(LOGTAG, "terminatePersistentConnection()... run()");
//					xmppLoginManager.getConnection().removePacketListener(xmppLoginManager.getNotificationPacketListener());
//					xmppLoginManager.getConnection().disconnect();
//				}
//				xmppLoginManager.runTask();
//			}
//		};
//		addTask(runnable);
//	}
//
//	/**
//	 * 得到xmpp连接
//	 * 
//	 * @return
//	 */
//	public XMPPConnection getConnection() {
//		return connection;
//	}
//
//	/**
//	 * 设置连接
//	 * 
//	 * @param connection 一个xmpp连接
//	 */
//	public void setConnection(XMPPConnection connection) {
//		this.connection = connection;
//	}
//
//	/**
//	 * 得到xmpp的用户名
//	 * 
//	 * @return
//	 */
//	public String getUsername() {
//		return username;
//	}
//
//	/**
//	 * 设置xmpp的用户名
//	 * 
//	 * @param username
//	 */
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	/**
//	 * 得到xmpp的密码
//	 * 
//	 * @return
//	 */
//	public String getPassword() {
//		return password;
//	}
//
//	/**
//	 * 设置xmpp的密码
//	 * 
//	 * @param password
//	 */
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	/**
//	 * 得到持久连接监听器
//	 * 
//	 * @return
//	 */
//	public ConnectionListener getConnectionListener() {
//		return connectionListener;
//	}
//
//	/**
//	 * 得到通知数据包监听器
//	 * 
//	 * @return
//	 */
//	public PacketListener getNotificationPacketListener() {
//		return notificationPacketListener;
//	}
//
//	/**
//	 * 开启重连线程
//	 */
//	public void startReconnectionThread() {
//		synchronized (reconnection) {
//			if (!reconnection.isAlive()) {// 如果已经不是存活的
//				reconnection.setName("Xmpp Reconnection Thread");
//				reconnection.start();
//			}
//		}
//	}
//
//	public Handler getHandler() {
//		return handler;
//	}
//
//	/**
//	 * 重新注册账户
//	 */
//	public void reregisterAccount() {
//		removeAccount();
//		submitLoginTask();
//		runTask();
//	}
//
//	/**
//	 * 得到任务列表
//	 * 
//	 * @return
//	 */
//	public List<Runnable> getTaskList() {
//		return taskList;
//	}
//
//	/**
//	 * 得到任务将来结果
//	 * 
//	 * @return
//	 */
//	public Future<?> getFutureTask() {
//		return futureTask;
//	}
//
//	/**
//	 * 运行任务
//	 */
//	public void runTask() {
//		L.d(LOGTAG, "runTask()...");
//		synchronized (taskList) {
//			running = false;
//			futureTask = null;
//			if (!taskList.isEmpty()) {
//				Runnable runnable = (Runnable) taskList.get(0);
//				taskList.remove(0);
//				running = true;
//				futureTask = taskSubmitter.submit(runnable);
//				if (futureTask == null) {
//					taskTracker.decrease();
//				}
//			}
//		}
//		taskTracker.decrease();
//		L.d(LOGTAG, "runTask()...done");
//	}
//
//	/**
//	 * 得到一个随机生成的UUID
//	 * 
//	 * @return
//	 */
//	private String newRandomUUID() {
//		String uuidRaw = UUID.randomUUID().toString();
//		System.out.println("UUID--->" + uuidRaw);
//		return uuidRaw.replaceAll("-", "");
//	}
//
//	/**
//	 * xmpp连接是否建立并连接成功
//	 * 
//	 * @return
//	 */
//	private boolean isConnected() {
//		return connection != null && connection.isConnected();
//	}
//
//	/**
//	 * 建立连接成功的账户是否被认证
//	 * 
//	 * @return
//	 */
//	private boolean isAuthenticated() {
//		return connection != null && connection.isConnected() && connection.isAuthenticated();
//	}
//
//	/**
//	 * 是否是已注册的xmpp用户
//	 * 
//	 * @return
//	 */
//	private boolean isRegistered() {
//		return sp.contains(Constants.XMPP_USERNAME) && sp.contains(Constants.XMPP_PASSWORD);
//	}
//
//	/**
//	 * 提交连接任务
//	 */
//	private void submitConnectTask() {
//		L.d(LOGTAG, "submitConnectTask()...");
//		addTask(new ConnectTask());
//	}
//
//	/**
//	 * 提交注册任务
//	 */
//	private void submitRegisterTask() {
//		L.d(LOGTAG, "submitRegisterTask()...");
//		submitConnectTask();
//		addTask(new RegisterTask());
//	}
//
//	/**
//	 * 提交登录任务
//	 */
//	private void submitLoginTask() {
//		L.d(LOGTAG, "submitLoginTask()...");
//		submitRegisterTask();
//		addTask(new LoginTask());
//	}
//
//	/**
//	 * 添加任务到线程池
//	 * 
//	 * @param runnable
//	 */
//	private void addTask(Runnable runnable) {
//		L.d(LOGTAG, "addTask(runnable)...");
//		taskTracker.increase();
//		synchronized (taskList) {
//			if (taskList.isEmpty() && !running) {//如果任务列表为空,并且没有任务在运行中
//				running = true;
//				futureTask = taskSubmitter.submit(runnable);
//				if (futureTask == null) {//如果得到的将来的结果为null
//					taskTracker.decrease();
//				}
//			} else {
//				taskList.add(runnable);
//			}
//		}
//		L.d(LOGTAG, "addTask(runnable)... done");
//	}
//
//	/**
//	 * 删除账户
//	 */
//	private void removeAccount() {
//		Editor editor = sharedPrefs.edit();
//		editor.remove(Constants.XMPP_USERNAME);
//		editor.remove(Constants.XMPP_PASSWORD);
//		editor.commit();
//	}
//
//	/**
//	 * A runnable task to connect the server. 创建一个线程-->连接到服务器的
//	 */
//	private class ConnectTask implements Runnable {
//
//		final XmppManager xmppManager;
//
//		private ConnectTask() {
//			this.xmppManager = XmppManager.this;
//		}
//
//		public void run() {
//			L.i(LOGTAG, "ConnectTask.run()...");
//
//			if (!xmppManager.isConnected()) {
//				// Create the configuration for this new connection
//				//给这个新的xmpp连接创建一个配置信息
//				ConnectionConfiguration connConfig = new ConnectionConfiguration(xmppHost, xmppPort);
//				// connConfig.setSecurityMode(SecurityMode.disabled);
//				connConfig.setSecurityMode(SecurityMode.required);//设置安全模式
//				connConfig.setSASLAuthenticationEnabled(false);//设置SASL认证是否启用
//				connConfig.setCompressionEnabled(false);//设置数据压缩是否启用
//
//				//新建一个xmpp连接
//				XMPPConnection connection = new XMPPConnection(connConfig);
//				xmppManager.setConnection(connection);
//
//				try {
//					// Connect to the server
//					// 连接到服务器
//					connection.connect();
//					L.i(LOGTAG, "XMPP connected successfully");
//
//					// packet provider
//					// 添加一个数据包(IQ)提供者
//					ProviderManager.getInstance().addIQProvider("notification", "androidpn:iq:notification", new NotificationIQProvider());
//
//				} catch (XMPPException e) {
//					L.e(LOGTAG, "XMPP connection failed", e);
//				}
//
//				xmppManager.runTask();
//
//			} else {
//				//如果XMPP连接之前已经建立,那么就运行这个任务
//				L.i(LOGTAG, "XMPP connected already");
//				xmppManager.runTask();
//			}
//		}
//	}
//
//	/**
//	 * A runnable task to register a new user onto the server.
//	 * 创建一个线程-->注册一个新用户到服务器的
//	 */
//	private class RegisterTask implements Runnable {
//
//		final XmppManager xmppManager;
//
//		private RegisterTask() {
//			xmppManager = XmppManager.this;
//		}
//
//		public void run() {
//			L.i(LOGTAG, "RegisterTask.run()...");
//
//			if (!xmppManager.isRegistered()) {
//				final String newUsername = newRandomUUID();
//				final String newPassword = newRandomUUID();
//
//				//创建一个限量注册
//				Registration registration = new Registration();
//
//				//数据包过滤器
//				PacketFilter packetFilter = new AndFilter(new PacketIDFilter(registration.getPacketID()), new PacketTypeFilter(IQ.class));
//
//				//数据包监听
//				PacketListener packetListener = new PacketListener() {
//
//					/**
//					 * 数据包过程
//					 */
//					public void processPacket(Packet packet) {
//						L.d("RegisterTask.PacketListener", "processPacket().....");
//						L.d("RegisterTask.PacketListener", "packet=" + packet.toXML());
//
//						if (packet instanceof IQ) {//如果是iq,强转为IQ
//							IQ response = (IQ) packet;
//							if (response.getType() == IQ.Type.ERROR) {//如果是一个错误消息数据包
//								if (!response.getError().toString().contains("409")) {
//									//注册XMPP帐户时未知错误！
//									L.e(LOGTAG, "Unknown error while registering XMPP account! " + response.getError().getCondition());
//								}
//							} else if (response.getType() == IQ.Type.RESULT) {
//								xmppManager.setUsername(newUsername);
//								xmppManager.setPassword(newPassword);
//								L.d(LOGTAG, "username=" + newUsername);
//								L.d(LOGTAG, "password=" + newPassword);
//
//								Editor editor = sharedPrefs.edit();
//								editor.putString(Constants.XMPP_USERNAME, newUsername);
//								editor.putString(Constants.XMPP_PASSWORD, newPassword);
//								editor.commit();
//								//账户注册成功
//								Log.i(LOGTAG, "Account registered successfully");
//								xmppManager.runTask();
//							}
//						}
//					}
//				};
//
//				//给XMPP连接-->添加数据包监听器
//				connection.addPacketListener(packetListener, packetFilter);
//
//				registration.setType(IQ.Type.SET);
//				// registration.setTo(xmppHost);
//				// Map<String, String> attributes = new HashMap<String, String>();
//				// attributes.put("username", rUsername);
//				// attributes.put("password", rPassword);
//				// registration.setAttributes(attributes);
//				registration.addAttribute("username", newUsername);
//				registration.addAttribute("password", newPassword);
//				connection.sendPacket(registration);//发送注册数据包
//
//			} else {
//				L.i(LOGTAG, "Account registered already");
//				xmppManager.runTask();
//			}
//		}
//	}
//
//	/**
//	 * A runnable task to log into the server. 创建一个线程-->登陆到服务器的
//	 */
//	private class LoginTask implements Runnable {
//
//		final XmppManager xmppManager;
//
//		private LoginTask() {
//			this.xmppManager = XmppManager.this;
//		}
//
//		public void run() {
//			L.i(LOGTAG, "LoginTask.run()...");
//
//			if (!xmppManager.isAuthenticated()) {//如果账户已认证
//				L.d(LOGTAG, "username=" + username);
//				L.d(LOGTAG, "password=" + password);
//
//				try {
//					//登陆
//					xmppManager.getConnection().login(xmppManager.getUsername(), xmppManager.getPassword(), XMPP_RESOURCE_NAME);
//					L.d(LOGTAG, "Loggedn in successfully");
//
//					// connection listener
//					// 添加连接监听器
//					if (xmppManager.getConnectionListener() != null) {
//						xmppManager.getConnection().addConnectionListener(xmppManager.getConnectionListener());
//					}
//
//					// packet filter
//					PacketFilter packetFilter = new PacketTypeFilter(NotificationIQ.class);
//					// packet listener
//					PacketListener packetListener = xmppManager.getNotificationPacketListener();
//					connection.addPacketListener(packetListener, packetFilter);
//
//					xmppManager.runTask();
//
//				} catch (XMPPException e) {
//					L.e(LOGTAG, "LoginTask.run()... xmpp error");
//					L.e(LOGTAG, "Failed to login to xmpp server. Caused by: " + e.getMessage());
//					String INVALID_CREDENTIALS_ERROR_CODE = "401";
//					String errorMessage = e.getMessage();
//					//如果登陆失败,那么就重新发起注册账户
//					if (errorMessage != null && errorMessage.contains(INVALID_CREDENTIALS_ERROR_CODE)) {
//						xmppManager.reregisterAccount();
//						return;
//					}
//					// 开启重连线程
//					xmppManager.startReconnectionThread();
//
//				} catch (Exception e) {
//					L.e(LOGTAG, "LoginTask.run()... other error");
//					L.e(LOGTAG, "Failed to login to xmpp server. Caused by: " + e.getMessage());
//					xmppManager.startReconnectionThread();
//				}
//
//			} else {
//				L.i(LOGTAG, "Logged in already");
//				xmppManager.runTask();
//			}
//
//		}
//	}
}
