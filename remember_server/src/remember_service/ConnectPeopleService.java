package remember_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import remember_dao.ConnectPeopleDao;
import remember_dto.UserFriendsDisp;

@Service
public class ConnectPeopleService {
	
	@Autowired
	ConnectPeopleDao connectPeopleDao;
	public UserFriendsDisp connectPeople(Long friendId , String userName) throws Exception
	{
		
		return connectPeopleDao.connectPeopleUp(friendId, userName);
		
	}
	
}
