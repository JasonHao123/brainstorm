package org.apache.ofbiz.security.service;

import java.util.Collection;

import org.apache.ofbiz.security.model.User;
import org.springframework.security.access.annotation.Secured;

public interface SecurityService {
    /**
     * Find a user by the given ID
     *
     * @param id
     *            the ID of the user
     * @return the user, or <code>null</code> if user not found.
     */
    User findUser(Integer id);

    /**
     * Find all users
     *
     * @return a collection of all users
     */
    @Secured("ROLE_ADMIN2")  
    Collection<User> findUsers();

    /**
     * Update the given user
     *
     * @param user
     *            the user
     */
    void updateUser(User user);
}
