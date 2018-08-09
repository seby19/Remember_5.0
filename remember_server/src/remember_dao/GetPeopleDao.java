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
public class GetPeopleDao {
	
	@Autowired
	SessionFactory sessionFactory;

	List<UserFriendsDisp> peoples_list ;
	
	static Logger log = Logger.getLogger(UserFriendsDao.class.getName());
	
	public List<UserFriendsDisp> getAllPeople(Long user_id)
	{
		String sql = "select uinf.user_id,uinf.username from users_info uinf where uinf.user_id <> :userid_a"
				+ " and not exists (select frnd.friend_id from user_friends frnd where frnd.user_id = :userid_b"
				+ "	and frnd.friend_id = uinf.user_id)"
				+ " and not exists (select rqst.friend_id from connect_people rqst where rqst.user_id = :userid_c"
				+ " and rqst.friend_id = uinf.user_id)";
		this.peoples_list = new ArrayList<UserFriendsDisp>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(sql).addScalar("user_id",  LongType.INSTANCE)
														.addScalar("username", StringType.INSTANCE);
			query.setLong("userid_a", user_id);
			query.setLong("userid_b", user_id);
			query.setLong("userid_c", user_id);
			List<Object []> rows= query.list();
			if(rows != null)
			{
				
				for(Object []  iter : rows)
				{	
					UserFriendsDisp friends  = new UserFriendsDisp();
					friends.setId(Long.parseLong(iter[0].toString()));
					friends.setUsername(iter[1].toString());
					peoples_list.add(friends);
				}
			}
			
			session.close();
			return peoples_list;
		} catch (NumberFormatException e) {
			log.error("Exception occured in login function of getAllPeople because of NumberFormatException reason");
			throw e;
		} catch (HibernateException e) {
			log.error("Exception occured in login function of getAllPeople because of HibernateException reason");
			throw e;
		}
	}

}
