package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import pt.piscina.itf.dtos.BusinessDto;
import pt.piscina.itf.dtos.BusinessStatsDto;
import pt.piscina.itf.dtos.ClientDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.BusinessBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named("listBusinessesBean")
@SessionScoped
public class ListBusinessesBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<BusinessDto> businessList;
	private BusinessDto selectedBusiness;
	private List<BusinessStatsDto> businessStatsList;
	private BusinessStatsDto selectedBusinessStats;
	private String area;

	@Inject
	private BusinessBridge businessBridge;
	
	@Inject
	private ListClientsBean listClientsBean;
	
	@Inject
	private	SingletonBean singletonBean;
	
	@Inject
	private ErrorsHandler errorsHandler;

	public ListBusinessesBean() {	
	}

	//TODO
	public String updateBusiness() {
		return null;
	}
	
	public void resetFields(){
		area = null;
	}
	
	public String createBusiness() {
		BusinessDto business= new BusinessDto(area);
		Response response = businessBridge.createBusiness(business);
		if (response.getStatus()==200) {
			errorsHandler.createBusiness(true, null);
			singletonBean.addNewBusiness(business);
			return null;
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.createBusiness(false, errors);
		}
		return null;
	}

	public List<BusinessStatsDto> findAllBusinesses(){
		businessStatsList = businessBridge.findAllBusinesses();
		return businessStatsList;
	}
	
	public void updateBusinessStatsDto() {
		this.selectedBusinessStats=businessBridge.findSelectedBusinessStats(selectedBusiness.getArea());
	}
	
	public List<ClientDto> getAllBusinessClientsList() {
		return listClientsBean.getAllBusinessClientsList(selectedBusinessStats);
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////


	public List<BusinessDto> getBusinessList() {
		return businessList;
	}

	public void setBusinessList(List<BusinessDto> businessList) {
		this.businessList = businessList;
	}

	public BusinessDto getSelectedBusiness() {
		return selectedBusiness;
	}

	public void setSelectedBusiness(BusinessDto selectedBusiness) {
		this.selectedBusiness = selectedBusiness;
	}

	public BusinessStatsDto getSelectedBusinessStats() {
		return selectedBusinessStats;
	}

	public void setSelectedBusinessStats(BusinessStatsDto selectedBusinessStats) {
		this.selectedBusinessStats = selectedBusinessStats;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}
