package com.adrephos.chat.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrephos.chat.domain.Message;
import com.adrephos.chat.domain.repository.MessageRepository;

@Service
public class GetChatMessages {
  @Autowired
  MessageRepository messsageRepository;

  public Optional<List<Message>> run(UUID id) {
    Optional<List<Message>> messageList = messsageRepository.findByChatId(id);
    if (messageList.get().size() == 0) {
      return Optional.empty();
    }
    return messageList;
  }
}
