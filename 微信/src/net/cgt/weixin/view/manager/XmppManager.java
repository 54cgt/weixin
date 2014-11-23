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
package net.cgt.weixin.view.manager;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.cgt.weixin.Constants;
import net.cgt.weixin.GlobalParams;
import net.cgt.weixin.domain.FriendRooms;
import net.cgt.weixin.domain.MucHistory;
import net.cgt.weixin.domain.User;
import net.cgt.weixin.utils.FormatTools;
import net.cgt.weixin.utils.L;
import net.cgt.weixin.utils.LogUtil;
import net.cgt.weixin.utils.SpUtil;
import net.cgt.weixin.view.listener.TaxiChatManagerListener;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.ReportedData;
import org.jivesoftware.smackx.ReportedData.Row;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.ParticipantStatusListener;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.jivesoftware.smackx.packet.VCard;
import org.jivesoftware.smackx.search.UserSearchManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * XMPP管理
 * 
 * @author lijian
 * @date 2014-11-22
 */
public class XmppManager {

	private static final String LOGTAG = LogUtil.makeLogTag(XmppManager.class);

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

	private static XmppManager xmppManager;

	public static XmppManager getInstance() {
		if (xmppManager == null) {
			xmppManager = new XmppManager(GlobalParams.activity);
			return xmppManager;
		}
		return xmppManager;
	}

	private XmppManager(Context context) {
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

		connectServer();
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
	 * xmpp连接是否建立并连接成功
	 * 
	 * @return
	 */
	private boolean isConnected() {
		return connection != null && connection.isConnected();
	}

	/**
	 * 与服务器建立连接
	 * 
	 * @return
	 */
	public boolean connectServer() {
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
		this.setConnection(connection);

		try {
			// 连接到服务器
			connection.connect();
			L.i(LOGTAG, "XMPP connected successfully");
			return true;
		} catch (XMPPException e) {
			L.e(LOGTAG, "XMPP connection failed", e);
		}
		return false;
	}

	/**
	 * 注册
	 * 
	 * @param account 注册帐号
	 * @param password 注册密码
	 * @return 0、 服务器没有返回结果<br>
	 *         1、注册成功 <br>
	 *         2、这个帐号已经存在 <br>
	 *         3、注册失败
	 */
	public String regist(String account, String password) {
		if (!isConnected()) {
			return "0";
		}
		Registration reg = new Registration();
		reg.setType(IQ.Type.SET);
		reg.setTo(getConnection().getServiceName());
		reg.setUsername(account);
		reg.setPassword(password);
		reg.addAttribute("android", "geolo_createUser_android");

		//数据包过滤器
		PacketFilter filter = new AndFilter(new PacketIDFilter(reg.getPacketID()), new PacketTypeFilter(IQ.class));
		PacketCollector collector = getConnection().createPacketCollector(filter);
		getConnection().sendPacket(reg);

		IQ result = (IQ) collector.nextResult(SmackConfiguration.getPacketReplyTimeout());
		//停止请求result(是否成功的结果)
		collector.cancel();
		if (result == null) {
			L.e(LOGTAG, "No response from server.");
			return "0";
		} else if (result.getType() == IQ.Type.RESULT) {
			return "1";
		} else {
			if (result.getError().toString().equalsIgnoreCase("conflict(409)")) {
				L.e(LOGTAG, "IQ.Type.ERROR: " + result.getError().toString());
				return "2";
			} else {
				L.e(LOGTAG, "IQ.Type.ERROR: " + result.getError().toString());
				return "3";
			}
		}
	}

	/**
	 * 登陆
	 * 
	 * @param account 账户名
	 * @param password 密码
	 * @return
	 */
	public boolean login(String account, String password) {
		try {
			//登陆
			getConnection().login(account, password);

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
	 * 修改密码
	 * 
	 * @param password
	 * @return
	 */
	public boolean changePassword(String password) {
		try {
			getConnection().getAccountManager().changePassword(password);
			return true;
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 更改用户状态
	 * 
	 * @param code--><br>
	 *            0、在线<br>
	 *            1、Q我吧<br>
	 *            2、忙碌<br>
	 *            3、离开<br>
	 *            4、隐身<br>
	 *            5、离线
	 */
	public void setPresence(int code) {
		if (!isConnected()) {
			return;
		}
		Presence presence;
		switch (code) {
		case 0:
			presence = new Presence(Presence.Type.available);
			getConnection().sendPacket(presence);
			L.v(LOGTAG, "设置在线");
			break;
		case 1:
			presence = new Presence(Presence.Type.available);
			presence.setMode(Presence.Mode.chat);
			getConnection().sendPacket(presence);
			L.v(LOGTAG, "设置Q我吧");
			L.d(LOGTAG, "Q我吧--->" + presence.toXML());
			break;
		case 2:
			presence = new Presence(Presence.Type.available);
			presence.setMode(Presence.Mode.dnd);
			getConnection().sendPacket(presence);
			L.v(LOGTAG, "设置忙碌");
			L.d(LOGTAG, "忙碌--->" + presence.toXML());
			break;
		case 3:
			presence = new Presence(Presence.Type.available);
			presence.setMode(Presence.Mode.away);
			getConnection().sendPacket(presence);
			L.v(LOGTAG, "设置离开");
			L.d(LOGTAG, "离开--->" + presence.toXML());
			break;
		case 4:
			Roster roster = getConnection().getRoster();
			Collection<RosterEntry> entries = roster.getEntries();
			for (RosterEntry entry : entries) {
				presence = new Presence(Presence.Type.unavailable);
				presence.setPacketID(Packet.ID_NOT_AVAILABLE);
				presence.setFrom(getConnection().getUser());
				presence.setTo(entry.getUser());
				getConnection().sendPacket(presence);
				L.d(LOGTAG, entry.getUser() + "--->" + presence.toXML());
			}
			//向同一用户的其他客户端发送隐身状态
			presence = new Presence(Presence.Type.unavailable);
			presence.setPacketID(Packet.ID_NOT_AVAILABLE);
			presence.setFrom(getConnection().getUser());
			presence.setTo(StringUtils.parseBareAddress(getConnection().getUser()));
			getConnection().sendPacket(presence);
			L.v(LOGTAG, "设置隐身");
			break;
		case 5:
			presence = new Presence(Presence.Type.unavailable);
			getConnection().sendPacket(presence);
			L.v(LOGTAG, "设置离线");
			break;
		default:
			break;
		}
	}

	/**
	 * 删除当前账户
	 * 
	 * @return
	 */
	public boolean deleteAccount() {
		try {
			getConnection().getAccountManager().deleteAccount();
			return true;
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获得所有组
	 * 
	 * @param roster
	 * @return 所有组集合
	 */
	public List<RosterGroup> getGroups(Roster roster) {
		List<RosterGroup> groupList = new ArrayList<RosterGroup>();
		Collection<RosterGroup> rosterGroups = roster.getGroups();
		Iterator<RosterGroup> i = rosterGroups.iterator();
		while (i.hasNext()) {
			groupList.add(i.next());
		}
		return groupList;
	}

	/**
	 * 添加分组
	 * 
	 * @param roster
	 * @param groupName 分组名
	 * @return
	 */
	public boolean addGroup(Roster roster, String groupName) {
		try {
			roster.createGroup(groupName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获得某个组里面的所有好友
	 * 
	 * @param roster
	 * @param groupName 组名
	 * @return
	 */
	public List<RosterEntry> getEntriesByGroup(Roster roster, String groupName) {
		List<RosterEntry> entriesList = new ArrayList<RosterEntry>();
		RosterGroup rosterGroup = roster.getGroup(groupName);
		Collection<RosterEntry> rosterEntries = rosterGroup.getEntries();
		Iterator<RosterEntry> i = rosterEntries.iterator();
		while (i.hasNext()) {
			entriesList.add(i.next());
		}
		return entriesList;
	}

	/**
	 * 获取所有好友信息
	 * 
	 * @param roster
	 * @return
	 */
	public List<RosterEntry> getAllEntries(Roster roster) {
		List<RosterEntry> entriesList = new ArrayList<RosterEntry>();
		Collection<RosterEntry> rosterEntries = roster.getEntries();
		Iterator<RosterEntry> i = rosterEntries.iterator();
		while (i.hasNext()) {
			entriesList.add(i.next());
		}
		return entriesList;
	}

	/**
	 * 获取用户VCard信息
	 * 
	 * @param user
	 * @return
	 * @throws XMPPException
	 */
	public VCard getUserVCard(String user) throws XMPPException {
		VCard vCard = new VCard();
		vCard.load(getConnection(), user);
		return vCard;
	}

	/**
	 * 获取用户头像信息
	 * 
	 * @param user
	 * @return
	 */
	public Drawable getUserImage(String user) {
		ByteArrayInputStream bais = null;
		try {
			VCard vCard = new VCard();
			//加入这句，解决没有No VCard
			ProviderManager.getInstance().addIQProvider("vCard", "vcard-temp", new org.jivesoftware.smackx.provider.VCardProvider());
			vCard.load(getConnection(), user + "@" + getConnection().getServiceName());
			if (vCard == null || vCard.getAvatar() == null) {
				return null;
			}
			bais = new ByteArrayInputStream(vCard.getAvatar());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (bais == null) {
			return null;
		}
		return FormatTools.getInstance().InputStream2Drawable(bais);
	}

	/**
	 * 添加好友--无分组
	 * 
	 * @param roster
	 * @param userName
	 * @param name
	 * @return
	 */
	public boolean addUser(Roster roster, String userName, String name) {
		try {
			roster.createEntry(userName, name, null);
			return true;
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 添加好友--有分组
	 * 
	 * @param roster
	 * @param userName
	 * @param name
	 * @param groupName
	 * @return
	 */
	public boolean addUser(Roster roster, String userName, String name, String groupName) {
		try {
			roster.createEntry(userName, name, new String[] { groupName });
			return true;
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 删除好友
	 * 
	 * @param roster
	 * @param userName
	 * @return
	 */
	public boolean removeUser(Roster roster, String userName) {
		try {
			if (userName.contains("@")) {
				userName = userName.split("@")[0];
			}
			RosterEntry entry = roster.getEntry(userName);
			L.i(LOGTAG, "删除好友--->" + userName);
			L.d(LOGTAG, "User." + (roster.getEntry(userName) == null));
			roster.removeEntry(entry);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 查询用户
	 * 
	 * @param serverDomain
	 * @param userName
	 * @return
	 * @throws XMPPException
	 */
	public List<User> searchUsers(String serverDomain, String userName) throws XMPPException {
		List<User> results = new ArrayList<User>();
		L.i(LOGTAG, "查询开始--->Host=" + getConnection().getHost() + "  ServiceName=" + getConnection().getServiceName());

		UserSearchManager usm = new UserSearchManager(getConnection());
		Form searchForm = usm.getSearchForm(serverDomain);
		Form answerForm = searchForm.createAnswerForm();
		answerForm.setAnswer("userAccount", true);
		answerForm.setAnswer("userPhote", userName);
		ReportedData data = usm.getSearchResults(answerForm, serverDomain);

		Iterator<Row> i = data.getRows();
		Row row = null;
		User user = null;
		while (i.hasNext()) {
			user = new User();
			row = i.next();
			user.setUserAccount(row.getValues("userAccount").next().toString());
			user.setUserPhote(row.getValues("userPhote").next().toString());
			results.add(user);
			//若存在，则有返回，userName一定非空，其他两个若是有设，一定非空
		}
		return results;
	}

	/**
	 * 修改用户头像
	 * 
	 * @param f
	 * @throws XMPPException
	 * @throws IOException
	 */
	public void changeUserImage(File f) throws XMPPException, IOException {
		VCard vCard = new VCard();
		vCard.load(getConnection());
		byte[] bytes = getFileBytes(f);

		String encodeImage = StringUtils.encodeBase64(bytes);
		vCard.setAvatar(bytes, encodeImage);
		vCard.setEncodedImage(encodeImage);
		vCard.setField("PHOTO", "<TYPE>image/jpg</TYPE><BINVAL>" + encodeImage + "</BINVAL>", true);

		ByteArrayInputStream bais = new ByteArrayInputStream(vCard.getAvatar());
		FormatTools.getInstance().InputStream2Bitmap(bais);
		vCard.save(getConnection());
	}

	/**
	 * 获取一个文件的字符流
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private byte[] getFileBytes(File file) throws IOException {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			int bytes = (int) file.length();
			byte[] buffer = new byte[bytes];
			int readBytes = bis.read(buffer);
			if (readBytes != buffer.length) {
				throw new IOException("Entire file not read");
			}
			return buffer;
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
	}

	/**
	 * 创建房间
	 * 
	 * @param roomName 房间名称
	 */
	public void createRoom(String roomName) {
		if (!isConnected()) {
			return;
		}
		try {
			// 创建一个MultiUserChat  
			MultiUserChat muc = new MultiUserChat(getConnection(), roomName + "@conference." + getConnection().getServiceName());
			// 创建聊天室  
			muc.create(roomName); // roomName房间的名字  
			// 获得聊天室的配置表单  
			Form form = muc.getConfigurationForm();
			// 根据原始表单创建一个要提交的新表单。  
			Form submitForm = form.createAnswerForm();
			// 向要提交的表单添加默认答复  
			for (Iterator<FormField> fields = form.getFields(); fields.hasNext();) {
				FormField field = (FormField) fields.next();
				if (!FormField.TYPE_HIDDEN.equals(field.getType()) && field.getVariable() != null) {
					// 设置默认值作为答复  
					submitForm.setDefaultAnswer(field.getVariable());
				}
			}
			// 设置聊天室的新拥有者  
			List<String> owners = new ArrayList<String>();
			owners.add(getConnection().getUser());// 用户JID  
			submitForm.setAnswer("muc#roomconfig_roomowners", owners);
			// 设置聊天室是持久聊天室，即将要被保存下来  
			submitForm.setAnswer("muc#roomconfig_persistentroom", false);
			// 房间仅对成员开放  
			submitForm.setAnswer("muc#roomconfig_membersonly", false);
			// 允许占有者邀请其他人  
			submitForm.setAnswer("muc#roomconfig_allowinvites", true);
			// 进入是否需要密码  
			//submitForm.setAnswer("muc#roomconfig_passwordprotectedroom", true);  
			// 设置进入密码  
			//submitForm.setAnswer("muc#roomconfig_roomsecret", "password");  
			// 能够发现占有者真实 JID 的角色  
			// submitForm.setAnswer("muc#roomconfig_whois", "anyone");  
			// 登录房间对话  
			submitForm.setAnswer("muc#roomconfig_enablelogging", true);
			// 仅允许注册的昵称登录  
			submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
			// 允许使用者修改昵称  
			submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);
			// 允许用户注册房间  
			submitForm.setAnswer("x-muc#roomconfig_registration", false);
			// 发送已完成的表单（有默认值）到服务器来配置聊天室  
			submitForm.setAnswer("muc#roomconfig_passwordprotectedroom", true);
			// 发送已完成的表单（有默认值）到服务器来配置聊天室  
			muc.sendConfigurationForm(submitForm);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加入会议室
	 * 
	 * @param user 昵称
	 * @param password 会议室密码
	 * @param roomsName 会议室名
	 */
	public MultiUserChat joinMultiUserChat(String user, String password, String roomsName) {
		try {
			// 使用XMPPConnection创建一个MultiUserChat窗口  
			MultiUserChat muc = new MultiUserChat(getConnection(), roomsName + "@conference." + getConnection().getServiceName());
			// 聊天室服务将会决定要接受的历史记录数量  
			DiscussionHistory history = new DiscussionHistory();
			history.setMaxStanzas(0);
			//history.setSince(new Date());  
			// 用户加入聊天室  
			muc.join(user, password, history, SmackConfiguration.getPacketReplyTimeout());
			L.i(LOGTAG, "会议室加入成功........");
			return muc;
		} catch (XMPPException e) {
			e.printStackTrace();
			L.i(LOGTAG, "会议室加入失败........");
			return null;
		}
	}

	/**
	 * 查询会议室成员名字
	 * 
	 * @param muc
	 */
	public List<String> findMulitUser(MultiUserChat muc) {
		List<String> listUser = new ArrayList<String>();
		Iterator<String> it = muc.getOccupants();
		//遍历出聊天室人员名称  
		while (it.hasNext()) {
			// 聊天室成员名字  
			String name = StringUtils.parseResource(it.next());
			listUser.add(name);
		}
		return listUser;
	}

	/**
	 * 获取服务器上所有会议室
	 * 
	 * @return
	 * @throws XMPPException
	 */
	public List<FriendRooms> getConferenceRoom() throws XMPPException {
		List<FriendRooms> list = new ArrayList<FriendRooms>();
		new ServiceDiscoveryManager(getConnection());
		if (!MultiUserChat.getHostedRooms(getConnection(), getConnection().getServiceName()).isEmpty()) {

			for (HostedRoom k : MultiUserChat.getHostedRooms(getConnection(), getConnection().getServiceName())) {

				for (HostedRoom j : MultiUserChat.getHostedRooms(getConnection(), k.getJid())) {
					RoomInfo info2 = MultiUserChat.getRoomInfo(getConnection(), j.getJid());
					if (j.getJid().indexOf("@") > 0) {

						FriendRooms friendrooms = new FriendRooms();
						friendrooms.setName(j.getName());//聊天室的名称  
						friendrooms.setJid(j.getJid());//聊天室JID  
						friendrooms.setOccupants(info2.getOccupantsCount());//聊天室中占有者数量  
						friendrooms.setDescription(info2.getDescription());//聊天室的描述  
						friendrooms.setSubject(info2.getSubject());//聊天室的主题  
						list.add(friendrooms);
					}
				}
			}
		}
		return list;
	}

	//	五、监听会议室的消息
	//	添加会议室消息监听 ：
	//multiChat.addMessageListener(new multiListener());
	/**
	 * 会议室信息监听事件
	 * 
	 * @author Administrator
	 * 
	 */
	public class multiListener implements PacketListener {
		@Override
		public void processPacket(Packet packet) {
			Message message = (Message) packet;
			// 接收来自聊天室的聊天信息  
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			MucHistory mh = new MucHistory();
			//			mh.setUserAccount(account);
			String from = StringUtils.parseResource(message.getFrom());
			String fromRoomName = StringUtils.parseName(message.getFrom());
			mh.setMhRoomName(fromRoomName);
			mh.setFriendAccount(from);
			mh.setMhInfo(message.getBody());
			mh.setMhTime(time);
			mh.setMhType("left");
		}
	}

	//六、发送会议室消息
	//    multiChat.sendMessage(str);//multiChat 为聊天室对象

	//	七、监听会议室状态（成员的进入、离开等）
	//	添加监听事件：
	//    multiChat.addParticipantStatusListener(new ParticipantStatus());

	/**
	 * 会议室状态监听事件
	 * 
	 * @author Administrator
	 * 
	 */
	class ParticipantStatus implements ParticipantStatusListener {

		@Override
		public void adminGranted(String arg0) {
			// TODO Auto-generated method stub  

		}

		@Override
		public void adminRevoked(String arg0) {
			// TODO Auto-generated method stub  

		}

		@Override
		public void banned(String arg0, String arg1, String arg2) {
			// TODO Auto-generated method stub  

		}

		@Override
		public void joined(String participant) {
			System.out.println(StringUtils.parseResource(participant) + " has joined the room.");
		}

		@Override
		public void kicked(String arg0, String arg1, String arg2) {
			// TODO Auto-generated method stub  

		}

		@Override
		public void left(String participant) {
			// TODO Auto-generated method stub  
			System.out.println(StringUtils.parseResource(participant) + " has left the room.");

		}

		@Override
		public void membershipGranted(String arg0) {
			// TODO Auto-generated method stub  

		}

		@Override
		public void membershipRevoked(String arg0) {
			// TODO Auto-generated method stub  

		}

		@Override
		public void moderatorGranted(String arg0) {
			// TODO Auto-generated method stub  

		}

		@Override
		public void moderatorRevoked(String arg0) {
			// TODO Auto-generated method stub  

		}

		@Override
		public void nicknameChanged(String participant, String newNickname) {
			System.out.println(StringUtils.parseResource(participant) + " is now known as " + newNickname + ".");
		}

		@Override
		public void ownershipGranted(String arg0) {
			// TODO Auto-generated method stub  

		}

		@Override
		public void ownershipRevoked(String arg0) {
			// TODO Auto-generated method stub  

		}

		@Override
		public void voiceGranted(String arg0) {
			// TODO Auto-generated method stub  

		}

		@Override
		public void voiceRevoked(String arg0) {
			// TODO Auto-generated method stub  

		}
	}

	//	一、单人聊天
	//	1）发送消息：
	//	首先要获取一个聊天窗口，getConnection()为获取连接connection的方法，调用getFriendChat()获取

	private Map<String, Chat> chatManage = new HashMap<String, Chat>();// 聊天窗口管理map集合

	/**
	 * 获取或创建聊天窗口
	 * 
	 * @param friend 好友名
	 * @param listenter 聊天監聽器
	 * @return
	 */
	public Chat getFriendChat(String friend, MessageListener listenter) {
		if (getConnection() == null)
			return null;
		/** 判断是否创建聊天窗口 */
		for (String fristr : chatManage.keySet()) {
			if (fristr.equals(friend)) {
				// 存在聊天窗口，则返回对应聊天窗口
				return chatManage.get(fristr);
			}
		}
		/** 创建聊天窗口 */
		Chat chat = getConnection().getChatManager().createChat(friend + "@" + getConnection().getServiceName(), listenter);
		/** 添加聊天窗口到chatManage */
		chatManage.put(friend, chat);
		return chat;
	}

	//	friend为好友名，不是JID；listener 监听器可以传null，利用聊天窗口对象调用sendMessage发送消息
	//	这里sendMessage我传的是一个JSON字符串，以便更灵活的控制，发送消息完成~
	//	Chat chat = getFriendChat(friend,null);
	//	try {
	//	 	String msgjson = "{\"messageType\":\""+messageType+"\",\"chanId\":\""+chanId+"\",\"chanName\":\""+chanName+"\"}";
	//		chat.sendMessage(msgjson);
	//	} catch (XMPPException e) {
	//		e.printStackTrace();
	//	}

	//	添加监听，最好是放在登录方法中，在关闭连接方法中，移除监听，原因是为了避免重复添加监听，接受重复消息
	//	退出程序应该关闭连接，移除监听，该监听可以接受所有好友的消息，很方便吧~
	//	TaxiChatManagerListener chatManagerListener = new TaxiChatManagerListener();  
	//	getConnection().getChatManager().addChatListener(chatManagerListener); 

	//	connection.getChatManager().removeChatListener(chatManagerListener);

	//	二、多人聊天（会议室）
	//	1）、发送消息
	//	首先要获取会议室对象（MultiUserChat），有两种方法获取
	//	两个方法前面都有讲到，这里再回顾一下
	//	1、创建会议室
	/**
	 * 创建房间
	 * 
	 * @param user
	 * @param roomName 房间名称
	 * @param password
	 * @return
	 */
	public MultiUserChat createRoom(String user, String roomName, String password) {
		if (getConnection() == null)
			return null;

		MultiUserChat muc = null;
		try {
			// 创建一个MultiUserChat
			muc = new MultiUserChat(getConnection(), roomName + "@conference." + getConnection().getServiceName());
			// 创建聊天室
			muc.create(roomName);
			// 获得聊天室的配置表单
			Form form = muc.getConfigurationForm();
			// 根据原始表单创建一个要提交的新表单。
			Form submitForm = form.createAnswerForm();
			// 向要提交的表单添加默认答复
			for (Iterator<FormField> fields = form.getFields(); fields.hasNext();) {
				FormField field = (FormField) fields.next();
				if (!FormField.TYPE_HIDDEN.equals(field.getType()) && field.getVariable() != null) {
					// 设置默认值作为答复
					submitForm.setDefaultAnswer(field.getVariable());
				}
			}
			// 设置聊天室的新拥有者
			List<String> owners = new ArrayList<String>();
			owners.add(getConnection().getUser());// 用户JID
			submitForm.setAnswer("muc#roomconfig_roomowners", owners);
			// 设置聊天室是持久聊天室，即将要被保存下来
			submitForm.setAnswer("muc#roomconfig_persistentroom", true);
			// 房间仅对成员开放
			submitForm.setAnswer("muc#roomconfig_membersonly", false);
			// 允许占有者邀请其他人
			submitForm.setAnswer("muc#roomconfig_allowinvites", true);
			if (!password.equals("")) {
				// 进入是否需要密码
				submitForm.setAnswer("muc#roomconfig_passwordprotectedroom", true);
				// 设置进入密码
				submitForm.setAnswer("muc#roomconfig_roomsecret", password);
			}
			// 能够发现占有者真实 JID 的角色
			// submitForm.setAnswer("muc#roomconfig_whois", "anyone");
			// 登录房间对话
			submitForm.setAnswer("muc#roomconfig_enablelogging", true);
			// 仅允许注册的昵称登录
			submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
			// 允许使用者修改昵称
			submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);
			// 允许用户注册房间
			submitForm.setAnswer("x-muc#roomconfig_registration", false);
			// 发送已完成的表单（有默认值）到服务器来配置聊天室
			muc.sendConfigurationForm(submitForm);
		} catch (XMPPException e) {
			e.printStackTrace();
			return null;
		}
		return muc;
	}

	//	/**
	//	 * 加入会议室
	//	 * 
	//	 * @param user 昵称
	//	 * @param password 会议室密码
	//	 * @param roomsName 会议室名
	//	 */
	//	public MultiUserChat joinMultiUserChat(String user, String roomsName, String password) {
	//		if (getConnection() == null)
	//			return null;
	//		try {
	//			// 使用XMPPConnection创建一个MultiUserChat窗口
	//			MultiUserChat muc = new MultiUserChat(getConnection(), roomsName + "@conference." + getConnection().getServiceName());
	//			// 聊天室服务将会决定要接受的历史记录数量
	//			DiscussionHistory history = new DiscussionHistory();
	//			history.setMaxChars(0);
	//			// history.setSince(new Date());
	//			// 用户加入聊天室
	//			muc.join(user, password, history, SmackConfiguration.getPacketReplyTimeout());
	//			L.i("MultiUserChat", "会议室【" + roomsName + "】加入成功........");
	//			return muc;
	//		} catch (XMPPException e) {
	//			e.printStackTrace();
	//			L.i("MultiUserChat", "会议室【" + roomsName + "】加入失败........");
	//			return null;
	//		}
	//	}

	//	调用这个两个方法都可以获取到MultiUserChat，根据需求选择一个就可以。
	//	利用MultiUserChat对象调用sendMessage()方法即可，很容易吧~
	//	try {  
	//	    multiUserChat.sendMessage(message);  
	//	} catch (XMPPException e) {  
	//	    e.printStackTrace();  
	//	}

	//	添加监听器，每个会议室聊天对象都要添加一个消息监听器,为了避免重复监听
	//	应该把会议室聊天对象放在一个集合当中，需要用到的时候取出来用即可。
	//	multiUserChat.addMessageListener(new TaxiMultiListener());

	//	其次就是给连接设置监听器了，最好放在登录方法里，关闭连接方法里移除监听
	//	// 添加連接監聽  
	//	TaxiConnectionListener connectionListener = new TaxiConnectionListener();  
	//	getConnection().addConnectionListener(connectionListener);  

	//	connection.removeConnectionListener(connectionListener);
}
