package com.stackroute.MovieApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Movie not found with specific id")
public class MovieNotFoundException extends Exception {

}