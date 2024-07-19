package com.stackroute.payment.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private RazorpayClient razorpayClient;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> data) throws RazorpayException {
        try {

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", data.get("amount"));
            orderRequest.put("currency", "INR");
            orderRequest.put("payment_capture", 1);

            double amount = Double.parseDouble(data.get("amount").toString());

            // Create an order
            Order order = razorpayClient.Orders.create(orderRequest);

            // Convert Order to JSON string
            return ResponseEntity.ok(order.toString());
        }catch (RazorpayException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order: " + e.getMessage());
        }


    }
}
