package pt.uc.dei.ws.bridges;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.uc.dei.itf.charts.ChartProjectAllocationsDto;
import pt.uc.dei.itf.charts.ChartProjectGanttDto;
import pt.uc.dei.ws.beans.MySessionBean;

@Stateless
public class ChartBridge implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String URI="http://localhost:8080/aclgomes-rnavalho-project-ds/chart";
	//private final String URI="https://localhost:8443/aclgomes-rnavalho-project-ds/chart";
	
	@Inject
	private MySessionBean mySessionBean;
	
	public ChartBridge() {	
	}
	
	public ChartProjectAllocationsDto findProjectAllocationsChart(String projectId) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("getProjectAllocations").path(projectId);
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			ChartProjectAllocationsDto chartAllocs=response.readEntity(ChartProjectAllocationsDto.class);
			client.close();
			return chartAllocs;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}
	
	public Response findProjectGanttChart(String projectId) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("getProjectGantt").path(projectId);
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}
	

	public ChartProjectGanttDto findProjectGanttChart2(String projectId) {
		try {
			Response response = null;
			ChartProjectGanttDto gantt=null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("getProjectGantt").path(projectId);
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				gantt = response.readEntity(ChartProjectGanttDto.class);
			}
			client.close();
			return gantt;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}
	
	public Response getProjectTimeEvolutionCharts(String projectId) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("getProjectTimeCharts").path(projectId);
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}
	
	public Response getTaskTimeEvolutionCharts(int taskId) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("getTaskTimeCharts").path(taskId+"");
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}
	
	public Response findTaskAllocationsChart(int taskId) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("getTaskAllocations").path(taskId+"");
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}
	
	public Response findClientChartCountAndBudgetDto(String company) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("getClientTimeCharts").path(company);
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}
	
	public Response findBusinessChartCountAndBudgetDto(String area) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("getBusinessTimeCharts").path(area);
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	public Response findAllUsersPerformanceCharts(Date beginDate, Date endDate) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String begin= sdf.format(beginDate);
			String end= sdf.format(endDate);
			WebTarget myResource = client.target(URI).path("getAllUsersPerformance").path(begin).path(end);
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}
	
	public Response getUserPerformanceChart(String email, Date beginDate, Date endDate) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String begin= sdf.format(beginDate);
			String end= sdf.format(endDate);
			WebTarget myResource = client.target(URI).path("userPerformance").path(email).path(begin).path(end);
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	public Response getProjectCpiSpiTimeChart(String projectId) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("projectCpiSpiTimeChart").path(projectId);
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	public Response getMyUserPerformanceChart(Date beginDate, Date endDate) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String begin= sdf.format(beginDate);
			String end= sdf.format(endDate);
			WebTarget myResource = client.target(URI).path("myUserPerformance").path(mySessionBean.getLoggedUser().getEmail()).path(begin).path(end);
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}







}
