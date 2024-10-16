package com.adrephos.chat.application.dto;

import java.io.Serializable;
import java.util.List;

import com.adrephos.chat.domain.Message;

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
public class MessagesDTO implements Serializable {
  private List<Message> messages;
}
