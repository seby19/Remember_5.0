package authentication;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import remember_dto.JWTUser;

@Component
public class JWTValidator {
	
	private static String secret = "rememberserversecret";
	
	public static JWTUser validate(String token) throws Exception {
		JWTUser jwtUser = null;
		/*try {*/
			Claims body = Jwts.parser()
							.setSigningKey(secret)
							.parseClaimsJws(token)
							.getBody();
			jwtUser = new JWTUser();
			
			jwtUser.setUserName(body.getSubject());
			jwtUser.setId(Long.parseLong((String)body.get("userId")));
			jwtUser.setRole((String) body.get("role"));
		/*}
		catch(Exception e)
		{
			e.printStackTrace();
		}*/
		
		return jwtUser;
	}

}
