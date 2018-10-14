package pt.uc.dei.ws.bridges;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.uc.dei.itf.dtos.RoleDto;
import pt.uc.dei.ws.beans.MySessionBean;

@Stateless
public class RoleBridge implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String URI="http://localhost:8080/aclgomes-rnavalho-project-ds/role";
	//private final String URI="https://localhost:8443/aclgomes-rnavalho-project-ds/role";
	
	@Inject
	private MySessionBean mySessionBean;
	
	public RoleBridge() {	
	}
	 
	public List<RoleDto> getAllRoles() {
		List<RoleDto> allRoles= null ;
		Response response = null;
		//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
		Client client = ClientBuilder.newClient();
		response = client.target(URI).path("/getAll").request(MediaType.APPLICATION_JSON).get(Response.class);
		if (response.getStatus()==200) {
			allRoles = response.readEntity(new GenericType<List<RoleDto>>() {});
		}
		client.close();
		return allRoles;
	}
}
