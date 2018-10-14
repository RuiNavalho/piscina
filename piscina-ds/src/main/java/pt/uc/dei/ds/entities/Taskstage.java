package pt.uc.dei.ds.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the taskstage database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name="Taskstage.findAll", query="SELECT t FROM Taskstage t"),
	@NamedQuery(name="Taskstage.findTaskstageWithName", query="SELECT ts FROM Taskstage ts WHERE ts.taskStage=:taskStage"),
})

public class Taskstage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String taskStage;

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="taskstage")
	private List<Task> tasks;

	public Taskstage() {
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskStage() {
		return this.taskStage;
	}

	public void setTaskStage(String taskstage) {
		this.taskStage = taskstage;
	}

	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Task addTask(Task task) {
		getTasks().add(task);
		task.setTaskstage(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setTaskstage(null);

		return task;
	}

}