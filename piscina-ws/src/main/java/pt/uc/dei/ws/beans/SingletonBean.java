package pt.uc.dei.ws.beans;

import java.util.List;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.piscina.itf.dtos.BusinessDto;
import pt.piscina.itf.dtos.RoleDto;
import pt.piscina.itf.dtos.SkillDto;
import pt.piscina.itf.dtos.StageDto;
import pt.piscina.itf.dtos.TaskstageDto;
import pt.piscina.itf.dtos.TasktypeDto;
import pt.piscina.itf.dtos.TypologyDto;
import pt.uc.dei.ws.bridges.BusinessBridge;
import pt.uc.dei.ws.bridges.RoleBridge;
import pt.uc.dei.ws.bridges.SkillBridge;
import pt.uc.dei.ws.bridges.StageBridge;
import pt.uc.dei.ws.bridges.TypologyBridge;

@Named("util")
@Singleton
public class SingletonBean {

	private List<RoleDto> rolesList;
	private List<BusinessDto> businessList;
	private List<SkillDto> skillsList;
	private List<StageDto> stagesList;
	private List<TypologyDto> typologiesList;
	private List<TaskstageDto> taskstagesList;
	private List<TasktypeDto> tasktypesList;

	@Inject
	private RoleBridge roleBridge;

	@Inject
	private BusinessBridge businessBridge;
	
	@Inject
	private SkillBridge skillBridge;
	
	@Inject
	private StageBridge stageBridge;
	
	@Inject
	private TypologyBridge typologyBridge;

	public SingletonBean() {
	}

	public void addNewTasktype(TasktypeDto tasktypeDto) {
		if (tasktypesList==null) {
			this.tasktypesList=getTasktypesList();
		}
		tasktypesList.add(tasktypeDto);
	}

	public void addNewTaskstage(TaskstageDto taskstageDto) {
		if (taskstagesList==null) {
			this.taskstagesList=getTaskstagesList();
		}
		taskstagesList.add(taskstageDto);
	}

	public void addNewTypology(TypologyDto typologyDto) {
		if (typologiesList==null) {
			this.typologiesList=getTypologiesList();
		}
		typologiesList.add(typologyDto);
	}

	public void addNewStage(StageDto stageDto) {
		if (stagesList==null) {
			this.stagesList=getStagesList();
		}
		stagesList.add(stageDto);
	}

	public void addNewSkill(SkillDto skillDto) {
		if (skillsList==null) {
			this.skillsList=getSkillsList();
		}
		skillsList.add(skillDto);
	}

	public void addNewBusiness(BusinessDto businessDto) {
		if (businessList==null) {
			this.businessList=getBusinessList();
		}
		businessList.add(businessDto);
	}

	public void addNewRole(RoleDto roleDto) {
		if (rolesList==null) {
			this.rolesList=getRolesList();
		}
		rolesList.add(roleDto);
	}


	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public List<RoleDto> getRolesList() {
		if (rolesList==null) {
			rolesList = roleBridge.getAllRoles();	
		} 	
		return rolesList;
	}

	public void setRolesList(List<RoleDto> rolesList) {
		 this.rolesList = rolesList;
	}

	public List<BusinessDto> getBusinessList() {
		if (businessList==null) {
			businessList= businessBridge.getAllBusinesses();	
		}
		return businessList;	
	}

	public void setBusinessList(List<BusinessDto> businessList) {
		 this.businessList = businessList;
	}

	public List<SkillDto> getSkillsList() {
		if (skillsList==null) {
			skillsList= skillBridge.getAllSkills();
		}
		return skillsList;
	}

	public void setSkillsList(List<SkillDto> skillsList) {
		 this.skillsList = skillsList;
	}

	public List<StageDto> getStagesList() {
		if (stagesList==null) {
			stagesList=stageBridge.getAllStages();
		}	
		return stagesList;
	}

	public void setStagesList(List<StageDto> stagesList) {
		this.stagesList = stagesList;
	}

	public List<TypologyDto> getTypologiesList() {
		if (typologiesList==null) {
			typologiesList=typologyBridge.getAllTypologies();
		}
		return typologiesList;
	}

	public void setTypologiesList(List<TypologyDto> typologiesList) {
		this.typologiesList = typologiesList;
	}

	public List<TaskstageDto> getTaskstagesList() {
		if (taskstagesList==null) {
			final String URI="http://localhost:8080/aclgomes-rnavalho-project-ds/taskstage";
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getAll").request(MediaType.APPLICATION_JSON).get(Response.class);
			if (response.getStatus()==200) {
				taskstagesList = response.readEntity(new GenericType<List<TaskstageDto>>() {});
			}
			client.close();
		}
		return taskstagesList;
	}

	public void setTaskstagesList(List<TaskstageDto> taskstagesList) {
		this.taskstagesList = taskstagesList;
	}

	public List<TasktypeDto> getTasktypesList() {
		if (tasktypesList==null) {
			final String URI="http://localhost:8080/aclgomes-rnavalho-project-ds/tasktype";
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getAll").request(MediaType.APPLICATION_JSON).get(Response.class);
			if (response.getStatus()==200) {
				tasktypesList = response.readEntity(new GenericType<List<TasktypeDto>>() {});
			}
			client.close();
		}
		return tasktypesList;
	}

	public void setTasktypesList(List<TasktypeDto> tasktypesList) {
		this.tasktypesList = tasktypesList;
	}

}
