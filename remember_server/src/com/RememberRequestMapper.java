package com;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import authentication.JWTValidator;
import authentication.JwtGenerator;
import remember_dto.GroupsDto;
import remember_dto.JWTAuthenticationToken;
import remember_dto.JWTUser;
import remember_dto.UserFriendsDisp;
import remember_dto.UserLogin;
import remember_dto.UserSignUp;
import remember_service.GetConnectionRequestsService;
import remember_service.GetPeopleService;
import remember_service.GroupsService;
import remember_service.UserGetFriendsService;
import remember_service.UserLoginService;
import remember_service.UserSignUPService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@CrossOrigin(origins = "http://localhost:4200" , allowedHeaders = "*")
@RestController
public class RememberRequestMapper {

	
	@Autowired
	UserSignUPService userSignUp;
	@Autowired
	UserLoginService userLoginService;
	@Autowired
	UserGetFriendsService userFriends;
	@Autowired
	GetPeopleService getPeople;
	@Autowired
	JwtGenerator jwtGenerator;
	@Autowired
	GetConnectionRequestsService getConnectionRequestsService;
	@Autowired
	GroupsService groupsService;
	
	static Logger log = Logger.getLogger(RememberRequestMapper.class.getName());
	
	
	
	
	//@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/login" ,method = RequestMethod.POST)
	public long  login(@RequestBody UserLogin user )
	{
		try
		{
			long user_Id = userLoginService.loginIn(user); 
			if( user_Id > 0)
				return user_Id;
				
		}
		catch (Exception e)
		{
			return -3;
		}
		return -2;
	}
	
	//@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/signup" ,method = RequestMethod.POST)
	public int signUp(@RequestBody UserSignUp signUpUser)
	{
		
		log.info(signUpUser.getUsername_sign() + " has signed up!!");
		try {
			userSignUp.signUp(signUpUser);
		}
		catch(ConstraintViolationException e) {
			return 2;
		}
		catch(Exception e)
		{
			return 3;
		}
		return 1;
	}
	
	//@CrossOrigin(origins = "http://localhost:4200" , allowedHeaders = "*")
	@RequestMapping(value = "/rem/getFriends" ,method = RequestMethod.POST , consumes = "text/plain")
	@ResponseBody
	public List<UserFriendsDisp> getFriendsList( @RequestHeader("Authorization") String Token , HttpServletResponse response) 
	{
		log.info("requesting for friends");

		JWTUser jwtUser = this.getUserInfoFromToken(Token);
		
		try {
			List<UserFriendsDisp> frnds_list =  userFriends.getFriends(jwtUser.getId());
			response.addHeader("access-control-expose-headers", "Authorization");
			response.setHeader("Authorization", this.jwtGenerator.generate(jwtUser));
			return frnds_list;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@RequestMapping(value = "/rem/getPeople" ,method = RequestMethod.POST , consumes = "text/plain")
	@ResponseBody
	public List<UserFriendsDisp> getPeople( @RequestHeader("Authorization") String Token , HttpServletResponse response) 
	{
		log.info("Getting poeple to Connect");

		JWTUser jwtUser = this.getUserInfoFromToken(Token);
		
		
		try {
			List<UserFriendsDisp> frnds_list =  getPeople.getAllPeople(jwtUser.getId());
			response.addHeader("access-control-expose-headers", "Authorization");
			response.setHeader("Authorization", this.jwtGenerator.generate(jwtUser));
			return frnds_list;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@RequestMapping(value = "/rem/getConnectionsRequests" , method = RequestMethod.POST , consumes = "text/plain" )
	@ResponseBody
	public List<UserFriendsDisp> getConnectionsRequests( @RequestHeader("Authorization") String Token , HttpServletResponse response) 
	{
		log.info("Getting poeple to Connect");

		JWTUser jwtUser = this.getUserInfoFromToken(Token);
		
		
		try {
			List<UserFriendsDisp> requests_list =  getConnectionRequestsService.getAllRequests(jwtUser.getId());
			response.addHeader("access-control-expose-headers", "Authorization");
			response.setHeader("Authorization", this.jwtGenerator.generate(jwtUser));
			return requests_list;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@RequestMapping(value = "/rem/CreateGroup" , method = RequestMethod.POST )
	@ResponseBody
	public int createGroups( @RequestHeader("Authorization") String Token , @RequestBody GroupsDto group , HttpServletResponse response) 
	{
		log.info("Getting poeple to Connect");

		JWTUser jwtUser = this.getUserInfoFromToken(Token);
		
		try {
			System.out.println("group name" + group.getGroupName());
			System.out.println("group Desc" + group.getGroupDesc());
			System.out.println("Admin id" + jwtUser.getId());
			int result = groupsService.CreateGroup(group , jwtUser.getId());
			response.addHeader("access-control-expose-headers", "Authorization");
			response.setHeader("Authorization", this.jwtGenerator.generate(jwtUser));
			return result;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
		
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
