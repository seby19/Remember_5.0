package remember_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import remember_dao.ConnectPeopleDao;

@Service
public class ConnectPeopleService {
	
	@Autowired
	ConnectPeopleDao connectPeopleDao;
	public boolean connectPeople(Long friendId , String userName) throws Exception
	{
		
		return connectPeopleDao.connectPeopleUp(friendId, userName);
		
	}
	
}
