package pt.piscina.ds.daos;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;

import pt.piscina.ds.entities.Project;
import pt.piscina.ds.entities.Projmanager;

@Stateless
public class ProjmanagerDao extends AbstractDao<Projmanager>{

	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger.getLogger(ProjmanagerDao.class);
	
	public ProjmanagerDao() {
		super(Projmanager.class);
	}

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void createProjManager(Projmanager projmanager) {
		try {
			em.persist(projmanager);
			em.flush();
		} catch (Exception e) {
			logger.error("Exception "+e);
		}
	}
	
	public Projmanager getActualProjmanager(Project project) {
		try {
			TypedQuery<Projmanager> actualProjmanager = em.createNamedQuery("Projmanager.findActualProjmanager", Projmanager.class);
			actualProjmanager.setParameter("projectId", project.getId());
			return actualProjmanager.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

}
