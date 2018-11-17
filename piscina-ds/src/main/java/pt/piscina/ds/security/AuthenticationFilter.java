package pt.piscina.ds.security;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;
import io.jsonwebtoken.Claims;
import pt.piscina.ds.annotations.AdminDirectorOnly;
import pt.piscina.ds.annotations.AdminOnly;
import pt.piscina.ds.annotations.DirectorOnly;
import pt.piscina.ds.annotations.DirectorOrUser;
import pt.piscina.ds.annotations.NoTokenNeeded;

@Provider
public class AuthenticationFilter implements javax.ws.rs.container.ContainerRequestFilter
{

	@Context
	private ResourceInfo resourceInfo;

	private static final Response ACCESS_DENIED = Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
	private static final Response TOKEN_NEEDED = Response.status(Response.Status.FORBIDDEN).build();
	
	private final static Logger logger = Logger.getLogger(AuthenticationFilter.class);

	@Override
	public void filter(ContainerRequestContext requestContext)
	{	
		//method beeing accessed
		Method method = resourceInfo.getResourceMethod();
		
		//NoTokenNeeded
		
		if (method.isAnnotationPresent(NoTokenNeeded.class)) {
			return;
		}
		//
		if ( !( method.getName().equals("loginUser") || method.getName().equals("loginWithGoogle") || method.getName().equals("createNewUser") || method.getName().equals("forgotPassword")) ) {
			final MultivaluedMap<String, String> headers = requestContext.getHeaders();
			try {
				final List<String> authorization = headers.get("token");
				String myToken = authorization.get(0);
				//System.out.println("TOKEN "+myToken);
				List<String> roles=null;

				Claims claims = MyJwt.parseJWT(myToken);
				Date expiration = claims.getExpiration();
				//System.out.println("Validade do token "+expiration.getHours()+"h"+""+expiration.getMinutes()+"m");
				long nowMillis = System.currentTimeMillis();
				Date now = new Date(nowMillis);
				//System.out.println("NOW time "+now.getHours()+"h"+""+now.getMinutes()+"m");
				if (expiration.before(now)) {
					String subject = claims.getSubject();
					logger.error("Expired Token for user "+subject);
					requestContext.abortWith(ACCESS_DENIED);
				}

				boolean allowed=true;
				if (!method.getName().equals("changePassword")) {
					roles = (List<String>) claims.get("roles");
				}

				//TODO verificar todas as anotacoes
				if (method.isAnnotationPresent(AdminOnly.class)) {
					Annotation annotation = method.getAnnotation(AdminOnly.class);
					AdminOnly adminOnly = (AdminOnly) annotation;
					String[] allowedTo = adminOnly.allowedTo();
					allowed=allowUser(roles, allowedTo);
				} else if (method.isAnnotationPresent(DirectorOnly.class)) {
					Annotation annotation = method.getAnnotation(DirectorOnly.class);
					DirectorOnly directorOnly = (DirectorOnly) annotation;
					String[] allowedTo = directorOnly.allowedTo();
					allowed=allowUser(roles, allowedTo);
				} else if (method.isAnnotationPresent(AdminDirectorOnly.class)) {
					Annotation annotation = method.getAnnotation(AdminDirectorOnly.class);
					AdminDirectorOnly adminDirectorOnly = (AdminDirectorOnly) annotation;
					String[] allowedTo = adminDirectorOnly.allowedTo();
					allowed=allowUser(roles, allowedTo);
				} else if (method.isAnnotationPresent(DirectorOrUser.class)) {
					Annotation annotation = method.getAnnotation(DirectorOrUser.class);
					DirectorOrUser directorOrUser = (DirectorOrUser) annotation;
					String[] allowedTo = directorOrUser.allowedTo();
					allowed=allowUser(roles, allowedTo);
				}
				
				if (!allowed) {
					String subject = claims.getSubject();
					logger.error("SECURITY ALERT "+subject+" is NOT ALLOWED TO METHOD "+method.getName());
					requestContext.abortWith(ACCESS_DENIED);
				}
			} catch (Exception e) {
				logger.error("SECURITY ALERT - User without TOKEN is trying to access "+method.getName());
				requestContext.abortWith(TOKEN_NEEDED);
			}
		} else {
		}	
	}

	private boolean allowUser(List<String> roles, String[] allowedTo) {
		boolean allowed=false;
		for (int i=0; i<allowedTo.length; i++) {
			for (int j=0; j<roles.size(); j++) {
				if (allowedTo[i].equals(roles.get(j))) {
					return true;
				}
			}
		}
		return allowed;
	}
}