package pt.uc.dei.ws.charts;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.highfaces.component.api.ChartModel;
import org.highfaces.component.api.impl.DefaultChartModel;
import org.highfaces.component.api.impl.DefaultChartSeries;
import pt.uc.dei.itf.charts.ChartUserPerformanceDto;
import pt.uc.dei.itf.charts.UserPerformanceDto;
import pt.uc.dei.ws.bridges.ChartBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named("chartBar")
@SessionScoped
public class ChartBarUserPerformance implements Serializable {  

	private static final long serialVersionUID = 1L;

    private DefaultChartModel model;
    
    private List<ChartUserPerformanceDto> listCup;
    private ChartUserPerformanceDto cup;
    private Date beginDate;
    private Date endDate;
    private String email;
    
    private List<UserPerformanceDto> workList;
    
	@Inject
	private ChartBridge chartBridge;
	
	@Inject
	private ErrorsHandler errorsHandler;
    
    public ChartBarUserPerformance() {
    }
    
	public void findAllUsersPerformanceCharts() {

		Response response = chartBridge.findAllUsersPerformanceCharts(beginDate, endDate);
		if (response.getStatus()==200) {
			this.listCup = response.readEntity(new GenericType<List<ChartUserPerformanceDto>>() {});
			reloadCupList();
		}
	}
	
	public void findUserPerformanceChart() {
		Response response = chartBridge.getUserPerformanceChart(email, beginDate, endDate);
		if (response.getStatus()==200) {
			this.cup = response.readEntity(ChartUserPerformanceDto.class);
			reloadCup();
		}
	}

	public void reloadCupList() {
        model = new DefaultChartModel();
        DefaultChartSeries boysSeries = new DefaultChartSeries();
        DefaultChartSeries girlsSeries = new DefaultChartSeries();
        boysSeries.setName("Boys");
        girlsSeries.setName("Girls");
         
        Random r = new Random();
        for (int i = 2000; i < 2010; i++) {
            boysSeries.addPoint(Integer.toString(i), r.nextInt(500) + 800);
            girlsSeries.addPoint(Integer.toString(i), r.nextInt(500) + 800);
        }
        model.getSeries().add(boysSeries);
        model.getSeries().add(girlsSeries);
    }
	
    private void reloadCup() {
        model = new DefaultChartModel();
        DefaultChartSeries userSeries = new DefaultChartSeries();
        userSeries.setName(cup.getFullName());
        workList = cup.getWorkList();
        for (int i=0; i<workList.size(); i++) {
            userSeries.addPoint(workList.get(i).getDate().toString(), workList.get(i).getHoursProduced());
        }
        model.getSeries().add(userSeries);

	}
 
    public ChartModel getModel() {
        return model;
    }

	public List<ChartUserPerformanceDto> getListCup() {
		return listCup;
	}

	public void setListCup(List<ChartUserPerformanceDto> listCup) {
		this.listCup = listCup;
	}

	public ChartUserPerformanceDto getCup() {
		return cup;
	}

	public void setCup(ChartUserPerformanceDto cup) {
		this.cup = cup;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ChartBridge getChartBridge() {
		return chartBridge;
	}

	public void setChartBridge(ChartBridge chartBridge) {
		this.chartBridge = chartBridge;
	}

	public ErrorsHandler getErrorsHandler() {
		return errorsHandler;
	}

	public void setErrorsHandler(ErrorsHandler errorsHandler) {
		this.errorsHandler = errorsHandler;
	}

	public void setModel(DefaultChartModel model) {
		this.model = model;
	}

	public List<UserPerformanceDto> getWorkList() {
		return workList;
	}

	public void setWorkList(List<UserPerformanceDto> workList) {
		this.workList = workList;
	}
    
}
