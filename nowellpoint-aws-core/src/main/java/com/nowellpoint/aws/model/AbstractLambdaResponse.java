package com.nowellpoint.aws.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractLambdaResponse extends Base64EncoderDecoder implements Serializable {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -819250358735378538L;
	
	private Integer statusCode;
	
	private String errorCode;
	
	private String errorMessage;
	
	public AbstractLambdaResponse() {
		
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}