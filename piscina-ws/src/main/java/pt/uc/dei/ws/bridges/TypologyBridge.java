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

import pt.piscina.itf.dtos.TypologyDto;
import pt.uc.dei.ws.beans.MySessionBean;

@Stateless
public class TypologyBridge implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String URI="http://localhost:8080/aclgomes-rnavalho-project-ds/typology";
	//private final String URI="https://localhost:8443/aclgomes-rnavalho-project-ds/typology";
	
	@Inject
	private MySessionBean mySessionBean;
	
	public TypologyBridge() {	
	}
	 
	public List<TypologyDto> getAllTypologies() {
		List<TypologyDto> allTypologies= null ;
		Response response = null;
		//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
		Client client = ClientBuilder.newClient();
		//response = client.target(URI).path("/getAll").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
		
		response = client.target(URI).path("/getAll").request(MediaType.APPLICATION_JSON).get(Response.class);
		if (response.getStatus()==200) {
			allTypologies = response.readEntity(new GenericType<List<TypologyDto>>() {});
		}
		client.close();
		return allTypologies;
	}
}
