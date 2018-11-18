package pt.piscina.ds.endpoints;

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

import pt.piscina.ds.annotations.DirectorOnly;
import pt.piscina.ds.annotations.DirectorOrUser;
import pt.piscina.ds.services.ProjectService;
import pt.piscina.itf.dtos.ProjectDto;
import pt.piscina.itf.dtos.ProjectNewDto;

@Path("/project")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectEndPoint {

	@Inject
	private ProjectService projectService;

	@POST
	@DirectorOnly
	public Response createNewProject(ProjectNewDto newProject) {
		return projectService.createNewProject(newProject);
	}
	
	@GET
	@DirectorOnly
	@Path("/getProjectsForNewTask")
	public Response findProjectsForNewTask() {
		return projectService.findProjectsForNewTask();
	}
	
	@PUT
	@DirectorOrUser
	public Response updateProject(ProjectDto projectDto, @HeaderParam("token") String token) {
		return projectService.updateProject(projectDto, token);
	}
	
	@GET
	@Path("/advancedSearch/{type}/{searchText}")
	@DirectorOrUser
	public Response projectAdvancedSearch(@HeaderParam("token") String token, @PathParam("type") String type, @PathParam("searchText") String searchText) {
		return projectService.projectAdvancedSearch(token, type, searchText);
	}
	
	@GET
	@Path("/getAll")
	@DirectorOnly
	public Response projectGetAll(@HeaderParam("token") String token) {
		return projectService.projectAdvancedSearch(token, "all", "all");
	}
	
	@GET
	@Path("/getClientProjects/{company}")
	@DirectorOnly
	public Response getClientProjects(@PathParam("company") String company) {
		return projectService.getClientProjects(company);
	}
	
	@GET
	@Path("/getMyWorkingProjects")
	@DirectorOrUser
	public Response getMyWorkingProjects(@HeaderParam("token") String token) {
		return projectService.getMyWorkingProjects(token);
	}
	
	@GET
	@Path("/getMyWorkingProjectsBetweenDates/{beginDate}/{endDate}")
	@DirectorOrUser
	public Response getMyWorkingProjectsBetweenDates(@HeaderParam("token") String token, @PathParam("beginDate") String beginDate, @PathParam("endDate") String endDate) {
		return projectService.getMyWorkingProjectsBetweenDates(token, beginDate, endDate);
	}
	
	@GET
	@Path("/getMyManagedProjectsBetweenDates/{beginDate}/{endDate}")
	@DirectorOrUser
	public Response getMyManagedProjectsBetweenDates(@HeaderParam("token") String token, @PathParam("beginDate") String beginDate, @PathParam("endDate") String endDate) {
		return projectService.getMyManagedProjectsBetweenDates(token, beginDate, endDate);
	}
	
	@GET
	@Path("/getMyManagedProjects")
	@DirectorOrUser
	public Response getMyManagedProjects(@HeaderParam("token") String token) {
		return projectService.getMyManagedProjects(token);
	}

	@GET
	@Path("/getTasks/{idProject}")
	public Response getAllProjectTasks(@PathParam("idProject") String idProject) {
		return projectService.getAllProjectTasks(idProject);
	}
	
	@GET
	@Path("/getProject/{idProject}")
	public Response findProjectByProjectId(@PathParam("idProject") String idProject) {
		return projectService.findProjectByProjectId(idProject);
	}
	
	@GET
	@Path("/getAllocations/{idProject}")
	public Response getAllProjectAllocations(@PathParam("idProject") String idProject) {
		return projectService.getAllProjectAllocations(idProject);
	}


	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

}
