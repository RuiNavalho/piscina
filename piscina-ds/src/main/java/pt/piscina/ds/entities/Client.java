package pt.piscina.ds.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the client database table.
 * 
 */
/*
@Entity
@NamedQueries({ @NamedQuery(name="Client.findAll", query="SELECT c FROM Client c"),
	@NamedQuery(name="Client.findClientByName", query="SELECT c FROM Client c WHERE c.company=:company"),
	@NamedQuery(name="Client.countProjectsFromClient", query="SELECT count(p) FROM Client c join c.projects p WHERE c.id=:id"),
	@NamedQuery(name="Client.sumTotalBudget", query="SELECT SUM(p.budget) FROM Project p join p.client c WHERE c.id=:id"),
	@NamedQuery(name="Client.findAllInBusiness", query="SELECT c FROM Client c JOIN c.business b WHERE b.area=:businessArea"),
	@NamedQuery(name="Client.findAllClientProjectsByAscendingCreationDate", query="SELECT p FROM Project p JOIN p.client c WHERE c.company=:company ORDER BY p.creationDate"),


})
*/
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String address;

	private String company;

	private String contact;

	private String phone;

	//bi-directional many-to-one association to Business
	@ManyToOne
	private Business business;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="client")
	private List<Project> projects;

	public Client() {
	}
	
	public Client(String address, String company, String contact, String phone, Business business) {
		this.address = address;
		this.company = company;
		this.contact = contact;
		this.phone = phone;
		this.business = business;
		//TODO e mesmo necessario a lista VAZIA de projetos abaixo???
		List<Project> projects = new ArrayList<Project>();
		this.projects=projects;
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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Business getBusiness() {
		return this.business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project addProject(Project project) {
		getProjects().add(project);
		project.setClient(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setClient(null);

		return project;
	}

}