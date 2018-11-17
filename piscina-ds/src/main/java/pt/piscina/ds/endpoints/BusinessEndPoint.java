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

import pt.piscina.ds.annotations.DirectorOnly;
import pt.piscina.ds.annotations.NoTokenNeeded;
import pt.piscina.ds.services.BusinessService;
import pt.uc.dei.itf.dtos.BusinessDto;

@Path("/business")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BusinessEndPoint {
	
	@Inject
	private BusinessService businessService;
	
	@POST
	@DirectorOnly
	@Path("/create")
	public Response createNewBusiness(BusinessDto businessDto, @HeaderParam("token") String token) {
		return businessService.createNewBusiness(businessDto, token);
	}
	
	@GET
	@Path("/getAll")
	@NoTokenNeeded
	public Response getAllBusinesses() {
		return businessService.getAllBusinesses();
	}
	
	@GET
	@Path("/getAllBusinessesStats")
	@DirectorOnly
	public Response getAllBusinessesStats() {
		return businessService.getAllBusinessesStats();
	}
	
	@GET
	@Path("/getBusinessStats/{area}")
	@DirectorOnly
	public Response getBusinessStats(@PathParam("area") String area) {
		return businessService.getBusinessStats(area);
	}
	

}
