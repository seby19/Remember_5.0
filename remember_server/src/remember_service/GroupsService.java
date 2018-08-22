package remember_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import remember_dao.GroupsDao;
import remember_dto.CreateGroupsDto;
import remember_dto.GroupsDto;
import remember_dto.UserFriendsDisp;

@Service
public class GroupsService {
	
	@Autowired
	GroupsDao groupsDao;
	
	public long CreateGroup(GroupsDto group , Long id) {
		
		return groupsDao.getGroupNameChecked(group , id);
	}
	
	public List<UserFriendsDisp> getGroupAddFriendsList(long groupId , long userId)
	{
		return groupsDao.getGroupAddFriendsList(groupId , userId);
	}
	
	public CreateGroupsDto addPersonToGroup(String friendUsername ,long groupId)
	{
		return groupsDao.addPersonToGroup(friendUsername , groupId);
	}

}
