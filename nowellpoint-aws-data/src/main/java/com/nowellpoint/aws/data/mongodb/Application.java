package com.nowellpoint.aws.data.mongodb;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nowellpoint.aws.data.ApplicationCodec;
import com.nowellpoint.aws.data.annotation.Document;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collectionName="applications", codec=ApplicationCodec.class)
public class Application extends AbstractDocument implements Serializable {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -1315510190045597737L;
	
	private User owner;
	
	private String name;
	
	private ServiceInstance serviceInstance;
	
	private Connector connector;
	
	public Application() {
		
	}
	
	public Application(ObjectId id) {
		setId(id);
	}
	
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ServiceInstance getServiceInstance() {
		return serviceInstance;
	}

	public void setServiceInstance(ServiceInstance serviceInstance) {
		this.serviceInstance = serviceInstance;
	}

	public Connector getConnector() {
		return connector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}
}