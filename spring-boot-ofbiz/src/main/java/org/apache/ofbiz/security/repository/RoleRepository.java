package org.apache.ofbiz.security.repository;

import org.apache.ofbiz.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
