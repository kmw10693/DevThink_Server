package com.devthink.devthink_server.application;

import com.devthink.devthink_server.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticationServiceTest {
    private static final String secret = "12345678901234567890123456789012";
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {

        JwtUtil jwtUtil = new JwtUtil(secret);
        authenticationService = new AuthenticationService(jwtUtil);
    }
    
    @Test
    void login() {
        String accessToken = authenticationService.login();
        System.out.println(accessToken);
        assertThat(accessToken).contains(".");
    }

}
