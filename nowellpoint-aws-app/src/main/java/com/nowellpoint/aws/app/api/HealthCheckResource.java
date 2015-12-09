package com.nowellpoint.aws.app.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.nowellpoint.aws.app.data.Datastore;

@Path("/health")
public class HealthCheckResource {
	
	@GET
	public Response checkHealth() {
		Datastore.checkStatus();
		return Response.status(Status.OK).build();
	}
}