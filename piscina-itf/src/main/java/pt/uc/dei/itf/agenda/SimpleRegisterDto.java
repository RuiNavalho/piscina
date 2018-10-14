package pt.uc.dei.itf.agenda;

public class SimpleRegisterDto {
	 
	private String taskName;
	private int taskId;
	private int allocId;
	private int maxHours;
	private String projectId;
	private String projectTitle;
	
	public SimpleRegisterDto() {
	}
	
	public SimpleRegisterDto(String taskName, int taskId, int allocId, int maxHours, String projectId, String projectTitle) {
		this.taskName = taskName;
		this.taskId = taskId;
		this.allocId = allocId;
		this.maxHours = maxHours;
		this.projectId=projectId;
		this.projectTitle=projectTitle;
	}

	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getAllocId() {
		return allocId;
	}
	public void setAllocId(int allocId) {
		this.allocId = allocId;
	}
	public int getMaxHours() {
		return maxHours;
	}
	public void setMaxHours(int maxHours) {
		this.maxHours = maxHours;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectTitle() {
		return projectTitle;
	}
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
		
}
