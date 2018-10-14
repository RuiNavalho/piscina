package pt.uc.dei.ws.util;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("styleResponse")
@SessionScoped
public class StyleResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cssClass;
	
	public StyleResponse(){	
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

}
