package remember_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import remember_dao.PostDao;
import remember_dao.PostsDisp;
import remember_dto.Posts;

@Service
public class PostService {
	@Autowired
	PostDao postDao;
	
	public List<PostsDisp> getPosts(Long grpId)
	{
		return postDao.getPosts(grpId);
	}
	
}
