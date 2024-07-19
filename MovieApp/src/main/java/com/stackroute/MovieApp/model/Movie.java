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

@Document(collection = "movies")
public class Movie {

    @Id
    private String movieId;
//    private String emailId,role;

    private String movieName,genre,director,yearOfRelease;
    private List<String> leadActors;
    private int rating;
    private String poster;
}
