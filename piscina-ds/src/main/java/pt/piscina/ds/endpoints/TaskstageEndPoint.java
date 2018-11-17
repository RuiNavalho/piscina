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
import pt.piscina.ds.services.TaskstageService;

@Path("/taskstage")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskstageEndPoint {

	@Inject
	private TaskstageService taskstageService;

	@GET
	@Path("/getAll")
	@NoTokenNeeded
	public Response getAllTaskstages() {
		return taskstageService.getAllTaskstages();
	}

}
