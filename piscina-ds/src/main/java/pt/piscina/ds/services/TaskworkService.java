package pt.piscina.ds.services;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;

import pt.piscina.ds.daos.AllocationDao;
import pt.piscina.ds.daos.AttachmentDao;
import pt.piscina.ds.daos.ProjectDao;
import pt.piscina.ds.daos.TaskDao;
import pt.piscina.ds.daos.TaskworkDao;
import pt.piscina.ds.daos.UserDao;
import pt.piscina.ds.entities.Allocation;
import pt.piscina.ds.entities.Attachment;
import pt.piscina.ds.entities.Project;
import pt.piscina.ds.entities.Role;
import pt.piscina.ds.entities.Task;
import pt.piscina.ds.entities.Taskwork;
import pt.piscina.ds.entities.User;
import pt.piscina.ds.security.MyJwt;
import pt.piscina.ds.util.InternalError;
import pt.piscina.ds.util.TimeCalc;
import pt.piscina.ds.util.Trimmer;
import pt.uc.dei.itf.dtos.AttachmentDto;
import pt.uc.dei.itf.dtos.TaskworkDto;
import pt.uc.dei.itf.dtos.TaskworkNewDto;
import pt.uc.dei.itf.errors.ErrorMessage;

@Stateless
public class TaskworkService implements Serializable{

	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(TaskworkService.class);

	@Inject
	private TaskworkDao taskworkDao;

	@Inject
	private TaskDao taskDao;

	@Inject
	private UserService userService;

	@Inject
	private AllocationDao allocationDao;

	@Inject
	private	AttachmentDao attachmentDao;

	@Inject
	private	ProjectDao projectDao;

	@Inject
	private	UserDao userDao;

	public TaskworkService() {
	}

	public Response createNewTaskwork(TaskworkNewDto newTw, String token) {
		try {
			StringBuilder sb = new StringBuilder();
			String email = MyJwt.parseJWT(token).getSubject();
			sb.append("User with email ").append(email).append(" CREATING NEW Taskwork for Allocation ID ");
			sb.append(newTw.getAllocationId()).append(" Comments: ").append(newTw.getComments());
			sb.append(" HoursWorked: ").append(newTw.getHoursWorked()).append(" RemainingHours: ").append(newTw.getRemainingHours());
			sb.append(" ExpectedRemainingHours: ").append(newTw.getExpectedRemainingHours());
			sb.append(" Number of Documents: ").append(newTw.getDocumentNames().size());
			for (int i=0; i<newTw.getDocumentNames().size(); i++) {
				sb.append("Document ").append(i+1).append(": ").append(newTw.getDocumentNames().get(i));
			}
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			boolean sucess=true;
			Allocation alloc = allocationDao.find(newTw.getAllocationId());
			Task task = alloc.getTask();

			// comments must not be null nor empty
			String comments = Trimmer.clean(newTw.getComments());
			if (comments.equals("")) {		
				sucess=false;
				errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
				return statusNOK.entity(errorMessages).build();	
			}

			// worked hours must be a positive value
			if (newTw.getHoursWorked()<1) {
				sucess=false;
				errorMessages.add(ErrorMessage.MUST_BE_POSITIVE_VALUE);
				return statusNOK.entity(errorMessages).build();	
			}

			// remaining hours is task must not be a negative value
			if (newTw.getRemainingHours()<0) {
				sucess=false;
				errorMessages.add(ErrorMessage.MUST_BE_NON_NEGATIVE_VALUE);
				return statusNOK.entity(errorMessages).build();	
			}

			//worked hours must not exceed remaining hours in allocations
			int max = userService.calculateMaximumRemainingWorkHoursInAllocation(alloc);
			if (newTw.getHoursWorked()>max) {
				sucess=false;
				errorMessages.add(ErrorMessage.WORKED_HOURS_EXCEEDS_MAX_ALLOWED);
				return statusNOK.entity(errorMessages).build();	
			}

			if (sucess) {
				Timestamp now = TimeCalc.todayMidnight();
				Taskwork newTaskwork = new Taskwork(comments, now, newTw.getHoursWorked(), newTw.getRemainingHours(), 
						newTw.getExpectedRemainingHours(), alloc, task);
				taskworkDao.persist(newTaskwork);
				List<byte[]> docs = newTw.getDocuments();
				List<String> names = newTw.getDocumentNames();
				for (int i=0; i<docs.size(); i++) {
					Attachment att = new Attachment(docs.get(i), newTaskwork, names.get(i));
					att.setDocument(docs.get(i));
					attachmentDao.persist(att);
				}

				//if task has ZERO remaining hours - we free user from allocation setting allocation end date to Today
				if (newTw.getRemainingHours()==0) {
					alloc.setEndDate(now);
					allocationDao.merge(alloc);

				}
				task.setRemainingHours(newTw.getRemainingHours());
				taskDao.merge(task);
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

	public Response getMyWorkingHours(String token) {
		try {
			String email = MyJwt.parseJWT(token).getSubject();
			List<Taskwork> all = taskworkDao.findMyWorkingHours(email);
			List<TaskworkDto> list = getAllTaskworkDto(all);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}


	public Response getWorkingHoursInTask(String taskId, String token) {
		try {
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			boolean success=true;
			Task task = taskDao.find(Integer.parseInt(taskId));
			String email = MyJwt.parseJWT(token).getSubject();
			Project project = task.getProject();
			boolean isDirector = isUserInRole(email, "Director");
			if (!isDirector) {
				User manager = projectDao.findActualManager(project);
				if (!manager.getEmail().equals(email)) {
					success=false;
					errorMessages.add(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER);
				}
			}
			if (success) {
				List<Taskwork> all = taskworkDao.findWorkingHoursInTask(task.getId());
				List<TaskworkDto> list = getAllTaskworkDto(all);
				return Response.ok(list).build();
			} else {
				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getMyWorkingHoursInTask(String taskId, String token) {
		try {
			int taskId1 = Integer.parseInt(taskId);
			String email = MyJwt.parseJWT(token).getSubject();
			List<Taskwork> all = taskworkDao.findMyWorkingHoursInTask(taskId1, email);
			List<TaskworkDto> list = getAllTaskworkDto(all);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}


	public Response getWorkingHoursInProject(String projectId, String token) {
		try {
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			boolean success=true;
			String email = MyJwt.parseJWT(token).getSubject();
			Project project = projectDao.getProjectByUniqueIdproject(projectId);
			boolean isDirector = isUserInRole(email, "Director");
			if (!isDirector) {
				User manager = projectDao.findActualManager(project);
				if (!manager.getEmail().equals(email)) {
					success=false;
					errorMessages.add(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER);
				}
			}
			if (success) {
				List<Taskwork> all = taskworkDao.findWorkingHoursInProject(projectId);
				List<TaskworkDto> list = getAllTaskworkDto(all);
				return Response.ok(list).build();
			} else {

				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getMyWorkingHoursInProject(String projectId, String token) {
		try {
			String email = MyJwt.parseJWT(token).getSubject();
			List<Taskwork> all = taskworkDao.findMyWorkingHoursInProject(projectId, email);
			List<TaskworkDto> list = getAllTaskworkDto(all);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// Private Auxiliary Methods ///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////


	private List<TaskworkDto> getAllTaskworkDto(List<Taskwork> all) {
		List<TaskworkDto> list = new ArrayList<TaskworkDto>();
		for (int i=0; i<all.size(); i++) {
			Taskwork tw =all.get(i);
			Allocation a = tw.getAllocation();
			Task t = a.getTask();
			User u = a.getUser();
			Project p = t.getProject();
			List<Attachment> attachments = taskworkDao.findAttachments(tw);
			List<AttachmentDto> att = null;
			if (attachments!=null && attachments.size()>0) {
				att = new ArrayList<AttachmentDto>();
				for (int j=0; j<attachments.size(); j++) {
					att.add(new AttachmentDto(attachments.get(j).getDocument(), attachments.get(j).getDocumentName()));
				}

			} 
			list.add(new TaskworkDto(tw.getHoursWorked(), tw.getRemainingHours(), tw.getExpectedRemainingHours(), a.getId(), t.getId(), u.getFullName(), u.getEmail(), p.getIdProject(), p.getTitle(), t.getTaskName(), tw.getCreationDate(), att, tw.getComments()));
		}
		return list;
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




}
