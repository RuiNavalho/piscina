package pt.piscina.ds.daos;

import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;

import pt.piscina.ds.entities.Allocation;
import pt.piscina.ds.entities.Task;
import pt.piscina.ds.entities.Taskwork;
import pt.piscina.ds.entities.User;

@Stateless
public class TaskDao extends AbstractDao<Task> {

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(TaskDao.class);

	public TaskDao() {
		super(Task.class);
	}
	
	public List<Taskwork> getTaskTaskwork(Task task) {
		try {
			TypedQuery<Taskwork> taskTaskwork = em.createNamedQuery("Task.findTaskWorks", Taskwork.class);
			taskTaskwork.setParameter("id", task.getId());
			return taskTaskwork.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
	
	public List<Taskwork> getTaskTaskworkByAscendingDate(Task task) {
		try {
			TypedQuery<Taskwork> taskTaskwork = em.createNamedQuery("Task.findTaskTaskworkByAscendingDate", Taskwork.class);
			taskTaskwork.setParameter("id", task.getId());
			return taskTaskwork.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
	
	public Long findWorkedHours(Task task) {
		try {
			TypedQuery<Long> workedHours = em.createNamedQuery("Task.findWorkedHours", Long.class);
			workedHours.setParameter("id", task.getId());
			return workedHours.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
	
	//Task.findLastTaskAllocation
	
	public Allocation findLastTaskAllocation(Task task) {
		try {
			TypedQuery<Allocation> allocs = em.createNamedQuery("Task.findLastTaskAllocation", Allocation.class);
			allocs.setParameter("taskId", task.getId());
			if (allocs.getResultList().size()==1) {
				return allocs.getResultList().get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
	
	public Allocation findTaskAllocationInDate(Task task, Timestamp date) {
		try {
			TypedQuery<Allocation> allocs = em.createNamedQuery("Task.findTaskAllocationInDate", Allocation.class);
			allocs.setParameter("taskId", task.getId());
			allocs.setParameter("date", date);
			if (allocs.getResultList().size()==1) {
				return allocs.getResultList().get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
	public List<Allocation> findTaskAllocations(Task task) {
		try {
			TypedQuery<Allocation> taskTaskwork = em.createNamedQuery("Task.findAllocations", Allocation.class);
			taskTaskwork.setParameter("id", task.getId());
			return taskTaskwork.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
	
	public List<Allocation> findAllocationsBetweenDates(Task task, Timestamp begin, Timestamp end) {
		try {
			TypedQuery<Allocation> taskTaskwork = em.createNamedQuery("Task.findAllocationsBetweenDates", Allocation.class);
			taskTaskwork.setParameter("id", task.getId());
			taskTaskwork.setParameter("begin", begin);
			taskTaskwork.setParameter("end", end);
			return taskTaskwork.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Task> getTaskPrecedentTasks(Task task) {
		try {
			TypedQuery<Task> taskPrecedentTasks = em.createNamedQuery("Task.findPrecedentTasks", Task.class);
			taskPrecedentTasks.setParameter("id", task.getId());
			return taskPrecedentTasks.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
	
	public List<Task> getTaskNextTasks(Task task) {
		try {
			TypedQuery<Task> taskPrecedentTasks = em.createNamedQuery("Task.findNextTasks", Task.class);
			taskPrecedentTasks.setParameter("id", task.getId());
			return taskPrecedentTasks.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}



	public List<User> getUsersToTask(Task task) {
		try {
			TypedQuery<User> taskUsersToWork = em.createNamedQuery("Task.findUsersToTask", User.class);
			taskUsersToWork.setParameter("id", task.getId());
			return  taskUsersToWork.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}


	public boolean allocateUserToTask(Task task) {
		try {
			em.merge(task);
			return true;
		} catch (Exception e) {
			logger.error("Exception "+e);
			return false;
		}
	}

	public List<Task> findMyWorkingTasks(String email) {
		try {
			TypedQuery<Task> myWorkingTasks = em.createNamedQuery("Task.findMyWorkingTasks", Task.class);
			myWorkingTasks.setParameter("email", email);
			return  myWorkingTasks.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Task> findPrecendentTasksList(Task task) {
		try {
			TypedQuery<Task> tasks = em.createNamedQuery("Task.findPrecedentTasks", Task.class);
			tasks.setParameter("id", task.getId());
			return  tasks.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Task> findPossiblePrecendentTasks(Task task) {
		try {
			TypedQuery<Task> tasks = em.createNamedQuery("Task.findPossiblePrecendentTasks", Task.class);
			tasks.setParameter("projId", task.getProject().getId());
			tasks.setParameter("beginDate", task.getBeginDate());
			tasks.setParameter("id", task.getId());
			return  tasks.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

}
