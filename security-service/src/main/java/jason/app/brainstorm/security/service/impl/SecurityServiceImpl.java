package jason.app.brainstorm.security.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import jason.app.brainstorm.security.model.User;
import jason.app.brainstorm.security.service.SecurityService;

@Service("userService")
public class SecurityServiceImpl implements SecurityService {

    private final Map<Integer, User> users = new TreeMap<Integer, User>();

    public SecurityServiceImpl() {
        users.put(1, new User(1, "John Coltrane"));
        users.put(2, new User(2, "Miles Davis"));
        users.put(3, new User(3, "Sonny Rollins"));
    }

    @Override
    public User findUser(Integer id) {
        return users.get(id);
    }

    @Override
    @Secured("ROLE_ADMIN2")  
    public Collection<User> findUsers() {
        return users.values();
    }

    @Override
    public void updateUser(User user) {
        users.put(user.getId(), user);
    }
}
