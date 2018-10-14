package pt.uc.dei.ws.beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import pt.uc.dei.itf.agenda.DailyAgenda;
import pt.uc.dei.itf.agenda.SimpleRegisterDto;
import pt.uc.dei.itf.dtos.SessionDto;
import pt.uc.dei.itf.dtos.UserLoggedDto;
import pt.uc.dei.itf.dtos.UserUpdateSelfDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.UserBridge;
import pt.uc.dei.ws.charts.ChartBarUserPerformance;
import pt.uc.dei.ws.util.ErrorsHandler;
import pt.uc.dei.ws.util.MyWsProjectMail;

@Named("mySessionBean")
@SessionScoped
public class MySessionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String token=null;
	private String email;
	private String passw;
	private String passwConf;
	private Part file;
	private UserLoggedDto loggedUser;
	private boolean administratorRole;
	private boolean directorRole;
	private boolean userRole;
	private boolean visitorRole;
	private boolean enableRequestRoleButton=true;
	private String googleToken;

	private DailyAgenda dailyAgenda;
	private List<SimpleRegisterDto> lastDayRegisterList;
	

	@Inject
	private MyWsProjectMail myWsProjectMail;

	@Inject
	private UserBridge userBridge;

	@Inject
	private ErrorsHandler errorsHandler;

	@Inject
	private ChartBarUserPerformance teste;

	public MySessionBean() {
	}


	//	public void read() throws IOException {
	//		content = Utils.toByteArray(file1.getInputStream());
	//	}


	public String loginUser() {
		SessionDto sessionDto=null;
		sessionDto = userBridge.loginUser(email, passw);
		if (sessionDto!=null) {
			loggedUser=sessionDto.getLoggedUser();
			for (int i=0; i<loggedUser.getRoles().size(); i++) {
				if (loggedUser.getRoles().get(i).getRole().equals("Administrator")) {
					administratorRole=true;
				} else if (loggedUser.getRoles().get(i).getRole().equals("Director")) {
					directorRole=true;
				} else if (loggedUser.getRoles().get(i).getRole().equals("User")) {
					userRole=true;
				} else if (loggedUser.getRoles().get(i).getRole().equals("Visitor")) {
					visitorRole=true;
				} 
			}
			token=sessionDto.getToken();
			if (directorRole|| userRole) {
				dailyAgenda=sessionDto.getDailyAgenda();
				lastDayRegisterList=sessionDto.getTodayRegisterList();
			}
			return "/dashboard.xhtml?faces-redirect=true";
		} else {

		}
		passw=null;
		passwConf=null;
		loggedUser=null;
		token=null;
		return null;
	}
	
	public String loginUserWithGoogle() {
		SessionDto sessionDto=null;
		sessionDto = userBridge.loginUserWithGoogle(googleToken);
		if (sessionDto!=null) {
			loggedUser=sessionDto.getLoggedUser();
			for (int i=0; i<loggedUser.getRoles().size(); i++) {
				if (loggedUser.getRoles().get(i).getRole().equals("Administrator")) {
					administratorRole=true;
				} else if (loggedUser.getRoles().get(i).getRole().equals("Director")) {
					directorRole=true;
				} else if (loggedUser.getRoles().get(i).getRole().equals("User")) {
					userRole=true;
				} else if (loggedUser.getRoles().get(i).getRole().equals("Visitor")) {
					visitorRole=true;
				} 
			}
			token=sessionDto.getToken();
			if (directorRole|| userRole) {
				dailyAgenda=sessionDto.getDailyAgenda();
				lastDayRegisterList=sessionDto.getTodayRegisterList();
			}
			return "/dashboard.xhtml?faces-redirect=true";
		} else {

		}
		googleToken=null;
		passw=null;
		passwConf=null;
		loggedUser=null;
		token=null;
		return null;
	}

	public String updateSelfProfile() throws IOException{		
		if (file!=null) {
			upload();
		}
		UserUpdateSelfDto userUpdateSelfDto = new UserUpdateSelfDto(loggedUser.getFullName(), loggedUser.getPhoto(), passw);
		Response response = userBridge.updateSelfProfile(userUpdateSelfDto, token);	
		passw=null;
		passwConf=null;
		if (response.getStatus()==200) {
			loggedUser=userBridge.getMyUserData();
			errorsHandler.updateSelfProfile(true, null);
			return "/users_self_edit.xhtml";
		} else {		
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.updateSelfProfile(false, errors);
		}
		return null;
	}

	private void upload() throws IOException{
		InputStream input = file.getInputStream();	
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[10240];//TODO Qual o size maximo da PHOTO????
		for (int length = 0; (length = input.read(buffer)) > 0;) {
			output.write(buffer, 0, length);
		}
		//photo = output.toByteArray();
		loggedUser.setPhoto(output.toByteArray());
	}

	public void requestRoleAtribution() {
		Response response = userBridge.requestRoleAtribution();
		if (response.getStatus()==200) {
			errorsHandler.requestRoleAtribution(true, null);
			enableRequestRoleButton=false;
		} else {		
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.requestRoleAtribution(false, errors);
		}
	}
	


	public void logoutUser() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

	public Response sendDataServerDownAlert() {
		myWsProjectMail.sendDataServerDownAlert();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}


	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassw() {
		return passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}

	public UserLoggedDto getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(UserLoggedDto loggedUser) {
		this.loggedUser = loggedUser;
	}

	public UserBridge getUserBridge() {
		return userBridge;
	}

	public void setUserBridge(UserBridge userBridge) {
		this.userBridge = userBridge;
	}

	public boolean isAdministratorRole() {
		return administratorRole;
	}

	public void setAdministratorRole(boolean administratorRole) {
		this.administratorRole = administratorRole;
	}

	public boolean isVisitorRole() {
		return visitorRole;
	}

	public void setVisitorRole(boolean visitorRole) {
		this.visitorRole = visitorRole;
	}

	public boolean isDirectorRole() {
		return directorRole;
	}

	public void setDirectorRole(boolean directorRole) {
		this.directorRole = directorRole;
	}

	public boolean isUserRole() {
		return userRole;
	}

	public void setUserRole(boolean userRole) {
		this.userRole = userRole;
	}

	public String getPasswConf() {
		return passwConf;
	}

	public void setPasswConf(String passwConf) {
		this.passwConf = passwConf;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public DailyAgenda getDailyAgenda() {
		return dailyAgenda;
	}

	public void setDailyAgenda(DailyAgenda dailyAgenda) {
		this.dailyAgenda = dailyAgenda;
	}

	public List<SimpleRegisterDto> getLastDayRegisterList() {
		return lastDayRegisterList;
	}

	public void setLastDayRegisterList(List<SimpleRegisterDto> todayRegisterList) {
		this.lastDayRegisterList = todayRegisterList;
	}

	public boolean isEnableRequestRoleButton() {
		return enableRequestRoleButton;
	}

	public void setEnableRequestRoleButton(boolean enableRequestRoleButton) {
		this.enableRequestRoleButton = enableRequestRoleButton;
	}

	public String getGoogleToken() {

		return googleToken;
	}

	public void setGoogleToken(String googleToken) {
		this.googleToken = googleToken;
	}

}
