package pt.uc.dei.itf.dtos;

import java.util.List;

public class TaskPrecedence {
	
	private TaskLightDto task;
	private List<TaskLightDto> precentTasks;
	
	public TaskPrecedence() {
	}

	public TaskPrecedence(TaskLightDto task, List<TaskLightDto> precentTaskIds) {
		this.task = task;
		this.precentTasks = precentTaskIds;
	}

	public TaskLightDto getTask() {
		return task;
	}

	public void setTask(TaskLightDto task) {
		this.task = task;
	}

	public List<TaskLightDto> getPrecentTasks() {
		return precentTasks;
	}

	public void setPrecentTasks(List<TaskLightDto> precentTaskIds) {
		this.precentTasks = precentTaskIds;
	}
	
}
