package pt.uc.dei.ds.endpoints;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pt.uc.dei.ds.annotations.NoTokenNeeded;
import pt.uc.dei.ds.services.TypologyService;

@Path("/typology")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TypologyEndPoint {
	
	@Inject
	private TypologyService typologyService;
	
	@GET
	@Path("/getAll")
	@NoTokenNeeded
	public Response getAllTypologies() {
		return typologyService.getAllTypologies();
	}

}
