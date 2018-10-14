package pt.uc.dei.itf.dtos;

import java.util.Date;
import java.util.List;

public class DatesPercentages {
	
	private List<Date> datesList;
	private List<Float> percentageList;
	
	public DatesPercentages() {
	}
	
	public DatesPercentages(List<Date> datesList, List<Float> percentageList) {
		this.datesList = datesList;
		this.percentageList = percentageList;
	}

	public List<Date> getDatesList() {
		return datesList;
	}
	public void setDatesList(List<Date> datesList) {
		this.datesList = datesList;
	}
	public List<Float> getPercentageList() {
		return percentageList;
	}
	public void setPercentageList(List<Float> percentageList) {
		this.percentageList = percentageList;
	}

}
