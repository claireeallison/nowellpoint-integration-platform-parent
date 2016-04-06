package com.nowellpoint.client.sforce;

import com.nowellpoint.client.sforce.impl.SalesforceException;
import com.nowellpoint.client.sforce.model.Error;

public class ClientException extends SalesforceException {
	
	private static final long serialVersionUID = 2726007023971974319L;
	
	public ClientException(int statusCode, Error error) {
		super(statusCode, error);
	}
}