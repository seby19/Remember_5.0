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
public class GetConnectionRequestsDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	List<UserFriendsDisp> request_list ;
	
	static Logger log = Logger.getLogger(UserFriendsDao.class.getName());
	
	public List<UserFriendsDisp> getAllRequests(Long user_id) {
		String sql = "Select unif.user_id , unif.username from users_info unif , connect_people cnt " 
				     + "where cnt.friend_id = unif.user_id and cnt.user_id = :userId and cnt.status = 0";
		this.request_list = new ArrayList<UserFriendsDisp>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(sql).addScalar("user_id",  LongType.INSTANCE)
														.addScalar("username", StringType.INSTANCE);
			query.setLong("userId", user_id);
			List<Object []> rows= query.list();
			if(rows != null)
			{
				
				for(Object []  iter : rows)
				{	
					UserFriendsDisp friends  = new UserFriendsDisp();
					friends.setId(Long.parseLong(iter[0].toString()));
					friends.setUsername(iter[1].toString());
					request_list.add(friends);
				}
			}
			
			session.close();
			return request_list;
		} catch (NumberFormatException e) {
			log.error("Exception occured in login function of getAllPeople because of NumberFormatException reason");
			throw e;
		} catch (HibernateException e) {
			log.error("Exception occured in login function of getAllPeople because of HibernateException reason");
			throw e;
		}
	}

}
