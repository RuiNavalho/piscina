package pt.piscina.ds.daos;

import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;

import pt.piscina.ds.entities.Taskstage;

public class TaskstageDao extends AbstractDao<Taskstage>{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(TaskstageDao.class);

	public TaskstageDao() {
		super(Taskstage.class);
	}
	
	public Taskstage findTaskstageWithName(String taskstage) {
		try {
			TypedQuery<Taskstage> findTaskstageWithName = em.createNamedQuery("Taskstage.findTaskstageWithName", Taskstage.class);
			findTaskstageWithName.setParameter("taskStage", taskstage);
			return findTaskstageWithName.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
}
