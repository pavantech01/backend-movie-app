package com.stackroute.emailapp1.controller;

import com.stackroute.emailapp1.model.EmailData;
import com.stackroute.emailapp1.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail-app")
public class EmailController {
    @Autowired
    private EmailService  emailService;

    /* POST
    http://localhost:65500/mail-app/send-mail
    */
    @PostMapping("/send-mail")
    public ResponseEntity<?> sendEmail(@RequestBody EmailData emailData){
        return new ResponseEntity<>(emailService.sendEmail(emailData), HttpStatus.OK);
    }
}
