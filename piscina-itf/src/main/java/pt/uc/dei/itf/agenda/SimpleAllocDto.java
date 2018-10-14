package pt.uc.dei.itf.agenda;

public class SimpleAllocDto {
	
	private String projectId;
	private String projectTitle;
	private String taskName;
	private int taskId;
	private float percAlloc;
	
	public SimpleAllocDto() {
	}
	
	public SimpleAllocDto(String projectId, String projectTitle, String taskName, int taskId, float percAlloc) {
		this.projectId = projectId;
		this.projectTitle = projectTitle;
		this.taskName = taskName;
		this.taskId = taskId;
		this.percAlloc = percAlloc;
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
	public float getPercAlloc() {
		return percAlloc;
	}
	public void setPercAlloc(float percAlloc) {
		this.percAlloc = percAlloc;
	}
	
}
