package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import pt.uc.dei.itf.charts.ChartProjectAllocationsDto;
import pt.uc.dei.itf.charts.ChartUserPerformanceDto;
import pt.uc.dei.itf.charts.UserPerformanceDto;
import pt.uc.dei.itf.dtos.AllocationDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.AllocationBridge;
import pt.uc.dei.ws.bridges.ChartBridge;
import pt.uc.dei.ws.charts.ChartTimeline;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named("listAllocationsBean")
@SessionScoped
public class ListAllocationsBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String email;
	private Date begin;
	private Date end;
	
	private String type;
	private boolean showHidePanel = false;
	
	private List<AllocationDto> allocsList;
	
	//possivelmente remover
	private AllocationDto selectedAllocation;
	
	private List<UserPerformanceDto> workList;
	private ChartUserPerformanceDto cup;
	
	

	@Inject
	private AllocationBridge allocationBridge;
	
	@Inject
	private ChartBridge chartBridge;

	@Inject
	private ErrorsHandler errorsHandler;
	
	@Inject
	private ChartTimeline chartTimeline;
	
	

	public ListAllocationsBean() {
	}
	
	public void cleanFastSearch(){
		type="";
	}
	public void cleanAdvancedSearch(){
		begin=null;
		end=null;
	}
	public void cleanFields(){
		email="";
		begin=null;
		end=null;
		type="";
		showHidePanel = false;
		allocsList = null;
	}
	public void findMyAllocations() {
		cleanAdvancedSearch();
		Response response = allocationBridge.findMyAllocations(type);
		if (response.getStatus()==200) {
			this.allocsList = response.readEntity(new GenericType<List<AllocationDto>>() {});
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.findAllMyAllocations(false, errors);
		}
	}

	public List<AllocationDto> findMyAllocationsBetweenDates() {
		Response response = allocationBridge.getMyAllocationsBetweenDates(begin, end);
		if (response.getStatus()==200) {
			this.allocsList = response.readEntity(new GenericType<List<AllocationDto>>() {});
			ChartProjectAllocationsDto cup = new ChartProjectAllocationsDto("", begin, end, allocsList);
			chartTimeline.updateUserAllocationsChart(cup);
			return allocsList;
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.findMyAllocationsBetweenDates(false, errors);
			return null;
		}
	}
	
	public void findUserAllocationsBetweenDates() {
		Response response = allocationBridge.getUserAllocationsBetweenDates(email, begin, end);
		if (response.getStatus()==200) {
			this.allocsList = response.readEntity(new GenericType<List<AllocationDto>>() {});
			ChartProjectAllocationsDto cup = new ChartProjectAllocationsDto(email, begin, end, allocsList);
			chartTimeline.updateUserAllocationsChart(cup);

		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.findMyAllocationsBetweenDates(false, errors);
		}
	}
	
	public void findUserPerformanceChart() {
		Response response = chartBridge.getUserPerformanceChart(email, begin, end);
		if (response.getStatus()==200) {
			cup = response.readEntity(ChartUserPerformanceDto.class);
			workList=cup.getWorkList();
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.findUserPerformanceChart(false, errors);
		}
	}
	
	public void findMyUserPerformanceChart() {
		Response response = chartBridge.getMyUserPerformanceChart(begin, end);
		if (response.getStatus()==200) {
			cup = response.readEntity(ChartUserPerformanceDto.class);
			setWorkList(cup.getWorkList());
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.findMyUserPerformanceChart(false, errors);
		}
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public List<AllocationDto> getAllocsList() {
		return allocsList;
	}

	public void setAllocsList(List<AllocationDto> allocsList) {
		this.allocsList = allocsList;
	}

	public AllocationDto getSelectedAllocation() {
		return selectedAllocation;
	}

	public void setSelectedAllocation(AllocationDto selectedAllocation) {
		this.selectedAllocation = selectedAllocation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<UserPerformanceDto> getWorkList() {
		return workList;
	}

	public void setWorkList(List<UserPerformanceDto> workList) {
		this.workList = workList;
	}

	public ChartUserPerformanceDto getCup() {
		return cup;
	}

	public void setCup(ChartUserPerformanceDto cup) {
		this.cup = cup;
	}

	public boolean isShowHidePanel() {
		return showHidePanel;
	}

	public void setShowHidePanel(boolean showHidePanel) {
		this.showHidePanel = showHidePanel;
	}

}
