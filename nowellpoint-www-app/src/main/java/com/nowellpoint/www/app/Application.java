package com.nowellpoint.www.app;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nowellpoint.aws.http.HttpRequestException;
import com.nowellpoint.aws.http.HttpResponse;
import com.nowellpoint.aws.http.RestResource;
import com.nowellpoint.aws.idp.model.Token;
import com.nowellpoint.www.app.model.IsoCountry;
import com.nowellpoint.www.app.view.AccountProfileController;
import com.nowellpoint.www.app.view.AdministrationController;
import com.nowellpoint.www.app.view.ApplicationController;
import com.nowellpoint.www.app.view.AuthenticationController;
import com.nowellpoint.www.app.view.ContactUsController;
import com.nowellpoint.www.app.view.DashboardController;
import com.nowellpoint.www.app.view.NotificationController;
import com.nowellpoint.www.app.view.Path;
import com.nowellpoint.www.app.view.ProjectController;
import com.nowellpoint.www.app.view.SalesforceConnectorController;
import com.nowellpoint.www.app.view.SalesforceOauthController;
import com.nowellpoint.www.app.view.ServiceProviderController;
import com.nowellpoint.www.app.view.SetupController;
import com.nowellpoint.www.app.view.SignUpController;
import com.nowellpoint.www.app.view.VerifyEmailController;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.servlet.SparkApplication;
import spark.template.freemarker.FreeMarkerEngine;

public class Application implements SparkApplication {
	
	private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
    	new Application().init();
    }

	@Override
	public void init() {
		
		//
		// add resource bundle
		//

		ResourceBundle messages = ResourceBundle.getBundle("messages", Locale.US);
        
        //
		// Configure FreeMarker
		//
        
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

		//
		// set configuration options
		//

        cfg.setClassForTemplateLoading(this.getClass(), "/views");
		cfg.setDefaultEncoding("UTF-8");
		cfg.setLocale(Locale.US);
        
        //
        // load countries list
        //
		
		try {			
			List<IsoCountry> isoCountries = loadCountries();
			
			List<IsoCountry> filteredList = isoCountries.stream()
					.filter(country -> "US".equals(country.getLanguage()))
					.sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
					.collect(Collectors.toList());
			
			cfg.setSharedVariable("countryList", filteredList);
			
		} catch (TemplateModelException e) {
			e.printStackTrace();
			halt();
		}
        
        //
        // load messages for locale
        //
        
        messages.keySet().stream().forEach(message -> {
        	try {
				cfg.setSharedVariable(message, messages.getString(message));
			} catch (TemplateException e) {
				e.printStackTrace();
				halt();
			}
        });
        
        before("/", (request, response) -> {
        	
        });
        
        //
        //
        //
        
        before("/app/*", (request, response) -> verify(request, response));
        
        //
        // configure routes
        //
        
        get("/", (request, response) -> getContextRoot(request, response), new FreeMarkerEngine(cfg));
        
        //
        // search
        //
        
        get("/services", (request, response) -> getServices(request, response), new FreeMarkerEngine(cfg));
        
        //
        //
        //
        
        get("/healthcheck", (request, response) -> {
        	response.status(200);
        	return "";
        });
        
        AuthenticationController authenticationController = new AuthenticationController(cfg);
        AccountProfileController accountProfileController = new AccountProfileController(cfg);
        VerifyEmailController verifyEmailController = new VerifyEmailController(cfg);
        DashboardController dashboardController = new DashboardController(cfg);
        AdministrationController administrationController = new AdministrationController(cfg);
        SignUpController signUpController = new SignUpController(cfg);
        NotificationController notificationController = new NotificationController(cfg);
        SetupController setupController = new SetupController(cfg);
        SalesforceOauthController salesforceOauthController = new SalesforceOauthController(cfg);
        ContactUsController contactUsController = new ContactUsController(cfg);
        ApplicationController applicationController = new ApplicationController(cfg);
        ProjectController projectController = new ProjectController(cfg);
        ServiceProviderController serviceProviderController = new ServiceProviderController(cfg);
        SalesforceConnectorController salesforceConnectorController = new SalesforceConnectorController(cfg);
        
        // setup routes
        
        get(Path.Route.LOGIN, authenticationController.showLoginPage);
        post(Path.Route.LOGIN, authenticationController.login);
        get(Path.Route.LOGOUT, authenticationController.logout);
        
        get(Path.Route.CONTACT_US, contactUsController.showContactUs);
		post(Path.Route.CONTACT_US, contactUsController.contactUs);
        
        get(Path.Route.SIGN_UP, signUpController.showSignUp);
		post(Path.Route.SIGN_UP, signUpController.signUp);
        
        get(Path.Route.VERIFY_EMAIL, verifyEmailController.verifyEmail);
        
        get(Path.Route.START, dashboardController.showStartPage);
        get(Path.Route.DASHBOARD, dashboardController.showDashboard);
        
        get(Path.Route.NOTIFICATIONS, notificationController.showNotifications);
        
        get(Path.Route.SETUP, setupController.showSetup);
        
        get(Path.Route.APPLICATIONS.concat("/provider/:id"), applicationController.newApplication);
		get(Path.Route.APPLICATIONS.concat("/:id"), applicationController.getApplication);
		get(Path.Route.APPLICATIONS, applicationController.getApplications);
		delete(Path.Route.APPLICATIONS.concat("/:id"), applicationController.deleteApplication);
		post(Path.Route.APPLICATIONS, applicationController.saveApplication);
		
		get(Path.Route.PROJECTS, projectController.getProjects);
		get(Path.Route.PROJECTS.concat("/:id"), projectController.getProject);
		post(Path.Route.PROJECTS, projectController.saveProject);
		delete(Path.Route.PROJECTS.concat("/:id"), projectController.deleteProject);
		
		get(Path.Route.PROVIDERS, (request, response) -> serviceProviderController.getServiceProviders);
        get(Path.Route.PROVIDERS.concat("/:id"), serviceProviderController.getServiceProvider);
        delete(Path.Route.PROVIDERS.concat("/:id"), serviceProviderController.deleteServiceProvider);
        
        get(Path.Route.SALESFORCE_OAUTH, salesforceOauthController.oauth);
        get(Path.Route.SALESFORCE_OAUTH.concat("/callback"), salesforceOauthController.callback);
        get(Path.Route.SALESFORCE_OAUTH.concat("token"), salesforceOauthController.getSalesforceToken);
        
        get(Path.Route.ADMINISTRATION, administrationController.showAdministrationHome);	
        get(Path.Route.ADMINISTRATION.concat("/cache"), administrationController.showManageCache);	
        get(Path.Route.ADMINISTRATION.concat("/properties"), administrationController.showManageProperties);	
		get(Path.Route.ADMINISTRATION.concat("/cache/purge"), administrationController.purgeCache);
        
        get(Path.Route.ACCOUNT_PROFILE, accountProfileController.getAccountProfile);
        post(Path.Route.ACCOUNT_PROFILE, accountProfileController.updateAccountProfile);
        get(Path.Route.ACCOUNT_PROFILE.concat("/edit"), accountProfileController.editAccountProfile);
        get(Path.Route.ACCOUNT_PROFILE.concat("/disable"), accountProfileController.disableAccountProfile);
        delete(Path.Route.ACCOUNT_PROFILE.concat("/picture"), accountProfileController.removeProfilePicture);
        get(Path.Route.ACCOUNT_PROFILE_ADDRESS, accountProfileController.editAccountProfileAddress);
        post(Path.Route.ACCOUNT_PROFILE_ADDRESS, accountProfileController.updateAccountProfileAddress);
        get(Path.Route.ACCOUNT_PROFILE_PAYMENT_METHODS.concat("/:token/view"), accountProfileController.getCreditCard);
        get(Path.Route.ACCOUNT_PROFILE_PAYMENT_METHODS.concat("/new"), accountProfileController.newCreditCard);
        get(Path.Route.ACCOUNT_PROFILE_PAYMENT_METHODS.concat("/:token/edit"), accountProfileController.editCreditCard);
        post(Path.Route.ACCOUNT_PROFILE_PAYMENT_METHODS, accountProfileController.addCreditCard);
        post(Path.Route.ACCOUNT_PROFILE_PAYMENT_METHODS.concat("/:token"), accountProfileController.updateCreditCard);
        post(Path.Route.ACCOUNT_PROFILE_PAYMENT_METHODS.concat("/:token/primary"), accountProfileController.setPrimaryCreditCard);
        delete(Path.Route.ACCOUNT_PROFILE_PAYMENT_METHODS.concat("/:token"), accountProfileController.removeCreditCard);
        
        get(Path.Route.CONNECTORS_SALESFORCE, salesforceConnectorController.getSalesforceConnectors);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/environments/add"), salesforceConnectorController.newEnvironment);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/environments/:key/view"), salesforceConnectorController.getEnvironment);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/environments/:key/edit"), salesforceConnectorController.editEnvironment);
        post(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/environments"), salesforceConnectorController.addEnvironment);
        post(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/environments/:key"), salesforceConnectorController.updateEnvironment);
        delete(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/environments/:key"), salesforceConnectorController.removeEnvironment);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/environments/:key/test"), salesforceConnectorController.testConnection);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/service/:key/listeners"), salesforceConnectorController.getEventListeners);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/service/:key/targets"), salesforceConnectorController.getTargets);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/service/:key/environments"), salesforceConnectorController.getEnvironments);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/service/:key/details"), salesforceConnectorController.getServiceInstance);
        delete(Path.Route.CONNECTORS_SALESFORCE.concat("/:id"), salesforceConnectorController.deleteSalesforceConnector);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/edit"), salesforceConnectorController.editSalesforceConnector);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/service/:key/listeners/:environment/fields/:sobject"), salesforceConnectorController.getFields);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id"), salesforceConnectorController.getSalesforceConnector);
        post(Path.Route.CONNECTORS_SALESFORCE.concat("/:id"), salesforceConnectorController.updateSalesforceConnector);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/providers"), salesforceConnectorController.getServiceProviders);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/providers/:serviceProviderId/service/:serviceType/plan/:code"), salesforceConnectorController.reviewPlan);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/service/:key/sobjects"), salesforceConnectorController.getSobjects);
        post(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/service/:key/deployment/:environment"), salesforceConnectorController.deploy);
        post(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/service/:key/listeners"), salesforceConnectorController.saveEventListeners);
        get(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/service/:key/listeners/query"), salesforceConnectorController.testQuery);
        post(Path.Route.CONNECTORS_SALESFORCE, (request, response) -> salesforceConnectorController.createSalesforceConnector);
        post(Path.Route.CONNECTORS_SALESFORCE.concat("/:id/providers/:serviceProviderId/service/:serviceType/plan/:code"), salesforceConnectorController.addServiceInstance);
        
        //
        // exception handlers
        //
        
        exception(NotAuthorizedException.class, authenticationController.handleNotAuthorizedException);
        
        exception(BadRequestException.class, (exception, request, response) -> {
            response.status(400);
            response.body(exception.getMessage());
        });
        
        exception(NotFoundException.class, (exception, request, response) -> {
            response.status(404);
            response.body(exception.getMessage());
        });
        
        exception(InternalServerErrorException.class, (exception, request, response) -> {
            response.status(500);
            response.body(exception.getMessage());
        });
	}
	
	/**
	 * 
	 * @return
	 */
	
	private static List<IsoCountry> loadCountries() {
		try {
			HttpResponse httpResponse = RestResource.get(System.getenv("NCS_API_ENDPOINT"))
					.header("x-api-key", System.getenv("NCS_API_KEY"))
					.path("iso-countries")
					.execute();
			
			return httpResponse.getEntityList(IsoCountry.class);
			
		} catch (HttpRequestException e) {
			e.printStackTrace();
			halt();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	
	private static ModelAndView getContextRoot(Request request, Response response) {
    	Map<String,Object> model = new HashMap<String,Object>();
    	return new ModelAndView(model, "index.html");
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	
	private static ModelAndView getServices(Request request, Response response) {
    	Map<String,Object> model = new HashMap<String,Object>();
		return new ModelAndView(model, "services.html");
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	
	private static void verify(Request request, Response response) throws JsonParseException, JsonMappingException, IOException {
		
    	Optional<String> cookie = Optional.ofNullable(request.cookie("com.nowellpoint.auth.token"));
    	if (cookie.isPresent()) {
    		Token token = objectMapper.readValue(cookie.get(), Token.class);
    		request.attribute("token", token);
    	} else {
    		response.cookie("/", "redirectUrl", request.pathInfo(), 72000, Boolean.TRUE);
    		response.redirect("/login");
    		halt();
    	}
	}
}