package pt.piscina.ds.services;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;

import io.jsonwebtoken.Claims;
import pt.piscina.ds.daos.AllocationDao;
import pt.piscina.ds.daos.ClientDao;
import pt.piscina.ds.daos.ProjectDao;
import pt.piscina.ds.daos.ProjmanagerDao;
import pt.piscina.ds.daos.StageDao;
import pt.piscina.ds.daos.TaskDao;
import pt.piscina.ds.daos.TypologyDao;
import pt.piscina.ds.daos.UserDao;
import pt.piscina.ds.entities.Allocation;
import pt.piscina.ds.entities.Client;
import pt.piscina.ds.entities.Project;
import pt.piscina.ds.entities.Projmanager;
import pt.piscina.ds.entities.Role;
import pt.piscina.ds.entities.Skill;
import pt.piscina.ds.entities.Stage;
import pt.piscina.ds.entities.Task;
import pt.piscina.ds.entities.Tipology;
import pt.piscina.ds.entities.User;
import pt.piscina.ds.security.MyJwt;
import pt.piscina.ds.util.InternalError;
import pt.piscina.ds.util.MyProjectMail;
import pt.piscina.ds.util.TimeCalc;
import pt.piscina.ds.util.Trimmer;
import pt.piscina.itf.dtos.AllocationDto;
import pt.piscina.itf.dtos.BusinessDto;
import pt.piscina.itf.dtos.ClientLightDto;
import pt.piscina.itf.dtos.ProjectDto;
import pt.piscina.itf.dtos.ProjectNewDto;
import pt.piscina.itf.dtos.TaskLightDto;
import pt.uc.dei.itf.errors.ErrorMessage;

@Stateless
public class ProjectService implements Serializable{

	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger.getLogger(ProjectService.class);

	@Inject
	private ProjectDao projectDao;

	@Inject
	private ProjmanagerDao projmanagerDao;

	@Inject
	private ClientDao clientDao;

	@Inject
	private AllocationDao allocationDao;

	@Inject
	private StageDao stageDao;

	@Inject
	private UserDao userDao;

	@Inject
	private TaskService taskService;

	@Inject
	private TaskDao taskDao;

	@Inject
	private TypologyDao typologyDao;

	@Inject
	private MyProjectMail myProjectMail;

	public ProjectService() {	
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Response createNewProject(ProjectNewDto dtoNewProject) {
		try {
			//Messaged to be logged
			StringBuilder sb = new StringBuilder();

			boolean sucess=true;
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();

			if (dtoNewProject.getBeginDate().after(dtoNewProject.getEndDate()) || dtoNewProject.getBudget()<=0) {
				sucess=false;
				errorMessages.add(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE);
			}
			if (dtoNewProject.getStage().equals("Closed")) {
				sucess=false;
				errorMessages.add(ErrorMessage.INVALID_FIELDS);
			}

			Timestamp now = TimeCalc.todayMidnight();
			if (dtoNewProject.getEndDate().before(now)) {
				errorMessages.add(ErrorMessage.DATE_MUST_NOT_BE_PAST);
				sucess=false;
			}

			if (dtoNewProject.getBudget()<0) {
				sucess=false;
				errorMessages.add(ErrorMessage.MUST_BE_POSITIVE_VALUE);
			}
			String idProject = Trimmer.clean(dtoNewProject.getIdProject());
			boolean projectExists = projectDao.checkUniqueIdprojectExistence(idProject);
			if (projectExists) {
				sucess=false;
				errorMessages.add(ErrorMessage.UNIQUE_NAME_ALREADY_EXISTS);
			}

			String title = Trimmer.clean(dtoNewProject.getTitle());
			String typol = Trimmer.clean(dtoNewProject.getTipology());
			String stag = Trimmer.clean(dtoNewProject.getStage());
			String description = Trimmer.clean(dtoNewProject.getDescription());
			if (title.equals("") || idProject.equals("") || description.equals("") || typol.equals("") || stag.equals("")) {
				sucess=false;
				errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
			}

			Tipology tipology = typologyDao.findTypologyByName(dtoNewProject.getTipology());			
			Stage stage = stageDao.findStageByName(dtoNewProject.getStage());

			if (tipology==null || stage==null) {
				sucess=false;
				errorMessages.add(ErrorMessage.INVALID_FIELDS);
			}

			sb.append("User with email ").append(dtoNewProject.getCreatorEmail()).append(" CREATING NEW Project ");
			sb.append(" Title: ").append(title).append(" idProject: ").append(idProject).append(" description: ").append(description);
			sb.append(" End: ").append(dtoNewProject.getEndDate()).append(" Begin: ").append(dtoNewProject.getBeginDate());
			sb.append(" Manager: ").append(dtoNewProject.getManagerEmail()).append(" Client: ").append(dtoNewProject.getClientCompanyName());
			if (sucess) {
				Client client = clientDao.findClientByName(dtoNewProject.getClientCompanyName());
				User creator = userDao.getUserWithEmail(dtoNewProject.getCreatorEmail());
				Project project = new Project(title, idProject, description, client, dtoNewProject.getBeginDate(), 
						dtoNewProject.getEndDate(), dtoNewProject.getBudget(), stage, creator, tipology, now);
				projectDao.createNewProject(project);
				User manager = userDao.getUserWithEmail(dtoNewProject.getManagerEmail());
				Projmanager pm = new Projmanager(project.getBeginDate(), project.getEndDate(), project, manager);
				projmanagerDao.createProjManager(pm);
				myProjectMail.send(pm.getUser().getEmail(), "Alteração de Gestor", "Foi-lhe atribuido a gestão do projeto "+project.getIdProject()+" para a empresa "+project.getClient().getCompany()+". Para mais informações ","http://localhost:8080/aclgomes-rnavalho-project-ws/");		
				logger.info("SUCESS - "+sb.toString());
				return Response.ok(true).build();
			} else {
				logger.info("FAILED - "+sb.toString());
				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}


	public Response updateProject(ProjectDto projectDto, String token) {	
		try {
			StringBuilder sb = new StringBuilder();

			boolean sucess=true;
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			Project project = projectDao.getProjectByUniqueIdproject(projectDto.getIdProject());

			// Project end date cannot be before begin date
			if (projectDto.getBeginDate().after(projectDto.getEndDate())) {
				sucess=false;
				errorMessages.add(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE);
			}

			Claims claims = MyJwt.parseJWT(token);
			String email = claims.getSubject();
			boolean isDirector = isUserInRole(email, "Director");
			if (!isDirector) {
				boolean isProjectManager = isUserProjectManager(project, email);
				if (!isProjectManager) {
					sucess=false;
					errorMessages.add(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER);
				}
			}

			// project new begin date cannot be after any task begin date and project new end date cannot be before any task end date
			boolean datesOk = checkProjectNewDatesAgainstTasks(project, new Timestamp(projectDto.getBeginDate().getTime()), 
					new Timestamp(projectDto.getEndDate().getTime()));
			if (!datesOk) {
				sucess=false;
				errorMessages.add(ErrorMessage.PROJECT_HAS_TASKS_OUTSIDE_DATES);
			}

			Timestamp now = TimeCalc.todayMidnight();
			Timestamp tomorrow = TimeCalc.tomorrowMidnight();
			if (projectDto.getEndDate().before(now)) {
				errorMessages.add(ErrorMessage.DATE_MUST_NOT_BE_PAST);
				sucess=false;
			}

			// project new budget must be positive
			if (projectDto.getBudget()<0) {
				sucess=false;
				errorMessages.add(ErrorMessage.MUST_BE_POSITIVE_VALUE);
			}

			String description = Trimmer.clean(projectDto.getDescription());
			String title = Trimmer.clean(projectDto.getTitle());
			String typol = Trimmer.clean(projectDto.getTypology());
			String stag = Trimmer.clean(projectDto.getStage());
			if (description.equals("") || title.equals("") || typol.equals("") || stag.equals("")) {
				sucess=false;
				errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
			}

			Tipology tipology = typologyDao.findTypologyByName(projectDto.getTypology());			
			Stage stage = stageDao.findStageByName(projectDto.getStage());

			if (tipology==null || stage==null) {
				sucess=false;
				errorMessages.add(ErrorMessage.INVALID_FIELDS);
			}

			// cannot change project manager on last day of project
			User actualManager = projectDao.getActualManager(project);
			User manager = userDao.getUserWithEmail(projectDto.getManagerEmail());
			if (!actualManager.getEmail().equals(manager.getEmail())) {
				if (project.getEndDate().before(now)) {
					sucess=false;
					errorMessages.add(ErrorMessage.CANNOT_CHANGE_MANAGER_ON_LAST_PROJECT_DAY);
				}
			}

			sb.append("User with email ").append(email).append(" UPDATING Project ID ").append(project.getId());
			sb.append(" BEFORE ").append(" Title: ").append(project.getTitle()).append(" Description: ").append(project.getDescription());
			sb.append(" Typology: ").append(project.getTipology().getTipology()).append(" Stage: ").append(project.getStage().getStage());
			sb.append(" Begin: ").append(project.getBeginDate()).append(" End: ").append(project.getEndDate()).append(" Budget: ").append(project.getBudget());

			sb.append("\n").append(" AFTER : ").append(" Title: ").append(title).append(" description: ").append(description);
			sb.append(" Typology: ").append(typol).append(" Stage: ").append(stag);
			sb.append(" Begin: ").append(new Timestamp(projectDto.getBeginDate().getTime())).append(" End: ").append(new Timestamp(projectDto.getEndDate().getTime())).append(" Budget: ").append(projectDto.getBudget());

			if (sucess) {
				//if manager diferent from actual manager, update manager, else do not update
				if (!actualManager.getEmail().equals(manager.getEmail())) {
					Timestamp newProjBeginDate = new Timestamp(projectDto.getBeginDate().getTime());
					if (newProjBeginDate.before(tomorrow)) {
						newProjBeginDate=tomorrow;	
					}
					Projmanager oldProjmanager = projectDao.findActualProjmanager(project);
					oldProjmanager.setEndDate(now);
					sb.append(" New Manager: ").append(manager.getEmail());
					//change old Projmanager to have end date now
					projmanagerDao.merge(oldProjmanager);
					//send mail to old manager
					myProjectMail.send(oldProjmanager.getUser().getEmail(), "Alteração de Gestor", "A partir de agora, não é mais gestor do projeto "+project.getIdProject()+" da empresa "+project.getClient().getCompany()+". Para mais informações ", "http://localhost:8080/aclgomes-rnavalho-project-ws/");

					//change new Projmanager to have begin date now and end date equal to the project date

					Projmanager projmanager = new Projmanager(newProjBeginDate, new Timestamp(projectDto.getEndDate().getTime()), project, manager);
					projmanagerDao.persist(projmanager);
					//send mail to new manager
					myProjectMail.send(projmanager.getUser().getEmail(), "Alteração de Gestor", "Foi-lhe atribuido a gestão do projeto "+project.getIdProject()+" da empresa "+project.getClient().getCompany()+". Para mais informações ", "http://localhost:8080/aclgomes-rnavalho-project-ws/");
				} else {
					Projmanager projmanager = projectDao.findActualProjmanager(project);
					//projmanager.setBeginDate(project.getBeginDate());
					projmanager.setEndDate(new Timestamp(projectDto.getEndDate().getTime()));
					projmanager.setBeginDate(new Timestamp(projectDto.getBeginDate().getTime()));
					projmanagerDao.persist(projmanager);
				}
				project.setBeginDate(new Timestamp(projectDto.getBeginDate().getTime()));
				project.setEndDate(new Timestamp(projectDto.getEndDate().getTime()));
				project.setBudget(projectDto.getBudget());
				project.setDescription(description);
				project.setTitle(title);
				project.setStage(stage);
				project.setTipology(tipology);

				if (stage.getStage().equals("Closed")) {
					Timestamp yesterday = TimeCalc.yesterdayMidnight();
					endAllFutureTasksAllocationsAndRemoveFuturePrecedences(project, now, tomorrow, yesterday);
				}
				projectDao.merge(project);
				logger.info("SUCESS - "+sb.toString());
				return Response.ok(true).build();
			} else {
				logger.info("FAILED - "+sb.toString());
				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	private void endAllFutureTasksAllocationsAndRemoveFuturePrecedences(Project project, Timestamp today, Timestamp tomorrow, Timestamp yesterday) {
		List<Task> present = projectDao.findProjectPresentTasks(project, today);
		List<Task> futures = projectDao.findProjectFutureTasks(project, today);
		for (int i=0; i<futures.size(); i++) {
			Task task = futures.get(i);
			List<Allocation> allocs = taskDao.findTaskAllocations(task);
			//Invalidate all allocations
			for (int j=0; j<allocs.size(); j++) {
				allocs.get(j).setBeginDate(today);
				allocs.get(j).setEndDate(yesterday);
				allocationDao.merge(allocs.get(j));
			}
			//remove precendences
			task.setTasksBefore(null);
			task.setBeginDate(today);
			task.setEndDate(today);
			taskDao.merge(task);
		}

		for (int i=0; i<present.size(); i++) {
			Task task = present.get(i);
			List<Allocation> allocs = taskDao.findTaskAllocations(task);
			//Invalidate all allocations
			for (int j=0; j<allocs.size(); j++) {
				if (allocs.get(j).getBeginDate().after(today)) {
					allocs.get(j).setBeginDate(today);
					allocs.get(j).setEndDate(yesterday);
				} else if (allocs.get(j).getEndDate().after(today)) {
					allocs.get(j).setEndDate(today);
				}

				allocationDao.merge(allocs.get(j));
			}
			task.setEndDate(today);
			taskDao.merge(task);
		}

	}

	private boolean checkProjectNewDatesAgainstTasks(Project project, Timestamp newBeginDate, Timestamp newEndDate) {
		List<Task> projectTasks = projectDao.getProjectTasks(project.getId());
		for (int i=0; i<projectTasks.size(); i++) {
			if (newBeginDate.after(projectTasks.get(i).getBeginDate())) {
				return false;
			}
			if (newEndDate.before(projectTasks.get(i).getEndDate())) {
				return false;
			}
		}
		return true;
	}


	public Response findProjectByProjectId(String idProject) {
		try {
			Project proj = projectDao.getProjectByUniqueIdproject(idProject);
			List<Project> list =new ArrayList<>();
			list.add(proj);
			List<ProjectDto> userProjectsToList = getUserProjectsToList(list);
			return Response.ok(userProjectsToList.get(0)).build();
		} catch (Exception e) {	
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}


	public Response findProjectsForNewTask() {
		try {
			List<Project> allProjects = projectDao.findProjectsForNewTask();
			List<ProjectDto> projects= getUserProjectsToList(allProjects);
			return Response.ok(projects).build();
		} catch (Exception e) {	
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response projectAdvancedSearch(String token, String type, String searchText) {
		try {
			boolean success=true;
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();		
			Claims claims = MyJwt.parseJWT(token);
			String email = claims.getSubject();		
			boolean isDirector = isUserInRole(email, "Director");
			boolean isUser = isUserInRole(email, "User");		
			List<Project> projList = null;
			boolean getAll=false;
			if (isDirector) {
				getAll=true;
			} else if (isUser) {
				getAll=false;
			} else {
				errorMessages.add(ErrorMessage.ROLE_DOES_NOT_PERMIT_OPERATION);
				return statusNOK.entity(errorMessages).build();
			}
			String search=searchText;
			if(!type.equals("date")){
				search = Trimmer.clean(searchText);
			}

			if (search.equals("")) {
				success=false;
				errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
			}
			if (success) {
				List<ProjectDto> projects=null;
				if (getAll) {
					projList=projectDao.projectAdvancedSearchAll(email, type, search);
					projects= getUserProjectsToList(projList);
				} else {
					projList=projectDao.projectAdvancedSearchSome(email, type, search);
					projects= getUserProjectsToList(projList);
				}

				return Response.ok(projects).build();
			} else {
				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {	
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getMyWorkingProjects(String token) {
		try {
			String email = MyJwt.parseJWT(token).getSubject();
			List<Project> myWorkingProjects = projectDao.getMyWorkingProjects(email);
			List<ProjectDto> projects= getUserProjectsToList(myWorkingProjects);
			return Response.ok(projects).build();
		} catch (Exception e) {	
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getMyWorkingProjectsBetweenDates(String token, String beginDate1, String endDate1) {
		try {
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date beginDate2 = sdf.parse(beginDate1);
			Date endDate2 = sdf.parse(endDate1);
			Timestamp begin = new Timestamp(beginDate2.getTime());
			Timestamp end = new Timestamp(endDate2.getTime());
			if (begin.after(end)) {
				errorMessages.add(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE);
				return statusNOK.entity(errorMessages).build();
			}	
			String email = MyJwt.parseJWT(token).getSubject();
			List<Project> myWorkingProjects = projectDao.getMyWorkingProjectsBetweenDates(email, begin, end);
			List<ProjectDto> projects= getUserProjectsToList(myWorkingProjects);
			return Response.ok(projects).build();
		} catch (Exception e) {	
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getMyManagedProjectsBetweenDates(String token, String beginDate1, String endDate1) {
		try {
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date beginDate2 = sdf.parse(beginDate1);
			Date endDate2 = sdf.parse(endDate1);
			Timestamp begin = new Timestamp(beginDate2.getTime());
			Timestamp end = new Timestamp(endDate2.getTime());
			if (begin.after(end)) {
				errorMessages.add(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE);
				return statusNOK.entity(errorMessages).build();
			}	
			String email = MyJwt.parseJWT(token).getSubject();
			List<Project> myWorkingProjects = projectDao.getMyManagedProjectsBetweenDates(email, begin, end);
			List<ProjectDto> projects= getUserProjectsToList(myWorkingProjects);
			return Response.ok(projects).build();
		} catch (Exception e) {	
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}


	public Response getMyManagedProjects(String token) {
		try {
			String email = MyJwt.parseJWT(token).getSubject();
			List<Project> myManagedProjects = projectDao.getMyManagedProjects(email);
			List<ProjectDto> projects= getUserProjectsToList(myManagedProjects);
			return Response.ok(projects).build();
		} catch (Exception e) {	
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getClientProjects(String company) {
		try {
			Client client = clientDao.findClientByName(company);
			List<Project> clientProjects = projectDao.getClientProjects(client);
			List<ProjectDto> projects= getUserProjectsToList(clientProjects);
			return Response.ok(projects).build();
		} catch (Exception e) {	
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public User getActualManager(Project project) {
		return projectDao.getActualManager(project);		
	}

	public Response getAllProjectTasks(String idProject) {
		try {
			Timestamp today = TimeCalc.todayMidnight();
			Project project = projectDao.getProjectByUniqueIdproject(idProject);
			List<Task> projectTasks = projectDao.getProjectTasks(project.getId());
			List<TaskLightDto> projectTasksList = getProjectTasksAsDto(projectTasks,project.getTitle(), project.getIdProject(), today);
			return Response.ok(projectTasksList).build();
		} catch (Exception e) {	
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getAllProjectAllocations(String idProject) {
		try {
			Project project = projectDao.getProjectByUniqueIdproject(idProject);
			List<Allocation> allocs = projectDao.getAllProjectAllocations(project.getId());
			List<AllocationDto> list = getProjectAllocationsAsDto(allocs);
			return Response.ok(list).build();
		} catch (Exception e) {	
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}


	/**
	 * Cost Performance Index (CPI) = Earned Value (EV) / Actual Cost (AC)
	 * IF project has no Tasks or no working hours, this Index has no meaning
	 * EV = Earned Value
	 * BAC = Budget at Completion
	 * AC = Actual Cost
	 * EV = Actual Completion (%) * BAC
	 * @param project
	 * @return
	 */
	public Double calculateWeightedCPI(Project project) {
		Long initialEstimateHourDuration = projectDao.findInitialDurationEstimative(project);
		if (initialEstimateHourDuration==null) {
			return null;
		}	
		Long workedHours = projectDao.findTotalHoursWorkedInProject(project);
		if (workedHours==null) {
			workedHours=0L;
		}
		Long hoursToFinish = projectDao.findEstimatedHoursToFinishProject(project);
		Double actualCompletion = workedHours*1.0/(workedHours+hoursToFinish);
		Double ev=actualCompletion*project.getBudget();
		Double ac = projectDao.getActualCost(project);
		if (ac==null) {
			return null;
		}
		return ev*1.0/ac;
	}

	public Double calculateWeightedCpiInDate(Project project, Timestamp date) {
		Long initialEstimateHourDuration = projectDao.findInitialDurationEstimative(project);
		if (initialEstimateHourDuration==null) {
			return null;
		}	
		Long workedHours = projectDao.findTotalHoursWorkedInProjectInDate(project, date);
		if (workedHours==null) {
			return null;
		}
		Long projDuration = findprojectEstimateTotalDuration(project);
		Long hoursToFinish = projDuration-workedHours;
		Double completionAtDate = workedHours*1.0/(workedHours+hoursToFinish);
		Double ev=completionAtDate*project.getBudget();
		Double ac = projectDao.getProjectCostInDate(project, date);
		if (ac==null) {
			return null;
		}
		return ev*1.0/ac;
	}

	public Double calculateWeightedSpiInDate(Project project, Timestamp date) {
		Long initialEstimateProjectHourDuration = projectDao.findInitialDurationEstimative(project);
		if (initialEstimateProjectHourDuration==null) {
			return null;
		}
		Long workedHours = projectDao.findTotalHoursWorkedInProject(project);
		if (workedHours==null) {
			workedHours=0L;
		}

		Long hoursRemainingInEndedTasks = projectDao.findHoursRemainingInEndedTasks(project, date);
		if (hoursRemainingInEndedTasks==null) {
			hoursRemainingInEndedTasks=0L;
		}
		Long expectedHoursToBeWorkedInCurrentTasks = 0L;
		List<Task> currentTasks = projectDao.findCurrentTasks(project, date);

		for (int i=0; i<currentTasks.size(); i++) {
			int taskEstimateDuration = taskService.getTaskEstimateDuration(currentTasks.get(i));
			Float percentageTimeInTask=1f;

			if (currentTasks.get(i).getEndDate().getTime()-currentTasks.get(i).getBeginDate().getTime()>0) {
				percentageTimeInTask = (date.getTime()-currentTasks.get(i).getBeginDate().getTime())*1.0f/(currentTasks.get(i).getEndDate().getTime()-currentTasks.get(i).getBeginDate().getTime());
			}
			Float taskEstimatedWorkedHours = taskEstimateDuration*percentageTimeInTask;
			expectedHoursToBeWorkedInCurrentTasks= expectedHoursToBeWorkedInCurrentTasks+taskEstimatedWorkedHours.longValue();
		}
		Long expectedHoursToBeWorked =(workedHours+hoursRemainingInEndedTasks+expectedHoursToBeWorkedInCurrentTasks);
		Double spi = workedHours*1.0/expectedHoursToBeWorked;
		return spi;
	}

	private Long findprojectEstimateTotalDuration(Project project) {
		Long workedHours = projectDao.findTotalHoursWorkedInProject(project);
		if (workedHours==null) {
			workedHours=0L;
		}
		Long hoursToFinish = projectDao.findEstimatedHoursToFinishProject(project);
		return workedHours+hoursToFinish;
	}

	/**
	 * Schedule Performance Index (SPI) = Earned Value (EV) / Planned Value (PV)
	 * IF project has no Tasks or no working hours, this Index has no meaning
	 * EV = Earned Value
	 * PV = Planned Value
	 * BAC = Budget at Completion
	 * EV = Actual Completion (%) * BAC
	 * PV = Planned Completion (%) * BAC
	 * SPI resumes to: hours worked/expected hours to be worked in present time
	 * @param project
	 * @return
	 */
	public Double calculateWeightedSPI(Project project) {
		Long initialEstimateProjectHourDuration = projectDao.findInitialDurationEstimative(project);
		if (initialEstimateProjectHourDuration==null) {
			return null;
		}
		Long workedHours = projectDao.findTotalHoursWorkedInProject(project);
		if (workedHours==null) {
			workedHours=0L;
		}
		Timestamp now = new Timestamp(System.currentTimeMillis());
		try {
			now =TimeCalc.todayMidnight();
		} catch (ParseException e) {
			logger.error("Exception "+e);
		}
		Long hoursRemainingInEndedTasks = projectDao.findHoursRemainingInEndedTasks(project, now);
		if (hoursRemainingInEndedTasks==null) {
			hoursRemainingInEndedTasks=0L;
		}
		Long expectedHoursToBeWorkedInCurrentTasks = 0L;
		List<Task> currentTasks = projectDao.findCurrentTasks(project, now);
		for (int i=0; i<currentTasks.size(); i++) {
			int taskEstimateDuration = taskService.getTaskEstimateDuration(currentTasks.get(i));
			Float percentageTimeInTask=1f;
			if (currentTasks.get(i).getEndDate().getTime()-currentTasks.get(i).getBeginDate().getTime()>0) {
				percentageTimeInTask = (now.getTime()-currentTasks.get(i).getBeginDate().getTime())*1.0f/(currentTasks.get(i).getEndDate().getTime()-currentTasks.get(i).getBeginDate().getTime());
			}
			Float taskEstimatedWorkedHours = taskEstimateDuration*percentageTimeInTask;
			expectedHoursToBeWorkedInCurrentTasks= expectedHoursToBeWorkedInCurrentTasks+taskEstimatedWorkedHours.longValue();
		}
		Long expectedHoursToBeWorked =(workedHours+hoursRemainingInEndedTasks+expectedHoursToBeWorkedInCurrentTasks);
		Double spi = workedHours*1.0/expectedHoursToBeWorked;
		return spi;
	}


	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// Private Auxiliary Methods ///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	private List<TaskLightDto> getProjectTasksAsDto(List<Task> projectTasks, String projectTitle, String idProject, Timestamp date) {
		List<TaskLightDto> projectTasksList = new ArrayList<TaskLightDto>();
		for (int i=0; i<projectTasks.size(); i++) {
			int executedPercentageEstimate = taskService.getExecutedPercentageEstimate(projectTasks.get(i));
			User worker = taskService.getTaskCurrentResource(projectTasks.get(i));
			User manager = projectDao.findActualManager(projectTasks.get(i).getProject());
			TaskLightDto taskLightDto=null;
			Task task = projectTasks.get(i);
			Long workedHours = taskDao.findWorkedHours(task);
			if (workedHours==null) {
				workedHours=0L;
			}
			int nowTaskDuration = task.getRemainingHours()+workedHours.intValue();

			Allocation lastAllocation = taskDao.findTaskAllocationInDate(projectTasks.get(i), date);
			Float allocPercentage=null;
			Date beginAllocationDate=null;
			Date endAllocationDate=null;
			if (lastAllocation!=null) {
				allocPercentage=lastAllocation.getAllocPercentage();
				beginAllocationDate=lastAllocation.getBeginDate();
				endAllocationDate=lastAllocation.getEndDate();
			}

			Skill skill= projectTasks.get(i).getSkill();
			String skillString=null;
			if (skill!=null) {
				skillString= skill.getSkill();
			}

			if (worker!=null) {
				taskLightDto= new TaskLightDto(projectTasks.get(i).getId(), projectTasks.get(i).getTaskName(), 
						projectTasks.get(i).getInitialDurationHoursEstimate(), nowTaskDuration, projectTasks.get(i).getBeginDate(),
						projectTasks.get(i).getEndDate(), executedPercentageEstimate, worker.getFullName(), worker.getEmail(),
						manager.getEmail(), projectTitle, idProject, projectTasks.get(i).getDescription(), skillString, 
						projectTasks.get(i).getTaskstage().getTaskStage(), projectTasks.get(i).getTasktype().getTaskType(),
						projectTasks.get(i).getHourCost(), allocPercentage, beginAllocationDate, endAllocationDate, workedHours.intValue());		
			} else {
				taskLightDto= new TaskLightDto(projectTasks.get(i).getId(), projectTasks.get(i).getTaskName(), 
						projectTasks.get(i).getInitialDurationHoursEstimate(), nowTaskDuration, projectTasks.get(i).getBeginDate(), 
						projectTasks.get(i).getEndDate(), executedPercentageEstimate, null, null, manager.getEmail(), projectTitle, idProject, 
						projectTasks.get(i).getDescription(), skillString, projectTasks.get(i).getTaskstage().getTaskStage(), 
						projectTasks.get(i).getTasktype().getTaskType(), projectTasks.get(i).getHourCost(), allocPercentage, 
						beginAllocationDate, endAllocationDate, workedHours.intValue());	
			}
			projectTasksList.add(taskLightDto);
		}
		return projectTasksList;
	}

	private List<AllocationDto> getProjectAllocationsAsDto(List<Allocation> all) {
		List<AllocationDto> list = new ArrayList<AllocationDto>();
		for (int i=0; i<all.size(); i++) {
			list.add(new AllocationDto(all.get(i).getAllocPercentage(), all.get(i).getTask().getId(),
					all.get(i).getUser().getEmail(),all.get(i).getUser().getFullName(), null, all.get(i).getBeginDate(), all.get(i).getEndDate(), all.get(i).getId(), all.get(i).getTask().getEndDate(), all.get(i).getTask().getTaskName(), null));
		}
		return list;
	}

	private List<ProjectDto> getUserProjectsToList(List<Project> allProjects) {
		List<ProjectDto> projectsList= new ArrayList<ProjectDto>();
		for (int i=0; i<allProjects.size(); i++) {
			User actualManager = getActualManager(allProjects.get(i));
			String managerName = actualManager.getFullName();
			String managerEmail = actualManager.getEmail();
			Double cpi = calculateWeightedCPI(allProjects.get(i));
			Double spi = calculateWeightedSPI(allProjects.get(i));
			Integer percentageExecuted=calculatePercentageExecuted(allProjects.get(i));
			Long initialDurationHours=projectDao.findInitialDurationEstimative(allProjects.get(i));
			if(initialDurationHours==null){
				initialDurationHours=0L;
			}
			Long workedHours =projectDao.findTotalHoursWorkedInProject(allProjects.get(i));
			if(workedHours==null){
				workedHours=0L;
			}
			Long hoursToFinishProject = projectDao.findEstimatedHoursToFinishProject(allProjects.get(i));
			if(hoursToFinishProject==null){
				hoursToFinishProject=0L;
			}
			int nowDurationHours=workedHours.intValue()+hoursToFinishProject.intValue();
			ClientLightDto client = new ClientLightDto(allProjects.get(i).getClient().getCompany(), new BusinessDto(allProjects.get(i).getClient().getBusiness().getArea()));
			projectsList.add(new ProjectDto(allProjects.get(i).getTitle(), allProjects.get(i).getIdProject(), client, allProjects.get(i).getBeginDate(), allProjects.get(i).getEndDate(), allProjects.get(i).getBudget(), allProjects.get(i).getStage().getStage(),
					managerName, managerEmail, cpi, spi, allProjects.get(i).getDescription(), allProjects.get(i).getTipology().getTipology(), percentageExecuted, initialDurationHours.intValue(), nowDurationHours, workedHours.intValue()));
		}
		return projectsList;
	}

	private Integer calculatePercentageExecuted(Project project) {
		List<Task> projectTasks = projectDao.getProjectTasks(project.getId());
		if (projectTasks.size()==0) {
			return null;
		}
		int projectDurationEstimate=0;
		int projectHoursWorked=0;
		for (int i=0; i<projectTasks.size(); i++) {
			projectDurationEstimate= projectDurationEstimate+taskService.getTaskEstimateDuration(projectTasks.get(i));
			projectHoursWorked= projectHoursWorked+taskService.getWorkedHoursInTask(projectTasks.get(i));
		}
		return projectHoursWorked*100/projectDurationEstimate;
	}


	//TODO remover este METODO ??????
	private List<String> getUserRolesString(String email) {
		User user = userDao.getUserWithEmail(email);
		List<Role> userRoles = userDao.getUserRoles(user);
		List<String> userRolesString = new ArrayList<String>();
		for (int i=0; i<userRoles.size(); i++) {
			userRolesString.add(userRoles.get(i).getRole());
		}
		return userRolesString;
	}

	private boolean isUserInRole(String userEmail, String role) {
		User userWithEmail = userDao.getUserWithEmail(userEmail);
		List<Role> userRoles = userDao.getUserRoles(userWithEmail);
		for (int i=0; i<userRoles.size(); i++) {
			if (role.equals(userRoles.get(i).getRole())) {
				return true;
			}
		}
		return false;
	}

	private boolean isUserProjectManager(Project project, String email) {
		User actualManager = projectDao.getActualManager(project);
		if (actualManager!=null) {
			if (actualManager.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}











}
