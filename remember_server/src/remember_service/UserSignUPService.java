package remember_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import remember_dao.UserSignUpDao;
import remember_dto.UserSignUp;

@Service
public class UserSignUPService {
	
	@Autowired
	UserSignUpDao userSignUpDao;
	
	public boolean signUp(UserSignUp signUpUser) throws Exception
	{
		return userSignUpDao.signUpDao(signUpUser);		
	}

}
