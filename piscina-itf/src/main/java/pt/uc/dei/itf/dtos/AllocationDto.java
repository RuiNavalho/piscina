package pt.uc.dei.itf.dtos;

import java.util.Date;

public class AllocationDto {
	
	private float allocPercentage;
	private int taskId;
	private String workerEmail;
	private String workerName;
	private String allocatorEmail;
	private Date beginDate;
	private Date endDate;
	private Date taskEndDate;
	private Integer allocationId;
	
	private String taskName;
	private String projectId;
	
	public AllocationDto() {
	}
	
	public AllocationDto(float allocPercentage, int taskId, String workerEmail, String workerName, String allocatorEmail,
			Date beginDate, Date endDate) {
		this.allocPercentage = allocPercentage;
		this.taskId = taskId;
		this.workerEmail = workerEmail;
		this.workerName=workerName;
		this.allocatorEmail = allocatorEmail;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
	
	public AllocationDto(float allocPercentage, int taskId, String workerEmail, String workerName, String allocatorEmail, Date beginDate,
			Date endDate, Integer allocationId, Date taskEndDate, String taskName, String projectId) {
		this.allocPercentage = allocPercentage;
		this.taskId = taskId;
		this.workerEmail = workerEmail;
		this.workerName=workerName;
		this.allocatorEmail = allocatorEmail;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.allocationId = allocationId;
		this.taskEndDate=taskEndDate;
		this.taskName=taskName;
		this.projectId=projectId;
	}

	public float getAllocPercentage() {
		return allocPercentage;
	}
	public void setAllocPercentage(float allocPercentage) {
		this.allocPercentage = allocPercentage;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getWorkerEmail() {
		return workerEmail;
	}
	public void setWorkerEmail(String workerEmail) {
		this.workerEmail = workerEmail;
	}
	public String getAllocatorEmail() {
		return allocatorEmail;
	}
	public void setAllocatorEmail(String allocatorEmail) {
		this.allocatorEmail = allocatorEmail;
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
	public Integer getAllocationId() {
		return allocationId;
	}
	public void setAllocationId(Integer allocationId) {
		this.allocationId = allocationId;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public Date getTaskEndDate() {
		return taskEndDate;
	}
	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
