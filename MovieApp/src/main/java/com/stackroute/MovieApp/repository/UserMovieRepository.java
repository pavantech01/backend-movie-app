package com.stackroute.MovieApp.repository;

import com.stackroute.MovieApp.model.Movie;
import com.stackroute.MovieApp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.awt.*;

public interface UserMovieRepository extends MongoRepository<User, String> {
    User findByEmailId(String emailId);
    User findByEmailIdAndPassword(String emailId, String password);



}
