package remember_dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import remember_dto.Posts;
import remember_dto.UserFriendsDisp;
import remember_dto.UserSignUp;


@Repository
public class PostDao {
	
	@Autowired 
	Posts post;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	UserSignUp userData;
	
	List<PostsDisp> posts;
	@Autowired
	PostsDisp postDta;
	
	static Logger log = Logger.getLogger(PostDao.class.getName());

	
	public List<PostsDisp> getPosts(Long grpId)
	{
		try {
			
			Session session = sessionFactory.openSession();
			Transaction tran = session.beginTransaction();
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
			tran.commit();
			session.close();
			
		}catch (Exception e) {
			return null;
		}
		
		
		return posts;
	}
	public PostsDisp addPost(Long groupId ,String  postData ,String userName)
	{
		try {
			Session session = this.sessionFactory.openSession();
			Transaction tran = session.beginTransaction();
			Criteria criteria = session.createCriteria(UserSignUp.class);
			userData  =  (UserSignUp)criteria.add(Restrictions.eq("username_sign", userName)).uniqueResult();
			post.setUserId(userData.getId());
			post.setContent(postData);
			post.setGroupId(groupId);
			
			session.save(post);
			postDta.setContent(postData);
			postDta.setGroupId(groupId);
			postDta.setId(post.getId());
			postDta.setUserId(post.getUserId());
			postDta.setUsername(userData.getUsername_sign());
			tran.commit();
			session.close();
		}catch (Exception e) {
			return null;
		}
		return postDta;
	}
}
