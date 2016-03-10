package com.nowellpoint.aws.api.service;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import com.nowellpoint.aws.http.HttpResponse;
import com.nowellpoint.aws.http.MediaType;
import com.nowellpoint.aws.http.RestResource;
import com.nowellpoint.aws.idp.model.Token;
import com.nowellpoint.aws.model.admin.Properties;

public class IdentityProviderService {
	
	private static final Logger LOGGER = Logger.getLogger(IdentityProviderService.class.getName());
	
	public Token authenticate(String username, String password) {
		Token token = null;

		try {
			HttpResponse httpResponse = RestResource.post(System.getProperty(Properties.STORMPATH_API_ENDPOINT))
					.path("applications")
					.path(System.getProperty(Properties.STORMPATH_APPLICATION_ID))
					.path("oauth/token")
					.basicAuthorization(System.getProperty(Properties.STORMPATH_API_KEY_ID), System.getProperty(Properties.STORMPATH_API_KEY_SECRET))
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.accept(MediaType.APPLICATION_JSON)
					.parameter("grant_type", "password")
					.parameter("username", username)
					.parameter("password", password)
					.execute();
			
			LOGGER.info("Token response status: " + httpResponse.getStatusCode() + " Target: " + httpResponse.getURL());
			
			if (httpResponse.getStatusCode() >= 400) {
				throw new WebApplicationException(httpResponse.getAsString(), httpResponse.getStatusCode());
			}
			
			token = httpResponse.getEntity(Token.class);
			
		} catch (IOException e) {
			LOGGER.error( "getIdentity", e.getCause() );
			throw new WebApplicationException(e, Status.BAD_REQUEST);
		}	
		
		return token;
		
	}
}