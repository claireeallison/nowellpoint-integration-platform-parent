package com.nowellpoint.aws.sforce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nowellpoint.aws.model.AbstractResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAuthorizationResponse extends AbstractResponse {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -7110265949351282637L;
	
	private Token token;
	
	public GetAuthorizationResponse() {
		
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
}