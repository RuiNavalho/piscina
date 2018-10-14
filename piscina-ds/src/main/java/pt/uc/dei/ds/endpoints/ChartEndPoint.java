package pt.uc.dei.ds.endpoints;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.uc.dei.ds.annotations.DirectorOnly;
import pt.uc.dei.ds.annotations.DirectorOrUser;
import pt.uc.dei.ds.services.ChartService;

@Path("/chart")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChartEndPoint {

	@Inject
	private ChartService chartService;
	
	@GET
	@Path("/projectCpiSpiTimeChart/{projectId}")
	@DirectorOrUser
	public Response getProjectCpiSpiTimeChart(@PathParam("projectId") String projectId) {
		return chartService.getProjectCpiSpiTimeChart(projectId);
	}
	
	@GET
	@Path("/userPerformance/{email}/{beginDate}/{endDate}")
	@DirectorOnly
	public Response getUserPerformanceChart(@PathParam("email") String email, @PathParam("beginDate") String beginDate, @PathParam("endDate") String endDate) {
		return chartService.getUserPerformanceChart(email, beginDate, endDate);
	}
	
	@GET
	@Path("/myUserPerformance/{email}/{beginDate}/{endDate}")
	@DirectorOrUser
	public Response getMyUserPerformanceChart(@PathParam("email") String email, @PathParam("beginDate") String beginDate, @PathParam("endDate") String endDate) {
		return chartService.getUserPerformanceChart(email, beginDate, endDate);
	}
	
	@GET
	@Path("/getTaskTimeCharts/{taskId}")
	@DirectorOrUser
	public Response getTaskHourPercCostChart(@PathParam("taskId") String taskId) {
		return chartService.getTaskHourPercCostChart(taskId);
	}

	
	@GET
	@Path("/getProjectTimeCharts/{projectId}")
	@DirectorOrUser
	public Response getProjectHourPercCostChart(@PathParam("projectId") String projectId) {
		return chartService.getProjectHourPercCostChart(projectId);
	}
	
	@GET
	@Path("/getClientTimeCharts/{company}")
	@DirectorOrUser
	public Response getClientTimeCharts(@PathParam("company") String company) {
		return chartService.getClientTimeCharts(company);
	}
	
	@GET
	@Path("/getBusinessTimeCharts/{area}")
	@DirectorOrUser
	public Response getBusinessTimeCharts(@PathParam("area") String area) {
		return chartService.getBusinessTimeCharts(area);
	}
	
	
	@GET
	@Path("/getAllUsersPerformance/{beginDate}/{endDate}")
	@DirectorOnly
	public Response findAllUsersPerformanceCharts(@PathParam("beginDate") String beginDate, @PathParam("endDate") String endDate) {
		return chartService.findAllUsersPerformanceCharts(beginDate, endDate);
	}
	
//	@GET
//	@Path("/getUserReminders/{beginDate}/{endDate}")
//	@DirectorOnly
//	public Response findAllUsersPerformanceCharts(@PathParam("beginDate") String beginDate, @PathParam("endDate") String endDate) {
//		return chartService.findAllUsersPerformanceCharts(beginDate, endDate);
//	}
	
	@GET
	@Path("/getTaskAllocations/{taskId}")
	@DirectorOrUser
	public Response getTaskAllocationsChart(@PathParam("taskId") String taskId) {
		return chartService.getTaskAllocationsChart(taskId);
	}
	
	@GET
	@Path("/getProjectAllocations/{projectId}")
	@DirectorOrUser
	public Response getProjectAllocationsChart(@PathParam("projectId") String projectId) {
		return chartService.getProjectAllocationsChart(projectId);
	}
	
	@GET
	@Path("/getProjectGantt/{projectId}")
	@DirectorOrUser
	public Response getProjectGanttChart(@PathParam("projectId") String projectId) {
		return chartService.getProjectGanttChart(projectId);
	}

}
