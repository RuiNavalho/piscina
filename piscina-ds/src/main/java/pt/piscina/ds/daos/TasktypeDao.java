package pt.piscina.ds.daos;

import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;

import pt.piscina.ds.entities.Tasktype;

public class TasktypeDao extends AbstractDao<Tasktype>{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(TasktypeDao.class);

	public TasktypeDao() {
		super(Tasktype.class);
	}
	
	public Tasktype findTasktypeWithName(String tasktype) {
		try {
			TypedQuery<Tasktype> findTasktypeWithName = em.createNamedQuery("Tasktype.findTasktypeWithName", Tasktype.class);
			findTasktypeWithName.setParameter("taskType", tasktype);
			return findTasktypeWithName.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
}
