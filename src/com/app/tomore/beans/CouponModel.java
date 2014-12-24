package com.app.tomore.beans;

import java.io.Serializable;

public class CouponModel implements Serializable{
	private String CouponID;
	public String getCouponID() {
		return CouponID;
	}
	public void setCouponID(String couponID) {
		CouponID = couponID;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getThumbImage() {
		return ThumbImage;
	}
	public void setThumbImage(String thumbImage) {
		ThumbImage = thumbImage;
	}
	public String getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}
	public String getExpireDate() {
		return ExpireDate;
	}
	public void setExpireDate(String expireDate) {
		ExpireDate = expireDate;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	private String Image;
	private String ThumbImage;
	private String CreateDate;
	private String ExpireDate;
	private String Title;
	private String Description;
}
