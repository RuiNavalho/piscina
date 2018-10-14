package pt.uc.dei.ws.util;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator
public class BlankSpacesValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		String blankSpaces = bundle.getString("BlankSpaces");

		//Check if user has typed only blank spaces
		if(value.toString().trim().isEmpty()){
			FacesMessage msg = 
					new FacesMessage("Incorrect input provided", 
							blankSpaces);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
}