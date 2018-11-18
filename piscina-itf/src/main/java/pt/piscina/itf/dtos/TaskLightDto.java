package pt.piscina.itf.dtos;

import java.io.Serializable;
import java.util.Date;

public class TaskLightDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int taskId;
	private String taskName;
	private int durationHoursEstimate;
	private Date beginDate;
	private Date endDate;
	private int executedPercentageEstimate;
	private int workedHours;
	private int nowDurationHoursEstimate;
	private String allocatedUserName;
	private String allocatedUserEmail;
	private String managerEmail;
	private String projectTitle;
	private String idProject;
	private String description;
	private String skill;
	private String taskstage;
	private String tasktype;
	private float hourCost;
	private Float allocPercentage;
	private Date beginAllocationDate;
	private Date endAllocationDate;
	
	public TaskLightDto() {
	}
	
	public TaskLightDto(int taskId, String taskName, int durationHoursEstimate, int nowDurationHoursEstimate, Date beginDate, Date endDate,
			int executedPercentageEstimate, String allocatedUserName, String allocatedUserEmail, String managerEmail, String projectTitle,
			String idProject, String description, String skill, String taskstage, String tasktype, float hourCost,
			Float allocPercentage, Date beginAllocationDate,  Date endAllocationDate, int workedHours) {
		this.taskId=taskId;
		this.taskName = taskName;
		this.durationHoursEstimate = durationHoursEstimate;
		this.nowDurationHoursEstimate=nowDurationHoursEstimate;
		this.workedHours=workedHours;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.executedPercentageEstimate = executedPercentageEstimate;
		this.allocatedUserName=allocatedUserName;
		this.allocatedUserEmail=allocatedUserEmail;
		this.managerEmail=managerEmail;
		this.projectTitle=projectTitle;
		this.idProject=idProject;
		this.description=description;
		this.skill=skill;
		this.taskstage=taskstage;
		this.tasktype=tasktype;
		this.hourCost=hourCost;
		this.allocPercentage=allocPercentage;
		this.beginAllocationDate=beginAllocationDate;
		this.endAllocationDate=endAllocationDate;
	}

	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getDurationHoursEstimate() {
		return durationHoursEstimate;
	}
	public void setDurationHoursEstimate(int durationHoursEstimate) {
		this.durationHoursEstimate = durationHoursEstimate;
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
	public int getExecutedPercentageEstimate() {
		return executedPercentageEstimate;
	}
	public void setExecutedPercentageEstimate(int executedPercentageEstimate) {
		this.executedPercentageEstimate = executedPercentageEstimate;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getAllocatedUserName() {
		return allocatedUserName;
	}
	public void setAllocatedUserName(String allocatedUserName) {
		this.allocatedUserName = allocatedUserName;
	}
	public String getAllocatedUserEmail() {
		return allocatedUserEmail;
	}
	public void setAllocatedUserEmail(String allocatedUserEmail) {
		this.allocatedUserEmail = allocatedUserEmail;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
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

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
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

	public float getHourCost() {
		return hourCost;
	}

	public void setHourCost(float hourCost) {
		this.hourCost = hourCost;
	}

	public Float getAllocPercentage() {
		return allocPercentage;
	}

	public void setAllocPercentage(Float allocPercentage) {
		this.allocPercentage = allocPercentage;
	}

	public Date getBeginAllocationDate() {
		return beginAllocationDate;
	}

	public void setBeginAllocationDate(Date beginAllocationDate) {
		this.beginAllocationDate = beginAllocationDate;
	}

	public Date getEndAllocationDate() {
		return endAllocationDate;
	}

	public void setEndAllocationDate(Date endAllocationDate) {
		this.endAllocationDate = endAllocationDate;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allocatedUserEmail == null) ? 0 : allocatedUserEmail.hashCode());
		result = prime * result + ((allocatedUserName == null) ? 0 : allocatedUserName.hashCode());
		result = prime * result + ((beginDate == null) ? 0 : beginDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + durationHoursEstimate;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + executedPercentageEstimate;
		result = prime * result + Float.floatToIntBits(hourCost);
		result = prime * result + ((idProject == null) ? 0 : idProject.hashCode());
		result = prime * result + ((projectTitle == null) ? 0 : projectTitle.hashCode());
		result = prime * result + ((skill == null) ? 0 : skill.hashCode());
		result = prime * result + taskId;
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
		result = prime * result + ((taskstage == null) ? 0 : taskstage.hashCode());
		result = prime * result + ((tasktype == null) ? 0 : tasktype.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskLightDto other = (TaskLightDto) obj;
		if (allocatedUserEmail == null) {
			if (other.allocatedUserEmail != null)
				return false;
		} else if (!allocatedUserEmail.equals(other.allocatedUserEmail))
			return false;
		if (allocatedUserName == null) {
			if (other.allocatedUserName != null)
				return false;
		} else if (!allocatedUserName.equals(other.allocatedUserName))
			return false;
		if (beginDate == null) {
			if (other.beginDate != null)
				return false;
		} else if (!beginDate.equals(other.beginDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (durationHoursEstimate != other.durationHoursEstimate)
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (executedPercentageEstimate != other.executedPercentageEstimate)
			return false;
		if (Float.floatToIntBits(hourCost) != Float.floatToIntBits(other.hourCost))
			return false;
		if (idProject == null) {
			if (other.idProject != null)
				return false;
		} else if (!idProject.equals(other.idProject))
			return false;
		if (projectTitle == null) {
			if (other.projectTitle != null)
				return false;
		} else if (!projectTitle.equals(other.projectTitle))
			return false;
		if (skill == null) {
			if (other.skill != null)
				return false;
		} else if (!skill.equals(other.skill))
			return false;
		if (taskId != other.taskId)
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		if (taskstage == null) {
			if (other.taskstage != null)
				return false;
		} else if (!taskstage.equals(other.taskstage))
			return false;
		if (tasktype == null) {
			if (other.tasktype != null)
				return false;
		} else if (!tasktype.equals(other.tasktype))
			return false;
		return true;
	}

	public int getWorkedHours() {
		return workedHours;
	}

	public void setWorkedHours(int workedHours) {
		this.workedHours = workedHours;
	}

	public int getNowDurationHoursEstimate() {
		return nowDurationHoursEstimate;
	}

	public void setNowDurationHoursEstimate(int nowDurationHoursEstimate) {
		this.nowDurationHoursEstimate = nowDurationHoursEstimate;
	}


}
