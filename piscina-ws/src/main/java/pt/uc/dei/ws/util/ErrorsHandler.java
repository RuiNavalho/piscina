package pt.uc.dei.ws.util;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.itf.errors.ErrorMessage;
@Named("errorsHandler")
@SessionScoped
public class ErrorsHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private StyleResponse styleResponse;



	public ErrorsHandler() {	
	}

	public void createNewClient(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String newClientSuccess = bundle.getString("NewClientSuccess");
			facesContext.addMessage("createClientForm", new FacesMessage(newClientSuccess));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			StringBuilder errorMsgname= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.CLIENT_NAME_EXISTS.toString())) {
					errorMsgname = errorMsgname.append("* ").append(bundle.getString("CLIENT_NAME_EXISTS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				} 
			}
			facesContext.addMessage("createClientForm:name", new FacesMessage(errorMsgname.toString()));
			facesContext.addMessage("createClientForm", new FacesMessage(errorForm.toString()));
		}	
	}

	public void createNewProject(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String newProjectSuccess = bundle.getString("NewProjectSuccess");
			facesContext.addMessage("createProjectForm", new FacesMessage(newProjectSuccess));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorMsgBeginDate= new StringBuilder();
			StringBuilder errorMsgEndDate= new StringBuilder();
			StringBuilder errorMsgBudget= new StringBuilder();
			StringBuilder errorMsgIdProject= new StringBuilder();
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE.toString())) {
					errorMsgBeginDate = errorMsgBeginDate.append("* ").append(bundle.getString("END_DATE_MUST_BE_AFTER_BEGIN_DATE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.DATE_MUST_NOT_BE_PAST.toString())) {
					errorMsgEndDate = errorMsgEndDate.append("* ").append(bundle.getString("DATE_MUST_NOT_BE_PAST")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_POSITIVE_VALUE.toString())) {
					errorMsgBudget = errorMsgBudget.append("* ").append(bundle.getString("MUST_BE_POSITIVE_VALUE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.UNIQUE_NAME_ALREADY_EXISTS.toString())) {
					errorMsgIdProject = errorMsgIdProject.append("* ").append(bundle.getString("PROJECT_ID_ALREADY_EXISTS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				} 
			}
			facesContext.addMessage("createProjectForm:beginDate", new FacesMessage(errorMsgBeginDate.toString()));
			facesContext.addMessage("createProjectForm:endDate", new FacesMessage(errorMsgEndDate.toString()));
			facesContext.addMessage("createProjectForm:budget", new FacesMessage(errorMsgBudget.toString()));
			facesContext.addMessage("createProjectForm:idProject", new FacesMessage(errorMsgIdProject.toString()));
			facesContext.addMessage("createProjectForm", new FacesMessage(errorForm.toString()));
		}	
	}

	public void createNewTask(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String newTaskSuccess = bundle.getString("NewTaskSuccess");
			facesContext.addMessage("createTaskForm", new FacesMessage(newTaskSuccess));
		} else {
			StringBuilder errorMsgBeginDate= new StringBuilder();
			StringBuilder errorMsgEndDate= new StringBuilder();
			StringBuilder errorMsgHourCost= new StringBuilder();
			StringBuilder errorMsgDurationhoursestimate= new StringBuilder();
			StringBuilder errorForm= new StringBuilder();	
			styleResponse.setCssClass("error");
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.TASK_CANNOT_START_BEFORE_PROJECT.toString())) {
					errorMsgBeginDate = errorMsgBeginDate.append("* ").append(bundle.getString("TASK_CANNOT_START_BEFORE_PROJECT")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE.toString())) {
					errorMsgBeginDate = errorMsgBeginDate.append("* ").append(bundle.getString("END_DATE_MUST_BE_AFTER_BEGIN_DATE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.TASK_CANNOT_END_AFTER_PROJECT.toString())) {
					errorMsgEndDate = errorMsgEndDate.append("* ").append(bundle.getString("TASK_CANNOT_END_AFTER_PROJECT")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.TASK_MUST_START_TOMORROW_OR_AFTER.toString())) {
					errorMsgBeginDate = errorMsgBeginDate.append("* ").append(bundle.getString("TASK_MUST_START_TOMORROW_OR_AFTER")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_POSITIVE_VALUE.toString())) {
					errorMsgHourCost = errorMsgHourCost.append("* ").append(bundle.getString("MUST_BE_POSITIVE_VALUE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.TASK_MUST_HAVE_POSIVE_DURATION.toString())) {
					errorMsgDurationhoursestimate = errorMsgDurationhoursestimate.append("* ").append(bundle.getString("TASK_MUST_HAVE_POSIVE_DURATION")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				}  else if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("MUST_BE_DIRECTOR_OR_PROJECT_MANAGER")).append(" * ");
				}   else if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				} 
			}
			facesContext.addMessage("createTaskForm:beginDate1", new FacesMessage(errorMsgBeginDate.toString()));
			facesContext.addMessage("createTaskForm:endDate1", new FacesMessage(errorMsgEndDate.toString()));
			facesContext.addMessage("createTaskForm:hourCost", new FacesMessage(errorMsgHourCost.toString()));
			facesContext.addMessage("createTaskForm:durationhoursestimate", new FacesMessage(errorMsgDurationhoursestimate.toString()));
			facesContext.addMessage("createTaskForm", new FacesMessage(errorForm.toString()));
		}	
	}

	public void createNewTaskProject(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String newTaskSuccess = bundle.getString("NewTaskSuccess");
			facesContext.addMessage("createTaskForm", new FacesMessage(newTaskSuccess));
		} else {
			StringBuilder errorMsgBeginDate= new StringBuilder();
			StringBuilder errorMsgEndDate= new StringBuilder();
			StringBuilder errorMsgHourCost= new StringBuilder();
			StringBuilder errorMsgDurationhoursestimate= new StringBuilder();
			StringBuilder errorForm= new StringBuilder();	
			styleResponse.setCssClass("error");
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.TASK_CANNOT_START_BEFORE_PROJECT.toString())) {
					errorMsgBeginDate = errorMsgBeginDate.append("* ").append(bundle.getString("TASK_CANNOT_START_BEFORE_PROJECT")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE.toString())) {
					errorMsgBeginDate = errorMsgBeginDate.append("* ").append(bundle.getString("END_DATE_MUST_BE_AFTER_BEGIN_DATE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.TASK_CANNOT_END_AFTER_PROJECT.toString())) {
					errorMsgEndDate = errorMsgEndDate.append("* ").append(bundle.getString("TASK_CANNOT_END_AFTER_PROJECT")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.TASK_MUST_START_TOMORROW_OR_AFTER.toString())) {
					errorMsgBeginDate = errorMsgBeginDate.append("* ").append(bundle.getString("TASK_MUST_START_TOMORROW_OR_AFTER")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_POSITIVE_VALUE.toString())) {
					errorMsgHourCost = errorMsgHourCost.append("* ").append(bundle.getString("MUST_BE_POSITIVE_VALUE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.TASK_MUST_HAVE_POSIVE_DURATION.toString())) {
					errorMsgDurationhoursestimate = errorMsgDurationhoursestimate.append("* ").append(bundle.getString("TASK_MUST_HAVE_POSIVE_DURATION")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				}  else if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("MUST_BE_DIRECTOR_OR_PROJECT_MANAGER")).append(" * ");
				}   else if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				} 
			}
			facesContext.addMessage("createTaskForm:beginDate", new FacesMessage(errorMsgBeginDate.toString()));
			facesContext.addMessage("createTaskForm:endDate", new FacesMessage(errorMsgEndDate.toString()));
			facesContext.addMessage("createTaskForm:hourCost", new FacesMessage(errorMsgHourCost.toString()));
			facesContext.addMessage("createTaskForm:durationhoursestimate", new FacesMessage(errorMsgDurationhoursestimate.toString()));
			facesContext.addMessage("createTaskForm", new FacesMessage(errorForm.toString()));
		}	
	}

	public void createUser(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String newUserSuccess = bundle.getString("NewUserRegisterSuccess");
			facesContext.addMessage("createUserForm", new FacesMessage(newUserSuccess));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			StringBuilder errorMsgEmail= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.UNIQUE_NAME_ALREADY_EXISTS.toString())) {
					errorMsgEmail = errorMsgEmail.append("* ").append(bundle.getString("EMAIL_ALREADY_EXISTS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				} 
			}
			facesContext.addMessage("createUserForm:newEmail", new FacesMessage(errorMsgEmail.toString()));
			facesContext.addMessage("createUserForm", new FacesMessage(errorForm.toString()));
		}	
	}

	public void createBusiness(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String newBusinessSuccess = bundle.getString("NewBusinessSuccess");
			facesContext.addMessage("createBusinessForm", new FacesMessage(newBusinessSuccess));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			StringBuilder errorMsgname= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.UNIQUE_NAME_ALREADY_EXISTS.toString())) {
					errorMsgname = errorMsgname.append("* ").append(bundle.getString("BUSINESS_NAME_EXISTS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				}
			}
			facesContext.addMessage("createBusinessForm:name", new FacesMessage(errorMsgname.toString()));
			facesContext.addMessage("createBusinessForm", new FacesMessage(errorForm.toString()));	
		}
	}

	public void updateClient(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String editClientSuccess = bundle.getString("EditClientSuccess");
			facesContext.addMessage("updateClientForm", new FacesMessage(editClientSuccess));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				} 
			}
			facesContext.addMessage("updateClientForm", new FacesMessage(errorForm.toString()));
		}
	}

	public void updateProject(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String editProjectSuccess = bundle.getString("EditProjectSuccess");
			facesContext.addMessage("updateProjectForm", new FacesMessage(editProjectSuccess));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorMsgBeginDate= new StringBuilder();
			StringBuilder errorMsgEndDate= new StringBuilder();
			StringBuilder errorMsgBudget= new StringBuilder();
			StringBuilder errorMsgGestor= new StringBuilder();
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE.toString())) {
					errorMsgBeginDate = errorMsgBeginDate.append("* ").append(bundle.getString("END_DATE_MUST_BE_AFTER_BEGIN_DATE")).append(" * ");		
				} else if (errors.get(i).toString().equals(ErrorMessage.DATE_MUST_NOT_BE_PAST.toString())) {
					errorMsgEndDate = errorMsgEndDate.append("* ").append(bundle.getString("DATE_MUST_NOT_BE_PAST")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.PROJECT_HAS_TASKS_OUTSIDE_DATES.toString())) {
					errorMsgEndDate = errorMsgEndDate.append("* ").append(bundle.getString("PROJECT_HAS_TASKS_OUTSIDE_DATES")).append(" * ");
					errorMsgBeginDate = errorMsgBeginDate.append("* ").append(bundle.getString("PROJECT_HAS_TASKS_OUTSIDE_DATES")).append(" * ");		
				} else if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("MUST_BE_DIRECTOR_OR_PROJECT_MANAGER")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_POSITIVE_VALUE.toString())) {
					errorMsgBudget = errorMsgBudget.append("* ").append(bundle.getString("MUST_BE_POSITIVE_VALUE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.CANNOT_CHANGE_MANAGER_ON_LAST_PROJECT_DAY.toString())) {
					errorMsgGestor = errorMsgGestor.append("* ").append(bundle.getString("CANNOT_CHANGE_MANAGER_ON_LAST_PROJECT_DAY")).append(" * ");
				}
			}
			facesContext.addMessage("updateProjectForm:beginDate", new FacesMessage(errorMsgBeginDate.toString()));
			facesContext.addMessage("updateProjectForm:endDate", new FacesMessage(errorMsgEndDate.toString()));
			facesContext.addMessage("updateProjectForm:budget", new FacesMessage(errorMsgBudget.toString()));
			facesContext.addMessage("updateProjectForm:gestor", new FacesMessage(errorMsgGestor.toString()));
			facesContext.addMessage("updateProjectForm", new FacesMessage(errorForm.toString()));
		}
	}

	public void createSkill(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String newSkillSuccess = bundle.getString("NewSkillSuccess");
			facesContext.addMessage("createSkillsForm", new FacesMessage(newSkillSuccess));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			StringBuilder errorMsgname= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.UNIQUE_NAME_ALREADY_EXISTS.toString())) {
					errorMsgname = errorMsgname.append("* ").append(bundle.getString("SKILL_NAME_EXISTS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				}
			}
			facesContext.addMessage("createSkillsForm:skill", new FacesMessage(errorMsgname.toString()));
			facesContext.addMessage("createSkillsForm", new FacesMessage(errorForm.toString()));
		}
	}

	public void updateUserProfile(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String editUserSuccess = bundle.getString("EditUserSuccess");
			facesContext.addMessage("updateUserForm", new FacesMessage(editUserSuccess));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			StringBuilder errorMsgPerfil= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.USER_MUST_HAVE_A_ROLE.toString())) {
					errorMsgPerfil = errorMsgPerfil.append("* ").append(bundle.getString("USER_MUST_HAVE_A_ROLE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.USER_VISITOR_MUST_HAVE_A_SINGLE_ROLE.toString())) {
					errorMsgPerfil = errorMsgPerfil.append("* ").append(bundle.getString("USER_VISITOR_MUST_HAVE_A_SINGLE_ROLE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				} 
			}
			facesContext.addMessage("updateUserForm:perfil", new FacesMessage(errorMsgPerfil.toString()));
			facesContext.addMessage("updateUserForm", new FacesMessage(errorForm.toString()));
		}
	}

	public void loginUser(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.USER_NOT_ACTIVE.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("USER_NOT_ACTIVE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.LOGIN_FAILED.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("LOGIN_FAILED")).append(" * ");
				}
			}
			facesContext.addMessage("loginForm", new FacesMessage(errorForm.toString()));
		}
	}

	public void updateSelfProfile(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String editUserSuccess = bundle.getString("EditUserSuccess");
			facesContext.addMessage("updateUserForm", new FacesMessage(editUserSuccess));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				} 
			}
			facesContext.addMessage("updateUserForm", new FacesMessage(errorForm.toString()));
		}
	}

	public void allocateWorkerToTask(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String allocWorkerSuccess = bundle.getString("AllocWorkerSuccess");
			facesContext.addMessage("myFormRemove:controlForm", new FacesMessage(allocWorkerSuccess));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("MUST_BE_DIRECTOR_OR_PROJECT_MANAGER")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.INVALID_PERCENTAGE_ALLOCATION.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("INVALID_PERCENTAGE_ALLOCATION")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.TASK_OUTSIDE_DATES.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("TASK_OUTSIDE_DATES")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.DATE_MUST_BE_FUTURE.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("DATE_MUST_BE_FUTURE")).append(" * ");
				}  else if (errors.get(i).toString().equals(ErrorMessage.ALLOCATIONS_ALREADY_IN_INTERVAL.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("ALLOCATIONS_ALREADY_IN_INTERVAL")).append(" * ");
				}  else if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("END_DATE_MUST_BE_AFTER_BEGIN_DATE")).append(" * ");
				} 


			}
			facesContext.addMessage("myFormRemove:controlForm", new FacesMessage(errorForm.toString()));
		}
	}

	public void removeWorkerFromTask(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String allocWorkerSuccess = bundle.getString("RemoveAllocWorkerSuccess");
			facesContext.addMessage("userListAllocForm", new FacesMessage(allocWorkerSuccess));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.CAN_ONLY_REMOVE_ALLOCATIONS_ENDING_IN_FUTURE.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("CAN_ONLY_REMOVE_ALLOCATIONS_ENDING_IN_FUTURE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.USER_HAS_WORKING_HOURS_IN_ALLOCATION.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("USER_HAS_WORKING_HOURS_IN_ALLOCATION")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("MUST_BE_DIRECTOR_OR_PROJECT_MANAGER")).append(" * ");
				}  else if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				}  
			}
			facesContext.addMessage("userListAllocForm", new FacesMessage(errorForm.toString()));
		}
	}
	

	public void beginDateAfterEndDateFindAvailableWorkersToTaskErrorMessage() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		styleResponse.setCssClass("error");
		String errorForm = (bundle.getString("END_DATE_MUST_BE_AFTER_BEGIN_DATE"));
		facesContext.addMessage("searchUsersAllocForm", new FacesMessage(errorForm));
	}

	public void removeWorkerFromTaskInProject(boolean success, List<ErrorMessage> errors) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String allocWorkerSuccess = bundle.getString("RemoveAllocWorkerSuccess");
			facesContext.addMessage("listUsersProjectForm", new FacesMessage(allocWorkerSuccess));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.CAN_ONLY_REMOVE_ALLOCATIONS_ENDING_IN_FUTURE.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("CAN_ONLY_REMOVE_ALLOCATIONS_ENDING_IN_FUTURE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.USER_HAS_WORKING_HOURS_IN_ALLOCATION.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("USER_HAS_WORKING_HOURS_IN_ALLOCATION")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("MUST_BE_DIRECTOR_OR_PROJECT_MANAGER")).append(" * ");
				}  else if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				}  
			}
			facesContext.addMessage("listUsersProjectForm", new FacesMessage(errorForm.toString()));
		}

	}

	public void findAvailableWorkersToTask(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.TASK_OUTSIDE_DATES.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("TASK_OUTSIDE_DATES")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.ALLOCATIONS_ALREADY_IN_INTERVAL.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("ALLOCATIONS_ALREADY_IN_INTERVAL")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				}  
			}
			styleResponse.setCssClass("error");
			facesContext.addMessage("alocForm", new FacesMessage(errorForm.toString()));
		}
	}

	public void updateTask(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String editTaskSuccess = bundle.getString("EditTaskSuccess");
			facesContext.addMessage("updateTaskForm", new FacesMessage(editTaskSuccess));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorMsgBeginDate= new StringBuilder();
			StringBuilder errorMsgEndDate= new StringBuilder();
			StringBuilder errorMsgHourCost= new StringBuilder();
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.TASK_CANNOT_START_BEFORE_PROJECT.toString())) {
					errorMsgBeginDate = errorMsgBeginDate.append("* ").append(bundle.getString("TASK_CANNOT_START_BEFORE_PROJECT")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.TASK_CANNOT_END_AFTER_PROJECT.toString())) {
					errorMsgEndDate = errorMsgEndDate.append("* ").append(bundle.getString("TASK_CANNOT_END_AFTER_PROJECT")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.TASK_MUST_END_AFTER_ALL_PRECEDENTS_START.toString())) {
					errorMsgEndDate = errorMsgEndDate.append("* ").append(bundle.getString("TASK_MUST_END_AFTER_ALL_PRECEDENTS_START")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.DATE_MUST_NOT_BE_PAST.toString())) {
					errorMsgEndDate = errorMsgEndDate.append("* ").append(bundle.getString("DATE_MUST_NOT_BE_PAST")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_POSITIVE_VALUE.toString())) {
					errorMsgHourCost = errorMsgHourCost.append("* ").append(bundle.getString("MUST_BE_POSITIVE_VALUE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE.toString())) {
					errorMsgBeginDate = errorMsgBeginDate.append("* ").append(bundle.getString("END_DATE_MUST_BE_AFTER_BEGIN_DATE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.TASK_MUST_BEGIN_BEFORE_ALL_NEXT_END.toString())) {
					errorMsgBeginDate = errorMsgBeginDate.append("* ").append(bundle.getString("TASK_MUST_BEGIN_BEFORE_ALL_NEXT_END")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				}  else if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("MUST_BE_DIRECTOR_OR_PROJECT_MANAGER")).append(" * ");
				}   else if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.TASK_ALREADY_IN_PROGRESS.toString())) {
					errorMsgBeginDate = errorMsgBeginDate.append("* ").append(bundle.getString("TASK_ALREADY_IN_PROGRESS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.TASK_HAS_ALLOCATIONS_OUTSIDE_DATE_INTERVAL.toString())) {
					errorMsgBeginDate = errorMsgBeginDate.append("* ").append(bundle.getString("TASK_HAS_ALLOCATIONS_OUTSIDE_DATE_INTERVAL")).append(" * ");
				} 
			}
			facesContext.addMessage("updateTaskForm:beginDate", new FacesMessage(errorMsgBeginDate.toString()));
			facesContext.addMessage("updateTaskForm:endDate", new FacesMessage(errorMsgEndDate.toString()));
			facesContext.addMessage("updateTaskForm:hourCost", new FacesMessage(errorMsgHourCost.toString()));
			facesContext.addMessage("updateTaskForm", new FacesMessage(errorForm.toString()));
		}
	}

	public void assignTaskPrecendence(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String assignTaskPrecendence = bundle.getString("AssignTaskPrecendence");
			facesContext.addMessage("possibleprecedenciaForm", new FacesMessage(assignTaskPrecendence));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("MUST_BE_DIRECTOR_OR_PROJECT_MANAGER")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.INVALID_PRECEDENCE.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("INVALID_PRECEDENCE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				}  
			}
			styleResponse.setCssClass("error");
			facesContext.addMessage("possibleprecedenciaForm", new FacesMessage(errorForm.toString()));
		}
	}

	public void unAssignTaskPrecendence(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String unassignTaskPrecendence = bundle.getString("UnAssignTaskPrecendence");
			facesContext.addMessage("possibleprecedenciaForm", new FacesMessage(unassignTaskPrecendence));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("MUST_BE_DIRECTOR_OR_PROJECT_MANAGER")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.INVALID_PRECEDENCE.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("INVALID_PRECEDENCE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				}  
			}
			styleResponse.setCssClass("error");
			facesContext.addMessage("possibleprecedenciaForm", new FacesMessage(errorForm.toString()));
		}
	}


	public void registerWorkingHoursInTask(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String unassignTaskPrecendence = bundle.getString("RegisterWorkingHoursInTask");
			facesContext.addMessage("registHoursForm", new FacesMessage(unassignTaskPrecendence));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				}  
			}
			styleResponse.setCssClass("error");
			facesContext.addMessage("registHoursForm", new FacesMessage(errorForm.toString()));
		}
	}

	public void updateAllocationDates(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String updateAllocationDates = bundle.getString("UpdateAllocationDates");
			facesContext.addMessage("userListAllocForm", new FacesMessage(updateAllocationDates));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("MUST_BE_DIRECTOR_OR_PROJECT_MANAGER")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.ALLOCATION_CANNOT_BE_OUTSIDE_TASK_DATES.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("ALLOCATION_CANNOT_BE_OUTSIDE_TASK_DATES")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("END_DATE_MUST_BE_AFTER_BEGIN_DATE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.CAN_NOT_CHANGE_ENDING_ALLOCATIONS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("CAN_NOT_CHANGE_ENDING_ALLOCATIONS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.USER_HAS_NOT_REQUIRED_PERCENTAGE_ALLOCATION_FOR_PERIOD.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("USER_HAS_NOT_REQUIRED_PERCENTAGE_ALLOCATION_FOR_PERIOD")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.ALLOCATIONS_ALREADY_IN_INTERVAL.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("ALLOCATIONS_ALREADY_IN_INTERVAL")).append(" * ");
				}
			}
			facesContext.addMessage("userListAllocForm", new FacesMessage(errorForm.toString()));
		}
	}

	public void updateProjectAllocationDates(boolean success, List<ErrorMessage> errors) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String updateAllocationDates = bundle.getString("UpdateAllocationDates");
			facesContext.addMessage("listUsersProjectForm", new FacesMessage(updateAllocationDates));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.MUST_BE_DIRECTOR_OR_PROJECT_MANAGER.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("MUST_BE_DIRECTOR_OR_PROJECT_MANAGER")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.ALLOCATION_CANNOT_BE_OUTSIDE_TASK_DATES.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("ALLOCATION_CANNOT_BE_OUTSIDE_TASK_DATES")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("END_DATE_MUST_BE_AFTER_BEGIN_DATE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.CAN_NOT_CHANGE_ENDING_ALLOCATIONS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("CAN_NOT_CHANGE_ENDING_ALLOCATIONS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.USER_HAS_NOT_REQUIRED_PERCENTAGE_ALLOCATION_FOR_PERIOD.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("USER_HAS_NOT_REQUIRED_PERCENTAGE_ALLOCATION_FOR_PERIOD")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.ALLOCATIONS_ALREADY_IN_INTERVAL.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("ALLOCATIONS_ALREADY_IN_INTERVAL")).append(" * ");
				}
			}
			facesContext.addMessage("listUsersProjectForm", new FacesMessage(errorForm.toString()));
		}

	}




	public void findUserPerformanceChart(boolean b, List<ErrorMessage> errors) {
		// TODO Auto-generated method stub

	}

	public void findAllUsersPerformanceCharts(boolean b, List<ErrorMessage> errors) {
		// TODO Auto-generated method stub

	}

	//possibleprecedenciaForm
	public StyleResponse getStyleResponse() {
		return styleResponse;
	}

	public void setStyleResponse(StyleResponse styleResponse) {
		this.styleResponse = styleResponse;
	}

	public void findAllMyAllocations(boolean success, List<ErrorMessage> errors) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");

		if (!success) {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				}
			}
			facesContext.addMessage("allocationsFastSearchForm", new FacesMessage(errorForm.toString()));
		}

	}

	public void findMyAllocationsBetweenDates(boolean success, List<ErrorMessage> errors) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String searchAllocationDates = bundle.getString("SearchAllocationDates");
			facesContext.addMessage("allocationsAdvancedSearchForm", new FacesMessage(searchAllocationDates));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("END_DATE_MUST_BE_AFTER_BEGIN_DATE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				}
			}
			facesContext.addMessage("allocationsAdvancedSearchForm", new FacesMessage(errorForm.toString()));
		}

	}

	public void requestRoleAtribution(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String requestRoles = bundle.getString("RequestRoles");
			facesContext.addMessage("requestRoles", new FacesMessage(requestRoles));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				} 
			}
			facesContext.addMessage("requestRoles", new FacesMessage(errorForm.toString()));
		}

	}

	public void createNewHoliday(boolean success, List<ErrorMessage> errors) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String createHoliday = bundle.getString("CreateHoliday");
			facesContext.addMessage("createHolidaysForm", new FacesMessage(createHoliday));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.HOLIDAY_DATE_MUST_BE_FUTURE.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("HOLIDAY_DATE_MUST_BE_FUTURE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.DATE_IS_ALREADY_AN_HOLIDAY.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("DATE_IS_ALREADY_AN_HOLIDAY")).append(" * ");
				}
			}
			facesContext.addMessage("createHolidaysForm", new FacesMessage(errorForm.toString()));
		}

	}

	public void updateHoliday(boolean success, List<ErrorMessage> errors) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String updateHoliday = bundle.getString("UpdateHoliday");
			facesContext.addMessage("updateHolidaysForm", new FacesMessage(updateHoliday));
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.HOLIDAY_DATE_MUST_BE_FUTURE.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("HOLIDAY_DATE_MUST_BE_FUTURE")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.NULL_OR_EMPTY_FIELDS.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("NULL_OR_EMPTY_FIELDS")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.DATE_IS_ALREADY_AN_HOLIDAY.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("DATE_IS_ALREADY_AN_HOLIDAY")).append(" * ");
				}
			}
			facesContext.addMessage("updateHolidaysForm", new FacesMessage(errorForm.toString()));
		}

	}

	public void forgotPassword(boolean success, Object object) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String forgotPassword = bundle.getString("ForgotPasswFormSucess");
			facesContext.addMessage("forgotPasswForm", new FacesMessage(forgotPassword));
		} else {
			styleResponse.setCssClass("error");
			String forgotPassword = bundle.getString("ForgotPasswFormError");
			facesContext.addMessage("forgotPasswForm", new FacesMessage(forgotPassword));
		}

	}

	public void submitNewPassword(boolean success, Object object) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
			String recoverPassword = bundle.getString("RecoverPasswFormSucess");
			facesContext.addMessage("recoverPasswForm", new FacesMessage(recoverPassword));
		} else {
			styleResponse.setCssClass("error");
			String recoverPassword = bundle.getString("RecoverPasswFormError");
			facesContext.addMessage("recoverPasswForm", new FacesMessage(recoverPassword));
		}

	}

	public void loginUserWithGoogle(boolean success, List<ErrorMessage> errors) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		if (success) {
			styleResponse.setCssClass("success");
		} else {
			styleResponse.setCssClass("error");
			StringBuilder errorForm= new StringBuilder();
			for (int i=0; i<errors.size(); i++)  {
				if (errors.get(i).toString().equals(ErrorMessage.FATAL_ERROR.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("FATAL_ERROR")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.LOGIN_FAILED.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("LOGIN_FAILED")).append(" * ");
				} else if (errors.get(i).toString().equals(ErrorMessage.USER_NOT_ACTIVE.toString())) {
					errorForm = errorForm.append("* ").append(bundle.getString("USER_NOT_ACTIVE")).append(" * ");
				}
			}
			facesContext.addMessage("googleForm", new FacesMessage(errorForm.toString()));
		}

	}

	public void findMyUserPerformanceChart(boolean b, List<ErrorMessage> errors) {
		// TODO Auto-generated method stub
		
	}















}
