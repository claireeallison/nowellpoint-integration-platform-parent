package com.nowellpoint.www.app.view;

import static spark.Spark.get;

import java.util.Map;

import com.nowellpoint.aws.http.HttpResponse;
import com.nowellpoint.aws.http.RestResource;
import com.nowellpoint.aws.http.Status;
import com.nowellpoint.www.app.util.MessageProvider;
import com.nowellpoint.www.app.util.Path;

import freemarker.template.Configuration;
import spark.Request;
import spark.Response;

public class VerifyEmailController extends AbstractController {
	
	public static class Template {
		public static final String ADMINISTRATION_HOME = String.format(APPLICATION_CONTEXT, "administration-home.html");
		public static final String CACHE_MANAGER = String.format(APPLICATION_CONTEXT, "cache.html");
		public static final String PROPERTY_MANAGER = String.format(APPLICATION_CONTEXT, "properties-list.html");
		public static final String VERIFY_EMAIL = "verify-email.html";
	}

	public VerifyEmailController() {
		super(VerifyEmailController.class);
	}
	
	@Override
	public void configureRoutes(Configuration configuration) {
		get(Path.Route.VERIFY_EMAIL, (request, response) -> verifyEmail(configuration, request, response));
	}
	
	/**
	 * 
	 * @param configuration
	 * @param request
	 * @param response
	 * @return
	 */
	
	private String verifyEmail(Configuration configuration, Request request, Response response) {
		HttpResponse httpResponse = RestResource.post(API_ENDPOINT)
				.path("signup")
				.path("verify-email")
				.path(request.queryParams("emailVerificationToken"))
				.execute();
		
		Map<String,Object> model = getModel();
		
    	if (httpResponse.getStatusCode() == Status.OK) {
    		model.put("successMessage", MessageProvider.getMessage(getLocale(request), "email.verification.success"));
    	} else {
    		model.put("errorMessage", MessageProvider.getMessage(getLocale(request), "email.verification.failure"));
    	}
    	
    	return render(configuration, request, response, model, Template.VERIFY_EMAIL);
	}
}