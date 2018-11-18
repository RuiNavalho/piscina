package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import pt.piscina.itf.dtos.SkillDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.SkillBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named("listSkillsBean")
@SessionScoped
public class ListSkillsBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<SkillDto> skillsList;
	private SkillDto selectedSkill;
	private String skill;
	
	@Inject
	private SkillBridge skillBridge;
	
	@Inject
	private	SingletonBean singletonBean;
	
	@Inject
	private ErrorsHandler errorsHandler;
	
	public ListSkillsBean() {	
	}
	
	public String createSkill() {
		SkillDto skillDto= new SkillDto(skill);
		Response response = skillBridge.createSkill(skillDto);
		if (response.getStatus()==200) {
			errorsHandler.createSkill(true, null);
			singletonBean.addNewSkill(skillDto);
			
			errorsHandler.createSkill(true, null);
			return "skills_new.xhtml";
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.createSkill(false, errors);
		}
		return null;
	}
	
	//TODO
	public void updateSkills() {
		
	}
	
	public void cleanFields(){
		skill="";
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public List<SkillDto> getSkillsList() {
		if (skillsList!=null) {
			return skillsList;
		} else {
			return skillBridge.getAllSkills();
		}	
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public SkillDto getSelectedSkill() {
		return selectedSkill;
	}

	public void setSelectedSkill(SkillDto selectedSkill) {
		this.selectedSkill = selectedSkill;
	}
	

}
