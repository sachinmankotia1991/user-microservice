package com.edu.userms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.userms.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
