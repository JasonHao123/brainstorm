package org.apache.ofbiz.security.repository;

import java.util.List;

import org.apache.ofbiz.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

	@Query("SELECT u.username from User u")
	List<String> findAllUsernames();

}
