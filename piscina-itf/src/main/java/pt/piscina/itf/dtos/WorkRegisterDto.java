package pt.piscina.itf.dtos;

import java.sql.Timestamp;

public class WorkRegisterDto {
	
	private int allocationId;
	private String taskName;
	private String idProject;
	private String title; 
	private Timestamp allocationBeginDate;
	private Timestamp allocationEndDate;
	private int maxWorkingHours;
	private int hoursToFinish;
	private float taskPercentageExecuted;
	
	
	public WorkRegisterDto() {
		allocationId=0;
		taskName="";
		idProject="";
		title="";
		allocationBeginDate= new Timestamp(System.currentTimeMillis());
		allocationEndDate= new Timestamp(System.currentTimeMillis());
		maxWorkingHours=0;
		hoursToFinish=0;
		taskPercentageExecuted=0;
	}
	
	public WorkRegisterDto(int allocationId, String taskName, String idProject, String title, Timestamp taskBeginDate,
			Timestamp taskEndDate, int maxWorkingHours, int hoursToFinish, float percentageToFinish) {
		this.allocationId = allocationId;
		this.taskName = taskName;
		this.idProject = idProject;
		this.title = title;
		this.allocationBeginDate = taskBeginDate;
		this.allocationEndDate = taskEndDate;
		this.maxWorkingHours = maxWorkingHours;
		this.hoursToFinish = hoursToFinish;
		this.taskPercentageExecuted = percentageToFinish;
	}

	public int getAllocationId() {
		return allocationId;
	}
	public void setAllocationId(int allocationId) {
		this.allocationId = allocationId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getIdProject() {
		return idProject;
	}
	public void setIdProject(String idProject) {
		this.idProject = idProject;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getAllocationBeginDate() {
		return allocationBeginDate;
	}
	public void setAllocationBeginDate(Timestamp taskBeginDate) {
		this.allocationBeginDate = taskBeginDate;
	}
	public Timestamp getAllocationEndDate() {
		return allocationEndDate;
	}
	public void setAllocationEndDate(Timestamp taskEndDate) {
		this.allocationEndDate = taskEndDate;
	}
	public int getMaxWorkingHours() {
		return maxWorkingHours;
	}
	public void setMaxWorkingHours(int maxWorkingHours) {
		this.maxWorkingHours = maxWorkingHours;
	}
	public int getHoursToFinish() {
		return hoursToFinish;
	}
	public void setHoursToFinish(int hoursToFinish) {
		this.hoursToFinish = hoursToFinish;
	}
	public float getTaskPercentageExecuted() {
		return taskPercentageExecuted;
	}
	public void setTaskPercentageExecuted(float percentageToFinish) {
		this.taskPercentageExecuted = percentageToFinish;
	}

}
