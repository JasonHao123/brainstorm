package org.apache.ofbiz.security.repository;

import org.apache.ofbiz.security.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long>{

}
