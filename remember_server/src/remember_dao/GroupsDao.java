package remember_dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import remember_dto.CreateGroupsDto;
import remember_dto.GroupPeople;
import remember_dto.GroupsDto;
import remember_dto.UserFriendsDisp;
import remember_dto.UserSignUp;

@Repository
public class GroupsDao {
	
	@Autowired
	CreateGroupsDto groups;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	GroupPeople groupPeople;
	@Autowired
	UserSignUp userData;
	
	static Logger log = Logger.getLogger(GroupsDao.class.getName());
	
	List<UserFriendsDisp> friends_list ;
	

	
	public long getGroupNameChecked(GroupsDto group , Long id) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tran = session.beginTransaction();
			groups.setGroupName(group.getGroupName());
			groups.setGroupDesc(group.getGroupDesc());
			groups.setAdminId(id);
			session.save(groups);
			groupPeople.setGroupId(groups.getId());
			groupPeople.setUserId(id);
			session.save(groupPeople);
			tran.commit();
			session.close();
			
					
		} catch (Exception e) {
			return -1;
		}
		
		return groups.getId();
	}
	
	public List<UserFriendsDisp> getGroupAddFriendsList(long groupId ,long user_id) {
		String sql = "select uinf.user_id,uinf.username from users_info uinf , user_friends ufrnd where ufrnd.user_id = :user_id " + 
		" and ufrnd.friend_id = uinf.user_id " +
		" and uinf.user_id not in (select user_id from group_people gp where gp.group_id = :group_id)";
		this.friends_list = new ArrayList<UserFriendsDisp>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(sql).addScalar("user_id",  LongType.INSTANCE)
														.addScalar("username", StringType.INSTANCE);
			
			System.out.println("User Id In doa" + user_id);
			query.setLong("user_id", user_id);
			query.setLong("group_id" , groupId);
			
			List<Object []> rows= query.list();
			if(rows != null)
			{
				
				for(Object []  iter : rows)
				{	
					UserFriendsDisp friends  = new UserFriendsDisp();
					friends.setId(Long.parseLong(iter[0].toString()));
					friends.setUsername(iter[1].toString());
					friends_list.add(friends);
				}
			}
			
			session.close();
			return friends_list;
		} catch (NumberFormatException e) {
			log.error("Exception occured in login function of getFriends because of NumberFormatException reason");
			throw e;
		} catch (HibernateException e) {
			log.error("Exception occured in login function of getFriends because of HibernateException reason");
			throw e;
		}
	}
	
	public CreateGroupsDto addPersonToGroup(String friendUsername ,long groupId)
	{
		try {
			Session session = sessionFactory.openSession();
			Transaction tran = session.beginTransaction();
			Criteria criteria = session.createCriteria(UserSignUp.class);
			userData  =  (UserSignUp)criteria.add(Restrictions.eq("username_sign", friendUsername)).uniqueResult();
			groupPeople.setUserId(userData.getId());
			groupPeople.setGroupId(groupId);
			session.save(groupPeople);
			criteria = session.createCriteria(CreateGroupsDto.class);
			groups = (CreateGroupsDto)criteria.add(Restrictions.eq("id" , groupId)).uniqueResult();
			
			tran.commit();
			session.close();
			
					
		} catch (Exception e) {
			return null;
		}
		
		return groups;
	}
}
