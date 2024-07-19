package com.stackroute.UserAuthentication.service;

import com.stackroute.UserAuthentication.exception.InvalidCredentialsException;
import com.stackroute.UserAuthentication.exception.UserAlreadyExistsException;
import com.stackroute.UserAuthentication.exception.UserNotFoundException;
import com.stackroute.UserAuthentication.feignclient.OTPVerificationDTO;
import com.stackroute.UserAuthentication.feignclient.SignUpData;
import com.stackroute.UserAuthentication.model.User;

public interface UserService {
//    public abstract User registerUser(User user) throws UserAlreadyExistsException;
    public abstract User registerUser(SignUpData signUpData) throws UserAlreadyExistsException;

    public abstract User loginCheck(String emailId, String password) throws InvalidCredentialsException, UserNotFoundException;

    boolean verifyOTP(OTPVerificationDTO otpVerificationDTO);

    public User updateUserProfile(String emailId, User updatedUser) throws UserNotFoundException;
    }
