package com.stackroute.UserAuthentication.feignclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpData {
    private String emailId, userName,role, phoneNumber, password;
//    private List<Movie> movieList;
}
