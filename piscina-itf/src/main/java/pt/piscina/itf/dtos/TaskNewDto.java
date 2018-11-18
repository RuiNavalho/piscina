package pt.piscina.itf.dtos;

import java.util.Date;

public class TaskNewDto {

	private String idProject;
	private Date beginDate;
	private Date endDate;
	private String taskName;
	private String description;
	private int durationHoursEstimate;
	private String taskstage;
	private String tasktype;
	private String skill;
	private float hourCost;
	private String creatorEmail;
	//TODO remover resource email
	private String resourceEmail;

	public TaskNewDto() {
	}

	public TaskNewDto(String idProject, Date beginDate, Date endDate, String taskName, String description,
			int durationHoursEstimate, String taskstage, String tasktype, String skill, float hourCost, String resourceEmail, String creatorEmail) {
		this.idProject = idProject;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.taskName = taskName;
		this.description = description;
		this.durationHoursEstimate = durationHoursEstimate;
		this.taskstage = taskstage;
		this.tasktype = tasktype;
		this.skill = skill;
		this.hourCost = hourCost;
		this.resourceEmail=resourceEmail;
		this.creatorEmail=creatorEmail;
	}

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
	public String getCreatorEmail() {
		return creatorEmail;
	}
	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}
	public String getResourceEmail() {
		return resourceEmail;
	}
	public void setResourceEmail(String resourceEmail) {
		this.resourceEmail = resourceEmail;
	}
	
}
