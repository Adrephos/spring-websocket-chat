package com.adrephos.chat.presentation.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
  private static String SECRET_KEY = "secret";

  private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

  public String create(String username) {
    return JWT.create()
        .withSubject(username)
        .withIssuer("spring-boot-chat")
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
        .sign(ALGORITHM);
  }

  public boolean isValid(String token) {
    try {
      JWT.require(ALGORITHM).build().verify(token);
      return true;
    } catch (JWTVerificationException e) {
      return false;
    }
  }

  public String getUsername(String token) {
    return JWT.require(ALGORITHM).build().verify(token).getSubject();
  }
}
