package pt.uc.dei.ds.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the business database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name="Business.findAll", query="SELECT b FROM Business b"),
	@NamedQuery(name="Business.findBusinessByName", query="SELECT b FROM Business b WHERE b.area=:area"),
	@NamedQuery(name="Business.findTotalNumberOfProjects", query="SELECT count(p) FROM Business b JOIN b.clients c JOIN c.projects p WHERE b.id=:id"),
	@NamedQuery(name="Business.findActiveNumberOfProjects", query="SELECT count(p) FROM Business b JOIN b.clients c JOIN c.projects p WHERE b.id=:id AND p.endDate>:now"),
	@NamedQuery(name="Business.findTotalBudget", query="SELECT SUM(p.budget) FROM Project p JOIN p.client c JOIN c.business b WHERE b.id=:id"),
	@NamedQuery(name="Business.findActiveTotalBudget", query="SELECT SUM(p.budget) FROM Project p JOIN p.client c JOIN c.business b WHERE b.id=:id AND p.endDate>:now"),
	@NamedQuery(name="Business.findAllBusinessProjectsByAscendingCreationDate", query="SELECT p FROM Project p JOIN p.client c JOIN c.business b WHERE b.area=:area ORDER BY p.creationDate"),

})
public class Business implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String area;

	//bi-directional many-to-one association to Client
	@OneToMany(mappedBy="business")
	private List<Client> clients;

	public Business() {
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

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public List<Client> getClients() {
		return this.clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public Client addClient(Client client) {
		getClients().add(client);
		client.setBusiness(this);

		return client;
	}

	public Client removeClient(Client client) {
		getClients().remove(client);
		client.setBusiness(null);

		return client;
	}

}