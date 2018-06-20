package jason.app.brainstorm.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jason.app.brainstorm.security.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

	@Query("SELECT u.username from User u")
	List<String> findAllUsernames();

}
