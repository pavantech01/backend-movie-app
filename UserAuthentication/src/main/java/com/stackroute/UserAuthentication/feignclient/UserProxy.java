package com.stackroute.UserAuthentication.feignclient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient(name="movie-app",url = "http://localhost:9090")
public interface UserProxy {


    @PostMapping("/movie-app/register")
    public abstract ResponseEntity<String> sendUserDataToProductApp(AuthDTO authDTO);

    @PutMapping("/movie-app/profile/update")
    public abstract ResponseEntity<?> sendUpdateUserDataToProductApp(AuthDTO authDTO);

}
