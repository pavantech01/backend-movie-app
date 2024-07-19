package com.stackroute.UserAuthentication.service;

import com.stackroute.UserAuthentication.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtGenerateImpl implements SecurityKeyGenerator {
    @Override
    public Map<String, String> JwtGenerator(User user) {

        String key = "securekeysecurekeysecurekeysecurekeysecurekeysecurekeysecurekeyse" +
                "curekeysecurekeysecurekeysecurekeysecurekeysecurekeysecurekeysecurekeysecurekeysecurekeysecurekey";

        Map<String, String>result = new HashMap<String, String>();
        Map<String, Object> claims = new HashMap<String,Object>();

        claims.put("emailId",user.getEmailId());
        claims.put("userName",user.getUserName());
        claims.put("role",user.getRole());


//        long expirationTimeMillis = System.currentTimeMillis() + (15 * 60 * 1000); // 15 minutes in milliseconds
//        Date expirationDate = new Date(expirationTimeMillis);


        String token = Jwts.builder()
                .setSubject("testing token")
                .setClaims(claims)
                .setIssuer("auth-app")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+3600000)) //1000ms=1min
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        result.put("key",token);
        result.put("emailId", user.getEmailId());
//        result.put("password",user.getPassword());
        result.put("role",user.getRole());
        result.put("userName",user.getUserName()); // added to see username
        result.put("message","Login success");
        return result;
    }
}
