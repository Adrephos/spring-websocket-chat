package com.adrephos.chat.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Getter
@Setter
@NonNull
@AllArgsConstructor
public class Chat {
  private UUID id;
  private String firstUsername;
  private String secondUsername;
}
