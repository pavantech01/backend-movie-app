package com.stackroute.UserAuthentication.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    private String emailId;

    private String userName, password,phoneNumber, role,status;

//    private List<Movie> movieList;

}
