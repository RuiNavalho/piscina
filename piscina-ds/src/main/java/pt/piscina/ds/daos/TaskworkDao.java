package pt.piscina.ds.daos;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import pt.piscina.ds.entities.Attachment;
import pt.piscina.ds.entities.Taskwork;

@Stateless
public class TaskworkDao extends AbstractDao<Taskwork>{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(TaskworkDao.class);

	public TaskworkDao() {
		super(Taskwork.class);
	}
	
	public List<Taskwork> findWorkerTaskworksBetweenDates(String email, Date begin, Date end) {
		try {
			TypedQuery<Taskwork> list = em.createNamedQuery("Taskwork.findWorkerTaskworksBetweenDates", Taskwork.class);
			list.setParameter("email", email);
			list.setParameter("begin", begin);
			list.setParameter("end", end);
			return list.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Taskwork> findMyWorkingHours(String email) {
		try {
			TypedQuery<Taskwork> list = em.createNamedQuery("Taskwork.findMyWorkingHours", Taskwork.class);
			list.setParameter("email", email);
			return list.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Taskwork> findWorkingHoursInTask(int id) {
		try {
			TypedQuery<Taskwork> list = em.createNamedQuery("Taskwork.findWorkingHoursInTask", Taskwork.class);
			list.setParameter("taskId", id);
			return list.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
	
	public List<Taskwork> findMyWorkingHoursInTask(int id, String email) {
		try {
			TypedQuery<Taskwork> list = em.createNamedQuery("Taskwork.findMyWorkingHoursInTask", Taskwork.class);
			list.setParameter("taskId", id);
			list.setParameter("email", email);
			return list.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Taskwork> findWorkingHoursInProject(String projectId) {
		try {
			TypedQuery<Taskwork> list = em.createNamedQuery("Taskwork.findWorkingHoursInProject", Taskwork.class);
			list.setParameter("projectId", projectId);
			return list.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Taskwork> findMyWorkingHoursInProject(String projectId, String email) {
		try {
			TypedQuery<Taskwork> list = em.createNamedQuery("Taskwork.findMyWorkingHoursInProject", Taskwork.class);
			list.setParameter("projectId", projectId);
			list.setParameter("email", email);
			return list.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Attachment> findAttachments(Taskwork tw) {
		try {
			TypedQuery<Attachment> list = em.createNamedQuery("Taskwork.findAttachments", Attachment.class);
			list.setParameter("id", tw.getId());
			return list.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}



}
