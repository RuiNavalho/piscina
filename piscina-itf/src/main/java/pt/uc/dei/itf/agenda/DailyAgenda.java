package pt.uc.dei.itf.agenda;

import java.util.Date;
import java.util.List;

public class DailyAgenda {
	
	private Date date;
	private List<SimpleAllocDto> allocList;
	
	public DailyAgenda() {
	}

	public DailyAgenda(Date date, List<SimpleAllocDto> allocList) {
		this.date = date;
		this.allocList = allocList;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<SimpleAllocDto> getAllocList() {
		return allocList;
	}
	public void setAllocList(List<SimpleAllocDto> allocList) {
		this.allocList = allocList;
	}

	
}
