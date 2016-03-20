package com.nowellpoint.aws.api.resource;

import java.net.URI;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.nowellpoint.aws.api.dto.ServiceProviderDTO;
import com.nowellpoint.aws.api.dto.sforce.DescribeSObjectsResult;
import com.nowellpoint.aws.api.dto.sforce.ServiceProviderInfo;
import com.nowellpoint.aws.api.service.SalesforceService;
import com.nowellpoint.aws.api.service.ServiceProviderService;

@Path("/providers")
public class ServiceProviderResource {
	
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private ServiceProviderService serviceProviderService;
	
	@Inject
	private SalesforceService salesforceService;
	
	@Context 
	private SecurityContext securityContext;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllActive(
    		@QueryParam("localeSidKey") String localeSidKey, 
    		@QueryParam("languageLocaleKey") String languageLocaleKey) {
		
		Set<ServiceProviderDTO> resources = serviceProviderService.getAllActive(localeSidKey, languageLocaleKey);
		
		return Response.ok(resources).build();
    }
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServiceProvider(@PathParam("id") String id) {
		String subject = securityContext.getUserPrincipal().getName();
		
		ServiceProviderDTO resource = serviceProviderService.getServiceProvider(id, subject);
		
		return Response.ok(resource)
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response createServiceProvider(ServiceProviderDTO resource) {
		String subject = securityContext.getUserPrincipal().getName();
		
		serviceProviderService.createServiceProvider(subject, resource, uriInfo.getBaseUri());
		
		URI uri = UriBuilder.fromUri(uriInfo.getBaseUri())
				.path(ApplicationResource.class)
				.path("/{id}")
				.build(resource.getId());
		
		return Response.created(uri)
				.entity(resource)
				.build();
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteServiceProvider(@PathParam("id") String id) {
		String subject = securityContext.getUserPrincipal().getName();
		
		serviceProviderService.deleteServiceProvider(id, subject, uriInfo.getBaseUri());
		
		return Response.noContent().build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateServiceProvider(@PathParam("id") String id, ServiceProviderDTO resource) {
		String subject = securityContext.getUserPrincipal().getName();
		
		resource.setId(id);
		
		serviceProviderService.updateServiceProvider(subject, resource, uriInfo.getBaseUri());
		
		return Response.ok(resource).build();
	}
	
	@GET
	@Path("/salesforce")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSalesforceProvider(@QueryParam(value="code") String code) {
		String subject = securityContext.getUserPrincipal().getName();
		
		ServiceProviderInfo resource = salesforceService.getAsServiceProvider(subject, code);
		
		DescribeSObjectsResult result = salesforceService.describe(subject, resource.getAccount());
		
		resource.setSobjects(result.getSobjects());
		
		return Response.ok(resource).build();
	}
}