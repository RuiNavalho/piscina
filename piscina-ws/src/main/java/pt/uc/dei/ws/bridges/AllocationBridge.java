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
import pt.uc.dei.ws.beans.MySessionBean;

@Stateless
public class AllocationBridge implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String URI="http://localhost:8080/aclgomes-rnavalho-project-ds/allocation";
	//private final String URI="https://localhost:8443/aclgomes-rnavalho-project-ds/allocation";
	
	@Inject
	private MySessionBean mySessionBean;
	
	public AllocationBridge() {
	}
	
	public Response findMyAllocations(String type) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("getMyAllocations").path(type);
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}
	
	public Response getMyAllocationsBetweenDates(Date beginDate, Date endDate) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String begin= sdf.format(beginDate);
			String end= sdf.format(endDate);
			WebTarget myResource = client.target(URI).path("getMyAllocationsBetweenDates").path(begin).path(end);
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}
	
	public Response getUserAllocationsBetweenDates(String email, Date beginDate, Date endDate) {
		
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String begin= sdf.format(beginDate);
			String end= sdf.format(endDate);
			WebTarget myResource = client.target(URI).path("getUserAllocationsBetweenDates").path(email).path(begin).path(end);
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

}
