package com.stackroute.UserAuthentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found. Please check the provided user ID or username and try again.")
public class UserNotFoundException extends Exception{
}

