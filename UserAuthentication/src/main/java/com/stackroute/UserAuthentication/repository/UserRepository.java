package com.stackroute.UserAuthentication.repository;

import com.stackroute.UserAuthentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmailId(String emailId);
    User findByEmailIdAndPassword(String emailId, String password);
}
