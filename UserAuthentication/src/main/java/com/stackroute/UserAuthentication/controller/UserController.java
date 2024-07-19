package com.stackroute.UserAuthentication.controller;

import com.stackroute.UserAuthentication.exception.InvalidCredentialsException;
import com.stackroute.UserAuthentication.exception.UserAlreadyExistsException;
import com.stackroute.UserAuthentication.exception.UserNotFoundException;
import com.stackroute.UserAuthentication.feignclient.OTPVerificationDTO;
import com.stackroute.UserAuthentication.feignclient.SignUpData;
import com.stackroute.UserAuthentication.feignclient.UserProxy;
import com.stackroute.UserAuthentication.model.User;
import com.stackroute.UserAuthentication.service.SecurityKeyGenerator;
import com.stackroute.UserAuthentication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@CrossOrigin
@RequestMapping("/userAuth-app")
@RestController
public class UserController {

    private ResponseEntity<?> responseEntity;
    @Autowired
    private UserService userService;
    @Autowired
    private UserProxy userProxy;

    @Autowired
    private SecurityKeyGenerator securityKeyGenerator;
    private Map<String, String> otpMap = new HashMap<>();



    @Autowired
    public UserController(UserService userService, SecurityKeyGenerator securityKeyGenerator){
        this.userService=userService;
        this.securityKeyGenerator=securityKeyGenerator;
    }


    // http://localhost:8080/userAuth-app/registerUser    [POST]
    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody SignUpData signUpData, User user) throws UserAlreadyExistsException {
        signUpData.setRole("ROLE_USER");

//        signUpData.setRole("ROLE_ADMIN");
//       user.setStatus("Active");

        try {
//            if (!validateOTP(signUpData.getEmailId(), signUpData.getOtp())) {
//                return new ResponseEntity<>("Invalid OTP. Please try again.", HttpStatus.BAD_REQUEST);
//            }
            return new ResponseEntity<>(userService.registerUser(signUpData), HttpStatus.OK);

        }
        catch (UserAlreadyExistsException e){
            throw new UserAlreadyExistsException();
        }

    }
    // Validate the entered OTP
//    private boolean validateOTP(String emailId, String enteredOTP) {
//        // Retrieve the stored OTP for the given email from the Map or database
//        String storedOTP = otpMap.get(emailId);
//        return storedOTP != null && storedOTP.equals(enteredOTP);
//    }

    // http://localhost:8080/userAuth-app/verify-otp
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestBody OTPVerificationDTO otpVerificationDTO) {
        System.out.println("Received OTP Verification Request: " + otpVerificationDTO.toString());

        boolean isVerified = userService.verifyOTP(otpVerificationDTO);
        if (isVerified) {
            System.out.println("OTP verified successfully. You have completed the registration");
            return new ResponseEntity<>( HttpStatus.OK);
//            "OTP verified successfully. You have completed the registration.",
        } else {
            System.out.println("Invalid OTP. Please try again.");
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
//            "Invalid OTP. Please try again.",

        }
    }




    // http://localhost:8888/userAuth-app/loginUser   [POST]
    @PostMapping("/loginUser")
    public ResponseEntity<?> loginCheck(@RequestBody User user) throws InvalidCredentialsException, UserNotFoundException {
        User result = userService.loginCheck(user.getEmailId(),user.getPassword());
        if(result!=null){
            result.setPassword("***");
            return new ResponseEntity<>(securityKeyGenerator.JwtGenerator(result), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Login failed. Please check your emailId and password and try again.",HttpStatus.NOT_FOUND);
        }
    }

    // http://localhost:8888/userAuth-app/profile/update   [POST]
    @PutMapping("/profile/update")
    public ResponseEntity<User> updateUserProfile(@RequestBody User updatedUser, HttpServletRequest request) {
        try {
            String emailId = (String) request.getAttribute("emailId");
            System.out.println(emailId + " is in profile controller");

            User updatedUserProfile = userService.updateUserProfile(emailId, updatedUser);
            return new ResponseEntity<>(updatedUserProfile, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
