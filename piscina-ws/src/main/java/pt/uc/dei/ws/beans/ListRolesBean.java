package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pt.uc.dei.itf.dtos.RoleDto;
import pt.uc.dei.ws.bridges.RoleBridge;

@Named("listRolesBean")
@SessionScoped
public class ListRolesBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<RoleDto> rolesList;
	private RoleDto selectedRole;
	
	@Inject
	private RoleBridge roleBridge;
	
	public ListRolesBean() {	
	}
	
	//TODO
	public void updateRole() {
		
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public List<RoleDto> getRolesList() {
//		if (rolesList!=null) {
//			return rolesList;
//		} else {
			return roleBridge.getAllRoles();
	//	}	
	}
	
	public RoleDto getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(RoleDto selectedRole) {
		this.selectedRole = selectedRole;
	}

	public void setRolesList(List<RoleDto> rolesList) {
		this.rolesList = rolesList;
	}

}
