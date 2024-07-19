package com.stackroute.UserAuthentication.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "email-app", url = "http://localhost:65500")
public interface EmailServiceProxy {

    @PostMapping("/mail-app/send-mail")
    ResponseEntity<String> sendEmail(EmailDataDTO emailDataDTO);
}