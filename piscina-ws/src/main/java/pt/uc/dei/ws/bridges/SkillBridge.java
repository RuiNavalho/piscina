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

import pt.piscina.itf.dtos.SkillDto;
import pt.uc.dei.ws.beans.MySessionBean;

@Stateless
public class SkillBridge implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String URI="http://localhost:8080/aclgomes-rnavalho-project-ds/skill";
	//private final String URI="https://localhost:8443/aclgomes-rnavalho-project-ds/skill";
	
	@Inject
	private MySessionBean mySessionBean;
	
	public SkillBridge() {	
	}

	public List<SkillDto> getAllSkills() {
		List<SkillDto> allSkills= null ;
		Response response = null;
		//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
		Client client = ClientBuilder.newClient();
		//response = client.target(URI).path("/getAll").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
		response = client.target(URI).path("/getAll").request(MediaType.APPLICATION_JSON).get(Response.class);
		
		if (response.getStatus()==200) {
			allSkills = response.readEntity(new GenericType<List<SkillDto>>() {});
		}
		client.close();
		return allSkills;
	}

	public Response createSkill(SkillDto skillDto) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/create").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).post(Entity.json(skillDto), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}
}
