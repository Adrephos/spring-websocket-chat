package com.adrephos.chat.application;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrephos.chat.application.dto.CreateMessageDTO;
import com.adrephos.chat.domain.Chat;
import com.adrephos.chat.domain.Message;
import com.adrephos.chat.domain.repository.ChatRepository;
import com.adrephos.chat.domain.repository.MessageRepository;

@Service
public class CreateMessage {
  @Autowired
  MessageRepository messageRepository;

  @Autowired
  ChatRepository chatRepository;

  public Message run(CreateMessageDTO dto) {
    UUID id = UUID.randomUUID();
    LocalDateTime sentAt = LocalDateTime.now();
    Boolean edited = false;
    LocalDateTime editedAt = null;

    Chat chat = chatRepository.findByUsernames(dto.getSenderUsername(), dto.getReceiverUsername())
        .orElseThrow(() -> new RuntimeException("Chat not found"));

    Message message = new Message(
        id,
        dto.getContent(),
        sentAt,
        edited,
        editedAt,
        chat.getId(),
        dto.getSenderUsername(),
        dto.getReceiverUsername());

    try {
      messageRepository.create(message);
    } catch (Exception e) {
      throw new RuntimeException("Failed to create message", e);
    }

    return message;
  }
}
