package com.nowellpoint.aws.model.sforce;

import com.nowellpoint.aws.model.AbstractLambdaResponse;

public class GetTokenResponse extends AbstractLambdaResponse {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 6237973241119968784L;
	
	private Token token;
	
	public GetTokenResponse() {
		
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
}