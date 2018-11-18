package pt.uc.dei.ws.bridges;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.piscina.itf.dtos.AllocationDto;
import pt.piscina.itf.dtos.ClientDto;
import pt.piscina.itf.dtos.ProjectDto;
import pt.piscina.itf.dtos.ProjectNewDto;
import pt.piscina.itf.dtos.TaskLightDto;
import pt.uc.dei.ws.beans.MySessionBean;

@Stateless
public class ProjectBridge implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String URI="http://localhost:8080/aclgomes-rnavalho-project-ds/project";
	//private final String URI="https://localhost:8443/aclgomes-rnavalho-project-ds/project";
	
	@Inject
	private MySessionBean mySessionBean;

	public ProjectBridge() {	
	}
	
	public Response findProjectsForNewTask() {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("getProjectsForNewTask").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}
	
	public List<ProjectDto> projectAdvancedSearch(String type, String searchText, Date date) {
		try {
			List<ProjectDto> list= null ;
			Response response = null;
			if (type.equals("date")){
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				searchText= sdf.format(date);
			}
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("advancedSearch").path(type).path(searchText).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<ProjectDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		 mySessionBean.sendDataServerDownAlert();
		 return null;
		}
	}
	
	public Response getAllProjects() {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("getAll").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		 mySessionBean.sendDataServerDownAlert();
		 return null;
		}
	}
	
	public List<TaskLightDto> getAllProjectTasks(String idProject) {
		try {
			List<TaskLightDto> list= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getTasks/").path(idProject).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<TaskLightDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}
	
	public ProjectDto findProjectByProjectId(String projectId) {
		try {
			ProjectDto proj= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("getProject").path(projectId).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				proj = response.readEntity(ProjectDto.class);
			}
			client.close();
			return proj;
		} catch (Exception e) {
			e.printStackTrace();
			 mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}

	public List<AllocationDto> getAllProjectAllocations(String idProject) {
		try {
			List<AllocationDto> list= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("getAllocations").path(idProject).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<AllocationDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		 mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}
	
	public List<ProjectDto> getMyWorkingProjects() {
		try {
			List<ProjectDto> list= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getMyWorkingProjects").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<ProjectDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}
	
	public Response getMyWorkingProjectsBetweenDates(Date beginDate, Date endDate) {	
		try {
			Response response = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String begin= sdf.format(beginDate);
			String end= sdf.format(endDate);
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getMyWorkingProjectsBetweenDates").path(begin).path(end).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}
	
	public Response getMyManagedProjectsBetweenDates(Date beginDate, Date endDate) {
		try {
			Response response = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String begin= sdf.format(beginDate);
			String end= sdf.format(endDate);
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getMyManagedProjectsBetweenDates").path(begin).path(end).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}
	
	public List<ProjectDto> getMyManagedProjects() {
		try {
			List<ProjectDto> list= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getMyManagedProjects").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<ProjectDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}
	
	public List<ProjectDto> getClientProjects(ClientDto selectedClient) {
		try {
			List<ProjectDto> list= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getClientProjects").path(selectedClient.getCompany()).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<ProjectDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}

	public Response createNewProject(ProjectNewDto newProject) {
		try {
			newProject.setCreatorEmail(mySessionBean.getEmail());
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).post(Entity.json(newProject), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}
	
	
	public Response updateProject(ProjectDto projectDto) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).put(Entity.json(projectDto), Response.class);
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


	public String getURI() {
		return URI;
	}

	public MySessionBean getMySessionBean() {
		return mySessionBean;
	}

	public void setMySessionBean(MySessionBean mySessionBean) {
		this.mySessionBean = mySessionBean;
	}









}
