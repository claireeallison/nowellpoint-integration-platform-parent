package com.nowellpoint.client.sforce.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization implements Serializable {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -1315510190045597737L;
	
	@JsonProperty(value="Id")
	private String id;
	
	@JsonProperty(value="attributes")
	private Attributes attributes;
	
	@JsonProperty(value="Division")
	private String division;
	
	@JsonProperty(value="Fax")
	private String fax;
	
	@JsonProperty(value="DefaultLocaleSidKey")
	private String defaultLocaleSidKey;
	
	@JsonProperty(value="FiscalYearStartMonth")
	private String fiscalYearStartMonth;
	
	@JsonProperty(value="InstanceName")
	private String instanceName;
	
	@JsonProperty(value="IsSandbox")
	private Boolean isSandbox;
	
	@JsonProperty(value="LanguageLocaleKey")
	private String languageLocaleKey;
	
	@JsonProperty(value="Name")
	private String name;
	
	@JsonProperty(value="OrganizationType")
	private String organizationType;
	
	@JsonProperty(value="Phone")
	private String phone;
	
	@JsonProperty(value="PrimaryContact")
	private String primaryContact;
	
	@JsonProperty(value="UsesStartDateAsFiscalYearName")
	private Boolean usesStartDateAsFiscalYearName;
	
	public Organization() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getDefaultLocaleSidKey() {
		return defaultLocaleSidKey;
	}

	public void setDefaultLocaleSidKey(String defaultLocaleSidKey) {
		this.defaultLocaleSidKey = defaultLocaleSidKey;
	}

	public String getFiscalYearStartMonth() {
		return fiscalYearStartMonth;
	}

	public void setFiscalYearStartMonth(String fiscalYearStartMonth) {
		this.fiscalYearStartMonth = fiscalYearStartMonth;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public Boolean getIsSandbox() {
		return isSandbox;
	}

	public void setIsSandbox(Boolean isSandbox) {
		this.isSandbox = isSandbox;
	}

	public String getLanguageLocaleKey() {
		return languageLocaleKey;
	}

	public void setLanguageLocaleKey(String languageLocaleKey) {
		this.languageLocaleKey = languageLocaleKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	public Boolean getUsesStartDateAsFiscalYearName() {
		return usesStartDateAsFiscalYearName;
	}

	public void setUsesStartDateAsFiscalYearName(Boolean usesStartDateAsFiscalYearName) {
		this.usesStartDateAsFiscalYearName = usesStartDateAsFiscalYearName;
	}
}