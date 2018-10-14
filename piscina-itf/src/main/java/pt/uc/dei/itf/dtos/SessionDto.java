package pt.uc.dei.itf.dtos;

import java.util.List;

import pt.uc.dei.itf.agenda.DailyAgenda;
import pt.uc.dei.itf.agenda.SimpleRegisterDto;

public class SessionDto {

	private String token;
	private DailyAgenda dailyAgenda;
	private UserLoggedDto loggedUser;
	private List<SimpleRegisterDto> todayRegisterList;
	
	public SessionDto() {
	}
	
	public SessionDto(String token, DailyAgenda dailyAgenda, UserLoggedDto loggedUser,
			List<SimpleRegisterDto> todayRegisterList) {
		this.token = token;
		this.dailyAgenda = dailyAgenda;
		this.loggedUser = loggedUser;
		this.todayRegisterList = todayRegisterList;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public DailyAgenda getDailyAgenda() {
		return dailyAgenda;
	}
	public void setDailyAgenda(DailyAgenda dailyAgenda) {
		this.dailyAgenda = dailyAgenda;
	}
	public UserLoggedDto getLoggedUser() {
		return loggedUser;
	}
	public void setLoggedUser(UserLoggedDto loggedUser) {
		this.loggedUser = loggedUser;
	}
	public List<SimpleRegisterDto> getTodayRegisterList() {
		return todayRegisterList;
	}
	public void setTodayRegisterList(List<SimpleRegisterDto> todayRegisterList) {
		this.todayRegisterList = todayRegisterList;
	}
	
}
