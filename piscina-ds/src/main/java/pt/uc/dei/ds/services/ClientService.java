package pt.uc.dei.ds.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;

import pt.uc.dei.ds.daos.BusinessDao;
import pt.uc.dei.ds.daos.ClientDao;
import pt.uc.dei.ds.entities.Business;
import pt.uc.dei.ds.entities.Client;
import pt.uc.dei.ds.security.MyJwt;
import pt.uc.dei.ds.util.InternalError;
import pt.uc.dei.ds.util.Trimmer;
import pt.uc.dei.itf.dtos.BusinessDto;
import pt.uc.dei.itf.dtos.ClientDto;
import pt.uc.dei.itf.dtos.ClientNewDto;
import pt.uc.dei.itf.errors.ErrorMessage;

@Stateless
public class ClientService implements Serializable{

	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger.getLogger(ClientService.class);

	@Inject
	private ClientDao clientDao;

	@Inject
	private BusinessDao businessDao;

	public ClientService() {	
	}

	public Response updateClient(ClientDto selectedClient, String token) {
		try {
			String email = MyJwt.parseJWT(token).getSubject();
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			Client clientToUpdate = clientDao.findClientByName(selectedClient.getCompany());
			String address = Trimmer.clean(selectedClient.getAddress());
			String contact = Trimmer.clean(selectedClient.getContact());
			String phone = Trimmer.clean(selectedClient.getPhone());
			
			//message to be Logged
			StringBuilder sb = new StringBuilder();
			sb.append("ID: ").append(clientToUpdate.getId()).append(" Address: ").append(address).
			append(" Contact: ").append(contact).append(" Phone: ").append(phone);
			if (address.equals("") || contact.equals("") || phone.equals("")) {		
				List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
				errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);	
				logger.info("FAILED - User  "+email+" tryed to update Client : "+sb.toString());
				return statusNOK.entity(errorMessages).build();	
			} else {
				logger.info("SUCESS - User  "+email+" updated Client : "+sb.toString());
				clientToUpdate.setAddress(address);
				clientToUpdate.setContact(contact);
				clientToUpdate.setPhone(phone);
				clientDao.merge(clientToUpdate);
				return Response.ok(true).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response createNewClient(ClientNewDto newClient, String token) {
		ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
		List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
		try {
			//Messaged to be logged
			StringBuilder sb = new StringBuilder();
			String email = MyJwt.parseJWT(token).getSubject();
			String company = Trimmer.clean(newClient.getCompany());
			boolean clientExists = clientDao.checkClientNameExistence(company);
			
			sb.append("Company "+company);
			if (!clientExists) {

				String address = Trimmer.clean(newClient.getAddress());
				String contact = Trimmer.clean(newClient.getContact());
				String phone = Trimmer.clean(newClient.getPhone());
				sb.append("Address: ").append(address).append(" Contact: ").append(contact).append(" Phone: ");
				if (address.equals("") || contact.equals("") || phone.equals("") || company.equals("")) {		
					errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
					return statusNOK.entity(errorMessages).build();	
				} else {
					Business business=businessDao.findBusinessByName(newClient.getBusiness());
					Client client = new Client(address, company, contact, phone, business);
					clientDao.createNewClient(client);
					
					sb.append(phone).append(" Business: ").append(business.getArea()).append(" with ID: ").append(client.getId());
					logger.info("SUCESS - User  "+email+" created NEW Client : "+sb.toString());
					return Response.ok(true).build();
				}
			} else {
				logger.info("FAILED - User  "+email+" tryed to create NEW Client : "+sb.toString());
				errorMessages.add(ErrorMessage.CLIENT_NAME_EXISTS);
				return Response.status(Response.Status.UNAUTHORIZED).entity(errorMessages).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getAllClients() {
		try {
			List<Client> allClients = clientDao.getAllClients();
			if (allClients!=null) {
				List<ClientDto> list = getClientListDto(allClients);
				return Response.ok(list).build();
			} else {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getAllClientsInBusiness(String businessArea) {
		try {
			List<Client> allClients = clientDao.getAllClientsInBusiness(businessArea);
			if (allClients!=null) {
				List<ClientDto> list = getClientListDto(allClients);
				return Response.ok(list).build();
			} else {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// Private Auxiliary Methods ///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	private List<ClientDto> getClientListDto(List<Client> allClients) {
		List<ClientDto> list = new ArrayList<ClientDto>();
		for (int i=0; i<allClients.size(); i++) {
			Long clientCountProjects = clientDao.countProjectsFromClient(allClients.get(i));
			float clientTotalBudget = clientDao.getClientTotalBudget(allClients.get(i)).floatValue();
			BusinessDto businessDto = new BusinessDto(allClients.get(i).getBusiness().getArea());
			list.add(new ClientDto(allClients.get(i).getAddress(), allClients.get(i).getCompany(), allClients.get(i).getContact(), allClients.get(i).getPhone(), businessDto, clientCountProjects.intValue(), clientTotalBudget));
		}
		return list;
	}





}
