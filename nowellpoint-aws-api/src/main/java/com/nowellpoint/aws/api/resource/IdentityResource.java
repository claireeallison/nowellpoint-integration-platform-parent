package com.nowellpoint.aws.api.resource;

import static com.mongodb.client.model.Filters.eq;
import static com.nowellpoint.aws.api.data.CacheManager.deserialize;
import static com.nowellpoint.aws.api.data.CacheManager.serialize;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import javax.ws.rs.core.Response.Status;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nowellpoint.aws.api.data.CacheManager;
import com.nowellpoint.aws.api.data.Datastore;
import com.nowellpoint.aws.model.Event;
import com.nowellpoint.aws.model.EventAction;
import com.nowellpoint.aws.model.EventBuilder;
import com.nowellpoint.aws.model.admin.Properties;
import com.nowellpoint.aws.model.data.Identity;
import com.nowellpoint.aws.provider.DynamoDBMapperProvider;

@Path("/identity")
public class IdentityResource {
	
	@Inject
	private CacheManager cacheManager;

	@Context
	private UriInfo uriInfo;
	
	private DynamoDBMapper mapper = DynamoDBMapperProvider.getDynamoDBMapper();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signUp(Identity resource) {
		
		//
		//
		//
				
		Event event = null;
		try {			
			event = new EventBuilder().withAccountId(System.getProperty(Properties.DEFAULT_ACCOUNT_ID))
					.withEventAction(EventAction.SIGN_UP)
					.withEventSource(uriInfo.getRequestUri())
					.withPropertyStore(System.getenv("PROPERTY_STORE"))
					.withPayload(resource)
					.withType(Identity.class)
					.build();
		} catch (JsonProcessingException e) {
			throw new WebApplicationException(e);
		}
		
		//
		//
		//
		
		mapper.save(event);
		
		//
		//
		//
		
		cacheManager.getCache().set(event.getId().getBytes(), serialize(resource));
		
		//
		//
		//
		
		URI uri = UriBuilder.fromUri(uriInfo.getBaseUri())
				.path(IdentityResource.class)
				.path("/{id}")
				.build(event.getId());
		
		//
		//
		//
		
		return Response.created(uri).build();
	}
	
	@GET
	@Path("/{identityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIdentity(@PathParam("identityId") String identityId) {
		
		//
		//
		//
		
		byte[] bytes = cacheManager.getCache().get(identityId.getBytes());

		//
		//
		//
		
		Identity identity = null;
		
		if (bytes != null) {
			identity = (Identity) deserialize(bytes);
		} else {
			identity = Datastore.getDatabase().getCollection( "identities" )
					.withDocumentClass( Identity.class )
					.find( eq ( "_id", identityId ) )
					.first();
			
			cacheManager.getCache().setex(identity.getId().getBytes(), 259200, serialize(identity));
		}
		
		//
		//
		//
		
		return Response.status(Status.OK)
				.entity(identity)
				.build();
	}
}