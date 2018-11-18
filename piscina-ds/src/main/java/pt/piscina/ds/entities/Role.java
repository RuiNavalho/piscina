package pt.piscina.ds.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the role database table.
 * 
 */

@Entity
@NamedQueries({ @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
		@NamedQuery(name = "Role.findUsersWithRole", query = "SELECT u from User u join u.roles r where r.id=:id"),
		@NamedQuery(name = "Role.findRoleWithName", query = "SELECT r FROM Role r WHERE r.role=:role") })
@Table(name = "role", catalog = "gestgym")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String role;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy = "roles")
	private List<User> users;

	public Role() {
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

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}