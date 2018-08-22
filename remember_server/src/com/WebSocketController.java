package com;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


import authentication.JWTValidator;
import authentication.JwtGenerator;
import remember_dto.CreateGroupsDto;
import remember_dto.JWTAuthenticationToken;
import remember_dto.JWTUser;
import remember_dto.UserFriendsDisp;
import remember_service.ConnectPeopleService;
import remember_service.EditConnectionsService;
import remember_service.GroupsService;

@Controller
public class WebSocketController {
	
	@Autowired
	JwtGenerator jwtGenerator;
	@Autowired
	ConnectPeopleService connectPeopleService;
	@Autowired
	UserFriendsDisp userData ;
	@Autowired
	EditConnectionsService editConnectionsService;
	@Autowired
	GroupsService groupsService;
	@Autowired
	CreateGroupsDto groups;
	
	
	@MessageMapping(value = "/getConnections/{friendUsername}")
	@SendTo("/broker/{friendUsername}/queue/showFriends")
	//@SendToUser("/queue/showFriends")
	public UserFriendsDisp OnRecieveShowFriends( String connectId , @DestinationVariable String friendUsername , Principal principal) throws NumberFormatException, Exception
	{
		System.out.println("username :" +friendUsername);
		System.out.println("Principal" + principal.getName());
		System.out.println("In WebSocket");
		
		userData = connectPeopleService.connectPeople(Long.parseLong(connectId), principal.getName());
		//System.out.println("status : " + status);
		if(userData == null)
		{
			throw new RuntimeException("Connection Did'nt take places");
		}
		return (userData);
	}
	
	@MessageMapping(value = "/editConnections/{friendUsername}/{property}")
	@SendTo("/broker/{friendUsername}/queue/showConnections")
	//@SendToUser("/queue/showFriends")
	public UserFriendsDisp editConnections( String connectId , @DestinationVariable String friendUsername ,@DestinationVariable int property ,  Principal principal) throws NumberFormatException, Exception
	{
		System.out.println("username :" +friendUsername);
		System.out.println("Principal" + principal.getName());
		System.out.println("In WebSocket edit connections");
		
		userData = editConnectionsService.editConnection(Long.parseLong(connectId), principal.getName() , property);
		//System.out.println("status : " + status);
		/*if(userData == null)
		{
			throw new RuntimeException("Connection Did'nt take places");
		}*/
		return (userData);
	}
	
	@MessageMapping(value = "/addPersonToGroup/{friendUsername}")
	@SendTo("/broker/{friendUsername}/queue/newGroup")
	public CreateGroupsDto addPersonToGroup( String groupId , @DestinationVariable String friendUsername,  Principal principal) throws NumberFormatException, Exception
	{

		groups = groupsService.addPersonToGroup(friendUsername , Long.parseLong(groupId));
		return (groups);
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
 