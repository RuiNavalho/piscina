package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import pt.uc.dei.itf.dtos.AllocationDto;
import pt.uc.dei.itf.dtos.ProjectDto;
import pt.uc.dei.itf.dtos.TaskLightDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.ProjectBridge;
import pt.uc.dei.ws.bridges.TaskBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named("listProjectsBean")
@SessionScoped
public class ListProjectsBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<ProjectDto> projList;
	private ProjectDto selectedProject;

	private String searchText="";
	private Date searchDate=null;
	private String type="";

	private List<AllocationDto> projectAllocations;
	private AllocationDto selectedProjectAllocation;
	private Date beginDate;
	private Date endDate;
	private int showPopup=0;
	private boolean showAdvancedSearchBtn;


	@Inject
	private ProjectBridge projectBridge;

	@Inject
	private ListClientsBean listClientsBean;

	@Inject
	private ListTasksBean listTasksBean;

	@Inject
	private CreateTaskBean createTaskBean;

	@Inject
	private ErrorsHandler errorsHandler;

	@Inject
	private TaskBridge taskBridge;

	public ListProjectsBean() {	
	}
	
	public void findProjectsForNewTask() {	
		Response response = projectBridge.findProjectsForNewTask();
		if (response.getStatus()==200) {
			this.projList = response.readEntity(new GenericType<List<ProjectDto>>() {});
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			//errorsHandler.findMyManagedProjectsBetweenDates(false, errors);
		}
	}
	
	public void findAllProjects() {	
		Response response = projectBridge.getAllProjects();
		if (response.getStatus()==200) {
			this.projList = response.readEntity(new GenericType<List<ProjectDto>>() {});
		} else {;
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			//errorsHandler.findAllProjects(false, errors);
		}
	}
	
	

	public void findMyWorkingProjectsBetweenDates(Date beginDate, Date endDate) {	
		Response response = projectBridge.getMyWorkingProjectsBetweenDates(beginDate, endDate);
		if (response.getStatus()==200) {
			this.projList = response.readEntity(new GenericType<List<ProjectDto>>() {});
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			//errorsHandler.getMyWorkingProjectsBetweenDates(false, errors);
		}
	}

	public void findMyManagedProjectsBetweenDates(Date beginDate, Date endDate) {	
		Response response = projectBridge.getMyManagedProjectsBetweenDates(beginDate, endDate);
		if (response.getStatus()==200) {
			this.projList = response.readEntity(new GenericType<List<ProjectDto>>() {});
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			//errorsHandler.findMyManagedProjectsBetweenDates(false, errors);
		}
	}

	public String updateProjectAllocationDates() {
		Response response = taskBridge.updateAllocationDates(selectedProjectAllocation);
		if (response.getStatus()==200) {
			findAllProjectAllocations();
			errorsHandler.updateProjectAllocationDates(true, null);	
			return null;
		} else {
			findAllProjectAllocations();
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.updateProjectAllocationDates(false, errors);
		}
		return null;
	}

	public String removeWorkerFromTaskInProject() {
		Response response = taskBridge.removeWorkerFromTask(selectedProjectAllocation.getAllocationId());
		if (response.getStatus()==200) {
			findAllProjectAllocations();
			errorsHandler.removeWorkerFromTaskInProject(true, null);	
			return null;
		} else {
			findAllProjectAllocations();
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.removeWorkerFromTaskInProject(false, errors);
		}
		return null;
	}

	public void updateProjectWithAllocation(AllocationDto allocDto) {
		this.selectedProject = projectBridge.findProjectByProjectId(allocDto.getProjectId());
	}
	
	public void updateProjectWithTask(TaskLightDto taskDto) {
		this.selectedProject = projectBridge.findProjectByProjectId(taskDto.getIdProject());
	}

	public List<AllocationDto> findAllProjectAllocations() {
		this.projectAllocations=projectBridge.getAllProjectAllocations(selectedProject.getIdProject());
		return projectAllocations;
	}

	public void projectAdvancedSearch() {
		switch (type) {
		case "date":
			this.projList=projectBridge.projectAdvancedSearch(type, null, searchDate);
			break;	
		case "all":
			this.projList=projectBridge.projectAdvancedSearch(type, "all", null);
			break;
		case "open":
			this.projList=projectBridge.projectAdvancedSearch(type, "open", null);
			break;
		case "closed":
			this.projList=projectBridge.projectAdvancedSearch(type, "closed", null);
			break;
		default:
			this.projList=projectBridge.projectAdvancedSearch(type, searchText, null);
			break;
		}
		//type="";
		searchText="";
		searchDate=null;
	}

	public void cleanFields(){
		searchText = "";
		searchDate = null;
		type = "";
		showAdvancedSearchBtn=false;
	}

	public String createNewTaskToProject() {
		createTaskBean.createNewTaskToProject(selectedProject.getIdProject(), selectedProject.getTitle(), selectedProject.getBeginDate(), selectedProject.getEndDate());
		return "activities_new.xhtml?faces-redirect=true";
	}

	public List<ProjectDto> findMyWorkingProjects() {
		this.projList=projectBridge.getMyWorkingProjects();
		return projList;
	}

	public List<ProjectDto> findMyManagedProjects() {
		this.projList=projectBridge.getMyManagedProjects();
		return projList;
	}

	public List<ProjectDto> findClientProjects() {
		this.projList=projectBridge.getClientProjects(listClientsBean.getSelectedClient());
		return projList;
	}

	public String updateProject1() {
		showPopup=0;
		if (selectedProject.getStage().equals("Closed")) {
			showPopup=1;
			return null;
		} else {
			return updateProject();
		}
		
	}

	public String updateProject() {
		Response response = projectBridge.updateProject(selectedProject);
		if (response.getStatus()==200) {
			errorsHandler.updateProject(true, null);
			return null;
		} else {	
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.updateProject(false, errors);
			this.selectedProject=projectBridge.findProjectByProjectId(selectedProject.getIdProject());
		}
		return null;
	}
	
	public void refreshSelectedProject() {
		this.selectedProject=projectBridge.findProjectByProjectId(selectedProject.getIdProject());
	}

	public void updateListClientsBean(){
		listClientsBean.updateListClientsBean(selectedProject.getClientLightDto().getCompany());
	}

	public void updateListTasksBean(){
		listTasksBean.updateListTasksBean(selectedProject.getIdProject());
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public List<ProjectDto> getProjList() {
		return projList;
	}

	public void setProjList(List<ProjectDto> projList) {
		this.projList = projList;
	}

	public ProjectDto getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(ProjectDto selectedProject) {
		this.selectedProject = selectedProject;
	}

	public ProjectBridge getProjectBridge() {
		return projectBridge;
	}

	public void setProjectBridge(ProjectBridge projectBridge) {
		this.projectBridge = projectBridge;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public Date getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (type.equals("open") || type.equals("closed") || type.equals("all")) {
			showAdvancedSearchBtn=false;
		} else {
			showAdvancedSearchBtn=true;
		}
		this.type = type;
	}

	public List<AllocationDto> getProjectAllocations() {
		return projectAllocations;
	}

	public void setProjectAllocations(List<AllocationDto> projectAllocations) {
		this.projectAllocations = projectAllocations;
	}

	public AllocationDto getSelectedProjectAllocation() {
		return selectedProjectAllocation;
	}

	public void setSelectedProjectAllocation(AllocationDto selectedProjectAllocation) {
		this.selectedProjectAllocation = selectedProjectAllocation;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getShowPopup() {
		return showPopup;
	}

	public void setShowPopup(int showPopup) {
		this.showPopup = showPopup;
	}

	public boolean isShowAdvancedSearchBtn() {
		return showAdvancedSearchBtn;
	}

	public void setShowAdvancedSearchBtn(boolean showAdvancedSearchBtn) {
		this.showAdvancedSearchBtn = showAdvancedSearchBtn;
	}

}
