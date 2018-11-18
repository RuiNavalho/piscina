package pt.uc.dei.ws.beans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.omnifaces.util.Utils;

import pt.piscina.itf.dtos.UserNewDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.UserBridge;
import pt.uc.dei.ws.util.ErrorsHandler;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

@Named("createUserBean")
@SessionScoped
public class CreateUserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String newEmail;
	private String fullName;
	private String newPassw;
	private String newPasswConfirm;
	private Part file;
	private byte[] photo;

	@Inject
	private UserBridge userBridge;

	@Inject
	private ErrorsHandler errorsHandler;

	public CreateUserBean() {
	}

	//TODO posteriormente usar OMNI para carregar automaticamente a photo
	public void read() throws IOException {
		photo = Utils.toByteArray(file.getInputStream());
	}

	public String createUser() {		
/*		if (file!=null) {
			try {
				upload();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/

		UserNewDto userNewDto = new UserNewDto(newEmail, fullName, photo, newPassw);
		Response  response = userBridge.createNewUser(userNewDto);

		if (response.getStatus()==200) {
			setFieldsToNull();
			errorsHandler.createUser(true, null);
			return "registar.xhtml";
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.createUser(false, errors);
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
		photo =output.toByteArray();
	}

	//TODO melhorar
	private void setFieldsToNull() {
		newEmail=null;
		fullName=null;
		newPassw=null;
		newPasswConfirm=null;
		photo=null;
		file=null;
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNewPassw() {
		return newPassw;
	}

	public void setNewPassw(String newPassw) {
		this.newPassw = newPassw;
	}

	public String getNewPasswConfirm() {
		return newPasswConfirm;
	}

	public void setNewPasswConfirm(String newPasswConfirm) {
		this.newPasswConfirm = newPasswConfirm;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public UserBridge getUserBridge() {
		return userBridge;
	}

	public void setUserBridge(UserBridge userBridge) {
		this.userBridge = userBridge;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

}
