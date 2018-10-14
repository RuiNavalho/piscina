package pt.uc.dei.ds.daos;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import pt.uc.dei.ds.entities.Client;
import pt.uc.dei.ds.entities.Project;

@Stateless
public class ClientDao extends AbstractDao<Client>{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(ClientDao.class);

	public ClientDao() {
		super(Client.class);
	}

	public Boolean checkClientNameExistence(String clientCompanyName) {
		try {
			TypedQuery<Client> clientByName = em.createNamedQuery("Client.findClientByName", Client.class);
			clientByName.setParameter("company", clientCompanyName);
			if(clientByName.getResultList().size()!=0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public Client findClientByName(String clientCompanyName) {
		try {
			TypedQuery<Client> clientByName = em.createNamedQuery("Client.findClientByName", Client.class);
			clientByName.setParameter("company", clientCompanyName);
			return clientByName.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Client> getAllClients() {
		try {
			TypedQuery<Client> all = em.createNamedQuery("Client.findAll", Client.class);
			return all.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Client> getAllClientsInBusiness(String businessArea) {
		try {
			TypedQuery<Client> all = em.createNamedQuery("Client.findAllInBusiness", Client.class);
			all.setParameter("businessArea", businessArea);
			return all.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Project> findAllClientProjectsByAscendingCreationDate(String company) {
		try {
			TypedQuery<Project> all = em.createNamedQuery("Client.findAllClientProjectsByAscendingCreationDate", Project.class);
			all.setParameter("company", company);
			return all.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public boolean createNewClient(Client client) {
		try {
			em.persist(client);
			return true;
		} catch (Exception e) {
			logger.error("Exception "+e);
			return false;
		}
	}

	public Long countProjectsFromClient(Client client) {
		try {
			TypedQuery<Long> count = em.createNamedQuery("Client.countProjectsFromClient", Long.class);
			count.setParameter("id", client.getId());
			return count.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public Double getClientTotalBudget(Client client) {
		try {
			TypedQuery<Double> budget = em.createNamedQuery("Client.sumTotalBudget", Double.class);
			budget.setParameter("id", client.getId());
			if (budget.getSingleResult()==null) {
				return 0.0;
			} else {
				return budget.getSingleResult();
			}	
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}





}
