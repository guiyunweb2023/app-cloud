package org.example.admin.modules.auth.service;


import org.example.admin.modules.auth.util.user.JwtAuthenticationRequest;

import java.util.Map;

public interface AuthService {
    Map login(JwtAuthenticationRequest authenticationRequest) throws Exception;
    String refresh(String oldToken) throws Exception;
    void validate(String token) throws Exception;

    void logout(String token) throws Exception;
}
