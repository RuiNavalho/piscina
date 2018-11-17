package pt.piscina.ds.services;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;

import io.jsonwebtoken.Claims;
import pt.piscina.ds.daos.AllocationDao;
import pt.piscina.ds.daos.ProjectDao;
import pt.piscina.ds.daos.SkillDao;
import pt.piscina.ds.daos.TaskDao;
import pt.piscina.ds.daos.TaskstageDao;
import pt.piscina.ds.daos.TasktypeDao;
import pt.piscina.ds.daos.UserDao;
import pt.piscina.ds.entities.Allocation;
import pt.piscina.ds.entities.Project;
import pt.piscina.ds.entities.Role;
import pt.piscina.ds.entities.Skill;
import pt.piscina.ds.entities.Task;
import pt.piscina.ds.entities.Taskstage;
import pt.piscina.ds.entities.Tasktype;
import pt.piscina.ds.entities.Taskwork;
import pt.piscina.ds.entities.User;
import pt.piscina.ds.security.MyJwt;
import pt.piscina.ds.util.InternalError;
import pt.piscina.ds.util.TimeCalc;
import pt.piscina.ds.util.Trimmer;
import pt.uc.dei.itf.dtos.AllocationDto;
import pt.uc.dei.itf.dtos.ChartUserAllocations;
import pt.uc.dei.itf.dtos.TaskLightDto;
import pt.uc.dei.itf.dtos.TaskNewDto;
import pt.uc.dei.itf.dtos.TaskPrecedence;
import pt.uc.dei.itf.dtos.UserAllocationGraph;
import pt.uc.dei.itf.errors.ErrorMessage;


@Stateless
public class TaskService implements Serializable{

	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger.getLogger(TaskService.class);

	@Inject
	private TaskDao taskDao;

	@Inject
	private ProjectDao projectDao;

	@Inject
	private	SkillDao skillDao;

	@Inject
	private	TaskstageDao taskstageDao;

	@Inject
	private	TasktypeDao tasktypeDao;

	@Inject
	private	UserDao userDao;

	@Inject
	private	UserService userService;

	@Inject
	private	AllocationDao allocationDao;

	public TaskService() {	
	}

	public Response createNewTask(TaskNewDto taskNewDto, String token) {
		try {
			StringBuilder sb = new StringBuilder();
			boolean sucess=true;
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);

			Project project = projectDao.getProjectByUniqueIdproject(taskNewDto.getIdProject());
			Claims claims = MyJwt.parseJWT(token);
			String email = claims.getSubject();
			sb.append("User with email ").append(email).append(" CREATING NEW Task ").append("\n");

			boolean isDirector = isUserInRole(email, "Director");

			if (!isDirector) {
				boolean isProjectManager = isUserProjectManager(project, email);
				if (!isProjectManager) {
					sucess=false;
					sb.append("\t").append("Failed Because: ").append(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER.toString());
					logger.info("FAILED - "+sb.toString());
					errorMessages.add(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER);
					return statusNOK.entity(errorMessages).build();
				}
			}

			String description = Trimmer.clean(taskNewDto.getDescription());
			String taskName = Trimmer.clean(taskNewDto.getTaskName());
			if (taskName.equals("") ||description.equals("")) {
				sucess=false;
				errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
			}

			//Task begin and end date must not be before and after project's begin and end dates, respectively
			if (project.getBeginDate().after(taskNewDto.getBeginDate())) {
				sucess=false;
				errorMessages.add(ErrorMessage.TASK_CANNOT_START_BEFORE_PROJECT);
			}

			if (taskNewDto.getBeginDate().after(taskNewDto.getEndDate())) {
				sucess=false;
				errorMessages.add(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE);
			}

			Timestamp tomorrow = TimeCalc.tomorrowMidnight();
			if (taskNewDto.getBeginDate().before(tomorrow)) {
				errorMessages.add(ErrorMessage.TASK_MUST_START_TOMORROW_OR_AFTER);
				sucess=false;
			}

			if (taskNewDto.getHourCost()<=0) {
				errorMessages.add(ErrorMessage.MUST_BE_POSITIVE_VALUE);
				sucess=false;
			}

			if (taskNewDto.getDurationHoursEstimate()<=0) {
				errorMessages.add(ErrorMessage.TASK_MUST_HAVE_POSIVE_DURATION);
				sucess=false;
			}

			if (project.getEndDate().before(taskNewDto.getEndDate())) {
				sucess=false;
				errorMessages.add(ErrorMessage.TASK_CANNOT_END_AFTER_PROJECT);
			}

			//Task must not have a duration in hours superior than duration D in days (between begin and end date) times 8 hours (a day)			

			if (sucess) {
				Skill skill = null;
				if (taskNewDto.getSkill()!=null) {
					skill = skillDao.findSkillWithName(taskNewDto.getSkill());
				}	
				Taskstage taskstage = taskstageDao.findTaskstageWithName(taskNewDto.getTaskstage());
				Tasktype tasktype = tasktypeDao.findTasktypeWithName(taskNewDto.getTasktype());	
				User creator = userDao.getUserWithEmail(taskNewDto.getCreatorEmail());	
				Task task = new Task(new Timestamp(taskNewDto.getBeginDate().getTime()), description, 
						taskNewDto.getDurationHoursEstimate(), new Timestamp(taskNewDto.getEndDate().getTime()), 
						taskNewDto.getHourCost(), taskName, project, skill, taskstage, tasktype, creator);	
				taskDao.persist(task);

				sb.append("\t").append("ID: ").append(task.getId());
				sb.append("\t").append("taskName: ").append(task.getTaskName());
				sb.append("\t").append("projectId: ").append(project.getId());
				sb.append("\t").append("beginDate: ").append(task.getBeginDate());
				sb.append("\t").append("endDate: ").append(task.getEndDate());
				sb.append("\t").append("description: ").append(task.getDescription());
				sb.append("\t").append("initialDurationHoursEstimate: ").append(task.getInitialDurationHoursEstimate());
				sb.append("\t").append("hourCost: ").append(task.getHourCost());
				sb.append("\t").append("remainingHours: ").append(task.getRemainingHours());
				sb.append("\t").append("tasktype: ").append(task.getTasktype().getTaskType());
				sb.append("\t").append("taskstage: ").append(task.getTaskstage().getTaskStage());
				sb.append("\t").append("skill: ").append(skill);
				logger.info("SUCESS - "+sb.toString());


				return Response.ok(task.getId()).build();
			} else {
				sb.append("\t").append("Motives for Failure: ").append("\n");
				for (int i=0; i<errorMessages.size(); i++) {
					sb.append("\t").append(errorMessages.get(i).toString());
				}
				logger.info("FAILED - "+sb.toString());
				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getTaskAllocations(String taskId) {
		try {
			Task task = taskDao.find(Integer.parseInt(taskId));
			List<Allocation> all = taskDao.findTaskAllocations(task);
			List<AllocationDto> list = getAllocationListDto(all);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public User getTaskCurrentResource(Task task) {
		try {
			List<Allocation> allocList;
			allocList = taskDao.findTaskAllocations(task);
			User worker=null;
			if (allocList.size()!=0) {
				worker = allocList.get(0).getUser();
			}
			for (int i=1; i<allocList.size(); i++) {
				if (allocList.get(i).getEndDate().after(allocList.get(i-1).getEndDate())) {
					worker = allocList.get(i).getUser();
				}
			}
			return worker;
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public int getExecutedPercentageEstimate(Task task) {
		List<Taskwork> taskTaskwork = taskDao.getTaskTaskwork(task);
		if (taskTaskwork.size()==0) {
			return 0;
		}
		Long totalHoursWorked=taskDao.findWorkedHours(task);
		int totalHoursEstimateDuration=totalHoursWorked.intValue() +task.getRemainingHours();
		return totalHoursWorked.intValue()*100/totalHoursEstimateDuration;
	}

	public int getWorkedHoursInTask (Task task) {
		List<Taskwork> taskTaskwork = taskDao.getTaskTaskwork(task);
		if (taskTaskwork.size()==0) {
			return 0;
		}
		int totalHoursWorked=0;
		for (int i=0; i<taskTaskwork.size(); i++) {
			totalHoursWorked=totalHoursWorked+taskTaskwork.get(i).getHoursWorked();
		}
		return totalHoursWorked;
	}

	public int getTaskEstimateDuration(Task task) {
		List<Taskwork> taskTaskwork = taskDao.getTaskTaskwork(task);
		if (taskTaskwork.size()==0) {
			return task.getInitialDurationHoursEstimate();
		}
		int totalHoursWorked=0;
		for (int i=0; i<taskTaskwork.size(); i++) {
			totalHoursWorked=totalHoursWorked+taskTaskwork.get(i).getHoursWorked();
		}
		return totalHoursWorked+task.getRemainingHours();
	}

	public Response updateTask(TaskLightDto task, String token) {
		try {
			Claims claims = MyJwt.parseJWT(token);
			String email = claims.getSubject();
			//Messaged to be logged
			StringBuilder sb = new StringBuilder();
			Task taskToUpdate = taskDao.find(task.getTaskId());
			sb.append("User with email ").append(email).append(" UPDATING Task ID: "+taskToUpdate.getId());

			boolean sucess=true;
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();

			boolean isDirector = isUserInRole(email, "Director");

			if (!isDirector) {
				boolean isProjectManager = isUserProjectManager(taskToUpdate.getProject(), email);
				if (!isProjectManager) {
					sucess=false;		
					errorMessages.add(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER);
					sb.append("\t").append("Motives for Failure: ").append("\n");
					for (int i=0; i<errorMessages.size(); i++) {
						sb.append("\t").append(errorMessages.get(i).toString());
					}
					logger.info("FAILED - "+sb.toString());
					return statusNOK.entity(errorMessages).build();
				}
			}

			boolean allocError=false;
			List<Allocation> allocs = taskDao.findTaskAllocations(taskToUpdate);
			for (int i=0; i<allocs.size() && !allocError; i++) {
				if (allocs.get(i).getBeginDate().before(task.getBeginDate()) || allocs.get(i).getEndDate().after(task.getEndDate())) {
					allocError=true;
					errorMessages.add(ErrorMessage.TASK_HAS_ALLOCATIONS_OUTSIDE_DATE_INTERVAL);
					sucess=false;
				}
			}

			boolean dateError;
			dateError = checkTaskNewBeginDateAgainstProjectDate(taskToUpdate, task.getBeginDate());
			if (dateError) {
				errorMessages.add(ErrorMessage.TASK_CANNOT_START_BEFORE_PROJECT);
				sucess=false;
			}
			dateError = checkTaskNewEndDateAgainstProjectDate(taskToUpdate, task.getEndDate());
			if (dateError) {
				errorMessages.add(ErrorMessage.TASK_CANNOT_END_AFTER_PROJECT);
				sucess=false;
			}

			Timestamp now = TimeCalc.todayMidnight();
			if (task.getEndDate().before(now)) {
				errorMessages.add(ErrorMessage.DATE_MUST_NOT_BE_PAST);
				sucess=false;
			}
			if (task.getEndDate().before(task.getBeginDate())) {
				errorMessages.add(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE);
				sucess=false;
			}

			if (task.getHourCost()<=0) {
				errorMessages.add(ErrorMessage.MUST_BE_POSITIVE_VALUE);
				sucess=false;
			}

			dateError = checkTaskNewBeginDateAgainstPreviousTasks(taskToUpdate, task.getEndDate());
			if (dateError) {
				errorMessages.add(ErrorMessage.TASK_MUST_END_AFTER_ALL_PRECEDENTS_START);
				sucess=false;
			}

			dateError = checkTaskNewEndDateAgainstNextTasks(taskToUpdate, task.getBeginDate());
			if (dateError) {
				errorMessages.add(ErrorMessage.TASK_MUST_BEGIN_BEFORE_ALL_NEXT_END);
				sucess=false;
			}

			if (sucess) {
				taskToUpdate.setBeginDate(new Timestamp(task.getBeginDate().getTime()));
				taskToUpdate.setEndDate(new Timestamp(task.getEndDate().getTime()));
				taskToUpdate.setHourCost(task.getHourCost());
				taskToUpdate.setTaskName(task.getTaskName());
				taskToUpdate.setDescription(task.getDescription());
				Taskstage findTaskstageWithName = taskstageDao.findTaskstageWithName(task.getTaskstage());
				taskToUpdate.setTaskstage(findTaskstageWithName);
				Tasktype findTasktypeWithName = tasktypeDao.findTasktypeWithName(task.getTasktype());
				taskToUpdate.setTasktype(findTasktypeWithName);
				if (task.getSkill().equals("Nenhum")) {
					taskToUpdate.setSkill(null);
					sb.append("\t").append("Skill: ").append(" ----> NULL");
				} else {
					Skill findSkillWithName = skillDao.findSkillWithName(task.getSkill());
					taskToUpdate.setSkill(findSkillWithName);
					sb.append("\t").append("Skill: ").append(" ----> ").append(task.getSkill());
				}
				sb.append("\t").append("taskName: ").append(taskToUpdate.getTaskName()).append(" ----> ").append(task.getTaskName());
				sb.append("\t").append("beginDate: ").append(taskToUpdate.getBeginDate()).append(" ----> ").append(task.getBeginDate());
				sb.append("\t").append("endDate: ").append(taskToUpdate.getEndDate()).append(" ----> ").append(task.getEndDate());
				sb.append("\t").append("description: ").append(taskToUpdate.getDescription()).append(" ----> ").append(task.getDescription());;
				sb.append("\t").append("hourCost: ").append(taskToUpdate.getHourCost()).append(" ----> ").append(task.getHourCost());
				sb.append("\t").append("remainingHours: ").append(taskToUpdate.getRemainingHours()).append(" ----> ").append(task.getHourCost());
				sb.append("\t").append("tasktype: ").append(taskToUpdate.getTasktype().getTaskType()).append(" ----> ").append(task.getTasktype());
				sb.append("\t").append("taskstage: ").append(taskToUpdate.getTaskstage().getTaskStage()).append(" ----> ").append(task.getTaskstage());	
				taskDao.merge(taskToUpdate);
				logger.info("SUCESS - "+sb.toString());
				return Response.ok(true).build();
			} else {
				sb.append("\t").append("Motives for Failure: ").append("\n");
				for (int i=0; i<errorMessages.size(); i++) {
					sb.append("\t").append(errorMessages.get(i).toString());
				}
				logger.info("FAILED - "+sb.toString());
				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	/**
	 * Task with next tasks (those whose this one is precedent) cannot start all next end
	 * @param task
	 * @param endDate
	 * @return
	 */
	private boolean checkTaskNewEndDateAgainstNextTasks(Task task, Date beginDate) {
		List<Task> taskNextTasks = taskDao.getTaskNextTasks(task);
		for (int i=0; i<taskNextTasks.size(); i++) {
			if (taskNextTasks.get(i).getEndDate().before(beginDate)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Task with precedences cannot end before all precedents start
	 * @param task
	 * @param endDate
	 * @return
	 */
	private boolean checkTaskNewBeginDateAgainstPreviousTasks(Task task, Date endDate) {
		List<Task> taskPrecedentTasks = taskDao.getTaskPrecedentTasks(task);
		for (int i=0; i<taskPrecedentTasks.size(); i++) {
			if (taskPrecedentTasks.get(i).getBeginDate().after(endDate)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkTaskNewEndDateAgainstProjectDate(Task task, Date newEndDate) {
		if (task.getProject().getEndDate().before(newEndDate)) {
			return true;
		}
		return false;
	}

	private boolean checkTaskNewBeginDateAgainstProjectDate(Task task, Date newBeginDate) {
		if (task.getProject().getBeginDate().after(newBeginDate)) {
			return true;
		}
		return false;
	}

	//REMOVE worker from task is create an invalid allocation - with enddate the day before begindate
	public Response removeWorkerFromTask(Integer allocationId, String token) {
		try {
			boolean success=true;
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);

			Allocation alloc = allocationDao.find(allocationId);
			Task task = alloc.getTask();
			String email = MyJwt.parseJWT(token).getSubject();
			//Must be Director or project manager to this operation
			StringBuilder sb = new StringBuilder();
			sb.append("User with email ").append(email).append(" removing worker ").append(alloc.getUser().getEmail());
			sb.append(" from alloc ID: ").append(allocationId);
			
			sb.append(" in Task ID ").append(task.getId());
			if (!isUserInRole(email, "Director") && 
					!isUserProjectManager(task.getProject(), email))  {
				success=false;
				errorMessages.add(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER);
				return statusNOK.entity(errorMessages).build();
			}
			Timestamp tomorrow = TimeCalc.tomorrowMidnight();
			if (alloc.getEndDate().before(tomorrow)) {
				success=false;
				errorMessages.add(ErrorMessage.CAN_ONLY_REMOVE_ALLOCATIONS_ENDING_IN_FUTURE);
			}

			//if user is already working in task, do NOT remove allocation
			List<Taskwork> tw = allocationDao.findAllTaskworks(alloc);
			if (tw.size()>0) {
				success=false;
				errorMessages.add(ErrorMessage.USER_HAS_WORKING_HOURS_IN_ALLOCATION);
			}	
			if (success) {
				Timestamp beginDate = alloc.getBeginDate();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Timestamp dayBefore = new Timestamp(sdf.parse(sdf.format( beginDate.getTime() - TimeUnit.DAYS.toMillis(1)) ).getTime());
				alloc.setEndDate(dayBefore);
				allocationDao.merge(alloc);
				logger.info("SUCESS - "+sb.toString());
				return Response.ok(true).build();
			} else {
				sb.append("\t").append("Motives for Failure: ").append("\n");
				for (int i=0; i<errorMessages.size(); i++) {
					sb.append("\t").append(errorMessages.get(i).toString());
				}
				logger.info("FAILED - "+sb.toString());
				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return InternalError.response;
		}
	}

	public Response allocateWorkertoTask(AllocationDto alloc) {
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("User with email ").append(alloc.getAllocatorEmail()).append(" Allocating user ").append(alloc.getWorkerEmail());
			sb.append(" to Task ID ").append(alloc.getTaskId());
			boolean success=true;
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);

			Task task = taskDao.find(alloc.getTaskId());

			//Must be Director or project manager to this operation
			if (!isUserInRole(alloc.getAllocatorEmail(), "Director") && 
					!isUserProjectManager(task.getProject(), alloc.getAllocatorEmail()))  {
				success=false;
				errorMessages.add(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER);
				sb.append("\t").append("Motives for Failure: ").append("\n");
				for (int i=0; i<errorMessages.size(); i++) {
					sb.append("\t").append(errorMessages.get(i).toString());
				}
				logger.info("FAILED - "+sb.toString());
				return statusNOK.entity(errorMessages).build();
			}

			//TODO verificar se a percentagem de alocacao ainda esta valida
			User worker = userDao.getUserWithEmail(alloc.getWorkerEmail());
			if (!isThisPercentageValidtoUserAllocation(worker, new Timestamp(alloc.getBeginDate().getTime()), new Timestamp(alloc.getEndDate().getTime()), alloc.getAllocPercentage())) { 
				success=false;
				errorMessages.add(ErrorMessage.INVALID_PERCENTAGE_ALLOCATION);
			}


			Timestamp tomorrow = TimeCalc.tomorrowMidnight();
			Timestamp begin = new Timestamp(alloc.getBeginDate().getTime());
			Timestamp end = new Timestamp(alloc.getEndDate().getTime());

			//allocation dates must not be outside task dates
			if (task.getBeginDate().after(begin) || task.getEndDate().before(end)) {
				success=false;
				errorMessages.add(ErrorMessage.TASK_OUTSIDE_DATES);
			}

			if (alloc.getBeginDate().after(alloc.getEndDate())) {
				success=false;
				errorMessages.add(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE);
			}

			//we can only allocate workers to future dates
			if (begin.before(tomorrow)) {
				success=false;
				errorMessages.add(ErrorMessage.DATE_MUST_BE_FUTURE);
			}

			List<Allocation> ta = taskDao.findTaskAllocations(task);
			boolean error=false;

			//checks if time interval is valid.
			//NOT VALID if there is already a valid allocation between this dates
			for (int i=0; i<ta.size() && !error; i++) {
				//checks only valid allocations: where end date is not before begin date
				if (!ta.get(i).getEndDate().before(ta.get(i).getBeginDate())) {
					if ( !( begin.after(ta.get(i).getEndDate()) || begin.before(ta.get(i).getBeginDate())) ||
							!( end.after(ta.get(i).getEndDate()) || end.before(ta.get(i).getBeginDate())) ||
							!( begin.after(ta.get(i).getBeginDate()) || end.before(ta.get(i).getEndDate()))) {
						error=true;
						success=false;
						errorMessages.add(ErrorMessage.ALLOCATIONS_ALREADY_IN_INTERVAL);
					}
				}
			}
			sb.append("\t").append("AllocPercentage: ").append(alloc.getAllocPercentage());
			sb.append("\t").append("begin: ").append(begin);
			sb.append("\t").append("end: ").append(end);
			if (success) {
				Allocation newAllocation = new Allocation(alloc.getAllocPercentage(), begin, end, task, worker, null);
				allocationDao.persist(newAllocation);
				logger.info("SUCESS - "+sb.toString());
				return Response.ok(true).build();
			} else {
				sb.append("\t").append("Motives for Failure: ").append("\n");
				for (int i=0; i<errorMessages.size(); i++) {
					sb.append("\t").append(errorMessages.get(i).toString());
				}
				logger.info("FAILED - "+sb.toString());
				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response updateAllocationDates(AllocationDto dto, String token) {
		try {

			boolean success=true;
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);

			Allocation alloc = allocationDao.find(dto.getAllocationId());

			String email = MyJwt.parseJWT(token).getSubject();
			boolean isDirector = isUserInRole(email, "Director");
			StringBuilder sb = new StringBuilder();
			sb.append("User with email ").append(email).append(" updating Alocation Dates for User ").append(dto.getWorkerEmail());
			if (!isDirector) {
				boolean isProjectManager = isUserProjectManager(alloc.getTask().getProject(), email);
				if (!isProjectManager) {
					success=false;
					errorMessages.add(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER);
					return statusNOK.entity(errorMessages).build();
				}
			}

			Timestamp tomorrow = TimeCalc.tomorrowMidnight();
			if (dto.getBeginDate().before(alloc.getTask().getBeginDate()) || dto.getEndDate().after(alloc.getTask().getEndDate())) {
				success=false;
				errorMessages.add(ErrorMessage.ALLOCATION_CANNOT_BE_OUTSIDE_TASK_DATES);
			}
			if (dto.getBeginDate().after(dto.getEndDate())){
				success=false;
				errorMessages.add(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE);
			}
			//we can cannot permit to change allocations ending today or already ended
			if (alloc.getEndDate().before(tomorrow)) {
				success=false;
				errorMessages.add(ErrorMessage.CAN_NOT_CHANGE_ENDING_ALLOCATIONS);	
			} else {

				if (alloc.getEndDate().before(dto.getEndDate())) {
					//We want to extend end date of allocation - we must check if user has free alloc percentage in that extended time
					Timestamp newBeginPeriodToCheck = new Timestamp(alloc.getEndDate().getTime()+TimeUnit.DAYS.toMillis(1));
					Timestamp newEndPeriodToCheck = new Timestamp(dto.getEndDate().getTime());
					ChartUserAllocations cua = userService.createChartUserAllocations(alloc.getUser(), newBeginPeriodToCheck, newEndPeriodToCheck);
					if (cua.getFreePercentage()<dto.getAllocPercentage()) {
						success=false;
						errorMessages.add(ErrorMessage.USER_HAS_NOT_REQUIRED_PERCENTAGE_ALLOCATION_FOR_PERIOD);	
					}
					List<Allocation> allocsInInterval = taskDao.findAllocationsBetweenDates(alloc.getTask(), newBeginPeriodToCheck, newEndPeriodToCheck);
					boolean alocationAlreadyExists=false;

					for (int i=0; i<allocsInInterval.size() && !alocationAlreadyExists; i++) {
						if (allocsInInterval.get(i).getId()!=alloc.getId()) {
							alocationAlreadyExists=true;
						}
					}
					if (alocationAlreadyExists) {
						success=false;
						errorMessages.add(ErrorMessage.ALLOCATIONS_ALREADY_IN_INTERVAL);	
					}
				}
			}
			if (success) {
				alloc.setEndDate(new Timestamp(dto.getEndDate().getTime()));
				allocationDao.merge(alloc);
				logger.info("SUCESS - "+sb.toString());
				return Response.ok(true).build();
			} else {
				sb.append("\t").append("Motives for Failure: ").append("\n");
				for (int i=0; i<errorMessages.size(); i++) {
					sb.append("\t").append(errorMessages.get(i).toString());
				}
				logger.info("FAILED - "+sb.toString());
				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	private boolean isThisPercentageValidtoUserAllocation(User worker, Timestamp begin, Timestamp end, float perc) {
		UserAllocationGraph uag = userService.createUserAllocationGraph(worker,begin, end);
		uag.getFreeAllocationPercentage();
		return perc<=uag.getFreeAllocationPercentage();
	}

	public Response getAllTasks() {
		try {
			List<Task> findAll = taskDao.findAll();	
			List<TaskLightDto> list = getTaskListDto(findAll);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getMyWorkingTasks(String token) {
		try {
			String email = MyJwt.parseJWT(token).getSubject();
			List<Task> findMyWorkingTasks = taskDao.findMyWorkingTasks(email);	
			List<TaskLightDto> list = getTaskListDto(findMyWorkingTasks);
			return Response.ok(list).build();
		} catch (Exception e) {
			e.printStackTrace();
			return InternalError.response;
		}
	}

	public Response findPrecendentTasksList(String taskId) {
		try {
			Task task = taskDao.find(Integer.parseInt(taskId));
			List<Task> findMyWorkingTasks = taskDao.findPrecendentTasksList(task);	
			List<TaskLightDto> list = getTaskListDto(findMyWorkingTasks);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}	
	}

	public Response findTaskById(String taskId) {
		try {
			Task task = taskDao.find(Integer.parseInt(taskId));
			List<Task> listTasks = new ArrayList<Task>();
			listTasks.add(task);
			List<TaskLightDto> list = getTaskListDto(listTasks);
			return Response.ok(list.get(0)).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}	
	}

	public Response findPossiblePrecendentTasks(String taskId) {
		try {
			Task task = taskDao.find(Integer.parseInt(taskId));
			List<Task> findPossiblePrecendentTasks = taskDao.findPossiblePrecendentTasks(task);	
			List<TaskLightDto> list = getTaskListDto(findPossiblePrecendentTasks);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response assignTaskPrecendences(TaskPrecedence taskPrecedence, String token) {
		try {
			boolean sucess=true;
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);

			Claims claims = MyJwt.parseJWT(token);
			String email = claims.getSubject();
			StringBuilder sb = new StringBuilder();


			Task task = taskDao.find(taskPrecedence.getTask().getTaskId());
			sb.append("User with email ").append(email).append(" assignTaskPrecendences for taskId ").append(task.getId());
			List<TaskLightDto> precentTasks = taskPrecedence.getPrecentTasks();
			List<Task> possible = taskDao.findPossiblePrecendentTasks(task);
			List<Task> tasksBefore= new ArrayList<Task>();

			boolean isDirector = isUserInRole(email, "Director");

			Project project = task.getProject();

			if (!isDirector) {
				boolean isProjectManager = isUserProjectManager(project, email);
				if (!isProjectManager) {
					sucess=false;
					errorMessages.add(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER);
				}
			}

			//Quando a lista foi pedida as precendecias eram validas. No entanto, alguem poderia ter alterado as tasks e entretanto
			//as mesmas podem ja nao se validas. Novo check nas precedencias
			for (int i=0; i<precentTasks.size(); i++) {
				boolean possibleTask=false; 
				for (int j=0; j<possible.size(); j++) {
					if (precentTasks.get(i).getTaskId()==possible.get(j).getId()) {
						possibleTask=true;
					}
				}
				if (!possibleTask) {
					sucess=false;
					errorMessages.add(ErrorMessage.INVALID_PRECEDENCE);
				}
				Task precedent = taskDao.find(precentTasks.get(i).getTaskId());
				tasksBefore.add(precedent);
			}

			if (!sucess) {
				sb.append("\t").append("Motives for Failure: ").append("\n");
				for (int i=0; i<errorMessages.size(); i++) {
					sb.append("\t").append(errorMessages.get(i).toString());
				}
				logger.info("FAILED - "+sb.toString());
				return statusNOK.entity(errorMessages).build();
			}

			task.setTasksBefore(tasksBefore);
			taskDao.merge(task);
			logger.info("SUCESS - "+sb.toString());
			return Response.ok(true).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}


	public Response assignTaskPrecendence(TaskLightDto selectedTaskPrecedent, String token, String taskId) {
		try {
			boolean sucess=true;
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);

			Claims claims = MyJwt.parseJWT(token);
			String email = claims.getSubject();

			Task task = taskDao.find(Integer.parseInt(taskId));
			Task taskBefore = taskDao.find(selectedTaskPrecedent.getTaskId());
			List<Task> possible = taskDao.findPossiblePrecendentTasks(task);
			List<Task> tasksBefore= taskDao.findPrecendentTasksList(task);
			StringBuilder sb = new StringBuilder();
			sb.append("User with email ").append(email).append(" assigning PRECEDENCE taskID ");
			sb.append(taskBefore.getId()).append(" from TaskID ").append(task.getId());
			boolean isDirector = isUserInRole(email, "Director");

			Project project = task.getProject();

			if (!isDirector) {
				boolean isProjectManager = isUserProjectManager(project, email);
				if (!isProjectManager) {
					sucess=false;
					errorMessages.add(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER);
				}
			}

			//Quando a lista foi pedida as precendecias eram validas. No entanto, alguem poderia ter alterado as tasks e entretanto
			//as mesmas podem ja nao se validas. Novo check nas precedencias

			boolean possibleTask=false; 
			for (int j=0; j<possible.size() && !possibleTask; j++) {	
				if (taskBefore.getId()==possible.get(j).getId()) {
					possibleTask=true;
				}
			}

			if(!possibleTask){
				sucess=false;
				errorMessages.add(ErrorMessage.INVALID_PRECEDENCE);
			}

			boolean isAlreadyPrecedent=false; 
			for (int k=0; k<tasksBefore.size() && !isAlreadyPrecedent; k++) {
				if (taskBefore.getId()==tasksBefore.get(k).getId()) {
					isAlreadyPrecedent=true;
					sucess=false;
					errorMessages.add(ErrorMessage.INVALID_PRECEDENCE);
				}
			}

			possibleTask=false; 
			tasksBefore.add(taskBefore);


			if (!sucess) {
				sb.append("\t").append("Motives for Failure: ").append("\n");
				for (int i=0; i<errorMessages.size(); i++) {
					sb.append("\t").append(errorMessages.get(i).toString());
				}
				logger.info("FAILED - "+sb.toString());
				return statusNOK.entity(errorMessages).build();
			}

			task.setTasksBefore(tasksBefore);
			taskDao.merge(task);
			logger.info("SUCESS - "+sb.toString());
			return Response.ok(true).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response unAssignPrecedence(TaskLightDto selectedTaskPrecedent, String token, String taskId) {
		try {

			boolean sucess=true;
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);

			Claims claims = MyJwt.parseJWT(token);
			String email = claims.getSubject();

			Task task = taskDao.find(Integer.parseInt(taskId));
			Task taskBefore = taskDao.find(selectedTaskPrecedent.getTaskId());
			StringBuilder sb = new StringBuilder();
			sb.append("User with email ").append(email).append(" removing PRECEDENCE taskID ");
			sb.append(taskBefore.getId()).append(" from TaskID ").append(task.getId());
			boolean isDirector = isUserInRole(email, "Director");

			Project project = task.getProject();

			if (!isDirector) {
				boolean isProjectManager = isUserProjectManager(project, email);
				if (!isProjectManager) {
					sucess=false;
					errorMessages.add(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER);
				}
			}


			//Confirmar que a taskBefore ja nao existe
			List<Task> tasksBefore= taskDao.findPrecendentTasksList(task);

			boolean isTaskBefore=false; 
			for (int j=0; j<tasksBefore.size(); j++) {
				if (taskBefore.getId()==tasksBefore.get(j).getId()) {
					isTaskBefore=true;
				}
			}
			if (!isTaskBefore) {
				sucess=false;
				errorMessages.add(ErrorMessage.INVALID_PRECEDENCE);
			}

			tasksBefore.remove(taskBefore);
			if (!sucess) {
				sb.append("\t").append("Motives for Failure: ").append("\n");
				for (int i=0; i<errorMessages.size(); i++) {
					sb.append("\t").append(errorMessages.get(i).toString());
				}
				logger.info("FAILED - "+sb.toString());
				return statusNOK.entity(errorMessages).build();
			}

			task.setTasksBefore(tasksBefore);
			taskDao.merge(task);
			logger.info("SUCESS - "+sb.toString());
			return Response.ok(true).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	private List<AllocationDto> getAllocationListDto(List<Allocation> all) {
		List<AllocationDto> list = new ArrayList<AllocationDto>();
		for (int i=0; i<all.size(); i++) {
			list.add(new AllocationDto(all.get(i).getAllocPercentage(), all.get(i).getTask().getId(),
					all.get(i).getUser().getEmail(),all.get(i).getUser().getFullName(), null, all.get(i).getBeginDate(), all.get(i).getEndDate(), all.get(i).getId(), all.get(i).getTask().getEndDate(), null, all.get(i).getTask().getProject().getIdProject()));
		}
		return list;
	}

	private List<TaskLightDto> getTaskListDto(List<Task> findAll) {
		List<TaskLightDto> tasksList = new ArrayList<TaskLightDto>();
		for (int i=0; i<findAll.size(); i++) {
			int executedPercentageEstimate = getExecutedPercentageEstimate(findAll.get(i));
			User worker = getTaskCurrentResource(findAll.get(i));
			User manager = projectDao.findActualManager(findAll.get(i).getProject());
			TaskLightDto taskLightDto=null;
			Allocation lastAllocation = taskDao.findLastTaskAllocation(findAll.get(i));
			Float allocPercentage=null;
			Date beginAllocationDate=null;
			Date endAllocationDate=null;
			Task task = findAll.get(i);
			Long workedHours = taskDao.findWorkedHours(task);
			if (workedHours==null) {
				workedHours=0L;
			}
			int nowTaskDuration = task.getRemainingHours()+workedHours.intValue();

			if (lastAllocation!=null) {
				allocPercentage=lastAllocation.getAllocPercentage();
				beginAllocationDate=lastAllocation.getBeginDate();
				endAllocationDate=lastAllocation.getEndDate();
			}
			Skill skill= findAll.get(i).getSkill();
			String skillString=null;
			if (skill!=null) {
				skillString= skill.getSkill();
			}

			if (worker!=null) {
				taskLightDto= new TaskLightDto(findAll.get(i).getId(), findAll.get(i).getTaskName(), 
						findAll.get(i).getInitialDurationHoursEstimate(), nowTaskDuration, findAll.get(i).getBeginDate(), findAll.get(i).getEndDate(), 
						executedPercentageEstimate, worker.getFullName(), worker.getEmail(), manager.getEmail(),findAll.get(i).getProject().getTitle(),
						findAll.get(i).getProject().getIdProject(),findAll.get(i).getDescription(),
						skillString, findAll.get(i).getTaskstage().getTaskStage(), findAll.get(i).getTasktype().getTaskType(),
						findAll.get(i).getHourCost(), allocPercentage, beginAllocationDate, endAllocationDate, workedHours.intValue());	
			} else {
				taskLightDto= new TaskLightDto(findAll.get(i).getId(), findAll.get(i).getTaskName(), 
						findAll.get(i).getInitialDurationHoursEstimate(), nowTaskDuration, findAll.get(i).getBeginDate(), 
						findAll.get(i).getEndDate(), executedPercentageEstimate, null, null, manager.getEmail(),findAll.get(i).getProject().getTitle(),
						findAll.get(i).getProject().getIdProject(), findAll.get(i).getDescription(), 
						skillString, findAll.get(i).getTaskstage().getTaskStage(), findAll.get(i).getTasktype().getTaskType(),
						findAll.get(i).getHourCost(), allocPercentage, beginAllocationDate, endAllocationDate, workedHours.intValue());	
			}
			tasksList.add(taskLightDto);
		}
		return tasksList;
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
