package org.apache.ofbiz.security.dao;

import org.apache.ofbiz.security.entity.User;



public interface UserDao {

    User findByUsername(String username);

}
