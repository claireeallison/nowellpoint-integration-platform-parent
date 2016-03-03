package com.nowellpoint.www.app.model;

import java.io.Serializable;

public class ServiceProvider implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1932030123010671977L;
	
	private String name;
	
	private String account;
	
	private Boolean isActive;
	
	private String authenticate;
	
	private Double price;
	
	public ServiceProvider() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getAuthenticate() {
		return authenticate;
	}

	public void setAuthenticate(String authenticate) {
		this.authenticate = authenticate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}