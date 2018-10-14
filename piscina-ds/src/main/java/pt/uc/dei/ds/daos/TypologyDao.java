package pt.uc.dei.ds.daos;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import pt.uc.dei.ds.entities.Tipology;

@Stateless
public class TypologyDao extends AbstractDao<Tipology>{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(TypologyDao.class);
	
	public TypologyDao() {
		super(Tipology.class);
	}
	
	public Tipology findTypologyByName(String tipology) {
		try {
			TypedQuery<Tipology> projectByUniqueTypology = em.createNamedQuery("Tipology.findByName", Tipology.class);
			projectByUniqueTypology.setParameter("tipology", tipology);
			return projectByUniqueTypology.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

}