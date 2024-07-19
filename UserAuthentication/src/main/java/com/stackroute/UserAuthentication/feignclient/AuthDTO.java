package com.stackroute.UserAuthentication.feignclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthDTO {
    private String emailId, userName,password, phoneNumber, role;
//    private List<Movie> movieList;

}
