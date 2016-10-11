package com.nowellpoint.api.model.document;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nowellpoint.api.model.codec.PlanCodec;
import com.nowellpoint.mongodb.annotation.Document;
import com.nowellpoint.mongodb.document.MongoDocument;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collectionName="plans", codec=PlanCodec.class)
public class Plan extends MongoDocument {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -7569793449815113870L;
	
	private String localeSidKey;
	
	private String languageLocaleKey;
	
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
	
	private Boolean isActive;
	
	private Set<String> features;
	
	public Plan() {
		
	}

	public String getLocaleSidKey() {
		return localeSidKey;
	}

	public void setLocaleSidKey(String localeSidKey) {
		this.localeSidKey = localeSidKey;
	}

	public String getLanguageLocaleKey() {
		return languageLocaleKey;
	}

	public void setLanguageLocaleKey(String languageLocaleKey) {
		this.languageLocaleKey = languageLocaleKey;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<String> getFeatures() {
		return features;
	}

	public void setFeatures(Set<String> features) {
		this.features = features;
	}	
}