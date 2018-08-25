package remember_dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "group_posts")
public class Posts {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "post_id", updatable = false, nullable = false , unique = true)
	long id;
	
	@Column(name = "group_id")
	long groupId;
	
	@Column(name = "user_id")
	long userId;
	
	@Column(name = "content")
	String content;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
