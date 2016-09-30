package com.nowellpoint.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nowellpoint.client.model.sforce.Identity;
import com.nowellpoint.client.model.sforce.Organization;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesforceConnector extends Resource {
	
	private UserInfo createdBy;
	
	private UserInfo lastModifiedBy;

	private Identity identity;
	
	private Organization organization;
	
	private UserInfo owner;
	
	private String tag;
	
	private List<ServiceInstance> serviceInstances;
	
	private List<Environment> environments;
	
	public SalesforceConnector() {
		setServiceInstances(Collections.emptyList());
		setEnvironments(Collections.emptyList());
	}
	
	public SalesforceConnector(String id) {
		setId(id);
	}

	public UserInfo getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserInfo createdBy) {
		this.createdBy = createdBy;
	}

	public UserInfo getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(UserInfo lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Identity getIdentity() {
		return identity;
	}

	public void setIdentity(Identity identity) {
		this.identity = identity;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public UserInfo getOwner() {
		return owner;
	}

	public void setOwner(UserInfo owner) {
		this.owner = owner;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<ServiceInstance> getServiceInstances() {
		if (serviceInstances == null) {
			setServiceInstances(new ArrayList<ServiceInstance>());
		}
		return serviceInstances;
	}

	public void setServiceInstances(List<ServiceInstance> serviceInstances) {
		this.serviceInstances = serviceInstances;
	}

	public List<Environment> getEnvironments() {
		if (environments == null) {
			setEnvironments(new ArrayList<Environment>());
		}
		return environments;
	}

	public void setEnvironments(List<Environment> environments) {
		this.environments = environments;
	}

	@JsonIgnore
	public ServiceInstance getServiceInstance(String key) {
		return getServiceInstances()
    			.stream()
    			.filter(p -> p.getKey().equals(key))
    			.findFirst()
    			.orElse(new ServiceInstance());
	}
}