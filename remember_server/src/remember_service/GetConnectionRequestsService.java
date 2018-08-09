package remember_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import remember_dao.GetConnectionRequestsDao;
import remember_dto.UserFriendsDisp;

@Service
public class GetConnectionRequestsService {
	@Autowired
	GetConnectionRequestsDao getConnectionRequests;
	
	public List<UserFriendsDisp> getAllRequests(Long id) throws Exception
	{
		return getConnectionRequests.getAllRequests(id);
	}
}
