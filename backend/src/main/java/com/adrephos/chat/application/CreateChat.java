package com.adrephos.chat.application;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrephos.chat.application.dto.CreateChatDTO;
import com.adrephos.chat.domain.Chat;
import com.adrephos.chat.domain.repository.ChatRepository;

@Service
public class CreateChat {
  @Autowired
  ChatRepository chatRepository;

  public Chat run(CreateChatDTO dto) {
    UUID id = UUID.randomUUID();
    String firstUsername = dto.getFirstUsername();
    String secondUsername = dto.getSecondUsername();

    Chat chat = chatRepository.findByUsernames(firstUsername, secondUsername)
        .orElse(new Chat(id, firstUsername, secondUsername));

    try {
      chatRepository.create(chat);
    } catch (Exception e) {
      throw new RuntimeException("Failed to create chat", e);
    }

    return chat;
  }
}
