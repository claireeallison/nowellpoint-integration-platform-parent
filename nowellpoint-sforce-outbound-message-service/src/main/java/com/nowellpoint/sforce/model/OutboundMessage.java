package com.nowellpoint.sforce.model;

import java.util.Date;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="OutboundMessages")
public class OutboundMessage {
	
	@DynamoDBHashKey(attributeName="OrganizationId")  
	private String organizationId;
	
	@DynamoDBAutoGeneratedKey
	@DynamoDBRangeKey(attributeName="Key")
	private String key;

	@DynamoDBAttribute(attributeName="ActionId")  
	private String actionId;
	
	@DynamoDBAttribute(attributeName="SessionId")  
	private String sessionId;
	
	@DynamoDBAttribute(attributeName="EnterpriseUrl")  
	private String enterpriseUrl;
	
	@DynamoDBAttribute(attributeName="PartnerUrl")  
	private String partnerUrl;
	
	@DynamoDBAttribute(attributeName="ReceivedDate")  
	private Date receivedDate;
	
	@DynamoDBAttribute(attributeName="AcknowledgeDuration")  
	private Long acknowledgeDuration;
	
	@DynamoDBAttribute(attributeName="ProcessDuration")  
	private Long processDuration;
	
	@DynamoDBAttribute(attributeName="ProcessedDate")  
	private Date processedDate;
	
	@DynamoDBAttribute(attributeName="Status")
	private String status;
	
	@DynamoDBTypeConverted(converter = NotificationTypeConverter.class)
	@DynamoDBAttribute(attributeName="Notifications")
	private List<Notification> notifications;
	
	@DynamoDBTypeConverted(converter = OutboundMessageResultTypeConverter.class)
	@DynamoDBAttribute(attributeName="Results")
	private List<OutboundMessageResult> results;
	
	@DynamoDBAttribute(attributeName="ErrorMessage")
	private String errorMessage;
	
	@DynamoDBAttribute(attributeName="MessageCount")
	private Integer messageCount;
	
	@DynamoDBAttribute(attributeName="Duration")
	private Long duration;
	
	public OutboundMessage() {
		
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getEnterpriseUrl() {
		return enterpriseUrl;
	}

	public void setEnterpriseUrl(String enterpriseUrl) {
		this.enterpriseUrl = enterpriseUrl;
	}

	public String getPartnerUrl() {
		return partnerUrl;
	}

	public void setPartnerUrl(String partnerUrl) {
		this.partnerUrl = partnerUrl;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Long getAcknowledgeDuration() {
		return acknowledgeDuration;
	}

	public void setAcknowledgeDuration(Long acknowledgeDuration) {
		this.acknowledgeDuration = acknowledgeDuration;
	}

	public Long getProcessDuration() {
		return processDuration;
	}

	public void setProcessDuration(Long processDuration) {
		this.processDuration = processDuration;
	}

	public Date getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
		this.messageCount = notifications.size();
	}

	public List<OutboundMessageResult> getResults() {
		return results;
	}

	public void setResults(List<OutboundMessageResult> results) {
		this.results = results;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(Integer messageCount) {
		this.messageCount = messageCount;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
}