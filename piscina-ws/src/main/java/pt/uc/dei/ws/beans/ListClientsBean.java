package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import pt.uc.dei.itf.dtos.BusinessStatsDto;
import pt.uc.dei.itf.dtos.ClientDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.ClientBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named("listClientsBean")
@SessionScoped
public class ListClientsBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<ClientDto> clientsList;
	private ClientDto selectedClient;

	@Inject
	private ClientBridge clientBridge;

	@Inject
	private CreateProjectBean createProjectBean;

	@Inject
	private ErrorsHandler errorsHandler;

	public ListClientsBean() {	
	}

	public void updateListClientsBean(String company) {
		this.clientsList = clientBridge.getAllClients();
		for (int i = 0; i < clientsList.size(); i++) {
			if(clientsList.get(i).getCompany().equals(company)){
				selectedClient=clientsList.get(i);
				return;
			}	
		}
	}

	//TODO alterar o XHMTL para vir aqui buscar a lista de todos os clientes
	public List<ClientDto> getAllBusinessClientsList(BusinessStatsDto selectedBusiness) {
		clientsList= clientBridge.getAllClientsInBusinessList(selectedBusiness);
		return clientsList;
	}

	public String updateClient() {	
		Response response = clientBridge.updateClient(selectedClient);		
		if (response.getStatus()==200) {
			errorsHandler.updateClient(true, null);
			return "clients_edit.xhtml";
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.updateClient(false, errors);
		}
		return null;
	}

	public String createNewProjectToClient() {
		createProjectBean.createNewProjectToClient(selectedClient.getCompany());
		return "projects_new.xhtml?faces-redirect=true";
	}

	public void findAllClients(){
		clientsList= clientBridge.getAllClients();
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	//TODO alterar o getClients List
	public List<ClientDto> getClientsList() {
		return clientsList;	
	}

	public void setClientsList(List<ClientDto> clientsList) {
		this.clientsList = clientsList;
	}

	public ClientDto getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(ClientDto selectedClient) {
		this.selectedClient = selectedClient;
	}

	public ClientBridge getClientBridge() {
		return clientBridge;
	}

	public void setClientBridge(ClientBridge clientBridge) {
		this.clientBridge = clientBridge;
	}


}
