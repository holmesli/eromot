package com.app.tomore.beans;

import java.io.Serializable;

import org.json.JSONObject;


import android.content.ContentValues;
import android.database.Cursor;

public class CardModel extends BaseBean implements Serializable, Comparable<CardModel>  {
	
	public String getCardID() {
		return CardID;
	}

	public void setCardID(String cardID) {
		CardID = cardID;
	}

	public String getCardType() {
		return CardType;
	}

	public void setCardType(String cardType) {
		CardType = cardType;
	}

	public String getCardTitle() {
		return CardTitle;
	}

	public void setCardTitle(String cardTitle) {
		CardTitle = cardTitle;
	}

	public String getCardBarcode() {
		return CardBarcode;
	}

	public void setCardBarcode(String cardBarcode) {
		CardBarcode = cardBarcode;
	}

	public String getCardNumber() {
		return CardNumber;
	}

	public void setCardNumber(String cardNumber) {
		CardNumber = cardNumber;
	}

	public String getCardDes() {
		return CardDes;
	}

	public void setCardDes(String cardDes) {
		CardDes = cardDes;
	}

	public String getFrontViewImage() {
		return FrontViewImage;
	}

	public void setFrontViewImage(String frontViewImage) {
		FrontViewImage = frontViewImage;
	}

	public String getBackViewImage() {
		return BackViewImage;
	}

	public void setBackViewImage(String backViewImage) {
		BackViewImage = backViewImage;
	}

	public String getcoupon() {
		return coupon;
	}

	public void setcoupon(String Coupon) {
		coupon = Coupon;
	}

	

	private String CardID;
	private String  CardType;
	private String  CardTitle;
	private String  CardBarcode;
	private String  CardNumber;
	private String  CardDes;
	private String  FrontViewImage;
	private String  BackViewImage;
	private String  coupon;


	@Override
	public Object parseJSON(JSONObject jsonObj) {
		// TODO Auto-generated method stub
			
		return jsonObj;
	}

	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object cursorToBean(Cursor cursor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContentValues beanToValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(CardModel another) {
		// TODO Auto-generated method stub
		return 0;
	}

}
