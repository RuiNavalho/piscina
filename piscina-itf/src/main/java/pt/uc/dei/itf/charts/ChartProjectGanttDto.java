package pt.uc.dei.itf.charts;

import java.util.Date;
import java.util.List;

public class ChartProjectGanttDto {

	private String projectId;
	private String title;
	private Date beginDate;
	private Date endDate;
	private List<TaskGanttDto> tasksList;
	
	public ChartProjectGanttDto() {
	}
	
	public ChartProjectGanttDto(String projectId, String title, Date beginDate, Date endDate,
			List<TaskGanttDto> tasksList) {
		this.projectId = projectId;
		this.title = title;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.tasksList = tasksList;
	}
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public List<TaskGanttDto> getTasksList() {
		return tasksList;
	}
	public void setTasksList(List<TaskGanttDto> tasksList) {
		this.tasksList = tasksList;
	}
	
}
