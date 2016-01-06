package com.nowellpoint.aws.model.data;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class Organization implements Serializable {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -1315510190045597737L;
	
	/**
	 * 
	 */
	
	@JsonSerialize(using=ObjectIdSerializer.class)
	@JsonDeserialize(using=ObjectIdDeserializer.class)
	@JsonProperty("_id")
	private ObjectId id;
	
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

	@JsonProperty(value="Id")
	private String salesforceId;

	private String groupHref;
	
	public Organization() {
		
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
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

	public String getSalesforceId() {
		return salesforceId;
	}

	public void setSalesforceId(String salesforceId) {
		this.salesforceId = salesforceId;
	}

	public String getGroupHref() {
		return groupHref;
	}

	public void setGroupHref(String groupHref) {
		this.groupHref = groupHref;
	}
}