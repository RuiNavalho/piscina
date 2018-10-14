package pt.uc.dei.ws.bridges;

import java.io.Serializable;
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
import pt.uc.dei.itf.dtos.AllocationDto;
import pt.uc.dei.itf.dtos.TaskLightDto;
import pt.uc.dei.itf.dtos.TaskNewDto;
import pt.uc.dei.ws.beans.MySessionBean;

@Stateless
public class TaskBridge implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String URI="http://localhost:8080/aclgomes-rnavalho-project-ds/task/";
	//private final String URI="https://localhost:8443/aclgomes-rnavalho-project-ds/task/";

	@Inject
	private MySessionBean mySessionBean;

	public TaskBridge() {	
	}

	public Response createNewTask(TaskNewDto taskNewDto) {
		try {
			taskNewDto.setCreatorEmail(mySessionBean.getEmail());
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).post(Entity.json(taskNewDto), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	public List<TaskLightDto> getAllTasks() {
		try {
			List<TaskLightDto> list= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getAll").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
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

	public List<TaskLightDto> getMyWorkingTasks() {
		try {
			List<TaskLightDto> list= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getMyWorkingTasks").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
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

	public List<TaskLightDto> findPrecendentTasksList(TaskLightDto selectedTask) {
		try {
			List<TaskLightDto> list= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/precendentTasks").path(selectedTask.getTaskId()+"").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
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

	public List<TaskLightDto> findPossiblePrecendentTasksList(TaskLightDto selectedTask) {
		try {
			List<TaskLightDto> list= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/possiblePrecendentTasks").path(selectedTask.getTaskId()+"").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
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

	public List<AllocationDto> findTaskAllocations(TaskLightDto selectedTask) {
		try {
			List<AllocationDto> list= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getTaskAllocations").path(selectedTask.getTaskId()+"").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
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

	public Response removeWorkerFromTask(Integer allocationId) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/removeAllocation").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).put(Entity.json(allocationId), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	public Response assignTaskPrecendence(TaskLightDto selectedTask, TaskLightDto selectedTaskPrecedent) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/assignPrecedence").path(selectedTask.getTaskId()+"").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).put(Entity.json(selectedTaskPrecedent), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	public Response unAssignTaskPrecendence(TaskLightDto selectedTask, TaskLightDto selectedTaskPrecedent) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/unAssignPrecedence").path(selectedTask.getTaskId()+"").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).put(Entity.json(selectedTaskPrecedent), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	public Response updateTask(TaskLightDto selectedTask) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).put(Entity.json(selectedTask), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	//TODO mudar este metodo para AllocationNewDto
	public Response allocateWorkerToTask(float allocPercentage, int taskId, String workerEmail, Date beginDate, Date endDate) {
		try {
			AllocationDto allocDto = new AllocationDto(allocPercentage, taskId, workerEmail, null, mySessionBean.getEmail(), beginDate, endDate);
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("allocateWorkertoTask").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).put(Entity.json(allocDto), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	public Response updateAllocationDates(AllocationDto allocDto) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("updateAllocation").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).put(Entity.json(allocDto), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	public TaskLightDto findTaskById(int createdTaskId) {
		try {
			TaskLightDto createdTask=null;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("task").path(createdTaskId+"").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				createdTask = response.readEntity(TaskLightDto.class);
			}
			client.close();
			return createdTask;
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
