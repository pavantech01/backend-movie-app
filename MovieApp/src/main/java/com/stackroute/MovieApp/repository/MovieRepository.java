package com.stackroute.MovieApp.repository;

import com.stackroute.MovieApp.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface MovieRepository extends MongoRepository<Movie, String> {

    List<Movie> findByGenre(String genre);

    Movie findByMovieId(String movieId);


//    List<Movie> findByMovieNameContainingIgnoreCaseOrGenreContainingIgnoreCaseOrLeadActorsContainingIgnoreCase
//            (String movieName, String genre, String leadActor);
//
//
//    List<Movie> findByGenreAndYearOfReleaseAndRatingGreaterThan(
//            String genre, String yearOfRelease, int rating);

}
