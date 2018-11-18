package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import pt.piscina.itf.dtos.ProjectNewDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.ProjectBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named
@SessionScoped
public class CreateProjectBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String title;
	private String idProject;
	private String description;
	private String clientCompanyName;
	private Date beginDate;
	private Date endDate;
	private String budget;
	private String stage;
	private String managerEmail;
	private String tipology;
	private boolean projectToClient;

	@Inject
	private ProjectBridge projectBridge;
	
	@Inject
	private ErrorsHandler errorsHandler;

	public CreateProjectBean() {
	}
	
	public void resetFields(){
		title = null;
		projectToClient = false;
		idProject = null;
		description = null;
		clientCompanyName = null;
		beginDate = null;
		endDate = null;
		budget = null;
		stage = null;
		managerEmail = null;
		tipology = null;
	}
	public void resetFieldsFromClient(){
		title = null;
		projectToClient = false;
		idProject = null;
		description = null;
		beginDate = null;
		endDate = null;
		budget = null;
		stage = null;
		managerEmail = null;
		tipology = null;
	}
	
	public String createNewProject() {
		ProjectNewDto newProject = new ProjectNewDto(title, idProject, description, clientCompanyName, new Timestamp(beginDate.getTime()), new Timestamp(endDate.getTime()), Float.valueOf(budget), stage, null, managerEmail, tipology);
		Response response =projectBridge.createNewProject(newProject);
		if (response.getStatus()==200) {
			errorsHandler.createNewProject(true, null);
			return "projects_new.xhtml";
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.createNewProject(false, errors);
		}
		return null;
	}
	
	
	public void createNewProjectToClient(String clientCompanyName) {
		resetFields();
		projectToClient = true;
		this.clientCompanyName = clientCompanyName;
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdProject() {
		return idProject;
	}

	public void setIdProject(String idProject) {
		this.idProject = idProject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClientCompanyName() {
		return clientCompanyName;
	}

	public void setClientCompanyName(String clientCompanyName) {
		this.clientCompanyName = clientCompanyName;
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

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getTipology() {
		return tipology;
	}

	public void setTipology(String tipology) {
		this.tipology = tipology;
	}

	public ProjectBridge getProjectBridge() {
		return projectBridge;
	}

	public void setProjectBridge(ProjectBridge projectBridge) {
		this.projectBridge = projectBridge;
	}

	public boolean isProjectToClient() {
		return projectToClient;
	}

	public void setProjectToClient(boolean projectToClient) {
		this.projectToClient = projectToClient;
	}

}
