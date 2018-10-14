package pt.uc.dei.ds.daos;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import pt.uc.dei.ds.entities.Allocation;
import pt.uc.dei.ds.entities.Client;
import pt.uc.dei.ds.entities.Project;
import pt.uc.dei.ds.entities.Projmanager;
import pt.uc.dei.ds.entities.Task;
import pt.uc.dei.ds.entities.Taskwork;
import pt.uc.dei.ds.entities.User;
import pt.uc.dei.ds.util.TimeCalc;

@Stateless
public class ProjectDao extends AbstractDao<Project>{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(ProjectDao.class);

	public ProjectDao() {
		super(Project.class);
	}

	public Double calculateProjectsTotalBudget() {
		try {
			TypedQuery<Double> budget = em.createNamedQuery("Project.calculateAllProjectsTotalBudget", Double.class);
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

	public List<Project> getMyWorkingProjects(String email) {
		try {
			TypedQuery<Project> allProjects = em.createNamedQuery("Project.findMyWorkingProjects", Project.class);
			allProjects.setParameter("email", email);
			return allProjects.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Project> findProjectsForNewTask() {
		try {
			Timestamp today = TimeCalc.todayMidnight();
			TypedQuery<Project> projs = em.createNamedQuery("Project.findProjectsForNewTask", Project.class);
			projs.setParameter("today", today);
			return projs.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Taskwork> findProjectTaskworksByAscendingDate(Project project) {
		try {
			TypedQuery<Taskwork> tws = em.createNamedQuery("Project.findProjectTaskworksByAscendingDate", Taskwork.class);
			tws.setParameter("id", project.getId());
			return tws.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Project> getMyWorkingProjectsBetweenDates(String email, Timestamp begin, Timestamp end) {
		try {
			TypedQuery<Project> allProjects = em.createNamedQuery("Project.findMyWorkingProjectsBetweenDates", Project.class);
			allProjects.setParameter("email", email);
			allProjects.setParameter("begin", begin);
			allProjects.setParameter("end", end);
			return allProjects.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Project> getMyManagedProjectsBetweenDates(String email, Timestamp begin, Timestamp end) {
		try {
			TypedQuery<Project> allProjects = em.createNamedQuery("Project.findMyManagedProjectsBetweenDates", Project.class);
			allProjects.setParameter("email", email);
			allProjects.setParameter("begin", begin);
			allProjects.setParameter("end", end);
			return allProjects.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Project> getClientProjects(Client client) {
		try {
			TypedQuery<Project> allProjects = em.createNamedQuery("Project.findClientProjects", Project.class);
			allProjects.setParameter("company", client.getCompany());
			return allProjects.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}


	/**
	 * Returns projects managed by user
	 * @param email - user email
	 * @return
	 */
	public List<Project> getMyManagedProjects(String email) {
		try {
			TypedQuery<Project> myManagedProjects = em.createNamedQuery("Project.findMyManagedProjects", Project.class);
			myManagedProjects.setParameter("email", email);
			return myManagedProjects.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Project> projectAdvancedSearchAll(String email, String type, String searchText) {
		try {
			if (!type.equals("date") && !type.equals("stage") && !type.equals("business")) {
				searchText="%"+searchText+"%";
			}
			TypedQuery<Project> projs = null;
			switch (type) {
			case "date":
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date date1 = sdf.parse(searchText);
				Timestamp date = new Timestamp(date1.getTime());
				projs = em.createNamedQuery("Project.findAllActiveInDate", Project.class);
				projs.setParameter("date", date);
				break;
			case "name":
				projs = em.createNamedQuery("Project.findAllWithNameLIKE", Project.class);
				projs.setParameter("name", searchText);
				break;
			case "client":
				projs = em.createNamedQuery("Project.findAllWithClientLIKE", Project.class);
				projs.setParameter("client", searchText);
				break;
			case "stage":
				projs = em.createNamedQuery("Project.findAllWithStageLIKE", Project.class);
				projs.setParameter("stage", searchText);
				break;
			case "business":
				projs = em.createNamedQuery("Project.findAllWithBusinessLIKE", Project.class);
				projs.setParameter("business", searchText);		
				break;
			case "all":
				projs = em.createNamedQuery("Project.findAll", Project.class);
				break;
			case "open":
				projs = em.createNamedQuery("Project.findAllOpenProjects", Project.class);
				break;
			case "closed":
				projs = em.createNamedQuery("Project.findAllClosedProjects", Project.class);	
				break;
			default:
				break;
			}
			return projs.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Project> projectAdvancedSearchSome(String email, String type, String searchText) {
		try {
			if (!type.equals("date") && !type.equals("stage") && !type.equals("business")) {
				searchText="%"+searchText+"%";
			}
			TypedQuery<Project> projsManaged = null;
			TypedQuery<Project> projsWorking = null;
			switch (type) {
			case "date":
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date date1 = sdf.parse(searchText);
				Timestamp date = new Timestamp(date1.getTime());
				projsManaged = em.createNamedQuery("Project.findManagedActiveInDate", Project.class);
				projsManaged.setParameter("date", date);
				projsManaged.setParameter("email", email);
				projsWorking = em.createNamedQuery("Project.findWorkingActiveInDate", Project.class);
				projsWorking.setParameter("date", date);
				projsWorking.setParameter("email", email);
				break;
			case "name":
				projsManaged = em.createNamedQuery("Project.findManagedWithNameLIKE", Project.class);
				projsManaged.setParameter("name", searchText);
				projsManaged.setParameter("email", email);
				projsWorking = em.createNamedQuery("Project.findWorkingWithNameLIKE", Project.class);
				projsWorking.setParameter("name", searchText);
				projsWorking.setParameter("email", email);
				break;
			case "client":
				projsManaged = em.createNamedQuery("Project.findManagedWithClientLIKE", Project.class);
				projsManaged.setParameter("client", searchText);
				projsManaged.setParameter("email", email);
				projsWorking = em.createNamedQuery("Project.findWorkingWithClientLIKE", Project.class);
				projsWorking.setParameter("client", searchText);
				projsWorking.setParameter("email", email);
				break;
			case "stage":
				projsManaged = em.createNamedQuery("Project.findManagedWithStageLIKE", Project.class);
				projsManaged.setParameter("stage", searchText);
				projsManaged.setParameter("email", email);
				projsWorking = em.createNamedQuery("Project.findWorkingWithStageLIKE", Project.class);
				projsWorking.setParameter("stage", searchText);
				projsWorking.setParameter("email", email);
				break;
			case "business":
				projsManaged = em.createNamedQuery("Project.findManagedWithBusinessLIKE", Project.class);
				projsManaged.setParameter("business", searchText);
				projsManaged.setParameter("email", email);
				projsWorking = em.createNamedQuery("Project.findWorkingWithBusinessLIKE", Project.class);
				projsWorking.setParameter("business", searchText);
				projsWorking.setParameter("email", email);
				break;
			case "all":
				projsManaged = em.createNamedQuery("Project.findMyManagedProjects", Project.class);
				projsManaged.setParameter("email", email);
				projsWorking = em.createNamedQuery("Project.findMyWorkingProjects", Project.class);
				projsWorking.setParameter("email", email);
				break;
			case "open":
				projsManaged = em.createNamedQuery("Project.findManagedOpenProjects", Project.class);
				projsManaged.setParameter("email", email);
				projsWorking = em.createNamedQuery("Project.findWorkingOpenProjects", Project.class);
				projsWorking.setParameter("email", email);
				break;
			case "closed":
				projsManaged = em.createNamedQuery("Project.findManagedClosedProjects", Project.class);
				projsManaged.setParameter("email", email);
				projsWorking = em.createNamedQuery("Project.findWorkingClosedProjects", Project.class);
				projsWorking.setParameter("email", email);
				break;
			default:
				break;
			}
			List<Project> projs1 = projsManaged.getResultList();
			List<Project> projs2 = projsWorking.getResultList();
			for (int i=0; i<projs2.size(); i++) {
				if (!projs1.contains(projs2.get(i))) {
					projs1.add(projs2.get(i));
				}
			}
		return projs1;
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

@TransactionAttribute(TransactionAttributeType.MANDATORY)
public void createNewProject(Project newProject) {
	try {
		em.persist(newProject);
		em.flush();
	} catch (Exception e) {
		logger.error("Exception "+e);
	}
}

public Boolean checkUniqueIdprojectExistence(String idproject) {
	try {
		TypedQuery<Project> projectByUniqueIdproject = em.createNamedQuery("Project.ProjectByUniqueIdproject", Project.class);
		projectByUniqueIdproject.setParameter("idProject", idproject);
		if(projectByUniqueIdproject.getResultList().size()!=0) {
			return true;
		} else {
			return false;
		}
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public Project getProjectByUniqueIdproject(String idproject) {
	try {
		TypedQuery<Project> projectByUniqueIdproject = em.createNamedQuery("Project.ProjectByUniqueIdproject", Project.class);
		projectByUniqueIdproject.setParameter("idProject", idproject);
		return projectByUniqueIdproject.getSingleResult();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public Long findTotalHoursWorkedInEndedTasks(Project project, Timestamp now) {
	try {
		TypedQuery<Long> projectWorkedHours = em.createNamedQuery("Project.findTotalHoursWorkedInEndedTasks", Long.class);
		projectWorkedHours.setParameter("id", project.getId());
		projectWorkedHours.setParameter("now", now);
		return projectWorkedHours.getSingleResult();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public Long findHoursRemainingInEndedTasks(Project project, Timestamp now) {
	try {
		TypedQuery<Long> hours = em.createNamedQuery("Project.findHoursRemainingInEndedTasks", Long.class);
		hours.setParameter("id", project.getId());
		hours.setParameter("now", now);
		return hours.getSingleResult();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public Long findTotalHoursWorkedInCurrentTasks(Project project, Timestamp now) {
	try {
		TypedQuery<Long> hours = em.createNamedQuery("Project.findTotalHoursWorkedInCurrentTasks", Long.class);
		hours.setParameter("id", project.getId());
		hours.setParameter("now", now);
		return hours.getSingleResult();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public Long findInitialDurationEstimative(Project project) {
	try {
		TypedQuery<Long> projectWorkedHours = em.createNamedQuery("Project.findInitialDurationEstimative", Long.class);
		projectWorkedHours.setParameter("id", project.getId());
		return projectWorkedHours.getSingleResult();
	} catch (Exception e) {
		//logger.error("Exception "+e);
		return 0L;
	}
}

public Long findTotalHoursWorkedInProject(Project project) {
	try {
		TypedQuery<Long> projectWorkedHours = em.createNamedQuery("Project.findTotalHoursWorkedInProject", Long.class);
		projectWorkedHours.setParameter("id", project.getId());
		return projectWorkedHours.getSingleResult();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public Long findTotalHoursWorkedInProjectInDate(Project project, Timestamp date) {
	try {
		TypedQuery<Long> projectWorkedHours = em.createNamedQuery("Project.findTotalHoursWorkedInProjectInDate", Long.class);
		projectWorkedHours.setParameter("id", project.getId());
		projectWorkedHours.setParameter("date", date);
		return projectWorkedHours.getSingleResult();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public Long findEstimatedHoursToFinishProject(Project project) {
	try {
		TypedQuery<Long> projectExpectedDurationInHours = em.createNamedQuery("Project.findEstimatedHoursToFinishProject", Long.class);
		projectExpectedDurationInHours.setParameter("id", project.getId());
		return projectExpectedDurationInHours.getSingleResult();
	} catch (Exception e) {	
		logger.error("Exception "+e);
		return 0L;
	}
}

public List<Task> findCurrentTasks(Project project, Timestamp now) {
	try {
		TypedQuery<Task> tasks = em.createNamedQuery("Project.findCurrentTasks", Task.class);
		tasks.setParameter("id", project.getId());
		tasks.setParameter("now", now);
		return tasks.getResultList();
	} catch (Exception e) {	
		logger.error("Exception "+e);
		return null;
	}
}

public Double getActualCost(Project project) {
	try {
		TypedQuery<Double> projectActualCost = em.createNamedQuery("Project.findActualCost", Double.class);
		projectActualCost.setParameter("id", project.getId());
		return projectActualCost.getSingleResult();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public Double getProjectCostInDate(Project project, Timestamp date) {
	try {
		TypedQuery<Double> projectActualCost = em.createNamedQuery("Project.findProjectCostInDate", Double.class);
		projectActualCost.setParameter("id", project.getId());
		projectActualCost.setParameter("date", date);
		return projectActualCost.getSingleResult();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

//NOTE: Every project has a manager
public Projmanager findActualProjmanager(Project project) {
	try {
		TypedQuery<Projmanager> projectActualProjmanager = em.createNamedQuery("Project.findActualProjmanager", Projmanager.class);
		projectActualProjmanager.setParameter("id", project.getId());
		return projectActualProjmanager.getSingleResult();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public User findActualManager(Project project) {
	try {
		TypedQuery<User> projectActualProjmanager = em.createNamedQuery("Project.findActualManager", User.class);
		projectActualProjmanager.setParameter("id", project.getId());
		return projectActualProjmanager.getSingleResult();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public ArrayList<Allocation> getProjectAllocations(Project project) {
	try {
		TypedQuery<Allocation> projectAllocations = em.createNamedQuery("Project.findAllocations", Allocation.class);
		projectAllocations.setParameter("id", project.getId());
		return (ArrayList<Allocation>) projectAllocations.getResultList();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public ArrayList<User> getProjectManagers(Project project) {
	try {
		TypedQuery<User> projectManagers = em.createNamedQuery("Project.findManagers", User.class);
		projectManagers.setParameter("id", project.getId());
		return (ArrayList<User>) projectManagers.getResultList();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public List<Task> getProjectTasks(int id) {
	try {
		TypedQuery<Task> projectTasks = em.createNamedQuery("Project.findTasks", Task.class);
		projectTasks.setParameter("id", id);
		return projectTasks.getResultList();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public List<Allocation> getAllProjectAllocations(int id) {
	try {
		TypedQuery<Allocation> allocs = em.createNamedQuery("Project.findAllocations", Allocation.class);
		allocs.setParameter("id", id);
		return allocs.getResultList();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public User getActualManager(Project project) {
	try {
		TypedQuery<User> actualProjectManager = em.createNamedQuery("Project.findActualManager", User.class);
		actualProjectManager.setParameter("id", project.getId());
		return actualProjectManager.getSingleResult();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public List<Task> findProjectFutureTasks(Project project, Timestamp today) {
	try {
		TypedQuery<Task> tasks = em.createNamedQuery("Project.findProjectFutureTasks", Task.class);
		tasks.setParameter("id", project.getId());
		tasks.setParameter("today", today);
		return tasks.getResultList();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}

public List<Task> findProjectPresentTasks(Project project, Timestamp today) {
	try {
		TypedQuery<Task> tasks = em.createNamedQuery("Project.findProjectPresentTasks", Task.class);
		tasks.setParameter("id", project.getId());
		tasks.setParameter("today", today);
		return tasks.getResultList();
	} catch (Exception e) {
		logger.error("Exception "+e);
		return null;
	}
}
















}
