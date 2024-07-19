package com.stackroute.UserAuthentication.service;

import com.stackroute.UserAuthentication.exception.InvalidCredentialsException;
import com.stackroute.UserAuthentication.exception.UserAlreadyExistsException;
import com.stackroute.UserAuthentication.exception.UserNotFoundException;
import com.stackroute.UserAuthentication.feignclient.*;
import com.stackroute.UserAuthentication.model.User;
import com.stackroute.UserAuthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProxy userProxy;


    @Autowired
    private EmailServiceProxy emailServiceProxy;

    private Map<String, String> otpMap = new HashMap<>();


    @Override
    public User registerUser(SignUpData signUpData) throws UserAlreadyExistsException {

        if (userRepository.findByEmailId(signUpData.getEmailId()) != null) {
            throw new UserAlreadyExistsException();
        }
//        emailId, userName, password, phoneNumber;
        AuthDTO authDTO =new AuthDTO(signUpData.getEmailId(),signUpData.getUserName(),signUpData.getPassword(),signUpData.getPhoneNumber(),signUpData.getRole());
        ResponseEntity<?> responseEntity=userProxy.sendUserDataToProductApp(authDTO);
        System.out.println(responseEntity);


        // Generate OTP and send it via email
        String otp = generateOTP();
        EmailDataDTO emailDataDTO = new EmailDataDTO(signUpData.getEmailId(), "Hello "+signUpData.getUserName()+" Enter the given OTP to verify your account"+"\n"+"Your OTP is : " + otp, "OTP for Registration", null);
        emailServiceProxy.sendEmail(emailDataDTO);

        otpMap.put(signUpData.getEmailId(), otp);


//        User user=new User(signUpData.getEmailId(),signUpData.getUserName(),signUpData.getPhoneNumber(),signUpData.getPassword(),signUpData.getRole());
        User user = new User(signUpData.getEmailId(), signUpData.getUserName(), signUpData.getPassword(), signUpData.getPhoneNumber(), signUpData.getRole(),"ACTIVE");
        return userRepository.save(user);
    }


//@Override
//public User registerUser(SignUpData signUpData) throws UserAlreadyExistsException {
//    // Check if the email already exists
//    if (userRepository.findByEmailId(signUpData.getEmailId()) != null) {
//        throw new UserAlreadyExistsException();
//    }
//
//    AuthDTO authDTO =new AuthDTO(signUpData.getEmailId(),signUpData.getUserName(),signUpData.getPassword(),signUpData.getPhoneNumber(),signUpData.getRole());
//    ResponseEntity<?> responseEntity = userProxy.sendUserDataToProductApp(authDTO);
//    System.out.println(responseEntity);
//
//    String otp = generateOTP();
//    EmailDataDTO emailDataDTO = new EmailDataDTO(signUpData.getEmailId(), "Hello "+signUpData.getUserName()+" Enter the given OTP to verify your account"+"\n"+"Your OTP is : " + otp, "OTP for Registration", null);
//    emailServiceProxy.sendEmail(emailDataDTO);
//
//    User user = new User(signUpData.getEmailId(), signUpData.getUserName(), signUpData.getPassword(), signUpData.getPhoneNumber(), signUpData.getRole(),"ACTIVE");
//    return userRepository.save(user);
//}


    private String generateOTP() {
        // Generate a 6-digit random OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public boolean verifyOTP(OTPVerificationDTO otpVerificationDTO) {
        // Retrieve the stored OTP for the given email from the Map or database
        String storedOTP = otpMap.get(otpVerificationDTO.getEmailId());
//         Check if the entered OTP matches the stored OTP
        return storedOTP != null && storedOTP.equals(otpVerificationDTO.getOtp());
    }




    @Override
    public User loginCheck(String emailId, String password) throws InvalidCredentialsException, UserNotFoundException {
        System.out.println("email"+emailId);
        System.out.println("password"+password);

        User loggedInUser =userRepository.findByEmailIdAndPassword(emailId,password);
        System.out.println(loggedInUser);

        if (loggedInUser!=null){
            return loggedInUser; // login
        } else if (loggedInUser==null) {  //if value is null = not retrieved from repos (not found/not exists)
            throw new UserNotFoundException(); //login fail
        } else
            throw new InvalidCredentialsException(); // login fail because email and password not found

        }


    @Override
    public User updateUserProfile(String emailId, User updatedUser) throws UserNotFoundException {
        User existingUser = userRepository.findByEmailId(emailId);
        System.out.println("the user in service "+existingUser);

        if (existingUser != null) {
            AuthDTO authDTO =new AuthDTO(updatedUser.getEmailId(),updatedUser.getUserName(),updatedUser.getPassword(),updatedUser.getPhoneNumber(),updatedUser.getRole());
            ResponseEntity<?> responseEntity=userProxy.sendUpdateUserDataToProductApp(authDTO);
            System.out.println(responseEntity);

            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setPassword(updatedUser.getPassword());
            return userRepository.save(existingUser);
        } else {
            System.out.println("user not found");
            throw new UserNotFoundException();
        }
    }



}
