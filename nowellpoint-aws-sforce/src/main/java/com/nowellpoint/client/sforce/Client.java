package com.nowellpoint.client.sforce;

import java.nio.charset.StandardCharsets;

import com.nowellpoint.aws.http.HttpResponse;
import com.nowellpoint.aws.http.MediaType;
import com.nowellpoint.aws.http.RestResource;
import com.nowellpoint.aws.http.Status;
import com.nowellpoint.client.sforce.model.DescribeSobjectsResult;
import com.nowellpoint.client.sforce.model.Error;
import com.nowellpoint.client.sforce.model.Identity;
import com.nowellpoint.client.sforce.model.Organization;
import com.nowellpoint.client.sforce.model.User;

public class Client {
	
	private static final String USER_FIELDS = "Id,Username,LastName,FirstName,Name,CompanyName,Division,Department,"
			+ "Title,Street,City,State,PostalCode,Country,Latitude,Longitude,"
			+ "Email,SenderEmail,SenderName,Signature,Phone,Fax,MobilePhone,Alias,"
			+ "CommunityNickname,IsActive,TimeZoneSidKey,LocaleSidKey,EmailEncodingKey,"
			+ "UserType,LanguageLocaleKey,EmployeeNumber,DelegatedApproverId,ManagerId,AboutMe";
	
	private static final String ORGANIZATION_FIELDS = "Id,Division,Fax,DefaultLocaleSidKey,FiscalYearStartMonth,"
 			+ "InstanceName,IsSandbox,LanguageLocaleKey,Name,OrganizationType,Phone,PrimaryContact,"
 			+ "UsesStartDateAsFiscalYearName";
	
	public Client() {
		
	}
	
	/**
	 * 
	 * @param id
	 * @param accessToken
	 * @return
	 */
	
	public Identity getIdentity(GetIdentityRequest request) {
		HttpResponse httpResponse = RestResource.get(request.getId())
				.acceptCharset(StandardCharsets.UTF_8)
				.bearerAuthorization(request.getAccessToken())
				.accept(MediaType.APPLICATION_JSON)
				.queryParameter("version", "latest")
				.execute();
    	
		Identity identity = null;
    	
    	if (httpResponse.getStatusCode() == Status.OK) {
    		identity = httpResponse.getEntity(Identity.class);
		} else {
			throw new ClientException(httpResponse.getStatusCode(), httpResponse.getEntity(Error.class));
		}
    	
    	return identity;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	
	public User getUser(GetUserRequest request) {
		HttpResponse httpResponse = RestResource.get(request.getSobjectUrl())
     			.bearerAuthorization(request.getAccessToken())
     			.path("User")
     			.path(request.getUserId())
     			.queryParameter("fields", USER_FIELDS)
     			.queryParameter("version", "latest")
     			.execute();
		
		User user = null;
		
		if (httpResponse.getStatusCode() == Status.OK) {
			user = httpResponse.getEntity(User.class);
		} else {
			throw new ClientException(httpResponse.getStatusCode(), httpResponse.getEntity(Error.class));
		}
		
		return user;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	
	public Organization getOrganization(GetOrganizationRequest request) {
		HttpResponse httpResponse = RestResource.get(request.getSobjectUrl())
     			.bearerAuthorization(request.getAccessToken())
     			.path("Organization")
     			.path(request.getOrganizationId())
     			.queryParameter("fields", ORGANIZATION_FIELDS)
     			.queryParameter("version", "latest")
     			.execute();
		
		Organization organization = null;
		
		if (httpResponse.getStatusCode() == Status.OK) {
			organization = httpResponse.getEntity(Organization.class);
		} else {
			throw new ClientException(httpResponse.getStatusCode(), httpResponse.getEntity(Error.class));
		}
		
		return organization;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	
	public DescribeSobjectsResult describe(DescribeSobjectsRequest request) {
		HttpResponse httpResponse = RestResource.get(request.getSobjectUrl())
				.accept(MediaType.APPLICATION_JSON)
				.bearerAuthorization(request.getAccessToken())
				.execute();
		
		DescribeSobjectsResult result = null;
		
		if (httpResponse.getStatusCode() == Status.OK) {
			result = httpResponse.getEntity(DescribeSobjectsResult.class);
		} else {
			throw new ClientException(httpResponse.getStatusCode(), httpResponse.getEntity(Error.class));
		}
		
		return result;
	}
}