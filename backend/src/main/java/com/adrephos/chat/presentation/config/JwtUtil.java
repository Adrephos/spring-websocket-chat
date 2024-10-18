package com.adrephos.chat.presentation.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
  private Algorithm algorithm;

  public JwtUtil() {
    String secretKey = System.getenv("SECRET_KEY");
    if (secretKey == null) {
      secretKey = "secret";
    }
    System.out.println("secretKey: " + secretKey);
    this.algorithm = Algorithm.HMAC256(secretKey);
  }

  public String create(String username) {
    return JWT.create()
        .withSubject(username)
        .withIssuer("spring-boot-chat")
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
        .sign(algorithm);
  }

  public boolean isValid(String token) {
    try {
      JWT.require(algorithm).build().verify(token);
      return true;
    } catch (JWTVerificationException e) {
      return false;
    }
  }

  public String getUsername(String token) {
    return JWT.require(algorithm).build().verify(token).getSubject();
  }
}
