package net.cgt.weixin.domain;

/**
 * 聊天信息实体
 * 
 * @author lijian
 * @date 2014-12-29 22:33:46
 */
public class ChatMsgEntity {

	/**
	 * 文本消息
	 */
	public static final int MSGTYPE_TEXT = 1;
	/**
	 * 图片消息
	 */
	public static final int MSGTYPE_IMG = 2;
	/**
	 * 语音消息
	 */
	public static final int MSGTYPE_VOICE = 3;
	/**
	 * 分享消息
	 */
	public static final int MSGTYPE_SHARE = 4;
	/**
	 * 视频消息
	 */
	public static final int MSGTYPE_VIDEO = 5;

	/** 消息编号 **/
	private String id;

	/** 消息时间 **/
	private long time;

	/** 是否显示消息时间 **/
	private boolean isShowTime;

	/** 用户名称 **/
	private String userName;

	/** 用户头像 **/
	private int userImg;

	/** 文本消息内容 **/
	private String textMsg;

	/** 图片消息内容 **/
	private String imgMsg;

	/** 语音消息内容 **/
	private String voiceMsg;

	/** 消息类型(1：文本、2：图片、3：语音、4：分享、5：视频) **/
	private int msgType;

	/** 是否是我发送的消息 **/
	private boolean isMeMsg = true;

	public ChatMsgEntity() {
	}

	public ChatMsgEntity(String id, long time, boolean isShowTime, String userName, int userImg, String textMsg, String imgMsg, String voiceMsg, int msgType, boolean isMeMsg) {
		this.id = id;
		this.time = time;
		this.isShowTime = isShowTime;
		this.userName = userName;
		this.userImg = userImg;
		this.textMsg = textMsg;
		this.imgMsg = imgMsg;
		this.voiceMsg = voiceMsg;
		this.msgType = msgType;
		this.isMeMsg = isMeMsg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isShowTime() {
		return isShowTime;
	}

	public void setShowTime(boolean isShowTime) {
		this.isShowTime = isShowTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserImg() {
		return userImg;
	}

	public void setUserImg(int userImg) {
		this.userImg = userImg;
	}

	public String getTextMsg() {
		return textMsg;
	}

	public void setTextMsg(String textMsg) {
		this.textMsg = textMsg;
	}

	public String getImgMsg() {
		return imgMsg;
	}

	public void setImgMsg(String imgMsg) {
		this.imgMsg = imgMsg;
	}

	public String getVoiceMsg() {
		return voiceMsg;
	}

	public void setVoiceMsg(String voiceMsg) {
		this.voiceMsg = voiceMsg;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public boolean isMeMsg() {
		return isMeMsg;
	}

	public void setMeMsg(boolean isMeMsg) {
		this.isMeMsg = isMeMsg;
	}
}
