package com.stackroute.MovieApp.controller;

import com.stackroute.MovieApp.exception.MovieNotFoundException;
import com.stackroute.MovieApp.exception.UnauthorizedAccessException;
import com.stackroute.MovieApp.exception.UserAlreadyExistsException;
import com.stackroute.MovieApp.exception.UserNotFoundException;
import com.stackroute.MovieApp.feignclient.AuthProxy;
import com.stackroute.MovieApp.model.Movie;
import com.stackroute.MovieApp.model.User;
import com.stackroute.MovieApp.service.UserMovieService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/movie-app")
public class UserMovieController {
    private UserMovieService userMovieService;

    @Autowired
    private AuthProxy authProxy;

    private ResponseEntity<?> responseEntity;
//    private String emailId;

    @Autowired
    public UserMovieController(UserMovieService userMovieService, AuthProxy authProxy) {
        this.userMovieService = userMovieService;
        this.authProxy = authProxy;
    }

//    http://localhost:9090/movie-app/register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws UserAlreadyExistsException {
        try {
            responseEntity =  new ResponseEntity<>(userMovieService.registerUser(user), HttpStatus.CREATED);
        }
        catch(UserAlreadyExistsException e)
        {
            throw new UserAlreadyExistsException();
        }
        return responseEntity;
    }

//    http://localhost:9090/movie-app/user/movie
//    user to save
    @PostMapping("/user/movie")
    public ResponseEntity<?> saveUserMovieToList(@RequestBody Movie movie, HttpServletRequest request) throws UserNotFoundException {


                     String emailId = (String) request.getAttribute("emailId");
                     String role= (String) request.getAttribute("role");
                     System.out.println("Email in Controller: "+ emailId);
                     System.out.println("Role in Controller: " + role);

                if (role != null && role.equals("ROLE_USER")){
                    System.out.println("controller method got user role "+role);
                    return new ResponseEntity<>(userMovieService.saveUserMovieToList(movie,emailId),HttpStatus.CREATED);
                }
            else {
                    System.out.println("controller method not got the claims of user role");
                return new  ResponseEntity<>("Claims not found", HttpStatus.BAD_REQUEST);
            }
    }
//    http://localhost:9090/movie-app/user/movies
    @GetMapping("/user/movies")
    public ResponseEntity<?> getAllUserMoviesFromList(HttpServletRequest request) throws UserNotFoundException {
        try{
            String emailId = (String) request.getAttribute("emailId");
            return new ResponseEntity<>(userMovieService.getAllUserMovies(emailId), HttpStatus.OK);
        }catch(UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
    }

//  http://localhost:9090/movie-app/update-payment-status
    @PostMapping("/update-payment-status")
    public ResponseEntity<?> updatePaymentStatus(@RequestParam String email, @RequestParam boolean isPaid) {
        try {
            User user = userMovieService.updatePaymentStatus(email, isPaid);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

//    http://localhost:9090/movie-app/admin/delete/{movieId}
    @DeleteMapping("/admin/delete/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable String movieId, HttpServletRequest request)
        throws UnauthorizedAccessException, MovieNotFoundException {
    try {
        String emailId = (String) request.getAttribute("emailId");
        String role = (String) request.getAttribute("role");
        System.out.println("emailId: " + emailId);
        System.out.println("role: " + role);

        if ("ROLE_ADMIN".equals(role)) {
            System.out.println("Role is admin and movie is deleted");
            userMovieService.deleteMovie(movieId, emailId);
//            return new ResponseEntity<>("Movie deleted successfully", HttpStatus.OK);NO_CONTENT)
            return new ResponseEntity<>("Movie deleted successfully", HttpStatus.NO_CONTENT);
        } else {
            System.out.println("Role admin not found");
            return new ResponseEntity<>("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }
    } catch (UnauthorizedAccessException | MovieNotFoundException e) {
        return new ResponseEntity<>("Movie Not Found", HttpStatus.NOT_FOUND);
    }
}



    //http://localhost:9090/movie-app/admin/delete/{movieId}
        @PutMapping("/admin/update/{movieId}")
    public ResponseEntity<?> updateUserMovieDetails(@PathVariable String movieId,@RequestBody Movie updatedMovie,
            HttpServletRequest request)
    {
        try {
            String emailId = (String) request.getAttribute("emailId");
            String role = (String) request.getAttribute("role");
            Movie updatedMovieResult = userMovieService.updateUserMovieFromList(emailId, movieId, updatedMovie);
            if (role.equals("ROLE_ADMIN")){
                return new ResponseEntity<>(updatedMovieResult, HttpStatus.OK);
            }else {
                return new  ResponseEntity<>("Claims not to update", HttpStatus.BAD_REQUEST);
            }
        } catch (UserNotFoundException | MovieNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/admin/add/movie")
    public ResponseEntity<?> addMovie(@RequestBody Movie movie, HttpServletRequest request) {
        try {
            String emailId = (String) request.getAttribute("emailId");
            String role = (String) request.getAttribute("role");

            System.out.println("Email in Controller: "+ emailId);
            System.out.println("Role in Controller: " + role);

            if ( role.equals("ROLE_ADMIN")) {
                System.out.println("Role in Condition pass and saved "+role);
                return new ResponseEntity<>(userMovieService.addMovie(movie, emailId), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Invalid or missing claims for admin role", HttpStatus.FORBIDDEN);
            }
        } catch (UnauthorizedAccessException e) {
            String role = (String) request.getAttribute("role");
            System.out.println("UNAUTHORIZED access in controller");
            System.out.println("User Role: " + role);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while adding the movie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:9090/movie-app/movies/genre/{genre}
    @GetMapping("/movies/genre/{genre}")
    public ResponseEntity<List<Movie>> getAllMoviesByGenre(@PathVariable String genre) {
        List<Movie> movies = userMovieService.getAllMoviesByGenre(genre);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    
    // http://localhost:9090/movie-app/all-movies
    @GetMapping("/all-movies")
    public ResponseEntity<?> getAllMovies() {
        List<Movie> movies = userMovieService.getAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

//    http://localhost:9090/movie-app/all-users
@GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(HttpServletRequest request) throws UnauthorizedAccessException, UserNotFoundException {
        try {
            String emailId = (String) request.getAttribute("emailId");
            String role = (String) request.getAttribute("role");

            if (role != null && role.equals("ROLE_ADMIN")) {
         System.out.println("Role in getAll pass and saved "+role);
         return new ResponseEntity<>(userMovieService.getAllUsers(emailId), HttpStatus.CREATED);
        } else {
        throw new UnauthorizedAccessException();
        }}
        catch (UnauthorizedAccessException e) {
        String role = (String) request.getAttribute("role");
        System.out.println("UNAUTHORIZED access in get all controller");
        System.out.println("User Role: " + role);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }


//    http://localhost:9090/movie-app/profile/update
//    @PutMapping("/profile/update")
//    public ResponseEntity<User> updateUserProfile(@RequestBody User updatedUser, HttpServletRequest request) {
//        try {
//            String emailId = (String) request.getAttribute("emailId");
//            System.out.println(emailId+" is in profile controller");
//
//            User updatedUserProfile = userMovieService.updateUserProfile(emailId, updatedUser);
//            return new ResponseEntity<>(updatedUserProfile, HttpStatus.OK);
//        } catch (UserNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


@PutMapping("/profile/update")
public ResponseEntity<User> updateUserProfile(
        @RequestPart("updatedUser") User updatedUser,
        @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture,
        HttpServletRequest request
) {
    try {
        String emailId = (String) request.getAttribute("emailId");
        System.out.println(emailId+" is in profile controller");

        User updatedUserProfile = userMovieService.updateUserProfile(emailId, updatedUser);
        return new ResponseEntity<>(updatedUserProfile, HttpStatus.OK);
    } catch (UserNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
}
