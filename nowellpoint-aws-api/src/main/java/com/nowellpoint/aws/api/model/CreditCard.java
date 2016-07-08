package com.nowellpoint.aws.api.model;

import java.io.Serializable;

public class CreditCard implements Serializable {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -4734047848125348896L;

	/**
	 * 
	 */
	
	private String type;
	
	/**
	 * 
	 */
	
	private String firstName;
	
	/**
	 * 
	 */
	
	private String lastName;
	
	/**
	 * 
	 */
	
	private String number;
	
	/**
	 * 
	 */
	
	private String month;
	
	/**
	 * 
	 */
	
	private String year;
	
	/**
	 * 
	 */
	
	private String lastFour;
	
	/**
	 * 
	 */
	
	private Address address;

	public CreditCard() {
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getLastFour() {
		return lastFour;
	}

	public void setLastFour(String lastFour) {
		this.lastFour = lastFour;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}