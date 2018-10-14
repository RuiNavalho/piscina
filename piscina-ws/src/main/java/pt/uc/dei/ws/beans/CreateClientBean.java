package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import pt.uc.dei.itf.dtos.ClientNewDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.ClientBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named
@SessionScoped
public class CreateClientBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String address;
	private String company;
	private String contact;
	private String phone;
	private String business;

	@Inject
	private ClientBridge clientBridge;
	
	@Inject
	private ErrorsHandler errorsHandler;
	
	public CreateClientBean() {
		company=null;
	}
	
	public String createNewClient() {
		ClientNewDto clientNewDto = new ClientNewDto(address, company, contact, phone, business);
		Response response = clientBridge.createNewClient(clientNewDto);
		if (response.getStatus()==200) {
			errorsHandler.createNewClient(true, null);
			return "clients_new.xhtml";
		} else {			
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.createNewClient(false, errors);
		}
		return null;
	}
	public void cleanFields(){
		address = "";
		company = "";
		contact = "";
		phone = "";
		business = "";
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

}
