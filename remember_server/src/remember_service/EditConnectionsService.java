package remember_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import remember_dao.EditConnectionsDao;
import remember_dto.UserFriendsDisp;

@Service
public class EditConnectionsService {
	
	@Autowired
	EditConnectionsDao editConnectionsDao; 
	public UserFriendsDisp editConnection(long friendId, String username , int property) {
		return editConnectionsDao.editConnection( friendId, username , property);
	}

}
