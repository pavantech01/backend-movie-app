package com.stackroute.payment;

import com.razorpay.RazorpayException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentApplication {


	public static void main(String[] args) throws RazorpayException {
		SpringApplication.run(PaymentApplication.class, args);

	}

}
