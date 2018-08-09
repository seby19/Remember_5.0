package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import authentication.JwtGenerator;
import remember_dto.JWTUser;
import remember_dto.JwtInterUser;
//import remember_dto.UserLogin;

@RestController
public class TokenController {
	
	@Autowired
	private JwtGenerator jwtGenerator;
	private JWTUser jwtUser;
	public TokenController(JwtGenerator jwtGenerator) {
	    this.jwtGenerator = jwtGenerator;
	}
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/token" , method = RequestMethod.POST)
	public String generate(@RequestBody final JwtInterUser user) {
		jwtUser = new JWTUser();
		jwtUser.setUserName(user.getUsername());
		jwtUser.setRole("user");
		jwtUser.setId(user.getId());
	    return jwtGenerator.generate(jwtUser);
	
	}
}
