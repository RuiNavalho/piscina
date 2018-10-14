package pt.uc.dei.ds.daos;

import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import pt.uc.dei.ds.entities.Allocation;
import pt.uc.dei.ds.entities.Task;
import pt.uc.dei.ds.entities.Taskwork;
import pt.uc.dei.ds.entities.User;
import pt.uc.dei.ds.util.TimeCalc;

@Stateless
public class AllocationDao extends AbstractDao<Allocation>{

	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger.getLogger(AllocationDao.class);

	public AllocationDao() {
		super(Allocation.class);
	}

	public Allocation findByTaskIdAndEndDate(Task task) {
		try {
			TypedQuery<Allocation> taskIdAndEndDate = em.createNamedQuery("Allocation.findByTaskIdAndEndDate", Allocation.class);
			taskIdAndEndDate.setParameter("taskId", task.getId());
			taskIdAndEndDate.setParameter("endDate", task.getEndDate());
			return taskIdAndEndDate.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Taskwork> findAllTaskworks(Allocation alloc) {
		try {
			TypedQuery<Taskwork> list = em.createNamedQuery("Allocation.findAllTaskworks", Taskwork.class);
			list.setParameter("id", alloc.getId());
			return list.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Allocation> findActiveUserAllocations(User user) {
		try {
			Timestamp now = TimeCalc.todayMidnight();
			TypedQuery<Allocation> activeAllocs = em.createNamedQuery("Allocation.findActiveUserAllocations", Allocation.class);
			activeAllocs.setParameter("userId", user.getId());
			activeAllocs.setParameter("now", now);
			return activeAllocs.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Allocation> findAvailableAllocationsToRegisterWork(User user, int stageIdClosed, int taskIdBlocked) {
		try {
			Timestamp now =TimeCalc.todayMidnight();
			TypedQuery<Allocation> allocs = em.createNamedQuery("Allocation.findAvailableAllocationsToRegisterWork", Allocation.class);
			allocs.setParameter("userId", user.getId());
			allocs.setParameter("now", now);
			allocs.setParameter("stageIdClosed", stageIdClosed);
			allocs.setParameter("taskIdBlocked", taskIdBlocked);
			return allocs.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public Long findWorkedHoursInAllocation(Allocation allocation) {
		try {
			TypedQuery<Long> workingHours = em.createNamedQuery("Allocation.findWorkedHoursInAllocation", Long.class);
			workingHours.setParameter("id", allocation.getId());
			return workingHours.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Allocation> findMyAllocations(String email, String type) {
		try {
			Timestamp now = TimeCalc.todayMidnight();
			TypedQuery<Allocation> allocs=null;
			switch (type) {
			case "all":
				allocs = em.createNamedQuery("Allocation.findAllMyAllocations", Allocation.class);
				allocs.setParameter("email", email);
				break;
			case "present":
				allocs = em.createNamedQuery("Allocation.findMyPresentAllocations", Allocation.class);
				allocs.setParameter("email", email);
				allocs.setParameter("now", now);
				break;
			case "future":
				allocs = em.createNamedQuery("Allocation.findMyFutureAllocations", Allocation.class);
				allocs.setParameter("email", email);
				allocs.setParameter("now", now);
				break;
			default:
				break;
			}
			return allocs.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Allocation> findMyAllocationsBetweenDates(String email, Timestamp begin, Timestamp end) {
		try {
			TypedQuery<Allocation> allocs = em.createNamedQuery("Allocation.findMyAllocationsBetweenDates", Allocation.class);
			allocs.setParameter("email", email);
			allocs.setParameter("begin", begin);
			allocs.setParameter("end", end);
			return allocs.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
}
