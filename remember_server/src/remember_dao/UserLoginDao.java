package remember_dao;





import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import remember_dto.UserLogin;

@Repository
public class UserLoginDao {
	
	
	@Autowired
	SessionFactory sessionFactory;
	static Logger log = Logger.getLogger(UserLoginDao.class.getName());
	
	
	public long login(UserLogin user)
	{

		String sql = "Select user_id  from test.users_info where username = :username and password = :password";
		try
		{
			
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql).addScalar("user_id", LongType.INSTANCE);

			query.setString("username", user.getUsername());
			query.setString("password", user.getPassword());
			long count = 0;
			if(query.uniqueResult() != null)
			{
				count = (long)query.uniqueResult();
			}
			System.out.println("Long " + count);
			session.close();
			return count;
		}
		catch (Exception e) {
			log.error("Exception occured in login function of UserLoginDao because of unknown reason");
			e.printStackTrace();
			throw e;
		}
		
	}
}
