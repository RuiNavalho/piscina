package pt.piscina.ds.endpoints;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.piscina.ds.annotations.AdminOnly;
import pt.piscina.ds.annotations.NoTokenNeeded;
import pt.piscina.ds.services.SkillService;
import pt.uc.dei.itf.dtos.SkillDto;

@Path("/skill")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SkillEndPoint {
	
	@Inject
	private SkillService skillService;
	
	@GET
	@Path("/getAll")
	@NoTokenNeeded
	public Response getAllSkills() {
		return skillService.getAllSkills();
	}
	
	@POST
	@AdminOnly
	@Path("/create")
	public Response createNewSkill(SkillDto skillDto, @HeaderParam("token") String token) {
		return skillService.createNewSkill(skillDto, token);
	}

}
