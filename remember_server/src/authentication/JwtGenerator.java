package authentication;

import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import remember_dto.JWTUser;

@Component
public class JwtGenerator {
	
	private String secret = "rememberserversecret";
	
	public String generate(JWTUser jwtUser)
	{
		Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(15).toInstant());
		Claims claims = Jwts.claims()
						.setSubject(jwtUser.getUserName());
		claims.put("userId", String.valueOf(jwtUser.getId()));
        claims.put("role", jwtUser.getRole());
        
        return Jwts.builder()
        		.setClaims(claims)
        		.setExpiration(expirationDate)
        		.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
}
