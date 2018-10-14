package pt.uc.dei.ds.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import pt.uc.dei.ds.daos.RoleDao;
import pt.uc.dei.ds.entities.Role;
import pt.uc.dei.ds.util.InternalError;
import pt.uc.dei.itf.dtos.RoleDto;

@Stateless
public class RoleService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(RoleService.class);

	@Inject
	private RoleDao roleDao;

	public RoleService() {
	}

	public Response getAllRoles() {
		try {
			List<Role> allRoles = roleDao.findAll();			
			List<RoleDto> list = getRolesListAsDto(allRoles);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// Private Auxiliary Methods ///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////


	private List<RoleDto> getRolesListAsDto(List<Role> allRoles) {
		List<RoleDto> list = new ArrayList<RoleDto>();
		for (int i=0; i<allRoles.size(); i++) {
			list.add(new RoleDto(allRoles.get(i).getRole()));
		}		
		return list;
	}
}
