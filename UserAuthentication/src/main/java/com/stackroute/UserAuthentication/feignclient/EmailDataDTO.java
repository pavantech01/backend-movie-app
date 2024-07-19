package com.stackroute.UserAuthentication.feignclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDataDTO {
    private String emailId, messageBody, subject, attachment;
}
