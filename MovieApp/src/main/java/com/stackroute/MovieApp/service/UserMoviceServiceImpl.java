package com.stackroute.MovieApp.service;

import com.stackroute.MovieApp.exception.MovieNotFoundException;
import com.stackroute.MovieApp.exception.UnauthorizedAccessException;
import com.stackroute.MovieApp.exception.UserAlreadyExistsException;
import com.stackroute.MovieApp.exception.UserNotFoundException;
import com.stackroute.MovieApp.feignclient.AuthDTO;
import com.stackroute.MovieApp.feignclient.AuthProxy;
import com.stackroute.MovieApp.model.Movie;
import com.stackroute.MovieApp.model.User;
import com.stackroute.MovieApp.repository.MovieRepository;
import com.stackroute.MovieApp.repository.UserMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserMoviceServiceImpl implements UserMovieService {
//    interface UserMovieService=parent
//    class UserMoviceServiceImpl=child

    private UserMovieRepository userMovieRepository;
    public MovieRepository movieRepository;

    private AuthProxy authProxy;

    @Autowired
    public UserMoviceServiceImpl(UserMovieRepository userMovieRepository, MovieRepository movieRepository) {
        this.userMovieRepository = userMovieRepository;
        this.movieRepository=movieRepository;
        this.authProxy=authProxy;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        if (userMovieRepository.findById(user.getEmailId()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        User savedUser = userMovieRepository.save(user);
//        if(!(savedUser.getEmailId().isEmpty())) {
//            ResponseEntity r = userProxy.saveUser(user);
//            System.out.println(r.getBody());
//        }
        return savedUser;
    }


    @Override
    public User saveUserMovieToList(Movie movie, String emailId) throws UserNotFoundException {
        if (userMovieRepository.findById(emailId).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userMovieRepository.findByEmailId(emailId);
        user.setPaid(true);

        if (user.getMovieList() == null) {
            user.setMovieList(Arrays.asList(movie));
        } else {
            List<Movie> movies = user.getMovieList();
            movies.add(movie);
            user.setMovieList(movies);
        }
        return userMovieRepository.save(user);
    }
    @Override
    public User updatePaymentStatus(String email, boolean isPaid) throws UserNotFoundException {
        Optional<User> optionalUser = userMovieRepository.findById(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPaid(isPaid);
            return userMovieRepository.save(user);
        } else {
            throw new UserNotFoundException();
        }
    }


    @Override
    public Movie addMovie(Movie movie, String emailId) throws UnauthorizedAccessException {
        User user = userMovieRepository.findByEmailId(emailId);
//        && "ROLE_ADMIN".equals(user.getRole().trim())
        if ( user!=null ) {
            System.out.println("Saving movie to movieRepository");
            return movieRepository.insert(movie);

        }
        else {
            System.out.println("Unauthorized Access Exception in service");
            throw new UnauthorizedAccessException();
        }
    }



@Override
public void deleteMovie(String movieId, String emailId) throws UnauthorizedAccessException, MovieNotFoundException {
    User user = userMovieRepository.findByEmailId(emailId);

    if (user != null ) {
        Optional<Movie> movieToDelete = movieRepository.findById(movieId);

        if (movieToDelete.isPresent()) {
            movieRepository.delete(movieRepository.findByMovieId(movieId)); //movieToDelete.get()
            System.out.println("Movie deleted successfully"+movieId+"and id from optional "+movieToDelete.get());
        } else {
            System.out.println("Movie not found");
            throw new MovieNotFoundException();
        }
    } else {
        throw new UnauthorizedAccessException();
    }
}


    @Override
    public List<Movie> getAllUserMovies(String emailId) throws UserNotFoundException {
        if (userMovieRepository.findById(emailId).isPresent()) {
            return userMovieRepository.findById(emailId).get().getMovieList();
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public Movie updateUserMovieFromList(String emailId, String movieId, Movie updatedMovie) throws UserNotFoundException, MovieNotFoundException {
        Optional<User> optionalUser = userMovieRepository.findById(emailId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Movie> movies = user.getMovieList();

            // Check if the movieId exists in the user's movie list
            Optional<Movie> optionalMovie = movies.stream()
                    .filter(movie -> movie.getMovieId().equals(movieId))
                    .findFirst();

            if (optionalMovie.isPresent()) {
                // Update the movie details
                Movie existingMovie = optionalMovie.get();
                existingMovie.setMovieName(updatedMovie.getMovieName());
                existingMovie.setGenre(updatedMovie.getGenre());
                existingMovie.setLeadActors(updatedMovie.getLeadActors());
                existingMovie.setDirector(updatedMovie.getDirector());
                existingMovie.setYearOfRelease(updatedMovie.getYearOfRelease());
                existingMovie.setRating(updatedMovie.getRating());

                // Save the updated user with the modified movie list
                user.setMovieList(movies);
                userMovieRepository.save(user);

                return existingMovie;
            } else {
                throw new MovieNotFoundException();
            }
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<Movie> getAllMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<User> getAllUsers(String emailId) throws UnauthorizedAccessException,UserNotFoundException{
        User user= userMovieRepository.findByEmailId(emailId);
        if (user!=null){
            return userMovieRepository.findAll();
        } else if (user==null) {
            throw new UserNotFoundException();
        }
        else
            throw new RuntimeException("Data Not Found");

    }

//    @Override
//    public User updateUserProfile(String emailId, User updatedUser) throws UserNotFoundException {
//        User existingUser = userMovieRepository.findByEmailId(emailId);
//        System.out.println("the user in service "+existingUser);
//
////        sending data to auth app
//        AuthDTO authDTO =new AuthDTO(updatedUser.getEmailId(),updatedUser.getUserName(),updatedUser.getPassword(),updatedUser.getPhoneNumber(),updatedUser.getRole(),updatedUser.getMovieList());
//        ResponseEntity<?> responseEntity=authProxy.sendMovieDataToAuthApp(updatedUser);
//        System.out.println(responseEntity);
//
//
//        if (existingUser != null) {
//            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
//            existingUser.setUserName(updatedUser.getUserName());
//            existingUser.setPassword(updatedUser.getPassword());
//            return userMovieRepository.save(existingUser);
//        } else {
//            System.out.println("user not found");
//            throw new UserNotFoundException();
//        }
//    }

    @Override
    public User updateUserProfile(String emailId, User updatedUser) throws UserNotFoundException {

        Optional<User> optionalUser = userMovieRepository.findById(emailId);




        if (optionalUser.isPresent()) {

            User existingUser = userMovieRepository.findByEmailId(emailId);
            // Update user details
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setPassword(updatedUser.getPassword());

            // Update profile picture if provided
//            if (profilePicture != null) {
//                try {
//                    existingUser.setProfilePicture(profilePicture.getBytes());
//                } catch (IOException e) {
//                    throw new RuntimeException("Error updating profile picture", e);
//                }
//            }

            return userMovieRepository.save(existingUser);
        } else {
            throw new UserNotFoundException();
        }
    }




    @Override
    public Movie updateMovie(String movieId, Movie updatedMovie) throws MovieNotFoundException{
        Movie existingMovie = movieRepository.findByMovieId(movieId);
        System.out.println(existingMovie);
        if (existingMovie != null) {
            System.out.println("updated Movie method got id "+existingMovie);

            // Updating the fields
            existingMovie.setMovieName(updatedMovie.getMovieName());
            existingMovie.setGenre(updatedMovie.getGenre());
            existingMovie.setDirector(updatedMovie.getDirector());
            existingMovie.setYearOfRelease(updatedMovie.getYearOfRelease());

            return movieRepository.save(existingMovie);
        }
            throw new MovieNotFoundException();
    }

    @Override
    public void removeMovie(String movieId)throws MovieNotFoundException {
        Movie foundMovie = movieRepository.findByMovieId(movieId);

        if (foundMovie != null) {
            movieRepository.deleteById(movieId);
        }
        throw new MovieNotFoundException();

    }


}

