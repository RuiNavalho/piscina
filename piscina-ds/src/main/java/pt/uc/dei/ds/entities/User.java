package pt.uc.dei.ds.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
	@NamedQuery(name="User.loginUser", query="SELECT u FROM User u WHERE u.email=:email AND u.passw=:password"),
	@NamedQuery(name="User.findUserRoles", query="SELECT r from Role r join r.users u where u.id=:id"),
	@NamedQuery(name="User.findUserAllocations", query="SELECT a from Allocation a JOIN a.user u WHERE u.id=:id"),
	@NamedQuery(name="User.findUserProjects", query="SELECT p from Project p join p.user u where u.id=:id"),
	@NamedQuery(name="User.findUserManagedProjects", query="SELECT pm from Projmanager pm join pm.user u where u.id=:id"),
	@NamedQuery(name="User.findUserTasks", query="SELECT t from Task t join t.user u where u.id=:id"),
	@NamedQuery(name="User.findUserSkills", query="SELECT s from Skill s join s.users u where u.id=:id"),
	@NamedQuery(name="User.findUserByEmail", query="SELECT u FROM User u WHERE u.email=:email"),
	@NamedQuery(name="User.findUserAllocationsBetweenDates", query="SELECT a from Allocation a join a.user u WHERE u.id=:id AND a.endDate>=a.beginDate AND ((a.endDate>=:begin AND a.endDate<=:end) OR (a.beginDate>=:begin AND a.beginDate<=:end) OR (a.beginDate<=:begin AND a.endDate>=:end))"),
	//TODO remover a findAllToManageProject e passar a usar findUsersWithAtLeastOneOfTwoRoles
	@NamedQuery(name="User.findAllToManageProject", query="SELECT DISTINCT u FROM User u join u.roles r WHERE r.id=:id1 OR r.id=:id2"),
	@NamedQuery(name="User.findUsersWithAtLeastOneOfTwoRoles", query="SELECT DISTINCT u FROM User u join u.roles r WHERE r.role=:roleName1 OR r.role=:roleName2"),
	
	
	@NamedQuery(name="User.findUsersWithRole", query="SELECT u FROM User u join u.roles r WHERE r.role LIKE :roleName"),
	@NamedQuery(name="User.findAllRegisteredAfterDate", query="SELECT u FROM User u WHERE u.registryDate<=:date"),
	@NamedQuery(name="User.findAllWithNameOrEmailLIKE", query="SELECT u FROM User u WHERE u.email LIKE :name OR u.fullName LIKE :name"),
	@NamedQuery(name="User.findAllWithSkillLIKE", query="SELECT u FROM User u join u.skills s WHERE s.skill=:skill"),
	@NamedQuery(name="User.findAllActive", query="SELECT u FROM User u WHERE u.active=true"),
	@NamedQuery(name="User.findAllInactive", query="SELECT u FROM User u WHERE u.active=false"),
	
	@NamedQuery(name="User.findUserAllocationsActiveInDate", query="SELECT a from Allocation a JOIN a.user u WHERE u.email=:email AND a.endDate>=a.beginDate AND a.endDate>=:date AND a.beginDate<=:date"),
	@NamedQuery(name="User.findUserAllocationsEndingInDate", query="SELECT a from Allocation a JOIN a.user u WHERE u.email=:email AND a.endDate>=a.beginDate AND a.endDate=:date"),
	
})

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private boolean active;

	private String email;

	private String fullName;

	private String passw;

	@Lob
	private byte[] photo;

	private Timestamp registryDate;

	private String salt;
	
	private String tokenPasswRecover;
	
	private String tokenActivation;

	//bi-directional many-to-one association to Allocation
	@OneToMany(mappedBy="user")
	private List<Allocation> allocations;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="user")
	private List<Project> projects;

	//bi-directional many-to-one association to Projmanager
	@OneToMany(mappedBy="user")
	private List<Projmanager> projManagers;

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="user")
	private List<Task> tasks;

	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(
		name="user_role"
		, joinColumns={
			@JoinColumn(name="user_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="role_id")
			}
		)
	private List<Role> roles;

	//bi-directional many-to-many association to Skill
	@ManyToMany
	@JoinTable(
		name="user_skill"
		, joinColumns={
			@JoinColumn(name="user_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="skill_id")
			}
		)
	private List<Skill> skills;

	public User() {
	}
	
	public User(boolean active, String email, String fullName, String passw, byte[] photo, Timestamp registryDate,
			String salt, List<Role> roles) {
		this.active = active;
		this.email = email;
		this.fullName = fullName;
		this.passw = passw;
		this.photo = photo;
		this.registryDate = registryDate;
		this.salt = salt;
		this.roles = roles;
	}
	
	public User(boolean active, String email, String fullName, String passw, byte[] photo,
			Timestamp registryDate, String salt, List<Allocation> allocations, List<Project> projects,
			List<Projmanager> projManagers, List<Task> tasks, List<Role> roles, List<Skill> skills) {
		this.active = active;
		this.email = email;
		this.fullName = fullName;
		this.passw = passw;
		this.photo = photo;
		this.registryDate = registryDate;
		this.salt = salt;
		this.allocations = allocations;
		this.projects = projects;
		this.projManagers = projManagers;
		this.tasks = tasks;
		this.roles = roles;
		this.skills = skills;
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

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullname) {
		this.fullName = fullname;
	}

	public String getPassw() {
		return this.passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}

	public byte[] getPhoto() {
		return this.photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public Timestamp getRegistryDate() {
		return this.registryDate;
	}

	public void setRegistryDate(Timestamp registrydate) {
		this.registryDate = registrydate;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public List<Allocation> getAllocations() {
		return this.allocations;
	}

	public void setAllocations(List<Allocation> allocations) {
		this.allocations = allocations;
	}

	public Allocation addAllocation(Allocation allocation) {
		getAllocations().add(allocation);
		allocation.setUser(this);

		return allocation;
	}

	public Allocation removeAllocation(Allocation allocation) {
		getAllocations().remove(allocation);
		allocation.setUser(null);

		return allocation;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project addProject(Project project) {
		getProjects().add(project);
		project.setUser(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setUser(null);

		return project;
	}

	public List<Projmanager> getProjManagers() {
		return this.projManagers;
	}

	public void setProjManagers(List<Projmanager> projmanagers) {
		this.projManagers = projmanagers;
	}

	public Projmanager addProjmanager(Projmanager projmanager) {
		getProjManagers().add(projmanager);
		projmanager.setUser(this);

		return projmanager;
	}

	public Projmanager removeProjmanager(Projmanager projmanager) {
		getProjManagers().remove(projmanager);
		projmanager.setUser(null);

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
		task.setUser(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setUser(null);

		return task;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Skill> getSkills() {
		return this.skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public String getTokenPasswRecover() {
		return tokenPasswRecover;
	}

	public void setTokenPasswRecover(String tokenPasswRecover) {
		this.tokenPasswRecover = tokenPasswRecover;
	}

	public String getTokenActivation() {
		return tokenActivation;
	}

	public void setTokenActivation(String tokenActivation) {
		this.tokenActivation = tokenActivation;
	}

}