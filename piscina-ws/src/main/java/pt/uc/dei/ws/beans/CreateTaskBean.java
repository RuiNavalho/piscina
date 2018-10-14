package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import pt.uc.dei.itf.dtos.TaskNewDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.ProjectBridge;
import pt.uc.dei.ws.bridges.TaskBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named
@SessionScoped
public class CreateTaskBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String idProject;
	private Date beginDate;
	private Date endDate;
	private String taskName;
	private String description;
	private int durationHoursEstimate;
	private String taskstage;
	private String tasktype;
	private String skill="Nenhum";
	private String lastSkill;
	private float hourCost;
	private String resourceEmail;
	
	private boolean newTaskFromProject;

	private Date minBeginDate;
	private Date maxEndDate;
	
	private int createdTaskId;

	private boolean taskToProject; 

	private String titleProject;

	@Inject
	private TaskBridge taskBridge;
	
	@Inject
	private ListTasksBean listTasksBean;
	
	@Inject
	private ErrorsHandler errorsHandler;
	
	

	public CreateTaskBean() {
	}

	public void createNewTaskToProject(String idProject, String titleProject, Date beginDate, Date endDate) {
		resetFields();
		taskToProject=true;
		minBeginDate=beginDate;
		this.beginDate= beginDate;
		this.endDate=endDate;
		this.skill= "Nenhum";
		maxEndDate=endDate;
		this.idProject=idProject;
		this.titleProject=titleProject;
		//createNewTask();
	}
	
	
	public void resetFields(){
		taskToProject=false;
		idProject=null;
		beginDate=null;
		endDate=null;
		taskName=null;
		description=null;
		durationHoursEstimate=0;
		taskstage=null;
		tasktype=null;
		skill="Nenhum";
		hourCost=0;
	}

	//TODO
	public String createNewTask() {
		if (skill!=null) {
			lastSkill=skill;
		}
		if (skill==null || skill.equals("Nenhum")) {
			skill=null;
		}

		if (resourceEmail == null || resourceEmail.equals("Nenhum")) {
			resourceEmail=null;
		}
		TaskNewDto taskNewDto = new TaskNewDto(idProject, beginDate, endDate, taskName, description, durationHoursEstimate, taskstage, tasktype, skill, hourCost, resourceEmail, null);
		Response response = taskBridge.createNewTask(taskNewDto);

		if (response.getStatus()==200) {
			createdTaskId = response.readEntity(Integer.class);
			listTasksBean.newTaskCreated(createdTaskId);
			errorsHandler.createNewTask(true, null);
			return null;
		} else {
			if (newTaskFromProject) {
				List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
				errorsHandler.createNewTaskProject(false, errors);
				skill=lastSkill;
			} else {
				List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
				errorsHandler.createNewTask(false, errors);
				skill=lastSkill;
			}

		}
		return null;
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public String getIdProject() {
		return idProject;
	}

	public void setIdProject(String idProject) {
		this.idProject = idProject;
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDurationHoursEstimate() {
		return durationHoursEstimate;
	}

	public void setDurationHoursEstimate(int durationHoursEstimate) {
		this.durationHoursEstimate = durationHoursEstimate;
	}

	public String getTaskstage() {
		return taskstage;
	}

	public void setTaskstage(String taskstage) {
		this.taskstage = taskstage;
	}

	public String getTasktype() {
		return tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public float getHourCost() {
		return hourCost;
	}

	public void setHourCost(float hourCost) {
		this.hourCost = hourCost;
	}

	public Date getMinBeginDate() {
		return minBeginDate;
	}

	public void setMinBeginDate(Date minBeginDate) {
		this.minBeginDate = minBeginDate;
	}

	public Date getMaxEndDate() {
		return maxEndDate;
	}

	public void setMaxEndDate(Date maxEndDate) {
		this.maxEndDate = maxEndDate;
	}

	public String getTitleProject() {
		return titleProject;
	}

	public void setTitleProject(String titleProject) {
		this.titleProject = titleProject;
	}

	public TaskBridge getTaskBridge() {
		return taskBridge;
	}

	public void setTaskBridge(TaskBridge taskBridge) {
		this.taskBridge = taskBridge;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isTaskToProject() {
		return taskToProject;
	}

	public void setTaskToProject(boolean taskToProject) {
		this.taskToProject = taskToProject;
	}

	public String getResourceEmail() {
		return resourceEmail;
	}

	public void setResourceEmail(String resourceEmail) {
		this.resourceEmail = resourceEmail;
	}

	public String getLastSkill() {
		return lastSkill;
	}

	public void setLastSkill(String lastSkill) {
		this.lastSkill = lastSkill;
	}

	public int getCreatedTaskId() {
		return createdTaskId;
	}

	public void setCreatedTaskId(int createdTaskId) {
		this.createdTaskId = createdTaskId;
	}

	public boolean isNewTaskFromProject() {
		return newTaskFromProject;
	}

	public void setNewTaskFromProject(boolean newTaskFromProject) {
		this.newTaskFromProject = newTaskFromProject;
	}

}
