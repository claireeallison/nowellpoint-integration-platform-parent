package com.nowellpoint.aws.model.data;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDocument implements Serializable {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 4165321769330346404L;
	
	/**
	 * 
	 */
	
	@JsonProperty("_id")
	private String id;

	/**
	 * 
	 */
	
	@JsonInclude(Include.NON_NULL)
	private String createdById;
	
	/**
	 * 
	 */
	
	@JsonInclude(Include.NON_NULL)
	private String lastModifiedById;
	
	/**
	 * 
	 */
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	@JsonInclude(Include.NON_NULL)
	private Date createdDate;
	
	/**
	 * 
	 */
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	@JsonInclude(Include.NON_NULL)
	private Date lastModifiedDate;
	
	/**
	 * 
	 */
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	@JsonInclude(Include.NON_NULL)
	private Date systemCreationDate;
	
	/**
	 * 
	 */

	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	@JsonInclude(Include.NON_NULL)
	private Date systemModifiedDate;
	
	public AbstractDocument() {
		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public String getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(String lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Date getSystemCreationDate() {
		return systemCreationDate;
	}

	public void setSystemCreationDate(Date systemCreationDate) {
		this.systemCreationDate = systemCreationDate;
	}

	public Date getSystemModifiedDate() {
		return systemModifiedDate;
	}

	public void setSystemModifiedDate(Date systemModifiedDate) {
		this.systemModifiedDate = systemModifiedDate;
	}
}