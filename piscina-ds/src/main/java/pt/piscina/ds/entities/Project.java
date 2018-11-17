package pt.piscina.ds.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the project database table.
 * 
 */
/*
@Entity
@NamedQueries({ @NamedQuery(name="Project.findAll", query="SELECT p FROM Project p"),
	@NamedQuery(name="Project.findMyWorkingProjects", query="SELECT DISTINCT p FROM Project p JOIN p.tasks t JOIN t.allocations a JOIN a.user u where u.email=:email and a.endDate>=a.beginDate"),
	@NamedQuery(name="Project.findMyManagedProjects", query="SELECT DISTINCT p FROM Project p JOIN p.projManagers pm JOIN pm.user u WHERE u.email=:email AND pm.endDate=p.endDate"),
	@NamedQuery(name="Project.findClientProjects", query="SELECT p FROM Project p JOIN p.client c WHERE c.company=:company"),	
	@NamedQuery(name="Project.findManagers", query="SELECT u from User u join u.projManagers pm join pm.project p where p.id=:id"),
	@NamedQuery(name="Project.findActualManager", query="SELECT u from User u join u.projManagers pm join pm.project p where p.id=:id AND pm.endDate=p.endDate"),
	@NamedQuery(name="Project.findTasks", query="SELECT t from Task t join t.project p where p.id=:id"),
	@NamedQuery(name="Project.findAllocations", query="SELECT A from Allocation a JOIN a.task t JOIN t.project p WHERE p.id=:id AND a.beginDate<=a.endDate"),
	@NamedQuery(name="Project.findEstimatedHoursToFinishProject", query="SELECT SUM(t.remainingHours) from Task t join t.project p where p.id=:id"),
	
	@NamedQuery(name="Project.findTotalHoursWorkedInProject", query="SELECT SUM(tw.hoursWorked) from Taskwork tw JOIN tw.task t JOIN t.project p WHERE p.id=:id"),
	@NamedQuery(name="Project.findTotalHoursWorkedInProjectInDate", query="SELECT SUM(tw.hoursWorked) from Taskwork tw JOIN tw.task t JOIN t.project p WHERE p.id=:id AND tw.creationDate<=:date"),
	
	@NamedQuery(name="Project.findTotalHoursWorkedInEndedTasks", query="SELECT SUM(tw.hoursWorked) from Taskwork tw JOIN tw.task t JOIN t.project p WHERE p.id=:id AND t.endDate<:now"),
	@NamedQuery(name="Project.findHoursRemainingInEndedTasks", query="SELECT SUM(t.remainingHours) FROM Task t JOIN t.project p WHERE p.id=:id AND t.endDate<:now"),
	@NamedQuery(name="Project.findTotalHoursWorkedInCurrentTasks", query="SELECT SUM(tw.hoursWorked) from Taskwork tw JOIN tw.task t JOIN t.project p WHERE p.id=:id AND t.beginDate>=:now AND t.endDate<=:now"),
	@NamedQuery(name="Project.findCurrentTasks", query="SELECT t FROM Task t JOIN t.project p WHERE p.id=:id AND t.beginDate<=:now AND t.endDate>=:now"),
	
	//@NamedQuery(name="Project.findCurrentTasks", query="SELECT t FROM Task t JOIN t.project p WHERE p.id=:id AND t.beginDate>=:now AND t.endDate<=:now"),
	@NamedQuery(name="Project.calculateAllProjectsTotalBudget", query="SELECT SUM(p.budget) FROM Project p"),
	
	@NamedQuery(name="Project.findInitialDurationEstimative", query="SELECT SUM(t.initialDurationHoursEstimate) from Task t join t.project p where p.id=:id"),
	@NamedQuery(name="Project.findActualCost", query="SELECT SUM(tw.hoursWorked) from Taskwork tw join tw.allocation a JOIN a.task t join t.project p where p.id=:id"),
	@NamedQuery(name="Project.findProjectCostInDate", query="SELECT SUM(tw.hoursWorked) from Taskwork tw join tw.allocation a JOIN a.task t join t.project p where p.id=:id AND tw.creationDate<=:date"),

	@NamedQuery(name="Project.ProjectByUniqueIdproject", query="SELECT p FROM Project p where p.idProject=:idProject"),
	@NamedQuery(name="Project.findActualProjmanager", query="SELECT pm FROM Projmanager pm join pm.project p WHERE pm.endDate=p.endDate AND p.id=:id"),
	@NamedQuery(name="Project.findAllOpenProjects", query="SELECT p from Project p JOIN p.stage s WHERE s.stage<>'Closed'"),
	@NamedQuery(name="Project.findAllClosedProjects", query="SELECT p from Project p JOIN p.stage s WHERE s.stage='Closed'"),
	
	@NamedQuery(name="Project.findAllActiveInDate", query="SELECT p from Project p WHERE p.beginDate<=:date AND p.endDate>=:date"),
	@NamedQuery(name="Project.findAllWithNameLIKE", query="SELECT DISTINCT p from Project p WHERE p.idProject LIKE :name OR p.title LIKE :name"),
	@NamedQuery(name="Project.findAllWithClientLIKE", query="SELECT DISTINCT p from Project p JOIN p.client c WHERE c.company LIKE :client"),
	@NamedQuery(name="Project.findAllWithStageLIKE", query="SELECT DISTINCT p from Project p JOIN p.stage s WHERE s.stage=:stage"),
	@NamedQuery(name="Project.findAllWithBusinessLIKE", query="SELECT DISTINCT p from Project p JOIN p.client c JOIN c.business b WHERE b.area=:business"),

	
	@NamedQuery(name="Project.findManagedActiveInDate", query="SELECT DISTINCT p from Project p JOIN p.projManagers pm JOIN pm.user u WHERE p.endDate=pm.endDate AND u.email=:email AND p.beginDate<=:date AND p.endDate>=:date"),
	@NamedQuery(name="Project.findWorkingActiveInDate", query="SELECT DISTINCT p from Project p JOIN p.tasks t JOIN t.allocations a JOIN a.user u WHERE a.beginDate<=a.endDate AND u.email=:email"),
	
	
	@NamedQuery(name="Project.findManagedWithNameLIKE", query="SELECT DISTINCT p from Project p JOIN p.projManagers pm JOIN pm.user u WHERE p.endDate=pm.endDate AND u.email=:email AND (p.idProject LIKE :name OR p.title LIKE :name)"),
	@NamedQuery(name="Project.findWorkingWithNameLIKE", query="SELECT DISTINCT p from Project p JOIN p.tasks t JOIN t.allocations a JOIN a.user u WHERE u.email=:email AND a.beginDate<=a.endDate AND (p.idProject LIKE :name OR p.title LIKE :name)"),
	
	@NamedQuery(name="Project.findManagedWithClientLIKE", query="SELECT DISTINCT p from Project p JOIN p.projManagers pm JOIN pm.user u JOIN p.client c WHERE p.endDate=pm.endDate AND u.email=:email AND (c.company=:client)"),
	@NamedQuery(name="Project.findWorkingWithClientLIKE", query="SELECT DISTINCT p from Project p JOIN p.tasks t JOIN t.allocations a JOIN a.user u JOIN p.client c WHERE u.email=:email AND a.beginDate<=a.endDate AND (c.company=:client)"),
	
	@NamedQuery(name="Project.findManagedWithStageLIKE", query="SELECT DISTINCT p from Project p JOIN p.projManagers pm JOIN pm.user u JOIN p.stage s WHERE p.endDate=pm.endDate AND u.email=:email AND (s.stage=:stage)"),
	@NamedQuery(name="Project.findWorkingWithStageLIKE", query="SELECT DISTINCT p from Project p JOIN p.tasks t JOIN t.allocations a JOIN a.user u JOIN p.stage s  WHERE u.email=:email AND a.beginDate<=a.endDate AND (s.stage=:stage)"),
	
	@NamedQuery(name="Project.findManagedWithBusinessLIKE", query="SELECT DISTINCT p from Project p JOIN p.projManagers pm JOIN pm.user u JOIN p.client c JOIN c.business b WHERE p.endDate=pm.endDate AND u.email=:email AND (b.area LIKE :business)"),
	@NamedQuery(name="Project.findWorkingWithBusinessLIKE", query="SELECT DISTINCT p from Project p JOIN p.tasks t JOIN t.allocations a JOIN a.user u JOIN p.client c JOIN c.business b WHERE u.email=:email AND a.beginDate<=a.endDate AND (b.area LIKE :business)"),
	
	@NamedQuery(name="Project.findManagedOpenProjects", query="SELECT DISTINCT p from Project p JOIN p.projManagers pm JOIN pm.user u JOIN p.stage s WHERE p.endDate=pm.endDate AND u.email=:email AND (s.stage<>'Closed')"),
	@NamedQuery(name="Project.findWorkingOpenProjects", query="SELECT DISTINCT p from Project p JOIN p.tasks t JOIN t.allocations a JOIN a.user u JOIN p.stage s WHERE u.email=:email AND a.beginDate<=a.endDate AND (s.stage<>'Closed')"),
	
	@NamedQuery(name="Project.findManagedClosedProjects", query="SELECT DISTINCT p from Project p JOIN p.projManagers pm JOIN pm.user u JOIN p.stage s WHERE p.endDate=pm.endDate AND u.email=:email AND (s.stage='Closed')"),
	@NamedQuery(name="Project.findWorkingClosedProjects", query="SELECT DISTINCT p from Project p JOIN p.tasks t JOIN t.allocations a JOIN a.user u JOIN p.stage s WHERE u.email=:email AND a.beginDate<=a.endDate AND (s.stage='Closed')"),
	
	@NamedQuery(name="Project.findMyWorkingProjectsBetweenDates", query="SELECT DISTINCT p from Project p JOIN p.tasks t JOIN t.allocations a JOIN a.user u WHERE (a.endDate>=a.beginDate) AND u.email=:email AND ( (p.beginDate>=:begin AND p.beginDate<=:end) OR (p.endDate>=:begin AND p.endDate<=:end) OR (p.beginDate<=:begin AND p.endDate>=:end) )"),
	@NamedQuery(name="Project.findMyManagedProjectsBetweenDates", query="SELECT DISTINCT p from Project p JOIN p.projManagers pm JOIN pm.user u WHERE (p.endDate=pm.endDate) AND u.email=:email AND ( (p.beginDate>=:begin AND p.beginDate<=:end) OR (p.endDate>=:begin AND p.endDate<=:end) OR (p.beginDate<=:begin AND p.endDate>=:end) )"),
	
	@NamedQuery(name="Project.findProjectTaskworksByAscendingDate", query="SELECT DISTINCT tw FROM Taskwork tw JOIN tw.task t JOIN t.project p WHERE p.id=:id ORDER BY tw.creationDate"),
	
	
	@NamedQuery(name="Project.findProjectFutureTasks", query="SELECT t FROM Task t JOIN t.project p WHERE p.id=:id AND t.beginDate>:today"),
	@NamedQuery(name="Project.findProjectPresentTasks", query="SELECT t FROM Task t JOIN t.project p WHERE p.id=:id AND t.beginDate<=:today AND t.endDate>=:today"),
	@NamedQuery(name="Project.findProjectsForNewTask", query="SELECT p FROM Project p WHERE p.endDate>:today"),
	
})
*/
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private Timestamp beginDate;

	private float budget;

	private Timestamp creationDate;

	private String description;

	private Timestamp endDate;

	private String idProject;

	private String title;

	//bi-directional many-to-one association to Client
	@ManyToOne
	private Client client;

	//bi-directional many-to-one association to Stage
	@ManyToOne
	private Stage stage;

	//bi-directional many-to-one association to Tipology
	@ManyToOne
	private Tipology tipology;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_creator")
	private User user;

	//bi-directional many-to-one association to Projmanager
	@OneToMany(mappedBy="project")
	private List<Projmanager> projManagers;

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="project")
	private List<Task> tasks;

	public Project() {
	}
	
	public Project(String title, String idProject, String description, Client client, Timestamp beginDate, Timestamp endDate, Float budget, Stage stage, User creator, Tipology tipology, Timestamp now) {
		this.title = title;
		this.idProject = idProject;
		this.description = description;
		this.client = client;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.budget = budget;
		this.stage = stage;
		this.user = creator;
		this.tipology = tipology;
		this.creationDate= now;
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

	public float getBudget() {
		return this.budget;
	}

	public void setBudget(float budget) {
		this.budget = budget;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationdate) {
		this.creationDate = creationdate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp enddate) {
		this.endDate = enddate;
	}

	public String getIdProject() {
		return this.idProject;
	}

	public void setIdProject(String idproject) {
		this.idProject = idproject;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Stage getStage() {
		return this.stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Tipology getTipology() {
		return this.tipology;
	}

	public void setTipology(Tipology tipology) {
		this.tipology = tipology;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Projmanager> getProjManagers() {
		return this.projManagers;
	}

	public void setProjManagers(List<Projmanager> projmanagers) {
		this.projManagers = projmanagers;
	}

	public Projmanager addProjmanager(Projmanager projmanager) {
		getProjManagers().add(projmanager);
		projmanager.setProject(this);

		return projmanager;
	}

	public Projmanager removeProjmanager(Projmanager projmanager) {
		getProjManagers().remove(projmanager);
		projmanager.setProject(null);

		return projmanager;
	}

	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Task addTask(Task task) {
		getTasks().add(task);
		task.setProject(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setProject(null);

		return task;
	}

}