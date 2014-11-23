package net.cgt.weixin.domain;

/**
 * 聊天室
 * 
 * @author lijian
 * @date 2014-11-23
 */
public class FriendRooms {
	/** 聊天室的名称 **/
	private String name;
	/** 聊天室JID **/
	private String jid;
	/** 聊天室中占有者数量 **/
	private int occupants;
	/** 聊天室的描述 **/
	private String description;
	/** 聊天室的主题 **/
	private String subject;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public int getOccupants() {
		return occupants;
	}

	public void setOccupants(int occupants) {
		this.occupants = occupants;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
