package webSocket;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.RememberRequestMapper;

import authentication.JWTValidator;
import remember_dto.JWTAuthenticationToken;
import remember_dto.JWTUser;


@Component
public class UserHandShakeHandler extends DefaultHandshakeHandler {
	
	static Logger log = Logger.getLogger(UserHandShakeHandler.class.getName());
	
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,  
			Map<String, Object> attributes) {
		
		HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
		String token = servletRequest.getParameter("Authorization");
		
		
		
		
		System.out.println("In handshaker token " + token );
		//String token = (String)attributes.get("Authorization");
		
		JWTUser jwtUser = this.getUserInfoFromToken(token);
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(jwtUser.getRole());
		
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUser.getUserName(), null,grantedAuthorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(servletRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
		return new UsernamePasswordAuthenticationToken(authentication.getPrincipal() , null);
	}
	
	public JWTUser getUserInfoFromToken(String Token)
	{
		JWTUser jwtUser = null;
		Token = Token.substring(6);
		JWTAuthenticationToken token = new JWTAuthenticationToken(Token);
		String jwtToken = token.getToken();
		
		try {
			
			jwtUser = JWTValidator.validate(jwtToken);
			
		} catch (Exception e1) {
			
			throw new RuntimeException("JWT Token is not valid");
			
		}
		return jwtUser;
	}

}
