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
import remember_dto.UserFriends;
import remember_dto.UserFriendsDisp;
import remember_dto.UserSignUp;

@Repository
public class EditConnectionsDao {
	
	@Autowired
	ConnectPeople connectPeople;
	@Autowired
	UserSignUp userData;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	UserFriendsDisp userForSocket;

	UserFriends userFriends;
	UserFriends userFriends2;
	
	static Logger log = Logger.getLogger(ConnectPeopleDao.class.getName());

	public UserFriendsDisp editConnection(long friendId, String username, int property) {
		try
		{
			userFriends = new UserFriends();
			userFriends2 = new UserFriends();
			userForSocket = new UserFriendsDisp();
			Session session = this.sessionFactory.openSession();
			Transaction tran = session.beginTransaction();
			Criteria criteria = session.createCriteria(UserSignUp.class);
			userData  =  (UserSignUp)criteria.add(Restrictions.eq("username_sign", username)).uniqueResult();
			System.out.println("userData" + userData.getUsername_sign() + " id " + userData.getId());
			userForSocket.setId(userData.getId());
			userForSocket.setUsername(userData.getUsername_sign());
			System.out.println("userId Connectpeopeldao : " + userData.getId());
			criteria = session.createCriteria(ConnectPeople.class);
			criteria.add(Restrictions.eq("userId", userData.getId()));
			criteria.add(Restrictions.eq("friendId", friendId));
			connectPeople = (ConnectPeople)criteria.add(Restrictions.eq("status", 0)).uniqueResult();
			

			if(property == 1)
			{
				connectPeople.setStatus(1);
				userFriends.setUser_id(userData.getId());
				userFriends.setFriend_id(friendId);
				session.save(userFriends);
				userFriends2.setUser_id(friendId);
				userFriends2.setFriend_id(userData.getId());
				session.save(userFriends2);
			}
			else if(property == 2)
			{
				connectPeople.setStatus(2);
				userForSocket = null;
			}
			
			session.update(connectPeople);
			tran.commit();
			session.close();
			return userForSocket;
			
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
	}


}
