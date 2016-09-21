package com.nowellpoint.client.auth;

import java.util.Optional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nowellpoint.aws.http.HttpResponse;
import com.nowellpoint.aws.http.MediaType;
import com.nowellpoint.aws.http.RestResource;
import com.nowellpoint.aws.http.Status;
import com.nowellpoint.client.auth.impl.OauthAuthenticationResponseImpl;
import com.nowellpoint.client.auth.impl.OauthException;
import com.nowellpoint.client.model.idp.Token;

public class Authenticators {
	
	private static final String API_ENDPOINT = System.getenv("NCS_API_ENDPOINT");
	
	public static final PasswordGrantResponseFactory PASSWORD_GRANT_AUTHENTICATOR = new PasswordGrantResponseFactory();
	public static final RevokeTokenResponseFactory REVOKE_TOKEN_INVALIDATOR = new RevokeTokenResponseFactory();
	
	public static class PasswordGrantResponseFactory {
		public OauthAuthenticationResponse authenticate(PasswordGrantRequest passwordGrantRequest) {
			
			Optional.of(passwordGrantRequest.getUsername()).orElseThrow(() -> new IllegalArgumentException("missing username"));
			Optional.of(passwordGrantRequest.getPassword()).orElseThrow(() -> new IllegalArgumentException("missing password"));
			
			HttpResponse httpResponse = RestResource.post(API_ENDPOINT)
	    			.accept(MediaType.APPLICATION_JSON)
	    			.path("oauth")
	    			.path("token")
	    			.basicAuthorization(passwordGrantRequest.getUsername(), passwordGrantRequest.getPassword())
	    			.execute();
	    			
	    	int statusCode = httpResponse.getStatusCode();
	    			
	    	if (statusCode != Status.OK) {
	    		ObjectNode error = httpResponse.getEntity(ObjectNode.class);
	    		throw new OauthException(error.get("code").asInt(), error.get("message").asText());
	    	}
	    			
	    	Token token = httpResponse.getEntity(Token.class);
	    	
	    	OauthAuthenticationResponse response = new OauthAuthenticationResponseImpl(token);
	    	
	    	return response;
		}
	}
	
	public static class RevokeTokenResponseFactory {
		public void revoke(RevokeTokenRequest revokeTokenRequest) {
			HttpResponse httpResponse = RestResource.delete(API_ENDPOINT)
					.bearerAuthorization(revokeTokenRequest.getAccessToken())
	    			.path("oauth")
	    			.path("token")
	    			.execute();
	    	
	    	int statusCode = httpResponse.getStatusCode();
	    	
	    	if (statusCode != 204) {
	    		ObjectNode error = httpResponse.getEntity(ObjectNode.class);
	    		throw new OauthException(error.get("code").asInt(), error.get("message").asText());
	    	}
		}
	}
}