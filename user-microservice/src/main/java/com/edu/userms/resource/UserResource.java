package com.edu.userms.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edu.userms.model.User;
import com.edu.userms.repo.UserRepo;
import com.edu.userms.service.UserService;

@RestController
public class UserResource {
	private final static Logger LOG = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	private UserRepo repo;

	@Autowired
	private UserService service;

	// Method to get all users present in DB
	@GetMapping("/users")
	public List<User> getAllUsers() {
		LOG.info("inside getAllUsers method");
		return repo.findAll();
	}

	@GetMapping("/user/{userid}")
	public ResponseEntity<User> getAllUsers(@PathVariable("userid") int id) {
		LOG.info("inside getAllUsers method");
		return new ResponseEntity<User>(repo.getById(id), HttpStatus.OK);
	}

	// Method to create a new user
	@PostMapping("/users")
	public ResponseEntity<User> saveUser(@RequestBody User user) throws Exception {
		LOG.info("inside saveUser method");
		return new ResponseEntity<User>(service.saveUser(user), HttpStatus.CREATED);

	}

}
