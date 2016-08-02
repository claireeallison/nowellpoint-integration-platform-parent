package com.nowellpoint.aws.api.dto;

import java.util.Date;

import com.nowellpoint.aws.api.model.Address;
import com.nowellpoint.aws.api.model.Contact;

public class CreditCardDTO {

	/**
	 * 
	 */
	
	private String cardType;
	
	/**
	 * 
	 */
	
	private String cardholderName;
	
	/**
	 * 
	 */
	
	private String number;
	
	/**
	 * 
	 */
	
	private String expirationMonth;
	
	/**
	 * 
	 */
	
	private String expirationYear;
	
	/**
	 * 
	 */
	
	private String lastFour;
	
	/**
	 * 
	 */
	
	private String token;
	
	/**
	 * 
	 */
	
	private String imageUrl;
	
	/**
	 * 
	 */
	
	private Address billingAddress;
	
	/**
	 * 
	 */
	
	private Contact billingContact;
	
	/**
	 * 
	 */
	
	private Boolean primary;
	
	/**
	 * 
	 */
	
	private Date addedOn;
	
	/**
	 * 
	 */
	
	private Date updatedOn;
	
	/**
	 * 
	 */

	public CreditCardDTO() {
		
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public String getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLastFour() {
		return lastFour;
	}

	public void setLastFour(String lastFour) {
		this.lastFour = lastFour;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Contact getBillingContact() {
		return billingContact;
	}

	public void setBillingContact(Contact billingContact) {
		this.billingContact = billingContact;
	}

	public Boolean getPrimary() {
		return primary;
	}

	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
}