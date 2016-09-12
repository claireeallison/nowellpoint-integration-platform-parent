package com.nowellpoint.api.model.document;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nowellpoint.aws.data.mongodb.DateDeserializer;
import com.nowellpoint.aws.data.mongodb.DateSerializer;

public class Schedule {
	
	private String key;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date addedOn;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date updatedOn;
	
	private String environmentName;
	
	private String environmentKey;
	
	private Integer hour;
	
	private Integer minute;
	
	private Integer second;
	
	private String status;
	
	public Schedule() {
		
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}

	public String getEnvironmentKey() {
		return environmentKey;
	}

	public void setEnvironmentKey(String environmentKey) {
		this.environmentKey = environmentKey;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}
	
	public Integer getHour() {
		return hour;
	}
	
	public void setMinute(Integer minute) {
		this.minute = minute;
	}
	
	public Integer getMinute() {
		return minute;
	}
	
	public void setSecond(Integer second) {
		this.second = second;
	}
	
	public Integer getSecond() {
		return second;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}