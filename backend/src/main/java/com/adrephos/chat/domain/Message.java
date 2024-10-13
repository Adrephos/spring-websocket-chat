package com.adrephos.chat.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Message {
  @NonNull
  private UUID id;

  @NonNull
  private String content;

  @NonNull
  private LocalDateTime sentAt;

  @NonNull
  private Boolean edited;

  private LocalDateTime editedAt;

  @NonNull
  private UUID chatId;

  @NonNull
  private String senderUsername;

  @NonNull
  private String receiverUsername;
}
