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
import pt.uc.dei.itf.dtos.HolidayDto;
import pt.uc.dei.ws.beans.MySessionBean;

@Stateless
public class HolidayBridge implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String URI="http://localhost:8080/aclgomes-rnavalho-project-ds/holiday";
	//private final String URI="https://localhost:8443/aclgomes-rnavalho-project-ds/holiday";

	@Inject
	private MySessionBean mySessionBean;

	public HolidayBridge() {	
	}

	public List<HolidayDto> getAllHolidays() {
		try {
			List<HolidayDto> list= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getAll").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<HolidayDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}

	public Response createNewHoliday(HolidayDto holiday) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).post(Entity.json(holiday), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	public Response updateHoliday(HolidayDto holiday) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).put(Entity.json(holiday), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

}
