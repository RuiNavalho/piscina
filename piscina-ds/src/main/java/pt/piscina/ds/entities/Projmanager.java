package pt.piscina.ds.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the projmanager database table.
 * 
 */
/*
@Entity
@NamedQueries({ @NamedQuery(name="Projmanager.findAll", query="SELECT p FROM Projmanager p"),
	//TODO esta querie ja existe em Project entity.... REMOVER ou mudar a outra querie para AQUI?????
	@NamedQuery(name="Projmanager.findActualProjmanager", query="SELECT pm FROM Projmanager pm JOIN pm.project p WHERE p.id=:projectId AND pm.endDate=p.endDate"),
	@NamedQuery(name="Projmanager.findActualManager", query="SELECT u FROM User u JOIN u.projManagers pm JOIN pm.project p WHERE p.id=:projectId AND pm.endDate=p.endDate"),

})
*/
public class Projmanager implements Serializable {
	private static final long serialVersionUID = 1L; 

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private Timestamp beginDate;

	private Timestamp endDate;

	//bi-directional many-to-one association to Project
	@ManyToOne
	private Project project;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_manager")
	private User user;

	public Projmanager() {
	}

	public Projmanager(Timestamp beginDate, Timestamp endDate, Project project, User manager) {
		this.beginDate=beginDate;
		this.endDate=endDate;
		this.project=project;
		this.user=manager;
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

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp enddate) {
		this.endDate = enddate;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}