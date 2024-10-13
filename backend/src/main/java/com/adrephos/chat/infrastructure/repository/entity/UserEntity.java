package com.adrephos.chat.infrastructure.repository.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {
  @Id
  private String username;

  private String password;

  private String email;
}
