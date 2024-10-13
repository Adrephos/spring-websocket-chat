package com.adrephos.chat.infrastructure.repository.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "messages")
public class MessageEntity {
  @Id
  @Column(name = "message_id")
  private UUID id;

  private String content;

  @Column(name = "sent_at")
  private LocalDateTime sentAt;

  private Boolean edited;
  
  @Column(name = "edited_at")
  private LocalDateTime editedAt;

  @Column(name = "chat_id")
  private UUID chatId;

  @Column(name = "sender_username")
  private String senderUsername;

  @Column(name = "receiver_username")
  private String receiverUsername;
}
