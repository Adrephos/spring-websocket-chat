package com.adrephos.chat.application.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NonNull
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO implements Serializable {
  private String username;
  private String email;
  private String password;
}
