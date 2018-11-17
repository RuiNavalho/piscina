package pt.piscina.ds.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipology database table.
 * 
 */
/*
@Entity
@NamedQueries({ @NamedQuery(name="Tipology.findAll", query="SELECT t FROM Tipology t"),
	@NamedQuery(name="Tipology.findByName", query="SELECT t FROM Tipology t WHERE t.tipology=:tipology")
})
*/
public class Tipology implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String tipology;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="tipology")
	private List<Project> projects;

	public Tipology() {
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

	public String getTipology() {
		return this.tipology;
	}

	public void setTipology(String tipology) {
		this.tipology = tipology;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project addProject(Project project) {
		getProjects().add(project);
		project.setTipology(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setTipology(null);

		return project;
	}

}