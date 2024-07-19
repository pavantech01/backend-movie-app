package com.stackroute.emailapp1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailData {
    private String emailId, messageBody, subject, attachment;
    private String otp;

}
