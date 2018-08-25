package remember_dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import remember_dto.Posts;
import remember_dto.UserFriendsDisp;


@Repository
public class PostDao {
	
	@Autowired 
	Posts post;
	@Autowired
	SessionFactory sessionFactory;
	
	List<PostsDisp> posts;
	
	static Logger log = Logger.getLogger(PostDao.class.getName());

	
	public List<PostsDisp> getPosts(Long grpId)
	{
		try {
			
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			String sql = " select posts.post_id , posts.content , posts.group_id, " + 
					"  posts.user_id , users.username from group_posts posts , users_info users " + 
					"where posts.user_id = users.user_id " + 
					"and posts.group_id = :group_id  ";
			this.posts = new ArrayList<PostsDisp>();
			
			SQLQuery query = session.createSQLQuery(sql).addScalar("post_id",  LongType.INSTANCE)
					.addScalar("content", StringType.INSTANCE)
					.addScalar("group_id", LongType.INSTANCE)
					.addScalar("user_id", LongType.INSTANCE)
					.addScalar("username", StringType.INSTANCE);
			
			query.setLong("group_id", grpId);
			List<Object []> rows= query.list();
			
			if(rows != null)
			{
				
				for(Object []  iter : rows)
				{	
					PostsDisp postt  = new PostsDisp();
					postt.setId(Long.parseLong(iter[0].toString()));					
					postt.setContent(iter[1].toString());
					postt.setGroupId(Long.parseLong(iter[2].toString()));
					postt.setUserId(Long.parseLong(iter[3].toString()));
					postt.setUsername(iter[4].toString());
					posts.add(postt);
				}
			}
			
			
		}catch (Exception e) {
			return null;
		}
		
		
		return posts;
	}
}
