package com.app.tomore.beans;

import java.io.Serializable;

public class BLMenuSpecial  implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3705159450393805563L;
	
	private String SpecialImage;
	private String SpecialDes; 
	private String DiscountImage;
    private String DiscountDes;

public String getDiscountImage() {
	return DiscountImage;
}
public void setDiscountImage(String discountImage) {
	DiscountImage = discountImage;
}
public String getDiscountDes() {
	return DiscountDes;
}
public void setDiscountDes(String discountDes) {
	DiscountDes = discountDes;
}
public String getSpecialImage() {
	return SpecialImage;
}
public void setSpecialImage(String specialImage) {
	SpecialImage = specialImage;
}
public String getSpecialDes() {
	return SpecialDes;
}
public void setSpecialDes(String specialDes) {
	SpecialDes = specialDes;
}

}
