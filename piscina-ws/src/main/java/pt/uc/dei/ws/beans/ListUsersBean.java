package pt.uc.dei.ws.beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import pt.piscina.itf.dtos.UserProfileDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.UserBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named("listUsersBean")
@SessionScoped
public class ListUsersBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<UserProfileDto> usersList= new ArrayList<UserProfileDto>();
	private List<UserProfileDto> possibleManagers;
	private String passw;
	private String passwConf;
	private UserProfileDto selectedUser;
	private Part file=null;

	private String searchText;
	private Date searchDate;
	private String type = "";
	private boolean showAdvancedSearchBtn;


	@Inject
	private UserBridge userBridge;

	@Inject
	private ErrorsHandler errorsHandler;

	public ListUsersBean() {
	}

	public String updateUserProfile() throws IOException{		
		if (file!=null) {
			upload();
		}
		UserProfileDto userProfileDto = new UserProfileDto(selectedUser.isActive(), selectedUser.getEmail(), selectedUser.getFullName(), 
				selectedUser.getPhoto(), selectedUser.getRoles(), selectedUser.getSkills(), passw);
		Response response = userBridge.updateUserProfile(userProfileDto);
		passw=null;
		passwConf=null;
		file=null;	
		if (response.getStatus()==200) {
			errorsHandler.updateUserProfile(true, null);
			return "/users_edit.xhtml";
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.updateUserProfile(false, errors);
		}
		return null;
	}

	public void cleanSearchText(){
		searchText="";
	}
	public List<UserProfileDto> findManagersList() {
		possibleManagers = userBridge.getManagersList();
		return possibleManagers;
	}

	private void upload() throws IOException{
		InputStream input = file.getInputStream();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[10240];//TODO Qual o size maximo da PHOTO????
		for (int length = 0; (length = input.read(buffer)) > 0;) {
			output.write(buffer, 0, length);
		}
		selectedUser.setPhoto(output.toByteArray());
	}

	public void updateList() {
		this.usersList = userBridge.getAllUsers();
	}

	public void userAdvancedSearch() {
		switch (type) {
		case "date":
			this.usersList=userBridge.userAdvancedSearch(type, null, searchDate);
			break;
		case "all":
			usersList=userBridge.userAdvancedSearch(type, "all", null);
			break;
		case "active":
			usersList=userBridge.userAdvancedSearch(type, "active", null);
			break;
		case "inactive":
			usersList=userBridge.userAdvancedSearch(type, "inactive", null);
			break;
		default:
			usersList=userBridge.userAdvancedSearch(type, searchText, null);
			break;
		}
		searchText="";
	}


	public void cleanFields(){
		searchText = "";
		searchDate = null;
		setType("");
		showAdvancedSearchBtn=false;
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////


	public List<UserProfileDto> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<UserProfileDto> usersList) {
		this.usersList = usersList;
	}

	public UserBridge getUserBridge() {
		return userBridge;
	}

	public void setUserBridge(UserBridge userBridge) {
		this.userBridge = userBridge;
	}

	public String getPassw() {
		return passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}

	public String getPasswConf() {
		return passwConf;
	}

	public void setPasswConf(String passwConf) {
		this.passwConf = passwConf;
	}

	public UserProfileDto getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserProfileDto selectedUser) {	
		this.selectedUser = selectedUser;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public Date getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (type.equals("all") || type.equals("active") || type.equals("inactive")) {
			showAdvancedSearchBtn=false;
		} else {
			showAdvancedSearchBtn=true;
		}
		this.type = type;
	}

	public List<UserProfileDto> getPossibleManagers() {
		return possibleManagers;
	}

	public void setPossibleManagers(List<UserProfileDto> possibleManagers) {
		this.possibleManagers = possibleManagers;
	}

	public boolean isShowAdvancedSearchBtn() {
		return showAdvancedSearchBtn;
	}

	public void setShowAdvancedSearchBtn(boolean showAdvancedSearchBtn) {
		this.showAdvancedSearchBtn = showAdvancedSearchBtn;
	}

}
