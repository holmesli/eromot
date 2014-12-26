package com.app.tomore.beans;

public class ImageAndTexts {

	private String imageUrl;
	private String title;
	private String des;
	private String cardType;

	public ImageAndTexts(String imageUrl, String title, String des,
			String cardType) {
		this.imageUrl = imageUrl;
		this.title = title;
		this.des = des;
		this.cardType = cardType;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public String getDes() {
		return des;
	}

	public String getCardType() {
		return cardType;
	}

}
