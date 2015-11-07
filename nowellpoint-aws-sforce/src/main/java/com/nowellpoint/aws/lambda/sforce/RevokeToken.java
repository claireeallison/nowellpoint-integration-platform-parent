package com.nowellpoint.aws.lambda.sforce;

import java.io.IOException;
import java.util.logging.Logger;

import com.amazonaws.AmazonServiceException.ErrorType;
import com.amazonaws.services.apigateway.model.BadRequestException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.nowellpoint.aws.http.HttpResponse;
import com.nowellpoint.aws.http.RestResource;
import com.nowellpoint.aws.model.IntegrationRequest;
import com.nowellpoint.aws.util.Configuration;

public class RevokeToken implements RequestHandler<IntegrationRequest, Void> {
	
	private static final Logger log = Logger.getLogger(RevokeToken.class.getName());

	@Override
	public Void handleRequest(IntegrationRequest request, Context context) { 
			
		/**
		 * 
		 */
		
		HttpResponse response = null;
		try {
			response = RestResource.post(Configuration.getSalesforceRevokeUri())
					.header("Content-type", "application/x-www-form-urlencoded")
					.body(request.getBody())
					.execute();
			
			log.info("Revoke response status: " + response.getStatusCode() + " Target: " + response.getURL());
			
			/**
			 * 
			 */
				
			if (response.getStatusCode() != 200) {		
				log.severe(response.getEntity());
				JsonNode errorResponse = response.getEntity(JsonNode.class);
				BadRequestException exception = new BadRequestException(errorResponse.get("error_description").asText());
				exception.setStatusCode(401);
				exception.setErrorType(ErrorType.Client);
				exception.setRequestId(context.getAwsRequestId());
				exception.setServiceName(context.getFunctionName());
				exception.setErrorCode(errorResponse.get("error").asText());
				throw exception;
			}
			
			return null;
			
		} catch (IOException e) {
			BadRequestException exception = new BadRequestException(e.getMessage());
			exception.setStatusCode(400);
			exception.setErrorType(ErrorType.Client);
			exception.setRequestId(context.getAwsRequestId());
			exception.setServiceName(context.getFunctionName());
			exception.setErrorCode("INVALID_REQUEST");
			throw exception;
		}
	}
}