package com.stackroute.UserAuthentication.service;

import com.stackroute.UserAuthentication.model.User;

import java.util.Map;

public interface SecurityKeyGenerator {

    Map<String, String >JwtGenerator (User user);
}
