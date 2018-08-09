package remember_dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import remember_dto.UserFriendsDisp;

@Repository
public class UserFriendsDao {
	@Autowired
	SessionFactory sessionFactory;
	
	
	
	
	List<UserFriendsDisp> friends_list ;
	
	static Logger log = Logger.getLogger(UserFriendsDao.class.getName());
	
	public List<UserFriendsDisp> getFriends(Long user_id)
	{
		String sql = "select uinf.user_id,uinf.username from users_info uinf , user_friends ufrnd where ufrnd.user_id = :user_id and ufrnd.friend_id = uinf.user_id;";
		this.friends_list = new ArrayList<UserFriendsDisp>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(sql).addScalar("user_id",  LongType.INSTANCE)
														.addScalar("username", StringType.INSTANCE);
			
			System.out.println("User Id In doa" + user_id);
			query.setLong("user_id", user_id);
			
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
	
}
