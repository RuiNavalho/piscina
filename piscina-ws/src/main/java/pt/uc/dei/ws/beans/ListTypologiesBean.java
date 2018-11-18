package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.piscina.itf.dtos.TypologyDto;
import pt.uc.dei.ws.bridges.TypologyBridge;

@Named("listTypologiesBean")
@SessionScoped
public class ListTypologiesBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<TypologyDto> typologiesList;
	private TypologyDto selectedTypology;
	
	@Inject
	private TypologyBridge typologyBridge;
	
	public ListTypologiesBean() {	
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public List<TypologyDto> getTypologiesList() {
		if (typologiesList!=null) {
			return typologiesList;
		} else {
			typologiesList=typologyBridge.getAllTypologies();
			return typologiesList;
		}	
	}

	public TypologyDto getSelectedTypology() {
		return selectedTypology;
	}

	public void setSelectedTypology(TypologyDto selectedTypology) {
		this.selectedTypology = selectedTypology;
	}

	public void setTypologiesList(List<TypologyDto> typologiesList) {
		this.typologiesList = typologiesList;
	}

}
