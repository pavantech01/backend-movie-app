package com.stackroute.MovieApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "You are not authorized for this, You need to be admin to access this URL")
public class UnauthorizedAccessException extends Exception{
}
