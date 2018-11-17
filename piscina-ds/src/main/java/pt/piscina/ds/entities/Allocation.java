package pt.piscina.ds.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the allocation database table.
 * 
 */

/*
@Entity
@NamedQueries({ @NamedQuery(name="Allocation.findAll", query="SELECT a FROM Allocation a"),
	@NamedQuery(name="Allocation.findAllTaskworks", query="SELECT tw from Taskwork tw JOIN tw.allocation a WHERE a.id=:id"),
	@NamedQuery(name="Allocation.findByTaskIdAndEndDate", query="SELECT a FROM Allocation a WHERE a.task.id=:taskId AND a.endDate=:endDate"),
	@NamedQuery(name="Allocation.findActiveUserAllocations", query="SELECT a FROM Allocation a JOIN a.user u WHERE u.id=:userId AND a.beginDate<=:now AND a.endDate>=:now"), //TODO remover
	@NamedQuery(name="Allocation.findWorkedHoursInAllocation", query="SELECT SUM(tw.hoursWorked) from Taskwork tw JOIN tw.allocation a WHERE a.id=:id"),
	@NamedQuery(name="Allocation.findAvailableAllocationsToRegisterWork", query="SELECT a FROM Allocation a JOIN a.user u JOIN a.task t JOIN t.project p WHERE u.id=:userId AND a.beginDate<=:now AND a.endDate>=:now AND t.remainingHours>0 AND p.stage.id<>:stageIdClosed AND t.taskstage.id<>:taskIdBlocked"),
	//@NamedQuery(name="Allocation.findAvailableAllocationsToRegisterWorkToTask", query="SELECT a FROM Allocation a JOIN a.user u JOIN a.task t JOIN t.project p WHERE u.id=:userId AND a.beginDate<=:now AND a.endDate>=:now AND  t.remainingHours>0 AND p.stage.id<>:stageIdClosed AND t.taskstage.id<>:taskIdBlocked AND t.id=:taskId"),
	@NamedQuery(name="Allocation.findAllMyAllocations", query="SELECT a FROM Allocation a JOIN a.user u WHERE a.endDate>=a.beginDate AND u.email=:email"),
	@NamedQuery(name="Allocation.findMyAllocationsBetweenDates", query="SELECT DISTINCT a FROM Allocation a JOIN a.user u WHERE a.endDate>=a.beginDate AND u.email=:email AND ( (a.beginDate>=:begin AND a.beginDate<=:end) OR (a.endDate>=:begin AND a.endDate<=:end) OR (a.beginDate<=:begin AND a.endDate>=:end) )"),
	@NamedQuery(name="Allocation.findMyPresentAllocations", query="SELECT DISTINCT a FROM Allocation a JOIN a.user u WHERE a.endDate>=a.beginDate AND u.email=:email AND a.beginDate<=:now AND a.endDate>=:now"),
	@NamedQuery(name="Allocation.findMyFutureAllocations", query="SELECT DISTINCT a FROM Allocation a JOIN a.user u WHERE a.endDate>=a.beginDate AND u.email=:email AND a.beginDate>:now"),

	//Allocation.findMyPresentAllocations
})
	*/
public class Allocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private float allocPercentage;

	private Timestamp beginDate;

	private Timestamp endDate;

	//TODO mudar esta relacao de um para um
	//bi-directional many-to-one association to Task
	@ManyToOne
	private Task task;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	//bi-directional many-to-one association to Taskwork
	@OneToMany(mappedBy="allocation")
	private List<Taskwork> taskworks;

	public Allocation() {
	}

	public Allocation(float allocPercentage, Timestamp beginDate, Timestamp endDate, Task task, User user,
			List<Taskwork> taskworks) {
		this.allocPercentage = allocPercentage;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.task = task;
		this.user = user;
		this.taskworks = taskworks;
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

	public float getAllocPercentage() {
		return this.allocPercentage;
	}

	public void setAllocPercentage(float allocpercentage) {
		this.allocPercentage = allocpercentage;
	}

	public Timestamp getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Timestamp begindate) {
		this.beginDate = begindate;
	}

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp enddate) {
		this.endDate = enddate;
	}

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
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
		taskwork.setAllocation(this);

		return taskwork;
	}

	public Taskwork removeTaskwork(Taskwork taskwork) {
		getTaskworks().remove(taskwork);
		taskwork.setAllocation(null);

		return taskwork;
	}

}