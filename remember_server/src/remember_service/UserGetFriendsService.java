package remember_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import remember_dao.UserFriendsDao;
import remember_dto.UserFriendsDisp;

@Service
public class UserGetFriendsService {
	
	@Autowired
	UserFriendsDao userFriends;
	
	public List<UserFriendsDisp> getFriends(Long user_id) throws Exception
	{
		return userFriends.getFriends(user_id);
	}
}
