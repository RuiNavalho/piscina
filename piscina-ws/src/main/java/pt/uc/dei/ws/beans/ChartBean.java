package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import pt.uc.dei.itf.charts.ChartUserPerformanceDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.ChartBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named("chartBean")
@SessionScoped
public class ChartBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userEmail;
	private Date begin;
	private Date end;
	private ChartUserPerformanceDto cup;
	private List<ChartUserPerformanceDto> usersCup;
	
	@Inject
	private ChartBridge chartBridge;
	
	@Inject
	private ErrorsHandler errorsHandler;
	
	public ChartBean() {
	}
	
	public ChartUserPerformanceDto findUserPerformanceChart() {
		Response response = chartBridge.getUserPerformanceChart(userEmail, begin, end);
		if (response.getStatus()==200) {
			this.cup = response.readEntity(ChartUserPerformanceDto.class);
			return cup;
		} else {			
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.findUserPerformanceChart(false, errors);
			return null;
		}
	}
	
	public List<ChartUserPerformanceDto> findAllUsersPerformanceCharts() {
		Response response = chartBridge.findAllUsersPerformanceCharts(begin, end);
		if (response.getStatus()==200) {
			this.usersCup = response.readEntity(new GenericType<List<ChartUserPerformanceDto>>() {});
			return usersCup;
		} else {			
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.findAllUsersPerformanceCharts(false, errors);
			return null;
		}
	}

}
