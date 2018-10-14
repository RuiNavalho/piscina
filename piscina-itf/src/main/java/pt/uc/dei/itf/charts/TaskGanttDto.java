package pt.uc.dei.itf.charts;

import java.util.Date;

public class TaskGanttDto {

	private int taskId;
	private String taskName;
	private Date beginDate;
	private Date endDate;
	private float percentageExecuted;
	
	public TaskGanttDto() {
	}
	
	public TaskGanttDto(int taskId, String taskName, Date beginDate, Date endDate, float percentageExecuted) {
		this.taskId = taskId;
		this.taskName = taskName;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.percentageExecuted=percentageExecuted;
	}

	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
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
	public float getPercentageExecuted() {
		return percentageExecuted;
	}
	public void setPercentageExecuted(float percentageExecuted) {
		this.percentageExecuted = percentageExecuted;
	}
	
}
