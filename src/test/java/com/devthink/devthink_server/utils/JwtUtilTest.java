package com.devthink.devthink_server.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class JwtUtilTest {
    private JwtUtil jwtUtil;
    private static final String SECRET = "12345678901234567890123456789012";
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9" +
            ".eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaDk";
    private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9" +
            ".eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaDa";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(SECRET);
    }
    @Test
    void encode() {
        String token = jwtUtil.encode(1L);

        assertThat(token).isEqualTo("");
    }

    @Test
    void decode() {
        Claims claims = jwtUtil.decode(TOKEN);
        assertThat(claims.get("userId", Long.class)).isEqualTo(1L);
    }

    @Test
    void 올바르지_않은_토큰() {
        assertThatThrownBy(() -> jwtUtil.decode(INVALID_TOKEN))
                .isInstanceOf(SignatureException.class);
    }

}
