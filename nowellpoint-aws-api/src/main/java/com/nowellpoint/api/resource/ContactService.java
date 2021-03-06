package com.nowellpoint.api.resource;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.logging.Logger;

import com.nowellpoint.api.model.sforce.Lead;
import com.nowellpoint.aws.http.HttpResponse;
import com.nowellpoint.aws.http.RestResource;
import com.nowellpoint.aws.model.admin.Properties;
import com.nowellpoint.client.sforce.Authenticators;
import com.nowellpoint.client.sforce.OauthAuthenticationResponse;
import com.nowellpoint.client.sforce.OauthRequests;
import com.nowellpoint.client.sforce.UsernamePasswordGrantRequest;
import com.nowellpoint.client.sforce.model.Error;
import com.nowellpoint.client.sforce.model.Token;

@Path("/contact")
public class ContactService {
	
	private static final Logger LOGGER = Logger.getLogger(ContactService.class);

	@Context
	private UriInfo uriInfo;

	@PermitAll
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response contact(
			@FormParam("leadSource") @NotEmpty(message = "Lead Source must be filled in") String leadSource,
    		@FormParam("firstName") String firstName,
    		@FormParam("lastName") @NotEmpty(message="Last Name must be filled in") String lastName,
    		@FormParam("email") @Email @NotEmpty(message="Email must be filled in") String email,
    		@FormParam("phone") String phone,
    		@FormParam("company") String company,
    		@FormParam("description") String description) {
		
		Lead lead = new Lead();
		lead.setLeadSource(leadSource);
		lead.setFirstName(firstName);
		lead.setLastName(lastName);
		lead.setEmail(email);
		lead.setDescription(description);
		lead.setCompany(company);
		lead.setPhone(phone);
		lead.setCountryCode("US");
		
		UsernamePasswordGrantRequest request = OauthRequests.PASSWORD_GRANT_REQUEST.builder()
				.setClientId(System.getProperty(Properties.SALESFORCE_CLIENT_ID))
				.setClientSecret(System.getProperty(Properties.SALESFORCE_CLIENT_SECRET))
				.setUsername(System.getProperty(Properties.SALESFORCE_USERNAME))
				.setPassword(System.getProperty(Properties.SALESFORCE_PASSWORD))
				.setSecurityToken(System.getProperty(Properties.SALESFORCE_SECURITY_TOKEN))
				.build();
		
		OauthAuthenticationResponse response = Authenticators.PASSWORD_GRANT_AUTHENTICATOR
				.authenticate(request);
		
		Token token = response.getToken();
		
		HttpResponse httpResponse = RestResource.post(token.getInstanceUrl())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.path("services/apexrest/nowellpoint/lead")
				.bearerAuthorization(token.getAccessToken())
				.body(lead)
				.execute();
		
		LOGGER.info("Status Code: " + httpResponse.getStatusCode() + " Target: " + httpResponse.getURL());	
		
		if (httpResponse.getStatusCode() != 200 && httpResponse.getStatusCode() != 201) {
			Error error = httpResponse.getEntity(Error.class);
			ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
			builder.entity(error);
			throw new WebApplicationException(builder.build());
		}
			
		String leadId = httpResponse.getAsString();
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("leadId", leadId);
		
		return Response.ok(response).build();
	}
}