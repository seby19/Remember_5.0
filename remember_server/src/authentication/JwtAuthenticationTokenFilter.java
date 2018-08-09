//package authentication;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//
//import remember_dao.UserLoginDao;
//import remember_dto.JWTAuthenticationToken;
//
//
//public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {
//
//	public JwtAuthenticationTokenFilter() {
//		
//		super("/**");
//		
//		
//	}
//	
//
//	static Logger log = Logger.getLogger(JwtAuthenticationTokenFilter.class.getName());
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//			throws AuthenticationException, IOException, ServletException {
//		
//		String header = request.getHeader("Authorization");
//		log.error("In filter");
//		if(header == null || !header.startsWith("Token"))
//		{
//			throw new RuntimeException("Token is missing");
//		}
//		
//		header = header.substring(6);
//		JWTAuthenticationToken token = new JWTAuthenticationToken(header);
//		
//		
//		return getAuthenticationManager().authenticate(token);
//	}
//	
//	
//	@Override
//	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//			Authentication authResult) throws IOException, ServletException {
//		super.successfulAuthentication(request, response, chain, authResult);
//		chain.doFilter(request, response);
//	}
//
//}
