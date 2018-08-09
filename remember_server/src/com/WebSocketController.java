package com;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


import authentication.JWTValidator;
import authentication.JwtGenerator;
import remember_dto.JWTAuthenticationToken;
import remember_dto.JWTUser;
import remember_service.ConnectPeopleService;

@Controller
public class WebSocketController {
	
	@Autowired
	JwtGenerator jwtGenerator;
	@Autowired
	ConnectPeopleService connectPeopleService;
	
	@MessageMapping(value = "/getConnections/{friendUsername}")
	@SendTo("/broker/{friendUsername}/queue/showFriends")
	//@SendToUser("/queue/showFriends")
	public String OnRecieveShowFriends( String connectId , @DestinationVariable String friendUsername , Principal principal) throws NumberFormatException, Exception
	{
		System.out.println("username :" +friendUsername);
		System.out.println("Principal" + principal.getName());
		System.out.println("In WebSocket");
		
		boolean status = connectPeopleService.connectPeople(Long.parseLong(connectId), principal.getName());
		System.out.println("status : " + status);
		if(!status)
		{
			throw new RuntimeException("Connection Did'nt take places");
		}
		return (principal.getName());
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
 