package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.UserBridge;

@Named("registrationConfirmation")
@RequestScoped
public class RegistrationConfirmationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	
	private boolean valid;
	
	@Inject
	private UserBridge userBridge;
	
	public RegistrationConfirmationBean() {
	}
	
	public void confirmRegistration() {
		Response response = userBridge.confirmRegistration(this.token);
		if (response.getStatus()==200) {
			valid=true;
		} else {
			valid=false;
		}
	}

	public RegistrationConfirmationBean(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
		confirmRegistration();
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}
