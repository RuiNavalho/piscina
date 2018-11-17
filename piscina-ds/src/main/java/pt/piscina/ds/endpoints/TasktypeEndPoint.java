package pt.piscina.ds.endpoints;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.piscina.ds.annotations.NoTokenNeeded;
import pt.piscina.ds.services.TasktypeService;

@Path("/tasktype")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TasktypeEndPoint {

	@Inject
	private TasktypeService tasktypeService;

	@GET
	@Path("/getAll")
	@NoTokenNeeded
	public Response getAllTasktypes() {
		return tasktypeService.getAllTasktypes();
	}

}
