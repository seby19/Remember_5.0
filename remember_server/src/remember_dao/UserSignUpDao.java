package remember_dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import remember_dto.UserSignUp;
import remember_service.UserSignUPService;


@Repository
public class UserSignUpDao {
	@Autowired
	SessionFactory sessionFactory;
	static Logger log = Logger.getLogger(UserSignUpDao.class.getName());
	
	public boolean signUpDao(UserSignUp signUpUser)
	{
		try
		{
			Session session = sessionFactory.openSession();
			Transaction tran = session.beginTransaction();
			session.save(signUpUser);
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
		
		return true;
	}
}
