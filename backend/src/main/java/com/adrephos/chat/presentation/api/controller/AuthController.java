package com.adrephos.chat.presentation.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrephos.chat.application.dto.LoginDTO;
import com.adrephos.chat.application.dto.RegisterDTO;
import com.adrephos.chat.application.dto.TokenResponseDTO;
import com.adrephos.chat.application.dto.UserDTO;
import com.adrephos.chat.domain.User;
import com.adrephos.chat.domain.repository.UserRepository;
import com.adrephos.chat.presentation.config.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

  @Autowired
  UserRepository userRepository;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtUtil jwtUtil;

  @PostMapping("/login")
  public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginDTO dto) {
    UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
        dto.getUsername(), dto.getPassword());
    Authentication authentication = authenticationManager.authenticate(login);
    String jwt = jwtUtil.create(dto.getUsername());
    User user = userRepository.findByUsername(dto.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found"));

    return ResponseEntity.ok(new TokenResponseDTO(
        jwt,
        new UserDTO(
            user.getUsername(),
            user.getEmail())));
  }

  @PostMapping("/register")
  public ResponseEntity<TokenResponseDTO> register(@RequestBody RegisterDTO dto) {
    if (!userRepository.findByUsername(dto.getUsername()).isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    if (dto.getUsername() == null || dto.getUsername().isEmpty() || dto.getPassword() == null
        || dto.getPassword().isEmpty() || dto.getEmail() == null || dto.getEmail().isEmpty()
        || !dto.getEmail().matches(EMAIL_REGEX)) {
      return ResponseEntity.badRequest().build();
    }

    String encodedPassword = passwordEncoder.encode(dto.getPassword());
    User user = userRepository.create(
        new User(dto.getUsername(), encodedPassword, dto.getEmail()));

    UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
        dto.getUsername(), dto.getPassword());
    Authentication authentication = authenticationManager.authenticate(login);
    String jwt = jwtUtil.create(dto.getUsername());

    return ResponseEntity.ok(new TokenResponseDTO(
        jwt,
        new UserDTO(
            user.getUsername(),
            user.getEmail())));
  }
}
