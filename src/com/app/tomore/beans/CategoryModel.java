package com.app.tomore.beans;

import java.io.Serializable;


public class CategoryModel implements Serializable{
	private String IconID;
	private String Type;
	private String Name;
	private String Image;
	public String getIconID() {
		return IconID;
	}
	public void setIconID(String iconID) {
		IconID = iconID;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	
}
