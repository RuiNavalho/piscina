package pt.piscina.ds.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

import pt.piscina.ds.daos.RoleDao;
import pt.piscina.ds.entities.Role;
import pt.piscina.ds.util.InternalError;
import pt.piscina.itf.dtos.RoleDto;

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
