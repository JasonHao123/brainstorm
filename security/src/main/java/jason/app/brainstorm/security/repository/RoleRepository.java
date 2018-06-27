package jason.app.brainstorm.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jason.app.brainstorm.security.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
