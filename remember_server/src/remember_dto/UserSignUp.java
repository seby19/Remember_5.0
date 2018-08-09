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
@Table(name = "users_info")
public class UserSignUp {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "user_id", updatable = false, nullable = false , unique = true)
	Long id;
	
	@Column(name = "email", nullable = false)
	String email ;
	@Column(name = "password", nullable = false)
	String password_sign;
	@Column(name = "username", nullable = false , unique = true)
	String username_sign;
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword_sign() {
		return password_sign;
	}

	public void setPassword_sign(String password_sign) {
		this.password_sign = password_sign;
	}

	public String getUsername_sign() {
		return username_sign;
	}

	public void setUsername_sign(String username_sign) {
		this.username_sign = username_sign;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
