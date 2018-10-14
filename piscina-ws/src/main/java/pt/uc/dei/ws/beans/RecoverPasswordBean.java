package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;

import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.UserBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named("recoverPasswordBean")
@RequestScoped
public class RecoverPasswordBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	private String passw;
	private String passwConf;
	private String email;
	
	@Inject
	private UserBridge userBridge;
	
	@Inject
	private ErrorsHandler errorsHandler;
	
	public RecoverPasswordBean() {
	}
	
	public RecoverPasswordBean(String token, String passw, String passwConf) {
		this.token = token;
		this.passw = passw;
		this.passwConf = passwConf;
	}
	
	//TODO mudar este metodo para outro BEAN , possivelmente RecoverPasswordBean
	public String forgotPassword() {
		boolean validEmail=false;
		validEmail=userBridge.forgotPassword(this.email);
		
		if (validEmail) {
			errorsHandler.forgotPassword(true, null);
			return null;
		} else {			
			errorsHandler.forgotPassword(false, null);
			return null;
		}
		
	}

	public String submitNewPassword() {
		 boolean sucess = userBridge.submitNewPassword(token,passw);
			if (sucess) {
				errorsHandler.submitNewPassword(true, null);
				return null;
			} else {			
				errorsHandler.submitNewPassword(false, null);
				return null;
			}
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
