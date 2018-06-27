package jason.app.brainstorm.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jason.app.brainstorm.security.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

}
