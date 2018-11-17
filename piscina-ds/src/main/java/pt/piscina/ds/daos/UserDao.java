package pt.piscina.ds.daos;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import pt.piscina.ds.entities.Allocation;
import pt.piscina.ds.entities.Project;
import pt.piscina.ds.entities.Projmanager;
import pt.piscina.ds.entities.Role;
import pt.piscina.ds.entities.Skill;
import pt.piscina.ds.entities.Task;
import pt.piscina.ds.entities.User;

@Stateless
public class UserDao extends AbstractDao<User>{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(UserDao.class);

	public UserDao() {
		super(User.class);
	}

	public List<User> userAdvancedSearch(String type, String searchText) {
		try {
			if (!type.equals("date") && !type.equals("skill")) {
				searchText="%"+searchText+"%";
			}
			TypedQuery<User> users = null;
			switch (type) {
			case "date":
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date date1 = sdf.parse(searchText);
				Timestamp date = new Timestamp(date1.getTime());
				users = em.createNamedQuery("User.findAllRegisteredAfterDate", User.class);
				users.setParameter("date", date);
				break;
			case "name":
				users = em.createNamedQuery("User.findAllWithNameOrEmailLIKE", User.class);
				users.setParameter("name", searchText);
				break;
			case "role":
				users = em.createNamedQuery("User.findUsersWithRole", User.class);
				users.setParameter("roleName", searchText);
				break;
			case "skill":
				users = em.createNamedQuery("User.findAllWithSkillLIKE", User.class);
				users.setParameter("skill", searchText);
				break;
			case "all":
				users = em.createNamedQuery("User.findAll", User.class);
				break;
			case "active":
				users = em.createNamedQuery("User.findAllActive", User.class);
				break;
			case "inactive":
				users = em.createNamedQuery("User.findAllInactive", User.class);
				break;
			default:
				break;
			}
			return users.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	//TODO mudei para BBBBoolean
	public Boolean verifyEmailExistence(String email) {
		try {
			TypedQuery<User> usersWithEmail = em.createNamedQuery("User.findUserByEmail", User.class);
			usersWithEmail.setParameter("email", email);
			return usersWithEmail.getResultList().size()!=0;
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public User getUserWithEmail(String email) {
		try {
			TypedQuery<User> usersWithEmail = em.createNamedQuery("User.findUserByEmail", User.class);
			usersWithEmail.setParameter("email", email);
			return usersWithEmail.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}


	public List<User> findUsersWithAtLeastOneOfTwoRoles(String roleName1, String roleName2) {
		try {
			TypedQuery<User> users = em.createNamedQuery("User.findUsersWithAtLeastOneOfTwoRoles", User.class);
			users.setParameter("roleName1", roleName1);
			users.setParameter("roleName2", roleName2);
			return users.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<User> findUsersWithRole(String roleName) {
		try {
			TypedQuery<User> users = em.createNamedQuery("User.findUsersWithRole", User.class);
			users.setParameter("roleName", roleName);
			return users.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}


	//TODO esta logica de negocio deve de estar no userService
	//Mudei para BOOOOOlean
	public Boolean loginUser(String email, String password) {
		try {
			TypedQuery<User> loginUser = em.createNamedQuery("User.loginUser", User.class);
			loginUser.setParameter("email", email);
			loginUser.setParameter("password", password);
			if (loginUser.getResultList().isEmpty()) {
				return false;
			} else {
				//if user has no active state -- refuse login
				if (!loginUser.getSingleResult().getActive()) {
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public String getUserSalt(String email) {
		try {
			TypedQuery<User> userWithEmail = em.createNamedQuery("User.findUserByEmail", User.class);
			userWithEmail.setParameter("email", email);
			User singleResult = userWithEmail.getSingleResult();
			return singleResult.getSalt();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}

	}

	//TODO verificar se este metodo funciona
	public List<Allocation> getUserAllocationsBetweenDates(User user, Timestamp begin, Timestamp end) {
		try {
			TypedQuery<Allocation> userAllocationsBetweenDates = em.createNamedQuery("User.findUserAllocationsBetweenDates", Allocation.class);
			userAllocationsBetweenDates.setParameter("id", user.getId());
			userAllocationsBetweenDates.setParameter("begin", begin);
			userAllocationsBetweenDates.setParameter("end", end);
			return userAllocationsBetweenDates.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	//TODO analisar se devo retornar ArrayList (com cast) ou apenas List
	public List<User> getAllusers() {
		try {
			TypedQuery<User> allUsers = em.createNamedQuery("User.findAll", User.class);
			return allUsers.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<User> getManagersList(int id1, int id2) {
		try {
			TypedQuery<User> allUsers = em.createNamedQuery("User.findAllToManageProject", User.class);
			allUsers.setParameter("id1", id1);
			allUsers.setParameter("id2", id2);
			return allUsers.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Role> getUserRoles(User user) {
		try {
			TypedQuery<Role> userRoles = em.createNamedQuery("User.findUserRoles", Role.class);
			userRoles.setParameter("id", user.getId());
			return userRoles.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Task> getUserTasks(User user) {
		try {
			TypedQuery<Task> userTasks = em.createNamedQuery("User.findUserTasks", Task.class);
			userTasks.setParameter("id", user.getId());
			return userTasks.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Skill> getUserSkills(User user) {
		try {
			TypedQuery<Skill> userSkills = em.createNamedQuery("User.findUserSkills", Skill.class);
			userSkills.setParameter("id", user.getId());
			return userSkills.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Allocation> getUserAllocations(User user) {
		try {
			TypedQuery<Allocation> userAllocations = em.createNamedQuery("User.findUserAllocations", Allocation.class);
			userAllocations.setParameter("id", user.getId());
			return userAllocations.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Project> getUserProjects(User user) {
		try {
			TypedQuery<Project> userProjects = em.createNamedQuery("User.findUserProjects", Project.class);
			userProjects.setParameter("id", user.getId());
			return userProjects.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Projmanager> getUserManagedProjects(User user) {
		try {
			TypedQuery<Projmanager> userManagedProjects = em.createNamedQuery("User.findUserManagedProjects", Projmanager.class);
			userManagedProjects.setParameter("id", user.getId());
			return userManagedProjects.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	//TODO BOOOLEAN
	public Boolean isUserProjectManager(User user) {
		try {
			TypedQuery<Projmanager> userManagedProjects = em.createNamedQuery("User.findUserManagedProjects", Projmanager.class);
			userManagedProjects.setParameter("id", user.getId());
			return userManagedProjects.getResultList().size()>0;
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
	
	public void changeUser(User user) {
		try {
			em.merge(user);
		} catch (Exception e) {
			logger.error("Exception "+e);
		}
	}
//TODO BOOLEAN
	public Boolean updateUser(User selectedUser) {
		try {
			em.merge(selectedUser);
			return true;
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<User> userAdvancedSearch(String email, String type, String searchText, boolean getAll) {
		try {
			if (!type.equals("date")) {
				searchText="%"+searchText+"%";
			}
			return null;
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Allocation> findUserAllocationsActiveInDate(Timestamp date, String email) {
		try {
			TypedQuery<Allocation> userAllocations = em.createNamedQuery("User.findUserAllocationsActiveInDate", Allocation.class);
			userAllocations.setParameter("email", email);
			userAllocations.setParameter("date", date);
			return userAllocations.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Allocation> findUserAllocationsEndingInDate(Timestamp date, String email) {
		try {
			TypedQuery<Allocation> userAllocations = em.createNamedQuery("User.findUserAllocationsEndingInDate", Allocation.class);
			userAllocations.setParameter("email", email);
			userAllocations.setParameter("date", date);
			return userAllocations.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}













}
