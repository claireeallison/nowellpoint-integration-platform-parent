package com.nowellpoint.aws.lambda.idp;

import static com.nowellpoint.aws.tools.TokenParser.parseToken;

import java.time.Instant;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.nowellpoint.aws.http.HttpResponse;
import com.nowellpoint.aws.http.RestResource;
import com.nowellpoint.aws.model.Configuration;
import com.nowellpoint.aws.model.idp.Account;
import com.nowellpoint.aws.model.idp.GetAccountRequest;
import com.nowellpoint.aws.model.idp.GetAccountResponse;

public class GetAccount implements RequestHandler<GetAccountRequest, GetAccountResponse> {

	@Override
	public GetAccountResponse handleRequest(GetAccountRequest request, Context context) {
		
		/**
		 * 
		 */
		
		long startTime = System.currentTimeMillis();
		
		/**
		 * 
		 */
		
		LambdaLogger logger = context.getLogger();
		
		/**
		 * 
		 */
		
		GetAccountResponse response = new GetAccountResponse();
		
		/**
		 * 
		 */
		
		String href = parseToken(request.getAccessToken()).getBody().getSubject();
		
		/**
		 * 
		 */
		
		try {
			
			HttpResponse httpResponse = RestResource.get(href)
					.basicAuthorization(Configuration.getStormpathApiKeyId(), Configuration.getStormpathApiKeySecret())
					.execute();
				
			logger.log("Status Code: " + httpResponse.getStatusCode() + " Target: " + httpResponse.getURL());
			
			/**
			 * 
			 */
							
			response.setStatusCode(httpResponse.getStatusCode());
			
			if (httpResponse.getStatusCode() == 200) {
				Account account = httpResponse.getEntity(Account.class);
				response.setAccount(account);
			} else {
				JsonNode errorResponse = httpResponse.getEntity(JsonNode.class);
				response.setErrorCode(errorResponse.get("message").asText());
				response.setErrorMessage(errorResponse.get("developerMessage").asText());
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			response.setStatusCode(400);
			response.setErrorCode("invalid_request");
			response.setErrorMessage(e.getMessage());
		}
		
		logger.log(Instant.now() + " " + context.getAwsRequestId() + " execution time: " + (System.currentTimeMillis() - startTime));
		
		return response;
	}
}