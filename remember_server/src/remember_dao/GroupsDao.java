package remember_dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import remember_dto.CreateGroupsDto;
import remember_dto.GroupsDto;

@Repository
public class GroupsDao {
	
	@Autowired
	CreateGroupsDto groups;
	@Autowired
	SessionFactory sessionFactory;
	
	static Logger log = Logger.getLogger(GroupsDao.class.getName());
	
	
	public int getGroupNameChecked(GroupsDto group , Long id) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tran = session.beginTransaction();
			groups.setGroupName(group.getGroupName());
			groups.setGroupDesc(group.getGroupDesc());
			groups.setAdminId(id);
			session.save(groups);
			tran.commit();
			session.close();
			
					
		} catch (Exception e) {
			return 3;
		}
		
		return 1;
	}

}
