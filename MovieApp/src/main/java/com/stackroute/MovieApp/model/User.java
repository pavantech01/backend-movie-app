package com.stackroute.MovieApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class User {

    @Id
    private String emailId;
    private String userName, password, phoneNumber;
    private String role;
    private List<Movie> movieList;

    private boolean isPaid;
    private byte[] profilePicture;


}
