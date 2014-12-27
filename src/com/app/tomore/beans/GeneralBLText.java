package com.app.tomore.beans;

public class GeneralBLText {

	private String Title;
	private String Contact;
	private String Language_M;
	private String Language_C;
	private String Language_E;
	public GeneralBLText(String title,String Contact,String Language_M,String Language_C,String Language_E) {
		// TODO Auto-generated constructor stub
		this.Title = title;
		this.Contact = Contact;
		this.Language_M = Language_M;
		this.Language_C = Language_C;
		this.Language_E = Language_E;
	}
	public String getTitle(){
		return Title;
	}
	
	public String getContact(){
		return Contact;
	}

	public String getLanguage_M(){
		return Language_M;
	}

	public String getLanguage_C(){
		return Language_C;
	}

	public String getLanguage_E(){
		return Language_E;
	}


}
