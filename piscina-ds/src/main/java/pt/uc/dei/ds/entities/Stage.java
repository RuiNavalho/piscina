package pt.uc.dei.ds.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the stage database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name="Stage.findAll", query="SELECT s FROM Stage s"),
	@NamedQuery(name="Stage.findStageByName", query="SELECT s FROM Stage s WHERE s.stage=:stage"),
	@NamedQuery(name="Stage.findProjectsInStage", query="SELECT p from Project p join p.stage s where s.id=:id")
})
public class Stage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String stage;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="stage")
	private List<Project> projects;

	public Stage() {
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

	public String getStage() {
		return this.stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project addProject(Project project) {
		getProjects().add(project);
		project.setStage(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setStage(null);

		return project;
	}

}