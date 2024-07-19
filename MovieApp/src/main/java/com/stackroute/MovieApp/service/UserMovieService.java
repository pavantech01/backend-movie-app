package com.stackroute.MovieApp.service;

import com.stackroute.MovieApp.exception.MovieNotFoundException;
import com.stackroute.MovieApp.exception.UnauthorizedAccessException;
import com.stackroute.MovieApp.exception.UserAlreadyExistsException;
import com.stackroute.MovieApp.exception.UserNotFoundException;
import com.stackroute.MovieApp.model.Movie;
import com.stackroute.MovieApp.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserMovieService {
    User registerUser(User user) throws UserAlreadyExistsException;

    User saveUserMovieToList(Movie movie,String emailId) throws UserNotFoundException;
    public User updatePaymentStatus(String email, boolean isPaid) throws UserNotFoundException;// payment status
    Movie addMovie(Movie movie, String emailId) throws UnauthorizedAccessException;
    void deleteMovie(String movieId, String emailId) throws UnauthorizedAccessException, MovieNotFoundException;
    List<Movie> getAllUserMovies(String emailId) throws UserNotFoundException;

    Movie updateUserMovieFromList(String emailId, String movieId, Movie updatedMovie)
            throws UserNotFoundException, MovieNotFoundException;
    List<Movie> getAllMoviesByGenre(String genre);
    List<Movie> getAllMovies();

    List<User> getAllUsers(String emailId) throws UnauthorizedAccessException, UserNotFoundException;

    User updateUserProfile(String emailId, User updatedUser) throws UserNotFoundException;


    Movie updateMovie(String movieId, Movie updatedMovie) throws MovieNotFoundException;

    void removeMovie(String movieId) throws MovieNotFoundException;


}
