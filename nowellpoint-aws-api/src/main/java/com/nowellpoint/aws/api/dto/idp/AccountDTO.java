package com.nowellpoint.aws.api.dto.idp;

import java.io.Serializable;

public class AccountDTO implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 4359339056490011294L;
	
	private String href;
	
	private String username;
	
	private String email;
	
	private String fullName;
	
	private String givenName;
	
	private String middleName;
	
	private String surname;
	
	private String status;
	
	private String password;
	
	private EmailVerificationToken emailVerificationToken;
	
	private CustomData customData;
	
	private Tenant tenant;
	
	private Groups groups;
	
	private GroupMemberships groupMemberships;
	
	private Directory directory;
	  
	public AccountDTO() {
		
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public CustomData getCustomData() {
		return customData;
	}

	public void setCustomData(CustomData customData) {
		this.customData = customData;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public EmailVerificationToken getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(EmailVerificationToken emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	public GroupMemberships getGroupMemberships() {
		return groupMemberships;
	}

	public void setGroupMemberships(GroupMemberships groupMemberships) {
		this.groupMemberships = groupMemberships;
	}

	public Directory getDirectory() {
		return directory;
	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}
}