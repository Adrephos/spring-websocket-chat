package com.adrephos.chat.presentation.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
  @Autowired
  JwtUtil jwtUtil;

  @Autowired
  UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer")) {
      filterChain.doFilter(request, response);
      return;
    }

    String jwt = authHeader.split(" ")[1].trim();

    if (!this.jwtUtil.isValid(jwt)) {
      filterChain.doFilter(request, response);
      return;
    }

    String username = this.jwtUtil.getUsername(jwt);
    User user = (User) this.userDetailsService.loadUserByUsername(username);

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        user.getUsername(), user.getPassword(), user.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    filterChain.doFilter(request, response);
  }
}
