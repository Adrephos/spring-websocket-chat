package com.adrephos.chat.application.dto;

import java.io.Serializable;
import java.util.List;

import com.adrephos.chat.domain.Chat;

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
public class ChatsDTO implements Serializable {
  private List<Chat> chats;
}
