package remember_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import remember_dao.UserLoginDao;
import remember_dto.UserLogin;

@Service
public class UserLoginService {
	
	@Autowired
	UserLoginDao userLoginDao;
	
	public long loginIn(UserLogin user) throws Exception
	{
		
		return userLoginDao.login(user);
	}

}
