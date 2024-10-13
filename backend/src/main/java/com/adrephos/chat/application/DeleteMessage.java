package com.adrephos.chat.application;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrephos.chat.domain.repository.MessageRepository;

@Service
public class DeleteMessage {
  @Autowired
  MessageRepository messageRepository;

  public void run(UUID id) {
    try {
      messageRepository.delete(id);
    } catch (Exception e) {
      throw new RuntimeException("Failed to delete message", e);
    }
  }
}
