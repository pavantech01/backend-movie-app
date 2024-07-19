package com.stackroute.MovieApp.feignclient;

import com.stackroute.MovieApp.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "userAuth-app",url = "localhost:8888")
public interface AuthProxy {

    @PutMapping("userAuth-app/profile/update")
    public abstract ResponseEntity<String> sendMovieDataToAuthApp(User user);
}
