package pt.piscina.ds.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the taskwork database table.
 * 
 */
/*
@Entity
@NamedQueries({ @NamedQuery(name="Taskwork.findAll", query="SELECT t FROM Taskwork t"),
	@NamedQuery(name="Taskwork.findMyWorkingHours", query="SELECT tw FROM Taskwork tw JOIN tw.allocation a JOIN a.user u WHERE u.email=:email"),
	@NamedQuery(name="Taskwork.findMyWorkingHoursInTask", query="SELECT tw FROM Taskwork tw JOIN tw.task t JOIN tw.allocation a JOIN a.user u WHERE u.email=:email AND t.id=:taskId"),
	@NamedQuery(name="Taskwork.findWorkingHoursInTask", query="SELECT tw FROM Taskwork tw JOIN tw.task t WHERE t.id=:taskId"),
	@NamedQuery(name="Taskwork.findMyWorkingHoursInProject", query="SELECT tw FROM Taskwork tw JOIN tw.allocation a JOIN a.user u JOIN tw.task t JOIN t.project p WHERE p.idProject=:projectId AND u.email=:email"),
	@NamedQuery(name="Taskwork.findWorkingHoursInProject", query="SELECT tw FROM Taskwork tw JOIN tw.task t JOIN t.project p WHERE p.idProject=:projectId"),
	//TODO verificar se esta querie funciona mesmo com DAte em vez de Timestamp
	@NamedQuery(name="Taskwork.findWorkerTaskworksBetweenDates", query="SELECT tw FROM Taskwork tw JOIN tw.allocation a JOIN a.user u WHERE u.email=:email AND tw.creationDate>=:begin AND tw.creationDate<=:end"),
	@NamedQuery(name="Taskwork.findAttachments", query="SELECT a FROM Attachment a JOIN a.taskwork tw WHERE tw.id=:id"),

})
*/
public class Taskwork implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String comments;

	private Timestamp creationDate;

	private int hoursWorked;

	private int remainingHours;

	private int expectedRemainingHours;

	//bi-directional many-to-one association to Attachment
	@OneToMany(mappedBy="taskwork")
	private List<Attachment> attachments;

	//bi-directional many-to-one association to Allocation
	@ManyToOne
	private Allocation allocation;

	//bi-directional many-to-one association to Task
	@ManyToOne
	private Task task;

	public Taskwork() {
	}

	public Taskwork(String comments, Timestamp creationDate, int hoursWorked, int remainingHours,
			int expectedRemainingHours, Allocation allocation, Task task) {
		this.comments = comments;
		this.creationDate = creationDate;
		this.hoursWorked = hoursWorked;
		this.remainingHours = remainingHours;
		this.expectedRemainingHours = expectedRemainingHours;
		this.allocation = allocation;
		this.task = task;
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

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationdate) {
		this.creationDate = creationdate;
	}

	public int getHoursWorked() {
		return this.hoursWorked;
	}

	public void setHoursWorked(int hoursworked) {
		this.hoursWorked = hoursworked;
	}

	public int getRemainingHours() {
		return this.remainingHours;
	}

	public void setRemainingHours(int remaininghours) {
		this.remainingHours = remaininghours;
	}

	public List<Attachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Attachment addAttachment(Attachment attachment) {
		getAttachments().add(attachment);
		attachment.setTaskwork(this);

		return attachment;
	}

	public Attachment removeAttachment(Attachment attachment) {
		getAttachments().remove(attachment);
		attachment.setTaskwork(null);

		return attachment;
	}

	public Allocation getAllocation() {
		return this.allocation;
	}

	public void setAllocation(Allocation allocation) {
		this.allocation = allocation;
	}

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public int getExpectedRemainingHours() {
		return expectedRemainingHours;
	}

	public void setExpectedRemainingHours(int expectedRemainingHours) {
		this.expectedRemainingHours = expectedRemainingHours;
	}

}