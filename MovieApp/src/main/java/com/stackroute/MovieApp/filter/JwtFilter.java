package com.stackroute.MovieApp.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtFilter extends GenericFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String key = "securekeysecurekeysecurekeysecurekeysecurekeysecurekeysecurekeyse" +
                "curekeysecurekeysecurekeysecurekeysecurekeysecurekeysecurekeysecurekeysecurekeysecurekeysecurekey";
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String authHeader = request.getHeader("authorization");
        System.out.println("filter started");
        if(authHeader==null || !authHeader.startsWith("Bearer")){
            throw new ServletException();
        }
        else{

            String token = authHeader.substring(7);
            System.out.println("Received Token: " + token);

            try{
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody(); //handle
            System.out.println("Claims: " + claims);

//            getting email and role from claim
            String emailId=(String)claims.get("emailId");
            String role = (String) claims.get("role");

//          checking in console
            System.out.println("emailId in filter = "+emailId);
            System.out.println("role in filter = "+role);

//          retrieving email and role
            request.setAttribute("emailId",emailId);
            request.setAttribute("role",role);

            filterChain.doFilter(request,servletResponse); // forward to controller
            }
//            catch (ServletException e){
//                e.printStackTrace();
//
//            }
            catch (Exception e) {
                // Handle any other exceptions
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized msg from filter");
            }

        }
    }
}
