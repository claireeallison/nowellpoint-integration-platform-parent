package com.nowellpoint.www.app.view;

import static spark.Spark.get;

import java.io.IOException;
import java.util.Optional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nowellpoint.aws.http.HttpResponse;
import com.nowellpoint.aws.http.RestResource;
import com.nowellpoint.aws.http.Status;
import com.nowellpoint.client.sforce.model.Token;
import com.nowellpoint.www.app.model.ExceptionResponse;
import com.nowellpoint.www.app.model.SalesforceConnector;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;

public class SalesforceOauthController extends AbstractController {
	
	private final static Logger LOG = LoggerFactory.getLogger(SalesforceOauthController.class.getName());
	
	public SalesforceOauthController(Configuration configuration) {
		super(SalesforceOauthController.class, configuration);
	}
	
	public void configureRoutes(Configuration cfg) {
		get("/app/salesforce/oauth", (request, response) -> oauth(request, response));
        
        get("/app/salesforce/oauth/callback", (request, response) -> callback(request, response), new FreeMarkerEngine(cfg));
        
        get("/app/salesforce/oauth/token", (request, response) -> getSalesforceToken(request, response));
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	
	private String oauth(Request request, Response response) {
		
		HttpResponse httpResponse = RestResource.get(API_ENDPOINT)
    			.path("salesforce")
    			.path("oauth")
    			.queryParameter("state", request.queryParams("id"))
    			.execute();
    	
    	LOG.info("Status Code: " + httpResponse.getStatusCode() + " Method: " + request.requestMethod() + " : " + httpResponse.getURL() + " : " + httpResponse.getHeaders().get("Location"));
		
		response.redirect(httpResponse.getHeaders().get("Location").get(0));		
		
		return "";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	
	private ModelAndView callback(Request request, Response response) {
    	
    	Optional<String> code = Optional.ofNullable(request.queryParams("code"));
    	
    	if (! code.isPresent()) {
    		throw new BadRequestException("missing OAuth code from Salesforce");
    	}
    	
		return new ModelAndView(getModel(), "secure/salesforce-callback.html");	
    }
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	
	private String getSalesforceToken(Request request, Response response) {
    	
    	HttpResponse httpResponse = RestResource.get(API_ENDPOINT)
				.header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
				.bearerAuthorization(getToken(request).getAccessToken())
    			.path("salesforce")
    			.path("oauth")
    			.path("token")
    			.queryParameter("code", request.queryParams("code"))
    			.execute();
    	
    	Token token = null;
    	
    	if (httpResponse.getStatusCode() != Status.OK) {
    		ExceptionResponse error = httpResponse.getEntity(ExceptionResponse.class);
			throw new BadRequestException(error.getMessage());
    	}
    	
    	token = httpResponse.getEntity(Token.class);
    	
    	httpResponse = RestResource.post(API_ENDPOINT)
				.header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
				.bearerAuthorization(getToken(request).getAccessToken())
				.path("connectors")
    			.path("salesforce")
    			.parameter("id", token.getId())
    			.parameter("instanceUrl", token.getInstanceUrl())
    			.parameter("accessToken", token.getAccessToken())
    			.parameter("refreshToken", token.getRefreshToken())
    			.execute();
    	
    	if (httpResponse.getStatusCode() != Status.CREATED) {
    		ExceptionResponse error = httpResponse.getEntity(ExceptionResponse.class);
			throw new BadRequestException(error.getMessage());
    	}
    	
    	SalesforceConnector salesforceConnector = httpResponse.getEntity(SalesforceConnector.class);
    	
    	response.redirect(String.format("/app/connectors/salesforce/%s", salesforceConnector.getId()));
    	
    	return "";
	}
}