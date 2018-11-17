package pt.piscina.ds.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.log4j.Logger;

import pt.piscina.ds.daos.SkillDao;
import pt.piscina.ds.entities.Skill;
import pt.piscina.ds.security.MyJwt;
import pt.piscina.ds.util.InternalError;
import pt.piscina.ds.util.Trimmer;
import pt.uc.dei.itf.dtos.SkillDto;
import pt.uc.dei.itf.errors.ErrorMessage;

@Stateless
public class SkillService implements Serializable{

	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(SkillService.class);

	@Inject
	private SkillDao skillDao;

	public Response getAllSkills() {
		try {
			List<Skill> allSkills = skillDao.findAll();
			List<SkillDto> list = getSkillsListAsDto(allSkills);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}
	
	public Response createNewSkill(SkillDto skillDto, String token) {
		ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
		List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
		try {	
			StringBuilder sb = new StringBuilder();
			String email = MyJwt.parseJWT(token).getSubject();
			String skill = Trimmer.clean(skillDto.getSkill());		
			sb.append("User with email ").append(email).append(" CREATING NEW Skill with name: ").append(skill);
			boolean skillExists = skillDao.checkSkillNameExistence(skill);
			if (!skillExists) {		
				if (skill.equals("")) {		
					errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
					return statusNOK.entity(errorMessages).build();	
				} else {
					Skill newSkill= new Skill();
					newSkill.setSkill(skill);
					skillDao.persist(newSkill);
					logger.info("SUCESS - "+sb.toString());
					return Response.ok(true).build();
				}
			} else {
				logger.info("FAILED - "+sb.toString());
				errorMessages.add(ErrorMessage.UNIQUE_NAME_ALREADY_EXISTS);
				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// Private Auxiliary Methods ///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////


	private List<SkillDto> getSkillsListAsDto(List<Skill> allSkills) {
		List<SkillDto> list = new ArrayList<SkillDto>();
		for (int i=0; i<allSkills.size(); i++) {
			list.add(new SkillDto(allSkills.get(i).getSkill()));
		}
		return list;
	}



}
