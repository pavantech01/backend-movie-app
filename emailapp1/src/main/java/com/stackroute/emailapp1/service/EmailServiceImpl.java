package com.stackroute.emailapp1.service;

import com.stackroute.emailapp1.model.EmailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Random;

@Service
public class EmailServiceImpl implements  EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;



    @Override
    public String sendEmail(EmailData emailData) {
        System.out.println(emailData);
        try {
//            String generatedOtp = generateOTP();


            SimpleMailMessage mailMessage = new SimpleMailMessage();

//            emailData.setSubject("Confirmation mail");
//            emailData.setMessageBody("Your Registration will be Successful shortly please confirm the OTP :"+ generatedOtp);

            mailMessage.setFrom(sender);
            mailMessage.setTo(emailData.getEmailId());
            mailMessage.setText(emailData.getMessageBody());
            mailMessage.setSubject(emailData.getSubject());
            javaMailSender.send(mailMessage);
            return "Mail Sent to "+emailData.getEmailId();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Sending mail failed...";
        }
    }

//    private String generateOTP() {
//        // Generate a 6-digit random OTP
//        Random random = new Random();
//        int otp = 100000 + random.nextInt(900000);
//        return String.valueOf(otp);
//    }
}


