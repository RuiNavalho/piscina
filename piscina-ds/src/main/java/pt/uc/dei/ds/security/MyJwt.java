package pt.uc.dei.ds.security;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import pt.uc.dei.itf.dtos.RoleDto;

public class MyJwt {

	public static Key key=MacProvider.generateKey(SignatureAlgorithm.HS256);
	
	//TODO que dados deveremos guardar no TOKEN
	public static String createJWT(String email, List<RoleDto> roles) {
		long ttlMillis=28800000; // expires in 8 hours
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		JwtBuilder builder = Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())//REMOVER????
				.claim("roles", getRolesAsStrings(roles))
				.signWith(signatureAlgorithm, key);
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();
	}
	
	//Token para recuperacao de password
	public static String createJwtPasswordRecover(String email) {
		long ttlMillis=1800000; // 30min deadline to recover password
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		JwtBuilder builder = Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())//REMOVER????
				.signWith(signatureAlgorithm, key);
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();
	}
	
	private static List<String> getRolesAsStrings(List<RoleDto> roles) {
		List<String> rolesString = new ArrayList<String>();
		for (int i=0; i<roles.size(); i++) {
			rolesString.add(roles.get(i).getRole());
		}
		return rolesString;
	}

	public static Claims parseJWT(String jwt) {
		Claims claims = Jwts.parser()         
				.setSigningKey(key)
				.parseClaimsJws(jwt).getBody();	
		return claims;
	}

}