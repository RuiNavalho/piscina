package pt.piscina.ds.services;

import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import pt.piscina.ds.daos.AllocationDao;
import pt.piscina.ds.daos.HolidayDao;
import pt.piscina.ds.daos.RoleDao;
import pt.piscina.ds.daos.SkillDao;
import pt.piscina.ds.daos.StageDao;
import pt.piscina.ds.daos.TaskDao;
import pt.piscina.ds.daos.TaskstageDao;
import pt.piscina.ds.daos.UserDao;
import pt.piscina.ds.entities.Allocation;
import pt.piscina.ds.entities.Holiday;
import pt.piscina.ds.entities.Project;
import pt.piscina.ds.entities.Role;
import pt.piscina.ds.entities.Skill;
import pt.piscina.ds.entities.Stage;
import pt.piscina.ds.entities.Task;
import pt.piscina.ds.entities.Taskstage;
import pt.piscina.ds.entities.User;
import pt.piscina.ds.security.MyJwt;
import pt.piscina.ds.util.Cripter;
import pt.piscina.ds.util.InternalError;
import pt.piscina.ds.util.MyProjectMail;
import pt.piscina.ds.util.TimeCalc;
import pt.piscina.ds.util.Trimmer;
import pt.uc.dei.itf.agenda.DailyAgenda;
import pt.uc.dei.itf.agenda.SimpleAllocDto;
import pt.uc.dei.itf.agenda.SimpleRegisterDto;
import pt.uc.dei.itf.dtos.ChartUserAllocations;
import pt.uc.dei.itf.dtos.DatePercDto;
import pt.uc.dei.itf.dtos.GraphTimeAllocPerc;
import pt.uc.dei.itf.dtos.RoleDto;
import pt.uc.dei.itf.dtos.SessionDto;
import pt.uc.dei.itf.dtos.SkillDto;
import pt.uc.dei.itf.dtos.UserAllocationGraph;
import pt.uc.dei.itf.dtos.UserLoggedDto;
import pt.uc.dei.itf.dtos.UserLoginDto;
import pt.uc.dei.itf.dtos.UserNewDto;
import pt.uc.dei.itf.dtos.UserProfileDto;
import pt.uc.dei.itf.dtos.UserUpdateSelfDto;
import pt.uc.dei.itf.dtos.WorkRegisterDto;
import pt.uc.dei.itf.errors.ErrorMessage;

@Stateless
public class UserService implements Serializable{

	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(UserService.class);
	private final String CLIENT_ID="1098661566384-qutbcacnfcp8in0702cs9envseodo6gp.apps.googleusercontent.com";

	private final String URI_RECOVER_PASSW="http://localhost:8080/aclgomes-rnavalho-project-ws/recover_passw.xhtml?token=";
	private final String URI_CONFIRM_REGISTRATION="http://localhost:8080/aclgomes-rnavalho-project-ws/registration_confirmation.xhtml?token=";

	@Inject
	private UserDao userDao;

	@Inject
	private RoleDao roleDao;

	@Inject
	private SkillDao skillDao;

	@Inject
	private TaskDao taskDao;

	@Inject
	private	AllocationDao allocationDao;

	@Inject
	private	StageDao stageDao;

	@Inject
	private TaskstageDao taskstageDao;

	@Inject
	private	HolidayDao holidayDao;

	@Inject
	private MyProjectMail myProjectMail;

	public UserService() {
	}

	public Response updateUserProfile(UserProfileDto userDto, String token) { 
		try {
			StringBuilder sb = new StringBuilder();
			String email=userDto.getEmail();
			String email1 = MyJwt.parseJWT(token).getSubject();
			sb.append("User with email ").append(email1).append(" updating Profile for user with email "+email);

			boolean sucess =true;
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);

			User userToUpdate = userDao.getUserWithEmail(email);


			String fullName = Trimmer.clean(userDto.getFullName());
			String passw = Trimmer.clean(userDto.getPassw());
			sb.append("\t").append("fullName: ").append(userToUpdate.getFullName()).append(" ----> ").append(fullName);
			if (fullName.equals("")) {
				sucess=false;
				errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
			}
			if (userDto.getRoles()==null || userDto.getRoles().size()==0) {
				sucess=false;
				errorMessages.add(ErrorMessage.USER_MUST_HAVE_A_ROLE);
			}

			boolean projManager=userDao.isUserProjectManager(userToUpdate);
			if (projManager && userDto.getRoles()!=null) {
				boolean rolesOK=false;
				for (int i=0; i<userDto.getRoles().size(); i++) {
					if (userDto.getRoles().get(i).getRole().equals("Director") || userDto.getRoles().get(i).getRole().equals("User")) {
						rolesOK=true;
					}
				}
				if (!rolesOK) {
					sucess=false;
					errorMessages.add(ErrorMessage.USER_HAS_MANAGED_PROJECTS_SO_MUST_BE_USER_OR_DIRECTOR);
				}

			}

			// if user has "Visitor" role, then that role must be the only Role for that user
			if (userDto.getRoles()!=null) {
				if (userDto.getRoles().size()>1) {
					for (int i=0; i<userDto.getRoles().size(); i++) {
						if (userDto.getRoles().get(i).getRole().equals("Visitor")){
							sucess=false;
							errorMessages.add(ErrorMessage.USER_VISITOR_MUST_HAVE_A_SINGLE_ROLE);
						}
					}
				}
			}

			if (sucess) {
				if (!passw.equals("")) {
					String salt = generateSalt();
					String passAndSalt = passw+salt;
					String hashedValue = Cripter.cryptMyPass(passAndSalt);
					userToUpdate.setPassw(hashedValue);
					userToUpdate.setSalt(salt);
				}
				sb.append("\t").append("active: ").append(userToUpdate.getActive()).append(" ----> ").append(userDto.isActive());
				userToUpdate.setActive(userDto.isActive());
				userToUpdate.setFullName(fullName);
				userToUpdate.setPhoto(userDto.getPhoto());

				List<Role> myRoles = getRoleList(userDto.getRoles());
				userToUpdate.setRoles(myRoles);
				List<Skill> mySkills = getSkillList(userDto.getSkills());
				//userToUpdate.setSkills(mySkills);
				userDao.merge(userToUpdate);
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


	public Response updateSelfProfile(UserUpdateSelfDto userUpdateSelfDto, String token) {
		try {

			boolean sucess =true;
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			String email = MyJwt.parseJWT(token).getSubject();	
			StringBuilder sb = new StringBuilder();
			sb.append("User with email ").append(email).append(" updating SELF PROFILE");

			User user = userDao.getUserWithEmail(email);
			String username = Trimmer.clean(userUpdateSelfDto.getFullName());
			if (username.equals("")) {
				sucess=false;
				errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
			}
			String passw = userUpdateSelfDto.getPassw();
			if (passw!=null) {
				if (Trimmer.clean(userUpdateSelfDto.getFullName()).equals("")) {
					sucess=false;
					errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
				}
				sb.append("\t").append("Password updated");
			}
			sb.append("\t").append("active: ").append(user.getFullName()).append(" ----> ").append(username);
			if (sucess) {
				user.setFullName(username);
				if (passw!=null) {
					String salt = generateSalt();
					String passAndSalt = userUpdateSelfDto.getPassw()+salt;
					String hashedValue = Cripter.cryptMyPass(passAndSalt);
					user.setSalt(salt);
					user.setPassw(hashedValue);
				}
				if (userUpdateSelfDto.getPhoto()!=null) {
					user.setPhoto(userUpdateSelfDto.getPhoto());
					sb.append("\t").append("Photo updated");
				}
				userDao.merge(user);
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

	public Response updateUserRoles(List<String> roles, String email) {
		try {
			User user = userDao.getUserWithEmail(email);
			List<Role> rolesToUpdate = new ArrayList<Role>();
			for (int i=0; i<roles.size(); i++) {
				rolesToUpdate.add(roleDao.findRoleWithName(roles.get(i)));
			}
			user.setRoles(rolesToUpdate);
			userDao.merge(user);
			return Response.ok().build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response loginUser(UserLoginDto userLoginDto) {
		System.out.println("loginUser DS 1");
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("User with email ").append(userLoginDto.getEmail()).append(" LOGIN - NOT Google");
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			boolean sucess=true;
			/*
			boolean emailExists=userDao.verifyEmailExistence(userLoginDto.getEmail());

			if (emailExists) {
				User user = userDao.getUserWithEmail(userLoginDto.getEmail());
				if (!user.getActive()) {
					errorMessages.add(ErrorMessage.USER_NOT_ACTIVE);
					return statusNOK.entity(errorMessages).build();	
				}
			} else {
				sucess=false;
				errorMessages.add(ErrorMessage.LOGIN_FAILED);
			}
			String salt = userDao.getUserSalt(userLoginDto.getEmail());
			String passAndSalt = userLoginDto.getPassword()+salt;
			String hashedValue = Cripter.cryptMyPass(passAndSalt);
			sucess = userDao.loginUser(userLoginDto.getEmail(), hashedValue);
*/
			if (sucess) {
				SessionDto sessionDto = new SessionDto();
				String token = MyJwt.createJWT(userLoginDto.getEmail(), getUserRolesDto(userLoginDto.getEmail()));
				UserLoggedDto userLoggedDto = getLoggedUser(token);
				sessionDto.setToken(token);
				sessionDto.setLoggedUser(userLoggedDto);
				/*
				List<RoleDto> roles = userLoggedDto.getRoles();
				boolean roleDirectOrUser=false;
				for (int i=0; i<roles.size(); i++) {
					if (roles.get(i).getRole().equals("Director") || roles.get(i).getRole().equals("User")) {
						roleDirectOrUser=true;
					}
				}
				if (roleDirectOrUser) {
					Timestamp today = TimeCalc.todayMidnight();
					DailyAgenda dailyAgenda = generateDailyAgenda(today, userLoggedDto.getEmail());
					List<SimpleRegisterDto> lastDayRegisterList = generateRegisterList(today, userLoggedDto.getEmail());
					sessionDto.setDailyAgenda(dailyAgenda);
					sessionDto.setTodayRegisterList(lastDayRegisterList);
				}
				logger.info("SUCESS - "+sb.toString());
				*/
				return Response.ok(sessionDto).build();
			} else {
				sb.append("\t").append("Motives for Failure: ").append("\n");
				for (int i=0; i<errorMessages.size(); i++) {
					sb.append("\t").append(errorMessages.get(i).toString());
				}
				logger.info("FAILED - "+sb.toString());
				errorMessages.add(ErrorMessage.LOGIN_FAILED);
				return statusNOK.entity(errorMessages).build();	
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response loginWithGoogle(String googleToken) {
		try {
			StringBuilder sb = new StringBuilder();

			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			boolean sucess=true;
			String email=verifyLoginWithGoogle(googleToken);
			sb.append("User with email ").append(email).append(" LOGIN - WITH Google");
			if (email!=null) {
				User user = userDao.getUserWithEmail(email);
				if (!user.getActive()) {
					errorMessages.add(ErrorMessage.USER_NOT_ACTIVE);
					return statusNOK.entity(errorMessages).build();	
				}
			} else {
				sucess=false;
				errorMessages.add(ErrorMessage.LOGIN_FAILED);
			}

			if (sucess) {
				SessionDto sessionDto = new SessionDto();
				String token = MyJwt.createJWT(email, getUserRolesDto(email));
				UserLoggedDto userLoggedDto = getLoggedUser(token);
				sessionDto.setToken(token);
				sessionDto.setLoggedUser(userLoggedDto);
				List<RoleDto> roles = userLoggedDto.getRoles();
				boolean roleDirectOrUser=false;
				for (int i=0; i<roles.size(); i++) {
					if (roles.get(i).getRole().equals("Director") || roles.get(i).getRole().equals("User")) {
						roleDirectOrUser=true;
					}
				}
				if (roleDirectOrUser) {
					Timestamp today = TimeCalc.todayMidnight();
					DailyAgenda dailyAgenda = generateDailyAgenda(today, userLoggedDto.getEmail());
					List<SimpleRegisterDto> lastDayRegisterList = generateRegisterList(today, userLoggedDto.getEmail());
					sessionDto.setDailyAgenda(dailyAgenda);
					sessionDto.setTodayRegisterList(lastDayRegisterList);
				}
				logger.info("SUCESS - "+sb.toString());
				return Response.ok(sessionDto).build();
			} else {
				errorMessages.add(ErrorMessage.LOGIN_FAILED);
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

	private String verifyLoginWithGoogle(String googleToken) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
				.setAudience(Collections.singletonList(CLIENT_ID))
				.build();

		GoogleIdToken idToken=null;
		try {
			idToken = verifier.verify(googleToken);
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}
		if (idToken != null) {
			Payload payload = idToken.getPayload();

			// Print user identifier
			String userId = payload.getSubject();

			// Get profile information from payload
			String email = payload.getEmail();
			boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
			if (emailVerified) {
				if (userDao.verifyEmailExistence(email)) {
					return email;
				}
			}
		} else {
			return null;
		}
		return null;
	}

	private List<SimpleRegisterDto> generateRegisterList(Timestamp date, String email) {
		List<Allocation> allocs = userDao.findUserAllocationsEndingInDate(date, email);
		List<SimpleRegisterDto> regList = new ArrayList<SimpleRegisterDto>();
		for (int i=0; i<allocs.size(); i++) {
			Task task = allocs.get(i).getTask();
			Project p = task.getProject();
			int max= calculateMaximumRemainingWorkHoursInAllocation(allocs.get(i));
			SimpleRegisterDto simpleRegisterDto = new SimpleRegisterDto(task.getTaskName(), task.getId(), allocs.get(i).getId(), max, p.getIdProject(), p.getTitle());
			regList.add(simpleRegisterDto);
		}	
		return regList;
	}

	private DailyAgenda generateDailyAgenda(Timestamp date, String email) {
		DailyAgenda dailyAgenda = new DailyAgenda();
		dailyAgenda.setDate(date);
		List<Allocation> allocs = userDao.findUserAllocationsActiveInDate(date, email);
		List<SimpleAllocDto> allocList = new ArrayList<SimpleAllocDto>();
		for (int i=0; i<allocs.size(); i++) {
			Task task = allocs.get(i).getTask();
			Project project = task.getProject();
			SimpleAllocDto simpleAllocDto = new SimpleAllocDto(project.getIdProject(), project.getTitle(), task.getTaskName(), task.getId(), allocs.get(i).getAllocPercentage());
			allocList.add(simpleAllocDto);
		}
		dailyAgenda.setAllocList(allocList);
		return dailyAgenda;
	}


	public Response forgotPassword(String email) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("User with email ").append(email).append(" forgotPassword");
			boolean verifyEmailExistence = userDao.verifyEmailExistence(email);
			if (!verifyEmailExistence) {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
			User userToRecoverPassw = userDao.getUserWithEmail(email);
			if (!userToRecoverPassw.getActive()) {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
			String tokenPasswRecover = MyJwt.createJwtPasswordRecover(email);
			userToRecoverPassw.setTokenPasswRecover(tokenPasswRecover);
			userDao.merge(userToRecoverPassw);
			myProjectMail.send(email, "RESET Password", "Para recuperar a sua password: ",URI_RECOVER_PASSW+ tokenPasswRecover);
			logger.info("SUCESS - "+sb.toString());
			return Response.ok(true).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response changePassword(String token, String passw) {
		try {
			String email = MyJwt.parseJWT(token).getSubject();
			boolean verifyEmailExistence = userDao.verifyEmailExistence(email);
			if (!verifyEmailExistence) {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}

			User userToChangePassw = userDao.getUserWithEmail(email);
			String salt = generateSalt();
			String passAndSalt = passw+salt;
			String hashedValue = Cripter.cryptMyPass(passAndSalt);
			userToChangePassw.setPassw(hashedValue);
			userToChangePassw.setSalt(salt);
			userToChangePassw.setTokenPasswRecover(null);
			userDao.merge(userToChangePassw);
			myProjectMail.send(email, "ALTERACAO DE Password", "Informamos que a sua password de acesso ao site BLUE ROOTS foi alterada. Para aceder ao site "
					,"http://localhost:8080/aclgomes-rnavalho-project-ws/");
			return Response.ok(true).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}	
	}


	public Response confirmRegister(String token) {
		try {
			String email = MyJwt.parseJWT(token).getSubject();
			StringBuilder sb = new StringBuilder();
			sb.append("User with email ").append(email).append(" confirmRegister method");
			boolean verifyEmailExistence = userDao.verifyEmailExistence(email);
			if (!verifyEmailExistence) {
				logger.info("FAILED - Mail does not exist "+sb.toString());
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}

			User userToValidateRegister = userDao.getUserWithEmail(email);
			userToValidateRegister.setTokenActivation(null);
			userToValidateRegister.setActive(true);


			List<User> users = userDao.findUsersWithRole("Administrator");
			for (int i=0; i<users.size(); i++) {
				myProjectMail.send(users.get(i).getEmail(), "Ativacao De Registo", "Informamos que o utilizador "+email+" fez o registo e aguarda atribuicao de Roles. Para aceder ao site ","http://localhost:8080/aclgomes-rnavalho-project-ws/");
			}
			myProjectMail.send(email, "Ativacao De Registo", "Informamos que o seu registo foi concluido. Para aceder ao site ","http://localhost:8080/aclgomes-rnavalho-project-ws/");
			logger.info("SUCESS - "+sb.toString());
			return Response.ok(true).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}


	public Response getAllUsersProfiles() {
		try {
			List<User> allusers = userDao.getAllusers();
			List<UserProfileDto> users= getUsersToList(allusers);
			return Response.ok(users).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getManagersList() {
		try {
			Role director = roleDao.findRoleWithName("Director");
			Role user = roleDao.findRoleWithName("User");
			List<User> allusersToManager = userDao.getManagersList(director.getId(), user.getId());
			List<UserProfileDto> users= getUsersToList(allusersToManager);
			return Response.ok(users).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}


	/**
	 * Method that receives a token, extracts the user email from token, searches for User with current unique email 
	 * and constructs a DTO (Data Transfer Object) for that user
	 * @param token - the user token
	 * @return an Object DTO representing the User
	 */
	public UserLoggedDto getLoggedUser(String token) {
		try {
			String email = MyJwt.parseJWT(token).getSubject();
			User user = userDao.getUserWithEmail(email);
			List<RoleDto> userRolesDto = getUserRolesDto(email);
			List<SkillDto> userSkillsDto = null;
			//List<SkillDto> userSkillsDto = getUserSkillsDto(email);
			UserLoggedDto userLoggedDto = new UserLoggedDto(user.getEmail(), user.getFullName(), user.getPhoto(), userRolesDto, userSkillsDto);
			return userLoggedDto;
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public Response createNewUser(UserNewDto userNewDto) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("User with email ").append(userNewDto.getEmail()).append(" doing Register in SITE");
			boolean sucess=true;
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			String newEmail=Trimmer.clean(userNewDto.getEmail());
			boolean emailAlreadyExists = userDao.verifyEmailExistence(newEmail);

			if (emailAlreadyExists) {
				sucess=false;
				errorMessages.add(ErrorMessage.UNIQUE_NAME_ALREADY_EXISTS);
			}


			String fullName=Trimmer.clean(userNewDto.getFullName());
			String userPassw=userNewDto.getPassw();
			if (Trimmer.clean(userPassw).equals("") || fullName.equals("") || newEmail.equals("")) {
				sucess=false;
				errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
			}

			if (sucess) {
				String salt = generateSalt();
				String passAndSalt = userPassw+salt;
				String hashedValue = Cripter.cryptMyPass(passAndSalt);
				Timestamp now = null;
				try {
					now = TimeCalc.todayMidnight();
				} catch (ParseException e) {
					e.printStackTrace();
				}

				List<Role> roles = new ArrayList<Role>();
				Role role = roleDao.findRoleWithName("Visitor");
				roles.add(role);
				User newUser = new User(false, newEmail, fullName, hashedValue, userNewDto.getPhoto(), now, salt, roles);
				String tokenActivation = MyJwt.createJwtPasswordRecover(newEmail);
				newUser.setTokenActivation(tokenActivation);
				userDao.persist(newUser);
				sb.append("\t").append("Email: ").append(newEmail);
				sb.append("\t").append("fullName: ").append(fullName);
				System.out.println(URI_CONFIRM_REGISTRATION+tokenActivation);
				myProjectMail.send(userNewDto.getEmail(), "Ativacao de Conta","O seu registo foi efectuado. Para ativar a conta ", URI_CONFIRM_REGISTRATION+tokenActivation);
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

	public Response getWorkersToDateInterval(String taskId, String beginDate1, String endDate1) {
		try {
			boolean success=true;
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);

			Task task = taskDao.find(Integer.parseInt(taskId));
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date beginDate2 = sdf.parse(beginDate1);
			Date endDate2 = sdf.parse(endDate1);
			Timestamp begin = new Timestamp(beginDate2.getTime());
			Timestamp end = new Timestamp(endDate2.getTime());
			if (begin.before(task.getBeginDate()) || end.after(task.getEndDate())) {
				success=false;
				errorMessages.add(ErrorMessage.TASK_OUTSIDE_DATES);
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

			if (success) {
				//get all distinct user that are director or user
				List<User> direcOrUser = userDao.findUsersWithAtLeastOneOfTwoRoles("Director", "User");
				//contruct a list of UserAllocationGraph each containing:
				//User, data for percentage alocation between dates, minimum percentage allocation value in dates
				List<ChartUserAllocations> users = new ArrayList<ChartUserAllocations>();
				for (int i=0; i<direcOrUser.size(); i++) {
					ChartUserAllocations cua = createChartUserAllocations(direcOrUser.get(i), begin, end);
					if (cua.getFreePercentage()>0) {
						if (task.getSkill()!=null) {
							//se a tarefa tem skill entao veremos se o user esta capacitado para a tarefa
							cua.setGotSkillNeededTask(hasUserSkill(direcOrUser.get(i), task.getSkill()));
						} else {
							//se a tarefa nao tem nenhum skill entao o user esta capacitado para a tarefa
							cua.setGotSkillNeededTask(true);
						}
						users.add(cua);
					}
				}
				return Response.ok(users).build();
			} else {
				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}


	private boolean hasUserSkill(User user, Skill skill) {
		List<Skill> userSkills = userDao.getUserSkills(user);
		for (int i=0; i<userSkills.size(); i++) {
			if (userSkills.get(i).getSkill().equals(skill.getSkill())) {
				return true;
			}
		}
		return false;
	}

	public ChartUserAllocations createChartUserAllocations(User user, Timestamp begin, Timestamp end) {
		List<Allocation> allocs = userDao.getUserAllocationsBetweenDates(user, begin, end);
		ChartUserAllocations cua = new ChartUserAllocations();
		DateTime beginDate = new DateTime(begin.getTime());
		DateTime endDate = new DateTime(end.getTime());
		int max = Days.daysBetween(beginDate, endDate).getDays()+1;
		List<Holiday> holidays = holidayDao.findHolidaysBetweenDates(beginDate.toLocalDate(), endDate.toLocalDate());
		List<DatePercDto> dateAlloc = new ArrayList<DatePercDto>();
		for (int i=0; i<max; i++) {
			boolean isHoliday = false;
			Date day=new Date(beginDate.plusDays(i).withTimeAtStartOfDay().getMillis());
			for (int j=0; j<holidays.size() && !isHoliday; j++) {
				if (day.getTime()==holidays.get(j).getDay().getTime()) {
					isHoliday=true;
				}
			}	
			if (!(beginDate.plusDays(i).getDayOfWeek()==DateTimeConstants.SATURDAY) &&
					!(beginDate.plusDays(i).getDayOfWeek()==DateTimeConstants.SUNDAY) &&
					!isHoliday) {
				String pattern = "dd/MM/yyyy";
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				Date day1=null;
				String format = sdf.format(day);
				try {
					day1=new SimpleDateFormat("dd/MM/yyyy").parse(format);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dateAlloc.add(new DatePercDto(day1, 0f));
			}
		}
		for (int i=0; i<allocs.size(); i++) {
			for (int j=0; j<dateAlloc.size(); j++) {
				if (!(dateAlloc.get(j).getDate().before(allocs.get(i).getBeginDate()) || dateAlloc.get(j).getDate().after(allocs.get(i).getEndDate()))) {
					DatePercDto datePercDto = dateAlloc.get(j);
					datePercDto.setPercentage(dateAlloc.get(j).getPercentage()+allocs.get(i).getAllocPercentage());
					dateAlloc.set(j, datePercDto);
				}
			}
		}
		float freePerc=100;
		for (int i=0; i<dateAlloc.size(); i++) {
			if (100-dateAlloc.get(i).getPercentage()<freePerc) {
				freePerc=100-dateAlloc.get(i).getPercentage();
			}
		}
		cua.setFreePercentage(freePerc);
		cua.setEmail(user.getEmail());
		cua.setFullName(user.getFullName());
		cua.setDateAlloc(dateAlloc);
		return cua;
	}

	public UserAllocationGraph createUserAllocationGraph(User user, Timestamp begin, Timestamp end) {
		List<Allocation> allocs = userDao.getUserAllocationsBetweenDates(user, begin, end);
		List<Timestamp> dates = getAllDatesInAllocationListBetweenDates(allocs, begin, end);
		List<GraphTimeAllocPerc> xy = calculateGraphTimeAllocPerc(allocs, dates);
		UserAllocationGraph uag = new UserAllocationGraph();
		uag.setEmail(user.getEmail());
		uag.setFullName(user.getFullName());
		uag.setXy(xy);
		float minimum=100;
		for (int i=0; i<xy.size(); i++) {
			if (100-xy.get(i).getAllocPerc()<minimum) {
				minimum=100-xy.get(i).getAllocPerc();
			}
		}
		uag.setFreeAllocationPercentage(minimum);
		return uag;
	}

	private List<GraphTimeAllocPerc> calculateGraphTimeAllocPerc(List<Allocation> allocs, List<Timestamp> dates) {
		List<GraphTimeAllocPerc> xy = new ArrayList<GraphTimeAllocPerc>();
		for (int i=0; i<dates.size(); i++) {
			xy.add(new GraphTimeAllocPerc(dates.get(i), null, 0f));
		}
		for (int i=0; i<allocs.size(); i++) {
			Timestamp begin = allocs.get(i).getBeginDate();
			Timestamp end = new Timestamp(allocs.get(i).getEndDate().getTime()-TimeUnit.DAYS.toMillis(1));
			for (int j=0; j<xy.size(); j++) {
				if (!(xy.get(j).getBegin().before(begin) || xy.get(j).getBegin().after(end))) {
					xy.get(j).setAllocPerc(xy.get(j).getAllocPerc()+allocs.get(i).getAllocPercentage());
				}
			}
		}
		for (int i=0; i<xy.size()-1; i++) {
			xy.get(i).setEnd(xy.get(i+1).getBegin());
		}
		xy.remove(xy.size()-1);
		return xy;
	}

	private List<Timestamp> getAllDatesInAllocationListBetweenDates(List<Allocation> allocs, Timestamp begin, Timestamp end) {
		List<Timestamp> dates = new ArrayList<Timestamp>();
		dates.add(begin);
		dates.add(end);
		for (int i=0; i<allocs.size(); i++) {
			if (allocs.get(i).getBeginDate().after(begin)) {
				if(!dates.contains(allocs.get(i).getBeginDate())) {
					dates.add(allocs.get(i).getBeginDate());
				}
			}
			if (allocs.get(i).getEndDate().before(end)) {
				if(!dates.contains(allocs.get(i).getEndDate())) {
					dates.add(allocs.get(i).getEndDate());
				}
			}
		}
		for (int i = 0; i < dates.size()-1; i++) {
			int indiceMenor=i;
			Timestamp dateMenor = dates.get(i);
			for (int j = i+1; j < dates.size(); j++) {
				if(dates.get(j).before(dateMenor)){
					dateMenor=dates.get(j);
					indiceMenor=j;
				}
			}
			Timestamp aux = dates.get(i);
			dates.set(i, dateMenor);
			dates.set(indiceMenor, aux);
		}

		return dates;
	}

	public Response getWorkRegister(String email) {
		try {
			User user = userDao.getUserWithEmail(email);
			Stage stage =stageDao.findStageByName("Closed");
			Taskstage taskstage = taskstageDao.findTaskstageWithName("Blocked");
			List<Allocation> allocList = allocationDao.findAvailableAllocationsToRegisterWork(user, stage.getId(), taskstage.getId());
			List<WorkRegisterDto> wr= getWorkRegisterDto(allocList);
			return Response.ok(wr).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}


	public Response requestRoleAtribution(String token) {
		try {
			String email = MyJwt.parseJWT(token).getSubject();	
			User user = userDao.getUserWithEmail(email);
			myProjectMail.send("proj.management.2017@gmail.com", "Pedido de Atribuição de Roles", "O user "+user.getFullName()+" com o email " +email+" solicita que lhe seja atribuido um Perfil. Para atribuir perfil ","http://localhost:8080/aclgomes-rnavalho-project-ws/");
			return Response.ok().build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}

	}


	public Response userAdvancedSearch(String type, String searchText) {
		try {
			boolean success=true;
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();			
			List<User> userList = null;
			String search=searchText;
			if(!type.equals("date")){
				search = Trimmer.clean(searchText);
			}	
			if (search.equals("")) {
				success=false;
				errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
			}
			if (success) {
				userList=userDao.userAdvancedSearch(type, search);
				List<UserProfileDto> users= getUsersToList(userList);
				return Response.ok(users).build();
			} else {
				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {	
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// Private Auxiliary Methods ///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////


	private List<WorkRegisterDto> getWorkRegisterDto(List<Allocation> list) {
		List<WorkRegisterDto> wr = new ArrayList<WorkRegisterDto>();
		for (int i=0; i<list.size(); i++) {
			Allocation allocation = list.get(i);
			DateTime beginDate = new DateTime(allocation.getBeginDate().getTime());
			DateTime endDate = new DateTime(allocation.getEndDate().getTime());
			if(endDate.isAfterNow()){
				endDate=new DateTime();
			}
			//number of days between allocation begin and today (or begin and end date)
			int days = Days.daysBetween(beginDate.toLocalDate(), endDate.toLocalDate()).getDays()+1;;
			if(days>0){
				int allocationId = list.get(i).getId();
				String taskName = list.get(i).getTask().getTaskName();
				String idProject = list.get(i).getTask().getProject().getIdProject();
				String title = list.get(i).getTask().getProject().getTitle(); 
				int hoursToFinish = list.get(i).getTask().getRemainingHours();
				//calculate max hours that a worker can register in taskwork
				// MAX hours to register = (8h *number of weekdays since begin till today - numbers of hours already registered) * % allocation

				Task task = list.get(i).getTask();
				int maxWorkingHours=calculateMaximumRemainingWorkHoursInAllocation(allocation);
				float taskPercentageExecuted=calculatePercentageExecutedInTask(task);
				if (maxWorkingHours>0) {
					wr.add(new WorkRegisterDto(allocationId, taskName, idProject, title, allocation.getBeginDate(), allocation.getEndDate(), maxWorkingHours, hoursToFinish, taskPercentageExecuted));
				}
			}

		}
		return wr;
	}

	private float calculatePercentageExecutedInTask(Task task) {
		Long workedHoursLong = taskDao.findWorkedHours(task);
		int workedHours=0;
		if(workedHoursLong!=null){
			workedHours=workedHoursLong.intValue();
		}
		int totalHours = workedHours+task.getRemainingHours();
		//forma de garantir que o metodo nao retorna o valor arredondado
		float one =1;
		return (workedHours*one/totalHours);
	}

	public int calculateMaximumRemainingWorkHoursInAllocation(Allocation allocation) {
		//allocation begin and end date
		DateTime beginDate = new DateTime(allocation.getBeginDate().getTime());
		DateTime endDate = new DateTime(allocation.getEndDate().getTime());
		if(endDate.isAfterNow()){
			endDate=new DateTime();
		}

		//number of weekdays between allocation begin and today (or begin and end date)
		int weekDays = calculateNumberOfWorkingDaysBetweenDates(beginDate.toLocalDate(), endDate.toLocalDate());

		//can work 8 hours a day
		Float maxHours = weekDays*8*allocation.getAllocPercentage()/100;

		Double ceil = Math.ceil(maxHours.doubleValue());
		int roundMaxHours = ceil.intValue();

		int workedHoursIntValue;
		Long workedHours = allocationDao.findWorkedHoursInAllocation(allocation);
		if (workedHours==null) {
			workedHoursIntValue=0;
		} else {
			workedHoursIntValue=workedHours.intValue();
		}
		return (roundMaxHours-workedHoursIntValue);
	}

	private int calculateNumberOfWorkingDaysBetweenDates(LocalDate begin, LocalDate end) {	
		List<Holiday> holidays = holidayDao.findHolidaysBetweenDates(begin, end);
		int numberOfWorkingDays=Days.daysBetween(begin, end).getDays()+1;
		int max = Days.daysBetween(begin, end).getDays();
		for (int i=0; i<max; i++) {
			boolean isHoliday = false;
			Timestamp day=new Timestamp(begin.plusDays(i).toDateTimeAtStartOfDay().getMillis());
			for (int j=0; j<holidays.size() && !isHoliday; j++) {
				if (day.getTime()==holidays.get(j).getDay().getTime()) {
					isHoliday=true;
				}
			}	
			if (begin.plusDays(i).getDayOfWeek()==DateTimeConstants.SATURDAY ||
					begin.plusDays(i).getDayOfWeek()==DateTimeConstants.SUNDAY ||
					isHoliday) {
				numberOfWorkingDays--;
			}
		}
		return numberOfWorkingDays;
	}

	/**
	 * Method that constructs converts a User list to UserProfileDto list 
	 * @param allusers - the User list to convert
	 * @return usersList - the UserProfileDto list to return
	 */
	private List<UserProfileDto> getUsersToList(List<User> allusers) {
		List<UserProfileDto> usersList= new ArrayList<UserProfileDto>();
		for (int i=0; i<allusers.size(); i++) {
			usersList.add(new UserProfileDto(allusers.get(i).getActive(), allusers.get(i).getEmail(), allusers.get(i).getFullName(), allusers.get(i).getPhoto(), 
					getUserRolesDto(allusers.get(i).getEmail()), getUserSkillsDto(allusers.get(i).getEmail()), null));
		}
		return usersList;
	}

	/**
	 * Method that receives an user email (note: email is unique) and returns a list of Roles (as Strings also unique) from that user
	 * @param email - the user email
	 * @return - a list with Strings that represent the roles names
	 */
	private List<RoleDto> getUserRolesDto(String email) {
		User user = userDao.getUserWithEmail(email);
		List<Role> userRoles = userDao.getUserRoles(user);
		List<RoleDto> userRolesDto = new ArrayList<RoleDto>();
		for (int i=0; i<userRoles.size(); i++) {
			userRolesDto.add(new RoleDto(userRoles.get(i).getRole()));
		}
		return userRolesDto;
	}

	/**
	 * Method that receives an user email (note: email is unique) and returns a list of Skills (as Strings also unique) from that user
	 * @param email - the user email
	 * @return - a list with skillDTOs representing the skills
	 */
	private List<SkillDto> getUserSkillsDto(String email) {
		User user = userDao.getUserWithEmail(email);
		List<Skill> userSkills = userDao.getUserSkills(user);
		List<SkillDto> userSkillsDto = new ArrayList<SkillDto>();
		for (int i=0; i<userSkills.size(); i++) {
			userSkillsDto.add(new SkillDto(userSkills.get(i).getSkill()));
		}
		return userSkillsDto;
	}

	/**
	 * Method that receives a list with Skill names (with unique ids) and returns a list with the Skill Objects
	 * @param skills - list with the Skill names
	 * @return list with Skill Objects
	 */
	private List<Skill> getSkillList(List<SkillDto> skills) {
		List<Skill> list = new ArrayList<Skill>();
		for (int i=0; i<skills.size(); i++) {
			list.add(skillDao.findSkillWithName(skills.get(i).getSkill()));
		}
		return list;
	}

	/**
	 * Method that receives a list with Role names (with unique ids) and returns a list with the Role Objects
	 * @param skills - list with the Role names
	 * @return list with Role Objects
	 */
	private List<Role> getRoleList(List<RoleDto> roles) {
		List<Role> list = new ArrayList<Role>();
		for (int i=0; i<roles.size(); i++) {
			list.add(roleDao.findRoleWithName(roles.get(i).getRole()));
		}
		return list;
	}

	/**
	 * Method that generates a random String
	 * @return - the random String
	 */
	private String generateSalt() {
		Random r = new SecureRandom();
		byte[] salt = new byte[64];
		r.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}






}
