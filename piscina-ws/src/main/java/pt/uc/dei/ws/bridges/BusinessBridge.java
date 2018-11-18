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

import pt.piscina.itf.dtos.BusinessDto;
import pt.piscina.itf.dtos.BusinessStatsDto;
import pt.uc.dei.ws.beans.MySessionBean;

@Stateless
public class BusinessBridge implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String URI="http://localhost:8080/aclgomes-rnavalho-project-ds/business";
	//private final String URI="https://localhost:8443/aclgomes-rnavalho-project-ds/business";
	
	@Inject
	private MySessionBean mySessionBean;
	
	public BusinessBridge() {	
	}
	
	public List<BusinessDto> getAllBusinesses() {
		try {
			List<BusinessDto> allBusinesses= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			//response = client.target(URI).path("/getAll").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			response = client.target(URI).path("/getAll").request(MediaType.APPLICATION_JSON).get(Response.class);
			if (response.getStatus()==200) {
				allBusinesses = response.readEntity(new GenericType<List<BusinessDto>>() {});
			}
			client.close();
			return allBusinesses;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}
/*
	public boolean updateBusiness(BusinessDto selectedBusiness) {
		// TODO Auto-generated method stub
		return false;
	}*/
	
	public BusinessStatsDto findSelectedBusinessStats(String area) {
		try {
			BusinessStatsDto business= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getBusinessStats").path(area).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				business = response.readEntity(BusinessStatsDto.class);
			}
			client.close();
			return business;
		} catch (Exception e) {
			mySessionBean.sendDataServerDownAlert();
			e.printStackTrace();
			return null;
		}
	}

	public List<BusinessStatsDto> findAllBusinesses() {
		try {
			List<BusinessStatsDto> allBusinesses= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getAllBusinessesStats").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				allBusinesses = response.readEntity(new GenericType<List<BusinessStatsDto>>() {});
			}
			client.close();
			return allBusinesses;
		} catch (Exception e) {
			mySessionBean.sendDataServerDownAlert();
			e.printStackTrace();
			return null;
		}
	}

	public Response createBusiness(BusinessDto business) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/create").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).post(Entity.json(business), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}



}
