package com.adrephos.chat.application;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrephos.chat.domain.Message;
import com.adrephos.chat.application.dto.UpdateMessageDTO;
import com.adrephos.chat.domain.repository.MessageRepository;

@Service
public class UpdateMessage {
  @Autowired
  MessageRepository messageRepository;

  public Message run(UpdateMessageDTO dto) {
    Message existingMessage = messageRepository.find(dto.getId())
        .orElseThrow(() -> new RuntimeException("Message not found"));
    existingMessage.setContent(dto.getContent());
    existingMessage.setEdited(true);
    existingMessage.setEditedAt(LocalDateTime.now());

    try {
      messageRepository.update(existingMessage);
    } catch (Exception e) {
      throw new RuntimeException("Failed to update message", e);
    }

    return existingMessage;
  }
}
