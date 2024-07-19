package com.stackroute.MovieApp.service;

import com.stackroute.MovieApp.exception.MovieNotFoundException;
import com.stackroute.MovieApp.exception.UserNotFoundException;
import com.stackroute.MovieApp.model.Movie;
import com.stackroute.MovieApp.model.User;

public interface WishlistService {
    User saveMovieToWishlist(String emailId, Movie movie) throws UserNotFoundException;

    User removeMovieFromWishlist(String emailId, String movieId) throws UserNotFoundException, MovieNotFoundException;
}
