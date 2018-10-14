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
import pt.uc.dei.itf.dtos.TaskworkDto;
import pt.uc.dei.itf.dtos.TaskworkNewDto;
import pt.uc.dei.ws.beans.MySessionBean;

@Stateless
public class TaskWorkBridge implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String URI="http://localhost:8080/aclgomes-rnavalho-project-ds/taskwork/";
	//private final String URI="https://localhost:8443/aclgomes-rnavalho-project-ds/taskwork/";

	@Inject
	private MySessionBean mySessionBean;

	public TaskWorkBridge() {	
	}

	public Response registerWorkingHoursInTask(TaskworkNewDto newTw) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/create").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).post(Entity.json(newTw), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	public List<TaskworkDto> getMyWorkingHours() {
		try {
			Response response = null;
			List<TaskworkDto> list = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("getMyWorkingHours").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<TaskworkDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}
	

	public List<TaskworkDto> findMyWorkingHoursInTask(int taskId) {
		try {
			Response response = null;
			List<TaskworkDto> list = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("getMyWorkingHoursInTask").path(taskId+"").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<TaskworkDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}
	

	public List<TaskworkDto> findWorkingHoursInTask(int taskId) {
		try {
			Response response = null;
			List<TaskworkDto> list = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("getWorkingHoursInTask").path(taskId+"").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<TaskworkDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}

	public List<TaskworkDto> findWorkingHoursInProject(String idProject) {
		try {
			Response response = null;
			List<TaskworkDto> list = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("getWorkingHoursInProject").path(idProject).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<TaskworkDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}
	
	public List<TaskworkDto> findMyWorkingHoursInProject(String idProject) {
		try {
			Response response = null;
			List<TaskworkDto> list = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("getMyWorkingHoursInProject").path(idProject).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<TaskworkDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}


	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public String getURI() {
		return URI;
	}





}
