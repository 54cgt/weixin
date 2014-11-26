package net.cgt.weixin.domain;

/**
 * 用户信息
 * 
 * @author lijian
 * @date 2014-11-22 11:36:28
 */
public class User {
	private String userPhote;
	private String userAccount;
	private String personalizedSignature;
	private String date;

	public String getUserPhote() {
		return userPhote;
	}

	public void setUserPhote(String userPhote) {
		this.userPhote = userPhote;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getPersonalizedSignature() {
		return personalizedSignature;
	}

	public void setPersonalizedSignature(String personalizedSignature) {
		this.personalizedSignature = personalizedSignature;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
