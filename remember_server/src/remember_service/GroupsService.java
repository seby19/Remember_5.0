package remember_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import remember_dao.GroupsDao;
import remember_dto.GroupsDto;

@Service
public class GroupsService {
	
	@Autowired
	GroupsDao groupsDao;
	
	public int CreateGroup(GroupsDto group , Long id) {
		
		return groupsDao.getGroupNameChecked(group , id);
	}

}
