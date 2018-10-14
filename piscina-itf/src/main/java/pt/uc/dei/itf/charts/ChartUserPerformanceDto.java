package pt.uc.dei.itf.charts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChartUserPerformanceDto {

	private String email;
	private String fullName;
	private Date beginPeriod;
	private Date endPeriod;
	private List <UserPerformanceDto> workList;

	public ChartUserPerformanceDto() {
		this.email = "";
		this.fullName = "";
		this.beginPeriod = new Date();
		this.endPeriod = new Date();
		this.workList = new ArrayList <UserPerformanceDto>();
	}

	public ChartUserPerformanceDto(String email, String fullName, Date beginPeriod,
			Date endPeriod) {
		this.email = email;
		this.fullName = fullName;
		this.beginPeriod = beginPeriod;
		this.endPeriod = endPeriod;
	}

	public ChartUserPerformanceDto(String email, String fullName, Date beginPeriod,
			Date endPeriod, List<UserPerformanceDto> workList) {
		this.email = email;
		this.fullName = fullName;
		this.beginPeriod = beginPeriod;
		this.endPeriod = endPeriod;
		this.workList = workList;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public List<UserPerformanceDto> getWorkList() {
		return workList;
	}
	public void setWorkList(List<UserPerformanceDto> workList) {
		this.workList = workList;
	}
	public Date getBeginPeriod() {
		return beginPeriod;
	}
	public void setBeginPeriod(Date beginPeriod) {
		this.beginPeriod = beginPeriod;
	}
	public Date getEndPeriod() {
		return endPeriod;
	}
	public void setEndPeriod(Date endPeriod) {
		this.endPeriod = endPeriod;
	}

}
