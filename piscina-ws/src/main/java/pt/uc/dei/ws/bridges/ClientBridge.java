package pt.uc.dei.ws.bridges;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.piscina.itf.dtos.BusinessStatsDto;
import pt.piscina.itf.dtos.ClientDto;
import pt.piscina.itf.dtos.ClientNewDto;
import pt.uc.dei.ws.beans.MySessionBean;

@Stateless
public class ClientBridge implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String URI="http://localhost:8080/aclgomes-rnavalho-project-ds/client";
	//private final String URI="https://localhost:8443/aclgomes-rnavalho-project-ds/client";

	@Inject
	private MySessionBean mySessionBean;

	public ClientBridge() {
	}

	public Response updateClient(ClientDto selectedClient) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).put(Entity.json(selectedClient), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	public List<ClientDto> getAllClients() {
		try {
			Response response = null;
			List<ClientDto> list = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getAll").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<ClientDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			mySessionBean.sendDataServerDownAlert();
			e.printStackTrace();
			return null;
		}
	}

	public List<ClientDto> getAllClientsInBusinessList(BusinessStatsDto selectedBusiness) {
		try {
			Response response = null;
			List<ClientDto> list = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/business").path(selectedBusiness.getArea()).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<ClientDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}

	public Response createNewClient(ClientNewDto clientNewDto) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).post(Entity.json(clientNewDto), Response.class);	
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}






	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////


}
