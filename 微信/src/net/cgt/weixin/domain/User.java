package net.cgt.weixin.domain;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户信息
 * 
 * @author lijian
 * @date 2014-11-22 11:36:28
 */
public class User implements Parcelable {
	private String userPhote;
	private String userAccount;
	private String personalizedSignature;
	private String date;
	private Drawable userImage;

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

	public Drawable getUserImage() {
		return userImage;
	}

	public void setUserImage(Drawable userImage) {
		this.userImage = userImage;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(userPhote);
		dest.writeString(userAccount);
		dest.writeString(date);
		dest.writeString(personalizedSignature);
		dest.writeValue(userImage);
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		/**
		 * Return a new point from the data in the specified parcel.
		 */
		public User createFromParcel(Parcel in) {
			User u = new User();
			u.readFromParcel(in);
			return u;
		}

		/**
		 * Return an array of rectangles of the specified size.
		 */
		public User[] newArray(int size) {
			return new User[size];
		}
	};

	/**
	 * Set the point's coordinates from the data stored in the specified parcel.
	 * To write a point to a parcel, call writeToParcel().
	 * 
	 * @param in The parcel to read the point's coordinates from
	 */
	public void readFromParcel(Parcel in) {
		userPhote = in.readString();
		userAccount = in.readString();
		date = in.readString();
		personalizedSignature = in.readString();
		ClassLoader Drawable = ClassLoader.getSystemClassLoader();
		userImage = (android.graphics.drawable.Drawable) in.readValue(Drawable);
	}
}
