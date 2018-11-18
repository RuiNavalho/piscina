package pt.piscina.ds.services;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import pt.piscina.ds.daos.BusinessDao;
import pt.piscina.ds.daos.ClientDao;
import pt.piscina.ds.daos.ProjectDao;
import pt.piscina.ds.daos.TaskDao;
import pt.piscina.ds.daos.TaskworkDao;
import pt.piscina.ds.daos.UserDao;
import pt.piscina.ds.entities.Allocation;
import pt.piscina.ds.entities.Project;
import pt.piscina.ds.entities.Task;
import pt.piscina.ds.entities.Taskwork;
import pt.piscina.ds.entities.User;
import pt.piscina.ds.util.InternalError;
import pt.piscina.ds.util.TimeCalc;
import pt.piscina.itf.dtos.AllocationDto;
import pt.uc.dei.itf.charts.ChartCountAndBudgetDto;
import pt.uc.dei.itf.charts.ChartCpiSpiDto;
import pt.uc.dei.itf.charts.ChartProjectAllocationsDto;
import pt.uc.dei.itf.charts.ChartProjectGanttDto;
import pt.uc.dei.itf.charts.ChartTimePercHoursCost;
import pt.uc.dei.itf.charts.ChartUserPerformanceDto;
import pt.uc.dei.itf.charts.CountAndBudgetDto;
import pt.uc.dei.itf.charts.CpiSpiDto;
import pt.uc.dei.itf.charts.HourPercCostDto;
import pt.uc.dei.itf.charts.TaskGanttDto;
import pt.uc.dei.itf.charts.UserPerformanceDto;

@Stateless
public class ChartService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(ChartService.class);

	@Inject
	private UserDao userDao;

	@Inject
	private TaskworkDao taskworkDao;

	@Inject
	private TaskDao taskDao;

	@Inject
	private ProjectDao projectDao;

	@Inject
	private ClientDao clientDao;

	@Inject
	private BusinessDao businessDao;

	@Inject
	private TaskService taskService;
	
	@Inject
	private ProjectService projectService;

	public ChartService() {
	}
	
	public Response getProjectCpiSpiTimeChart(String projectId) {
		try {	
			Project project = projectDao.getProjectByUniqueIdproject(projectId);
			ChartCpiSpiDto chart= new ChartCpiSpiDto();
			chart.setTitle(projectId);
			List<Taskwork> twList = projectDao.findProjectTaskworksByAscendingDate(project);
			List<CpiSpiDto> list = getCpiSpiDtoList(twList);
			chart.setList(list);
			return Response.ok(chart).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getTaskAllocationsChart(String taskId) {
		try {	
			Task task = taskDao.find(Integer.parseInt(taskId));
			ChartProjectAllocationsDto cpa= new ChartProjectAllocationsDto();
			cpa.setBeginDate(task.getBeginDate());
			cpa.setEndDate(task.getEndDate());
			cpa.setTitle(task.getTaskName());
			List<Allocation> list = taskDao.findTaskAllocations(task);
			List<AllocationDto> allocsDto = getProjectAllocationsAsDto(list, task.getTaskName());
			cpa.setAllocsList(allocsDto);
			return Response.ok(cpa).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getProjectAllocationsChart(String projectId) {
		try {	
			Project project = projectDao.getProjectByUniqueIdproject(projectId);
			ChartProjectAllocationsDto cpa= new ChartProjectAllocationsDto();
			cpa.setBeginDate(project.getBeginDate());
			cpa.setEndDate(project.getEndDate());
			cpa.setTitle(projectId);
			List<Allocation> list = projectDao.getAllProjectAllocations(project.getId());
			List<AllocationDto> allocsDto = getProjectAllocationsAsDto(list, projectId);
			cpa.setAllocsList(allocsDto);
			return Response.ok(cpa).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getProjectGanttChart(String projectId) {
		try {	
			Project project = projectDao.getProjectByUniqueIdproject(projectId);
			ChartProjectGanttDto gantt= new ChartProjectGanttDto();
			gantt.setBeginDate(project.getBeginDate());
			gantt.setEndDate(project.getEndDate());
			gantt.setProjectId(projectId);
			gantt.setTitle(project.getTitle());
			List<Task> list = projectDao.getProjectTasks(project.getId());
			List<TaskGanttDto> tasksList = getProjectTaskGanttAsDto(list);
			gantt.setTasksList(tasksList);
			return Response.ok(gantt).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getUserPerformanceChart(String email, String beginDate, String endDate) {
		try {	
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date beginDate1 = sdf.parse(beginDate);
			Date endDate1 = sdf.parse(endDate);
			User user = userDao.getUserWithEmail(email);

			ChartUserPerformanceDto cup = new ChartUserPerformanceDto(email, user.getFullName(), beginDate1, endDate1);
			List<Taskwork> list = taskworkDao.findWorkerTaskworksBetweenDates(email, beginDate1, endDate1);
			List <UserPerformanceDto> workList = createUserPerformanceDtoList(list);
			cup.setWorkList(workList);
			return Response.ok(cup).build();
		} catch (ParseException e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response findAllUsersPerformanceCharts(String beginDate, String endDate) {
		try {	
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date beginDate1 = sdf.parse(beginDate);
			Date endDate1 = sdf.parse(endDate);
			List<ChartUserPerformanceDto> allUsersCUP =new ArrayList<ChartUserPerformanceDto>();
			List<User> users = userDao.findUsersWithAtLeastOneOfTwoRoles("Director", "User");
			for (int i=0; i<users.size(); i++) {
				ChartUserPerformanceDto cup = new ChartUserPerformanceDto(users.get(i).getEmail(), users.get(i).getFullName(), beginDate1, endDate1);
				List<Taskwork> list = taskworkDao.findWorkerTaskworksBetweenDates(users.get(i).getEmail(), beginDate1, endDate1);
				List <UserPerformanceDto> workList = createUserPerformanceDtoList(list);
				cup.setWorkList(workList);
				allUsersCUP.add(cup);
			}
			return Response.ok(allUsersCUP).build();
		} catch (ParseException e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getTaskHourPercCostChart(String taskId) {
		try {
			Task task = taskDao.find(Integer.parseInt(taskId));

			Timestamp today = TimeCalc.todayMidnight();
			Timestamp end = task.getEndDate();
			Timestamp begin = task.getBeginDate();	
			if (end.after(today)) {
				end = today;
			}
			if (begin.after(today)) {
				begin = today;
			}

			ChartTimePercHoursCost chart= new ChartTimePercHoursCost();
			chart.setBegin(task.getBeginDate());
			chart.setEnd(task.getEndDate());
			chart.setTitle(task.getTaskName());
			chart.setBudget(null);
			List<Taskwork> twList = taskDao.getTaskTaskworkByAscendingDate(task);
			Long findTotalHoursWorkedInTask = taskDao.findWorkedHours(task);
			if (findTotalHoursWorkedInTask==null) {
				findTotalHoursWorkedInTask=0L;
			}
			int hoursToFinish = task.getRemainingHours();
			int totalTaskDuration = (findTotalHoursWorkedInTask.intValue()+hoursToFinish);
			chart.setEstimateDurationInHours(totalTaskDuration);
			List<HourPercCostDto> list = getHourPercCostDtoList(twList, totalTaskDuration, begin, end);
			chart.setList(list);
			return Response.ok(chart).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getProjectHourPercCostChart(String projectId) {
		try {
			Project project = projectDao.getProjectByUniqueIdproject(projectId);
			Timestamp today = TimeCalc.todayMidnight();
			Timestamp end = project.getEndDate();
			Timestamp begin = project.getBeginDate();		
			if (end.after(today)) {
				end = today;
			}
			if (begin.after(today)) {
				begin = today;
			}
			ChartTimePercHoursCost chart= new ChartTimePercHoursCost();
			chart.setBegin(project.getBeginDate());
			chart.setEnd(project.getEndDate());
			chart.setTitle(projectId);
			chart.setBudget(project.getBudget());
			List<Taskwork> twList = projectDao.findProjectTaskworksByAscendingDate(project);
			Long findTotalHoursWorkedInProject = projectDao.findTotalHoursWorkedInProject(project);
			if (findTotalHoursWorkedInProject==null) {
				findTotalHoursWorkedInProject=0L;
			}
			Long findEstimatedHoursToFinishProject = projectDao.findEstimatedHoursToFinishProject(project);
			if (findEstimatedHoursToFinishProject==null) {
				findEstimatedHoursToFinishProject=0L;
			}
			int projectTotalEstimateDuration = (findTotalHoursWorkedInProject.intValue()+findEstimatedHoursToFinishProject.intValue());
			chart.setEstimateDurationInHours(projectTotalEstimateDuration);
			List<HourPercCostDto> list = getHourPercCostDtoListProj(twList, projectTotalEstimateDuration, begin, end);
			chart.setList(list);
			return Response.ok(chart).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getClientTimeCharts(String company) {
		try {
			Timestamp today = TimeCalc.todayMidnight();	
			ChartCountAndBudgetDto chart= new ChartCountAndBudgetDto();
			chart.setTitle(company);			
			List<CountAndBudgetDto> list = new  ArrayList<CountAndBudgetDto>();

			List<Project> projList = clientDao.findAllClientProjectsByAscendingCreationDate(company);

			float budgetCumulative=0;
			int projCountCumulative=0;

			if (projList.size()>0){
				budgetCumulative=projList.get(0).getBudget();
				projCountCumulative++;
				list.add(new CountAndBudgetDto(projList.get(0).getCreationDate(), budgetCumulative, projCountCumulative));
			}

			for (int i=1; i<projList.size(); i++) {
				budgetCumulative=budgetCumulative+projList.get(i).getBudget();
				projCountCumulative++;

				if (projList.get(i).getCreationDate().after(list.get(list.size()-1).getDate())) {
					list.add(new CountAndBudgetDto(projList.get(i).getCreationDate(), budgetCumulative, projCountCumulative));
				} else {
					list.get(list.size()-1).setBudget(budgetCumulative);
					list.get(list.size()-1).setProjCount(projCountCumulative);
				}


			}
			chart.setBudgetThis(budgetCumulative);
			chart.setProjCountThis(projCountCumulative);
			chart.setProjCountOthers(projectDao.findAll().size()-projCountCumulative);
			Double calculateProjectsTotalBudget = projectDao.calculateProjectsTotalBudget();
			float budgetOthers= calculateProjectsTotalBudget.floatValue()-budgetCumulative;
			chart.setBudgetOthers(budgetOthers);
			if (projList.size()>0) {
				chart.setBeginDate(projList.get(0).getCreationDate());
			} else {
				chart.setBeginDate(today);
			}
			chart.setEndDate(today);
			list.add(new CountAndBudgetDto(today, budgetCumulative, projCountCumulative));

			chart.setList(list);
			return Response.ok(chart).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}


	public Response getBusinessTimeCharts(String area) {
		try {
			Timestamp today = TimeCalc.todayMidnight();	
			ChartCountAndBudgetDto chart= new ChartCountAndBudgetDto();
			chart.setTitle(area);			
			List<CountAndBudgetDto> list = new  ArrayList<CountAndBudgetDto>();

			List<Project> projList = businessDao.findAllBusinessProjectsByAscendingCreationDate(area);

			float budgetCumulative=0;
			int projCountCumulative=0;

			if (projList.size()>0){
				budgetCumulative=projList.get(0).getBudget();
				projCountCumulative++;
				list.add(new CountAndBudgetDto(projList.get(0).getCreationDate(), budgetCumulative, projCountCumulative));
			}

			for (int i=1; i<projList.size(); i++) {
				budgetCumulative=budgetCumulative+projList.get(i).getBudget();
				projCountCumulative++;	
				if (projList.get(i).getCreationDate().after(list.get(list.size()-1).getDate())) {
					list.add(new CountAndBudgetDto(projList.get(i).getCreationDate(), budgetCumulative, projCountCumulative));
				} else {
					list.get(list.size()-1).setBudget(budgetCumulative);
					list.get(list.size()-1).setProjCount(projCountCumulative);
				}
			}

			chart.setBudgetThis(budgetCumulative);
			chart.setProjCountThis(projCountCumulative);
			chart.setProjCountOthers(projectDao.findAll().size()-projCountCumulative);
			Double calculateProjectsTotalBudget = projectDao.calculateProjectsTotalBudget();
			float budgetOthers= calculateProjectsTotalBudget.floatValue()-budgetCumulative;
			chart.setBudgetOthers(budgetOthers);
			if (projList.size()>0) {
				chart.setBeginDate(projList.get(0).getCreationDate());
			} else {
				chart.setBeginDate(today);
			}
			chart.setEndDate(today);
			list.add(new CountAndBudgetDto(today, budgetCumulative, projCountCumulative));
			chart.setList(list);
			return Response.ok(chart).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// Private Auxiliary Methods ///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	private List<HourPercCostDto> getHourPercCostDtoList(List<Taskwork> twList, int durationInHours,
			Timestamp begin, Timestamp end) {
		List<HourPercCostDto> list = new ArrayList<HourPercCostDto>();
		float percExec=0;
		float cost=0;
		int hoursWorked=0;	
		list.add(new HourPercCostDto(begin, percExec, cost, hoursWorked));
		for (int i=0; i<twList.size(); i++) {
			hoursWorked= hoursWorked+twList.get(i).getHoursWorked();
			percExec= (durationInHours-twList.get(i).getRemainingHours())*100/durationInHours;
			cost = cost + twList.get(i).getHoursWorked()*twList.get(i).getTask().getHourCost();
			if (twList.get(i).getCreationDate().after(list.get(list.size()-1).getDate())) {
				list.add(new HourPercCostDto(twList.get(i).getCreationDate(), percExec, cost, hoursWorked));
			} else {
				list.get(list.size()-1).setCost(cost);
				list.get(list.size()-1).setHoursWorked(hoursWorked);
				list.get(list.size()-1).setPercExec(percExec);
			}	
		}
		if (begin.before(end)){
			list.add(new HourPercCostDto(end, percExec, cost, hoursWorked));
		}
		return list;
	}
	
	private List<HourPercCostDto> getHourPercCostDtoListProj(List<Taskwork> twList, int durationInHours,
			Timestamp begin, Timestamp end) {
		List<HourPercCostDto> list = new ArrayList<HourPercCostDto>();
		float percExec=0;
		float cost=0;
		int hoursWorked=0;	
		list.add(new HourPercCostDto(begin, percExec, cost, hoursWorked));
		for (int i=0; i<twList.size(); i++) {
			hoursWorked= hoursWorked+twList.get(i).getHoursWorked();
			percExec= hoursWorked*100/durationInHours;
			cost = cost + twList.get(i).getHoursWorked()*twList.get(i).getTask().getHourCost();
			if (twList.get(i).getCreationDate().after(list.get(list.size()-1).getDate())) {
				list.add(new HourPercCostDto(twList.get(i).getCreationDate(), percExec, cost, hoursWorked));
			} else {
				list.get(list.size()-1).setCost(cost);
				list.get(list.size()-1).setHoursWorked(hoursWorked);
				list.get(list.size()-1).setPercExec(percExec);
			}	
		}
		if (begin.before(end)){
			list.add(new HourPercCostDto(end, percExec, cost, hoursWorked));
		}
		return list;
	}

	private List<AllocationDto> getProjectAllocationsAsDto(List<Allocation> all, String title) {
		List<AllocationDto> list = new ArrayList<AllocationDto>();
		for (int i=0; i<all.size(); i++) {
			list.add(new AllocationDto(all.get(i).getAllocPercentage(), all.get(i).getTask().getId(),
					all.get(i).getUser().getEmail(),all.get(i).getUser().getFullName(), null, all.get(i).getBeginDate(), all.get(i).getEndDate(), all.get(i).getId(), all.get(i).getTask().getEndDate(), all.get(i).getTask().getTaskName(), title));
		}
		return list;
	}


	private List<TaskGanttDto> getProjectTaskGanttAsDto(List<Task> all) {
		List<TaskGanttDto> list = new ArrayList<TaskGanttDto>();
		for (int i=0; i<all.size(); i++) {
			int perc = taskService.getExecutedPercentageEstimate(all.get(i));
			list.add(new TaskGanttDto(all.get(i).getId(), all.get(i).getTaskName(), all.get(i).getBeginDate(), all.get(i).getEndDate(), perc));
		}
		return list;
	}

	/**
	 * User Performance Index (UPI) = (W + ER -R)/W
	 * W - worked hours
	 * ER - Expected Remaining Hours
	 * R - Remaining hours
	 * Positive UPI indicates that task advanced with work (has less remaining hours than before) - UPI value indicates how much %
	 * advanced compared to expected.
	 * Examples:
	 * UPI=1 indicates task advanced as expected.
	 * UPI=2 means task advanced twice as expected.
	 * UPI = 0.5 means task advanced half has expected.
	 * UPI = 0 means task did not advance
	 * UPI negative value indicates that task regressed - need now more hours to complete than before
	 * UPI = -1 indicates that task regressed the amount of hours worked
	 * UPI = -2 indicates that task regressed twice the amount of hours worked
	 * @param list
	 * @return
	 */
	private List<UserPerformanceDto> createUserPerformanceDtoList(List<Taskwork> list) {
		List <UserPerformanceDto> workList = new ArrayList<UserPerformanceDto>();
		if (list.size()>0) {	
			int hoursProduced = list.get(0).getExpectedRemainingHours()-list.get(0).getRemainingHours();
			int hoursWorked = list.get(0).getHoursWorked();
			int erh= list.get(0).getExpectedRemainingHours();
			int rh = list.get(0).getRemainingHours();
			float prodIndex=(hoursWorked+erh-rh)*1.0f/hoursWorked;		
			UserPerformanceDto up = new UserPerformanceDto();	
			up.setDate(list.get(0).getCreationDate());
			up.setHoursProduced(hoursProduced);
			up.setHoursWorked(hoursWorked);
			up.setProdIndex(prodIndex);
			workList.add(up);
		}
		for (int i=1; i<list.size(); i++) {
			int hoursProduced = list.get(i).getExpectedRemainingHours()-list.get(i).getRemainingHours();
			int hoursWorked = list.get(i).getHoursWorked();
			int erh= list.get(i).getExpectedRemainingHours();
			int rh = list.get(i).getRemainingHours();
			float prodIndex=(hoursWorked+erh-rh)*1.0f/hoursWorked;
			if (list.get(i).getCreationDate().after(workList.get(workList.size()-1).getDate())) {
				UserPerformanceDto up = new UserPerformanceDto();	
				up.setDate(list.get(i).getCreationDate());
				up.setHoursProduced(hoursProduced);
				up.setHoursWorked(hoursWorked);
				up.setProdIndex(prodIndex);
				workList.add(up);
			} else {
				workList.get(workList.size()-1).setHoursProduced(workList.get(workList.size()-1).getHoursProduced()+hoursProduced);
				workList.get(workList.size()-1).setHoursWorked(workList.get(workList.size()-1).getHoursWorked()+hoursWorked);
				workList.get(workList.size()-1).setProdIndex((workList.get(workList.size()-1).getProdIndex()+prodIndex)*1.0f/2);	
			}
		}
		return workList;
	}

	private List<CpiSpiDto> getCpiSpiDtoList(List<Taskwork> twList) {
		List<CpiSpiDto> list = new ArrayList<CpiSpiDto>();
		if (twList.size()>0) {	
			Double cpi = projectService.calculateWeightedCpiInDate(twList.get(0).getTask().getProject(), twList.get(0).getCreationDate());
			Double spi = projectService.calculateWeightedSpiInDate(twList.get(0).getTask().getProject(), twList.get(0).getCreationDate());
			list.add(new CpiSpiDto(new Date(twList.get(0).getCreationDate().getTime()), cpi, spi));
		}
		for (int i=1; i<twList.size(); i++) {
			if (twList.get(i).getCreationDate().after(list.get(list.size()-1).getDate())) {
				Double cpi = projectService.calculateWeightedCpiInDate(twList.get(i).getTask().getProject(), twList.get(i).getCreationDate());
				Double spi = projectService.calculateWeightedSpiInDate(twList.get(i).getTask().getProject(), twList.get(i).getCreationDate());
				list.add(new CpiSpiDto(new Date(twList.get(i).getCreationDate().getTime()), cpi, spi));
			} else {
				//list.get(list.size()-1).setCpi( list.get(list.size()-1).getCpi() );
	
			}
		}
		return list;
	}









}
