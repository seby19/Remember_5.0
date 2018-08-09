package authentication;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Repository;
import org.springframework.web.filter.OncePerRequestFilter;

import remember_dto.JWTAuthenticationToken;
import remember_dto.JWTUser;

public class JwtAuthenticationTokenFilter1 extends OncePerRequestFilter {
	
	static Logger log = Logger.getLogger(JwtAuthenticationTokenFilter1.class.getName());
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = null;
		JWTUser jwtUser = null;
		boolean flag = false;
		
		
		if(request.getHeader("Origin").equals("http://localhost:4200")
			&&
			(request.getParameter("Authorization") != null))
		{
			header = request.getParameter("Authorization");
			request.setAttribute("Authorization", request.getParameter("Authorization"));
			response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD , OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers , authorization");
            response.setHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials , Access-Control-Allow-Headers");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            //response.addIntHeader("Access-Control-Max-Age", 10);
            response.flushBuffer();
            flag = true;
		}
		
		
		else if (request.getHeader("Access-Control-Request-Headers") != null && "OPTIONS".equals(request.getMethod())) {
        	response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD , OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers , authorization");
            response.setHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials , Access-Control-Allow-Headers");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.addIntHeader("Access-Control-Max-Age", 10);
            response.flushBuffer();
            
        }
        
        else
        {
		
			header = request.getHeader("Authorization");
			log.error("In filter");
			if(header == null || !header.startsWith("Token"))
			{
				throw new RuntimeException("Token is missing");
			}
			flag = true;
			
        }
		
		if(flag)
		{	
			try
			{
				header = header.substring(6);
				JWTAuthenticationToken token = new JWTAuthenticationToken(header);
				
				System.out.println( "header " + header);
				String jwtToken = token.getToken();
				
				jwtUser = JWTValidator.validate(jwtToken);
			}
			catch(Exception e)
			{
				throw new RuntimeException("JWT Token is not valid");
			}
	        
			
			if (jwtUser == null) {
				throw new RuntimeException("JWT Token is incorrect");
	        }
			else
			{
				List<GrantedAuthority> grantedAuthorities = AuthorityUtils
		                .commaSeparatedStringToAuthorityList(jwtUser.getRole());
				
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUser, null,grantedAuthorities);
		        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		        SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
        
        filterChain.doFilter(request, response);
	}

}
