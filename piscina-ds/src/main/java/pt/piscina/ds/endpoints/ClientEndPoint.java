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
import pt.piscina.ds.services.ClientService;
import pt.uc.dei.itf.dtos.ClientDto;
import pt.uc.dei.itf.dtos.ClientNewDto;

@Path("/client")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientEndPoint {
	
	@Inject
	private ClientService clientService;

	@POST
	@DirectorOnly
	public Response createNewClient(ClientNewDto newClient, @HeaderParam("token") String token) {
		return clientService.createNewClient(newClient, token);
	}
	
	@PUT
	@DirectorOnly
	public Response updateClient(ClientDto selectedClient, @HeaderParam("token") String token) {
		return clientService.updateClient(selectedClient, token);
	}
	
	@GET
	@Path("/getAll")
	@DirectorOnly
	public Response getAllClients() {
		return clientService.getAllClients();
	}
	
	@GET
	@Path("/business/{businessArea}")
	@DirectorOnly
	public Response getAllClientsInBusiness(@PathParam("businessArea") String businessArea) {
		return clientService.getAllClientsInBusiness(businessArea);
	}

}
