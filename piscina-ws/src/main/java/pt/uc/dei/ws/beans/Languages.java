package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("languages")
@javax.enterprise.context.SessionScoped
public class Languages implements Serializable {
	private static final long serialVersionUID = 1L;
	private String locale=FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
	final static Locale pt_PT = new Locale("pt", "PT");
	
	
	
	private static Map<String, Object> countries;
	static {
		countries = new LinkedHashMap<String, Object>();
		countries.put("Portuguese", pt_PT);
		countries.put("English", Locale.ENGLISH);
		countries.put("French", Locale.FRENCH);
	}
	
	public Languages() {
		
	}

	public Map<String, Object> getCountries() {
		return countries;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		ResourceBundle messages = ResourceBundle.getBundle("interlocal.messages",
				FacesContext.getCurrentInstance().getViewRoot().getLocale());
		countries.clear();
		countries.put(messages.getString("Portuguese"), pt_PT);
		countries.put(messages. getString("English"), Locale.ENGLISH);
		countries.put(messages.getString("French"), Locale.FRENCH);
		this.locale = locale;
	}

	public void localeChanged(String newLocaleValue) {
		for (Map.Entry<String, Object> entry : countries.entrySet()) {	
			if (entry.getValue().toString().equals(newLocaleValue)) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
				String idioma = ResourceBundle.getBundle("interlocal.messages",
						FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("Idioma");
				FacesMessage idiomMessage =
						new FacesMessage(idioma);
				FacesContext.getCurrentInstance().addMessage(null, idiomMessage);
			}
		}
		this.locale = newLocaleValue;
	}

}