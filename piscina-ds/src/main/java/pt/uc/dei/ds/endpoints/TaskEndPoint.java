package pt.uc.dei.ds.endpoints;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pt.uc.dei.ds.annotations.DirectorOnly;
import pt.uc.dei.ds.annotations.DirectorOrUser;
import pt.uc.dei.ds.services.TaskService;
import pt.uc.dei.itf.dtos.AllocationDto;
import pt.uc.dei.itf.dtos.TaskLightDto;
import pt.uc.dei.itf.dtos.TaskNewDto;

@Path("/task")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskEndPoint {
	
	@Inject
	private TaskService taskService;

	@POST
	@DirectorOrUser // Director or User if Manager
	public Response createNewTask(TaskNewDto taskNewDto, @HeaderParam("token") String token) {
		return taskService.createNewTask(taskNewDto, token);
	}
	
	@PUT
	@DirectorOrUser //Apenas o Diretor pode alterar Tasks???
	public Response updateTask(TaskLightDto task, @HeaderParam("token") String token) {
		return taskService.updateTask(task, token);
	}
	
	@PUT
	@Path("/allocateWorkertoTask")
	@DirectorOrUser // Director or User if Manager
	public Response allocateWorkertoTask(AllocationDto allocationDto) {
		return taskService.allocateWorkertoTask(allocationDto);
	}
	
	@PUT
	@Path("/updateAllocation")
	@DirectorOrUser // Director or User if Manager
	public Response updateAllocationDates(AllocationDto allocationDto, @HeaderParam("token") String token) {
		return taskService.updateAllocationDates(allocationDto, token);
	}
	
	@GET
	@Path("/getAll")
	@DirectorOnly
	public Response getAllTasks() {
		return taskService.getAllTasks();
	}
	
	@GET
	@Path("/task/{taskId}")
	@DirectorOrUser
	public Response findTaskById(@PathParam("taskId") String taskId) {
		return taskService.findTaskById(taskId);
	}
	
	@GET
	@Path("/precendentTasks/{taskId}")
	@DirectorOrUser
	public Response findPrecendentTasksList(@PathParam("taskId") String taskId) {
		return taskService.findPrecendentTasksList(taskId);
	}
	
	@GET
	@Path("/possiblePrecendentTasks/{taskId}")
	@DirectorOrUser
	public Response findPossiblePrecendentTasks(@PathParam("taskId") String taskId) {
		return taskService.findPossiblePrecendentTasks(taskId);
	}
	
	@PUT
	@Path("/assignPrecedence/{taskId}")
	@DirectorOrUser // Director or User if Manager
	public Response assignTaskPrecendence(TaskLightDto selectedTaskPrecedent, @HeaderParam("token") String token, @PathParam("taskId") String taskId) {
		return taskService.assignTaskPrecendence(selectedTaskPrecedent, token, taskId);
	}
	
	@PUT
	@Path("/unAssignPrecedence/{taskId}")
	@DirectorOrUser // Director or User if Manager
	public Response unAssignPrecedence(TaskLightDto selectedTaskPrecedent, @HeaderParam("token") String token, @PathParam("taskId") String taskId) {
		return taskService.unAssignPrecedence(selectedTaskPrecedent, token, taskId);
	}
	
	@GET
	@Path("/getMyWorkingTasks")
	@DirectorOrUser
	public Response getMyWorkingTasks(@HeaderParam("token") String token) {
		return taskService.getMyWorkingTasks(token);
	}
	
	@GET
	@Path("/getTaskAllocations/{taskId}")
	@DirectorOrUser
	public Response getTaskAllocations(@PathParam("taskId") String taskId) {
		return taskService.getTaskAllocations(taskId);
	}
	
	@PUT
	@Path("/removeAllocation")
	@DirectorOrUser // Director or User if Manager
	public Response removeWorkerFromTask(Integer allocationId, @HeaderParam("token") String token) {
		return taskService.removeWorkerFromTask(allocationId, token);
	}
	
	//removeWorkerFromTask
	

}
