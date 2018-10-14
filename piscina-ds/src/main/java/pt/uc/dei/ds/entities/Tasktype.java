package pt.uc.dei.ds.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tasktype database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name="Tasktype.findAll", query="SELECT t FROM Tasktype t"),
	@NamedQuery(name="Tasktype.findTasktypeWithName", query="SELECT tt FROM Tasktype tt WHERE tt.taskType=:taskType"),
})

public class Tasktype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String taskType;

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="tasktype")
	private List<Task> tasks;

	public Tasktype() {
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

	public String getTaskType() {
		return this.taskType;
	}

	public void setTaskType(String tasktype) {
		this.taskType = tasktype;
	}

	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Task addTask(Task task) {
		getTasks().add(task);
		task.setTasktype(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setTasktype(null);

		return task;
	}

}