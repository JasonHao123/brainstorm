package jason.app.brainstorm.security.dao;

import jason.app.brainstorm.security.entity.User;



public interface UserDao {

    User findByUsername(String username);

}
