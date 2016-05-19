package com.nowellpoint.sforce.model;

import java.util.Date;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
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
	
	@DynamoDBAttribute(attributeName="MessageDate")  
	private Date messageDate;
	
	@DynamoDBMarshalling(marshallerClass = NotificationMarshaller.class)
	@DynamoDBAttribute(attributeName="Notifications")
	private List<Notification> notifications;
	
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

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
}