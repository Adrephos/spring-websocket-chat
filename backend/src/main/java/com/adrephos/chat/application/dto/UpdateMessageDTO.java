package com.adrephos.chat.application.dto;

import java.io.Serializable;
import java.util.UUID;

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
public class UpdateMessageDTO implements Serializable {
  private UUID id;
  private String content;
}
