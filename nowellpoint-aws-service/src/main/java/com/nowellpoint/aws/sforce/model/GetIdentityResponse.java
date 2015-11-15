package com.nowellpoint.aws.sforce.model;

import java.io.Serializable;

import com.nowellpoint.aws.model.AbstractResponse;

public class GetIdentityResponse extends AbstractResponse implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -4249532606207243086L;
	
	private Identity identity;
	
	public GetIdentityResponse() {
		
	}

	public Identity getIdentity() {
		return identity;
	}

	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
}