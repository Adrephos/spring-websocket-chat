package com.adrephos.chat.infrastructure.repository.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "chats")
public class ChatEntity {
  @Id
  @Column(name = "chat_id")
  private UUID  id;

  @Column(name = "first_username")
  private String firstUsername;

  @Column(name = "second_username")
  private String secondUsername;
}
