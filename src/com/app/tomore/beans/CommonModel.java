package com.app.tomore.beans;

import java.io.Serializable;

public class CommonModel implements Serializable{

	public CommonModel() {
		// TODO Auto-generated constructor stub
	}
	
	private static final long serialVersionUID = 1L;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	private String result;

}
