package com.nowellpoint.api.model.dto;

import java.util.HashSet;
import java.util.Set;

public class Application extends AbstractResource {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -365479731136738071L;
	
	private AccountProfile owner;
	
	private String name;
	
	private String description;
	
	private Set<ServiceInstanceDTO> serviceInstances;
	
	private Set<Environment> environments;
	
	private String status;
	
	public Application() {
		
	}

	public AccountProfile getOwner() {
		return owner;
	}

	public void setOwner(AccountProfile owner) {
		this.owner = owner;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<ServiceInstanceDTO> getServiceInstances() {
		return serviceInstances;
	}

	public void setServiceInstances(Set<ServiceInstanceDTO> serviceInstances) {
		this.serviceInstances = serviceInstances;
	}
	
	public void addServiceInstance(ServiceInstanceDTO serviceInstance) {
		if (serviceInstances == null || serviceInstances.isEmpty()) {
			serviceInstances = new HashSet<ServiceInstanceDTO>();
		}
		serviceInstances.add(serviceInstance);
	}

	public Set<Environment> getEnvironments() {
		return environments;
	}

	public void setEnvironments(Set<Environment> environments) {
		this.environments = environments;
	}
	
	public void addEnvironment(Environment environment) {
		if (environments == null || environments.isEmpty()) {
			environments = new HashSet<Environment>();
		}
		environments.add(environment);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}