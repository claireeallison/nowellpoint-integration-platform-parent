package com.nowellpoint.aws.api.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/health")
public class HealthCheckResource {
	
	@GET
	public Response checkHealth() {
		return Response.status(Status.OK).build();
	}
}