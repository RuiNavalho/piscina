package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.piscina.itf.dtos.StageDto;
import pt.uc.dei.ws.bridges.StageBridge;

@Named("listStagesBean")
@SessionScoped
public class ListStagesBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<StageDto> stagesList;
	private StageDto selectedStage;
	
	@Inject
	private StageBridge stageBridge;
	
	public ListStagesBean() {	
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public List<StageDto> getStagesList() {
		if (stagesList!=null) {
			return stagesList;
		} else {
			stagesList=stageBridge.getAllStages();
			return stagesList;
		}	
	}

	public void setStagesList(List<StageDto> stagesList) {
		this.stagesList = stagesList;
	}

	public StageDto getSelectedStage() {
		return selectedStage;
	}

	public void setSelectedStage(StageDto selectedStage) {
		this.selectedStage = selectedStage;
	}
}
