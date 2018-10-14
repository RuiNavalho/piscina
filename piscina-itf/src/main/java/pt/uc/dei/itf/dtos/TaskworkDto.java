package pt.uc.dei.itf.dtos;

import java.util.Date;
import java.util.List;

public class TaskworkDto {
	
	private int workedHours;
	private int remainingHours;
	private int expectedRemainingHours;
	private int allocationId;
	private int taskId;
	private String userName;
	private String userEmail;
	private String projectId;
	private String projectName;
	private String taskName;
	private Date date;
	private List<AttachmentDto> attachs;
	private String comments;
	
	public TaskworkDto() {
	}

	public TaskworkDto(int workedHours, int remainingHours, int expectedRemainingHours, int allocationId, int taskId, String userName, String userEmail,
			String projectId, String projectName, String taskName, Date date, List<AttachmentDto> attachs, String comments) {
		this.workedHours = workedHours;
		this.remainingHours=remainingHours;
		this.expectedRemainingHours=expectedRemainingHours;
		this.allocationId = allocationId;
		this.taskId = taskId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.projectId = projectId;
		this.projectName = projectName;
		this.taskName = taskName;
		this.date=date;
		this.attachs=attachs;
		this.comments=comments;
	}

	public int getWorkedHours() {
		return workedHours;
	}

	public void setWorkedHours(int workedHours) {
		this.workedHours = workedHours;
	}

	public int getAllocationId() {
		return allocationId;
	}

	public void setAllocationId(int allocationId) {
		this.allocationId = allocationId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<AttachmentDto> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<AttachmentDto> attachs) {
		this.attachs = attachs;
	}

	public int getRemainingHours() {
		return remainingHours;
	}

	public void setRemainingHours(int remainingHours) {
		this.remainingHours = remainingHours;
	}

	public int getExpectedRemainingHours() {
		return expectedRemainingHours;
	}

	public void setExpectedRemainingHours(int expectedRemainingHours) {
		this.expectedRemainingHours = expectedRemainingHours;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
