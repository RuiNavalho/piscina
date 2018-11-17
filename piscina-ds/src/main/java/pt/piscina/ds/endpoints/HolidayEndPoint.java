package pt.piscina.ds.endpoints;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.piscina.ds.annotations.AdminOnly;
import pt.piscina.ds.annotations.NoTokenNeeded;
import pt.piscina.ds.services.HolidayService;
import pt.uc.dei.itf.dtos.HolidayDto;

@Path("/holiday")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HolidayEndPoint {
	
	@Inject
	private HolidayService holidayService;
	
	//TODO deveremos de permitir fazer DELETE a um feriado?????
	
	@GET
	@Path("/getAll")
	@NoTokenNeeded
	public Response getAllHolidays() {
		return holidayService.getAllHolidays();
	}
	
	@POST
	@AdminOnly
	public Response createNewHoliday(HolidayDto holidayDto, @HeaderParam("token") String token) {
		return holidayService.createNewHoliday(holidayDto, token);
	}
	
	@PUT
	@AdminOnly
	public Response updateHoliday(HolidayDto holidayDto, @HeaderParam("token") String token) {
		return holidayService.updateHoliday(holidayDto, token);
	}

}
