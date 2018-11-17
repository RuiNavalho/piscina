package pt.piscina.ds.endpoints;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.piscina.ds.annotations.DirectorOrUser;
import pt.piscina.ds.services.TaskworkService;
import pt.uc.dei.itf.dtos.TaskworkNewDto;

@Path("/taskwork")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskworkEndPoint {
	
	@Inject
	private TaskworkService taskworkService;
	
	@POST
	@DirectorOrUser
	@Path("/create")
	public Response createNewTaskwork(TaskworkNewDto taskworkNewDto, @HeaderParam("token") String token) {
		return taskworkService.createNewTaskwork(taskworkNewDto, token);
	}
	
	@GET
	@Path("/getMyWorkingHours")
	@DirectorOrUser
	public Response getMyWorkingHours(@HeaderParam("token") String token) {
		return taskworkService.getMyWorkingHours(token);
	}
	
	@GET
	@Path("/getWorkingHoursInTask/{taskId}")
	@DirectorOrUser //Director or user if manager
	public Response getWorkingHoursInTask(@PathParam("taskId") String taskId, @HeaderParam("token") String token) {
		return taskworkService.getWorkingHoursInTask(taskId, token);
	}
	
	@GET
	@Path("/getMyWorkingHoursInTask/{taskId}")
	@DirectorOrUser //Director or user if manager
	public Response getMyWorkingHoursInTask(@PathParam("taskId") String taskId, @HeaderParam("token") String token) {
		return taskworkService.getMyWorkingHoursInTask(taskId, token);
	}
	
	@GET
	@Path("/getWorkingHoursInProject/{projectId}")
	@DirectorOrUser //Director or user if manager
	public Response getWorkingHoursInProject(@PathParam("projectId") String projectId, @HeaderParam("token") String token) {
		return taskworkService.getWorkingHoursInProject(projectId, token);
	}
	
	@GET
	@Path("/getMyWorkingHoursInProject/{projectId}")
	@DirectorOrUser //Director or user if manager
	public Response getMyWorkingHoursInProject(@PathParam("projectId") String projectId, @HeaderParam("token") String token) {
		return taskworkService.getMyWorkingHoursInProject(projectId, token);
	}
	
//	@PUT
//	@DirectorOnly
//	public Response updateTaskwork(TaskworkNewDto taskworkNewDto) {
//		return taskworkService.updateTaskwork(taskworkNewDto);
//	}
//	
//	@GET
//	@Path("/getAll")
//	@DirectorOnly
//	public Response getAllTaskworks() {
//		return taskworkService.getAllTaskworks();
//	}

}
