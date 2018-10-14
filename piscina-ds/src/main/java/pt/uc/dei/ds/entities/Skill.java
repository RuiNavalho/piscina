package pt.uc.dei.ds.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the skill database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name="Skill.findAll", query="SELECT s FROM Skill s"),
@NamedQuery(name="Skill.findUsersWithSkill", query="SELECT u from User u join u.skills s where s.id=:id"),
@NamedQuery(name="Skill.findSkillWithName", query="SELECT s from Skill s WHERE s.skill=:skill"),
//@NamedQuery(name="Skill.findTasks", query="SELECT t from Task t join t.skill s where s.id=:id"), //TODO esta querie sera necessaria???
})
public class Skill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String skill;

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="skill")
	private List<Task> tasks;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="skills")
	private List<User> users;

	public Skill() {
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

	public String getSkill() {
		return this.skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Task addTask(Task task) {
		getTasks().add(task);
		task.setSkill(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setSkill(null);

		return task;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}