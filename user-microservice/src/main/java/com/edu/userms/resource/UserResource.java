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
import org.springframework.web.client.RestTemplate;

import com.edu.userms.model.Order;
import com.edu.userms.model.User;
import com.edu.userms.repo.UserRepo;
import com.edu.userms.service.UserService;

@RestController
public class UserResource {
	private final static Logger LOG = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	private UserRepo repo;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UserService service;

	// Method to get all users present in DB
	@GetMapping("/users")
	public List<User> getAllUsers() {
		LOG.info("inside getAllUsers method");
		return repo.findAll();
	}

	@GetMapping("/user/{userid}/order/{orderid}")
	public ResponseEntity<User> getUserById(@PathVariable("userid") int userid, @PathVariable("orderid") int orderid) {
		LOG.info("inside getUserById method");
		User user = repo.getById(userid);
		LOG.info("going to call OrderMS using Eureka discovery");
		Order order = restTemplate.getForObject("http://ORDERMS/order/" + orderid, Order.class);
		LOG.info("printing order details started--->>>");
		LOG.info("Orderd is " + order.getOrderId());
		LOG.info("ItemName is " + order.getItemName());
		LOG.info("ItemCount is " + order.getItemCount());
		LOG.info("DeliveryAddress is " + order.getDeliveryAddress());
		LOG.info("DeliveryRemarks is " + order.getDeliveryRemarks());
		LOG.info("printing order details ended--->>>");
		user.setOrderInfo(order);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// Method to create a new user
	@PostMapping("/users")
	public ResponseEntity<User> saveUser(@RequestBody User user) throws Exception {
		LOG.info("inside saveUser method");
		return new ResponseEntity<User>(service.saveUser(user), HttpStatus.CREATED);

	}

}
