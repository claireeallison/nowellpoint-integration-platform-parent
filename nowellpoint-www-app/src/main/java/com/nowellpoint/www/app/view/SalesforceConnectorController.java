package com.nowellpoint.www.app.view;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.nowellpoint.aws.http.HttpRequestException;
import com.nowellpoint.aws.http.HttpResponse;
import com.nowellpoint.aws.http.MediaType;
import com.nowellpoint.aws.http.PostRequest;
import com.nowellpoint.aws.http.RestResource;
import com.nowellpoint.aws.http.Status;
import com.nowellpoint.aws.idp.model.Token;
import com.nowellpoint.www.app.model.Environment;
import com.nowellpoint.www.app.model.ExceptionResponse;
import com.nowellpoint.www.app.model.SalesforceConnector;
import com.nowellpoint.www.app.model.Service;
import com.nowellpoint.www.app.model.ServiceInstance;
import com.nowellpoint.www.app.model.ServiceProvider;
import com.nowellpoint.www.app.model.sforce.Field;
import com.nowellpoint.www.app.service.GetSalesforceConnectorRequest;
import com.nowellpoint.www.app.service.SalesforceConnectorService;
import com.nowellpoint.www.app.util.MessageProvider;
import com.nowellpoint.www.app.util.Path;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

public class SalesforceConnectorController extends AbstractController {
	
	private static final Logger LOG = LoggerFactory.getLogger(SalesforceConnectorController.class.getName());
	
	private static final SalesforceConnectorService salesforceConnectorService = new SalesforceConnectorService();
	
	public SalesforceConnectorController(Configuration cfg) {
		super(SalesforceConnectorController.class, cfg);      
	}
	
	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * newEnvironment
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	public Route newEnvironment = (Request request, Response response) -> {			
		String id = request.params(":id");
		
		SalesforceConnector salesforceConnector = new SalesforceConnector(id);
		
		Environment environment = new Environment();
		environment.setAuthEndpoint("https://test.salesforce.com");
		
		Map<String, Object> model = getModel();
		model.put("salesforceConnector", salesforceConnector);
		model.put("mode", "add");
		model.put("action", String.format("/app/connectors/salesforce/%s/environments", id));
		model.put("environment", environment);
		
		return render(request, model, Path.Template.ENVIRONMENT);
	};
	
	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * getEnvironment
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	public Route viewEnvironment = (Request request, Response response) -> {		
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");
		
		HttpResponse httpResponse = RestResource.get(API_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
    			.path(id)
    			.path("environment")
    			.path(key)
    			.execute();
		
		Environment environment = null;
		
		if (httpResponse.getStatusCode() == Status.OK) {
			environment = httpResponse.getEntity(Environment.class);
		} else if (httpResponse.getStatusCode() == Status.NOT_FOUND) {
			throw new NotFoundException(httpResponse.getAsString());
		} else if (httpResponse.getStatusCode() == Status.BAD_REQUEST) {
			throw new BadRequestException(httpResponse.getAsString());
		}
		
		Map<String, Object> model = getModel();
		model.put("salesforceConnector", new SalesforceConnector(id));
		model.put("mode", "view");
		model.put("environment", environment);
		
		return render(request, model, Path.Template.ENVIRONMENT);
	};

	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * editEnvironment
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route editEnvironment = (Request request, Response response) -> {		
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");
		
		HttpResponse httpResponse = RestResource.get(API_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
    			.path(id)
    			.path("environment")
    			.path(key)
    			.execute();
		
		Environment environment = null;
		
		if (httpResponse.getStatusCode() == Status.OK) {
			environment = httpResponse.getEntity(Environment.class);
		} else if (httpResponse.getStatusCode() == Status.NOT_FOUND) {
			throw new NotFoundException(httpResponse.getAsString());
		} else if (httpResponse.getStatusCode() == Status.BAD_REQUEST) {
			throw new BadRequestException(httpResponse.getAsString());
		}
		
		Map<String, Object> model = getModel();
		model.put("salesforceConnector", new SalesforceConnector(id));
		model.put("mode", "edit");
		model.put("action", String.format("/app/connectors/salesforce/%s/environments/%s", id, key));
		model.put("environment", environment);
		
		return render(request, model, Path.Template.ENVIRONMENT);
	};

	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * addEnvironment
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route addEnvironment = (Request request, Response response) -> {		
		Token token = getToken(request);
		
		String id = request.params(":id");
		String active = request.queryParams("active");
		String authEndpoint = request.queryParams("authEndpoint");
		String name = request.queryParams("environmentName");
		String password = request.queryParams("password");
		String username = request.queryParams("username");
		String securityToken = request.queryParams("securityToken");
		
		Environment environment = new Environment()
				.withIsActive(Boolean.valueOf(active))
				.withAuthEndpoint(authEndpoint)
				.withEnvironmentName(name)
				.withPassword(password)
				.withUsername(username)
				.withSecurityToken(securityToken);
		
		HttpResponse httpResponse = RestResource.post(API_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
    			.path(id)
    			.path("environment")
    			.body(environment)
				.execute();

		if (httpResponse.getStatusCode() != Status.OK) {
			ExceptionResponse error = httpResponse.getEntity(ExceptionResponse.class);
			
			Map<String, Object> model = getModel();
			model.put("salesforceConnector", new SalesforceConnector(id));
			model.put("mode", "add");
			model.put("action", String.format("/app/connectors/salesforce/%s/environments", id));
			model.put("environment", environment);
			model.put("errorMessage", error.getMessage());
			
			String output = render(request, model, Path.Template.ENVIRONMENT);
			
			throw new BadRequestException(output);
		}

		response.cookie(Path.Route.CONNECTORS_SALESFORCE.replace(":id", id), "successMessage", MessageProvider.getMessage(getDefaultLocale(request), "add.environment.success"), 3, Boolean.FALSE);
		response.redirect(Path.Route.CONNECTORS_SALESFORCE.replace(":id", id));
		
		return "";		
	};

	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * updateEnvironment
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route updateEnvironment = (Request request, Response response) -> {		
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");
		String active = request.queryParams("active");
		String authEndpoint = request.queryParams("authEndpoint");
		String environmentName = request.queryParams("environmentName");
		String password = request.queryParams("password");
		String username = request.queryParams("username");
		String securityToken = request.queryParams("securityToken");
		
		Environment environment = new Environment()
				.withIsActive(Boolean.valueOf(active))
				.withAuthEndpoint(authEndpoint)
				.withEnvironmentName(environmentName)
				.withPassword(password)
				.withUsername(username)
				.withSecurityToken(securityToken);
		
		HttpResponse httpResponse = RestResource.put(API_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
    			.path(id)
    			.path("environment")
    			.path(key)
    			.body(environment)
				.execute();
		
		if (httpResponse.getStatusCode() != Status.OK) {
			ExceptionResponse error = httpResponse.getEntity(ExceptionResponse.class);
			
			Map<String, Object> model = getModel();
			model.put("salesforceConnector", new SalesforceConnector(id));
			model.put("mode", "edit");
			model.put("action", String.format("/app/connectors/salesforce/%s/environments/%s", id, key));
			model.put("environment", environment);
			model.put("errorMessage", error.getMessage());
			
			String output = render(request, model, Path.Template.ENVIRONMENT);
			
			throw new BadRequestException(output);
		}
		
		response.cookie("successMessage", MessageProvider.getMessage(getDefaultLocale(request), "update.environment.success"), 3);
		response.redirect(Path.Route.CONNECTORS_SALESFORCE.replace(":id", id));
		
		return "";		
	};
	
	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * removeEnvironment
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	public Route removeEnvironment = (Request request, Response response) -> {		
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");
		
		HttpResponse httpResponse = RestResource.delete(API_ENDPOINT)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
    			.path(id)
    			.path("environment")
    			.path(key)
				.execute();
		
		if (httpResponse.getStatusCode() != Status.OK) {
			ExceptionResponse error = httpResponse.getEntity(ExceptionResponse.class);
			throw new BadRequestException(error.getMessage());
		}
		
		response.cookie("successMessage", MessageProvider.getMessage(getDefaultLocale(request), "remove.environment.success"), 3);
		response.header("Location", Path.Route.CONNECTORS_SALESFORCE.concat("/").concat(id));
		
		return "";
	};

	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * getEventListeners
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route getEventListeners = (Request request, Response response) -> {
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");
		
		GetSalesforceConnectorRequest getSalesforceConnectorRequest = new GetSalesforceConnectorRequest()
				.withAccessToken(token.getAccessToken())
				.withId(id);
    	
		SalesforceConnector salesforceConnector = salesforceConnectorService.getSalesforceConnector(getSalesforceConnectorRequest);
		
		ServiceInstance serviceInstance = salesforceConnector.getServiceInstance(key);
		
		Map<String, Object> model = getModel();
		model.put("salesforceConnector", salesforceConnector);
		model.put("serviceInstance", serviceInstance);
			
		return render(request, model, Path.Template.EVENT_LISTENERS);
	};
	
	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * updateServiceInstance
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	public Route updateServiceInstance = (Request request, Response response) -> {
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");
		String name = request.queryParams("name");
		String tag = request.queryParams("tag");
		String bucketName = request.queryParams("bucketName");
		String awsAccessKey = request.queryParams("awsAccessKey");
		String awsSecretAccessKey = request.queryParams("awsSecretAccessKey");
		
		PostRequest httpRequest = RestResource.post(API_ENDPOINT)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
				.path(id)
				.path("service")
				.path(key);
		
		if (name != null) {
			httpRequest.parameter("name", name);
		}
		
		if (tag != null) {
			httpRequest.parameter("tag", tag);
		}
		
		if (bucketName != null) {
			httpRequest.parameter("bucketName", bucketName);
		}
		
		if (awsAccessKey != null) {
			httpRequest.parameter("awsAccessKey", awsAccessKey);
		}
		
		if (awsSecretAccessKey != null) {
			httpRequest.parameter("awsSecretAccessKey", awsSecretAccessKey);
		}
				
		HttpResponse httpResponse = httpRequest.execute();
		
		if (httpResponse.getStatusCode() != Status.OK) {
			ExceptionResponse error = httpResponse.getEntity(ExceptionResponse.class);
			response.cookie("errorMessage", error.getMessage(), 3, Boolean.FALSE);
			response.redirect(Path.Route.CONNECTORS_SALESFORCE.replace(":id", id).concat("/services/:key/edit".replace(":key", key)));
			return "";
		}

		response.cookie(Path.Route.CONNECTORS_SALESFORCE.replace(":id", id), "successMessage", MessageProvider.getMessage(this.getDefaultLocale(request), "update.service.success"), 3, Boolean.FALSE);
		response.redirect(Path.Route.CONNECTORS_SALESFORCE.replace(":id", id));
		
		return "";		
	};
	
//	private ModelAndView deleteServiceInstance(Request request, Response response) {
//		Token token = getToken(request);
//		
//		String id = request.params(":id");
//		String key = request.params(":key");
//		
//		HttpResponse httpResponse = RestResource.delete(API_ENDPOINT)
//				.bearerAuthorization(token.getAccessToken())
//				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
//				.path("connectors")
//    			.path("salesforce")
//				.path(id)
//				.path("service")
//				.path(key)
//				.execute();
//		
//		Map<String, Object> model = getModel();
//		
//		if (httpResponse.getStatusCode() == Status.OK) {
//			model.put("successMessage", MessageProvider.getMessage(Locale.US, "deleteSuccess"));
//			return new ModelAndView(model, "secure/fragments/success-message.html");
//		} else {
//			model.put("message", httpResponse.getEntity(JsonNode.class).get("message").asText());
//			return new ModelAndView(model, "secure/fragments/e-message.html");
//		}
//	}
	
//	private ModelAndView getEnvironmentVariables(Request request, Response response) {		
//		Token token = getToken(request);
//		AccountProfile account = getAccount(request);
//		
//		String id = request.params(":id");
//		String key = request.params(":key");
//		String environmentName = request.params(":environment");
//		
//		SalesforceConnector salesforceConnector = getSalesforceConnector(token, id);
//		
//		ServiceInstance serviceInstance = salesforceConnector.getServiceInstance(key);
//		
//		Environment environment = null;
//		
//		Map<String, Object> model = getModel();
//		
//		if (environmentName != null) {
//			
//			environment = serviceInstance.getEnvironments()
//					.stream()
//					.filter(p -> p.getName().equals(environmentName))
//					.findFirst()
//					.orElse(new Environment());
//			
//			if (environment.getEnvironmentVariables().size() == 0) {
//				environment.getEnvironmentVariables().add(new EnvironmentVariable());
//			}
//			
//			model.put("environment", environment);
//			
//			return new ModelAndView(model, "secure/fragments/environment-variables-table.html");
//		} else {
//			
//			environment = serviceInstance.getEnvironments()
//					.stream()
//					.filter(p -> p.getName().equals(serviceInstance.getSourceEnvironment()))
//					.findFirst()
//					.orElse(new Environment());
//			
//			if (environment.getEnvironmentVariables().size() == 0) {
//				environment.getEnvironmentVariables().add(new EnvironmentVariable());
//			}
//			
//			model.put("account", account);
//			model.put("salesforceConnector", salesforceConnector);
//			model.put("serviceInstance", serviceInstance);
//			model.put("environment", environment);
//			
//			return new ModelAndView(model, "secure/environment-variables.html");
//		}	
//	}
	
	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * getServiceInstance
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	public Route viewServiceInstance = (Request request, Response response) -> {
		
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");
		
		HttpResponse httpResponse = RestResource.get(API_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
    			.path(id)
    			.path("service")
    			.path(key)
    			.execute();
		
		ServiceInstance serviceInstance = null;
		
		if (httpResponse.getStatusCode() == Status.OK) {
			serviceInstance = httpResponse.getEntity(ServiceInstance.class);
		} else if (httpResponse.getStatusCode() == Status.NOT_FOUND) {
			throw new NotFoundException(httpResponse.getAsString());
		} else if (httpResponse.getStatusCode() == Status.BAD_REQUEST) {
			throw new BadRequestException(httpResponse.getAsString());
		}
		
		Map<String, Object> model = getModel();
		model.put("salesforceConnector", new SalesforceConnector(id));
		model.put("mode", "view");
		model.put("serviceInstance", serviceInstance);
		model.put("successMessage", request.cookie("successMessage"));
		
		return render(request, model, String.format(Path.APPLICATION_CONTEXT, serviceInstance.getConfigurationPage()));
	};
	
	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * editServiceInstance
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	public Route editServiceInstance = (Request request, Response response) -> {
		
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");
		
		HttpResponse httpResponse = RestResource.get(API_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
    			.path(id)
    			.path("service")
    			.path(key)
    			.execute();
		
		ServiceInstance serviceInstance = null;
		
		if (httpResponse.getStatusCode() == Status.OK) {
			serviceInstance = httpResponse.getEntity(ServiceInstance.class);
		} else if (httpResponse.getStatusCode() == Status.NOT_FOUND) {
			throw new NotFoundException(httpResponse.getAsString());
		} else if (httpResponse.getStatusCode() == Status.BAD_REQUEST) {
			throw new BadRequestException(httpResponse.getAsString());
		}
		
		Map<String, Object> model = getModel();
		model.put("salesforceConnector", new SalesforceConnector(id));
		model.put("mode", "edit");
		model.put("action", String.format("/app/connectors/salesforce/%s/services/%s/update", id, key));
		model.put("serviceInstance", serviceInstance);
		model.put("errorMessage", request.cookie("errorMessage"));
		
		return render(request, model, String.format(Path.APPLICATION_CONTEXT, serviceInstance.getConfigurationPage()));
	};
	
	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * addServiceInstance
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	public Route addServiceInstance = (Request request, Response response) -> {
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");
		
		HttpResponse httpResponse = RestResource.post(API_ENDPOINT)
				.bearerAuthorization(token.getAccessToken())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.path("connectors")
    			.path("salesforce")
				.path(id)
				.path("service")
				.parameter("key", key)
				.execute();
		
		if (httpResponse.getStatusCode() != Status.OK) {
			ExceptionResponse error = httpResponse.getEntity(ExceptionResponse.class);
			
			List<ServiceProvider> providers = Collections.emptyList();
			
			httpResponse = RestResource.get(API_ENDPOINT)
					.bearerAuthorization(token.getAccessToken())
					.path("providers")
					.queryParameter("localeSidKey", "en_US")
					.queryParameter("languageLocaleKey", "en_US")
					.execute();
				
			if (httpResponse.getStatusCode() == Status.OK) {
				providers = httpResponse.getEntityList(ServiceProvider.class);
			} else {
				throw new BadRequestException(httpResponse.getAsString());
			}
	    	
			SalesforceConnector salesforceConnector = new SalesforceConnector(id);
			
			Map<String, Object> model = getModel();
			model.put("serviceProviders", providers);
			model.put("salesforceConnector", salesforceConnector);
			model.put("errorMessage", error.getMessage());
	    	
			return render(request, model, Path.Template.SERVICE_CATALOG);
		}
		
		response.cookie(Path.Route.CONNECTORS_SALESFORCE.replace(":id", id), "successMessage", MessageProvider.getMessage(getDefaultLocale(request), "add.service.success"), 3, Boolean.FALSE);
		response.redirect(Path.Route.CONNECTORS_SALESFORCE.replace(":id", id));
		
		return "";		
	};
	
	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * getSalesforceConnector
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	public Route viewSalesforceConnector = (Request request, Response response) -> {
		Token token = getToken(request);
		
		String id = request.params(":id");
		
		GetSalesforceConnectorRequest getSalesforceConnectorRequest = new GetSalesforceConnectorRequest()
				.withAccessToken(token.getAccessToken())
				.withId(id);
    	
		SalesforceConnector salesforceConnector = salesforceConnectorService.getSalesforceConnector(getSalesforceConnectorRequest);
		
		Map<String, Object> model = getModel();
    	model.put("salesforceConnector", salesforceConnector);
    	model.put("successMessage", request.cookie("successMessage"));
		
    	return render(request, model, Path.Template.SALESFORCE_CONNECTOR);
	};

	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * editSalesforceConnector
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route editSalesforceConnector = (Request request, Response response) -> {
		Token token = getToken(request);
		
		String id = request.params(":id");
    	
		GetSalesforceConnectorRequest getSalesforceConnectorRequest = new GetSalesforceConnectorRequest()
				.withAccessToken(token.getAccessToken())
				.withId(id);
    	
		SalesforceConnector salesforceConnector = salesforceConnectorService.getSalesforceConnector(getSalesforceConnectorRequest);
		
		Map<String, Object> model = getModel();
    	model.put("salesforceConnector", salesforceConnector);
		
    	return render(request, model, Path.Template.SALESFORCE_CONNECTOR_EDIT);
	};

	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * getSalesforceConnectors
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route getSalesforceConnectors = (Request request, Response response) -> {	
		Token token = getToken(request);
		
		HttpResponse httpResponse = RestResource.get(API_ENDPOINT)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
    			.execute();

		LOG.info("Status Code: " + httpResponse.getStatusCode() + " Method: " + request.requestMethod() + " : " + request.pathInfo());
		
		List<SalesforceConnector> salesforceConnectors = null;
		
		if (httpResponse.getStatusCode() == Status.OK) {
			salesforceConnectors = httpResponse.getEntityList(SalesforceConnector.class);
		} else {
			throw new BadRequestException(httpResponse.getAsString());
		}
		
		Map<String, Object> model = getModel();
    	model.put("salesforceConnectorsList", salesforceConnectors);
    	
    	return render(request, model, Path.Template.SALESFORCE_CONNECTORS_LIST);
    	
	};

	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * updateSalesforceConnector
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route updateSalesforceConnector = (Request request, Response response) -> {	
		Token token = getToken(request);
		
		String id = request.params(":id");
		String tag = request.queryParams("tag");
		
		HttpResponse httpResponse = RestResource.post(API_ENDPOINT)
				.header("Content-Type", "application/x-www-form-urlencoded")
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
    			.path(id)
    			.parameter("tag", tag)
    			.execute();
		
		SalesforceConnector salesforceConnector = null;
		String message = null;
		
		try {
			salesforceConnector = httpResponse.getEntity(SalesforceConnector.class);
		} catch (BadRequestException e) {
			message = e.getMessage();
		}
		
		Map<String, Object> model = getModel();
    	model.put("salesforceConnector", salesforceConnector);
    	model.put("successMessage", request.cookie("successMessage"));
    	model.put("errorMessage", message);
		
    	return render(request, model, Path.Template.SALESFORCE_CONNECTOR);
	};

	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * deleteSalesforceConnector
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route deleteSalesforceConnector = (Request request, Response response) -> {
		Token token = getToken(request);
		
		HttpResponse httpResponse = RestResource.delete(API_ENDPOINT)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
    			.path(request.params(":id"))
    			.execute();
		
		LOG.info("Status Code: " + httpResponse.getStatusCode() + " Method: " + request.requestMethod() + " : " + httpResponse.getURL());
		
		return "";
	};

	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * newServiceInstance
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route newServiceInstance = (Request request, Response response) -> {
		
		Token token = getToken(request);
		
		String id = request.params(":id");
		
		SalesforceConnector salesforceConnector = new SalesforceConnector(id);
		
		List<ServiceProvider> providers = Collections.emptyList();
		
		HttpResponse httpResponse = RestResource.get(API_ENDPOINT)
				.bearerAuthorization(token.getAccessToken())
				.path("providers")
				.queryParameter("localeSidKey", "en_US")
				.queryParameter("languageLocaleKey", "en_US")
				.execute();
			
		if (httpResponse.getStatusCode() == Status.OK) {
			providers = httpResponse.getEntityList(ServiceProvider.class);
		} else {
			throw new BadRequestException(httpResponse.getAsString());
		}
    	
		Map<String, Object> model = getModel();
		model.put("serviceProviders", providers);
		model.put("salesforceConnector", salesforceConnector);
		model.put("mode", "add");
    	
		return render(request, model, Path.Template.SERVICE_CATALOG);
	};
	
	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * reviewPlans
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */
	
	public Route reviewPlans = (Request request, Response response) -> {
		Token token = getToken(request);
		
		String id = request.params(":id");
		String serviceProviderId = request.params(":serviceProviderId");
		String serviceType = request.params(":serviceType");
		//String code = request.params(":code");
		
		SalesforceConnector salesforceConnector = new SalesforceConnector(id);
		ServiceProvider provider = null;
		
		HttpResponse httpResponse = RestResource.get(API_ENDPOINT)
				.bearerAuthorization(token.getAccessToken())
				.path("providers")
				.path(serviceProviderId)
				.execute();
			
		if (httpResponse.getStatusCode() == Status.OK) {
			provider = httpResponse.getEntity(ServiceProvider.class);
		} else {
			throw new BadRequestException(httpResponse.getAsString());
		}
		
		//salesforceConnector = getSalesforceConnector(token, id);
		
		Service service = provider.getServices()
				.stream()
				.filter(s -> s.getType().equals(serviceType))
				.findFirst()
				.get();
		
//		Plan plan = service.getPlans()
//				.stream()
//				.filter(p -> p.getCode().equals(code))
//				.findFirst()
//				.get();
		
		Map<String, Object> model = getModel();
		model.put("serviceProvider", provider);
		model.put("service", service);
		//model.put("plan", plan);
		model.put("salesforceConnector", salesforceConnector);
    	
		return render(request, model, Path.Template.REVIEW_SERVICE_PLANS);
	};

	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * getSobjects
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route getSobjects = (Request request, Response response) -> {
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");
		
		String message = null;
		
		SalesforceConnector salesforceConnector = null;
		
		HttpResponse httpResponse = RestResource.get(API_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
				.path(id)
				.path("service")
				.path(key)
				.path("sobjects")
    			.execute();
	
		response.status(httpResponse.getStatusCode());
		
		if (httpResponse.getStatusCode() == Status.OK) {
			salesforceConnector = httpResponse.getEntity(SalesforceConnector.class);
		} else {
			message = httpResponse.getEntity(JsonNode.class).get("message").asText();
		}
		
		ServiceInstance serviceInstance = salesforceConnector.getServiceInstance(key);
    	
		Map<String, Object> model = getModel();
		model.put("salesforceConnector", salesforceConnector);
		model.put("serviceInstance", serviceInstance);
		model.put("message", message);
		
		return render(request, model, "secure/fragments/sobjects-table.html");
	};

	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * getFields
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route getFields = (Request request, Response response) -> {
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");
		String sobject = request.params(":sobject");
		
		String message = null;
		
		List<Field> fields = null;
		
		HttpResponse httpResponse = RestResource.get(API_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
				.path(id)
				.path("service")
				.path(key)
				.path("sobjects")
				.path(sobject)
				.path("fields")
    			.execute();
	
		response.status(httpResponse.getStatusCode());
		
		if (httpResponse.getStatusCode() == Status.OK) {
			fields = httpResponse.getEntityList(Field.class);
		} else {
			message = httpResponse.getEntity(JsonNode.class).get("message").asText();
		}
		
		GetSalesforceConnectorRequest getSalesforceConnectorRequest = new GetSalesforceConnectorRequest()
				.withAccessToken(token.getAccessToken())
				.withId(id);
    	
		SalesforceConnector salesforceConnector = salesforceConnectorService.getSalesforceConnector(getSalesforceConnectorRequest);
		
		ServiceInstance serviceInstance = salesforceConnector.getServiceInstance(key);
		
		String query = serviceInstance
				.getEventListeners()
				.stream()
				.filter(p -> sobject.equals(p.getName()))
				.findFirst()
				.get()
				.getCallback();
		
		if (query == null || query.trim().length() == 0) {
			query = "Select %s From " + sobject;
			query = String.format(query, fields.stream().map(e -> e.getName()).collect(Collectors.joining(", ")));
		}
		
		Map<String, Object> model = getModel();
		model.put("fields", fields);
		model.put("query", query);
		model.put("sobject", sobject);
		model.put("salesforceConnector", salesforceConnector);
		model.put("serviceInstance", serviceInstance);
		model.put("message", message);
		
		return render(request, model, Path.Template.QUERY_EDIT);
	};

	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * testQuery
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route testQuery = (Request request, Response response) -> {	
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");

		String queryString = request.queryParams("callback");
		
		HttpResponse httpResponse = null;
		try {
			httpResponse = RestResource.get(API_ENDPOINT)
					.accept(MediaType.APPLICATION_JSON)
					.bearerAuthorization(token.getAccessToken())
					.path("connectors")
					.path("salesforce")
					.path(id)
					.path("service")
					.path(key)
					.path("query")
					.queryParameter("q", URLEncoder.encode(queryString.concat(" Limit 1"), "UTF-8"))
					.execute();
			
		} catch (HttpRequestException | UnsupportedEncodingException e) {
			throw new InternalServerErrorException(e);
		} 
		
		Map<String, Object> model = getModel();
		
		if (httpResponse.getStatusCode() == Status.OK) {
			model.put("successMessage", MessageProvider.getMessage(Locale.US, "testSuccess"));
			return render(request, model, "secure/fragments/success-message.html");
		} else {
			model.put("message", httpResponse.getEntity(JsonNode.class).get("message").asText());
			return render(request, model, "secure/fragments/error-message.html");
		}
	};
	
	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * testConnection
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route testConnection = (Request request, Response response) -> {	
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");
		
		HttpResponse httpResponse = RestResource.post(API_ENDPOINT)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
				.path(id)
				.path("environment")
    			.path(key)
				.parameter("test", Boolean.TRUE.toString())
    			.execute();
		
		if (httpResponse.getStatusCode() != Status.OK) {
			throw new BadRequestException(httpResponse.getAsString());
		}
		
		Environment environment = httpResponse.getEntity(Environment.class);
		
		if (environment.getIsValid()) {
			environment.setTestMessage(MessageProvider.getMessage(getDefaultLocale(request), "test.connection.success"));
		} else {
			environment.setTestMessage(String.format("%s: %s", MessageProvider.getMessage(getDefaultLocale(request), "test.connection.fail"), environment.getTestMessage()));
		}
		
		return objectMapper.writeValueAsString(environment);
	};
	
//	private ModelAndView saveEnvironmentVariables(Request request, Response response) {
//		Token token = getToken(request);
//		
//		String id = request.params(":id");
//		String key = request.params(":key");
//		String sourceEnvironment = request.queryParams("sourceEnvironment");
//		
//		Map<String, Object> model = getModel();
//		
//		String[] variables = request.queryParamsValues("variable");
//		String[] values = request.queryParamsValues("value");
//		
//		ArrayNode node = new ObjectMapper().createArrayNode();
//
//		if (variables != null) {
//			for (int i = 0; i < variables.length; i++) {
//				if (variables != null && ! variables[i].trim().isEmpty()) {
//					node.addObject()
//						.put("value", values[i])
//						.put("variable", variables[i])
//						.put("encrypted", request.queryMap().get("encrypted" + i).hasValue() ? Boolean.TRUE : Boolean.FALSE);
//				}
//			}
//		} else {
//			model.put("message", MessageProvider.getMessage(Locale.US, "nothingToSave"));
//			return new ModelAndView(model, "secure/fragments/error-message.html");
//		}
//
//		SalesforceConnector salesforceConnector = null;
//
//		String successMessage = null;
//
//		HttpResponse httpResponse = RestResource.post(API_ENDPOINT)
//					.contentType(MediaType.APPLICATION_JSON)
//					.accept(MediaType.APPLICATION_JSON)
//					.bearerAuthorization(token.getAccessToken())
//					.path("connectors")
//	    			.path("salesforce")
//					.path(id)
//					.path("service")
//					.path(key)
//					.path("variables")
//					.path(sourceEnvironment)
//					.body(node)
//	    			.execute();
//		
//		response.status(httpResponse.getStatusCode());
//		
//		if (httpResponse.getStatusCode() == Status.OK || httpResponse.getStatusCode() == Status.CREATED) {
//			successMessage = MessageProvider.getMessage(Locale.US, "saveSuccess");
//				
//			salesforceConnector = httpResponse.getEntity(SalesforceConnector.class);
//				
//			ServiceInstance serviceInstance = salesforceConnector.getServiceInstance(key);
//				
//			Optional<Environment> environment = serviceInstance.getEnvironment(sourceEnvironment);
//				
//			if (environment.isPresent() && environment.get().getEnvironmentVariables().size() == 0) {
//				environment.get().getEnvironmentVariables().add(new EnvironmentVariable());
//			}
//				
//			model.put("successMessage", successMessage);
//			model.put("serviceInstance", serviceInstance);
//			model.put("environment", environment.get());
//			return new ModelAndView(model, "secure/fragments/success-message.html");
//		} else {
//			model.put("message", httpResponse.getEntity(JsonNode.class).get("message").asText());
//			return new ModelAndView(model, "secure/fragments/error-message.html");
//		}
//	}
	
	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * deploy
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route deploy = (Request request, Response response) -> {	
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");
		String environment = request.params(":environment");
		
		Map<String, Object> model = getModel();
		
		HttpResponse httpResponse = RestResource.post(API_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
				.path(id)
				.path("service")
				.path(key)
				.path("deployment")
				.path(environment)
    			.execute();
		
		response.status(httpResponse.getStatusCode());
		
		return render(request, model, "secure/fragments/success-message.html");
	};
	
	/**
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * saveEventListeners
	 * 
	 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public Route saveEventListeners = (Request request, Response response) -> {	
		Token token = getToken(request);
		
		String id = request.params(":id");
		String key = request.params(":key");
		
		Map<String, Object> model = getModel();
		
		String[] sobjects = request.queryParamsValues("sobject");
		
		if (sobjects == null) {
			model.put("message", MessageProvider.getMessage(Locale.US, "nothingToSave"));
			return new ModelAndView(model, "secure/fragments/error-message.html");
		}
		
		String[] callbacks = request.queryParamsValues("callback");
		
		List<String> create = new ArrayList<String>();
		List<String> update = new ArrayList<String>();
		List<String> delete = new ArrayList<String>();
		
		if (request.queryParamsValues("create") != null) {
			create = Arrays.asList(request.queryParamsValues("create"));
		}
		
		if (request.queryParamsValues("update") != null) {
			update = Arrays.asList(request.queryParamsValues("update"));
		}
		
		if (request.queryParamsValues("delete") != null) {
			delete = Arrays.asList(request.queryParamsValues("delete"));
		}
		
		ArrayNode node = new ObjectMapper().createArrayNode();
		
		for (int i = 0; i < sobjects.length; i++) {
			node.addObject()
				.put("name", sobjects[i])
				.put("create", create.contains(sobjects[i]))
				.put("update", update.contains(sobjects[i]))
				.put("delete", delete.contains(sobjects[i]))
				.put("callback", callbacks[i]);
		}
	
		HttpResponse httpResponse = RestResource.post(API_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.bearerAuthorization(token.getAccessToken())
				.path("connectors")
    			.path("salesforce")
				.path(id)
				.path("service")
				.path(key)
				.path("listeners")
				.body(node)
    			.execute();
		
		SalesforceConnector salesforceConnector = httpResponse.getEntity(SalesforceConnector.class);
		
		model.put("salesforceConnector", salesforceConnector);
		model.put("successMessage", MessageProvider.getMessage(Locale.US, "saveSuccess"));
		
		return render(request, model, "secure/fragments/success-message.html");
	};
}