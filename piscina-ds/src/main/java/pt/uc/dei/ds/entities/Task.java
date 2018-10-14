package pt.uc.dei.ds.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the task database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name="Task.findAll", query="SELECT t FROM Task t"),
@NamedQuery(name="Task.findAttachments", query="SELECT a from Attachment a join a.taskwork tw JOIN tw.task t where t.id=:id"),
@NamedQuery(name="Task.findPrecedentTasks", query="SELECT tb from Task t join t.tasksBefore tb WHERE t.id=:id"),
@NamedQuery(name="Task.findPossiblePrecendentTasks", query="SELECT t from Task t JOIN t.project p WHERE t.project.id=:projId AND t.endDate<:beginDate AND t.id<>:id"),
@NamedQuery(name="Task.findNextTasks", query="SELECT DISTINCT ta from Task t join t.tasksAfter ta WHERE t.id=:id"),
@NamedQuery(name="Task.findTaskWorks", query="SELECT tw from Taskwork tw join tw.allocation a JOIN a.task t where t.id=:id"),
@NamedQuery(name="Task.findTaskTaskworkByAscendingDate", query="SELECT tw from Taskwork tw join tw.task t where t.id=:id ORDER BY tw.creationDate"),
@NamedQuery(name="Task.findMyWorkingTasks", query="SELECT DISTINCT t FROM Task t JOIN t.allocations a JOIN a.user u WHERE u.email=:email AND a.beginDate<=a.endDate"),
@NamedQuery(name="Task.findAllocations", query="SELECT a FROM Allocation a JOIN a.task t WHERE t.id=:id AND a.endDate>=a.beginDate"),
@NamedQuery(name="Task.findAllocationsBetweenDates", query="SELECT a FROM Allocation a JOIN a.task t WHERE t.id=:id AND a.endDate>=a.beginDate AND a.beginDate>=:begin AND a.endDate>=:end"),
@NamedQuery(name="Task.findWorkedHours", query="SELECT SUM(tw.hoursWorked) from Taskwork tw join tw.task t where t.id=:id"),
//TODO remover a querie abaixo pois nao servira para nada
@NamedQuery(name="Task.findLastTaskAllocation", query="SELECT a from Allocation a JOIN a.task t WHERE a.endDate=t.endDate and t.id=:taskId"),
@NamedQuery(name="Task.findTaskAllocationInDate", query="SELECT a from Allocation a JOIN a.task t WHERE a.endDate>=a.beginDate AND t.id=:taskId AND a.beginDate>=:date AND a.endDate<=:date"),


})
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private Timestamp beginDate;

	private String description;

	private int initialDurationHoursEstimate;
	
	private int remainingHours;

	private Timestamp endDate;

	private float hourCost;

	private String taskName;

	//bi-directional many-to-one association to Allocation
	@OneToMany(mappedBy="task")
	private List<Allocation> allocations;

	//bi-directional many-to-one association to Project
	@ManyToOne
	private Project project;

	//bi-directional many-to-one association to Skill
	@ManyToOne
	private Skill skill;

	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(
		name="task_task"
		, joinColumns={
			@JoinColumn(name="task_before")
			}
		, inverseJoinColumns={
			@JoinColumn(name="task_after")
			}
		)
	private List<Task> tasksBefore;

	//bi-directional many-to-one association to Task
	@ManyToMany(mappedBy="tasksBefore")
	private List<Task> tasksAfter;

	//bi-directional many-to-one association to Taskstage
	@ManyToOne
	private Taskstage taskstage;

	//bi-directional many-to-one association to Tasktype
	@ManyToOne
	private Tasktype tasktype;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_creator")
	private User user;

	//bi-directional many-to-one association to Taskwork
	@OneToMany(mappedBy="task")
	private List<Taskwork> taskworks;

	public Task() {
	}
	
	public Task(Timestamp beginDate, String description, int durationHoursEstimate, Timestamp endDate,
			float hourCost, String taskName,Project project, Skill skill, Taskstage taskstage, Tasktype tasktype, User user) {
		this.beginDate = beginDate;
		this.description = description;
		this.initialDurationHoursEstimate = durationHoursEstimate;
		this.endDate = endDate;
		this.hourCost = hourCost;
		this.taskName = taskName;
		this.project = project;
		this.skill = skill;
		this.taskstage = taskstage;
		this.tasktype = tasktype;
		this.user = user;
		this.remainingHours=durationHoursEstimate;
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

	public Timestamp getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Timestamp begindate) {
		this.beginDate = begindate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getInitialDurationHoursEstimate() {
		return this.initialDurationHoursEstimate;
	}

	public void setInitialDurationHoursEstimate(int durationhoursestimate) {
		this.initialDurationHoursEstimate = durationhoursestimate;
	}

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp enddate) {
		this.endDate = enddate;
	}

	public float getHourCost() {
		return this.hourCost;
	}

	public void setHourCost(float hourcost) {
		this.hourCost = hourcost;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskname) {
		this.taskName = taskname;
	}

	public List<Allocation> getAllocations() {
		return this.allocations;
	}

	public void setAllocations(List<Allocation> allocations) {
		this.allocations = allocations;
	}

	public Allocation addAllocation(Allocation allocation) {
		getAllocations().add(allocation);
		allocation.setTask(this);

		return allocation;
	}

	public Allocation removeAllocation(Allocation allocation) {
		getAllocations().remove(allocation);
		allocation.setTask(null);

		return allocation;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Skill getSkill() {
		return this.skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public List<Task> getTasksBefore() {
		return tasksBefore;
	}

	public void setTasksBefore(List<Task> tasksBefore) {
		this.tasksBefore = tasksBefore;
	}

	public List<Task> getTasksAfter() {
		return tasksAfter;
	}

	public void setTasksAfter(List<Task> tasksAfter) {
		this.tasksAfter = tasksAfter;
	}

	public Taskstage getTaskstage() {
		return this.taskstage;
	}

	public void setTaskstage(Taskstage taskstage) {
		this.taskstage = taskstage;
	}

	public Tasktype getTasktype() {
		return this.tasktype;
	}

	public void setTasktype(Tasktype tasktype) {
		this.tasktype = tasktype;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Taskwork> getTaskworks() {
		return this.taskworks;
	}

	public void setTaskworks(List<Taskwork> taskworks) {
		this.taskworks = taskworks;
	}

	public Taskwork addTaskwork(Taskwork taskwork) {
		getTaskworks().add(taskwork);
		taskwork.setTask(this);

		return taskwork;
	}

	public Taskwork removeTaskwork(Taskwork taskwork) {
		getTaskworks().remove(taskwork);
		taskwork.setTask(null);

		return taskwork;
	}

	public int getRemainingHours() {
		return remainingHours;
	}

	public void setRemainingHours(int remainingHours) {
		this.remainingHours = remainingHours;
	}

}