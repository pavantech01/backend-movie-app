package com.stackroute.MovieApp.service;

import com.stackroute.MovieApp.exception.MovieNotFoundException;
import com.stackroute.MovieApp.exception.UserNotFoundException;
import com.stackroute.MovieApp.model.Movie;
import com.stackroute.MovieApp.model.User;
import com.stackroute.MovieApp.repository.UserMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService{

    private  UserMovieRepository userMovieRepository;

    @Autowired
    public WishlistServiceImpl(UserMovieRepository userMovieRepository) {
        this.userMovieRepository = userMovieRepository;
    }

    @Override
    public User saveMovieToWishlist(String emailId, Movie movie) throws UserNotFoundException {
        User user = userMovieRepository.findByEmailId(emailId);

        List<Movie> movies = user.getMovieList();
        if (movies == null) {
            user.setMovieList(Arrays.asList(movie));
        } else {
            movies.add(movie);
            user.setMovieList(movies);
        }

        return userMovieRepository.save(user);
    }


    @Override
    public User removeMovieFromWishlist(String emailId, String movieId) throws UserNotFoundException, MovieNotFoundException {
        User user = userMovieRepository.findByEmailId(emailId);

        List<Movie> movies = user.getMovieList();
        boolean movieFound = false;
        for (Movie movie : movies) {
            if (movie.getMovieId().equals(movieId)) {
                movies.remove(movie);
                movieFound = true;
                break;
            }
        }

        if (!movieFound) {
            // If the movie is not found, throw an exception
            throw new MovieNotFoundException();
        }

//        if (movies == null || !movies.removeIf(x -> x.getMovieId().equals(movieId))) {
//            throw new MovieNotFoundException();
//        }

        user.setMovieList(movies);
        return userMovieRepository.save(user);
    }
}
