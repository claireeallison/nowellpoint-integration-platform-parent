package com.nowellpoint.aws.event.model;

public class Sobject {
	
	private String type;
	
	private String id;
	
	public Sobject() {
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}