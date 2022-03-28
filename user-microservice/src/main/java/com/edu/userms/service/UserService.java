package com.edu.userms.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.userms.model.User;
import com.edu.userms.repo.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo repo;

	private final static Logger LOG = LoggerFactory.getLogger(UserService.class);

	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

	private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

	public User saveUser(User user) throws Exception {
		LOG.info("inside getAllUsers method " + user.toString());
		// password validation
		Matcher matcher = pattern.matcher(user.getPassword());
		if (matcher.matches()) {
			repo.save(user);
		} else {
			throw new Exception("Password not valid");
		}
		return user;

	}

}
