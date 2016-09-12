package com.nowellpoint.api.model.dto;

public class EventListenerDTO {
	
	private String name;
	
	private String label;
	
	private Boolean triggerable;
	
	private Boolean queryable;
	
	private Boolean createable;
	
	private Boolean updateable;
	
	private Boolean deleteable;
	
	private Boolean replicateable;
	
	private Boolean create;
	
	private Boolean update;
	
	private Boolean delete;
	
	private String callback;
	
	public EventListenerDTO() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getTriggerable() {
		return triggerable;
	}

	public void setTriggerable(Boolean triggerable) {
		this.triggerable = triggerable;
	}

	public Boolean getQueryable() {
		return queryable;
	}

	public void setQueryable(Boolean queryable) {
		this.queryable = queryable;
	}

	public Boolean getCreateable() {
		return createable;
	}

	public void setCreateable(Boolean createable) {
		this.createable = createable;
	}

	public Boolean getUpdateable() {
		return updateable;
	}

	public void setUpdateable(Boolean updateable) {
		this.updateable = updateable;
	}

	public Boolean getDeleteable() {
		return deleteable;
	}

	public void setDeleteable(Boolean deleteable) {
		this.deleteable = deleteable;
	}

	public Boolean getReplicateable() {
		return replicateable;
	}

	public void setReplicateable(Boolean replicateable) {
		this.replicateable = replicateable;
	}

	public Boolean getCreate() {
		return create;
	}

	public void setCreate(Boolean create) {
		this.create = create;
	}

	public Boolean getUpdate() {
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}
}