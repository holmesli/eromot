package com.app.tomore.beans;

import java.io.Serializable;

public class GeneralBLModel implements Serializable{
	private String AdID;
	private String Title;
	private String Content;
	private String MemberID;
	private String PostDate;
	private String UpdateDate;
	private String ServiceRegion;
	private String Address;
	private String Longitude;
	private String Latitude;
	private String AccountName;
	private String Email;
	private String Phone1;
	private String Phone2;
	private String Role;
	private String Language_M;
	private String Language_C;
	private String Language_E;
	private String Contact;
	private String Url;
	private String HotLevel;
	
	public GeneralBLModel() {
		// TODO Auto-generated constructor stub
	}
	public String getAdID() {
		return AdID;
	}
	
	public void setAdID(String ADID) {
		AdID = ADID;
	}
	
	public String getTitle() {
		return Title;
	}
	
	public void setTitle(String Title) {
		AdID = Title;
	}
	
	public String getContent() {
		return Content;
	}
	
	public void setContent(String content) {
		Content = content;
	}
	
	public String getMemberID() {
		return MemberID;
	}
	
	public void setMemberID(String Memberid) {
		MemberID = Memberid;
	}
	
	public String getPostDate() {
		return PostDate;
	}
	
	public void setPostdate(String Postdate) {
		PostDate = Postdate;
	}
	
	public String getUpdateDate() {
		return UpdateDate;
	}
	
	public void setUpdateDate(String Updatedate) {
		UpdateDate = Updatedate;
	}
	
	public String getServiceRegion() {
		return ServiceRegion;
	}
	
	public void setServiceRegion(String serviceRegion) {
		ServiceRegion = serviceRegion;
	}
	
	public String getAddress() {
		return Address;
	}
	public String getLongitude() {
		return Longitude;
	}
	
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	
	public String getLatitude() {
		return Latitude;
	}
	
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	
	public String getAccountName() {
		return AccountName;
	}
	
	public void setAccountName(String accountName) {
		AccountName = accountName;
	}
	
	public String getEmail() {
		return Email;
	}
	
	public void setEmail(String email) {
		Email = email;
	}
	
	public String getPhone1() {
		return Phone1;
	}
	
	public void setPhone1(String phone1) {
		Phone1 = phone1;
	}
	
	public String getPhone2() {
		return Phone2;
	}
	
	public void setPhone2(String phone2) {
		Phone2 = phone2;
	}
	
	public String getRole() {
		return Role;
	}
	
	public void SetRole(String role) {
		Role = role;
	}
	
	public String getLanguage_M() {
		return Language_M;
	}
	
	public void setLanguage_M(String language_M) {
		Language_M = language_M;
	}
	
	public String getLanguage_C() {
		return Language_C;
	}
	
	public void setLanguage_C(String language_C) {
		Language_C = language_C;
	}
	
	public String getLanguage_E() {
		return Language_E;
	}
	
	public void setLanguage_E(String language_E) {
		Language_E = language_E;
	}
	
	public String getContact() {
		return Contact;
	}
	
	public void setContact(String contact) {
		Contact = contact;
	}
	
	public String getUrl() {
		return Url;
	}
	
	public void setUrl(String url) {
		Url = url;
	}
	
	public String getHotLevel() {
		return HotLevel;
	}
	
	public void setHotLevelD(String hotlevel) {
		HotLevel = hotlevel;
	}
}
