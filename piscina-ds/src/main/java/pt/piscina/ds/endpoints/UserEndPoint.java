package pt.piscina.ds.endpoints;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.piscina.ds.annotations.AdminDirectorOnly;
import pt.piscina.ds.annotations.AdminOnly;
import pt.piscina.ds.annotations.DirectorOrUser;
import pt.piscina.ds.annotations.NoTokenNeeded;
import pt.piscina.ds.services.UserService;
import pt.uc.dei.itf.dtos.UserLoggedDto;
import pt.uc.dei.itf.dtos.UserLoginDto;
import pt.uc.dei.itf.dtos.UserChangeRoleDto;
import pt.uc.dei.itf.dtos.UserNewDto;
import pt.uc.dei.itf.dtos.UserProfileDto;
import pt.uc.dei.itf.dtos.UserUpdateSelfDto;

@Path("/user")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserEndPoint {

	private static final Logger LOGGER = Logger.getLogger(UserEndPoint.class.getName());

	@Inject
	private UserService userService;
	
	@PUT
	@Path("/resetPassword")
	public Response changePassword(@HeaderParam("token") String token, String passw) {
		return userService.changePassword(token, passw);
	}
	
	//loginWithGoogle
	@PUT
	@Path("/loginWithGoogle")
	@NoTokenNeeded
	public Response loginWithGoogle(String googleToken) {
		return userService.loginWithGoogle(googleToken);
	}
	
	@PUT
	@Path("/login")
	@NoTokenNeeded
	public Response loginUser(UserLoginDto userLoginDto) {
		System.out.println("loginUser DS LOGIN endpoint");
		return userService.loginUser(userLoginDto);
	}
	
	@PUT
	@Path("/forgotPassword")
	@NoTokenNeeded
	public Response forgotPassword(String email) {
		return userService.forgotPassword(email);
	}
	
	@PUT
	@Path("/confirmRegister")
	@NoTokenNeeded
	public Response confirmRegister(String token) {
		return userService.confirmRegister(token);
	}

	@PUT
	@Path("/updateProfile")
	@AdminOnly
	public Response updateUserProfile(UserProfileDto userDto, @HeaderParam("token") String token) {
		return userService.updateUserProfile(userDto, token);
	}
	
	@PUT
	@Path("/updateSelf")
	public Response updateSelfProfile(UserUpdateSelfDto userUpdateSelfDto, @HeaderParam("token") String token) {
		return userService.updateSelfProfile(userUpdateSelfDto, token);
	}

	@GET
	public UserLoggedDto getLoggedUser(@HeaderParam("token") String token) {
		return userService.getLoggedUser(token);
	}
	
	@GET
	@Path("/getWorkRegister/{email}")
	@DirectorOrUser
	public Response getWorkRegister(@PathParam("email") String email) {
		return userService.getWorkRegister(email);
	}
	
	@GET
	@Path("/requestRoleAtribution")
	public Response requestRoleAtribution(@HeaderParam("token") String token) {
		return userService.requestRoleAtribution(token);
	}
	
//	@GET
//	@Path("/getWorkRegisterToTask/{email}/{taskId}")
//	@DirectorOrUser
//	public Response getWorkRegisterToTask(@PathParam("email") String email, @PathParam("taskId") String taskId) {
//		return userService.getWorkRegisterToTask(email, taskId);
//	}

	@GET
	@Path("/getAll")
	@AdminDirectorOnly
	public Response getAllUsersProfiles() {
		return userService.getAllUsersProfiles();
	}
	
	@GET
	@Path("/advancedSearch/{type}/{searchText}")
	@AdminDirectorOnly
	public Response userAdvancedSearch(@PathParam("type") String type, @PathParam("searchText") String searchText) {
		return userService.userAdvancedSearch(type, searchText);
	}
	
	@GET
	@Path("/getUsersToManager")
	@DirectorOrUser
	public Response getManagersList() {
		return userService.getManagersList();
	}
	
//	@GET
//	@Path("/getWorkersToTask/{taskId}")
//	@DirectorOrUser
//	public Response getAvailableWorkersToTask(@PathParam("taskId") String taskId) {
//		return userService.getAvailableWorkersToTask(taskId);
//	}
	
	@GET
	@Path("/getWorkersToDateInterval/{taskId}/{beginDate}/{endDate}")
	@DirectorOrUser
	public Response getWorkersToDateInterval(@PathParam("taskId") String taskId, @PathParam("beginDate") String beginDate, @PathParam("endDate") String endDate) {
		return userService.getWorkersToDateInterval(taskId, beginDate, endDate);
	}

	@POST
	@Path("/create")
	@NoTokenNeeded
	public Response createNewUser(UserNewDto userNewDto) {
		return userService.createNewUser(userNewDto);
	}

	@PUT
	@Path("/update")
	public boolean updateUser(UserUpdateSelfDto userUpdateSelfDto) {
		//return userService.updateUser(dtoUserUpdate);
		//REMOVER
		return false;//TODO remover
	}

	@PUT
	@Path("/role")
	@AdminOnly
	public boolean changeRole(UserChangeRoleDto userChangeRoleDto) {
		//return userService.changeRole(dtoUserChangeRole);
		return false;//TODO remover
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
