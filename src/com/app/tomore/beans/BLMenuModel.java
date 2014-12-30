package com.app.tomore.beans;

import java.io.Serializable;

public class BLMenuModel implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1574771534544000171L;
	public String getItemID() {
		return ItemID;
	}
	public void setItemID(String itemID) {
		ItemID = itemID;
	}
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	public String getItemImage() {
		return ItemImage;
	}
	public void setItemImage(String itemImage) {
		ItemImage = itemImage;
	}
	public String getItemPrice() {
		return ItemPrice;
	}
	public void setItemPrice(String itemPrice) {
		ItemPrice = itemPrice;
	}
	public String getItemType() {
		return ItemType;
	}
	public void setItemType(String itemType) {
		ItemType = itemType;
	}
	private String ItemID;
	private String ItemName;
	private String ItemImage;
	private String ItemPrice;
	private String ItemType;
}
