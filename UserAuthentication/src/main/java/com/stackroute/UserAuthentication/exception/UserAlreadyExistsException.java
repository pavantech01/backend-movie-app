package com.stackroute.UserAuthentication.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User with same Id Already Exists. Try to SignUp with a different User Id or If you want to SignIn, you can do so using the existing User Id")
public class UserAlreadyExistsException extends Exception{
}
