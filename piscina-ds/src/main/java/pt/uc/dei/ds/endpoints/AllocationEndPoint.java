package pt.uc.dei.ds.endpoints;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.uc.dei.ds.annotations.DirectorOnly;
import pt.uc.dei.ds.annotations.DirectorOrUser;
import pt.uc.dei.ds.services.AllocationService;

@Path("/allocation")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AllocationEndPoint {
	
	@Inject
	private AllocationService allocationService;
	
	@GET
	@Path("/getMyAllocations/{type}")
	@DirectorOrUser
	public Response findMyAllocations(@HeaderParam("token") String token, @PathParam("type") String type) {
		return allocationService.findMyAllocations(token, type);
	}
	
	@GET
	@Path("/getMyAllocationsBetweenDates/{beginDate}/{endDate}")
	@DirectorOrUser
	public Response getMyAllocationsBetweenDates(@HeaderParam("token") String token, @PathParam("beginDate") String beginDate, @PathParam("endDate") String endDate) {
		return allocationService.getMyAllocationsBetweenDates(token, beginDate, endDate);
	}
	
	@GET
	@Path("/getUserAllocationsBetweenDates/{email}/{beginDate}/{endDate}")
	@DirectorOnly
	public Response getUserAllocationsBetweenDates(@PathParam("email") String email, @PathParam("beginDate") String beginDate, @PathParam("endDate") String endDate) {
		return allocationService.getUserAllocationsBetweenDates(email, beginDate, endDate);
	}

//	@GET
//	@Path("/getAllocationsToRegisterTaskWork/{email}")
//	@NoTokenNeeded
//	public Response getAllocationsToRegisterTaskWorks() {
//		return businessService.getAllBusinesses();
//	}
	
}
