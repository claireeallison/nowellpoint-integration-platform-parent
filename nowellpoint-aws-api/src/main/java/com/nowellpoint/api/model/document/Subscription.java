package com.nowellpoint.api.model.document;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nowellpoint.mongodb.document.DateDeserializer;
import com.nowellpoint.mongodb.document.DateSerializer;

public class Subscription implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -7569793449815113870L;
	
	private String planName;
	
	private String planCode;
	
	private String currencyIsoCode;
	
	private String currencySymbol;
	
	private Double unitPrice;
	
	private String billingFrequency;
	
	private String billingFrequencyPer;
	
	private String billingFrequencyUnit;
	
	private String billingFrequencyQuantity;
	
	private Double proratedDailyUnitPrice;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date addedOn;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date updatedOn;
	
	public Subscription() {
		
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
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

	public String getCurrencyIsoCode() {
		return currencyIsoCode;
	}

	public void setCurrencyIsoCode(String currencyIsoCode) {
		this.currencyIsoCode = currencyIsoCode;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getBillingFrequency() {
		return billingFrequency;
	}

	public void setBillingFrequency(String billingFrequency) {
		this.billingFrequency = billingFrequency;
	}

	public String getBillingFrequencyPer() {
		return billingFrequencyPer;
	}

	public void setBillingFrequencyPer(String billingFrequencyPer) {
		this.billingFrequencyPer = billingFrequencyPer;
	}

	public String getBillingFrequencyUnit() {
		return billingFrequencyUnit;
	}

	public void setBillingFrequencyUnit(String billingFrequencyUnit) {
		this.billingFrequencyUnit = billingFrequencyUnit;
	}

	public String getBillingFrequencyQuantity() {
		return billingFrequencyQuantity;
	}

	public void setBillingFrequencyQuantity(String billingFrequencyQuantity) {
		this.billingFrequencyQuantity = billingFrequencyQuantity;
	}

	public Double getProratedDailyUnitPrice() {
		return proratedDailyUnitPrice;
	}

	public void setProratedDailyUnitPrice(Double proratedDailyUnitPrice) {
		this.proratedDailyUnitPrice = proratedDailyUnitPrice;
	}
}