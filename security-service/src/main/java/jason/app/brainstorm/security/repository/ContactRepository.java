package jason.app.brainstorm.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jason.app.brainstorm.security.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{

}
