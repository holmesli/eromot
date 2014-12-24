package com.app.tomore.beans;

import java.io.Serializable;

public class FollowModel implements Serializable{
	private String MemberID;
	public String getMemberID() {
		return MemberID;
	}
	public void setMemberID(String memberID) {
		MemberID = memberID;
	}
	public String getAccountName() {
		return AccountName;
	}
	public void setAccountName(String accountName) {
		AccountName = accountName;
	}
	public String getMemberImage() {
		return MemberImage;
	}
	public void setMemberImage(String memberImage) {
		MemberImage = memberImage;
	}
	public String getFollowed() {
		return followed;
	}
	public void setFollowed(String followed) {
		this.followed = followed;
	}
	private String AccountName;
	private String MemberImage;
	private String followed;
}
