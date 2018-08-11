package remember_dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import remember_dto.ConnectPeople;
import remember_dto.UserFriendsDisp;
import remember_dto.UserSignUp;

@Repository
public class ConnectPeopleDao {
	
	@Autowired
	ConnectPeople connectPeople;
	@Autowired
	UserSignUp userData;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	UserFriendsDisp userForSocket;
	
	static Logger log = Logger.getLogger(ConnectPeopleDao.class.getName());
	
	public UserFriendsDisp connectPeopleUp(Long friendId , String userName)
	{
		try
		{
			Session session = this.sessionFactory.openSession();
			Transaction tran = session.beginTransaction();
			Criteria criteria = session.createCriteria(UserSignUp.class);
			userData  =  (UserSignUp)criteria.add(Restrictions.eq("username_sign", userName)).uniqueResult();
			userForSocket.setId(userData.getId());
			userForSocket.setUsername(userData.getUsername_sign());
			System.out.println("userId Connectpeopeldao : " + userData.getId());
			connectPeople.setStatus(0);
			connectPeople.setFriendId(userData.getId());
			connectPeople.setUserId(friendId);
			session.save(connectPeople);
			tran.commit();
			session.close();
		}
		catch (ConstraintViolationException e)
		{
			log.error("Exception occured in signUpDao function of UserSignUpDao becaue of duplication of username");
			throw e;
		}
		catch (Exception e) {
			log.error("Exception occured in signUpDao function of UserSignUpDao because of unknown reason");
			e.printStackTrace();
			throw e;
		}
		
		return userForSocket;
	}

}
