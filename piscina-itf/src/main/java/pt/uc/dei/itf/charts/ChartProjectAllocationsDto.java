package pt.uc.dei.itf.charts;

import java.util.Date;
import java.util.List;
import pt.uc.dei.itf.dtos.AllocationDto;

public class ChartProjectAllocationsDto {
	
	private String title;
	private Date beginDate;
	private Date endDate;
	private List <AllocationDto> allocsList;
	
	public ChartProjectAllocationsDto() {
	}
	
	public ChartProjectAllocationsDto(String title, Date beginDate, Date endDate, List<AllocationDto> allocsList) {
		this.title = title;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.allocsList = allocsList;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String projectId) {
		this.title = projectId;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<AllocationDto> getAllocsList() {
		return allocsList;
	}
	public void setAllocsList(List<AllocationDto> allocsList) {
		this.allocsList = allocsList;
	}

}
