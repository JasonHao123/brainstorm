/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jason.app.brainstorm.customer.service.service;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.camel.Exchange;
import org.apache.camel.http.common.HttpMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Service;

import jason.app.brainstorm.customer.service.model.InitLoginResult;
import jason.app.brainstorm.customer.service.model.LoginResult;
import jason.app.brainstorm.customer.service.model.User;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CsrfTokenRepository csrfTokenRepository;
    private final Map<Integer, User> users = new TreeMap<Integer, User>();

    public UserServiceImpl() {
        users.put(1, new User(1, "John Coltrane"));
        users.put(2, new User(2, "Miles Davis"));
        users.put(3, new User(3, "Sonny Rollins"));
    }

    @Override
    public User findUser(Integer id) {
        return users.get(id);
    }

    @Override
    public Collection<User> findUsers() {
        return users.values();
    }

    @Override
    public void updateUser(User user) {
        users.put(user.getId(), user);
    }

	@Override
	public void test(Exchange exchange) {
		// TODO Auto-generated method stub
		if (exchange.getIn() instanceof HttpMessage) {
			HttpServletRequest request = ((HttpMessage) exchange.getIn()).getRequest();
			HttpSession session = request.getSession(true);
			
			session.setAttribute("hello", "world");
		}
	}

	@Override
	public void login(Exchange exchange) {
		LoginResult result = new LoginResult();
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("user", "password");
		Authentication auth = authenticationManager.authenticate(token);
		result.setStatus(auth.isAuthenticated()?1:0);
		SecurityContextHolder.getContext().setAuthentication(auth);
		exchange.getOut().setBody(result);
	}

	@Override
	public void initLogin(Exchange exchange) {
		HttpMessage message = (HttpMessage) exchange.getIn();
		CsrfToken token = csrfTokenRepository.generateToken(message.getRequest());
		HttpSession session = message.getRequest().getSession(true);
		session.setAttribute("org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN", token);
		InitLoginResult result = new InitLoginResult();
		result.setCsrfToken(token.getToken());
		result.setSessionId(session.getId());
		exchange.getOut().setBody(result);;
	}

	@Override
	public void logout(Exchange exchange) {
		// TODO Auto-generated method stub
		HttpMessage message = (HttpMessage) exchange.getMessage();
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         if (auth != null){    
            new SecurityContextLogoutHandler().logout(message.getRequest(), message.getResponse(), auth);
         }
         SecurityContextHolder.getContext().setAuthentication(null);
         exchange.getOut().setBody("logout success!");
	}
}
