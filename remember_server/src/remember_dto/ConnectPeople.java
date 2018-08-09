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
@Table(name = "connect_people")
public class ConnectPeople {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "request_id", updatable = false, nullable = false , unique = true)
	Long id;
	
	@Column(name = "user_id" , nullable = false)
	Long userId;
	
	@Column(name = "friend_id" , nullable = false)
	Long friendId;
	
	@Column(name = "status" , nullable = false)
	int status;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	
	
	
	
}
