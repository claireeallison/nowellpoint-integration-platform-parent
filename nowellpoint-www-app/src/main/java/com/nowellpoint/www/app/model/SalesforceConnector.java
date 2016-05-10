package com.nowellpoint.www.app.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nowellpoint.www.app.model.sforce.Identity;
import com.nowellpoint.www.app.model.sforce.Organization;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesforceConnector extends Resource {

	private Identity identity;
	
	private Organization organization;
	
	private AccountProfile owner;
	
	private String tag;
	
	private List<ServiceInstance> serviceInstances;
	
	public SalesforceConnector() {
		
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

	public AccountProfile getOwner() {
		return owner;
	}

	public void setOwner(AccountProfile owner) {
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
	
	@JsonIgnore
	public ServiceInstance getServiceInstance(String key) {
		return getServiceInstances()
    			.stream()
    			.filter(p -> p.getKey().equals(key))
    			.findFirst()
    			.orElse(new ServiceInstance());
	}
}