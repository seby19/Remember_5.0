package authentication;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import remember_dto.JWTUser;
import remember_dto.JWTAuthenticationToken;
import remember_dto.JwtUserDetails;



@Component

public class JWTAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	
	
	//private JWTValidator validator ;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0, UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {

	}

	@Override
	protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken token)
			throws AuthenticationException {
		JWTAuthenticationToken jwtAuthToken = (JWTAuthenticationToken) token;
		
		String jwtToken = jwtAuthToken.getToken();
		
		JWTUser jwtUser = null;
		try {
			jwtUser = JWTValidator.validate(jwtToken);
		} catch (Exception e) {
			
			throw new RuntimeException("JWT Token is not valid");
		}
        
		
		if (jwtUser == null) {
            throw new RuntimeException("JWT Token is incorrect");
        }		
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(jwtUser.getRole());
		
		return new JwtUserDetails(jwtUser.getUserName(), jwtUser.getId(),
				jwtToken,
                grantedAuthorities);
		
	}
	
	
	 @Override
    public boolean supports(Class<?> aClass) {
        return (JWTAuthenticationToken.class.isAssignableFrom(aClass));
    }
	
	

}
