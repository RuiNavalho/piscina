package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import pt.piscina.itf.dtos.HolidayDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.HolidayBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named("listHolidayBean")
@SessionScoped
public class ListHolidayBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<HolidayDto> holidaysList;
	private HolidayDto selectedHoliday;

	private String holidayname;
	private Date day;

	@Inject
	private HolidayBridge holidayBridge;
	
	@Inject
	private ErrorsHandler errorsHandler;

	public ListHolidayBean() {
	}

	public String createNewHoliday() {	
		HolidayDto holidayDto = new HolidayDto(holidayname, day);
		Response response = holidayBridge.createNewHoliday(holidayDto);
		if (response.getStatus()==200) {
			//holidayname=null;
			//day=null;
			errorsHandler.createNewHoliday(true, null);
			return "config_holidays_new.xhtml";
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.createNewHoliday(false, errors);
		}
		return null;
	}

	public String updateHoliday() {
		//FacesContext facesContext = FacesContext.getCurrentInstance();
		Response response = holidayBridge.updateHoliday(selectedHoliday);
		if (response.getStatus()==200) {
			//facesContext.addMessage("updateHolidaysForm", new FacesMessage("Feriado alterado com sucesso"));
			errorsHandler.updateHoliday(true, null);
			return "config_holidays_edit.xhtml";
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.updateHoliday(false, errors);

		}
		return null;
	}

	public List<HolidayDto> findAllHolidays() {
		holidaysList = holidayBridge.getAllHolidays();
		return holidaysList;
	}
	public void cleanFields(){
		holidayname=null;
		day=null;
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	////////////////////////////////////////////////////////////////////////////////// 

	public ListHolidayBean(List<HolidayDto> holidaysList, HolidayDto selectedHoliday) {
		this.holidaysList = holidaysList;
		this.selectedHoliday = selectedHoliday;
	}
	public List<HolidayDto> getHolidaysList() {
		return holidaysList;
	}
	public void setHolidaysList(List<HolidayDto> holidaysList) {
		this.holidaysList = holidaysList;
	}
	public HolidayDto getSelectedHoliday() {
		return selectedHoliday;
	}
	public void setSelectedHoliday(HolidayDto selectedHoliday) {
		this.selectedHoliday = selectedHoliday;
	}
	public String getHolidayname() {
		return holidayname;
	}
	public void setHolidayname(String holidayname) {
		this.holidayname = holidayname;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
}
