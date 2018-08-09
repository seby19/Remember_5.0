package remember_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import remember_dao.GetPeopleDao;
import remember_dao.UserFriendsDao;
import remember_dto.UserFriendsDisp;

@Service
public class GetPeopleService{
	
	@Autowired
	GetPeopleDao getPeople;
	
	public List<UserFriendsDisp> getAllPeople(Long id) throws Exception
	{
		return getPeople.getAllPeople(id);
	}

}
