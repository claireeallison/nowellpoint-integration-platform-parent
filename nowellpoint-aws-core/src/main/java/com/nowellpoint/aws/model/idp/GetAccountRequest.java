package com.nowellpoint.aws.model.idp;

import com.nowellpoint.aws.model.AbstractLambdaRequest;

public class GetAccountRequest extends AbstractLambdaRequest {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 350380797564170812L;

	private String apiKeyId;
	
	private String apiKeySecret;
	
	private String href;
	
	public GetAccountRequest() {
		
	}

	public String getApiKeyId() {
		return apiKeyId;
	}

	public void setApiKeyId(String apiKeyId) {
		this.apiKeyId = apiKeyId;
	}

	public String getApiKeySecret() {
		return apiKeySecret;
	}

	public void setApiKeySecret(String apiKeySecret) {
		this.apiKeySecret = apiKeySecret;
	}
	
	public String getHref() {
		return href;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public GetAccountRequest withApiKeyId(String apiKeyId) {
		setApiKeyId(apiKeyId);
		return this;
	}
	
	public GetAccountRequest withApiKeySecret(String apiKeySecret) {
		setApiKeySecret(apiKeySecret);
		return this;
	}
	
	public GetAccountRequest withHref(String href) {
		setHref(href);
		return this;
	}
}