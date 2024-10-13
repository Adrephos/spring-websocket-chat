package com.adrephos.chat.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NonNull
@AllArgsConstructor
public class User {
  private String username;
  private String password;
  private String email;
}
